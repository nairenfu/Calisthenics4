package com.hylux.calisthenics4.workoutview;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hylux.calisthenics4.Debug;
import com.hylux.calisthenics4.R;
import com.hylux.calisthenics4.objects.Workout;

public class WorkoutOverviewFragment extends Fragment {

    private Workout workout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            workout = getArguments().getParcelable("EXTRA_WORKOUT");
        } else {
            workout = Debug.debugWorkout(); //TODO Change
        }
        assert workout != null;
        Log.d("WORKOUT", workout.toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_workout_overview, container, false);

        TextView nameView = rootView.findViewById(R.id.nameView);
        nameView.setText(workout.getName());

        TextView authorView = rootView.findViewById(R.id.authorView);
        authorView.setText(workout.getAuthorId());

        FrameLayout equipmentsContainer = rootView.findViewById(R.id.equipmentsContainer);
        for (int i : workout.getEquipments()) {
            TextView equipment = new TextView(getContext());
            equipment.setText(String.valueOf(i));
            equipmentsContainer.addView(equipment);
        }

        TextView briefView = rootView.findViewById(R.id.briefView);
        briefView.setText(workout.getBrief());


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static WorkoutOverviewFragment newInstance(@NonNull Workout workout) {

        Bundle args = new Bundle();
        args.putParcelable("EXTRA_WORKOUT", workout);

        WorkoutOverviewFragment fragment = new WorkoutOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }
}