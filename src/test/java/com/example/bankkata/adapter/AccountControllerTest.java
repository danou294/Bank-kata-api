package com.example.bankkata.adapter;

import com.example.bankkata.domain.adapter.AccountController;
import com.example.bankkata.domain.model.Account;
import com.example.bankkata.domain.service.Account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
    Account createdAccount = new Account(0.0, false, 0.0); // Adjusted to match the new constructor signature
    createdAccount.setAccountId(123456L); // Mocking the generated ID
    when(accountService.createAccount(0.0, false, 0.0)).thenReturn(createdAccount); // Adjusted to match the new method signature

    mockMvc.perform(post("/api/accounts/create")
            .param("initialBalance", "0.0")
            .param("autorisationDecouvert", "false")
            .param("montantAutoriseDecouvert", "0.0"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accountId").value(123456L)) // Expecting the mocked ID
            .andExpect(jsonPath("$.balance").value(0.0)); // Expecting initial balance to be 0.0
}


    @Test
    void deposit_ReturnsUpdatedAccountAfterDeposit() throws Exception {
        // Mocking the updated account after deposit
        Account updatedAccount = new Account(100.0, false, 0.0); // Adjusted to match the new constructor signature
        updatedAccount.setAccountId(123456L);
        when(accountService.deposit(eq("123456"), any(Double.class))).thenReturn(updatedAccount);

        // Perform the deposit request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/123456/deposit")
                .param("amount", "100.0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").value(123456L)) // Expected account ID
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(100.0)); // Expected updated balance
    }

}
