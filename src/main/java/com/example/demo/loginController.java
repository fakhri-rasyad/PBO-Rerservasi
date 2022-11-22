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

    @FXML
    private void loginButtonToRegister() throws IOException {
        App.setRoot("registPage");
    }

    private void checkAkun(){
            StringBuilder msg = new StringBuilder();

            if(emailField.getText() == null & passField.getText()==null){
                msg.append("Email atau password tidak boleh salah\n");

            }
            if(!MongoController.CheckPassword(emailField.getText(), passField.getText())){
                loginError.setText("Email atau Password salah!\n");
            }

            loginError.setText(msg.toString());
    }
}
