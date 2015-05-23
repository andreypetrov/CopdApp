package com.petrodevelopment.copdapp.model;


import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Fix this model to reflect properly our medical data
 * Created on 22/05/2015.
 */
public class Questions extends ArrayList {

    public String question;

    public Questions(String question) {
        this.question = question;
    }

    public Questions() {
    }

    public static List<Questions> getQuestions() {
        List<Questions> questions = new ArrayList<>();
        questions.add(new Questions("What is the severity of my condition?"));
        questions.add(new Questions("What is the doctor's assessment of me?"));
        questions.add(new Questions("What are the recommended medications? (name, dosage, frequency, and route, beginning date and end date if applicable)"));
        questions.add(new Questions("What tests will be done"));
        questions.add(new Questions("What lifestyle changes do I have to make?"));
        questions.add(new Questions("What clinicians have I been referred to?"));
        return questions;
    }
}


/*
What is the severity of my condition?
What is the doctor's assessment of me?
What are the recommended medications? (upon entering, will have to enter: name, dosage, frequency, and route, beginning date and end date if applicable)
What tests will be done?
What lifestyle changes do I have to make?
What clinicians have I been referred to?
 */