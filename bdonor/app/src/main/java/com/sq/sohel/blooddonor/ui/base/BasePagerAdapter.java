package com.sq.sohel.blooddonor.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public abstract class BasePagerAdapter extends FragmentStatePagerAdapter {
    public BasePagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public abstract Fragment getItem(int position);
    public abstract int getCount();
    public abstract BaseFragment getActiveFragment();
}
