package com.easygoal.achieve;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

@TargetApi(23)
public class Activity_RequestPermission extends BaseActivity {
    private String permisson;
	
	public Activity_RequestPermission() {
		// TODO Auto-generated constructor stub
		this.permisson="Manifest.permission.READ_PHONE_STATE";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	   
		int checkPermission = ContextCompat.checkSelfPermission(Activity_RequestPermission.this,permisson);
        //if (checkPermission != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(Activity_RequestPermission.this, new String[]{permisson}, 1);
       /*    
            	return ;
        }else {
        
        }
       */ 
	}

	@Override
	public void dobusiness() {

	}

	@Override
	public void bindViews() {

	}

	@Override
	public void initViews() {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {

	}

	/*
	 @Override
	    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
	        switch (requestCode) {
	            case 1:
	                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
	                	TaskData.user=getPhoneNumber();
	                    //mLocationClient.start();
	                } else {
	                	TaskData.user="test";
	                    Log.d("TTTT", "啊偶，被拒绝了，少年不哭，站起来撸");
	                }
	                break;
	            default:
	                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	        }
	    }
*/
	 private String getPhoneNumber(){  
		    TelephonyManager mTelephonyMgr;  
		    mTelephonyMgr = (TelephonyManager)  getSystemService(Context.TELEPHONY_SERVICE);   
		    String pn;
		    if (!TextUtils.isEmpty(mTelephonyMgr.getLine1Number())){
		      pn=mTelephonyMgr.getLine1Number();
		    }else{
		      pn="test";
		    }
		    return pn;	
		} 
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//IntentFilter filter=new IntentFilter("short");
		//AlarmReciever reciever = new AlarmReciever(getApplication());
		//getApplication().registerReceiver(reciever,filter);
	}

}
