package com.easygoal.achieve;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

    public class RocketService extends Service {

        private WindowManager.LayoutParams params;
        private View view;
        private WindowManager wm;

        @Override
        public IBinder onBind(Intent intent) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void onCreate() {
            Log.d("rocket service","start");
            wm = (WindowManager) getSystemService(WINDOW_SERVICE);

            //初始化params（土司参数）
            initToastParams();

            showRocket();//打开小火箭
            super.onCreate();
        }
        /**
         * 初始化土司的参数
         */
        private void initToastParams() {
            // TODO Auto-generated method stub
            // XXX This should be changed to use a Dialog, with a Theme.Toast
            // defined that sets up the layout params appropriately.

            params = new WindowManager.LayoutParams();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;

            //对齐方式左上角
            params.gravity = Gravity.LEFT | Gravity.TOP;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
  /* | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE */
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            params.format = PixelFormat.TRANSLUCENT;

            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 土司天生不响应事件,改变类型。TYPE_SYSTEM_ALERT系统弹窗
            params.setTitle("Toast");
            Log.d("rocket service","init params");
        }

        private void closeRocket(){
            if (view != null) {
                wm.removeView(view);//移除小火箭
            }
        }

        private Handler handler = new Handler(){
            public void handleMessage(android.os.Message msg) {
                wm.updateViewLayout(view, params);//更新小火箭在屏幕中的位置，刷新位置。属于更新ui。在主线程执行（更新土司的位置）
            };
        };

        private void showRocket(){
            //小火箭的布局

            view = View.inflate(getApplicationContext(), R.layout.rocket, null);
            ImageView iv_rocket = (ImageView) view.findViewById(R.id.iv_rocket);
            //获取小火箭的动画背景

            wm.addView(view, params);//把小火箭加到窗体管理器
            Log.d("rocket service","add view");
            new Thread(){//发射火箭改变y轴属于耗时操作，更新火箭位置是更新UI操作
                public void run() {
                    for (int j = 0; j < view.getHeight(); ) {
                        SystemClock.sleep(50);//休眠50毫秒
                        params.y -= j;
                        j += 5;
                        handler.obtainMessage().sendToTarget();//参数y的值改变一次，发消息通知更新一次ui，更新一次土司的位置
                    }

                    //，发射完毕，关闭小火箭
                    stopSelf();//关闭服务，关闭当前自己服务。这个方法用在关闭自己服务里。触发onDestroy方法，从而触发这个方法里面的关闭小火箭
                };
            }.start();
        }


        @Override
        public void onDestroy() {
            // TODO Auto-generated method stub
            closeRocket();//关闭小火箭
            super.onDestroy();
        }

    }
