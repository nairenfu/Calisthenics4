package com.hylux.calisthenics4.workoutview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Set;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RoutineOverviewFragment extends Fragment {

    private RoutineOverviewFragmentListener actualRepsCallback;

    private ArrayList<Set> routine;
    HashMap<String, String> exerciseNamesMap;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button nextButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            actualRepsCallback = (RoutineOverviewFragmentListener) context;
        } catch (Exception e) {
            Log.e("CALLBACK", "Implement ActualRepsCallback");
        }
    }

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

        adapter = new SetAdapter(routine, exerciseNamesMap, actualRepsCallback);
        recyclerView.setAdapter(adapter);

        nextButton = rootView.findViewById(R.id.activateButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText editActualRepsView = Objects.requireNonNull(layoutManager
                            .findViewByPosition(((SetAdapter) adapter)
                                    .getActiveItem()))
                            .findViewById(R.id.editActualReps);
                    int actualReps = Integer.valueOf(editActualRepsView.getText().toString());
                    actualRepsCallback.setActualReps(actualReps, ((SetAdapter) adapter).getActiveItem());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((SetAdapter) adapter).setActiveItem(((SetAdapter) adapter).getActiveItem() + 1);
                if (((SetAdapter) adapter).getActiveItem() != -1) {
                    ((RoutineRecyclerLayoutManager) layoutManager).setScrollEnabled(false);
                }
                adapter.notifyDataSetChanged();
                ((RoutineRecyclerLayoutManager) layoutManager).scrollToPositionWithOffset(((SetAdapter) adapter).getActiveItem(), 0);

                if (((SetAdapter) adapter).getActiveItem() >= routine.size()) {
                    ((SetAdapter) adapter).setActiveItem(-1);
                    ((RoutineRecyclerLayoutManager) layoutManager).setScrollEnabled(true);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        actualRepsCallback = null;
    }

    public void activate() {
        //BUG adapter is null on cold start. May apply to switching application
        ((SetAdapter) adapter).setActiveItem(0);
        ((RoutineRecyclerLayoutManager) layoutManager).setScrollEnabled(false);
        adapter.notifyDataSetChanged();
        nextButton.setVisibility(View.VISIBLE);
    }

    public static RoutineOverviewFragment newInstance(ArrayList<Set> routine, HashMap<String, String> exerciseNamesMap) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("EXTRA_ROUTINE", routine);
        args.putSerializable("EXTRA_NAME_MAP", exerciseNamesMap);

        RoutineOverviewFragment fragment = new RoutineOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    interface RoutineOverviewFragmentListener {
        void setActualReps(int actualReps, int position);
    }
}