package com.example.fitnessapp.db.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Program {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String description;

    private String picturePath;

    private int requiredFitnessLevel;



    public Program(String name, String description, String picturePath, int requiredFitnessLevel){
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
        this.requiredFitnessLevel = requiredFitnessLevel;
    }

    //GETTER

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public int getRequiredFitnessLevel() {
        return requiredFitnessLevel;
    }

    //SETTER

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setRequiredFitnessLevel(int requiredFitnessLevel) {
        this.requiredFitnessLevel = requiredFitnessLevel;
    }
}
