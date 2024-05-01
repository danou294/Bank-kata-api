package com.example.bankkata.domain.model;

import com.example.bankkata.domain.exceptions.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1000.0, true, 500.0); // Crée un compte avec autorisation de découvert
    }

    @Test
    public void deposit_positiveAmount_shouldIncreaseBalance() {
        account.deposit(200);
        assertEquals(1200.0, account.getBalance());
    }

    @Test
    public void deposit_negativeAmount_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-200));
    }

    @Test
    public void withdraw_sufficientBalance_shouldDecreaseBalance() {
        account.withdraw(200);
        assertEquals(800.0, account.getBalance());
    }

    @Test
    public void withdraw_insufficientBalance_shouldThrowException() {
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(2500)); // Tentative de retrait supérieur au solde autorisé
    }
}
