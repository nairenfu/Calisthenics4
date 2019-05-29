package com.hylux.calisthenics4.workoutview;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Ignore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hylux.calisthenics4.Debug;
import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkoutOverviewFragment extends Fragment {

    private Workout workout;

    private boolean withData;
    private ArrayList<String> progressions;
    private HashMap<String, Exercise> exercisesMap;

    private RecyclerView progressionsRecycler;
    private ProgressionAdapter adapter;

    private StartWorkoutCallback startWorkoutCallback;

    private View rootView;

//    private FrameLayout.LayoutParams layoutParams;

    @Override
    @SuppressWarnings("unchecked") // Clearly defined data types
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            workout = getArguments().getParcelable("EXTRA_WORKOUT");
            withData = getArguments().getBoolean("EXTRA_WITH");
            if (withData) {
                progressions = getArguments().getStringArrayList("EXTRA_PROGRESSIONS");
                exercisesMap = (HashMap<String, Exercise>) getArguments().getSerializable("EXTRA_EXERCISES");
            } else {
                progressions = new ArrayList<>();
                exercisesMap = new HashMap<>();
            }
        } else {
            workout = Debug.debugWorkout(); //TODO Change
            progressions = new ArrayList<>();
            exercisesMap = new HashMap<>();
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

        progressionsRecycler = rootView.findViewById(R.id.progressionRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        progressionsRecycler.setLayoutManager(layoutManager);

        if (withData) { // Or if exercises not 0 sized?
            adapter = new ProgressionAdapter(progressions, exercisesMap, getContext());
        } else {
            adapter = new ProgressionAdapter(getContext());
        }
        progressionsRecycler.setAdapter(adapter);


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

    public ProgressionAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ProgressionAdapter adapter) {
        this.adapter = adapter;
    }

    public ArrayList<String> getProgressions() {
        return progressions;
    }

    public void setProgressions(ArrayList<String> progressions) {
        this.progressions = progressions;
    }

    public HashMap<String, Exercise> getExercisesMap() {
        return exercisesMap;
    }

    public void setExercisesMap(HashMap<String, Exercise> exercisesMap) {
        this.exercisesMap = exercisesMap;
    }

    public void setData(ArrayList<String> progressions, HashMap<String, Exercise> exercisesMap) {
        this.progressions = progressions;
        this.exercisesMap = exercisesMap;

        adapter.setData(progressions, exercisesMap);
    }

    public RecyclerView getProgressionsRecycler() {
        return progressionsRecycler;
    }

//    void createProgressionsRecycler(ProgressionAdapter adapter) {
//        //TODO Maybe use ViewTreeObserver?
//        FrameLayout progressionContainer = rootView.findViewById(R.id.progressionContainer);
////        progressionContainer.removeAllViews();
//        //TODO BUG onDetach RecyclerView disappears
//
//        progressionsRecycler = new RecyclerView(Objects.requireNonNull(getContext()));
//        this.adapter = adapter;
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        progressionsRecycler.setLayoutManager(layoutManager);
//
//        progressionsRecycler.setAdapter(this.adapter);
//
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        progressionContainer.addView(progressionsRecycler, layoutParams);
//    }

    static WorkoutOverviewFragment newInstance(@NonNull Workout workout, boolean withData, @Nullable ArrayList<String> progressions, @Nullable HashMap<String, Exercise> exercisesMap) {

        Bundle args = new Bundle();
        args.putParcelable("EXTRA_WORKOUT", workout);
        args.putBoolean("EXTRA_WITH", withData);
        if (withData) {
            args.putStringArrayList("EXTRA_PROGRESSIONS", progressions);
            args.putSerializable("EXTRA_EXERCISES", exercisesMap);
        }

        WorkoutOverviewFragment fragment = new WorkoutOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }
}