package com.easygoal.achieve;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Acer on 2017/5/20.
 */

public class DisplayUtils {
    /**
     * 将px装换成dp，保证尺寸不变
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue){
        float density = context.getResources().getDisplayMetrics().density;//得到设备的密度
        return (int) (pxValue/density+0.5f);
    }
    public static int dp2px(Context context,float dpValue){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*density+0.5f);
    }
    public static int px2sp(Context context,float pxValue){
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;//缩放密度
        return (int) (pxValue/scaleDensity+0.5f);
    }
    public static int sp2px(Context context,float spValue) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*scaleDensity+0.5f);
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context)
    {

        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    public static void toastRocket(){

    }


    public static void toastFullScreen(Context context){
        Toast toast = Toast.makeText(context, null,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout)toast.getView();

        // Get the screen size with unit pixels.
        //WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        //DisplayMetrics outMetrics = new DisplayMetrics();
        //wm.getDefaultDisplay().getMetrics(outMetrics);
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(outMetrics.widthPixels,
                outMetrics.heightPixels);
        vlp.setMargins(0, 0, 0, 0);
        toastView.setLayoutParams(vlp);

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        //Bitmap bm_rocket = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket_64px);
        imageView.setImageResource(R.drawable.rocket_64px);
        imageView.bringToFront();
        AnimationSet animationSet = new AnimationSet(true);
        float originalY =1000;
        float finalY =-10;
        animationSet.addAnimation(new TranslateAnimation(500, 500, originalY,
                finalY));
        animationSet.setInterpolator(new AccelerateInterpolator(2f));

        animationSet.setRepeatCount(Animation.INFINITE);
        animationSet.setRepeatMode(Animation.REVERSE);
        //animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        //animationSet.setFillAfter(true);
        Animation.AnimationListener restartLister = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animation.reset();
                animation.start();
            }
        };
        animationSet.setDuration(3000);
        animationSet.setAnimationListener(restartLister);
        imageView.startAnimation(animationSet);
        animationSet.start();
        /*
        TextView tv = new TextView(this);
        LayoutParams vlp = new LayoutParams(outMetrics.widthPixels,
                outMetrics.heightPixels);
        vlp.setMargins(0, 0, 0, 0);
        tv.setLayoutParams(vlp);
        tv.setText("Hello Toast! I am full screen now.");
        tv.setGravity(Gravity.CENTER);
        */
        //new ToastUtils(context,imageView, Toast.LENGTH_LONG).show();
        toastView.addView(imageView);
        toast.show();
    }
}
