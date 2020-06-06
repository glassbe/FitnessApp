package com.example.fitnessapp.Interface;

import com.example.fitnessapp.db.Entity.User;

public interface IUser {

    User getUser(String email);

    User Login(String email, String password);

    User Register(String email, String password);

    User UpdateInfo(User user);
}
