package com.example.fitnessapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.fitnessapp.db.DAO.StatusUpdateDAO;
import com.example.fitnessapp.db.DAO.UserDAO;
import com.example.fitnessapp.db.Entity.StatusUpdate;
import com.example.fitnessapp.db.Entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, StatusUpdate.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class FitnessDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract StatusUpdateDAO statusUpdateDAO();


    private static volatile FitnessDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);




    public static FitnessDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (FitnessDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FitnessDatabase.class, "fitness_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
