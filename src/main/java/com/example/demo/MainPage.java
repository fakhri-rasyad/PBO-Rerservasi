package com.example.demo;

import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.bson.Document;


public class MainPage {
    @FXML
    private VBox listTiket;

    @FXML
    private ChoiceBox<String> dariComboBox;

    @FXML
    private ChoiceBox<String> menujuComboBox;

    @FXML
    public void tampilkanDaftarPesawat(){

        MongoCollection<Document> daftarPesawat = MongoController.MongoConnect("pesawat");
        MongoCollection<Document> provinsi = MongoController.MongoConnect("Provinsi");
        listTiket.setSpacing(20);

        dariComboBox.setValue("Jakarta");
        menujuComboBox.setValue("Jakarta");

        provinsi.find().forEach(document ->
        {
            dariComboBox.getItems().add(String.valueOf(document.get("Provinsi")));
            menujuComboBox.getItems().add(String.valueOf(document.get("Provinsi")));
        });

        daftarPesawat.find().limit(5).forEach(doc -> {
            GridPane tiketPesawat = new GridPane();

            Label namaMaskapai = new Label("Maskapai");
            namaMaskapai.setFont(Font.font("Arial", 18));
            Label hargaMaskapai = new Label("Harga");
            hargaMaskapai.setFont(Font.font("Arial", 18));
            Label fiturMaskapai = new Label("Fitur Maskapai");
            fiturMaskapai.setFont(Font.font("Arial", 18));

            tiketPesawat.add(namaMaskapai,1, 1);
            tiketPesawat.add(hargaMaskapai, 2, 1);
            tiketPesawat.add(fiturMaskapai, 3, 1);

            tiketPesawat.setMinWidth(720);
            tiketPesawat.setMinHeight(90);
            tiketPesawat.setAlignment(Pos.CENTER_LEFT);

            tiketPesawat.getColumnConstraints().add(new ColumnConstraints(100));
            tiketPesawat.getColumnConstraints().add(new ColumnConstraints(200));
            tiketPesawat.getColumnConstraints().add(new ColumnConstraints(150));

            tiketPesawat.setStyle("-fx-background-color: orange;");
            listTiket.getChildren().add(tiketPesawat);
        });
    }

    public static void main(String[] args) {
        System.out.println();
        MongoCollection<Document> daftarPesawat = MongoController.MongoConnect("pesawat");

        daftarPesawat.find().forEach(document ->
        {
            System.out.println(document.get("Maskapai"));
            System.out.println(document.get("Dari"));
            System.out.println(document.get("Menuju"));
            System.out.println(document.get("Harga"));
            System.out.println(document.get("Maskapai"));
        });
    }
}
