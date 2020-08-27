package com.example.fitnessapp.ui.exercises;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.FragmentCoachDashboardBinding;
import com.example.fitnessapp.databinding.FragmentCoachProfileBinding;

public class ExercisesFragment extends Fragment {

    private FragmentCoachDashboardBinding binding;


    private ExercisesViewModel mExercisesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mExercisesViewModel = ViewModelProviders.of(this).get(ExercisesViewModel.class);

        binding = FragmentCoachDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Set Recycler
        RecyclerView recyclerView = binding.exerciseRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
