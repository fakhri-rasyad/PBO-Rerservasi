package com.example.demo;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class MongoController {
    static String uri = "mongodb+srv://K4ND4:KRZero-1@cluster0.ll3ytwj.mongodb.net/?retryWrites=true&w=majority";

    public static List<String> MongoInsertDocument (String firstName, String lastName, String email, String password){
        ArrayList<String> kumpulanError = new ArrayList<>();
        Document User = new Document();
        User
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("email", email)
                .append("password", password);
        if(CheckEmail(email)){
            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("latihan");
            MongoCollection<Document> collection = database.getCollection("Pengguna");
            collection.insertOne(User);
        } else {
            kumpulanError.add("Email telah terpakai!");
        }

        return kumpulanError;
    }

    public static boolean CheckEmail(String newEmail){
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("latihan");
        MongoCollection<Document> collection = database.getCollection("Pengguna");

        Document found = collection.find(eq("email", newEmail)).first();
        return found == null;
    }

    public static boolean CheckPassword(String email, String password){
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("latihan");
        MongoCollection<Document> collection = database.getCollection("Pengguna");

        Document found = collection.find(eq("email", email)).first();
        if(Objects.requireNonNull(found).get("password") == password){
            System.out.println("Password cocok");
            return true;
        }else{
            return false;
        }
    }

    public static FindIterable<Document> daftarPesawat(){
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("latihan");
        MongoCollection<Document> collection = database.getCollection("pesawat");

        return collection.find();
    }

    public static void main(String[] args){
        FindIterable<Document> listPesawat = daftarPesawat();
        listPesawat.forEach(doc -> System.out.println(doc.get("nama")));
    }
}
