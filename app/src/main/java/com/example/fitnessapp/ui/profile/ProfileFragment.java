package com.example.fitnessapp.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.DatePickerDialogFragment;
import com.example.fitnessapp.R;
import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp._ActivityStart;
import com.example.fitnessapp.databinding.FragmentCoachProfileBinding;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.utils.DateConverter;
import com.example.fitnessapp.utils.ImageUtil;
import com.example.fitnessapp.utils.RealPathUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class ProfileFragment extends Fragment {

    //Use Services
    private UserViewModel _user;
    private User mUser = null;

    private FragmentCoachProfileBinding binding;

    private ImageUtil imgUtil = null;
    private String mCurrentPhotoPath = "";

    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; ++i) {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                    return "";
                }
            }

            return null;
        }
    };
    private DatePickerDialogFragment mDateFrag;
    private DatePickerDialog.OnDateSetListener dateSet = (view, year, month, day) -> onDateSet(view, year, month, day);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Bind layout to Class
        binding = FragmentCoachProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //INIT Viewmodels
        {
            //Start Service
            _user = new ViewModelProvider(getActivity()).get(UserViewModel.class);

            //Get User
            mUser = _user.getUser();


            //set Observer
            _user.getLiveUser().observe(getActivity(), user -> {
                mUser = user;

                binding.firstnameInput.setText(mUser.getFirstName());
                binding.lastnameInput.setText(mUser.getLastName());
                String gender = null;
                switch (mUser.getGender()) {
                    case 1:
                        gender = getString(R.string.maleGender);
                        break;
                    case 2:
                        gender = getString(R.string.femaleGender);
                        break;
                    case 3:
                        gender = getString(R.string.otherGender);
                        break;
                    default:
                        gender = "";
                }
                binding.genderInput.setText(gender);
                if (mUser.getBirthdate() != null)
                    binding.birthdateInput.setText(DateConverter.dateToLocalDateStr(mUser.getBirthdate(), getContext()));
                binding.heightInput.setText(String.valueOf(mUser.getHeight()) + " cm");

                mCurrentPhotoPath = mUser.getProfilePicPath();

                binding.emailInput.setText(mUser.getEmail());
            });
        }


        //Set all OnClickListeners
        binding.photoRoundProfile.setOnClickListener(v -> ClickProfilePhoto());

        binding.firstnameInput.setOnClickListener(v -> ClickOnFirstName((TextView) v));
        binding.lastnameInput.setOnClickListener(v -> ClickOnLastName((TextView) v));

        binding.birthdateInput.setOnClickListener(v -> ClickOnBirthdate(v));
        binding.genderInput.setOnClickListener(v -> ClickGenderInput(v));
        binding.heightInput.setOnClickListener(v -> ClickOnHeight(v));

        binding.emailInput.setOnClickListener(v -> ClickEmail());
        binding.passwordInput.setOnClickListener(v -> ClickOnPassword(v));

        binding.btnDeleteAccount.setOnClickListener(v -> ClickDeleteAccount(v));
        binding.btnLogout.setOnClickListener(v -> ClickLogout());


        /* Initialisation des valeurs */
        imgUtil = new ImageUtil(binding.photoRoundProfile);

        //ProfilePic gets loaded before UI is drawn
        loadProfilePicListener();

        return view;
    }


    private void loadProfilePicListener() {
        ImageView iv = binding.photoRoundProfile;
        ViewTreeObserver vto = iv.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            private int mFinalWidth = 0;
            private int mFinalHeight = 0;

            public boolean onPreDraw() {

                if (mFinalHeight != 0 || mFinalWidth != 0)
                    return true;

                mFinalHeight = iv.getHeight();
                mFinalWidth = iv.getWidth();
                Log.d("hilength", "Height: " + mFinalHeight + " Width: " + mFinalWidth);


                ImageUtil.setPic(binding.photoRoundProfile, mCurrentPhotoPath);
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case ImageUtil.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    mCurrentPhotoPath = imgUtil.getFilePath();
                    ImageUtil.setPic(binding.photoRoundProfile, mCurrentPhotoPath);
                    ImageUtil.saveThumb(mCurrentPhotoPath);
                    imgUtil.galleryAddPic(this, mCurrentPhotoPath);
                }
                break;
            case ImageUtil.REQUEST_PICK_GALERY_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    String realPath;
                    realPath = RealPathUtil.getRealPath(this.getContext(), data.getData());

                    ImageUtil.setPic(binding.photoRoundProfile, realPath);
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

                    ImageUtil.setPic(binding.photoRoundProfile, realPath);
                    ImageUtil.saveThumb(realPath);
                    mCurrentPhotoPath = realPath;
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;

        }
        if (mCurrentPhotoPath != "") {
            mUser.setProfilePicPath(mCurrentPhotoPath);
            _user.updateUser(mUser);
        }


    }


    private void ClickProfilePhoto() {
        takePicture();
    }


    private void ClickOnFirstName(TextView v) {
        EditTextDialog(v);
    }

    private void ClickOnLastName(TextView v) {
        EditTextDialog(v);
    }

    private void ClickOnBirthdate(View v) {
        showDatePickerFragment();
    }

    private void ClickGenderInput(View v) {
        String oldValue = binding.genderInput.getText().toString();

        new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(getContext().getString(R.string.edit_value))
                .setNeutralText(getString(R.string.maleGender))
                .setCancelText(getString(R.string.femaleGender))
                .setConfirmText(getString(R.string.otherGender))
                .setNeutralClickListener(sDialog -> {
                    if (!oldValue.equals(getString(R.string.maleGender))) {
                        binding.genderInput.setText(getString(R.string.maleGender));
                        mUser.setGender(1);
                        updateUser();
                    }
                    sDialog.dismissWithAnimation();
                })
                .setCancelClickListener(sDialog -> {
                    if (!oldValue.equals(getString(R.string.femaleGender))) {
                        binding.genderInput.setText(getString(R.string.femaleGender));
                        mUser.setGender(2);
                        updateUser();
                    }
                    sDialog.dismissWithAnimation();
                })
                .setConfirmClickListener(sDialog -> {
                    if (!oldValue.equals(getString(R.string.otherGender))) {
                        binding.genderInput.setText(getString(R.string.otherGender));
                        mUser.setGender(3);
                        updateUser();
                    }
                    sDialog.dismissWithAnimation();
                })
                .show();
    }

    private void ClickOnHeight(View v) {
        EditNumberDialog((TextView) v);
    }


    private void ClickEmail() {
        Toasty.error(getActivity().getWindow().getContext(), "E-Mail kann nicht geändert werden", Toasty.LENGTH_SHORT, true).show();
    }

    private void ClickOnPassword(View v) {
        EditPasswordDialog((TextView) v);
    }


    private void ClickDeleteAccount(View v) {
        new MaterialAlertDialogBuilder(getContext(), SweetAlertDialog.SUCCESS_TYPE)
//                     Add customization options here
                .setTitle("Willst du dein Profil wirklich löschen?")
                .setMessage("Alle deine Daten gehen verloren!")
                .setIcon(android.R.drawable.ic_delete)
                .setPositiveButton("Löschen", (dialog, which) -> {
                    _user.mUserRepo.DeleteUser(mUser);
                    Toasty.success(getContext(), "User gelöscht", Toasty.LENGTH_SHORT, true).show();
                    startActivity(new Intent(getActivity(), _ActivityStart.class));
                    getActivity().finish();


                })
                .setNegativeButton("Logout", ((dialog, which) -> ClickLogout()))
                .setNeutralButton(R.string.cancel, (dialog, which) -> {
                })
                .show();
    }

    private void ClickLogout() {
        new MaterialAlertDialogBuilder(getContext(), SweetAlertDialog.SUCCESS_TYPE)
//                     Add customization options here
                .setTitle("Willst du dich wirklich ausloggen?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    _user.mUserRepo.Logout(mUser);
                    Toasty.success(getContext(), "Ausgeloggt", Toasty.LENGTH_SHORT, true).show();
                    startActivity(new Intent(getActivity(), _ActivityStart.class));
                    getActivity().finish();
                })
                .setNeutralButton(R.string.cancel, (dialog, which) -> {
                })
                .show();

    }


    private void updateUser() {

        mUser.setFirstName(binding.firstnameInput.getText().toString());
        mUser.setLastName(binding.lastnameInput.getText().toString());
        mUser.setBirthdate(DateConverter.localDateStrToDate(binding.birthdateInput.getText().toString(), getContext()));
        String gender = binding.genderInput.getText().toString();
        int result = 0;
        if (gender == getString(R.string.maleGender))
            result = 1;
        else if (gender == getString(R.string.femaleGender))
            result = 2;
        else if (gender == getString(R.string.otherGender))
            result = 3;
        mUser.setGender(result);
        mUser.setFirstName(binding.firstnameInput.getText().toString());
        mUser.setHeight(Float.parseFloat(binding.heightInput.getText().toString().replaceAll(" cm", "")));


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                // Try Block
                try {
                    _user.updateUser(mUser);

                } catch (Exception e) {
                    Log.getStackTraceString(e);
                }
//        _user.mUserRepo.UpdateInfo(mUser);

                return null;
            }
        }.execute();


    }


    private void EditTextDialog(TextView editTextView) {
        Context context = getContext();

        final EditText editText = new EditText(context);
        editText.setText(editTextView.getText().toString());
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.requestFocus();

        LinearLayout linearLayout = new LinearLayout(context.getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(editText);

        final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(getContext().getString(R.string.edit_value))
                .setConfirmClickListener(sDialog -> {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    }
                    editTextView.setText(editText.getText().toString());
                    sDialog.dismissWithAnimation();
                    updateUser();

//                    if (mConfirmClickListener != null)
//                        mConfirmClickListener.onTextChanged(EditableInputView.this);
                });
        dialog.setCustomView(linearLayout);
        dialog.show();
    }

    private void EditNumberDialog(TextView editTextView) {
        Context context = getContext();
        EditText mEditText;
        LinearLayout mLinearLayout;

        mEditText = new EditText(context);
        mEditText.setText(editTextView.getText().toString().replaceAll(" cm", ""));
        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEditText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(6)});
        mEditText.requestFocus();

        mLinearLayout = new LinearLayout(context.getApplicationContext());
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.addView(mEditText);

        final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(getContext().getString(R.string.edit_value))
                .setConfirmClickListener(sDialog -> {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
                    }
                    editTextView.setText(mEditText.getText().toString() + " cm");
                    sDialog.dismissWithAnimation();
                    updateUser();
//                    if (mConfirmClickListener != null)
//                        mConfirmClickListener.onTextChanged(EditableInputView.this);
                });
        dialog.setCustomView(mLinearLayout);
        dialog.show();

    }

    private void EditPasswordDialog(TextView editTextView) {
        Context context = getContext();


        AsyncTask a = new AsyncTask<Void, Void, Void>() {

            private EditText mEditText_newPasswordConfirm;
            private EditText mEditText_newPassword;
            private EditText mEditText_oldPassword;
            private LinearLayout mLinearLayout;

            private WeakReference<ProfileFragment> mFragmentWeakReference;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... voids) {

                mEditText_oldPassword = new EditText(context);
                mEditText_oldPassword.setHint("Altes Passwort");
                mEditText_oldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                mEditText_oldPassword.requestFocus();

                final TextInputLayout textInputLayout_oldPassword = new TextInputLayout(context);
                textInputLayout_oldPassword.addView(mEditText_oldPassword);


                mEditText_newPassword = new EditText(context);
                mEditText_newPassword.setHint("Neues Passwort");
                mEditText_newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                final TextInputLayout textInputLayout_newPassword = new TextInputLayout(context);
                textInputLayout_newPassword.addView(mEditText_newPassword);


                mEditText_newPasswordConfirm = new EditText(context);
                mEditText_newPasswordConfirm.setHint("Neues Passwort bestätigen");
                mEditText_newPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                final TextInputLayout textInputLayout_newPasswordConfirm = new TextInputLayout(context);
                textInputLayout_newPasswordConfirm.addView(mEditText_newPasswordConfirm);


                mLinearLayout = new LinearLayout(context.getApplicationContext());
                mLinearLayout.setOrientation(LinearLayout.VERTICAL);

                mLinearLayout.addView(textInputLayout_oldPassword);
                mLinearLayout.addView(textInputLayout_newPassword);
                mLinearLayout.addView(textInputLayout_newPasswordConfirm);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);


                // Try Block
                try {

                    SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText(getContext().getString(R.string.edit_value))
                            .setConfirmClickListener(sDialog -> {
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                                if (imm != null) {
                                    imm.hideSoftInputFromWindow(mEditText_oldPassword.getWindowToken(), 0);
                                }


                                String old_password = mEditText_oldPassword.getText().toString();
                                String new_password = mEditText_newPassword.getText().toString();
                                String new_password_confirm = mEditText_newPasswordConfirm.getText().toString();

                                if (!new_password.equals(new_password_confirm)) {
                                    Toasty.error(context, "Passwörter stimmen nicht überein", Toasty.LENGTH_SHORT, true).show();
                                    return;
                                }
                                if (!_user.mUserRepo.changePassword(mUser.getEmail(), old_password, new_password)) {
                                    Toasty.error(context, "Passwort wurde NICHT geändert!", Toasty.LENGTH_SHORT, true).show();
                                    return;
                                }
                                Toasty.success(context, "Passwort wurde geändert!", Toasty.LENGTH_SHORT, true).show();

                                sDialog.dismissWithAnimation();

                            });

                    dialog.setCustomView(mLinearLayout);
                    dialog.show();


                } catch (Exception e) {
                    Log.getStackTraceString(e);
                }

            }
        }.execute();


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


    private void showDatePickerFragment() {
        if (mDateFrag == null) {
            mDateFrag = DatePickerDialogFragment.newInstance(dateSet);
        }

        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        mDateFrag.show(ft, "dialog");
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        binding.birthdateInput.setText(DateConverter.dateToLocalDateStr(year, month + 1, day, getContext()));
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.birthdateInput.getWindowToken(), 0);
        updateUser();
    }


}
