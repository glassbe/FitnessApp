package com.example.fitnessapp.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.fitnessapp.db.DAO.ExerciseDAO;
import com.example.fitnessapp.db.DAO.ProgramDAO;
import com.example.fitnessapp.db.DAO.StatusUpdateDAO;
import com.example.fitnessapp.db.DAO.UserDAO;
import com.example.fitnessapp.db.DAO.WorkoutDAO;
import com.example.fitnessapp.db.Entity.Exercise;
import com.example.fitnessapp.db.Entity.Program;
import com.example.fitnessapp.db.Entity.StatusUpdate;
import com.example.fitnessapp.db.Entity.User;
import com.example.fitnessapp.db.Entity.Workout;
import com.example.fitnessapp.db.Entity.WorkoutExerciseJoin;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, StatusUpdate.class, Exercise.class, Workout.class, Program.class, WorkoutExerciseJoin.class}, version = 16)
@TypeConverters({Converters.class})
abstract class FitnessDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

    public abstract StatusUpdateDAO statusUpdateDAO();

    public abstract ExerciseDAO exerciseDAO();

    public abstract ProgramDAO programDAO();

    public abstract WorkoutDAO workoutDAO();

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
                    ProgramDAO programDAO = INSTANCE.programDAO();
                    WorkoutDAO workoutDAO = INSTANCE.workoutDAO();

                    List<Exercise> basicExercises = new Seed().getExercises();
                    List<Program> basicProgram = new Seed().getProgram();
                    List<Workout> basicWorkouts = new Seed().getWorkouts();

                    for (Exercise exercise: basicExercises) {

                        Exercise fromDB = exerciseDAO.getExerciseByJsonId(exercise.getJsonId());

                        if(fromDB != null){
                            boolean update = false;

                            if(fromDB.getName() != exercise.getName()){
                                fromDB.setName(exercise.getName());
                                update = true;
                            }

                            if(fromDB.getDescription() != exercise.getDescription()){
                                fromDB.setDescription(exercise.getDescription());
                                update = true;
                            }

                            if(fromDB.getPicturePath() != exercise.getPicturePath()){
                                fromDB.setPicturePath(exercise.getPicturePath());
                                update = true;
                            }

                            if(update) {
                                exerciseDAO.updateExercise(fromDB);
                            }
                        }
                        else{
                            exerciseDAO.insertExercise(exercise);
                        }
                    }

                    for(Program program: basicProgram){
                        Program fromDB = programDAO.getProgramByJsonId(program.getJsonId());

                        if(fromDB != null){
                            boolean update = false;

                            if(fromDB.getName() != program.getName()){
                                fromDB.setName(program.getName());
                                update = true;
                            }

                            if(fromDB.getDescription() != program.getDescription()){
                                fromDB.setDescription(program.getDescription());
                                update = true;
                            }

                            if(fromDB.getPicturePath() != program.getPicturePath()){
                                fromDB.setPicturePath(program.getPicturePath());
                                update = true;
                            }

                            if(fromDB.getRequiredFitnessLevel() != program.getRequiredFitnessLevel()){
                                fromDB.setRequiredFitnessLevel(program.getRequiredFitnessLevel());
                                update = true;
                            }

                            if(update){
                                programDAO.updateProgram(fromDB);
                            }
                        }
                        else{
                            programDAO.insertProgram(program);
                        }

                    }

                    for(Workout workout : basicWorkouts){
                        Workout fromDB = workoutDAO.getWorkoutByJsonId(workout.getJsonId());

                        if(fromDB != null){

                                boolean update = false;

                                if(fromDB.getName() != workout.getName()){
                                    fromDB.setName(workout.getName());
                                    update = true;
                                }
                                if(fromDB.getDescription() != workout.getDescription()){
                                    fromDB.setDescription(workout.getDescription());
                                    update = true;
                                }
                                if(fromDB.getPicturePath() != workout.getPicturePath()){
                                    fromDB.setPicturePath(workout.getPicturePath());
                                    update = true;
                                }

                                if(update){
                                    workoutDAO.updateWorkout(fromDB);
                                }

                        }else {
                            Program existingProgram = programDAO.getProgramByJsonId(workout.getPlanId());

                            if(existingProgram != null){
                                workout.setPlanId(existingProgram.getId());
                                workoutDAO.insertWorkout(workout);
                            }
                        }


                    }

                }
            });

        }
    };
}
