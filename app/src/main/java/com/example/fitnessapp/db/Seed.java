package com.example.fitnessapp.db;

import com.example.fitnessapp.db.DAO.ExerciseDAO;
import com.example.fitnessapp.db.Entity.Exercise;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class Seed {

    public Seed(){

    }

    public List<Exercise> getExercises(){

        List<Exercise> exer = null;
        try (FileReader reader = new FileReader("app/src/main/assets/JSON/BasicExercises"))
        {
            //Read JSON file
            JSONObject obj = new JSONObject(reader.toString());

            JSONArray exercisesList = obj.getJSONArray("exercises");

            for(int i = 0; i < exercisesList.length(); i++){
                List<String> pictures = null;

                JSONObject c = exercisesList.getJSONObject(i);
                JSONArray pics = c.getJSONArray("picturePath");

                for(int j = 0; j < pics.length(); j++){
                    pictures.add(pics.getString(j));
                }


                Exercise newExercise = new Exercise(c.getString("name"), pictures, c.getString("description"));
                newExercise.setJsonId(c.getInt("jsonId"));

                exer.add(newExercise);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return exer;
    }

}
