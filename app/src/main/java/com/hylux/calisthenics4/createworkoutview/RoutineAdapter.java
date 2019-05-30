package com.hylux.calisthenics4.createworkoutview;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Set;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> implements ItemTouchHelperAdapter{

    private ArrayList<Set> routine;
    private HashMap<String, String> exerciseNamesMap;

    RoutineAdapter(ArrayList<Set> routine, HashMap<String, String> exerciseNamesMap) {
        this.routine = routine;
        this.exerciseNamesMap = exerciseNamesMap;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_basic_set, parent, false);
        return new RoutineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RoutineViewHolder holder, int position) {
        TextView nameView = holder.itemView.findViewById(R.id.exerciseName);
        final EditText repsView = holder.itemView.findViewById(R.id.reps);

        nameView.setText(exerciseNamesMap.get(routine.get(position).getExerciseId()));
        repsView.clearFocus();

        // Instantiate Type toggle
        holder.toggleTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.toggleTypeButton.isChecked()) {
                    // Exercise is Time based
                    routine.get(holder.getAdapterPosition()).setType(Set.TIME);
                } else {
                    // Exercise is Reps based
                    routine.get(holder.getAdapterPosition()).setType(Set.REPS);
                }
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        repsView.setText(String.valueOf(routine.get(position).getTargetReps()));
        repsView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                    case EditorInfo.IME_ACTION_NEXT:
                    case EditorInfo.IME_ACTION_PREVIOUS:
                        Log.d("ACTION_ID", String.valueOf(actionId));
                        routine.get(holder.getAdapterPosition()).setTargetReps(Integer.valueOf(repsView.getText().toString()));
                        notifyItemChanged(holder.getAdapterPosition());
                        v.clearFocus();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return routine.size();
    }

    ArrayList<Set> getRoutine() {
        return routine;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(routine, i, i+1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(routine, i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        routine.remove(position);
        notifyItemRemoved(position);
    }

    class RoutineViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView nameView, repsView;
        ToggleButton toggleTypeButton;

        RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            nameView = itemView.findViewById(R.id.exerciseName);
            repsView = itemView.findViewById(R.id.reps);
            toggleTypeButton = itemView.findViewById(R.id.toggleType);
        }
    }
}
