package com.example.fitnessapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.databinding.FragmentStartCreateWorkoutPlanBinding;
import com.example.fitnessapp.databinding.FragmentStartYourGoalBinding;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.utils.EditableInputView.EditableInputView;
import com.onurkaganaldemir.ktoastlib.KToast;


import java.lang.ref.WeakReference;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;
import es.dmoral.toasty.Toasty;


import static android.content.ContentValues.TAG;
import static at.wirecube.additiveanimations.additive_animator.AdditiveAnimator.animate;
import static com.example.fitnessapp._FragmentStartYourGoal.START_ANIMATION_TIME;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link _FragmentStartCreateWorkoutPlan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class _FragmentStartCreateWorkoutPlan extends Fragment {

    private UserViewModel _user;
    private _ActivityStart_ViewModel mViewModel;

    private boolean UserIsCreated = false;
    private boolean WorkoutIsCreated = false;

    protected FragmentStartCreateWorkoutPlanBinding binding;


    private LooperThread mLooperThread= new LooperThread();
    private Thread mTextUpdate;
    private int mTextLength;


    public _FragmentStartCreateWorkoutPlan() {
        // Required empty public constructor
    }

    public static _FragmentStartCreateWorkoutPlan newInstance(String param1, String param2) {
        _FragmentStartCreateWorkoutPlan fragment = new _FragmentStartCreateWorkoutPlan();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mLooperThread.start();
        mViewModel = new ViewModelProvider(getActivity()).get(_ActivityStart_ViewModel.class);

        _user = new ViewModelProvider(this).get(UserViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout._fragment_start_create_workout_plan, container, false);
        binding = FragmentStartCreateWorkoutPlanBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.createWorkoutTextview.setText("Benutzer wird angelegt");
        mTextLength = binding.createWorkoutTextview.getText().toString().length();

        mTextUpdate = new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());

                while (!UserIsCreated) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String newString;
                                binding.createWorkoutTextview.append(".");
                                if (binding.createWorkoutTextview.getText().toString().length() >= mTextLength+4) {
                                    newString = binding.createWorkoutTextview.getText().toString().substring(0, binding.createWorkoutTextview.getText().toString().length() - 4);
                                    binding.createWorkoutTextview.setText(newString);
                                }
                                Log.d(TAG, "run: " + binding.createWorkoutTextview.getText().toString().length() + "!");
                            } catch (Exception e) {
                                Log.getStackTraceString(e);
                            }

                        }
                    });
                    Log.d(TAG, "Thread sleep");
                    SystemClock.sleep(500);
                }
                handler.post(()->{
                    binding.createWorkoutTextview.setText("Trainingsplan wird erstellt");
                    mTextLength = binding.createWorkoutTextview.getText().toString().length();
                });
                while(!WorkoutIsCreated){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String newString;
                                binding.createWorkoutTextview.append(".");
                                if (binding.createWorkoutTextview.getText().toString().length() >= mTextLength+4) {
                                    newString = binding.createWorkoutTextview.getText().toString().substring(0, binding.createWorkoutTextview.getText().toString().length() - 4);
                                    binding.createWorkoutTextview.setText(newString);
                                }
                                Log.d(TAG, "run: " + binding.createWorkoutTextview.getText().toString().length() + "!");
                            } catch (Exception e) {
                                Log.getStackTraceString(e);
                            }

                        }
                    });
                    Log.d(TAG, "Thread sleep");
                    SystemClock.sleep(500);
                }
            }
        });
        mTextUpdate.start();

        new Thread(()->{
            Handler handler = new Handler(Looper.getMainLooper());
            User user = mViewModel.getUser();
            _user.mUserRepo.Register(user.getEmail(),user.getPwHash(), true);
            _user.mUserRepo.UpdateInfo(user);
            handler.post(()->{
                Toasty.success(getActivity(),"Benutzer angelegt", Toasty.LENGTH_SHORT, true).show();
                UserIsCreated = true;
            });
        }).start();


        binding.createWorkoutLogoSquare.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.anim_try));




    }

    @Override
    public void onPause() {
        super.onPause();
        mTextUpdate.destroy();

    }


    static class LooperThread extends Thread{

        public Looper looper;
        public Handler handler;

        LooperThread(){
        }

        @Override
        public void run() {
            Looper.prepare();

            looper = looper.myLooper();

            handler = new Handler();

            Looper.loop();

            Log.d(TAG, "End of Thread");
        }
    }

    class MyRunnable implements Runnable {
        private Handler mThreadHandler;

        @Override
        public void run() {
            mThreadHandler = new Handler(Looper.getMainLooper());
            Looper.prepare();

                try {

                    mThreadHandler.post(() -> {

                        Log.d(TAG, "Thread sleep");
                        SystemClock.sleep(100);

                    });
                } catch (Exception e) {
                    Log.getStackTraceString(e);
                }
            Looper.loop();


//            for(int i = 0; i < 4; i++) {
//                Log.d(TAG, "run: start Thread" + i);
//                try{
//                    SystemClock.sleep(1000);
//                } catch (Exception e){
//                    Log.getStackTraceString(e);
//                }
//            }
        }
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<_FragmentStartCreateWorkoutPlan> fragmentWeakReference;
        private Handler mThreadHandler;

        MyAsyncTask(_FragmentStartCreateWorkoutPlan fragment){
            fragmentWeakReference = new WeakReference<_FragmentStartCreateWorkoutPlan>(fragment);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            _FragmentStartCreateWorkoutPlan fragment = fragmentWeakReference.get();
            if(fragment == null)
                return;

            mThreadHandler = new Handler(Looper.getMainLooper());

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                _FragmentStartCreateWorkoutPlan fragment = fragmentWeakReference.get();
                if(fragment == null) return null;


//                Looper.prepare();
                mThreadHandler.post(() -> {

                });

                mThreadHandler.post(() -> {

                });



//                Looper.loop();

                return null;

            } catch (Exception e){
                Log.getStackTraceString(e);
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

}