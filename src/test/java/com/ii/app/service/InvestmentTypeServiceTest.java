package com.ii.app.service;

import com.ii.app.AppApplication;
import com.ii.app.repositories.InvestmentTypeRepository;
import com.ii.app.services.interfaces.InvestmentTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = AppApplication.class)
public class InvestmentTypeServiceTest {

    @SpyBean
    private InvestmentTypeService investmentTypeService;

    @Autowired
    private InvestmentTypeRepository investmentTypeRepository;

    @Test
    public void findAllInvestmentTypesTest(){
        assertThat(investmentTypeService.findAll().size()).isEqualTo(investmentTypeRepository.findAll().size());
    }
}