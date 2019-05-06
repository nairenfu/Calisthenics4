package com.hylux.calisthenics4;

import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Set;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;
import java.util.Arrays;

public class Debug {

    public static Exercise debugExercise() {
        ArrayList<Integer> equipments = new ArrayList<>();
        equipments.add(1);

        ArrayList<String> steps = new ArrayList<>();
        steps.add("debugPull");
        steps.add("debugRelax");

        ArrayList<String> images = new ArrayList<>();
        images.add("debugPlaceholder");

        ArrayList<Integer> targetGroups = new ArrayList<>();
        targetGroups.add(0);

        return new Exercise("9V7CfImi2p4NVzOdd1lx", "debugPullUp", "debugBack", steps, images, equipments, targetGroups);
    }

    public static ArrayList<Exercise> debugExercises() {
        ArrayList<Exercise> debugExercises = new ArrayList<>();
        debugExercises.add(debugExercise());
        for (int i = 0; i < 5; i++) {
            debugExercises.add(new Exercise("debug" + i));
        }
        return debugExercises;
    }

    public static Set debugSet() {
        return new Set("9V7CfImi2p4NVzOdd1lx", 10);
    }

    public static ArrayList<Set> debugRoutine() {
        ArrayList<Set> debugRoutine = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            debugRoutine.add(new Set("9V7CfImi2p4NVzOdd1lx", i));
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
