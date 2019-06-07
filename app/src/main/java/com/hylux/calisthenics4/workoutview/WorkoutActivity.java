package com.hylux.calisthenics4.workoutview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.hylux.calisthenics4.Debug;
import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Set;
import com.hylux.calisthenics4.objects.Workout;
import com.hylux.calisthenics4.roomdatabase.ActivitiesDatabase;
import com.hylux.calisthenics4.roomdatabase.ActivitiesViewModel;
import com.hylux.calisthenics4.roomdatabase.OnTaskCompletedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

// TODO Generation: need to sort out how to generate exercise briefs. If not too many will be generated
// TODO New: explore section: explore exercises and their briefs.

public class WorkoutActivity extends FragmentActivity implements OnTaskCompletedListener, StartWorkoutCallback, RoutineOverviewFragment.RoutineOverviewFragmentListener, ProgressionAdapter.OnLevelSelectedListener {

    private ActivitiesViewModel activitiesViewModel;

    private ArrayList<Fragment> fragments;
    private WorkoutOverviewFragment workoutOverviewFragment;
    private RoutineOverviewFragment routineOverviewFragment;
    private HashMap<String, Integer> exerciseIdPositionMap;

    private ToggleSwipeViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private Workout workout;
    private HashMap<String, Exercise> exerciseMap;
    private HashMap<String, String> exerciseNameMap;
    HashSet<String> uniqueExercises;
    ArrayList<String> progressions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        ActivitiesDatabase activitiesDatabase = ActivitiesDatabase.getDatabase(getApplicationContext());
        activitiesViewModel = new ActivitiesViewModel(getApplication());

        exerciseIdPositionMap = new HashMap<>();
        exerciseMap = new HashMap<>();
        exerciseNameMap = new HashMap<>();

        if (getIntent() != null) {
            workout = getIntent().getParcelableExtra("EXTRA_WORKOUT");
            Log.d("WORKOUT", workout.toString());
        } else {
            workout = Debug.debugWorkout(); //TODO Change
        }

        fragments = new ArrayList<>();

        //TODO What to do if not found
        uniqueExercises = new HashSet<>();
        progressions = new ArrayList<>();
        for (Set set : workout.getRoutine()) {
            if (!uniqueExercises.contains(set.getExerciseId())) {
                uniqueExercises.add(set.getExerciseId());
                activitiesViewModel.getExerciseById(set.getExerciseId(), this);
            }
        }
        Log.d("UNIQUE_EXERCISES", uniqueExercises.toString());

        if (fragments.size() == 0) {
            workoutOverviewFragment = WorkoutOverviewFragment.newInstance(workout, false, null, null);
            fragments.add(workoutOverviewFragment);
        }
        Log.d("FRAGMENTS", fragments.toString());

        viewPager = findViewById(R.id.swipeViewPager);
        pagerAdapter = new SwipeViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        }

        if (viewPager.getCurrentItem() == 1) {
            routineOverviewFragment.getAdapter().setActiveItem(-1);
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            viewPager.setCanSwipe(true);
        }

        if (viewPager.getCurrentItem() > 1) {
            viewPager.setCurrentItem(1);
        }
    }

    @Override
    public void startWorkout() {
        Log.d("START", "WORKOUT");
        viewPager.setCurrentItem(1, true);
        viewPager.setCanSwipe(false);

        workout.setStartTime(Calendar.getInstance().getTime().getTime());

        routineOverviewFragment.activate();
    }

    @Override
    public void setActualReps(int actualReps, int position) {
        workout.setActualReps(actualReps, position);
        Log.d("WORKOUT", workout.toString());
    }

    @Override
    public void onWorkoutEnded(long endTime) {
        workout.setEndTime(endTime);

        Log.d("TIME", String.valueOf(workout.getStartTime()));
        Log.d("TIME", String.valueOf(workout.getEndTime()));

        Intent resultIntent = new Intent();
        resultIntent.putExtra("EXTRA_WORKOUT", workout);
        setResult(RESULT_OK, resultIntent);
        finish();

    }

    @Override
    public void onDetailsRequested(String exerciseId) {
        Integer position = exerciseIdPositionMap.get(exerciseId);
        if (position != null) {
            viewPager.setCurrentItem(position, true);
        } else {
            Toast.makeText(getApplicationContext(), "Sorry, details could not be retrieved.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetRecentActivities(ArrayList<Workout> activities) {

    }

    @Override
    public void onGetAllExercises(ArrayList<Exercise> exercises) {

    }

    @Override
    public void onGetExerciseFromId(Exercise exercise) {
        exerciseMap.put(exercise.getId(), exercise);
        if (exercise.isProgressive() && exercise.getId().equals(exercise.getProgression().get(exercise.getProgression().size()-1))) {
            Log.d("PROGRESSIONS", exercise.getName());
            progressions.add(exercise.getId());
            Log.d("PROGRESSIONS", exercise.getProgression().toString());
            for (String exerciseId : exercise.getProgression()) {
                String id = exerciseId.replace(" ", "");
                if (exerciseMap.get(id) == null) {
                    Log.d("PROGRESSIONS_NULL", id);
                    activitiesViewModel.getExerciseById(id, this);
                    uniqueExercises.add(id);
                } else {
                    Log.d("PROGRESSIONS_NOT_N", Objects.requireNonNull(exerciseMap.get(id)).getName());
                }
            }
        }
        exerciseNameMap.put((exercise.getId()), exercise.getName()); //TODO Change all exerciseNameMap to exerciseNamesMap
        Log.d("EXERCISE", exerciseMap.toString());
        Log.d("PROGRESSION_SIZE", String.valueOf(exerciseNameMap.size()));
        Log.d("PROGRESSION_UNIQUE", String.valueOf(uniqueExercises.size()));
        if (exerciseNameMap.size() == uniqueExercises.size()) {
            routineOverviewFragment = RoutineOverviewFragment.newInstance(workout.getRoutine(), exerciseNameMap);
            fragments.add(routineOverviewFragment);
            if (fragments.size() == 0) {
                workoutOverviewFragment = WorkoutOverviewFragment.newInstance(workout, true, progressions, exerciseMap);
                fragments.set(0, workoutOverviewFragment);
            } else {
                workoutOverviewFragment.setData(progressions, exerciseMap);
            }

            pagerAdapter.notifyDataSetChanged();
            Log.d("EXERCISES", uniqueExercises.toString());
            for (String exerciseId : uniqueExercises) {
                exerciseIdPositionMap.put(exerciseId, fragments.size());
                fragments.add(ExerciseDetailsFragment.newInstance(exerciseMap.get(exerciseId)));
                pagerAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onGetWorkoutFromId(Workout workout) {

    }

    @Override
    public void onGetWorkoutsByAuthor(ArrayList<Workout> workouts) {

    }

    @Override
    public void onGetAllWorkouts(ArrayList<Workout> workouts) {

    }

    @Override
    public void onLevelSelected(int parentPosition, int position) {
        Log.d("SPINNER", "PARENT" + parentPosition + ", POSITION" + position);

        Log.d("EXERCISE_MAP", exerciseMap.toString());
        Log.d("PROGRESSIONS", progressions.toString());

        for (Set set : workout.getRoutine()) {
            if (progressions.size() > 0 && Objects.requireNonNull(exerciseMap.get(progressions.get(parentPosition))).getProgression().contains(set.getExerciseId())) {
                set.setExerciseId(Objects.requireNonNull(exerciseMap.get(set.getExerciseId())).getProgression().get(position));
                if (routineOverviewFragment != null) {
                    routineOverviewFragment.getAdapter().notifyDataSetChanged();
                }
            }
        }
    }
}