package com.hylux.calisthenics4.createworkoutview;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hylux.calisthenics4.objects.Exercise;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private CreateWorkoutListener listener;

    private ArrayList<Exercise> mergedProgressionExercises;

    ExerciseAdapter(ArrayList<Exercise> exercises, CreateWorkoutListener listener) {
        this.listener = listener;

        mergedProgressionExercises = new ArrayList<>();
        for (Exercise exercise: exercises) {
            if (exercise.isProgressive()) {
                if (exercise.getId().equals(exercise.getProgression().get(exercise.getProgression().size() - 1))) {
                    mergedProgressionExercises.add(exercise);
                }
            } else {
                mergedProgressionExercises.add(exercise);
            }
        }
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExerciseViewHolder(new TextView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        String name = mergedProgressionExercises.get(position).getName();
        if (mergedProgressionExercises.get(position).isProgressive()) {
            name += " (Progression)";
        }
        holder.exerciseName.setText(name);
        holder.exerciseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EXERCISE_ADAPTER", mergedProgressionExercises.get(index).getName());
                listener.onExerciseSelected(mergedProgressionExercises.get(index));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mergedProgressionExercises.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName;

        ExerciseViewHolder(@NonNull TextView itemView) {
            super(itemView);
            exerciseName = itemView;
            exerciseName.setPadding(16, 8, 8, 16);
        }
    }
}
