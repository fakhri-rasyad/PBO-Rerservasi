package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class loginController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField passField;

    @FXML
    private Label loginError;

    @FXML
    private void loginButton() throws IOException {
        checkAkun();
        App.setRoot("mainPage");
    }

    private void checkAkun(){
            if(!MongoController.CheckPassword(emailField.getText(), passField.getText())){
                loginError.setText("Password salah!");
            }
    }
}
