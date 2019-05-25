package com.hylux.calisthenics4.homeview;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hylux.calisthenics4.createworkoutview.CreateWorkoutListener;
import com.hylux.calisthenics4.objects.Exercise;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private CreateWorkoutListener listener;

    private ArrayList<Exercise> exercises;

    public ExerciseAdapter(ArrayList<Exercise> exercises, CreateWorkoutListener listener) {
        this.exercises = exercises;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExerciseViewHolder(new TextView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.exerciseName.setText(exercises.get(position).getName());
        holder.exerciseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EXERCISE_ADAPTER", exercises.get(index).getName());
                listener.onExerciseSelected(exercises.get(index));
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName;

        ExerciseViewHolder(@NonNull TextView itemView) {
            super(itemView);
            exerciseName = itemView;
        }
    }
}
