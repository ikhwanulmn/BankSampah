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

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class ProfileActivity extends Fragment {

    private ProgressBar progressBar;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId,orderId,address;
    private UserData data;
    private OrderData orderData;
    private Button btnChangePassword,btnExecChangePass;
    private EditText newPassword;
    private int terbuka;
    private FirebaseAuth auth;
    private TextView nama,gender,alamat,nomor,textNewPass;
    public ProfileActivity() {
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        userId=user.getUid();

        nama = (TextView)v.findViewById(R.id.textViewNama);
        gender = (TextView)v.findViewById(R.id.textViewGender);
        alamat = (TextView)v.findViewById(R.id.textViewAlamat);
        nomor = (TextView)v.findViewById(R.id.textViewNo);

        mFirebaseDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = dataSnapshot.getValue(UserData.class);
                nama.setText(data.name);
                gender.setText(data.gender);
                alamat.setText(data.address);
                nomor.setText(data.phone);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        return v;

    }
    public void signOut() {
        auth.signOut();
    }
}
