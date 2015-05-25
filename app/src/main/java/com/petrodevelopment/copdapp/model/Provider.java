package com.petrodevelopment.copdapp.model;

import android.content.Context;

import com.petrodevelopment.copdapp.MainApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 10/05/2015.
 * Added coordinates - Tom - 18-05-2015
 */
public class Provider extends Model {

    public String id;
    public String title;
    public String firstName;
    public String lastName;
    public String photoUrl;
    public String phoneNumber;
    public String email;
    public String clinicianTypeId;
    public Address address;

    public ClinicianType getClinitianType(Context context) {
        return ((MainApplication)context.getApplicationContext()).getClinitianType(clinicianTypeId);
    }

    public Provider() {
    }
}
