package com.petrodevelopment.copdapp.model;

import android.content.Context;

import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.util.JsonLoader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO Fix this model to reflect properly our medical data
 * Created by andrey on 10/05/2015.
 */
public class Appointment extends Model {

    public String id;
    public long date;
    public String providerId;

    //Edited via the Record Appointment screen
    public List<AppointmentRecord> appointmentRecords;

    public Appointment(){

    }

    public Provider getProvider(Context context) {
        return ((MainApplication)context.getApplicationContext()).getProvider(providerId);
    }
}
