package com.petrodevelopment.copdapp.model;


import android.content.Context;

import com.petrodevelopment.copdapp.util.FileUtil;
import com.petrodevelopment.copdapp.util.JsonLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Fix this model to differentiate questions per providers
 * Created on 22/05/2015.
 */
public class AppointmentList extends Model {
    public List<Appointment> appointments;
}
