package com.example.fitnessapp.Interface;

import com.example.fitnessapp.db.Entity.Exercise;

import java.util.List;

public interface IExercise {

    // Functions Implemented


    boolean InsertExercise(Exercise exercise);

    void UpdateExercise(Exercise exercise);

    void DeleteExercise(Exercise exercise);

    List<Exercise> getAllExercises();

}
