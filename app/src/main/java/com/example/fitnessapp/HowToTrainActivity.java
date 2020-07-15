package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static androidx.core.content.ContextCompat.startActivity;

public class HowToTrainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_train);

        Button continue_button = findViewById(R.id.continue_button5);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HowToTrainActivity.this, DefineGoalsActivity.class));
            }
        });
    }
}
