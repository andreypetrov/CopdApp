package com.petrodevelopment.copdapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petrodevelopment.copdapp.R;

/**
 * Created by andrey on 10/05/2015.
 */
public class AppointmentsFragment extends SectionFragment {
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AppointmentsFragment newInstance(int sectionNumber, String sectionTitle) {
        AppointmentsFragment fragment = new AppointmentsFragment();
        SectionFragment.addSectionAndTitleToFragment(fragment, sectionNumber, sectionTitle);
        return fragment;
    }

    public AppointmentsFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_appointments, container, false);
        return rootView;
    }


}
