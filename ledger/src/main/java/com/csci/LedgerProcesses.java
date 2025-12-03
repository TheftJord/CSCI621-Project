package com.csci;

import java.util.LinkedList;

public class LedgerProcesses {
    
 // -------------------------------------------------List Functions-----------------------------------------------------

    static LinkedList<TransactionNode> transactionList = new LinkedList<>();

    protected LinkedList<String> toPrint(){

        // return value
        LinkedList<String> returnValue = new LinkedList<>();

        // puts all the values to a printable list
        for(int i = 0; i < transactionList.size(); i++){
            // making the node into a string
            String temp = "Account " + transactionList.get(i).getAccountNumber() + " | " + transactionList.get(i).getTransactionAmount() + " | " 
            + transactionList.get(i).getTransactionStatement() + " | " + transactionList.get(i).getBalance();

            // Adds to the return value
            returnValue.add(temp);
        }

        // return
        return returnValue;
    }

 // ----------------------------------------------------Utilities-------------------------------------------------------

    protected void editTransaction(double newAmount, String transactionStatement, TransactionNode oldTransaction){

        // variables
        boolean changeHasBeenMade = false;

        // transaction changing
        if(oldTransaction.getTransactionAmount() != newAmount && newAmount != 0){ // changes transaction amount
            oldTransaction.setTransactionAmount(newAmount);
            changeHasBeenMade = true;
        }
        if(oldTransaction.getTransactionStatement() != transactionStatement && !transactionStatement.isEmpty()){ // changes statement
            oldTransaction.setTransactionStatement(transactionStatement);
        }
        if(changeHasBeenMade){ // updates balance if transaction amount has changed
            oldTransaction.setBalance(oldTransaction.getBalance() - newAmount);
        }
    }

    protected static TransactionNode makeNode(int accountNumber, double amount, String TransactionStatement){

        // variables
        TransactionNode accountUpdated = new TransactionNode();
        int tempHolder = -1;

        // update values
        accountUpdated.setAccountNumber(accountNumber);
        accountUpdated.setTransactionAmount(amount);
        accountUpdated.setTransactionStatement(TransactionStatement);
        for(int i = transactionList.size() - 1 ; i > 0; i--){
            if(transactionList.get(i).getAccountNumber() == accountNumber){
                tempHolder = i;
                break;
            }
        }

        if(tempHolder == -1){
            accountUpdated.setBalance(amount);
        }
        else{
            accountUpdated.setBalance(transactionList.get(tempHolder).getBalance() + amount);
        }

        // return value
        return accountUpdated;
    }
}
