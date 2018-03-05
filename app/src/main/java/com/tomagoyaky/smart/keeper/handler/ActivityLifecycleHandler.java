package com.tomagoyaky.smart.keeper.handler;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.tomagoyaky.smart.keeper.Logger;
import com.tomagoyaky.smart.keeper.SmartApplication;
import com.tomagoyaky.smart.keeper.SmartContext;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * @author tomagoyaky
 * @date 2018/3/6
 */

public class ActivityLifecycleHandler extends AbstractHandler implements Application.ActivityLifecycleCallbacks {

    private static ActivityLifecycleHandler activityLifecycleHandler;
    private ActivityLifecycleHandler(SmartContext smartContext) {
        super(smartContext);
    }

    public static ActivityLifecycleHandler INSTANCE(SmartContext smartContext){
        if(activityLifecycleHandler == null){
            activityLifecycleHandler = new ActivityLifecycleHandler(smartContext);
        }
        return activityLifecycleHandler;
    }

    @Override
    public void register(SmartApplication smartApplication) {
        smartApplication.registerActivityLifecycleCallbacks(this);
    }

    private static final int APP_STATUS_UNKNOWN = -1;
    private static final int APP_STATUS_LIVE = 0;

    private int appStatus = APP_STATUS_UNKNOWN;

    private boolean isForground = true;
    private int appCount = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ActivityStackManager.getInstance().addActivity(activity);

        if (appStatus == APP_STATUS_UNKNOWN) {
            appStatus = APP_STATUS_LIVE;
            startLauncherActivity(activity);
        }

        if (savedInstanceState != null && savedInstanceState.getBoolean("saveStateKey", false)) {
            Logger.log("localTime --> " + savedInstanceState.getLong("localTime"));
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        appCount++;
        if (!isForground) {
            isForground = true;
            Logger.log("app into forground");
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        // 弱引用持有当前 Activity 实例
        ActivityStackManager.getInstance().setCurrentActivity(activity);
        // Activity 页面栈方式
        ActivityStackManager.getInstance().setTopActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        appCount--;
        if (!isForgroundAppValue()) {
            isForground = false;
            Logger.log("app into background ");
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        outState.putBoolean("saveStateKey", true);
        outState.putLong("localTime", System.currentTimeMillis());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityStackManager.getInstance().removeActivity(activity);
    }

    private boolean isForgroundAppValue() {
        return appCount > 0;
    }

    private static void startLauncherActivity(Activity activity) {
        try {
            Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
            String launcherClassName = launchIntent.getComponent().getClassName();
            String className = activity.getComponentName().getClassName();

            if (TextUtils.isEmpty(launcherClassName) || launcherClassName.equals(className)) {
                return;
            }

            Logger.log("launcher ClassName --> " + launcherClassName);
            Logger.log("current ClassName --> " + className);

            launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(launchIntent);
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ActivityStackManager {

        private Stack<Activity> activities;

        private WeakReference<Activity> sCurrentActivityWeakRef;

        private static class InstanceHolder {
            private static final ActivityStackManager sInstance = new ActivityStackManager();
        }

        public static ActivityStackManager getInstance() {
            return InstanceHolder.sInstance;
        }

        public Activity getCurrentActivity() {
            Activity currentActivity = null;
            if (sCurrentActivityWeakRef != null) {
                currentActivity = sCurrentActivityWeakRef.get();
            }

            return currentActivity;
        }

        public void setCurrentActivity(Activity activity) {
            sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
        }

        public void addActivity(Activity activity) {
            if (activities == null) {
                activities = new Stack<Activity>();
            }

            if (activities.search(activity) == -1) {
                activities.push(activity);
            }
        }

        public void removeActivity(Activity activity) {
            if (activities != null && activities.size() > 0) {
                activities.remove(activity);
            }
        }

        public void setTopActivity(Activity activity) {
            if (activities != null && activities.size() > 0) {
                if (activities.search(activity) == -1) {
                    activities.push(activity);
                    return ;
                }

                int location = activities.search(activity);
                if (location != 1) {
                    activities.remove(activity);
                    activities.push(activity);
                }
            }
        }

        public Activity getTopActivity() {
            if (activities != null && activities.size() > 0) {
                return activities.peek();
            }

            return null;
        }

        public boolean isTopActivity(Activity activity) {
            return activity.equals(activities.peek());
        }

        public void finishTopActivity() {
            if (activities != null && activities.size() > 0) {
                Activity activity = activities.pop();
                if (activity != null) {
                    activity.finish();
                }
            }
        }

        public void finishAllActivity() {
            if (activities != null && activities.size() > 0) {
                while (!activities.empty()) {
                    Activity activity = activities.pop();
                    if (activity != null) {
                        activity.finish();
                    }
                }

                activities.clear();
                activities = null;
            }
        }

        public Stack<Activity> getActivityStack() {
            return activities;
        }
    }
}
