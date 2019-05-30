package com.hylux.calisthenics4.workoutview;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Set;

import java.util.ArrayList;
import java.util.HashMap;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetViewHolder> {

    private NextSetCallback listener;

    private int activeItem = -1;

    private ArrayList<Set> routine;
    private HashMap<String, String> exerciseNamesMap;

    private CountDownTimer timer = null;

    static class SetViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView exerciseNameView, repsView;
        private ImageView detailsButton;
        private FrameLayout expandedLayout;

        SetViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            exerciseNameView = itemView.findViewById(R.id.exerciseName);
            repsView = itemView.findViewById(R.id.reps);
            detailsButton = itemView.findViewById(R.id.detailsButton);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);
        }

        void setExerciseName(String exerciseName) {
            exerciseNameView.setText(exerciseName);
        }

        void setReps(int reps) {
            repsView.setText(String.valueOf(reps));
        }

        void setVisible(boolean visible, int type) {
            if (visible) {
                itemView.setLayoutParams(
                        new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.MATCH_PARENT,
                                ConstraintLayout.LayoutParams.MATCH_PARENT));
                expandedLayout.removeAllViews();

//                detailsButton.setVisibility(View.GONE);
//                detailsButton.setVisibility(View.VISIBLE);

                View expanded;
                if (type == Set.REPS) {
                    expanded = LayoutInflater.from(expandedLayout.getContext()).inflate(R.layout.container_reps, expandedLayout, false);
                } else {
                    expanded = LayoutInflater.from(expandedLayout.getContext()).inflate(R.layout.container_time, expandedLayout, false);
                }

                expandedLayout.addView(expanded);
            } else {
                itemView.setLayoutParams(
                        new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.MATCH_PARENT,
                                ConstraintLayout.LayoutParams.WRAP_CONTENT));
                expandedLayout.removeAllViews();
//                detailsButton.setVisibility(View.GONE);
            }
        }
    }

    SetAdapter(ArrayList<Set> routine, HashMap<String, String> exerciseNamesMap, NextSetCallback listener) {
        this.routine = routine;
        this.exerciseNamesMap = exerciseNamesMap;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SetAdapter.SetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View setView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_set, parent, false);
        return new SetViewHolder(setView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SetAdapter.SetViewHolder setViewHolder, int position) {
        final String setId = routine.get(position).getExerciseId();
        setViewHolder.setExerciseName(exerciseNamesMap.get(setId));

        setViewHolder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DETAILS", setId);
            }
        });

        int targetReps = routine.get(position).getTargetReps();
        setViewHolder.setReps(targetReps);

        if (activeItem == position) {
            int type = routine.get(position).getType();
            setViewHolder.setVisible(true, type);

            // TODO set a Switch group to switch between time and reps

            switch (type) {
                case Set.REPS:
                    EditText actualRepsView = setViewHolder.itemView.findViewById(R.id.editActualReps);
                    actualRepsView.setText(String.valueOf(routine.get(position).getActualReps()));
                    break;

                case Set.TIME:
                    timer = new CountDownTimer((long) targetReps * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            TextView timeView = setViewHolder.itemView.findViewById(R.id.timeView);
                            timeView.setText(String.valueOf((int) millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {
                            listener.next();
                        }
                    };
                    timer.start();
            }
        } else {
            setViewHolder.setVisible(false, 0);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        listener = null;
    }

    @Override
    public int getItemCount() {
        return routine.size();
    }

    int getActiveItem() {
        return activeItem;
    }

    void setActiveItem(int activeItem) {
        this.activeItem = activeItem;
        if (timer != null) { //Prevent memory leak
            timer.cancel();
            timer = null;
        }
    }
}
