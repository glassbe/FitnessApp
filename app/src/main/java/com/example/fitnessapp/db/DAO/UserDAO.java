package com.example.fitnessapp.db.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessapp.db.Entity.User;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUser(User user);



    @Update
    public void updateUser(User user);

    @Delete
    public void deleteUser(User user);

    @Query("SELECT * FROM User WHERE email = :mail LIMIT 1")
    public User getUserByMail(String mail);

    @Query("SELECT * FROM User WHERE Id = :id LIMIT 1")
    public User getUserById(int id);


}
