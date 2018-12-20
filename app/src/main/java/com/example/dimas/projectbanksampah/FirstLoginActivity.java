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

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FirstLoginActivity extends AppCompatActivity {
    private static final String TAG = SignupActivity.class.getSimpleName();
    private EditText inputFullName, inputAddress, inputPhone;
    private Spinner inputGender;
    private Button next;
    private ProgressBar progressBar;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    private UserProfileChangeRequest profileUpdates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signprofile);

        //Get Firebase auth instance
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // mendapatkan tabel user dari database
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        next = (Button) findViewById(R.id.next);
        inputFullName = (EditText) findViewById(R.id.name);
        inputAddress = (EditText) findViewById(R.id.address);
        inputPhone = (EditText) findViewById(R.id.phone);

        inputGender = (Spinner) findViewById(R.id.gender);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        userId = user.getUid();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getEmail();
                String name = inputFullName.getText().toString();
                String address = inputAddress.getText().toString();
                String phone = inputPhone.getText().toString();

                String gender = inputGender.getSelectedItem().toString();
                profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                user.updateProfile(profileUpdates);
                // pesan jika field nama tidak diisi. Pengguna tidak dapat melanjutkan ke halaman berikutnya
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Masukkan nama lengkap anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                // pesan jika field alamat tidak diisi. Pengguna tidak dapat melanjutkan ke halaman berikutnya
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(getApplicationContext(), "Masukkan alamat rumah anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                // pesan jika field phone tidak diisi. Pengguna tidak dapat melanjutkan ke halaman berikutnya
                if (TextUtils.isEmpty(phone) ) {
                    Toast.makeText(getApplicationContext(), "Masukkan nomor telepon/HP anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                // pesan jika field alamat kurang dari 6 huruf. Pengguna tidak dapat melanjutkan ke halaman berikutnya
                if (address.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Masukkan alamat rumah dengan benar dan lengkap", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create user
                createUser(name, address, phone, email, gender);
                progressBar.setVisibility(View.VISIBLE);

                startActivity(new Intent(FirstLoginActivity.this, MainActivity.class));
                finish();

            }
        });
    }
    // Fungsi membuat user baru
    private void createUser(String name, String address, String phone, String email, String gender) {
        // Mengirim data user baru ke firebase
        UserData user = new UserData(name,address,phone, email,gender,0);
        mFirebaseDatabase.child(userId).setValue(user);
        addUserChangeListener();
    }

    // Listener untuk dibaca pada log running
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user = dataSnapshot.getValue(UserData.class);

                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.email);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}
