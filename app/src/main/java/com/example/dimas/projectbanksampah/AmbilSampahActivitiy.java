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


public class AmbilSampahActivitiy extends android.support.v4.app.Fragment implements View.OnClickListener {

    private Spinner inputTipeSampah;
    private EditText inputWaktu, inputTanggal, inputAlamat, inputBerat;
    private Button order;
    private ImageButton buttonJam, buttonTanggal;
    private ProgressBar progressBar;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId,orderId,address;
    private UserData data;
    private OrderData orderData;
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

        buttonTanggal = (ImageButton) v.findViewById(R.id.imageButtonKalender);
        buttonTanggal.setOnClickListener(this);

        inputTipeSampah = (Spinner) v.findViewById(R.id.spinner);
        inputWaktu = (EditText) v.findViewById(R.id.waktuEdit);
        inputTanggal = (EditText) v.findViewById(R.id.tanggalEdit);
        inputAlamat = (EditText) v.findViewById(R.id.alamat);
        inputBerat = (EditText) v.findViewById(R.id.berat_sampah);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order:
                String nama = data.name;
                String waktu = inputWaktu.getText().toString();
                String tanggal = inputTanggal.getText().toString();
                String tipeSampah = inputTipeSampah.getSelectedItem().toString();
                String alamat = inputAlamat.getText().toString();
                String beratSampahStr = inputBerat.getText().toString();

                if (TextUtils.isEmpty(waktu)) {
                    Toast.makeText(((MainActivity) getActivity()), "Masukkan waktu penjemputan sampah", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(tanggal)) {
                    Toast.makeText(((MainActivity) getActivity()), "Masukkan tanggal penjemputan sampah", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(((MainActivity) getActivity()), beratSampahStr, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty((alamat))){
                    Toast.makeText(((MainActivity) getActivity()), "Masukkan lokasi penjemputan sampah", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(beratSampahStr)){
                    Toast.makeText(((MainActivity) getActivity()), "Masukkan berat sampah", Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer beratSampah = Integer.valueOf(inputBerat.getText().toString());

                createOrder(nama, userId, waktu, tanggal, alamat, tipeSampah, beratSampah);

                android.support.v4.app.Fragment fragment = new CommitOrder(orderId,data,orderData);

                if (fragment != null) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                break;

            case R.id.imageButtonJam:
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getActivity().getFragmentManager(),"TimePicker");
                break;
            case R.id.imageButtonKalender:
                DialogFragment newFragment1 = new DatePickerFragment();
                newFragment1.show(getActivity().getFragmentManager(),"DatePicker");
                break;
        }
    }

    private void createOrder(String name, String userId, String waktu, String tanggal, String alamat, String tipeSampah, Integer beratSampah ) {
        orderData = new OrderData(name, userId, waktu, tanggal, alamat, tipeSampah, beratSampah);
        orderId = mFirebaseDatabase.push().getKey();
        mFirebaseDatabase.child(orderId).setValue(orderData);
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
}