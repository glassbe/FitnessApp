package com.example.fitnessapp.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.fitnessapp.db.DAO.ExerciseDAO;
import com.example.fitnessapp.db.DAO.StatusUpdateDAO;
import com.example.fitnessapp.db.DAO.UserDAO;
import com.example.fitnessapp.db.Entity.Exercise;
import com.example.fitnessapp.db.Entity.Program;
import com.example.fitnessapp.db.Entity.StatusUpdate;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.db.Entity.Workout;
import com.example.fitnessapp.db.Entity.WorkoutExerciseJoin;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, StatusUpdate.class, Exercise.class, Workout.class, Program.class, WorkoutExerciseJoin.class}, version = 8)
@TypeConverters({Converters.class})
abstract class FitnessDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

    public abstract StatusUpdateDAO statusUpdateDAO();

    public abstract ExerciseDAO exerciseDAO();

    private static volatile FitnessDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static FitnessDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FitnessDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FitnessDatabase.class, "fitness_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    //Seed Exercises
                    ExerciseDAO exerciseDAO = INSTANCE.exerciseDAO();
                    List<Exercise> basicExercises = new Seed().getExercises();

                    for (Exercise exer: basicExercises) {

                        Exercise fromDB = exerciseDAO.getExerciseByJsonId(exer.getJsonId());

                        if(fromDB != null){
                            boolean update = false;

                            if(fromDB.getName() != exer.getName()){
                                fromDB.setName(exer.getName());
                                update = true;
                            }

                            if(fromDB.getDescription() != exer.getDescription()){
                                fromDB.setDescription(exer.getDescription());
                                update = true;
                            }

                            if(fromDB.getPicturePath() != exer.getPicturePath()){
                                fromDB.setPicturePath(exer.getPicturePath());
                                update = true;
                            }



                            if(update) {
                                exerciseDAO.updateExercise(fromDB);
                            }
                        }
                        else{
                            exerciseDAO.insertExercise(exer);
                        }
                    }




                }
            });

        }
    };
}
