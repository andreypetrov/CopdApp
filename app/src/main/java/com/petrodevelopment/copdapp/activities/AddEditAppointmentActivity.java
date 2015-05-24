package com.petrodevelopment.copdapp.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.adapters.EditAddAppointmentProviderListAdapter;
import com.petrodevelopment.copdapp.model.Provider;
import com.petrodevelopment.copdapp.model.Questions;

import java.util.Calendar;


public class AddEditAppointmentActivity extends FragmentActivity implements OnClickListener, OnMapReadyCallback {

    //Variables for date and time
    TextView selectTime,selectDate;
    private int hour, minute, day, month, year;

    //Variables for google maps
    private String address = "Test";
    private String coords = "";
    private String name = "Dr";
    private double lat = 43.6430879;
    private double lng = -79.4186298;
    private GoogleMap map;


    private Provider provider = new Provider();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_appointment);

        //Google Maps Added 19-05-2015 by Tom
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //Populate Provider Spinner
        final EditAddAppointmentProviderListAdapter providerAdapter = new EditAddAppointmentProviderListAdapter(Provider.getDummy(), this, R.layout.cell_provider_list);
        final Spinner providerSpinner = (Spinner) findViewById(R.id.select_provider);
        providerSpinner.setAdapter(providerAdapter);

        providerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                address = Provider.getDummy().get(position).address;
                coords = Provider.getDummy().get(position).Coordinates;
                name = "Dr. " + Provider.getDummy().get(position).firstName + " " + Provider.getDummy().get(position).lastName;

                //Update coords for mapview
                String[] latLng;
                latLng = coords.split(",");
                lat = Double.parseDouble(latLng[0]);
                lng = Double.parseDouble(latLng[1]);

                //Calling method to populate questions
               // populateQuestions();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                providerSpinner.setPrompt("Select Provider");
                return;
            }
        });

        //For picking time
        selectTime = (TextView) findViewById(R.id.select_time);
        selectTime.setOnClickListener(this);

        //For picking Date
        selectDate = (TextView) findViewById(R.id.select_date);
        selectDate.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit_appointment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    //Added 16/05/2015 by Tom
    //For the two pickers, time and date
    @Override
    public void onClick(View view) {
        //If date selected
        if (view== selectDate) {
            //Current D/M/Y
            final Calendar c = Calendar.getInstance();
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year =  c.get(Calendar.YEAR);

            //DatePicker
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            selectDate.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);

                        }
                    }, year, month, day);
            dpd.show();
        }
        //If Time selected
        if (view == selectTime) {

            // Process to get Current Time
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            // Display Selected time in textbox
                            selectTime.setText(hourOfDay + ":" + minute);
                        }
                    }, hour, minute, false);
            tpd.show();
        }
    }


    //Added for Google Maps 19-05-2015 by Tom
    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        BitmapDescriptor bitmapAZURE = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE); //for map marker colour

        //Adding the marker information
        map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .icon(bitmapAZURE)
                .title(name)
                .snippet(address));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 11));
    }

    //Added by Tom 22-05-2015, for Questions once Provider has been selected
    public void populateQuestions() {

        //First hide the textview questions_per_specialist"
        TextView tv = (TextView) findViewById(R.id.questions_per_specialist);
        tv.setVisibility(View.GONE);

        //Display Questions//
        //Questions
        Questions questions = new Questions();
        questions.getQuestions();

        LinearLayout layout = new LinearLayout(this);
        setContentView(layout);
        layout.setOrientation(LinearLayout.VERTICAL);

        for (int i =0; i < questions.size(); i++)
        {
            TextView textView=new TextView(getApplicationContext());
            tv.setText(questions.get(i).toString());
            layout.addView(tv);
        }
    }
}

