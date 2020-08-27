package com.example.fitnessapp.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.fitnessapp.Interface.IStatusUpdate;
import com.example.fitnessapp.db.DAO.StatusUpdateDAO;
import com.example.fitnessapp.db.Entity.StatusUpdate;
import com.example.fitnessapp.helper.DateHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StatusUpdateRepo implements IStatusUpdate {

    private StatusUpdateDAO mStatusUpdateDAO;

    public StatusUpdateRepo(Application application) {
        FitnessDatabase db = FitnessDatabase.getDatabase(application);
        mStatusUpdateDAO = db.statusUpdateDAO();
    }

    @Override
    public void insertNewUpdate(StatusUpdate update) {
        new insertNewUpdateAsync(mStatusUpdateDAO).execute(update);
        return;
    }

    private static class insertNewUpdateAsync extends AsyncTask<StatusUpdate, Void, Void> {

        private StatusUpdateDAO dao;

        insertNewUpdateAsync(StatusUpdateDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(StatusUpdate... statusUpdates) {
            StatusUpdate update = statusUpdates[0];
            //Check if a statusupdate already exists
            String userMail = update.getUserMail();
            Date newTimestamp = update.getTimestamp();
            Date onlyDate = new Date();


            try {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                onlyDate = formatter.parse(formatter.format(newTimestamp));
            } catch (ParseException e) {
                e.printStackTrace();
                onlyDate = newTimestamp;
            }

            StatusUpdate existingUpdate = dao.getStatusUpdateForUserFromUntilFirst(update.getUserMail(), onlyDate, update.getTimestamp());
            //If update already exists return null not possible to set new StatusUpdate for the day
            if (existingUpdate != null) return null;

            dao.insertStatusUpdate(update);

            return null;
        }
    }

    @Override
    public void updateAnExistingUpdate(StatusUpdate update) {
        new updateAnExistingUpdateAsync(mStatusUpdateDAO).execute(update);
    }

    private static class updateAnExistingUpdateAsync extends AsyncTask<StatusUpdate, Void, Void> {

        private StatusUpdateDAO dao;

        updateAnExistingUpdateAsync(StatusUpdateDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(StatusUpdate... statusUpdates) {
            StatusUpdate update = statusUpdates[0];

            StatusUpdate existingUpdate = dao.getStatusUpdateById(update.getId());

            //if existingUpdate is null no Update exists ERROR
            if (existingUpdate == null) return null;

            existingUpdate.setCompletedUpdate(update.isCompletedUpdate());
            existingUpdate.setEnergieLevel(update.getEnergieLevel());
            existingUpdate.setMotivationToTrain(update.getMotivationToTrain());
            existingUpdate.setPicturePath(update.getPicturePath());
            existingUpdate.setSleepQuality(update.getSleepQuality());
            existingUpdate.setSleepQuantity(update.getSleepQuantity());
            existingUpdate.setSteps(update.getSteps());
            existingUpdate.setStressLevel(update.getStressLevel());
            existingUpdate.setWeight(update.getWeight());

            dao.updateStatusUpdate(existingUpdate);


            return null;
        }
    }

    @Override
    public LiveData<List<StatusUpdate>> getAllUpdatesForUserAsync(String userMail) {
        return mStatusUpdateDAO.getStatusUpdatesForUserAsync(userMail);
    }

    @Override
    public List<StatusUpdate> getAllUpdatesForUser(String userMail) {
        return mStatusUpdateDAO.getStatusUpdatesForUser(userMail);
    }

    @Override
    public LiveData<List<StatusUpdate>> getAllUpdatesForUserInTimeFrameAsync(String userMail, Date from, Date until) {
        return mStatusUpdateDAO.getStatusUpdateForUserFromUntilAsync(userMail, from, until);
    }

    @Override
    public List<StatusUpdate> getAllUpdatesFromUserInTimeFrame(String userMail, Date from, Date until) {
        return mStatusUpdateDAO.getStatusUpdateForUserFromUntil(userMail, from, until);
    }

    @Override
    public LiveData<StatusUpdate> getUpdateForUserForOneDayAsync(String userMail, Date date) {
        Date dayBefore = new Date();
        Date dayAfter = new Date();
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            dayBefore = formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            dayBefore = date;
        }
        dayAfter = DateHelper.addDays(dayBefore, 1);

        return mStatusUpdateDAO.getStatusUpdateForUserFromUntilFirstAsync(userMail, dayBefore, dayAfter);
    }

    @Override
    public StatusUpdate getUpdateForUserForOneDay(String userMail, Date date) {
        Date dayBefore = new Date();
        Date dayAfter = new Date();
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            dayBefore = formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            dayBefore = date;
        }
        dayAfter = DateHelper.addDays(dayBefore, 1);

        return mStatusUpdateDAO.getStatusUpdateForUserFromUntilFirst(userMail, dayBefore, dayAfter);
    }


}
