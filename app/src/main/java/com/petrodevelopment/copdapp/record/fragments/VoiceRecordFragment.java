package com.petrodevelopment.copdapp.record.fragments;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.fragments.AppointmentsFragment;
import com.petrodevelopment.copdapp.fragments.SectionFragment;
import com.petrodevelopment.copdapp.util.U;

import java.io.IOException;

/**
 * Fragment for voice recording and play
 * TODO figure out whether we need to allow creation of more than one record
 * Created by user on 15-05-14.
 */
public class VoiceRecordFragment extends SectionFragment {
    private static String mFileName;
    private Button mRecordButton;
    private Button mPlayButton;

    private boolean isRecording = false;
    private boolean isPlaying = false;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static VoiceRecordFragment newInstance(int sectionNumber, String sectionTitle) {
        VoiceRecordFragment fragment = new VoiceRecordFragment();
        SectionFragment.addSectionAndTitleToFragment(fragment, sectionNumber, sectionTitle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_record_voice, container, false);

        initView(rootView);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.3gp";


        return rootView;
    }

    private void initView(View rootView) {
        initRecordButton(rootView);
        initPlayButton(rootView);
    }

    private void initRecordButton(View rootView) {
        mRecordButton = (Button) rootView.findViewById(R.id.record_btn);
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    stopRecording();
                    mRecordButton.setText(R.string.record);
                    mPlayButton.setEnabled(true);
                }
                else {
                    startRecording();
                    mRecordButton.setText(R.string.stop);
                    mPlayButton.setEnabled(false);
                }

                isRecording = !isRecording;
            }
        });
    }


    private void initPlayButton(View rootView) {
        mPlayButton = (Button) rootView.findViewById(R.id.play_btn);
        mPlayButton.setEnabled(false); //TODO replace is with a check whether we have already a file or not
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    stopPlaying();
                    mPlayButton.setText(R.string.play);
                    mRecordButton.setEnabled(true);
                } else {
                    startPlaying();
                    mPlayButton.setText(R.string.stop);
                    mRecordButton.setEnabled(false);
                }

                isPlaying = !isPlaying;
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }


    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            U.log(this, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    isPlaying = false;
                    mPlayButton.setText(R.string.play);
                }
            });
        } catch (IOException e) {
            U.log(this, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

}
