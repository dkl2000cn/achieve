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

            //��ʼ��params����˾������
            initToastParams();

            showRocket();//��С���
            super.onCreate();
        }
        /**
         * ��ʼ����˾�Ĳ���
         */
        private void initToastParams() {
            // TODO Auto-generated method stub
            // XXX This should be changed to use a Dialog, with a Theme.Toast
            // defined that sets up the layout params appropriately.

            params = new WindowManager.LayoutParams();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;

            //���뷽ʽ���Ͻ�
            params.gravity = Gravity.LEFT | Gravity.TOP;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
  /* | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE */
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            params.format = PixelFormat.TRANSLUCENT;

            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// ��˾��������Ӧ�¼�,�ı����͡�TYPE_SYSTEM_ALERTϵͳ����
            params.setTitle("Toast");
            Log.d("rocket service","init params");
        }

        private void closeRocket(){
            if (view != null) {
                wm.removeView(view);//�Ƴ�С���
            }
        }

        private Handler handler = new Handler(){
            public void handleMessage(android.os.Message msg) {
                wm.updateViewLayout(view, params);//����С�������Ļ�е�λ�ã�ˢ��λ�á����ڸ���ui�������߳�ִ�У�������˾��λ�ã�
            };
        };

        private void showRocket(){
            //С����Ĳ���

            view = View.inflate(getApplicationContext(), R.layout.rocket, null);
            ImageView iv_rocket = (ImageView) view.findViewById(R.id.iv_rocket);
            //��ȡС����Ķ�������

            wm.addView(view, params);//��С����ӵ����������
            Log.d("rocket service","add view");
            new Thread(){//�������ı�y�����ں�ʱ���������»��λ���Ǹ���UI����
                public void run() {
                    for (int j = 0; j < view.getHeight(); ) {
                        SystemClock.sleep(50);//����50����
                        params.y -= j;
                        j += 5;
                        handler.obtainMessage().sendToTarget();//����y��ֵ�ı�һ�Σ�����Ϣ֪ͨ����һ��ui������һ����˾��λ��
                    }

                    //��������ϣ��ر�С���
                    stopSelf();//�رշ��񣬹رյ�ǰ�Լ���������������ڹر��Լ����������onDestroy�������Ӷ����������������Ĺر�С���
                };
            }.start();
        }


        @Override
        public void onDestroy() {
            // TODO Auto-generated method stub
            closeRocket();//�ر�С���
            super.onDestroy();
        }

    }
