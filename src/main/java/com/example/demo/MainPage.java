package com.example.demo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.bson.Document;

import java.io.IOException;

public class MainPage {
    @FXML
    private VBox listTiket;

    @FXML
    private ChoiceBox<String> dariComboBox;

    @FXML
    private ChoiceBox<String> menujuComboBox;



    @FXML
    public void tampilkanDaftarPesawat(){

        MongoCollection<Document> daftarPesawat = MongoController.MongoConnect("Pesawat");
        MongoCollection<Document> provinsi = MongoController.MongoConnect("Provinsi");
        listTiket.setSpacing(20);

        listTiket.getChildren().clear();

        String tujuan = menujuComboBox.getValue();
        String berangkat = dariComboBox.getValue();

        provinsi.find().forEach(document ->
        {
            dariComboBox.getItems().add(String.valueOf(document.get("Provinsi")));
            menujuComboBox.getItems().add(String.valueOf(document.get("Provinsi")));
        });

        BasicDBObject kriteria = new BasicDBObject();
        kriteria.append("Dari", berangkat);
        kriteria.append("Menuju", tujuan);

        daftarPesawat.find(kriteria).forEach(doc -> {
            Tiket tiketBaru = new Tiket();
            tiketBaru.setMaskapai(String.valueOf(doc.get("Maskapai")));
            tiketBaru.setDari(String.valueOf(doc.get("Dari")));
            tiketBaru.setMenuju(String.valueOf(doc.get("Menuju")));
            tiketBaru.setHarga(String.valueOf(doc.get("Harga")));

            GridPane tiketPesawat = new GridPane();

            Label namaMaskapai = new Label(String.valueOf(doc.get("Maskapai")));
            namaMaskapai.setFont(Font.font("Arial", 14));
            Label hargaMaskapai = new Label(String.valueOf(doc.get("Dari")));
            hargaMaskapai.setFont(Font.font("Arial", 14));
            Label fiturMaskapai = new Label(String.valueOf(doc.get("Menuju")));
            fiturMaskapai.setFont(Font.font("Arial", 14));

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
            tiketPesawat.setOnMouseClicked(e ->{
                MongoCollection<Document> daftarTiket = MongoController.MongoConnect("Tiket");
                Document newTiket = new Document();
                newTiket
                        .append("id", 10)
                        .append("Nama Maskapai", tiketBaru.getMaskapai())
                        .append("Dari", tiketBaru.getDari())
                        .append("Menuju", tiketBaru.getMenuju())
                        .append("Harga", "$" + tiketBaru.getHarga());
                daftarTiket.insertOne(newTiket);
                System.out.println(newTiket);
                System.out.println("Ditambahkan");
            });
        });
    }

    @FXML
    public void tampilkanTiket(){
        String asal = dariComboBox.getValue();
        String tujuan = menujuComboBox.getValue();
    }

    @FXML
    public void menujuAkunUser(){
        try {
            App.setRoot("Pengguna");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
