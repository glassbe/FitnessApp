package com.example.fitnessapp.db.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.fitnessapp.db.Typeconverters.MyTypeConverters;

import java.util.List;

// My Entity is Down Below

//@Entity
//public class Exercise {
//
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//
//    private String name;
//
//    private String picturePath;
//
//    private String description;
//
//    public Exercise(String name, String picturePath, String description){
//        this.name = name;
//        this.picturePath = picturePath;
//        this.description = description;
//    }
//
//    //GETTER
//    public int getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getPicturePath() {
//        return picturePath;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//
//    //SETTER
//    public void setId(int id) {}
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setPicturePath(String picturePath) {
//        this.picturePath = picturePath;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//}









@Entity
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    @TypeConverters(MyTypeConverters.class)
    private List<String> pictures;

    @TypeConverters(MyTypeConverters.class)
    private List<String> muscleGroups;


    //Constructor
    public Exercise(String title, String description, List<String> pictures, List<String> muscleGroups) {
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

    public List<String> getPictures() {
        return pictures;
    }

    public List<String> getMuscleGroups() {
        return muscleGroups;
    }
}
