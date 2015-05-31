package com.petrodevelopment.copdapp.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.activities.LoginActivity;

public class WelcomeBackFragment extends BaseFragment {

    LoginActivity loginActivity;

    public WelcomeBackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_login, container, false);

        loginActivity = (LoginActivity) getActivity();
        initLoginButton(inflatedView);

        return inflatedView;
    }


    /*
     * For login button
     */
    private void initLoginButton(View view)
    {
        Button signin = (Button) view.findViewById(R.id.login);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApp().getPreferences().logIn();
                loginActivity.goToHome();
            }
        });
    }



}
