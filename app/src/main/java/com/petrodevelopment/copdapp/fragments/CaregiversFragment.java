package com.petrodevelopment.copdapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.petrodevelopment.copdapp.R;

/**
 * Created by andrey on 10/05/2015.
 */
public class CaregiversFragment extends SectionFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CaregiversFragment newInstance(int sectionNumber, String sectionTitle) {
        CaregiversFragment fragment = new CaregiversFragment();
        SectionFragment.addSectionAndTitleToFragment(fragment, sectionNumber, sectionTitle);
        return fragment;
    }

    public CaregiversFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_providers, container, false);

        return rootView;
    }



    private void populateList(ListView listView) {

    }

}
