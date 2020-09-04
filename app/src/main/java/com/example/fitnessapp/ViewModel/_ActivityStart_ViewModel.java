package com.example.fitnessapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnessapp.db.Entity.User;

public class _ActivityStart_ViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> photoPath = new MutableLiveData<>();


    public void setUser(User user) {
        this.user.setValue(user);
    }

    public User getUser() {
        return user.getValue();
    }


    public void setPassword(String input) {
        password.setValue(input);
    }

    public LiveData<String> getPassword() {
        return password;
    }


    public void setEmail(String input) {
        email.setValue(input);
    }

    public LiveData<String> getEmail() {
        return email;
    }


    public String getPhotoPath() {
        return photoPath.getValue();
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath.setValue(photoPath);
    }


}
