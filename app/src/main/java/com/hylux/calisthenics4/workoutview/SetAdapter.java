package com.hylux.calisthenics4.workoutview;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Set;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetViewHolder> {

    private RecyclerView recyclerView;
    private RoutineOverviewFragment.RoutineOverviewFragmentListener actualRepsCallback;

    private int activeItem = -1;

    private ArrayList<Set> routine;
    private HashMap<String, String> exerciseNamesMap;

    static class SetViewHolder extends RecyclerView.ViewHolder {

        private TextView exerciseNameView, repsView;
        private View itemView, expandedView;
        private EditText editActualRepsView;
        private Button nextButton;

        SetViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            exerciseNameView = itemView.findViewById(R.id.exerciseName);
            repsView = itemView.findViewById(R.id.reps);
            expandedView = itemView.findViewById(R.id.expandedView);
            editActualRepsView = expandedView.findViewById(R.id.editActualReps);
            nextButton = expandedView.findViewById(R.id.nextButton);
        }

        void setExerciseName(String exerciseName) {
            exerciseNameView.setText(exerciseName);
        }

        void setReps(int reps) {
            repsView.setText(String.valueOf(reps));
        }

        void setActualReps(int reps) {
            editActualRepsView.setText(String.valueOf(reps));
        }

        int getActualReps() {
            return Integer.valueOf(editActualRepsView.getText().toString());
        }

        Button getNextButton() {
            return nextButton;
        }

        void setNextButton(Button nextButton) {
            this.nextButton = nextButton;
        }

        void setVisible(boolean visible) {
            if (visible) {
                itemView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                expandedView.setVisibility(View.VISIBLE);
            } else {
                itemView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                expandedView.setVisibility(View.GONE);
            }
        }
    }

    SetAdapter(ArrayList<Set> routine, HashMap<String, String> exerciseNamesMap, RoutineOverviewFragment.RoutineOverviewFragmentListener actualRepsCallback) {
        this.routine = routine;
        this.exerciseNamesMap = exerciseNamesMap;
        this.actualRepsCallback = actualRepsCallback;
    }

    @NonNull
    @Override
    public SetAdapter.SetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View setView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_set, parent, false);
        return new SetViewHolder(setView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SetAdapter.SetViewHolder setViewHolder, final int position) {
        //Dude asks me to use getAdapterPosition in onClick but it doesn't work... WHYYYYYYY
        setViewHolder.setExerciseName(exerciseNamesMap.get(routine.get(position).getExerciseId()));
        setViewHolder.setReps(routine.get(position).getTargetReps());
        setViewHolder.setActualReps(routine.get(position).getTargetReps());
        setViewHolder.getNextButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EXPANDED", "CLICK");
                setActiveItem(setViewHolder.getAdapterPosition() + 1);
                actualRepsCallback.setActualReps(setViewHolder.getActualReps(), setViewHolder.getAdapterPosition());
                notifyDataSetChanged();
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                layoutManager.scrollToPositionWithOffset(position + 1, 0);
            }
        });
        if (activeItem == position) {
            setViewHolder.setVisible(true);
        } else {
            setViewHolder.setVisible(false);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {
        return routine.size();
    }

    public int getActiveItem() {
        return activeItem;
    }

    public void setActiveItem(int activeItem) {
        this.activeItem = activeItem;
    }
}
