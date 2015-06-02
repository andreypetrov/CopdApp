package com.petrodevelopment.copdapp.fragments;

/**
 * Created by andrey on 30/05/2015.
 */
public abstract class FilterableFragment extends SectionFragment {

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    public abstract void updateUi();
    public abstract void filterList(String searchQuery);
}
