package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.db.UserRepo;
import com.example.fitnessapp.ui.dashboard.DashboardFragment;
import com.example.fitnessapp.ui.home.HomeFragment;
import com.example.fitnessapp.ui.home.HomeViewModel;
import com.example.fitnessapp.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.snackbar.Snackbar;
import eu.long1.spacetablayout.SpaceTabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class _ActivityCoach extends AppCompatActivity {

    //Use Services
    private UserViewModel _user;
    private User mUser = null;
    SpaceTabLayout mSpaceTabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        setContentView(R.layout._spacelayout);

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


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(_ActivityCoach.this, R.id.spaceTabLayout);
//        NavigationUI.setupActionBarWithNavController(_ActivityCoach.this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);


//        Button continue_button = findViewById(R.id.button);
//        continue_button.setOnClickListener(v -> ClickRegister());
    }

    private void setupWindowAnimations() {
//        Explode explode = new Explode();
//        explode.setDuration(1000);
//        getWindow().setEnterTransition(explode);

//        Fade fade = new Fade();
//        fade.setDuration(10000);
//        getWindow().setEnterTransition(fade);
    }

    private void ClickRegister(){
        startActivity(new Intent(_ActivityCoach.this, _ActivityStart.class));
    }

}
