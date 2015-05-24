package com.petrodevelopment.copdapp.model;


import android.content.Context;

import com.petrodevelopment.copdapp.util.JsonLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Fix this model to differentiate questions per providers
 * Created on 22/05/2015.
 */
public class QuestionList extends ArrayList {
    public List<Question> questions;

    public QuestionList() {
    }

    private static QuestionList fromJson(String json) {
        return JsonLoader.GSON.fromJson(json, QuestionList.class);
    }

    public static QuestionList fromAsset(Context context) {
        String json = JsonLoader.loadJsonFromAssets(context, "questions");
        return fromJson(json);
    }
}
