package com.hylux.calisthenics4;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hylux.calisthenics4.homeview.RecentActivitiesFragment;
import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Workout;
import com.hylux.calisthenics4.roomdatabase.ActivitiesDatabase;
import com.hylux.calisthenics4.roomdatabase.ActivitiesViewModel;
import com.hylux.calisthenics4.roomdatabase.OnTaskCompletedListener;
import com.hylux.calisthenics4.workoutview.WorkoutActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnTaskCompletedListener {

    public static final int NEW_WORKOUT_REQUEST = 0;

    private RecentActivitiesFragment fragment;
    private OnTaskCompletedListener onTaskCompletedListener;

    private ActivitiesViewModel activitiesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onTaskCompletedListener = this;

        final Button debugButton = findViewById(R.id.debugButton);
        debugButton.setOnClickListener(new View.OnClickListener() { //TODO Maybe can replace with lambda
            @Override
            public void onClick(View v) {
                Intent debugActivityIntent = new Intent(MainActivity.this, WorkoutActivity.class);
                Workout debugWorkout = Debug.debugWorkout();
                debugActivityIntent.putExtra("EXTRA_WORKOUT", debugWorkout);
//                startActivity(debugActivityIntent);
                startActivityForResult(debugActivityIntent, NEW_WORKOUT_REQUEST);
            }
        });

        //Firebase Firestore database for templates
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        addExercise(Debug.debugExercise(), database);

        //Room database for actual activities
        ActivitiesDatabase activitiesDatabase = ActivitiesDatabase.getDatabase(getApplicationContext());
        activitiesViewModel = new ActivitiesViewModel(getApplication());

        //Initialise RecentActivitiesFragment
        fragment = RecentActivitiesFragment.newInstance(new ArrayList<Workout>());
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragmentContainer, fragment, "raf").commit();

        activitiesViewModel.getRecentActivities(5,this);

        final ImageButton refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("REFRESH", "onClick()");
                activitiesViewModel.getRecentActivities(5, onTaskCompletedListener);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == NEW_WORKOUT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Workout activity = Objects.requireNonNull(data).getParcelableExtra("EXTRA_WORKOUT");
                activitiesViewModel.insert(activity);
            }
        }
    }

    private void addExercise(Exercise exercise, final FirebaseFirestore database) {
        //TODO Check if item exists (maybe can do by initializing id to 0 first)
        //TODO Do a further check if ID exists
        final Exercise addedExercise = exercise;
        if (exercise.getId().equals("default")) {
            database.collection("exercises")
                    .add(Debug.debugExercise())
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String exerciseId = documentReference.getId();
                            addedExercise.setId(exerciseId);
                            database.collection("exercises")
                                    .document(exerciseId)
                                    .set(addedExercise)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("FIRE_STORE", "DocumentSnapshot successfully written!");
                                        }
                                    });
                        }
                    });
        }
    }

    @Override
    public ArrayList<Workout> onGetRecentActivities(ArrayList<Workout> activities) {

        if (fragment != null) {
            fragment.setActivities(activities);
        }
        return activities;
    }
}
