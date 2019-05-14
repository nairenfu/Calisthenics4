package com.hylux.calisthenics4.workoutview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

public class ExpandingRecyclerView extends RecyclerView {

    private boolean allowScroll = true;

    public ExpandingRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ExpandingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isAllowScroll() {
        return allowScroll;
    }

    public void setAllowScroll(boolean allowScroll) {
        this.allowScroll = allowScroll;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        Log.d("onScrolled", String.valueOf(allowScroll));
        if (allowScroll) {
            super.onScrolled(dx, dy);
        }
    }


}
