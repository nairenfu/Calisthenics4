package com.hylux.calisthenics4.homeview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hylux.calisthenics4.R;

public class ChooseWorkoutFragment extends Fragment {


    //TODO have a loading flag
    private ChoiceListener choiceListener;

    private WorkoutsAdapter adapter;

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

        final CardView chooseToSearchButton = rootView.findViewById(R.id.chooseView);
        final ImageView chooseButton = chooseToSearchButton.findViewById(R.id.chooseButton);

        final int[] parentWidth = new int[1];
        ViewTreeObserver observer = rootView.getViewTreeObserver();
        if (observer.isAlive()) {
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    parentWidth[0] = rootView.getMeasuredWidth();

                    chooseButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CardView.MarginLayoutParams searchViewParams = (CardView.MarginLayoutParams) chooseToSearchButton.getLayoutParams();
                            int finalWidth = parentWidth[0] - searchViewParams.leftMargin - searchViewParams.rightMargin;
                            Log.d("ANIM_FINAL_WIDTH", String.valueOf(finalWidth));

                            ValueAnimator animator = ValueAnimator.ofInt(chooseToSearchButton.getMeasuredWidth(), finalWidth);
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    CardView.MarginLayoutParams searchViewParams = (CardView.MarginLayoutParams) chooseToSearchButton.getLayoutParams();
                                    searchViewParams.width = (int) animation.getAnimatedValue();
                                    chooseToSearchButton.setLayoutParams(searchViewParams);
                                }
                            });
                            animator.start();
                            choiceListener.onStartActivity("cVboIWnFiDCszUf3sK5H");
                        }
                    });
                }
            });
        }

        RecyclerView workoutRecycler = rootView.findViewById(R.id.workoutRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        workoutRecycler.setLayoutManager(layoutManager);
        workoutRecycler.setHasFixedSize(true);
        adapter = new WorkoutsAdapter(choiceListener);
        workoutRecycler.setAdapter(adapter);

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        choiceListener = null;
    }

    public WorkoutsAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(WorkoutsAdapter adapter) {
        this.adapter = adapter;
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
