package com.example.bankkata.domain.port;

import com.example.bankkata.domain.model.LivretEpargne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivretEpargneRepository extends JpaRepository<LivretEpargne, Long> {

    // Recherche un livret d'épargne par son ID
    Optional<LivretEpargne> findById(Long id);

    // Recherche un livret d'épargne par son plafond de dépôt
    Optional<LivretEpargne> findByPlafondDepot(double plafondDepot);

    // Enregistre un livret d'épargne
    LivretEpargne save(LivretEpargne livretEpargne);

    // Supprime un livret d'épargne par son ID
    void deleteById(Long id);
}
