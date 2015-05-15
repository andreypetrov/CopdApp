package com.petrodevelopment.copdapp.viewmodel;

import com.petrodevelopment.copdapp.model.Appointment;

import java.util.ArrayList;
import java.util.List;

/**
 * Appointment view model - mapping the model Appointment to the list view
 * Created by andrey on 13/05/2015.
 */
public class AppointmentRecordCategoryVm {
    public int id;
    public String name;
    public String image;
    public List<AppointmentRecordCategoryVm> subcategories;
}
