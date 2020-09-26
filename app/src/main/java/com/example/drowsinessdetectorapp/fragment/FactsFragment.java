package com.example.drowsinessdetectorapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.drowsinessdetectorapp.R;


public class FactsFragment extends Fragment {
    private static final String TAG = "FactsFragment";

    private ImageView factsImgView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG,"View is to be Created");
        View view = inflater.inflate(R.layout.fragment_facts, container, false);

        Log.i(TAG,"Widgets are to be Initialized");
        factsImgView = view.findViewById(R.id.factsImgView);

        return view;
    }
}