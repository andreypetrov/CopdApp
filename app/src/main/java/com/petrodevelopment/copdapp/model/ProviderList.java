package com.petrodevelopment.copdapp.model;


import android.content.Context;

import com.petrodevelopment.copdapp.util.JsonLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Fix this model to differentiate questions per providers
 * Created on 22/05/2015.
 */
public class ProviderList extends Model {
    public List<Provider> providers;

    public ProviderList() {
    }

    private static ProviderList fromJson(String json) {
        return JsonLoader.GSON.fromJson(json, ProviderList.class);
    }

    public static ProviderList fromAsset(Context context) {
        String json = JsonLoader.loadJsonFromAssets(context, "providers");
        return fromJson(json);
    }
}
