package com.hylux.calisthenics4.objects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.hylux.calisthenics4.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Exercise implements Parcelable {

    public static final ArrayList<String> EQUIPMENT = new ArrayList<>(Arrays.asList("NOTHING", "PULL_UP_BAR"));
    public static final ArrayList<String> TARGET_GROUP = new ArrayList<>(Arrays.asList("NONE", "UPPER_BODY", "LOWER_BODY"));

    private String name, id, aim;
    private ArrayList<String> steps, images;
    private ArrayList<Integer> equipments, targetGroups; //TODO Use IntDef

    public Exercise() {

    }

    public Exercise(String name) {
        this.id = Utility.randomId(10);
        this.name = name;
        this.steps = new ArrayList<>();
        this.images = new ArrayList<>();
        this.equipments = new ArrayList<>();
        this.targetGroups = new ArrayList<>();
    }

    public Exercise(String id, String name, String aim, ArrayList<String> steps, ArrayList<String> images, ArrayList<Integer> equipments, ArrayList<Integer> targetGroups) {
        this.id = id;
        this.name = name;
        this.aim = aim;
        this.steps = steps;
        this.images = images;
        this.equipments = equipments;
        this.targetGroups = targetGroups;
    }

    @SuppressWarnings("unchecked") // Clearly defined data types
    public Exercise(HashMap<String, Object> data) {
        this.id = (String) data.get("id");
        this.name = (String) data.get("name");
        this.aim = (String) data.get("aim");
        this.steps = (ArrayList<String>) data.get("steps");
        this.images = new ArrayList<>();
        this.equipments = (ArrayList<Integer>) data.get("equipments");
        this.targetGroups = (ArrayList<Integer>) data.get("targetGroups");
    }

    @SuppressWarnings("unchecked") // Clearly defined data types
    public Exercise(Parcel in) {
        id = in.readString();
        name = in.readString();
        aim = in.readString();
        steps = new ArrayList<>();
        in.readStringList(steps);
        images = new ArrayList<>();
        in.readStringList(images);
        equipments = in.readArrayList(Integer.class.getClassLoader());
        targetGroups = in.readArrayList(Integer.class.getClassLoader());
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(aim);
        dest.writeStringList(steps);
        dest.writeStringList(images);
        dest.writeList(equipments);
        dest.writeList(targetGroups);
    }

//      this.id = id;
//        this.name = name;
//        this.aim = aim;
//        this.steps = steps;
//        this.images = images;
//        this.equipments = equipments;
//        this.targetGroups = targetGroups;

    @NonNull
    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", aim='" + aim + '\'' +
                ", steps=" + steps +
                ", images=" + images +
                ", equipments=" + equipments +
                ", targetGroups=" + targetGroups +
                '}';
    }
}
