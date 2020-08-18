package com.example.fitnessapp.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.UserRepo;

public class UserViewModel extends AndroidViewModel {

    public IUser mUserRepo;


    public UserViewModel(Application application){
        super(application);
        mUserRepo = new UserRepo(application);
    }

}
