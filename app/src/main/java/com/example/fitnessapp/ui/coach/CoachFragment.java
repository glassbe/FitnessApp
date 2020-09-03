package com.example.fitnessapp.ui.coach;

import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.emredavarci.noty.Noty;
import com.example.fitnessapp.R;
import com.example.fitnessapp.ViewModel.StatusUpdateViewModel;
import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.databinding.FragmentCoachHomeBinding;
import com.example.fitnessapp.db.Entity.StatusUpdate;
import com.example.fitnessapp.db.Entity.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
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

        //Set Observer
        _status.getLiveStatusOfToday().observe(getActivity(),statusUpdate -> {

        });

        _status.getLiveStatusOfSelectedStatus().observe(getActivity(),statusUpdate -> {
            if(statusUpdate != null)
                if(statusUpdate.getId() == _status.getStatusOfToday().getId()){
                    _status.setStatusOfToday(statusUpdate);
                    mStatus = statusUpdate;
                    setMainCoachInformation();
                }
        });


        //Get Current User and Status and ...
        mUser = _user.getUser();
        mStatus = getStatusOfToday();

        //SetOnClickListeners
        binding.btnCoachStatusMyStats.setOnClickListener(v -> OnClickMyStatus());
        binding.btnCoachStatusUpdateStatus.setOnClickListener(v -> OnClickStatusUpdate());

        binding.coachHomeAvatarImg.setOnClickListener(v -> dialogStartTodaysUpdate());
        binding.coachHomeTextInputWeight.setOnClickListener(v -> dialogStartTodaysUpdate());
        binding.coachHomeTextInputEnergyLevel.setOnClickListener(v -> dialogStartTodaysUpdate());


        //Insert Information into Main Page
        setMainCoachInformation();



        //set Dialog if User isnt UpToDate
        if(!mStatus.isCompletedUpdate())
            dialogForTodaysUpdate();




        _user.getLiveUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mUser = user;
            }
        });


        return view;
    }

    private void setMainCoachInformation() {

        //Set Data
        int energieLevel = mStatus.getEnergieLevel();
        float weight = mStatus.getWeight();
        String photoPath = mStatus.getPicturePath();

        binding.coachHomeTextInputEnergyLevel.setText(String.valueOf(energieLevel));
        binding.coachHomeTextInputWeight.setText(String.valueOf(weight));
        Glide.with(getContext())
                .load(photoPath)
                .centerCrop()
                .placeholder(R.drawable.avatar_status_picture_my_blue)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.avatar_status_picture_my_blue)
                .into(binding.coachHomeAvatarImg);
    }


    private void dialogStartTodaysUpdate() {
        new MaterialAlertDialogBuilder(getContext(), SweetAlertDialog.SUCCESS_TYPE)
//                     Add customization options here
                .setTitle("Update deinen täglichen Status?")
                .setIcon(R.drawable.ic_baseline_help_outline_24px)
                .setPositiveButton(R.string.dialog_ok, (dialog, which) -> {
                    OnClickStatusUpdate();
                })
                .setNegativeButton("Nein", (dialog, which) -> {})
                .show();
    }

    private StatusUpdate getStatusOfToday() {
        StatusUpdate statusOfToday;
        Date today = Calendar.getInstance().getTime();


        statusOfToday = _status.mStatusRepo.getUpdateForUserForOneDay(mUser.getEmail(), today);
        if (statusOfToday != null) {

            Toasty.success(getContext(), "Status is loaded for today").show();

            _status.setStatusOfToday(statusOfToday);

            return statusOfToday;
        }

        //Create empty Status
        _status.mStatusRepo.insertNewUpdate(new StatusUpdate(mUser.getEmail(), "", (float) 0, 0, (float) 0, 0));

        //shows Dialog to Update for Today
        dialogForTodaysUpdate();

        Toasty.success(getContext(), "Created status of today").show();

        statusOfToday = _status.mStatusRepo.getUpdateForUserForOneDay(mUser.getEmail(), today);

        _status.setStatusOfToday(statusOfToday);

        return statusOfToday;
    }

    private void dialogForTodaysUpdate() {
        Noty.init(getActivity().getWindow().getContext(), "Update your Status daily!", binding.getRoot(), Noty.WarningStyle.ACTION)
                .setActionText("Update Now!")
                .setWarningBoxBgColor("#D43A3A")
                .setWarningTappedColor("#FFFFFF")
                .setWarningBoxPosition(Noty.WarningPos.TOP)
                .setWarningInset(0, 0, 0, 0)
                .setWarningBoxRadius(0, 0, 0, 0)
                .setAnimation(Noty.RevealAnim.SLIDE_DOWN, Noty.DismissAnim.BACK_TO_TOP, 400, 400)
                .setClickListener(new Noty.ClickListener() {
                    @Override
                    public void onClick(Noty warning) {
                        Toasty.info(getContext(), "Function will be implemented soon.", Toasty.LENGTH_SHORT, true).show();
                    }
                })
                .show();
    }

    private void OnClickStatusUpdate() {

        //Set Status that StatusUpdateFragment can use the selected one
        _status.setSelectedStatus(_status.getStatusOfToday());

        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

//        mFragmentManager.findFragmentByTag(mStatus.getTimestamp() + mStatus.getUserMail() + "StatusUpdateofToday");
        mFragmentManager.popBackStack();

        mFragmentTransaction.replace(R.id.coach_fullview_frame, new StatusUpdateFragment(), mStatus.getTimestamp() + mStatus.getUserMail() + "StatusUpdateofToday");
        mFragmentTransaction.addToBackStack(mStatus.getTimestamp() + mStatus.getUserMail() + "StatusUpdateofToday");


        mFragmentTransaction.commit();

    }


    private void OnClickMyStatus() {

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
