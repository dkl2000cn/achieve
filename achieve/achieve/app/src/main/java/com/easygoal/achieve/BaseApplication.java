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
    // �쳣����
    protected boolean isNeedCaughtExeption = true;// �Ƿ񲶻�δ֪�쳣
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
        // ����1������������2��������ڵ�activity
        intent.setClassName(packgeName, packgeName + ".MainActivity");//���ó������
        restartIntent = PendingIntent.getActivity(getApplicationContext(), -1, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // �������ʱ�����߳�
        uncaughtExceptionHandler = new MyUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

    // �����������ڲ�������쳣
    private class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // ���������־
            // saveCatchInfo2File(ex);

            // 1���Ӻ�����Ӧ��
            AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);

            // �رյ�ǰӦ��
            finishAllActivity();
            //finishProgram();
        }
    };

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    // activity�������activity���б�
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // activity������������activity
    public void finishAllActivity() {
        for (Activity activity : activityList) {
            if (null != activity) {
                activity.finish();
            }
        }
    }

}
