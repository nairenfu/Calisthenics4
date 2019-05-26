package com.hylux.calisthenics4.roomdatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Workout;

public class FirestoreViewModel extends AndroidViewModel {

    private ActivitiesRepository repository;

    public FirestoreViewModel(@NonNull Application application) {
        super(application);
        repository = new ActivitiesRepository(application);
    }

    public void addExercise(Exercise exercise, boolean overwrite) {
        repository.addExercise(exercise, overwrite);
    }

    public void addWorkout(Workout workout) {
        repository.addWorkout(workout);
    }

    public void getAllExercises(OnTaskCompletedListener listener) {
        repository.getAllExercisesAsync(listener);
    }
}