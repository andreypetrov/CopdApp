package com.petrodevelopment.copdapp.record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.activities.BaseActivity;
import com.petrodevelopment.copdapp.adapters.AppointmentRecordCategoriesAdapter;
import com.petrodevelopment.copdapp.model.Appointment;
import com.petrodevelopment.copdapp.model.AppointmentList;
import com.petrodevelopment.copdapp.model.AppointmentRecord;
import com.petrodevelopment.copdapp.util.JsonLoader;
import com.petrodevelopment.copdapp.util.U;
import com.petrodevelopment.copdapp.viewmodel.AppointmentRecordCategoriesVm;

/**
 * Created by andrey on 15/05/2015.
 */
public class RecordAppointmentActivity extends BaseActivity {
    Appointment appointment;
    AppointmentRecordCategoriesAdapter adapter;
    String appointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_appointment);
        initViews();
        initModel();
        initToolbar();
        initListView();
    }


    public void initViews() {

    }

    /**
     * The appointment is passed in from the previous activity via the intent
     */
    @Override
    public void initModel() {
        appointmentId  = getIntent().getStringExtra(MainApplication.APPOINTMENT_ID_EXTRA);
        appointment = getApp().getAppointment(appointmentId);
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


        adapter = new AppointmentRecordCategoriesAdapter(
                categoriesObject.appointmentRecordCategories,
                appointment,
                this,
                R.layout.cell_appointment_record_category,
                R.layout.cell_appointment_record_subcategory);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItemViewType(position) == 1)
                    startRecordActivity(position); //if it is a subcategory then open it for editing
            }
        });




        U.log(this, json);
    }

    public void startRecordActivity(int position) {
        Intent intent = new Intent(this, RecordActivity.class);
        intent.putExtra(MainApplication.APPOINTMENT_ID_EXTRA, appointment.id);
        intent.putExtra(MainApplication.RECORD_TYPE_ID_EXTRA, adapter.getItem(position).name);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                saveAndClose();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
