package com.example.fitnessapp.db.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String picturePath;

    private String description;

    public Exercise(String name, String picturePath, String description){
        this.name = name;
        this.picturePath = picturePath;
        this.description = description;
    }

    //GETTER
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public String getDescription() {
        return description;
    }


    //SETTER
    public void setId(int id) {}

    public void setName(String name) {
        this.name = name;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
