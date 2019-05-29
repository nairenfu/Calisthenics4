package com.hylux.calisthenics4.homeview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutsViewHolder> {

    private ChooseWorkoutFragment.ChoiceListener listener;
    private boolean loading;

    private ArrayList<Workout> workouts;
    //TODO Change to a simpler data structure

    WorkoutsAdapter(ChooseWorkoutFragment.ChoiceListener listener) {
        this.listener = listener;
        workouts = new ArrayList<>();
    }

    public WorkoutsAdapter(ArrayList<Workout> workouts, ChooseWorkoutFragment.ChoiceListener listener) {
        this.workouts = workouts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WorkoutsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_activity, parent, false);
        return new WorkoutsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutsViewHolder holder, final int position) {
        TextView nameView = holder.itemView.findViewById(R.id.nameView);
        nameView.setText(workouts.get(position).getName());
        nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loading) {
                    listener.onStartActivity(workouts.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public boolean isLoading() {
        return loading;
    }

    void setLoading(boolean loading) {
        this.loading = loading;
    }

    public ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
        notifyDataSetChanged();
    }

    class WorkoutsViewHolder extends RecyclerView.ViewHolder {

        WorkoutsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
