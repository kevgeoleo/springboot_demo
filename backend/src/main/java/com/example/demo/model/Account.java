package com.example.demo.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class Account {
    private Long userid;
    private String username; 
    private Long balance; 
    private final Deque<Transaction> transactionHistory = new ArrayDeque<>();

    public Account() {
    }

    public Account(Long userid, String username, Long balance){
        this.userid = userid;
        this.username = username;
        this.balance = balance; 
    }

    public Long getId(){
        return userid;
    }

    public String getUsername(){
        return username;
    }

    public Long getBalance(){
        return balance;
    }

    public void setBalance(Long amount){
        this.balance = amount;
    }

    public Deque<Transaction> getTransactionHistory(){
        return transactionHistory; 
    }

    public void addTransaction(String from, String to,Long amount, Long original_balance, Long new_balance, String transactionType){
        Transaction trans = new Transaction(from,to,amount,original_balance,new_balance,transactionType);

        if (transactionHistory.size() >= 50) {
            transactionHistory.removeFirst();
        }
        transactionHistory.addLast(trans);
    }

}
