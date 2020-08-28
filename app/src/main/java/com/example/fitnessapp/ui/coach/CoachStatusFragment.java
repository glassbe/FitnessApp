package com.example.fitnessapp.ui.coach;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.R;
import com.example.fitnessapp.ViewModel.ExercisesViewModel;
import com.example.fitnessapp.databinding.FragmentCoachExercisesBinding;
import com.example.fitnessapp.databinding.FragmentCoachStatusBinding;
import com.example.fitnessapp.ui.exercises.ExerciseAdapter;


public class CoachStatusFragment extends Fragment {

    private FragmentCoachStatusBinding binding;

    private RecyclerView mRecyclerView;
    private StatusAdapter mExerciseAdapter;


    public CoachStatusFragment() {
        // Required empty public constructor
    }


    public static CoachStatusFragment newInstance(String param1, String param2) {
        CoachStatusFragment fragment = new CoachStatusFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate/Bind the layout for this fragment
        binding = FragmentCoachStatusBinding.inflate(inflater,container,false);
        View view = binding.getRoot();


        //Set ViewModel (getting Data)
        _status = new ViewModelProvider(getActivity()).get(StatusViewModel.class);



        return view;
    }
}