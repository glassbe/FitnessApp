package com.example.fitnessapp.db.Entity;

import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class User implements Serializable {

    @PrimaryKey
    @NonNull
    private String email;

    private String pwHash;

    private String firstName;

    private String lastName;

    private float height;

    private int gender; // 1|Mann, 2|Frau, 3|Divers

    private boolean rememberMe;

    private Date lastLogIn;

    //=======================
    // Variables to Implement

    private String profilePicPath;

    private float weight;

    private Date birthdate;

    private int energyLevel;

    private int focusToTrain; // 1|Gain Muscles, 2|Loose Weight, 3|Become Fitter

//=======================
    //=======================



    public User(String email, String pwHash){
        this.email = email;
        this.pwHash = pwHash;
        this.rememberMe = false;
        this.lastLogIn = new Date(System.currentTimeMillis());
    }



    public String getEmail(){return this.email;}

    public String getFirstName(){return this.firstName;}

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){return this.lastName;}

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public float getHeight(){return this.height;}

    public void setHeight(float height){
        this.height = height;
    }

    public int getGender(){return this.gender;}

    public void setGender(int gender){
        this.gender = gender;
    }

    public String getPwHash(){return this.pwHash;}

    public void setPwHash(String pwHash){
        this.pwHash = pwHash;
    }

    public void setRememberMe(boolean b) { this.rememberMe = b; }

    public boolean getRememberMe() {
        return  rememberMe;
    }

    public void setLastLogIn(){
        this.lastLogIn.setTime(System.currentTimeMillis());
    }

    public void setLastLogIn(Date t){
        //No direkt access to LastLogIn
        this.lastLogIn.setTime(System.currentTimeMillis());
    }

    public Date getLastLogIn(){
        return  lastLogIn;
    }




    //=======================
    // Methods to Implement

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    public int getFocusToTrain() {
        return focusToTrain;
    }

    public void setFocusToTrain(int focusToTrain) {
        this.focusToTrain = focusToTrain;
    }


}
