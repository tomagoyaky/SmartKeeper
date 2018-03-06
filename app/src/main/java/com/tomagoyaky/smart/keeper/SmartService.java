package com.tomagoyaky.smart.keeper;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.tomagoyaky.smart.keeper.handler.RunnableHandler;

public class SmartService extends Service {

    private static SmartContext smartContext;
    public static long count = 0;

    /**
     * 开启服务
     *
     * @param _smartContext
     */
    public static void start(@NonNull SmartContext _smartContext, @NonNull ServiceConnection conn) throws Exception {
        smartContext = _smartContext;
        Logger.log("SmartService.start()");
        Intent intent = new Intent();
        intent.setClass(smartContext, SmartService.class);
        smartContext.bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }

    /**
     * 关闭服务
     *
     * @param smartContext
     */
    public static void stop(@NonNull SmartContext smartContext, @NonNull ServiceConnection conn) throws Exception {
        Logger.log("SmartService.stop()");
        Intent intent = new Intent();
        intent.setClass(smartContext, SmartService.class);
        smartContext.unbindService(conn);
    }

    public void sayHi() throws Exception {
        Logger.log("[SmartService] Hi, smart service!");

        RunnableHandler.INSTANCE(smartContext).execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        count++;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Logger.printStackTrace(e);
                    }
                }
            }
        });
    }

    public void sayBye() {
        Logger.log("[SmartService] Bye, smart service!");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.log("SmartService.onBind()");
        return new SmartBinder();
    }

    @Override
    public void onCreate() {
        Logger.log("SmartService.onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.log("SmartService.onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.log("SmartService.onDestroy()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        boolean ret = super.onUnbind(intent);
        Logger.log("SmartService.onUnbind()");
        return ret;
    }

    public class SmartBinder extends Binder {
        @NonNull
        public SmartService getService() {
            return SmartService.this;
        }
    }
}
