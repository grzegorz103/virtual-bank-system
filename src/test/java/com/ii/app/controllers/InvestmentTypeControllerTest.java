package com.ii.app.controllers;

import com.ii.app.config.security.JwtTokenGenerator;
import com.ii.app.dto.out.InvestmentTypeOut;
import com.ii.app.services.interfaces.InvestmentTypeService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InvestmentTypeControllerTest {

    private final String API_URL = "/api/investmentTypes";

    @Autowired
    private MockMvc mockMvc;

    private static String jwt;

    @MockBean
    private InvestmentTypeService investmentTypeService;

    @BeforeClass
    public static void setup() {
        jwt = "Bearer " + JwtTokenGenerator.generate(
            "testUser",
            "testIdentifier",
            Collections.singletonList("ROLE_USER")
        );
    }

    @Before
    public void init() {
        when(investmentTypeService.findAll())
            .thenReturn(Arrays.asList(new InvestmentTypeOut(), new InvestmentTypeOut(), new InvestmentTypeOut()));
    }

    @Test
    public void findAllTest() throws Exception {
        this.mockMvc.perform(get(API_URL)
            .header("Authorization", jwt))
            .andExpect(jsonPath("$.*", hasSize(3)))
            .andExpect(status().isOk());
    }

    @Test
    public void findAllTestUnauthorized() throws Exception {
        this.mockMvc.perform(get(API_URL))
            .andExpect(status().isForbidden());
    }
}
