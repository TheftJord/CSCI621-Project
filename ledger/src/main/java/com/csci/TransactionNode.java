package com.csci;

public class TransactionNode {
    
    // variables
    private int accountNumber;
    private double balance; // balance for account after transaction
    private double transactionAmount; // transaction amount
    private String transactionStatement; // reason for transaction

    // -----------------------------------------------Setters----------------------------------------------------

    protected void setAccountNumber(int newId){
        accountNumber = newId;
    }
    
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

    protected int getAccountNumber(){
        return accountNumber;
    }
    
    protected double getBalance(){
        return balance;
    }

    protected double getTransactionAmount(){
        return transactionAmount;
    }

    protected String getTransactionStatement(){
        return transactionStatement;
    }

    // ------------------------------------------------Misc--------------------------------------------------------

    @Override
    public String toString(){
        return String.valueOf(accountNumber) + " | " + String.valueOf(transactionAmount) + " | " + transactionStatement + " | " + String.valueOf(balance);
    }

    protected boolean toCompare(TransactionNode other){

        if(this.accountNumber == other.accountNumber && this.transactionAmount == other.transactionAmount 
            && this.transactionStatement.compareTo(other.transactionStatement) == 0 && this.balance == other.balance){
                return true;
        } else {
            return false;
        }
    }
}
