package com.hylux.calisthenics4.homeview;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Set;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;

public class CreateWorkoutFragment extends Fragment {

    private Workout workout;
    private ArrayList<Exercise> exercises;

    private CreateWorkoutFragmentListener listener;

    private RecyclerView exercisesRecycler;
    private RecyclerView.Adapter exercisesAdapter;

    private RecyclerView routineRecycler;
    private RecyclerView.Adapter routineAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (CreateWorkoutFragmentListener) context;
        } catch (Exception e) {
            Log.e("LISTENER", "Implement CreateWorkoutFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workout = new Workout();

        if (getArguments() != null) {
            Log.d("CWF", "ARGS");
            exercises = getArguments().getParcelableArrayList("EXTRA_EXERCISES");
        }  //TODO ELSE should get from Firebase and notifyDataSetChanged

        if (exercises == null) {
            exercises = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_create_workout, container, false);

        // Set up basic info (Name and brief description)
        final CardView setupCard = rootView.findViewById(R.id.setupCard);
        final CardView nextButton = setupCard.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get EditText > Get Value > To String > Set in Workout
                workout.setName(((EditText) setupCard.findViewById(R.id.editName)).getText().toString());
                workout.setBrief(((EditText) setupCard.findViewById(R.id.editBrief)).getText().toString());

                Log.d("CREATE_WORKOUT_BASIC", workout.toString());

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
        final ConstraintLayout routineContainer = rootView.findViewById(R.id.routineContainer);
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
        exercisesRecycler = exercisesContainer.findViewById(R.id.exercisesRecycler);

        RecyclerView.LayoutManager exercisesLayoutManager = new LinearLayoutManager(getContext());
        exercisesRecycler.setLayoutManager(exercisesLayoutManager);

        exercisesAdapter = new ExerciseAdapter(exercises, listener);
        exercisesRecycler.setAdapter(exercisesAdapter);

        //Instantiate Routine RecyclerView
        routineRecycler = routineContainer.findViewById(R.id.routineRecycler);

        RecyclerView.LayoutManager routineLayoutManager = new LinearLayoutManager(getContext());
        routineRecycler.setLayoutManager(routineLayoutManager);

        routineAdapter = new RoutineAdapter(workout.getRoutine());
        routineRecycler.setAdapter(routineAdapter);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public void addSet(Set set) {
        workout.getRoutine().add(set);
        ((RoutineAdapter) routineAdapter).addToRoutine(set);
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public RecyclerView.Adapter getExercisesAdapter() {
        return exercisesAdapter;
    }

    public void setExercisesAdapter(RecyclerView.Adapter exercisesAdapter) {
        this.exercisesAdapter = exercisesAdapter;
    }

    public RecyclerView.Adapter getRoutineAdapter() {
        return routineAdapter;
    }

    public void setRoutineAdapter(RecyclerView.Adapter routineAdapter) {
        this.routineAdapter = routineAdapter;
    }

    public static CreateWorkoutFragment newInstance() {

        Bundle args = new Bundle();

        CreateWorkoutFragment fragment = new CreateWorkoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static CreateWorkoutFragment newInstance(ArrayList<Exercise> exercises) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("EXTRA_EXERCISES", exercises);

        CreateWorkoutFragment fragment = new CreateWorkoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface CreateWorkoutFragmentListener {
        void onExercisesRetrieved(ArrayList<Exercise> exercises);
        void onExerciseSelected(Exercise exercise);
    }
}
