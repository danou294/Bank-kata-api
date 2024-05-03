package com.example.bankkata.domain.adapter;

import com.example.bankkata.domain.model.LivretEpargne;
import com.example.bankkata.domain.service.LivretEpargne.LivretEpargneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class LivretEpargneControllerTest {

    @Mock
    private LivretEpargneService livretEpargneService;

    @InjectMocks
    private LivretEpargneController livretEpargneController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createLivretEpargne_ShouldReturnCreatedLivretEpargne() {
        double plafondDepot = 1000.0;
        LivretEpargne livretEpargne = new LivretEpargne(plafondDepot);
        when(livretEpargneService.createLivretEpargne(anyDouble())).thenReturn(livretEpargne);

        ResponseEntity<LivretEpargne> responseEntity = livretEpargneController.createLivretEpargne(plafondDepot);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(livretEpargne, responseEntity.getBody());
    }

    @Test
    void getLivretEpargneById_ExistingId_ShouldReturnLivretEpargne() {
        long existingLivretEpargneId = 1L;
        LivretEpargne livretEpargne = new LivretEpargne(2000.0);
        when(livretEpargneService.getLivretEpargneById(existingLivretEpargneId)).thenReturn(livretEpargne);

        ResponseEntity<LivretEpargne> responseEntity = livretEpargneController.getLivretEpargneById(existingLivretEpargneId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(livretEpargne, responseEntity.getBody());
    }

    @Test
    void updatePlafondDepot_ExistingId_ShouldReturnUpdatedLivretEpargne() {
        long existingLivretEpargneId = 1L;
        double nouveauPlafond = 3000.0;
        LivretEpargne livretEpargne = new LivretEpargne(2000.0);
        when(livretEpargneService.updatePlafondDepot(existingLivretEpargneId, nouveauPlafond)).thenReturn(livretEpargne);

        ResponseEntity<LivretEpargne> responseEntity = livretEpargneController.updatePlafondDepot(existingLivretEpargneId, nouveauPlafond);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(livretEpargne, responseEntity.getBody());
    }

    @Test
    void deleteLivretEpargne_ExistingId_ShouldReturnNoContent() {
        long existingLivretEpargneId = 1L;

        ResponseEntity<Void> responseEntity = livretEpargneController.deleteLivretEpargne(existingLivretEpargneId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(livretEpargneService, times(1)).deleteLivretEpargne(existingLivretEpargneId);
    }
}
