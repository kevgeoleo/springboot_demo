package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.service.BankingService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class BankController {

    private final BankingService bankingService;

    public BankController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    // to print all accounts on landing page 
    @GetMapping("/accounts")
    public List<Account> getAccounts() {
        return bankingService.getAllAccounts();
    }

    // create new account 
    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
        return bankingService.CreateAccount(account.getUsername());
    }

    // login 
    @GetMapping("/login/{id}")
    public ResponseEntity<Account> login(@PathVariable Long id) {

        // store account into Account object
        Account account = bankingService.getAccount(id);

        if (account == null) {

            // 404 not found
            return ResponseEntity.notFound().build();
        }

        // if account is found, return 200OK with account as body
        return ResponseEntity.ok(account);
    }

    //deposit
    @PutMapping("/{id}/deposit")
    public Boolean deposit(
            @PathVariable Long id,
            @RequestParam Long amount) {

        return bankingService.Deposit(id, amount);
    }

    // withdraw
    @PutMapping("/{id}/withdraw")
    public Boolean withdraw(
            @PathVariable Long id,
            @RequestParam Long amount) {

        return bankingService.Withdraw(id, amount);
    }

    // transfer 
    @PutMapping("/transfer")
    public String transfer(
            @RequestParam Long fromId,
            @RequestParam Long toId,
            @RequestParam Long amount) {

        return bankingService.transfer(fromId, toId, amount);
    }
}