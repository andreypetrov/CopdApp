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
import android.widget.TextView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.fragments.AppointmentsFragment;
import com.petrodevelopment.copdapp.fragments.SectionFragment;
import com.petrodevelopment.copdapp.util.TimeAndDate;
import com.petrodevelopment.copdapp.util.U;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Fragment for voice recording and play
 * TODO add total time and current time and a bar to navigate to any time within the file?
 * Created by user on 15-05-14.
 */
public class VoiceRecordFragment extends SectionFragment {
    private static String mFileName;
    private Button mRecordButton;
    private Button mPlayButton;
    private Button mDeleteButton;


    private TextView mTimeTextView;

    private boolean isRecording = false;
    private boolean isPlaying = false;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private Timer mTimer;
    private TimerTask mTimerTask;


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

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.3gp";
        mTimer = new Timer();

        initView(rootView);
        initMediaPlayerAndButtons();

        return rootView;
    }


    private void initView(View rootView) {
        mTimeTextView = (TextView) rootView.findViewById(R.id.time);

        initRecordButton(rootView);
        initPlayButton(rootView);
        initDeleteButton(rootView);
    }

    private void initRecordButton(View rootView) {
        mRecordButton = (Button) rootView.findViewById(R.id.record_btn);
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    stopRecording();
                    stopTimer();
                    mRecordButton.setText(R.string.record);
                    mPlayButton.setEnabled(true);
                    mDeleteButton.setEnabled(true);
                    initMediaPlayerAndButtons();
                } else {
                    startRecording();
                    startTimer();
                    mRecordButton.setText(R.string.stop);
                    mPlayButton.setEnabled(false);
                    mDeleteButton.setEnabled(false);

                }

                isRecording = !isRecording;
            }
        });
    }

    /**
     * After one second of delay start updating the seconds
     */
    private void startTimer() {
        mTimeTextView.setText(R.string.time_zero);

        mTimerTask = new TimerTask() {
            long timeInMilliseconds = 0;
            @Override
            public void run() {
                timeInMilliseconds+=1000;
                //refresh your textview
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTimeTextView.setText(TimeAndDate.formatMinutesAndSeconds(timeInMilliseconds));
                    }
                });
            }
        };
        mTimer.schedule(mTimerTask, 1000, 1000);
    }

    private void stopTimer() {
        mTimerTask.cancel();
    }



    private void initPlayButton(View rootView) {
        mPlayButton = (Button) rootView.findViewById(R.id.play_btn);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    stopPlaying();
                    mPlayButton.setText(R.string.play);
                    mRecordButton.setEnabled(true);
                    mDeleteButton.setEnabled(true);
                } else {
                    startPlaying();
                    mPlayButton.setText(R.string.stop);
                    mRecordButton.setEnabled(false);
                    mDeleteButton.setEnabled(false);
                }

                isPlaying = !isPlaying;
            }
        });
    }


    private void initDeleteButton(View rootView) {
        mDeleteButton = (Button) rootView.findViewById(R.id.delete_btn);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(mFileName);
                if (file.exists()) {
                    file.delete();
                    mTimeTextView.setText(R.string.time_zero);
                    mDeleteButton.setEnabled(false);
                    mPlayButton.setEnabled(false);
                    mRecordButton.setEnabled(true);
                }
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



    private void preparePlayer() {
        try {
            mPlayer.prepare();
        } catch (IOException e) {
            mPlayButton.setEnabled(false);
        }
        mPlayButton.setEnabled(true);
    }

    private void initMediaPlayerAndButtons() {
        mPlayer = new MediaPlayer();

        File file = new File(mFileName);
        if (file.exists()) {
            try {
                mPlayer.setDataSource(mFileName);
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mTimeTextView.setText(TimeAndDate.formatMinutesAndSeconds(mPlayer.getDuration()));
                    }
                });
                preparePlayer();
//                mPlayer.prepare();
//                mPlayButton.setEnabled(true);
            } catch (IOException e) {
                mPlayButton.setEnabled(false);
                U.log(this, "prepare() failed");
            }
        } else {
            mDeleteButton.setEnabled(false);
            mPlayButton.setEnabled(false);
        }
    }


    private void startPlaying() {
        initMediaPlayerAndButtons();
        mPlayer.start();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying = false;
                mPlayButton.setText(R.string.play);
                mRecordButton.setEnabled(true);
                mDeleteButton.setEnabled(true);
                stopPlaying();
            }
        });
    }

    private void stopPlaying() {
        mPlayer.stop();
        preparePlayer();
    }


    private void deleteFile() {

    }

}
