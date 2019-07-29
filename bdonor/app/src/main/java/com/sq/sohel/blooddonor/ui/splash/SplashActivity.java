
package com.sq.sohel.blooddonor.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;

import com.sq.sohel.blooddonor.BR;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.databinding.ActivitySplashBinding;
import com.sq.sohel.blooddonor.ui.base.BaseActivity;
import com.sq.sohel.blooddonor.ui.base.BasePagerAdapter;
import com.sq.sohel.blooddonor.ui.dialogMessage.DialogView;
import com.sq.sohel.blooddonor.ui.login.LoginActivity;
import com.sq.sohel.blooddonor.ui.main.MainActivity;
import com.sq.sohel.blooddonor.utils.AppUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashNavigator, HasSupportFragmentInjector {

    @Inject
    SplashViewModel mSplashViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashViewModel getViewModel() {
        return mSplashViewModel;
    }

    @Override
    public BasePagerAdapter getPagerAdapter() {
        return null;
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashViewModel.setNavigator(this);
        mSplashViewModel.startSeeding(); // After Login
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
