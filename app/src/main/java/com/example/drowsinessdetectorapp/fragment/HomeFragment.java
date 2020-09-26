package com.example.drowsinessdetectorapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.drowsinessdetectorapp.R;
import com.example.drowsinessdetectorapp.activity.MainActivity;

public class HomeFragment extends Fragment {
    private Button homeStartButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeStartButton = (Button) view.findViewById(R.id.homeStartButton);
        homeStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);

                if (getActivity() != null)
                    getActivity().finish();
            }
        });

        return view;
    }
}