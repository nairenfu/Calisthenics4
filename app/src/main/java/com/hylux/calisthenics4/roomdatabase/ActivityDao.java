package com.hylux.calisthenics4.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hylux.calisthenics4.objects.Workout;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ActivityDao {

    @Query("SELECT * FROM activities")
    LiveData<List<Workout>> getAll();

    @Query("SELECT * FROM activities ORDER BY startTime LIMIT :n")
    List<Workout> getRecentActivities(int n);

    @Insert
    void insert(Workout activity);
}
