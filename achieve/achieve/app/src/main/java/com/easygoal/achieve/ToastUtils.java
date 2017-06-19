package com.easygoal.achieve;

/**
 * Created by Acer on 2017/5/20.
 */

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by �Գ�� on 2016/8/11.
 */
public class ToastUtils {

    private  Toast toast;
    private LinearLayout toastView;

    /**
     * �޸�ԭ���ֵ�Toast
     */
    public ToastUtils() {

    }

    /**
     * ��ȫ�Զ��岼��Toast
     * @param context
     * @param view
     */
    public ToastUtils(Context context, View view,int duration){
        toast=new Toast(context);
        toast.setView(view);
        toast.setDuration(duration);
    }

    /**
     * ��Toast������Զ���view
     * @param view
     * @param postion
     * @return
     */
    public  ToastUtils addView(View view,int postion) {
        toastView = (LinearLayout) toast.getView();
        toastView.addView(view, postion);

        return this;
    }

    /**
     * ����Toast���弰������ɫ
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public ToastUtils setToastColor(int messageColor, int backgroundColor) {
        View view = toast.getView();
        if(view!=null){
            TextView message=((TextView) view.findViewById(android.R.id.message));
            message.setBackgroundColor(backgroundColor);
            message.setTextColor(messageColor);
        }
        return this;
    }

    /**
     * ����Toast���弰����
     * @param messageColor
     * @param background
     * @return
     */
    public ToastUtils setToastBackground(int messageColor, int background) {
        View view = toast.getView();
        if(view!=null){
            TextView message=((TextView) view.findViewById(android.R.id.message));
            message.setBackgroundResource(background);
            message.setTextColor(messageColor);
        }
        return this;
    }

    /**
     * ��ʱ����ʾToast
     */
    public  ToastUtils Short(Context context, CharSequence message){
        if(toast==null||(toastView!=null&&toastView.getChildCount()>1)){
            toast= Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toastView=null;
        }else{
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        return this;
    }

    /**
     * ��ʱ����ʾToast
     */
    public ToastUtils Short(Context context, int message) {
        if(toast==null||(toastView!=null&&toastView.getChildCount()>1)){
            toast= Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toastView=null;
        }else{
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        return this;
    }

    /**
     * ��ʱ����ʾToast
     */
    public ToastUtils Long(Context context, CharSequence message){
        if(toast==null||(toastView!=null&&toastView.getChildCount()>1)){
            toast= Toast.makeText(context, message, Toast.LENGTH_LONG);
            toastView=null;
        }else{
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        return this;
    }

    /**
     * ��ʱ����ʾToast
     *
     * @param context
     * @param message
     */
    public ToastUtils Long(Context context, int message) {
        if(toast==null||(toastView!=null&&toastView.getChildCount()>1)){
            toast= Toast.makeText(context, message, Toast.LENGTH_LONG);
            toastView=null;
        }else{
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        return this;
    }

    /**
     * �Զ�����ʾToastʱ��
     *
     * @param context
     * @param message
     * @param duration
     */
    public ToastUtils Indefinite(Context context, CharSequence message, int duration) {
        if(toast==null||(toastView!=null&&toastView.getChildCount()>1)){
            toast= Toast.makeText(context, message,duration);
            toastView=null;
        }else{
            toast.setText(message);
            toast.setDuration(duration);
        }
        return this;
    }

    /**
     * �Զ�����ʾToastʱ��
     *
     * @param context
     * @param message
     * @param duration
     */
    public ToastUtils Indefinite(Context context, int message, int duration) {
        if(toast==null||(toastView!=null&&toastView.getChildCount()>1)){
            toast= Toast.makeText(context, message,duration);
            toastView=null;
        }else{
            toast.setText(message);
            toast.setDuration(duration);
        }
        return this;
    }

    /**
     * ��ʾToast
     * @return
     */
    public ToastUtils show (){
        toast.show();

        return this;
    }

    /**
     * ��ȡToast
     * @return
     */
    public Toast getToast(){
        return toast;
    }


}
