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

import javax.print.Doc;
import java.io.IOException;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

public class MainPage {

    public static String userEmail;
    @FXML
    private VBox listTiket;

    @FXML
    private ChoiceBox<String> dariComboBox;

    @FXML
    private ChoiceBox<String> menujuComboBox;



    @FXML
    public void tampilkanDaftarPesawat(){
        Random pembuatIdTransaksi = new Random();

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
            namaMaskapai.setFont(Font.font("Arial", 18));

            Label hargaMaskapai = new Label(String.valueOf(doc.get("Harga")));
            hargaMaskapai.setFont(Font.font("Arial", 18));

            tiketPesawat.add(ikonPesawat, 0, 1);
            tiketPesawat.add(namaMaskapai,1, 1);
            tiketPesawat.add(hargaMaskapai, 2, 1);

            tiketPesawat.setMinWidth(720);
            tiketPesawat.setMinHeight(90);
            tiketPesawat.setAlignment(Pos.CENTER);

            tiketPesawat.getColumnConstraints().add(new ColumnConstraints(180));
            tiketPesawat.getColumnConstraints().add(new ColumnConstraints(360));
            tiketPesawat.getColumnConstraints().add(new ColumnConstraints(180));

            tiketPesawat.setStyle("-fx-background-color: orange;");

            listTiket.getChildren().add(tiketPesawat);
            tiketPesawat.setOnMouseClicked(e ->{
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
            });
        });
    }

    @FXML
    public void menujuAkunUser(){
        try {
            App.setRoot("accountPage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
