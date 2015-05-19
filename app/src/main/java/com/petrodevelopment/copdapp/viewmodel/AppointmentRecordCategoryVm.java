package com.petrodevelopment.copdapp.viewmodel;

import com.petrodevelopment.copdapp.model.Appointment;
import com.petrodevelopment.copdapp.model.AppointmentRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Appointment view model - mapping the model AppointmentRecord to the list view
 * Created by andrey on 13/05/2015.
 */
public class AppointmentRecordCategoryVm {
    public String id;
    public String type;
    public String name;
    public String image;
    public AppointmentRecord record;
}
