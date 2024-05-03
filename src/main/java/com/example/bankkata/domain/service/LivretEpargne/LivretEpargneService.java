package com.example.bankkata.domain.service.LivretEpargne;

import com.example.bankkata.domain.model.LivretEpargne;

public interface LivretEpargneService {

    // Crée un nouveau livret d'épargne
    LivretEpargne createLivretEpargne(double plafondDepot);

    // Récupère un livret d'épargne par son ID
    LivretEpargne getLivretEpargneById(Long id);

    // Met à jour le plafond de dépôt d'un livret d'épargne
    LivretEpargne updatePlafondDepot(Long id, double nouveauPlafond);

    // Supprime un livret d'épargne par son ID
    void deleteLivretEpargne(Long id);
}
