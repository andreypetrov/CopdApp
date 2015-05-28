package com.petrodevelopment.copdapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.activities.HomeActivity;
import com.petrodevelopment.copdapp.adapters.AppointmentListAdapter;
import com.petrodevelopment.copdapp.model.Appointment;
import com.petrodevelopment.copdapp.util.U;
import com.petrodevelopment.copdapp.viewmodel.AppointmentListVm;
import com.petrodevelopment.copdapp.views.TabsView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Andrey on 14/05/2015.
 */
public class AppointmentsFragment extends SectionFragment {
    private List<Appointment> filteredAppointments;
    private ListView listView;
    private AppointmentListAdapter adapter;

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
        initTabs(rootView);
        initList(rootView);
        return rootView;
    }

    private void initTabs(View rootView) {
        TabsView tabsView = (TabsView) rootView.findViewById(R.id.tabs);
        tabsView.setOnTabSelectedListener(new TabsView.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, View view) {
                switch (position) {
                    case 0:
                        filterReset();
                        break;
                    case 1:
                        filterUpcoming();
                        break;
                    case 2:
                        filterReset();
                        break;
                    default://do nothing
                }
            }
        });
    }

    private void initList(View rootView) {
        U.log(this,"initList");
        listView = (ListView) rootView.findViewById(R.id.list_view);
        filterReset();
        List<AppointmentListVm> viewModels = AppointmentListVm.createFromModel(filteredAppointments, getActivity());
        adapter = new AppointmentListAdapter(viewModels, getActivity(), R.layout.cell_appointment_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Appointment appointment = filteredAppointments.get(position);
                ((HomeActivity) getActivity()).startAddAppointmentActivity(appointment.id);
            }
        });
    }

    /**
     * Filter down to only those appointments that have a starting date and time after the current date and time
     */
    private void filterUpcoming() {
        long currentTime = ((new Date()).getTime())/1000l;
        filteredAppointments.clear();
        for (Appointment appointment : getModel()) {
            if (appointment.date > currentTime) {
                filteredAppointments.add(appointment);
            }
        }
        if(adapter !=null) adapter.notifyDataSetChanged();

        U.log(this, filteredAppointments.size());
    }

    private void filterReset() {
        if (filteredAppointments == null) filteredAppointments = new ArrayList<>();
        else filteredAppointments.clear();

        for (Appointment appointment : getModel()) {
            filteredAppointments.add(appointment);
        }
        U.log(this, filteredAppointments.size());
        if(adapter !=null) adapter.notifyDataSetChanged();
    }

    private List<Appointment> getModel() {
        return getApp().appointmentList.appointments;
    }


}
