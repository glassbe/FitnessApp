package com.example.fitnessapp.ui.coach;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fitnessapp.R;
import com.example.fitnessapp.ViewModel.StatusUpdateViewModel;
import com.example.fitnessapp.ViewModel.UserViewModel;
import com.example.fitnessapp.databinding.FragmentCoachStatusBinding;
import com.example.fitnessapp.db.Entity.StatusUpdate;
import com.example.fitnessapp.db.Entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class CoachStatusFragment extends Fragment {

    private FragmentCoachStatusBinding binding;

    private RecyclerView mRecyclerView;
    private StatusUpdateAdapter mStatusUpdateAdapter;

    private List<StatusUpdate> mStatusUpdateList = new ArrayList<>();

    private StatusUpdateViewModel _status;
    private UserViewModel _user;

    private StatusUpdate mStatusUpdate;
    private User mUser;


    public CoachStatusFragment() {
        // Required empty public constructor
    }


    public static CoachStatusFragment newInstance(String param1, String param2) {
        CoachStatusFragment fragment = new CoachStatusFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    @SuppressLint("StaticFieldLeak")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate/Bind the layout for this fragment
        binding = FragmentCoachStatusBinding.inflate(inflater,container,false);
        View view = binding.getRoot();


        //Set ViewModel (getting Data)
        _status = new ViewModelProvider(getActivity()).get(StatusUpdateViewModel.class);
        _user = new ViewModelProvider(getActivity()).get(UserViewModel.class);


        // Set Recycler
        mRecyclerView = binding.statusDaysRecyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);


        //Set Adapter async
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                //Create Adapter in Background Thread
                mStatusUpdateAdapter = new StatusUpdateAdapter();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                //Set Adapter in Main Thread
                mRecyclerView.setAdapter(mStatusUpdateAdapter);
            }
        }.execute();




        //Fill List with Data from Database
        new AsyncTask<Void,Void,Void>(){
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... voids) {

                mStatusUpdateList = _status.mStatusRepo.getAllUpdatesForUser(_user.getUser().getEmail());
                mStatusUpdateAdapter.submitList(mStatusUpdateList);

                //Set OnclickListener
                mStatusUpdateAdapter.setOnItemClickListener(new StatusUpdateAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(StatusUpdate status) {

                        //Set Status that StatusUpdateFragment can use the selected one
                        _status.setSelectedStatus(status);

                        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

                        // Try Block
                        try {

                            mFragmentManager.findFragmentByTag(status.getTimestamp() + status.getUserMail() + "StatusUpdate");

                            mFragmentTransaction.replace(R.id.coach_fullview_frame, new StatusUpdateFragment(), status.getTimestamp() + status.getUserMail() + "StatusUpdate");
                            mFragmentTransaction.addToBackStack(status.getTimestamp() + status.getUserMail() + "StatusUpdate");


                            mFragmentTransaction.commit();

                        } catch (Exception e){
                            Log.getStackTraceString(e);
                        }

//                Intent intent = new Intent(this, AddEditNoteActivity.class);
//                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
//                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
//                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
//                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
////                startActivityForResult(intent, EDIT_NOTE_REQUEST);
//                startActivity(intent);
                    }
                });

                return null;
            }
        }.execute();




        //SetOnClickListeners for bindings
        binding.btnStatusUpdateGraphAnalysis.setOnClickListener(v -> ClickGraphAnalysis());
        binding.statusDaysBackbuttonbottom.setOnClickListener(v -> getActivity().onBackPressed());

        binding.coachStatusBackground.setOnClickListener(v -> {});


        Glide.with(getContext())
                .load(_status.mStatusRepo.getUpdateForUserForOneDay(_user.getUser().getEmail(), new Date()).getPicturePath())
                .fitCenter()
                .placeholder(R.drawable.avatar_status_picture_my_blue)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.statusDaysPictureToday);






        return view;
    }

    private void ClickGraphAnalysis() {
        Toasty.info(getContext(),"Function will be implemented soon.", Toasty.LENGTH_SHORT, true).show();
    }
}