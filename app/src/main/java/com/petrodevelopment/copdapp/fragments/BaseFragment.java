package com.petrodevelopment.copdapp.fragments;

import android.app.Fragment;

import com.petrodevelopment.copdapp.MainApplication;

/**
 * Created by andrey on 28/05/2015.
 */
public class BaseFragment extends Fragment{

    public MainApplication getApp() {
        return (MainApplication) getActivity().getApplication();
    }
}
