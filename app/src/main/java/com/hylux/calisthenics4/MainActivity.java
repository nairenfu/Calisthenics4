package com.hylux.calisthenics4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hylux.calisthenics4.createworkoutview.CreateWorkoutActivity;
import com.hylux.calisthenics4.homeview.ChooseWorkoutFragment;
import com.hylux.calisthenics4.homeview.RecentActivitiesFragment;
import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Workout;
import com.hylux.calisthenics4.roomdatabase.ActivitiesDatabase;
import com.hylux.calisthenics4.roomdatabase.ActivitiesViewModel;
import com.hylux.calisthenics4.roomdatabase.FirestoreViewModel;
import com.hylux.calisthenics4.roomdatabase.OnTaskCompletedListener;
import com.hylux.calisthenics4.workoutview.SwipeViewPagerAdapter;
import com.hylux.calisthenics4.workoutview.WorkoutActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnTaskCompletedListener, ChooseWorkoutFragment.ChoiceListener, SwipeRefreshLayout.OnRefreshListener {

    public static final int NEW_ACTIVITY_REQUEST = 0;
    public static final int CREATE_WORKOUT_REQUEST = 1;

    private ActivitiesViewModel activitiesViewModel;
    private FirestoreViewModel firestoreViewModel;

    private RecentActivitiesFragment recentActivitiesFragment;
    private ChooseWorkoutFragment chooseWorkoutFragment;
    private ViewPager viewPager;

    private boolean withExercises = false;
    private ArrayList<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase Firestore database for templates
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        exercises = new ArrayList<>();

        //Room database for actual activities
        ActivitiesDatabase activitiesDatabase = ActivitiesDatabase.getDatabase(getApplicationContext());
        activitiesViewModel = new ActivitiesViewModel(getApplication());

        firestoreViewModel = new FirestoreViewModel(getApplication());
        firestoreViewModel.getAllExercises(this);
        firestoreViewModel.getAllWorkouts(this); // Maybe should put this behind

        //Instantiate fragments
        ArrayList<Fragment> fragments = new ArrayList<>();
        recentActivitiesFragment = RecentActivitiesFragment.newInstance(new ArrayList<Workout>());
        fragments.add(recentActivitiesFragment);
        chooseWorkoutFragment = ChooseWorkoutFragment.newInstance();
        fragments.add(chooseWorkoutFragment);

        //Set up SwipeViewPager
        viewPager = findViewById(R.id.swipeViewPager);
        PagerAdapter adapter = new SwipeViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == NEW_ACTIVITY_REQUEST) {
            if (resultCode == RESULT_OK) {
                Workout activity = Objects.requireNonNull(data).getParcelableExtra("EXTRA_WORKOUT");
                activitiesViewModel.insert(activity);
                viewPager.setCurrentItem(0);
                onRefresh();
            }
        }

        if (requestCode == CREATE_WORKOUT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Workout workout = Objects.requireNonNull(data).getParcelableExtra("EXTRA_WORKOUT");
                firestoreViewModel.addWorkout(workout);
            }
        }
    }

    @Override
    public void onGetRecentActivities(ArrayList<Workout> activities) {
        if (recentActivitiesFragment != null) {
            recentActivitiesFragment.setActivities(activities);
            recentActivitiesFragment.getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    @Override
    public void onGetAllExercises(ArrayList<Exercise> exercises) {
        Log.d("ON_GET_ALL_EX", exercises.toString());
        this.exercises = exercises;
        withExercises = true;
        activitiesViewModel.addAllExercises(exercises);
    }

    @Override
    public void onGetExerciseFromId(Exercise exercise) {

    }

    @Override
    public void onGetWorkoutFromId(Workout workout) {
        Intent debugActivityIntent = new Intent(MainActivity.this, WorkoutActivity.class);
        debugActivityIntent.putExtra("EXTRA_WORKOUT", workout);

        if (chooseWorkoutFragment != null) {
            chooseWorkoutFragment.setLoading(false);
        }

        startActivityForResult(debugActivityIntent, NEW_ACTIVITY_REQUEST);
    }

    @Override
    public void onGetAllWorkouts(ArrayList<Workout> workouts) {
        if (chooseWorkoutFragment != null) {
            Log.d("CWF_ADAPTER", chooseWorkoutFragment.getAdapter().toString()); // This is null on refresh.
            Log.d("CWF_WORKOUTS", workouts.toString());
            chooseWorkoutFragment.getAdapter().setWorkouts(workouts);
        }
    }

    @Override
    public void onCreateWorkout() {
        Intent createWorkoutIntent = new Intent(MainActivity.this, CreateWorkoutActivity.class);

        createWorkoutIntent.putExtra("WITH_EXERCISES", withExercises);
        if (withExercises) {
            createWorkoutIntent.putExtra("EXTRA_EXERCISES", exercises);
        }

        startActivityForResult(createWorkoutIntent, CREATE_WORKOUT_REQUEST);
    }

    @Override
    public void onStartActivity(String id) {
        if (chooseWorkoutFragment != null) {
            chooseWorkoutFragment.setLoading(true);
        }
        firestoreViewModel.getWorkoutByIdAsync(id, this);
    }

    @Override
    public void onRefresh() {
        if (recentActivitiesFragment != null) {
            activitiesViewModel.getRecentActivities(5, this);
        }
    }
}
