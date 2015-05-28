package com.petrodevelopment.copdapp.record.fragments;

import android.media.MediaPlayer;
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
import com.petrodevelopment.copdapp.util.TextViewTimerTask;
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
public class VoicePlayFragment extends SectionFragment {
    private static String mFileName;

    private ImageView mPlayButton;
    private ImageView mDeleteButton;
    private SeekBar mSeekBar;

    private TextView mCurrentTimeView;
    private TextView mTotalTimeView;

    private MediaPlayer mPlayer;
    private Timer mTimer;
    private TimerTask mPlayerTimerTask;

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
    public static VoicePlayFragment newInstance(int sectionNumber, String sectionTitle) {
        VoicePlayFragment fragment = new VoicePlayFragment();
        SectionFragment.addSectionAndTitleToFragment(fragment, sectionNumber, sectionTitle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_voice_record, container, false);

        setFileName(Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.3gp");
        initView(rootView);
        initMediaPlayerAndButtons();

        return rootView;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    private void initView(View rootView) {
        mCurrentTimeView = (TextView) rootView.findViewById(R.id.current_time);
        mTotalTimeView = (TextView) rootView.findViewById(R.id.total_time);

        initSeekBar(rootView);
        initPlayButton(rootView);
        initDeleteButton(rootView);
    }




    private void updateUi(long currentMillis) {


    }


    private void initPlayButton(View rootView) {
        mPlayButton = (ImageView) rootView.findViewById(R.id.play_btn);
        mPlayButton.setEnabled(false);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                U.log(this, "play button pressed");
                U.log(this, "Player State: " + mCurrentPlayerState);
                if (mCurrentPlayerState == PlayerState.STARTED) {
                    stopPlaying();
                } else if (mCurrentPlayerState == PlayerState.PREPARED) {
                    startPlaying();
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
                    mCurrentTimeView.setText(R.string.time_zero);
                    mDeleteButton.setEnabled(false);
                    mPlayButton.setEnabled(false);
                }
            }
        });
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
//                updateTimer(progressInMillisRounded);
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



    private void initMediaPlayerAndButtons() {
        mPlayer = new MediaPlayer();
        U.log(this, "set state to prepared: PlayerState.IDLE");
        mCurrentPlayerState = PlayerState.IDLE;

        File file = new File(mFileName);
        if (file.exists()) {
            try {
                mPlayer.setDataSource(mFileName);
                U.log(this, "set state to prepared: PlayerState.INITIALIZED");
                mCurrentPlayerState = PlayerState.INITIALIZED;
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mTrackDuration = mPlayer.getDuration();
                        mTotalTimeView.setText(TimeAndDate.formatMinutesAndSeconds(mTrackDuration));
                        if (mCurrentPlayerState != PlayerState.STARTED) {
                            U.log(this, "set state to prepared: PlayerState.PREPARED");
                            mCurrentPlayerState = PlayerState.PREPARED;
                        }
                    }
                });
                preparePlayer();
            } catch (IOException e) {
                mPlayButton.setEnabled(false);
                //TODO: show error message?
                U.log(this, "prepare() failed");
            }
        } else {
            //TODO: show error message?
            mDeleteButton.setEnabled(false);
            mPlayButton.setEnabled(false);
        }
    }

    private void updateTime(long currentTime) {
        float progress = ((float) currentTime * 100) / (float) mTrackDuration;
        U.log(this, "update progress: " + progress);
        mSeekBar.setProgress(Math.round(progress));
    }









    private void updatePlayer(int progressInMillis) {
        if (mCurrentPlayerState == PlayerState.STARTED) {
            mPlayer.pause();
            mPlayer.seekTo(progressInMillis);
            mPlayer.start();
        } else if (mCurrentPlayerState == PlayerState.INITIALIZED || mCurrentPlayerState == PlayerState.STOPPED) {
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



    @Override
    public void onPause() {
        super.onPause();

        if (mPlayer != null) {
            mPlayer.release();
            U.log(this, "set state to prepared: PlayerState.END");
            mCurrentPlayerState = PlayerState.END;
            mPlayer = null;
        }
        stopPlayerTimer();
    }

    private void preparePlayer() {
        try {
            mPlayer.prepare();
        } catch (IOException e) {
            mPlayButton.setEnabled(false);
        }
        mPlayButton.setEnabled(true);
    }



    private void startPlaying() {
        mPlayer.start();
        U.log(this, "set state to prepared: PlayerState.STARTED");
        mCurrentPlayerState = PlayerState.STARTED;

        //mPlayButton.setText(R.string.stop);
        mDeleteButton.setEnabled(false);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //mPlayButton.setText(R.string.play);
                stopPlaying();
            }
        });
    }

    private void pausePlaying() {
        mPlayer.pause();
        U.log(this, "set state to prepared: PlayerState.PAUSED");
        mCurrentPlayerState = PlayerState.PAUSED;
        mDeleteButton.setEnabled(true);
    }

    private void stopPlaying() {
        mPlayer.stop();
        U.log(this, "set state to prepared: PlayerState.STOPPED");
        mCurrentPlayerState = PlayerState.STOPPED;
        mDeleteButton.setEnabled(true);
        preparePlayer();
    }


    /// TIMER METHODS


    /**
     * After one second of delay start updating the seconds
     */
    private void startPlayerTimer() {
        mCurrentTimeView.setText((R.string.time_zero));
        initPlayerTimerTask(0);
    }

    private void initPlayerTimerTask(final long initialTime) {
        if (mPlayerTimerTask != null) mPlayerTimerTask.cancel();

        mPlayerTimerTask = new TextViewTimerTask(initialTime, getActivity(), mCurrentTimeView);
        if (mTimer == null) mTimer = new Timer();
        mTimer.schedule(mPlayerTimerTask, 1000, 1000);
    }

//    private void updateTimer(long timeInMilliseconds) {
//        mCurrentTimeView.setText(TimeAndDate.formatMinutesAndSeconds(timeInMilliseconds));
//        initRecordTimerTask(timeInMilliseconds);
//    }

    private void stopPlayerTimer() {
        if (mPlayerTimerTask != null) mPlayerTimerTask.cancel();
    }


}
