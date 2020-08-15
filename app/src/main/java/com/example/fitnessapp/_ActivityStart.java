package com.example.fitnessapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.repo.UserRepo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class _ActivityStart extends AppCompatActivity {

    private User mUser = null;


    private static String mStartFrame;

    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

    //Use Services
    private IUser _user = new UserRepo(this.getApplication());


    public static Boolean EmailAndPassword_BOOL = true;     //lets user loin
    public static Boolean PasswordReminderSet_BOOL = true;  //sets password reminder
    public static Boolean UserExists_BOOL = false;           //starts with login


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set Activity Layout
        setContentView(R.layout._activity_start);

        //Make Full-View
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


        //Find Start Frame in Activity
        if(findViewById(R.id.start_frame) != null){
            if(savedInstanceState != null){
                return;
            }
            loginOrRegister();
        }
    }

    @Override
    public  void onBackPressed() {
        int count = mFragmentManager.getBackStackEntryCount();
        if(count == 0){
            super.onBackPressed();
        } else{
            getSupportFragmentManager().popBackStack();
            //mFragmentTransaction.replace(R.id.start_frame,);
        }
    }

    //=====================
    // private functions

    private void loginOrRegister() {

        if(_user.UserExists()){
            //LOGIN
            mUser = _user.getLastUser();

            mStartFrame = "login";

            if(mUser.passwordIsSet()){
                mFragmentTransaction.replace(R.id.start_frame, _FragmentStartLogin.newInstance(mUser, mUser.getEmail(), mUser.getPassword()), null);
            } else {
                mFragmentTransaction.replace(R.id.start_frame, _FragmentStartLogin.newInstance(mUser, mUser.getEmail()), null);
            }
        }
        else {
            //REGISTER
            mStartFrame = "register";
            mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartRegister(), null);
        }
        mFragmentTransaction.commit();
    }

    //=====================
    // public functions

    public static String getStartFrame(){
        return mStartFrame;
    }

    public User Login(String email, String password){
        return IUser.Login(email,password);
    }

    //=====================
    //Database Functions

    public boolean userExists() {
        return _ActivityStart.UserExists_BOOL;
    }

    public String getPassword() {
        return "";
    }

    public String getEmail() {
        return "";
    }

    public String passwordIsSet() {
        if(passwordReminderIsSet())
            return getPassword();
        else
            return "";
    }

    public boolean passwordReminderIsSet() {
        // get PasswordReminder
        return _ActivityStart.PasswordReminderSet_BOOL;
    }

    public void setPasswordReminder(boolean b) {
        // set passwordReminder in Database
    }

    public boolean emailExists(String mail) {
        // search mail in database, return true if mail exits, else false
        return _ActivityStart.EmailAndPassword_BOOL;
    }

    public boolean passwordIsMatched(String email, String password) {
        // check if hash of password equals hash in database from email record data
        return _ActivityStart.EmailAndPassword_BOOL;
    }

    public void loadUser() {
        //load specific User Database, to get right data from functions
    }

    public void createNewUser() {
        // Create new Data-Record in Database
    }


}
