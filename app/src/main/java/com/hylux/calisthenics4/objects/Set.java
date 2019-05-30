package com.hylux.calisthenics4.objects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class Set implements Parcelable {

    private static final int DEFAULT_REPS = 20;

    public static final int REPS = 0, TIME = 1;

    private String exerciseId;
    private int targetReps, actualReps, type;

    public Set(String exerciseId) {
        this.exerciseId = exerciseId;
        this.type = REPS;
        this.targetReps = DEFAULT_REPS;
        this.actualReps = 0;
    }

    public Set(String exerciseId, int targetReps) {
        this.exerciseId = exerciseId;
        this.type = REPS;
        this.targetReps = targetReps;
        this.actualReps = 0;
    }

    public Set(String exerciseId, int type, int targetReps) {
        this.exerciseId = exerciseId;
        this.type = type;
        this.targetReps = targetReps;
        this.actualReps = 0;
    }

    public Set(HashMap<String, Object> data) {
        exerciseId = (String) data.get("exerciseId");
        Long typeLong = (long) data.get("type");
        type = typeLong.intValue();
        Long targetLong = (long) data.get("targetReps");
        targetReps = targetLong.intValue();
        Long actualLong = (long) data.get("actualReps");
        actualReps = actualLong.intValue();
    }

    protected Set(Parcel in) {
        exerciseId = in.readString();
        type = in.readInt();
        targetReps = in.readInt();
        actualReps = in.readInt();
    }

    public static final Creator<Set> CREATOR = new Creator<Set>() {
        @Override
        public Set createFromParcel(Parcel in) {
            return new Set(in);
        }

        @Override
        public Set[] newArray(int size) {
            return new Set[size];
        }
    };

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTargetReps() {
        return targetReps;
    }

    public void setTargetReps(int targetReps) {
        this.targetReps = targetReps;
    }

    public int getActualReps() {
        return actualReps;
    }

    void setActualReps(int actualReps) {
        this.actualReps = actualReps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(exerciseId);
        dest.writeInt(type);
        dest.writeInt(targetReps);
        dest.writeInt(actualReps);
    }

    @NonNull
    @Override
    public String toString() {
        return "Set{" +
                "exerciseId='" + exerciseId + '\'' +
                ", targetReps=" + targetReps +
                ", actualReps=" + actualReps +
                ", type=" + type +
                '}';
    }
}
