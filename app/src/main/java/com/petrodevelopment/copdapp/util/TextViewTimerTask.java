package com.petrodevelopment.copdapp.util;

import android.app.Activity;
import android.widget.TextView;

import java.util.TimerTask;

/**
 * A task for creating a timer to update a given text field in the format MM:SS (minutes:seconds)
 * Created by andrey on 27/05/2015.
 */
public class TextViewTimerTask extends TimerTask {
    public long initialTimeInMillis;
    public long timeInMillis;
    public Activity activity;
    public TextView targetView;

    public TextViewTimerTask(long initialTimeInMillis, Activity activity, TextView targetView) {
        //rounding down to the closest second
        this.initialTimeInMillis = (initialTimeInMillis/1000)*1000;
        this.timeInMillis = this.initialTimeInMillis;
        this.activity = activity;
        this.targetView = targetView;
        this.targetView.setText(TimeAndDate.formatMinutesAndSeconds(initialTimeInMillis));
    }

    @Override
    public void run() {
        timeInMillis += 1000;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                targetView.setText(TimeAndDate.formatMinutesAndSeconds(timeInMillis));
            }
        });
    }
}
