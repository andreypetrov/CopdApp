package com.petrodevelopment.copdapp.viewmodel;

import com.petrodevelopment.copdapp.model.Appointment;

import java.util.ArrayList;
import java.util.List;

/**
 * Appointment view model - mapping the model Appointment to the list view
 * Created by andrey on 13/05/2015.
 */
public class AppointmentListVm {
    public String doctorImageUrl;
    public String date;
    public String doctorName;
    public String doctorTitle;


    public static AppointmentListVm createFromModel(Appointment appointment) {
        AppointmentListVm viewModel = new AppointmentListVm(
                "https://lh4.googleusercontent.com/-4OBPaUrA_5g/AAAAAAAAAAI/AAAAAAAAABY/DQUmqzsH4qY/photo.jpg",
                "09 June, 2015",
                "Dr. Thomas White",
                "Respirologist"
        );
        return viewModel;
    }


    /**
     * Generate a list of appointment view models from a list of appointments for the purpose of displaying them in a list view
     * @param appointments
     * @return
     */
    public static  List<AppointmentListVm> createFromModel(List<Appointment> appointments) {
        List<AppointmentListVm> viewModels = new ArrayList<>();
        for (Appointment appointment : appointments) {
            viewModels.add(createFromModel(appointment));
        }
        return viewModels;
    }

    public AppointmentListVm(String doctorImageUrl, String date, String doctorName, String doctorTitle) {
        this.doctorImageUrl = doctorImageUrl;
        this.date = date;
        this.doctorName = doctorName;
        this.doctorTitle = doctorTitle;
    }
}
