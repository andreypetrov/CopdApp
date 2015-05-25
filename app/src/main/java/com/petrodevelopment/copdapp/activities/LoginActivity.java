package com.petrodevelopment.copdapp.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.petrodevelopment.copdapp.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity  {

    private TextView tv;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        underline();
        setRegitrationTextViewListener(tv);
        setSignInButtonListener(signIn);
    }


    //For New User tab at top
    private void setRegitrationTextViewListener(TextView tv)
    {
        this.tv = tv;
        tv =  (TextView) findViewById(R.id.newAccount);
        tv.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                goToNewUser(v);
            }
        });
    }

    //For login button
    private void setSignInButtonListener(Button b)
    {
        this.signIn = signIn;
        signIn =  (Button) findViewById(R.id.login);
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loggedIn(v);
            }
        });
    }


    //Show underline under Login
    private void underline()
    {
        TextView textView = (TextView) findViewById(R.id.signin);
        SpannableString content = new SpannableString("Login");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
    }


    //Go back to registration screen
    private void goToNewUser(View v)
    {
        Intent intent = new Intent(this, HomeRegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


    //For login button
    private void loggedIn(View v)
    {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}

