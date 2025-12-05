package com.csci;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class PrimaryController {

    // ListStorage
    LedgerProcesses primary = new LedgerProcesses();

    TransactionNode selectedNode = new TransactionNode();

    // File Interface Variables
    FileChooser fileChooser = new FileChooser();
    File current = null;
    File selectedFile;
    FileReader fr;
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");

    // FXML variables
    @FXML
    private TextField textBoxAccountNumber;
    @FXML
    private TextField textBoxAmount;
    @FXML
    private TextField textBoxReason;
    @FXML
    private TextField textBoxAccountNumberEdit;
    @FXML
    private TextField textBoxAmountEdit;
    @FXML
    private TextField textBoxReasonEdit;
    @FXML
    private TextField textBoxBalanceEdit;
    @FXML
    private TextField textBoxOverviewRemove;
    @FXML
    private ListView<TransactionNode> display;

    // -----------------------------------------------------------------Initializer------------------------------------------------------------------------

    public void initialize(){

        // set up file explorer
        fileChooser.getExtensionFilters().add(extFilter);

        try{
            current = new File(new File(".").getCanonicalPath());
        } catch(IOException ex){
            ex.printStackTrace();
        }

        fileChooser.setInitialDirectory(current);
        
        selectedFile = fileChooser.showOpenDialog(null);

        primary.setFile(selectedFile);

        textBoxBalanceEdit.setEditable(false);
        textBoxOverviewRemove.setEditable(false);

        primary.convertJsonToList();
        actionAddToList();
    }
    
    // ---------------------------------------------------------------Controller Methods-------------------------------------------------------------------
    
    /**
     * This the method to make and add a transaction to the list
     */
    @FXML
    private void addTransaction(){
    
        // variables
        int accountNumber = Integer.parseInt(retrieveTextField(textBoxAccountNumber));
        double amount = Double.parseDouble(retrieveTextField(textBoxAmount));
        String reason = retrieveTextField(textBoxReason);
        TransactionNode tempNode = new TransactionNode();

        // converts info into a node
        tempNode = LedgerProcesses.makeNode(accountNumber, amount, reason);

        // adds it to the linked list
        primary.transactionList.add(tempNode);

        // prints it on list view
        actionAddToList();
    }

    @FXML
    private void editTransaction(){
        
        int location = primary.getLocation(selectedNode);
        boolean changeHappened = false;

        if(location == -1){
            System.out.println("An Error Has Occured - editTransaction()");
            return;
        }

        int accountNumber = Integer.parseInt(retrieveTextField(textBoxAccountNumberEdit));
        double amount = Double.parseDouble(retrieveTextField(textBoxAmountEdit));
        String reason = retrieveTextField(textBoxReasonEdit);

        if(accountNumber != selectedNode.getAccountNumber()){
            selectedNode.setAccountNumber(accountNumber);
        }
        if(amount != selectedNode.getTransactionAmount()){
            selectedNode.setTransactionAmount(amount);
            changeHappened = true;
        }
        if(reason.compareTo(selectedNode.getTransactionStatement()) != 0 && reason.isBlank() == false){
            selectedNode.setTransactionStatement(reason);
        }
        if(changeHappened){
            for(int i = location-1; i > 0; i--){
                if(accountNumber == primary.transactionList.get(i).getAccountNumber()){
                    selectedNode.setBalance(primary.transactionList.get(i).getBalance() + amount);
                    break;
                }
            }
        }

        clearThatOneBox();
        theOtherBox();

        primary.updateList(selectedNode);

        actionAddToList();
    }

    @FXML
    private void removeItem(){

        int location = primary.getLocation(selectedNode);

        if(location == -1){
            System.out.println("An Error has Occured - removeItem()");
            return;
        }

        primary.updateAndRemove(selectedNode);

        clearThatOneBox();

        actionAddToList();
    }

    @FXML
    private void actionSave(){
        selectedFile = fileChooser.showSaveDialog(null);
        primary.setFile(selectedFile);

        primary.saveToJson();
    }

    @FXML
    private void actionLoad(){
        selectedFile = fileChooser.showOpenDialog(null);
        primary.setFile(selectedFile);

        primary.convertJsonToList();

        actionAddToList();
    }

    @FXML
    private void closeApplication(){
        actionSave();

        Platform.exit();

        System.exit(0);
    }
    
    // ---------------------------------------------------------------------GUI----------------------------------------------------------------------------

    @FXML
    private void actionAddToList(){
        display.getItems().clear();

        display.getItems().addAll(primary.transactionList);
    }

    @FXML
    private void clearSections(){
        textBoxAccountNumber.clear();
        textBoxAmount.clear();
        textBoxReason.clear();
    }

    @FXML
    private void actionSelectNode(){
        selectedNode = display.getSelectionModel().getSelectedItem();

        textBoxAccountNumberEdit.setText(String.valueOf(selectedNode.getAccountNumber()));
        textBoxAmountEdit.setText(String.valueOf(selectedNode.getTransactionAmount()));
        textBoxReasonEdit.setText(selectedNode.getTransactionStatement());

        textBoxBalanceEdit.setEditable(true);
        textBoxBalanceEdit.setText(String.valueOf(selectedNode.getBalance()));
        textBoxBalanceEdit.setEditable(false);
        
        textBoxOverviewRemove.setEditable(true);
        textBoxOverviewRemove.setText(selectedNode.toString());
        textBoxOverviewRemove.setEditable(false);
    }

    // ---------------------------------------------------------------------Misc---------------------------------------------------------------------------

    /**
     * This is how you get the values out of the text field for use
     * @return
     */
    @FXML
    private String retrieveTextField(TextField tempTextField){

        // return value
        String returnValue = null;

        // sets return value to what is in the text area
        if(tempTextField.getText().isBlank() == false){
            returnValue = tempTextField.getText();
        } else {
            returnValue = "-001";
        }

        // clears the TextField
        tempTextField.clear();

        // returns value
        return returnValue;
    }

    @FXML
    private void clearThatOneBox(){
        textBoxBalanceEdit.setEditable(true);
        textBoxBalanceEdit.clear();
        textBoxBalanceEdit.setEditable(false);
    }

    @FXML
    private void theOtherBox(){
        textBoxOverviewRemove.setEditable(true);
        textBoxOverviewRemove.setText(selectedNode.toString());
        textBoxOverviewRemove.setEditable(false);
    }
}
