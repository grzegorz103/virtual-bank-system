package com.ii.app.services;

import com.ii.app.dto.in.ExchangeCurrencyIn;
import com.ii.app.dto.out.ExchangeCurrencyOut;
import com.ii.app.mappers.ExchangeCurrencyMapper;
import com.ii.app.models.BankAccount;
import com.ii.app.models.ExchangeCurrency;
import com.ii.app.models.Saldo;
import com.ii.app.repositories.BankAccountRepository;
import com.ii.app.repositories.ExchangeCurrencyRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.services.interfaces.ExchangeCurrencyService;
import com.ii.app.utils.Constants;
import com.ii.app.utils.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Service
public class ExchangeCurrencyServiceImpl implements ExchangeCurrencyService
{
        private final ExchangeCurrencyRepository exchangeCurrencyRepository;

        private final ExchangeCurrencyMapper exchangeCurrencyMapper;

        private final BankAccountRepository bankAccountRepository;

        private final CurrencyConverter currencyConverter;

        private final SaldoRepository saldoRepository;

        private final Constants constants;

        @Autowired
        public ExchangeCurrencyServiceImpl ( ExchangeCurrencyRepository exchangeCurrencyRepository,
                                             ExchangeCurrencyMapper exchangeCurrencyMapper,
                                             BankAccountRepository bankAccountRepository,
                                             CurrencyConverter currencyConverter,
                                             Constants constants,
                                             SaldoRepository saldoRepository )
        {
                this.exchangeCurrencyRepository = exchangeCurrencyRepository;
                this.exchangeCurrencyMapper = exchangeCurrencyMapper;
                this.bankAccountRepository = bankAccountRepository;
                this.currencyConverter = currencyConverter;
                this.constants = constants;
                this.saldoRepository = saldoRepository;
        }

        @Override
        public ExchangeCurrencyOut create ( @NotNull ExchangeCurrencyIn exchangeCurrencyIn )
        {
                //TODO dodac wyjatek
                BankAccount sourceBankAcc = bankAccountRepository.findByNumber( exchangeCurrencyIn.getSourceBankAccNumber() )
                        .orElseThrow( () -> new RuntimeException( "nie znaleziono" ) );

                if ( !sourceBankAcc.isMultiCurrency() )
                        throw new RuntimeException( "nie jest wielowalutowe" );

                Saldo sourceSaldo = sourceBankAcc.getSaldos()
                        .stream()
                        .filter( e -> e.getCurrencyType().getCurrency() == exchangeCurrencyIn.getSourceCurrency() )
                        .findFirst()
                        .orElseThrow( () -> new RuntimeException( "not found" ) );

                Saldo destSaldo = sourceBankAcc.getSaldos()
                        .stream()
                        .filter( e -> e.getCurrencyType().getCurrency() == exchangeCurrencyIn.getDestCurrency() )
                        .findFirst()
                        .orElseThrow( () -> new RuntimeException( "not found" ) );


                ExchangeCurrency mapped = exchangeCurrencyMapper.DTOtoEntity( exchangeCurrencyIn );

                BigDecimal convertedBalance = currencyConverter.convertCurrency(
                        mapped.getBalance(),
                        mapped.getSourceCurrencyType(),
                        mapped.getDestCurrencyType()
                );

                BigDecimal balanceAfterCommission = BigDecimal.valueOf(
                        convertedBalance.doubleValue() - ((constants.CURRENCY_CONVERT_COMMISSION / 100d) * convertedBalance.doubleValue()
                        ) );

                if ( balanceAfterCommission.doubleValue() < 0 )
                        balanceAfterCommission = BigDecimal.ZERO;

                sourceSaldo.setBalance( sourceSaldo.getBalance().subtract( BigDecimal.valueOf( exchangeCurrencyIn.getBalance() ) ) );
                destSaldo.setBalance( destSaldo.getBalance().add( balanceAfterCommission ) );

                mapped.setBalanceAfterExchange( balanceAfterCommission );
                mapped.setBankAccount( sourceBankAcc );
                mapped.set


        }
}
