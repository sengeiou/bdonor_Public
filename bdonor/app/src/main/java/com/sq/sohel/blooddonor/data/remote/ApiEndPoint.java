package com.sq.sohel.blooddonor.data.remote;


import com.sq.sohel.blooddonor.BuildConfig;

public final class ApiEndPoint {

    public static final String ENDPOINT_DONOR_LIST = BuildConfig.ONION_BASE + "/donorlist";
    public static final String ENDPOINT_BLOOD_REQUEST_LIST = BuildConfig.ONION_BASE + "/bloodrequestlist";


    public static final String ENDPOINT_FACEBOOK_LOGIN = BuildConfig.XPTO_BASE + "/extarnallogin/facebook";
    public static final String ENDPOINT_GOOGLE_LOGIN = BuildConfig.XPTO_BASE + "/extarnallogin/google";
    public static final String ENDPOINT_LOGOUT = BuildConfig.XPTO_BASE + "/logout";
    public static final String ENDPOINT_TOKEN = BuildConfig.XPTO_BASE + "/connect/token";
    public static final String ENDPOINT_USERS = BuildConfig.XPTO_BASE + "/api/users";
    public static final String ENDPOINT_FORGOT_PASSWORD = BuildConfig.XPTO_BASE + "/api/users/{email}/forgotpassword"; //api/users/{email}/forgotpassword
    public static final String ENDPOINT_SERVER_LOGIN = BuildConfig.XPTO_BASE + "/ServerLogin.json";

    public static final String ENDPOINT_COMPANY_CONTACT = BuildConfig.ONION_BASE + "/companies/" + BuildConfig.COMUGID + "/contacts";

    public static final String ENDPOINT_COMPANY_LIST_FOR_TEST = BuildConfig.ONION_BASE + "/companies";


    public static final String ENDPOINT_DONOR_USER_POST = BuildConfig.ONION_BASE + "/donoruser";
    public static final String ENDPOINT_DONOR_INFO = BuildConfig.ONION_BASE + "/donor";
    public static final String ENDPOINT_DONOR_IMAGE_INFO = BuildConfig.ONION_BASE + "/donorimage";
    public static final String ENDPOINT_BLOOD_REQUEST = BuildConfig.ONION_BASE + "/bloodrequest";


    public static final String ENDPOINT_RATE_US = BuildConfig.ONION_BASE + "/rateus";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }
}
