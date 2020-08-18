package com.example.fitnessapp.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.DAO.UserDAO;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.helper.Security;

import java.util.List;

public class UserRepo extends AndroidViewModel implements IUser {

    private LiveData<List<User>> mSelectedUser;

    private UserDAO mUserDAO;


    public UserRepo(Application application) {
        super(application);
        FitnessDatabase db = FitnessDatabase.getDatabase(application);
        mUserDAO = db.userDAO();

    }

    @Override
    public boolean UserExists() {
        List<User> users = mUserDAO.getAllUser();
        return !users.isEmpty();
    }

    @Override
    public User getLastUser() {
        List<User> m = mUserDAO.getLatestLogin();
        if(!m.isEmpty())
            return m.get(0);
        return null;
    }


    @Override
    public User getUser(String email) {
        List<User> m = mUserDAO.getUserByMail(email);
        if(!m.isEmpty())
            return m.get(0);
        return null;
    }

    @Override
    public User Login(String email, String password) {
        User requestedUser = (getUser(email));

        if (requestedUser == null) {
            //Email not found
            return null;
        }

        //Check the entered Password
        if (Security.encrypt(password).equals(requestedUser.getPwHash())) {
            //Password is correct

            //Set New Timestamp
            User updateUser = requestedUser;
            updateUser.setLastLogIn();

            new updateAsyncTask(mUserDAO).execute(updateUser);
            return requestedUser;
        }
        //Password is false
        return null;
    }

    @Override
    public User Login(String email, String password, Boolean rememberMe) {

        User mUser = Login(email, password);

        if (mUser != null) {

            User updateUser = mUser;

            //Set the Remember Me
            updateUser.setRememberMe(rememberMe);

            new updateAsyncTask(mUserDAO).execute(updateUser);

            mUser = (getUser(updateUser.getEmail()));
        }

        return mUser;
    }

    @Override
    public void Logout(User user) {

        if (user != null) {
            //Set rememberMe to false so autologin is disabled
            user.setRememberMe(false);

            new updateAsyncTask(mUserDAO).execute(user);
        }
    }

    @Override
    public User Register(String email, String password, Boolean rememberMe) {

        //Check if User exists
        User existingUser = getUser(email);
        if (existingUser != null) {
            return null;
        }

        //Hash password
        String pwHash = Security.encrypt(password);

        //Create User
        User newUser = new User(email, pwHash);
        newUser.setRememberMe(rememberMe);
        //Insert User in DB async
//        FitnessDatabase.databaseWriteExecutor.execute(() -> {
//            mUserDAO.insertUser(newUser);
//        });
        new insertAsyncTask(mUserDAO).execute(newUser);

        //Get new User from DB
        User insertedUser = getUser(email);

        User user = insertedUser;

        return insertedUser;

    }

    public User UpdateInfo(User user) {
        /*
         * Only change non-critical values
         * Email, Id and pwHash remain unchanged.
         */


        //Get User from DB
        User userFromDB = (getUser(user.getEmail()));

        //Set firstName if input is valid
        if (user.getFirstName() != null && !(user.getFirstName().equals(""))) {
            userFromDB.setFirstName(user.getFirstName());
        }

        //Set lastName if input is valid
        if (user.getLastName() != null && !(user.getLastName().equals(""))) {
            userFromDB.setLastName(user.getLastName());
        }

        //Set height if input is between 50 und 300 cm
        if (user.getHeight() > 50 && user.getHeight() < 300) {
            userFromDB.setHeight(user.getHeight());
        }

        //Set gender
        userFromDB.setGender(user.getGender());

        //Update User on DB
        new updateAsyncTask(mUserDAO).execute(userFromDB);


        //Get current user with changes from DB
        return (getUser(userFromDB.getEmail()));

    }

    // AsyncTasks

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDAO mAsyncTaskDao;

        insertAsyncTask(UserDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... users) {
            mAsyncTaskDao.insertUser(users[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDAO mAsyncTaskDAO;

        updateAsyncTask(UserDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDAO.updateUser(users[0]);
            return null;
        }
    }


    //=============
    //My Functions

    <T> LiveData<T> ConvertLiveDataList(LiveData<List<T>> input) {
        List<T> m;
        T n;
        LiveData<T> l = new MutableLiveData<T>();
        if ((m = input.getValue()) != null) {
            n = m.get(0);
            ((MutableLiveData<T>) l).setValue(n);
        }
        return l;
    }


}
