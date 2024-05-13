package com.example.bankkata.domain.adapter;


import com.example.bankkata.domain.model.Operation;
import com.example.bankkata.domain.service.Account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountNumber}/deposit")
    public void deposit(@PathVariable String accountNumber, @RequestBody double amount) {
        accountService.deposit(accountNumber, amount);
    }

    @PostMapping("/{accountNumber}/withdraw")
    public void withdraw(@PathVariable String accountNumber, @RequestBody double amount) {
        accountService.withdraw(accountNumber, amount);
    }

    @GetMapping("/{accountNumber}/balance")
    public double getBalance(@PathVariable String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    @GetMapping("/{accountNumber}/statement")
    public List<Operation> getMonthlyStatement(@PathVariable String accountNumber) {
        return accountService.getMonthlyStatement(accountNumber);
    }
}
