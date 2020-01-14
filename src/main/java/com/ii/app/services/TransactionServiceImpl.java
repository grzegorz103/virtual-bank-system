package com.ii.app.services;

import com.ii.app.dto.in.TransactionIn;
import com.ii.app.dto.out.TransactionOut;
import com.ii.app.exceptions.ApiException;
import com.ii.app.mappers.TransactionMapper;
import com.ii.app.models.BankAccount;
import com.ii.app.models.CurrencyType;
import com.ii.app.models.Saldo;
import com.ii.app.models.Transaction;
import com.ii.app.repositories.BankAccountRepository;
import com.ii.app.repositories.CurrencyTypeRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.repositories.TransactionRepository;
import com.ii.app.services.interfaces.TransactionService;
import com.ii.app.utils.Constants;
import com.ii.app.utils.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {
    private final CurrencyTypeRepository currencyTypeRepository;

    private final BankAccountRepository bankAccountRepository;

    private final SaldoRepository saldoRepository;

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final Constants constants;

    private final CurrencyConverter currencyConverter;

    @Autowired
    public TransactionServiceImpl(CurrencyTypeRepository currencyTypeRepository,
                                  BankAccountRepository bankAccountRepository,
                                  SaldoRepository saldoRepository,
                                  Constants constants,
                                  TransactionRepository transactionRepository,
                                  TransactionMapper transactionMapper, CurrencyConverter currencyConverter) {
        this.currencyTypeRepository = currencyTypeRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.saldoRepository = saldoRepository;
        this.constants = constants;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public TransactionOut create(TransactionIn transactionIn) {
        transactionIn.setSourceAccountNumber(transactionIn.getSourceAccountNumber()
                .replace(" ", ""));
        transactionIn.setDestinedAccountNumber(transactionIn.getDestinedAccountNumber()
                .replace(" ", ""));

        CurrencyType sourceCurrency = currencyTypeRepository
                .findByName(transactionIn.getSourceCurrency())
                .orElseThrow(() -> new ApiException("Exception.sourceCurrencyNotFound", null));
        CurrencyType destCurrency = currencyTypeRepository
                .findByName(transactionIn.getDestinedCurrency())
                .orElseThrow(() -> new ApiException("Exception.destCurrencyNotFound", null));
        BankAccount destinedBankAccount = bankAccountRepository
                .findByNumberAndRemovedFalse(transactionIn.getDestinedAccountNumber())
                .orElseThrow(() ->
                        new ApiException("Exception.notFoundBankAcc",
                                new String[]{transactionIn.getDestinedAccountNumber()})
                );
        BankAccount sourceBankAccount = bankAccountRepository
                .findByNumberAndRemovedFalse(transactionIn.getSourceAccountNumber())
                .orElseThrow(() -> new ApiException("Exception.notFoundBankAcc",
                        new String[]{transactionIn.getSourceAccountNumber()}));

        Saldo sourceSaldo = sourceBankAccount.getSaldos()
                .stream()
                .filter(e -> e.getCurrencyType() == sourceCurrency)
                .findFirst()
                .get();

        Saldo destSaldo = getDestSaldo(destCurrency, destinedBankAccount);

        if (sourceSaldo.getBalance().floatValue() < transactionIn.getBalance())
            throw new ApiException("Exception.notEnoughBalanceSaldo", null);

        BigDecimal balance = currencyConverter.convertCurrency(
                transactionIn.getBalance(),
                sourceCurrency,
                destSaldo.getCurrencyType()
        );

        BigDecimal balanceWithCommission = getBalanceWithCommission(sourceBankAccount, balance);

        sourceSaldo.setBalance(sourceSaldo.getBalance()
                .subtract(BigDecimal.valueOf(transactionIn.getBalance())));
        destSaldo.setBalance(destSaldo.getBalance().add(balanceWithCommission));

        return transactionMapper.entityToDTO(transactionRepository.save(
                Transaction.builder()
                        .balance(BigDecimal.valueOf(transactionIn.getBalance()))
                        .balanceWithCommission(balanceWithCommission)
                        .date(Instant.now())
                        .destinedBankAccount(destinedBankAccount)
                        .sourceBankAccount(sourceBankAccount)
                        .title(transactionIn.getTitle())
                        .sourceCurrencyType(sourceCurrency)
                        .destinedCurrencyType(destSaldo.getCurrencyType())
                        .build()
        ));
    }

    private Saldo getDestSaldo(CurrencyType destCurrency, BankAccount destinedBankAccount) {
        return destinedBankAccount.getSaldos()
                .stream()
                .filter(e -> e.getCurrencyType() == destCurrency)
                .findFirst()
                .orElse(destinedBankAccount.getSaldos().stream()
                        .filter(e -> Objects.equals(e.getCurrencyType().getName(), "PLN"))
                        .findFirst()
                        .get()
                );
    }

    private BigDecimal getBalanceWithCommission(BankAccount sourceBankAccount, BigDecimal balance) {
        return BigDecimal.valueOf(
                balance.doubleValue() - ((sourceBankAccount.getBankAccType().getTransactionComission() / 100d) * balance.doubleValue()
                )
        ).setScale(2, RoundingMode.DOWN);
    }

    @Override
    public List<TransactionOut> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionOut> findAllByBankAccountId(Long bankAccountId) {
        return transactionRepository.findTransactionsByBankAccountId(bankAccountId)
                .stream()
                .map(transactionMapper::entityToDTO)
                .collect(Collectors.toList());
    }

}
