package com.example.fitnessapp.db.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessapp.db.Entity.Exercise;

import java.util.List;

@Dao
public interface ExerciseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertExercise(Exercise exercise);

    @Update
    public void updateExercise(Exercise exercise);

    @Delete
    public void deleteExercise(Exercise exercise);

    @Query("SELECT * FROM Exercise")
    public List<Exercise> getAll();

    @Query("SELECT * FROM Exercise WHERE jsonId = :jsonId")
    public Exercise getExerciseByJsonId(int jsonId);

}
