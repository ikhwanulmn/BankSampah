package com.example.dimas.projectbanksampah;
import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by dimas on 12/4/2017.
 */

@IgnoreExtraProperties
public class UserData {
    public String name;
    public String address;
    public String phone;
    public String recEmail;
    public String email;
    public String gender;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public UserData() {
    }

    public UserData(String name, String address, String phone, String recEmail, String email, String gender) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.recEmail =recEmail;
        this.email = email;
        this.gender = gender;
    }
}

