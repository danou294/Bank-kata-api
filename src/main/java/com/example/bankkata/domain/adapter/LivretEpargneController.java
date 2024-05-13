package com.example.bankkata.domain.adapter;

import com.example.bankkata.domain.service.LivretEpargne.LivretEpargneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livret-epargne")
public class LivretEpargneController {

    private final LivretEpargneService livretEpargneService;

    @Autowired
    public LivretEpargneController(LivretEpargneService livretEpargneService) {
        this.livretEpargneService = livretEpargneService;
    }

    @PostMapping("/{accountNumber}/deposit")
    public void deposit(@PathVariable String accountNumber, @RequestBody double amount) {
        livretEpargneService.deposit(accountNumber, amount);
    }

    @GetMapping("/{accountNumber}/balance")
    public double getBalance(@PathVariable String accountNumber) {
        return livretEpargneService.getBalance(accountNumber);
    }
}
