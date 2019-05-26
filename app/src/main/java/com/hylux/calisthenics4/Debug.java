package com.hylux.calisthenics4;

import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Set;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

    public static ArrayList<Exercise> pullUpProgression() {
        ArrayList<Integer> equipments = new ArrayList<>(Collections.singletonList(1));

        Exercise pullUp = new Exercise("Pull-Up");
        pullUp.setAim("Level 4 of progressive back strengthening");
        pullUp.setEquipments(equipments);
        pullUp.setSteps(new ArrayList<>(Arrays.asList(
                "Body slightly hollow with straight legs throughout the whole exercise. Don't cross your legs.",
                "If you cannot get straight legs, it's preferable to keep the feet in front of the body rather than behind.",
                "Arms straight at the bottom. Don't think about anything else, just straight arms. The rest will happen automatically.",
                "Strive for chest to bar at the top. For this the forearms have to deviate from vertical, which may be a bit hard on the elbows, so build up to it slowly.",
                "Keep the neck in a neutral position: avoid craning it to get your chin over the bar",
                "It's natural for your legs to come forward: this keeps your centre of mass under the bar. Just make sure you're not violently swinging them upwards.")));

        Exercise pullUpNegatives = new Exercise("Pull-Up Negatives");
        pullUpNegatives.setId("default1");
        pullUpNegatives.setAim("Level 3 of progressive back strengthening");
        pullUpNegatives.setEquipments(equipments);
        pullUpNegatives.setSteps(new ArrayList<>(Arrays.asList(
                "Jump to the top of the pull-up position, then slowly (as slowly as you can), lower yourself until your arms are straight.",
                "Build up to 10sec negatives!"
        )));

        Exercise archHangs = new Exercise("Arch Hangs");
        archHangs.setId("default2");
        archHangs.setAim("Level 2 of progressive back strengthening");
        archHangs.setEquipments(equipments);
        archHangs.setSteps(new ArrayList<>(Arrays.asList(
                "Bring your chest as close to the bar as possible",
                "Elbows should stay straight",
                "Hold it for time/reps",
                "Progress towards a 90 degree in your shoulder."
        )));

        Exercise scapularPulls = new Exercise("Scapular Pulls");
        scapularPulls.setId("default3");
        scapularPulls.setAim("Level 1 of progressive back strengthening");
        scapularPulls.setEquipments(equipments);
        scapularPulls.setSteps(new ArrayList<String>(Arrays.asList(
                "Bring your chest as close to the bar as possible",
                "Elbows should stay straight",
                "Your back will arch more as you get stronger",
                "These should be performed with a pause at the \"top\". Squeeze your shoulders, hold it for 3-5s then release into a dead hang under control",
                "If you can't pull out of the dead hang at all, consider using bands or your feet to assist you until the gain the necessary strength."
        )));

        return new ArrayList<>(Arrays.asList(scapularPulls, archHangs, pullUpNegatives, pullUp));
    }
}
