package com.example.bankkata.domaine.service;

import com.example.bankkata.domain.exceptions.InsufficientFundsException;
import com.example.bankkata.domain.model.Account;
import com.example.bankkata.domain.port.AccountRepository;
import com.example.bankkata.domain.service.Account.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAccount_ValidData_ReturnsAccount() {
        Account account = new Account(100.0);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account createdAccount = accountService.createAccount();

        assertNotNull(createdAccount);
        assertNotNull(createdAccount.getAccountId());
        assertEquals(100.0, createdAccount.getBalance());
    }

    @Test
    public void deposit_ValidAmount_UpdatesBalance() {
        String accountId = "ABCD123456";
        double initialBalance = 100.0;
        double depositAmount = 50.0;
        Account account = new Account(initialBalance);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        Account updatedAccount = accountService.deposit(accountId, depositAmount);

        assertEquals(150.0, updatedAccount.getBalance());
    }

    @Test
    public void withdraw_SufficientBalance_UpdatesBalance() throws InsufficientFundsException {
        String accountId = "ABCD123456";
        double initialBalance = 100.0;
        double withdrawalAmount = 50.0;
        Account account = new Account(initialBalance);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        Account updatedAccount = accountService.withdraw(accountId, withdrawalAmount);

        assertEquals(50.0, updatedAccount.getBalance());
    }

    @Test
    public void withdraw_InsufficientBalance_ThrowsException() {
        String accountId = "ABCD123456";
        double initialBalance = 100.0;
        double withdrawalAmount = 150.0;
        Account account = new Account(initialBalance);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(InsufficientFundsException.class, () -> {
            accountService.withdraw(accountId, withdrawalAmount);
        });
    }
}
