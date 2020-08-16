package com.example.fitnessapp.repo;

import androidx.lifecycle.LiveData;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.Entity.User;

public class UserRepoDummy implements IUser {

    @Override
    public LiveData<User> getUser(String email) {
        return null;
    }

    @Override
    public LiveData<User> Login(String email, String password) {
        return null;
    }

    @Override
    public LiveData<User> Login(String email, String password, Boolean rememberMe) {
        return null;
    }

    @Override
    public LiveData<User> Register(String email, String password, Boolean rememberMe) {
        return null;
    }

    @Override
    public LiveData<User> UpdateInfo(User user) {
        return null;
    }

    @Override
    public boolean UserExists() {
        return false;
    }

    @Override
    public LiveData<User> getLastUser() {
        return null;
    }

    @Override
    public LiveData<User> getUserById(int userById) {
        return null;
    }

    @Override
    public void Logout(User user) {

    }

}
