package com.sq.sohel.blooddonor.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.sq.sohel.blooddonor.data.model.others.NameValuePair;
import com.sq.sohel.blooddonor.ui.base.BaseFragment;
import com.sq.sohel.blooddonor.ui.base.BasePagerAdapter;
import com.sq.sohel.blooddonor.ui.main.donor.DonorFragment;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodFragment;
import com.sq.sohel.blooddonor.utils.AppUtils;

//import ValidationModel;

import java.util.List;


public class MainPagerAdapter extends BasePagerAdapter {

    private int mTabCount;
    private DonorFragment donorFragment = null;
    //private RequestBloodFragment requestBloodFragment = null;
    private BaseFragment activeBaseFragment = null;

    public MainPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mTabCount = 1;
    }
    public MainPagerAdapter(FragmentManager fragmentManager, DonorFragment donorFragment) {
        super(fragmentManager);
        this.mTabCount = 1;
        this.donorFragment = donorFragment;
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

    public void FilterData(int count, String query) {
        donorFragment.FilterData(query);
    }

    public void FilterData(int count, List<NameValuePair> query, AppUtils.SearchType searchType) {
        donorFragment.FilterData(query, searchType);
    }


    @Override
    public Fragment getItem(int position) {
        donorFragment = DonorFragment.newInstance();
        activeBaseFragment = donorFragment;
        return donorFragment;
//        switch (position) {
//            case 0:
//                donorFragment = DonorFragment.newInstance();
//                activeBaseFragment = donorFragment;
//                return donorFragment;
//            case 1:
////                requestBloodFragment = RequestBloodFragment.newInstance();
////                activeBaseFragment = requestBloodFragment;
////                return requestBloodFragment;
//            default:
//                return null;
//        }
    }
}