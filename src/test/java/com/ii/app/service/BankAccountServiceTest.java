package com.ii.app.service;

import com.ii.app.AppApplication;
import com.ii.app.dto.in.TransactionTemplateIn;
import com.ii.app.dto.out.TransactionTemplateOut;
import com.ii.app.mappers.TransactionTemplateMapper;
import com.ii.app.mappers.TransactionTemplateMapperImpl;
import com.ii.app.models.*;
import com.ii.app.models.enums.BankAccountType;
import com.ii.app.models.user.Address;
import com.ii.app.models.user.User;
import com.ii.app.models.user.UserRole;
import com.ii.app.repositories.*;
import com.ii.app.services.TransactionTemplateServiceImpl;
import com.ii.app.services.interfaces.TransactionTemplateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes=AppApplication.class)
public class BankAccountServiceTest {

    @Autowired
    private TransactionTemplateRepository transactionTemplateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionTemplateServiceImpl transactionTemplateService;


    @Autowired
    private TransactionTemplateMapperImpl transactionTemplateMapper;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired

    private CurrencyTypeRepository currencyTypeRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private BankAccountTypeRepository bankAccountTypeRepository;
    @Autowired
    private SaldoRepository saldoRepository;

    @Before
    public void setup() {
        initMocks(this);
        userRoleRepository.save(UserRole.builder().userType(UserRole.UserType.ROLE_EMPLOYEE).build());
        userRoleRepository.save(UserRole.builder().userType(UserRole.UserType.ROLE_USER).build());

        if (userRoleRepository.findAll().isEmpty()) {
            userRoleRepository.save(UserRole.builder().userType(UserRole.UserType.ROLE_EMPLOYEE).build());
            userRoleRepository.save(UserRole.builder().userType(UserRole.UserType.ROLE_USER).build());
        }

        if (userRepository.findAll().isEmpty()) {
            Address address = Address.builder()
                .city("Warszawa")
                .houseNumber("10")
                .name("Jan")
                .surname("Kowalski")
                .phoneNumber("662003004")
                .postCode("03-100")
                .street("Warszawska")
                .build();

            User user = User.builder()
                .credentials(false)
                .email("jan@kowalski.pl")
                .enabled(true)
                .expired(false)
                .locked(false)
                .password("asd")
                .userRoles(Collections.singleton(userRoleRepository.findByUserType(UserRole.UserType.ROLE_USER)))
                .transactionTemplates(new HashSet<>())
                .identifier("11111111")
                .bankAccounts(new HashSet<>())
                .address(address)
                .build();

            userRepository.save(user);
            if (currencyTypeRepository.findAll().isEmpty()) {
                CurrencyType pln = CurrencyType.builder()
                    .name("PLN")
                    .exchangeRate(1f)
                    .build();

                CurrencyType usd = CurrencyType.builder()
                    .name("USD")
                    .exchangeRate(3.88f)
                    .build();

                CurrencyType eur = CurrencyType.builder()
                    .name("EUR")
                    .exchangeRate(4.23f)
                    .build();

                CurrencyType chf = CurrencyType.builder()
                    .name("CHF")
                    .exchangeRate(2.31f)
                    .build();

                CurrencyType gbp = CurrencyType.builder()
                    .name("GBP")
                    .exchangeRate(5.60f)
                    .build();

                currencyTypeRepository.save(pln);
                currencyTypeRepository.save(usd);
                currencyTypeRepository.save(eur);
                currencyTypeRepository.save(chf);
                currencyTypeRepository.save(gbp);
            }

            if (bankAccountTypeRepository.findAll().isEmpty()) {
                BankAccType bankAccType = BankAccType.builder()
                    .bankAccountType(BankAccountType.MULTI_CURRENCY)
                    .exchangeCurrencyCommission((float) 2f)
                    .transactionComission((float) 2f)
                    .build();

                bankAccountTypeRepository.save(bankAccType);

                BankAccType bankAccType2 = BankAccType.builder()
                    .bankAccountType(BankAccountType.STANDARD)
                    .exchangeCurrencyCommission((float) 2f)
                    .transactionComission((float) 2f)
                    .build();

                bankAccountTypeRepository.save(bankAccType2);

                BankAccType bankAccType3 = BankAccType.builder()
                    .bankAccountType(BankAccountType.STUDENT)
                    .exchangeCurrencyCommission((float) 2f)
                    .transactionComission((float) 2f)
                    .build();

                bankAccountTypeRepository.save(bankAccType3);
            }

            if (bankAccountRepository.findAll().isEmpty()) {
                BankAccType single = bankAccountTypeRepository.findByBankAccountType(BankAccountType.STANDARD);
                BankAccType multi = bankAccountTypeRepository.findByBankAccountType(BankAccountType.MULTI_CURRENCY);
                BankAccType student = bankAccountTypeRepository.findByBankAccountType(BankAccountType.STUDENT);

                BankAccount bankAccount = BankAccount.builder()
                    .bankAccType(multi)
                    .number("321123")
                    .saldos(new HashSet<>())
                    .transactions(new HashSet<>())
                    .user(userRepository.findByIdentifier("11111111").get())
                    .build();

                bankAccountRepository.save(bankAccount);

                Set<Saldo> saldos = currencyTypeRepository.findAll()
                    .stream()
                    .map(e -> saldoRepository.save(Saldo.builder()
                        .balance(new BigDecimal(100f))
                        .currencyType(e)
                        .credits(new HashSet<>())
                        .bankAccount(bankAccount)
                        .build()))
                    .collect(Collectors.toSet());

            }
        }
    }

    @Test
    public void deleteBankAccountByIdTest() {  BankAccType multi = bankAccountTypeRepository.findByBankAccountType(BankAccountType.MULTI_CURRENCY);

        BankAccount bankAccount = BankAccount.builder()
            .bankAccType(multi)
            .number("321123")
            .saldos(new HashSet<>())
            .transactions(new HashSet<>())
            .user(userRepository.findByIdentifier("11111111").get())
            .build();

        bankAccountRepository.save(bankAccount);
        assertThat(bankAccountRepository.findAll().size()).isEqualTo(4);
    }
}
