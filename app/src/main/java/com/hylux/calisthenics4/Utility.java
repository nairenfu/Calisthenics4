package com.hylux.calisthenics4;

import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

public class Utility {

    private static final String ALLOWED_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //TODO Ensure all generated ids are unique (alternatively just use FireBase ids)
    public static String randomId(int length) {
        Random generator = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        char currentChar;
        for (int i = 0; i < length; i++) {
            currentChar = ALLOWED_CHARS.charAt(generator.nextInt(ALLOWED_CHARS.length()));
            stringBuilder.append(currentChar);
        }
        Log.d("RANDOM_ID", "of length " + length + " generated: " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy 'at' HH:mm:ss", Resources.getSystem().getConfiguration().locale);
}
