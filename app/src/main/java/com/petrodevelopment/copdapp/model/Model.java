package com.petrodevelopment.copdapp.model;

import android.content.Context;

import com.petrodevelopment.copdapp.util.JsonLoader;

/**
 * All models should provide static implementations of fromJson and fromAsset
 * Created by andrey on 18/05/2015.
 */
public abstract class Model<E> {

    @Override
    public String toString() {
        return JsonLoader.GSON.toJson(this);
    }
}
