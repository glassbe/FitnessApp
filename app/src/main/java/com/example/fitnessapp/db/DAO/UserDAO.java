package com.example.fitnessapp.db.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessapp.db.Entity.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUser(User user);

    @Update
    public void updateUser(User user);

    @Delete
    public void deleteUser(User user);

    @Query("SELECT * FROM User WHERE email LIKE :mail LIMIT 1")
    public LiveData<User> getUserByMailAsync(String mail);

    @Query("SELECT * FROM User WHERE email LIKE :mail LIMIT 1")
    public User getUserByMail(String mail);

    @Query("SELECT * FROM User WHERE lastLogIn = (SELECT MAX(lastLogIn) FROM User)")
    public LiveData<User> getLatestLoginAsync();

    @Query("SELECT * FROM User WHERE lastLogIn = (SELECT MAX(lastLogIn) FROM User)")
    public User getLatestLogin();

    @Query("SELECT * FROM User")
    public LiveData<List<User>> getAllUserAsync();

    @Query("SELECT * FROM User")
    public List<User> getAllUser();

}
