package com.example.fitnessapp.Interface;

import androidx.lifecycle.LiveData;

import com.example.fitnessapp.db.Entity.StatusUpdate;

import java.util.Date;
import java.util.List;

public interface IStatusUpdate {

    void insertNewUpdate(StatusUpdate update);

    void updateAnExistingUpdate(StatusUpdate update);

    LiveData<List<StatusUpdate>> getAllUpdatesForUserAsync(String userMail);

    List<StatusUpdate> getAllUpdatesForUser(String userMail);

    LiveData<List<StatusUpdate>> getAllUpdatesForUserInTimeFrameAsync(String userMail, Date from, Date until);

    List<StatusUpdate> getAllUpdatesFromUserInTimeFrame(String userMail, Date from, Date until);

    LiveData<StatusUpdate> getUpdateForUserForOneDayAsync(String userMail, Date date);

    StatusUpdate getUpdateForUserForOneDay(String userMail, Date date);
}
