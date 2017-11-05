package com.zuccessful.trackem;

import android.app.Application;
import android.util.Log;

import com.hypertrack.lib.HyperTrack;

public class MyApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        // Initialize HyperTrack SDK with your Publishable Key here
        // Refer to documentation at
        // https://docs.hypertrack.com/gettingstarted/authentication.html
        // @NOTE: Add **YOUR_PUBLISHABLE_KEY_HERE** here for SDK to be
        // authenticated with HyperTrack Server
        HyperTrack.initialize(this, "pk_test_973608505e941bab29db278ba9cc6822b516cf63");
        HyperTrack.enableDebugLogging(Log.VERBOSE);
    }
}