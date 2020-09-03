package com.example.fitnessapp.ui.coach;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fitnessapp.R;
import com.example.fitnessapp.ViewModel.StatusUpdateViewModel;
import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.databinding.FragmentCoachUpdateStatusBinding;
import com.example.fitnessapp.db.Entity.StatusUpdate;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.utils.ImageUtil;
import com.example.fitnessapp.utils.RealPathUtil;
import com.rey.material.widget.Slider;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class StatusUpdateFragment extends Fragment {

    FragmentCoachUpdateStatusBinding binding;
    private StatusUpdateViewModel _status;
    private UserViewModel _user;
    private User mUser;
    private StatusUpdate mStatus;

    private ImageUtil imgUtil = null;

    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean maxOneDot = false;
            for (int i = start; i < end; ++i) {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890.,]").matcher(String.valueOf(source.charAt(i))).matches()) {
                    return "";
                }
            }

            return null;
        }
    };


    private TextView mTextView;
    private Slider mSlider;
    private LinearLayout mLinearLayoutSlider;

    private EditText mEditTextNumber;
    private LinearLayout mLinearLayoutNumber;
    private StatusUpdate mOldStatus;


    public static StatusUpdateFragment newInstance(String param1, String param2) {
        StatusUpdateFragment fragment = new StatusUpdateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCoachUpdateStatusBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        //Set ViewModel (getting Data)
        _status = new ViewModelProvider(getActivity()).get(StatusUpdateViewModel.class);
        _user = new ViewModelProvider(getActivity()).get(UserViewModel.class);



        //Get Current User and Status and ...
        mUser = _user.getUser();
        mStatus = _status.getSelectedStatus();

        mOldStatus = mStatus;


        //Set Data
        updateUiWithData();

        //Init Dialog Elements
        initAndUpdateDialogElements();



        //SetOnClickListeners
        binding.updateStatusUpdatePhotoImageview.setOnClickListener(v -> {});
        binding.updateStatusUpdateTakePhotoBtn.setOnClickListener(v -> OnClickPhoto());

        binding.updateStatusUpdateBottomBackbutton.setOnClickListener(v -> OnClickBackButton());
        binding.updateStatusUpdateBottomSavebutton.setOnClickListener(v -> OnClickSave());

        binding.updateStatusUpdateDesireToTrainInput.setOnClickListener(v -> OnClickDesireToTrain());
        binding.updateStatusUpdateEnergyLevelInput.setOnClickListener(v -> OnClickEnergyLevel());
        binding.updateStatusUpdateSleepQualityInput.setOnClickListener(v -> OnClickSleepQuality());
        binding.updateStatusUpdateSleepQuantityInput.setOnClickListener(v -> OnClickSleepQuantity());
        binding.updateStatusUpdateStepsInput.setOnClickListener(v -> OnClickSteps());
        binding.updateStatusUpdateWeightInput.setOnClickListener(v -> OnClickWeight());
        binding.updateStatusUpdateStressLevelInput.setOnClickListener(v -> OnClickStressLevel());

        binding.coachUpdateStatusBackground.setOnClickListener(v -> {});



        /* Initialisation des valeurs */
        imgUtil = new ImageUtil(binding.updateStatusUpdatePhotoImageview);




        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    private void OnClickWeight() {

        initAndUpdateDialogElements();
        String m = String.valueOf(mStatus.getWeight());

        mEditTextNumber.setText(m);

        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(getContext().getString(R.string.edit_value) + " in kg")
                .setConfirmClickListener(sDialog -> {

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(mEditTextNumber.getWindowToken(), 0);
                    }

                    if(mEditTextNumber.getText().length() == 0) return;

                    binding.updateStatusUpdateWeightInput.setText(String.valueOf(mEditTextNumber.getText()) + " kg");

                    mStatus.setWeight(Float.parseFloat(mEditTextNumber.getText().toString()));

                    sDialog.dismissWithAnimation();
                });
        dialog.setCustomView(mLinearLayoutNumber);
        dialog.show();
    }

    private void OnClickSteps() {
        initAndUpdateDialogElements();
        mEditTextNumber.setText(String.valueOf(mStatus.getSteps()));

        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(getContext().getString(R.string.edit_value))
                .setConfirmClickListener(sDialog -> {


// Try Block
                    try {


                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(mEditTextNumber.getWindowToken(), 0);
                        }

                        if(mEditTextNumber.getText().length() == 0) return;

                        binding.updateStatusUpdateStepsInput.setText(String.valueOf(mEditTextNumber.getText()));

                        mStatus.setSteps(Integer.parseInt(String.valueOf(mEditTextNumber.getText())));

                        sDialog.dismissWithAnimation();



                    } catch (Exception e){
                        Log.getStackTraceString(e);
                    }



                });
        dialog.setCustomView(mLinearLayoutNumber);
        dialog.show();
    }

    private void OnClickSleepQuantity() {
//        initAndUpdateDialogElements();
//        mEditTextNumber.setText(String.valueOf(mStatus.getSleepQuantity()));
//
//        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
//                .setTitleText(getContext().getString(R.string.edit_value) + " in Std.")
//                .setConfirmClickListener(sDialog -> {
//
//                    // Try Block
//                    try {
//
//
//                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
//                        if (imm != null) {
//                            imm.hideSoftInputFromWindow(mEditTextNumber.getWindowToken(), 0);
//                        }
//
//                        if(mEditTextNumber.getText().length() == 0) return;
//
//                        binding.updateStatusUpdateSleepQuantityInput.setText(String.valueOf(mEditTextNumber.getText()) + " Std.");
//
//                        mStatus.setSleepQuantity(Float.parseFloat(String.valueOf(mEditTextNumber.getText())));
//
//                        sDialog.dismissWithAnimation();
//
//
//                    } catch (Exception e){
//                        Log.getStackTraceString(e);
//                    }
//
//
//                });
//        dialog.setCustomView(mLinearLayoutNumber);
//        dialog.show();


        initAndUpdateDialogElements();
        mSlider.setValue(mStatus.getSleepQuantity(),true);
        mTextView.setText(String.valueOf(mStatus.getSleepQuantity()));
        mSlider.setValueRange(0,24,true);
        mSlider.setOnPositionChangeListener((view, fromUser, oldPos, newPos, oldValue, newValue) -> {
            mTextView.setText(String.valueOf(new DecimalFormat("##.#").format(mSlider.getExactValue())));
        });

        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(getContext().getString(R.string.edit_value))
                .setConfirmClickListener(sDialog -> {
                    binding.updateStatusUpdateSleepQuantityInput.setText(String.valueOf(new DecimalFormat("##.#").format(mSlider.getExactValue())) + " Std.");

                    mStatus.setSleepQuantity(Float.parseFloat(new DecimalFormat("##.#").format(mSlider.getExactValue())));

                    sDialog.dismissWithAnimation();
                });
        dialog.setCustomView(mLinearLayoutSlider);
        dialog.show();
    }

    private void OnClickSleepQuality() {
        initAndUpdateDialogElements();
        mSlider.setValue(mStatus.getSleepQuality(),true);
        mTextView.setText(String.valueOf(mStatus.getSleepQuality()));

        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(getContext().getString(R.string.edit_value))
                .setConfirmClickListener(sDialog -> {
                    binding.updateStatusUpdateSleepQualityInput.setText(String.valueOf(mSlider.getValue()) + " / 100");

                    mStatus.setSleepQuality(mSlider.getValue());

                    sDialog.dismissWithAnimation();
                });
        dialog.setCustomView(mLinearLayoutSlider);
        dialog.show();
    }

    private void OnClickStressLevel() {
        initAndUpdateDialogElements();
        mSlider.setValue(mStatus.getStressLevel(),true);
        mTextView.setText(String.valueOf(mStatus.getStressLevel()));

        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(getContext().getString(R.string.edit_value))
                .setConfirmClickListener(sDialog -> {
                    binding.updateStatusUpdateStressLevelInput.setText(String.valueOf(mSlider.getValue()) + " / 100");

                    mStatus.setStressLevel(mSlider.getValue());

                    sDialog.dismissWithAnimation();
                });
        dialog.setCustomView(mLinearLayoutSlider);
        dialog.show();
    }

    private void OnClickPhoto() {
        takePicture();
    }

    private void OnClickEnergyLevel() {
        initAndUpdateDialogElements();
        mSlider.setValue(mStatus.getEnergieLevel(),true);
        mTextView.setText(String.valueOf(mStatus.getEnergieLevel()));

        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(getContext().getString(R.string.edit_value))
                .setConfirmClickListener(sDialog -> {
                    binding.updateStatusUpdateEnergyLevelInput.setText(String.valueOf(mSlider.getValue()) + " / 100");

                    mStatus.setEnergieLevel(mSlider.getValue());

                    sDialog.dismissWithAnimation();
                });
        dialog.setCustomView(mLinearLayoutSlider);
        dialog.show();
    }

    private void OnClickDesireToTrain() {
        initAndUpdateDialogElements();
        mSlider.setValue(mStatus.getMotivationToTrain(),true);
        mTextView.setText(String.valueOf(mStatus.getMotivationToTrain()));

        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(getContext().getString(R.string.edit_value))
                .setConfirmClickListener(sDialog -> {
                    binding.updateStatusUpdateDesireToTrainInput.setText(String.valueOf(mSlider.getValue()) + " / 100");

                    mStatus.setMotivationToTrain(mSlider.getValue());

                    sDialog.dismissWithAnimation();
                });
        dialog.setCustomView(mLinearLayoutSlider);
        dialog.show();
    }


    private void OnClickSave() {
        // Try Block
        try {


            _status.mStatusRepo.updateAnExistingUpdate(mStatus);
            _status.setSelectedStatus(mStatus);
            _status.setSelectedStatus(_status.mStatusRepo.getUpdateForUserForOneDay(mUser.getEmail(),new Date()));


            Toasty.success(getContext(),"Status gesichert", Toasty.LENGTH_SHORT, true).show();

            OnClickBackButton();



        } catch (Exception e){
            Log.getStackTraceString(e);
        }

    }

    private void OnClickBackButton() {
        _status.setSelectedStatus(_status.mStatusRepo.getUpdateForUserForOneDay(mUser.getEmail(),new Date()));
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _status.setSelectedStatus(_status.mStatusRepo.getUpdateForUserForOneDay(mUser.getEmail(),new Date()));
    }

    private void updateUiWithData() {
        //Get Status to write into Form
        mStatus = _status.getSelectedStatus();

        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        binding.updateStatusUpdateDesireToTrainInput.setText(String.valueOf(mStatus.getMotivationToTrain()) + " / 100");
        binding.updateStatusUpdateEnergyLevelInput.setText(String.valueOf(mStatus.getEnergieLevel()) + " / 100");
        binding.updateStatusUpdateHeadlineDate.setText(String.valueOf(formatter.format(mStatus.getTimestamp())) + " / Status Update");
        binding.updateStatusUpdateSleepQualityInput.setText(String.valueOf(mStatus.getSleepQuality()) + " / 100");
        binding.updateStatusUpdateSleepQuantityInput.setText(String.valueOf(mStatus.getSleepQuantity()) + " Std.");
        binding.updateStatusUpdateStepsInput.setText(String.valueOf(String.valueOf(mStatus.getSteps())));
        binding.updateStatusUpdateWeightInput.setText(String.valueOf(mStatus.getWeight()) + " kg");
        binding.updateStatusUpdateStressLevelInput.setText(String.valueOf(mStatus.getStressLevel()) + " / 100");


        Glide.with(getContext())
                .load(mStatus.getPicturePath())
                .fitCenter()
                .placeholder(R.drawable.avatar_status_picture_my_blue)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.updateStatusUpdatePhotoImageview);
    }



    private void initAndUpdateDialogElements() {
        Context context = getContext();

        mTextView = new TextView(context);
        mTextView.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        mTextView.setTextSize(24);
        mTextView.setTypeface(null, Typeface.BOLD);


        mSlider = new Slider(context);
        mSlider.setValueRange(0,100,true);
        mSlider.setOnPositionChangeListener((view, fromUser, oldPos, newPos, oldValue, newValue) -> {
            mTextView.setText(String.valueOf(newValue));
        });


        mLinearLayoutSlider = new LinearLayout(context);
        mLinearLayoutSlider.setOrientation(LinearLayout.VERTICAL);
        mLinearLayoutSlider.addView(mTextView);
        mLinearLayoutSlider.addView(mSlider);


        mEditTextNumber = new EditText(context);
        mEditTextNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mEditTextNumber.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(6)});
        mEditTextNumber.requestFocus();
        mEditTextNumber.setKeyListener(DigitsKeyListener.getInstance(false, true));

        mLinearLayoutNumber = new LinearLayout(context);
        mLinearLayoutNumber.setOrientation(LinearLayout.VERTICAL);
        mLinearLayoutNumber.addView(mEditTextNumber);

    }



    private void takePicture() {
        //Get Permissions, if not granted yet
        requestPermissionForWriting(this);
        try {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getActivity().getApplicationContext().getApplicationContext().getApplicationContext(), this);
        } catch (Exception e) {
            Log.getStackTraceString(e);
            Toasty.error(getActivity().getApplicationContext(), "Need Camera Permissions", Toasty.LENGTH_SHORT, true).show();
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case ImageUtil.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    mStatus.setPicturePath(imgUtil.getFilePath());
                    ImageUtil.setPic(binding.updateStatusUpdatePhotoImageview, mStatus.getPicturePath());
                    ImageUtil.saveThumb(mStatus.getPicturePath());
                    imgUtil.galleryAddPic(this, mStatus.getPicturePath());
                }
                break;
            case ImageUtil.REQUEST_PICK_GALERY_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    String realPath;
                    realPath = RealPathUtil.getRealPath(this.getContext(), data.getData());

                    ImageUtil.setPic(binding.updateStatusUpdatePhotoImageview, realPath);
                    ImageUtil.saveThumb(realPath);

                    mStatus.setPicturePath(realPath);
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

                    ImageUtil.setPic(binding.updateStatusUpdatePhotoImageview, realPath);
                    ImageUtil.saveThumb(realPath);
                    mStatus.setPicturePath(realPath);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;

        }
        if (mStatus.getPicturePath() != "") {
            mUser.setProfilePicPath(mStatus.getPicturePath());
            _user.updateUser(mUser);
        }


    }
}
