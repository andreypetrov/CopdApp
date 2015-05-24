package com.petrodevelopment.copdapp.activities;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.model.Login;

import java.util.ArrayList;
import java.util.List;

public class HomeRegisterActivity extends ActionBarActivity {

    private int parsedPasscode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_register);

        underline();

        //For Sign up button
        Button signup = (Button)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                signUp();;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Added by Tom 23-05-2015
    //Signing the user up, currently just goes into an arraylist, but will have to be changed when database implemented
    public void signUp()
    {
        //Getting values from edit text fields
        EditText emailText = (EditText)findViewById(R.id.enterEmail);
        EditText passFirst = (EditText)findViewById(R.id.enterPassword);
        EditText passSecond = (EditText)findViewById(R.id.confirmPassword);
        EditText numbPass = (EditText)findViewById(R.id.enterPasscode);

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

                addToLoginList(email, password,parsedPasscode);

                showMsg("Account Successfully Created!");
            }
            else
            {
                showMsg("Your passwords do not match!");
            }
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
        Context context = getApplicationContext();
        CharSequence text = Error;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    //Show underline under New Account
    private void underline()
    {
        TextView textView = (TextView) findViewById(R.id.newAccount);
        SpannableString content = new SpannableString("New Account");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
    }

}
