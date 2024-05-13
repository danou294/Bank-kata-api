package com.example.bankkata.adapter;

import com.example.bankkata.domain.adapter.AccountController;
import com.example.bankkata.domain.model.Operation;
import com.example.bankkata.domain.service.Account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void deposit_ShouldCallServiceDeposit() throws Exception {
        String accountNumber = "1";
        double amount = 100.0;

        mockMvc.perform(post("/accounts/{accountNumber}/deposit", accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(amount)))
                .andExpect(status().isOk());

        verify(accountService, times(1)).deposit(accountNumber, amount);
    }

    @Test
    void withdraw_ShouldCallServiceWithdraw() throws Exception {
        String accountNumber = "1";
        double amount = 50.0;

        mockMvc.perform(post("/accounts/{accountNumber}/withdraw", accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(amount)))
                .andExpect(status().isOk());

        verify(accountService, times(1)).withdraw(accountNumber, amount);
    }

    @Test
    void getBalance_ShouldReturnBalance() throws Exception {
        String accountNumber = "1";
        double balance = 100.0;

        when(accountService.getBalance(accountNumber)).thenReturn(balance);

        mockMvc.perform(get("/accounts/{accountNumber}/balance", accountNumber))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(balance)));

        verify(accountService, times(1)).getBalance(accountNumber);
    }

    @Test
    void getMonthlyStatement_ShouldReturnStatement() throws Exception {
        String accountNumber = "1";
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(LocalDateTime.now(), "DEPOSIT", 100.0, 200.0));
        operations.add(new Operation(LocalDateTime.now(), "WITHDRAW", 50.0, 150.0));

        when(accountService.getMonthlyStatement(accountNumber)).thenReturn(operations);

        mockMvc.perform(get("/accounts/{accountNumber}/statement", accountNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(operations.size()));

        verify(accountService, times(1)).getMonthlyStatement(accountNumber);
    }
}
