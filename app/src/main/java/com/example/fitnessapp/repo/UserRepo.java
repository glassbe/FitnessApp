package com.example.fitnessapp.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.DAO.UserDAO;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.db.FitnessDatabase;
import com.example.fitnessapp.helper.Security;

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
    public LiveData<User> getLastUser() {
        return mUserDAO.getLatestLogin();
    }

    @Override
    public LiveData<User> getUserById(int userById) {
        return mUserDAO.getUserById(userById);
    }

    @Override
    public LiveData<User> getUser(String email) {
        return mUserDAO.getUserByMail(email);
    }

    @Override
    public LiveData<User> Login(String email, String password) {
        LiveData<User> requestedUser = mUserDAO.getUserByMail(email);

        if(requestedUser.getValue() == null){
            //Email not found
            return null;
        }

        //Check the entered Password
        if(Security.encrypt(password).equals(requestedUser.getValue().getPwHash())){
            //Password is correct

            //Set New Timestamp
            requestedUser.getValue().setLastLogIn();

            mUserDAO.updateUser(requestedUser.getValue());

            return requestedUser;
        }
        //Password is false
        return null;
    }

    @Override
    public LiveData<User> Login(String email, String password, Boolean rememberMe) {

        LiveData<User> mUser = Login(email, password);

        if(mUser.getValue() != null){

            User updateUser = mUser.getValue();

            //Set the Remember Me
            updateUser.setRememberMe(rememberMe);

            mUserDAO.updateUser(updateUser);

            mUser  = mUserDAO.getUserById(updateUser.getId());
        }

        return mUser;
    }


    @Override
    public LiveData<User> Register(String email, String password, Boolean rememberMe) {

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

    public LiveData<User> UpdateInfo(User user){
        /*
        * Only change non-critical values
        * Email, Id and pwHash remain unchanged.
        */


        //Get User from DB
        LiveData<User> userFromDB = mUserDAO.getUserById(user.getId());

        //Set firstName if input is valid
        if(user.getFirstName() != null && !(user.getFirstName().equals(""))){
            userFromDB.getValue().setFirstName(user.getFirstName());
        }

        //Set lastName if input is valid
        if(user.getLastName() != null && !(user.getLastName().equals(""))){
            userFromDB.getValue().setLastName(user.getLastName());
        }

        //Set height if input is between 50 und 300 cm
        if(user.getHeight() > 50 && user.getHeight() < 300){
            userFromDB.getValue().setHeight(user.getHeight());
        }

        //Set gender
        userFromDB.getValue().setGender(user.getGender());

        //Update User on DB
        mUserDAO.updateUser(userFromDB.getValue());

        //Get current user with changes from DB
        return mUserDAO.getUserById(userFromDB.getValue().getId());

    }




}
