package com.hylux.calisthenics4.createworkoutview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Set;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateWorkoutActivity extends AppCompatActivity implements CreateWorkoutListener{

    private Workout workout;
    private ArrayList<Exercise> exercises;

    private RoutineAdapter routineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        workout = new Workout();
        workout.setAuthorId("user"); // TODO change to UserID if logged in
        HashMap<String, String> exerciseNamesMap = new HashMap<>();

        // Set up Exercises list
        if (getIntent() != null) {
            if (getIntent().getBooleanExtra("WITH_EXERCISES", false)) {
                exercises = getIntent().getParcelableArrayListExtra("EXTRA_EXERCISES");
                for (Exercise exercise : exercises) {
                    exerciseNamesMap.put(exercise.getId(), exercise.getName());
                }
            } else {
                exercises = new ArrayList<>();
            }
        }

        // Set up basic info (Name and brief description)
        final CardView setupCard = findViewById(R.id.setupCard);
        final CardView nextButton = setupCard.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get EditText > Get Value > To String > Set in Workout
                workout.setName(((EditText) setupCard.findViewById(R.id.editName)).getText().toString());
                workout.setBrief(((EditText) setupCard.findViewById(R.id.editBrief)).getText().toString());

                Log.d("CREATE_WORKOUT_BASIC", workout.toString());

                // Hide Setup Card once done
                setupCard
                        .animate()
                        .translationY(-setupCard.getMeasuredHeight())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                setupCard.setVisibility(View.GONE);
                            }
                        });
            }
        });

        // Set up opening and closing of exercises side bar
        final ConstraintLayout routineContainer = findViewById(R.id.routineContainer);
        final boolean[] exercisesContainerExpanded = {true};
        final CardView exercisesContainer = routineContainer.findViewById(R.id.exercisesContainer);
        final CardView expandExercisesContainerButton = exercisesContainer.findViewById(R.id.expandExercisesButton);
        expandExercisesContainerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int translation;
                if (exercisesContainerExpanded[0]) {
                    translation = expandExercisesContainerButton.getMeasuredWidth() - exercisesContainer.getMeasuredWidth();
                } else {
                    translation = exercisesContainer.getMeasuredWidth() - expandExercisesContainerButton.getMeasuredWidth();
                }
                exercisesContainer
                        .animate()
                        .translationXBy(translation)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                exercisesContainerExpanded[0] = !exercisesContainerExpanded[0];
                            }
                        });
            }
        });

        // Instantiate Exercises RecyclerView (View > LayoutManager > Adapter)
        RecyclerView exercisesRecycler = exercisesContainer.findViewById(R.id.exercisesRecycler);

        RecyclerView.LayoutManager exercisesLayoutManager = new LinearLayoutManager(this);
        exercisesRecycler.setLayoutManager(exercisesLayoutManager);

        ExerciseAdapter exercisesAdapter = new ExerciseAdapter(exercises, this);
        exercisesRecycler.setAdapter(exercisesAdapter);

        //Instantiate Routine RecyclerView
        RecyclerView routineRecycler = routineContainer.findViewById(R.id.routineRecycler);

        RecyclerView.LayoutManager routineLayoutManager = new LinearLayoutManager(this);
        routineRecycler.setLayoutManager(routineLayoutManager);

        routineAdapter = new RoutineAdapter(workout.getRoutine(), exerciseNamesMap);
        routineRecycler.setAdapter(routineAdapter);

        // Instantiate ItemTouchHelper for reordering/removing exercises from RoutineRecycler
        ItemTouchHelper.Callback touchHelperCallback = new RoutineItemTouchHelperCallback(routineAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(routineRecycler);

        // Finish Creating
        FloatingActionButton finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("EXTRA_WORKOUT", workout);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    public void onExerciseSelected(Exercise exercise) {
        Log.d("RECYCLER_EXERCISE", exercise.toString());
        workout.getRoutine().add(new Set(exercise.getId()));
        Log.d("RECYCLER_WORKOUT", workout.toString());
//        routineAdapter.getRoutine().add(new Set(exercise.getId()));
        // Somehow ExerciseAdapter has the Routine itself, not just a reference. Updates to Workout Routine updates ExerciseAdapter Routine.
        Log.d("RECYCLER_ROUTINE", routineAdapter.getRoutine().toString());
        routineAdapter.notifyDataSetChanged();
    }
}
