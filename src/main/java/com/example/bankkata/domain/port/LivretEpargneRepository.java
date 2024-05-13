package com.example.bankkata.domain.port;

import com.example.bankkata.domain.model.LivretEpargne;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivretEpargneRepository extends CrudRepository<LivretEpargne, String> {
    Optional<LivretEpargne> findByAccountId(Long accountId);
}
