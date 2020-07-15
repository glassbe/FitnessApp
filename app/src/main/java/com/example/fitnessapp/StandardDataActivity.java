package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

public class StandardDataActivity extends AppCompatActivity implements View.OnClickListener, View.{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_standard_data);

        Button continue_button = findViewById(R.id.continue_button);
        continue_button.setOnClickListener(this);

        }

    @Override
    public void onClick(View v) {

    });
    }
}
