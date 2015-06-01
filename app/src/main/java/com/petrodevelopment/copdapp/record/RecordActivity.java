package com.petrodevelopment.copdapp.record;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.activities.BaseActivity;
import com.petrodevelopment.copdapp.gallery.GalleryActivity;
import com.petrodevelopment.copdapp.model.Appointment;
import com.petrodevelopment.copdapp.model.AppointmentRecord;
import com.petrodevelopment.copdapp.record.fragments.GalleryPreviewFragment;
import com.petrodevelopment.copdapp.record.fragments.VoicePlayFragment;
import com.petrodevelopment.copdapp.record.fragments.VoiceRecordFragment;
import com.petrodevelopment.copdapp.util.U;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Record a note, an audio and images
 * Created by andrey on 10/05/2015.
 */
public class RecordActivity extends BaseActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    //Model
    private String appointmentId;
    private Appointment appointment;
    private String appointmentRecordCategoryId;
    private AppointmentRecord appointmentRecord;

    private boolean isRecordingVisible = false;

    private VoiceRecordFragment voiceRecordFragment;
    private VoicePlayFragment voicePlayFragment;
    private GalleryPreviewFragment galleryPreviewFragment;
    private ScrollView scrollView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        initNote();
        initModel();
        initToolbar();
        initVoice();
        initGallery();
    }

    private void initNote() {
        EditText note = (EditText) findViewById(R.id.note);
        note.setText(appointmentRecord.note);
        note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                appointmentRecord.note = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void initModel() {
        appointmentId = getIntent().getStringExtra(MainApplication.APPOINTMENT_ID_EXTRA);
        appointment = getModelFacade().getAppointment(appointmentId);
        appointmentRecordCategoryId = getIntent().getStringExtra(MainApplication.APPOINTMENT_RECORD_CATEGORY_ID);
        appointmentRecord = getModelFacade().getAppointmentRecord(appointmentRecordCategoryId, appointment);
    }


    private void initVoice() {

        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.3gp";
        File file = new File(fileName);
        if (file.exists()) {
            initVoicePlayFragment();
        }
    }
    
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(appointmentRecord.getAppointmentRecordCategory(this).name);
    }

    private void initVoiceRecordFragment() {
        voiceRecordFragment = new VoiceRecordFragment();
        voiceRecordFragment.setOnStopListener(new VoiceRecordFragment.OnStopListener() {
            @Override
            public void onStop(String fileName) {
                U.log(this, "FILE CREATED: " + fileName);
                initVoicePlayFragment();
            }
        });
        replaceFragment(R.id.voice_record_container, voiceRecordFragment);
        removeFragment(voicePlayFragment);
        isRecordingVisible = true;
    }

    private void initVoicePlayFragment() {
        voicePlayFragment = new VoicePlayFragment();
        voicePlayFragment.setOnDeleteListener(new VoicePlayFragment.OnDeleteListener() {
            @Override
            public void onDelete(String fileName) {
                U.log(this, "FILE DELETED: " + fileName);
                removeFragment(voicePlayFragment);
            }
        });
        replaceFragment(R.id.voice_play_container, voicePlayFragment);
        removeFragment(voiceRecordFragment);
        isRecordingVisible = false;
    }

    /**
     * If we stop recording, make sure you show the correct fragment if it is needed.
     */
    public void removeRecordFragmentOrShowPlayFragmentIfNeeded () {
        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + VoiceRecordFragment.FILE_NAME;
        File file = new File(fileName);
        if (file.exists()) {
            initVoicePlayFragment();
        } else {
            removeFragment(voiceRecordFragment);
            isRecordingVisible = false;
        }
    }

    private void initGallery() {
        galleryPreviewFragment = (GalleryPreviewFragment) getFragmentManager().findFragmentById(R.id.gallery_preview_fragment);
        galleryPreviewFragment.initImages(appointmentRecord);
        galleryPreviewFragment.setOnGalleryClickListener(new GalleryPreviewFragment.OnGalleryClickListener() {
            @Override
            public void onImageClick(int position) {
                startGalleryActivity(position);
            }
        });
    }


    private void startGalleryActivity(int imagePosition) {
        Intent intent = new Intent(RecordActivity.this, GalleryActivity.class);
        intent.putExtra(MainApplication.APPOINTMENT_ID_EXTRA, appointmentId);
        intent.putExtra(MainApplication.APPOINTMENT_RECORD_CATEGORY_ID, appointmentRecord.getAppointmentRecordCategory(this).name);
        intent.putExtra(MainApplication.IMAGE_INDEX_EXTRA, imagePosition);
        startActivity(intent);
    }

    public void onMicrophoneClick(View view) {
        //TODO figure out whether it is safe to remove fragment while the recording is still hapenning
        if (isRecordingVisible) removeRecordFragmentOrShowPlayFragmentIfNeeded();
        else initVoiceRecordFragment();
    }

    public void onCameraClick(View view) {
        PackageManager packageManager = getPackageManager();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
        U.log(this, "on camera click open new taking picture activity with special intent");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "Title", null);
            String url = Uri.parse(path).toString();
            appointmentRecord.imageUrls.add(url);
            galleryPreviewFragment.update();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.record_menu, menu);
        return true;
    }

    //@Override
    public void saveModel() {
        U.log(this, "save appointment record");
    }

    //@Override
    public void deleteModel() {
        U.log(this, "delete appointment record");
    }


}
