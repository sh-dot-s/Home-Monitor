package com.raspberrypi.slash.simple_db;

import android.content.SharedPreferences;
import android.util.Log;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        getRegistrationToken(refreshedToken);
        //TODO: Implement this method to send any registration to your app's servers.
    }
    public void getRegistrationToken(String token){
        SharedPreferences sharedPreference = getSharedPreferences(Constants.SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(Constants.FCM_TOKEN,token);
        editor.apply();
    }

}
