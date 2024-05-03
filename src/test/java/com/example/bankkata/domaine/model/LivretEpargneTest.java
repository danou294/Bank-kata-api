package com.example.bankkata.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LivretEpargneTest {

    private LivretEpargne livretEpargne;

    @BeforeEach
    void setUp() {
        livretEpargne = new LivretEpargne(1000.0); // Plafond de dépôt de 1000 euros
    }

    @Test
    void deposer_ValidAmount_DepositSuccessful() {
        livretEpargne.deposer(500.0);
        assertEquals(500.0, livretEpargne.getSolde());
    }

    @Test
    void deposer_NegativeAmount_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> livretEpargne.deposer(-100.0));
    }

    @Test
    void deposer_ExceedPlafond_DepositFails() {
        assertThrows(IllegalArgumentException.class, () -> livretEpargne.deposer(1500.0));
    }

    @Test
    void retirer_ValidAmount_WithdrawalSuccessful() {
        livretEpargne.deposer(800.0);
        livretEpargne.retirer(300.0);
        assertEquals(500.0, livretEpargne.getSolde());
    }

    @Test
    void retirer_NegativeAmount_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> livretEpargne.retirer(-100.0));
    }

    @Test
    void retirer_InsufficientFunds_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> livretEpargne.retirer(600.0));
    }
}
