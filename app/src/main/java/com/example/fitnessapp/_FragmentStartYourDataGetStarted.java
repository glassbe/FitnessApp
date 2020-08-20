package com.example.fitnessapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;
import at.wirecube.additiveanimations.additive_animator.view_visibility.ViewVisibilityAnimation;


/**
 * A simple {@link Fragment} subclass.
 */
public class _FragmentStartYourDataGetStarted extends Fragment{

    private Button mBtn_getStarted;
    private ImageView mImg_yourData;


    public _FragmentStartYourDataGetStarted() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout._fragment_start_your_data_get_started, container, false);


        mImg_yourData = view.findViewById(R.id.img_yourData);
        mImg_yourData.setY(500);
        new AdditiveAnimator()
                .animate(mImg_yourData)
                .setDuration(750)
                .visibility(ViewVisibilityAnimation.fadeInAndTranslateBack())
                .start();


        mBtn_getStarted = view.findViewById(R.id.btn_getStarted);
        mBtn_getStarted.setAlpha(0);
        new AdditiveAnimator()
                .animate(mBtn_getStarted)
                .setDuration(750)
                .alpha(1)
                .start();

//        mBtn_getStarted.setAnimation(mAnim_alpha_in);
        mBtn_getStarted.setOnClickListener(v -> btn_getStartedClicked());

        return view;
    }


    private void btn_getStartedClicked() {
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        Fragment f = null;
        f = mFragmentManager.findFragmentByTag("setProfilePicture");
        if(f == null){
            mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartYourDataSetProfilePicture(), "setProfilePicture");
            mFragmentTransaction.addToBackStack("setProfilePicture");
        } else {
            mFragmentManager.popBackStack();
            mFragmentManager.popBackStack("setProfilePicture", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        mFragmentTransaction.commit();
    }


}
