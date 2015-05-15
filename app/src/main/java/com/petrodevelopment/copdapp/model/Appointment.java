package com.petrodevelopment.copdapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO Fix this model to reflect properly our medical data
 * Created by andrey on 10/05/2015.
 */
public class Appointment {

    public Date date;
    public String doctorName;

    public Appointment(Date date, String doctorName) {
        this.date = date;
        this.doctorName = doctorName;
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
