package com.example.fitnessapp.db.Entity;

import android.util.Base64;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String email;

    private String pwHash;

    private String firstName;

    private String lastName;

    private int height;

    private int gender;


    public User (String email, String pwHash, String firstName, String lastName, int height, int gender) {
        this.Id = 0;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.gender = gender;

        //Basic save the password in clear text
        //Change to hashed later..
        this.pwHash = encrypt(pwHash);
    }

    public int getId(){return this.Id;}

    public String getEmail(){return this.email;}

    public String getFirstName(){return this.firstName;}

    public String getLastName(){return this.lastName;}

    public int getHeight(){return this.height;}

    public int getGender(){return this.gender;}

    public String getPwHash(){return this.pwHash;}

    public void setId(int Id){
        this.Id = 0;
    }

    @Ignore
    public boolean checkPw(String pw){
        //Check if the entered password is the same as the stored Hash
        String hash = encrypt(pw);

        if(pwHash == pw){
            return true;
        }
        else{
            return false;
        }
    }

    //*********************** Password hashing *********************************
    @Ignore
    private static final String ALGORITHM = "AES";

    @Ignore
    private static final String KEY = "1Hbfh667adfDEJ78";

    @Ignore
    private static String encrypt(String value){

        try{
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
            String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
            return encryptedValue64;
        }
        catch(Exception e){
            return value;
        }


    }

    @Ignore
    private static Key generateKey()
    {
        Key key = new SecretKeySpec(KEY.getBytes(),ALGORITHM);
        return key;
    }


}
