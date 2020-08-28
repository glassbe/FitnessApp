package com.example.fitnessapp.ui.exercises;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.ExerciseDetailsFragment;
import com.example.fitnessapp.R;
import com.example.fitnessapp.ViewModel.ExercisesViewModel;
import com.example.fitnessapp.databinding.FragmentCoachExercisesBinding;
import com.example.fitnessapp.db.Entity.Exercise;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ExercisesFragment extends Fragment {

    private FragmentCoachExercisesBinding binding;


    private RecyclerView mRecyclerView;
    private ExerciseAdapter mExerciseAdapter;

    private List<Exercise> mExerciseList = new ArrayList<>();
    private ExercisesViewModel _exercise;
    //    private List<com.example.fitnessapp.db.Entity.Exercise> m;

    @Override
    @SuppressLint("StaticFieldLeak")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCoachExercisesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //Set ViewModel (getting Data)
        _exercise = new ViewModelProvider(getActivity()).get(ExercisesViewModel.class);


        // Set Recycler
        mRecyclerView = binding.exerciseRecyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        //Set Adapter async
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                //Set Adapter async
                mExerciseAdapter = new ExerciseAdapter();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mRecyclerView.setAdapter(mExerciseAdapter);
            }
        }.execute();



        //Fill List with Data from Database
        new AsyncTask<Void,Void,Void>(){
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... voids) {

                mExerciseList = _exercise.mExerciseRepo.getAllExercises();
                mExerciseAdapter.submitList(mExerciseList);

                //Set OnclickListener
                mExerciseAdapter.setOnItemClickListener(new ExerciseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Exercise exercise) {
                        //Set Current Exercise
                        _exercise.setExerciseToView(exercise);

                        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

                        // Try Block
                        try {


                        // New Fragment Block
                        Fragment f = null;
                        f = mFragmentManager.findFragmentByTag(exercise.getName());
                        if(f == null){
                            mFragmentTransaction.replace(R.id.coach_fullview_frame, new ExerciseDetailsFragment(), exercise.getName());
                            mFragmentTransaction.addToBackStack(exercise.getName());
                        } else {
                            mFragmentManager.popBackStack();
                            mFragmentManager.popBackStack(exercise.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }

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


//        //Set TouchHelper
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
////                _.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
//                Toast.makeText(getContext(), "Note deleted", Toast.LENGTH_SHORT).show();
//            }
//        }).attachToRecyclerView(mRecyclerView);




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
