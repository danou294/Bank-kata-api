package com.example.bankkata.domaine.service;

import com.example.bankkata.domain.model.LivretEpargne;
import com.example.bankkata.domain.port.LivretEpargneRepository;
import com.example.bankkata.domain.service.LivretEpargne.LivretEpargneServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LivretEpargneServiceImplTest {

    @Mock
    private LivretEpargneRepository livretEpargneRepository;

    @InjectMocks
    private LivretEpargneServiceImpl livretEpargneService;

    @Test
    void createLivretEpargne_ShouldReturnLivretEpargne() {
        // Given
        double plafondDepot = 1000.0;
        LivretEpargne livretEpargne = new LivretEpargne(plafondDepot);
        when(livretEpargneRepository.save(any(LivretEpargne.class))).thenReturn(livretEpargne);

        // When
        LivretEpargne createdLivretEpargne = livretEpargneService.createLivretEpargne(plafondDepot);

        // Then
        assertNotNull(createdLivretEpargne);
        assertEquals(plafondDepot, createdLivretEpargne.getPlafondDepot());
    }

    @Test
    void getLivretEpargneById_NonExistingId_ShouldReturnNull() {
        // Given
        long nonExistingLivretEpargneId = 999L;
        when(livretEpargneRepository.findById(nonExistingLivretEpargneId)).thenReturn(Optional.empty());

        // When
        LivretEpargne fetchedLivretEpargne = livretEpargneService.getLivretEpargneById(nonExistingLivretEpargneId);

        // Then
        assertNull(fetchedLivretEpargne, "Le livret d'épargne récupéré devrait être null");
    }

    @Test
    void updatePlafondDepot_ExistingId_ShouldReturnUpdatedLivretEpargne() {
        // Given
        long livretEpargneId = 1L;
        double nouveauPlafond = 3000.0;
        LivretEpargne livretEpargne = new LivretEpargne(2000.0);
        when(livretEpargneRepository.findById(livretEpargneId)).thenReturn(Optional.of(livretEpargne));
        when(livretEpargneRepository.save(any(LivretEpargne.class))).thenReturn(livretEpargne);

        // When
        LivretEpargne updatedLivretEpargne = livretEpargneService.updatePlafondDepot(livretEpargneId, nouveauPlafond);

        // Then
        assertNotNull(updatedLivretEpargne);
        assertEquals(nouveauPlafond, updatedLivretEpargne.getPlafondDepot());
    }

    @Test
    void deleteLivretEpargne_ExistingId_ShouldNotThrowException() {
        // Given
        long livretEpargneId = 1L;

        // No need to mock repository behavior for delete operation

        // When/Then
        assertDoesNotThrow(() -> {
            livretEpargneService.deleteLivretEpargne(livretEpargneId);
        });
    }

    @Test
    void getLivretEpargneById_ExistingId_ShouldReturnLivretEpargne() {
        // Given
        long existingLivretEpargneId = 1L;
        LivretEpargne livretEpargne = new LivretEpargne(2000.0);
        livretEpargne.setId(existingLivretEpargneId);

        // Configurer le comportement simulé du repository pour retourner le livret d'épargne créé
        when(livretEpargneRepository.findById(existingLivretEpargneId)).thenReturn(Optional.of(livretEpargne));

        // When
        LivretEpargne fetchedLivretEpargne = livretEpargneService.getLivretEpargneById(existingLivretEpargneId);

        // Then
        assertNotNull(fetchedLivretEpargne, "Le livret d'épargne récupéré ne devrait pas être null");
        assertEquals(existingLivretEpargneId, fetchedLivretEpargne.getId(), "L'ID du livret d'épargne récupéré devrait correspondre");
    }
}
