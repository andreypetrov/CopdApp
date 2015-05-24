package com.petrodevelopment.copdapp.model;


import android.content.Context;

import com.petrodevelopment.copdapp.util.JsonLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Fix this model to differentiate questions per providers
 * Created on 22/05/2015.
 */
public class ClinicianTypeList extends Model {
    public List<ClinicianType> clinicianTypes;

    public ClinicianTypeList() {
    }

    private static ClinicianTypeList fromJson(String json) {
        return JsonLoader.GSON.fromJson(json, ClinicianTypeList.class);
    }

    public static ClinicianTypeList fromAsset(Context context) {
        String json = JsonLoader.loadJsonFromAssets(context, "clinician_types");
        return fromJson(json);
    }
}
