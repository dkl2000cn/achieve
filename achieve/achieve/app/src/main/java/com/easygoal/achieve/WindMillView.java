package com.easygoal.achieve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Acer on 2017/5/19.
 */

public class WindMillView extends View {
    private int DEFAULT_COLOR;
    private static final int DEFAULT_WIDTH = 1;//画笔宽度1dp
    private static final float LENGTH_1 = 3;//下面三角形高度5dp
    private static final float ALPHA = (float) (Math.PI / 6);//旋转角度
    private static final int DELAY = 30;

    private Paint mPaint;
    private Path mPath;
    private float mAngle = 0;//旋转角度 通过改变角度实现旋转动画

    public WindMillView(Context context) {
        this(context, null);
    }

    public WindMillView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WindMillView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化
    private void init() {
        mPaint = new Paint();
        mPath = new Path();
        DEFAULT_COLOR=getContext().getResources().getColor(R.color.orange);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mAngle = (float) (mAngle + 3 * Math.PI / 360);

        float centerX = getWidth() / 2;
        float centerY = getHeight() * 4 / 9.0f;
        mPaint.setStrokeWidth(5.0f);
        //绘制风车的身体
        //canvas.drawLine(centerX, centerY, centerX - getWidth() / 10, getHeight(), mPaint);
        //canvas.drawLine(centerX, centerY, centerX + getWidth() / 10, getHeight(), mPaint);

        //绘制叶片 叶片由两个三角形组成 length1是下面三角形的高 length为整个叶片长度
        float length = (float) (10.0f * Math.sin(ALPHA)
                + getHeight() * 2 / 9.0f);
        float length1 = 10.0f;

        //分别计算叶片4个顶点的坐标 通过path来绘制
        float alpha = (float) (Math.PI / 2 - ALPHA + mAngle);
        mPath.moveTo(centerX, centerY);
        mPath.lineTo((float) (centerX + length1 * Math.cos(alpha)),
                (float) (centerY - length1 * Math.sin(alpha)));

        mPath.lineTo((float) (centerX + length * Math.cos(Math.PI / 2 + mAngle)),
                (float) (centerY - length * Math.sin(Math.PI / 2 + mAngle)));

        mPath.lineTo((float) (centerX + length1 * Math.cos(alpha + 2 * ALPHA)),
                (float) (centerY - length1 * Math.sin(alpha + 2 * ALPHA)));
        mPath.close();
        canvas.drawPath(mPath, mPaint);

        //叶片之间夹角是2/3PI
        alpha = (float) (Math.PI / 2 - ALPHA + mAngle + Math.PI * 2 / 3);
        mPath.moveTo(centerX, centerY);
        mPath.lineTo((float) (centerX + length1 * Math.cos(alpha)),
                (float) (centerY - length1 * Math.sin(alpha)));
        mPath.lineTo((float) (centerX + length * Math.cos(Math.PI / 2 + mAngle + Math.PI * 2 / 3)),
                (float) (centerY - length * Math.sin(Math.PI / 2 + mAngle + Math.PI * 2 / 3)));
        mPath.lineTo((float) (centerX + length1 * Math.cos(alpha + 2 * ALPHA)),
                (float) (centerY - length1 * Math.sin(alpha + 2 * ALPHA)));
        mPath.close();
        canvas.drawPath(mPath, mPaint);

        alpha = (float) (Math.PI / 2 - ALPHA + mAngle - Math.PI * 2 / 3);
        mPath.moveTo(centerX, centerY);
        mPath.lineTo((float) (centerX + length1 * Math.cos(alpha)),
                (float) (centerY - length1 * Math.sin(alpha)));
        mPath.lineTo((float) (centerX + length * Math.cos(Math.PI / 2 + mAngle - Math.PI * 2 / 3)),
                (float) (centerY - length * Math.sin(Math.PI / 2 + mAngle - Math.PI * 2 / 3)));
        mPath.lineTo((float) (centerX + length1 * Math.cos(alpha + 2 * ALPHA)),
                (float) (centerY - length1 * Math.sin(alpha + 2 * ALPHA)));
        mPath.close();
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        postInvalidateDelayed(DELAY);
    }
}
