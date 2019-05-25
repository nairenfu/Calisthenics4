package com.hylux.calisthenics4.createworkoutview;

import com.hylux.calisthenics4.objects.Exercise;

import java.util.ArrayList;

public interface CreateWorkoutListener {
    void onExercisesRetrieved(ArrayList<Exercise> exercises);
    void onExerciseSelected(Exercise exercise);
}
