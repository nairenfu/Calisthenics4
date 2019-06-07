package com.hylux.calisthenics4.homeview;

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
import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;

public class ChooseWorkoutFragment extends Fragment {

    private ChoiceListener choiceListener;
    private boolean loading = false;

    private ArrayList<Workout> workouts, userWorkouts;

    private RecyclerView workoutRecycler;
    private WorkoutsAdapter adapter, userCreatedAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            choiceListener = (ChoiceListener) context;
        } catch (Exception e) {
            Log.d("IMPLEMENT", "CHOICE LISTENER");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_choose_workout, container, false);

        workouts = new ArrayList<>();
        userWorkouts = new ArrayList<>();

        workoutRecycler = rootView.findViewById(R.id.workoutsRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        workoutRecycler.setLayoutManager(layoutManager);
        workoutRecycler.setHasFixedSize(true);
        adapter = new WorkoutsAdapter(choiceListener);
        userCreatedAdapter = new WorkoutsAdapter(choiceListener);
        workoutRecycler.setAdapter(adapter);

        if (savedInstanceState != null) {
            workouts = savedInstanceState.getParcelableArrayList("SAVED_WORKOUTS");
            if (workouts != null) {
                adapter.setWorkouts(workouts);
            }

            userWorkouts = savedInstanceState.getParcelableArrayList("SAVED_USER_WORKOUTS");
            if (userWorkouts != null) {
                userCreatedAdapter.setWorkouts(userWorkouts);
            }
        }

        // Tab bar to toggle between Top Selected and User Created
        final TextView topText = rootView.findViewById(R.id.topHeader);
        topText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setWorkouts(workouts);
            }
        });

        final TextView createdText = rootView.findViewById(R.id.createdHeader);
        createdText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setWorkouts(userWorkouts);
            }
        });

        final FloatingActionButton createWorkoutButton = rootView.findViewById(R.id.createWorkoutButton);
        createWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceListener.onCreateWorkout();
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        choiceListener = null;
    }

    public ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
    }

    public ArrayList<Workout> getUserWorkouts() {
        return userWorkouts;
    }

    public void setUserWorkouts(ArrayList<Workout> userWorkouts) {
        this.userWorkouts = userWorkouts;
    }

    public RecyclerView getWorkoutRecycler() {
        return workoutRecycler;
    }

    public void setWorkoutRecycler(RecyclerView workoutRecycler) {
        this.workoutRecycler = workoutRecycler;
    }

    public WorkoutsAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(WorkoutsAdapter adapter) {
        this.adapter = adapter;
    }

    public WorkoutsAdapter getUserCreatedAdapter() {
        return userCreatedAdapter;
    }

    public void setUserCreatedAdapter(WorkoutsAdapter userCreatedAdapter) {
        this.userCreatedAdapter = userCreatedAdapter;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;

        adapter.setLoading(loading);
    }

    public static ChooseWorkoutFragment newInstance() {

        Bundle args = new Bundle();

        ChooseWorkoutFragment fragment = new ChooseWorkoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface ChoiceListener {
        void onCreateWorkout();
        void onStartActivity(String id);
    }
}
