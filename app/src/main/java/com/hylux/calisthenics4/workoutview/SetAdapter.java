package com.hylux.calisthenics4.workoutview;

import android.support.annotation.NonNull;
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

    private ArrayList<Set> routine;
    private HashMap<String, String> exerciseNamesMap;

    static class SetViewHolder extends RecyclerView.ViewHolder {

        private TextView exerciseNameView, repsView;

        SetViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameView = itemView.findViewById(R.id.exerciseName);
            repsView = itemView.findViewById(R.id.reps);
        }

        void setExerciseName(String exerciseName) {
            exerciseNameView.setText(exerciseName);
        }

        void setReps(int reps) {
            repsView.setText(String.valueOf(reps));
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
}

    @Override
    public int getItemCount() {
        return routine.size();
    }
}
