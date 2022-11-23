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
    private void loginButtonToRegister() throws IOException {
        App.setRoot("registPage");
    }

    @FXML
    private void loginButton() throws IOException {
            StringBuilder msg = new StringBuilder();

            if(emailField.getText().equals("") & passField.getText().equals("")){
                msg.append("Email atau password tidak boleh Kosong\n");
            } else if(MongoController.CheckPassword(emailField.getText(), passField.getText())){
                msg.append("Email atau Password salah!\n");
            }

            loginError.setText(msg.toString());

            if(msg.length() == 0){
                MainPage.userEmail = emailField.getText();
                App.setRoot("mainPage");
            }
    }
}
