

package com.sq.sohel.blooddonor.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.webkit.URLUtil;

import com.google.api.client.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


public final class NetworkUtils {

    private NetworkUtils() {
        // This class is not publicly instantiable
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    public static byte[] downloadFile(URL url) {
        try {
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), baos);
            return baos.toByteArray();
        } catch (IOException e) {
            // Log error and return null, some default or throw a runtime exception
            if (e != null) {

            }
        } catch (Exception ex) {
            if (ex != null) {

            }
        }
        return null;
    }

    public static byte[] downloadFile(Uri uri) {
        try {
            URL url = new URL(uri.toString());
            return downloadFile(url);
        } catch (IOException e) {
            // Log error and return null, some default or throw a runtime exception
        }
        return null;
    }

    public static byte[] downloadFile(String uri) {
        try {
            if (URLUtil.isValidUrl(uri)) {
                URL url = new URL(uri.toString());
                return downloadFile(url);
            }
        } catch (IOException e) {
            // Log error and return null, some default or throw a runtime exception
        }
        return null;
    }
}
