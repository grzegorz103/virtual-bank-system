package com.ii.app.services;

import com.ii.app.dto.in.ExchangeCurrencyIn;
import com.ii.app.dto.out.ExchangeCurrencyOut;
import com.ii.app.exceptions.ApiException;
import com.ii.app.mappers.ExchangeCurrencyMapper;
import com.ii.app.models.BankAccount;
import com.ii.app.models.CurrencyType;
import com.ii.app.models.ExchangeCurrency;
import com.ii.app.models.Saldo;
import com.ii.app.models.enums.BankAccountType;
import com.ii.app.repositories.BankAccountRepository;
import com.ii.app.repositories.CurrencyTypeRepository;
import com.ii.app.repositories.ExchangeCurrencyRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.services.interfaces.ExchangeCurrencyService;
import com.ii.app.utils.Constants;
import com.ii.app.utils.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Service
public class ExchangeCurrencyServiceImpl implements ExchangeCurrencyService {
    private final ExchangeCurrencyRepository exchangeCurrencyRepository;

    private final ExchangeCurrencyMapper exchangeCurrencyMapper;

    private final BankAccountRepository bankAccountRepository;

    private final CurrencyConverter currencyConverter;

    private final CurrencyTypeRepository currencyTypeRepository;

    private final Constants constants;

    @Autowired
    public ExchangeCurrencyServiceImpl(ExchangeCurrencyRepository exchangeCurrencyRepository,
                                       ExchangeCurrencyMapper exchangeCurrencyMapper,
                                       BankAccountRepository bankAccountRepository,
                                       CurrencyConverter currencyConverter,
                                       Constants constants,
                                       SaldoRepository saldoRepository,
                                       CurrencyTypeRepository currencyTypeRepository) {
        this.exchangeCurrencyRepository = exchangeCurrencyRepository;
        this.exchangeCurrencyMapper = exchangeCurrencyMapper;
        this.bankAccountRepository = bankAccountRepository;
        this.currencyConverter = currencyConverter;
        this.constants = constants;
        this.currencyTypeRepository = currencyTypeRepository;
    }

    @Override
    public ExchangeCurrencyOut create(@NotNull ExchangeCurrencyIn exchangeCurrencyIn) {
        //TODO dodac wyjatek
        BankAccount sourceBankAcc = bankAccountRepository.findByNumberAndRemovedFalse(exchangeCurrencyIn.getSourceBankAccNumber())
            .orElseThrow(() -> new RuntimeException("nie znaleziono"));

        if (sourceBankAcc.getBankAccType().getBankAccountType() != BankAccountType.MULTI_CURRENCY)
            throw new RuntimeException("nie jest wielowalutowe");

        Saldo sourceSaldo = sourceBankAcc.getSaldos()
            .stream()
            .filter(e -> Objects.equals(e.getCurrencyType().getName(), exchangeCurrencyIn.getSourceCurrency()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("not found"));

        if (sourceSaldo.getBalance().doubleValue() < exchangeCurrencyIn.getBalance())
            throw new ApiException("Exception.notEnoughBalanceSaldo", null);


        Saldo destSaldo = sourceBankAcc.getSaldos()
            .stream()
            .filter(e -> Objects.equals(e.getCurrencyType().getName(), exchangeCurrencyIn.getDestCurrency()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("not found"));


        ExchangeCurrency mapped = exchangeCurrencyMapper.DTOtoEntity(exchangeCurrencyIn);

        CurrencyType sourceCurrencyType = sourceSaldo.getCurrencyType();
        CurrencyType destCurrencyType = destSaldo.getCurrencyType();

        BigDecimal convertedBalance = currencyConverter.convertCurrency(
            mapped.getBalance(),
            sourceCurrencyType,
            destCurrencyType
        );

        float transactionCommission = sourceBankAcc.getBankAccType().getExchangeCurrencyCommission();
        BigDecimal balanceAfterCommission = BigDecimal.valueOf(
            convertedBalance.doubleValue() - ((transactionCommission / 100d) * convertedBalance.doubleValue()
            ));

        if (balanceAfterCommission.doubleValue() < 0)
            balanceAfterCommission = BigDecimal.ZERO;

        sourceSaldo.setBalance(sourceSaldo.getBalance().subtract(BigDecimal.valueOf(exchangeCurrencyIn.getBalance())));
        destSaldo.setBalance(destSaldo.getBalance().add(balanceAfterCommission));

        mapped.setBalanceAfterExchange(balanceAfterCommission);
        mapped.setBankAccount(sourceBankAcc);
        mapped.setDate(Instant.now());

        exchangeCurrencyRepository.save(mapped);
        return exchangeCurrencyMapper.entityToDTO(mapped);
    }

    @Override
    public BigDecimal calculate(ExchangeCurrencyIn exchangeCurrencyIn) {
        CurrencyType sourceCurrencyType = currencyTypeRepository.findByName(exchangeCurrencyIn.getSourceCurrency()).orElseThrow(() -> new RuntimeException("Nie znaleziono"));
        CurrencyType destCurrencyType = currencyTypeRepository.findByName(exchangeCurrencyIn.getDestCurrency()).orElseThrow(() -> new RuntimeException("Nie znaleziono"));

        return currencyConverter.convertCurrency(
            exchangeCurrencyIn.getBalance(),
            sourceCurrencyType,
            destCurrencyType
        );
    }
}
