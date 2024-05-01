package com.example.bankkata.domain.service.User;

import com.example.bankkata.domain.model.User;

public interface UserService {
    /**
     * Crée un nouvel utilisateur et le sauvegarde dans la base de données.
     * @param user L'utilisateur à créer.
     * @return L'utilisateur sauvegardé.
     */
    User createUser(User user);

    /**
     * Met à jour un utilisateur existant dans la base de données.
     * @param user L'utilisateur à mettre à jour.
     * @return L'utilisateur mis à jour.
     */
    User updateUser(User user);

    /**
     * Supprime un utilisateur de la base de données par son identifiant.
     * @param userId L'identifiant de l'utilisateur à supprimer.
     */
    void deleteUser(Long userId);

    /**
     * Récupère un utilisateur par son identifiant.
     * @param userId L'identifiant de l'utilisateur à récupérer.
     * @return L'utilisateur trouvé.
     */
    User getUserById(Long userId);

    /**
     * Récupère tous les utilisateurs enregistrés dans la base de données.
     * @return Tous les utilisateurs.
     */
    Iterable<User> getAllUsers();

    User getUserByEmail(String email) throws Exception;
}
