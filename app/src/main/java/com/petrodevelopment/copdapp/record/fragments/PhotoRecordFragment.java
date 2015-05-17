package com.petrodevelopment.copdapp.record.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.fragments.AppointmentsFragment;
import com.petrodevelopment.copdapp.fragments.SectionFragment;

/**
 * Created by user on 15-05-14.
 */
public class PhotoRecordFragment extends SectionFragment {
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PhotoRecordFragment newInstance(int sectionNumber, String sectionTitle) {
        PhotoRecordFragment fragment = new PhotoRecordFragment();
        SectionFragment.addSectionAndTitleToFragment(fragment, sectionNumber, sectionTitle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_record_photo, container, false);

        return rootView;
    }
}
