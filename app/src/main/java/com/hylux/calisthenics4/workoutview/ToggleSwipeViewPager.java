package com.hylux.calisthenics4.workoutview;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ToggleSwipeViewPager extends ViewPager {

    private boolean canSwipe = true;

    public ToggleSwipeViewPager(@NonNull Context context) {
        super(context);
    }

    public ToggleSwipeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!canSwipe) {
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (!canSwipe) {
            return false;
        }

        return super.onTouchEvent(ev);
    }

    public boolean isCanSwipe() {
        return canSwipe;
    }

    public void setCanSwipe(boolean canSwipe) {
        this.canSwipe = canSwipe;
    }
}
