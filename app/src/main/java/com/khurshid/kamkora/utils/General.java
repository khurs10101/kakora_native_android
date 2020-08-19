package com.khurshid.kamkora.utils;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.khurshid.kamkora.MyApplication;

public class General {

    private static final String MYTAG = General.class.getSimpleName();

    public static boolean internetCheck() {
        ConnectivityManager ConnectionManager = (ConnectivityManager) MyApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            Log.d(MYTAG, "Internet Active");
            return true;
        } else {
            Log.d(MYTAG, "Internet Not available");
            return false;

        }
    }
}
