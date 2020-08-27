package com.example.fitnessapp.ui.exercises;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private String[] pictures;

    private String[] muscleGroups;


    //Constructor
    public Exercise(String title, String description, String[] pictures, String[] muscleGroups) {
        this.title = title;
        this.description = description;
        this.pictures = pictures;
        this.muscleGroups = muscleGroups;
    }


    //Setter
    public void setId(int id) {
        this.id = id;
    }

    //Getter
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String[] getPictures() {
        return pictures;
    }

    public String[] getMuscleGroups() {
        return muscleGroups;
    }
}
