package com.sq.sohel.blooddonor.ui.forgotPassword;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.sq.sohel.blooddonor.BR;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.databinding.ActivityForgotPasswordBinding;
import com.sq.sohel.blooddonor.databinding.ActivityForgotPasswordBinding;
import com.sq.sohel.blooddonor.ui.base.BaseActivity;
import com.sq.sohel.blooddonor.ui.base.BasePagerAdapter;
import com.sq.sohel.blooddonor.ui.signUp.SignUpNavigator;
import com.sq.sohel.blooddonor.ui.signUp.SignUpViewModel;
import com.sq.sohel.blooddonor.utils.CommonUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ForgotPasswordActivity extends BaseActivity<ActivityForgotPasswordBinding,
        ForgotPasswordViewModel> implements ForgotPasswordNavigator, HasSupportFragmentInjector  {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    ForgotPasswordViewModel mForgotPasswordViewModel;
    @Inject
    Gson mGson;
    ActivityForgotPasswordBinding mActivityForgotPasswordBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public ForgotPasswordViewModel getViewModel() {
        return mForgotPasswordViewModel;
    }

    @Override
    public BasePagerAdapter getPagerAdapter() {
        return null;
    }

    @Override
    public void closeMe() {
        finish();
    }

    @Override
    public void recoveryEmailSend() {
        LinearLayout ll = findViewById(R.id.layout_forgot_pwd_email_recovery) ;
        ll.setVisibility(View.GONE);
        ll = findViewById(R.id.layout_forgot_pwd_email_send) ;
        ll.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityForgotPasswordBinding = getViewDataBinding();
        mForgotPasswordViewModel.setNavigator(this);
        setupActivity();

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }

    private void setupActivity() {
        EditText editText = findViewById(R.id.txt_signup_Email);
        ValidationModel validationModel = new ValidationModel("email", getString(R.string.EmailFormatRequired), editText, null);
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && CommonUtils.isEmailValid(s.toString())) {
                    mForgotPasswordViewModel.removeViewModelError(validationModel);
                    editText.setError(null);
                } else {
                    mForgotPasswordViewModel.setViewModelError(validationModel);
                    editText.requestFocus();
                    editText.setError(getString(R.string.EmailFormatRequired));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
