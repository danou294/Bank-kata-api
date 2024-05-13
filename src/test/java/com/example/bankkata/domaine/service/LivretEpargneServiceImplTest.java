package com.example.bankkata.domaine.service;

import com.example.bankkata.domain.model.LivretEpargne;
import com.example.bankkata.domain.port.LivretEpargneRepository;
import com.example.bankkata.domain.service.LivretEpargne.LivretEpargneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LivretEpargneServiceImplTest {

    @Mock
    private LivretEpargneRepository livretEpargneRepository;

    @InjectMocks
    private LivretEpargneServiceImpl livretEpargneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deposit_ShouldIncreaseBalance() {
        String accountNumber = "1";
        double initialAmount = 100.0;
        double depositAmount = 50.0;
        double depositCeiling = 200.0;

        LivretEpargne livretEpargne = new LivretEpargne(initialAmount, depositCeiling);

        when(livretEpargneRepository.findByAccountId(Long.valueOf(accountNumber))).thenReturn(Optional.of(livretEpargne));

        // Débogage : Afficher le solde initial
        System.out.println("Solde initial avant dépôt : " + livretEpargne.getBalance());

        livretEpargneService.deposit(accountNumber, depositAmount);

        // Débogage : Afficher le solde après dépôt
        System.out.println("Solde après dépôt : " + livretEpargne.getBalance());

        assertEquals(initialAmount + depositAmount, livretEpargne.getBalance(), "Le solde doit augmenter après un dépôt.");
    }

    @Test
    void deposit_ShouldThrowException_WhenExceedsMaxBalance() {
        String accountNumber = "1";
        double initialAmount = 150.0;
        double depositAmount = 100.0;
        double depositCeiling = 200.0;

        LivretEpargne livretEpargne = new LivretEpargne(initialAmount, depositCeiling);

        when(livretEpargneRepository.findByAccountId(Long.valueOf(accountNumber))).thenReturn(Optional.of(livretEpargne));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            livretEpargneService.deposit(accountNumber, depositAmount);
        });

        assertEquals("Deposit exceeds the account ceiling.", exception.getMessage());
    }

    @Test
    void getBalance_ShouldReturnCurrentBalance() {
        String accountNumber = "1";
        double initialAmount = 100.0;
        double depositCeiling = 200.0;

        LivretEpargne livretEpargne = new LivretEpargne(initialAmount, depositCeiling);

        when(livretEpargneRepository.findByAccountId(Long.valueOf(accountNumber))).thenReturn(Optional.of(livretEpargne));

        double balance = livretEpargneService.getBalance(accountNumber);

        assertEquals(initialAmount, balance, "Le solde initial doit être retourné.");
    }
}
