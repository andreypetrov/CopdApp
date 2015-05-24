package com.petrodevelopment.copdapp.model;

import android.content.Context;

import com.petrodevelopment.copdapp.util.JsonLoader;

import java.util.List;

/**
 * Created by andrey on 18/05/2015.
 */
public class AppointmentRecord extends Model {
    public String note;
    public String voiceRecordUrl;
    public List<String> imageUrls;
    //TODO add reminder data

    public AppointmentRecord() {
    }

    public AppointmentRecord(String note, String voiceRecordUrl, List<String> imageUrls) {
        this.note = note;
        this.voiceRecordUrl = voiceRecordUrl;
        this.imageUrls = imageUrls;
    }


    private static AppointmentRecord fromJson(String json) {
        return JsonLoader.GSON.fromJson(json, AppointmentRecord.class);
    }

    public static AppointmentRecord fromAsset(Context context) {
        String json = JsonLoader.loadJsonFromAssets(context, "appointment_record");
        return fromJson(json);
    }
}
