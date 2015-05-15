package com.petrodevelopment.copdapp.record;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.util.JsonLoader;
import com.petrodevelopment.copdapp.util.U;
import com.petrodevelopment.copdapp.viewmodel.AppointmentRecordCategories;

/**
 * Created by andrey on 15/05/2015.
 */
public class RecordAppointmentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_appointment);
        initToolbar();
        initListView();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initListView() {
        String json = JsonLoader.loadJsonFromAssets(this, "appointment_record_categories");
        AppointmentRecordCategories categories = JsonLoader.GSON.fromJson(json, AppointmentRecordCategories.class);


        U.log(this, categories);
        U.log(this, json);
    }

}
