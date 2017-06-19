package com.easygoal.achieve;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class mListView_backup extends ListView implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;

    private OnDeleteListener listener;

    private View layout;

    private View topTextVeiw;

    private View deleteTextView;

    private ViewGroup itemLayout;

    private int selectedItem;

    private boolean isDeleteShown;

    public mListView_backup(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(getContext(), this);
        setOnTouchListener(this);
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
        if (isDeleteShown) {
            itemLayout.removeView(layout);
            layout = null;
            isDeleteShown = false;
            return false;
        } else {
            return gestureDetector.onTouchEvent(event);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (!isDeleteShown) {
            // 强制转换成int
            // 首先是pointToPosition(int x, int y)
            // Android 官方的解释是” Maps a point to a position in the list”，
            // 可理解为通过x和y的位置来确定这个listView里面这个item的位置。
            selectedItem = pointToPosition((int) e.getX(), (int) e.getY());
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

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
        if (!isDeleteShown && (e1.getX()-e2.getX()>15) && Math.abs(velocityX) > Math.abs(velocityY)) {
            layout = LayoutInflater.from(getContext()).inflate(R.layout.delete_layout, null);
            topTextVeiw = layout.findViewById(R.id.tv_top);
            deleteTextView = layout.findViewById(R.id.tv_delete);
            deleteTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemLayout.removeView(layout);
                    layout = null;
                    //deleteTextView=null;
                    isDeleteShown = false;
                    listener.onDelete(selectedItem);
                }
            });

            topTextVeiw.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemLayout.removeView(layout);
                    layout = null;
                    //deleteTextView=null;
                    isDeleteShown = false;
                    listener.onTop(selectedItem);
                }
            });

            // 一系列的LayoutParams设置
            itemLayout = (ViewGroup) getChildAt(selectedItem - getFirstVisiblePosition());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            // 添加layout进入itemLayout
            itemLayout.addView(layout, params);
            isDeleteShown = true;

        }

        return false;
    }

    public interface OnDeleteListener {

        void onDelete(int index);

        void onTop(int index);

    }
}