package com.example.fitnessapp.db;

import com.example.fitnessapp.db.Entity.Exercise;
import com.example.fitnessapp.db.Entity.Program;
import com.example.fitnessapp.db.Entity.Workout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Seed {

    private String jsonFileExercise = "{\"exercises\":[  {    \"exercise\": {      \"jsonId\": \"1\",      \"name\": \"Bicep-Curl\",      \"picturePath\": [\"app/src/main/assets/exercises/Bicep-curl-1.png\", \"app/src/main/assets/exercises/Bicep-curl-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"2\",      \"name\": \"Hammer Curl\",      \"picturePath\": [\"app/src/main/assets/exercises/Alternate-hammer-curl-1.png\", \"app/src/main/assets/exercises/Alternate-hammer-curl-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"3\",      \"name\": \"Incline Curl\",      \"picturePath\": [\"app/src/main/assets/exercises/Alternate-incline-curl-1.png\", \"app/src/main/assets/exercises/Alternate-incline-curl-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"4\",      \"name\": \"Arnold Press\",      \"picturePath\": [\"app/src/main/assets/exercises/Arnold-press-1.png\", \"app/src/main/assets/exercises/Arnold-press-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"5\",      \"name\": \"Back extension on stability ball\",      \"picturePath\": [\"app/src/main/assets/exercises/Back-extension-on-stability-ball-1.png\",\"app/src/main/assets/exercises/Back-extension-on-stability-ball-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"6\",      \"name\": \"Barbell front raises\",      \"picturePath\": [\"app/src/main/assets/exercises/Barbell-front-raises-1.png\",\"app/src/main/assets/exercises/Barbell-front-raises-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"7\",      \"name\": \"Barbell shrugs\",      \"picturePath\": [\"app/src/main/assets/exercises/Barbell-shrugs-1.png\",\"app/src/main/assets/exercises/Barbell-shrugs-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"8\",      \"name\": \"Barbell upright rows\",      \"picturePath\": [\"app/src/main/assets/exercises/Barbell-upright-rows-1.png\",\"app/src/main/assets/exercises/Barbell-upright-rows-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"9\",      \"name\": \"Bench dips\",      \"picturePath\": [\"app/src/main/assets/exercises/Bench-dips-1.png\",\"app/src/main/assets/exercises/Bench-dips-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"10\",      \"name\": \"Bench press\",      \"picturePath\": [\"app/src/main/assets/exercises/Bench-press-1.png\",\"app/src/main/assets/exercises/Bench-press-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"11\",      \"name\": \"Bent arm pullover\",      \"picturePath\": [\"app/src/main/assets/exercises/Bent-arm-pullover-1.png\",\"app/src/main/assets/exercises/Bent-arm-pullover-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"12\",      \"name\": \"Bent knee hip raise\",      \"picturePath\": [\"app/src/main/assets/exercises/Bent-knee-hip-raise-1.png\",\"app/src/main/assets/exercises/Bent-knee-hip-raise-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"13\",      \"name\": \"Bridge\",      \"picturePath\": [\"app/src/main/assets/exercises/Bridge-1.png\",\"app/src/main/assets/exercises/Bridge-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"14\",      \"name\": \"Cable internal rotation\",      \"picturePath\": [\"app/src/main/assets/exercises/Cable-internal-rotation-1.png\",\"app/src/main/assets/exercises/Cable-internal-rotation-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"15\",      \"name\": \"Calf raises\",      \"picturePath\": [\"app/src/main/assets/exercises/Calf-raises-1.png\",\"app/src/main/assets/exercises/Calf-raises-2.png\"],      \"description\": \"\"   }},  {    \"exercise\": {      \"jsonId\": \"16\",      \"name\": \"Chin ups\",      \"picturePath\": [\"app/src/main/assets/exercises/Chin-ups-1.png\",\"app/src/main/assets/exercises/Chin-ups-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"17\",      \"name\": \"Climbers chin up\",      \"picturePath\": [\"app/src/main/assets/exercises/Climbers-chin-up-1.png\",\"app/src/main/assets/exercises/Climbers-chin-up-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"18\",      \"name\": \"Close grip bench press\",      \"picturePath\": [\"app/src/main/assets/exercises/Close-grip-bench-press-1.png\",\"app/src/main/assets/exercises/Close-grip-bench-press-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"19\",      \"name\": \"Concentration curls\",      \"picturePath\": [\"app/src/main/assets/exercises/Concentration-curls-1.png\",\"app/src/main/assets/exercises/Concentration-curls-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"20\",      \"name\": \"Cross body crunch\",      \"picturePath\": [\"app/src/main/assets/exercises/Cross-body-crunch-1.png\",\"app/src/main/assets/exercises/Cross-body-crunch-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"21\",      \"name\": \"Cross body hammer curl\",      \"picturePath\": [\"app/src/main/assets/exercises/Cross-body-hammer-curl-1.png\",\"app/src/main/assets/exercises/Cross-body-hammer-curl-2.png\"],      \"description\": \"\"    }  }]}";

    private String jsonFileProgram = "{  \"programs\": [{    \"program\":{      \"jsonId\": \"1\",      \"name\": \"Starter\",      \"description\": \"First Program\",      \"picturePath\": [\"/exercises/Arnold-press-2.png\"],      \"fitnessLevel\":\"1\"    }  }  ]}";

    private String jsonFileWorkouts = "{  \"workouts\": [    {\"workout\": {      \"jsonId\": \"1\",      \"planJsonId\": \"1\",      \"name\": \"Day 1\",      \"description\": \"Push Day\",      \"picturePath\": [\"exercises/Bench-press-1.png\"]    }},    {\"workout\": {      \"jsonId\": \"2\",      \"planJsonId\": \"1\",      \"name\": \"Day 2\",      \"description\": \"Pull Day\",      \"picturePath\": [\"/exercises/Alternate-bicep-curl-1.png\"]    }}  ]}";

    private String jsonFileWorkExercise = "";

    public Seed() {

    }

    public List<Exercise> getExercises() {

        List<Exercise> exer = new ArrayList<>();
//        try (FileReader reader = new FileReader(new File("app/src/main/assets/JSON/BasicExercises.json")))

//        try (FileReader reader = new FileReader("file:///android_assets/JSON/BasicExercises.json"))

        try {

        //Read JSON file
//        JSONObject obj = new JSONObject(reader.toString());
        JSONObject obj = new JSONObject(jsonFileExercise);

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


    public List<Program> getProgram() {

        List<Program> prg = new ArrayList<>();
//        try (FileReader reader = new FileReader(new File("app/src/main/assets/JSON/BasicExercises.json")))

//        try (FileReader reader = new FileReader("file:///android_assets/JSON/BasicExercises.json"))

        try {

            //Read JSON file
//        JSONObject obj = new JSONObject(reader.toString());
            JSONObject obj = new JSONObject(jsonFileProgram);

            JSONArray programList = obj.getJSONArray("programs");

            for (int i = 0; i < programList.length(); i++) {
                List<String> pictures = new ArrayList<>();

                JSONObject c = programList.getJSONObject(i);
                JSONObject program = c.getJSONObject("program");
                JSONArray pics = program.getJSONArray("picturePath");

                for (int j = 0; j < pics.length(); j++) {
                    pictures.add(pics.getString(j));
                }


                Program newProgram = new Program(program.getString("name"), program.getString("description"), pictures, program.getInt("fitnessLevel"));
                newProgram.setJsonId(program.getInt("jsonId"));

                prg.add(newProgram);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return prg;
    }

    public List<Workout> getWorkouts() {

        List<Workout> work = new ArrayList<>();
//        try (FileReader reader = new FileReader(new File("app/src/main/assets/JSON/BasicExercises.json")))

//        try (FileReader reader = new FileReader("file:///android_assets/JSON/BasicExercises.json"))

        try {

            //Read JSON file
//        JSONObject obj = new JSONObject(reader.toString());
            JSONObject obj = new JSONObject(jsonFileWorkouts);

            JSONArray workoutList = obj.getJSONArray("workouts");

            for (int i = 0; i < workoutList.length(); i++) {
                List<String> pictures = new ArrayList<>();

                JSONObject c = workoutList.getJSONObject(i);
                JSONObject workout = c.getJSONObject("workout");
                JSONArray pics = workout.getJSONArray("picturePath");

                for (int j = 0; j < pics.length(); j++) {
                    pictures.add(pics.getString(j));
                }


                Workout newWorkout = new Workout(workout.getInt("planJsonId"), workout.getString("name"), workout.getString("description"), pictures);
                newWorkout.setJsonId(workout.getInt("jsonId"));

                work.add(newWorkout);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return work;
    }

}
