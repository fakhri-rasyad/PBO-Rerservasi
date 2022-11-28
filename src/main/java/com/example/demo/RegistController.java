package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class RegistController {


    @FXML
    private Label registLabel;
    @FXML
    private TextField frontNameFieldRegist;

    @FXML
    private TextField lastNameFieldRegist;

    @FXML
    private TextField emailFieldRegist;

    @FXML
    private TextField passFieldRegist;

    @FXML
     public void registButton() throws  IOException{
        String firstName = frontNameFieldRegist.getText();
        String lastName =  lastNameFieldRegist.getText();
        String email = emailFieldRegist.getText();
        String password = passFieldRegist.getText();


        List<String> Error = MongoController.MongoInsertDocument(firstName, lastName, email, password);

        if(Error.size() > 0){
            for (String o : Error) {
                registLabel.setText(o);
            }
        }else{
            App.setRoot("loginPage");
        }
    }




}