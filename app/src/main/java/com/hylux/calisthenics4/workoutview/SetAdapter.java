package com.hylux.calisthenics4.workoutview;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Set;

import java.util.ArrayList;
import java.util.HashMap;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetViewHolder> {

    private NextWorkoutCallback listener;

    private int activeItem = -1;

    private ArrayList<Set> routine;
    private HashMap<String, String> exerciseNamesMap;

    private CountDownTimer timer = null;

    static class SetViewHolder extends RecyclerView.ViewHolder {

        private TextView exerciseNameView, repsView, timeView;
        private View itemView, expandedView;
        private EditText editActualRepsView;

        SetViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            exerciseNameView = itemView.findViewById(R.id.exerciseName);
            repsView = itemView.findViewById(R.id.reps);
            expandedView = itemView.findViewById(R.id.expandedView);
            editActualRepsView = expandedView.findViewById(R.id.editActualReps);
            timeView = expandedView.findViewById(R.id.timeView);
        }

        void setExerciseName(String exerciseName) {
            exerciseNameView.setText(exerciseName);
        }

        void setReps(int reps) {
            repsView.setText(String.valueOf(reps));
        }

        void setActualReps(int reps) {
            editActualRepsView.setText(String.valueOf(reps));
        }

        int getActualReps() {
            return Integer.valueOf(editActualRepsView.getText().toString());
        }

        void setTimeView(String time) {
            timeView.setText(time);
        }

        void setVisible(boolean visible) {
            if (visible) {
                itemView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                expandedView.setVisibility(View.VISIBLE);
            } else {
                itemView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                expandedView.setVisibility(View.GONE);
            }
        }
    }

    SetAdapter(ArrayList<Set> routine, HashMap<String, String> exerciseNamesMap, NextWorkoutCallback listener) {
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
        String setId = routine.get(position).getExerciseId();
        setViewHolder.setExerciseName(exerciseNamesMap.get(setId));

        int targetReps = routine.get(position).getTargetReps();
        setViewHolder.setReps(targetReps);
        setViewHolder.setActualReps(targetReps);

        if (activeItem == position) {
            setViewHolder.setVisible(true);
            if (setId.equals("PXgTd7HydzIJhD8goXj9")) { //if setId is rest
                timer = new CountDownTimer((long) targetReps * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        setViewHolder.setTimeView((int) millisUntilFinished/1000 + "s");
                    }

                    @Override
                    public void onFinish() {
                        listener.next();
                    }
                };
                timer.start();
            }
        } else {
            setViewHolder.setVisible(false);
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
