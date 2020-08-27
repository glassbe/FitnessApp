package com.example.fitnessapp.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.db.UserRepo;

public class UserViewModel extends AndroidViewModel {

    public IUser mUserRepo;

    public UserViewModel(Application application) {
        super(application);
        mUserRepo = new UserRepo(application);
    }


    private MutableLiveData<User> user = new MutableLiveData<>();

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public User getUser() {
        return user.getValue();
    }

    public void updateUser(User user) {
        this.user.setValue(user);
        mUserRepo.UpdateInfo(user);
    }

    public LiveData<User> getLiveUser() {
        return user;
    }

}
