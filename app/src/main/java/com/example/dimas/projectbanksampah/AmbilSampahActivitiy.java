package com.example.dimas.projectbanksampah;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by dimas on 11/28/2017.
 */

public class AmbilSampahActivitiy extends android.support.v4.app.Fragment implements View.OnClickListener {

    private Spinner inputTipeSampah;
    private EditText inputWaktu, inputTanggal, inputAlamat;
    private Button order;
    private ImageButton buttonJam;
    private ProgressBar progressBar;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId,orderId,address;
    private UserData data;
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
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // get reference to 'users' node
        userId = user.getUid();
        mFirebaseDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = dataSnapshot.getValue(UserData.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        mFirebaseDatabase = mFirebaseInstance.getReference("order");

        order = (Button) v.findViewById(R.id.order);
        order.setOnClickListener(this);

        buttonJam = (ImageButton) v.findViewById(R.id.imageButtonJam);
        buttonJam.setOnClickListener(this);

        /*
        buttonJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(((MainActivity) getActivity()), "Goblog", Toast.LENGTH_SHORT).show();
                DialogFragment newFragment = new timePickerFragment();
                newFragment.show(getActivity().getFragmentManager(),"TimePicker");
            }
        });
        */
        inputTipeSampah = (Spinner) v.findViewById(R.id.spinner);
        inputWaktu = (EditText) v.findViewById(R.id.waktuEdit);
        inputTanggal = (EditText) v.findViewById(R.id.tanggalEdit);
        inputAlamat = (EditText) v.findViewById(R.id.alamat);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order:
                String waktu = inputWaktu.getText().toString();
                String tanggal = inputTanggal.getText().toString();
                String tipeSampah = inputTipeSampah.getTransitionName();
                String alamat = inputAlamat.getText().toString();

                if (TextUtils.isEmpty(waktu)) {
                    Toast.makeText(((MainActivity) getActivity()), "Enter your full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(tanggal)) {
                    Toast.makeText(((MainActivity) getActivity()), "Enter your address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty((alamat))){
                    Toast.makeText(((MainActivity) getActivity()), "Yout Default Home Address Will Be Used", Toast.LENGTH_SHORT).show();
                }
                /*
                if (address.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Enter Proper Address!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                */

                createOrder(data.name, userId, waktu, tanggal, tipeSampah);
                android.support.v4.app.Fragment fragment = new HomeActivity();

                if (fragment != null) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                break;
            case R.id.imageButtonJam:
                DialogFragment newFragment = new timePickerFragment();
                newFragment.show(getActivity().getFragmentManager(),"TimePicker");
        }
    }

    private void createOrder(String name, String userId, String waktu, String tanggal, String tipeSampah ) {
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
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user = dataSnapshot.getValue(UserData.class);
                data = user;
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