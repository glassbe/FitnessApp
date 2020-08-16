package com.example.fitnessapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.repo.UserRepo;



public class _ActivityStart extends AppCompatActivity {

    //Use Services
    private IUser _user = null;
    private User mUser = null;



    private static String mStartFrame;

    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start Service
        _user = new UserRepo(getApplication());

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
        mUser = _user.getLastUser().getValue();
        if(mUser != null){
            //LOGIN

            mStartFrame = "login";

            if(mUser.getRememberMe()){
                //Login

                //Call Intent to Coach Activity
                Intent intent = new Intent(this, _ActivityCoach.class);
                intent.putExtra("ARG_USER_ID", mUser.getId());
                startActivity(intent);

            } else {
                mFragmentTransaction.replace(R.id.start_frame, _FragmentStartLogin.newInstance(mUser.getId(), mUser.getEmail()), null);
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


    //=====================
    //Database Functions






}
