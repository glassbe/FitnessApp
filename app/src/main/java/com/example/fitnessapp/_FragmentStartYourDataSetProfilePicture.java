package com.example.fitnessapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.utils.ImageUtil;
import com.example.fitnessapp.utils.RealPathUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class _FragmentStartYourDataSetProfilePicture extends Fragment {

    //Use Services
    private UserViewModel _IUser;
    private User mUser = null;

    private _ActivityStart_ViewModel mViewModel = null;

    private Button mBtn_take_picture;
    private Uri mResultUri;
    private ImageUtil imgUtil = null;
    private CircularImageView mRoundProfile = null;
    private String mCurrentPhotoPath;
    private Button mBtn_set_profile_pic;


    public _FragmentStartYourDataSetProfilePicture() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _IUser = new ViewModelProvider(this).get(UserViewModel.class);

        setupWindowAnimations();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout._fragment_start_your_data_set_profile_pic, container, false);

        //Get ViewModel
        mViewModel = new ViewModelProvider(getActivity()).get(_ActivityStart_ViewModel.class);
//        mUser = _IUser.mUserRepo.getUser(mViewModel.getEmail().getValue());

        //Set Button Take Picture Adapter
        mBtn_take_picture = view.findViewById(R.id.btn_take_picture);
        mBtn_take_picture.setOnClickListener(v -> takePicture());

        //Set Button Set Profile Picture Adapter
        mBtn_set_profile_pic = view.findViewById(R.id.btn_set_profile_pic);
        mBtn_set_profile_pic.setOnClickListener(v -> clickSetProfilePic());

        mRoundProfile = view.findViewById(R.id.photo_round_profile);

        /* Initialisation des valeurs */
        imgUtil = new ImageUtil(mRoundProfile);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        _IUser.mUserRepo.UpdateInfo(mUser);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case ImageUtil.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    mCurrentPhotoPath = imgUtil.getFilePath();
                    ImageUtil.setPic(mRoundProfile, mCurrentPhotoPath);
                    ImageUtil.saveThumb(mCurrentPhotoPath);
                    imgUtil.galleryAddPic(this, mCurrentPhotoPath);
                }
                break;
            case ImageUtil.REQUEST_PICK_GALERY_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    String realPath;
                    realPath = RealPathUtil.getRealPath(this.getContext(), data.getData());

                    ImageUtil.setPic(mRoundProfile, realPath);
                    ImageUtil.saveThumb(realPath);
                    mCurrentPhotoPath = realPath;
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == Activity.RESULT_OK) {
                    Uri resultUri = result.getUri();
                    String realPath;
                    realPath = RealPathUtil.getRealPath(this.getContext(), resultUri);

                    // Le fichier est crée dans le cache.
                    // Déplacer le fichier dans le repertoire de FastNFitness
                    File SourceFile = new File(realPath);

                    File storageDir = null;
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "JPEG_" + timeStamp + ".jpg";
                    String state = Environment.getExternalStorageState();
                    if (!Environment.MEDIA_MOUNTED.equals(state)) {
                        return;
                    } else {
                        //We use the FastNFitness directory for saving our .csv file.
                        storageDir = Environment.getExternalStoragePublicDirectory("/TrueFitness/Camera/");
                        if (!storageDir.exists()) {
                            storageDir.mkdirs();
                        }
                    }
                    new File(storageDir.getPath() + imageFileName);
                    File DestinationFile;

                    try {
                        DestinationFile = imgUtil.moveFile(SourceFile, storageDir);
                        Log.v("Moving", "Moving file successful.");
                        realPath = DestinationFile.getPath();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.v("Moving", "Moving file failed.");
                    }

                    ImageUtil.setPic(mRoundProfile, realPath);
                    ImageUtil.saveThumb(realPath);
                    mCurrentPhotoPath = realPath;
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;
        }
    }

    //===============
    //private Functions

    private void clickSetProfilePic() {
        if (mCurrentPhotoPath == null) {
            new MaterialAlertDialogBuilder(getContext(), SweetAlertDialog.SUCCESS_TYPE)
//                     Add customization options here
                    .setTitle("Ohne Profilbild fortfahren?")
                    .setIcon(R.drawable.ic_baseline_help_outline_24px)
                    .setPositiveButton(R.string.dialog_ok, (dialog, which) -> {
                        mCurrentPhotoPath = "";
                        clickSetProfilePic();
                    })
                    .setNegativeButton("Foto machen", (dialog, which) -> takePicture())
                    .show();
            return;

//            if(mCurrentPhotoPath == null) {
//                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("Ohne Profilbild fortfahren?")
//                        .setCustomImage(R.drawable.__arrow_up)
//    //                    .setContentText(getContext().getResources().getText(R.string.profileCreated).toString())
//    //                    .setConfirmClickListener(sDialog -> nextSlide())
//                        .setNeutralClickListener(sweetAlertDialog -> {})
//                        .show();
//            return;
        }

        requestForSave(mRoundProfile);


        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.setCustomAnimations(R.anim.animate_slide_in_right, R.anim.animate_slide_out_left);


        Fragment f = null;
        f = mFragmentManager.findFragmentByTag("setData");
        if (f == null) {
            mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartYourDataSetData(), "setData");
            mFragmentTransaction.addToBackStack("setData");
        } else {
            mFragmentManager.popBackStack();
            mFragmentManager.popBackStack("setData", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        mFragmentTransaction.commit();
    }

    private void takePicture() {
        //Get Permissions, if not granted yet
        requestPermissionForWriting(this);
        try {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getContext(), this);
        } catch (Exception e) {
            Log.getStackTraceString(e);
            Toasty.error(getActivity(), "Need Camera Permissions", Toasty.LENGTH_SHORT, true).show();
        }

    }

    private void requestPermissionForWriting(Fragment pF) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(pF.getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.

            int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 102;
            ActivityCompat.requestPermissions(pF.getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        }
    }

    protected void requestForSave(View view) {
        boolean profileToUpdate = false;

        // Save all the fields in the ViewModel
        switch (view.getId()) {
            case R.id.photo_round_profile:
//                if (mUser != null) {
//                    mUser.setProfilePicPath(mCurrentPhotoPath);
                mViewModel.setPhotoPath(mCurrentPhotoPath);
                profileToUpdate = true;
//                }
                break;
        }

        if (profileToUpdate) {
//            _IUser.mUserRepo.UpdateInfo(mUser);
//            KToast.successToast(getActivity(), "Profilbild gespeichert", Gravity.BOTTOM, KToast.LENGTH_SHORT);
            Toasty.success(getActivity(), "Profilbild gespeichert", Toasty.LENGTH_SHORT, true).show();
        }
    }


    private void setupWindowAnimations() {
        Window window = getActivity().getWindow();
        Slide slide = new Slide();
        slide.setDuration(1000);
        slide.setInterpolator(new LinearInterpolator());
        slide.setSlideEdge(Gravity.RIGHT);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        window.setEnterTransition(slide); // The Transition to use to move Views into the initial Scene.
        window.setReturnTransition(slide); //
//        window.setExitTransition(slide); // The Transition to use to move Views out of the scene when calling a new Activity.
//        window.setReenterTransition(slide);
    }


}
