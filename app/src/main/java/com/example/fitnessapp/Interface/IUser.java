package com.example.fitnessapp.Interface;

import com.example.fitnessapp.db.Entity.User;

public interface IUser {

    //Functions to Implement

    // Is at least one User in the database
    static boolean UserExists() {
        return false;
    }

    // Give the Last User, that has been changed in the Database
    static User getLastUser() {
        return null;
    }

    //check, if Email already exists in database
    static boolean emailExists(String email) {
        return false;
    }

    //chekc, if password,matches the
    static boolean passwordIsMatched(String email, String password) {
        return false;
    }


    // Functions Implemented

    User getUser(String email);

    static User Login(String email, String password) {
        return null;
    }

    User Register(String email, String password);

    User UpdateInfo(User user);
}
