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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        // Try Block
        try {
            List<String> ex1_pics = Arrays.asList("https://wger.de/media/exercise-images/172/Push-ups-1.png.800x800_q90.png","https://wger.de/media/exercise-images/172/Push-ups-2.png.800x800_q90.png");
            List<String> ex1_muscles = Arrays.asList("Anterior deltoid",
                    "Biceps brachii",
                    "Pectoralis major",
                    "Quadriceps femoris",
                    "Rectus abdominis",
                    "Serratus anterior");

            _exercise.mExerciseRepo.InsertExercise(new Exercise("Test Uebung1", "Das ist eine Testbeschreibung fuer diese Uebung. Wir werden sehen, was am Ende dabei herauskommt.", ex1_pics, ex1_muscles));
            _exercise.mExerciseRepo.InsertExercise(new Exercise("Test Uebung2", "Das ist eine Testbeschreibung fuer diese Uebung. Wir werden sehen, was am Ende dabei herauskommt.", ex1_pics, ex1_muscles));
        } catch (Exception e){
                Log.getStackTraceString(e);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
