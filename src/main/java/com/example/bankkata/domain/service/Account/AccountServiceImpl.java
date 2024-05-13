package com.example.bankkata.domain.service.Account;

import com.example.bankkata.domain.model.Account;
import com.example.bankkata.domain.model.Operation;
import com.example.bankkata.domain.exceptions.AccountNotFoundException;
import com.example.bankkata.domain.port.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void deposit(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountId(Long.valueOf(accountNumber))
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        account.deposit(amount);
        accountRepository.save(account);
    }

    @Override
    public void withdraw(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountId(Long.valueOf(accountNumber))
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        account.withdraw(amount);
        accountRepository.save(account);
    }

    @Override
    public double getBalance(String accountNumber) {
        Account account = accountRepository.findByAccountId(Long.valueOf(accountNumber))
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return account.getBalance();
    }

    @Override
    public List<Operation> getMonthlyStatement(String accountNumber) {
        Account account = accountRepository.findByAccountId(Long.valueOf(accountNumber))
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        return account.getOperations().stream()
                .filter(op -> op.getDate().isAfter(oneMonthAgo))
                .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .collect(Collectors.toList());
    }
}
