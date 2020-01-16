package com.ii.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ii.app.config.security.JwtTokenGenerator;
import com.ii.app.dto.in.PaymentIn;
import com.ii.app.dto.out.PaymentOut;
import com.ii.app.services.interfaces.PaymentService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

    private final String API_URL = "/api/payments";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private PaymentIn paymentIn;

    @Before
    public void init() {
        this.paymentIn = new PaymentIn("11112222333344445555666677", "PLN", BigDecimal.ONE, null);
        when(paymentService.create(any(PaymentIn.class))).thenReturn(new PaymentOut());
        when(paymentService.findAll()).thenReturn(Arrays.asList(new PaymentOut(), new PaymentOut(), new PaymentOut(), new PaymentOut()));
        when(paymentService.findAllByBankAccountId(anyLong())).thenReturn(Arrays.asList(new PaymentOut(), new PaymentOut()));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void createTest() throws Exception {
        this.mockMvc.perform(post(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(this.paymentIn)))
            .andExpect(status().isOk());
    }

    @Test
    public void createUnauthorizedTest() throws Exception {
        this.mockMvc.perform(post(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(this.paymentIn)))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void createUserRoleTest() throws Exception {
        this.mockMvc.perform(post(API_URL)
             .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(this.paymentIn)))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void findByAccountIdTest() throws Exception {
        this.mockMvc.perform(get(API_URL + "/account/1"))
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(status().isOk());
    }

    @Test
    public void findByAccountIdUnauthorizedTest() throws Exception {
        this.mockMvc.perform(get(API_URL + "/account/1"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void findAllTest() throws Exception {
        this.mockMvc.perform(get(API_URL))
            .andExpect(jsonPath("$.*", hasSize(4)))
            .andExpect(status().isOk());
    }

    @Test
    public void findAllUnauthorizedTest() throws Exception {
        this.mockMvc.perform(get(API_URL))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void findAllUserRoleTest() throws Exception {
        this.mockMvc.perform(get(API_URL))
            .andExpect(status().isForbidden());
    }
}
