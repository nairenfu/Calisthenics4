package com.hylux.calisthenics4.roomdatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesViewModel extends AndroidViewModel {

    private ActivitiesRepository repository;

    private LiveData<List<Workout>> allActivities;

    public ActivitiesViewModel(@NonNull Application application) {
        super(application);
        repository = new ActivitiesRepository(application);
        allActivities = repository.getAllActivities();
    }

    LiveData<List<Workout>> getAllActivities() {
        return allActivities;
    }

    public void getRecentActivities(int n, OnTaskCompletedListener listener) {
        repository.getRecentActivities(n, listener);
    }

    public void getExerciseById(String id, OnTaskCompletedListener listener) {
        repository.getExerciseById(id, listener, ActivitiesRepository.ROOM);
    }

    public void insert(Workout activity) {
        repository.insert(activity);
    }

    public void addAllExercises(ArrayList<Exercise> exercises) {
        repository.addAllExercises(exercises);
    }
}
