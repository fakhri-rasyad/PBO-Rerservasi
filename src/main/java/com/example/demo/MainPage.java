package com.example.demo;

import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.bson.Document;

import javax.print.Doc;

public class MainPage {
    @FXML
    private AnchorPane mainPage;

    @FXML
    private Pane kontenPesawat;

    public static void tampilkanDaftarPesawat(){

        MongoCollection<Document> daftarPesawat = MongoController.MongoConnect("pesawat");

        daftarPesawat.find().limit(5).forEach(doc -> {
            System.out.println(doc.toJson());
            GridPane tiketPesawat = new GridPane();
            Image logoPesawat = new Image("account_circle_FILL0_wght400_GRAD0_opsz48.png");
            Label namaMaskapai = new Label(doc.get("nama").toString());
        });
    }

    public static void main(String[] args){
        MongoCollection<Document> daftarPesawat = MongoController.MongoConnect("pesawat");

        daftarPesawat.find().forEach(document -> System.out.println("Posisi: "+ document.toJson()));
    }
}
