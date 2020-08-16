package com.example.fitnessapp.repo;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.Entity.User;

public class UserRepoDummy implements IUser {

    @Override
    public User getUser(String email) {
        return null;
    }

    @Override
    public User Login(String email, String password) {
        return null;
    }

    @Override
    public User Register(String email, String password) {
        return null;
    }

    @Override
    public User UpdateInfo(User user) {
        return null;
    }

    @Override
    public boolean UserExists() {
        return false;
    }

    @Override
    public User getLastUser() {
        return null;
    }

    @Override
    public User getUserById(int userById) {
        return null;
    }

}
