package com.petrodevelopment.copdapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.activities.HomeActivity;
import com.petrodevelopment.copdapp.adapters.AppointmentListAdapter;
import com.petrodevelopment.copdapp.model.Appointment;
import com.petrodevelopment.copdapp.model.ClinicianType;
import com.petrodevelopment.copdapp.model.Provider;
import com.petrodevelopment.copdapp.util.U;
import com.petrodevelopment.copdapp.views.TabsView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * TODO in the distant future apply filtering both by tab and by search field to return the intersection of the two search criteria
 * Created by Andrey on 14/05/2015.
 */
public class AppointmentsFragment extends FilterableFragment {
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
                ((HomeActivity)getActivity()).closeSearchView();
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
        adapter = new AppointmentListAdapter(filteredAppointments, getActivity(), R.layout.cell_appointment_list);
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
    }

    private void filterReset() {
        if (filteredAppointments == null) filteredAppointments = new ArrayList<>();
        else filteredAppointments.clear();

        for (Appointment appointment : getModel()) {
            filteredAppointments.add(appointment);
        }
        U.log(this, "filterReset():" + filteredAppointments.size());
        if(adapter !=null) adapter.notifyDataSetChanged();
    }

    private List<Appointment> getModel() {
        return getApp().appointmentList.appointments;
    }


    @Override
    public void filterList(String searchQuery) {
        String searchQueryLowerCase = searchQuery.toLowerCase(Locale.getDefault());

        U.log(this, "filtering: " + searchQuery);
        filteredAppointments.clear();
        for (Appointment appointment : getModel()) {
            Provider provider = appointment.getProvider(getActivity());
            ClinicianType clinicianType = provider.getClinitianType(getActivity());

            //in the date fields we will check for contaning
            Date date = U.convertUnixStringToDate(appointment.date);
            String dateString = U.convertToDateString(date);
            String timeString = U.convertToTimeString(date);

            //we will check in those fields for starting with
            String[] fields = {
                    provider.firstName,
                    provider.lastName,
                    provider.email,
                    provider.title,
                    provider.phoneNumber,
                    clinicianType.name
            };

            for (String field : fields) {
                if (field.toLowerCase(Locale.getDefault()).startsWith(searchQueryLowerCase)) {
                    filteredAppointments.add(appointment);
                    break;
                }
                if (dateString.toLowerCase(Locale.getDefault()).contains(searchQueryLowerCase) ||
                    timeString.toLowerCase(Locale.getDefault()).contains(searchQueryLowerCase) ) {
                    filteredAppointments.add(appointment);
                    break;
                }

            }
        }
        if(adapter !=null) adapter.notifyDataSetChanged();
    }


}
