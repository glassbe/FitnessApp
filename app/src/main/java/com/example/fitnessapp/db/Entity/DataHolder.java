//package com.example.fitnessapp.db.Entity;
//
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.Lifecycle;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.example.fitnessapp.Interface.IUser;
//import com.example.fitnessapp.db.UserRepo;
//
//import java.util.List;
//
//public class DataHolder extends AppCompatActivity {
//
//    private IUser _user;
//
//    private User getLastUser;
//
//
//    public DataHolder() {
//
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        _user = new UserRepo(getApplication());
//        _user = ViewModelProviders.of(this).get(UserRepo.class);
//
//        _user.getLastUser().observe(this, user -> {getLastUser = user;});
//        _user.getUser()
//
//    }
//
//
//    public User getLastUser(){
//        return getLastUser;
//    }
//
//
//}
