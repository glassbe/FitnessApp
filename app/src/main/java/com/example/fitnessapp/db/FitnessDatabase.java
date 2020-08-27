package com.example.fitnessapp.db;

import android.content.Context;
import android.os.AsyncTask;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, StatusUpdate.class, Exercise.class, Workout.class, Program.class, WorkoutExerciseJoin.class}, version = 10)
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
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExerciseDAO mExerciseDAO;

        public PopulateDbAsyncTask(FitnessDatabase db) {
            mExerciseDAO = db.exerciseDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<String> ex1_pics = Arrays.asList("https://wger.de/media/exercise-images/172/Push-ups-1.png.800x800_q90.png","https://wger.de/media/exercise-images/172/Push-ups-2.png.800x800_q90.png");
            List<String> ex1_muscles = Arrays.asList("Anterior deltoid",
                                                    "Biceps brachii",
                                                    "Pectoralis major",
                                                    "Quadriceps femoris",
                                                    "Rectus abdominis",
                                                    "Serratus anterior");

            mExerciseDAO.insertExercise(new Exercise("Test Uebung1", "Das ist eine Testbeschreibung fuer diese Uebung. Wir werden sehen, was am Ende dabei herauskommt.", ex1_pics, ex1_muscles));
            mExerciseDAO.insertExercise(new Exercise("Test Uebung2", "Das ist eine Testbeschreibung fuer diese Uebung. Wir werden sehen, was am Ende dabei herauskommt.", ex1_pics, ex1_muscles));
            mExerciseDAO.insertExercise(new Exercise("Test Uebung3", "Das ist eine Testbeschreibung fuer diese Uebung. Wir werden sehen, was am Ende dabei herauskommt.", ex1_pics, ex1_muscles));
            return null;
        }
    }
}
