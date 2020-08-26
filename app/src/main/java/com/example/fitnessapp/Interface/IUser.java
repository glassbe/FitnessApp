package com.example.fitnessapp.Interface;

import androidx.lifecycle.LiveData;

import com.example.fitnessapp.db.Entity.User;

import java.util.List;

public interface IUser {

    // Functions Implemented

    LiveData<User> getUserAsync(String email);

    User getUser(String email);

    Boolean Login(String email, String password, Boolean rememberMe);

    Boolean Register(String email, String password, Boolean rememberMe);

    Boolean UpdateInfo(User user);

    // Last edited user
    LiveData<User> getLastUserAsync();

    User getLastUser();

    void Logout(User user);

    //Functions to Implement

    // check if more than one User exits in Database
    boolean UserExists();

    void DeleteUser(User user);

    boolean changePassword(String email, String oldPassword, String newPassword);

}
