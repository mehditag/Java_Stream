package com.mtaguema.streams;

public class BankAccount {
    private BankAccountType type;
    private String holder;
    private double balance;
    public BankAccount(String holder, double balance,BankAccountType type) {
        this.holder = holder;
        this.balance = balance;
        this.type=type;
    }
    public double getBalance() {
        return balance;
    }
    public String getHolder() {
        return holder;
    }
    public void setHolder(String holder) {
        this.holder = holder;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public BankAccountType getType() {
        return type;
    }
    public void setType(BankAccountType type) {
        this.type = type;
    }

    

    


    
    
}
