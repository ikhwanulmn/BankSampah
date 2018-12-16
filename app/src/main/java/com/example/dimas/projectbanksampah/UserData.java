package com.example.dimas.projectbanksampah;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserData {
    public String name;
    public String address;
    public String phone;
    public String email;
    public String gender;
    public Integer points;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public UserData() {
    }

    public UserData(String name, String address, String phone, String email, String gender, Integer points) {
        this.name = name;
        this.address = address;
        this.phone = phone;

        this.email = email;
        this.gender = gender;
        this.points = points;
    }
}

