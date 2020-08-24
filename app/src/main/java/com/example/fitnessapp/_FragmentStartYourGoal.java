package com.example.fitnessapp;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.databinding.FragmentStartYourGoalBinding;
import com.example.fitnessapp.db.Entity.User;
import com.onurkaganaldemir.ktoastlib.KToast;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;

import static com.example.fitnessapp._FragmentStartYourGoal.END_ANIMATION_TIME;
import static com.example.fitnessapp._FragmentStartYourGoal.START_ANIMATION_TIME;

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
    private ImageView mGoal_gain_weight_image;
    private TextView mGoal_gain_weight_text;
    private ImageView mGoal_loose_weight_image;
    private TextView mGoal_loose_weight_text;
    private ImageView mYour_goal_become_fitter_image;
    private TextView mYour_goal_become_fitter_text;
    private Button mBtn_create_workout_plan;
    private ImageView mYour_goal_image;
    private TextView mYour_goal_text;
    private LinearLayout mYour_goal_image_holder;

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
        },START_ANIMATION_TIME);

        endAnimationOfGoal(imageview, textview);

    }

    private void ClickLooseWeightGoal(ImageView imageview, TextView textview) {
        String text = textview.getText().toString();
        Drawable image = imageview.getDrawable();

        startAnimationOfGoal(imageview, textview);

        new Handler().postDelayed(() -> {
            binding.yourGoalImage.setImageDrawable(image);
            binding.yourGoalText.setText(text.replaceAll("\\n", " "));
        },START_ANIMATION_TIME);

        endAnimationOfGoal(imageview, textview);
    }


    private void ClickGainWeightGoal(ImageView imageview, TextView textview) {
        String text = textview.getText().toString();
        Drawable image = imageview.getDrawable();

        startAnimationOfGoal(imageview, textview);

        new Handler().postDelayed(() -> {
            binding.yourGoalImage.setImageDrawable(image);
            binding.yourGoalText.setText(text.replaceAll("\\n", " "));
        },START_ANIMATION_TIME);

        endAnimationOfGoal(imageview, textview);
    }

    private void endAnimationOfGoal(ImageView imageview, TextView textview) {
        AdditiveAnimator
                .animate(imageview, textview)
                .setStartDelay(START_ANIMATION_TIME)
                .setDuration(END_ANIMATION_TIME)
                .alpha(1)
                .start();
    }

    private void startAnimationOfGoal(ImageView imageview, TextView textview) {
        AdditiveAnimator
                .animate(imageview, textview)
                .setDuration(START_ANIMATION_TIME)
                .alpha(0)
                .start();
    }

    private void ClickCreateWorkoutPlan() {
        int result = 0;

        if(binding.yourGoalText.getText().toString().length() == 0){
            KToast.warningToast(getActivity(),"Ziel auswählen", Gravity.BOTTOM, KToast.LENGTH_SHORT);
            return;
        }

        if(binding.yourGoalImage.getDrawable() == binding.goalGainWeightImage.getDrawable()){
            result = 1;
        }
        else if(binding.yourGoalImage.getDrawable() == binding.goalLooseWeightImage.getDrawable()){
            result = 2;
        }
        else if(binding.yourGoalImage.getDrawable() == binding.yourGoalBecomeFitterImage.getDrawable()){
            result = 3;
        }

        mViewModel.getUser().setFocusToTrain(result);

        FragmentManager mFragmentManager = getFragmentManager();
        androidx.fragment.app.FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        Fragment f = null;
        f = mFragmentManager.findFragmentByTag("WorkoutCreated");
        if(f == null){
            mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartYourGoal(), "WorkoutCreated");
            mFragmentTransaction.addToBackStack("WorkoutCreated");
        } else {
            mFragmentManager.popBackStack();
            mFragmentManager.popBackStack("WorkoutCreated", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        mFragmentTransaction.commit();
    }


}

class MyAsyncTask extends android.os.AsyncTask<Object, Void, Void> {
    private WeakReference<_FragmentStartYourGoal> fragmentWeakReference;

    MyAsyncTask(_FragmentStartYourGoal fragment){
        fragmentWeakReference = new WeakReference<_FragmentStartYourGoal>(fragment);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        _FragmentStartYourGoal fragment = fragmentWeakReference.get();
        if(fragment == null)
            return;


    }

    @Override
    protected Void doInBackground(Object... objects) {
        try{
            _FragmentStartYourGoal fragment = fragmentWeakReference.get();
            if(fragment == null) return null;

            ImageView imageView = (ImageView) objects[0];
            TextView textView = (TextView) objects[1];

            new Handler().postDelayed(() -> {
                fragment.binding.yourGoalImage.setImageDrawable(imageView.getDrawable());
                fragment.binding.yourGoalText.setText(textView.getText().toString().replaceAll("\\n", " "));
            }, START_ANIMATION_TIME);

            return null;

        } catch (Exception e){
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