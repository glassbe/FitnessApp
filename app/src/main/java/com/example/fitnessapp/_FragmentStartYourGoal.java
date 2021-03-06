package com.example.fitnessapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.ViewModel._ActivityStart_ViewModel;
import com.example.fitnessapp.databinding.FragmentStartYourGoalBinding;
import com.example.fitnessapp.db.Entity.User;

import java.lang.ref.WeakReference;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link _FragmentStartYourGoal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class _FragmentStartYourGoal extends Fragment {
    public static final int START_ANIMATION_TIME = 150;
    public static final int END_ANIMATION_TIME = 150;


    //Use Services
    private UserViewModel _user;
    private User mUser = null;

    private _ActivityStart_ViewModel mViewModel;

    protected FragmentStartYourGoalBinding binding;


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

        //For Activities
//        binding = FragmentStartYourGoalBinding.inflate(getLayoutInflater());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout._fragment_start_your_goal, container, false);
        binding = FragmentStartYourGoalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.goalGainWeightImage.setOnClickListener(v ->
                ClickGainWeightGoal(
                        binding.goalGainWeightImage,
                        binding.goalGainWeightText
                )
        );

        binding.goalGainWeightText.setOnClickListener(v ->
                ClickGainWeightGoal(
                        binding.goalGainWeightImage,
                        binding.goalGainWeightText
                )
        );


        binding.goalLooseWeightImage.setOnClickListener(v ->
                ClickLooseWeightGoal(
                        binding.goalLooseWeightImage,
                        binding.goalLooseWeightText
                )
        );

        binding.goalLooseWeightText.setOnClickListener(v ->
                ClickLooseWeightGoal(
                        binding.goalLooseWeightImage,
                        binding.goalLooseWeightText
                )
        );


        binding.yourGoalBecomeFitterImage.setOnClickListener(v ->
                ClickBecomeFitterGoal(
                        binding.yourGoalBecomeFitterImage,
                        binding.yourGoalBecomeFitterText
                )
        );

        binding.yourGoalBecomeFitterText.setOnClickListener(v ->
                ClickBecomeFitterGoal(
                        binding.yourGoalBecomeFitterImage,
                        binding.yourGoalBecomeFitterText
                )
        );


        binding.btnCreateWorkoutPlan.setOnClickListener(v ->
                ClickCreateWorkoutPlan()
        );


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void ClickBecomeFitterGoal(ImageView imageview, TextView textview) {
        String text = textview.getText().toString();
        Drawable image = imageview.getDrawable();

        startAnimationOfGoal(imageview, textview);

        new Handler().postDelayed(() -> {
            binding.yourGoalImage.setImageDrawable(image);
            binding.yourGoalText.setText(text.replaceAll("\\n", " "));
        }, START_ANIMATION_TIME);

        endAnimationOfGoal(imageview, textview);

    }

    private void ClickLooseWeightGoal(ImageView imageview, TextView textview) {
        String text = textview.getText().toString();
        Drawable image = imageview.getDrawable();

        startAnimationOfGoal(imageview, textview);

        new Handler().postDelayed(() -> {
            binding.yourGoalImage.setImageDrawable(image);
            binding.yourGoalText.setText(text.replaceAll("\\n", " "));
        }, START_ANIMATION_TIME);

        endAnimationOfGoal(imageview, textview);
    }


    private void ClickGainWeightGoal(ImageView imageview, TextView textview) {
        String text = textview.getText().toString();
        Drawable image = imageview.getDrawable();

        startAnimationOfGoal(imageview, textview);

        new Handler().postDelayed(() -> {
            binding.yourGoalImage.setImageDrawable(image);
            binding.yourGoalText.setText(text.replaceAll("\\n", " "));
        }, START_ANIMATION_TIME);

        endAnimationOfGoal(imageview, textview);
    }

    private void endAnimationOfGoal(ImageView imageview, TextView textview) {
        AdditiveAnimator
                .animate(imageview, textview, binding.yourGoalImage, binding.yourGoalText)
                .setStartDelay(START_ANIMATION_TIME)
                .setDuration(END_ANIMATION_TIME)
                .alpha(1)
                .start();
    }

    private void startAnimationOfGoal(ImageView imageview, TextView textview) {
        AdditiveAnimator
                .animate(imageview, textview, binding.yourGoalImage, binding.yourGoalText)
                .setDuration(START_ANIMATION_TIME)
                .alpha(0)
                .start();
    }

    private void ClickCreateWorkoutPlan() {
        int result = 0;

        if (binding.yourGoalText.getText().toString().length() == 0) {
//            Noty.init(getActivity(),"Ziel auswählen", binding.getRoot(), Noty.WarningStyle.SIMPLE)
//                    .setAnimation(Noty.RevealAnim.SLIDE_UP, Noty.DismissAnim.BACK_TO_BOTTOM, 400,400)
//                    .setWarningBoxPosition(Noty.WarningPos.BOTTOM)
//                    .setWarningInset(0,0,0,0)
//                    .setWarningBoxBgColor("#ff5c33")
//                    .setWarningTappedColor("#ff704d")
//                    .show();
            Toasty.warning(getActivity(), "Ziel auswählen", Toast.LENGTH_SHORT, true).show();
            return;
        }

        if (binding.yourGoalImage.getDrawable() == binding.goalGainWeightImage.getDrawable()) {
            result = 1;
        } else if (binding.yourGoalImage.getDrawable() == binding.goalLooseWeightImage.getDrawable()) {
            result = 2;
        } else if (binding.yourGoalImage.getDrawable() == binding.yourGoalBecomeFitterImage.getDrawable()) {
            result = 3;
        }

        mViewModel.getUser().setFocusToTrain(result);

        FragmentManager mFragmentManager = getFragmentManager();
        androidx.fragment.app.FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.setCustomAnimations(R.anim.animate_slide_in_right, R.anim.animate_slide_out_left);


        Fragment f = null;
        f = mFragmentManager.findFragmentByTag("WorkoutCreated");
        if (f == null) {
            mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartCreateWorkoutPlan(), "WorkoutCreated");
            mFragmentTransaction.addToBackStack("WorkoutCreated");
        } else {
            mFragmentManager.popBackStack();
            mFragmentManager.popBackStack("WorkoutCreated", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        mFragmentTransaction.commit();
    }


    static class MyAsyncTask extends android.os.AsyncTask<Object, Void, Void> {
        private WeakReference<_FragmentStartYourGoal> fragmentWeakReference;

        MyAsyncTask(_FragmentStartYourGoal fragment) {
            fragmentWeakReference = new WeakReference<_FragmentStartYourGoal>(fragment);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            _FragmentStartYourGoal fragment = fragmentWeakReference.get();
            if (fragment == null)
                return;


        }

        @Override
        protected Void doInBackground(Object... objects) {
            try {
                _FragmentStartYourGoal fragment = fragmentWeakReference.get();
                if (fragment == null) return null;

                ImageView imageView = (ImageView) objects[0];
                TextView textView = (TextView) objects[1];

                new Handler().postDelayed(() -> {
                    fragment.binding.yourGoalImage.setImageDrawable(imageView.getDrawable());
                    fragment.binding.yourGoalText.setText(textView.getText().toString().replaceAll("\\n", " "));
                }, START_ANIMATION_TIME - 100);

                return null;

            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

}