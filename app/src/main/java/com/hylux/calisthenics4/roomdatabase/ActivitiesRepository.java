package com.hylux.calisthenics4.roomdatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;
import java.util.List;

class ActivitiesRepository {

    private ActivityDao activityDao;
    private LiveData<List<Workout>> allActivities;

    ActivitiesRepository(Application application) {
        ActivitiesDatabase activitiesDatabase = ActivitiesDatabase.getDatabase(application);
        activityDao = activitiesDatabase.activityDao();
//        allActivities = activityDao.getAll();
    }

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
            onTaskCompletedListener.onGetRecentActivities(activitiesArray);
        }
    }

    LiveData<List<Workout>> getAllActivities() {
        return allActivities;
    }
}
