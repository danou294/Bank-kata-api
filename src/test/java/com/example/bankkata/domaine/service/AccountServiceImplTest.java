package com.example.bankkata.domaine.service;

import com.example.bankkata.domain.exceptions.AccountNotFoundException;
import com.example.bankkata.domain.model.Account;
import com.example.bankkata.domain.model.Operation;
import com.example.bankkata.domain.port.AccountRepository;
import com.example.bankkata.domain.service.Account.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper subclass to access protected Account constructor
    static class TestAccount extends Account {
        public TestAccount() {
            super();
        }
    }

    @Test
    void deposit_ShouldIncreaseBalance() {
        String accountNumber = "1";
        double amount = 100.0;
        Account account = new TestAccount();
        account.deposit(50.0);

        when(accountRepository.findByAccountId(Long.valueOf(accountNumber))).thenReturn(Optional.of(account));

        accountService.deposit(accountNumber, amount);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountCaptor.capture());
        Account savedAccount = accountCaptor.getValue();

        assertEquals(150.0, savedAccount.getBalance());
    }

    @Test
    void withdraw_ShouldDecreaseBalance() {
        String accountNumber = "1";
        double amount = 50.0;
        Account account = new TestAccount();
        account.deposit(100.0);

        when(accountRepository.findByAccountId(Long.valueOf(accountNumber))).thenReturn(Optional.of(account));

        accountService.withdraw(accountNumber, amount);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountCaptor.capture());
        Account savedAccount = accountCaptor.getValue();

        assertEquals(50.0, savedAccount.getBalance());
    }

    @Test
    void getBalance_ShouldReturnCurrentBalance() {
        String accountNumber = "1";
        Account account = new TestAccount();
        account.deposit(100.0);

        when(accountRepository.findByAccountId(Long.valueOf(accountNumber))).thenReturn(Optional.of(account));

        double balance = accountService.getBalance(accountNumber);

        assertEquals(100.0, balance);
    }

    @Test
    void getMonthlyStatement_ShouldReturnRecentOperations() {
        String accountNumber = "1";
        Account account = new TestAccount();
        Operation operation = new Operation();
        operation.setDate(LocalDateTime.now());
        operation.setAmount(100.0);
        operation.setType("deposit");
        account.getOperations().add(operation);

        when(accountRepository.findByAccountId(Long.valueOf(accountNumber))).thenReturn(Optional.of(account));

        List<Operation> operations = accountService.getMonthlyStatement(accountNumber);

        assertEquals(1, operations.size());
        assertEquals(operation, operations.get(0));
    }

    @Test
    void deposit_ShouldThrowException_WhenAccountNotFound() {
        String accountNumber = "1";
        double amount = 100.0;

        when(accountRepository.findByAccountId(Long.valueOf(accountNumber))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.deposit(accountNumber, amount));
    }

    @Test
    void withdraw_ShouldThrowException_WhenAccountNotFound() {
        String accountNumber = "1";
        double amount = 50.0;

        when(accountRepository.findByAccountId(Long.valueOf(accountNumber))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.withdraw(accountNumber, amount));
    }

    @Test
    void getBalance_ShouldThrowException_WhenAccountNotFound() {
        String accountNumber = "1";

        when(accountRepository.findByAccountId(Long.valueOf(accountNumber))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getBalance(accountNumber));
    }

    @Test
    void getMonthlyStatement_ShouldThrowException_WhenAccountNotFound() {
        String accountNumber = "1";

        when(accountRepository.findByAccountId(Long.valueOf(accountNumber))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getMonthlyStatement(accountNumber));
    }
}
