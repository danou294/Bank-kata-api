package com.example.bankkata.adapter;

import com.example.bankkata.domain.adapter.AccountController;
import com.example.bankkata.domain.exceptions.InsufficientFundsException;
import com.example.bankkata.domain.model.Account;
import com.example.bankkata.domain.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService)).build();
    }

    @Test
    void createAccount_ReturnsCreatedAccountWithGeneratedId() throws Exception {
        Account createdAccount = new Account(0.0);
        createdAccount.setAccountId(123456L); // Mocking the generated ID
        when(accountService.createAccount()).thenReturn(createdAccount);

        mockMvc.perform(post("/api/accounts/create"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(123456L)) // Expecting the mocked ID
                .andExpect(jsonPath("$.balance").value(0.0)); // Expecting initial balance to be 0.0
    }
}
