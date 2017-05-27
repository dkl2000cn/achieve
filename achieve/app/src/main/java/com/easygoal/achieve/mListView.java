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
     * 在onTouch()方法中进行判断，如果删除按钮已经显示了，就将它移除掉，
     * 如果删除按钮没有显示，就使用GestureDetector来处理当前手势
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
                    //双击事件
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
     * 当手指按下时，会调用OnGestureListener的onDown()方法，
     * 在这里通过pointToPosition()方法来判断出当前选中的是ListView的哪一行。
     * 当手指快速滑动时，会调用onFling()方法，在这里会去加载delete_button.xml这个布局，
     * 然后将删除按钮添加到当前选中的那一行item上。
     * 给删除按钮添加了一个点击事件，当点击了删除按钮时就会回调onDeleteListener的onDelete()方法，
     * 给置顶按钮添加一个点击事件，当点击了置顶按钮时就会回调onDeleteListener的onTop()方法
     * 在回调方法中应该去处理具体的置顶删除操作。
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // 简单理解：鼠标手势相当于一个向量（当然有可能手势是曲线），
        // e1为向量的起点，e2为向量的终点，velocityX为向量水平方向的速度，velocityY为向量垂直方向的速度
        Log.d("gestureDetector", "onFling");
        //Toast.makeText(getContext(), "向右手势" + offX, Toast.LENGTH_SHORT).show();
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

                //Toast.makeText(getContext(), "向左手势" + offX+selectedItem, Toast.LENGTH_SHORT).show();
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
            //测量子view的宽高，？不测量，右侧布局会不显示，这里有点疑问
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