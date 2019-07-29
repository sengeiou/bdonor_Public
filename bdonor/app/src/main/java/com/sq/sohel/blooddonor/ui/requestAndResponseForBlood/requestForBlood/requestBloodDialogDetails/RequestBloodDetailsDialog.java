package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.requestBloodDialogDetails;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.databinding.DialogRequestBloodBinding;
import com.sq.sohel.blooddonor.ui.base.BaseDialog;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodItemViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class RequestBloodDetailsDialog extends BaseDialog implements RequestBloodDetailsDialogCallback {
    private static final String TAG = RequestBloodDetailsDialog.class.getSimpleName();
    @Inject
    RequestBloodDialogViewModel mRequestBloodDialogViewModel;
    RequestBloodItemViewModel requestBloodItemViewModel;

    public static RequestBloodDetailsDialog newInstance() {
        RequestBloodDetailsDialog fragment = new RequestBloodDetailsDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null) {
            requestBloodItemViewModel = (RequestBloodItemViewModel) savedInstanceState.getSerializable("requestBloodItemViewModel");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("requestBloodItemViewModel", requestBloodItemViewModel);
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DialogRequestBloodBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_request_blood, container, false);
        View view = binding.getRoot();
        AndroidSupportInjection.inject(this);
        binding.setViewModel(mRequestBloodDialogViewModel);
        mRequestBloodDialogViewModel.setNavigator(this);
        mRequestBloodDialogViewModel.setRequestBloodItemViewModel(requestBloodItemViewModel);
        return view;
    }

    public void show(FragmentManager fragmentManager, RequestBloodItemViewModel requestBloodItemViewModel) {
        this.requestBloodItemViewModel = requestBloodItemViewModel;
        super.show(fragmentManager, TAG);
    }

    @Override
    public void openLoginActivity() {
        getBaseActivity().openLoginActivity();
    }
}

