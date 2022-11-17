package com.example.demo;

import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.bson.Document;


public class MainPage {
    @FXML
    private VBox listTiket;

    @FXML
    private Label Maskapai;

    public static void tampilkanDaftarPesawat(){

        MongoCollection<Document> daftarPesawat = MongoController.MongoConnect("pesawat");

        daftarPesawat.find().limit(5).forEach(doc -> {
            System.out.println(doc.toJson());
            GridPane tiketPesawat = new GridPane();
            Image logoPesawat = new Image("account_circle_FILL0_wght400_GRAD0_opsz48.png");
            Label namaMaskapai = new Label(doc.get("nama").toString());
            Label hargaMaskapai = new Label("$"+doc.get("Harga").toString());
            tiketPesawat.add(namaMaskapai,1, 1);
            tiketPesawat.add(hargaMaskapai, 2, 1);
        });
    }

    public static void main(String[] args) {
        System.out.println();

    }
}
