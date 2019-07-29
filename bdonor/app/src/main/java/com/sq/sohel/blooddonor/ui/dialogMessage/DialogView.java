package com.sq.sohel.blooddonor.ui.dialogMessage;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.databinding.DialogMessageBinding;
import com.sq.sohel.blooddonor.databinding.DialogRateUsBinding;
import com.sq.sohel.blooddonor.ui.base.BaseDialog;
import com.sq.sohel.blooddonor.ui.main.rating.RateUsCallback;
import com.sq.sohel.blooddonor.ui.main.rating.RateUsViewModel;
import com.sq.sohel.blooddonor.utils.AppUtils;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.AndroidSupportInjection;


public class DialogView extends BaseDialog implements DialogCallback {

    private static final String TAG = DialogView.class.getSimpleName();
    private static DialogView fragment;
    @Inject
    DialogViewModel mDialogViewModel;

    public static DialogView newInstance(AppUtils.MessageType messageType) {
        fragment = new DialogView();
        Bundle bundle = new Bundle();
        bundle.putString("message", String.valueOf(messageType));


        fragment.setArguments(bundle);
        return fragment;
    }

    public static DialogView newInstance() {
        fragment = new DialogView();
        return fragment;
    }
    public void SetMessageType(AppUtils.MessageType messageType){
        Bundle bundle = new Bundle();
        bundle.putString("messageType", String.valueOf(messageType));
        fragment.setArguments(bundle);
    }
    public void SetMessage(String message){
        Bundle bundle = new Bundle();
        bundle.putString("messageType", String.valueOf(AppUtils.MessageType.INFO));
        bundle.putString("messageDesc", message);
        fragment.setArguments(bundle);
    }
    public void SetMessage(String message, AppUtils.MessageType messageType){
        Bundle bundle = new Bundle();
        bundle.putString("messageType", String.valueOf(messageType));
        bundle.putString("messageDesc", message);
        fragment.setArguments(bundle);
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DialogMessageBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_message, container, false);
        View view = binding.getRoot();

        AndroidSupportInjection.inject(this);
        binding.setViewModel(mDialogViewModel);
        mDialogViewModel.setNavigator(this);
        String dialogMessage = getArguments().getString("messageType");
        AppUtils.MessageType messageType = AppUtils.MessageType.valueOf(dialogMessage);
        String message="";
        switch (messageType) {
            case INTERNET_NOT_FOUND:
                mDialogViewModel.setMessage(getResources().getString(R.string.internetNotFound));
                break;
            case INFO:
                message =  getArguments().getString("messageDesc");
                mDialogViewModel.setMessage(message);
                //mDialogViewModel.setImageUrl(String.valueOf(R.drawable.error_128));
                break;
            case ERROR:
                message =  getArguments().getString("messageDesc");
                mDialogViewModel.setMessage(message);
                //mDialogViewModel.setImageUrl(String.valueOf(R.drawable.error_128));
                break;
            case THANK_YOU:
                message =  getArguments().getString("messageDesc");
                mDialogViewModel.setMessage(message);
                //mDialogViewModel.setImageUrl(String.valueOf(R.drawable.error_128));
                break;

        }


        //mDialogViewModel.setImageUrl(R.drawable.no_internet);
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
