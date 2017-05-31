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
 * ��Ŀ���ƣ�achieve
 * ��������
 * �����ˣ�Acer
 * ����ʱ�䣺2017/5/28 20:21
 * �޸��ˣ�Acer
 * �޸�ʱ�䣺2017/5/28 20:21
 * �޸ı�ע��
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
            Toast.makeText(getApplicationContext(), "�������", Toast.LENGTH_SHORT).show();
        }

    }
}
