package com.example.fitnessapp.ui.coach;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.ExerciseDetailsFragment;
import com.example.fitnessapp.R;
import com.example.fitnessapp.ViewModel.StatusUpdateViewModel;
import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.databinding.FragmentCoachHomeBinding;
import com.example.fitnessapp.db.Entity.StatusUpdate;
import com.example.fitnessapp.db.Entity.User;

import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class CoachFragment extends Fragment {

    private FragmentCoachHomeBinding binding;

    private StatusUpdateViewModel _status;
    private UserViewModel _user;

    private StatusUpdate mStatus;
    private User mUser;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate/Bind the layout for this fragment
        binding = FragmentCoachHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //Set ViewModel (getting Data)
        _status = new ViewModelProvider(getActivity()).get(StatusUpdateViewModel.class);
        _user = new ViewModelProvider(getActivity()).get(UserViewModel.class);


        //Get Current User and Status and ...
        mUser = _user.getUser();
        mStatus = getStatusOfToday();






        binding.btnCoachStatusMyStats.setOnClickListener(v -> ClickOnMyStats());




        _user.getLiveUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mUser = user;
            }
        });


        return view;
    }

    private StatusUpdate getStatusOfToday() {
        StatusUpdate statusOfToday;
        Date today = Calendar.getInstance().getTime();

        statusOfToday = _status.mStatusRepo.getUpdateForUserForOneDay(mUser.getEmail(), today);
        if(statusOfToday == null)
             _status.mStatusRepo.insertNewUpdate(new StatusUpdate(mUser.getEmail(),"",(float) 0,0,(float) 0, 0));

        statusOfToday = _status.mStatusRepo.getUpdateForUserForOneDay(mUser.getEmail(), today);
        if(statusOfToday == null){
            Toasty.error(getContext(),"Status of today couldn't get loaded",Toasty.LENGTH_SHORT, true).show();
            return null;
        }

        Toasty.success(getContext(),"Status is loaded for today");
        return statusOfToday;
    }

    private void ClickOnMyStats() {


        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        // Try Block
        try {


            // New Fragment Block
            Fragment f = null;
            String TAG = mStatus.getTimestamp() + mStatus.getUserMail() + "StatsOverview";

            f = mFragmentManager.findFragmentByTag(TAG);
            if(f == null){
                mFragmentTransaction.replace(R.id.coach_fullview_frame, new CoachStatusFragment(), TAG);
                mFragmentTransaction.addToBackStack(TAG);
            } else {
                mFragmentManager.popBackStack();
                mFragmentManager.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            mFragmentTransaction.commit();

        } catch (Exception e){
            Log.getStackTraceString(e);
        }

    }
}
