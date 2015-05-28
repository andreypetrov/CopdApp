package com.petrodevelopment.copdapp.util;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.TimerTask;

/**
 * A task for creating a timer to update a given text field in the format MM:SS (minutes:seconds)
 * Created by andrey on 27/05/2015.
 */
public class TextViewTimerTask extends TimerTask {
    private int initialTimeInMillis;
    private int totalTimeInMillis;
    private int timeInMillis;
    private Activity activity;
    private TextView targetView;
    private ProgressBar progressBar;

    public TextViewTimerTask(int initialTimeInMillis, int totalTimeInMillis, Activity activity, TextView targetView, ProgressBar progressBar) {

        this.totalTimeInMillis = (totalTimeInMillis / 1000) * 1000;     //rounding down to the closest second
        this.initialTimeInMillis = (initialTimeInMillis / 1000) * 1000; //rounding down to the closest second

        this.timeInMillis = this.initialTimeInMillis;
        this.activity = activity;
        this.targetView = targetView;
        this.targetView.setText(TimeAndDate.formatMinutesAndSeconds(initialTimeInMillis));

        if (progressBar != null) {
            this.progressBar = progressBar;
            this.progressBar.setProgress(U.calculatePercentage(initialTimeInMillis, totalTimeInMillis));
        }
    }

    @Override
    public void run() {
        timeInMillis += 1000; //add a second
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                targetView.setText(TimeAndDate.formatMinutesAndSeconds(timeInMillis));
                if (progressBar != null && ((Boolean)progressBar.getTag())) { // if it is not null and it can be updated
                    progressBar.setProgress(U.calculatePercentage(timeInMillis, totalTimeInMillis));
                }
            }
        });

    }
}
