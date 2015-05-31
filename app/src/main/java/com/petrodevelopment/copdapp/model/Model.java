package com.petrodevelopment.copdapp.model;

import android.content.Context;

import com.petrodevelopment.copdapp.util.JsonLoader;

/**
 * All models should inherit for this one
 * Two models will be equal to each other as long as their ids are matching.
 * This may be an issue if models with the same id from different types get into the same data structure.
 * Created by andrey on 18/05/2015.
 */
public abstract class Model {
    public String id;

    @Override
    public String toString() {
        return JsonLoader.GSON.toJson(this);
    }

    @Override
    public int hashCode() {
        int hash = 12;
        hash = hash * 17 + id.hashCode();
        return hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(!(o instanceof Model)) return false;
        Model other = (Model) o;
        return this.id.equals(other.id);
    }
}
