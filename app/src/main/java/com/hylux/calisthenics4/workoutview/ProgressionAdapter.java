package com.hylux.calisthenics4.workoutview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ProgressionAdapter extends RecyclerView.Adapter<ProgressionAdapter.ProgressionViewHolder> {

    private final Context context;

    private ArrayList<String> progressions;
    private HashMap<String, Exercise> exercises;

    ProgressionAdapter(Context context) {
        this.context = context;
        progressions = new ArrayList<>();
        exercises = new HashMap<>();
    }

    ProgressionAdapter(ArrayList<String> progressions, HashMap<String, Exercise> exercises, Context context) {
        this.progressions = progressions;
        this.exercises = exercises;
        this.context = context;

        Log.d("PROG_ADAP_PROG", progressions.toString());
        Log.d("PROG_ADAP_EX", exercises.toString());
    }

    @NonNull
    @Override
    public ProgressionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View progressionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_progression_selection, parent, false);
        return new ProgressionViewHolder(progressionView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProgressionViewHolder holder, int position) {
        String currentExerciseId = progressions.get(position);
        ArrayList<String> progressionIds = Objects.requireNonNull(exercises.get(currentExerciseId)).getProgression(); // Will not be null
        Log.d("PROG_ADAP_IDS", progressionIds.toString());
        ArrayList<String> progressionList = new ArrayList<>();
        for (String id : progressionIds) {
            Log.d("PROG_ADAP_ID", id);
            progressionList.add(Objects.requireNonNull(exercises.get(id)).getName());
            Log.d("PROG_ADAP_LIST", progressionList.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, progressionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.progressionSpinner.setAdapter(adapter);
//        holder.progressionSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) context);
        holder.progressionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((OnLevelSelectedListener) context).onLevelSelected(holder.getAdapterPosition(), position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return progressions.size();
    }

    public void setData(ArrayList<String> progressions, HashMap<String, Exercise> exercises) {
        this.progressions = progressions;
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    class ProgressionViewHolder extends RecyclerView.ViewHolder {

        private Spinner progressionSpinner;

        ProgressionViewHolder(@NonNull View itemView) {
            super(itemView);
            progressionSpinner = itemView.findViewById(R.id.progressionSpinner);
        }
    }

    public interface OnLevelSelectedListener {
        void onLevelSelected(int parentPosition, int position);
    }
}
