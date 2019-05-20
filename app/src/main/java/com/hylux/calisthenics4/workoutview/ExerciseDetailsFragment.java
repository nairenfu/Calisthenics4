package com.hylux.calisthenics4.workoutview;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hylux.calisthenics4.Debug;
import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Exercise;

public class ExerciseDetailsFragment extends Fragment {

    private StartWorkoutCallback startWorkoutCallback;

    private String exerciseId;
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
            exerciseId = getArguments().getString("EXTRA_EXERCISE_ID");
        } else {
            exerciseId = "DEFAULT";
        }
        exercise = Debug.debugExercise(); //TODO get from list of all exercises
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_exercise_details, container, false);

        TextView nameView = rootView.findViewById(R.id.exerciseName);
        nameView.setText(exerciseId);
        //TODO Either on swipe image change step as well, or a RecyclerView/List of image-step pair

        FloatingActionButton startWorkoutButton = rootView.findViewById(R.id.startWorkoutButton);
        startWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutCallback.startWorkout();
            }
        });

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        startWorkoutCallback = null;
    }

    public static ExerciseDetailsFragment newInstance(String exerciseId) {

        Bundle args = new Bundle();
        args.putString("EXTRA_EXERCISE_ID", exerciseId);

        ExerciseDetailsFragment fragment = new ExerciseDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
