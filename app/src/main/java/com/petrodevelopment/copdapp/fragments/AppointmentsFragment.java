package com.petrodevelopment.copdapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.adapters.AppointmentListAdapter;
import com.petrodevelopment.copdapp.model.Appointment;
import com.petrodevelopment.copdapp.viewmodel.AppointmentListVm;

import java.util.List;

/**
 * Created by Andrey on 14/05/2015.
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_appointments, container, false);
        populateList(rootView);
        return rootView;
    }

    private void populateList(View rootView) {
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        List<AppointmentListVm> viewModels = AppointmentListVm.createFromModel(getModel());
        listView.setAdapter(new AppointmentListAdapter(viewModels, getActivity(), R.layout.cell_appointment_list));
    }


    private List<Appointment> getModel() {
        return Appointment.getDummy();
    }


}
