package com.sq.sohel.blooddonor.ui.main.newRequestForBlood;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.sq.sohel.blooddonor.BR;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.databinding.ActivityNewRequestForBloodBinding;
import com.sq.sohel.blooddonor.ui.base.BaseActivity;
import com.sq.sohel.blooddonor.ui.base.BasePagerAdapter;
import com.sq.sohel.blooddonor.utils.DatePickerUtils.DatePickerUtil;
import com.sq.sohel.blooddonor.utils.LocationUtils.GPSUtils;
import com.sq.sohel.blooddonor.utils.MinMaxFilter;
import com.sq.sohel.blooddonor.utils.ViewUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.sq.sohel.blooddonor.utils.AppConstants.MY_CAMERA_PERMISSION_CODE;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_CAMERA_REQUEST_CODE;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_LOCATION_PERMISSION_CODE;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_PHONE_STATE_PERMISSION_CODE;


public class NewRequestActivity extends BaseActivity<ActivityNewRequestForBloodBinding,
        NewRequestViewModel> implements NewRequestNavigator, HasSupportFragmentInjector {


    ActivityNewRequestForBloodBinding mActivityNewRequestForBloodBinding;
    @Inject
    NewRequestViewModel mNewRequestViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    double wayLatitude = 0.0;
    double wayLongitude = 0.0;
    private boolean isGPS = false;

    @Override
    public void closeMe() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NavUtils.navigateUpTo(this, upIntent);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_request_for_blood;
    }

    @Override
    public NewRequestViewModel getViewModel() {
        return mNewRequestViewModel;
    }

    @Override
    public BasePagerAdapter getPagerAdapter() {
        return null;
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
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_LOCATION_PERMISSION_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        setAddressData();
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNewRequestForBloodBinding = getViewDataBinding();
        mNewRequestViewModel.setNavigator(this);
        setSupportActionBar(mActivityNewRequestForBloodBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//        EditText txtnoOfBags = findViewById(R.id.txtnoOfBags);
//        txtnoOfBags.setFilters(new InputFilter[]{new MinMaxFilter(1, Integer.MAX_VALUE)});
//        txtnoOfBags.setTextDirection(View.TEXT_DIRECTION_RTL);

        DatePickerUtil.setDatePicker(findViewById(R.id.txtBloodNeedDate), null, false,true);

        TextView txtAddressDetect = findViewById(R.id.txtAddressDetect);
        txtAddressDetect.setOnClickListener(locationPermissionClickListener);
        ViewUtils.setImageOnTextView(this, txtAddressDetect, R.drawable.location_detect, 1.0f, 3);

//        TextView txtCityDetect = findViewById(R.id.txtCityDetect);
//        txtCityDetect.setOnClickListener(locationPermissionClickListener);
//        ViewUtils.setImageOnTextView(this, txtCityDetect, R.drawable.location_detect, 1.0f, 3);
//
//        TextView txtCountryDetect = findViewById(R.id.txtCountryDetect);
//        txtCountryDetect.setOnClickListener(locationPermissionClickListener);
//        ViewUtils.setImageOnTextView(this, txtCountryDetect, R.drawable.location_detect, 1.0f, 3);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        new GPSUtils(this).turnGPSOn(new GPSUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();

                        setAddressData();

                        if (mFusedLocationClient != null) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }
                    }
                }
            }
        };
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NewRequestActivity.class);
    }


    private View.OnClickListener locationPermissionClickListener = v -> {
//        if (!isGPS) {
//            Toast.makeText(this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
//            return;
//        }
        getLocation();
    };
    private void setAddressData() {
        mNewRequestViewModel.getDataManager().setCurrentUserLatLong(new String[]{String.valueOf(wayLatitude), String.valueOf(wayLongitude)});
        android.location.Address address = getAddressFromLatLong(); //lf.getAddressFromGPS();
        if (address != null) {
            String addressLine = address.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = address.getLocality();
            String state = address.getAdminArea();
            String country = address.getCountryName();
            String knownName = address.getFeatureName(); // Only if available else return NULL
            String postalCode = address.getPostalCode();
            city = city == null ? (state == null ? knownName : state) : city;

            EditText txtTemp = findViewById(R.id.txtAddress);
            txtTemp.setText(addressLine);
            txtTemp = findViewById(R.id.txtCity);
            txtTemp.setText(city);
            txtTemp = findViewById(R.id.txtCountry);
            txtTemp.setText(country);
        }
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_LOCATION_PERMISSION_CODE);

        } else {

            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    wayLatitude = location.getLatitude();
                    wayLongitude = location.getLongitude();
                    setAddressData();
                } else {
                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                }
            });

        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
