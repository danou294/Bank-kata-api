package com.example.bankkata.domain.port;

import com.example.bankkata.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Enregistrer un utilisateur dans la base de données.
    // Spring Data JPA fournit déjà une méthode save, donc celle-ci pourrait être redondante.
    <S extends User> S save(S user);


    // Supprimer un utilisateur de la base de données.
    // Cette méthode est ajoutée pour compléter les opérations CRUD.
    void delete(User user);

    // Récupérer tous les utilisateurs de la base de données.
    List<User> findAll();

    // Ajout d'une méthode pour trouver un utilisateur par son email.
    Optional<User> findByEmail(String email);
}
