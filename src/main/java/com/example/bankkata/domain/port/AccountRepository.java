package com.example.bankkata.domain.port;

import com.example.bankkata.domain.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

    // Enregistrer un compte dans la base de données.
    // Spring Data JPA fournit déjà une méthode save, donc celle-ci pourrait être redondante.
    <S extends Account> S save(S account);

    void delete(Account account);

    Iterable<Account> findAll();
}

