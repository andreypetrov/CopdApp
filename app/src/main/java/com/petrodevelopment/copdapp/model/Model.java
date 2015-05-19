package com.petrodevelopment.copdapp.model;

import com.petrodevelopment.copdapp.util.JsonLoader;

/**
 * Created by andrey on 18/05/2015.
 */
public class Model {

    @Override
    public String toString() {
        return JsonLoader.GSON.toJson(this);
    }
}
