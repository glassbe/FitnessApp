package com.example.fitnessapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.Entity.User;


public class _ActivityStart extends AppCompatActivity implements View.OnClickListener{

    User mUser = null;

    private static String mStartFrame;

    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

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
        setContentView(R.layout._activity_start);

        // make full view
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


        if(findViewById(R.id.start_frame) != null){
            if(savedInstanceState != null){
                return;
            }
            loginOrRegister();
        }
    }

    private void loginOrRegister() {

        if(IUser.UserExists()){
            //LOGIN
            mUser = IUser.getLastUser();

            mStartFrame = "login";
            if(mUser.passwordIsSet()){
                mFragmentTransaction.replace(R.id.start_frame, _FragmentStartLogin.newInstance(mUser.getEmail(), mUser.getPassword()), null);
            } else {
                mFragmentTransaction.replace(R.id.start_frame, _FragmentStartLogin.newInstance(mUser.getEmail()), null);
            }
            //mFragmentTransaction.addToBackStack("login");
            mFragmentTransaction.commit();
        }
        else {
            //REGISTER
            mStartFrame = "register";
            mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartRegister(), null);
            //mFragmentTransaction.addToBackStack("register");
            mFragmentTransaction.commit();
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

    @Override
    public void onClick(View v) {

    }


    //=====================
    // public functions
    public void replaceFragment (int v, Fragment fragment){
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(v, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    public static String getStartFrame(){
        return mStartFrame;
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

    public static boolean passwordReminderIsSet() {
        // get PasswordReminder
        return _ActivityStart.PasswordReminderSet_BOOL;
    }

    public static void setPasswordReminder(boolean b) {
        // set passwordReminder in Database
    }

    public static boolean emailExists(String mail) {
        // search mail in database, return true if mail exits, else false
        return _ActivityStart.EmailAndPassword_BOOL;
    }

    public static boolean passwordIsMatched(String email, String password) {
        // check if hash of password equals hash in database from email record data
        return _ActivityStart.EmailAndPassword_BOOL;
    }

    public static void loadUser() {
        //load specific User Database, to get right data from functions
    }

    public static void createNewUser() {
        // Create new Data-Record in Database
    }


}
