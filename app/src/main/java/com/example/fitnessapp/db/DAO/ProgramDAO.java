package com.example.fitnessapp.db.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessapp.db.Entity.Program;

import java.util.List;

@Dao
public interface ProgramDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertProgram(Program program);

    @Update
    public void updateProgram(Program program);

    @Delete
    public void deleteProgram(Program program);

    @Query("SELECT * FROM Program")
    public List<Program> getAll();

}
