

package com.sq.sohel.blooddonor.ui.becomeDonor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sq.sohel.blooddonor.ui.base.BaseFragment;
import com.sq.sohel.blooddonor.ui.base.BasePagerAdapter;
import com.sq.sohel.blooddonor.ui.becomeDonor.becomeHero.HeroFragment;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

public class BecomeDonorPagerAdapter extends BasePagerAdapter {

    private int mTabCount;
    private AppUtils.DonorAddType donorAddType = AppUtils.DonorAddType.AddMySelf;
    private String donorEmail;

    public void setDonorAddType(AppUtils.DonorAddType mDonorAddType) {
        this.donorAddType = mDonorAddType;
    }

    public void setDonorEmail(String donorEmail) {
        this.donorEmail = donorEmail;
    }

    private BaseFragment activeBaseFragment;
    private HeroFragment heroFragment;

    public BecomeDonorPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mTabCount = 0;
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(int count) {
        mTabCount = count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (StringExtension.isNullOrWhiteSpace(this.donorEmail)) {
                    heroFragment = HeroFragment.newInstance(donorAddType);
                } else {
                    heroFragment = HeroFragment.newInstance(donorEmail);
                }

                activeBaseFragment = heroFragment;
                return heroFragment;
            default:
                return null;
        }
    }

    public final BaseFragment getActiveFragment() {
        return activeBaseFragment;
    }
}
