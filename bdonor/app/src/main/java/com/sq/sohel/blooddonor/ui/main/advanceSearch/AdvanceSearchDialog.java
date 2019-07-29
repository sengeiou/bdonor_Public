package com.sq.sohel.blooddonor.ui.main.advanceSearch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
//import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.api.services.people.v1.model.Gender;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.data.model.others.NameValuePair;
import com.sq.sohel.blooddonor.databinding.DialogAdvanceSearchBinding;
import com.sq.sohel.blooddonor.ui.base.BaseActivity;
import com.sq.sohel.blooddonor.ui.base.BaseDialog;
import com.sq.sohel.blooddonor.ui.main.MainPagerAdapter;
import com.sq.sohel.blooddonor.utils.AppConstants;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.LocationUtils.GPSUtils;
import com.sq.sohel.blooddonor.utils.MinMaxFilter;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;
import com.sq.sohel.blooddonor.utils.ViewUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static android.app.Activity.RESULT_OK;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_CAMERA_PERMISSION_CODE;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_CAMERA_REQUEST_CODE;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_LOCATION_PERMISSION_CODE;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_PHONE_STATE_PERMISSION_CODE;


public class AdvanceSearchDialog extends BaseDialog implements AdvanceSearchCallback {

    private static final String TAG = AdvanceSearchDialog.class.getSimpleName();
    @Inject
    AdvanceSearchViewModel mAdvanceSearchViewModel;

    PagerAdapter mPagerAdapter;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    double wayLatitude = 0.0;
    double wayLongitude = 0.0;
    private boolean isGPS = false;

    EditText txtAddress;
    EditText txtCity;
    EditText txtCountry;

    public static AdvanceSearchDialog newInstance() {
        AdvanceSearchDialog fragment = new AdvanceSearchDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void dismissDialog(boolean isCancel) {
        if(isCancel){
            dismissDialog(TAG);
            return;
        }
        String bt = mAdvanceSearchViewModel.getBloodType();
        String gn = mAdvanceSearchViewModel.getGender();
        String add = mAdvanceSearchViewModel.getAddress();
        String city = mAdvanceSearchViewModel.getCity();
        String country = mAdvanceSearchViewModel.getCountry();
        String minAge = mAdvanceSearchViewModel.getMinAge();
        String maxAge = mAdvanceSearchViewModel.getMaxAge();

        List<NameValuePair> nvp = new ArrayList<>();
        if (!StringExtension.isNullOrWhiteSpace(bt)) {
            nvp.add(new NameValuePair("BloodType", bt, null));
        }
        if (!StringExtension.isNullOrWhiteSpace(gn)) {
            nvp.add(new NameValuePair("Gender", gn, null));
        }
        if (!StringExtension.isNullOrWhiteSpace(add)) {
            nvp.add(new NameValuePair("Address", add, null));
        }
        if (!StringExtension.isNullOrWhiteSpace(city)) {
            nvp.add(new NameValuePair("City", city, null));
        } else {
            hideKeyboard();
            //Toast.makeText(getActivity(), "Please enter city. To auto detect your city, press the location icon.", Toast.LENGTH_LONG).show();
            ShowMessage("Please enter city. To auto detect your city, press the location icon.");
            return;
        }
        if (!StringExtension.isNullOrWhiteSpace(country)) {
            nvp.add(new NameValuePair("Country", country, null));
        } else {
            hideKeyboard();
            //Toast.makeText(getActivity(), "Please enter country. To auto detect your country, press the location icon.", Toast.LENGTH_LONG).show();
            ShowMessage("Please enter country. To auto detect your country, press the location icon.");
            return;
        }
        if (StringExtension.isNullOrWhiteSpace(minAge)) {
            minAge = "18";
        }

        if (StringExtension.isNullOrWhiteSpace(maxAge)) {
            maxAge = "50";
        }
        if (!StringExtension.isNullOrWhiteSpace(minAge) && !StringExtension.isNullOrWhiteSpace(maxAge)) {
            if (Integer.valueOf(minAge) > Integer.valueOf(maxAge)) {
                hideKeyboard();
                //Toast.makeText(getActivity(), "Minimum age can't be greater than maximum age.", Toast.LENGTH_LONG).show();
                ShowMessage("Minimum age can't be greater than maximum age.");
                return;
            } else {
                nvp.add(new NameValuePair("Age", minAge + ";" + maxAge, null));
            }
        }
        if (mPagerAdapter != null && !isCancel) {
            try {
                ((MainPagerAdapter) mPagerAdapter).FilterData(1, nvp, AppUtils.SearchType.Advance_Search);
            } catch (Exception ex) {
                if (ex != null) { // Shoudn't be error, check it

                }
            }
        }
        dismissDialog(TAG);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DialogAdvanceSearchBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_advance_search, container, false);

        View view = binding.getRoot();

        AndroidSupportInjection.inject(this);

        binding.setViewModel(mAdvanceSearchViewModel);

        mAdvanceSearchViewModel.setNavigator(this);
        EditText ads = view.findViewById(R.id.advanceSearchMaxAge);
        ads.setFilters(new InputFilter[]{new MinMaxFilter(18, 50)});

        ads = view.findViewById(R.id.advanceSearchMinAge);
        ads.setFilters(new InputFilter[]{new MinMaxFilter(18, 50)});

        TextView txtAddressDetect = view.findViewById(R.id.txtAddressDetect);
        txtAddressDetect.setOnClickListener(locationPermissionClickListener);
        ViewUtils.setImageOnTextView(getContext(), txtAddressDetect, R.drawable.location_detect, 1.0f, 3);

        txtAddress = view.findViewById(R.id.txtAddress);
        txtCity = view.findViewById(R.id.txtCity);
        txtCountry = view.findViewById(R.id.txtCountry);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getActivity());

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        new GPSUtils(this.getActivity()).turnGPSOn(new GPSUtils.onGpsListener() {
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

        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_LOCATION_PERMISSION_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(this.getActivity(), location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        setAddressData();
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
            } else {
                //Toast.makeText(getActivity(), "Location permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }

    private View.OnClickListener locationPermissionClickListener = v -> {
//        if (!isGPS) {
//            Toast.makeText(this.getActivity(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
//            return;
//        }
        getLocation();
    };

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_LOCATION_PERMISSION_CODE);

        } else {

            mFusedLocationClient.getLastLocation().addOnSuccessListener(this.getActivity(), location -> {
                if (location != null) {
                    wayLatitude = location.getLatitude();
                    wayLongitude = location.getLongitude();

                    //txtAddress = getView().findViewById(R.id.txtAddress);
                    setAddressData();
                } else {
                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                }
            });

        }
    }

    private void setAddressData() {
        mAdvanceSearchViewModel.getDataManager().setCurrentUserLatLong(new String[]{String.valueOf(wayLatitude), String.valueOf(wayLongitude)});
        android.location.Address address = ((BaseActivity) getActivity()).getAddressFromLatLong(); //lf.getAddressFromGPS();
        if (address != null) {
            String addressLine = address.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = address.getLocality();
            String state = address.getAdminArea();
            String country = address.getCountryName();
            String knownName = address.getFeatureName(); // Only if available else return NULL
            String postalCode = address.getPostalCode();
            city = city == null ? (state == null ? knownName : state) : city;

            txtAddress.setText(addressLine);
            txtCity.setText(city);
            txtCountry.setText(country);
        }


    }

    public void show(FragmentManager fragmentManager, PagerAdapter pagerAdapter) {
        mPagerAdapter = pagerAdapter;
        super.show(fragmentManager, TAG);
    }


    @Override
    public void openLoginActivity() {
        getBaseActivity().openLoginActivity();
    }
}
