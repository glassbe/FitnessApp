package com.example.fitnessapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnessapp.db.Entity.User;

public class _Activity_CoachViewModel extends AndroidViewModel {

    private MutableLiveData<User> user = new MutableLiveData<>();

    public _Activity_CoachViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public User getUser() {
        return user.getValue();
    }

    public LiveData<User> getLiveUser() {
        return user;
    }


}
