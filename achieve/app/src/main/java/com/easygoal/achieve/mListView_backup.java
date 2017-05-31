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
     * ��onTouch()�����н����жϣ����ɾ����ť�Ѿ���ʾ�ˣ��ͽ����Ƴ�����
     * ���ɾ����ťû����ʾ����ʹ��GestureDetector������ǰ����
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
            // ǿ��ת����int
            // ������pointToPosition(int x, int y)
            // Android �ٷ��Ľ����ǡ� Maps a point to a position in the list����
            // �����Ϊͨ��x��y��λ����ȷ�����listView�������item��λ�á�
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

            // һϵ�е�LayoutParams����
            itemLayout = (ViewGroup) getChildAt(selectedItem - getFirstVisiblePosition());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            // ���layout����itemLayout
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