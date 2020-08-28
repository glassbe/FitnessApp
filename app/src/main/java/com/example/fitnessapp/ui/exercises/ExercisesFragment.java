package com.example.fitnessapp.ui.exercises;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.ViewModel.ExercisesViewModel;
import com.example.fitnessapp.databinding.FragmentCoachExercisesBinding;
import com.example.fitnessapp.db.Entity.Exercise;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static android.content.ContentValues.TAG;

public class ExercisesFragment extends Fragment {

    private FragmentCoachExercisesBinding binding;


    private ExercisesViewModel mExercisesViewModel;

    private RecyclerView mRecyclerView;
    private NestedScrollView mNestedScrollView;
    private ProgressBar mProgressBar;

    private ExerciseAdapter mExerciseAdapter;

    private List<Exercise> mExerciseList = new ArrayList<>();
    private ExercisesViewModel _exercise;
    //    private List<com.example.fitnessapp.db.Entity.Exercise> m;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mExercisesViewModel = ViewModelProviders.of(this).get(ExercisesViewModel.class);

        binding = FragmentCoachExercisesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //Set NestedScrollView
        mNestedScrollView = binding.exerciseScrollView;

        //Set ProgressBar
        mProgressBar = binding.exercisesProgressBar;

        // Set Recycler
        mRecyclerView = binding.exerciseRecyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);


        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                //Set Adapter async
                mExerciseAdapter = new ExerciseAdapter();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mRecyclerView.setAdapter(mExerciseAdapter);
            }
        }.execute();


        //Set ViewModel (getting Data)
        _exercise = new ViewModelProvider(ExercisesFragment.this).get(ExercisesViewModel.class);

        //Fill List with Data from Database
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                mExerciseList = _exercise.mExerciseRepo.getAllExercises();
                mExerciseAdapter.submitList(mExerciseList);

                return null;
            }
        }.execute();



        binding.button.setOnClickListener(v -> {
            Log.d(TAG, "onCreateView: Hello I am clicked");
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
