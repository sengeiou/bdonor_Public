

package com.sq.sohel.blooddonor.utils;


public final class AppConstants {

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final int DB_VERSION = 4;
    public static final String DB_NAME = String.format("bloodDonorV%d.db", DB_VERSION);

    public static final long NULL_INDEX = -1L;

    public static final String PREF_NAME = "sam_pref";

    public static final String API_RESULT_FAILED = "Failure";//
    public static final String API_RESULT_SUCCESS = "Success";
    public static final String API_RESULT_WARNING = "Warning";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DISPLAY_DATE_FORMAT = "dd-MMM-yyyy";

    public static final String DUMMY_IMAGE = "ab_positive";// "ic_give_blood_save_life";
    public static final String DONOR_ADD_TYPE = "DONOR_ADD_TYPE";// "ic_give_blood_save_life";

    public static final String[] BLOOD_GROUP_LIST = {"A+", "B+", "AB+", "O+", "A-", "B-", "AB-", "O-"};
    public static final String[] REQUEST_FOR_LIST = {"Blood", "Platelet"};
    public static final String[] GENDER_LIST = {"Male", "Female"};


    public static final int MY_CAMERA_REQUEST_CODE = 1888;
    public static final int MY_CAMERA_PERMISSION_CODE = 100;

    public static final int ALL_PERMISSION_LIST_CODE = 9999;
    public static final String[] ALL_PERMISSION_LIST = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_PHONE_NUMBERS,
            android.Manifest.permission.CAMERA
    };

    public static final int GPS_REQUEST = 1001;
    public static final int MY_LOCATION_PERMISSION_CODE = 300;
    public static final String[] LOCATION_PERMISSION_LIST = {android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION};

    public static final int MY_PHONE_STATE_PERMISSION_CODE = 400;
    public static final String[] PHONE_STATE_PERMISSION_LIST = {android.Manifest.permission.READ_PHONE_STATE};

    public static final int GOOGLE_SIGN_IN = 1000;
    public static final int FACEBOOK_SIGN_IN = 2000;

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
