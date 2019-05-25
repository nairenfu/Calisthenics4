package com.hylux.calisthenics4.homeview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Set;

import java.util.ArrayList;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {

    private ArrayList<Set> routine;

    public RoutineAdapter(ArrayList<Set> routine) {
        this.routine = routine;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_basic_set, parent, false);
        return new RoutineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        TextView nameView = holder.itemView.findViewById(R.id.exerciseName);
        TextView repsView = holder.itemView.findViewById(R.id.reps);

        nameView.setText(routine.get(position).getExerciseId());
        repsView.setText(String.valueOf(routine.get(position).getTargetReps()));
    }

    @Override
    public int getItemCount() {
        return routine.size();
    }

    public ArrayList<Set> getRoutine() {
        return routine;
    }

    public void addToRoutine(Set set) {
        routine.add(set);
        notifyDataSetChanged();
    }

    class RoutineViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView nameView, repsView;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            nameView = itemView.findViewById(R.id.exerciseName);
            repsView = itemView.findViewById(R.id.reps);
        }
    }
}
