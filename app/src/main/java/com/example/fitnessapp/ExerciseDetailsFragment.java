package com.example.fitnessapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fitnessapp.ViewModel.ExercisesViewModel;
import com.example.fitnessapp.databinding.FragmentCoachExercisesBinding;
import com.example.fitnessapp.databinding.FragmentExerciseDetailsBinding;
import com.example.fitnessapp.db.Entity.Exercise;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private FragmentExerciseDetailsBinding binding;
    private ExercisesViewModel _exercise;
    private Exercise mExercise;

    public ExerciseDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExerciseDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseDetailsFragment newInstance(String param1, String param2) {
        ExerciseDetailsFragment fragment = new ExerciseDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


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