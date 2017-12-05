package com.example.dimas.projectbanksampah;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by dimas on 11/28/2017.
 */

public class AmbilSampahActivitiy extends android.support.v4.app.Fragment implements View.OnClickListener{

    private Spinner inputTipeSampah;
    private EditText inputWaktu, inputTanggal;
    private Button order;
    private ImageButton buttonJam;
    //private ProgressBar progressBar;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId,orderId;
    private int hour_x, minute_x;
    static final int DIALOG_ID = 0;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.ambilsampah_fragment, container, false);
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("order");
        order = (Button) v.findViewById(R.id.order);
        order.setOnClickListener(this);

        buttonJam = (ImageButton) v.findViewById(R.id.imageButtonJam);

        buttonJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(((MainActivity) getActivity()), "Goblog", Toast.LENGTH_SHORT).show();
                DialogFragment newFragment = new timePickerFragment();
                newFragment.show(getActivity().getFragmentManager(),"TimePicker");
            }
        });


        inputTipeSampah = (Spinner) v.findViewById(R.id.spinner);
        inputWaktu = (EditText) v.findViewById(R.id.waktuEdit);
        inputTanggal = (EditText) v.findViewById(R.id.tanggalEdit);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        userId = user.getUid();


        /*
        userId = user.getUid();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = user.getDisplayName();
                String email = user.getEmail();
                String userID = user.getUid();
                String waktu = inputWaktu.getText().toString();
                String tanggal = inputTanggal.getText().toString();
                String tipeSampah = inputTipeSampah.getTransitionName().toString();

                if (TextUtils.isEmpty(waktu)) {
                    Toast.makeText(((MainActivity)getActivity()), "Enter your full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(tanggal)) {
                    Toast.makeText(((MainActivity)getActivity()), "Enter your address", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*
                if (address.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Enter Proper Address!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                createOrder(name, address, phone, recEmail, email, gender);
                //progressBar.setVisibility(View.VISIBLE);
                //create user

                //startActivity(new Intent(AmbilSampahActivitiy.this, MainActivity.class));
                //finish();

            }
        });
        */
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order:
                String name = user.getDisplayName();
                String userID = user.getUid();
                String waktu = inputWaktu.getText().toString();
                String tanggal = inputTanggal.getText().toString();
                String tipeSampah = inputTipeSampah.getTransitionName();

                if (TextUtils.isEmpty(waktu)) {
                    Toast.makeText(((MainActivity) getActivity()), "Enter your full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(tanggal)) {
                    Toast.makeText(((MainActivity) getActivity()), "Enter your address", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*
                if (address.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Enter Proper Address!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                */
                createOrder(name, userId, waktu, tanggal, tipeSampah);
                break;
        }
    }

    private void createOrder(String name, String userId, String waktu, String tanggal, String tipeSampah ) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        Toast.makeText(((MainActivity) getActivity()), "Enter your full name", Toast.LENGTH_SHORT).show();
        OrderData order = new OrderData(name, userId, waktu, tanggal, tipeSampah);
        orderId = mFirebaseDatabase.push().getKey();
        mFirebaseDatabase.child(orderId).setValue(order);

        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user = dataSnapshot.getValue(UserData.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID)
            return new TimePickerDialog((MainActivity)getActivity(),kTimePickerListener,hour_x,minute_x,false);
        return null;
    }
    protected TimePickerDialog.OnTimeSetListener kTimePickerListener=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    hour_x =  hour;
                    minute_x = minute;
                    Toast.makeText(((MainActivity) getActivity()),hour_x + " : " + minute_x,Toast.LENGTH_LONG).show();
                }
            };

}

