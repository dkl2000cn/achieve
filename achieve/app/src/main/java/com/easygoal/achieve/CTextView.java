package com.easygoal.achieve;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * Created by cchen on 2014/9/2.
 */
public class CTextView extends ViewGroup {
    private Context context;

    public CTextView(Context context) {
        super(context);
        this.context = context;
    }

    public CTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void setText(String text, final Animation animation, int duration) {
        int time = 0;
        if(text != null && !text.isEmpty()) {
            char[] characters = text.toCharArray();
            for(char c : characters) {
                final TextView t = new TextView(context);
                //����������ַ�����ÿ���ַ�������һ��TextView�����������Ķ���
                t.setText(String.valueOf(c));
                t.setTextSize(28);
                Handler h = new Handler();
                //ÿ��durationʱ�䣬������һ��TextView�Ķ���
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addView(t);
                        t.setAnimation(animation);
                    }
                }, time);

                time += duration;

            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        // �����Զ����ViewGroup�������ӿؼ��Ĵ�С
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        // �����Զ���Ŀؼ�MyViewGroup�Ĵ�С
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        // ������������ͼ
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            // ��ȡ��onMeasure�м������ͼ�ߴ�
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();

            //�����Ǻ�������
            childView.layout(childLeft, 0, childLeft + measuredWidth, measureHeight);

            childLeft += measuredWidth;
        }
    }

    private int measureWidth(int pWidthMeasureSpec) {
        int result = 0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);// �õ�ģʽ
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);// �õ��ߴ�

        switch (widthMode) {
            /**
             * mode�������������ȡֵ�ֱ�ΪMeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
             * MeasureSpec.AT_MOST��
             *
             *
             * MeasureSpec.EXACTLY�Ǿ�ȷ�ߴ磬
             * �����ǽ��ؼ���layout_width��layout_heightָ��Ϊ������ֵʱ��andorid
             * :layout_width="50dip"������ΪFILL_PARENT�ǣ����ǿؼ���С�Ѿ�ȷ������������Ǿ�ȷ�ߴ硣
             *
             *
             * MeasureSpec.AT_MOST�����ߴ磬
             * ���ؼ���layout_width��layout_heightָ��ΪWRAP_CONTENTʱ
             * ���ؼ���Сһ�����ſؼ����ӿռ�����ݽ��б仯����ʱ�ؼ��ߴ�ֻҪ���������ؼ���������ߴ缴��
             * ����ˣ���ʱ��mode��AT_MOST��size�����˸��ؼ���������ߴ硣
             *
             *
             * MeasureSpec.UNSPECIFIED��δָ���ߴ磬����������࣬һ�㶼�Ǹ��ؼ���AdapterView��
             * ͨ��measure���������ģʽ��
             */
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = widthSize;
                break;
        }
        return result;
    }

    private int measureHeight(int pHeightMeasureSpec) {
        int result = 0;

        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = heightSize;
                break;
        }
        return result;
    }
}
