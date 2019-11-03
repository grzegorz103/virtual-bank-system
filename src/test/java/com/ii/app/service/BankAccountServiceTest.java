package com.ii.app.service;

import com.ii.app.AppApplication;
import com.ii.app.exceptions.ApiException;
import com.ii.app.repositories.BankAccountRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = AppApplication.class)
public class BankAccountServiceTest {

    @Autowired
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @BeforeClass
    public static void setup() {
    }

    @Test
    public void findAllBankAccountTest(){
        assertThat(bankAccountService.findAll().size()).isEqualTo(bankAccountRepository.findAll().size());
    }

    @Test
    public void deleteBankAccountByIdTest() {
        final int accountSize = bankAccountService.findAll().size();
        bankAccountService.deleteById(1L);
        assertThat(bankAccountService.findAll().size()).isEqualTo(accountSize - 1);
    }

    @Test(expected = ApiException.class)
    public void deleteBankAccountByIdNotFoundTest() {
        bankAccountService.deleteById(999L);
    }
}
