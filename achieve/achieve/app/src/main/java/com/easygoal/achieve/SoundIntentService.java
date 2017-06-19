package com.easygoal.achieve;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * 项目名称：achieve
 * 类描述：
 * 创建人：Acer
 * 创建时间：2017/5/28 18:47
 * 修改人：Acer
 * 修改时间：2017/5/28 18:47
 * 修改备注：
 */

public class SoundIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private String name;
    private int res;
    private MediaPlayer mp;
    private Context context;

    public SoundIntentService(String name) {
        super(name);

    }
    public  SoundIntentService() {
        super("hello");
// TODO Auto-generated constructor stub
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mp=MediaPlayer.create(getBaseContext(),R.raw.success1);
        mp.setLooping(true);
        //mp.stop();
        LogUtils.d("mp build");
        mp.start();
        LogUtils.d("mp start");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp!=null){
            mp.release();
            LogUtils.d("mp end");
        }
    }
}
