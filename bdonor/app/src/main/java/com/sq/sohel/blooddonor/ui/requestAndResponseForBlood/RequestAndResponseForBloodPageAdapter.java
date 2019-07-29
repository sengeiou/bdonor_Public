package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sq.sohel.blooddonor.ui.base.BaseFragment;
import com.sq.sohel.blooddonor.ui.base.BasePagerAdapter;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodFragment;

public class RequestAndResponseForBloodPageAdapter extends BasePagerAdapter {

    private int mTabCount;
    private BaseFragment activeBaseFragment;
    private RequestBloodFragment requestBloodFragment;
    private Boolean showOnlyMyRequest = false;

    public RequestAndResponseForBloodPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        //this.mTabCount = 0;
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    @Override
    public BaseFragment getActiveFragment() {
        return activeBaseFragment;
    }

    public void setCount(int count) {
        mTabCount = count;
    }
    public void setShowOnlyMyRequest(Boolean showOnlyMyRequest) {
        this.showOnlyMyRequest = showOnlyMyRequest;
    }

    @Override
    public Fragment getItem(int position) {
        requestBloodFragment = RequestBloodFragment.newInstance();
        activeBaseFragment = requestBloodFragment;
        requestBloodFragment.setShowOnlyMyRequest(this.showOnlyMyRequest);
        switch (position) {
            case 0:
                requestBloodFragment.setBloodType("A+");
                return requestBloodFragment;
            case 1:
                requestBloodFragment.setBloodType("B+");
                return requestBloodFragment;
            case 2:
                requestBloodFragment.setBloodType("O+");
                return requestBloodFragment;
            case 3:
                requestBloodFragment.setBloodType("AB+");
                return requestBloodFragment;
            case 4:
                requestBloodFragment.setBloodType("A-");
                return requestBloodFragment;
            case 5:
                requestBloodFragment.setBloodType("B-");
                return requestBloodFragment;
            case 6:
                requestBloodFragment.setBloodType("O-");
                return requestBloodFragment;
            case 7:
                requestBloodFragment.setBloodType("AB-");
                return requestBloodFragment;
            default:
                return null;
        }
    }
}
