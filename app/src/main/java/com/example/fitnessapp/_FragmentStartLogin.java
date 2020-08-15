package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import static com.example.fitnessapp._ActivityStart.emailExists;
import static com.example.fitnessapp._ActivityStart.loadUser;
import static com.example.fitnessapp._ActivityStart.passwordIsMatched;
import static com.example.fitnessapp._ActivityStart.passwordReminderIsSet;
import static com.example.fitnessapp._ActivityStart.setPasswordReminder;


/**
 * A simple {@link Fragment} subclass.
 */
public class _FragmentStartLogin extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, CompoundButton.OnCheckedChangeListener
{
    public final String backStateName = this.getClass().getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EMAIL = "param1";
    private static final String ARG_PWD = "param2";

    // TODO: Rename and change types of parameters
    private ImageView mIv_loginLogo;
    private TextInputEditText mEt_eMail;
    private TextInputEditText mEt_password;
    private CheckBox mCb_passwordReminder;
    private Button mBtn_login;
    private TextView mTv_register;


    public _FragmentStartLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * @param email Parameter 1.
     * @return A new instance of fragment FragmentCoachOverview.
     */
    public static _FragmentStartLogin newInstance(String email, String password) {
        _FragmentStartLogin fragment = new _FragmentStartLogin();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_PWD, password);
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
        return inflater.inflate(R.layout._fragment_start_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIv_loginLogo = view.findViewById(R.id.iv_login_logo);
        //mIv_loginLogo.setImageResource(R.drawable.true_fitness_logo);

        mEt_eMail = view.findViewById(R.id.et_e_mail_text);
        mEt_eMail.setText(getArguments().getString(ARG_EMAIL, ""));                     // Set received email
        mEt_eMail.setOnFocusChangeListener(this);

        mEt_password = view.findViewById(R.id.et_password_text);
        if(passwordReminderIsSet())
            mEt_password.setText(getArguments().getString(ARG_PWD, ""));                    // Set received password
        mEt_password.setOnFocusChangeListener(this);

        mCb_passwordReminder = view.findViewById(R.id.cb_password_reminder);
        mCb_passwordReminder.setChecked(passwordReminderIsSet());
        mCb_passwordReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setPasswordReminder(true);
                }
                else{
                    setPasswordReminder(false);
                }
            }
        });

        mBtn_login = view.findViewById(R.id.tv_login);
        mBtn_login.setOnClickListener(this);

        mTv_register = view.findViewById(R.id.tv_register);
        mTv_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == mBtn_login){
            btnLoginClicked();
        }
        else if( v == mTv_register){
            btnRegisterClicked();
        }
    }

    private void btnRegisterClicked() {
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentManager.popBackStack();                                                            // delete last Step from Back Stack
        mFragmentTransaction.replace
                (R.id.start_frame, _FragmentStartRegister.newInstance(getEmail()), "register"); // Create new Frame

        if(_ActivityStart.getStartFrame() != "register")                                             // Don't add to StackBack, when it is start Frame
            mFragmentTransaction.addToBackStack("register");

        mFragmentTransaction.commit();
    }


    private void btnLoginClicked() {
        // e-mail und passwort abfrage
        if(emailExists(getEmail()) && passwordIsMatched(getEmail(), getPassword())){

            loadUser();                                                                             // Load Data from the User

            try{
                startActivity(new Intent(this.getActivity(), _ActivityCoach.class));
            } catch(Exception e){
                Toast toast=Toast.
                        makeText(this.getActivity(),
                                "Login Error",
                                Toast.LENGTH_SHORT);
            }
        }
        else{
            Toast toast=Toast.
                    makeText(this.getActivity(),
                            "Passwort oder E-Mail falsch",
                            Toast.LENGTH_SHORT);
            //toast.setMargin(50,50);
            toast.show();
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView == mCb_passwordReminder){
        }
    }

    private String getPassword() {
        return mEt_password.getText().toString();
    }

    private String getEmail() {
        return mEt_eMail.getText().toString();
    }

    //==================
    //Database Functions


}
