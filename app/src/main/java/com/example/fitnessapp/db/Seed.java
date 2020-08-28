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

    private String jsonFile = "{\"exercises\":[  {    \"exercise\": {      \"jsonId\": \"1\",      \"name\": \"Bicep-Curl\",      \"picturePath\": [\"exercises/Bicep-curl-1.png\", \"exercises/Bicep-curl-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"2\",      \"name\": \"Hammer Curl\",      \"picturePath\": [\"exercises/Alternate-hammer-curl-1.png\", \"exercises/Alternate-hammer-curl-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"3\",      \"name\": \"Incline Curl\",      \"picturePath\": [\"exercises/Alternate-incline-curl-1.png\", \"exercises/Alternate-incline-curl-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"4\",      \"name\": \"Arnold Press\",      \"picturePath\": [\"exercises/Arnold-press-1.png\", \"exercises/Arnold-press-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"5\",      \"name\": \"Back extension on stability ball\",      \"picturePath\": [\"exercises/Back-extension-on-stability-ball-1.png\",\"exercises/Back-extension-on-stability-ball-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"6\",      \"name\": \"Barbell front raises\",      \"picturePath\": [\"exercises/Barbell-front-raises-1.png\",\"exercises/Barbell-front-raises-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"7\",      \"name\": \"Barbell shrugs\",      \"picturePath\": [\"exercises/Barbell-shrugs-1.png\",\"exercises/Barbell-shrugs-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"8\",      \"name\": \"Barbell upright rows\",      \"picturePath\": [\"exercises/Barbell-upright-rows-1.png\",\"exercises/Barbell-upright-rows-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"9\",      \"name\": \"Bench dips\",      \"picturePath\": [\"exercises/Bench-dips-1.png\",\"exercises/Bench-dips-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"10\",      \"name\": \"Bench press\",      \"picturePath\": [\"exercises/Bench-press-1.png\",\"exercises/Bench-press-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"11\",      \"name\": \"Bent arm pullover\",      \"picturePath\": [\"exercises/Bent-arm-pullover-1.png\",\"exercises/Bent-arm-pullover-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"12\",      \"name\": \"Bent knee hip raise\",      \"picturePath\": [\"exercises/Bent-knee-hip-raise-1.png\",\"exercises/Bent-knee-hip-raise-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"13\",      \"name\": \"Bridge\",      \"picturePath\": [\"exercises/Bridge-1.png\",\"exercises/Bridge-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"14\",      \"name\": \"Cable internal rotation\",      \"picturePath\": [\"exercises/Cable-internal-rotation-1.png\",\"exercises/Cable-internal-rotation-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"15\",      \"name\": \"Calf raises\",      \"picturePath\": [\"exercises/Calf-raises-1.png\",\"exercises/Calf-raises-2.png\"],      \"description\": \"\"   }},  {    \"exercise\": {      \"jsonId\": \"16\",      \"name\": \"Chin ups\",      \"picturePath\": [\"exercises/Chin-ups-1.png\",\"exercises/Chin-ups-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"17\",      \"name\": \"Climbers chin up\",      \"picturePath\": [\"exercises/Climbers-chin-up-1.png\",\"exercises/Climbers-chin-up-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"18\",      \"name\": \"Close grip bench press\",      \"picturePath\": [\"exercises/Close-grip-bench-press-1.png\",\"exercises/Close-grip-bench-press-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"19\",      \"name\": \"Concentration curls\",      \"picturePath\": [\"exercises/Concentration-curls-1.png\",\"exercises/Concentration-curls-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"20\",      \"name\": \"Cross body crunch\",      \"picturePath\": [\"exercises/Cross-body-crunch-1.png\",\"exercises/Cross-body-crunch-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"21\",      \"name\": \"Cross body hammer curl\",      \"picturePath\": [\"exercises/Cross-body-hammer-curl-1.png\",\"exercises/Cross-body-hammer-curl-2.png\"],      \"description\": \"\"    }  }]}";
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
