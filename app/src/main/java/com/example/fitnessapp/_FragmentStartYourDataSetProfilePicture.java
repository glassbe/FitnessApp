package com.example.fitnessapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessapp.DAO.Profile;
import com.example.fitnessapp.utils.EditableInputView.EditableInputView;
import com.example.fitnessapp.utils.ImageUtil;
import com.example.fitnessapp.utils.RealPathUtil;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.graphics.ImageDecoder.decodeBitmap;


/**
 * A simple {@link Fragment} subclass.
 */
public class _FragmentStartYourDataSetProfilePicture extends Fragment{

    EditableInputView sizeEdit = null;
    CircularImageView roundProfile = null;
    String mCurrentPhotoPath = null;

    private Profile mProfile = null;
    ImageUtil imgUtil = null;
    private EditableInputView.OnTextChangedListener itemOnTextChange = this::requestForSave;


    private View.OnClickListener onClickPhoto = v -> CreatePhotoSourceDialog();


    private ArrayList<String> permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 107;
    private Button mBtn_photoButton;


    public _FragmentStartYourDataSetProfilePicture() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout._fragment_start_your_data_set_profile_pic, container, false);




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            mBtn_photoButton = view.findViewById(R.id.btn_take_picture);
            roundProfile = view.findViewById(R.id.photo_round_profile);

            imgUtil = new ImageUtil(roundProfile);

            /* Initialisation des boutons */


            mBtn_photoButton.setOnClickListener(onClickPhoto);


            imgUtil.setOnDeleteImageListener(imgUtil -> {
                imgUtil.getView().setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_profile_black));
                mCurrentPhotoPath = null;
                requestForSave(imgUtil.getView());
            });
        } catch (Exception e) {
            onViewCreated(view, savedInstanceState);
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        roundProfile.post(() -> {
            refreshData();
            sizeEdit.setOnTextChangeListener(itemOnTextChange);
//            birthdayEdit.setOnTextChangeListener(itemOnTextChange);
//            nameEdit.setOnTextChangeListener(itemOnTextChange);
//            genderEdit.setOnTextChangeListener(itemOnTextChange);
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) refreshData();
    }

    private void refreshData() {
        mProfile = getProfil();

//        /* Initialisation des valeurs */
//        if (mProfile.getSize() == 0) {
//            sizeEdit.setText("");
//            sizeEdit.setHint(getString(R.string.profileEnterYourSize));
//        } else {
//            sizeEdit.setText(String.valueOf(mProfile.getSize()));
//        }

//        switch (mProfile.getGender()) {
//            case Gender.MALE:
//                genderEdit.setText(getString(R.string.maleGender));
//                break;
//            case Gender.FEMALE:
//                genderEdit.setText(getString(R.string.femaleGender));
//                break;
//            case Gender.OTHER:
//                genderEdit.setText(getString(R.string.otherGender));
//                break;
//            default:
//                genderEdit.setText("");
//                genderEdit.setHint(getString(R.string.enter_gender_here));
//        }

//        if (mProfile.getBirthday().getTime() == 0) {
//            birthdayEdit.setText("");
//            birthdayEdit.setHint(getString(R.string.profileEnterYourBirthday));
//        } else {
//            birthdayEdit.setText(DateConverter.dateToLocalDateStr(mProfile.getBirthday(), getContext()));
//            //sizeEdit.setNormalColor();
//        }
//
//        nameEdit.setText(mProfile.getName());

//        if (mProfile.getPhoto() != null) {
            ImageUtil.setPic(roundProfile, mProfile.getPhoto());
            roundProfile.invalidate();
//        } else
//            roundProfile.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_profile_black));
    }

    private void requestForSave(View view) {
        boolean profileToUpdate = false;

        // Save all the fields in the Profile (Database)
//        switch (view.getId()) {
//            case R.id.name:
//                mProfile.setName(nameEdit.getText());
//                profileToUpdate = true;
//                break;
//            case R.id.size:
//                try {
//                    mProfile.setSize((int) Float.parseFloat(sizeEdit.getText()));
//                } catch (NumberFormatException e) {
//                    mProfile.setSize(0);
//                }
//                profileToUpdate = true;
//                break;
//            case R.id.birthday:
//                mProfile.setBirthday(DateConverter.localDateStrToDate(birthdayEdit.getText(), getContext()));
//                profileToUpdate = true;
//                break;
//            case R.id.photo:
//                mProfile.setPhoto(mCurrentPhotoPath);
//                profileToUpdate = true;
//                break;
//            case R.id.gender:
//                int lGender = Gender.UNKNOWN;
//                if (genderEdit.getText().equals(getString(R.string.maleGender))) {
//                    lGender = Gender.MALE;
//                } else if (genderEdit.getText().equals(getString(R.string.femaleGender))) {
//                    lGender = Gender.FEMALE;
//                } else if (genderEdit.getText().equals(getString(R.string.otherGender))) {
//                    lGender = Gender.OTHER;
//                }
//                mProfile.setGender(lGender);
//                profileToUpdate = true;
//                break;
//        }
//
//        if (profileToUpdate) {
//            mDb.updateProfile(mProfile);
//            KToast.infoToast(getActivity(), mProfile.getName() + " updated", Gravity.BOTTOM, KToast.LENGTH_SHORT);
//            mActivity.setCurrentProfil(mProfile);
//        }
    }

    private Profile getProfil() {

//            return ((MainActivity) getActivity()).getCurrentProfil();
            return MainActivity.getCurrentProfil();

    }

    private boolean CreatePhotoSourceDialog() {
        if (imgUtil == null)
            imgUtil = new ImageUtil();

        return imgUtil.CreatePhotoSourceDialog(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ImageUtil.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    mCurrentPhotoPath = imgUtil.getFilePath();
                    ImageUtil.setPic(roundProfile, mCurrentPhotoPath);
                    ImageUtil.saveThumb(mCurrentPhotoPath);
                    imgUtil.galleryAddPic(this, mCurrentPhotoPath);
                    requestForSave(roundProfile);
                }
                break;
            case ImageUtil.REQUEST_PICK_GALERY_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    String realPath;
                    realPath = RealPathUtil.getRealPath(this.getContext(), data.getData());

                    ImageUtil.setPic(roundProfile, realPath);
                    ImageUtil.saveThumb(realPath);
                    mCurrentPhotoPath = realPath;
                    requestForSave(roundProfile);
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
                        storageDir = Environment.getExternalStoragePublicDirectory("/FastnFitness/Camera/");
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

                    ImageUtil.setPic(roundProfile, realPath);
                    ImageUtil.saveThumb(realPath);
                    mCurrentPhotoPath = realPath;
                    requestForSave(roundProfile);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;
        }
    }

}
