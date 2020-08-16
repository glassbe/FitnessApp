package com.example.fitnessapp.db.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessapp.db.Entity.User;

@Dao
public abstract class UserDAO_Dummy extends LiveData<User> {

    public static final User user_dummy = new User()


    public abstract void insertUser(User user);


    public abstract void updateUser(User user);


    public abstract void deleteUser(User user);


    public abstract LiveData<User> getUserByMail(String mail);



    public abstract LiveData<User> getUserById(int id);


    public abstract LiveData<User> getLatestLogin();

}
