package com.sq.sohel.blooddonor.ui.main.rating;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.databinding.DialogRateUsBinding;
import com.sq.sohel.blooddonor.ui.base.BaseDialog;
import com.sq.sohel.blooddonor.ui.dialogMessage.DialogView;
import com.sq.sohel.blooddonor.utils.AppUtils;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class RateUsDialog extends BaseDialog implements RateUsCallback {

    private static final String TAG = RateUsDialog.class.getSimpleName();
    @Inject
    RateUsViewModel mRateUsViewModel;


    public static RateUsDialog newInstance() {
        RateUsDialog fragment = new RateUsDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void closeMe() {
        dismissDialog(TAG);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DialogRateUsBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_rate_us, container, false);
        View view = binding.getRoot();

        AndroidSupportInjection.inject(this);

        binding.setViewModel(mRateUsViewModel);

        mRateUsViewModel.setNavigator(this);

        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void openLoginActivity() {
        getBaseActivity().openLoginActivity();
    }
}
