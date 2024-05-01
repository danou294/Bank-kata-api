package com.example.bankkata.domain.model;

import com.example.bankkata.domain.exceptions.InsufficientFundsException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Account {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private double balance;

    private boolean autorisationDecouvert;

    private double montantAutoriseDecouvert;

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
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        System.out.println("Current balance: " + balance);
        System.out.println("Requested amount: " + amount);

        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance");

            if (autorisationDecouvert) {
                double montantTotalDisponible = balance + montantAutoriseDecouvert;
                System.out.println("Total available amount (balance + authorized overdraft): " + montantTotalDisponible);

                if (montantTotalDisponible >= amount) {
                    balance -= amount;
                } else {
                    throw new InsufficientFundsException("Your overdraft does not allow this withdrawal");
                }
            } else {
                throw new InsufficientFundsException("Insufficient funds for withdrawal");
            }
        }
    }
}
