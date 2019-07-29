package com.sq.sohel.blooddonor.data.local.prefs;

import com.sq.sohel.blooddonor.data.DataManager;



public interface PreferencesHelper {

    String getAccessToken();
    void setAccessToken(String accessToken);

    String getCurrentUserEmail();
    void setCurrentUserEmail(String email);

    String getCurrentUserId();
    void setCurrentUserId(String userId);

    int getCurrentUserLoggedInMode();
    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    String getCurrentUserName();
    void setCurrentUserName(String userName);

    String getCurrentUserProfilePicUrl();
    void setCurrentUserProfilePicUrl(String profilePicUrl);

    byte[] getCurrentUserProfilePicData();
    void setCurrentUserProfilePicData(byte[] profilePicData);

    String getCurrentUserContactNumber();
    void setCurrentUserContactNumber(String contactNumber);

    String[] getCurrentUserLatLong();
    void setCurrentUserLatLong(String[] LatLong);
}
