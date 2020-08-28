package com.example.fitnessapp.db;

import com.example.fitnessapp.db.Entity.Exercise;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Seed {

    private String jsonFile = "{\"exercises\":[{\"exercise\":{\"jsonId\":\"1\",\"name\":\"Bicep-Curl\",\"picturePath\":[\"app/src/main/assets/exercises/Bicep-curl-1.png\",\"app/src/main/assets/exercises/Bicep-curl-2.png\"],\"description\":\"\"}},{\"exercise\":{\"jsonId\":\"2\",\"name\":\"Hammer Curl\",\"picturePath\":[\"app/src/main/assets/exercises/Alternate-hammer-curl-1.png\",\"app/src/main/assets/exercises/Alternate-hammer-curl-2.png\"],\"description\":\"\"}}]}";

    public Seed() {

    }

    public List<Exercise> getExercises() {

        List<Exercise> exer = new ArrayList<>();
//        try (FileReader reader = new FileReader(new File("app/src/main/assets/JSON/BasicExercises.json")))

//        try (FileReader reader = new FileReader("file:///android_assets/JSON/BasicExercises.json"))

        try {

        //Read JSON file
//        JSONObject obj = new JSONObject(reader.toString());
        JSONObject obj = new JSONObject(jsonFile);

        JSONArray exercisesList = obj.getJSONArray("exercises");

        for (int i = 0; i < exercisesList.length(); i++) {
            List<String> pictures = new ArrayList<>();

            JSONObject c = exercisesList.getJSONObject(i);
            JSONObject exercise = c.getJSONObject("exercise");
            JSONArray pics = exercise.getJSONArray("picturePath");

            for (int j = 0; j < pics.length(); j++) {
                pictures.add(pics.getString(j));
            }


            Exercise newExercise = new Exercise(exercise.getString("name"), pictures, exercise.getString("description"));
            newExercise.setJsonId(exercise.getInt("jsonId"));

            exer.add(newExercise);
        }

//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return exer;
    }

}
