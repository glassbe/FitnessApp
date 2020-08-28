package com.example.fitnessapp.db.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

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

    private int jsonId = 0;

    private String name;

    private String description;

    private List<String> picturePath;

    public Workout(int planId, String name, String description, List<String> picturePath){
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

    public List<String> getPicturePath() {
        return picturePath;
    }

    public int getJsonId() { return jsonId; }

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

    public void setPicturePath(List<String> picturePath) {
        this.picturePath = picturePath;
    }

    public void setJsonId(int jsonId) { this.jsonId = jsonId; }
}

