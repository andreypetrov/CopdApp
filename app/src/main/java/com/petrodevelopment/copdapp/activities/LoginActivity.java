package com.petrodevelopment.copdapp.activities;

import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.fragments.LoginFragment;
import com.petrodevelopment.copdapp.fragments.RegisterFragment;
import com.petrodevelopment.copdapp.fragments.WelcomeBackFragment;


public class LoginActivity extends BaseActivity {

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Creating First fragment(Register?)
        if (findViewById(R.id.user_interface) != null)
        {
            if (savedInstanceState != null)
                return;

            RegisterFragment register = new RegisterFragment();
            //LoginFragment login = new LoginFragment();
            //WelcomeBackFragment wbf = new WelcomeBackFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.user_interface, register);
            ft.commit();
        }
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
