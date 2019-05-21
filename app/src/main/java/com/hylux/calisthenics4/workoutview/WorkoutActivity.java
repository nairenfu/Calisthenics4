package com.hylux.calisthenics4.workoutview;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hylux.calisthenics4.DatabaseCallback;
import com.hylux.calisthenics4.Debug;
import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Set;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class WorkoutActivity extends FragmentActivity implements DatabaseCallback, StartWorkoutCallback, RoutineOverviewFragment.RoutineOverviewFragmentListener {

    private ArrayList<Fragment> fragments;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private Workout workout;
    private HashMap<String, Exercise> exerciseMap;
    private HashMap<String, String> exerciseNameMap;
    ArrayList<String> uniqueExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        exerciseMap = new HashMap<>();
        exerciseNameMap = new HashMap<>();

        if (getIntent() != null) {
            workout = getIntent().getParcelableExtra("EXTRA_WORKOUT");
            Log.d("WORKOUT", workout.toString());
        } else {
            workout = Debug.debugWorkout(); //TODO Change
        }

        //TODO Set flag OK once all data in
        //TODO What to do if not found
        uniqueExercises = new ArrayList<>();
        for (Set set : workout.getRoutine()) {
            if (!uniqueExercises.contains(set.getExerciseId())) {
                uniqueExercises.add(set.getExerciseId());
                getExerciseFromId(set.getExerciseId());
            }
        }
        Log.d("UNIQUE_EXERCISES", uniqueExercises.toString());

        fragments = new ArrayList<>();
        fragments.add(WorkoutOverviewFragment.newInstance(workout));
//        fragments.add(RoutineOverviewFragment.newInstance(workout.getRoutine()));
//        for (String exerciseId : uniqueExercises) {
//            fragments.add(ExerciseDetailsFragment.newInstance(exerciseId));
//        }
        Log.d("FRAGMENTS", fragments.toString());

        viewPager = findViewById(R.id.swipeViewPager);
        pagerAdapter = new SwipeViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public void getExerciseFromId(String exerciseId) {
        Log.d("FIRE_STORE", "getExerciseFromId " + exerciseId);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference exerciseDoc = database.collection("exercises").document(exerciseId);
        exerciseDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        Log.d("FIRE_STORE", Objects.requireNonNull(document.getData()).toString());
                        Log.d("FIRE_STORE", (String) document.get("name"));
                        Exercise exercise = new Exercise((HashMap<String, Object>) document.getData());
                        exerciseCallback(exercise);
                    }
                }
            }
        });
    }

    @Override
    public void exerciseCallback(Exercise exercise) {
        exerciseMap.put(exercise.getId(), exercise);
        exerciseNameMap.put((exercise.getId()), exercise.getName()); //TODO Change all exerciseNameMap to exerciseNamesMap
        Log.d("EXERCISE", exerciseMap.toString());
        if (exerciseNameMap.size() == uniqueExercises.size()) {
            fragments.add(RoutineOverviewFragment.newInstance(workout.getRoutine(), exerciseNameMap));
            pagerAdapter.notifyDataSetChanged();
            for (String exerciseId : uniqueExercises) {
                fragments.add(ExerciseDetailsFragment.newInstance(exerciseId));
                pagerAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void startWorkout() {
        Log.d("START", "WORKOUT");
        viewPager.setCurrentItem(1, true);
        workout.setStartTime(System.currentTimeMillis());

        ((RoutineOverviewFragment) fragments.get(1)).activate();
    }

    @Override
    public void setActualReps(int actualReps, int position) {
        workout.setActualReps(actualReps, position);
        Log.d("WORKOUT", workout.toString());
    }

    @Override
    public void onWorkoutEnded(long endTime) {
        workout.setEndTime(endTime);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("EXTRA_WORKOUT", workout);
        setResult(RESULT_OK, resultIntent);
        finish();

    }
}
