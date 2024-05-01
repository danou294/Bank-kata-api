package com.example.bankkata.domain.adapter;

import com.example.bankkata.domain.exceptions.InsufficientFundsException;
import com.example.bankkata.domain.model.Account;
import com.example.bankkata.domain.service.Account.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllAccounts() {
        try {
            List<Account> accounts = (List<Account>) accountService.getAllAccounts();
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving accounts");
        }
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Object> getAccountBalance(@PathVariable String accountId) {
        try {
            double balance = accountService.getAccountBalance(accountId);
            return ResponseEntity.ok(balance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving account balance");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAccount(@RequestParam double initialBalance, @RequestParam boolean autorisationDecouvert, @RequestParam double montantAutoriseDecouvert) {
        try {
            Account account = accountService.createAccount(initialBalance, autorisationDecouvert, montantAutoriseDecouvert);
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Object> deposit(@PathVariable String accountId, @RequestParam double amount) {
        try {
            Account account = accountService.deposit(accountId, amount);
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<Object> withdraw(@PathVariable String accountId, @RequestParam double amount) {
        try {
            Account account = accountService.withdraw(accountId, amount);
            return ResponseEntity.ok(account);
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
