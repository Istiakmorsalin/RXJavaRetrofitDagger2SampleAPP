package istiak.com.rxjavaretrofitsampleapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import istiak.com.rxjavaretrofitsampleapp.RXJavaRetrofitSampleApp;

public class ConnectionUtil {
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) RXJavaRetrofitSampleApp.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static String getNoConnectionMessage() {
        return "TIME_OUT + TRY_AGAIN";
    }
}
