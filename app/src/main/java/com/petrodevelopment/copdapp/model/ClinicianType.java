package com.petrodevelopment.copdapp.model;

import com.orm.SugarRecord;

/**
 * Created by andrey on 10/05/2015.
 */
public class ClinicianType extends SugarRecord<ClinicianType> {
    public String name;

    public ClinicianType() {
    }

    public ClinicianType(String name) {
        this.name = name;
    }
}
