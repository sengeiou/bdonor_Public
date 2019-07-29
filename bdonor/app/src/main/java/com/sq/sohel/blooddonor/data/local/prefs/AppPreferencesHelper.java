package com.sq.sohel.blooddonor.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.di.PreferenceInfo;
import com.sq.sohel.blooddonor.utils.AppConstants;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import java.io.Serializable;

import javax.inject.Inject;


public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";

    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";

    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";

    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";
    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_DATA = "PREF_KEY_CURRENT_USER_PROFILE_PIC_DATA";

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
    private static final String PREF_KEY_USER_CONTACT_NUMBER = "PREF_KEY_USER_CONTACT_NUMBER";
    private static final String PREF_KEY_USER_CURRENT_LAT_LONG = "PREF_KEY_USER_CURRENT_LAT_LONG";


    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getCurrentUserEmail() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, null);
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply();
    }

    @Override
    public String getCurrentUserId() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_ID, null);
    }

    @Override
    public void setCurrentUserId(String userId) {
        //String id = userId == null ? AppConstants.NULL_INDEX : userId;
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_ID, userId).apply();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public String getCurrentUserName() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null);
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply();
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, null);
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply();
    }

    @Override
    public byte[] getCurrentUserProfilePicData() {
        String stringFromSharedPrefs = mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_DATA, null);
        byte[] array = null;
        if (StringExtension.isNullOrEmpty(stringFromSharedPrefs)) {
            array = Base64.decode(stringFromSharedPrefs, Base64.DEFAULT);
        }
        return array;
    }

    @Override
    public void setCurrentUserProfilePicData(byte[] profilePicData) {
        if (profilePicData != null && profilePicData.length > 0) {
            String saveThis = Base64.encodeToString(profilePicData, Base64.DEFAULT);
            mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_DATA, saveThis).apply();
        }
    }

    @Override
    public String getCurrentUserContactNumber() {
        return mPrefs.getString(PREF_KEY_USER_CONTACT_NUMBER, null);
    }

    @Override
    public void setCurrentUserContactNumber(String contactNumber) {
        mPrefs.edit().putString(PREF_KEY_USER_CONTACT_NUMBER, contactNumber).apply();
    }

    @Override
    public String[] getCurrentUserLatLong() {
        String str = mPrefs.getString(PREF_KEY_USER_CURRENT_LAT_LONG, null);
        if (str != null && !str.isEmpty()) {
            return str.split(";");
        }
        return null;
    }

    @Override
    public void setCurrentUserLatLong(String[] LatLong) {
        if (LatLong.length == 2) {
            mPrefs.edit().putString(PREF_KEY_USER_CURRENT_LAT_LONG, LatLong[0] + ";" + LatLong[1]).apply();
        }

    }
}
