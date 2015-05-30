package com.petrodevelopment.copdapp.fragments;

import android.app.Fragment;

import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.model.ModelFacade;
import com.petrodevelopment.copdapp.util.Preferences;

/**
 * Created by andrey on 28/05/2015.
 */
public class BaseFragment extends Fragment{

    public MainApplication getApp() {
        return (MainApplication) getActivity().getApplication();
    }
    public Preferences getPreferences (){
        return getApp().getPreferences();
    }
    public ModelFacade getModelFacade() {
        return  getApp().getModelFacade();
    }
}
