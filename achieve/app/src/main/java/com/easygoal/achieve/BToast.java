package com.easygoal.achieve;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Acer on 2017/5/20.
 */

public class BToast extends Toast {
    BToast toast;
    ImageView toast_img;
    TextView toast_text;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    /**
     * ͼ��״̬ ����ʾͼ��
     */
    private static final int TYPE_HIDE = -1;
    /**
     * ͼ��״̬ ��ʾ��
     */
    private static final int TYPE_TRUE = 0;
    /**
     * ͼ��״̬ ��ʾ��
     */
    private static final int TYPE_FALSE = 1;

    /**
     * ��ʾToast
     *
     * @param context ������
     * @param text  ��ʾ���ı�
     * @param time  ��ʾʱ��
     * @param imgType ͼ��״̬
     */

    /**
     * ��ʼ��Toast
     *
     * @param context ������
     */

    public BToast(Context context) {
        super(context);
    }

    private void initToast(Context context, CharSequence text) {
        try {
            //cancelToast();

            toast = new BToast(context);

            // ��ȡLayoutInflater����
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // ��layout�ļ�����һ��View����
            View layout = inflater.inflate(R.layout.toast_view, null);

            // ��˾�ϵ�ͼƬ
            toast_img = (ImageView) layout.findViewById(R.id.toast_img);

            // ��˾�ϵ�����
            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
            toast_text.setText(text);
            toast.setView(layout);
            toast.setGravity(Gravity.BOTTOM, 0, 70);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(Context context, CharSequence text, int time, int imgType) {
        // ��ʼ��һ���µ�Toast����
        initToast(context, text);

        // ������ʾʱ��
        if (time == Toast.LENGTH_LONG) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        // �ж�ͼ���Ƿ����ʾ����ʾ�̻��ǡ�
        if (imgType == TYPE_HIDE) {
            toast_img.setVisibility(View.GONE);
        } else {
            if (imgType == TYPE_TRUE) {
                toast_img.setImageDrawable(context.getResources().getDrawable(R.drawable.rocket_64px));
            } else {
                toast_img.setImageDrawable(context.getResources().getDrawable(R.drawable.rocket_48px));
            }
            toast_img.setVisibility(View.VISIBLE);
            //AnimUtils.applyRocket(toast_img);
            // ����
            /*
            ObjectAnimator.ofFloat(toast_img, "rotationY", 0, 360).setDuration(1700).start();
            */
        }

        // ��ʾToast
        toast.show();
    }


    public void showToast(Context context, CharSequence text,int imgres) {
        // ��ʼ��һ���µ�Toast����
        initToast(context, text);

                toast_img.setImageDrawable(context.getResources().getDrawable(imgres));

            toast_img.setVisibility(View.VISIBLE);
            //AnimUtils.applyRocket(toast_img);
            // ����
            /*
            ObjectAnimator.ofFloat(toast_img, "rotationY", 0, 360).setDuration(1700).start();
            */

        // ��ʾToast
        toast.show();
    }

    /**
     * ���ص�ǰToast
     */
    public  void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }


}
