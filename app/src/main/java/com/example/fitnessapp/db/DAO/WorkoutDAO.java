package com.example.fitnessapp.db.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessapp.db.Entity.Exercise;
import com.example.fitnessapp.db.Entity.Workout;

import java.util.List;

@Dao
public interface WorkoutDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertWorkout(Workout workout);

    @Update
    public void updateWorkout(Workout workout);

    @Delete
    public void deleteWorkout(Workout workout);

    @Query("SELECT * FROM Workout")
    public List<Workout> getAll();
}
