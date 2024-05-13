package com.example.bankkata.domain.model;

import com.example.bankkata.domain.exceptions.InsufficientFundsException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Account {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private double balance;

    private boolean autorisationDecouvert;

    private double montantAutoriseDecouvert;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Operation> operations = new ArrayList<>();

    public Account(double balance, boolean autorisationDecouvert, double montantAutoriseDecouvert) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
        this.autorisationDecouvert = autorisationDecouvert;
        this.montantAutoriseDecouvert = montantAutoriseDecouvert;
        this.accountId = generateAccountId();
    }

    private Long generateAccountId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount;
        operations.add(new Operation(LocalDateTime.now(), "DEPOSIT", amount, balance));
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (balance >= amount) {
            balance -= amount;
        } else {
            if (autorisationDecouvert) {
                double montantTotalDisponible = balance + montantAutoriseDecouvert;
                if (montantTotalDisponible >= amount) {
                    balance -= amount;
                } else {
                    throw new InsufficientFundsException("Your overdraft does not allow this withdrawal");
                }
            } else {
                throw new InsufficientFundsException("Insufficient funds for withdrawal");
            }
        }
        operations.add(new Operation(LocalDateTime.now(), "WITHDRAW", amount, balance));
    }

    public List<Operation> getOperations() {
        return operations;
    }
}
