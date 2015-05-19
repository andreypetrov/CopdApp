package com.petrodevelopment.copdapp.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 15-05-19.
 */
public class TimeAndDate {

    /**
     * Format milliseconds into minutes and seconds. e.g. five minutes and twenty three seconds would be formatted as 05:23
     * @param millis
     * @return
     */
    public static String formatMinutesAndSeconds(long millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }
}
