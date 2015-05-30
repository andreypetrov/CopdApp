package com.petrodevelopment.copdapp.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Save models to and from files, also the first time you load model, get it from the assets if it doesn't exist yet and save it to a file.
 * Created by andrey on 30/05/2015.
 */
public class FileUtil {

    public static final String JSON_EXTENSION = ".json";


    public static String loadModelFromFile(String fileName, Context context) {
        String fullFileName = fileName + JSON_EXTENSION;

        File file = new File(context.getFilesDir(), fullFileName);
        if (file.exists()) {
            return loadStringFromFile(fullFileName, context);
        }
        else {
            String stringFromAssets = initFileFromStringAsset(fullFileName, context);
            return stringFromAssets;
        }
    }

    private static String initFileFromStringAsset (String fileName, Context context) {
        String stringFromAssets = loadStringFromAssets(fileName, context);
        saveStringToFile(fileName, stringFromAssets, context);
        return stringFromAssets;
    }


    public static void saveModelToFile(String fileName, Context context, Object model) {
        String json = JsonLoader.GSON.toJson(model);
        saveStringToFile(fileName+ JSON_EXTENSION,json, context);
    }

    public static void saveStringToFile(String fileName, String content, Context context) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String loadStringFromFile(String fileName, Context context) {
        try {
            InputStream inputStream  = context.openFileInput(fileName);
            return loadStringFromInputStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String loadStringFromAssets(String fileName, Context context) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            return loadStringFromInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String loadStringFromInputStream(InputStream inputStream) {
        String assetAsString = null;
        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            assetAsString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return assetAsString;
    }
}
