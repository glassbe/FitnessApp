package com.example.fitnessapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.db.Entity.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link _FragmentStartYourGoal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class _FragmentStartYourGoal extends Fragment {

    //Use Services
    private UserViewModel _user;
    private User mUser = null;

    private _ActivityStart_ViewModel mViewModel;
    private ImageView mGoal_gain_weight_image;
    private TextView mGoal_gain_weight_text;
    private ImageView mGoal_loose_weight_image;
    private TextView mGoal_loose_weight_text;
    private ImageView mYour_goal_become_fitter_image;
    private TextView mYour_goal_become_fitter_text;
    private Button mBtn_create_workout_plan;


    public _FragmentStartYourGoal() {
        // Required empty public constructor
    }

    public static _FragmentStartYourGoal newInstance(String param1, String param2) {
        _FragmentStartYourGoal fragment = new _FragmentStartYourGoal();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _user = new ViewModelProvider(this).get(UserViewModel.class);

        mViewModel = new ViewModelProvider(getActivity()).get(_ActivityStart_ViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout._fragment_start_your_goal, container, false);

        ImageView your_goal_image = view.findViewById(R.id.your_goal_image);
        TextView your_goal_text = view.findViewById(R.id.your_goal_text);

        mGoal_gain_weight_image = view.findViewById(R.id.goal_gain_weight_image);
        mGoal_gain_weight_image.setOnClickListener(v -> ClickGainWeightGoal());
        mGoal_gain_weight_text = view.findViewById(R.id.goal_gain_weight_text);
        mGoal_gain_weight_text.setOnClickListener(v -> ClickGainWeightGoal());

        mGoal_loose_weight_image = view.findViewById(R.id.goal_loose_weight_image);
        mGoal_loose_weight_image.setOnClickListener(v -> ClickLooseWeightGoal());
        mGoal_loose_weight_text = view.findViewById(R.id.goal_loose_weight_text);
        mGoal_loose_weight_text.setOnClickListener(v -> ClickLooseWeightGoal());

        mYour_goal_become_fitter_image = view.findViewById(R.id.your_goal_become_fitter_image);
        mYour_goal_become_fitter_image.setOnClickListener(v -> ClickBecomeFitterGoal());
        mYour_goal_become_fitter_text = view.findViewById(R.id.your_goal_become_fitter_text);
        mYour_goal_become_fitter_text.setOnClickListener(v -> ClickBecomeFitterGoal());

        mBtn_create_workout_plan = view.findViewById(R.id.btn_create_workout_plan);
        mBtn_create_workout_plan.setOnClickListener(v -> ClickCreateWorkoutPlan());




        return view;
    }

    private void ClickBecomeFitterGoal() {

    }

    private void ClickLooseWeightGoal() {

    }

    private void ClickGainWeightGoal() {

    }

    private void ClickCreateWorkoutPlan() {

    }
}