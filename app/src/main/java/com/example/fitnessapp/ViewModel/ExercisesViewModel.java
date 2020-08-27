package com.example.fitnessapp.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnessapp.Interface.IExercise;
import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.ExerciseRepo;
import com.example.fitnessapp.db.UserRepo;

public class ExercisesViewModel extends AndroidViewModel {

    public IExercise mExerciseRepo;



    public ExercisesViewModel(Application application) {
        super(application);
        mExerciseRepo = new ExerciseRepo(application);


//        mText = new MutableLiveData<>();
//        mText.setValue("This is dashboard fragment");
    }

    private MutableLiveData<String> mText;

    public LiveData<String> getText() {
        return mText;
    }
}