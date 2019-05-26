package com.hylux.calisthenics4;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnTaskCompletedListener, ChooseWorkoutFragment.ChoiceListener {

    public static final int NEW_ACTIVITY_REQUEST = 0;
    public static final int CREATE_WORKOUT_REQUEST = 1;

    private ActivitiesViewModel activitiesViewModel;
    private FirestoreViewModel firestoreViewModel;

    private ArrayList<Fragment> fragments;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    private boolean withExercises = false;
    private ArrayList<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        onTaskCompletedListener = this;

//        final Button debugButton = findViewById(R.id.debugButton);
//        debugButton.setOnClickListener(new View.OnClickListener() { //TODO Maybe can replace with lambda
//            @Override
//            public void onClick(View v) {
//                Intent debugActivityIntent = new Intent(MainActivity.this, WorkoutActivity.class);
//                Workout debugWorkout = Debug.debugWorkout();
//                debugActivityIntent.putExtra("EXTRA_WORKOUT", debugWorkout);
//                startActivityForResult(debugActivityIntent, NEW_WORKOUT_REQUEST);
//            }
//        });

        //Firebase Firestore database for templates
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        exercises = new ArrayList<>();

        //Room database for actual activities
        ActivitiesDatabase activitiesDatabase = ActivitiesDatabase.getDatabase(getApplicationContext());
        activitiesViewModel = new ActivitiesViewModel(getApplication());

        firestoreViewModel = new FirestoreViewModel(getApplication());
        firestoreViewModel.getAllExercises(this);

        //Instantiate fragments
        fragments = new ArrayList<>();
        fragments.add(RecentActivitiesFragment.newInstance(new ArrayList<Workout>()));
        fragments.add(ChooseWorkoutFragment.newInstance());

        //Set up SwipeViewPager
        viewPager = findViewById(R.id.swipeViewPager);
        adapter = new SwipeViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        activitiesViewModel.getRecentActivities(5,this);

//        final ImageButton refreshButton = findViewById(R.id.refreshButton);
//        refreshButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("REFRESH", "onClick()");
//                activitiesViewModel.getRecentActivities(5, onTaskCompletedListener);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == NEW_ACTIVITY_REQUEST) {
            if (resultCode == RESULT_OK) {
                Workout activity = Objects.requireNonNull(data).getParcelableExtra("EXTRA_WORKOUT");
                activitiesViewModel.insert(activity);
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

        if (fragments.get(1).getClass() == RecentActivitiesFragment.class) {
            ((RecentActivitiesFragment) fragments.get(1)).setActivities(activities);
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
    public void onCreateWorkout() {
        Intent createWorkoutIntent = new Intent(MainActivity.this, CreateWorkoutActivity.class);

        createWorkoutIntent.putExtra("WITH_EXERCISES", withExercises);
        if (withExercises) {
            createWorkoutIntent.putExtra("EXTRA_EXERCISES", exercises);
        }

        startActivityForResult(createWorkoutIntent, CREATE_WORKOUT_REQUEST);
    }
}
