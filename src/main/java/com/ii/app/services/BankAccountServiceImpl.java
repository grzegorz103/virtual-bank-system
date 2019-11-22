package com.ii.app.services;

import com.ii.app.dto.edit.BankAccountEdit;
import com.ii.app.dto.edit.SaldoEdit;
import com.ii.app.dto.in.BankAccountIn;
import com.ii.app.dto.out.BankAccountOut;
import com.ii.app.dto.out.SaldoOut;
import com.ii.app.exceptions.ApiException;
import com.ii.app.mappers.BankAccountMapper;
import com.ii.app.mappers.SaldoMapper;
import com.ii.app.models.BankAccount;
import com.ii.app.models.Saldo;
import com.ii.app.models.enums.BankAccountType;
import com.ii.app.models.enums.CreditStatus;
import com.ii.app.models.enums.InvestmentType;
import com.ii.app.repositories.*;
import com.ii.app.services.interfaces.BankAccountService;
import com.ii.app.utils.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountMapper bankAccountMapper;

    private final BankAccountRepository bankAccountRepository;

    private final BankAccountTypeRepository bankAccountTypeRepository;

    private final SaldoRepository saldoRepository;

    private final CurrencyTypeRepository currencyTypeRepository;

    private final Constants constants;

    private final UserRepository userRepository;

    private final SaldoMapper saldoMapper;

    @Autowired
    public BankAccountServiceImpl(BankAccountMapper bankAccountMapper,
                                  BankAccountRepository bankAccountRepository,
                                  Constants constants,
                                  SaldoRepository saldoRepository,
                                  CurrencyTypeRepository currencyTypeRepository,
                                  BankAccountTypeRepository bankAccountTypeRepository,
                                  UserRepository userRepository,
                                  SaldoMapper saldoMapper) {
        this.bankAccountMapper = bankAccountMapper;
        this.bankAccountRepository = bankAccountRepository;
        this.constants = constants;
        this.saldoRepository = saldoRepository;
        this.currencyTypeRepository = currencyTypeRepository;
        this.bankAccountTypeRepository = bankAccountTypeRepository;
        this.userRepository = userRepository;
        this.saldoMapper = saldoMapper;
    }

    @Override
    public BankAccountOut create(@NotNull BankAccountIn bankAccountIn,
                                 String userIdentifier) {
        BankAccount bankAccount = new BankAccount();
        String accountNumber;
        do {
            accountNumber = RandomStringUtils.randomNumeric(constants.BANK_ACCOUNT_NUMBER_LENGTH);
        } while (accountNumber.charAt(0) == '0' || bankAccountRepository.existsByNumber(accountNumber));
        bankAccount.setNumber(accountNumber);
        bankAccount.setBankAccType(bankAccountTypeRepository.findByBankAccountType(bankAccountIn.getBankAccountType()));
        bankAccount.setUser(userRepository.findByIdentifier(userIdentifier).orElseThrow(() -> new RuntimeException("User not found")));
        BankAccount finalBankAccount = bankAccountRepository.save(bankAccount);

        if (bankAccount.getBankAccType().getBankAccountType() == BankAccountType.MULTI_CURRENCY) {
            currencyTypeRepository.findAll()
                .forEach(e ->
                    saldoRepository.save(new Saldo(BigDecimal.ZERO, e, finalBankAccount))
                );
        } else {
            currencyTypeRepository.findByName("PLN")
                .ifPresent(e -> saldoRepository.save(new Saldo(BigDecimal.ZERO, e, finalBankAccount)));
        }
        BankAccount account = bankAccountRepository.findById(finalBankAccount.getId()).get();
        account.setSaldos(saldoRepository.findAll().stream().filter(e -> e.getBankAccount() == account).collect(Collectors.toSet()));
        return bankAccountMapper.entityToDTO(account);
    }

    @Override
    public List<BankAccountOut> findAll() {
        return bankAccountRepository.findAllByRemovedFalse()
            .stream()
            .map(bankAccountMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<BankAccountOut> findByUser() {
        return bankAccountRepository.findByUserIdentifierAndRemovedFalse(SecurityContextHolder.getContext().getAuthentication().getName())
            .stream()
            .map(bankAccountMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public BankAccountOut findById(Long id) {
        return bankAccountRepository.findById(id)
            .map(bankAccountMapper::entityToDTO)
            .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public Long findBankAccountCountByType(Long id) {
        return bankAccountRepository.countByBankAccTypeAndRemovedFalse(bankAccountTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found")));
    }

    @Override
    public void deleteById(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() -> new ApiException("Exception.notFoundBankAcc", new Long[]{id}));
        if (bankAccount.getSaldos().stream()
            .anyMatch(e -> e.getCredits().stream()
                .anyMatch(f -> f.getCreditStatus().getCreditType() == CreditStatus.CreditType.ACTIVE))) {
            throw new ApiException("Exception.hasActiveCredits", null);
        }

        if (bankAccount.getSaldos().stream()
            .anyMatch(e -> e.getInvestments().stream()
                .anyMatch(f -> f.getInvestmentType().getInvestmentStatus() == InvestmentType.InvestmentStatus.ACTIVE)))
            throw new ApiException("Exception.hasActiveInvestments", null);
        bankAccountRepository.markRemovedAsTrue(id);
    }

    @Override
    public BankAccountOut update(Long id, BankAccountEdit bankAccountEdit) {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() -> new ApiException("Exception.notFound", null));
        if (!Objects.equals(bankAccountEdit.getNumber(), bankAccount.getNumber()) && bankAccountRepository.existsByNumber(bankAccountEdit.getNumber())) {
            throw new ApiException("Exception.bankAccountExists", null);
        }
        bankAccount.setNumber(bankAccountEdit.getNumber());
        return bankAccountMapper.entityToDTO(bankAccountRepository.save(bankAccount));
    }

    @Override
    public SaldoOut updateSaldo(Long id, SaldoEdit saldoEdit) {
        Saldo saldo = saldoRepository.findById(id).orElseThrow(() -> new ApiException("Exception.notFound", null));
        saldo.setBalance(saldoEdit.getBalance());
        return saldoMapper.saldoToSaldoOut(saldoRepository.save(saldo));
    }

}
