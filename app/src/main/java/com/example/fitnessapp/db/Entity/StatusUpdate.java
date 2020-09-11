package com.example.fitnessapp.db.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class StatusUpdate {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int Id;

    private Date timestamp;

    private String userMail;

    private String picturePath;

    private float weight; //Kg

    private int sleepQuality; //0 - 100

    private float sleepQuantity; //Stunden

    private int energieLevel; //0 - 100

    private int steps;

    private int stressLevel; //0-100

    private int motivationToTrain; //0-100

    private boolean completedUpdate;

    public StatusUpdate(String userMail, String picturePath, float weight, int sleepQuality, float sleepQuantity, int energieLevel) {
        this.userMail = userMail;
        this.timestamp = new Date(System.currentTimeMillis());
        this.picturePath = picturePath;
        this.weight = weight;

        //Check sleep quality for max values
        this.sleepQuality = sleepQuality;
        if (sleepQuality > 100) this.sleepQuality = 100;
        if (sleepQuality < 0) this.sleepQuality = 0;

        //Check sleepQuantity for max Value
        this.sleepQuantity = sleepQuantity;
        if (sleepQuantity > 24) this.sleepQuantity = 24;
        if (sleepQuantity < 0) this.sleepQuantity = 0;

        //Check energieLevel
        this.energieLevel = energieLevel;
        if (energieLevel > 100) this.energieLevel = 100;
        if (energieLevel < 0) this.energieLevel = 0;

        completedUpdate = false;

    }


    public int getId() {
        return Id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public float getWeight() {
        return weight;
    }

    public int getSleepQuality() {
        return sleepQuality;
    }

    public float getSleepQuantity() {
        return sleepQuantity;
    }

    public int getEnergieLevel() {
        return energieLevel;
    }

    public int getMotivationToTrain() {
        return motivationToTrain;
    }

    public int getSteps() {
        return steps;
    }

    public int getStressLevel() {
        return stressLevel;
    }

    public boolean isCompletedUpdate() {
        return completedUpdate;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserMail(String userMail) {

    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setSleepQuality(int sleepQuality) {
        this.sleepQuality = sleepQuality;
        if (sleepQuality > 100) this.sleepQuality = 100;
        if (sleepQuality < 0) this.sleepQuality = 0;
    }

    public void setSleepQuantity(float sleepQuantity) {
        this.sleepQuantity = sleepQuantity;
        if (sleepQuantity > 24) this.sleepQuantity = 24;
        if (sleepQuantity < 0) this.sleepQuantity = 0;
    }

    public void setEnergieLevel(int energieLevel) {
        this.energieLevel = energieLevel;
        if (energieLevel > 100) this.energieLevel = 100;
        if (energieLevel < 0) this.energieLevel = 0;
    }

    public void setMotivationToTrain(int motivationToTrain) {
        this.motivationToTrain = motivationToTrain;
        if (motivationToTrain > 100) this.motivationToTrain = 100;
        if (motivationToTrain < 0) this.motivationToTrain = 0;
    }

    public void setSteps(int steps) {
        this.steps = steps;
        if (steps < 0) this.steps = 0;
    }

    public void setStressLevel(int stressLevel) {
        this.stressLevel = stressLevel;
        if (stressLevel > 100) this.stressLevel = 100;
        if (stressLevel < 0) this.stressLevel = 0;

    }

    public void setCompletedUpdate(boolean completedUpdate) {
        this.completedUpdate = completedUpdate;
    }
}
