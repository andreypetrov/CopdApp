package com.petrodevelopment.copdapp.record;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.activities.BaseActivity;
import com.petrodevelopment.copdapp.model.AppointmentRecord;
import com.petrodevelopment.copdapp.record.fragments.GalleryPreviewFragment;
import com.petrodevelopment.copdapp.record.fragments.VoicePlayFragment;
import com.petrodevelopment.copdapp.record.fragments.VoiceRecordFragment;
import com.petrodevelopment.copdapp.util.U;

import java.io.File;

/**
 *
 * Record a note, an audio and images
 * Created by andrey on 10/05/2015.
 */
public class RecordActivity extends BaseActivity {
//    AudioRecorder audioRecorder;
//    boolean recording = false;
//    boolean recorded = false;
//    boolean playing = false;
    private  String appointmentId;
    private AppointmentRecord appointmentRecord;

    VoiceRecordFragment voiceRecordFragment;
    VoicePlayFragment voicePlayFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initModel();
        initToolbar();
        initVoice();
        initGallery();
    }

    @Override
    public void initModel() {
        appointmentId = getIntent().getStringExtra(MainApplication.APPOINTMENT_ID_EXTRA);
        appointmentRecord = getApp().getAppointment(appointmentId).severity; //TODO make this dynamic
    }

    private void initVoice(){
        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.3gp";
        File file = new File(fileName);
        if (file.exists()) {
            initVoicePlayFragment();
        } else {
            initVoiceRecordFragment();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initVoiceRecordFragment() {
        voiceRecordFragment =  new VoiceRecordFragment();
        voiceRecordFragment.setOnStopListener(new VoiceRecordFragment.OnStopListener() {
            @Override
            public void onStop(String fileName) {
                U.log(this, "FILE CREATED: " + fileName);
                initVoicePlayFragment();
            }
        });
        replaceFragment(R.id.voice_container, voiceRecordFragment);
    }

    private void initVoicePlayFragment() {
        voicePlayFragment =  new VoicePlayFragment();
        voicePlayFragment.setOnDeleteListener(new VoicePlayFragment.OnDeleteListener() {
            @Override
            public void onDelete(String fileName) {
                U.log(this, "FILE DELETED: " + fileName);
                initVoiceRecordFragment();
            }
        });
        replaceFragment(R.id.voice_container, voicePlayFragment);
    }


    private void initGallery(){
        GalleryPreviewFragment fragment = (GalleryPreviewFragment) getFragmentManager().findFragmentById(R.id.gallery_preview_fragment);
        fragment.updateImages(appointmentRecord);
        fragment.setOnGalleryClickListener(new GalleryPreviewFragment.OnGalleryClickListener() {
            @Override
            public void onImageClick(int position) {
                U.log(this, "image clicked at position: " + position);
            }
        });
    }


    public void onCameraClick(View view) {
        U.log(this, "on camera click open new taking picture activity with special intent");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.record_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                saveAndClose();
                return true;
            case R.id.action_delete:
                deleteAndClose();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void saveModel() {
        U.log(this, "save appointment record");
    }

    @Override
    public void deleteModel() {
        U.log(this, "delete appointment record");
    }


    @Override
    public void onBackPressed() {
        saveAndClose();
    }



    public void onNoteClick(View v) {

    }

    public void onVoiceClick(View v) {

    }

    public void onPhotoClick(View v) {

    }

    public void onReminderClick(View v) {

    }




}
