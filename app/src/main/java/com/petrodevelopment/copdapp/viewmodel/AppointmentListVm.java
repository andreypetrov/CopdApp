package com.petrodevelopment.copdapp.viewmodel;

import android.content.Context;

import com.petrodevelopment.copdapp.model.Appointment;
import com.petrodevelopment.copdapp.util.U;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Appointment view model - mapping the model Appointment to the list view
 * Created by andrey on 13/05/2015.
 */
public class AppointmentListVm {

    public String backgroundColor;
    public String doctorImageUrl;
    public String date;
    public String time;
    public String doctorName;
    public String doctorTitle;


    public static AppointmentListVm createFromModel(Appointment appointment, Context context) {
        Date date = U.convertUnixStringToDate(appointment.date);
        AppointmentListVm viewModel = new AppointmentListVm(
                appointment.getProvider(context).getClinitianType(context).color,
                appointment.getProvider(context).getClinitianType(context).imageResourceName,
                U.convertToDateString(date),
                U.convertToTimeString(date),
                appointment.getProvider(context).title + " " + appointment.getProvider(context).firstName + " " + appointment.getProvider(context).lastName,
                appointment.getProvider(context).getClinitianType(context).name
        );
        return viewModel;
    }


    /**
     * Generate a list of appointment view models from a list of appointments for the purpose of displaying them in a list view
     * @param appointments
     * @return
     */
    public static  List<AppointmentListVm> createFromModel(List<Appointment> appointments, Context context) {
        List<AppointmentListVm> viewModels = new ArrayList<>();
        for (Appointment appointment : appointments) {
            viewModels.add(createFromModel(appointment, context));
        }
        return viewModels;
    }

    public AppointmentListVm(String backgroundColor, String doctorImageUrl, String date, String time, String doctorName, String doctorTitle) {
        this.backgroundColor = backgroundColor;
        this.doctorImageUrl = doctorImageUrl;
        this.date = date;
        this.time = time;
        this.doctorName = doctorName;
        this.doctorTitle = doctorTitle;
    }
}
