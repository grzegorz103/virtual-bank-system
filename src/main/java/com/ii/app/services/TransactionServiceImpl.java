package com.ii.app.services;

import com.ii.app.dto.TransactionDTO;
import com.ii.app.dto.out.TransactionOut;
import com.ii.app.mappers.TransactionMapper;
import com.ii.app.models.BankAccount;
import com.ii.app.models.CurrencyType;
import com.ii.app.models.Saldo;
import com.ii.app.models.Transaction;
import com.ii.app.models.enums.BankAccountType;
import com.ii.app.models.enums.Currency;
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

@Service ("transactionService")
public class TransactionServiceImpl implements TransactionService
{
        private final CurrencyTypeRepository currencyTypeRepository;

        private final BankAccountRepository bankAccountRepository;

        private final SaldoRepository saldoRepository;

        private final TransactionRepository transactionRepository;

        private final TransactionMapper transactionMapper;

        private final Constants constants;

        private final CurrencyConverter currencyConverter;

        @Autowired
        public TransactionServiceImpl ( CurrencyTypeRepository currencyTypeRepository,
                                        BankAccountRepository bankAccountRepository,
                                        SaldoRepository saldoRepository,
                                        Constants constants,
                                        TransactionRepository transactionRepository,
                                        TransactionMapper transactionMapper, CurrencyConverter currencyConverter )
        {
                this.currencyTypeRepository = currencyTypeRepository;
                this.bankAccountRepository = bankAccountRepository;
                this.saldoRepository = saldoRepository;
                this.constants = constants;
                this.transactionRepository = transactionRepository;
                this.transactionMapper = transactionMapper;
                this.currencyConverter = currencyConverter;
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

                final Saldo destSaldo = destinedBankAccount.getSaldos()
                        .stream()
                        .filter( Objects::nonNull )
                        .filter( e -> e.getCurrencyType() == sourceCurrency )
                        .findFirst()
                        .orElse( destinedBankAccount.getSaldos().stream().filter( e -> e.getCurrencyType().getCurrency() == Currency.PLN ).findFirst().get() );

                if ( sourceSaldo.getBalance().floatValue() < transactionDTO.getBalance() )
                        throw new RuntimeException( "Source saldo has no required balance" );

                boolean sourceMultiCurrency = sourceBankAccount.getBankAccType().getBankAccountType() == BankAccountType.MULTI_CURRENCY;

                final BigDecimal balance = currencyConverter.convertCurrency(
                        transactionDTO.getBalance(),
                        sourceCurrency,
                        destSaldo.getCurrencyType()
                );

                final BigDecimal balanceWithCommission = BigDecimal.valueOf(
                        balance.doubleValue() - (((sourceMultiCurrency
                                ? constants.MULTI_CURRENCY_TRANSFER_COMMISSION
                                : constants.SINGLE_CURRENCY_TRANSFER_COMMISSION) / 100d) * balance.doubleValue()
                        )
                ).setScale( BigDecimal.ROUND_DOWN, 2 );

                sourceSaldo.setBalance( sourceSaldo.getBalance().subtract( BigDecimal.valueOf( transactionDTO.getBalance() ) ) );

                destSaldo.setBalance( destSaldo.getBalance().add( balanceWithCommission ) );

         /*       destinedBankAccount.getSaldos()
                        .stream()
                        .filter( Objects::nonNull )
                        .filter( e -> {
                                if ( destMultiCurrency )
                                        return e.getCurrencyType() == sourceCurrency;
                                else
                                        return e.getCurrencyType().getCurrency() == Currency.PLN;
                        } )
                        .findFirst()
                        .ifPresent( e -> {
                                e.setBalance( e.getBalance().add( balanceWithCommission ) );
                                saldoRepository.save( e );
                        } );
*/
                transaction.setBalance( BigDecimal.valueOf( transactionDTO.getBalance() ) );
                transaction.setBalanceWithCommission( balanceWithCommission );
                transaction.setDate( Instant.now() );
                transaction.setDestinedBankAccount( destinedBankAccount );
                transaction.setSourceBankAccount( sourceBankAccount );
                transaction.setTitle( transactionDTO.getTitle() );

                return transactionRepository.save( transaction );
        }

        @Override
        public List<TransactionOut> findAll ()
        {
                return transactionRepository.findAll()
                        .stream()
                        .map( transactionMapper::entityToDTO )
                        .collect( Collectors.toList() );
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
