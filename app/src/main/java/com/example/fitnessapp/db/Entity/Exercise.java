package com.example.fitnessapp.db.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int jsonId = 0; // a link to the data Stored in the JSON files if its a costume Exercise its 0

    private String name;

    private List<String> picturePath;

    private String description;

    public Exercise(String name, List<String> picturePath, String description){
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

    public List<String> getPicturePath() {
        return picturePath;
    }

    public String getDescription() {
        return description;
    }

    public int getJsonId(){return jsonId;}

    //SETTER
    public void setId(int id) {}

    public void setName(String name) {
        this.name = name;
    }

    public void setPicturePath(List<String> picturePath) {
        this.picturePath = picturePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setJsonId(int jsonId) { this.jsonId = jsonId; }
}
