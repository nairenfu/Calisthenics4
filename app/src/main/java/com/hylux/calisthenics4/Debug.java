package com.hylux.calisthenics4;

import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Set;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;
import java.util.Arrays;

public class Debug {

    public static Exercise debugExercise(boolean isNew) {

        String id;
        if (isNew) {
            id = "default";
        } else {
            id = "9V7CfImi2p4NVzOdd1lx";
        }

        ArrayList<Integer> equipments = new ArrayList<>();
        equipments.add(1);

        ArrayList<String> steps = new ArrayList<>();
        steps.add("debugPull");
        steps.add("debugRelax");

        ArrayList<String> images = new ArrayList<>();
        images.add("debugPlaceholder");

        ArrayList<String> progression = new ArrayList<>();

        return new Exercise(id, "debugPullUp", "debugBack", false, steps, images, equipments, progression);
    }

    public static ArrayList<Exercise> debugExercises() {
        ArrayList<Exercise> debugExercises = new ArrayList<>();
        debugExercises.add(debugExercise(false));
        for (int i = 0; i < 5; i++) {
            debugExercises.add(new Exercise("debug" + i));
        }
        return debugExercises;
    }

    public static Set debugSet() {
        return new Set("9V7CfImi2p4NVzOdd1lx", 10);
    }

    private static ArrayList<Set> debugRoutine() {
        ArrayList<Set> debugRoutine = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            debugRoutine.add(new Set("9V7CfImi2p4NVzOdd1lx", i));
            debugRoutine.add(new Set("PXgTd7HydzIJhD8goXj9", 10));
        }
        return debugRoutine;
    }

    public static Workout debugWorkout() {
        Workout debugWorkout = new Workout("debugWorkout", "a debug workout", debugRoutine());
        debugWorkout.setAuthorId("debugAuthor");
        debugWorkout.setEquipments(new ArrayList<>(Arrays.asList(1, 0)));
        debugWorkout.setTargetGroups(new ArrayList<>(Arrays.asList(0, 1)));
        return debugWorkout;
    }
}
