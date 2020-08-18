package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.db.UserRepo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class _ActivityCoach extends AppCompatActivity {

    //Use Services
    private UserViewModel _user;
    private User mUser = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //Start Service
        _user = new ViewModelProvider(this).get(UserViewModel.class);

        //get User by Id, from other Activity
        mUser = _user.mUserRepo.getUser(getIntent().getStringExtra("ARG_USER_MAIL"));


        setContentView(R.layout._activity_coach);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(_ActivityCoach.this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(ActivityCoach.this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);


        Button continue_button = findViewById(R.id.button);
        continue_button.setOnClickListener(v -> ClickRegister());
    }

    private void ClickRegister(){
        startActivity(new Intent(_ActivityCoach.this, _ActivityStart.class));
    }

}
