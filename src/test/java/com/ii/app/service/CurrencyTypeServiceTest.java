package com.ii.app.service;

import com.ii.app.AppApplication;
import com.ii.app.dto.edit.CurrencyTypeEdit;
import com.ii.app.dto.out.CurrencyTypeOut;
import com.ii.app.exceptions.ApiException;
import com.ii.app.models.CurrencyType;
import com.ii.app.repositories.CurrencyTypeRepository;
import com.ii.app.services.interfaces.CurrencyTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = AppApplication.class)
public class CurrencyTypeServiceTest {

    @Autowired
    private CurrencyTypeService currencyTypeService;

    @Autowired
    private CurrencyTypeRepository currencyTypeRepository;

    @Test
    public void findAllCurrencyTypesTest(){
        assertThat(currencyTypeService.findAll().size()).isEqualTo(currencyTypeRepository.findAll().size());
    }

    @Test
    public void updateCurrencyTypeTest(){
        CurrencyType currencyType = currencyTypeRepository.save(
            CurrencyType.builder()
                .exchangeRate(2.32f)
                .name("USD")
                .saldos(new HashSet<>())
                .build()
        );
        final String name = currencyType.getName();
        final float exchangeRate = currencyType.getExchangeRate();

        CurrencyTypeEdit currencyTypeEdit = new CurrencyTypeEdit(currencyType.getId(), name, exchangeRate);
        CurrencyTypeOut currencyTypeOut = currencyTypeService.update(currencyType.getId(), currencyTypeEdit);

        Optional<CurrencyType> afterUpdate = currencyTypeRepository.findById(currencyType.getId());
        assertThat(afterUpdate).isPresent();
        assertThat(afterUpdate.get().getName()).isEqualTo(name);
        assertThat(afterUpdate.get().getExchangeRate()).isEqualTo(exchangeRate);

        assertThat(currencyTypeOut.getExchangeRate()).isEqualTo(exchangeRate);
        assertThat(currencyTypeOut.getName()).isEqualTo(name);
    }

    @Test(expected = ApiException.class)
    public void updateCurrencyTypeNotFoundTest(){
        currencyTypeService.update(-1L, null);
    }
}
