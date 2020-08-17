package com.example.fitnessapp.db;

import androidx.lifecycle.LiveData;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.db.LiveDatabase;

public class UserRepoDummy implements IUser {

    private static LiveDatabase user_dummy_live;
    User user_dummy = new User("test@mail.com", "pwd#123");



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
    public void Logout(User user) {

    }

}
