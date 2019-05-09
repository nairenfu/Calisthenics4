package com.hylux.calisthenics4.workoutview;


import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hylux.calisthenics4.R;

public class RoutineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_routine, container, false);

        final CardView cardView = rootView.findViewById(R.id.card);
        final LinearLayout expandContainer = cardView.findViewById(R.id.linearLayout);

        Button expandButton = rootView.findViewById(R.id.expandButton);
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout expandView = (LinearLayout) LayoutInflater
                        .from(getContext())
                        .inflate(R.layout.container_expand, expandContainer, true);
                //expandContainer.addView(expandView);
                cardView.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
            }
        });

        return rootView;
    }

    public static RoutineFragment newInstance() {

        Bundle args = new Bundle();

        RoutineFragment fragment = new RoutineFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
