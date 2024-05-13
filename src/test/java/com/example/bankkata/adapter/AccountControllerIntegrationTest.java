package com.example.bankkata.adapter;

import com.example.bankkata.BankKataApplication;
import com.example.bankkata.domain.model.Operation;
import com.example.bankkata.domain.service.Account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BankKataApplication.class)
public class AccountControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void deposit_ShouldIncreaseBalance() throws Exception {
        String accountNumber = "1";
        double amount = 50.0;

        mockMvc.perform(post("/accounts/{accountNumber}/deposit", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(amount)))
                .andExpect(status().isOk());

        verify(accountService, times(1)).deposit(accountNumber, amount);
    }

    @Test
    void withdraw_ShouldDecreaseBalance() throws Exception {
        String accountNumber = "1";
        double amount = 30.0;

        mockMvc.perform(post("/accounts/{accountNumber}/withdraw", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(amount)))
                .andExpect(status().isOk());

        verify(accountService, times(1)).withdraw(accountNumber, amount);
    }

    @Test
    void getBalance_ShouldReturnCurrentBalance() throws Exception {
        String accountNumber = "1";
        double balance = 100.0;

        when(accountService.getBalance(accountNumber)).thenReturn(balance);

        mockMvc.perform(get("/accounts/{accountNumber}/balance", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(balance)));

        verify(accountService, times(1)).getBalance(accountNumber);
    }

    @Test
    void getMonthlyStatement_ShouldReturnStatement() throws Exception {
        String accountNumber = "1";
        Operation operation1 = new Operation(LocalDateTime.now(), "DEPOSIT", 100.0, 100.0);
        Operation operation2 = new Operation(LocalDateTime.now().minusDays(1), "WITHDRAW", 50.0, 50.0);

        when(accountService.getMonthlyStatement(accountNumber)).thenReturn(Arrays.asList(operation1, operation2));

        mockMvc.perform(get("/accounts/{accountNumber}/statement", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("DEPOSIT"))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[1].type").value("WITHDRAW"))
                .andExpect(jsonPath("$[1].amount").value(50.0));

        verify(accountService, times(1)).getMonthlyStatement(accountNumber);
    }
}
