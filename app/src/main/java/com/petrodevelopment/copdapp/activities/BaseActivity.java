package com.petrodevelopment.copdapp.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.model.ModelFacade;
import com.petrodevelopment.copdapp.util.Preferences;
import com.petrodevelopment.copdapp.util.U;

/**
 * Created by andrey on 27/05/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                saveAndClose();
                return true;
            case R.id.action_delete:
                deleteAndClose();
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    public void onBackPressed() {
        saveAndClose();
    }

    public MainApplication getApp() {
        return (MainApplication) getApplication();
    }

    public Preferences getPreferences (){
        return getApp().getPreferences();
    }
    public ModelFacade getModelFacade() {
        return  getApp().getModelFacade();
    }
}
