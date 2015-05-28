package com.petrodevelopment.copdapp.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by andrey on 27/05/2015.
 */
public class BaseActivity extends ActionBarActivity {

    public void replaceFragment(int containerId, Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }
}
