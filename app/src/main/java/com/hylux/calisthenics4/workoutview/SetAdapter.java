package com.hylux.calisthenics4.workoutview;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Set;

import java.util.ArrayList;
import java.util.HashMap;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetViewHolder> {

    private int activeItem = -1;

    private ArrayList<Set> routine;
    private HashMap<String, String> exerciseNamesMap;

    static class SetViewHolder extends RecyclerView.ViewHolder {

        private TextView exerciseNameView, repsView;
        private View expandedView;

        SetViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameView = itemView.findViewById(R.id.exerciseName);
            repsView = itemView.findViewById(R.id.reps);
            expandedView = itemView.findViewById(R.id.expandedView);
        }

        void setExerciseName(String exerciseName) {
            exerciseNameView.setText(exerciseName);
        }

        void setReps(int reps) {
            repsView.setText(String.valueOf(reps));
        }

        void setVisible(boolean visible) {
            if (visible) {
                expandedView.setVisibility(View.VISIBLE);
            } else {
                expandedView.setVisibility(View.GONE);
            }
        }
    }

    SetAdapter(ArrayList<Set> routine, HashMap<String, String> exerciseNamesMap) {
        this.routine = routine;
        this.exerciseNamesMap = exerciseNamesMap;
    }

    @NonNull
    @Override
    public SetAdapter.SetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View setView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_set, parent, false);
        return new SetViewHolder(setView);
    }

    @Override
    public void onBindViewHolder(@NonNull SetAdapter.SetViewHolder setViewHolder, int position) {
        setViewHolder.setExerciseName(exerciseNamesMap.get(routine.get(position).getExerciseId()));
        setViewHolder.setReps(routine.get(position).getTargetReps());
        if (activeItem == position) {
            setViewHolder.setVisible(true);
        } else {
            setViewHolder.setVisible(false);
        }
}

    @Override
    public int getItemCount() {
        return routine.size();
    }

    public int getActiveItem() {
        return activeItem;
    }

    public void setActiveItem(int activeItem) {
        this.activeItem = activeItem;
    }
}
