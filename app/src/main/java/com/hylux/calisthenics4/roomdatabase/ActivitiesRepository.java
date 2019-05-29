package com.hylux.calisthenics4.roomdatabase;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hylux.calisthenics4.Debug;
import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Set;
import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class ActivitiesRepository {

    public static final int ROOM = 0, FIRE = 1;

    private ActivityDao activityDao;
    private LiveData<List<Workout>> allActivities;

    private FirebaseFirestore fireStore;

    ActivitiesRepository(Application application) {
        ActivitiesDatabase activitiesDatabase = ActivitiesDatabase.getDatabase(application);
        activityDao = activitiesDatabase.activityDao();
//        allActivities = activityDao.getAll();

        // Firebase
        fireStore = FirebaseFirestore.getInstance();
    }

    // Room
    //Insert activity
    void insert(Workout activity) {
        new InsertAsyncTask(activityDao).execute(activity);
    }

    private static class InsertAsyncTask extends AsyncTask<Workout, Void, Void> {
        private ActivityDao activityDao;

        InsertAsyncTask(ActivityDao dao) {
            activityDao = dao;
        }

        @Override
        protected Void doInBackground(final Workout... params) {
            activityDao.insert(params[0]);
            return null;
        }

        //DEBUGGING ONLY
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("INSERT", "DONE");
        }
    }

    // Add all exercises
    void addAllExercises(ArrayList<Exercise> exercises) {
        Exercise[] exercisesArray = exercises.toArray(new Exercise[0]);
        new AddAllExercisesAsync(activityDao).execute(exercisesArray);
        Log.d("ADD_ALL_EX", Arrays.toString(exercisesArray));
    }

    private static class AddAllExercisesAsync extends AsyncTask<Exercise, Void, Void> {
        private  ActivityDao activityDao;

        AddAllExercisesAsync(ActivityDao dao) {
            activityDao = dao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            activityDao.insertExercises(exercises);
            return null;
        }
    }

    // Get exercise by id
    void getExerciseById(String exerciseId, final OnTaskCompletedListener listener, int flag) {

        if (flag == 0) {
            new GetExerciseByIdAsyncTask(activityDao, this, listener).execute(exerciseId);
        }

        if (flag == 1) {
            DocumentReference exerciseDoc = fireStore.collection("exercises").document(exerciseId);
            exerciseDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        assert document != null;
                        if (document.exists()) {
                            Log.d("FIRE_STORE", Objects.requireNonNull(document.getData()).toString());
                            Log.d("FIRE_STORE", (String) document.get("name"));
                            Exercise exercise = new Exercise((HashMap<String, Object>) document.getData());
                            listener.onGetExerciseFromId(exercise);
                        }
                    }
                }
            });
        }
    }

    // TODO get Workout from ROOM

    private static class GetExerciseByIdAsyncTask extends AsyncTask<String, Void, Exercise> {

        private ActivityDao activityDao;
        private ActivitiesRepository activitiesRepository;
        private OnTaskCompletedListener onTaskCompletedListener;

        private String id;

        GetExerciseByIdAsyncTask(ActivityDao dao, ActivitiesRepository repository, OnTaskCompletedListener listener) {
            activityDao = dao;
            activitiesRepository = repository;
            onTaskCompletedListener = listener;
        }

        @Override
        protected Exercise doInBackground(String... strings) {
            id = strings[0];
            return activityDao.getExerciseById(id);
        }

        @Override
        protected void onPostExecute(Exercise exercise) {
            super.onPostExecute(exercise);
            if (exercise != null) {
                Log.d("GET_FROM_ROOM", exercise.toString());
                onTaskCompletedListener.onGetExerciseFromId(exercise);
            } else {
                Log.d("GET_FROM_FIRE", "TRUE");
                activitiesRepository.getExerciseById(id, onTaskCompletedListener, FIRE);
            }
        }
    }

    //Get n recent activities
    void getRecentActivities(int n, OnTaskCompletedListener listener) {
        new GetRecentActivitiesAsyncTask(activityDao, listener).execute(n);
    }

    private static class GetRecentActivitiesAsyncTask extends AsyncTask<Integer, Void, List<Workout>> {
        private ActivityDao activityDao;
        private OnTaskCompletedListener onTaskCompletedListener;

        GetRecentActivitiesAsyncTask(ActivityDao dao, OnTaskCompletedListener listener) {
            activityDao = dao;
            onTaskCompletedListener = listener;
        }

        @Override
        protected List<Workout> doInBackground(Integer... integers) {
            return activityDao.getRecentActivities(integers[0]);
        }

        @Override
        protected void onPostExecute(List<Workout> activities) {
            super.onPostExecute(activities);
            ArrayList<Workout> activitiesArray = (ArrayList<Workout>) activities;
            Log.d("GET_RECENT_ACT", activities.toString());
            onTaskCompletedListener.onGetRecentActivities(activitiesArray);
        }
    }

    // Get all activities
    LiveData<List<Workout>> getAllActivities() {
        return allActivities;
    }



    // Firebase Firestore
    void addExercise(Exercise exercise, boolean overwrite) {
        //TODO Check if item exists (maybe can do by initializing id to 0 first)
        //TODO Do a further check if ID exists
        final Exercise addedExercise = exercise;
        if (addedExercise.getId().contains("default")) {
            fireStore.collection("exercises")
                    .add(addedExercise)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String exerciseId = documentReference.getId();
                            addedExercise.setId(exerciseId);
                            fireStore.collection("exercises")
                                    .document(exerciseId)
                                    .set(addedExercise)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("FIRE_STORE", "DocumentSnapshot successfully written!");
                                        }
                                    });
                        }
                    });
        } else {
            if (overwrite) {
                fireStore.collection("exercises")
                        .document(exercise.getId())
                        .set(addedExercise)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("FIRE_STORE", "DocumentSnapshot successfully written!");
                            }
                        });
            }
        }
    }

    void addWorkout(Workout workout) {
        final Workout addedWorkout = workout;
        if (addedWorkout.getId().equals("default") || addedWorkout.getId() == null) {
            fireStore.collection("workouts")
                    .add(addedWorkout)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String workoutId = documentReference.getId();
                            addedWorkout.setId(workoutId);
                            fireStore.collection("workouts")
                                    .document(workoutId)
                                    .set(addedWorkout)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("FIRE_STORE", "DocumentSnapshot successfully written!");
                                        }
                                    });
                        }
                    });
        }
    }

    void getWorkoutById(String id, final OnTaskCompletedListener listener) {
        fireStore.collection("workouts")
                .document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Workout workout = new Workout((HashMap<String, Object>) Objects.requireNonNull(documentSnapshot.getData()));
                        Log.d("WORKOUT", workout.toString());
                        listener.onGetWorkoutFromId(workout);
                    }
                });
    }

    void getAllExercisesAsync(final OnTaskCompletedListener listener) {
        final ArrayList<Exercise> exercises = new ArrayList<>();
        fireStore.collection("exercises")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d("EXERCISE", documentSnapshot.toString());
                                exercises.add(new Exercise((HashMap<String, Object>) Objects.requireNonNull(documentSnapshot.getData())));
                            }
                            Log.d("EXERCISES", exercises.toString());
                            listener.onGetAllExercises(exercises);
                        } else {
                            Log.d("Error getting documents", Objects.requireNonNull(task.getException()).toString());
                        }
                    }
                });
    }

    void getAllWorkoutsAsync(final OnTaskCompletedListener listener) {
        final ArrayList<Workout> workouts = new ArrayList<>();
        fireStore.collection("workouts")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            workouts.add(new Workout((HashMap<String, Object>) Objects.requireNonNull(documentSnapshot.getData())));
                        }
                        listener.onGetAllWorkouts(workouts);
                    }
                });
    }
}
