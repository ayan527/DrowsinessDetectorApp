package com.example.drowsinessdetectorapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.drowsinessdetectorapp.R;


public class FactsFragment extends Fragment {
    private ImageView factsImgView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facts, container, false);

        factsImgView = view.findViewById(R.id.factsImgView);

        return view;
    }
}