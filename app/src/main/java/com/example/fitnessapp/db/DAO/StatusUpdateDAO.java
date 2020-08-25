package com.example.fitnessapp.db.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessapp.db.Entity.StatusUpdate;

import java.util.Date;
import java.util.List;

@Dao
public interface StatusUpdateDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertStatusUpdate(StatusUpdate update);

    @Update
    public void updateStatusUpdate(StatusUpdate update);

    @Delete
    public void deleteStatusUpdate(StatusUpdate update);

    @Query("SELECT * FROM StatusUpdate WHERE id = :id")
    public StatusUpdate getStatusUpdateById(long id);

    @Query("SELECT * FROM StatusUpdate WHERE userMail = :userMail")
    public List<StatusUpdate> getStatusUpdatesForUser(String userMail);

    @Query("SELECT * FROM StatusUpdate WHERE userMail = :userMail")
    public LiveData<List<StatusUpdate>> getStatusUpdatesForUserAsync(String userMail);

    @Query("SELECT * FROM StatusUpdate WHERE userMail = :userMail AND timestamp > :from AND timestamp < :until")
    public LiveData<List<StatusUpdate>> getStatusUpdateForUserFromUntilAsync(String userMail, Date from, Date until);

    @Query("SELECT * FROM StatusUpdate WHERE userMail = :userMail AND timestamp > :from AND timestamp < :until")
    public List<StatusUpdate> getStatusUpdateForUserFromUntil(String userMail, Date from, Date until);

    @Query("SELECT * FROM StatusUpdate WHERE userMail = :userMail AND timestamp > :from AND timestamp < :until LIMIT 1")
    public LiveData<StatusUpdate> getStatusUpdateForUserFromUntilFirstAsync(String userMail, Date from, Date until);

    @Query("SELECT * FROM StatusUpdate WHERE userMail = :userMail AND timestamp > :from AND timestamp < :until LIMIT 1")
    public StatusUpdate getStatusUpdateForUserFromUntilFirst(String userMail, Date from, Date until);
}
