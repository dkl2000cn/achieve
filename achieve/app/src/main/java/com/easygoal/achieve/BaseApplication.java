package com.easygoal.achieve;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 2017/3/1.
 */

public class BaseApplication extends Application {
    private List<Activity> activityList;
    // 异常捕获
    protected boolean isNeedCaughtExeption = true;// 是否捕获未知异常
    private PendingIntent restartIntent;
    private MyUncaughtExceptionHandler uncaughtExceptionHandler;
    private String packgeName;

    @Override
    public void onCreate() {
        super.onCreate();
        activityList = new ArrayList<Activity>();
        packgeName = getPackageName();
        Log.d("start", packgeName );
        if (isNeedCaughtExeption) {
            cauchException();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    private void cauchException() {
        Intent intent = new Intent();
        // 参数1：包名，参数2：程序入口的activity
        intent.setClassName(packgeName, packgeName + ".MainActivity");//设置程序入口
        restartIntent = PendingIntent.getActivity(getApplicationContext(), -1, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // 程序崩溃时触发线程
        uncaughtExceptionHandler = new MyUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

    // 创建服务用于捕获崩溃异常
    private class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // 保存错误日志
            // saveCatchInfo2File(ex);

            // 1秒钟后重启应用
            AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);

            // 关闭当前应用
            finishAllActivity();
            //finishProgram();
        }
    };

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    // activity管理：添加activity到列表
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // activity管理：结束所有activity
    public void finishAllActivity() {
        for (Activity activity : activityList) {
            if (null != activity) {
                activity.finish();
            }
        }
    }

}
