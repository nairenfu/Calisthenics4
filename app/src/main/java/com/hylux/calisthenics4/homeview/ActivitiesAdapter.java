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

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivitiesViewHolder> {

    private ArrayList<Workout> activities;

    public ActivitiesAdapter(ArrayList<Workout> activities) {
        this.activities = activities;
    }

    class ActivitiesViewHolder extends RecyclerView.ViewHolder {

        TextView nameView, startTimeView;

        ActivitiesViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.nameView);
            startTimeView = itemView.findViewById(R.id.startTimeView);
        }
    }

    @NonNull
    @Override
    public ActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View activityView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_activity, parent, false);
        return new ActivitiesViewHolder(activityView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitiesViewHolder holder, int position) {
        holder.nameView.setText(activities.get(position).getName());
        holder.startTimeView.setText(String.valueOf(activities.get(position).getStartTime()));
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public ArrayList<Workout> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Workout> activities) {
        this.activities = activities;
        notifyDataSetChanged();
    }

    public void addActivity(Workout activity, int position) {
        //TODO auto sort position
        activities.add(position, activity);
        notifyDataSetChanged();
    }

    //TODO Add multiple activities, sorted by recent
}
