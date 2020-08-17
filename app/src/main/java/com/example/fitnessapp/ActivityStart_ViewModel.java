package com.example.fitnessapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActivityStart_ViewModel extends ViewModel {
    private MutableLiveData<String> password;
    private MutableLiveData<String> email;


    public void setPassword(String input) {
        password.setValue(input);
    }
    public LiveData<String> getPassword(){
        if (password == null) {
            password = new MutableLiveData<>();
        }
        return password;
    }



    public void setEmail(String input) {
        email.setValue(input);
    }
    public LiveData<String> getEmail(){
        if (email == null) {
            email = new MutableLiveData<>();
        }
        return email;
    }


}
