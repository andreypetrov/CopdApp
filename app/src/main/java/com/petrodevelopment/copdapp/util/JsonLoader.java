package com.petrodevelopment.copdapp.util;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by andrey on 15/05/2015.
 */
public class JsonLoader {

    public static Gson GSON;

    static {
        GSON = new Gson();
    }

    public static final String JSON_EXTENSION = ".json";





    public static String loadJsonFromAssets (Context context, String fileName) {
        return loadStringFromAssets(context, fileName, JSON_EXTENSION);
    }

    public static String loadStringFromAssets(Context context, String fileName, String fileExtension) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(fileName+fileExtension);
            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
