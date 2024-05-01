package com.example.bankkata.domain.service.Account;

import com.example.bankkata.domain.exceptions.InsufficientFundsException;
import com.example.bankkata.domain.model.Account;
import com.example.bankkata.domain.port.AccountRepository;
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
    public Account createAccount(double initialBalance, boolean autorisationDecouvert, double montantAutoriseDecouvert) {
        Account account = new Account(initialBalance, autorisationDecouvert, montantAutoriseDecouvert);
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(String accountId) {
        if (accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
        } else {
            throw new IllegalArgumentException("Account not found with ID: " + accountId);
        }
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
