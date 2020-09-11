package com.example.fitnessapp.db;

import com.example.fitnessapp.db.Entity.Exercise;
import com.example.fitnessapp.db.Entity.Program;
import com.example.fitnessapp.db.Entity.Workout;
import com.example.fitnessapp.db.Entity.WorkoutExerciseJoin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Seed {

    private String jsonFileExercise = "{\"exercises\":[  {    \"exercise\": {      \"jsonId\": \"1\",      \"name\": \"Bicep-Curl\",      \"picturePath\": [\"exercises/Bicep-curl-1.png\", \"exercises/Bicep-curl-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"2\",      \"name\": \"Hammer Curl\",      \"picturePath\": [\"exercises/Alternate-hammer-curl-1.png\", \"exercises/Alternate-hammer-curl-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"3\",      \"name\": \"Incline Curl\",      \"picturePath\": [\"exercises/Alternate-incline-curl-1.png\", \"exercises/Alternate-incline-curl-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"4\",      \"name\": \"Arnold Press\",      \"picturePath\": [\"exercises/Arnold-press-1.png\", \"exercises/Arnold-press-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"5\",      \"name\": \"Back extension on stability ball\",      \"picturePath\": [\"exercises/Back-extension-on-stability-ball-1.png\",\"exercises/Back-extension-on-stability-ball-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"6\",      \"name\": \"Barbell front raises\",      \"picturePath\": [\"exercises/Barbell-front-raises-1.png\",\"exercises/Barbell-front-raises-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"7\",      \"name\": \"Barbell shrugs\",      \"picturePath\": [\"exercises/Barbell-shrugs-1.png\",\"exercises/Barbell-shrugs-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"8\",      \"name\": \"Barbell upright rows\",      \"picturePath\": [\"exercises/Barbell-upright-rows-1.png\",\"exercises/Barbell-upright-rows-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"9\",      \"name\": \"Bench dips\",      \"picturePath\": [\"exercises/Bench-dips-1.png\",\"exercises/Bench-dips-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"10\",      \"name\": \"Bench press\",      \"picturePath\": [\"exercises/Bench-press-1.png\",\"exercises/Bench-press-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"11\",      \"name\": \"Bent arm pullover\",      \"picturePath\": [\"exercises/Bent-arm-pullover-1.png\",\"exercises/Bent-arm-pullover-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"12\",      \"name\": \"Bent knee hip raise\",      \"picturePath\": [\"exercises/Bent-knee-hip-raise-1.png\",\"exercises/Bent-knee-hip-raise-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"13\",      \"name\": \"Bridge\",      \"picturePath\": [\"exercises/Bridge-1.png\",\"exercises/Bridge-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"14\",      \"name\": \"Cable internal rotation\",      \"picturePath\": [\"exercises/Cable-internal-rotation-1.png\",\"exercises/Cable-internal-rotation-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"15\",      \"name\": \"Calf raises\",      \"picturePath\": [\"exercises/Calf-raises-1.png\",\"exercises/Calf-raises-2.png\"],      \"description\": \"\"   }},  {    \"exercise\": {      \"jsonId\": \"16\",      \"name\": \"Chin ups\",      \"picturePath\": [\"exercises/Chin-ups-1.png\",\"exercises/Chin-ups-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"17\",      \"name\": \"Climbers chin up\",      \"picturePath\": [\"exercises/Climbers-chin-up-1.png\",\"exercises/Climbers-chin-up-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"18\",      \"name\": \"Close grip bench press\",      \"picturePath\": [\"exercises/Close-grip-bench-press-1.png\",\"exercises/Close-grip-bench-press-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"19\",      \"name\": \"Concentration curls\",      \"picturePath\": [\"exercises/Concentration-curls-1.png\",\"exercises/Concentration-curls-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"20\",      \"name\": \"Cross body crunch\",      \"picturePath\": [\"exercises/Cross-body-crunch-1.png\",\"exercises/Cross-body-crunch-2.png\"],      \"description\": \"\"    }  },  {    \"exercise\": {      \"jsonId\": \"21\",      \"name\": \"Cross body hammer curl\",      \"picturePath\": [\"exercises/Cross-body-hammer-curl-1.png\",\"exercises/Cross-body-hammer-curl-2.png\"],      \"description\": \"\"    }  }]}";

    private String jsonFileProgram = "{  \"programs\": [{    \"program\":{      \"jsonId\": \"1\",      \"name\": \"Starter\",      \"description\": \"First Program\",      \"picturePath\": [\"exercises/Arnold-press-2.png\"],      \"fitnessLevel\":\"1\"    }  }  ]}";

    private String jsonFileWorkouts = "{  \"workouts\": [    {\"workout\": {      \"jsonId\": \"1\",      \"planJsonId\": \"1\",      \"name\": \"Day 1\",      \"description\": \"Push Day\",      \"picturePath\": [\"exercises/Bench-press-1.png\"]    }},    {\"workout\": {      \"jsonId\": \"2\",      \"planJsonId\": \"1\",      \"name\": \"Day 2\",      \"description\": \"Pull Day\",      \"picturePath\": [\"exercises/Alternate-bicep-curl-1.png\"]    }}  ]}";

    private String jsonFileWorkExercise = "{\"joins\": [  {    \"join\": {      \"workoutJsonId\": \"1\",      \"exerciseJsonId\": \"4\",      \"sets\": \"3\",      \"reps\": \"12\"    }  },  {    \"join\": {      \"workoutJsonId\": \"1\",      \"exerciseJsonId\": \"10\",      \"sets\": \"3\",      \"reps\": \"12\"    }  },  {    \"join\":{      \"workoutJsonId\": \"1\",      \"exerciseJsonId\": \"15\",      \"sets\": \"3\",      \"reps\": \"12\"    }  },  {    \"json\": {      \"workoutJsonId\": \"2\",      \"exerciseJsonId\": \"1\",      \"sets\": \"3\",      \"reps\": \"12\"    }  },  {    \"join\": {      \"workoutJsonId\": \"2\",      \"exerciseJsonId\": \"6\",      \"sets\": \"3\",      \"reps\": \"12\"    }  },  {    \"join\": {      \"workoutJsonId\": \"2\",      \"exerciseJsonId\": \"7\",      \"sets\": \"3\",      \"reps\": \"12\"    }  }]}";

    public Seed() {

    }

    public List<Exercise> getExercises() {

        List<Exercise> exer = new ArrayList<>();
//        try (FileReader reader = new FileReader(new File("/JSON/BasicExercises.json")))

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
//        try (FileReader reader = new FileReader(new File("/JSON/BasicExercises.json")))

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
//        try (FileReader reader = new FileReader(new File("/JSON/BasicExercises.json")))

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

    public List<WorkoutExerciseJoin> getWorkoutExerciseJoin() {

        List<WorkoutExerciseJoin> work = new ArrayList<>();
//        try (FileReader reader = new FileReader(new File("/JSON/BasicExercises.json")))

//        try (FileReader reader = new FileReader("file:///android_assets/JSON/BasicExercises.json"))

        try {

            //Read JSON file
//        JSONObject obj = new JSONObject(reader.toString());
            JSONObject obj = new JSONObject(jsonFileWorkExercise);

            JSONArray workoutList = obj.getJSONArray("joins");

            for (int i = 0; i < workoutList.length(); i++) {
                List<String> pictures = new ArrayList<>();

                JSONObject c = workoutList.getJSONObject(i);
                JSONObject workout = c.getJSONObject("workout");
                JSONArray pics = workout.getJSONArray("picturePath");

                for (int j = 0; j < pics.length(); j++) {
                    pictures.add(pics.getString(j));
                }


                WorkoutExerciseJoin newWorkout = new WorkoutExerciseJoin(workout.getInt("workoutJsonId"), workout.getInt("exerciseJsonId"),workout.getInt("sets"),workout.getInt("reps"));


                work.add(newWorkout);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return work;
    }

}
