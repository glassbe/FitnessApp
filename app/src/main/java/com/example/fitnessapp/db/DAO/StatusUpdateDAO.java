package com.example.fitnessapp.db.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessapp.db.Entity.StatusUpdate;

import java.util.List;

@Dao
public interface StatusUpdateDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertStatusUpdate(StatusUpdate update);

    @Update
    public void updateStatusUpdate(StatusUpdate update);

    @Delete
    public void deleteStatusUpdate(StatusUpdate update);

    @Query("SELECT * FROM StatusUpdate WHERE userId = :userId")
    public List<StatusUpdate> getStatusUpdatesForUser(int userId);

}
