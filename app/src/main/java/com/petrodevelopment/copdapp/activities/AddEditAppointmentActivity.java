package com.petrodevelopment.copdapp.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.adapters.EditAddAppointmentProviderListAdapter;
import com.petrodevelopment.copdapp.model.ClinicianType;
import com.petrodevelopment.copdapp.model.Provider;
import com.petrodevelopment.copdapp.model.ProviderList;
import com.petrodevelopment.copdapp.model.Question;
import com.petrodevelopment.copdapp.model.QuestionList;
import com.petrodevelopment.copdapp.record.RecordActivity;

import java.util.Calendar;
import java.util.List;


public class AddEditAppointmentActivity extends ActionBarActivity implements OnClickListener, OnMapReadyCallback {

    //Variables for date and time
    TextView selectTime,selectDate,selectQuestions;
    private int hour, minute, day, month, year;

    //Variables for google maps
    private String address = "Test";
    private String name = "Dr";
    private double lat = 43.6430879;
    private double lng = -79.4186298;
    private GoogleMap map;

    private List<Provider> providers;
    private List<ClinicianType> ct;
    private ClinicianType clinicianType = new ClinicianType();
    private Provider provider = new Provider();
    private Button recordButton;
    private Button saveAppointment;

    //For Testing Questions
    private String[] questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_appointment);

        //Google Maps Added 19-05-2015 by Tom
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initProviders();
        initSpinner();
        initToolbar();
        setUp();

        questionList = new String[]{"...Select Provider to see Quesitons, etc, etc.", "One", "Two", "Three", "Four"};
    }


    /*
     * All listeners methods here
     */
    public void setUp()
    {
        selectPickers();
        setButtonListeners();
    }

    public void setButtonListeners()
    {
        recordButton = (Button) findViewById(R.id.recordAppointment);
        setRecordButtonListener(recordButton);
        saveAppointment = (Button) findViewById(R.id.saveAsUpcoming);
        setSaveAppointmentButtonListener(saveAppointment);
    }


    //Button click listener for recording
    public void setRecordButtonListener(Button b)
    {
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToRecording(v);
            }
        });
    }


    //Button click listener for saving appointments button
    public void setSaveAppointmentButtonListener(Button b)
    {
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveAppointment(v);
            }
        });
    }


    //Go to recording activity when record results button is clicked
    public void goToRecording(View v)
    {
        Intent intent = new Intent(this, RecordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


    //Saving appointment
    public void saveAppointment(View v)
    {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    private void initSpinner() {
        //Populate Provider Spinner
        final EditAddAppointmentProviderListAdapter providerAdapter = new EditAddAppointmentProviderListAdapter(providers, this, R.layout.cell_provider_add_appointment_list);
        final Spinner providerSpinner = (Spinner) findViewById(R.id.select_provider);
        providerSpinner.setAdapter(providerAdapter);

        providerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //populateQuestions();
                provider = providers.get(position);
                address = provider.address.street;
                name = "Dr. " + provider.firstName + " " + provider.lastName;

                //Update coords for mapview
                lat = Double.parseDouble(provider.address.latitude);
                lng = Double.parseDouble(provider.address.longitude);

                if (providerSpinner.getSelectedItemPosition() != 0) {
                    createMarker();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

    private void initProviders() {
        providers = ProviderList.fromAsset(this).providers;
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

        if (view == selectDate) {
            //Current D/M/Y
            final Calendar c = Calendar.getInstance();
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year =  c.get(Calendar.YEAR);

            datePicker();
        }
        if (view == selectTime) {
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            timePicker();
        }
    }

    /*
     *  For date picker
     */
    public void datePicker()
    {
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


    /*
     * For time picker
     */
    public void timePicker()
    {
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


    /*
     * Selecting date or time, or number picker for questions
     */
    private void selectPickers()
    {
        selectTime = (TextView) findViewById(R.id.select_time);
        selectTime.setOnClickListener(this);

        selectDate = (TextView) findViewById(R.id.select_date);
        selectDate.setOnClickListener(this);
    }


    //Added for Google Maps 19-05-2015 by Tom
    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 11));
    }


    public void createMarker()
    {
        BitmapDescriptor bitmapAZURE = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE); //for map marker colour

        Marker info = map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .icon(bitmapAZURE)
                .title(name)
                .snippet(address));

        setMapScreen(info);
        info.showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 11));
    }


    public void setMapScreen(Marker marker)
    {
        Projection projection = map.getProjection();
        LatLng markerLocation = marker.getPosition();
        Point screenPosition = projection.toScreenLocation(markerLocation);

        Point mappoint = map.getProjection().toScreenLocation(new LatLng(lat, lng));
        mappoint.set(mappoint.x, mappoint.y+150);
        map.animateCamera(CameraUpdateFactory.newLatLng(map.getProjection().fromScreenLocation(mappoint)));
    }
    /*

    Projection projection = map.getProjection();
    LatLng markerLocation = marker.getPosition();
     Point screenPosition = projection.toScreenLocation(markerLocation);

    Point mappoint = googleMap.getProjection().toScreenLocation(new LatLng(latitude, longitude));
        mappoint.set(mappoint.x, mappoint.y-30);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(googleMap.getProjection().fromScreenLocation(mappoint)));

     */


    //Added by Tom 22-05-2015, for Question once Provider has been selected
    public void populateQuestions()
    {


        //First hide the textview questions_per_specialist"
        //TextView tv = (TextView) findViewById(R.id.questions_per_specialist);
       // tv.setVisibility(View.GONE);

        //Display Question//
        //Question

        //List<Question> questions = QuestionList.fromAsset(this).questions;
        /*
        LinearLayout layout = new LinearLayout(this);
        setContentView(layout);
        layout.setOrientation(LinearLayout.VERTICAL);

        for (int i =0; i < questions.size(); i++)
        {
            TextView textView=new TextView(getApplicationContext());
            tv.setText(questions.get(i).toString());
            layout.addView(tv);
        }*/

    }
}

