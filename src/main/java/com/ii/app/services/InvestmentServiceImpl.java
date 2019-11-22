package com.ii.app.services;

import com.ii.app.dto.in.InvestmentIn;
import com.ii.app.dto.out.InvestmentOut;
import com.ii.app.exceptions.ApiException;
import com.ii.app.mappers.InvestmentMapper;
import com.ii.app.models.Investment;
import com.ii.app.models.Saldo;
import com.ii.app.models.enums.InvestmentType;
import com.ii.app.repositories.InvestmentRepository;
import com.ii.app.repositories.InvestmentTypeRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.services.interfaces.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;

    private final InvestmentTypeRepository investmentTypeRepository;

    private final InvestmentMapper investmentMapper;

    private final float DAILY_PERCENT_PROFIT = 5f;

    @Autowired
    private SaldoRepository saldoRepository;

    @Autowired
    public InvestmentServiceImpl(InvestmentMapper investmentMapper,
                                 InvestmentRepository investmentRepository,
                                 InvestmentTypeRepository investmentTypeRepository) {
        this.investmentMapper = investmentMapper;
        this.investmentRepository = investmentRepository;
        this.investmentTypeRepository = investmentTypeRepository;
    }

    @Override
    public List<InvestmentOut> findAll() {
        return investmentMapper.entityToDTO(investmentRepository.findAll());
    }

    @Override
    public List<InvestmentOut> findAllByUser() {
        String identifier = SecurityContextHolder.getContext().getAuthentication().getName();
        return investmentRepository.findAllByDestinedSaldo_BankAccount_User_Identifier(identifier)
            .stream()
            .map(this::calculateProfit)
            .collect(Collectors.toList());
    }

    @Override
    public InvestmentOut findById(Long id) {
        return investmentMapper.entityToDTO(investmentRepository.findById(id).orElseThrow(() -> new ApiException("Exception.notFound", null)));
    }

    @Override
    public Long findInvestmentCountByType(Long id) {
        return investmentRepository.countAllByInvestmentType(
            investmentTypeRepository.findById(id).orElseThrow(() -> new ApiException("Exception.notFound", null)));
    }

    @Override
    public InvestmentOut updateStatus(Long id) {
        Investment investment = investmentRepository.findById(id).orElseThrow(() -> new ApiException("Exception.notFound", null));
        if (investment.getInvestmentType().getInvestmentStatus() == InvestmentType.InvestmentStatus.CLOSED) {
            throw new ApiException("Exception.investmentAlreadyClosed", null);
        }
        calculateProfit(investment);
        investment.getDestinedSaldo().setBalance(investment.getDestinedSaldo().getBalance().add(investment.getCurrentBalance()));
        investment.setInvestmentType(investmentTypeRepository.findByInvestmentStatus(InvestmentType.InvestmentStatus.CLOSED));
        return investmentMapper.entityToDTO(investmentRepository.save(investment));
    }

    @Override
    public InvestmentOut create(InvestmentIn investment) {
        Saldo destinedSaldo = saldoRepository.findById(investment.getDestinedSaldoId()).orElseThrow(() -> new ApiException("Exception.notFound", null));
        if (destinedSaldo.getBalance().compareTo(investment.getStartBalance()) < 0) {
            throw new ApiException("Exception.notEnoughBalanceSaldo", null);
        }
        destinedSaldo.setBalance(destinedSaldo.getBalance().subtract(investment.getStartBalance()));
        Investment mapped = investmentMapper.dtoToEntity(investment);
        mapped.setInvestmentType(investmentTypeRepository.findByInvestmentStatus(InvestmentType.InvestmentStatus.ACTIVE));
        Instant currentTime = Instant.now();
        mapped.setCreationDate(currentTime);
        mapped.setUpdateTimespan(currentTime);
        mapped.setCurrentBalance(mapped.getStartBalance());
        mapped.setDestinedSaldo(destinedSaldo);
        mapped.setUpdateTimespan(currentTime);
        mapped.setCurrency(destinedSaldo.getCurrencyType().getName());

        return investmentMapper.entityToDTO(investmentRepository.save(mapped));
    }

    @Override
    public List<InvestmentOut> findActiveByBankAccountId(Long bankAccountId) {
        return investmentRepository.findAllByInvestmentTypeAndDestinedSaldo_BankAccount_Id(investmentTypeRepository.findByInvestmentStatus(InvestmentType.InvestmentStatus.ACTIVE), bankAccountId)
            .stream()
            .map(investmentMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    private InvestmentOut calculateProfit(Investment investment) {
        if (investment == null)
            return null;

        if (investment.getInvestmentType().getInvestmentStatus() == InvestmentType.InvestmentStatus.CLOSED)
            return investmentMapper.entityToDTO(investment);

        Instant currentTime = Instant.now();

        long secondsTimespan = ChronoUnit.SECONDS.between(investment.getUpdateTimespan(), currentTime);

        float startBalancePercent = investment.getStartBalance().floatValue() * (DAILY_PERCENT_PROFIT / 100f);
        float profitPerSecond = startBalancePercent / Duration.ofDays(1).getSeconds();

        investment.setCurrentBalance(
            investment.getCurrentBalance().add(BigDecimal.valueOf(profitPerSecond * secondsTimespan)).setScale(2, RoundingMode.DOWN)
        );
        investment.setUpdateTimespan(currentTime);

        return investmentMapper.entityToDTO(investmentRepository.save(investment));
    }

}
