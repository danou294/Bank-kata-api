package com.example.bankkata.domain.service.Account;

import com.example.bankkata.domain.model.Operation;

import java.util.List;

public interface AccountService {
    void deposit(String accountNumber, double amount);
    void withdraw(String accountNumber, double amount);
    double getBalance(String accountNumber);
    List<Operation> getMonthlyStatement(String accountNumber);
}
