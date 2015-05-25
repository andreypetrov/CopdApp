package com.petrodevelopment.copdapp;

import android.app.Application;

import com.petrodevelopment.copdapp.model.ClinicianType;
import com.petrodevelopment.copdapp.model.ClinicianTypeList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO add here any singleton instances
 * Created by andrey on 10/05/2015.
 */
public class MainApplication extends Application {

    private Map<String, String> clinicianIdToName;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    /**
     * Get a clinician's name from it's id. On first access lazy init of clinician types
     * @param clinicianTypeId
     * @return
     */
    public String getClinicianName(String clinicianTypeId) {
        if (clinicianIdToName == null) initClinicianTypes();
        return clinicianIdToName.get(clinicianTypeId);
    }


    /**
     * Initialize clinician types
     */
    private void initClinicianTypes() {
        ClinicianTypeList clinicianTypeList = ClinicianTypeList.fromAsset(this);
        clinicianIdToName = new HashMap<>();
        for (ClinicianType clinicianType: clinicianTypeList.clinicianTypes) {
            clinicianIdToName.put(clinicianType.id, clinicianType.name);
        }
    }
}
