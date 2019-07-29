

package com.sq.sohel.blooddonor.ui.becomeDonor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.sq.sohel.blooddonor.ui.base.BaseActivity;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.LocationUtils.GPSUtils;
//import com.sq.sohel.blooddonor.utils.LocationUtils.LocationFinder;
import com.sq.sohel.blooddonor.utils.PermissionUtils;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.sq.sohel.blooddonor.utils.AppConstants.DONOR_ADD_TYPE;
import static com.sq.sohel.blooddonor.utils.AppConstants.LOCATION_PERMISSION_LIST;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_CAMERA_REQUEST_CODE;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_LOCATION_PERMISSION_CODE;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_PHONE_STATE_PERMISSION_CODE;


public class BecomeDonorActivity extends BaseActivity<ActivityBecomeDonorBinding, BecomeDonorViewModel> implements HasSupportFragmentInjector{

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    BecomeDonorViewModel mBecomeDonorViewModel;
    @Inject
    BecomeDonorPagerAdapter mPagerAdapter;
    private ActivityBecomeDonorBinding mActivityBecomeDonorBinding;

    //LocationFinder mLocationFinder;

    public static Intent newIntent(Context context, AppUtils.DonorAddType donorAddType) {
        return new Intent(context, BecomeDonorActivity.class).putExtra(DONOR_ADD_TYPE, donorAddType.name());
    }

    public static Intent newIntent(Context context, String donorEmail) {
        return new Intent(context, BecomeDonorActivity.class).putExtra("donorEmail", donorEmail);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_become_donor;
    }

    @Override
    public BecomeDonorViewModel getViewModel() {
        return mBecomeDonorViewModel;
    }

    @Override
    public BecomeDonorPagerAdapter getPagerAdapter() {
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
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public AndroidInjector<Fragment> supportFragmentInjector() {
//        return fragmentDispatchingAndroidInjector;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String donorEmail = getIntent().getExtras().getString("donorEmail");
        AppUtils.DonorAddType donorAddType;
        if (!StringExtension.isNullOrWhiteSpace(donorEmail)) {
            if (donorEmail.equals(mBecomeDonorViewModel.getDataManager().getCurrentUserEmail())) {
                donorAddType = AppUtils.DonorAddType.AddMySelf;
            } else {
                donorAddType = AppUtils.DonorAddType.AddOtherDonor;
            }
        } else {
            donorAddType = AppUtils.DonorAddType.valueOf(getIntent().getExtras().getString(DONOR_ADD_TYPE));
        }

        mPagerAdapter.setDonorAddType(donorAddType);
        mPagerAdapter.setDonorEmail(donorEmail);

        switch (donorAddType) {
            case AddMySelf:
                mBecomeDonorViewModel.setActivityTitle(getResources().getString(R.string.become_donor_title));
                break;
            case AddOtherDonor:
                mBecomeDonorViewModel.setActivityTitle(getResources().getString(R.string.add_new_donor));
                break;
        }

        this.setFinishOnTouchOutside(true);
        //mLocationFinder = new LocationFinder(this, this, mBecomeDonorViewModel.getDataManager());
        mActivityBecomeDonorBinding = getViewDataBinding();

        if (!PermissionUtils.hasPermissions(this, LOCATION_PERMISSION_LIST)) {
            requestPermissionsSafely(LOCATION_PERMISSION_LIST, MY_PHONE_STATE_PERMISSION_CODE);
        } else {
            enableLoc();
            if (GPSUtils.hasGPSDevice(this) && GPSUtils.GPSStatus(this)) {
                enableLoc();
            }
        }

        setUp();
    }

    private void setUp() {
        setSupportActionBar(mActivityBecomeDonorBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mPagerAdapter.setCount(1);
        mActivityBecomeDonorBinding.becomeDonorViewPager.setAdapter(mPagerAdapter);
    }

    private void enableLoc() {
        //mLocationFinder.createLocationRequest();
        //mLocationFinder.startLocationUpdates();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MY_LOCATION_PERMISSION_CODE:
                //enableLoc();
                //requestPermissionsSafely(LOCATION_PERMISSION_LIST, MY_LOCATION_PERMISSION_CODE);
                break;
            case MY_CAMERA_REQUEST_CODE:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_LOCATION_PERMISSION_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableLoc();
            } else {
                //Toast.makeText(this, "Location permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!PermissionUtils.hasPermissions(this, LOCATION_PERMISSION_LIST)) {
//            requestPermissionsSafely(LOCATION_PERMISSION_LIST, MY_PHONE_STATE_PERMISSION_CODE);
//        } else {
//            enableLoc();
//        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
