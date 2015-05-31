package com.petrodevelopment.copdapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.fragments.LoginFragment;
import com.petrodevelopment.copdapp.fragments.RegisterFragment;
import com.petrodevelopment.copdapp.fragments.WelcomeBackFragment;
import com.petrodevelopment.copdapp.util.Preferences;


public class LoginActivity extends BaseActivity {

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private WelcomeBackFragment welcomeBackFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Preferences p = getPreferences();
         if(p.isUserLoggedIn())
            initWelcomeBackFragment();
        else
            initLoginFragment();
    }


    public void goToHome()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        //intent.putExtra();
        startActivity(intent);
    }


    /*
     * Login Fragment
     */
    public void initLoginFragment() {
        loginFragment = new LoginFragment();
        replaceFragment(R.id.user_interface, loginFragment);
    }


    /*
     * Register Fragment
     */
    public void initRegisterFragment() {
        registerFragment =  new RegisterFragment();
        replaceFragment(R.id.user_interface, registerFragment);
    }

    /*
     * Welcome back fragment
     */
    public void initWelcomeBackFragment() {
        welcomeBackFragment = new WelcomeBackFragment();
        replaceFragment(R.id.user_interface, welcomeBackFragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_register, menu);
        return true;
    }

    @Override
    public void initModel() {

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
}
