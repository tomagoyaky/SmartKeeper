package com.tomagoyaky.smart.keeper;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.tomagoyaky.smart.keeper.handler.ActivityLifecycleHandler;
import com.tomagoyaky.smart.keeper.handler.CrashHandler;
import com.tomagoyaky.smart.keeper.util.ProcessUtil;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

/**
 * @author tomagoyaky
 * @date 2018/3/2
 */

public class SmartApplication extends Application {

    private static SmartContext smartContext;
    private static SmartApplication smartApplication;
    public SmartService smartService;
    private SmartServiceConnection conn = new SmartServiceConnection();

    public static SmartApplication get() {
        return smartApplication;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        smartApplication = this;

        int pid = android.os.Process.myPid();
        String processName = ProcessUtil.getCurProcessName(smartContext);
        if (processName != null && processName.endsWith(":application")) {
            Logger.log("-==============================================-");
            Logger.log("-={               SmartKeeper                }=-");
            Logger.log("-==============================================-");
            Logger.log("| processName: " + processName);
            Logger.log("|         pid: " + pid);
            Logger.log("-==============================================-");
            CrashHandler.INSTANCE(smartContext).register(this);
            ActivityLifecycleHandler.INSTANCE(smartContext).register(this);

            SkinCompatManager.withoutActivity(this)                         // Basic Widget support
                    .addInflater(new SkinMaterialViewInflater())            // material design support           [selectable]
                    .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout support          [selectable]
                    .addInflater(new SkinCardViewInflater())                // CardView v7 support               [selectable]
                    .setSkinStatusBarColorEnable(false)                     // Disable statusBarColor skin support，default true   [selectable]
                    .setSkinWindowBackgroundEnable(false)                   // Disable windowBackground skin support，default true [selectable]
                    .loadSkin();

            try {
                SmartService.start(smartContext, conn);
            } catch (Exception e) {
                Logger.printStackTrace(e);
            }
        }
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

    public class SmartServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                SmartService.SmartBinder smartBinder = (SmartService.SmartBinder) iBinder;
                smartService = smartBinder.getService();
                smartService.sayHi();
            } catch (Exception e) {
                Logger.printStackTrace(e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            try {
                smartService.sayBye();
            } catch (Exception e) {
                Logger.printStackTrace(e);
            }
        }
    }
}
