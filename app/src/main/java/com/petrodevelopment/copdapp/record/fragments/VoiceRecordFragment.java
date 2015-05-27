package com.petrodevelopment.copdapp.record.fragments;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.petrodevelopment.copdapp.R;
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
    private ImageView mRecordButton;
    private ImageView mPlayButton;
    private ImageView mDeleteButton;
    private SeekBar mSeekBar;


    private TextView mTimeTextView;

    private boolean isRecording = false;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private Timer mTimer;
    private TimerTask mTimerTask;

    private long mTrackDuration = 0l;

    private PlayerState mCurrentPlayerState = PlayerState.IDLE;

    private enum PlayerState { //corresponds to the diagram at http://developer.android.com/reference/android/media/MediaPlayer.html
        IDLE,
        INITIALIZED,
        PREPARED,
        STARTED,
        PAUSED,
        STOPPED,
        END
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
        View rootView = inflater.inflate(R.layout.fragment_record_voice, container, false);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.3gp";
        mTimer = new Timer();

        initView(rootView);
        initMediaPlayerAndButtons();

        return rootView;
    }


    private void initView(View rootView) {
        mTimeTextView = (TextView) rootView.findViewById(R.id.time);
        initSeekBar(rootView);
        initRecordButton(rootView);
        initPlayButton(rootView);
        initDeleteButton(rootView);
    }

    private void initSeekBar(View rootView) {
        mSeekBar = (SeekBar) rootView.findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                U.log(this, "From user: " + fromUser + ", progress: " + progress);
                long progressInMillis = (mTrackDuration / 100) * progress;
                U.log(this, "From user: " + fromUser + ", progressInMillis: " + progressInMillis);
                long progressInMillisRounded = U.roundToSeconds(progressInMillis);
                updateTimer(progressInMillisRounded);
                updatePlayer((int) progressInMillisRounded);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void updatePlayer(int progressInMillis) {
        if (mCurrentPlayerState == PlayerState.STARTED) {
            mPlayer.pause();
            mPlayer.seekTo(progressInMillis);
            mPlayer.start();
        } else if (mCurrentPlayerState == PlayerState.INITIALIZED || mCurrentPlayerState == PlayerState.STOPPED){
            try {
                mPlayer.prepare();
                mPlayer.seekTo(progressInMillis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mPlayer.seekTo(progressInMillis);
        }
    }

    private void initRecordButton(View rootView) {
        mRecordButton = (ImageView) rootView.findViewById(R.id.record_btn);
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    stopRecording();
                    stopTimer();
                    //mRecordButton.setText(R.string.record);
                    mPlayButton.setEnabled(true);
                    mDeleteButton.setEnabled(true);
                    initMediaPlayerAndButtons();
                } else {
                    startRecording();
                    startTimer();
                    //mRecordButton.setText(R.string.stop);
                    mPlayButton.setEnabled(false);
                    mDeleteButton.setEnabled(false);

                }

                isRecording = !isRecording;
            }
        });
    }



    private void initPlayButton(View rootView) {
        mPlayButton = (ImageView) rootView.findViewById(R.id.play_btn);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                U.log(this, "play button pressed");
                U.log(this, "Player State: " + mCurrentPlayerState);

                if (mCurrentPlayerState == PlayerState.STARTED) {
                    stopPlaying();
                    //mPlayButton.setText(R.string.play);
                    mRecordButton.setEnabled(true);
                    mDeleteButton.setEnabled(true);
                } else {
                    startPlaying();
                    //mPlayButton.setText(R.string.stop);
                    mRecordButton.setEnabled(false);
                    mDeleteButton.setEnabled(false);
                }
            }
        });
    }


    private void initDeleteButton(View rootView) {
        mDeleteButton = (ImageView) rootView.findViewById(R.id.delete_btn);
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
            mCurrentPlayerState = PlayerState.END;
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
        mCurrentPlayerState = PlayerState.IDLE;

        File file = new File(mFileName);
        U.log(this, "player state: " + mCurrentPlayerState.name());

        if (file.exists() && (mCurrentPlayerState == PlayerState.IDLE || mCurrentPlayerState == PlayerState.STOPPED)) {
            try {
                mPlayer.setDataSource(mFileName);
                mCurrentPlayerState = PlayerState.INITIALIZED;
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mTrackDuration = mPlayer.getDuration();
                        mTimeTextView.setText(TimeAndDate.formatMinutesAndSeconds(mTrackDuration));
                        mCurrentPlayerState = PlayerState.PREPARED;
                    }
                });
                preparePlayer();
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
                //mPlayButton.setText(R.string.play);
                mRecordButton.setEnabled(true);
                mDeleteButton.setEnabled(true);
                stopPlaying();
            }
        });
    }

    private void stopPlaying() {
        mPlayer.stop();
        mCurrentPlayerState = PlayerState.STOPPED;

        preparePlayer();
    }





    /// TIMER METHODS


    /**
     * After one second of delay start updating the seconds
     */
    private void startTimer() {
        mTimeTextView.setText(R.string.time_zero);
        initTimerTask(0);
    }


    private void initTimerTask(final long initialTime) {
        if (mTimerTask != null) mTimerTask.cancel();
        mTimerTask = new TimerTask() {
            long timeInMilliseconds = initialTime;
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


    private void updateTimer(long timeInMilliseconds) {
        mTimeTextView.setText(TimeAndDate.formatMinutesAndSeconds(timeInMilliseconds));
        initTimerTask(timeInMilliseconds);
    }


    private void stopTimer() {
        mTimerTask.cancel();
    }



}
