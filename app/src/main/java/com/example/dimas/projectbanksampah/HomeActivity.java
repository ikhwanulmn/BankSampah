package com.example.dimas.projectbanksampah;

import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by dimas on 11/28/2017.
 */

public class HomeActivity extends Fragment{
    private ImageView imageView;
    private AnimationDrawable anim;
    public HomeActivity(){

    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v =inflater.inflate(R.layout.home_fragment, container, false);

        imageView = (ImageView)v.findViewById(R.id.imageAnimation);
        if(imageView == null) throw new AssertionError();
        imageView.setBackgroundResource(R.drawable.animasi);
        anim = (AnimationDrawable)imageView.getBackground();
        anim.start();
        return v;
    }
}
