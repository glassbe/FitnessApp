package com.example.fitnessapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.db.UserRepo;

import java.util.List;


public class _ActivityStart extends AppCompatActivity {

    //Use Services
    private UserViewModel _user;
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
        _user = new ViewModelProvider(this).get(UserViewModel.class);
        //_user = new UserRepoDummy();



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
        mUser = _user.mUserRepo.getLastUser();
        if(mUser != null){
            //LOGIN

            mStartFrame = "login";

            if(mUser.getRememberMe()){
                //Login

                //Call Intent to Coach Activity
                Intent intent = new Intent(this, _ActivityCoach.class);
                intent.putExtra("ARG_USER_MAIL", mUser.getEmail());
                startActivity(intent);

            } else {
                mFragmentTransaction.replace(R.id.start_frame, _FragmentStartLogin.newInstance(), null);
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
