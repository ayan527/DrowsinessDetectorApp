package com.example.drowsinessdetectorapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.drowsinessdetectorapp.R;
import com.example.drowsinessdetectorapp.fragment.AboutFragment;
import com.example.drowsinessdetectorapp.fragment.DevelopersFragment;
import com.example.drowsinessdetectorapp.fragment.FactsFragment;
import com.example.drowsinessdetectorapp.fragment.HomeFragment;
import com.google.android.material.navigation.NavigationView;

public class StartingActivity extends AppCompatActivity {
    private static final String TAG = "StartingActivity";

    private NavigationView startingViewNavigation;
    private CoordinatorLayout startingCoordinatorLayout;
    //private FrameLayout startingFrameLayout;
    private DrawerLayout startingDrawerLayout;

    private MenuItem previousMenuItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        Log.i(TAG,"Created");

        initializeViews();
        openHomeFragment();

        startingViewNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (previousMenuItem != null)
                    previousMenuItem.setChecked(false);

                menuItem.setChecked(true);
                menuItem.setCheckable(true);

                previousMenuItem = menuItem;

                switch (menuItem.getItemId()) {
                    case R.id.home_fragment :
                        Log.i(TAG,"Opening Home-Fragment");

                        Toast.makeText(StartingActivity.this,"Home!",Toast.LENGTH_SHORT).show();
                        addFragmentToFrame(new HomeFragment());
                        break;
                    case R.id.facts_fragment:
                        Log.i(TAG,"Opening Facts-Fragment");

                        Toast.makeText(StartingActivity.this,"Facts!",Toast.LENGTH_SHORT).show();
                        addFragmentToFrame(new FactsFragment());
                        break;
                    case R.id.developers_fragment:
                        Log.i(TAG,"Opening Developers-Fragment");

                        Toast.makeText(StartingActivity.this,"Developers!",Toast.LENGTH_SHORT).show();
                        addFragmentToFrame(new DevelopersFragment());
                        break;
                    case R.id.about_fragment:
                        Log.i(TAG,"Opening About-Fragment");

                        Toast.makeText(StartingActivity.this,"About!",Toast.LENGTH_SHORT).show();
                        addFragmentToFrame(new AboutFragment());
                        break;
                }

                return true;
            }
        });

    }

    private void addFragmentToFrame(Fragment newFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.startingFrameLayout,newFragment)
                .commit();

        startingDrawerLayout.closeDrawers();
    }

    private void openHomeFragment() {
        Log.i(TAG,"Opening Home-Fragment");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.startingFrameLayout, new HomeFragment())
                .commit();

        startingViewNavigation.setCheckedItem(R.id.home_fragment);
    }

    private void initializeViews() {
        Log.i(TAG,"Views are Initialized");

        startingDrawerLayout = (DrawerLayout) findViewById(R.id.startingDrawerLayout);
        startingCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.startingCoordinatorLayout);
        //startingFrameLayout = (FrameLayout) findViewById(R.id.startingFrameLayout);
        startingViewNavigation = (NavigationView) findViewById(R.id.startingViewNavigation);
    }

}