package com.petrodevelopment.copdapp.record;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.adapters.AppointmentRecordCategoriesAdapter;
import com.petrodevelopment.copdapp.util.JsonLoader;
import com.petrodevelopment.copdapp.util.U;
import com.petrodevelopment.copdapp.viewmodel.AppointmentRecordCategoriesVm;

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
        ListView listView = (ListView) findViewById(R.id.list_view);
        String json = JsonLoader.loadJsonFromAssets(this, "appointment_record_categories");
        AppointmentRecordCategoriesVm categoriesObject = JsonLoader.GSON.fromJson(json, AppointmentRecordCategoriesVm.class);

        listView.setAdapter(new AppointmentRecordCategoriesAdapter(
                categoriesObject.appointmentRecordCategories,
                this,
                R.layout.cell_appointment_record_category,
                R.layout.cell_appointment_record_subcategory));


        U.log(this, json);
    }

}
