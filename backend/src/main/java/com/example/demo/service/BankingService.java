package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.demo.model.Account;

@Service 
public class BankingService {
    
    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();

    private Long nextUserId = 1L; 

    public Account CreateAccount(String username){
        Account acc = new Account(nextUserId++,username,0L);
        accounts.put(acc.getId(), acc);
        return acc; 
    }

    // Login 
    public Account getAccount(Long userid) {
       return accounts.get(userid);
    }

    // To print userid and username of all accounts at landing page 
    public List<Account> getAllAccounts(){
        return new ArrayList<>(accounts.values());
    }

    public Boolean Deposit(Long userid, Long amount){

        if (amount <= 0) {
            return false;
        }
        Account account = accounts.get(userid);
        
        // if account doesnt exist return false
        if (account == null) {
            return false;
        }

        // prevent race conditon on account
        synchronized (account) {
            // if account exists, update balance
            Long original_balance = account.getBalance();

            account.setBalance(
                original_balance + amount
            );

            account.addTransaction(
                account.getUsername(),
                null,
                amount,
                original_balance,
                account.getBalance(),
                "DEPOSIT"
            );
        }

        return true;
    }

    public Boolean Withdraw(Long userid, Long amount){

        if (amount <= 0) {
            return false;
        }

        Account account = accounts.get(userid);

        // if account does not exist, return false 
        if (account == null) {
            return false;
        }

        // prevent race conditon on account
        synchronized (account) {

            // else update balance
            Long original_balance = account.getBalance();

            if (original_balance < amount) {
                return false;
            }

            account.setBalance(
                original_balance - amount
            );

            account.addTransaction(
                account.getUsername(),
                null,
                amount,
                original_balance,
                account.getBalance(),
                "WITHDRAW"
            );
        }
        return true; 
    }

    public String transfer(Long fromId, Long toId, Long amount) {

        if (amount <= 0) {
            return "Invalid amount";
        }

        if (fromId.equals(toId)) {
            return "Cannot transfer to yourself";
        }

        Account from_acc = accounts.get(fromId);
        Account to_acc = accounts.get(toId);

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
