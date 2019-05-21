package com.hylux.calisthenics4.roomdatabase;

import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;

public interface OnTaskCompletedListener {

    ArrayList<Workout> onGetRecentActivities(ArrayList<Workout> activities);
}
