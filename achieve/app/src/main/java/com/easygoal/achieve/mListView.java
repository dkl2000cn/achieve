package com.easygoal.achieve;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static anetwork.channel.monitor.Monitor.start;

public class mListView extends ListView implements View.OnTouchListener,GestureDetector.OnDoubleTapListener,GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;
    private OnDeleteListener listener;
    private int actionflag;
    private double lastX;
    private double lastY;
    private double lastLeft;
    private double lastTop;
    private double lastRight;
    private double lastBottom;
    private View layout;
    private int m_max_scrollX;
    private View topTextVeiw;

    private View deleteTextView;

    private ViewGroup itemLayout;

    private int selectedItem;

    private boolean isDeleteShown;

    public mListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(getContext(), this);
        setOnTouchListener(this);
        actionflag=-1;
    }

    public void setOnDeleteListener(OnDeleteListener l) {
        listener = l;
    }
    /**
     * ��onTouch()�����н����жϣ����ɾ����ť�Ѿ���ʾ�ˣ��ͽ����Ƴ�����
     * ���ɾ����ťû����ʾ����ʹ��GestureDetector������ǰ����
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
            lastX=event.getX();
            lastY=event.getY();
        /*
        if(MotionEvent.ACTION_DOWN == event.getAction()){
            int count = 0;
            count++;
            long firClick = 0;
            if(count == 1){
                firClick = System.currentTimeMillis();

            } else if (count == 2){
                long secClick = System.currentTimeMillis();
                if(secClick - firClick < 2000){
                    //˫���¼�
                    listener.onDoubleChick(selectedItem);
                    return true;
                }
                count = 0;
                firClick = 0;
                secClick = 0;

            }
        }*/
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("gestureDetector", "onDown");
            selectedItem = pointToPosition((int) e.getX(), (int) e.getY());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("gestureDetector", "onShowPress");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
         Log.d("gestureDetector", "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("gestureDetector", "onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("gestureDetector", "onLongPress");
        if (selectedItem>=0&&actionflag<1) {
            listener.onShow(selectedItem);
            actionflag=0;
        }
    }

    /**
     * ����ָ����ʱ�������OnGestureListener��onDown()������
     * ������ͨ��pointToPosition()�������жϳ���ǰѡ�е���ListView����һ�С�
     * ����ָ���ٻ���ʱ�������onFling()�������������ȥ����delete_button.xml������֣�
     * Ȼ��ɾ����ť��ӵ���ǰѡ�е���һ��item�ϡ�
     * ��ɾ����ť�����һ������¼����������ɾ����ťʱ�ͻ�ص�onDeleteListener��onDelete()������
     * ���ö���ť���һ������¼�����������ö���ťʱ�ͻ�ص�onDeleteListener��onTop()����
     * �ڻص�������Ӧ��ȥ���������ö�ɾ��������
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // ����⣺��������൱��һ����������Ȼ�п������������ߣ���
        // e1Ϊ��������㣬e2Ϊ�������յ㣬velocityXΪ����ˮƽ������ٶȣ�velocityYΪ������ֱ������ٶ�
        Log.d("gestureDetector", "onFling");
        //Toast.makeText(getContext(), "��������" + offX, Toast.LENGTH_SHORT).show();
        //

        final int offX = (int) (e2.getX() - e1.getX());
       /*
        itemLayout = (ViewGroup) getChildAt(selectedItem - getFirstVisiblePosition());
        if ( itemLayout!=null) {
            //itemLayout.scrollBy(offX,0);
            //itemLayout.scrollBy((int)(-offX),0);
            itemLayout.setBackgroundResource(R.color.gray);
         }

        if (offX<-10&&offX>-200){

        }
        if ((offX > 100) ) {
            itemLayout = (ViewGroup) getChildAt(selectedItem - getFirstVisiblePosition());
            if ( itemLayout!=null) {
                //itemLayout.scrollBy(offX,0);
                /*
                itemLayout.layout(getLeft() + offX,
                        getTop(),
                        getRight() + offX,
                        getBottom()
                );
                if (selectedItem >= 0) {
                    listener.onClose(selectedItem);
                }
            }
            actionflag=1;
            return true;

        }
         */
        if ((offX < -100)) {

                //Toast.makeText(getContext(), "��������" + offX+selectedItem, Toast.LENGTH_SHORT).show();
                    itemLayout = (ViewGroup) getChildAt(selectedItem - getFirstVisiblePosition());

                if ( itemLayout!=null) {
                    itemLayout.setBackgroundResource(R.color.lightgray);
                    listener.onDelete(selectedItem);
                    final AnimatorSet animationSet = new AnimatorSet();
                    ObjectAnimator anim = ObjectAnimator.ofFloat(itemLayout, "translationX", 0, -DisplayUtils.getScreenWidth(getContext()))
                            .setDuration(500)
                            ;
                    ObjectAnimator anim2 = ObjectAnimator.ofFloat(itemLayout, "translationX", 0, 0)
                            .setDuration(50)
                            ;
                           animationSet.play(anim2).after(anim);

                    animationSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (selectedItem >= 0) {
                                itemLayout.setBackgroundResource(R.color.mTextColor2);
                                //listener.onDelete(selectedItem);
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animationSet.start();
                    /*
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //itemLayout.scrollBy(offX,0);
                            //itemLayout.scrollBy(-DisplayUtils.getScreenWidth(getContext()), 0);
                            if (selectedItem >= 0) {
                                itemLayout.setBackgroundResource(R.color.mTextColor2);
                                listener.onDelete(selectedItem);
                            }
                        }
                    },50);*/
                    //itemLayout.scrollTo((int)lastX,(int)lastY);
                    /*
                    itemLayout.setBackgroundResource(R.color.lightgray);
                    itemLayout.scrollBy((int)(DisplayUtils.getScreenWidth(getContext())),0);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //itemLayout.scrollBy(offX,0);
                            itemLayout.scrollBy(-DisplayUtils.getScreenWidth(getContext()), 0);
                            if (selectedItem >= 0) {
                                itemLayout.setBackgroundResource(R.color.mTextColor2);
                                listener.onDelete(selectedItem);
                            }
                        }
                    },200);
                    actionflag = 2;
                    */
                }
                /*
                itemLayout.layout(0,
                        0,
                        0,
                        0);*/
                //TaskTool.quickdelete(getContext(),ts);

            }


        return false;
    }

    @Override    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            //������view�Ŀ�ߣ������������Ҳ಼�ֻ᲻��ʾ�������е�����
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
            if (i == 1) {
                m_max_scrollX = getChildAt(i).getMeasuredWidth();
            }
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d("gestureDetector", "onSingleTapConfirmed");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("gestureDetector", "onDoubleTap");
        listener.onDoubleChick(selectedItem);

        //Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d("gestureDetector", "onDoubleTapEvent");
        listener.onDoubleChick(selectedItem);
        return false;
    }

    public interface OnDeleteListener {

        void onDelete(int index);

        void onClose(int index);

        void onShow(int index);

        void onDoubleChick(int index);

    }
}