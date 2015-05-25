package com.petrodevelopment.copdapp.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

public class U {

    /**
     * A convenience debug method. Logs an info message in the LogCat. The log tag is the name of the class generating it (without the package) Usage: U.log(this, message);
     *
     * @param message
     */
    public static void log(Object author, String message) {
        Log.i(author.getClass().getSimpleName(), message);
    }

    public static void log(Object author, boolean b) {
        Log.i(author.getClass().getSimpleName(), String.valueOf(b));
    }

    public static void log(Object author, Object object) {
        String message = (object == null ? "null" : object.toString());
        log(author, message);
    }

    public static void log(Object author, int[] array) {
        for (int i = 0; i < array.length; i++) {
            U.log(author, i + ": " + array[i]);
        }
    }

    public static void log(Object author, float[] array) {
        for (int i = 0; i < array.length; i++) {
            U.log(author, i + ": " + array[i]);
        }
    }

    public static void log(Object author, Object[] array) {
        for (int i = 0; i < array.length; i++) {
            U.log(author, i + ": " + (array[i] == null ? "null" : array[i].toString()));
        }
    }

    public static void log(Object author, String[] array) {
        for (int i = 0; i < array.length; i++) {
            U.log(author, i + ": " + array[i]);
        }
    }

    /**
     * For logging longer strings that are otherwise trimmed by the logcat utility
     *
     * @param author
     * @param str
     */
    public static void longLog(Object author, String str) {
        if (str.length() > 4000) {
            U.log(author, str.substring(0, 4000));
            longLog(author, str.substring(4000));
        } else log(author, str);
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenWidthInDp(Activity activity) {
        int px = U.getScreenWidth(activity);
        int dp = (int) (px / Resources.getSystem().getDisplayMetrics().density);
        return dp;
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * Get percentage of screen height
     *
     * @param activity
     * @param percentage (from 0 to 100)
     * @return
     */
    public static int getScreenHeightFromPercentageToPx(Activity activity, int percentage) {
        if (percentage < 0 || percentage > 100) throw new IllegalArgumentException("percentage can be only between 0 and 100");

        int screenHeight = getScreenHeight(activity);
        return Math.round((float) screenHeight * percentage / 100);
    }

    /**
     * Convert density independent pixels to native actual pixels
     *
     * @param dp
     * @return
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * Convert native actual pixels to density independent pixels
     *
     * @param px
     * @return
     */
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * Measure the new wrap_content height for the view FIXME: not big enough sometimes!!
     *
     * @param activity
     * @param view
     * @return
     */
    public static int getHeightForWrapContent(Activity activity, View view) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        View parent = (View) view.getParent();
        int screenWidth = metrics.widthPixels;
        int parentWidth = parent.getMeasuredWidth();

        // Assume that the view is spanning over the whole parent width. If this is not the case, then this calculation will be wrong
        int newWidth = (parentWidth > 0 ? parentWidth : screenWidth);

        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        U.log("getHeightForWrapContent", "parent width: " + parentWidth);
        U.log("getHeightForWrapContent", "screen width: " + screenWidth);

        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY);

        view.measure(widthMeasureSpec, heightMeasureSpec);
        int height = view.getMeasuredHeight();
        return height;
    }

    /**
     * Reflection method to extract a field from an object by name. T is the return type of the object. The method depends on the fact that its return value is assigned to a
     * variable from the correct type.
     *
     * @param objectInstance
     * @param fieldName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Object objectInstance, String fieldName) {
        Class<? extends Object> aClass = objectInstance.getClass();
        try {
            Field field = aClass.getField(fieldName);
            try {
                Object fieldValue = field.get(objectInstance);
                return (T) fieldValue;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reflection method to set a field from an object by name. The method depends on the fact that its set value is assigned to a variable from the correct type.
     *
     * @param objectInstance
     * @param fieldName
     * @param fieldValue
     */
    public static void set(Object objectInstance, String fieldName, Object fieldValue) {
        Class<? extends Object> aClass = objectInstance.getClass();
        try {
            Field field = aClass.getField(fieldName);
            try {
                field.set(objectInstance, fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the min and max boundaries for the graph as a point object - min being Point.x, and max being Point.y. We get a wrapper range around the data range, so that all
     * points are within and the interval can be split in 3 even parts, divisible by 10
     *
     * @param data
     * @return Point(minBoundary, maxBoundary);
     */
    public static Point calculateBoundaries(Integer[] data) {

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < data.length; i++) {
            if (data[i] < min) min = data[i];
            if (data[i] > max) max = data[i];
        }

        int reminderMin = min % 10; // e.g. for min 16, reminder is 6, for min = 4, is 4, and for min = -11 is -1
        int minRoundedDown;
        if (min > 0) minRoundedDown = min - reminderMin;
        else minRoundedDown = min - 10 - reminderMin;

        int reminderMax = max % 10;
        int maxRoundedUp;
        if (max > 0) maxRoundedUp = max + 10 - reminderMax;
        else maxRoundedUp = max - reminderMax;
        // so now if we started with the interval [-11,26], we have [-20, 30]
        // next we make sure the interval can be evenly divided in 3 intervals, still being rounded to ten, meaning the interval should be divisable by 30

        int range = Math.abs(maxRoundedUp - minRoundedDown);
        int rangeReminder = range % 30;

        // if the rangeReminder is not 0, then do the following
        if (rangeReminder == 20) minRoundedDown -= 10; // subtract ten from the lower bound
        else if (rangeReminder == 10) {
            minRoundedDown -= 10;
            maxRoundedUp += 10;
        }
        Point result = new Point(minRoundedDown, maxRoundedUp);
        return result;
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    /**
     * Convert an array of float values written as strings into a rounded integer array
     *
     * @param data
     * @return
     */
    public static Integer[] convertStringArrayToIntegerArray(String[] data) {
        Integer[] dataAsNumber = new Integer[data.length];
        for (int i = 0; i < data.length; i++) {
            dataAsNumber[i] = Math.round(Float.valueOf(data[i]));
        }
        return dataAsNumber;
    }

    public static float celsiusToFahrenheit(float celsius) {
        return celsius * (9 / 5) + 32;
    }

    public static int celsiusToFahrenheit(int celsius) {
        return celsius * (9 / 5) + 32;
    }

    public static Integer[] celsiusToFahrenheit(Integer[] inCelsius) {
        Integer[] inFahrenheit = new Integer[inCelsius.length];
        for (int i = 0; i < inCelsius.length; i++) {
            inFahrenheit[i] = celsiusToFahrenheit(inCelsius[i]);
        }
        return inFahrenheit;
    }

    ;

    public static String generateUniqueId() {
        UUID uuid = UUID.randomUUID();
        Date date = new Date();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(uuid.toString());
        stringBuilder.append("-");
        stringBuilder.append(String.valueOf(date.getTime()));
        final String UNIQUEID = stringBuilder.toString();
        return UNIQUEID;
    }

    /**
     * Add loader to a given view
     *
     * @param targetView
     * @param context
     */
    public static void addLoader(ViewGroup targetView, Context context) {
        targetView.removeAllViews();
        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
        // progressBar.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.progress_loader));
        targetView.addView(progressBar);
    }

    /**
     * Remove all views from a given view, including the loader. This should be called before loading the actual UI hierarchy
     *
     * @param targetView
     */
    public static void removeLoader(ViewGroup targetView) {
        targetView.removeAllViews();
    }

    public static void toggleVisibility(View view) {
        if (view != null) {
            if (view.isShown()) view.setVisibility(View.GONE);
            else view.setVisibility(View.VISIBLE);
        }
    }

    public static void showLongToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(Context context, int stringId) {
        Toast.makeText(context, context.getString(stringId), Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showShortToast(Context context, int stringId) {
        Toast.makeText(context, context.getString(stringId), Toast.LENGTH_SHORT).show();
    }

    public static String toLower(Context context, String input) {
        return input.toLowerCase(context.getResources().getConfiguration().locale);
    }

    public static String createInAppHtmlLink(String url, String displayName) {

        String domain = "com.kineticcafe.flightnetwork://?link=";
        return String.format("<a href=\"%s%s\">%s</a>", domain, url, displayName);
    }

    public static String getCurlFromNameValuePairs(List<NameValuePair> pairs) {
        StringBuilder sb = new StringBuilder();
        for (NameValuePair pair : pairs) {
            sb.append(pair.getName());
            sb.append("=");
            sb.append(pair.getValue());
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1); // remove last ampersand
        return sb.toString();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void addArrayElementsToList(List list, Object[] array) {
        for (Object o : array) {
            list.add(o);
        }
    }


    public static int getDrawableResourceIdFromName(String resourceName, Context context) {
        return context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
    }

    public static Drawable getDrawableFromImageName(String resourceName, Context context) {
        return context.getResources().getDrawable(getDrawableResourceIdFromName(resourceName, context));
    }
}
