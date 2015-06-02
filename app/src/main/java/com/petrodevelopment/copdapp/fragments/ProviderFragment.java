package com.petrodevelopment.copdapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.adapters.ProviderListAdapter;
import com.petrodevelopment.copdapp.model.Provider;
import com.petrodevelopment.copdapp.model.ProviderList;

import java.util.List;

/**
 * Created by andrey on 10/05/2015.
 */
public class ProviderFragment extends FilterableFragment {
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    BaseAdapter adapter;

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
        adapter = new ProviderListAdapter(getModel(), getActivity(), R.layout.cell_provider_list);
        listView.setAdapter(adapter);
    }

    @Override
    public void updateUi() {
        if (adapter!=null) adapter.notifyDataSetChanged();
    }

    private List<Provider> getModel() {
        return getModelFacade().providerList.providers;
    }

    @Override
    public void filterList(String searchQuery) {

    }
}
