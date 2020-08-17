package com.example.fitnessapp.db.Entity;

import android.view.animation.ScaleAnimation;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String email;

    private String pwHash;

    private String firstName;

    private String lastName;

    private int height;

    private int gender;

    private boolean rememberMe;

    private Date lastLogIn;

    //=======================
    // Variables to Implement

    private String profilePicPath;


    //=======================
    //=======================



    public User(String email, String pwHash){
        this.email = email;
        this.pwHash = pwHash;
        this.rememberMe = false;
        this.Id = 0;
    }



    public int getId(){return this.Id;}

    public void setId(int Id){
        this.Id = 0;
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

    public int getHeight(){return this.height;}

    public void setHeight(int height){
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

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }
}
