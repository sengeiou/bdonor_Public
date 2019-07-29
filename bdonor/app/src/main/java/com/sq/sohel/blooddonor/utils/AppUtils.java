

package com.sq.sohel.blooddonor.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.sq.sohel.blooddonor.R;


public final class AppUtils {

    private AppUtils() {
        // This class is not publicly instantiable
    }

    public static void openPlayStoreForApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_market_link) + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_google_play_store_link) + appPackageName)));
        }
    }

    public static boolean isGooglePlayServicesAvailable(Context context) {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);// GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            return false;
        }
    }

    public enum SearchType {
        Simpple_Search,
        Advance_Search
    }

    public enum DonorAddType {
        AddMySelf,
        AddOtherDonor
    }
    public enum MessageType{
        INTERNET_NOT_FOUND,
        ERROR,
        INFO,
        THANK_YOU,
    }
    public enum ValidationViewType{
        ACTIVITY,
        FRAGMENT,
        DIALOG
    }
}
