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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by dimas on 12/4/2017.
 */

public class FirstLoginActivity extends AppCompatActivity {
    private static final String TAG = SignupActivity.class.getSimpleName();
    private EditText inputFullName, inputAddress, inputPhone, inputRecEmail, inputGender;
    private Button next;
    private ProgressBar progressBar;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signprofile);

        //Get Firebase auth instance
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        next = (Button) findViewById(R.id.next);
        inputFullName = (EditText) findViewById(R.id.name);
        inputAddress = (EditText) findViewById(R.id.address);
        inputPhone = (EditText) findViewById(R.id.phone);
        inputRecEmail = (EditText) findViewById(R.id.recEmail);
        inputGender = (EditText) findViewById(R.id.gender);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);

        userId = user.getUid();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getEmail();
                String name= inputFullName.getText().toString();
                String address = inputAddress.getText().toString();
                String phone = inputPhone.getText().toString();
                String recEmail = inputRecEmail.getText().toString();
                String gender = inputGender.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter your full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(getApplicationContext(), "Enter your address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter yout phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(recEmail)) {
                    Toast.makeText(getApplicationContext(), "Enter your recovery email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (address.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Enter Proper Address!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                createUser(name,address,phone,recEmail,email,gender);
                //progressBar.setVisibility(View.VISIBLE);
                //create user
                startActivity(new Intent(FirstLoginActivity.this, MainActivity.class));
                finish();

            }
        });
    }

    private void createUser(String name, String address, String phone, String recEmail, String email, String gender) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        UserData user = new UserData(name,address,phone,recEmail,email,gender);
        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }

    /**
     * User data change listener
     */
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
        //progressBar.setVisibility(View.GONE);
    }

}
