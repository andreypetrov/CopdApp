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
 * TODO fix bug with sometimes showing in the end current time more than total (00.12/00:11)
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
    private int mCurrentPosition = 0;

    private PlayerState mCurrentPlayerState = PlayerState.IDLE;

    private OnDeleteListener mOnDeleteListener;
    private boolean mCanSeekBarBeUpdatedByTimer = true;

    public interface OnDeleteListener {
        void onDelete(String fileName);
    }


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
        View rootView = inflater.inflate(R.layout.fragment_voice_play, container, false);

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
                    pausePlaying();
                } else if (mCurrentPlayerState == PlayerState.PREPARED || mCurrentPlayerState == PlayerState.PAUSED) {
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
                    mTotalTimeView.setText(R.string.time_zero);
                    if (mOnDeleteListener != null) mOnDeleteListener.onDelete(mFileName);
                    mDeleteButton.setEnabled(false);
                    mPlayButton.setEnabled(false);
                }
            }
        });
    }



    private void initSeekBar(View rootView) {
        mSeekBar = (SeekBar) rootView.findViewById(R.id.seek_bar);
        mSeekBar.setTag(mCanSeekBarBeUpdatedByTimer);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { //disable updating of the seek bar by other sources, specifically the timer
                mCanSeekBarBeUpdatedByTimer = false;
                mSeekBar.setTag(mCanSeekBarBeUpdatedByTimer);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { // only adjust media once we have stopped dragging on the bar
                mCanSeekBarBeUpdatedByTimer = true;
                mSeekBar.setTag(mCanSeekBarBeUpdatedByTimer);

                int progressInMillis = ((int) mTrackDuration / 100) * seekBar.getProgress();
                mCurrentPosition = U.roundToSeconds(progressInMillis);
                updateTimer(mCurrentPosition);


                if (mCurrentPlayerState == PlayerState.STARTED) {  //if the record was playing then continue immediately playing
                    mPlayer.pause();
                    startPlaying();
                }
                //else adjust the head on the bar so that there won't be a flickering jump backwards
                //caused by the fact we are updating the bar only on round seconds
                else mSeekBar.setProgress(U.calculatePercentage(mCurrentPosition, (int) mTrackDuration));
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
                        mTrackDuration = (mPlayer.getDuration()/1000) * 1000; // get rounded down to a second duration
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

    /*private void updatePlayer(int progressInMillis) {
        updatePauseUi();

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
    }*/



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
        mPlayer.seekTo(mCurrentPosition);
        mPlayer.start();
        U.log(this, "set state to prepared: PlayerState.STARTED");
        mCurrentPlayerState = PlayerState.STARTED;
        startPlayerTimerTask(mCurrentPosition);
        mPlayButton.setImageResource(R.drawable.btn_pause);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                resetPlaying();
            }
        });
        mDeleteButton.setEnabled(false);
    }

    private void pausePlaying() {
        mPlayer.pause();
        mCurrentPosition = mPlayer.getCurrentPosition();
        updatePauseUi();
    }

    private void updatePauseUi() {
        mPlayButton.setImageResource(R.drawable.btn_play);
        U.log(this, "set state to prepared: PlayerState.PAUSED");
        mCurrentPlayerState = PlayerState.PAUSED;
        mDeleteButton.setEnabled(true);

        stopPlayerTimer();
    }

    /**
     * Same as pause playing except that the current position is changed to 0
     */
    private void resetPlaying() {
        mPlayer.pause();
        mCurrentPosition = 0;
        updatePauseUi();
    }


    /// TIMER METHODS


    /**
     * After one second of delay start updating the seconds
     */
//    private void startPlayerTimer() {
//        initPlayerTimerTask(mCurrentPosition);
//    }

    private void startPlayerTimerTask(final int initialTime) {

        if (mPlayerTimerTask != null) mPlayerTimerTask.cancel();

        mPlayerTimerTask = new TextViewTimerTask(initialTime, (int) mTrackDuration, getActivity(), mCurrentTimeView, mSeekBar);
        if (mTimer == null) mTimer = new Timer();
        mTimer.schedule(mPlayerTimerTask, 1000, 1000);
    }


    private void updateTimer(int timeInMillis) {
        mCurrentTimeView.setText(TimeAndDate.formatMinutesAndSeconds( U.roundToSeconds(timeInMillis)));
    }

    private void stopPlayerTimer() {
        if (mPlayerTimerTask != null) mPlayerTimerTask.cancel();
    }


    /// GETTERS AND SETTERS
    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.mOnDeleteListener = onDeleteListener;
    }
}
