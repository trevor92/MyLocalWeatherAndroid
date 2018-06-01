package com.mylocalweather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Trevor on 2/20/2016.
 * Network class to check for network connectivity
 */
public class Network {

   public boolean checkNetworkConnection(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

       return isConnected;

    }

    public void showNetworkError(Context context){
        Toast.makeText(context, "Please Check Internet Connection", Toast.LENGTH_LONG).show();
    }

}

