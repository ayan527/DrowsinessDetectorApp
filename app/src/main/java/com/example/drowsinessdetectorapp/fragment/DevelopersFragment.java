package com.example.drowsinessdetectorapp.fragment;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.drowsinessdetectorapp.R;


public class DevelopersFragment extends Fragment {
    private static final String TAG = "DevelopersFragment";

    private ImageButton hamburgerImageButton;
    private DrawerLayout startingDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_developers, container, false);;

        startingDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.startingDrawerLayout);
        hamburgerImageButton = (ImageButton) view.findViewById(R.id.hamburgerImageButton);

        hamburgerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!startingDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    startingDrawerLayout.openDrawer(GravityCompat.START);
                } else {
                    startingDrawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });

        return view;
    }
}