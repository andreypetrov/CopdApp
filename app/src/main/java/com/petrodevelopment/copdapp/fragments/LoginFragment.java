package com.petrodevelopment.copdapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.activities.LoginActivity;

/**
 * Fragment for login
 */
public class LoginFragment extends BaseFragment {

    private RegisterFragment registerFragment;
    public Context context;
    private LoginActivity loginActivity;

    private TextView newUser;


    public LoginFragment() {
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
        goToRegister(inflatedView); //Register screen
        initLoginButton(inflatedView); //To login
        // Inflate the layout for this fragment
        return inflatedView;
    }

    /*
     * Login after signup
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

    private void goToRegister(View view)
    {
        newUser = (TextView) view.findViewById(R.id.newAccount);
        newUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginActivity.initRegisterFragment();
            }
        });
    }



}
