package com.example.fitnessapp.db;

import android.app.Application;

import com.example.fitnessapp.Interface.IExercise;
import com.example.fitnessapp.db.DAO.ExerciseDAO;
import com.example.fitnessapp.db.Entity.Exercise;

import java.util.List;

public class ExerciseRepo implements IExercise {

    private ExerciseDAO mExerciseDAO;

    public ExerciseRepo(Application application){
        FitnessDatabase db = FitnessDatabase.getDatabase(application);
        mExerciseDAO = db.exerciseDAO();
    }

    @Override
    public List<Exercise> getAllExercises() {
        return mExerciseDAO.getAll();
    }
}
