package com.example.bankkata.domaine.model;

import com.example.bankkata.domain.exceptions.InsufficientFundsException;
import com.example.bankkata.domain.model.LivretEpargne;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LivretEpargneTest {

    @Test
    public void testCreateLivretEpargne() {
        LivretEpargne livretEpargne = new LivretEpargne(100.0, 200.0);
        assertNotNull(livretEpargne);
        assertEquals(100.0, livretEpargne.getBalance(), 0.01);
        assertEquals(200.0, livretEpargne.getDepositCeiling(), 0.01);
        assertFalse(livretEpargne.isAutorisationDecouvert());
        assertEquals(0.0, livretEpargne.getMontantAutoriseDecouvert(), 0.01);
    }

    @Test
    public void testDeposit() {
        LivretEpargne livretEpargne = new LivretEpargne(100.0, 200.0);
        livretEpargne.deposit(50.0);
        assertEquals(150.0, livretEpargne.getBalance(), 0.01);

        assertEquals(1, livretEpargne.getOperations().size());
        assertEquals("DEPOSIT", livretEpargne.getOperations().get(0).getType());
        assertEquals(50.0, livretEpargne.getOperations().get(0).getAmount(), 0.01);
        assertEquals(150.0, livretEpargne.getOperations().get(0).getBalanceAfterOperation(), 0.01);
    }

    @Test
    public void testDepositExceedsCeiling() {
        LivretEpargne livretEpargne = new LivretEpargne(180.0, 200.0);
        assertThrows(IllegalArgumentException.class, () -> {
            livretEpargne.deposit(50.0);
        });
    }

    @Test
    public void testWithdraw() {
        LivretEpargne livretEpargne = new LivretEpargne(100.0, 200.0);
        livretEpargne.withdraw(50.0);
        assertEquals(50.0, livretEpargne.getBalance(), 0.01);

        assertEquals(1, livretEpargne.getOperations().size());
        assertEquals("WITHDRAW", livretEpargne.getOperations().get(0).getType());
        assertEquals(50.0, livretEpargne.getOperations().get(0).getAmount(), 0.01);
        assertEquals(50.0, livretEpargne.getOperations().get(0).getBalanceAfterOperation(), 0.01);
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        LivretEpargne livretEpargne = new LivretEpargne(100.0, 200.0);
        assertThrows(InsufficientFundsException.class, () -> {
            livretEpargne.withdraw(150.0);
        });

        assertEquals(0, livretEpargne.getOperations().size());
    }

    @Test
    public void testNoOverdraftAllowed() {
        LivretEpargne livretEpargne = new LivretEpargne(100.0, 200.0);
        assertThrows(InsufficientFundsException.class, () -> {
            livretEpargne.withdraw(130.0);
        });

        assertEquals(0, livretEpargne.getOperations().size());
    }
}
