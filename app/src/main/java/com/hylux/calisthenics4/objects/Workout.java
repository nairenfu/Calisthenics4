package com.hylux.calisthenics4.objects;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.hylux.calisthenics4.Utility;

import java.util.ArrayList;

public class Workout implements Parcelable {

    private String id, name, authorId, brief;
    private ArrayList<Set> routine;
    private ArrayList<Integer> equipments, targetGroups;
    private Long startTime, endTime;

    public Workout(String name, String brief) {
        this.id = Utility.randomId(15);

        this.name = name;
        this.brief = brief;

        routine = new ArrayList<>();
        equipments = new ArrayList<>();
        targetGroups = new ArrayList<>();
    }

    public Workout(String name, String brief, ArrayList<Set> routine) {
        this.name = name;
        this.brief = brief;
        this.routine = routine;

        equipments = new ArrayList<>();
        targetGroups = new ArrayList<>();

        //TODO Automatically add equipments and targetGroups. Need to sort out how to have a copy of all exercises
//        for (Set set : routine) {
//            String exerciseId = set.getExerciseId();
//        }
    }

    @SuppressWarnings("unchecked") //Clearly defined data types
    public Workout(Parcel in) {
        id = in.readString();
        name = in.readString();
        authorId = in.readString();
        brief = in.readString();
        routine = new ArrayList<>();
        in.readTypedList(routine, Set.CREATOR);
        equipments = in.readArrayList(Integer.class.getClassLoader()); 
        targetGroups = in.readArrayList(Integer.class.getClassLoader());
        startTime = in.readLong();
        endTime = in.readLong();
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public ArrayList<Set> getRoutine() {
        return routine;
    }

    public void setRoutine(ArrayList<Set> routine) {
        this.routine = routine;
    }

    public void setActualReps(int actualReps, int position) {
        routine.get(position).setActualReps(actualReps);
    }

    public ArrayList<Integer> getEquipments() {
        return equipments;
    }

    public void setEquipments(ArrayList<Integer> equipments) {
        this.equipments = equipments;
    }

    public ArrayList<Integer> getTargetGroups() {
        return targetGroups;
    }

    public void setTargetGroups(ArrayList<Integer> targetGroups) {
        this.targetGroups = targetGroups;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(authorId);
        dest.writeString(brief);
        dest.writeTypedList(routine);
        dest.writeList(equipments);
        dest.writeList(targetGroups);
        dest.writeValue(startTime);
        dest.writeValue(endTime);
    }

    @NonNull
    @Override
    public String toString() {
        return "Workout{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", authorId='" + authorId + '\'' +
                ", brief='" + brief + '\'' +
                ", routine=" + routine +
                ", equipments=" + equipments +
                ", targetGroups=" + targetGroups +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
