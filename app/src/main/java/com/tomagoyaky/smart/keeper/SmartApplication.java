package com.tomagoyaky.smart.keeper;

import android.app.Application;
import android.content.Context;

/**
 * Created by admin on 2018/3/2.
 */

public class SmartApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
