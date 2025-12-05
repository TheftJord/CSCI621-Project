package com.csci;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class LedgerProcesses {

    // variables
    private File selectedFile;
    static LinkedList<TransactionNode> transactionList = new LinkedList<>();

    // GSON variables needed
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    FileReader fr = null;
    
 // -------------------------------------------------List Functions-----------------------------------------------------

    

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

    protected void addToList(int accountNumber, double amount, String reason, double balance){

        transactionList.add(makeNode(accountNumber, amount, reason, balance));
    }

    protected int getLocation(TransactionNode target){

        int targetLocation = -1;

        for(int i = 0; i < transactionList.size(); i++){
            if(transactionList.get(i).toCompare(target) == true){
                targetLocation = i;
                break;
            }
        }

        return targetLocation;
    }

    protected void updateAndRemove(TransactionNode removedNode){

        int location = getLocation(removedNode);
        double holderBalance = 0;
        int account = removedNode.getAccountNumber();
        boolean first = true;

        if(location == -1){
            System.out.println("An Error has Occured - updateAndRemove()");
            return;
        }

        transactionList.remove(location);

        for(int i = 0; i <= transactionList.size()-1; i++){
            if(first && account == transactionList.get(i).getAccountNumber()){
                transactionList.get(i).setBalance(transactionList.get(i).getTransactionAmount());
                holderBalance = transactionList.get(i).getBalance();
                first = false;
            }
            else if(account == transactionList.get(i).getAccountNumber()){
                transactionList.get(i).setBalance(holderBalance + transactionList.get(i).getTransactionAmount());
                holderBalance = transactionList.get(i).getBalance();
            }
        }
    }

    protected void updateList(TransactionNode changedNode){

        double holderBalance = 0;
        int account = changedNode.getAccountNumber();
        boolean first = true;

        for(int i = 0; i <= transactionList.size()-1; i++){
            if(first && account == transactionList.get(i).getAccountNumber()){
                transactionList.get(i).setBalance(transactionList.get(i).getTransactionAmount());
                holderBalance = transactionList.get(i).getBalance();
                first = false;
            }
            else if(account == transactionList.get(i).getAccountNumber()){
                transactionList.get(i).setBalance(holderBalance + transactionList.get(i).getTransactionAmount());
                holderBalance = transactionList.get(i).getBalance();
            }
        }
    }

 // -------------------------------------------------------File---------------------------------------------------------

    public void setFile(File setFile){
        this.selectedFile = setFile;
    }

    public void convertJsonToList(){
        if(selectedFile != null){
            LinkedList<TransactionNode> listData = new LinkedList<>();

            transactionList.clear();

            try{
                fr = new FileReader(selectedFile.getName());
            } catch(FileNotFoundException ex){
                System.out.println("Error found in convertJsonToList");
            }

            listData = gson.fromJson(fr, new TypeToken<LinkedList<TransactionNode>>(){}.getType());

            for(int i = 0; i < listData.size(); i++){
                addToList(listData.get(i).getAccountNumber(), listData.get(i).getTransactionAmount(), listData.get(i).getTransactionStatement(), listData.get(i).getBalance());
            }
        } else {
            System.out.println("Error found in convertJsonToList");
        }
    }

    public void saveToJson(){
        builder.setPrettyPrinting();

        if(selectedFile != null){
            String jsonString = gson.toJson(transactionList);
            PrintStream ps;

            try{
                ps = new PrintStream(selectedFile.getPath());
                ps.println(jsonString);
            } catch(FileNotFoundException ex){
                System.out.println("An Error has occured in saveToJson");
            }
        } else {
            System.out.println("An Error has occured in saveToJson");
        }
    }

 // ----------------------------------------------------Utilities-------------------------------------------------------

    protected static TransactionNode makeNode(int accountNumber, double amount, String TransactionStatement){

        // variables
        TransactionNode accountUpdated = new TransactionNode();
        int tempHolder = -1;

        // update values
        accountUpdated.setAccountNumber(accountNumber);
        accountUpdated.setTransactionAmount(amount);
        accountUpdated.setTransactionStatement(TransactionStatement);
        for(int i = transactionList.size() -1 ; i >= 0; i--){
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

    protected static TransactionNode makeNode(int accountNumber, double amount, String reason, double Balance){
        // variables
        TransactionNode accountUpdated = new TransactionNode();

        // update values
        accountUpdated.setAccountNumber(accountNumber);
        accountUpdated.setTransactionAmount(amount);
        accountUpdated.setTransactionStatement(reason);
        accountUpdated.setBalance(Balance);

        // return value
        return accountUpdated;
    }

    
}
