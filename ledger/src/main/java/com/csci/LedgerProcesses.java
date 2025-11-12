package com.csci;

public class LedgerProcesses {
    


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

    protected TransactionNode addTransaction(double amount, String TransactionStatement){

        // variables
        TransactionNode accountUpdated = new TransactionNode();

        // update values
        accountUpdated.setTransactionAmount(amount);
        accountUpdated.setTransactionStatement(TransactionStatement);
        accountUpdated.setBalance(accountUpdated.getBalance()+amount);

        // return value
        return accountUpdated;
    }
}
