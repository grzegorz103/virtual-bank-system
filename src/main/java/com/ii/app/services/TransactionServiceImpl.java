package com.ii.app.services;

import com.ii.app.dto.TransactionDTO;
import com.ii.app.models.BankAccount;
import com.ii.app.models.CurrencyType;
import com.ii.app.models.Saldo;
import com.ii.app.models.Transaction;
import com.ii.app.models.enums.Currency;
import com.ii.app.repositories.BankAccountRepository;
import com.ii.app.repositories.CurrencyTypeRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.repositories.TransactionRepository;
import com.ii.app.services.interfaces.TransactionService;
import com.ii.app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;

@Service ("transactionService")
public class TransactionServiceImpl implements TransactionService
{
        private final CurrencyTypeRepository currencyTypeRepository;

        private final BankAccountRepository bankAccountRepository;

        private final SaldoRepository saldoRepository;

        private final TransactionRepository transactionRepository;

        private final Constants constants;

        @Autowired
        public TransactionServiceImpl ( CurrencyTypeRepository currencyTypeRepository,
                                        BankAccountRepository bankAccountRepository,
                                        SaldoRepository saldoRepository,
                                        Constants constants, TransactionRepository transactionRepository )
        {
                this.currencyTypeRepository = currencyTypeRepository;
                this.bankAccountRepository = bankAccountRepository;
                this.saldoRepository = saldoRepository;
                this.constants = constants;
                this.transactionRepository = transactionRepository;
        }

        @Override
        public Transaction create ( @NotNull TransactionDTO transactionDTO )
        {
                final Transaction transaction = new Transaction();

                final CurrencyType sourceCurrency = currencyTypeRepository.findByCurrency( transactionDTO.getCurrency() ).orElseThrow( () -> new RuntimeException( "Currency type not found" ) );
                final BankAccount destinedBankAccount = bankAccountRepository.findByNumber( transactionDTO.getDestinedAccountNumber() ).orElseThrow( () -> new RuntimeException( "Bank account does not exists" ) );
                final BankAccount sourceBankAccount = bankAccountRepository.findByNumber( transactionDTO.getSourceAccountNumber() ).get();

                final Saldo sourceSaldo = sourceBankAccount.getSaldos()
                        .stream()
                        .filter( Objects::nonNull )
                        .filter( e -> e.getCurrencyType() == sourceCurrency )
                        .findFirst()
                        .get();

                if ( sourceSaldo.getBalance().floatValue() < transactionDTO.getBalance() )
                        throw new RuntimeException( "Source saldo has no required balance" );


                final BigDecimal balance = convertCurrency(
                        transactionDTO.getBalance(),
                        sourceCurrency,
                        destinedBankAccount.isMultiCurrency()
                                ? sourceCurrency
                                : currencyTypeRepository.findByCurrency( Currency.PLN ).get()
                );

                final BigDecimal balanceWithCommission = BigDecimal.valueOf(
                        balance.doubleValue() - (((sourceBankAccount.isMultiCurrency()
                                ? constants.MULTI_CURRENCY_TRANSFER_COMMISSION
                                : constants.SINGLE_CURRENCY_TRANSFER_COMMISSION) / 100d) * balance.doubleValue()
                        )
                );

                sourceSaldo.setBalance( sourceSaldo.getBalance().subtract( BigDecimal.valueOf( transactionDTO.getBalance() ) ) );

                destinedBankAccount.getSaldos()
                        .stream()
                        .filter( Objects::nonNull )
                        .filter( e -> {
                                if ( destinedBankAccount.isMultiCurrency() )
                                        return e.getCurrencyType() == sourceCurrency;
                                else
                                        return e.getCurrencyType().getCurrency() == Currency.PLN;
                        } )
                        .findFirst()
                        .ifPresent( e -> {
                                e.setBalance( e.getBalance().add( balanceWithCommission ) );
                                saldoRepository.save( e );
                        } );

                transaction.setBalance( BigDecimal.valueOf( transactionDTO.getBalance() ) );
                transaction.setBalanceWithCommission( balanceWithCommission );
                transaction.setDate( Instant.now() );
                transaction.setDestinedBankAccount( destinedBankAccount );
                transaction.setSourceBankAccount( sourceBankAccount );
                transaction.setTitle( transactionDTO.getTitle() );

                return transactionRepository.save( transaction );
        }

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
                return null;
        }

}
