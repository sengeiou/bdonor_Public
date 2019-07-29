package com.sq.sohel.blooddonor.ui.becomeDonor.becomeHero;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.Observable;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sq.sohel.blooddonor.BR;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.databinding.FragmentBecomeDonorBinding;
import com.sq.sohel.blooddonor.ui.base.BaseActivity;
import com.sq.sohel.blooddonor.ui.base.BaseFragment;
import com.sq.sohel.blooddonor.utils.AppConstants;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.CommonUtils;
import com.sq.sohel.blooddonor.utils.DatePickerUtils.DatePickerUtil;
import com.sq.sohel.blooddonor.utils.LocationUtils.GPSUtils;
import com.sq.sohel.blooddonor.utils.PermissionUtils;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;
import com.sq.sohel.blooddonor.utils.ViewUtils;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.TELEPHONY_SERVICE;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;
import static com.sq.sohel.blooddonor.utils.AppConstants.DONOR_ADD_TYPE;
import static com.sq.sohel.blooddonor.utils.AppConstants.LOCATION_PERMISSION_LIST;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_CAMERA_PERMISSION_CODE;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_CAMERA_REQUEST_CODE;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_LOCATION_PERMISSION_CODE;
import static com.sq.sohel.blooddonor.utils.AppConstants.MY_PHONE_STATE_PERMISSION_CODE;
import static com.sq.sohel.blooddonor.utils.AppConstants.PHONE_STATE_PERMISSION_LIST;


public class HeroFragment extends BaseFragment<FragmentBecomeDonorBinding, HeroViewModel>
        implements HeroNavigator {


    FragmentBecomeDonorBinding mFragmentHeroBinding;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    @Named("HeroFragment")
    ViewModelProvider.Factory mViewModelFactory;
    private HeroViewModel mHeroViewModel;
    ImageView imageView = null;
    AppUtils.DonorAddType mDonorAddType;


    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    double wayLatitude = 0.0;
    double wayLongitude = 0.0;
    private boolean isGPS = false;
    EditText txtAddress;

    public static HeroFragment newInstance(AppUtils.DonorAddType donorAddType) {
        Bundle args = new Bundle();
        args.putString(DONOR_ADD_TYPE, donorAddType.name());
        HeroFragment fragment = new HeroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static HeroFragment newInstance(String donorEmail) {
        Bundle args = new Bundle();
        args.putString("donorEmail", donorEmail);
        HeroFragment fragment = new HeroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_become_donor;
    }

    @Override
    public HeroViewModel getViewModel() {
        mHeroViewModel = ViewModelProviders.of(this, mViewModelFactory).get(HeroViewModel.class);
        return mHeroViewModel;
    }

    @Override
    public void closeHero() {
        Intent upIntent = NavUtils.getParentActivityIntent(getActivity());
        upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NavUtils.navigateUpTo(getActivity(), upIntent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeroViewModel.setNavigator(this);
        String donorEmail = getArguments().getString("donorEmail");
        if (StringExtension.isNullOrWhiteSpace(donorEmail)) {
            mDonorAddType = AppUtils.DonorAddType.valueOf(getArguments().getString(DONOR_ADD_TYPE));
        } else {
            if (donorEmail.equals(mHeroViewModel.getDataManager().getCurrentUserEmail())) {
                mDonorAddType = AppUtils.DonorAddType.AddMySelf;
            } else {
                mDonorAddType = AppUtils.DonorAddType.AddOtherDonor;
            }
        }

        EditText editText = getActivity().findViewById(R.id.become_donor_email_address);
        if (mDonorAddType == AppUtils.DonorAddType.AddMySelf) {
            mHeroViewModel.fetchHero(mHeroViewModel.getDataManager().getCurrentUserEmail());
        } else {
            if (StringExtension.isNullOrWhiteSpace(donorEmail)) {
                mHeroViewModel.fetchHero("");
            } else {
                mHeroViewModel.fetchHero(donorEmail);
            }

        }
        txtAddress = getActivity().findViewById(R.id.txtAddress);
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentHeroBinding = getViewDataBinding();
        setUp();

    }


    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        imageView = getActivity().findViewById(R.id.becomeDonorImageView);
        imageView.setOnClickListener(v -> {
            if (checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, MY_CAMERA_REQUEST_CODE);
            }
        });

        EditText editText = getView().findViewById(R.id.txtPhoneNo);
        ValidationModel validationModel = new ValidationModel("contactNumber", getString(R.string.PhoneFormatRequired), editText, null);
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && CommonUtils.isPhoneNoValid(s.toString())) {
                    mHeroViewModel.removeViewModelError(validationModel);
                    editText.setError(null);
                } else {
                    mHeroViewModel.setViewModelError(validationModel);
                    editText.requestFocus();
                    editText.setError(getString(R.string.PhoneFormatRequired));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        TextView textView = getView().findViewById(R.id.txtAddressDetect);
        textView.setOnClickListener(locationPermissionClickListener);
        ViewUtils.setImageOnTextView(getContext(), textView, R.drawable.location_detect, 1.0f, 3);

        DatePickerUtil.setDatePicker(getView().findViewById(R.id.txtdob), null, 18);
        DatePickerUtil.setDatePicker(getView().findViewById(R.id.txtLastDonationDate), null, true, false);

        mHeroViewModel.getDOB().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (mHeroViewModel.getDOB() != null) {
                    DatePickerUtil.setDatePicker(getView().findViewById(R.id.txtdob), mHeroViewModel.getDOB().get(), 18);
                }

            }
        });

        mHeroViewModel.getLastDonationDate().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (mHeroViewModel.getLastDonationDate() != null) {
                    DatePickerUtil.setDatePicker(getView().findViewById(R.id.txtLastDonationDate), mHeroViewModel.getLastDonationDate().get(), true, false);
                }

            }
        });

        switch (mDonorAddType) {
            case AddMySelf:
                addMySelfDonor();
                break;
            case AddOtherDonor:
                addOtherDonorSetup();
                break;
        }
    }

    private void addMySelfDonor() {
        //Email Address
        EditText editText = getView().findViewById(R.id.become_donor_email_address);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(true);
        editText.setFocusable(false);
        editText.setMaxLines(1);
        editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        //Phone Detector
        TextView textView = getView().findViewById(R.id.txtPhoneDetect);
        ViewUtils.setImageOnTextView(getContext(), textView, R.drawable.phone_detect, 1.0f, 3);
        textView.setOnClickListener(phonePermissionClickListener);
    }

    private void addOtherDonorSetup() {
        EditText editText = getView().findViewById(R.id.become_donor_email_address);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);
        editText.setFocusable(true);
        editText.setMaxLines(1);
        editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        ValidationModel validationModel = new ValidationModel("email", getString(R.string.EmailFormatRequired), editText, null);
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && CommonUtils.isEmailValid(s.toString())) {
                    mHeroViewModel.removeViewModelError(validationModel);
                    editText.setError(null);
                } else {
                    mHeroViewModel.setViewModelError(validationModel);
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

    private View.OnClickListener locationPermissionClickListener = v -> {
//        if (!isGPS) {
//            Toast.makeText(this.getActivity(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
//            return;
//        }
        getLocation();
    };
    private View.OnClickListener phonePermissionClickListener = v -> {
        if (!PermissionUtils.hasPermissions(getContext(), PHONE_STATE_PERMISSION_LIST)) {
            requestPermissions(PHONE_STATE_PERMISSION_LIST, MY_PHONE_STATE_PERMISSION_CODE);
        } else {
            gotPhoneNumber();
        }
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

                    txtAddress = getView().findViewById(R.id.txtAddress);
                    setAddressData();
                } else {
                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                }
            });

        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, MY_CAMERA_REQUEST_CODE);
            } else {
                //Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
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
        if (requestCode == MY_PHONE_STATE_PERMISSION_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gotPhoneNumber();
            } else {
                //Toast.makeText(getActivity(), "Phone state permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            //imageBitmap.recycle();
            mHeroViewModel.setImageData(byteArray);
        }
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }


    @SuppressLint("MissingPermission")
    private void gotPhoneNumber() {
        if (PermissionUtils.hasPermissions(getContext(), PHONE_STATE_PERMISSION_LIST)) {
            TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(TELEPHONY_SERVICE);
            mHeroViewModel.getDataManager().setCurrentUserContactNumber(tm.getLine1Number());
            EditText txtTemp = getView().findViewById(R.id.txtPhoneNo);
            txtTemp.setText(tm.getLine1Number());
        }
    }

    private void setAddressData() {
        mHeroViewModel.getDataManager().setCurrentUserLatLong(new String[]{String.valueOf(wayLatitude), String.valueOf(wayLongitude)});
        android.location.Address address = ((BaseActivity) getActivity()).getAddressFromLatLong(); //lf.getAddressFromGPS();
        if (address != null) {
            String addressLine = address.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = address.getLocality();
            String state = address.getAdminArea();
            String country = address.getCountryName();
            String knownName = address.getFeatureName(); // Only if available else return NULL
            String postalCode = address.getPostalCode();
            city = city == null ? (state == null ? knownName : state) : city;

            EditText txtTemp = getView().findViewById(R.id.txtAddress);
            txtTemp.setText(addressLine);
            txtTemp = getView().findViewById(R.id.txtCity);
            txtTemp.setText(city);
            txtTemp = getView().findViewById(R.id.txtCountry);
            txtTemp.setText(country);
        }


    }


    @Override
    public void openLoginActivity() {
        getBaseActivity().openLoginActivity();
    }
}
