package com.ii.app.controllers;

import com.ii.app.dto.out.BankAccTypeOut;
import com.ii.app.models.BankAccType;
import com.ii.app.models.enums.BankAccountType;
import com.ii.app.service.BankAccountServiceTest;
import com.ii.app.services.interfaces.BankAccTypeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankAccTypeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccTypeService bankAccTypeService;

    @Before
    public void setup() {
        when(bankAccTypeService.findAll()).thenReturn(
            Arrays.asList(
                BankAccTypeOut.builder()
                    .bankAccountType(BankAccountType.MULTI_CURRENCY)
                    .exchangeCurrencyCommission(2f)
                    .transactionComission(3f
                    ).build(),
                BankAccTypeOut.builder()
                    .bankAccountType(BankAccountType.STUDENT)
                    .exchangeCurrencyCommission(2f)
                    .transactionComission(3f
                    ).build()
            )
        );
    }

    @Test
    @WithMockUser
    public void findAllTest() throws Exception {
        this.mockMvc.perform(get("/api/bankaccounttypes"))
            .andExpect(status().isOk());
    }
}
