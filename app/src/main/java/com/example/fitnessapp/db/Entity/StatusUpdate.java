package com.example.fitnessapp.db.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class StatusUpdate {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int Id;

    public Date timestamp;

    public int userId;

    public String picturePath;

    public float weight;

}
