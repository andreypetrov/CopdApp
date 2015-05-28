package com.petrodevelopment.copdapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petrodevelopment.copdapp.R;

/**
 * Parent of all other main menu section fragments
 * Created by andrey on 10/05/2015.
 */
public class SectionFragment extends BaseFragment {
    /**
     * Interface which the parent activity should implement to receive callbacks when the section was successfully attached to the activity
     */
    public interface SectionFragmentParent {
        void onSectionFragmentAttached(int sectionNumber, String title);
    }

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_SECTION_TITLE = "section_title";

    public static void addSectionAndTitleToFragment(Fragment fragment, int sectionNumber, String sectionTitle) {
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_SECTION_TITLE, sectionTitle);
        fragment.setArguments(args);
    }

    public SectionFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SectionFragmentParent) {
            ((SectionFragmentParent) activity).onSectionFragmentAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER),
                    getArguments().getString(ARG_SECTION_TITLE));
        }
    }
}
