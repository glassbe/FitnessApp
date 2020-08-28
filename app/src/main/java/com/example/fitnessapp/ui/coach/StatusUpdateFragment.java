package com.example.fitnessapp.ui.coach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.ViewModel.StatusUpdateViewModel;
import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.databinding.FragmentCoachUpdateStatusBinding;
import com.example.fitnessapp.db.Entity.StatusUpdate;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.ui.exercises.ExerciseDetailsFragment;

public class StatusUpdateFragment extends Fragment {

    FragmentCoachUpdateStatusBinding binding;
    private StatusUpdateViewModel _status;
    private UserViewModel _user;
    private User mUser;
    private StatusUpdate mStatus;

    public static StatusUpdateFragment newInstance(String param1, String param2) {
        StatusUpdateFragment fragment = new StatusUpdateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCoachUpdateStatusBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        //Set ViewModel (getting Data)
        _status = new ViewModelProvider(getActivity()).get(StatusUpdateViewModel.class);
        _user = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        //Get Current User and Status and ...
        mUser = _user.getUser();
        mStatus = _status.getSelectedStatus();


        //SetOnClickListeners


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
