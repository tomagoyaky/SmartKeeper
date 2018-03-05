package com.tomagoyaky.smart.keeper.handler;

import android.os.Looper;

import com.tomagoyaky.smart.keeper.SmartApplication;
import com.tomagoyaky.smart.keeper.SmartContext;
import com.tomagoyaky.smart.keeper.util.DialogUtil;

/**
 * @author tomagoyaky
 * @date 2018/3/5
 */

public class CrashHandler extends AbstractHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler crashHandler;
    private CrashHandler(SmartContext smartContext) {
        super(smartContext);
    }

    public static CrashHandler INSTANCE(SmartContext smartContext){
        if(crashHandler == null){
            crashHandler = new CrashHandler(smartContext);
        }
        return crashHandler;
    }

    /**
     * 系统默认的 UncaughtException 处理类
     * */
    private Thread.UncaughtExceptionHandler defaultHandler;

    @Override
    public void register(SmartApplication smartApplication) {
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && defaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            defaultHandler.uncaughtException(thread, ex);
        }
        AppDistory();
    }

    private void AppDistory() {
        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            // ignore
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 使用 Toast 来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                DialogUtil.getMessageDialog(smartContext, ex.getMessage())
                        .setTitle("程序异常")
                        .create()
                        .show();
                Looper.loop();
            }
        }.start();
        return true;
    }
}
