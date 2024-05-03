package com.example.bankkata.domaine.model;

import com.example.bankkata.domain.exceptions.InsufficientFundsException;
import com.example.bankkata.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        // Initialise l'utilisateur avec un rôle et crée un nouveau compte associé
        user = new User("John", "Doe", "password", "john@example.com", Arrays.asList(Role.USER));
        user.setAccount(new Account(0.0, true, 100.0));
        user.creerLivretEpargne(500.0); // Création d'un livret d'épargne avec un plafond de dépôt de 500 euros
    }

    @Test
    void testUserRoles() {
        // Vérifie que les rôles de l'utilisateur sont correctement assignés
        assertNotNull(user.getRoles(), "La liste des rôles ne devrait pas être null");
        assertTrue(user.getRoles().contains(Role.USER), "L'utilisateur devrait avoir le rôle USER");
    }

    @Test
    void testDeposit() {
        // Effectue un dépôt sur le compte associé à l'utilisateur
        user.getAccount().deposit(50.0);

        // Vérifie que le solde du compte est correct après le dépôt
        assertEquals(50.0, user.getAccount().getBalance(), 0.01);
    }

    @Test
    void testWithdrawSufficientFunds() {
        // Dépose de l'argent sur le compte associé à l'utilisateur pour s'assurer qu'il a suffisamment de fonds
        user.getAccount().deposit(100.0);

        // Vérifie qu'aucune exception n'est lancée lors du retrait avec des fonds suffisants
        assertDoesNotThrow(() -> {
            user.getAccount().withdraw(50.0);
        });

        // Vérifie que le solde du compte est correct après le retrait
        assertEquals(50.0, user.getAccount().getBalance(), 0.01);
    }

    @Test
    void testWithdrawInsufficientFunds() {
        // Dépose de l'argent insuffisant sur le compte associé à l'utilisateur
        user.getAccount().deposit(30.0);

        // Vérifie qu'un retrait avec des fonds insuffisants lance une exception
        assertThrows(InsufficientFundsException.class, () -> {
            user.getAccount().withdraw(500.0);
        });

        // Vérifie que le solde du compte est inchangé après un retrait infructueux
        assertEquals(30.0, user.getAccount().getBalance(), 0.01);
    }

    @Test
    void testLivretEpargneDeposit() {
        // Effectue un dépôt sur le livret d'épargne associé à l'utilisateur
        user.deposerSurLivret(300.0);

        // Vérifie que le solde du livret d'épargne est correct après le dépôt
        assertEquals(300.0, user.soldeLivretEpargne(), 0.01);
    }

    @Test
    void testLivretEpargneWithdraw() {
        // Dépose de l'argent sur le livret d'épargne associé à l'utilisateur
        user.deposerSurLivret(500.0);

        // Effectue un retrait sur le livret d'épargne
        user.retirerDuLivret(200.0);

        // Vérifie que le solde du livret d'épargne est correct après le retrait
        assertEquals(300.0, user.soldeLivretEpargne(), 0.01);
    }
}
