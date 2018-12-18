/*
    Ditulis oleh Ikhwanul Murtadlo
    Editor: Android Studio
    Compiler dan lib yang digunakan: Android Studio,
                  JRE 1.8.0_152-release-1024-b02 amd64
                  JVM OpenJDK 64-Bit Server VM by JetBrains.s.r.o
    Versi dan Upgrade History: 3.1.4
    Tanggal pembuatan software: 24 Juli 2018
    Deskripsi singkat tentang software: Android Studio adalah Integrated Development Enviroment (IDE) untuk sistem operasi Android
*/
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

