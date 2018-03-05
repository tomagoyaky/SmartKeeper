package com.tomagoyaky.smart.keeper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 *
 * @author tomagoyaky
 * @date 2018/3/6
 */

public class Logger {

    public static boolean debug = true;
    public static final String TAG = "SmartLogger";

    @Nullable
    public static String log(@Nullable String msg){
        if(msg != null && debug) {
            Log.d(TAG, msg);
        }
        return msg;
    }

    @Nullable
    public static String err(@Nullable String msg){
        if(msg != null && debug) {
            Log.d(TAG, msg);
        }
        return msg;
    }

    @Nullable
    public static void printStackTrace(@NonNull Exception e){
        err("\\ >>> Exception Message:" + e.toString());
        StackTraceElement[] stackTraceElement = e.getStackTrace();
        for (int i = 0; i < stackTraceElement.length; i++) {
            err(String.format("\\__%02d_%s.%s:%d", i, stackTraceElement[i].getClassName(), stackTraceElement[i].getMethodName(), stackTraceElement[i].getLineNumber()));
        }
    }

    @Nullable
    public static void printStackTrace(@NonNull Throwable throwable) {
        err("\\ >>> Throwable Message:" + throwable.toString());
        StackTraceElement[] stackTraceElement = throwable.getStackTrace();
        for (int i = 0; i < stackTraceElement.length; i++) {
            err(String.format("\\__%02d_%s.%s:%d", i, stackTraceElement[i].getClassName(), stackTraceElement[i].getMethodName(), stackTraceElement[i].getLineNumber()));
        }
    }
}
