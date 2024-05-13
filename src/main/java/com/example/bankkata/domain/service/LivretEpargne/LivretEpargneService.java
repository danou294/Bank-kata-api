package com.example.bankkata.domain.service.LivretEpargne;

public interface LivretEpargneService {
    void deposit(String accountNumber, double amount);
    double getBalance(String accountNumber);
}
