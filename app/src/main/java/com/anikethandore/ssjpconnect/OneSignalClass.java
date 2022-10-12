package com.anikethandore.ssjpconnect;

import android.app.Application;

import com.onesignal.OneSignal;

public class OneSignalClass extends Application {

    private static final String ONESIGNAL_APP_ID = "40f2491d-7a4f-475a-8ab5-bb55d046e83b";

    @Override
    public void onCreate() {
        super.onCreate();


        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
