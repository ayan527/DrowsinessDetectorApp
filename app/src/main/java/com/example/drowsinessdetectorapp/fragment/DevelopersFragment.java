package com.example.drowsinessdetectorapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.drowsinessdetectorapp.R;


public class DevelopersFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "DevelopersFragment";

    private Animation button_click;
    private String url;

    private ImageButton hamburgerImageButton;
    private DrawerLayout startingDrawerLayout;

    private ImageButton ayanGithubButton;
    private ImageButton souvikGithubButton;
    private ImageButton sawonGithubButton;
    private ImageButton rajarsiGithubButton;

    private ImageButton ayanLinkedinButton;
    private ImageButton souvikLinkedinButton;
    private ImageButton sawonLinkedinButton;
    private ImageButton rajarsiLinkedinButton;

    private ImageButton ayanGmailButton;
    private ImageButton souvikGmailButton;
    private ImageButton sawonGmailButton;
    private ImageButton rajarsiGmailButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_developers, container, false);;

        button_click = AnimationUtils.loadAnimation(getContext(),R.anim.button_click);

        startingDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.startingDrawerLayout);
        hamburgerImageButton = (ImageButton) view.findViewById(R.id.hamburgerImageButton);

        ayanGithubButton = (ImageButton) view.findViewById(R.id.ayanGithubButton);
        ayanGithubButton.setOnClickListener(this);
        souvikGithubButton = (ImageButton) view.findViewById(R.id.souvikGithubButton);
        souvikGithubButton.setOnClickListener(this);
        sawonGithubButton = (ImageButton) view.findViewById(R.id.sawonGithubButton);
        sawonGithubButton.setOnClickListener(this);
        rajarsiGithubButton = (ImageButton) view.findViewById(R.id.rajarsiGithubButton);
        rajarsiGithubButton.setOnClickListener(this);

        ayanLinkedinButton = (ImageButton) view.findViewById(R.id.ayanLinkedinButton);
        ayanLinkedinButton.setOnClickListener(this);
        souvikLinkedinButton = (ImageButton) view.findViewById(R.id.souvikLinkedinButton);
        souvikLinkedinButton.setOnClickListener(this);
        sawonLinkedinButton = (ImageButton) view.findViewById(R.id.sawonLinkedinButton);
        sawonLinkedinButton.setOnClickListener(this);
        rajarsiLinkedinButton = (ImageButton) view.findViewById(R.id.rajarsiLinkedinButton);
        rajarsiLinkedinButton.setOnClickListener(this);

        ayanGmailButton = (ImageButton) view.findViewById(R.id.ayanGmailButton);
        ayanGmailButton.setOnClickListener(this);
        souvikGmailButton = (ImageButton) view.findViewById(R.id.souvikGmailButton);
        souvikGmailButton.setOnClickListener(this);
        sawonGmailButton = (ImageButton) view.findViewById(R.id.sawonGmailButton);
        sawonGmailButton.setOnClickListener(this);
        rajarsiGmailButton = (ImageButton) view.findViewById(R.id.rajarsiGmailButton);
        rajarsiGmailButton.setOnClickListener(this);


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

    private void openGmail(String url) {
        if (getActivity() != null) {
            String[] recipients = {url};
            Intent gmailIntent = new Intent(Intent.ACTION_SEND);
            gmailIntent.putExtra(Intent.EXTRA_EMAIL,recipients);
            gmailIntent.setType("plain/text");
            getActivity().startActivity(gmailIntent);
        } else {
            Toast.makeText(getContext(),"Error in Gmail!",Toast.LENGTH_SHORT).show();
        }
    }

    private void openBrowser(String url) {
        if (getActivity() != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getActivity().startActivity(browserIntent);
        } else {
            Toast.makeText(getContext(),"Error in link!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        url = null;

        switch (v.getId()){
            case R.id.ayanGithubButton:
                url = "https://github.com/ayan527";
                break;
            case R.id.souvikGithubButton:
                url = "https://github.com/fear-the-lord";
                break;
            case R.id.sawonGithubButton:
                url = "https://github.com/SawonBhattacharya";
                break;
            case R.id.rajarsiGithubButton:
                url = "https://github.com/RajarsiGit";
                break;
            case R.id.ayanLinkedinButton:
                url = "https://www.linkedin.com/in/ayan-mukherjee-3aa436178/";
                break;
            case R.id.souvikLinkedinButton:
                url = "https://www.linkedin.com/in/souvik-ghosh31/";
                break;
            case R.id.sawonLinkedinButton:
                url = "https://www.linkedin.com/in/sawon-bhattacharya-890187167/";
                break;
            case R.id.rajarsiLinkedinButton:
                url = "https://www.linkedin.com/in/rajarsi-saha-2709a297";
                break;
            case R.id.ayanGmailButton:
                url = "ayanmukherjee20102011@gmail.com";
                break;
            case R.id.souvikGmailButton:
                url = "souvikghosh199831@gmail.com";
                break;
            case R.id.sawonGmailButton:
                url = "sawon17081997@gmail.com";
                break;
            case R.id.rajarsiGmailButton:
                url = "rajarsi3997@gmail.com";
                break;
            default:
                url = "https://www.google.com";
        }

        if (url.contains("https"))
            openBrowser(url);
        else
            openGmail(url);
    }

}