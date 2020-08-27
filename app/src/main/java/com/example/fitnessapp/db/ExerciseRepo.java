package com.example.fitnessapp.db;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.fitnessapp.Interface.IExercise;
import com.example.fitnessapp.db.DAO.ExerciseDAO;
import com.example.fitnessapp.db.Entity.Exercise;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExerciseRepo implements IExercise {

    private ExerciseDAO mExerciseDAO;

    public ExerciseRepo(Application application) {
        FitnessDatabase db = FitnessDatabase.getDatabase(application);
        mExerciseDAO = db.exerciseDAO();
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean InsertExercise(Exercise exercise) {
        //Insert User in DB async
        AsyncTask<Exercise, Void, Void> insert = new AsyncTask <Exercise, Void, Void>(){
            @Override
            protected Void doInBackground(Exercise... exercises) {
                mExerciseDAO.insertExercise(exercises[0]);
                return null;
            }
        };

//        insert.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, exercise);

        try {
            insert.get(1000, TimeUnit.MILLISECONDS);

            return Boolean.TRUE;
        } catch (Exception e) {
            Log.e("Data Access", e.getMessage());

            return Boolean.FALSE;
        }
    }

    @Override
    public void UpdateExercise(Exercise exercise) {

    }

    @Override
    public void DeleteExercise(Exercise exercise) {

    }

    @Override
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = mExerciseDAO.getAllExercise();

        if(exercises.isEmpty())
            return null;

        return exercises;
    }
}
