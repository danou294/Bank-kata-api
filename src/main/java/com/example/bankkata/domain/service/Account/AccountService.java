package com.example.bankkata.domain.service.Account;

import com.example.bankkata.domain.exceptions.InsufficientFundsException;
import com.example.bankkata.domain.model.Account;

public interface AccountService {

    Account createAccount();

    void deleteAccount(String accountId);

    Account deposit(String accountId, double amount);

    Account withdraw(String accountId, double amount) throws InsufficientFundsException;

    Iterable<Account> getAllAccounts(); // Ajout de la méthode getAllAccounts()

    double getAccountBalance(String accountId); // Ajout de la méthode getAccountBalance


}
