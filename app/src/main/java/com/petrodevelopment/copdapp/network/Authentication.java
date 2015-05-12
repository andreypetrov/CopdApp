package com.petrodevelopment.copdapp.network;

import android.util.Base64;

/**
 *
 *
 * Created by user on 15-05-12.
 */
public class Authentication {

    private final static String BASIC_AUTHENTICATION = "Basic " + Base64.encodeToString("user:password".getBytes(), Base64.NO_WRAP);




    /**
     * Example usage:
     * HttpGet request = new HttpGet(...);
     * request.setHeader("Authorization", getAuthenticationString());
     * @return
     */
    public static String getAuthenticationString() {
        return BASIC_AUTHENTICATION;
    }
}
