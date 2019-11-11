package com.ii.app.service;

import com.ii.app.AppApplication;
import com.ii.app.dto.edit.BankAccTypeEdit;
import com.ii.app.dto.out.BankAccTypeOut;
import com.ii.app.exceptions.ApiException;
import com.ii.app.models.BankAccType;
import com.ii.app.models.enums.BankAccountType;
import com.ii.app.repositories.BankAccountTypeRepository;
import com.ii.app.services.interfaces.BankAccTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = AppApplication.class)
public class BankAccTypeServiceTest {

    @SpyBean
    private BankAccTypeService bankAccTypeService;

    @Autowired
    private BankAccountTypeRepository bankAccountTypeRepository;

    @Test
    public void updateBankAccTypeTest() {
        BankAccType bankAccType = bankAccountTypeRepository.save(
            BankAccType.builder().bankAccountType(BankAccountType.MULTI_CURRENCY)
                .exchangeCurrencyCommission(2f)
                .transactionComission(3f)
                .build()
        );
        final float excBefore = bankAccType.getExchangeCurrencyCommission();
        final float transBefore = bankAccType.getTransactionComission();

        final float newExch = 5f;
        final float newTrans = 6f;
        BankAccTypeOut bankAccTypeOut = bankAccTypeService.update(
            bankAccType.getId(), new BankAccTypeEdit(bankAccType.getId(), BigDecimal.valueOf(newTrans), BigDecimal.valueOf(newExch))
        );

        Optional<BankAccType> afterUpdate = bankAccountTypeRepository.findById(bankAccType.getId());
        assertThat(afterUpdate).isPresent();
        assertThat(afterUpdate.get().getExchangeCurrencyCommission()).isEqualTo(newExch);
        assertThat(afterUpdate.get().getTransactionComission()).isEqualTo(newTrans);
        assertThat(afterUpdate.get().getBankAccountType()).isEqualTo(bankAccType.getBankAccountType());
        assertThat(bankAccTypeOut.getExchangeCurrencyCommission()).isEqualTo(newExch);
        assertThat(bankAccTypeOut.getTransactionComission()).isEqualTo(newTrans);
    }

    @Test(expected = ApiException.class)
    public void updateBankAccTypeNotFound() {
        bankAccTypeService.update(-1L, null);
    }

    @Test
    public void findByIdTest() {
        BankAccType bankAccType = bankAccountTypeRepository.save(
            BankAccType.builder().bankAccountType(BankAccountType.MULTI_CURRENCY)
                .exchangeCurrencyCommission(2f)
                .transactionComission(3f)
                .build()
        );

        BankAccTypeOut bankAccTypeOut = bankAccTypeService.findById(bankAccType.getId());

        assertThat(bankAccTypeOut.getTransactionComission()).isEqualTo(bankAccType.getTransactionComission());
        assertThat(bankAccTypeOut.getExchangeCurrencyCommission()).isEqualTo(bankAccType.getExchangeCurrencyCommission());
        assertThat(bankAccTypeOut.getBankAccountType()).isEqualTo(bankAccType.getBankAccountType());
    }

    @Test(expected = ApiException.class)
    public void findByIdNotFoundTest() {
        bankAccTypeService.findById(-1L);
    }
}

