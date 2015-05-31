package com.petrodevelopment.copdapp.record.fragments;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.fragments.SectionFragment;
import com.petrodevelopment.copdapp.util.TextViewTimerTask;
import com.petrodevelopment.copdapp.util.U;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Fragment for voice recording and play
 * Created by user on 15-05-14.
 */
public class VoiceRecordFragment extends SectionFragment {
    public static final String FILE_NAME ="/audiorecordtest.3gp";
    private static String mFileName;
    private ImageButton mRecordButton;
    private TextView mTimeTextView;
    private boolean isRecording = false;
    private MediaRecorder mRecorder;
    private Timer mTimer;
    private TimerTask mRecordTimerTask;


    private OnStopListener mOnStopListener;

    public interface OnStopListener {
        void onStop(String fileName);
    }



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
        View rootView = inflater.inflate(R.layout.fragment_voice_record, container, false);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + FILE_NAME;

        mTimer = new Timer();
        initView(rootView);
        return rootView;
    }


    private void initView(View rootView) {
        mTimeTextView = (TextView) rootView.findViewById(R.id.time);
        initRecordButton(rootView);
    }


    private void initRecordButton(View rootView) {
        mRecordButton = (ImageButton) rootView.findViewById(R.id.record_btn);
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    stopRecording();
                } else {
                    startRecording();
                }
                isRecording = !isRecording;
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        stopRecording();
    }

    public void deleteTrack() {
        File file = new File(mFileName);
        if (file.exists()) file.delete();
    }



    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
            mRecorder.start();
            mRecordButton.setImageResource(R.drawable.ic_stop_active);
            mRecordButton.setContentDescription(getString(R.string.stop));
            startRecordTimerTask();
        } catch (IOException e) {
            U.log(this, "prepare() failed");
        }

    }

    private void stopRecording() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            mRecordButton.setImageResource(R.drawable.ic_record);
            mRecordButton.setContentDescription(getString(R.string.record));
            if (mOnStopListener != null) mOnStopListener.onStop(mFileName);
            stopRecordTimer();
        }
    }


    /// TIMER METHODS

    private void startRecordTimerTask() {
        if (mRecordTimerTask != null) mRecordTimerTask.cancel();
        mRecordTimerTask = new TextViewTimerTask(0, 0, getActivity(), mTimeTextView, null);
        mTimer.schedule(mRecordTimerTask, 1000, 1000);
    }

    private void stopRecordTimer() {
        if (mRecordTimerTask != null) mRecordTimerTask.cancel();
    }

    //GETTERS AND SETTERS

    public void setOnStopListener(OnStopListener onStopListener) {
        mOnStopListener = onStopListener;
    }
}
