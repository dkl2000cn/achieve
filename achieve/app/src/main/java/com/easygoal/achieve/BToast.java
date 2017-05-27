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
     * 图标状态 不显示图标
     */
    private static final int TYPE_HIDE = -1;
    /**
     * 图标状态 显示√
     */
    private static final int TYPE_TRUE = 0;
    /**
     * 图标状态 显示×
     */
    private static final int TYPE_FALSE = 1;

    /**
     * 显示Toast
     *
     * @param context 上下文
     * @param text  显示的文本
     * @param time  显示时长
     * @param imgType 图标状态
     */

    /**
     * 初始化Toast
     *
     * @param context 上下文
     */

    public BToast(Context context) {
        super(context);
    }

    private void initToast(Context context, CharSequence text) {
        try {
            //cancelToast();

            toast = new BToast(context);

            // 获取LayoutInflater对象
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 由layout文件创建一个View对象
            View layout = inflater.inflate(R.layout.toast_view, null);

            // 吐司上的图片
            toast_img = (ImageView) layout.findViewById(R.id.toast_img);

            // 吐司上的文字
            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
            toast_text.setText(text);
            toast.setView(layout);
            toast.setGravity(Gravity.BOTTOM, 0, 70);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(Context context, CharSequence text, int time, int imgType) {
        // 初始化一个新的Toast对象
        initToast(context, text);

        // 设置显示时长
        if (time == Toast.LENGTH_LONG) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        // 判断图标是否该显示，显示√还是×
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
            // 动画
            /*
            ObjectAnimator.ofFloat(toast_img, "rotationY", 0, 360).setDuration(1700).start();
            */
        }

        // 显示Toast
        toast.show();
    }


    public void showToast(Context context, CharSequence text,int imgres) {
        // 初始化一个新的Toast对象
        initToast(context, text);

                toast_img.setImageDrawable(context.getResources().getDrawable(imgres));

            toast_img.setVisibility(View.VISIBLE);
            //AnimUtils.applyRocket(toast_img);
            // 动画
            /*
            ObjectAnimator.ofFloat(toast_img, "rotationY", 0, 360).setDuration(1700).start();
            */

        // 显示Toast
        toast.show();
    }

    /**
     * 隐藏当前Toast
     */
    public  void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }


}
