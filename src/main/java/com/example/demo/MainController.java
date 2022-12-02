package com.example.demo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.bson.Document;

import java.io.IOException;
import java.util.Random;


public class MainController {

    public static String userEmail;

    public static String userName;
    @FXML
    private VBox listTiket;

    @FXML
    private ChoiceBox<String> dariChoiceBox;

    @FXML
    private ChoiceBox<String> menujuChoiceBox;



    @FXML
    public void tampilkanDaftarPesawat(String tujuan, String berangkat){
        Random pembuatIdTransaksi = new Random();

        MongoCollection<Document> daftarPesawat = MongoController.MongoConnect("Pesawat");

        listTiket.setSpacing(20);

        listTiket.getChildren().clear();

        BasicDBObject kriteria = new BasicDBObject();
        kriteria.append("Dari", berangkat);
        kriteria.append("Menuju", tujuan);

        daftarPesawat.find(kriteria).forEach(doc -> {

            Tiket tiketBaru = new Tiket();

            tiketBaru.setIdTransaksi(pembuatIdTransaksi.nextInt(10000000));
            tiketBaru.setIkonMaskapai(String.valueOf(doc.get("Ikon")));
            tiketBaru.setMaskapai(String.valueOf(doc.get("Maskapai")));
            tiketBaru.setDari(String.valueOf(doc.get("Dari")));
            tiketBaru.setMenuju(String.valueOf(doc.get("Menuju")));
            tiketBaru.setHarga(String.valueOf(doc.get("Harga")));

            GridPane tiketPesawat = new GridPane();

            ImageView ikonPesawat = new ImageView(String.valueOf(doc.get("Ikon")));
            ikonPesawat.setFitWidth(64);
            ikonPesawat.setPreserveRatio(true);

            Label namaMaskapai = new Label(String.valueOf(doc.get("Maskapai")));
            namaMaskapai.setFont(Font.font("Arial", 14));

            Label hargaMaskapai = new Label("$" + doc.get("Harga"));
            hargaMaskapai.setFont(Font.font("Arial", 14));

            ImageView ikonBelanja = new ImageView("com/example/demo/shopping_cart_FILL0_wght400_GRAD0_opsz48.png");
            ikonPesawat.setFitWidth(64);
            ikonPesawat.setPreserveRatio(true);

            ikonBelanja.setOnMouseClicked(e -> {
                MongoCollection<Document> daftarTiket = MongoController.MongoConnect("Tiket");
                Document newTiket = new Document();
                newTiket
                        .append("idTransaksi", tiketBaru.getIdTransaksi())
                        .append("Email", userEmail)
                        .append("Ikon", tiketBaru.getIkonMaskapai())
                        .append("Nama Maskapai", tiketBaru.getMaskapai())
                        .append("Dari", tiketBaru.getDari())
                        .append("Menuju", tiketBaru.getMenuju())
                        .append("Harga", "$" + tiketBaru.getHarga());
                daftarTiket.insertOne(newTiket);
                PopUp.Display("Transaksi");
            });

            tiketPesawat.add(ikonPesawat, 0,1 );
            tiketPesawat.add(namaMaskapai,1, 1);
            tiketPesawat.add(hargaMaskapai, 2, 1);
            tiketPesawat.add(ikonBelanja, 3, 1);

            tiketPesawat.setMinWidth(720);
            tiketPesawat.setMinHeight(90);
            tiketPesawat.setAlignment(Pos.CENTER);

            tiketPesawat.getColumnConstraints().add(new ColumnConstraints(144));
            tiketPesawat.getColumnConstraints().add(new ColumnConstraints(216));
            tiketPesawat.getColumnConstraints().add(new ColumnConstraints(216));
            tiketPesawat.getColumnConstraints().add(new ColumnConstraints(144));

            tiketPesawat.setStyle("-fx-background-color: #81c3d7;");

            listTiket.getChildren().add(tiketPesawat);
        });
    }

    public void setAksiChoiceBox(){
        dariChoiceBox.setOnAction(event -> tampilkanDaftarPesawat(dariChoiceBox.getValue(), menujuChoiceBox.getValue()));

        menujuChoiceBox.setOnAction(event -> tampilkanDaftarPesawat(dariChoiceBox.getValue(), menujuChoiceBox.getValue()));
    }

    @FXML
    public void menujuAkunUser(){
        try {
            App.setRoot("accountPage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize(){
        MongoCollection<Document> provinsi = MongoController.MongoConnect("Provinsi");
        MongoCollection<Document> listPengguna = MongoController.MongoConnect("Pengguna");

        BasicDBObject pengguna = new BasicDBObject();
        pengguna.append("email", userEmail);

        listPengguna.find(pengguna).forEach(doc -> userName = doc.get("firstName") + " " + doc.get("lastName"));

        provinsi.find().forEach(document ->
        {
            dariChoiceBox.getItems().add(String.valueOf(document.get("Provinsi")));
            menujuChoiceBox.getItems().add(String.valueOf(document.get("Provinsi")));
        });

        setAksiChoiceBox();

        System.out.println(userName);
    }
}
