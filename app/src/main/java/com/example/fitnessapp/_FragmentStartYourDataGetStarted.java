package com.example.fitnessapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set Shared Elements to its position
//        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout._fragment_start_your_data_get_started, container, false);

        mBtn_getStarted = view.findViewById(R.id.btn_getStarted);
        mBtn_getStarted.setOnClickListener(v -> btn_getStartedClicked());

        return view;
    }


    private void btn_getStartedClicked() {
        new Thread(() ->
        {
            FragmentManager mFragmentManager = getFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
//            mFragmentTransaction.setCustomAnimations(R.anim.animate_slide_in_right, R.anim.animate_slide_out_left);


            Fragment f = null;
            f = mFragmentManager.findFragmentByTag("setProfilePicture");
            if (f == null) {
                mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartYourDataSetProfilePicture(), "setProfilePicture");
                mFragmentTransaction.addToBackStack("setProfilePicture");
            } else {
                mFragmentManager.popBackStack();
                mFragmentManager.popBackStack("setProfilePicture", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            mFragmentTransaction.commit();
        }).start();
    }


}
