package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.fitnessapp.db.UserRepo;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class _FragmentStartLogin extends Fragment
{
    //Use Services
    private IUser _user = null;
    private User mUser = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EMAIL = "param1";
    private static final String ARG_USER_ID = "param2";

    // TODO: Rename and change types of parameters
    private String mEmail = null;
    private int mUser_Id = -1;

    private ImageView mIv_loginLogo;
    private TextInputEditText mEt_eMail;
    private TextInputEditText mEt_password;
    private CheckBox mCb_passwordReminder;
    private Button mBtn_login;
    private TextView mTv_register;
    private SharedViewModel mViewModel;


    public _FragmentStartLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * @param email Parameter 1.
     * @return A new instance of fragment FragmentCoachOverview.
     */
    public static _FragmentStartLogin newInstance(String email) {
        _FragmentStartLogin fragment = new _FragmentStartLogin();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _user = new UserRepo(getActivity().getApplication());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get User
        if(getArguments() != null)
            if((mEmail = getArguments().getString(ARG_EMAIL, "")) != "")
                mUser = _user.getUser(mEmail).getValue();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout._fragment_start_login, container, false);

//        mIv_loginLogo = view.findViewById(R.id.iv_login_logo);

        // Set received email
        mEt_eMail = view.findViewById(R.id.et_e_mail_text);
        if((mEmail = getArguments().getString(ARG_EMAIL, "")) != null)
            mEt_eMail.setText(mEmail);

        // Set password Adapter
        mEt_password = view.findViewById(R.id.et_password_text);
        mEt_password.setOnFocusChangeListener((v,hasFocus) -> passwordChanged(v, hasFocus));


        mCb_passwordReminder = view.findViewById(R.id.cb_password_reminder);
        mCb_passwordReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mUser != null) {
                    if (isChecked) {
                        mUser.setRememberMe(true);
                    } else {
                        mUser.setRememberMe(false);
                    }
                }
            }
        });

        mBtn_login = view.findViewById(R.id.tv_login);
        mBtn_login.setOnClickListener(v -> btnLoginClicked());

        mTv_register = view.findViewById(R.id.tv_register);
        mTv_register.setOnClickListener(v -> btnRegisterClicked());

        return view;
    }

    private void passwordChanged(View v, boolean hasFocus) {
        mViewModel.setText(mEt_password.getText().toString());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        mViewModel.getText().observe(getActivity(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence charSequence) {
                mEt_password.setText(charSequence);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        mViewModel.getText().observe(getActivity(), v ->  mEt_password.setText(v));
    }


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }

    //==================
    //private Functions

    private void btnRegisterClicked() {

        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        // delete last Step from Back Stack
        mFragmentManager.popBackStack();

        // Create new Frame
        mFragmentTransaction.replace
                (R.id.start_frame, _FragmentStartRegister.newInstance(getEmail()), "register");

        // Don't add to StackBack, when it is start Frame
        if(_ActivityStart.getStartFrame() != "register")
            mFragmentTransaction.addToBackStack("register");

        mFragmentTransaction.commit();
    }


    private void btnLoginClicked() {

        // Load User, if fails mUser = null
        mUser = _user.Login(getEmail(), getPassword()).getValue();

        if( mUser != null){
            try{
                Intent intent = new Intent(this.getActivity(), _ActivityCoach.class);
                intent.putExtra("ARG_USER_MAIL", mUser.getEmail());
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


}
