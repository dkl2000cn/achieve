package com.easygoal.achieve;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * ��Ŀ���ƣ�achieve
 * ��������
 * �����ˣ�Acer
 * ����ʱ�䣺2017/5/28 22:23
 * �޸��ˣ�Acer
 * �޸�ʱ�䣺2017/5/28 22:23
 * �޸ı�ע��
 */

public class ServiceUtils {

    public static void playMusicService(Context context) {
        final Intent musicintent;
        ServiceConnection sc = new ServiceConnection() {
            MusicService.MusicBinder musicServiceBinder;

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicServiceBinder = (MusicService.MusicBinder) service;
                musicServiceBinder.saySucess();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                musicServiceBinder = null;
            }
        };

        musicintent = new Intent(context, MusicService.class);
        context.bindService(musicintent, sc, Context.BIND_AUTO_CREATE);
        LogUtils.d("intent ready");
        context.startService(musicintent);
        LogUtils.d("intent done");
    }
}
