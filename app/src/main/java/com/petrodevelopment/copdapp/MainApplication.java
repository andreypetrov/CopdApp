package com.petrodevelopment.copdapp;

import android.app.Application;

import com.petrodevelopment.copdapp.model.Appointment;
import com.petrodevelopment.copdapp.model.AppointmentList;
import com.petrodevelopment.copdapp.model.ClinicianType;
import com.petrodevelopment.copdapp.model.ClinicianTypeList;
import com.petrodevelopment.copdapp.model.Provider;
import com.petrodevelopment.copdapp.model.ProviderList;
import com.petrodevelopment.copdapp.model.Question;
import com.petrodevelopment.copdapp.model.QuestionList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO add here any singleton instances
 * Created by andrey on 10/05/2015.
 */
public class MainApplication extends Application {


    //All model data is stored here
    public ClinicianTypeList clinicianTypeList;
    private Map<String, ClinicianType> clinicianTypeMap;

    public ProviderList providerList;
    private Map<String, Provider> providerMap;

    public AppointmentList appointmentList;
    private Map<String, Appointment> appointmentMap;

    public QuestionList questionList;
    private Map<String, Question> questionMap;


    @Override
    public void onCreate() {
        super.onCreate();
        initClinicianTypes();
        initProviders();
        initAppointments();
        initQuestions();
    }


    /**
     * Initialize clinician types
     */
    private void initClinicianTypes() {
        clinicianTypeList = ClinicianTypeList.fromAsset(this);
        clinicianTypeMap = new HashMap<>();
        for (ClinicianType clinicianType : clinicianTypeList.clinicianTypes) {
            clinicianTypeMap.put(clinicianType.id, clinicianType);
        }
    }


    private void initAppointments() {
        appointmentList = AppointmentList.fromAsset(this);
        appointmentMap = new HashMap<>();
        for (Appointment appointment : appointmentList.appointments) {
            appointmentMap.put(appointment.id, appointment);
        }
    }

    private void initProviders() {
        providerList = ProviderList.fromAsset(this);
        providerMap = new HashMap<>();
        for (Provider provider : providerList.providers) {
            providerMap.put(provider.id, provider);
        }
    }


    private void initQuestions() {
        questionList = QuestionList.fromAsset(this);
        questionMap = new HashMap<>();
        for (Question question : questionList.questions) {
            questionMap.put(question.id, question);
        }
    }


    public ClinicianType getClinitianType(String id) {
        return clinicianTypeMap.get(id);
    }

    public Provider getProvider(String id) {
        return providerMap.get(id);
    }

    public Appointment getAppointment(String id) {
        return appointmentMap.get(id);
    }

    public Question getQuestion(String id) {
        return questionMap.get(id);
    }

}
