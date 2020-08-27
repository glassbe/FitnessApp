package com.example.fitnessapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.db.Entity.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class _FragmentStartRegister extends Fragment {

    //Use Services
    private UserViewModel _user;
    private User mUser = null;


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

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EMAIL = "param1";
    private String mParam1;

    private ImageView mIv_loginLogo;
    private TextInputEditText mEt_eMail;
    private TextInputEditText mEt_password;
    private TextInputEditText mEt_password_repeat;
    private CheckBox mCb_passwordReminder;
    private TextView mTv_login;
    private Button mBtn_register;
    private Boolean mIsNewInstanceNotEmpty = false;
    private boolean registerWithoutInput = false;
    private _ActivityStart_ViewModel mViewModel;
    private View mImg_logo;

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
        args.putString(ARG_EMAIL, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static _FragmentStartRegister newInstance() {
        return new _FragmentStartRegister();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _user = new ViewModelProvider(this).get(UserViewModel.class);

        //Set Shared Elements to its position
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(0, R.anim.animate_slide_out_left);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout._fragment_start_register, container, false);


        // Set logo Adapter to make it draggable
        mImg_logo = view.findViewById(R.id.iv_login_logo_draggable);
        view.setOnTouchListener((v, event) -> onLogoTouch(v, event));


        // Set email Adapter
        mEt_eMail = view.findViewById(R.id.et_e_mail_text);
        mEt_eMail.setOnFocusChangeListener((v, hasFocus) -> emailChanged());

        // Set password Adapter
        mEt_password = view.findViewById(R.id.et_password_text);
        mEt_password.setOnFocusChangeListener((v, hasFocus) -> passwordChanged());

        mEt_password_repeat = view.findViewById(R.id.et_password_repeat_text);

        // Set Login Adapter
        mTv_login = view.findViewById(R.id.tv_login);
        mTv_login.setOnClickListener(v -> btnLoginClicked());

        // Set Register Adapter
        mBtn_register = view.findViewById(R.id.tv_register);
        mBtn_register.setOnClickListener(v -> btnRegisterClicked());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set Data ViewModel
        mViewModel = new ViewModelProvider(getActivity()).get(_ActivityStart_ViewModel.class);

        mViewModel.getPassword().observe(getActivity(), (password -> mEt_password.setText(password)));
        mViewModel.getEmail().observe(getActivity(), (email -> mEt_eMail.setText(email)));
    }


    // =================
    // Private Functions

    private void emailChanged() {
        mViewModel.setEmail(mEt_eMail.getText().toString());
    }

    private void passwordChanged() {
        mViewModel.setPassword(mEt_password.getText().toString());
    }

    private void btnLoginClicked() {
        new Thread(() ->
        {
            FragmentManager mFragmentManager = getFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out);


            mFragmentManager.popBackStack();
            mFragmentTransaction
                    .replace(R.id.start_frame, _FragmentStartLogin.newInstance(), "login")
                    .addSharedElement(mImg_logo, ViewCompat.getTransitionName(mImg_logo));


            if (_ActivityStart.getStartFrame() != "login")
                mFragmentTransaction.addToBackStack("login");
            mFragmentTransaction.commit();
        }).start();

    }

    private void btnRegisterClicked() {
        String mail = getEmail();
        String password = getPassword();
        String passwordRepeat = getPasswordRepeat();

        if (!validateEmail(mail) && registerWithoutInput) {
            myToast("E-Mail is not valid");
            return;
        }

        if (!validatePassword(password) && registerWithoutInput) {
            myToast("Password is invalid");
            return;
        }

        if (!passwordMatches(password, passwordRepeat) && registerWithoutInput) {
            myToast("Passwords doesn't match");
            return;
        }

        User m = _user.mUserRepo.getUser(mail);
        if (((m = _user.mUserRepo.getUser(mail)) != null) && registerWithoutInput) {
            myToast("E-Mail already exists in Database");
            return;
        }


        //Create New User in ViewModel
//         _user.mUserRepo.Register(getEmail(), getPassword(), Boolean.TRUE);
        mViewModel.setUser(new User(mail, password));

        // Store Values in ViewModel
        emailChanged();
        passwordChanged();

        new Thread(() -> {
            FragmentManager mFragmentManager = getFragmentManager();
            @SuppressLint("WrongConstant") FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.addSharedElement(mImg_logo, ViewCompat.getTransitionName(mImg_logo));

//            mFragmentTransaction.setCustomAnimations(R.anim.animate_slide_in_right, R.anim.animate_slide_out_left);


            Fragment f = null;
            f = mFragmentManager.findFragmentByTag("startMyData");
            if (f == null) {
                mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartYourDataGetStarted(), "startMyData");
                //                mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartCreateWorkoutPlan(), "startMyData");
//                mFragmentTransaction.replace(R.id.start_frame, new _FragmentStartYourGoal(), "startMyData");
                mFragmentTransaction.addToBackStack("startMyData");
            } else {
                mFragmentManager.popBackStack();
                mFragmentManager.popBackStack("startMyData", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            mFragmentTransaction.commit();

        }).start();
    }


    private boolean validateEmail(String emailInput) {
        emailInput = getEmail().trim();

        if (emailInput.isEmpty()) {
            mEt_eMail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            mEt_eMail.setError("Please enter a valid email address");
            return false;
        } else {
            mEt_eMail.setError(null);
            return true;
        }
    }

    private boolean validatePassword(String passwordInput) {
        passwordInput = getPassword().trim();

        if (passwordInput.isEmpty()) {
            mEt_password.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            mEt_password.setError("Password needs to include: \na-z, A-Z, 1-9, !@#$%= \nAt least 8 Character");
            return false;
        } else {
            mEt_password.setError(null);
            return true;
        }
    }

    private boolean passwordMatches(String password, String password_repeat) {
        return password.equals(password_repeat);
    }

    private void myToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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


    // Gimmig
    public boolean onLogoTouch(View v, MotionEvent event) {
        float x = (event.getX());
        float y = (event.getY());
        AdditiveAnimator.animate(mImg_logo, 500).centerX(x).centerY(y).start();
//        Handler handler = new Handler();
//        handler.postDelayed()
        return true;
    }
}
