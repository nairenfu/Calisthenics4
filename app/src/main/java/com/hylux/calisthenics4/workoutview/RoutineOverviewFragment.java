package com.hylux.calisthenics4.workoutview;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Set;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

// TODO Remember if it is activated or not, and current item. Probably also pause the timer?
public class RoutineOverviewFragment extends Fragment implements SetAdapterListener {

    private RoutineOverviewFragmentListener routineOverviewFragmentListener;

    private ArrayList<Set> routine;
    private HashMap<String, String> exerciseNamesMap;

    private SetAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button nextButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            routineOverviewFragmentListener = (RoutineOverviewFragmentListener) context;
        } catch (Exception e) {
            Log.e("CALLBACK", "Implement ActualRepsCallback");
        }
    }

    @SuppressWarnings("unchecked") //Clearly defined data type
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            routine = getArguments().getParcelableArrayList("EXTRA_ROUTINE");
            exerciseNamesMap = (HashMap<String, String>) getArguments().getSerializable("EXTRA_NAME_MAP");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_routine_overview, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.setRecycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new RoutineRecyclerLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SetAdapter(routine, exerciseNamesMap, this);
        recyclerView.setAdapter(adapter);

        nextButton = rootView.findViewById(R.id.activateButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = adapter.getActiveItem();
                if (currentItem == routine.size() - 1) {
                    routineOverviewFragmentListener.onWorkoutEnded(Calendar.getInstance().getTime().getTime());
                } else {
                    next();
                }
            }
        });

        if (savedInstanceState != null) {
            Log.d("BACK", String.valueOf(adapter.getActiveItem()));
            if (savedInstanceState.getInt("SAVED_CURRENT") != -1) {
                activate();
                adapter.setActiveItem(savedInstanceState.getInt("SAVED_CURRENT"));
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SAVED_CURRENT", adapter.getActiveItem());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("ROUTINE_OVERVIEW_FRAG", "onDetach()");
        routineOverviewFragmentListener = null;
    }

    public SetAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(SetAdapter adapter) {
        this.adapter = adapter;
    }

    void activate() {
        //BUG adapter is null on cold start. May apply to switching application
        adapter.setActiveItem(0);
        ((RoutineRecyclerLayoutManager) layoutManager).setScrollEnabled(false);
        adapter.notifyDataSetChanged();
        nextButton.setVisibility(View.VISIBLE);
    }

    static RoutineOverviewFragment newInstance(ArrayList<Set> routine, HashMap<String, String> exerciseNamesMap) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("EXTRA_ROUTINE", routine);
        args.putSerializable("EXTRA_NAME_MAP", exerciseNamesMap);

        RoutineOverviewFragment fragment = new RoutineOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void next() {
        try {
            EditText editActualRepsView = Objects.requireNonNull(layoutManager
                    .findViewByPosition(adapter
                            .getActiveItem()))
                    .findViewById(R.id.editActualReps);
            int actualReps = Integer.valueOf(editActualRepsView.getText().toString());
            routineOverviewFragmentListener.setActualReps(actualReps, adapter.getActiveItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter.setActiveItem((adapter).getActiveItem() + 1);
        if (adapter.getActiveItem() != -1) {
            ((RoutineRecyclerLayoutManager) layoutManager).setScrollEnabled(false);
        }
        adapter.notifyDataSetChanged();
        ((RoutineRecyclerLayoutManager) layoutManager).scrollToPositionWithOffset((adapter).getActiveItem(), 0);

        if (adapter.getActiveItem() >= routine.size()) {
            adapter.setActiveItem(-1);
            ((RoutineRecyclerLayoutManager) layoutManager).setScrollEnabled(true);
        }
    }

    @Override
    public void onDetailsRequested(String exerciseId) {
        routineOverviewFragmentListener.onDetailsRequested(exerciseId);
    }

    interface RoutineOverviewFragmentListener {
        void setActualReps(int actualReps, int position);
        void onWorkoutEnded(long endTime);
        void onDetailsRequested(String exerciseId);
    }
}