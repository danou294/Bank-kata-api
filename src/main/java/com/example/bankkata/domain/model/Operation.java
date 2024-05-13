package com.example.bankkata.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String type;
    private double amount;
    private double balanceAfterOperation;

    public Operation(LocalDateTime date, String type, double amount, double balanceAfterOperation) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.balanceAfterOperation = balanceAfterOperation;
    }
}
