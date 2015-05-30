package com.petrodevelopment.copdapp.model;

import android.content.Context;

import com.petrodevelopment.copdapp.util.FileUtil;
import com.petrodevelopment.copdapp.util.JsonLoader;
import com.petrodevelopment.copdapp.util.U;

import java.util.HashMap;
import java.util.Map;

/**
 * A united access to the model classes
 * Created by andrey on 30/05/2015.
 */
public class ModelFacade {
    public static final String APPOINTMENTS = "appointments";
    public static final String PROVIDERS = "providers";
    public static final String QUESTIONS = "questions";
    public static final String APPOINTMENT_RECORD_CATEGORIES = "appointment_record_categories";
    public static final String CLINICIAN_TYPES = "clinician_types";


    //All model data is stored here
    public ClinicianTypeList clinicianTypeList;
    private Map<String, ClinicianType> clinicianTypeMap;
    public ProviderList providerList;
    private Map<String, Provider> providerMap;
    public AppointmentList appointmentList;
    private Map<String, Appointment> appointmentMap;
    public QuestionList questionList;
    private Map<String, Question> questionMap;
    public AppointmentRecordCategoryList appointmentRecordCategoryList;
    private Map<String, AppointmentRecordCategory> appointmentRecordCategoryMap;
    private Context context;

    public ModelFacade(Context context) {
        this.context = context;
        loadModel();
    }


    public void loadModel() {
        initClinicianTypes();
        initProviders();
        initAppointments();
        initAppointmentRecordCategories();
        initQuestions();
    }

    public void saveModel() {
        FileUtil.saveModelToFile(APPOINTMENTS, appointmentList, context);
        FileUtil.saveModelToFile(PROVIDERS, providerList, context);
        FileUtil.saveModelToFile(QUESTIONS, questionList, context);
        FileUtil.saveModelToFile(APPOINTMENT_RECORD_CATEGORIES, appointmentRecordCategoryList, context);
        FileUtil.saveModelToFile(CLINICIAN_TYPES, clinicianTypeList, context);
    }


    private void initClinicianTypes() {
        clinicianTypeList = (ClinicianTypeList) fromFile(CLINICIAN_TYPES, ClinicianTypeList.class, context);
        clinicianTypeMap = new HashMap<>();
        for (ClinicianType clinicianType : clinicianTypeList.clinicianTypes) {
            clinicianTypeMap.put(clinicianType.id, clinicianType);
        }
    }

    private void initAppointments() {
        appointmentList = (AppointmentList) fromFile(APPOINTMENTS, AppointmentList.class, context);
        appointmentMap = new HashMap<>();
        for (Appointment appointment : appointmentList.appointments) {
            appointmentMap.put(appointment.id, appointment);
        }
    }

    private void initAppointmentRecordCategories() {
        appointmentRecordCategoryList = (AppointmentRecordCategoryList) fromFile(APPOINTMENT_RECORD_CATEGORIES, AppointmentRecordCategoryList.class, context);
        appointmentRecordCategoryMap = new HashMap<>();
        for (AppointmentRecordCategory appointmentRecordCategory : appointmentRecordCategoryList.appointmentRecordCategories) {
            appointmentRecordCategoryMap.put(appointmentRecordCategory.id, appointmentRecordCategory);
        }
        U.log(this, appointmentRecordCategoryList.toString());
    }

    private void initProviders() {
        providerList = (ProviderList) fromFile(PROVIDERS, ProviderList.class, context);
        providerMap = new HashMap<>();
        for (Provider provider : providerList.providers) {
            providerMap.put(provider.id, provider);
        }
    }

    private void initQuestions() {
        questionList = (QuestionList) fromFile(QUESTIONS, QuestionList.class, context);
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

    public AppointmentRecordCategory getAppointmentRecordCategory(String id) {
        return appointmentRecordCategoryMap.get(id);
    }

    public AppointmentRecord getAppointmentRecord(String appointmentRecordCategoryId, Appointment appointment) {
        for (AppointmentRecord appointmentRecord : appointment.appointmentRecords) {
            if (appointmentRecord.getAppointmentRecordCategory(context).id.equals(appointmentRecordCategoryId)) {
                return appointmentRecord;
            }
        }
        return null;
    }

    public static Object fromFile(String fileName, Class klass, Context context) {
        String json = FileUtil.loadModelFromFile(fileName, context);
        return JsonLoader.GSON.fromJson(json, klass);
    }
}
