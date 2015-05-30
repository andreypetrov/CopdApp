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
//
//    public static final String JSON_EXTENSION = ".json";
//
//
//    public static String loadJsonFromAssets (Context context, String fileName) {
//        return FileUtil.loadStringFromAssets(fileName+JSON_EXTENSION, context);
//    }

}
