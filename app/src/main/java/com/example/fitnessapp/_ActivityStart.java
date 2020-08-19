package com.example.fitnessapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.db.Entity.User;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;
import at.wirecube.additiveanimations.additive_animator.view_visibility.ViewVisibilityAnimation;


public class _ActivityStart extends AppCompatActivity {

    //Use Services
    private UserViewModel _user;
    private User mUser = null;



    private static String mStartFrame;

    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
    private ImageView mImg_logo;

    //Change login animation
    private boolean startFromMiddle = true;


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
        if(startFromMiddle)
            setContentView(R.layout._activity_start_from_middle);
        else
            setContentView(R.layout._activity_start);

        //Make FullScreen-View
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Handler for Delay
        final Handler handler = new Handler();
        final Runnable loginOrRegister = () -> loginOrRegister();

        // Get View to Logo
        mImg_logo = findViewById(R.id.iv_login_logo_draggable);

        // Start Login Animation
        int delay;
        if(startFromMiddle)
            delay = animation_from_middle();
        else
            delay = animation_from_top();

        //Find Start Frame in Activity
        if (findViewById(R.id.start_frame) != null) {
            if (savedInstanceState != null) {
                return;
            }
            handler.postDelayed(loginOrRegister , delay);
        }

        // Set Animation for Fragments
        mFragmentTransaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out);


    }

    private int animation_from_top() {
        final int duration_anim1 = 1000;
        final int duration_anim2 = 750;
        final int duration_anim3 = 750;

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
                .rotationX(180)
                .rotationY(180)
                .then()
                .setDuration(duration_anim3)
                .rotationX(360)
                .rotationY(360)
                .start();

        AdditiveAnimator
                .animate(mImg_logo)
                .setStartDelay(duration_anim1 + duration_anim2 + duration_anim3+200)
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
                .rotationX(180)
                .rotationY(180)
                .then()
                .setDuration(duration_anim3)
                .rotationX(360)
                .rotationY(360)
                .start();

            AdditiveAnimator
                    .animate(mImg_logo)
                    .setStartDelay(duration_anim1 + duration_anim2 + duration_anim3)
                    .setDuration(duration_anim4)
                    .scaleX(5)
                    .scaleY(5)
                    .alpha(0)
                    .start();

        //return duration
        return (duration_anim1 + duration_anim2 + duration_anim3 + duration_anim4);
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
                //Get Login Fragment
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
