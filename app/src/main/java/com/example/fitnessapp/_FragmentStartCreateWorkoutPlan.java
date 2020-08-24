package com.example.fitnessapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.databinding.FragmentStartCreateWorkoutPlanBinding;
import com.example.fitnessapp.databinding.FragmentStartYourGoalBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link _FragmentStartCreateWorkoutPlan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class _FragmentStartCreateWorkoutPlan extends Fragment {

    protected FragmentStartCreateWorkoutPlanBinding binding;



    public _FragmentStartCreateWorkoutPlan() {
        // Required empty public constructor
    }

    public static _FragmentStartCreateWorkoutPlan newInstance(String param1, String param2) {
        _FragmentStartCreateWorkoutPlan fragment = new _FragmentStartCreateWorkoutPlan();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout._fragment_start_create_workout_plan, container, false);
        binding = FragmentStartCreateWorkoutPlanBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        return view;
    }
}