package com.ii.app.config;

import com.ii.app.models.BankAccount;
import com.ii.app.models.CurrencyType;
import com.ii.app.models.Saldo;
import com.ii.app.models.enums.Currency;
import com.ii.app.repositories.BankAccountRepository;
import com.ii.app.repositories.CurrencyTypeRepository;
import com.ii.app.repositories.SaldoRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class RepositoryInitializer
{
        @Autowired
        BankAccountRepository bankAccountRepository;

        @Autowired
        CurrencyTypeRepository currencyTypeRepository;

        @Autowired
        SaldoRepository saldoRepository;

        @Bean
        public InitializingBean intializeRepo ()
        {
                return () -> {
                        if ( currencyTypeRepository.findAll().isEmpty() )
                        {
                                CurrencyType pln = CurrencyType.builder()
                                        .currency( Currency.PLN )
                                        .exchangeRate( 1f )
                                        .build();

                                CurrencyType usd = CurrencyType.builder()
                                        .currency( Currency.USD )
                                        .exchangeRate( 3.88f )
                                        .build();

                                CurrencyType eur = CurrencyType.builder()
                                        .currency( Currency.EUR )
                                        .exchangeRate( 4.23f )
                                        .build();

                                CurrencyType chf = CurrencyType.builder()
                                        .currency( Currency.CHF )
                                        .exchangeRate( 2.31f )
                                        .build();

                                CurrencyType gbp = CurrencyType.builder()
                                        .currency( Currency.GBP )
                                        .exchangeRate( 5.60f )
                                        .build();

                                currencyTypeRepository.save( pln );
                                currencyTypeRepository.save( usd );
                                currencyTypeRepository.save( eur );
                                currencyTypeRepository.save( chf );
                                currencyTypeRepository.save( gbp );
                        }

                        if ( bankAccountRepository.findAll().isEmpty() )
                        {

                                BankAccount bankAccount = BankAccount.builder()
                                        .multiCurrency( true )
                                        .number( "321123" )
                                        .saldos( new HashSet<>() )
                                        .transactions( new HashSet<>() )
                                        .build();

                                bankAccountRepository.save( bankAccount );

                                Set<Saldo> saldos = currencyTypeRepository.findAll()
                                        .stream()
                                        .map( e -> saldoRepository.save( Saldo.builder()
                                                .balance( new BigDecimal( 100f ) )
                                                .currencyType( e )
                                                .bankAccount( bankAccount )
                                                .build() ) )
                                        .collect( Collectors.toSet() );


                                BankAccount bankAccount2 = BankAccount.builder()
                                        .multiCurrency( true )
                                        .number( "432123" )
                                        .saldos( new HashSet<>() )
                                        .transactions( new HashSet<>() )
                                        .build();

                                bankAccountRepository.save( bankAccount2 );

                                Set<Saldo> saldos2 = currencyTypeRepository.findAll()
                                        .stream()
                                        .map( e -> saldoRepository.save( Saldo.builder()
                                                .balance( new BigDecimal( 100f ) )
                                                .currencyType( e )
                                                .bankAccount( bankAccount2 )
                                                .build() ) )
                                        .collect( Collectors.toSet() );

                        }
                };
        }
}
