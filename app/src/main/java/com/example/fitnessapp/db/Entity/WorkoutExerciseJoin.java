package com.example.fitnessapp.db.Entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(primaryKeys = {"workoutId", "exerciseId"},
        foreignKeys = {
                @ForeignKey(entity = Workout.class,
                            parentColumns = "id",
                            childColumns = "workoutId"),
                @ForeignKey(entity = Exercise.class,
                            parentColumns = "id",
                            childColumns = "exerciseId")
        },
        indices = {@Index("exerciseId")})
public class WorkoutExerciseJoin {
    private int workoutId;
    private int exerciseId;
    private int sets;
    private int reps;

    public WorkoutExerciseJoin(int workoutId, int exerciseId, int sets, int reps){
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
        this.sets = sets;
        this.reps = reps;

    }

    //GETTER


    public int getWorkoutId() {
        return workoutId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    //SETTER

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
