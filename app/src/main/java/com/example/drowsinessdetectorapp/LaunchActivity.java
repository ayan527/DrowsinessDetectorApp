package com.example.drowsinessdetectorapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {
    private static final String TAG = "LaunchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Log.i(TAG,"Created");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"Resumed");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"Moving to MainActivity");
                Intent intent = new Intent(LaunchActivity.this,StartingActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }*/
}