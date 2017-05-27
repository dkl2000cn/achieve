package com.easygoal.achieve;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Acer on 2017/5/20.
 */

public class RingView extends View {

     Paint paint1;
     Paint paint2;
     Paint paint0;
     Context context;
     int ringColor;
     int arrowColor;
    public RingView(Context context) {
        super(context);
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
         this.context=context;
         TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.RingView);
         ringColor=a.getColor(R.styleable.RingView_RingColor,003344);
         arrowColor=a.getColor(R.styleable.RingView_ArrowColor,003344);
         this.paint1=new Paint();
         this.paint2=new Paint();
         //this.paint0=new Paint();
         paint1.setAntiAlias(true);
         paint1.setStrokeWidth(4);
         paint1.setColor(ringColor);
         paint1.setStyle(Paint.Style.STROKE);
         paint2.setAntiAlias(true);
         paint2.setStrokeWidth(4);
         paint2.setColor(arrowColor);
         paint2.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width=0;
        int height=0 ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
             width = widthSize;
        } else
        {    width = widthSize;
            /*
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);
            float textWidth = mBounds.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
            */
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
             height = heightSize;
        } else
        {    height = heightSize;
            /*
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);
            float textHeight = mBounds.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
            */
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(final Canvas canvas) {

        final int center = getMeasuredWidth()/2;
        final int ringInterval=getMeasuredWidth()/20;
        final int arrowLength=getMeasuredWidth()/3;
        int cx=200;
        int cy=200;
        int radius=ringInterval;
        for (int i=0;i<9;i++) {
            canvas.drawCircle(center , center , radius+ringInterval*i, paint1);
        }
                Path path = new Path();
                path.moveTo(center, center);
                path.lineTo(center - 15, center + 15);
                path.moveTo(center, center);
                path.lineTo(center + 15, center + 15);
                path.moveTo(center, center);
                path.lineTo(center, center + arrowLength);
                canvas.drawPath(path, paint2);
                //canvas.drawText("hit!",center,center,paint2);

        super.onDraw(canvas);
    }
}
