package com.easygoal.achieve;

import android.view.MotionEvent;
import android.view.View;

class OnDoubleClickListener implements View.OnTouchListener {
	
	
	private DoubleClickCallback mCallback;


    public interface DoubleClickCallback {
        void onDoubleClick();
    }

    public OnDoubleClickListener(DoubleClickCallback callback) {
        super();
        this.mCallback = callback;
    }
	  
    @Override  
    public boolean onTouch(View v, MotionEvent event) {  
        if(MotionEvent.ACTION_DOWN == event.getAction()){  
        	int count = 0;
			count++;  
            long firClick = 0;
			if(count == 1){  
                firClick = System.currentTimeMillis();  
                  
            } else if (count == 2){  
                long secClick = System.currentTimeMillis();  
                if(secClick - firClick < 1000){  
                    //Ë«»÷ÊÂ¼þ  
                    return false;
                }  
                count = 0;  
                firClick = 0;  
                secClick = 0;  
                  
            }  
        }  
        return true;  
    } 
}   