package com.hylux.calisthenics4.workoutview;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hylux.calisthenics4.Debug;
import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Workout;

import java.util.Objects;

public class WorkoutOverviewFragment extends Fragment {

    private Workout workout;

    private RecyclerView progressionsRecycler;
    private ProgressionAdapter adapter;

    private StartWorkoutCallback startWorkoutCallback;

    private View rootView;

//    private FrameLayout.LayoutParams layoutParams;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            workout = getArguments().getParcelable("EXTRA_WORKOUT");
        } else {
            workout = Debug.debugWorkout(); //TODO Change
        }
        assert workout != null;
        Log.d("WORKOUT", workout.toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            startWorkoutCallback = (StartWorkoutCallback) context;
        } catch (Exception e) {
            Log.e("CALLBACK", "Implement StartWorkoutCallback");
        }
        if (adapter != null) {
            createProgressionsRecycler(adapter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_workout_overview, container, false);
        this.rootView = rootView;

        TextView nameView = rootView.findViewById(R.id.nameView);
        nameView.setText(workout.getName());

        TextView authorView = rootView.findViewById(R.id.authorView);
        authorView.setText(workout.getAuthorId());

        FrameLayout equipmentsContainer = rootView.findViewById(R.id.equipmentsContainer);
        for (int i : workout.getEquipments()) {
            TextView equipment = new TextView(getContext());
            equipment.setText(String.valueOf(i));
            equipmentsContainer.addView(equipment);
        }

        TextView briefView = rootView.findViewById(R.id.briefView);
        briefView.setText(workout.getBrief());

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

    public RecyclerView getProgressionsRecycler() {
        return progressionsRecycler;
    }

    void createProgressionsRecycler(ProgressionAdapter adapter) {
        //TODO Maybe use ViewTreeObserver?
        FrameLayout progressionContainer = rootView.findViewById(R.id.progressionContainer);
//        progressionContainer.removeAllViews();
        //TODO BUG onDetach RecyclerView disappears

        progressionsRecycler = new RecyclerView(Objects.requireNonNull(getContext()));
        this.adapter = adapter;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        progressionsRecycler.setLayoutManager(layoutManager);

        progressionsRecycler.setAdapter(this.adapter);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressionContainer.addView(progressionsRecycler, layoutParams);
    }

    static WorkoutOverviewFragment newInstance(@NonNull Workout workout) {

        Bundle args = new Bundle();
        args.putParcelable("EXTRA_WORKOUT", workout);

        WorkoutOverviewFragment fragment = new WorkoutOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }
}