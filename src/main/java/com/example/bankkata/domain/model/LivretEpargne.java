package com.example.bankkata.domain.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class LivretEpargne extends Account {

    private double depositCeiling;

    public LivretEpargne() {
        super();
    }

    public LivretEpargne(double initialBalance, double depositCeiling) {
        super(initialBalance, false, 0.0); // Pas de découvert autorisé
        this.depositCeiling = depositCeiling;
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        if (this.getBalance() + amount > depositCeiling) {
            throw new IllegalArgumentException("Deposit exceeds the account ceiling.");
        }
        super.deposit(amount);
    }
}
