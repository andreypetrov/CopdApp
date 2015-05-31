package com.petrodevelopment.copdapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Encapsulates the access to the shared preferences file for the application
 * Created by Andrey Petrov on 2014-12-14.
 */
public class Preferences {
    public static final String PREFERENCES_FILE_NAME = "preferences";

    public static final String IS_USER_LOGGED_IN = "is_user_logged_in";
    public static final String USER_EMAIL = "user_email";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;
    private Context mContext;

    public Preferences(Context context) {
        mContext = context;
    }


    /**
     * Initialize the preferences. It can be called only after activity's onCreate method
     */
    public void init() {
        mSharedPreferences = mContext.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }


    /**
     * Return the word count saved in the preferences file or if there is none, then return the default one
     *
     * @return
     */
    public boolean isUserLoggedIn() {
        return mSharedPreferences.getBoolean(IS_USER_LOGGED_IN, false);
    }

    private void setIsUserLoggedIn (boolean isUserLoggedIn) {
        mSharedPreferencesEditor.putBoolean(IS_USER_LOGGED_IN, isUserLoggedIn);
        mSharedPreferencesEditor.apply();
    }


    public String getUserEmail() {
        return mSharedPreferences.getString(USER_EMAIL, "");
    }

    public void setUserEmail(String userName) {
        mSharedPreferencesEditor.putString(USER_EMAIL, userName);
        mSharedPreferencesEditor.apply();
    }

    public void logIn() {
        setIsUserLoggedIn(true);
    }

    public void logOut() {
        setIsUserLoggedIn(false);
    }

}