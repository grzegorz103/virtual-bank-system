package com.ii.app.config;

import com.ii.app.models.BankAccType;
import com.ii.app.models.BankAccount;
import com.ii.app.models.CurrencyType;
import com.ii.app.models.Saldo;
import com.ii.app.models.enums.BankAccountType;
import com.ii.app.models.enums.Currency;
import com.ii.app.models.user.UserRole;
import com.ii.app.repositories.*;
import com.ii.app.utils.Constants;
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
        private final BankAccountRepository bankAccountRepository;

        private final CurrencyTypeRepository currencyTypeRepository;

        private final SaldoRepository saldoRepository;

        private final BankAccountTypeRepository bankAccountTypeRepository;

        private final Constants constants;

        private final UserRoleRepository userRoleRepository;

        @Autowired
        public RepositoryInitializer ( BankAccountRepository bankAccountRepository,
                                       CurrencyTypeRepository currencyTypeRepository,
                                       SaldoRepository saldoRepository,
                                       BankAccountTypeRepository bankAccountTypeRepository,
                                       Constants constants,
                                       UserRoleRepository userRoleRepository )
        {
                this.bankAccountRepository = bankAccountRepository;
                this.currencyTypeRepository = currencyTypeRepository;
                this.saldoRepository = saldoRepository;
                this.bankAccountTypeRepository = bankAccountTypeRepository;
                this.constants = constants;
                this.userRoleRepository = userRoleRepository;
        }

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

                        if ( bankAccountTypeRepository.findAll().isEmpty() )
                        {
                                BankAccType bankAccType = BankAccType.builder()
                                        .bankAccountType( BankAccountType.MULTI_CURRENCY )
                                        .exchangeCurrencyCommission( ( float ) constants.CURRENCY_CONVERT_COMMISSION )
                                        .transactionComission( ( float ) constants.MULTI_CURRENCY_TRANSFER_COMMISSION )
                                        .build();

                                bankAccountTypeRepository.save( bankAccType );

                                BankAccType bankAccType2 = BankAccType.builder()
                                        .bankAccountType( BankAccountType.SINGLE_CURRENCY )
                                        .exchangeCurrencyCommission( ( float ) constants.CURRENCY_CONVERT_COMMISSION )
                                        .transactionComission( ( float ) constants.SINGLE_CURRENCY_TRANSFER_COMMISSION )
                                        .build();

                                bankAccountTypeRepository.save( bankAccType2 );

                        }

                        if ( bankAccountRepository.findAll().isEmpty() )
                        {
                                BankAccType single = bankAccountTypeRepository.findByBankAccountType( BankAccountType.SINGLE_CURRENCY );
                                BankAccType multi = bankAccountTypeRepository.findByBankAccountType( BankAccountType.MULTI_CURRENCY );

                                BankAccount bankAccount = BankAccount.builder()
                                        .bankAccType( multi )
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
                                        .bankAccType( multi )
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

                        if(userRoleRepository.findAll().isEmpty()){
                                userRoleRepository.save( UserRole.builder().userType( UserRole.UserType.ROLE_EMPLOYEE ).build() );
                                userRoleRepository.save( UserRole.builder().userType( UserRole.UserType.ROLE_USER ).build() );
                        }
                };
        }
}
