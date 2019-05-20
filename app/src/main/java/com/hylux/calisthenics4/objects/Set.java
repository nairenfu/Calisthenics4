package com.hylux.calisthenics4.objects;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Set implements Parcelable {

    private String exerciseId;
    private int targetReps, actualReps;

    public Set(String exerciseId, int targetReps) {
        this.exerciseId = exerciseId;
        this.targetReps = targetReps;
        this.actualReps = 0;
    }

    protected Set(Parcel in) {
        exerciseId = in.readString();
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

    public int getTargetReps() {
        return targetReps;
    }

    public void setTargetReps(int targetReps) {
        this.targetReps = targetReps;
    }

    public int getActualReps() {
        return actualReps;
    }

    public void setActualReps(int actualReps) {
        this.actualReps = actualReps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(exerciseId);
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
                '}';
    }
}
