package com.example.fitnessapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.db.Entity.User;
import com.google.android.material.textfield.TextInputEditText;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class _FragmentStartLogin extends Fragment
{
    //Use Services
    private UserViewModel _user;
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
    private _ActivityStart_ViewModel mViewModel;
    private View mImg_logo;
        private boolean mRememberMe;


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

    public static _FragmentStartLogin newInstance() {
        return new _FragmentStartLogin();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _user = new ViewModelProvider(this).get(UserViewModel.class);

        //Set Shared Elements to its position
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get User
//        if(getEmail() != null)
        mUser = _user.mUserRepo.getUser(mEmail);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout._fragment_start_login, container, false);

        // Set logo Adapter to make it draggable
        mImg_logo = view.findViewById(R.id.iv_login_logo_draggable);
        view.setOnTouchListener((v, event) -> onLogoTouch(v,event));


        // Set email Adapter
        mEt_eMail = view.findViewById(R.id.et_e_mail_text);
        mEt_eMail.setOnFocusChangeListener((v,hasFocus) -> emailChanged());

        // Set password Adapter
        mEt_password = view.findViewById(R.id.et_password_text);
        mEt_password.setOnFocusChangeListener((v,hasFocus) -> passwordChanged());

        // Set Checkbox Adapter
        mCb_passwordReminder = view.findViewById(R.id.cb_password_reminder);
        mCb_passwordReminder.setOnCheckedChangeListener((buttonView, isChecked) -> ChangeRememberMe(isChecked));

        // Set Login Button Adapter
        mBtn_login = view.findViewById(R.id.tv_login);
        mBtn_login.setOnClickListener(v -> btnLoginClicked());

        // Set Register Button Adapter
        mTv_register = view.findViewById(R.id.tv_register);
        mTv_register.setOnClickListener(v -> btnRegisterClicked());

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        AdditiveAnimator
//                .animate(view)
//                .scaleX(0)
//                .scaleY(0)
//                .alpha(0)
//                .start();
//
//        AdditiveAnimator
//                .animate(view)
//                .setDuration(500)
//                .scale(1)
//                .alpha(1)
//                .start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(_ActivityStart_ViewModel.class);

        mViewModel.getPassword().observe(getActivity(), (password -> mEt_password.setText(password)));
        mViewModel.getEmail().observe(getActivity(), (email -> mEt_eMail.setText(email)));
    }



    //==================
    //private Functions


    private void emailChanged() {
        mViewModel.setEmail(mEt_eMail.getText().toString());
    }

    private void passwordChanged() {
        mViewModel.setPassword(mEt_password.getText().toString());
    }

    private void ChangeRememberMe(boolean isChecked) {
        if (isChecked) {
            mRememberMe = true;
        } else {
            mRememberMe = false;
        }
    }

    private void btnRegisterClicked() {

        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out);


        // delete last Step from Back Stack
        mFragmentManager.popBackStack();

        // Create new Frame
        mFragmentTransaction
                .replace(R.id.start_frame, _FragmentStartRegister.newInstance(), "register")
                .addSharedElement(mImg_logo, ViewCompat.getTransitionName(mImg_logo));

        // Don't add to StackBack, when it is start Frame
        if(_ActivityStart.getStartFrame() != "register")
            mFragmentTransaction.addToBackStack("register");

        mFragmentTransaction.commit();
    }


    private void btnLoginClicked() {

        // Load User, if fails mUser = null
        Boolean success = false;
        String mail = getEmail();
        try {
            success =  _user.mUserRepo.Login(mail, getPassword(), mRememberMe);
        } catch (Exception e){
            Log.getStackTraceString(e);
        }

        if(success){
            try{
//                mUser.setRememberMe(mRememberMe);
//                _user.mUserRepo.UpdateInfo(mUser);

                Intent intent = new Intent(this.getActivity(), _ActivityCoach.class);
                Bundle bundle = ActivityOptions
                        .makeSceneTransitionAnimation(getActivity(),mImg_logo, ViewCompat.getTransitionName(mImg_logo))
                        .toBundle();
                intent.putExtra("ARG_USER_MAIL", mail);
                startActivity(intent,bundle);

//                getActivity().finish();
//                getActivity().overridePendingTransition(R.anim.zoom_out, R.anim.zoom_out);
                ((_ActivityStart) getActivity()).setupWindowAnimations();


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


    // Gimmig
    public boolean onLogoTouch(View v, MotionEvent event) {
        float x = (event.getX());
        float y = (event.getY());
        AdditiveAnimator.animate(mImg_logo, 500).centerX(x).centerY(y).start();
//        Handler handler = new Handler();
//        handler.postDelayed()
        return true;
    }

    protected void setupWindowAnimations() {
        Window window = getActivity().getWindow();

        Fade fade = new Fade();
        fade.setDuration(500);
//        fade.setStartDelay(2000);
//        window.setExitTransition(fade);

        window.setEnterTransition(fade);
//        window.setReturnTransition(fade);
//        window.setReenterTransition(fade);

    }
}
