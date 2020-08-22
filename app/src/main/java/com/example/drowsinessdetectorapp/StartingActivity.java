package com.example.drowsinessdetectorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class StartingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "StartingActivity";

    private RelativeLayout startingLayout;
    private Button btnNextSlide;
    private Button btnPreviousSlide;
    private Button btnStart;

    private int drawableId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        Log.i(TAG,"Created");

        startingLayout = (RelativeLayout) findViewById(R.id.startingLayout);
        setBackgroundImage(R.drawable.androidfact1);

        btnNextSlide = (Button) findViewById(R.id.btnNextSlide);
        btnNextSlide.setOnClickListener(this);
        btnNextSlide.setEnabled(true);

        btnPreviousSlide = (Button) findViewById(R.id.btnPreviousSlide);
        btnPreviousSlide.setOnClickListener(this);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNextSlide) {
            switch (drawableId) {
                case R.drawable.androidfact1:
                    setBackgroundImage(R.drawable.androidfact2);
                    btnPreviousSlide.setEnabled(true);
                    break;
                case R.drawable.androidfact2:
                    setBackgroundImage(R.drawable.androiddeveloper);
                    break;
                case R.drawable.androiddeveloper:
                    setBackgroundImage(R.drawable.androidstart);
                    btnStart.setEnabled(true);
                    btnNextSlide.setEnabled(false);
                    break;
                default:
                    Log.i(TAG, "Click Start");
            }
        } else if (v.getId() == R.id.btnPreviousSlide) {
            switch (drawableId) {
                case R.drawable.androidfact2:
                    setBackgroundImage(R.drawable.androidfact1);
                    btnPreviousSlide.setEnabled(false);
                    break;
                case R.drawable.androiddeveloper:
                    setBackgroundImage(R.drawable.androidfact2);
                    break;
                case R.drawable.androidstart:
                    btnStart.setEnabled(false);
                    btnNextSlide.setEnabled(true);
                    setBackgroundImage(R.drawable.androiddeveloper);
                default:
                    Log.i(TAG, "Click Start");
            }
        } else if (v.getId() == R.id.btnStart) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setBackgroundImage(int id) {
        drawableId = id;
        startingLayout.setBackground(getDrawable(drawableId));
    }
}