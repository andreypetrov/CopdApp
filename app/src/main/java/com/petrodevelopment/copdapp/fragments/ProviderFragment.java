package com.petrodevelopment.copdapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.adapters.ProviderListAdapter;
import com.petrodevelopment.copdapp.model.Provider;

import java.util.List;

/**
 * Created by andrey on 10/05/2015.
 */
public class ProviderFragment extends SectionFragment {
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ProviderFragment newInstance(int sectionNumber, String sectionTitle) {
        ProviderFragment fragment = new ProviderFragment();
        SectionFragment.addSectionAndTitleToFragment(fragment, sectionNumber, sectionTitle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_providers, container, false);
        populateList(rootView);
        return rootView;
    }

    private void populateList(View rootView) {
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(new ProviderListAdapter(getModel(), getActivity(), R.layout.cell_provider_list));
    }


    private List<Provider> getModel() {
        return Provider.getDummy();
    }
}
