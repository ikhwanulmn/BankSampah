package com.example.dimas.projectbanksampah;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class OrderData {
    public String name;
    public String userId;
    public String waktu;
    public String tanggal;
    public String alamat;
    public String tipeSampah;
    public Integer berat;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public OrderData() {
    }

    public OrderData(String name, String userId, String waktu, String tanggal, String alamat, String tipeSampah, Integer berat) {
        this.name = name;
        this.userId = userId;
        this.waktu = waktu;
        this.tanggal = tanggal;
        this.alamat = alamat;
        this.tipeSampah = tipeSampah;
        this.berat = berat;
    }
}

