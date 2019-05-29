package com.hylux.calisthenics4.roomdatabase;

import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;

public interface OnTaskCompletedListener {
    void onGetRecentActivities(ArrayList<Workout> activities);
    void onGetAllExercises(ArrayList<Exercise> exercises);
    void onGetExerciseFromId(Exercise exercise);
    void onGetWorkoutFromId(Workout workout);
    void onGetAllWorkouts(ArrayList<Workout> workouts);
}
