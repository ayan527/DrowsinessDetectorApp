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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.drowsinessdetectorapp.R;


public class FactsFragment extends Fragment {
    private static final String TAG = "FactsFragment";

    private Animation button_click;

    private ImageButton hamburgerImageButton;
    private ImageButton nextFactsButton;
    private ImageButton previousFactsButton;

    private ImageView imageFact1,imageFact2,imageFact3,imageFact4,imageFact5,imageFact6;

    private DrawerLayout startingDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG,"View is to be Created");
        View view = inflater.inflate(R.layout.fragment_facts, container, false);

        button_click = AnimationUtils.loadAnimation(getContext(),R.anim.button_click);

        startingDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.startingDrawerLayout);

        imageFact1 = (ImageView) view.findViewById(R.id.imageFact1);
        imageFact2 = (ImageView) view.findViewById(R.id.imageFact2);
        imageFact3 = (ImageView) view.findViewById(R.id.imageFact3);
        imageFact4 = (ImageView) view.findViewById(R.id.imageFact4);
        imageFact5 = (ImageView) view.findViewById(R.id.imageFact5);
        imageFact6 = (ImageView) view.findViewById(R.id.imageFact6);

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

        nextFactsButton = (ImageButton) view.findViewById(R.id.nextFactsButton);
        previousFactsButton = (ImageButton) view.findViewById(R.id.previousFactsButton);

        nextFactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFactsButton.startAnimation(button_click);

                hamburgerImageButton.setVisibility(View.INVISIBLE);
                nextFactsButton.setVisibility(View.INVISIBLE);
                previousFactsButton.setVisibility(View.VISIBLE);

                imageFact1.setVisibility(View.INVISIBLE);
                imageFact2.setVisibility(View.INVISIBLE);
                imageFact3.setVisibility(View.INVISIBLE);

                imageFact4.setVisibility(View.VISIBLE);
                imageFact5.setVisibility(View.VISIBLE);
                imageFact6.setVisibility(View.VISIBLE);
            }
        });

        previousFactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //previousFactsButton.startAnimation(button_click);

                hamburgerImageButton.setVisibility(View.VISIBLE);
                previousFactsButton.setVisibility(View.INVISIBLE);
                nextFactsButton.setVisibility(View.VISIBLE);

                imageFact1.setVisibility(View.VISIBLE);
                imageFact2.setVisibility(View.VISIBLE);
                imageFact3.setVisibility(View.VISIBLE);

                imageFact4.setVisibility(View.INVISIBLE);
                imageFact5.setVisibility(View.INVISIBLE);
                imageFact6.setVisibility(View.INVISIBLE);
            }
        });

        Log.i(TAG,"Widgets are to be Initialized");

        return view;
    }
}