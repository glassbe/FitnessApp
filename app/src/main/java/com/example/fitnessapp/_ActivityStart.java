package com.example.fitnessapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.databinding.ActivityStartBinding;
import com.example.fitnessapp.db.Entity.User;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;
import at.wirecube.additiveanimations.additive_animator.view_visibility.ViewVisibilityAnimation;


public class _ActivityStart extends AppCompatActivity {
    //Binding to Layout
    private ActivityStartBinding binding;

    //Use Services
    private UserViewModel _user;
    private User mUser = null;

    private static String mStartFrame;

    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
    private ImageView mImg_logo;

    private boolean userToLogin;
    private _ActivityStart_ViewModel mViewModelData;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //Bind Layout to Activity
//        binding = ActivityStartBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();


        //Start ViewModel for Data Storage
        mViewModelData = new ViewModelProvider(this).get(_ActivityStart_ViewModel.class);

        //Start Service
        _user = new ViewModelProvider(this).get(UserViewModel.class);

        //Try Block
        try {
            mUser = _user.mUserRepo.getLastUser();

        } catch (Exception e){
            Log.getStackTraceString(e);
        }

        //Get Last User
        mUser = _user.mUserRepo.getLastUser();
        if(mUser != null){
            //Set Email from last User
            mViewModelData.setEmail(mUser.getEmail());

            //Decide whather User Login or Register
            if(mUser.getRememberMe()) userToLogin = true;
            else userToLogin = false;
        }


        //Set Activity Layout
        if(userToLogin)
            setContentView(R.layout._activity_start_from_middle);
        else
            setContentView(R.layout._activity_start);

        //Make FullScreen-View
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


        // Get View to Logo
        mImg_logo = findViewById(R.id.iv_login_logo_draggable);


        // Start Login Animation
        int delay;
        if(userToLogin)
            delay = animation_from_middle();
        else
            delay = animation_from_top();

        //Find Start Frame in Activity
        if (findViewById(R.id.start_frame) != null) {
            if (savedInstanceState != null) return;

            //Start Login or Register Prozess with delay
            new Handler().postDelayed(() -> loginOrRegister() , delay);
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
        if(mUser != null) Login();
        else Register();
    }

    private void Register() {
        //REGISTER-Layout
        mStartFrame = "register";
        mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartRegister(), null);
        mFragmentTransaction.commit();
    }

    private void Login() {
        //LOGIN-Layout
        mStartFrame = "login";

        if(userToLogin){
            //Login User
            Bundle bundle = ActivityOptions
                    .makeSceneTransitionAnimation(this, mImg_logo, ViewCompat.getTransitionName(mImg_logo))
//                        .makeSceneTransitionAnimation(this)
                    .toBundle();
            Intent intent = new Intent(this, _ActivityCoach.class);
            intent.putExtra("ARG_USER_MAIL", mUser.getEmail());
            startActivity(intent, bundle);
        } else {
            //Set Login Fragment
            Fragment LoginFragment = _FragmentStartLogin.newInstance();
//            LoginFragment.setEnterTransition(new Fade().excludeTarget(R.id.iv_login_logo_draggable, true));
            mFragmentTransaction.replace(R.id.start_frame, LoginFragment, null);
            mFragmentTransaction.commit();
        }
    }


    private int animation_from_top() {
        final int duration_anim1 = 1000;
        final int duration_anim2 = 750;
        final int duration_anim3 = 750;
        @SuppressLint("ResourceType") final int duration_anim4 = Integer.parseInt(getString(R.integer.config_navAnimTime));

        //Start Point
        AdditiveAnimator
                .animate(mImg_logo)
                .setDuration(0)
                .y(1000)
                .alpha(0)
                .scaleX(0)
                .scaleY(0)
                .start();

        AdditiveAnimator
                .animate(mImg_logo)
                .setStartDelay(0)
                .setDuration(duration_anim1)
                .scale(1)
                .visibility(ViewVisibilityAnimation.fadeInAndTranslateBack())
                .then()
                .setDuration(duration_anim2)
//                .rotationX(180)
                .rotationY(180)
                .then()
                .setDuration(duration_anim3)
//                .rotationX(360)
                .rotationY(360)
                .start();

        AdditiveAnimator
                .animate(mImg_logo)
                .setStartDelay(duration_anim1 + duration_anim2 + duration_anim3 + duration_anim4 + 600)
                .setDuration(0)
                .alpha(0)
                .start();

        //return duration
        return (duration_anim1 + duration_anim2 + duration_anim3);
    }

    private int animation_from_middle() {
        final int duration_anim1 = 1000;
        final int duration_anim2 = 750;
        final int duration_anim3 = 750;
        final int duration_anim4 = 300;

        //Start Point
        AdditiveAnimator
                .animate(mImg_logo)
                .setDuration(0)
                .alpha(0)
                .scaleX(0)
                .scaleY(0)
                .start();

        AdditiveAnimator
                .animate(mImg_logo)
                .setStartDelay(0)
                .setDuration(duration_anim1)
                .scale(1)
                .visibility(ViewVisibilityAnimation.fadeInAndTranslateBack())
                .then()
                .setDuration(duration_anim2)
//                .rotationX(180)
                .rotationY(180)
                .then()
                .setDuration(duration_anim3)
//                .rotationX(360)
                .rotationY(360)
                .start();

//            AdditiveAnimator
//                    .animate(mImg_logo)
//                    .setStartDelay(duration_anim1 + duration_anim2 + duration_anim3)
//                    .setDuration(duration_anim4)
//                    .scaleX(5)
//                    .scaleY(5)
//                    .alpha(0)
//                    .start();

        AdditiveAnimator
                .animate(mImg_logo)
                .setStartDelay(duration_anim1 + duration_anim2 + duration_anim3)
                .setDuration(duration_anim4)
                .y(250)
                .scaleX((float)0.5)
                .scaleY((float)0.5)
//                    .alpha(0)
                .start();

        //return duration
        return (duration_anim1 + duration_anim2 + duration_anim3 + duration_anim4);
    }


    //=====================
    // public functions

    public static String getStartFrame(){
        return mStartFrame;
    }




    //=====================
    //Database Functions




}
