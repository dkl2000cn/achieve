package com.easygoal.achieve;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

//import com.umeng.analytics.MobclickAgent;
//import com.umeng.message.PushAgent;

/**
 * Created by Acer on 2017/3/13.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView();
        //PushAgent.getInstance(getApplicationContext()).onAppStart();
        initViews();
        initData();
        dobusiness();
        initListener();
    }
    //public abstract void setContentView();
    public abstract void dobusiness();
    public abstract void bindViews();
    public abstract void initViews();
    public abstract void initData();
    public abstract void initListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }
}
