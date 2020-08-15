package com.example.fitnessapp.db.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

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


    public User(String email, String pwHash){
        this.email = email;
        this.pwHash = pwHash;
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




    //Functions to implement


    public void setRememberMe(boolean b) {
        // Set Value in Database, to remember at login
    }

    public boolean getRememberMe() {
        //Dummy return
        return false;
    }
}
