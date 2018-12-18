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

import android.app.DialogFragment;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends Fragment implements View.OnClickListener{
    private ImageView imageView;
    private TextView points;
    private AnimationDrawable anim;
    private Button jemput;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    private UserData data;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        super.onCreate(savedInstanceState);

        jemput = (Button) v.findViewById(R.id.buttonJemput);
        jemput.setOnClickListener(this);

        points = (TextView) v.findViewById(R.id.points);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        userId=user.getUid();


        imageView = (ImageView) v.findViewById(R.id.imageAnimation);
        if (imageView == null) throw new AssertionError();
        imageView.setBackgroundResource(R.drawable.animasi);
        anim = (AnimationDrawable) imageView.getBackground();
        anim.start();

        mFirebaseDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = dataSnapshot.getValue(UserData.class);
                points.setText(data.points.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        return v;

    }

    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.buttonJemput:
                fragment = new AmbilSampahActivitiy();
                break;
        }
        if (fragment != null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
    }
}