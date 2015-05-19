package com.petrodevelopment.copdapp.model;

import android.content.Context;

import com.petrodevelopment.copdapp.util.JsonLoader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO Fix this model to reflect properly our medical data
 * Created by andrey on 10/05/2015.
 */
public class Appointment extends Model {

    public Date date;
    public String doctorName;

    //Edited via the Record Appointment screen
    public AppointmentRecord severity;
    public AppointmentRecord assessment;
    public AppointmentRecord medications;
    public AppointmentRecord tests;
    public AppointmentRecord lifeStyleChanges;
    public AppointmentRecord futureReferrals;

    public Appointment(){

    }

    public Appointment(Date date,
                       String doctorName,
                       AppointmentRecord severity,
                       AppointmentRecord assessment,
                       AppointmentRecord medications,
                       AppointmentRecord tests,
                       AppointmentRecord lifeStyleChanges,
                       AppointmentRecord futureReferrals
                       ) {
        this.date = date;
        this.doctorName = doctorName;
        this.severity = severity;
        this.assessment = assessment;
        this.medications = medications;
        this.tests = tests;
        this.lifeStyleChanges = lifeStyleChanges;
        this.futureReferrals = futureReferrals;
    }

    public Appointment(Date date, String doctorName) {
        this.date = date;
        this.doctorName = doctorName;
    }

    public static Appointment fromJson(String json) {
        return JsonLoader.GSON.fromJson(json, Appointment.class);
    }

    public static List<Appointment> getDummy(Context context) {
        String json = JsonLoader.loadJsonFromAssets(context, "appointment");
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(Appointment.fromJson(json));
        appointments.add(Appointment.fromJson(json));
        appointments.add(Appointment.fromJson(json));
        appointments.add(Appointment.fromJson(json));
        appointments.add(Appointment.fromJson(json));
        appointments.add(Appointment.fromJson(json));
        return appointments;
    }


    public static List<Appointment> getDummy() {
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(new Date(), "Dr. Jones"));
        appointments.add(new Appointment(new Date(), "Dr. Bones"));
        appointments.add(new Appointment(new Date(), "Dr. Zones"));
        appointments.add(new Appointment(new Date(), "Dr. Holms"));
        appointments.add(new Appointment(new Date(), "Dr. Holms"));
        appointments.add(new Appointment(new Date(), "Dr. Holms"));
        appointments.add(new Appointment(new Date(), "Dr. Holms"));
        return appointments;
    }
}
