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
    public Transaction create(@NotNull TransactionIn transactionIn) {
        final Transaction transaction = new Transaction();

        transactionIn.setSourceAccountNumber(transactionIn.getSourceAccountNumber().replace(" ", ""));
        transactionIn.setDestinedAccountNumber(transactionIn.getDestinedAccountNumber().replace(" ", ""));

        final CurrencyType sourceCurrency = currencyTypeRepository.findByName(transactionIn.getSourceCurrency())
            .orElseThrow(() -> new ApiException("Exception.sourceCurrencyNotFound", null));
        final CurrencyType destCurrency = currencyTypeRepository.findByName(transactionIn.getDestinedCurrency())
            .orElseThrow(() -> new ApiException("Exception.destCurrencyNotFound", null));
        final BankAccount destinedBankAccount = bankAccountRepository.findByNumberAndRemovedFalse(transactionIn.getDestinedAccountNumber())
            .orElseThrow(() -> new ApiException("Exception.notFoundBankAcc", new String[]{transactionIn.getDestinedAccountNumber()}));
        final BankAccount sourceBankAccount = bankAccountRepository.findByNumberAndRemovedFalse(transactionIn.getSourceAccountNumber())
            .orElseThrow(() -> new ApiException("Exception.notFoundBankAcc", new String[]{transactionIn.getSourceAccountNumber()}));

        final Saldo sourceSaldo = sourceBankAccount.getSaldos()
            .stream()
            .filter(e -> e.getCurrencyType() == sourceCurrency)
            .findFirst()
            .get();

        final Saldo destSaldo = destinedBankAccount.getSaldos()
            .stream()
            .filter(e -> e.getCurrencyType() == destCurrency)
            .findFirst()
            .orElse(destinedBankAccount.getSaldos().stream()
                .filter(e -> Objects.equals(e.getCurrencyType().getName(), "PLN")).findFirst().get());

        if (sourceSaldo.getBalance().floatValue() < transactionIn.getBalance())
            throw new ApiException("Exception.notEnoughBalanceSaldo", null);

        final BigDecimal balance = currencyConverter.convertCurrency(
            transactionIn.getBalance(),
            sourceCurrency,
            destSaldo.getCurrencyType()
        );

        final BigDecimal balanceWithCommission = BigDecimal.valueOf(
            balance.doubleValue() - ((sourceBankAccount.getBankAccType().getTransactionComission() / 100d) * balance.doubleValue()
            )
        ).setScale(2, RoundingMode.DOWN);

        sourceSaldo.setBalance(sourceSaldo.getBalance().subtract(BigDecimal.valueOf(transactionIn.getBalance())));

        destSaldo.setBalance(destSaldo.getBalance().add(balanceWithCommission));

        transaction.setBalance(BigDecimal.valueOf(transactionIn.getBalance()));
        transaction.setBalanceWithCommission(balanceWithCommission);
        transaction.setDate(Instant.now());
        transaction.setDestinedBankAccount(destinedBankAccount);
        transaction.setSourceBankAccount(sourceBankAccount);
        transaction.setTitle(transactionIn.getTitle());
        transaction.setSourceCurrencyType(sourceCurrency);
        transaction.setDestinedCurrencyType(destSaldo.getCurrencyType());

        return transactionRepository.save(transaction);
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

        /*
        private BigDecimal convertCurrency ( float currency, CurrencyType sourceCurrency, CurrencyType destinedCurrency )
        {

                BigDecimal convertedCurrency;
                if ( currency > 0 )
                {
                        if ( sourceCurrency != null && destinedCurrency != null )
                        {
                                // gdy waluta zrodlowa == docelowa nie trzeba konwertowac na inna walute
                                if ( sourceCurrency == destinedCurrency )
                                {
                                        convertedCurrency = new BigDecimal( currency );
                                        convertedCurrency = convertedCurrency.setScale( 2, RoundingMode.DOWN );
                                        return convertedCurrency;
                                } else
                                {
                                        final float sourceExchangeRate = sourceCurrency.getExchangeRate();
                                        final float destinedExchangeRate = destinedCurrency.getExchangeRate();
                                        if ( destinedExchangeRate > 0 )
                                        {
                                                convertedCurrency = new BigDecimal( (currency * sourceExchangeRate) / destinedExchangeRate );
                                                convertedCurrency = convertedCurrency.setScale( 2, RoundingMode.DOWN );
                                                return convertedCurrency;
                                        }
                                }
                        }
                }
                return BigDecimal.ZERO;
        }
        */

}
