package com.example.bankkata.domain.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "livret_epargne")
public class LivretEpargne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    private double plafondDepot;

    private double solde;

    public LivretEpargne(double plafondDepot) {
        this.plafondDepot = plafondDepot;
        this.solde = 0.0;
    }

    public void deposer(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant du dépôt doit être positif");
        }
        if (solde + montant > plafondDepot) {
            throw new IllegalArgumentException("Dépassement du plafond de dépôt");
        }
        solde += montant;
    }

    public void retirer(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant du retrait doit être positif");
        }
        if (solde - montant < 0) {
            throw new IllegalArgumentException("Solde insuffisant");
        }
        solde -= montant;
    }
}
