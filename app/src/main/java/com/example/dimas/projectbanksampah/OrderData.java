package com.example.dimas.projectbanksampah;
import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by dimas on 12/4/2017.
 */

@IgnoreExtraProperties
public class OrderData {
    public String name;
    public String userId;
    public String waktu;
    public String tanggal;
    public String tipeSampah;
    public Integer berat;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public OrderData() {
    }

    public OrderData(String name, String userId, String waktu, String tanggal, String tipeSampah, Integer berat) {
        this.name = name;
        this.userId = userId;
        this.waktu = waktu;
        this.tanggal = tanggal;
        this.tipeSampah = tipeSampah;
        this.berat = berat;
    }
}

