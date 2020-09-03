package com.example.fitnessapp.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnessapp.Interface.IStatusUpdate;
import com.example.fitnessapp.db.Entity.StatusUpdate;
import com.example.fitnessapp.db.StatusUpdateRepo;

public class StatusUpdateViewModel extends AndroidViewModel {

    public IStatusUpdate mStatusRepo;

    public StatusUpdateViewModel(Application application) {
        super(application);
        mStatusRepo = new StatusUpdateRepo(application);
    }


    //====================================================================
    //====================================================================
    //====================================================================

    private MutableLiveData<StatusUpdate> mStatusOfToday = new MutableLiveData<>();

    public StatusUpdate getStatusOfToday() {
        return mStatusOfToday.getValue();
    }

    public LiveData<StatusUpdate> getLiveStatusOfToday() {
        return mStatusOfToday;
    }


    public void setStatusOfToday(StatusUpdate statusOfToday) {
        mStatusOfToday.setValue(statusOfToday);
    }


    //====================================================================
    private MutableLiveData<StatusUpdate> mSelectedStatus = new MutableLiveData<>();

    public StatusUpdate getSelectedStatus() {
        return mSelectedStatus.getValue();
    }

    public LiveData<StatusUpdate> getLiveStatusOfSelectedStatus() {
        return mSelectedStatus;
    }

    public void setSelectedStatus(StatusUpdate statusOfToday) {
        mSelectedStatus.setValue(statusOfToday);
    }


}

