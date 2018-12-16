package com.example.dimas.projectbanksampah;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommitOrder extends Fragment implements View.OnClickListener {

    private TextView name, address, tanggal, waktu, phone;
    private Button proceed, cancel;
    private ProgressBar progressBar;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId, data;
    private UserData dataUser;
    private OrderData dataOrder;

    public CommitOrder() {
    }

    public CommitOrder(String orderId, UserData dataUser, OrderData dataOrder) {
        this.data = orderId;
        this.dataUser = dataUser;
        this.dataOrder = dataOrder;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.createorder_fragment, container, false);
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        userId=user.getUid();

        mFirebaseDatabase = mFirebaseInstance.getReference("order");

        // get reference to 'users' node
        userId = user.getUid();

        proceed = (Button) v.findViewById(R.id.proceed);
        proceed.setOnClickListener(this);

        cancel = (Button) v.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        name = (TextView) v.findViewById(R.id.namaPemesan);
        address = (TextView) v.findViewById(R.id.alamatPemesan);
        tanggal = (TextView) v.findViewById(R.id.tanggalPengPemesan);
        waktu = (TextView) v.findViewById(R.id.waktuPengPemesan);
        phone = (TextView) v.findViewById(R.id.noHPPemesan);

        name.setText(dataUser.name);
        address.setText(dataUser.address);
        tanggal.setText(dataOrder.tanggal);
        waktu.setText(dataOrder.waktu);
        phone.setText(dataUser.phone);

        return v;
    }

    public void addPoints(OrderData data){

        if (data.tipeSampah.equals("Botol Kaca (1 poin)") ) {
            dataUser.points += data.berat * 1;
        }
        else if (data.tipeSampah.equals("Botol Platik (2 poin)")){
            dataUser.points += data.berat * 2;
        }
        else if (data.tipeSampah.equals("Paralon (3 poin)")){
            dataUser.points += data.berat * 3;
        }
        else if (data.tipeSampah.equals("Kardus (4 poin)")){
            dataUser.points += data.berat * 4;
        }
        else if (data.tipeSampah.equals("Kertas (5 poin)")){
            dataUser.points += data.berat * 5;
        }

        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mFirebaseDatabase.child(userId).child("points").setValue(dataUser.points);
    }
    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()) {
            case R.id.proceed:
                fragment = new HomeActivity();
                addPoints(this.dataOrder);
                if (fragment != null) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                break;
            case R.id.cancel:

                mFirebaseDatabase.child(data).removeValue();
                fragment = new HomeActivity();

                if (fragment != null) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                break;
        }
    }
}
