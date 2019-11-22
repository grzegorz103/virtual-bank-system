package com.ii.app.services;

import com.ii.app.dto.in.PaymentIn;
import com.ii.app.dto.out.PaymentOut;
import com.ii.app.mappers.PaymentMapper;
import com.ii.app.models.BankAccount;
import com.ii.app.models.CurrencyType;
import com.ii.app.models.Payment;
import com.ii.app.models.Saldo;
import com.ii.app.repositories.BankAccountRepository;
import com.ii.app.repositories.CurrencyTypeRepository;
import com.ii.app.repositories.PaymentRepository;
import com.ii.app.services.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    private final CurrencyTypeRepository currencyTypeRepository;

    private final BankAccountRepository bankAccountRepository;

    private final PaymentMapper paymentMapper;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              CurrencyTypeRepository currencyTypeRepository,
                              BankAccountRepository bankAccountRepository,
                              PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.currencyTypeRepository = currencyTypeRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.paymentMapper = paymentMapper;
    }


    @Override
    public PaymentOut create(@NotNull PaymentIn paymentIn) {
        BankAccount bankAccount = bankAccountRepository.findByNumberAndRemovedFalse(paymentIn.getDestinedBankAccountNumber())
            .orElseThrow(() -> new RuntimeException("Source bank account not found"));
        CurrencyType currencyType = currencyTypeRepository.findByName(
            paymentIn.getSourceCurrencyType()).orElseThrow(() -> new RuntimeException("Source currency not found"));
        Saldo destinedSaldo = bankAccount.getSaldos()
            .stream()
            .filter(e -> e.getCurrencyType() == currencyType)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Destined bank account has no saldo with provided currency type"));

        destinedSaldo.setBalance(destinedSaldo.getBalance().add(paymentIn.getBalance()));

        return paymentMapper.entityToDto(paymentRepository.save(Payment.builder()
            .date(Instant.now())
            .balance(paymentIn.getBalance())
            .sourceCurrencyType(currencyType)
            .destinedBankAccount(bankAccount)
            .build())
        );
    }

    @Override
    public List<PaymentOut> findAllByBankAccountId(Long bankAccountId) {
        if (!bankAccountRepository.existsById(bankAccountId)) {
            throw new RuntimeException("Bank account not found");
        }
        return paymentRepository.findAllByDestinedBankAccount_Id(bankAccountId)
            .stream()
            .map(paymentMapper::entityToDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<PaymentOut> findAll() {
        return paymentRepository.findAll()
            .stream()
            .map(paymentMapper::entityToDto)
            .collect(Collectors.toList());
    }
}
