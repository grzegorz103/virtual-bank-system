package com.ii.app.service;

import com.ii.app.AppApplication;
import com.ii.app.dto.in.InvestmentIn;
import com.ii.app.dto.out.InvestmentOut;
import com.ii.app.exceptions.ApiException;
import com.ii.app.models.Investment;
import com.ii.app.models.Saldo;
import com.ii.app.models.enums.InvestmentType;
import com.ii.app.repositories.InvestmentRepository;
import com.ii.app.repositories.InvestmentTypeRepository;
import com.ii.app.repositories.SaldoRepository;
import com.ii.app.services.interfaces.InvestmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = AppApplication.class)
public class InvestmentServiceTest {

    @SpyBean
    private InvestmentService investmentService;

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private InvestmentTypeRepository investmentTypeRepository;

    @Autowired
    private SaldoRepository saldoRepository;

    @Test
    public void findAllInvestmentsTest() {
        assertThat(investmentService.findAll().size()).isEqualTo(investmentRepository.findAll().size());
    }

    @Test
    public void findAllInvestmentsByUserTest() {
        Saldo testSaldo = saldoRepository.findAll().get(0);
        String testIdentifier = testSaldo.getBankAccount().getUser().getIdentifier();

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getName()).thenReturn(testIdentifier);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        investmentRepository.save(Investment.builder()
            .destinedSaldo(testSaldo)
            .investmentType(investmentTypeRepository.findAll().get(0))
            .startBalance(BigDecimal.valueOf(1000L))
            .currency("PLN")
            .creationDate(Instant.now())
            .updateTimespan(Instant.now())
            .currentBalance(BigDecimal.valueOf(1000L))
            .build()
        );

        assertThat(investmentService.findAllByUser().size())
            .isEqualTo(investmentRepository.findAllByDestinedSaldo_BankAccount_User_Identifier(testIdentifier).size());
    }

    @Test
    public void findByIdTest() {
        Investment fromDatabase = investmentRepository.findAll().get(0);
        InvestmentOut fromService = investmentService.findById(fromDatabase.getId());

        assertThat(fromService.getCreationDate()).isEqualTo(fromDatabase.getCreationDate());
        assertThat(fromService.getCurrency()).isEqualTo(fromDatabase.getCurrency());
        assertThat(fromService.getInvestmentType().getInvestmentStatus()).isEqualTo(fromDatabase.getInvestmentType().getInvestmentStatus());
        assertThat(fromService.getCurrentBalance()).isEqualTo(fromDatabase.getCurrentBalance());
        assertThat(fromService.getStartBalance()).isEqualTo(fromDatabase.getStartBalance());
    }

    @Test(expected = ApiException.class)
    public void findByIdNotFoundTest() {
        investmentService.findById(-1L);
    }

    @Test
    public void findInvestmentCountByTypeTest() {
        assertThat(investmentService.findInvestmentCountByType(1L)).isEqualTo(1L);
        assertThat(investmentService.findInvestmentCountByType(2L)).isEqualTo(1L);
    }

    @Test
    public void updateInvestmentStatusTest() {
        Investment fromDatabase = investmentRepository.findAll().stream().filter(e -> e.getInvestmentType().getInvestmentStatus() == InvestmentType.InvestmentStatus.ACTIVE).findFirst().get();

        InvestmentOut fromService = investmentService.updateStatus(fromDatabase.getId());

        assertThat(fromService.getInvestmentType().getInvestmentStatus()).isEqualTo(InvestmentType.InvestmentStatus.CLOSED);
    }

    @Test(expected = ApiException.class)
    public void updateClosedInvestmentTest() {
        Investment fromDatabase = investmentRepository.findAll().stream().filter(e -> e.getInvestmentType().getInvestmentStatus() == InvestmentType.InvestmentStatus.CLOSED).findFirst().get();
        investmentService.updateStatus(fromDatabase.getId());
    }

    @Test
    public void createInvestmentTest() {
        Saldo saldo = saldoRepository.findAll().get(0);
        final int dbCount = investmentRepository.findAll().size();

        InvestmentIn investmentIn = new InvestmentIn(saldo.getId(), BigDecimal.TEN);

        InvestmentOut fromService = investmentService.create(investmentIn);

        assertThat(fromService.getStartBalance()).isEqualTo(investmentIn.getStartBalance());
        assertThat(investmentRepository.findAll().size()).isEqualTo(dbCount + 1);
    }
}
