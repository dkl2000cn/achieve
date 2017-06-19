package com.easygoal.achieve;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

/**
 * Created by Acer on 2017/5/20.
 */

public class AnimUtils extends Animation {
    public static float thisDelta = 0.05f;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void applyRipple(final View v) {
        v.post(new Runnable()
        {
            @Override
            public void run(){
                //create your anim here
                Animator animator = ViewAnimationUtils.createCircularReveal(v, v.getMeasuredWidth()/2,
                        v.getMeasuredHeight()/2,
                        0,
                        (float) Math.sqrt((v.getMeasuredWidth() * v.getMeasuredWidth() +
                                v.getMeasuredHeight() * v.getMeasuredHeight())));

                animator.setDuration(5000);
                animator.start();
            }
        });
    }

     public void applyTransformation(float interpolatedTime, Transformation t) {
            t.getMatrix().setTranslate(
                    (float) Math.sin(interpolatedTime * 50) * 8,
                    (float) Math.sin(interpolatedTime * 50) * 8
            );// 50??????????8?????????
            super.applyTransformation(interpolatedTime, t);
        }

        public static void applyRotation(View v){
            Animation anim = null;
            anim = new RotateAnimation(0.0f,+360.0f);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(3000);
            v.startAnimation(anim);
        }

    public static void applyJump(View v) {
        float originalY = 0;
        float finalY =-20;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(0, 0, originalY,
                finalY));
        animationSet.setDuration(500);
        animationSet.setRepeatCount(Animation.INFINITE);
        animationSet.setRepeatMode(Animation.REVERSE);
        //animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        //animationSet.setFillAfter(true);
        AnimationListener restartLister = new AnimationListener() {
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
        animationSet.setAnimationListener(restartLister);
        v.startAnimation(animationSet);
        animationSet.start();
    }

    public static void applyRocket(View v) {
        float originalY =500;
        float finalY =0;
        v.setVisibility(View.VISIBLE);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(v.getX(), v.getX(), originalY,
                finalY));
        animationSet.setInterpolator(new AccelerateInterpolator(2f));

        animationSet.setRepeatCount(Animation.INFINITE);
        animationSet.setRepeatMode(Animation.REVERSE);
        //animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        //animationSet.setFillAfter(true);
        AnimationListener restartLister = new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        };
        animationSet.setDuration(1500);
        animationSet.setAnimationListener(restartLister);
        v.startAnimation(animationSet);
        animationSet.start();
    }
    /*
    public static void applyFlower(final View v) {
        float originalY =1000;
        float finalY =0;
        v.setVisibility(View.VISIBLE);
        final ValueAnimator animationSet=new ValueAnimator();
        animationSet.setDuration(3000);
        animationSet.setObjectValues(new PointF(0, 0));
        //animationSet.setInterpolator(new AccelerateInterpolator(2f));
        animationSet.setRepeatCount(Animation.INFINITE);
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animationSet.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });
        //animationSet.setFillAfter(true);
        animationSet.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                v.setX(point.x);
                v.setY(point.y);
            }
        });

        animationSet.start();
    }*/

    public static void applyFlower(final View v) {
        //float originalY =1000;
        //float finalY =0;
        final float originalY =v.getY();
        final float originalHeight =v.getHeight();
        final float originalPivotY =v.getPivotY();
        final float originalPivotX =v.getPivotX();
        final LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) v.getLayoutParams();
//?????????????????
        //final int width = params.width;
        //final int height = params.height;//????????????????
       // int width ;
        //int height;//????????????????
        //imageView.setLayoutParams(params);
        int intw=View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int inth=View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        v.measure(intw, inth);
        final int width = v.getMeasuredWidth();
        final int height = v.getMeasuredHeight();
        Log.d("flower","w"+width+"h"+height);
        v.setVisibility(View.VISIBLE);
        final ValueAnimator animationSet=ValueAnimator.ofFloat(0.4f,1.0f);
        animationSet.setDuration(1500);

        //animationSet.setTarget(v);
        //animationSet.setObjectValues(new PointF(0, 0));
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        //animationSet.setRepeatCount(Animation.INFINITE);
        //animationSet.setInterpolator(new LinearInterpolator());
        /*
        animationSet.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });*/
        //animationSet.setFillAfter(true);

        animationSet.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                //v.setMinimumHeight((int)(scale*originalHeight));
                //v.setScaleY(scale);
                //v.setScaleX(scale);
                //params.height=(int)(params.height*scale);//????????????????

                //v.requestLayout();
                //
               // v.requestLayout();
                //v.invalidate();


                //v.setY(originalY-(int)(height*scale));
                //v.setY(originalY);
                //v.setScaleY(scale);
                //v.setMinimumHeight(height);
                v.setScaleX(scale);
                v.setScaleY(scale);
                Log.d("flower",""+scale);
                //v.setY(scale);
            }
        });

        animationSet.start();
    }

    public static void applyArrow(View v) {
          v.setVisibility(View.VISIBLE);
          ObjectAnimator oa1=ObjectAnimator.ofFloat(v,"rotation",0f,360f);
          oa1.setDuration(1500);

          ObjectAnimator oa2=ObjectAnimator.ofInt(v,
                "backgroundColor",  Color.DKGRAY,Color.WHITE
                 );
           oa2.setEvaluator(new ArgbEvaluator());
           oa2.setDuration(1500);


           ObjectAnimator oa3=ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
           oa3.setInterpolator(new DecelerateInterpolator());
           oa3.setDuration(1500);//设置动画时间

           AnimatorSet set = new AnimatorSet() ;
           set.play(oa1).with(oa2).with(oa3);
           set.start();

    }
    public static void applyRocket(ViewGroup container, Context context) {
        float originalY =1000;
        float finalY =-10;
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //layoutParams.gravity= Gravity.BOTTOM;
        imageView.setLayoutParams(layoutParams);
        imageView.layout(500,500,500,500);
        //Bitmap bm_rocket = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket_64px);
        imageView.setImageResource(R.drawable.rocket_64px);
        imageView.setBackgroundColor(Color.WHITE);
        container.addView(imageView);
        imageView.bringToFront();
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(500, 500, originalY,
                finalY));
        animationSet.setInterpolator(new AccelerateInterpolator(2f));

        animationSet.setRepeatCount(Animation.INFINITE);
        animationSet.setRepeatMode(Animation.REVERSE);
        //animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        //animationSet.setFillAfter(true);
        AnimationListener restartLister = new AnimationListener() {
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
        animationSet.setDuration(1500);
        animationSet.setAnimationListener(restartLister);
        imageView.startAnimation(animationSet);
        animationSet.start();
    }

    public static void applyShimmer(View v) {
        float originalY = 0;
        float finalY =-20;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new AlphaAnimation(1,0));
        animationSet.setDuration(1000);
        animationSet.setRepeatCount(Animation.INFINITE);
        animationSet.setRepeatMode(Animation.REVERSE);
        //animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        //animationSet.setFillAfter(true);
        AnimationListener restartLister = new AnimationListener() {
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
        animationSet.setAnimationListener(restartLister);
        v.startAnimation(animationSet);
        animationSet.start();
    }

       public static void applyTranslation(View v){
            int count=10;
            TranslateAnimation animation = new TranslateAnimation(count*100, -100+count*100, 0, 0);
            animation.setInterpolator(new LinearInterpolator());
            animation.setDuration(400);
            animation.setFillAfter(true);
            v.startAnimation(animation);
            count--;
        }


    public static void showViewFlipper(Context context,ViewFlipper vf){
        vf.addView(View.inflate(context,R.layout.merge_noticeitem, vf));
        vf.addView(View.inflate(context,R.layout.merge_noticeitem2, vf));
        vf.addView(View.inflate(context,R.layout.merge_noticeitem3, vf));
    }

}
