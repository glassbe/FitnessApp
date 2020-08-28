package com.example.fitnessapp.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnessapp.Interface.IExercise;
import com.example.fitnessapp.db.Entity.Exercise;
import com.example.fitnessapp.db.ExerciseRepo;

public class ExercisesViewModel extends AndroidViewModel {

    public IExercise mExerciseRepo;



    public ExercisesViewModel(Application application) {
        super(application);
        mExerciseRepo = new ExerciseRepo(application);
    }




    //====================================================================
    //====================================================================
    //====================================================================

    private MutableLiveData<Exercise> mExerciseToView = new MutableLiveData<>();

    public void setExerciseToView(Exercise exerciseToView) {
        mExerciseToView.setValue(exerciseToView);
    }

    public Exercise getExerciseToView() {
        return mExerciseToView.getValue();
    }
}