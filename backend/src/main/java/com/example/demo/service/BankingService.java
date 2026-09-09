package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Account;

@Service 
public class BankingService {
    
    private final List<Account> accounts = new ArrayList<>();
    
    private Long nextUserId = 1L; 

    public Account CreateAccount(String username){
        Account acc = new Account(nextUserId++,username,0L);
        accounts.add(acc);
        return acc; 
    }

    // Login 
    public Account getAccount(Long userid) {

        for (Account account : accounts) {

            if (account.getId().equals(userid)) {
                return account;
            }
        }

        return null;
    }

    // To print userid and username of all accounts at landing page 
    public List<Account> getAllAccounts(){
        return accounts;
    }

    public Boolean Deposit(Long userid, Long amount){

        if (amount <= 0) {
            return false;
        }
        
        for (Account account : accounts) {
            if (account.getId().equals(userid)) {

                Long original_balance = account.getBalance();
                account.setBalance(
                    original_balance + amount
                );

                account.addTransaction(account.getUsername(), null, amount, original_balance, account.getBalance(),"DEPOSIT");

                return true;
            }
        } 
        return false;
    }

    public Boolean Withdraw(Long userid, Long amount){

        if (amount <= 0) {
            return false;
        }

        for (Account account : accounts) {
            if (account.getId().equals(userid)) {

                Long original_balance = account.getBalance();
                if(original_balance >= amount){

                    account.setBalance(
                        original_balance - amount
                    );
                    account.addTransaction(account.getUsername(), null, amount, original_balance, account.getBalance(),"WITHDRAW");
                    return true;
                }
                return false; 
                
            }
        }
        return false; 
    }

    public String transfer(Long fromId, Long toId, Long amount) {

        if (amount <= 0) {
            return "Invalid amount";
        }

        if (fromId.equals(toId)) {
            return "Cannot transfer to yourself";
        }

        Account from_acc = null;
        Account to_acc = null;

        for (Account account : accounts) {

            if (account.getId().equals(fromId)) {
                from_acc = account;
            }

            if (account.getId().equals(toId)) {
                to_acc = account;
            }
        }

        if (from_acc == null || to_acc == null) {
            return "Provide proper id";
        }

        if (from_acc.getBalance() < amount) {
            return "Insufficient funds";
        }

        Long original_balance_from = from_acc.getBalance();
        Long original_balance_to = to_acc.getBalance();

        from_acc.setBalance(original_balance_from - amount);
        to_acc.setBalance(original_balance_to + amount);

        from_acc.addTransaction(
            from_acc.getUsername(),
            to_acc.getUsername(),
            amount,
            original_balance_from,
            original_balance_from - amount,
            "TRANSFER"
        );

        to_acc.addTransaction(
            from_acc.getUsername(),
            to_acc.getUsername(),
            amount,
            original_balance_to,
            original_balance_to + amount,
            "TRANSFER"
        );

        return "Funds transferred";
    }

}
