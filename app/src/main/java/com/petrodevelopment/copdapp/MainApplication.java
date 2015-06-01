package com.petrodevelopment.copdapp;

import android.app.Application;

import com.petrodevelopment.copdapp.model.Appointment;
import com.petrodevelopment.copdapp.model.AppointmentList;
import com.petrodevelopment.copdapp.model.AppointmentRecord;
import com.petrodevelopment.copdapp.model.AppointmentRecordCategory;
import com.petrodevelopment.copdapp.model.AppointmentRecordCategoryList;
import com.petrodevelopment.copdapp.model.ClinicianType;
import com.petrodevelopment.copdapp.model.ClinicianTypeList;
import com.petrodevelopment.copdapp.model.ModelFacade;
import com.petrodevelopment.copdapp.model.Provider;
import com.petrodevelopment.copdapp.model.ProviderList;
import com.petrodevelopment.copdapp.model.Question;
import com.petrodevelopment.copdapp.model.QuestionList;
import com.petrodevelopment.copdapp.util.Preferences;
import com.petrodevelopment.copdapp.util.U;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO add here any singleton instances
 * Created by andrey on 10/05/2015.
 */
public class MainApplication extends Application {
    public static final String APPOINTMENT_ID_EXTRA = "appointment_id_extra";
    public static final String PROVIDER_ID_EXTRA = "provider_id_extra";
    public static final String CLINICIAN_TYPE_ID_EXTRA = "clinician_type_id_extra";
    public static final String QUESTION_ID_EXTRA = "question_id_extra";
    public static final String APPOINTMENT_RECORD_CATEGORY_ID = "appointment_record_category_id";
    public static final String IMAGE_INDEX_EXTRA = "image_index_extra";

    private ModelFacade modelFacade;
    private Preferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
    }



    public ModelFacade getModelFacade() {
        if (modelFacade == null) modelFacade = new ModelFacade(this);
        return modelFacade;
    }

    public Preferences getPreferences() {
        if (preferences == null) preferences = new Preferences(this);
        return preferences;
    }

}
