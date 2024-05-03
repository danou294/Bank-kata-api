package com.example.bankkata.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "User2")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String prenom;

    private String motDePasse;

    private String email;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING) // Stocker les rôles en tant que chaînes de caractères dans la base de données
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_name")
    private List<Role> roles;

    @OneToOne(cascade = CascadeType.PERSIST) // Utiliser CascadeType.PERSIST uniquement pour la création d'un nouvel utilisateur
    private Account account;

    @OneToOne(cascade = CascadeType.PERSIST)
    private LivretEpargne livretEpargne;

    public User(String nom, String prenom, String motDePasse, String email, List<Role> roles) {
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
        this.email = email;
        this.roles = roles;
    }

    public void creerLivretEpargne(double plafondDepot) {
        livretEpargne = new LivretEpargne(plafondDepot);
    }

    public void deposerSurLivret(double montant) {
        if (livretEpargne != null) {
            livretEpargne.deposer(montant);
        }
    }

    public void retirerDuLivret(double montant) {
        if (livretEpargne != null) {
            livretEpargne.retirer(montant);
        }
    }

    public double soldeLivretEpargne() {
        return livretEpargne != null ? livretEpargne.getSolde() : 0.0;
    }
}
