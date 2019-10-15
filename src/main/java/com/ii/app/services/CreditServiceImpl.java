package com.ii.app.services;

import com.ii.app.dto.in.CreditIn;
import com.ii.app.dto.out.CreditOut;
import com.ii.app.mappers.CreditMapper;
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

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository,
                             CreditMapper creditMapper,
                             SaldoRepository saldoRepository,
                             CreditStatusRepository creditStatusRepository) {
        this.creditRepository = creditRepository;
        this.creditMapper = creditMapper;
        this.saldoRepository = saldoRepository;
        this.creditStatusRepository = creditStatusRepository;
    }

    @Override
    public CreditOut create(CreditIn creditIn) {
        Credit mapped = creditMapper.DTOtoEntity(creditIn);
        mapped.setBalancePaid(BigDecimal.ZERO);

        Saldo destinedSaldo = saldoRepository.findById(creditIn.getDestinedSaldoId())
            .orElseThrow(() -> new RuntimeException("Not found"));

        destinedSaldo.setBalance(destinedSaldo.getBalance().add(creditIn.getTotalBalance()));
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
        }

        return creditMapper.entityToDTO(creditRepository.save(credit));
    }

    @Override
    public CreditOut changeStatus(Long creditId, CreditStatus.CreditType creditType) {
        Credit credit = creditRepository.findById(creditId).orElseThrow(() -> new RuntimeException("Credit not found"));
        credit.setCreditStatus(creditStatusRepository.findByCreditType(creditType));
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
        return creditMapper.entityToDTO(creditRepository.findById(id).orElseThrow(()->new RuntimeException("Credit not found")));
    }


}
