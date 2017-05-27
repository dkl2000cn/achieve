package com.easygoal.achieve;

import java.util.Calendar;
import java.util.Date;

import org.litepal.crud.DataSupport;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class Alarm {
    private final Context context;
    private final long deadlinetime;
    private final long advance;
    private final long intervalmin;
    private final int recurring;
    private final String alarmName;
    private final String sourceId;
    private PendingIntent pi;
    private final int REQUEST_CODE_0 = 0;  
    private final static int REQUEST_CODE_1 = 1; 
    private int alarmcount=0;
    private static String Tags="Alarm";
    
	public Alarm(Context context,String sourceId,String alarmName,long deadlinetime,int recurring,int advance,int intervalmin) {
		// TODO Auto-generated constructor stub
    this.context=context;
    this.deadlinetime=deadlinetime;
    this.intervalmin=intervalmin;
    this.alarmName=alarmName;
    this.sourceId=sourceId;
    this.advance=advance;
    this.recurring=recurring;
    //this.remainingtime=remainingtime;
    //alarminit();
	}
	
	public void alarminit()	
	{ 
	  
  	Cursor c_alarm=DataSupport.findBySQL("select id as _id,name,sourceId,deadlinetime,frequency,advance,alarminterval,piaction from reminder where piaction!='0';");
	
	
	if (c_alarm!=null&&c_alarm.getCount()>0){
		 c_alarm.moveToFirst();
		
	 	do {	
	 		int alarm_interval = 0;
			int alarm_advance = 0;
			if (c_alarm.getString(6)!=null){
			    alarm_interval = Integer.parseInt(c_alarm.getString(6));
			    alarm_advance = Integer.parseInt(c_alarm.getString(5));
			//int int_alarmInterval=0;
			//if	(alarm_interval!=null){
		  // 	  int_alarmInterval = Integer.parseInt(alarm_interval);
			//}
			Log.d(Tags,"int_alarmInterval"+alarm_interval);
			}
		       new Alarm(context,
		    		   c_alarm.getString(2),
		    		   c_alarm.getString(1),	 
		    		   TimeData.changeStrToTime_YY(c_alarm.getString(c_alarm.getColumnIndex("deadlinetime"))),
		    		   alarm_advance,
		    		   //Integer.parseInt(c_alarm.getString(c_alarm.getColumnIndex("frequency"))),
		    		   Integer.parseInt(c_alarm.getString(c_alarm.getColumnIndex("advance"))),
		    		   alarm_interval).alarmset();	

		       
		      }while(c_alarm.moveToNext());
			}
      
		
	}
	
	public void alarmset(){
	AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    // 指定要启动的是Activity组件,通过PendingIntent调用getActivity来设置
    
	//Toast.makeText(context, alarmName+deadlinetime+"\n"+" freq "+recurring+"interval"+intervalmin,Toast.LENGTH_SHORT).show();
	//long triggerAtTime = SystemClock.elapsedRealtime()+deadlinetime;
	//long triggerAtTime = deadlinetime;
	long triggerAtTime = 0;
	long interval = intervalmin*60*1000;
	
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 18);
    calendar.set(Calendar.SECOND, 10);
    calendar.set(Calendar.MILLISECOND, 0);
    
    Intent intent = new Intent();
    final Context mContext=this.context;
    //LocalBroadcastManager lbm=LocalBroadcastManager.getInstance(mContext);
	  //intent.putExtra("flag", true);
	  //intent.putExtra("name", alarmName);
	  intent.setAction(sourceId);
	  intent.putExtra("sourceId", sourceId);
	  intent.putExtra("alarmName", alarmName);
	  intent.putExtra("recurring", String.valueOf(recurring));
	  //intent.putExtra("alarmno", String.valueOf((TaskData.alarmlist.size())));
     //
	  
	  IntentFilter filter=new IntentFilter(sourceId);
	  BroadcastReceiver receiver= new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				if(intent.getAction().equals(sourceId)){
					//mContext.unregisterReceiver(this);
					Log.d(Tags,"alarm"+sourceId+"the time is up,start the alarm...");
					//Toast.makeText(context, "收到了!"+sourceId, Toast.LENGTH_LONG).show();
					Intent i=new Intent(context,AlarmActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("alarmName", intent.getStringExtra("alarmName"));
					bundle.putString("sourceId", intent.getStringExtra("sourceId"));
					//bundle.putString("alarmno", intent.getStringExtra("alarmno"));
					bundle.putString("recurring", intent.getStringExtra("recurring"));
					i.putExtras(bundle);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
					context.startActivity(i);
	                
				}else{
					Log.d(Tags,"alarm"+sourceId+"没收到!");
					//Toast.makeText(context, "没收到!"+sourceId, Toast.LENGTH_SHORT).show();
				}
				
			}
			
		};
	  
		
		//AlarmReceiverBean arbean=new AlarmReceiverBean();
		//arbean.setBreceiver(receiver);
		//arbean.setSourceid(sourceId);
		if (receiver!=null&&filter!=null){
			  mContext.registerReceiver(receiver,filter);
			      TaskData.alarmReceiverSet.add(receiver);
				   TaskData.bcreceiverlist.add(receiver);
				   Log.d(Tags+"|alarm receiver|","SourceId"+sourceId+" receiver "+receiver.toString());		  
		       
				   TaskData.alarmsourceidlist.add(sourceId);
		      
			
		}else{
			Toast.makeText(mContext, "动态注册失败"+sourceId, Toast.LENGTH_SHORT).show();
		}
		
		
       
    //Toast.makeText(context, "set ok", Toast.LENGTH_LONG).show();
	//context.sendBroadcast(intent);
	
	//TaskData.intentfilterlist.add(filter);
	//Toast.makeText(context, "registered"+filter+receiver, Toast.LENGTH_LONG).show();
    //context.sendBroadcast(intent);
	long sysTime = SystemClock.elapsedRealtime();
	 pi = PendingIntent.getBroadcast(mContext,REQUEST_CODE_1, intent, PendingIntent.FLAG_UPDATE_CURRENT);	    

	    // 注册以上创建的2个PendingIntent，每隔10秒重复发广播
	  // 
	 if (recurring==1){   
		  //triggerAtTime=deadlinetime-new Date().getTime()-advance*60*1000;	 
		 triggerAtTime=sysTime+(deadlinetime-new Date().getTime()-advance*60*1000);	 
          alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
    		 triggerAtTime, interval, pi);
        ContentValues cv = new ContentValues();
        cv.put("piaction", intent.getAction());
        Cursor c=DataSupport.findBySQL("select id as _id,name,sourceId,content from reminder where sourceId='"+sourceId+"';");
        int id=0;
        if (c!=null&&c.getCount()>0){
         c.moveToFirst();
          id = c.getInt(0);
        }
        DataSupport.update(Reminder.class, cv, id);
        TaskData.alarmpilist.add(pi);
        alarmcount++;
        //Toast.makeText(context, "设置重复闹钟"+sourceId+"\n"+ SystemClock.elapsedRealtime()+"\n"+new Date().getTime(),Toast.LENGTH_SHORT).show();
        Log.d(Tags+"|alarm set|","设置重复闹钟"+sourceId);
        /*
        if (TaskData.initreminderflag==1){
        	    TaskData.initreminderflag=0;
        }else{
            if (TaskData.initreminderflag==0){
            	Toast.makeText(mContext, "闹钟已开启"+sourceId+"(重复)",Toast.LENGTH_SHORT).show();
            }
        }
        */
        //arbean.setPi(pi);
        //TaskData.alarmReceiverlist.add(arbean);
        
        
	 }else{if (recurring==0){
		 //triggerAtTime=deadlinetime-new Date().getTime()-advance*60*1000;
		 triggerAtTime=sysTime+(deadlinetime-new Date().getTime()-advance*60*1000);	 
	     alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi); 
	     ContentValues cv = new ContentValues();
	     cv.put("piaction", intent.getAction());
	     Cursor c=DataSupport.findBySQL("select id as _id,name,sourceId,content from reminder where sourceId='"+sourceId+"';");
	     int id=0;
	        if (c!=null&&c.getCount()>0){
	         c.moveToFirst();
	          id = c.getInt(0);
	        }
         DataSupport.update(Reminder.class, cv, id); 
         TaskData.alarmpilist.add(pi);
         alarmcount++;
         Log.d(Tags+"|alarm set|","设置一次闹钟"+sourceId);
	     //Toast.makeText(context, "设置一次闹钟"+sourceId+"\n"+ SystemClock.elapsedRealtime()+"\n"+new Date().getTime(),Toast.LENGTH_SHORT).show();
         /*
         if (TaskData.initreminderflag==1){
         	TaskData.initreminderflag=0;
         }else{
             if (TaskData.initreminderflag==0){
            	  Toast.makeText(mContext, "闹钟已开启"+sourceId+"(一次)",Toast.LENGTH_SHORT).show();
             }
         }
         */
	     //arbean.setPi(pi);
	     //TaskData.alarmReceiverlist.add(arbean);
	   }
	  }
	  Log.d(Tags+"|alarm set|","alarm count "+TaskData.bcreceiverlist.size());
	}	
	
	/*
	static PendingIntent openpi(Context context,String souceId){	
	  Intent intent = new Intent();
	  intent.putExtra("sourceId", sourceId);
	  intent.putExtra("alarmName", alarmName);
	  intent.setAction(sourceId);
	  intent.setClass(context, AlarmActivity.class);
	  PendingIntent pi = PendingIntent.getBroadcast(context, REQUEST_CODE_1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	  return pi;
	}  
	*/ 
	
	static void alarmCancel(Context context,String sourceId){
		AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		//LocalBroadcastManager lbm=LocalBroadcastManager.getInstance(context);
		int k = -1;
		
	     Cursor c=DataSupport.findBySQL("select id as _id,name,sourceId,content from reminder where sourceId='"+sourceId+"';");
	    
	     Log.d(Tags+"|alarm cancel|","sourceIdlist size"+TaskData.alarmsourceidlist.size()+"\n"+
	                                  "alarmpilist size"+TaskData.alarmpilist.size()+"\n"+
	    	                          "bcreceiverlist size"+TaskData.bcreceiverlist.size());
	     
	     for (int i=0;i<TaskData.alarmsourceidlist.size();i++){
	    	 if (TaskData.alarmsourceidlist.get(i).equals(sourceId)){
	    		 alarmMgr.cancel(TaskData.alarmpilist.get(i));
	    		  try{
	    			  context.unregisterReceiver(TaskData.bcreceiverlist.get(i));
	    			  Log.d(Tags+"|alarm cancel|","unregit alarm No."+i+" Receiver ID "+TaskData.bcreceiverlist.get(i).toString());
				  }catch (IllegalArgumentException e) { 
						    if (e.getMessage().contains("Receiver not registered")) { 
						        // Ignore this exception. This is exactly what is desired 
						    	 Log.d(Tags+"|alarm cancel|","unregit alarm No."+i+" error Receiver not registered");
						    } else { 
						        // unexpected, re-throw 
						    	 Log.d(Tags+"|alarm cancel|","unregit alarm No."+i+" error"+e.toString());
						        throw e; 
						        
						    } 
				   
				  }
	    	   
	    		 k=i;
	    		 Log.d(Tags+"|alarm cancel|",sourceId+" no "+k);
	    	 }
	     }
	     if (k>=0){
	    	 
	        TaskData.alarmpilist.remove(k);
	        TaskData.alarmsourceidlist.remove(k);
	        TaskData.bcreceiverlist.remove(k);
	      
	     }else{
	    	 Log.d(Tags+"|alarm cancel|","未找到"+sourceId);
	     }
	   if (c.getCount()>0){  
	       c.moveToFirst();
	       int id = c.getInt(0);
           //DataSupport.update(Reminder.class, cv, id);  
           DataSupport.delete(Reminder.class,id);
           Log.d(Tags+"|reminder del|", ""+id);
        if (TaskData.adapter_reminder!=null){
           TaskData.adapter_reminder.getCursor().requery();
           TaskData.adapter_reminder.notifyDataSetChanged();;
        }
        //Toast.makeText(context, "闹钟已取消"+sourceId+" no "+alarmno, Toast.LENGTH_LONG).show();
        //Toast.makeText(context, "闹钟已取消"+sourceId, Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, "闹钟已取消", Toast.LENGTH_SHORT).show();
        
	 }else{
		 Log.d(Tags+"|reminder del|", "闹钟不存在");
		//Toast.makeText(context, "闹钟不存在"+sourceId, Toast.LENGTH_SHORT).show();
		//Toast.makeText(context, "闹钟不存在", Toast.LENGTH_SHORT).show();
	 }
  }	
}



