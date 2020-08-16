package com.example.fitnessapp.repo;

import android.app.Application;
import android.util.Log;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.DAO.UserDAO;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.db.FitnessDatabase;
import com.example.fitnessapp.helper.Security;

import java.util.List;

public class UserRepo implements IUser {

    private UserDAO mUserDAO;

    public UserRepo(Application application){
        FitnessDatabase db = FitnessDatabase.getDatabase(application);
        mUserDAO = db.userDAO();
    }

    @Override
    public boolean UserExists() {
        return false;
    }

    @Override
    public User getLastUser() {
        return null;
    }

    @Override
    public User getUserById(int userById) {
        return mUserDAO.getUserById(userById);
    }

    @Override
    public User getUser(String email) {
        return mUserDAO.getUserByMail(email);
    }

    @Override
    public User Login(String email, String password) {
        User requestedUser = null;
        try {
            requestedUser = mUserDAO.getUserByMail(email);
        }catch(Exception e){
            Log.getStackTraceString(e);
            Login(email,password);
        }


        if(requestedUser == null){
            //Email not found
            return null;
        }

        //Check the entered Password
        if(Security.encrypt(password).equals(requestedUser.getPwHash())){
            //Password is correct
            return requestedUser;
        }
        //Password is false
        return null;
    }

    @Override
    public User Login(String email, String password, Boolean rememberMe) {

        User mUser = Login(email, password);

        if(mUser != null){
            //Set the Remember Me
            mUser.setRememberMe(rememberMe);

            //Set Timestamp to now
            mUser.setLastLogIn();

            mUserDAO.updateUser(mUser);

            mUser  = mUserDAO.getUserById(mUser.getId());
        }

        return mUser;
    }


    @Override
    public User Register(String email, String password, Boolean rememberMe) {

        //Check if User exists
        if(getUser(email) != null){
            return null;
        }

        //Hash password
        String pwHash = Security.encrypt(password);

        //Create User
        User newUser = new User(email, pwHash);
        newUser.setRememberMe(rememberMe);

        //Insert User in DB
        mUserDAO.insertUser(newUser);

        //Get new User from DB
        return getUser(email);

    }

    public User UpdateInfo(User user){
        /*
        * Only change non-critical values
        * Email, Id and pwHash remain unchanged.
        */


        //Get User from DB
        User userFromDB = mUserDAO.getUserById(user.getId());

        //Set firstName if input is valid
        if(user.getFirstName() != null && !(user.getFirstName().equals(""))){
            userFromDB.setFirstName(user.getFirstName());
        }

        //Set lastName if input is valid
        if(user.getLastName() != null && !(user.getLastName().equals(""))){
            userFromDB.setLastName(user.getLastName());
        }

        //Set height if input is between 50 und 300 cm
        if(user.getHeight() > 50 && user.getHeight() < 300){
            userFromDB.setHeight(user.getHeight());
        }

        //Set gender
        userFromDB.setGender(user.getGender());

        //Update User on DB
        mUserDAO.updateUser(userFromDB);

        //Get current user with changes from DB
        return mUserDAO.getUserById(userFromDB.getId());

    }

    @Override
    public boolean UserExists() {
        return false;
    }

    @Override
    public User getLastUser() {
        return null;
    }

    @Override
    public User getUserById(int userById) {
        return null;
    }

    @Override
    public User CreateUser() {
        return null;
    }


}
