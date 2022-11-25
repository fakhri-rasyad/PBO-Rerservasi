package com.example.demo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.bson.Document;

import java.io.IOException;


public class AccountController {

    @FXML
    private VBox ticketBox;

    @FXML
    public static Label userName;

    @FXML
    public static Label userEmail;
    public void loadTiket(){
        MongoCollection<Document> tiketPengguna = MongoController.MongoConnect("Tiket");
        BasicDBObject idPengguna = new BasicDBObject();
        idPengguna.append("Email", MainPage.userEmail);
        tiketPengguna.find(idPengguna).forEach(doc ->{

            Label maskapai = new Label(String.valueOf(doc.get("Nama Maskapai")));
            maskapai.setFont(Font.font(12));

            Label harga = new Label(String.valueOf(doc.get("Harga")));
            harga.setFont(Font.font(12));

            Label destinasi = new Label("Dari: " + (doc.get("Dari") + "\n" + "Menuju: " + doc.get("Menuju")));
            destinasi.setFont(Font.font(12));

            ImageView iconMaskapai = new ImageView(String.valueOf(doc.get("Ikon")));
            ImageView iconHapus = new ImageView("com/example/demo/delete_FILL0_wght400_GRAD0_opsz48.png");

            iconMaskapai.setFitWidth(64);
            iconMaskapai.setPreserveRatio(true);

            iconHapus.setFitWidth(32);
            iconHapus.setPreserveRatio(true);
            iconHapus.setOnMouseClicked(e -> {
                ticketBox.getChildren().remove(iconHapus.getParent());

                Document deletedTicket = new Document();
                deletedTicket
                        .append("Email", MainPage.userEmail)
                        .append("idTransaksi",doc.get("idTransaksi"));

                tiketPengguna.deleteOne(deletedTicket);
            });

            GridPane ticket = new GridPane();
            ticket.setMinWidth(500);
            ticket.setMinHeight(100);
            ticket.setAlignment(Pos.CENTER);
            ticket.setStyle("-fx-background-color: #2f6690");

            ticket.getColumnConstraints().add(new ColumnConstraints(100));
            ticket.getColumnConstraints().add(new ColumnConstraints(170));
            ticket.getColumnConstraints().add(new ColumnConstraints(70));
            ticket.getColumnConstraints().add(new ColumnConstraints(70));
            ticket.getColumnConstraints().add(new ColumnConstraints(200));

            ticket.add(iconMaskapai, 0, 0);
            ticket.add(maskapai, 1, 0);
            ticket.add(harga, 2, 0);
            ticket.add(iconHapus, 3, 0);
            ticket.add(destinasi, 4, 0);

            ticketBox.getChildren().add(ticket);
        });
    }

    @FXML
    public void kembaliKeMenuUtama(){
        try {
            App.setRoot("MainPage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
