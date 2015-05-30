package com.petrodevelopment.copdapp.model;

import android.content.Context;

import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.util.FileUtil;
import com.petrodevelopment.copdapp.util.JsonLoader;

import java.util.List;

/**
 * Created by andrey on 18/05/2015.
 */
public class AppointmentRecord extends Model {

    public String id;
    public String appointmentRecordTypeId;
    public String note;
    public String voiceRecordUrl;
    public List<String> imageUrls;

    public AppointmentRecordCategory getAppointmentRecordCategory(Context context) {
        return ((MainApplication)context.getApplicationContext()).getModelFacade().getAppointmentRecordCategory(appointmentRecordTypeId);
    }

}
