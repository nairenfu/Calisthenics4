package com.hylux.calisthenics4.workoutview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class ExpandingRecyclerView extends RecyclerView {

    public ExpandingRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ExpandingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void expandItem(int position) {
        Adapter adapter = getAdapter();
    }
}
