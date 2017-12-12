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
import android.widget.Toast;

/**
 * Created by dimas on 11/28/2017.
 */

public class HomeActivity extends Fragment implements View.OnClickListener{
    private ImageView imageView;
    private AnimationDrawable anim;
    private Button jemput;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        super.onCreate(savedInstanceState);

        jemput = (Button) v.findViewById(R.id.buttonJemput);
        jemput.setOnClickListener(this);

        imageView = (ImageView) v.findViewById(R.id.imageAnimation);
        if (imageView == null) throw new AssertionError();
        imageView.setBackgroundResource(R.drawable.animasi);
        anim = (AnimationDrawable) imageView.getBackground();
        anim.start();
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