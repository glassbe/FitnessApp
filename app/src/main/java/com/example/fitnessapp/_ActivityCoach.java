package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.ui.dashboard.DashboardFragment;
import com.example.fitnessapp.ui.home.HomeFragment;
import com.example.fitnessapp.ui.notifications.NotificationsFragment;

import eu.long1.spacetablayout.SpaceTabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class _ActivityCoach extends AppCompatActivity {

    //Use Services
    private UserViewModel _user;
    private User mUser = null;
    SpaceTabLayout mSpaceTabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(0, R.anim.my_splash_fade_out);
        setContentView(R.layout._activity_coach);

        setupWindowAnimations();

        //Start Service
        _user = new ViewModelProvider(this).get(UserViewModel.class);


        //get User by Id, from other Activity
        mUser = _user.mUserRepo.getUser(getIntent().getStringExtra("ARG_USER_MAIL"));

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new DashboardFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new NotificationsFragment());

        ViewPager viewPager = findViewById(R.id.viewPager);
        mSpaceTabLayout = findViewById(R.id.spaceTabLayout);
        mSpaceTabLayout.initialize(viewPager, getSupportFragmentManager(), fragmentList,null);


//        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(_ActivityCoach.this, R.id.spaceTabLayout);
//        NavigationUI.setupActionBarWithNavController(_ActivityCoach.this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);


        Button continue_button = findViewById(R.id.button);
        continue_button.setOnClickListener(v -> ClickRegister());
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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

    private void ClickRegister(){
//        mUser.setRememberMe(false);
        _user.mUserRepo.Logout(mUser);

        startActivity(new Intent(_ActivityCoach.this, _ActivityStart.class));
        finish();
    }

}
