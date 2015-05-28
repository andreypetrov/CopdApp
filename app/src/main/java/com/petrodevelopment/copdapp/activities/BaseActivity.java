package com.petrodevelopment.copdapp.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.util.U;

/**
 * Created by andrey on 27/05/2015.
 */
public abstract class BaseActivity extends ActionBarActivity {

    public void replaceFragment(int containerId, Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }


    abstract public void initModel();


    /**
     * Override to save model associated with current activity (e.g. appointment or record)
     */
    public void saveModel() {

    }

    /**
     *  Override to delete model associated with current activity (e.g. appointment or record)
     */
    public void deleteModel() {

    }

    public void saveAndClose() {
        saveModel();
        finish();
    }

    public void deleteAndClose() {
        deleteModel();
        finish();
    }


    public MainApplication getApp() {
        return (MainApplication) getApplication();
    }
}
