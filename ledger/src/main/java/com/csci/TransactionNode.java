package com.csci;

public class TransactionNode {
    
    // variables
    private double balance; // balance for account after transaction
    private double transactionAmount; // transaction amount
    private String transactionStatement; // reason for transaction

    // -----------------------------------------------Setters----------------------------------------------------

    protected void setBalance(double newBalance){
        balance = newBalance;
    }

    protected void setTransactionAmount(double amount){
        transactionAmount = amount;
    }

    protected void setTransactionStatement(String reason){
        transactionStatement = reason;
    }

    // -----------------------------------------------Getters-----------------------------------------------------

    protected double getBalance(){
        return balance;
    }

    protected double getTransactionAmount(){
        return transactionAmount;
    }

    protected String getTransactionStatement(){
        return transactionStatement;
    }
}
