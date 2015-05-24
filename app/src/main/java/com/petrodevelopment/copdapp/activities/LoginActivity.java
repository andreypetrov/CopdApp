package com.petrodevelopment.copdapp.activities;


import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.petrodevelopment.copdapp.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        underline();
    }


    //Show underline under Login
    private void underline()
    {
        TextView textView = (TextView) findViewById(R.id.signin);
        SpannableString content = new SpannableString("Login");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
    }
}

