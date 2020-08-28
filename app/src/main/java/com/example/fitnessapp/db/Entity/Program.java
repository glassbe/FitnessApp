package com.example.fitnessapp.db.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Program {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int jsonId = 0;

    private String description;

    private List<String> picturePath;

    private int requiredFitnessLevel; //0 - 10



    public Program(String name, String description, List<String> picturePath, int requiredFitnessLevel){
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
        this.requiredFitnessLevel = requiredFitnessLevel;
        if(requiredFitnessLevel > 10) this.requiredFitnessLevel = 10;
        if(requiredFitnessLevel < 0) this.requiredFitnessLevel = 0;
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

    public List<String> getPicturePath() {
        return picturePath;
    }

    public int getRequiredFitnessLevel() {
        return requiredFitnessLevel;
    }

    public int getJsonId() {
        return jsonId;
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

    public void setPicturePath(List<String> picturePath) {
        this.picturePath = picturePath;
    }

    public void setRequiredFitnessLevel(int requiredFitnessLevel) {
        this.requiredFitnessLevel = requiredFitnessLevel;
        if(requiredFitnessLevel > 10) this.requiredFitnessLevel = 10;
        if(requiredFitnessLevel < 0) this.requiredFitnessLevel = 0;
    }

    public void setJsonId(int jsonId) {
        this.jsonId = jsonId;
    }
}
