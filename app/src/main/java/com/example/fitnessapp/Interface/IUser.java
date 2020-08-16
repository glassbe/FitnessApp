package com.example.fitnessapp.Interface;

import androidx.lifecycle.LiveData;

import com.example.fitnessapp.db.Entity.User;

public interface IUser {

    // Functions Implemented

    LiveData<User> getUser(String email);

    LiveData<User> Login(String email, String password);

    LiveData<User> Login(String email, String password, Boolean rememberMe);

    LiveData<User> Register(String email, String password, Boolean rememberMe);

    LiveData<User> UpdateInfo(User user);

    // Last edited user
    LiveData<User> getLastUser();

    // Get User by Id, not by E-Mail
    LiveData<User> getUserById(int userById);

    void Logout(User user);

    //Functions to Implement

    // check if more than one User exits in Database
    boolean UserExists();



}
