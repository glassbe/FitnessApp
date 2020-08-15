package com.example.fitnessapp.Interface;

import com.example.fitnessapp.db.Entity.User;

public interface IUser {

    //Functions to Implement

    // check if more than one User exits in Database
    boolean UserExists();

    // Last edited user
    User getLastUser();

    // Functions Implemented

    User getUser(String email);

    User Login(String email, String password);

    User Register(String email, String password);

    User UpdateInfo(User user);
}
