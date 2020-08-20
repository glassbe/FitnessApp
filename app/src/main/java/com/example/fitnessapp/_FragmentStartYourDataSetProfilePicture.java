package com.example.fitnessapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.onurkaganaldemir.ktoastlib.KToast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class _FragmentStartYourDataSetProfilePicture extends Fragment{

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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout._fragment_start_your_data_set_profile_pic, container, false);

        //Get User by Mail from ViewModel
        mViewModel = new ViewModelProvider(getActivity()).get(_ActivityStart_ViewModel.class);
        mUser = _IUser.mUserRepo.getUser(mViewModel.getEmail().getValue());

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
        _IUser.mUserRepo.UpdateInfo(mUser);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
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
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        requestForSave(mRoundProfile);

        Fragment f = null;
        f = mFragmentManager.findFragmentByTag("setData");
        if(f == null){
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
        try{
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getContext(),this);
        } catch(Exception e){
            Log.getStackTraceString(e);
            KToast.infoToast(getActivity(), "Need Camera Permissions", Gravity.BOTTOM, KToast.LENGTH_SHORT);
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

        // Save all the fields in the Profile
        switch (view.getId()) {
            case R.id.photo_round_profile:
//                if (mUser != null) {
                    mUser.setProfilePicPath(mCurrentPhotoPath);
                    profileToUpdate = true;
//                }
                break;
        }

        if (profileToUpdate) {
            _IUser.mUserRepo.UpdateInfo(mUser);
            KToast.infoToast(getActivity(), "Profile Pic set", Gravity.BOTTOM, KToast.LENGTH_SHORT);
        }
    }



}
