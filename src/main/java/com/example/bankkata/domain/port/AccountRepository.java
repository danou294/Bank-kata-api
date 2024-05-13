package com.example.bankkata.domain.port;

import com.example.bankkata.domain.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
        Optional<Account> findByAccountId(Long accountId);
}
