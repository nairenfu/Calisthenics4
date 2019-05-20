package com.hylux.calisthenics4.workoutview;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class SwipeViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;

    SwipeViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);

        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
