package com.example.fitnessapp.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.DAO.UserDAO;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.helper.Security;

import java.util.List;

public class UserRepo_V1 implements IUser {

    private UserDAO mUserDAO;
    public UserRepo_V1(Application application){
        FitnessDatabase db = FitnessDatabase.getDatabase(application);
        mUserDAO = db.userDAO();
    }

    @Override
    public boolean UserExists() {
        List<LiveData<User>> users = (List<LiveData<User>>) mUserDAO.getAllUser();

        return !users.isEmpty();
    }

    @Override
    public LiveData<User> getLastUser() {
        return mUserDAO.getLatestLogin();
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
            User updateUser = requestedUser.getValue();
            updateUser.setLastLogIn();

            new updateAsyncTask(mUserDAO).execute(updateUser);
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

            new updateAsyncTask(mUserDAO).execute(updateUser);

            mUser  = mUserDAO.getUserByMail(updateUser.getEmail());
        }

        return mUser;
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
    public LiveData<User> Register(String email, String password, Boolean rememberMe) {

        //Check if User exists
        LiveData<User> existingUser = getUser(email);
        if(existingUser.getValue() != null){
            return null;
        }

        //Hash password
        String pwHash = Security.encrypt(password);

        //Create User
        User newUser = new User(email, pwHash);
        newUser.setRememberMe(rememberMe);
        //Insert User in DB async
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            mUserDAO.insertUser(newUser);
        });
//        new insertAsyncTask(mUserDAO).execute(newUser);

        //Get new User from DB
        LiveData<User> insertedUser = getUser(email);

        User user = insertedUser.getValue();

        return insertedUser;

    }

    public LiveData<User> UpdateInfo(User user){
        /*
        * Only change non-critical values
        * Email, Id and pwHash remain unchanged.
        */


        //Get User from DB
        User userFromDB = mUserDAO.getUserByMail(user.getEmail()).getValue();

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


        //Get current user with changes from DB
        return mUserDAO.getUserByMail(userFromDB.getEmail());

    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDAO mAsyncTaskDao;

        insertAsyncTask(UserDAO dao){
            this.mAsyncTaskDao = dao;
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
