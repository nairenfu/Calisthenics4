package com.hylux.calisthenics4.homeview;

import com.hylux.calisthenics4.objects.Exercise;

import java.util.ArrayList;

public interface OnExercisesRetrievedCallback {
    void onExercisesRetrieved(ArrayList<Exercise> exercises);
}
