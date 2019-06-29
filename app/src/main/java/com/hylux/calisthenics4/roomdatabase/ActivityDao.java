package com.hylux.calisthenics4.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hylux.calisthenics4.objects.Exercise;
import com.hylux.calisthenics4.objects.Workout;

import java.util.List;

@Dao
public interface ActivityDao {

    @Query("SELECT * FROM activities")
    LiveData<List<Workout>> getAll();

    @Query("SELECT * FROM activities ORDER BY startTime DESC LIMIT :n")
    List<Workout> getRecentActivities(int n);

    @Insert
    void insert(Workout activity);

    @Query("SELECT * FROM exercises WHERE id=:id LIMIT 1")
    Exercise getExerciseById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //TODO Have a better replace strategy
    void insertExercises(Exercise... exercises);
}
