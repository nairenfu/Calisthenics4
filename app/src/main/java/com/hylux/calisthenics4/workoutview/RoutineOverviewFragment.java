package com.hylux.calisthenics4.workoutview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Set;

import java.util.ArrayList;
import java.util.HashMap;

public class RoutineOverviewFragment extends Fragment {

    private ArrayList<Set> routine;
    HashMap<String, String> exerciseNamesMap;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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

        recyclerView = rootView.findViewById(R.id.setRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SetAdapter(routine, exerciseNamesMap);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public static RoutineOverviewFragment newInstance(ArrayList<Set> routine, HashMap<String, String> exerciseNamesMap) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("EXTRA_ROUTINE", routine);
        args.putSerializable("EXTRA_NAME_MAP", exerciseNamesMap);

        RoutineOverviewFragment fragment = new RoutineOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }
}