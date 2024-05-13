package com.example.bankkata.domaine.model;

import com.example.bankkata.domain.exceptions.InsufficientFundsException;
import com.example.bankkata.domain.model.Account;
import com.example.bankkata.domain.model.Operation;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    @Test
    public void testCreateAccount() {
        Account account = new Account(100.0, false, 0.0);
        assertNotNull(account);
        assertEquals(100.0, account.getBalance());
        assertFalse(account.isAutorisationDecouvert());
        assertEquals(0.0, account.getMontantAutoriseDecouvert());
    }

    @Test
    public void testDeposit() {
        Account account = new Account(100.0, false, 0.0);
        account.deposit(50.0);
        assertEquals(150.0, account.getBalance(), 0.01);

        List<Operation> operations = account.getOperations();
        assertEquals(1, operations.size());
        assertEquals("DEPOSIT", operations.get(0).getType());
        assertEquals(50.0, operations.get(0).getAmount(), 0.01);
        assertEquals(150.0, operations.get(0).getBalanceAfterOperation(), 0.01);
    }

    @Test
    public void testDepositNegativeAmount() {
        Account account = new Account(100.0, false, 0.0);
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-50.0);
        });
    }

    @Test
    public void testWithdraw() {
        Account account = new Account(100.0, false, 0.0);
        account.withdraw(50.0);
        assertEquals(50.0, account.getBalance(), 0.01);

        List<Operation> operations = account.getOperations();
        assertEquals(1, operations.size());
        assertEquals("WITHDRAW", operations.get(0).getType());
        assertEquals(50.0, operations.get(0).getAmount(), 0.01);
        assertEquals(50.0, operations.get(0).getBalanceAfterOperation(), 0.01);
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        Account account = new Account(100.0, false, 0.0);
        assertThrows(InsufficientFundsException.class, () -> {
            account.withdraw(150.0);
        });

        List<Operation> operations = account.getOperations();
        assertTrue(operations.isEmpty());
    }

    @Test
    public void testWithdrawWithOverdraft() {
        Account account = new Account(100.0, true, 50.0);
        account.withdraw(130.0);
        assertEquals(-30.0, account.getBalance(), 0.01);

        List<Operation> operations = account.getOperations();
        assertEquals(1, operations.size());
        assertEquals("WITHDRAW", operations.get(0).getType());
        assertEquals(130.0, operations.get(0).getAmount(), 0.01);
        assertEquals(-30.0, operations.get(0).getBalanceAfterOperation(), 0.01);
    }

    @Test
    public void testWithdrawOverOverdraftLimit() {
        Account account = new Account(100.0, true, 50.0);
        assertThrows(InsufficientFundsException.class, () -> {
            account.withdraw(160.0);
        });

        List<Operation> operations = account.getOperations();
        assertTrue(operations.isEmpty());
    }

    @Test
    public void testOperationsTracking() {
        Account account = new Account(100.0, false, 0.0);
        account.deposit(50.0);
        account.withdraw(30.0);

        List<Operation> operations = account.getOperations();
        assertEquals(2, operations.size());

        Operation depositOperation = operations.get(0);
        assertEquals("DEPOSIT", depositOperation.getType());
        assertEquals(50.0, depositOperation.getAmount(), 0.01);
        assertEquals(150.0, depositOperation.getBalanceAfterOperation(), 0.01);

        Operation withdrawOperation = operations.get(1);
        assertEquals("WITHDRAW", withdrawOperation.getType());
        assertEquals(30.0, withdrawOperation.getAmount(), 0.01);
        assertEquals(120.0, withdrawOperation.getBalanceAfterOperation(), 0.01);
    }
}
