package com.example.fitnessapp;

import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.Window;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.ui.coach.CoachFragment;
import com.example.fitnessapp.ui.exercises.ExercisesFragment;
import com.example.fitnessapp.ui.profile.ProfileFragment;

import java.util.ArrayList;

import eu.long1.spacetablayout.SpaceTabLayout;

public class _ActivityCoach extends AppCompatActivity {

    //Use Services
    private UserViewModel _user;
    private User mUser = null;
    private SpaceTabLayout mSpaceTabLayout;

    private FragmentManager mFragmentManager = getSupportFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(0, R.anim.my_splash_fade_out);
        setContentView(R.layout._activity_coach);

        setupWindowAnimations();

        //Set Animation for Shared Elements
        getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move).setDuration(1000));


        //Start Service
        _user = new ViewModelProvider(this).get(UserViewModel.class);

        //get User by Id, from other Activity
        mUser = _user.mUserRepo.getUser(getIntent().getStringExtra("ARG_USER_MAIL"));
        _user.setUser(mUser);

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ExercisesFragment());
        fragmentList.add(new CoachFragment());
        fragmentList.add(new ProfileFragment());

        ViewPager viewPager = findViewById(R.id.viewPager);
        mSpaceTabLayout = findViewById(R.id.spaceTabLayout);
        mSpaceTabLayout.initialize(viewPager, getSupportFragmentManager(), fragmentList, null);


//        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(_ActivityCoach.this, R.id.spaceTabLayout);
//        NavigationUI.setupActionBarWithNavController(_ActivityCoach.this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

    }



    private void setupWindowAnimations() {
        Window window = getWindow();

//        Explode explode = new Explode();
//        explode.setDuration(1000);
//        explode.excludeTarget(android.R.id.statusBarBackground, true);
//        explode.excludeTarget(android.R.id.navigationBarBackground, true);
//        window.setEnterTransition(explode); // The Transition to use to move Views into the initial Scene.
////        window.setReturnTransition(fade); //
////        window.setExitTransition(fade); // The Transition to use to move Views out of the scene when calling a new Activity.
//        window.setReenterTransition(explode);


//        Slide slide = new Slide();
//        slide.setDuration(1000);
//        slide.setInterpolator(new LinearInterpolator());
//        slide.setSlideEdge(Gravity.RIGHT);
//        slide.excludeTarget(android.R.id.statusBarBackground, true);
//        slide.excludeTarget(android.R.id.navigationBarBackground, true);
//        window.setEnterTransition(slide); // The Transition to use to move Views into the initial Scene.
//        window.setReturnTransition(slide); //
//        window.setExitTransition(slide); // The Transition to use to move Views out of the scene when calling a new Activity.
//        window.setReenterTransition(slide);


        Fade fade = new Fade();
//        fade.setDuration(1000);
        fade.setInterpolator(new LinearInterpolator());
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        window.setEnterTransition(fade); // The Transition to use to move Views into the initial Scene.
//        window.setReturnTransition(fade); //
//        window.setExitTransition(fade); // The Transition to use to move Views out of the scene when calling a new Activity.
        window.setReenterTransition(fade);

    }


    @Override
    public void onBackPressed() {
        int count = mFragmentManager.getBackStackEntryCount();
        if (count == 0) {
//            super.onBackPressed();
            moveTaskToBack(true);
        } else {
            getSupportFragmentManager().popBackStack();
            //mFragmentTransaction.replace(R.id.start_frame,);
        }
    }

//    @Override
//    public void onBackPressed() {
//        moveTaskToBack(true);
//    }


}
