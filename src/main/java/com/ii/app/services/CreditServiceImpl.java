package com.ii.app.services;

import com.ii.app.dto.in.CreditIn;
import com.ii.app.dto.out.CreditOut;
import com.ii.app.dto.out.CreditStatusOut;
import com.ii.app.dto.out.InvestmentOut;
import com.ii.app.mappers.CreditMapper;
import com.ii.app.mappers.CreditStatusMapper;
import com.ii.app.models.Credit;
import com.ii.app.models.Saldo;
import com.ii.app.models.enums.CreditStatus;
import com.ii.app.repositories.CreditRepository;
import com.ii.app.repositories.CreditStatusRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.services.interfaces.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;

    private final SaldoRepository saldoRepository;

    private final CreditMapper creditMapper;

    private final CreditStatusRepository creditStatusRepository;

    private final CreditStatusMapper creditStatusMapper;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository,
                             CreditMapper creditMapper,
                             SaldoRepository saldoRepository,
                             CreditStatusRepository creditStatusRepository,
                             CreditStatusMapper creditStatusMapper) {
        this.creditRepository = creditRepository;
        this.creditMapper = creditMapper;
        this.saldoRepository = saldoRepository;
        this.creditStatusRepository = creditStatusRepository;
        this.creditStatusMapper = creditStatusMapper;
    }

    @Override
    public CreditOut create(CreditIn creditIn) {
        Credit mapped = creditMapper.DTOtoEntity(creditIn);
        mapped.setBalancePaid(BigDecimal.ZERO);

        Saldo destinedSaldo = saldoRepository.findById(creditIn.getDestinedSaldoId())
            .orElseThrow(() -> new RuntimeException("Not found"));

        mapped.setAcceptedAlready(false);
        mapped.setCurrency(destinedSaldo.getCurrencyType().getName());
        mapped.setDestinedSaldo(destinedSaldo);
        mapped.setInstallments(new HashSet<>());
        mapped.setCreditStatus(creditStatusRepository.findByCreditType(CreditStatus.CreditType.AWAITING));

        BigDecimal installmentAmount = new BigDecimal(
            creditIn.getTotalBalance().floatValue() / creditIn.getTotalInstallmentCount()
        ).setScale(2, BigDecimal.ROUND_UP);
        mapped.setInstallmentAmount(installmentAmount);

        return creditMapper.entityToDTO(creditRepository.save(mapped));
    }

    @Override
    public CreditOut changeStatus(Long creditId) {
        Credit credit = creditRepository.findById(creditId).orElseThrow(() -> new RuntimeException("Credit not found"));
        switch (credit.getCreditStatus().getCreditType()) {
            case ACTIVE:
                credit.setCreditStatus(creditStatusRepository.findByCreditType(CreditStatus.CreditType.PAID));
                break;
            case CANCELED:
                credit.setCreditStatus(creditStatusRepository.findByCreditType(CreditStatus.CreditType.AWAITING));
                break;
            case PAID:
                credit.setCreditStatus(creditStatusRepository.findByCreditType(CreditStatus.CreditType.CANCELED));
                break;
            case AWAITING:
                credit.setCreditStatus(creditStatusRepository.findByCreditType(CreditStatus.CreditType.ACTIVE));
                if (!credit.isAcceptedAlready()) {
                    credit.setAcceptedAlready(true);
                    transferBalance(credit);
                }
                break;
        }

        return creditMapper.entityToDTO(creditRepository.save(credit));
    }

    @Override
    public CreditOut changeStatus(Long creditId, CreditStatus.CreditType creditType) {
        Credit credit = creditRepository.findById(creditId).orElseThrow(() -> new RuntimeException("Credit not found"));
        credit.setCreditStatus(creditStatusRepository.findByCreditType(creditType));

        if (credit.getCreditStatus().getCreditType() == CreditStatus.CreditType.ACTIVE && !credit.isAcceptedAlready()) {
            credit.setAcceptedAlready(true);
            transferBalance(credit);
        }

        return creditMapper.entityToDTO(creditRepository.save(credit));
    }

    @Override
    public List<CreditOut> findByUser() {
        return creditRepository.findAllByDestinedSaldo_BankAccount_User_Identifier(
            SecurityContextHolder.getContext().getAuthentication().getName()
        ).stream()
            .map(creditMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<CreditOut> findByCreditType(CreditStatus.CreditType creditType) {
        return creditRepository.findAllByCreditStatus_CreditType(creditType)
            .stream()
            .map(creditMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public CreditOut findById(Long id) {
        return creditMapper.entityToDTO(creditRepository.findById(id).orElseThrow(() -> new RuntimeException("Credit not found")));
    }

    @Override
    public List<CreditOut> findActiveByBankAccountId(Long bankAccountId) {
        return creditRepository.findAllByCreditStatusAndDestinedSaldo_BankAccount_Id(creditStatusRepository.findByCreditType(CreditStatus.CreditType.ACTIVE), bankAccountId)
            .stream()
            .map(creditMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<CreditStatusOut> findAllCreditStatuses() {
        return creditStatusRepository.findAll()
            .stream()
            .map(creditStatusMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Long countAllByCreditType(CreditStatus.CreditType creditType) {
        return creditRepository.countAllByCreditStatus_CreditType(creditType);
    }

    private void transferBalance(Credit credit) {
        Saldo destinedSaldo = credit.getDestinedSaldo();
        destinedSaldo.setBalance(credit.getDestinedSaldo().getBalance().add(credit.getTotalBalance()));
        saldoRepository.save(destinedSaldo);
    }

}
