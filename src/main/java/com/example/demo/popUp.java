package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class popUp {

    public static void Display(String events){
        Stage purchasePopUp = new Stage();
        purchasePopUp.initModality(Modality.APPLICATION_MODAL);
        purchasePopUp.setTitle( events + " sukses");

        Label label1 = new Label( events + " sukses");

        Button button1= new Button("Kembali");

        button1.setOnAction(e -> purchasePopUp.close());

        VBox layout = new VBox(20);

        layout.getChildren().addAll(label1, button1);

        layout.setAlignment(Pos.CENTER);

        Scene popUp = new Scene(layout, 300, 250);

        purchasePopUp.setScene(popUp);

        purchasePopUp.showAndWait();
    }
}
