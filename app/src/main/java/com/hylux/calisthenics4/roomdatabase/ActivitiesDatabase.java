package com.hylux.calisthenics4.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Workout;

@Database(entities = {Workout.class, Exercise.class}, version = 2)
@TypeConverters(Converters.class)
public abstract class ActivitiesDatabase extends RoomDatabase {

    public abstract ActivityDao activityDao();

    private static volatile ActivitiesDatabase INSTANCE;

    public static ActivitiesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ActivitiesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), ActivitiesDatabase.class, "activities_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
