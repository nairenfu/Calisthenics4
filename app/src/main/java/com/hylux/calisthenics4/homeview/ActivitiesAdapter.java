package com.hylux.calisthenics4.homeview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.Utility;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;
import java.util.Date;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivitiesViewHolder> {

    private ArrayList<Workout> activities;

    ActivitiesAdapter(ArrayList<Workout> activities) {
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
    public void onBindViewHolder(@NonNull final ActivitiesViewHolder holder, int position) {
        holder.nameView.setText(activities.get(position).getName());
        Date date = new Date(activities.get(position).getStartTime());
        holder.startTimeView.setText(Utility.simpleDateFormat.format(date));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RECENT_SELECTED", String.valueOf(activities.get(holder.getAdapterPosition())));
            }
        });
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
        //TODO auto sort position (do not need to declare position explicitly)
        activities.add(position, activity);
        notifyDataSetChanged();
    }

    //TODO Add multiple activities, sorted by recent
}
