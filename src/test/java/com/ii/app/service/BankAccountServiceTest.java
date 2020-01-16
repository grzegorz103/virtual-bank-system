package com.ii.app.service;

import com.ii.app.AppApplication;
import com.ii.app.exceptions.ApiException;
import com.ii.app.models.BankAccType;
import com.ii.app.models.BankAccount;
import com.ii.app.models.Credit;
import com.ii.app.models.Saldo;
import com.ii.app.models.enums.BankAccountType;
import com.ii.app.repositories.BankAccountRepository;
import com.ii.app.repositories.CreditRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.services.BankAccountServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = AppApplication.class)
public class BankAccountServiceTest {

    @Autowired
    private BankAccountServiceImpl bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CreditRepository creditRepository;

    @BeforeClass
    public static void setup() {
    }

     @Test
    public void findAllBankAccountTest() {
        assertThat(bankAccountService.findAll().size())
            .isEqualTo(bankAccountRepository.findAll().stream().filter(e->!e.isRemoved()).collect(Collectors.toList()).size());
    }

    @Test(expected = ApiException.class)
    public void deleteBankAccountByIdTest() {
        final int accountSize = bankAccountRepository.findAllByRemovedFalse().size();
        bankAccountService.deleteById(1L);

        assertThat(bankAccountRepository.findById(1L).get().isRemoved()).isTrue();
        assertThat(bankAccountRepository.findAllByRemovedFalse().size()).isEqualTo(accountSize - 1);
    }

    public void deleteBankAccountByIdNotFoundTest() {
        bankAccountService.deleteById(999L);
    }

}
