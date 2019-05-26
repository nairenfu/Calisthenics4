package com.hylux.calisthenics4.objects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

@Entity(tableName = "exercises")
public class Exercise implements Parcelable {

    @Ignore
    public static final ArrayList<String> EQUIPMENT = new ArrayList<>(Arrays.asList("NOTHING", "PULL_UP_BAR"));
    @Ignore
    public static final ArrayList<String> TARGET_GROUP = new ArrayList<>(Arrays.asList("NONE", "UPPER_BODY", "LOWER_BODY"));

    @PrimaryKey
    @NonNull
    private String id;
    private String name, aim;
    private boolean progressive;
    private ArrayList<String> steps, images, progression;
    //TODO need to think of a good way to store this array list, so that can easily search by equipments/target groups
    //TODO otherwise for all such queries to be made through Firebase?
    private ArrayList<Integer> equipments; //TODO Use IntDef

    @Ignore
    public Exercise(String name) {
        this.id = "default";
        this.name = name;
        this.progressive = false;
        this.steps = new ArrayList<>();
        this.images = new ArrayList<>();
        this.equipments = new ArrayList<>();
        this.progression = new ArrayList<>();
    }

    public Exercise(@NonNull String id, String name, String aim, boolean progressive, ArrayList<String> steps, ArrayList<String> images, ArrayList<Integer> equipments, ArrayList<String> progression) {
        this.id = id;
        this.name = name;
        this.aim = aim;
        this.progressive = progressive;
        this.steps = steps;
        this.images = images;
        this.equipments = equipments;
        this.progression = progression;
    }

    @Ignore
    @SuppressWarnings("unchecked") // Clearly defined data types
    public Exercise(HashMap<String, Object> data) {
        id = (String) Objects.requireNonNull(data.get("id"));
        name = (String) data.get("name");
        aim = (String) data.get("aim");
        progressive = (boolean) data.get("progressive");
        steps = (ArrayList<String>) data.get("steps");
        images = new ArrayList<>();
        equipments = (ArrayList<Integer>) data.get("equipments");
        ArrayList<String> progressionTemp = (ArrayList<String>) data.get("progression");
        progression = new ArrayList<>();
        for (String id : Objects.requireNonNull(progressionTemp)) {
            progression.add(id.replace(" ", ""));
        }
    }

    @Ignore
    @SuppressWarnings("unchecked") // Clearly defined data types
    public Exercise(Parcel in) {
        id = Objects.requireNonNull(in.readString());
        name = in.readString();
        aim = in.readString();
        progressive = in.readInt() != 0;
        steps = new ArrayList<>();
        in.readStringList(steps);
        images = new ArrayList<>();
        in.readStringList(images);
        equipments = in.readArrayList(Integer.class.getClassLoader());
        ArrayList<String> progressionTemp = new ArrayList<>();
        in.readStringList(progressionTemp);
        progression = new ArrayList<>();
        for (String id : progressionTemp) {
            progression.add(id.replace(" ", ""));
        }
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

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public boolean isProgressive() {
        return progressive;
    }

    public void setProgressive(boolean progressive) {
        this.progressive = progressive;
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

    public ArrayList<String> getProgression() {
        return progression;
    }

    public void setProgression(ArrayList<String> progression) {
        this.progression = progression;
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
        dest.writeInt(progressive ? 1 : 0);
        dest.writeStringList(steps);
        dest.writeStringList(images);
        dest.writeList(equipments);
        dest.writeStringList(progression);
    }

    @NonNull
    @Override
    public String toString() {
        return "Exercise{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", aim='" + aim + '\'' +
                ", progressive=" + progressive +
                ", steps=" + steps +
                ", images=" + images +
                ", equipments=" + equipments +
                ", progression=" + progression +
                '}';
    }
}
