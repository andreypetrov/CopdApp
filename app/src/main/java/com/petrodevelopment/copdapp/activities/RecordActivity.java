package com.petrodevelopment.copdapp.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.record.AudioRecorder;
import com.petrodevelopment.copdapp.util.U;

/**
 * Created by andrey on 10/05/2015.
 */
public class RecordActivity extends ActionBarActivity{
    AudioRecorder audioRecorder;
    boolean recording = false;
    boolean recorded = false;
    boolean playing = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initToolbar();
        initActionsMenu();
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initActionsMenu() {

    }

    public void onNoteClick (View v) {
        if (audioRecorder == null) audioRecorder = new AudioRecorder("test.3gp");

        if (!recording & !playing) {
            recording = true;
            recorded = false;
            audioRecorder.startRecording();
            U.log(this, "start recording");
        } else {
            recording = false;
            recorded = true;
            audioRecorder.stopRecording();
            U.log(this, "stop recording");
        }
    }

    public void onVoiceClick(View v) {
        if (recorded) {
            playing = true;
            audioRecorder.startPlaying();
        }
        else {
            playing = false;
            audioRecorder.stopPlaying();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (audioRecorder!= null) audioRecorder.releaseRecorderAndPlayer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
