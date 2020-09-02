package com.example.fitnessapp.ui.exercises;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fitnessapp.R;
import com.example.fitnessapp.ViewModel.ExercisesViewModel;
import com.example.fitnessapp.databinding.FragmentExerciseDetailsBinding;
import com.example.fitnessapp.db.Entity.Exercise;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseDetailsFragment extends Fragment {


    private FragmentExerciseDetailsBinding binding;
    private ExercisesViewModel _exercise;
    private Exercise mExercise;

    public ExerciseDetailsFragment() {
        // Required empty public constructor
    }


    public static ExerciseDetailsFragment newInstance(String param1, String param2) {
        ExerciseDetailsFragment fragment = new ExerciseDetailsFragment();
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
        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        _exercise = new ViewModelProvider(getActivity()).get(ExercisesViewModel.class);

        mExercise = _exercise.getExerciseToView();

        //Set OnClickListener
        binding.exerciseDetailedBackbuttontop.setOnClickListener(v -> ClickBack());
        binding.exerciseDetailedBackbuttonbottom.setOnClickListener(v -> ClickBack());

        //Set Data into Views
        binding.exerciseDetailedName.setText(mExercise.getName());
        binding.exerciseDetailedDescription.setText(mExercise.getDescription());

        //Do nothing on Click
        binding.exerciseDetailedBackground.setOnClickListener(v -> {});


        //Set Pictures
        Glide.with(getContext())
                .load(Uri.parse("file:///android_asset/"+mExercise.getPicturePath().get(0)))
//                .centerCrop()
//                .apply(new RequestOptions().override(50, 50))
                .fitCenter()
                .placeholder(R.drawable.placeholder_exercise)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_error_no_pic_exercise)
                .into(binding.exerciseDetailedFirstPic);

        Glide.with(getContext())
                .load(Uri.parse("file:///android_asset/"+mExercise.getPicturePath().get(1)))
//                .centerCrop()
//                .apply(new RequestOptions().override(50, 50))
                .fitCenter()
                .placeholder(R.drawable.placeholder_exercise)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_error_no_pic_exercise)
                .into(binding.exerciseDetailedSecondPic);


        return view;
    }

    private void ClickBack() {
        getActivity().onBackPressed();
    }
}