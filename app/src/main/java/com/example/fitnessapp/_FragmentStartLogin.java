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

import com.example.fitnessapp.Interface.IUser;
import com.example.fitnessapp.db.Entity.User;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class _FragmentStartLogin extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EMAIL = "param1";
    private static final String ARG_PWD = "param2";
    private static final String ARG_OBJECT = "param3";

    // TODO: Rename and change types of parameters
    private String pwd = null;
    private String email = null;

    private ImageView mIv_loginLogo;
    private TextInputEditText mEt_eMail;
    private TextInputEditText mEt_password;
    private CheckBox mCb_passwordReminder;
    private Button mBtn_login;
    private TextView mTv_register;

    private User mUser;


    public _FragmentStartLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * @param email Parameter 1.
     * @return A new instance of fragment FragmentCoachOverview.
     */
    public static _FragmentStartLogin newInstance(User user, String email, String password) {
        _FragmentStartLogin fragment = new _FragmentStartLogin();
        Bundle args = new Bundle();
        args.putSerializable(ARG_OBJECT, user);
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_PWD, password);
        fragment.setArguments(args);
        return fragment;
    }

    public static _FragmentStartLogin newInstance(User user, String email) {
        _FragmentStartLogin fragment = new _FragmentStartLogin();
        Bundle args = new Bundle();
        args.putSerializable(ARG_OBJECT, user);
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }


//    public static _FragmentStartLogin newInstance() {
//    return new _FragmentStartLogin();
//}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mUser = (User) getArguments().getSerializable(ARG_OBJECT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout._fragment_start_login, container, false);

        mIv_loginLogo = view.findViewById(R.id.iv_login_logo);

        mEt_eMail = view.findViewById(R.id.et_e_mail_text);
        if((email = getArguments().getString(ARG_EMAIL, "")) != null)
            mEt_eMail.setText(email);                     // Set received email
        //mEt_eMail.setOnFocusChangeListener(this);

        mEt_password = view.findViewById(R.id.et_password_text);
        if((pwd = getArguments().getString(ARG_PWD, "")) != null)
            mEt_password.setText(pwd);                                                              // Set received password
        //mEt_password.setOnFocusChangeListener(this);

        mCb_passwordReminder = view.findViewById(R.id.cb_password_reminder);
        mCb_passwordReminder.setChecked(mUser.passwordIsSet());
        mCb_passwordReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mUser.setRememberMe(true);
                }
                else{
                    mUser.setRememberMe(false);
                }
            }
        });

        mBtn_login = view.findViewById(R.id.tv_login);
        mBtn_login.setOnClickListener(v -> btnLoginClicked());

        mTv_register = view.findViewById(R.id.tv_register);
        mTv_register.setOnClickListener(v -> btnRegisterClicked());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    //==================
    //private Functions

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

        mUser = _user.Login(getEmail(), getPassword());

        // e-mail und passwort abfrage
//        if(IUser.emailExists(getEmail()) && IUser.passwordIsMatched(getEmail(), getPassword())){
        if( mUser != null){
//            loadUser();                                                                             // Load Data from the User
            try{
                Intent intent = new Intent(this.getActivity(), _ActivityCoach.class);
                intent.putExtra("ARG_USER_ID", mUser.getId());
                startActivity(intent);
            } catch(Exception e){
                Toast toast=Toast.makeText(this.getActivity(), "Login Error",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else{
            Toast toast=Toast.makeText(this.getActivity(),"Passwort oder E-Mail falsch",Toast.LENGTH_SHORT);
            toast.show();
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
