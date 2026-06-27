package com.bank;

public class Account {

    private String accountHolder;
    private double balance;

    public Account(String accountHolder, double balance) {
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isOverdrawn() {
        return balance < 0;
    }

    // Returns null if no account holder name was ever set (kept simple for assertNull/assertNotNull demo)
    public String getNickname(String nickname) {
        return nickname;
    }
}
