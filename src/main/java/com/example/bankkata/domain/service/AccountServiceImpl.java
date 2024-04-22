package com.example.bankkata.domaine.service;

import com.example.bankkata.domain.exceptions.InsufficientFundsException;
import com.example.bankkata.domain.model.Account;
import com.example.bankkata.domain.port.AccountRepository;
import com.example.bankkata.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

  @Override
public Account createAccount() {
    Account account = new Account(0.0); // Crée un compte avec un solde initial de 0.0
    return accountRepository.save(account);
}

    @Override
    public Account deposit(String accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));
        account.deposit(amount);
        return accountRepository.save(account);
    }

    @Override
    public Account withdraw(String accountId, double amount) throws InsufficientFundsException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));
        account.withdraw(amount);
        return accountRepository.save(account);
    }

    @Override
    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll(); // Utilisation de la méthode findAll() du repository pour obtenir tous les comptes
    }

     @Override
    public double getAccountBalance(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));
        return account.getBalance();
    }

}
