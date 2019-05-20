package com.hylux.calisthenics4;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Workout;
import com.hylux.calisthenics4.workoutview.WorkoutActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button debugButton = findViewById(R.id.debugButton);
        debugButton.setOnClickListener(new View.OnClickListener() { //TODO Maybe can replace with lambda
            @Override
            public void onClick(View v) {
                Intent debugActivityIntent = new Intent(MainActivity.this, WorkoutActivity.class);
                Workout debugWorkout = Debug.debugWorkout();
                debugActivityIntent.putExtra("EXTRA_WORKOUT", debugWorkout);
                startActivity(debugActivityIntent);

            }
        });


        FirebaseFirestore database = FirebaseFirestore.getInstance();
        addExercise(Debug.debugExercise(), database);
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
}
