package com.example.fitnessapp.db.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.db.Entity.WorkoutExerciseJoin;

import java.util.List;

@Dao
public interface WorkoutExerciseJoinDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertJoin(WorkoutExerciseJoin join);

    @Update
    public void updateJoin(WorkoutExerciseJoin join);

    @Delete
    public void deleteJoin(WorkoutExerciseJoin join);

    @Query("SELECT * FROM WorkoutExerciseJoin WHERE workoutId = :workoutId")
    public LiveData<List<WorkoutExerciseJoin>> getAllJoinsForWorkoutAsync(int workoutId);

    @Query("SELECT * FROM WorkoutExerciseJoin WHERE workoutId = :workoutId AND exerciseId = :exerciseId")
    public WorkoutExerciseJoin getJoinByWorkoutAndExercise(int workoutId, int exerciseId);

}
