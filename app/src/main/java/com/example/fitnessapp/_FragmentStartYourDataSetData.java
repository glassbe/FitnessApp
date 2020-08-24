package com.example.fitnessapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.drm.DrmStore;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.utils.DateConverter;
import com.example.fitnessapp.utils.Keyboard;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.onurkaganaldemir.ktoastlib.KToast;
import com.rey.material.widget.Slider;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class _FragmentStartYourDataSetData extends Fragment {

    //Use Services
    private UserViewModel _user;
    private User mUser = null;

    private _ActivityStart_ViewModel mViewModel;


    private EditText mFirstName;
    private EditText mSurName;
    private TextView mBirthdate;
    private Spinner mGender;
    private EditText mWeight;
    private EditText mHeight;
    private Slider mEnergyLevel;

    private Button mSetData;

    private DatePickerDialog.OnDateSetListener dateSet = (view, year, month, day) -> onDateSet(view, year, month, day);
    private DatePickerDialogFragment mDateFrag = null;



    public _FragmentStartYourDataSetData() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _user = new ViewModelProvider(this).get(UserViewModel.class);

        mViewModel = new ViewModelProvider(getActivity()).get(_ActivityStart_ViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout._fragment_start_your_data_set_data, container, false);

            mFirstName = view.findViewById(R.id.et_input_firstname);
            mSurName = view.findViewById(R.id.et_input_surname);
            mBirthdate = view.findViewById(R.id.et_input_birthdate);
            mBirthdate.setOnClickListener(v -> showDatePickerFragment());

            mGender = view.findViewById(R.id.s_input_gender);
            mWeight = view.findViewById(R.id.et_input_weight);
            mHeight = view.findViewById(R.id.et_input_height);
            mEnergyLevel = view.findViewById(R.id.sb_energyLevel);

            mSetData = view.findViewById(R.id.btn_setData);
            mSetData.setOnClickListener(_view -> setData(_view));

            // set selection of Spinner
            String[] arraySpinner = new String[]{
                    "männlich",
                    "weiblich",
                    "divers"
            };
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.spinner_item, arraySpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mGender.setAdapter(adapter);

            return view;

    }


    private void openDateDialog() {

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Date");
        MaterialDatePicker<Long> picker = builder.build();
//        picker.showYear(boolean yearPicker)
        picker.show(getActivity().getSupportFragmentManager().beginTransaction(), "DATE_PICKER");

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                    mBirthdate.setText(selection.toString());
//                    mBirthdate.setText(DateConverter.dateToString(year, month + 1, day));
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(mBirthdate.getWindowToken(), 0);

                }
        });


//        DatePickerDialog dpd = null;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            dpd = new DatePickerDialog(getContext());
//        }
//
//        dpd.getDatePicker().updateDate(2000,0,1);
//        dpd.getDatePicker().setFirstDayOfWeek(2);
//
//        // If you're calling this from a support Fragment
//        dpd.show();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    mBirthdate.setText(DateConverter.dateToString(year, month + 1, dayOfMonth));
//                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(mBirthdate.getWindowToken(), 0);
//                }
//            });
//        }


    }

    private void openDateDialog(boolean hasFocus) {
        if (hasFocus) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(mBirthdate.getWindowToken(), 0);
            showDatePickerFragment();
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
        mBirthdate.setText(DateConverter.dateToLocalDateStr(year, month + 1, day, getContext()));
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mBirthdate.getWindowToken(), 0);
    }


    private View.OnClickListener setData(View _view) {
        //Check Inputs
        if(InputIsFalse() && false) {
            KToast.warningToast(getActivity(),"Eingabe ungültig", Gravity.BOTTOM, KToast.LENGTH_SHORT);
            return null;
        }

//        User newUser = mViewModel.getUser();
//        newUser.setProfilePicPath(mViewModel.getPhotoPath());
//        newUser.setFirstName(mFirstName.getText().toString());
//        newUser.setLastName(mSurName.getText().toString());
//        newUser.setBirthdate(DateConverter.localDateStrToDate(mSurName.getText().toString(), getContext()));
//        newUser.setGender(mGender.getSelectedItemPosition());
//        newUser.setWeight(Float.parseFloat(mWeight.getText().toString()));
//        newUser.setHeight(Float.parseFloat(mHeight.getText().toString()));
//        newUser.setEnergyLevel(mEnergyLevel.getValue());
//
//        mViewModel.setUser(newUser);
//
//        KToast.successToast(getActivity(),"Daten eingetragen", Gravity.BOTTOM, KToast.LENGTH_SHORT);


        // Start new Fragment
        FragmentManager mFragmentManager = getFragmentManager();
        androidx.fragment.app.FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        Fragment f = null;
        f = mFragmentManager.findFragmentByTag("createWorkoutPlan");
        if(f == null){
            mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartYourGoal(), "createWorkoutPlan");
            mFragmentTransaction.addToBackStack("createWorkoutPlan");
        } else {
            mFragmentManager.popBackStack();
            mFragmentManager.popBackStack("createWorkoutPlan", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        mFragmentTransaction.commit();

        return null;
    }

    private boolean InputIsFalse() {


        if(mFirstName.getText().toString().length() == 0) return true;
        if(mSurName.getText().toString().length() == 0) return true;
        if(mBirthdate.getText().toString().length() == 0) return true;
//        if(mGender.getSelectedItemPosition() == "") return true;
        if(mWeight.getText().toString().length() == 0) return true;
        if(mHeight.getText().toString().length() == 0) return true;
//        if(mEnergyLevel.getText().toString() == "") return true;
        return false;
    }





}
