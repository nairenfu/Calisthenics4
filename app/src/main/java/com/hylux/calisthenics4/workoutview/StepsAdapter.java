package com.hylux.calisthenics4.workoutview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hylux.calisthenics4.R;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private ArrayList<String> steps;

    public StepsAdapter(ArrayList<String> steps) {
        this.steps = steps;
        Log.d("STEPS", steps.toString());
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("STEPS_CREATE", "ON_CREATE");
        View stepView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_activity, parent, false);
        return new StepsViewHolder(stepView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        Log.d("STEPS_CREATE", "ON_BIND");
        holder.textView.setText(steps.get(position));
        Log.d("STEPS_TEXT", holder.textView.getText().toString());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.nameView);
        }
    }
}
