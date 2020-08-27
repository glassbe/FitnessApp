package com.example.fitnessapp.db.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Program.class,
                                    parentColumns = "id",
                                    childColumns = "planId",
                                    onDelete = CASCADE),
        indices = {@Index("planId")}
        )
public class Workout {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int planId;

    private String name;

    private String description;

    private String picturePath;

    public Workout(int planId, String name, String description, String picturePath){
        this.planId = planId;
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
    }


    //GETTER
    public int getId() {
        return id;
    }

    public int getPlanId() {
        return planId;
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

    //SETTER


    public void setId(int id) {}

    public void setPlanId(int planId) {
        this.planId = planId;
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

}

