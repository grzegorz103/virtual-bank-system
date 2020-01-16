package com.ii.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ii.app.config.security.JwtTokenGenerator;
import com.ii.app.dto.in.TransactionTemplateIn;
import com.ii.app.dto.out.TransactionTemplateOut;
import com.ii.app.services.interfaces.TransactionTemplateService;
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
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionTemplateControllerTest {

    private final String API_URL = "/api/transactiontemplates";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionTemplateService templateService;

    private final String TEST_ID = "12";

    private TransactionTemplateIn templateIn;

    @Before
    public void init() {
        String testAccountNr = "11112222333344445555666677";
        templateIn = new TransactionTemplateIn(
            testAccountNr, "PLN", testAccountNr, "PLN",
            BigDecimal.TEN, "Tytul", false, "Nazwa"
        );
        when(templateService.create(any(TransactionTemplateIn.class)))
            .thenReturn(new TransactionTemplateOut());
        when(templateService.findAll())
            .thenReturn(Arrays.asList(new TransactionTemplateOut(), new TransactionTemplateOut()));
        when(templateService.update(any(Long.class), any(TransactionTemplateIn.class)))
            .thenReturn(new TransactionTemplateOut());
        when(templateService.findOneById(any(Long.class)))
            .thenReturn(new TransactionTemplateOut());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void createTest() throws Exception {
       this.mockMvc.perform(post(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(templateIn)))
            .andExpect(status().isOk());
    }

    @Test
    public void createUnauthorizedTest() throws Exception {
        this.mockMvc.perform(post(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(templateIn)))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void findAllTest() throws Exception {
      this.mockMvc.perform(get(API_URL))
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void findOneByIdTest() throws Exception {
       this.mockMvc.perform(get(API_URL + "/" + TEST_ID))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateByIdTest() throws Exception {
       this.mockMvc.perform(put(API_URL + "/" + TEST_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(templateIn)))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void deleteByIdTest() throws Exception {
        this.mockMvc.perform(delete(API_URL + "/" + TEST_ID))
            .andExpect(status().isOk());
    }

    @Test
    public void findAllUnauthorizedTest() throws Exception {
        this.mockMvc.perform(get(API_URL))
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(status().isForbidden());
    }

    @Test
    public void findOneByIdUnauthorizedTest() throws Exception {
       this.mockMvc.perform(get(API_URL + "/" + TEST_ID))
            .andExpect(status().isForbidden());
    }

    @Test
    public void updateByIdUnauthorizedTest() throws Exception {
        this.mockMvc.perform(put(API_URL + "/" + TEST_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(templateIn)))
            .andExpect(status().isForbidden());
    }

    @Test
    public void deleteByIdUnauthorizedTest() throws Exception {
        this.mockMvc.perform(delete(API_URL + "/" + TEST_ID))
            .andExpect(status().isForbidden());
    }
}
