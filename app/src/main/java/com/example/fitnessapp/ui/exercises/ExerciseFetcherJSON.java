package com.example.fitnessapp.ui.exercises;

import com.google.gson.Gson;

public class ExerciseFetcherJSON {

    Gson gson = new Gson();

//    ExerciseJSON mExerciseJSON = new ExerciseJSON("John", 30, "john@gmail.com");
//    String json = gson.toJson(mExerciseJSON);

    String json = "{\"age\": 30,\"name\": \"John\", \"email\": \"john@gmail.com\"}";
    ExerciseJSON mExerciseJSON = gson.fromJson(json, ExerciseJSON.class);
}
