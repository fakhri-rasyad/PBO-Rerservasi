package com.example.demo;

public class Tiket {
    private int idTransaksi;
    private String Maskapai;
    private String Dari;
    private String Menuju;
    private String Harga;

    private String ikonMaskapai;

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getIkonMaskapai(){
        return ikonMaskapai;
    }

    public void setIkonMaskapai(String ikonMaskapai){
        this.ikonMaskapai = ikonMaskapai;
    }

    public String getMaskapai() {
        return Maskapai;
    }

    public void setMaskapai(String maskapai) {
        this.Maskapai = maskapai;
    }

    public String getDari() {
        return Dari;
    }

    public void setDari(String dari) {
        this.Dari = dari;
    }

    public String getMenuju() {
        return Menuju;
    }

    public void setMenuju(String menuju) {
        this.Menuju = menuju;
    }

    public String getHarga() {
        return Harga;
    }

    public void setHarga(String harga) {
        this.Harga = harga;
    }
}
