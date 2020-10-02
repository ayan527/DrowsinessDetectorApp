package com.example.drowsinessdetectorapp.fragment;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.example.drowsinessdetectorapp.R;

public class AboutFragment extends Fragment {
    private static final String TAG = "AboutFragment";

    private ImageButton hamburgerImageButton;
    private DrawerLayout startingDrawerLayout;

    private Animation button_click;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        Log.i(TAG,"AboutFragment is Created");

        button_click = AnimationUtils.loadAnimation(getContext(),R.anim.button_click);

        startingDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.startingDrawerLayout);
        hamburgerImageButton = (ImageButton) view.findViewById(R.id.hamburgerImageButton);

        hamburgerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hamburgerImageButton.setAnimation(button_click);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!startingDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                            startingDrawerLayout.openDrawer(GravityCompat.START);
                        } else {
                            startingDrawerLayout.closeDrawer(GravityCompat.START);
                        }
                    }
                },200);
            }
        });

        return view;
    }
}