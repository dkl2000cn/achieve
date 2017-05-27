package com.easygoal.achieve;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * 项目名称：achieve
 * 类描述：
 * 创建人：Acer
 * 创建时间：2017/5/28 20:21
 * 修改人：Acer
 * 修改时间：2017/5/28 20:21
 * 修改备注：
 */

public class MusicService extends Service {
    private MediaPlayer mp;
    private MusicBinder mb;
    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return mb;
    }

    public MusicService() {
        mb= new MusicBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp= MediaPlayer.create(getBaseContext(),R.raw.success3);
        //mp.setLooping(true);
        //mp.stop();
        LogUtils.d("mp build");
        mp.start();
        LogUtils.d("mp start");
        return super.onStartCommand(intent, flags, startId);
    }

    class MusicBinder extends Binder{
        public void saySucess(){
            Toast.makeText(getApplicationContext(), "光荣完成", Toast.LENGTH_SHORT).show();
        }

    }
}
