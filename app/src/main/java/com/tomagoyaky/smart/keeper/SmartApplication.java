package com.tomagoyaky.smart.keeper;

import android.app.Application;
import android.content.Context;

import com.tomagoyaky.smart.keeper.handler.ActivityLifecycleHandler;
import com.tomagoyaky.smart.keeper.handler.CrashHandler;

/**
 * @author tomagoyaky
 * @date 2018/3/2
 */

public class SmartApplication extends Application {

    private static SmartContext smartContext;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.INSTANCE(smartContext).register(this);
        ActivityLifecycleHandler.INSTANCE(smartContext).register(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            smartContext = new SmartContext(base);
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public static SmartContext getSmartContext() {
        return smartContext;
    }
}
