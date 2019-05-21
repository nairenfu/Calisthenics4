package com.hylux.calisthenics4.homeview;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;

public class RecentActivitiesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Workout> activities;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activities = new ArrayList<>();
        if (getArguments() != null) {
            activities = getArguments().getParcelableArrayList("EXTRA_ACTIVITIES");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recent_activities, container, false);

        //Set up RecyclerView
        recyclerView = rootView.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setAdapter(adapter);
        adapter = new ActivitiesAdapter(activities);

        return rootView;
    }

    public static RecentActivitiesFragment newInstance(ArrayList<Workout> activities) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("EXTRA_ACTIVITIES", activities);

        RecentActivitiesFragment fragment = new RecentActivitiesFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
