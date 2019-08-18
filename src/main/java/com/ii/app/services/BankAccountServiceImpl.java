package com.ii.app.services;

import com.ii.app.dto.in.BankAccountIn;
import com.ii.app.dto.out.BankAccountOut;
import com.ii.app.mappers.BankAccountMapper;
import com.ii.app.models.BankAccount;
import com.ii.app.models.Saldo;
import com.ii.app.models.enums.Currency;
import com.ii.app.repositories.BankAccountRepository;
import com.ii.app.repositories.CurrencyTypeRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.services.interfaces.BankAccountService;
import com.ii.app.utils.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService
{
        private final BankAccountMapper bankAccountMapper;

        private final BankAccountRepository bankAccountRepository;

        private final SaldoRepository saldoRepository;

        private final CurrencyTypeRepository currencyTypeRepository;

        private final Constants constants;

        @Autowired
        public BankAccountServiceImpl ( BankAccountMapper bankAccountMapper,
                                        BankAccountRepository bankAccountRepository,
                                        Constants constants,
                                        SaldoRepository saldoRepository,
                                        CurrencyTypeRepository currencyTypeRepository )
        {
                this.bankAccountMapper = bankAccountMapper;
                this.bankAccountRepository = bankAccountRepository;
                this.constants = constants;
                this.saldoRepository = saldoRepository;
                this.currencyTypeRepository = currencyTypeRepository;
        }

        @Override
        public BankAccountOut create ( @NotNull BankAccountIn bankAccountIn )
        {
                BankAccount bankAccount = bankAccountMapper.DTOtoEntity( bankAccountIn );
                bankAccount.setNumber( RandomStringUtils.randomNumeric( constants.BANK_ACCOUNT_NUMBER_LENGTH ) );
                BankAccount finalBankAccount = bankAccountRepository.save( bankAccount );

                if ( bankAccount.isMultiCurrency() )
                {
                        currencyTypeRepository.findAll()
                                .forEach( e ->
                                        saldoRepository.save( new Saldo( BigDecimal.ZERO, e, finalBankAccount ) )
                                );
                } else
                {
                        currencyTypeRepository.findByCurrency( Currency.PLN )
                                .ifPresent( e -> saldoRepository.save( new Saldo( BigDecimal.ZERO, e, finalBankAccount ) ) );
                }

                return bankAccountMapper.entityToDTO( bankAccount );
        }

        @Override
        public List<BankAccountOut> findAll ()
        {
                return bankAccountRepository.findAll()
                        .stream()
                        .map( bankAccountMapper::entityToDTO )
                        .collect( Collectors.toList() );
        }

        @Override
        public List<BankAccountOut> findByUser ()
        {
                return null;
        }
}
