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

    public static ArrayList<Exercise> squatProgression() {
        ArrayList<Integer> equipments = new ArrayList<>(Collections.singletonList(0));

        ArrayList<String> progression = new ArrayList<>(Arrays.asList(
                "N3KRTUu6D6aGFotGPBpu",
                "H2LQHcUORfs40nxdPDoY",
                "aD275wPXHDfRecVRVODq",
                "OTI0jlL4FYJVlF5AIHFD",
                "VLHGJfS1hskU2fClfh7p",
                "BGQL73T6y9GQYXZTebjr",
                "Vz6uhv1sKpbVYQeiU8A1"
                ));

        Exercise assistedSquats = new Exercise("Assisted Squats");
        assistedSquats.setId("N3KRTUu6D6aGFotGPBpu");
        assistedSquats.setAim("Level 1 of progressive legs strengthening");
        assistedSquats.setEquipments(equipments);
        assistedSquats.setSteps(new ArrayList<String>(Arrays.asList(
                "Grab something in front of you and use your hands to assist in the squat.",
                "Reduce assistance over time."
        )));
        assistedSquats.setProgressive(true);
        assistedSquats.setProgression(progression);


        Exercise squats = new Exercise("Squats");
        squats.setId("H2LQHcUORfs40nxdPDoY");
        squats.setAim("Level 2 of progressive legs strengthening");
        squats.setEquipments(equipments);
        squats.setSteps(new ArrayList<String>(Arrays.asList(
                "Stand up straight at the top",
                "Go as low as you can, preferably until the hips are below the knees",
                "Dig your big toe and heel into the ground",
                "Keep your knee in-line with your toes",
                "Don't let the knees come inward on either the descend or the ascend; think about pushing the knees out"
        )));
        squats.setProgressive(true);
        squats.setProgression(progression);

        Exercise splitSquats = new Exercise("Split Squats");
        splitSquats.setId("aD275wPXHDfRecVRVODq");
        splitSquats.setAim("Level 3 of progressive legs strengthening");
        splitSquats.setEquipments(equipments);
        splitSquats.setSteps(new ArrayList<String>(Arrays.asList(
                "Stand with one leg forward, other leg back",
                "Only toes of your back leg touches the ground",
                "Lower yourself until both legs are at 90 degrees",
                "Remain in the split stance throughout"
        )));
        splitSquats.setProgressive(true);
        splitSquats.setProgression(progression);

        Exercise bulgarianSplitSquats = new Exercise("Bulgarian Split Squats");
        bulgarianSplitSquats.setId("OTI0jlL4FYJVlF5AIHFD");
        bulgarianSplitSquats.setAim("Level 4 of progressive legs strengthening");
        bulgarianSplitSquats.setEquipments(new ArrayList<Integer>(Collections.singletonList(2)));
        bulgarianSplitSquats.setSteps(new ArrayList<String>(Arrays.asList(
                "Rest your rear leg on a bench",
                "Go as low as you can",
                "You can progress this further by elevating both your legs"
        )));
        bulgarianSplitSquats.setProgressive(true);
        bulgarianSplitSquats.setProgression(progression);

        Exercise shrimpSquats = new Exercise("Shrimp Squats");
        shrimpSquats.setId("VLHGJfS1hskU2fClfh7p");
        shrimpSquats.setAim("Level 5(a) of progressive legs strengthening");
        shrimpSquats.setEquipments(equipments);
        shrimpSquats.setSteps(new ArrayList<String>(Arrays.asList(
                "Raise your rear leg off the ground",
                "Lower yourself until your knees and toes touch the ground",
                "You can progress this further by having only your knees touch the ground"
        )));
        shrimpSquats.setProgressive(true);
        shrimpSquats.setProgression(progression);

        Exercise stepUps = new Exercise("Step-Ups");
        stepUps.setId("BGQL73T6y9GQYXZTebjr");
        stepUps.setAim("Level 5(b) of progressive legs strengthening");
        stepUps.setEquipments(equipments);
        stepUps.setSteps(new ArrayList<String>(Arrays.asList(
                "Putting one leg up on a high object in front of you",
                "Put all of your weight on the front leg and step up to the object",
                "Aim to minimize pushing off with the back leg",
                "To make it harder, increase the height of the object (until your back leg leaves the ground), or hold some weight"
        )));
        stepUps.setProgressive(true);
        stepUps.setProgression(progression);

        Exercise pistolSquats = new Exercise("Pistol Squats");
        pistolSquats.setId("Vz6uhv1sKpbVYQeiU8A1");
        pistolSquats.setAim("Level 5(c) of progressive legs strengthening");
        pistolSquats.setEquipments(equipments);
        pistolSquats.setSteps(new ArrayList<String>(Arrays.asList(
                "Start with one leg elevated, the other in the air",
                "Go as low as possible",
                "Extend your leg in front of you as you lower yourself",
                "Progress this by increasing your range of motion"
        )));
        pistolSquats.setProgressive(true);
        pistolSquats.setProgression(progression);

        return new ArrayList<>(Arrays.asList(assistedSquats, squats, splitSquats, bulgarianSplitSquats, shrimpSquats, stepUps, pistolSquats));
    }
}
