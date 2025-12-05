package com.csci;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class PrimaryController {

    // ListStorage
    LedgerProcesses primary = new LedgerProcesses();

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
    @SuppressWarnings("rawtypes")
    @FXML
    private ListView display;

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
    }
    
    // ---------------------------------------------------------------Controller Methods-------------------------------------------------------------------
    
    /**
     * This the method to make and add a transaction to the list
     */
    @FXML
    private void addTransaction(){
    
        // variables
        int accountNumber = Integer.parseInt(retrieveTextField(textBoxAccountNumber));
        int amount = Integer.parseInt(retrieveTextField(textBoxAmount));
        String reason = retrieveTextField(textBoxReason);
        TransactionNode tempNode = new TransactionNode();

        // converts info into a node
        tempNode = LedgerProcesses.makeNode(accountNumber, amount, reason);

        // adds it to the linked list
        primary.transactionList.add(tempNode);

        for(int i = 0; i < primary.toPrint().size(); i++){
            System.out.println(primary.toPrint().get(i));
        }
        System.out.println("-------------------------------------------------------");
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
        returnValue = tempTextField.getText();

        // clears the TextField
        tempTextField.clear();

        // returns value
        return returnValue;
    }

}
