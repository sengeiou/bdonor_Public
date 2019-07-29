package com.sq.sohel.blooddonor.data;

import android.os.AsyncTask;

import com.sq.sohel.blooddonor.data.local.db.DbHelper;
import com.sq.sohel.blooddonor.data.local.prefs.PreferencesHelper;
import com.sq.sohel.blooddonor.data.model.others.DonorCardData;
import com.sq.sohel.blooddonor.data.model.others.RequestBloodCardData;
import com.sq.sohel.blooddonor.data.remote.ApiHelper;

import java.util.List;

import io.reactivex.Observable;

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {

    Observable<List<DonorCardData>> getDonorCardData();
    Observable<List<RequestBloodCardData>> getRequestBloodCardData(String bloodType, boolean showOnlyMyRequest);

    //Observable<Boolean> seedDatabaseOptions();

    //Observable<Boolean> seedDatabaseQuestions();

    Observable<Boolean> seedBloodDonorDatabase();

    void setUserAsLoggedOut();

    void updateApiHeader(String userId, String accessToken);

    void updateUserInfo(
            String accessToken,
            String userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath,
            byte[] profilePicBytes);

    void updateUserProfilePicture(byte[] profilePicBytes);

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}

