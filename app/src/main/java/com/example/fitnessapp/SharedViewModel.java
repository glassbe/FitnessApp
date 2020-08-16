package com.example.fitnessapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<CharSequence> text;


    public void setText(CharSequence input) {
        text.setValue(input);
    }
    public LiveData<CharSequence> getText(){
        if (text == null) {
            text = new MutableLiveData<>();
        }
        return text;
    }


}
