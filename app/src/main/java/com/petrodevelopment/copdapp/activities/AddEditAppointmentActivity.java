package com.petrodevelopment.copdapp.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.adapters.EditAddAppointmentProviderListAdapter;
import com.petrodevelopment.copdapp.model.Appointment;
import com.petrodevelopment.copdapp.model.Provider;
import com.petrodevelopment.copdapp.model.Question;
import com.petrodevelopment.copdapp.record.RecordAppointmentActivity;

import java.util.Calendar;
import java.util.List;

import android.widget.TableRow.LayoutParams;


public class AddEditAppointmentActivity extends BaseActivity implements OnClickListener, OnMapReadyCallback {

    //Variables for date and time
    TextView selectTime, selectDate, selectQuestions;
    private int hour, minute, day, month, year;

    //Variables for google maps
    private String address = "Test";
    private String name = "Dr";
    private double lat = 43.6430879;
    private double lng = -79.4186298;
    private GoogleMap map;

    private String appointmentId;
    private Appointment appointment;
    private List<Provider> providers;

    private Provider provider;
    private String providerId;

    private Button recordButton;
    private Button saveAppointment;

    private List<Question> questions;


    //This is used for creating table rows for questions
    private final LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private TextView providerQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_appointment);


        //Google Maps Added 19-05-2015 by Tom
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setUp();
    }


    /*
     * Grouping methods for readability
     */
    public void setUp() {
        initModel();
        initSpinner();
        initToolbar();
        initNoteField();
        selectPickers();
        setButtonListeners();
    }

    private void initNoteField() {
        EditText noteEditTextView = (EditText) findViewById(R.id.note);
        noteEditTextView.setText(appointment.note);
        noteEditTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                appointment.note = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initModel() {
        //TODO if there is no appointment id, then create a new appointment with a unique id
        appointmentId = getIntent().getStringExtra(MainApplication.APPOINTMENT_ID_EXTRA);
        appointment = getModelFacade().getAppointment(appointmentId);
        provider = appointment.getProvider(this);
        if (provider != null) providerId = provider.id;

        questions = getModelFacade().questionList.questions;
        providers = getModelFacade().providerList.providers;
    }


    public void setButtonListeners() {
        recordButton = (Button) findViewById(R.id.recordAppointment);
        setRecordButtonListener(recordButton);
        saveAppointment = (Button) findViewById(R.id.saveAsUpcoming);
        setSaveAppointmentButtonListener(saveAppointment);
    }


    //Button click listener for recording
    public void setRecordButtonListener(Button b) {
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToRecording(v);
            }
        });
    }


    //Button click listener for saving appointments button
    public void setSaveAppointmentButtonListener(Button b) {
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveAppointment(v);
            }
        });
    }


    //Go to recording activity when record results button is clicked
    public void goToRecording(View v) {
        Intent intent = new Intent(this, RecordAppointmentActivity.class);
        intent.putExtra(MainApplication.APPOINTMENT_ID_EXTRA, appointmentId);
        startActivity(intent);
        finish();
    }


    //Saving appointment
    public void saveAppointment(View v) {
        Intent intent = new Intent(this, HomeActivity.class);
        //Pass appointment id
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
        providerSpinner.setSelection(getCurrentProviderIndexInSpinner());

        providerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (providerSpinner.getSelectedItemPosition() != 0) {
                    provider = providers.get(position - 1); // offsetting because of the extra first item on the spinner
                    providerId = provider.id;
                    appointment.providerId = providerId;

                    createMarker();
                    populateQuestions();
                } else { //reset provider to none
                    provider = null;
                    providerId = null;
                    appointment.providerId = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

    private int getCurrentProviderIndexInSpinner() { //depends on equals implementation
        if (provider != null && providers != null ) return providers.indexOf(provider) + 1;
        else return 0;
    }

    private void initProviders() {

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
            year = c.get(Calendar.YEAR);

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
    public void datePicker() {
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
    public void timePicker() {
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
    private void selectPickers() {
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


    public void createMarker() {
        address = provider.address.street;
        name = provider.title + " " + provider.firstName + " " + provider.lastName;
        //Update coords for mapview
        lat = Double.parseDouble(provider.address.latitude);
        lng = Double.parseDouble(provider.address.longitude);

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


    public void setMapScreen(Marker marker) {
        Projection projection = map.getProjection();
        LatLng markerLocation = marker.getPosition();
        Point screenPosition = projection.toScreenLocation(markerLocation);

        Point mappoint = map.getProjection().toScreenLocation(new LatLng(lat, lng));
        mappoint.set(mappoint.x, mappoint.y + 150);
        map.animateCamera(CameraUpdateFactory.newLatLng(map.getProjection().fromScreenLocation(mappoint)));
    }


    //Added by Tom 22-05-2015, for Question once Provider has been selected
    public void populateQuestions() {
        if (provider != null) {
            //First hide the textview questions_per_specialist"
            TextView tv = (TextView) findViewById(R.id.questions_per_specialist);
            tv.setVisibility(View.GONE);

            for (int i = 0; i < questions.size(); i++)

            {
                setTableRow(i);
            }
        } else {
            //TODO hide questions
        }
    }





    /*
     *Programmatically create table row for questions
     */
    public void setTableRow(int i) {
        TableLayout questionLayout = (TableLayout) findViewById(R.id.questions_table);
        TableRow row = new TableRow(this);
        row.setLayoutParams(lp);

        setTableQuestion(i);
        row.addView(providerQuestion);

        questionLayout.addView(row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }


    /*
     * To set textview for question to go in the Question Table
     */
    public void setTableQuestion(int i) {
        providerQuestion = new TextView(this);
        providerQuestion.setLayoutParams(lp);
        providerQuestion.setBackgroundColor(Color.WHITE);
        providerQuestion.setText(questions.get(i).name);
        //providerQuestion.setBackgroundDrawable(getResources().getDrawable(R.drawable.greenline));
    }
}

