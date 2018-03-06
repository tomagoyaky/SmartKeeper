package com.tomagoyaky.smart.keeper.util;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by admin on 2017/6/20.
 */

public class ProcessUtil {

    public static String getCurProcessName(@NonNull Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
