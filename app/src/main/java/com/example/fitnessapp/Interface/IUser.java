package com.example.fitnessapp.Interface;

import com.example.fitnessapp.db.Entity.User;

public interface IUser {

    //Functions to Implement

    static boolean UserExists() {
        // Is at least one User in the database
        return false;
    }

    static User getLastUser() {
        // Give the Last User, that has been changed in the Database
        return null;
    }

    // Functions Implemented

    User getUser(String email);

    User Login(String email, String password);

    User Register(String email, String password);

    User UpdateInfo(User user);
}
