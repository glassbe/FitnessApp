package com.example.fitnessapp.Interface;

import androidx.lifecycle.LiveData;

import com.example.fitnessapp.db.Entity.User;

public interface IUser {

    // Functions Implemented

    User getUser(String email);

    User Login(String email, String password);

    User Login(String email, String password, Boolean rememberMe);

    User Register(String email, String password, Boolean rememberMe);

    User UpdateInfo(User user);

    // Last edited user
    User getLastUser();

    void Logout(User user);

    //Functions to Implement

    // check if more than one User exits in Database
    boolean UserExists();



}
