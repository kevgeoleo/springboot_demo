package com.example.demo.model;

import java.time.LocalDateTime;

public class Transaction {
    
    private String from;
    private String to; 
    private Long amount;
    private Long originalBalance;
    private Long newBalance;
    private String transactionType; 
    private LocalDateTime timestamp;

    public Transaction(String from, String to, Long amount, Long original_balance, Long new_balance, String transactionType){
        this.from = from; 
        this.to = to; 
        this.amount = amount;
        this.originalBalance = original_balance;
        this.newBalance = new_balance;
        this.transactionType = transactionType;
        this.timestamp = LocalDateTime.now();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Long getAmount() {
        return amount;
    }

    public Long getOriginalBalance() {
        return originalBalance;
    }

    public Long getNewBalance() {
        return newBalance;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
