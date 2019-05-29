package com.hylux.calisthenics4.homeview;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;

public class RecentActivitiesFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener listener;

    private ActivitiesAdapter adapter;

    private ArrayList<Workout> activities;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (SwipeRefreshLayout.OnRefreshListener) context;
        } catch (Exception e) {
            Log.d("IMPLEMENT", "ON REFRESH LISTENER");
        }
    }

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

        // Set up RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ActivitiesAdapter(activities);
        recyclerView.setAdapter(adapter);

        // Set up SwipeRefreshLayout
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(listener);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_blue_dark, android.R.color.holo_green_dark);
        swipeRefreshLayout.setNestedScrollingEnabled(true);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public static RecentActivitiesFragment newInstance(ArrayList<Workout> activities) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("EXTRA_ACTIVITIES", activities);

        RecentActivitiesFragment fragment = new RecentActivitiesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public ArrayList<Workout> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Workout> activities) {
        this.activities = activities;

        //TODO BUG: on apply changes reset, recyclerView becomes null
        Log.d("ADAPTER", adapter.toString());
        adapter.setActivities(activities);
    }
}
