package com.easygoal.achieve;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReciever extends BroadcastReceiver {
    private Context context;
	private String alarmName;
	final String Tags="AlarmReciever";
	
	public AlarmReciever(Context context) {
		// TODO Auto-generated constructor stub
	  this.context=context;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	
		
		if(intent.getAction().equals("short")){  
			Log.d(Tags,"alarm"+alarmName+" time is up,start the alarm...");
			Toast.makeText(context, "收到了!"+alarmName, Toast.LENGTH_LONG).show();
			//Intent i=new Intent(context,AlarmActivity.class);
			//Bundle bundle=new Bundle();
			//bundle.putString("alarmName", alarmName);
			//i.putExtras(bundle);
			//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
			//context.startActivity(i);
		}else{
			Toast.makeText(context, "没收到!", Toast.LENGTH_SHORT).show();
		}
	}
}

