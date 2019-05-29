package com.hylux.calisthenics4.workoutview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

public class RoutineRecyclerLayoutManager extends LinearLayoutManager {

    private boolean scrollEnabled = true;

    RoutineRecyclerLayoutManager(Context context) {
        super(context);
    }

    public RoutineRecyclerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public RoutineRecyclerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean isScrollEnabled() {
        return scrollEnabled;
    }

    void setScrollEnabled(boolean scrollEnabled) {
        this.scrollEnabled = scrollEnabled;
    }

    @Override
    public boolean canScrollVertically() {
        return scrollEnabled && super.canScrollVertically();
    }
}
