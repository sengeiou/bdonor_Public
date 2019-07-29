package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.PagerAdapter;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sq.sohel.blooddonor.BR;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.databinding.ActivityBecomeDonorBinding;
import com.sq.sohel.blooddonor.databinding.ActivityRequestResponseForBloodBinding;
import com.sq.sohel.blooddonor.ui.base.BaseActivity;
import com.sq.sohel.blooddonor.ui.base.BasePagerAdapter;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodFragment;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.LocationUtils.GPSUtils;
import com.sq.sohel.blooddonor.utils.PermissionUtils;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RequestAndResponseForBloodActivity extends BaseActivity<ActivityRequestResponseForBloodBinding, RequestAndResponseForBloodViewModel> implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    RequestAndResponseForBloodViewModel mRequestAndResponseForBloodViewModel;
    @Inject
    RequestAndResponseForBloodPageAdapter mPagerAdapter;
    private ActivityRequestResponseForBloodBinding mActivityRequestAndResponseForBloodBinding;

    public static Intent newIntent(Context context, boolean showOnlyMyRequest) {
        return new Intent(context, RequestAndResponseForBloodActivity.class).putExtra("showOnlyMyRequest", showOnlyMyRequest);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_response_for_blood;
    }

    @Override
    public RequestAndResponseForBloodViewModel getViewModel() {
        return mRequestAndResponseForBloodViewModel;
    }

    @Override
    public RequestAndResponseForBloodPageAdapter getPagerAdapter() {
        return mPagerAdapter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRequestAndResponseForBloodBinding = getViewDataBinding();
        boolean showOnlyMyRequest = getIntent().getExtras().getBoolean("showOnlyMyRequest");
        mPagerAdapter.setShowOnlyMyRequest(showOnlyMyRequest);
        if (showOnlyMyRequest) {
            mActivityRequestAndResponseForBloodBinding.toolbar.setTitle("My request");
        }
        setUp();
    }

    private void setUp() {
        setSupportActionBar(mActivityRequestAndResponseForBloodBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mPagerAdapter.setCount(8);

        mActivityRequestAndResponseForBloodBinding.requestAndResponseForBloodViewPager.setAdapter(mPagerAdapter);

        mActivityRequestAndResponseForBloodBinding.tabLayout.addTab(mActivityRequestAndResponseForBloodBinding.tabLayout.newTab().setText("A+"));
        mActivityRequestAndResponseForBloodBinding.tabLayout.addTab(mActivityRequestAndResponseForBloodBinding.tabLayout.newTab().setText("B+"));
        mActivityRequestAndResponseForBloodBinding.tabLayout.addTab(mActivityRequestAndResponseForBloodBinding.tabLayout.newTab().setText("O+"));
        mActivityRequestAndResponseForBloodBinding.tabLayout.addTab(mActivityRequestAndResponseForBloodBinding.tabLayout.newTab().setText("AB+"));
        mActivityRequestAndResponseForBloodBinding.tabLayout.addTab(mActivityRequestAndResponseForBloodBinding.tabLayout.newTab().setText("A-"));
        mActivityRequestAndResponseForBloodBinding.tabLayout.addTab(mActivityRequestAndResponseForBloodBinding.tabLayout.newTab().setText("B-"));
        mActivityRequestAndResponseForBloodBinding.tabLayout.addTab(mActivityRequestAndResponseForBloodBinding.tabLayout.newTab().setText("O-"));
        mActivityRequestAndResponseForBloodBinding.tabLayout.addTab(mActivityRequestAndResponseForBloodBinding.tabLayout.newTab().setText("AB-"));
        mActivityRequestAndResponseForBloodBinding.requestAndResponseForBloodViewPager.setOffscreenPageLimit(mActivityRequestAndResponseForBloodBinding.tabLayout.getTabCount());

        mActivityRequestAndResponseForBloodBinding.requestAndResponseForBloodViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivityRequestAndResponseForBloodBinding.tabLayout));

        mActivityRequestAndResponseForBloodBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mActivityRequestAndResponseForBloodBinding.requestAndResponseForBloodViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });
    }
}
