package com.example.fitnessapp;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import static com.example.fitnessapp._ActivityStart.createNewUser;
import static com.example.fitnessapp._ActivityStart.emailExists;


/**
 * A simple {@link Fragment} subclass.
 */
public class _FragmentStartRegister extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    public final String backStateName = this.getClass().getName();
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[!@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 4 characters
                    "$");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EMAIL = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private ImageView mIv_loginLogo;
    private TextInputEditText mEt_eMail;
    private TextInputEditText mEt_password;
    private TextInputEditText mEt_password_repeat;
    private CheckBox mCb_passwordReminder;
    private TextView mTv_login;
    private Button mBtn_register;
    private static Boolean mIsNewInstanceNotEmpty = false;
    private boolean my_bool = true;

    public _FragmentStartRegister() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FragmentCoachOverview.
     */
    // TODO: Rename and change types and number of parameters
    public static _FragmentStartRegister newInstance(String param1) {
        _FragmentStartRegister fragment = new _FragmentStartRegister();
        Bundle args = new Bundle();
        if(param1 != ""){
            mIsNewInstanceNotEmpty = true;
            fragment.mParam1 = param1;
            //args.putString(ARG_EMAIL, param1);
        }
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate( @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout._fragment_start_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIv_loginLogo = view.findViewById(R.id.iv_login_logo);
        //mIv_loginLogo.setImageResource(R.drawable.true_fitness_logo);

        mEt_eMail = view.findViewById(R.id.et_e_mail_text);
        if(mIsNewInstanceNotEmpty) {
//            mEt_eMail.setText(getArguments().getString(ARG_EMAIL, ""));                     // Set received email
            mEt_eMail.setText(mParam1);                     // Set received email
        }
//        mEt_eMail.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (emailIsValid(getEmail())){
//                    myToast("E-Mail is great");
//                } else{
//                    myToast("E-Mail is not valid");
//                }
//            }
//        });


        mEt_password = view.findViewById(R.id.et_password_text);
//        mEt_password.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (passwordIsValid(getPassword())){
//                    myToast("Password is great");
//                } else{
//                    myToast("Password is not valid");
//                }
//            }
//        });

        mEt_password_repeat = view.findViewById(R.id.et_password_repeat_text);
        mEt_password_repeat.setOnFocusChangeListener(this);
//        mEt_password_repeat.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (passwordMatches(getPassword(),getPasswordRepeat())){
//                    myToast("Passwords match");
//                } else{
//                    myToast("Passwords doesn't match");
//                }
//            }
//        });


        mTv_login = view.findViewById(R.id.tv_login);
        mTv_login.setOnClickListener(this);

        mBtn_register = view.findViewById(R.id.tv_register);
        mBtn_register.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v == mTv_login){
            btnLoginClicked();
        }
        else if( v == mBtn_register){
            btnRegisterClicked();
        }
    }

    private void btnRegisterClicked() {
        if (validateEmail(getEmail()) || my_bool){
            if(emailExists(getEmail()) || my_bool){
                if(validatePassword(getPassword()) || my_bool){
                    if(passwordMatches(getPassword(), getPasswordRepeat()) || my_bool){
                        createNewUser();

                        FragmentManager mFragmentManager = getFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

                        Fragment f = null;
                        f = mFragmentManager.findFragmentByTag("startMyData");
                        if(f == null){
                            mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartYourDataGetStarted(), "startMyData");
                            mFragmentTransaction.addToBackStack("startMyData");
                        } else {
                            mFragmentManager.popBackStack();
                            mFragmentManager.popBackStack("startMyData", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }

                        mFragmentTransaction.commit();
                    }
                    else{
                        myToast("Passwords doesn't match");
                    }
                }
                else{
                    myToast("Password is invalid");
                }
            }
            else{
                myToast("E-Mail already exists in Database");
            }
        }
        else{
            myToast("E-Mail is not valid");
        }

    }



    private boolean validateEmail(String emailInput){
        emailInput = getEmail().trim();

        if(emailInput.isEmpty()){
            mEt_eMail.setError("Field can't be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            mEt_eMail.setError("Please enter a valid email address");
            return false;
        } else {
            mEt_eMail.setError(null);
            return true;
        }
    }

    private boolean validatePassword(String passwordInput){
        passwordInput = getPassword().trim();

        if(passwordInput.isEmpty()){
            mEt_password.setError("Field can't be empty");
            return false;
        } else if(!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            mEt_password.setError("Password needs to include: \na-z, A-Z, 1-9, !@#$%=");
            return false;
        } else {
            mEt_password.setError(null);
            return true;
        }
    }


    private boolean passwordMatches(String password, String password_repeat) {
        return password.equals(password_repeat);
    }


    private void btnLoginClicked() {
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentManager.popBackStack();
        mFragmentTransaction.replace(R.id.start_frame, _FragmentStartLogin.newInstance(getEmail(), ""), "login");
        if(_ActivityStart.getStartFrame() != "login")
            mFragmentTransaction.addToBackStack("login");
        mFragmentTransaction.commit();

    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
    }

    private void myToast(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }

    private String getPassword() {
        return mEt_password.getText().toString();
    }

    private String getPasswordRepeat() {
        return mEt_password_repeat.getText().toString();
    }

    private String getEmail() {
        return mEt_eMail.getText().toString();
    }

}
