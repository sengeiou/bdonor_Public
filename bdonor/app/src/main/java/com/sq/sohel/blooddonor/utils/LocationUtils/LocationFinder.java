//package com.sq.sohel.blooddonor.utils.LocationUtils;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.IntentSender;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.os.Looper;
//import android.support.annotation.NonNull;
//import android.widget.Toast;
//
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.ResolvableApiException;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.gms.location.SettingsClient;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.sq.sohel.blooddonor.data.DataManager;
//import com.sq.sohel.blooddonor.utils.AppUtils;
//import com.sq.sohel.blooddonor.utils.PermissionUtils;
//
//import java.text.DateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import static com.sq.sohel.blooddonor.utils.AppConstants.LOCATION_PERMISSION_LIST;
//import static com.sq.sohel.blooddonor.utils.AppConstants.MY_LOCATION_PERMISSION_CODE;
//
//public class LocationFinder {
//
//
//    private LocationFinder() {
//
//    }
//
//    public LocationFinder(Context mContext, Activity mActivity, DataManager mDataManager) {
//        this.mContext = mContext;
//        this.mDataManager = mDataManager;
//        this.mActivity = mActivity;
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
//        mSettingsClient = LocationServices.getSettingsClient(mContext);
//    }
//
//    private static final String TAG = "LocationFinder";
//    private static final long INTERVAL = 1000 * 10;
//    private static final long FASTEST_INTERVAL = 1000 * 5;
//
//    private LocationRequest mLocationRequest;
//    private Location mCurrentLocation;
//    private String mLastUpdateTime;
//    private Context mContext;
//    private Activity mActivity;
//    private DataManager mDataManager;
//    private FusedLocationProviderClient mFusedLocationClient;
//    private SettingsClient mSettingsClient;
//    private LocationSettingsRequest mLocationSettingsRequest;
//    private Boolean mRequestingLocationUpdates;
//
//    LocationCallback mLocationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            super.onLocationResult(locationResult);
//            if (locationResult == null) {
//                return;
//            }
//            mCurrentLocation = locationResult.getLastLocation();
//            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//            mDataManager.setCurrentUserLatLong(new String[]{String.valueOf(mCurrentLocation.getLatitude()), String.valueOf(mCurrentLocation.getLongitude())});
//
//        }
//    };
//
//    private void buildLocationSettingsRequest() {
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
//        builder.addLocationRequest(mLocationRequest);
//        builder.setAlwaysShow(true);
//        mLocationSettingsRequest = builder.build();
//
//    }
//
//    public void createLocationRequest() {
//        if (!AppUtils.isGooglePlayServicesAvailable(mContext)) {
//            return;
//        }
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(INTERVAL);
//        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        buildLocationSettingsRequest();
//
//    }
//
//
//
//
//    @SuppressLint("MissingPermission")
//    public void startLocationUpdates() {
//        mRequestingLocationUpdates = true;
//        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
//                .addOnSuccessListener(mActivity, locationSettingsResponse -> {
//                    //Log.i(TAG, "All location settings are satisfied.");
//                    mFusedLocationClient.requestLocationUpdates(mLocationRequest,
//                            mLocationCallback, Looper.myLooper());
//                })
//                .addOnFailureListener(mActivity, e -> {
//                    int statusCode = ((ApiException) e).getStatusCode();
//                    switch (statusCode) {
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                            //Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade location settings ");
//                            try {
//                                // Show the dialog by calling startResolutionForResult(), and check the
//                                // result in onActivityResult().
//                                ResolvableApiException rae = (ResolvableApiException) e;
//                                rae.startResolutionForResult(mActivity, MY_LOCATION_PERMISSION_CODE);
//                            } catch (IntentSender.SendIntentException sie) {
//                                //Log.i(TAG, "PendingIntent unable to execute request.");
//                            }
//                            break;
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                            String errorMessage = "Location settings are inadequate, and cannot be fixed here. Fix in Settings.";
//                            //Log.e(TAG, errorMessage);
//                            Toast.makeText(mActivity, errorMessage, Toast.LENGTH_LONG).show();
//                            mRequestingLocationUpdates = false;
//                    }
//                });
//    }
//
//    public void stopLocationUpdates() {
//        if (!mRequestingLocationUpdates) {
//            return;
//        }
//
//        // It is a good practice to remove location requests when the activity is in a paused or
//        // stopped state. Doing so helps battery performance and is especially
//        // recommended in applications that request frequent location updates.
//        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
//                .addOnCompleteListener(mActivity, task -> mRequestingLocationUpdates = false);
//    }
//
//
//}
//
//
