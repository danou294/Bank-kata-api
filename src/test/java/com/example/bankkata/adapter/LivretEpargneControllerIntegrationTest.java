package com.example.bankkata.adapter;

import com.example.bankkata.BankKataApplication;
import com.example.bankkata.domain.service.LivretEpargne.LivretEpargneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BankKataApplication.class)
public class LivretEpargneControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private LivretEpargneService livretEpargneService;

    @Test
    void deposit_ShouldIncreaseBalance() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String accountNumber = "1";
        double amount = 50.0;

        mockMvc.perform(post("/livret-epargne/{accountNumber}/deposit", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(amount)))
                .andExpect(status().isOk());

        verify(livretEpargneService, times(1)).deposit(accountNumber, amount);
    }

    @Test
    void getBalance_ShouldReturnCurrentBalance() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String accountNumber = "1";
        double balance = 100.0;

        when(livretEpargneService.getBalance(accountNumber)).thenReturn(balance);

        mockMvc.perform(get("/livret-epargne/{accountNumber}/balance", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(balance)));

        verify(livretEpargneService, times(1)).getBalance(accountNumber);
    }
}
