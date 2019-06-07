package com.hylux.calisthenics4.roomdatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.HashMap;

@Entity(tableName = "meta")
public class MetaWorkout {

    private String workoutId, authorId;
    private boolean starred = false;
    private int votes = 0;

    @Ignore
    public MetaWorkout(String workoutId) {
        this.workoutId = workoutId;
    }

    public MetaWorkout(String workoutId, String authorId, boolean starred, int votes) {
        this.workoutId = workoutId;
        this.authorId = authorId;
        this.starred = starred;
        this.votes = votes;
    }

    @Ignore
    public MetaWorkout(HashMap<String, Object> data) {
        workoutId = (String) data.get("workoutId");
        authorId = (String) data.get("authorId");
        Long votesLong = (long) data.get("votes");
        votes = votesLong.intValue();
    }

    public String getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(String workoutId) {
        this.workoutId = workoutId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @NonNull
    @Override
    public String toString() {
        return "MetaWorkout{" +
                "workoutId='" + workoutId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", starred=" + starred +
                ", votes=" + votes +
                '}';
    }
}
