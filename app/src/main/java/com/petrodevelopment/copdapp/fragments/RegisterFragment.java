package com.petrodevelopment.copdapp.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.activities.LoginActivity;
import com.petrodevelopment.copdapp.model.Login;

import java.util.ArrayList;
import java.util.List;


/**
 * Added by tom 30/05/2015
 * For register screen on home page
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private int parsedPasscode = 0;
    //Getting values from edit text fields
    private EditText emailText;
    private EditText passFirst;
    private EditText passSecond;
    private EditText numbPass;

    private LoginFragment loginFragment;
    public Context context;
    private LoginActivity loginActivity;

    private OnItemSelectedListener listener;
    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_register, container, false);

        TextView tv = (TextView) inflatedView.findViewById(R.id.login);
       tv.setOnClickListener(this);

        // Inflate the layout for this fragment
        return inflatedView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                loginActivity.initLoginFragment();
                break;
        }
    }



    public interface OnItemSelectedListener {
        public void onRssItemSelected(String link);
    }



    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //mListener = (OnFragmentInteractionListener) activity;
            context = getActivity();
            loginActivity = (LoginActivity)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    //Added by Tom 23-05-2015
    //Signing the user up, currently just goes into an arraylist, but will have to be changed when database implemented
    public void signUp(View v)
    {
        emailText = (EditText) getView().findViewById(R.id.enterEmail);
        passFirst = (EditText) getView().findViewById(R.id.enterPassword);
        passSecond = (EditText) getView().findViewById(R.id.confirmPassword);
        numbPass = (EditText) getView().findViewById(R.id.enterPasscode);

        //Validation NOTE: need to add int validation for passcode
        if(emailText.getText().toString().matches(""))
        {
            showMsg("Email Cannot be blank!");
        }
        else if (passFirst.getText().toString().matches(""))
        {
            showMsg("Password Cannot be blank!");
        }
        else if (passSecond.getText().toString().matches(""))
        {
            showMsg("Password Cannot be blank!");
        }
        else if (numbPass.getText().toString().matches(""))
        {
            showMsg("4-digit passcode cannot be blank!");
        }
        else
        {
            secondValidation(v);
        }
    }


    //Check if passwords match, validation good then go to Home
    public void secondValidation(View v)
    {
        if (passFirst.getText().toString().matches(passSecond.getText().toString()))
        {
            String email = emailText.getText().toString();
            String password = passFirst.getText().toString();
            String passcode = numbPass.getText().toString();
            try{
                parsedPasscode = Integer.parseInt(passcode);
            }
            catch (NumberFormatException e)
            {
                showMsg("Passcode must be anumber");
            }

            addToLoginList(email, password, parsedPasscode);
            goToHome(v);
        }
        else
        {
            showMsg("Your passwords do not match!");
        }
    }


    /*
     *  Adding patient registration to login
     */

    public void addToLoginList(String email, String password, int parsedPasscode)
    {
        List<Login> login = new ArrayList<>();
        Login patientInfo = new Login(email, password, parsedPasscode);
        patientInfo.addLogin(login);
    }

    //To show validation error --Added by Tom 23/05/2015
    public void showMsg(String Error)
    {
        Context context = getActivity().getApplicationContext();
        CharSequence text = Error;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    //Going to home screen after login
    public void goToHome(View v)
    {

    }

}
