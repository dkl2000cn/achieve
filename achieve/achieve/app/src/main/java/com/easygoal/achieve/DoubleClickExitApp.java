package com.easygoal.achieve;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;
public class DoubleClickExitApp {

	   private Context mcontext;
	   /**是否是再次点击back键*/
	   private boolean isOnKeyBack;
	   private Handler mHandler;
	   /**退出提示Toast*/
	   private Toast mExitToast;

	   public DoubleClickExitApp(Context context) {
	      this.mcontext = context;
	      this.mHandler = new Handler(Looper.getMainLooper());
	   }

	   /**所在Activity中的点击返回事件处理*/
	   public boolean onKeyDown(int keyCode, KeyEvent event) {
	      if(keyCode != KeyEvent.KEYCODE_BACK) {//如果不是点击的返回键，则返回false
	         return false;
	      }
	      if(isOnKeyBack) {
	         mHandler.removeCallbacks(onBackExitRunnable);
	         if(mExitToast != null){
	            mExitToast.cancel();
	         }
	         /**直接退出*/
	       //  ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
	         //AppManager.getAppManager().AppExit(mcontext);
	         return true;
	      } else {
	         isOnKeyBack = true;
	         if(mExitToast == null) {
	            mExitToast = Toast.makeText(mcontext, "再按一次返回键退出应用", Toast.LENGTH_SHORT);
	         }
	         mExitToast.show();
	         mHandler.postDelayed(onBackExitRunnable, 2000);
	         return true;
	      }
	   }

	   private Runnable onBackExitRunnable = new Runnable() {

	      @Override
	      public void run() {
	         isOnKeyBack = false;
	         if(mExitToast != null){
	            mExitToast.cancel();
	         }
	      }
	   };
	}