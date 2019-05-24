package com.hylux.calisthenics4.homeview;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Workout;

import java.util.Objects;

public class CreateWorkoutFragment extends Fragment {

    private Workout workout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workout = new Workout();
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

        return rootView;
    }

    public static CreateWorkoutFragment newInstance() {

        Bundle args = new Bundle();

        CreateWorkoutFragment fragment = new CreateWorkoutFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
