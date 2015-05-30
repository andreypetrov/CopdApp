package com.petrodevelopment.copdapp.model;

import android.content.Context;

import com.petrodevelopment.copdapp.util.JsonLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Appointment Record Category
 * Created by andrey on 13/05/2015.
 */
public class AppointmentRecordCategoryList extends Model {
    public List<AppointmentRecordCategory> appointmentRecordCategories;
    public List<AppointmentRecordCategory> appointmentRecordCategoryTypes;



    private List<AppointmentRecordCategory> appointmentRecordCategoriesAndTypesFlattened;

    private static AppointmentRecordCategoryList fromJson(String json) {
        return JsonLoader.GSON.fromJson(json, AppointmentRecordCategoryList.class);
    }

    public static AppointmentRecordCategoryList fromAsset(Context context) {
        String json = JsonLoader.loadJsonFromAssets(context, "appointment_record_categories");
        return fromJson(json);
    }


    /**
     * Get all the categories and all the types together. This needed as a view model convenience for the adapter
     * @return
     */
    public List<AppointmentRecordCategory> getAppointmentRecordCategoriesAndTypesFlattened() {
        if (appointmentRecordCategoriesAndTypesFlattened == null) {
            appointmentRecordCategoriesAndTypesFlattened = createFlatList();
        }
        return appointmentRecordCategoriesAndTypesFlattened;
    }

    /**
     * Create flat list with all the types before all members of their type
     * @return
     */
    public List<AppointmentRecordCategory> createFlatList() {
        List<AppointmentRecordCategory> flattenedList = new ArrayList<>();
        for (AppointmentRecordCategory appointmentRecordCategoryType : appointmentRecordCategoryTypes) {
            flattenedList.add(appointmentRecordCategoryType);
            for (AppointmentRecordCategory appointmentRecordCategory :appointmentRecordCategories) {
                if (appointmentRecordCategoryType.id.equals(appointmentRecordCategory.typeId)) {
                    flattenedList.add(appointmentRecordCategory);
                }
            }
        }
        return flattenedList;
    }
}