package com.example.fitnessapp.db;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.DAO.UserDAO;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.db.FitnessDatabase;
import com.example.fitnessapp.helper.Security;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UserRepo implements IUser {

    private UserDAO mUserDAO;
    public UserRepo(Application application){
        FitnessDatabase db = FitnessDatabase.getDatabase(application);
        mUserDAO = db.userDAO();
    }

    @Override
    public boolean UserExists() {
        List<User> users = mUserDAO.getAllUser();

        return !users.isEmpty();
    }

    @Override
<<<<<<< Updated upstream
    public LiveData<User> getLastUserAsync() {
        return mUserDAO.getLatestLoginAsync();
    }

    @Override
    public User getLastUser() {
        return mUserDAO.getLatestLogin();
=======
>>>>>>> Stashed changes
    }

    @Override
    public LiveData<User> getUserAsync(String email) {
        return mUserDAO.getUserByMailAsync(email);
    }

    @Override
    public User getUser(String email) {
        return mUserDAO.getUserByMail(email);
    }

    @Override
    public Boolean Login(String email, String password, Boolean rememberMe) {

        User requestedUser = mUserDAO.getUserByMail(email);

        if(requestedUser == null){
            //Email not found
            return Boolean.FALSE;
        }

        //Check the entered Password
        if(Security.encrypt(password).equals(requestedUser.getPwHash())){
            //Password is correct

            //Set New Timestamp
            requestedUser.setLastLogIn(new Date(System.currentTimeMillis()));
            requestedUser.setRememberMe(rememberMe);

            new updateAsyncTask(mUserDAO).execute(requestedUser);

            return Boolean.TRUE;
        }
        //Password is false
        return Boolean.FALSE;
    }

    @Override
    public void Logout(User user) {

        if(user != null){
            //Set rememberMe to false so autologin is disabled
            user.setRememberMe(false);

            new updateAsyncTask(mUserDAO).execute(user);
        }
    }

    @Override
    public Boolean Register(String email, String password, Boolean rememberMe) {

        //Check if User exists
        User existingUser = mUserDAO.getUserByMail(email);

        if(existingUser != null){
            return false;
        }



        //Hash password
        String pwHash = Security.encrypt(password);

        //Create User
        User newUser = new User(email, pwHash);
        newUser.setRememberMe(rememberMe);
        //Insert User in DB async
        AsyncTask<User, Void, Void> insert = new insertAsyncTask(mUserDAO).execute(newUser);

        try{
            insert.get(1000, TimeUnit.MILLISECONDS);

            return Boolean.TRUE;
        }
        catch (Exception e){
            Log.e("Data Access",e.getMessage());

            return Boolean.FALSE;
        }


    }

    public Boolean UpdateInfo(User user){
        /*
        * Only change non-critical values
        * Email, Id and pwHash remain unchanged.
        */


        //Get User from DB
        User userFromDB = mUserDAO.getUserByMail(user.getEmail());

        if(userFromDB == null){
            return Boolean.FALSE;
        }

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
        new updateAsyncTask(mUserDAO).execute(userFromDB);

        return Boolean.TRUE;



    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDAO mAsyncTaskDao;

        insertAsyncTask(UserDAO dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... users) {
            mAsyncTaskDao.insertUser(users[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<User, Void, Void>{

        private UserDAO mAsyncTaskDAO;

        updateAsyncTask(UserDAO dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDAO.updateUser(users[0]);
            return null;
        }
    }


}
