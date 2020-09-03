package com.example.fitnessapp.ui.exercises;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.ViewModel.ExercisesViewModel;
import com.example.fitnessapp.databinding.FragmentCoachExercisesBinding;
import com.example.fitnessapp.db.Entity.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExercisesFragment extends Fragment {

    private FragmentCoachExercisesBinding binding;


    private RecyclerView mRecyclerView;
    private ExerciseAdapter mExerciseAdapter;

    private List<Exercise> mExerciseList = new ArrayList<>();
    private ExercisesViewModel _exercise;
    //    private List<com.example.fitnessapp.db.Entity.Exercise> m;

    @Override
    @SuppressLint("StaticFieldLeak")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCoachExercisesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //Set ViewModel (getting Data)
        _exercise = new ViewModelProvider(getActivity()).get(ExercisesViewModel.class);



        //Fill List with Data from Database
        new AsyncTask<Void,Void,Void>(){
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... voids) {

                mExerciseAdapter = new ExerciseAdapter();

                mExerciseList = _exercise.mExerciseRepo.getAllExercises();
                mExerciseAdapter.submitList(mExerciseList);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                // Set Recycler
                mRecyclerView = binding.exerciseRecyclerView;
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setHasFixedSize(true);

                mRecyclerView.setAdapter(mExerciseAdapter);

//                Set OnclickListener
                mExerciseAdapter.setOnItemClickListener(new ExerciseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Exercise exercise) {
                        //Set Current Exercise
                        _exercise.setExerciseToView(exercise);

                        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();


                        // New Fragment Block
                        Fragment f = null;
                        f = mFragmentManager.findFragmentByTag(exercise.getName());
                        if(f == null){
                            mFragmentTransaction.replace(R.id.coach_fullview_frame, new ExerciseDetailsFragment(), exercise.getName());
                            mFragmentTransaction.addToBackStack(exercise.getName());
                        } else {
                            mFragmentManager.popBackStack();
                            mFragmentManager.popBackStack(exercise.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }

                        mFragmentTransaction.commit();
                    }
                });

            }
        }.execute();




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
