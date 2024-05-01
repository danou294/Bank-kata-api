package com.example.bankkata.domaine.model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.bankkata.domain.model.Account;
import com.example.bankkata.domain.model.Role;
import com.example.bankkata.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        // Initie l'utilisateur avec un rôle
        user = new User("John", "Doe", "password", "john@example.com", Arrays.asList(Role.USER));
    }

    @Test
    void testUserRoles() {
        // Vérifie que les rôles de l'utilisateur sont correctement assignés
        assertNotNull(user.getRoles(), "La liste des rôles ne devrait pas être null");
        assertTrue(user.getRoles().contains(Role.USER), "L'utilisateur devrait avoir le rôle USER");
    }

    @Test
void testDeposit() {
    // Vérifie que le compte associé à l'utilisateur n'est pas null avant de déposer de l'argent
    assertNotNull(user.getAccount(), "Le compte associé à l'utilisateur ne devrait pas être null");

    // Effectue un dépôt sur le compte associé à l'utilisateur
    user.getAccount().deposit(50.0);

    // Vérifie que le solde du compte est correct après le dépôt
    assertEquals(50.0, user.getAccount().getBalance(), 0.01);
}

@Test
void testWithdrawSufficientFunds() {
    // Vérifie que le compte associé à l'utilisateur n'est pas null avant de retirer de l'argent
    assertNotNull(user.getAccount(), "Le compte associé à l'utilisateur ne devrait pas être null");

    // Dépose de l'argent sur le compte associé à l'utilisateur pour s'assurer qu'il a suffisamment de fonds
    user.getAccount().deposit(100.0);

    // Effectue un retrait sur le compte associé à l'utilisateur
    assertTrue(user.getAccount().withdraw(50.0));

    // Vérifie que le solde du compte est correct après le retrait
    assertEquals(50.0, user.getAccount().getBalance(), 0.01);
}

}
