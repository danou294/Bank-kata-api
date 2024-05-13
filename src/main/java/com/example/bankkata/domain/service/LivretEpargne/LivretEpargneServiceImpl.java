package com.example.bankkata.domain.service.LivretEpargne;

import com.example.bankkata.domain.model.LivretEpargne;
import com.example.bankkata.domain.exceptions.AccountNotFoundException;
import com.example.bankkata.domain.port.LivretEpargneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LivretEpargneServiceImpl implements LivretEpargneService {

    private final LivretEpargneRepository livretEpargneRepository;

    @Autowired
    public LivretEpargneServiceImpl(LivretEpargneRepository livretEpargneRepository) {
        this.livretEpargneRepository = livretEpargneRepository;
    }

    @Override
    public void deposit(String accountNumber, double amount) {
        LivretEpargne livretEpargne = livretEpargneRepository.findByAccountId(Long.valueOf(accountNumber))
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        livretEpargne.deposit(amount);
        livretEpargneRepository.save(livretEpargne);
    }

    @Override
    public double getBalance(String accountNumber) {
        LivretEpargne livretEpargne = livretEpargneRepository.findByAccountId(Long.valueOf(accountNumber))
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return livretEpargne.getBalance();
    }
}
