package com.hylux.calisthenics4.roomdatabase;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hylux.calisthenics4.objects.Set;

import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static Set setFromString(String value) {
        return new Gson().fromJson(value, new TypeToken<Set>(){}.getType());
    }

    @TypeConverter
    public static String setToString(Set set) {
        return new Gson().toJson(set);
    }

    @TypeConverter
    public static ArrayList<Set> routineFromString(String value) {
        return new Gson().fromJson(value, new TypeToken<ArrayList<Set>>(){}.getType());
    }

    @TypeConverter
    public static String routineToString(ArrayList<Set> routine) {
        return new Gson().toJson(routine);
    }

    @TypeConverter
    public static ArrayList<String> stringArrayFromString(String value) {
        return new Gson().fromJson(value, new TypeToken<ArrayList<String>>(){}.getType());
    }

    @TypeConverter
    public static String stringArrayToString(ArrayList<String> stringArray) {
        return new Gson().toJson(stringArray);
    }

    @TypeConverter
    public static ArrayList<Integer> integerArrayFromString(String value) {
        return new Gson().fromJson(value, new TypeToken<ArrayList<Integer>>(){}.getType());
    }

    @TypeConverter
    public static String integerArrayToString(ArrayList<Integer> integerArray) {
        return new Gson().toJson(integerArray);
    }
}
