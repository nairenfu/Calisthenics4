package com.hylux.calisthenics4.workoutview;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hylux.calisthenics4.Debug;
import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Exercise;

public class ExerciseDetailsFragment extends Fragment {

    private StartWorkoutCallback startWorkoutCallback;

    private Exercise exercise;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            startWorkoutCallback = (StartWorkoutCallback) context;
        } catch (Exception e) {
            Log.e("CALLBACK", "Implement StartWorkoutCallback");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            exercise = getArguments().getParcelable("EXTRA_EXERCISE");
        } else {
            exercise = Debug.debugExercise(false); // TODO replace to a better default exercise
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_exercise_details, container, false);

        TextView nameView = rootView.findViewById(R.id.exerciseName);
        nameView.setText(exercise.getName());
        //TODO Either on swipe image change step as well, or a RecyclerView/List of image-step pair

        RecyclerView stepsRecycler = rootView.findViewById(R.id.stepsRecycler);
        Log.d("STEPS_RECYCLER", stepsRecycler.toString());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        stepsRecycler.setLayoutManager(layoutManager);

        StepsAdapter stepsAdapter = new StepsAdapter(exercise.getSteps());
        stepsRecycler.setAdapter(stepsAdapter);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        startWorkoutCallback = null;
    }

    static ExerciseDetailsFragment newInstance(Exercise exercise) {

        Bundle args = new Bundle();
        args.putParcelable("EXTRA_EXERCISE", exercise);
        Log.d("EXTRA_EXERCISE", exercise.toString());

        ExerciseDetailsFragment fragment = new ExerciseDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
