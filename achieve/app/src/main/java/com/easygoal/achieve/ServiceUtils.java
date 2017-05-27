package com.easygoal.achieve;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 项目名称：achieve
 * 类描述：
 * 创建人：Acer
 * 创建时间：2017/5/28 22:23
 * 修改人：Acer
 * 修改时间：2017/5/28 22:23
 * 修改备注：
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
