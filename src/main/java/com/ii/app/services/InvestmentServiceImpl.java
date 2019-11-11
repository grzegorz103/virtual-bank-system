package com.ii.app.services;

import com.ii.app.dto.in.InvestmentIn;
import com.ii.app.dto.out.InvestmentOut;
import com.ii.app.exceptions.ApiException;
import com.ii.app.mappers.InvestmentMapper;
import com.ii.app.models.Investment;
import com.ii.app.models.enums.InvestmentType;
import com.ii.app.repositories.InvestmentRepository;
import com.ii.app.repositories.InvestmentTypeRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.services.interfaces.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;

    private final InvestmentTypeRepository investmentTypeRepository;

    private final InvestmentMapper investmentMapper;

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
        return investmentMapper.entityToDTO(
            investmentRepository.findAllByDestinedSaldo_BankAccount_User_Identifier(identifier)
        );
    }

    @Override
    public InvestmentOut findById(Long id) {
        return investmentMapper.entityToDTO(
            investmentRepository.findById(id).orElseThrow(() -> new ApiException("Exception.notFound", null))
        );
    }

    @Override
    public Long findInvestmentCountByType(Long id) {
        return investmentRepository.countAllByInvestmentType(
            investmentTypeRepository.findById(id).orElseThrow(() -> new ApiException("Exception.notFound", null)));
    }

    @Override
    public InvestmentOut updateStatus(Long id) {
        Investment investment = investmentRepository.findById(id).orElseThrow(() -> new ApiException("Exception.notFound", null));
        switch (investment.getInvestmentType().getInvestmentStatus()) {
            case ACTIVE:
                investment.setInvestmentType(investmentTypeRepository.findByInvestmentStatus(InvestmentType.InvestmentStatus.CLOSED));
                break;
            case CLOSED:
                investment.setInvestmentType(investmentTypeRepository.findByInvestmentStatus(InvestmentType.InvestmentStatus.ACTIVE));
                break;
        }
        return investmentMapper.entityToDTO(investmentRepository.save(investment));
    }

    @Override
    public InvestmentOut create(InvestmentIn investment) {
        Investment mapped = investmentMapper.dtoToEntity(investment);
        mapped.setInvestmentType(investmentTypeRepository.findByInvestmentStatus(InvestmentType.InvestmentStatus.ACTIVE));
        mapped.setCreationDate(Instant.now());
        mapped.setCurrentBalance(mapped.getCurrentBalance());
        mapped.setDestinedSaldo(saldoRepository.findById(investment.getDestinedSaldoId()).orElseThrow(() -> new ApiException("Exception.notFound", null)));
        return investmentMapper.entityToDTO(investmentRepository.save(mapped));
    }

    private Investment calculateProfit(Investment investment) {
        return null;
    }
}
