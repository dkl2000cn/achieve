package com.easygoal.achieve;


import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskTool_backup {



	public static String getCurTime(){
		SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date curDate = new Date();//获取当前时间
		String curTime = formatter.format(curDate);
		return curTime;
	}

	public static String getCurDate(){
		SimpleDateFormat formatter = new SimpleDateFormat ("yyMMdd");
		String curDate = formatter.format(new Date());
		return curDate;
	}

	public static void notiSend(Context context,String contentTitle,String contentText,long when){
		//创建???个NotificationManager的引???
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(ns);

		Resources res = context.getResources();
		//Notification notification = new Notification(icon,tickerText,when);
		Notification notification = new Notification.Builder(context).
				setContentText(contentText)
				.setContentTitle(contentTitle)
				.setTicker(contentTitle)
				.setWhen(when)
				.setDefaults(Notification.DEFAULT_VIBRATE)
				.setVibrate(new long[] {0,300,500,700,1000})
				//.setLargeIcon(BitmapFactory.decodeResource(res,R.drawable.logo))
				.setSmallIcon(R.drawable.roundlogo98px)
				.getNotification();
		//.build();

		int ID_NOTIFICATION = 0;
		Log.d("noti", "ready");
		mNotificationManager.notify(ID_NOTIFICATION,notification);
		Log.d("noti", "sent out");

	}

	public static void  sendSMS(Context context,String smsMsg){

		if (TaskData.user!=null&&isMobileNO(TaskData.user)){
			String smsBody = smsMsg;
			Uri smsToUri = Uri.parse( "smsto:" );
			Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);
			sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			sendIntent.putExtra("address", TaskData.user); // 电话号码，这行去掉的话，默认就没有电???
			sendIntent.putExtra( "sms_body", smsBody);
			sendIntent.setType( "vnd.android-dir/mms-sms");
			context.startActivity(sendIntent);
		}
	}

	public static void  FastSendSMS(Context context,String phoneNo,String smsMsg){
		SmsManager manager = SmsManager.getDefault();
		ArrayList<String> list = manager.divideMessage("[目标达]您的密码为:"+smsMsg);  //因为??条短信有字数限制，因此要将长短信拆分
		for(String text:list){
			manager.sendTextMessage(phoneNo, null, text, null, null);
		}
		//Toast.makeText(context, "发???完??", Toast.LENGTH_SHORT).show();
	}

	public static boolean requestPhonePrivilege(Context context){
		if(Build.VERSION.SDK_INT>=23){
			return false;

		}else{
			return true;
		}
	}

	public static String getPhoneNumber(Context context){
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String pn;
		//Toast.makeText(context, "SDK:"+Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show();
		if(Build.VERSION.SDK_INT>=23){
			int checkPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
			if (checkPermission != PackageManager.PERMISSION_GRANTED) {
				//ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
				//Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
				return "test";
			}else {
				if (!TextUtils.isEmpty(mTelephonyMgr.getLine1Number())){
					pn=mTelephonyMgr.getLine1Number();
				}else{
					//Toast.makeText(context, "phoneNo:empty", Toast.LENGTH_SHORT).show();
					pn="test";
				}
				return pn;
			}
		}else{
			if (!TextUtils.isEmpty(mTelephonyMgr.getLine1Number())){
				pn=mTelephonyMgr.getLine1Number();
			}else{
				//Toast.makeText(context, "phoneNo:empty", Toast.LENGTH_SHORT).show();
				pn="test";
			}
			return pn;
		}
	}

	public static boolean isMobileNO11(String mobiles){

		if (mobiles!=null&&!TextUtils.isEmpty(mobiles)){
			//Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Pattern p = Pattern.compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
			Matcher m = p.matcher(mobiles);
			return m.matches();
		}else{
			return false;
		}
	}

	public static boolean isMobileNO(String mobiles){

		if (mobiles!=null&&!TextUtils.isEmpty(mobiles)){
			Pattern p = Pattern.compile("^.*(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
			Matcher m = p.matcher(mobiles);
			Log.d("check phoneNo",""+m.matches());
			return m.matches();
		}else{
			return false;
		}
	}

	public static int getConnCode(Context context){

		ConnectivityManager con = (ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);  ;
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

		if (wifi==true&& internet==false){
			return 1;
		}
		if (wifi==false&& internet==true){
			return 2;
		}
		if (wifi==true&& internet==true){
			return 3;
		}
		return 0;
	}

	public static JSONArray getJSONArray(String s){
		JSONArray gsonArrayback=null;
		if (!TextUtils.isEmpty(s)){
			try {
				gsonArrayback = new JSONArray(s.trim());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else{
			try {
				gsonArrayback=new JSONArray("[]");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return gsonArrayback;
	}

	public static Fragment showFrag(FragmentManager fm,Fragment from_fg,Fragment[] frag,int i){

      if (fm!=null) {
		  FragmentTransaction ft = fm.beginTransaction();
		  ft.setTransition(FragmentTransaction.TRANSIT_NONE);
		  if (from_fg == null) {
			  ft.add(R.id.main_layout, frag[i]).commit();

		  } else {
			  if (!frag[i].isAdded()) {

				  // ft.hide(from_fg);

				  ft.hide(from_fg).add(R.id.main_layout, frag[i]).commit();

			  } else {
				  ft.hide(from_fg).show(frag[i]).commit();

			  }
		  }
		  ;
	  }
		  from_fg = frag[i];
		  return from_fg;
	}


	public static String addZero(int sourceData,int formatLength){

		String newString = String.format("%0"+formatLength+"d", sourceData);

		return newString;
	};

	public static void showDialog(DialogFragment dialogFragment) {

		if (TaskData.fm!=null&&dialogFragment!= null){
			//  dialogFragment = new Fragment_Search();
			//dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
			dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
			TaskData.fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_NONE).commit();
			dialogFragment.show(TaskData.fm, "dialog");
		}
	}

	public static void showDialog(FragmentManager fm,DialogFragment dialogFragment) {

		if (fm!=null&&dialogFragment!= null){
			//  dialogFragment = new Fragment_Search();
			//dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
			dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
			fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_NONE).commit();
			dialogFragment.show(fm, "dialog");
		}
	}




	public static boolean isNumeric(String str){
		//Pattern pattern = Pattern.compile("[0-9]*");
		Pattern pattern = Pattern.compile("^[.0-9]+$");
		Matcher isNum = pattern.matcher(str);

		if( !isNum.matches() ){
			return false;

		}
		return true;

	}

	public static boolean isPwdFormat(String str){
		//Pattern pattern = Pattern.compile("[0-9]*");
		Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
		Matcher isNum = pattern.matcher(str);

		if( !isNum.matches() ){
			return false;

		}
		return true;

	}

	/*
	public static boolean isTimeFormat(String str){
		  //Pattern pattern = Pattern.compile("[0-9]*");
		  Pattern pattern = Pattern.compile("^[0-9]{2}+"-"+[0-9]{2}+"-"+[0-9][0-9]+"."+[0-9][0-9]+":"+[0-9][0-9]+"-"+$");
		  Matcher isNum = pattern.matcher(str);

		  if( !isNum.matches() ){
		      return false;

		        }
		        return true;
  } */

	public static void replaceheader(Context context,TextView tv,String text,String target){

		SpannableString spanString = new SpannableString(text);
		AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(28);
		ForegroundColorSpan span_color=new ForegroundColorSpan(TaskData.overduetextcolor);
		//StyleSpan span_style = new StyleSpan(Typeface.ITALIC);
		spanString.setSpan(span_size, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanString.setSpan(span_color, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.append(spanString);

	}

	public static void replace(Context context,TextView tv,String target,int resid){
		String temp=tv.getText().toString();
		//SpannableStringBuilder spannable = new SpannableStringBuilder(temp);
		//CharacterStyle span=null;
		Pattern p = Pattern.compile(target);
		Matcher m = p.matcher(temp);
		SpannableString spanString= new SpannableString(temp);

		int pos_spanstart=0;
		int pos_spanend=0;

		while (m.find()) {
			//span = new ForegroundColorSpan(Color.RED);//???要重复！
			//span = new ImageSpan(drawable,ImageSpan.XX);//设置现在图片

			//list_imgspan.add(imgspan);
			pos_spanstart=m.start();
			pos_spanend=m.end();
			Bitmap b = BitmapFactory.decodeResource(context.getResources(),resid);
			ImageSpan imgspan = new ImageSpan(context, b);
			//ForegroundColorSpan colorspan = new ForegroundColorSpan(Color.RED);
			spanString.setSpan(imgspan , pos_spanstart, pos_spanend,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			//i++;
			//SpannableString subspanString = spanString.subSequence(pos_textstart,  pos_textend);
			//str_start=spanString.subSequence(0, start)

		}
		tv.setText(spanString);
	}

	public static void replaceTextFormat(Context context,TextView tv,String target){
		String temp=tv.getText().toString();
		//SpannableStringBuilder spannable = new SpannableStringBuilder(temp);
		//CharacterStyle span=null;
		Pattern p = Pattern.compile(target);
		Matcher m = p.matcher(temp);
		SpannableString spanString= new SpannableString(temp);

		int pos_spanstart=0;
		int pos_spanend=0;

		while (m.find()) {
			//span = new ForegroundColorSpan(Color.RED);//???要重复！
			//span = new ImageSpan(drawable,ImageSpan.XX);//设置现在图片

			//list_imgspan.add(imgspan);
			pos_spanstart=m.start();
			pos_spanend=m.end();

			AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(32);
			ForegroundColorSpan span_color=new ForegroundColorSpan(TaskData.overduetextcolor);
			//StyleSpan span_style = new StyleSpan(Typeface.ITALIC);
			spanString.setSpan(span_size, pos_spanstart, pos_spanend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			spanString.setSpan(span_color, pos_spanstart, pos_spanend,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		}
		tv.setText(spanString);
	}

	public static void replaceTextFormat(Context context,TextView tv,String target,int size,int color,int style){
		String temp=tv.getText().toString();
		//SpannableStringBuilder spannable = new SpannableStringBuilder(temp);
		//CharacterStyle span=null;
		Pattern p = Pattern.compile(target);
		Matcher m = p.matcher(temp);
		SpannableString spanString= new SpannableString(temp);

		int pos_spanstart=0;
		int pos_spanend=0;

		while (m.find()) {
			//span = new ForegroundColorSpan(Color.RED);//???要重复！
			//span = new ImageSpan(drawable,ImageSpan.XX);//设置现在图片

			//list_imgspan.add(imgspan);
			pos_spanstart=m.start();
			pos_spanend=m.end();

			StyleSpan span_style = new StyleSpan(style);
			if (size!=0){
				AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(size);
				spanString.setSpan(span_size, pos_spanstart, pos_spanend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			if (color!=0){
				ForegroundColorSpan span_color=new ForegroundColorSpan(color);
				spanString.setSpan(span_color, pos_spanstart, pos_spanend,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			if (style!=0){
				spanString.setSpan(span_style, pos_spanstart, pos_spanend,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		tv.setText(spanString);
	}

	public static SpannableString GetSpan(Context context,String target,int size,int color,int style){

		SpannableString spanString = new SpannableString(target);
		AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(size);
		ForegroundColorSpan span_color=new ForegroundColorSpan(color);
		StyleSpan span_style = new StyleSpan(style);
		spanString.setSpan(span_size, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanString.setSpan(span_color, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanString.setSpan(span_style, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spanString;
	}

	public static SpannableString GetSpan(Context context,String target,int size,int color){

		SpannableString spanString = new SpannableString(target);
		AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(size);
		ForegroundColorSpan span_color=new ForegroundColorSpan(color);
		spanString.setSpan(span_size, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanString.setSpan(span_color, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spanString;
	}

	public static SpannableString GetSpan(Context context,String text){

		SpannableString spanString = new SpannableString(text);
		AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(20);
		ForegroundColorSpan span_color=new ForegroundColorSpan(TaskData.overduetextcolor);
		StyleSpan span_style = new StyleSpan(Typeface.ITALIC);
		spanString.setSpan(span_size, 0,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanString.setSpan(span_color, 0,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanString.setSpan(span_style, 0,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spanString;
	}

	public static SpannableString GetHeaderSpan(Context context,String text){

		SpannableString spanString = new SpannableString(text);
		AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(32);
		ForegroundColorSpan span_color=new ForegroundColorSpan(TaskData.overduetextcolor);
		//StyleSpan span_style = new StyleSpan(Typeface.ITALIC);
		spanString.setSpan(span_size, 0,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanString.setSpan(span_color, 0,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		//spanString.setSpan(span_style, 0,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spanString;
	}



	public static boolean setReminder(Context context,String deadline,String sourceId,String name,String content){
		final SharedPreferences  alarmSettingSP=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
		//int freq= alarmSettingSP.getInt("freq", 1);
		int freq=1;
		int interval= alarmSettingSP.getInt("alarminterval", 5);
		int advance= alarmSettingSP.getInt("advance", 0);
		int lastRowId = 0;
		SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date curDate = new Date();//获取当前时间
		String curTime = formatter.format(curDate);
		Reminder reminder=new Reminder();
		//reminder.setAdvance(et_eventadvance.getText().toString());
		reminder.setSourceId(sourceId);
		reminder.setUsername(TaskData.user);
		reminder.setName(name);
		reminder.setContent(content);
		reminder.setCreatedtime(curTime);
		reminder.setDeadlinetime(deadline);
		//reminder.setFrequency(et_reminderfreq.getText().toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
		String dead=deadline;
		long a=0;
		try {
			a=sdf.parse(dead).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//a = TimeData.changeStrToTime_YYYY(et_eventdeadlinetime.getText().toString());
		long b=new Date().getTime();
		String deadline_yyyy=TimeData.changeStrTime_YYToTime_YYYY(deadline);
		long deadlineData= TimeData.changeStrToMillisTime_YY(deadline_yyyy);
		//String timegap=TimeData.changeLongToTimeStr(a-b);
		//reminder.setRemainingtime(timegap);
		//Toast.makeText(context, deadline+"\n"+deadlineData+"\n"+new Date().getTime(), Toast.LENGTH_LONG).show();
		reminder.setAdvance(String.valueOf(advance));
		reminder.setFrequency(String.valueOf(freq));
		reminder.setAlarminterval(String.valueOf(interval));
		reminder.save();
		//TaskData.eventlist.add(reminder);
		//DataSupport.saveAll(TaskData.eventlist);

		new Alarm(context,
				sourceId,
				name,
				deadlineData,
				freq,
				advance,
				interval).alarmset();

		Cursor cur= DataSupport.findBySQL("select LAST_INSERT_ROWID() from reminder");
		if (cur!=null&&cur.getCount()>0){
			cur.moveToFirst();
			lastRowId=cur.getInt(0);
		}
		String t_sn=TaskData.user+"r";
		reminder.setSn(t_sn+ TaskTool_backup.addZero(lastRowId,10));
		reminder.save();

		TaskData.cursor_reminder.requery();
		if (TaskData.adapter_reminder!=null){
			TaskData.adapter_reminder.notifyDataSetChanged();
		}
		//Toast.makeText(getActivity(),"a:"+a+"\n"+"b:"+b, Toast.LENGTH_SHORT).show();
		//new Alarm(getActivity(),a).alarmset();
		return true;
	}

	public static boolean RegisterAllReminders(Context context){
		Cursor c_alarm=DataSupport.findBySQL("select id as _id,name,sourceId,deadlinetime,frequency,advance,alarminterval,piaction from reminder where piaction!='0' and username="+"'"+TaskData.user+"'"+";");
        try{

		    if (c_alarm!=null&&c_alarm.getCount()>0){
			    c_alarm.moveToFirst();

			do {
				int alarm_recurring = 0;
				int alarm_interval = 0;
				int alarm_advance = 0;
				long deadlinetimeData=0;
				String deadlinetime= c_alarm.getString(c_alarm.getColumnIndex("deadlinetime"));
				SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
				try {
					deadlinetimeData=sdf.parse(deadlinetime).getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if ( c_alarm.getString(6)!=null){
					alarm_advance=c_alarm.getInt(c_alarm.getColumnIndex("advance"));
					alarm_recurring = Integer.parseInt(c_alarm.getString(4));
					alarm_interval = Integer.parseInt(c_alarm.getString(6));
					alarm_advance = Integer.parseInt(c_alarm.getString(5));
					//t=alarm_advance*60*1000;
				}
				long timegap=deadlinetimeData-new Date().getTime()-alarm_advance*60*1000;

				long reminder_id = c_alarm.getLong(0);

				      /*
				    if (timegap<-3*60*1000){
					         //alarmgr.cancel(TaskData.alarmlist.get(c_alarm.getPosition()));
					         DataSupport.delete(Reminder.class, reminder_id );
				             //TaskData.alarmlist.remove(selPos);
					         c_alarm.requery();
					         //Toast.makeText(getActivity(), "已删??"+selId, Toast.LENGTH_SHORT).show();
					          //TaskData.adapter_alltasks.notifyDataSetChanged();
					         //TaskData.adapter_reminder.notifyDataSetChanged();
					         Log.d("time filter", TaskData.cursor_reminder.getPosition()+"filtered");
				    }else{  */
				new Alarm(context,
						c_alarm.getString(2),
						c_alarm.getString(1),
						deadlinetimeData,
						alarm_recurring,
						//Integer.parseInt(c_alarm.getString(c_alarm.getColumnIndex("frequency"))),
						alarm_advance,
						alarm_interval).alarmset();
				Log.d("|RegisterReminder|","reminder id:"+reminder_id);
			}while(c_alarm.moveToNext());
			return true;
		  }
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (c_alarm!=null) {
				c_alarm.close();
			}
		}
		return false;
	}

	public static void unregisterReceiver(Context context,BroadcastReceiver bcr){
		try{
			context.unregisterReceiver(bcr);
			Log.d("unregit broadcastReceiver",bcr.toString());
		}catch (IllegalArgumentException e) {
			if (e.getMessage().contains("Receiver not registered")) {
				// Ignore this exception. This is exactly what is desired
				Log.d("unregit broadcastReceiver"," error Receiver not registered");
			} else {
				// unexpected, re-throw
				Log.d("unregit broadcastReceiver",e.toString());
				throw e;

			}
		}

	}

	public static void clearlinkedReminder(Context context,String headername){
		Cursor cr=DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId like "+"'"+headername+"%"+"'"+" and username="+"'"+TaskData.user+"';");

		if (cr.getCount()>0){
			cr.moveToFirst();
			do{
				String remindersn = cr.getString(1);
				Alarm.alarmCancel(context, remindersn);
				int reminderno = cr.getInt(0);
				DataSupport.delete(Reminder.class, reminderno);
				Log.d("clear reminder","No."+reminderno);

			}while(cr.moveToNext());
		}
	}

	public static void deleteReminder(Context context,String sourceId){
		Cursor cr=DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId = "+"'"+sourceId+"'"+" and username="+"'"+TaskData.user+"';");

		if (cr.getCount()>0){
			cr.moveToFirst();
			do{
				String remindersn = cr.getString(1);
				Alarm.alarmCancel(context, remindersn);
				int reminderno = cr.getInt(0);
				DataSupport.delete(Reminder.class, reminderno);
				Log.d("delreminder","No."+reminderno);
				Toast.makeText(context, "delreminder"+"No."+reminderno, Toast.LENGTH_SHORT).show();
			}while(cr.moveToNext());
		}
	}


	public static void checkTaskCycle(Cursor c){
		String Tags = " checkTaskCycle";
		String cycle_unit;
		String deadline_str;
		String curTime_str = TaskTool_backup.getCurTime();
		//Cursor cursor=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain,null);
		Log.d(Tags, String.valueOf(TaskData.cursor_opentasks.getCount()) + "position" + String.valueOf(TaskData.cursor_opentasks.getPosition()));
		if (c!=null&&c.getCount()>0) {
			c.moveToFirst();
			//Log.d(Tags ,""+c.getCount());
			do {
				cycle_unit = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CYCLEUNIT));
				deadline_str = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
				Log.d(Tags ,""+cycle_unit+" "+deadline_str);
				if (TimeData.TimeGap_YYMMDDHHSS(curTime_str, deadline_str) < 0) {

					if (cycle_unit != null && cycle_unit.equals("单次")) {

					} else {
						if (cycle_unit != null&& !cycle_unit.equals("单次")) {
							try {
								Log.d(Tags ,""+c.getPosition());
								TaskTool_backup.copy2NewTaskByCycleUnit(c);

							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
				}
			} while (c.moveToNext());
		}
		Log.d(Tags , "done");
	}

	public static void cancelCurrentTask(Cursor c){
		String Tags = " AutoCancelCurrentTask";

					if (c != null && c.getCount() > 0) {
						c.moveToFirst();
						String taskhistory = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_HISTORY));
						String tasklesson = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_LESSON));
						final ContentValues cv = new ContentValues();
						String where = TaskData.TdDB.TASK_ID + " = ?";
						String[] whereValue = {TaskData.selTaskID};
						cv.put(TaskData.TdDB.TASK_STATUS, "cancelled");
						cv.put(TaskData.TdDB.TASK_MODIFIED, TaskTool_backup.getCurTime());
						if (tasklesson != null && !org.apache.http.util.TextUtils.isEmpty(tasklesson)) {
							tasklesson = tasklesson.trim() + "\n" + "到期未完成自动取消" + TaskData.tag_tasklesson;
						} else {
							tasklesson = "到期未完成自动取消" + TaskData.tag_tasklesson;
						}

						if (taskhistory != null && !org.apache.http.util.TextUtils.isEmpty(taskhistory)) {
							taskhistory = taskhistory.trim() + "\n" + "到期未完成自动取消" + TaskData.tag_taskhistory;
						} else {
							taskhistory = "到期未完成自动取消" + TaskData.tag_taskhistory;
						}
						cv.put(TaskData.TdDB.TASK_LESSON, tasklesson);
						cv.put(TaskData.TdDB.TASK_HISTORY, taskhistory);
						//Log.d("submit data",cv.toString() );
						long a = TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
					}
				Log.d(Tags,"done");
	}

	public static void copy2NewTaskByCycleUnit(Cursor c) throws ParseException {
		String Tags = "copy2NewTaskByCycleUnit";
		String t_sn;
		Log.d(Tags + "|copy data|", "ready");

						if (c != null && c.getCount() > 0) {
							c.moveToFirst();

							final String[] oldcolumn_task = {
									TaskData.TdDB.TASK_NAME,
									TaskData.TdDB.TASK_DESCRIPTION,
									TaskData.TdDB.TASK_IMPORTANCE,
									TaskData.TdDB.TASK_IMPORTANCEDETAIL,
									TaskData.TdDB.TASK_URGENCY,
									TaskData.TdDB.TASK_URGENCYDETAIL,
									TaskData.TdDB.TASK_ASSESSMENT,
									TaskData.TdDB.TASK_PRIORITY,
									TaskData.TdDB.TASK_PASSION,
									TaskData.TdDB.TASK_PASSIONDETAIL,
									TaskData.TdDB.TASK_DIFFICULTY,
									TaskData.TdDB.TASK_DIFFICULTYDETAIL,
									TaskData.TdDB.TASK_DURATION,
									TaskData.TdDB.TASK_DURATIONUNIT,
									TaskData.TdDB.TASK_DEADLINE,
									TaskData.TdDB.TASK_CYCLEUNIT,
									TaskData.TdDB.TASK_RECORDCOUNT,
									TaskData.TdDB.TASK_STATUS,
									TaskData.TdDB.TASK_USER,
									TaskData.TdDB.TASK_OWNER,
									TaskData.TdDB.TASK_USERGROUP

							};
							List value = new ArrayList();

							if (c != null && c.getCount() > 0) {
								for (int i = 0; i < oldcolumn_task.length; i++) {
									Map map=new HashMap();
									map.put("key",oldcolumn_task[i]);
									map.put("value",c.getString(c.getColumnIndex(oldcolumn_task[i])));
									value.add(map);
									//insertstr_column = insertstr_column+column_task[i]+",";
									//cv.put(column_task[i], value[i]);
									//a=a+" "+et_task[i].getText().toString();
									Log.d(Tags + "|copy data|", "value" + i);

								}
							}

							insertNewTaskByCycleUnit(value);
							cancelCurrentTask(c);
							//insertNewTaskByCycleUnit(value);
							Log.d(Tags + "|copy data|insert task|", "ok");
						}

	}

	public static void copy2NewTaskRecordByCycleUnit(Cursor c,String old_tsn,String new_tsn) throws ParseException {
		String Tags = "copy2NewTaskRecordByCycleUnit";
		int r_no=1;

		//Cursor c;
		Log.d(Tags + "|copy data|", "ready");
		final String[] oldcolumn_record = {
				TaskData.TdDB.RECORD_TASKID_NO,
				TaskData.TdDB.TASK_USER,
				TaskData.TdDB.RECORD_AUTHOR,
				TaskData.TdDB.TASK_OWNER,
				TaskData.TdDB.RECORD_COMMENTS,
				TaskData.TdDB.RECORD_WEIGHT,
				TaskData.TdDB.RECORD_TYPE
		};
		if (TaskData.selTaskID!=null) {
			//TaskData.selTaskSN=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
			c = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord + " where " + TaskData.TdDB.TASK_SN + "=" +"'"+old_tsn+"'", null);
			//query(TaskData.TdDB.TABLE_NAME_TaskMain, , null, null, null, null, null);
			Log.d("cursor", String.valueOf(c.getCount()) + "position" + String.valueOf(c.getPosition()));
            try{
						if (c != null && c.getCount() > 0) {
							c.moveToFirst();

							do {
								List value = new ArrayList();

									for (int i = 0; i < oldcolumn_record.length; i++) {
										Map map = new HashMap();
										map.put("key", oldcolumn_record[i]);
										map.put("value", c.getString(c.getColumnIndex(oldcolumn_record[i])));
										value.add(map);
										//insertstr_column = insertstr_column+column_task[i]+",";
										//cv.put(column_task[i], value[i]);
										//a=a+" "+et_task[i].getText().toString();
										Log.d(Tags + "|copy data|", "value" + i);

									}
								insertNewTaskRecordByCycleUnit(value,old_tsn,new_tsn,r_no);
								Log.d(Tags + "|copy data|insert task|", "ok");
								r_no++;
							}while (c.moveToNext());
						}
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				if (c!=null) {
					c.close();
				}
			}
		}
	}

	public static void insertNewTaskRecordByCycleUnit(List<Map> value,String old_tsn,String new_tsn,int r_no){
		String Tags="insertNewTaskByCycleUnit";
		String newTime=null;
		String cycle_unit;
		long newTimeData=0;
		String old_deadline=null;
		String insertstr_column = " (";
		for (int i=0;i<value.size()-1;i++){
			insertstr_column = insertstr_column+value.get(i).get("key").toString()+",";
			//cv.put(column_task[i], value[i]);
			//a=a+" "+et_task[i].getText().toString();

		}

		insertstr_column=insertstr_column+value.get(value.size()-1).get("key")+") ";
		//Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
		//Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
		String aaa = "  values (";
		String insertstr_value="";
		for (int i=0;i<value.size();i++){
			if (value.get(i).get("value")!=null) {
				insertstr_value = insertstr_value + "'" + value.get(i).get("value") + "'" + ",";
				//cv.put(column_task[i], value[i]);
				//a=a+" "+et_task[i].getText().toString();
				Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
			}else{
				insertstr_value = insertstr_value  + value.get(i).get("value")  + ",";
				//cv.put(column_task[i], value[i]);
				//a=a+" "+et_task[i].getText().toString();
				Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
			}
		}
		insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";
		Log.d(Tags+"|insert value|", insertstr_column+insertstr_value);
		TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskRecord+insertstr_column+insertstr_value);

		Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID() from "+TaskData.TdDB.TABLE_NAME_TaskRecord,null);
		cur.moveToFirst();
		int lastRowId=cur.getInt(0);
		Cursor c= TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord + " where " + TaskData.TdDB.TASK_ID + "=" + TaskData.selTaskID, null);
		Log.d("cursor", String.valueOf(c.getCount()) + "position" + String.valueOf(c.getPosition()));
		/*
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			cycle_unit = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CYCLEUNIT));
			old_deadline = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
			Log.d(Tags, "sss" + old_deadline + cycle_unit);
			try {
				newTime = TimeData.getNewTime(old_deadline, cycle_unit);
				newTimeData = TimeData.getNewTimeData(old_deadline, cycle_unit);
				Log.d(Tags, "sss" + newTime + newTimeData);
			} catch (ParseException e) {
				e.printStackTrace();
			}*/
        try{
					Log.d(Tags + "|new t_sn|", ""+new_tsn);
			ContentValues cv3 = new ContentValues();
			cv3.put(TaskData.TdDB.RECORD_TIME, TaskTool_backup.getCurTime());
			cv3.put(TaskData.TdDB.RECORD_CREATEDTIME, TaskTool_backup.getCurTime());
			//cv3.put(TaskData.TdDB.RECORD_TASKID,lastRowId);
			cv3.put(TaskData.TdDB.TASK_SN,new_tsn);
			cv3.put(TaskData.TdDB.RECORD_SN,  new_tsn+ TaskTool_backup.addZero(r_no,4));
			cv3.put(TaskData.TdDB.RECORD_CREATEDTIME , TaskTool_backup.getCurTime());
			cv3.put(TaskData.TdDB.RECORD_PROGRESS,0);
			cv3.put(TaskData.TdDB.RECORD_DONE,"false");
			cv3.put(TaskData.TdDB.RECORD_DEADLINE, newTime);
			String where_record=TaskData.TdDB.RECORD_ID+"=?";
			String[] where_recordid={String.valueOf(lastRowId)};
			int updateresult = TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskRecord, cv3, where_record, where_recordid);
			//TaskData.getSequenceNo();
			Log.d(Tags + "|new r_sn|", ""+new_tsn+ TaskTool_backup.addZero(r_no,4));
			Log.d(Tags + "|insert task|", "update ok"+lastRowId+" "+updateresult);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (c!=null) {
				c.close();
			}
		}
	}


	public static String getInitSequnceNo(){
		         String Tags="getSequenceNo";
		         String sn_sequence=null;
				 String sn_rank=null;
				int value_taskimportance;
				int value_taskurgency;
		         String taskdeadline;
		         String  value_taskduration;
		         String value_taskdurationunit;
				 Cursor c;
		 if (TaskData.selTaskID!=null) {
			 //TaskData.selTaskSN=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
			 c = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_ID + "=" + TaskData.selTaskID, null);
			 //query(TaskData.TdDB.TABLE_NAME_TaskMain, , null, null, null, null, null);
			 Log.d("cursor", String.valueOf(c.getCount()) + "position" + String.valueOf(c.getPosition()));
			 try{
						 if (c != null && c.getCount() > 0) {
							 c.moveToFirst();
							 value_taskurgency = c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_URGENCY));
							 value_taskimportance = c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE));
							 taskdeadline = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
							 value_taskduration = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATION));
							 value_taskdurationunit = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATIONUNIT));
							 if (value_taskimportance >= 2 && value_taskurgency >= 2) {
								 if (value_taskimportance > 2 && value_taskurgency > 2) {
									 sn_rank = "1";
								 } else {
									 sn_rank = "2";
								 }
							 }
							 if (value_taskimportance == 1 && value_taskurgency >= 2) {
								 sn_rank = "3";

							 }
							 if (value_taskimportance >= 2 && value_taskurgency == 1) {
								 sn_rank = "4";
							 }
							 if (value_taskimportance == 1 && value_taskurgency == 1) {
								 sn_rank = "5";

							 }

							 SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
							 long taskdeadlinedate = 0;
							 try {
								 taskdeadlinedate = (long) df.parse(taskdeadline).getTime() / (1000 * 60);
								 long dd = (long) ((new Date().getTime()) / (1000 * 60));
								 //tv_newtaskheader.setText(""+taskdeadlinedate+"system"+dd);
								 // tv_newtaskheader.setText(""+dd);

							 } catch (ParseException e1) {
								 // TODO Auto-generated catch block
								 e1.printStackTrace();
							 }

							 double plannedtime = 0.0;
							 double hr_timeunit;
							 switch (Integer.parseInt(value_taskdurationunit)) {

								 case 1:
									 hr_timeunit = 8;
									 plannedtime = Double.parseDouble(value_taskduration) * hr_timeunit;
									 Log.d(Tags, "plannedtime:" + plannedtime);
									 break;
								 case 2:
									 hr_timeunit = 1;
									 plannedtime = Double.parseDouble(value_taskduration) * hr_timeunit;
									 Log.d(Tags, "plannedtime:" + plannedtime);
									 break;
								 case 3:
									 hr_timeunit = (double) 1 / 60;
									 plannedtime = Double.parseDouble(value_taskduration) * hr_timeunit;
									 Log.d(Tags, "plannedtime:" + plannedtime);
									 break;
								 default:
									 break;
							 }

							 //taskdeadline=et15_taskdeadline.getText().toString();
							 //date1=dg_time.GetDate();
							 //Toast.makeText(getActivity(), deadline+"\n"+date1, Toast.LENGTH_LONG).show();
							 //taskdeadlineTimeData=TimeData.changeStrToTime_YY(taskdeadline);
							 //taskdeadlinetimestamp=Timestamp.valueOf(deadline);
							 String taskdeadlineDate = TimeData.convertDataNoTime_YY(taskdeadline);

							 String sn_deadline = TimeData.convertShortDate_YYMdtoYYYYMMdd(taskdeadlineDate);
							 //DecimalFormat df_sn = new DecimalFormat("0.0000");//格式化小数，.后跟几个零代表几位小数
							 //float dt=((float)plannedtime/10000);
							 //sn_duration=df_sn.format(dt);;
							 String sn_duration = TaskTool_backup.addZero((int) (plannedtime * 10), 6);
							 String sn_importance = null;
							 switch (value_taskimportance) {
								 case 3:
									 sn_importance = "1";
									 break;
								 case 2:
									 sn_importance = "2";
									 break;
								 case 1:
									 sn_importance = "3";
									 break;
								 default:
									 break;
							 }
							 String sn_urgency = null;
							 switch (value_taskurgency) {
								 case 3:
									 sn_urgency = "1";
									 break;
								 case 2:
									 sn_urgency = "2";
									 break;
								 case 1:
									 sn_urgency = "3";
									 break;
								 default:
									 break;
							 }

							 //sn_sequence=sn_deadline+sn_importance+sn_urgency+sn_duration;
							 sn_sequence = sn_deadline + sn_rank + sn_importance + sn_urgency + sn_duration;

						 }
			 }catch(Exception e){
				 e.printStackTrace();
			 }finally {
				 if (c!=null) {
					 c.close();
				 }
			 }
		 }
		return sn_sequence;
	}

	public static void insertNewTaskByCycleUnit(List<Map> value){
		String Tags="insertNewTaskByCycleUnit";
		String newTime=null;
		String old_tsn=null;
		String new_tsn;
		String cycle_unit;
		long newTimeData=0;
		String old_deadline=null;
		String insertstr_column = " (";
		for (int i=0;i<value.size()-1;i++){
			insertstr_column = insertstr_column+value.get(i).get("key").toString()+",";
			//cv.put(column_task[i], value[i]);
			//a=a+" "+et_task[i].getText().toString();

		}
		insertstr_column=insertstr_column+value.get(value.size()-1).get("key")+") ";
		//Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
		//Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
		String aaa = "  values (";
		String insertstr_value="";
		for (int i=0;i<value.size();i++){
			if (value.get(i).get("value")!=null) {
				insertstr_value = insertstr_value + "'" + value.get(i).get("value") + "'" + ",";
				//cv.put(column_task[i], value[i]);
				//a=a+" "+et_task[i].getText().toString();
				Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
			}else{
				insertstr_value = insertstr_value  + value.get(i).get("value")  + ",";
				//cv.put(column_task[i], value[i]);
				//a=a+" "+et_task[i].getText().toString();
				Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
			}
		}
		insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";
		Log.d(Tags+"|insert value|", insertstr_column+insertstr_value);
		TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskMain+insertstr_column+insertstr_value);

		Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID()",null);
		cur.moveToFirst();
		int lastRowId=cur.getInt(0);
		Cursor c= TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_ID + "=" + TaskData.selTaskID, null);
		Log.d("cursor", String.valueOf(c.getCount()) + "position" + String.valueOf(c.getPosition()));

		try {
			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				old_tsn = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
				cycle_unit = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CYCLEUNIT));
				old_deadline = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
				Log.d(Tags, "sss" + old_deadline + cycle_unit);
				try {
					newTime = TimeData.getNewTime(old_deadline, cycle_unit);
					newTimeData = TimeData.getNewTimeData(old_deadline, cycle_unit);
					Log.d(Tags, "sss" + newTime + newTimeData);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			ContentValues cv3 = new ContentValues();
			new_tsn = TaskData.user + "0" + TaskTool_backup.addZero(lastRowId, 10);
			cv3.put(TaskData.TdDB.TASK_CREATEDTIME, TaskTool_backup.getCurTime());
			cv3.put(TaskData.TdDB.TASK_CREATEDTIMEDATA, new Date().getTime());
			cv3.put(TaskData.TdDB.TASK_STARTEDTIME, TaskTool_backup.getCurTime());
			cv3.put(TaskData.TdDB.TASK_PROGRESS, 0);
			cv3.put(TaskData.TdDB.SUM_ACCOMPLISHED, 0);
			cv3.put(TaskData.TdDB.SUM_ACHIEVED, 0);
			cv3.put(TaskData.TdDB.SUM_ENJOYMENT, 0);
			cv3.put(TaskData.TdDB.SUM_EXPERIENCE, 0);
			cv3.put(TaskData.TdDB.TASK_SEQUENCE, getInitSequnceNo());
			cv3.put(TaskData.TdDB.TASK_DEADLINE, newTime);
			cv3.put(TaskData.TdDB.TASK_DEADLINETIMEDATA, newTimeData);
			cv3.put(TaskData.TdDB.TASK_DEADLINEDATE, TimeData.convertDataNoTime_YY(newTime));
			cv3.put("_sn", TaskData.user + "0" + TaskTool_backup.addZero(lastRowId, 10));
			String where_task = TaskData.TdDB.TASK_ID + "=?";
			String[] where_taskid = {String.valueOf(lastRowId)};
			TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv3, where_task, where_taskid);


			try {
				copy2NewTaskRecordByCycleUnit(c, old_tsn, new_tsn);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			TaskData.getSequenceNo();
			Log.d(Tags + "insert task|", "insert ok" + lastRowId);
			TaskData.adapterUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (c!=null) {
				c.close();
			}
		}
	}


	public static void copy2NewTask() {
			String Tags = "copy2NewTask";
		    Cursor c;
		   Log.d(Tags + "|copy data|", "ready");
			if (TaskData.selTaskID!=null) {
				//TaskData.selTaskSN=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
				c = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_ID + "=" + TaskData.selTaskID, null);
				//query(TaskData.TdDB.TABLE_NAME_TaskMain, , null, null, null, null, null);
				Log.d("cursor", String.valueOf(c.getCount()) + "position" + String.valueOf(c.getPosition()));
				try{
							if (c != null && c.getCount() > 0) {
								c.moveToFirst();
								final String[] oldcolumn_task = {
										TaskData.TdDB.TASK_NAME,
										TaskData.TdDB.TASK_DESCRIPTION,
										TaskData.TdDB.TASK_IMPORTANCE,
										TaskData.TdDB.TASK_IMPORTANCEDETAIL,
										TaskData.TdDB.TASK_URGENCY,
										TaskData.TdDB.TASK_URGENCYDETAIL,
										TaskData.TdDB.TASK_ASSESSMENT,
										TaskData.TdDB.TASK_PRIORITY,
										TaskData.TdDB.TASK_PASSION,
										TaskData.TdDB.TASK_PASSIONDETAIL,
										TaskData.TdDB.TASK_DIFFICULTY,
										TaskData.TdDB.TASK_DIFFICULTYDETAIL,
										TaskData.TdDB.TASK_DURATION,
										TaskData.TdDB.TASK_DURATIONUNIT,
										TaskData.TdDB.TASK_DEADLINE,
										TaskData.TdDB.TASK_CYCLEUNIT,
										TaskData.TdDB.TASK_DEADLINETIMEDATA,
										TaskData.TdDB.TASK_DEADLINEDATE,
										TaskData.TdDB.TASK_RECORDCOUNT,
										TaskData.TdDB.TASK_STATUS,
										TaskData.TdDB.TASK_USER,
										TaskData.TdDB.TASK_OWNER,
										TaskData.TdDB.TASK_USERGROUP

								};
								List value = new ArrayList();
								if (c != null && c.getCount() > 0) {
									for (int i = 0; i < oldcolumn_task.length; i++) {
											Map map=new HashMap();
											map.put("key",oldcolumn_task[i]);
											map.put("value",c.getString(c.getColumnIndex(oldcolumn_task[i])));
											value.add(map);
											//insertstr_column = insertstr_column+column_task[i]+",";
											//cv.put(column_task[i], value[i]);
											//a=a+" "+et_task[i].getText().toString();
											Log.d(Tags + "|copy data|", "value" + i);

									}
								}
								insertNewTask(value);
								Log.d(Tags + "|copy data|insert task|", "ok");
							}
				}catch(Exception e){
					e.printStackTrace();
				}finally {
					if (c!=null) {
						c.close();
					}
				}
			}
	}



	public static void insertNewTask(List<Map> value){
		String Tags="InsertTask";

		String insertstr_column = " (";
		for (int i=0;i<value.size()-1;i++){
			insertstr_column = insertstr_column+value.get(i).get("key").toString()+",";
			//cv.put(column_task[i], value[i]);
			//a=a+" "+et_task[i].getText().toString();

		}

		insertstr_column=insertstr_column+value.get(value.size()-1).get("key")+") ";
		//Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
		//Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
		String aaa = "  values (";
		String insertstr_value="";
		for (int i=0;i<value.size();i++){
           if (value.get(i).get("value")!=null) {
			   insertstr_value = insertstr_value + "'" + value.get(i).get("value") + "'" + ",";
			   //cv.put(column_task[i], value[i]);
			   //a=a+" "+et_task[i].getText().toString();
			   Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
		   }else{
			   insertstr_value = insertstr_value  + value.get(i).get("value")  + ",";
			   //cv.put(column_task[i], value[i]);
			   //a=a+" "+et_task[i].getText().toString();
			   Log.d(Tags + "|insert data|", "value" + i + " " + value.get(i).get("value"));
		   }
		}
		insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";
		Log.d(Tags+"|insert value|", insertstr_column+insertstr_value);
		TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskMain+insertstr_column+insertstr_value);

		Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID()",null);
		cur.moveToFirst();
		int lastRowId=cur.getInt(0);

		ContentValues cv3 = new ContentValues();
		cv3.put(TaskData.TdDB.TASK_CREATEDTIME, TaskTool_backup.getCurTime());
		cv3.put(TaskData.TdDB.TASK_CREATEDTIMEDATA,new Date().getTime());
		cv3.put(TaskData.TdDB.TASK_PROGRESS,0);
		cv3.put(TaskData.TdDB.SUM_ACCOMPLISHED,0);
		cv3.put(TaskData.TdDB.SUM_ACHIEVED,0);
		cv3.put(TaskData.TdDB.SUM_ENJOYMENT,0);
		cv3.put(TaskData.TdDB.SUM_EXPERIENCE,0);
		cv3.put("_sn",TaskData.user+"0"+ TaskTool_backup.addZero(lastRowId,10));

		String where_task=TaskData.TdDB.TASK_ID+"=?";
		String[] where_taskid={String.valueOf(lastRowId)};
		TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv3, where_task, where_taskid);
		getInitSequnceNo();
		TaskData.getSequenceNo();
		Log.d(Tags + "insert task|", "insert ok"+lastRowId);
		TaskData.adapterUpdate();
	}

	public static void insertNewTask(String[] column_task,List value){
		String Tags="InsertTask";

		String insertstr_column = " (";
		for (int i=0;i<column_task.length-1;i++){
			insertstr_column = insertstr_column+column_task[i]+",";
			//cv.put(column_task[i], value[i]);
			//a=a+" "+et_task[i].getText().toString();

		}

		insertstr_column=insertstr_column+column_task[column_task.length-1]+") ";
		//Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
		//Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
		String aaa = "  values (";
		String insertstr_value="";
		for (int i=0;i<value.size();i++){

			insertstr_value = insertstr_value+"'"+value.get(i).toString()+"'"+",";
			//cv.put(column_task[i], value[i]);
			//a=a+" "+et_task[i].getText().toString();
			Log.d(Tags+"|insert data|", "value"+i+" "+value.get(i).toString());
		}
		insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";
		Log.d(Tags+"|insert value|", insertstr_column+insertstr_value);
		TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskMain+insertstr_column+insertstr_value);

		Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID()",null);
		cur.moveToFirst();
		int lastRowId=cur.getInt(0);

		ContentValues cv3 = new ContentValues();
		cv3.put("_sn",TaskData.user+"0"+ TaskTool_backup.addZero(lastRowId,10));
		String where_task=TaskData.TdDB.TASK_ID+"=?";
		String[] where_taskid={String.valueOf(lastRowId)};
		TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv3, where_task, where_taskid);
		TaskData.getSequenceNo();
		Log.d(Tags + "insert task|", "insert ok"+lastRowId);
		TaskData.adapterUpdate();
	}


	public static void insertNewTask(String[] column_task,Object[] value){
		String Tags="InsertTask";

		String insertstr_column = " (";
		for (int i=0;i<column_task.length-1;i++){

			insertstr_column = insertstr_column+column_task[i]+",";
			//cv.put(column_task[i], value[i]);
			//a=a+" "+et_task[i].getText().toString();
			Log.d(Tags+"|insert data|", "value"+i+" "+value[i]);
		}

		insertstr_column=insertstr_column+column_task[column_task.length-1]+") ";
		//Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
		//Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
		String aaa = "  values (";
		String insertstr_value="";
		for (int i=0;i<value.length;i++){

			insertstr_value = insertstr_value+"'"+value[i]+"'"+",";
			//cv.put(column_task[i], value[i]);
			//a=a+" "+et_task[i].getText().toString();
			Log.d(Tags+"|insert data|", "value"+i+" "+value[i]);
		}
		insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";

		TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskMain+insertstr_column+insertstr_value);

		Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID()",null);
		cur.moveToFirst();
		int lastRowId=cur.getInt(0);

		ContentValues cv3 = new ContentValues();
		cv3.put("_sn",TaskData.user+"0"+ TaskTool_backup.addZero(lastRowId,10));
		String where_task=TaskData.TdDB.TASK_ID+"=?";
		String[] where_taskid={String.valueOf(lastRowId)};
		TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv3, where_task, where_taskid);
		TaskData.getSequenceNo();
		Log.d(Tags + "|copy data|insert task|", "insert ok"+lastRowId);
		TaskData.adapterUpdate();
	}
   /*
	public static void insertTask(){
		String Tags="InsertTask";
		final String[] column_task={
				TaskData.TdDB.TASK_NAME,
				TaskData.TdDB.TASK_CREATEDTIME,
				TaskData.TdDB.TASK_CREATEDTIMEDATA,
				TaskData.TdDB.TASK_DESCRIPTION,
				TaskData.TdDB.TASK_IMPORTANCE,
				TaskData.TdDB.TASK_IMPORTANCEDETAIL,
				TaskData.TdDB.TASK_URGENCY,
				TaskData.TdDB.TASK_URGENCYDETAIL,
				TaskData.TdDB.TASK_ASSESSMENT,
				TaskData.TdDB.TASK_PRIORITY,
				TaskData.TdDB.TASK_PASSION,
				TaskData.TdDB.TASK_PASSIONDETAIL,
				TaskData.TdDB.TASK_DIFFICULTY,
				TaskData.TdDB.TASK_DIFFICULTYDETAIL,
				TaskData.TdDB.TASK_DURATION,
				TaskData.TdDB.TASK_DURATIONUNIT,
				TaskData.TdDB.TASK_STARTEDTIME,
				TaskData.TdDB.TASK_DEADLINE,
				TaskData.TdDB.TASK_CYCLEUNIT,
				TaskData.TdDB.TASK_PROGRESS,
				TaskData.TdDB.TASK_REMINDER,
				TaskData.TdDB.TASK_STATUS,
				TaskData.TdDB.TASK_FINISHED,
				TaskData.TdDB.TASK_DELAYED,
				TaskData.TdDB.SUM_ACCOMPLISHED,
				TaskData.TdDB.SUM_ACHIEVED,
				TaskData.TdDB.SUM_ENJOYMENT,
				TaskData.TdDB.SUM_EXPERIENCE,
				TaskData.TdDB.SUM_HORNOR,
				TaskData.TdDB.TASK_DEADLINETIMEDATA,
				TaskData.TdDB.TASK_DEADLINEDATE,
				TaskData.TdDB.TASK_DELAYEDDAYS,
				TaskData.TdDB.TASK_RECORDCOUNT,
				TaskData.TdDB.TASK_SEQUENCE,
				TaskData.TdDB.TASK_USER,
				TaskData.TdDB.TASK_OWNER,
				TaskData.TdDB.TASK_REMARKS,
				TaskData.TdDB.TASK_HISTORY,

		};

		final Object[] value={
				et1_taskname.getText().toString(),
				curTime,
				curTimeData,
				et3_taskdescription.getText().toString(),
				String.valueOf(list_imp.get(spin4_taskimportance.getSelectedItemPosition()).getNo()),
				String.valueOf(list_imp.get(spin4_taskimportance.getSelectedItemPosition()).getValue()),
				String.valueOf(list_urg.get(spin5_taskurgency.getSelectedItemPosition()).getNo()),
				String.valueOf(list_urg.get(spin5_taskurgency.getSelectedItemPosition()).getValue()),
				item6_taskassess,
				item7_taskprority,
				String.valueOf(list_passion.get(spin8_taskpassion.getSelectedItemPosition()).getNo()),
				String.valueOf(list_passion.get(spin8_taskpassion.getSelectedItemPosition()).getValue()),
				String.valueOf(list_difficulty.get(spin9_taskdifficulty.getSelectedItemPosition()).getNo()),
				String.valueOf(list_difficulty.get(spin9_taskdifficulty.getSelectedItemPosition()).getValue()),
				et10_taskduration.getText().toString(),
				String.valueOf(spin_timeunit.getSelectedItemPosition()+1),
				taskstartedtime,
				taskdeadline,
				cycle_unit_model2,
				"0",
				taskreminder,
				"open",
				"no",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0",
				taskdeadlineTimeData,
				taskdeadlineDate,
				1,
				0,
				sn_sequence,
				TaskData.user,
				TaskData.user,
				"",
				"任务创建"+TaskData.tag_taskhistory
		};

		String insertstr_column = " (";
		for (int i=0;i<column_task.length-1;i++){

			insertstr_column = insertstr_column+column_task[i]+",";
			//cv.put(column_task[i], value[i]);
			//a=a+" "+et_task[i].getText().toString();
			Log.d(Tags+"|insert data|", "value"+i+" "+value[i]);
		}

		insertstr_column=insertstr_column+column_task[column_task.length-1]+") ";
		//Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
		//Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
		String aaa = "  values (";
		String insertstr_value="";
		for (int i=0;i<value.length;i++){

			insertstr_value = insertstr_value+"'"+value[i]+"'"+",";
			//cv.put(column_task[i], value[i]);
			//a=a+" "+et_task[i].getText().toString();
			Log.d(Tags+"|insert data|", "value"+i+" "+value[i]);
		}
		insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";

		TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskMain+insertstr_column+insertstr_value);

		Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID()",null);
		cur.moveToFirst();
		int lastRowId=cur.getInt(0);

		ContentValues cv3 = new ContentValues();
		cv3.put("_sn",TaskData.user+"0"+TaskTool.addZero(lastRowId,10));
		String where_task=TaskData.TdDB.TASK_ID+"=?";
		String[] where_taskid={String.valueOf(lastRowId)};
		TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv3, where_task, where_taskid);
		TaskData.getSequenceNo();
	}
    */
	public static void quickclose(Context context){

		boolean readyflag;
		String t_sn;
		String tasklesson;
		String taskhistory;
		String curTime;
		int duration_unit;
		double duration;
		double plannedtime = 0;
		double hr_timeunit;
		Cursor c_rf;
		Cursor c;

		if (TaskData.selTaskID!=null){
			readyflag=false;
			String a = TaskData.TdDB.TABLE_NAME_TaskMain;
			String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
			curTime= TaskTool_backup.getCurTime();
			c = TaskData.db_TdDB.rawQuery("select * from "+a+" where "+TaskData.TdDB.TASK_ID+"="+TaskData.selTaskID,null);
			//
			c.moveToFirst();
			// Log.d("selTab count", "total "+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME))+" "+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME+" "+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS+""+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)))))));
			t_sn=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
			tasklesson = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_LESSON));
			taskhistory = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_HISTORY));

			c_rf= TaskData.db_TdDB.rawQuery("select * from "+b+" where "+
					TaskData.TdDB.TASK_SN+"="+"'"+t_sn+"'"+
					" and "+TaskData.TdDB.RECORD_DONE+"="+"'"+"false"+"'",null);
			if (c_rf!=null&&c_rf.getCount()>0){
				Toast.makeText(context, "无法快速关闭,有子任务未完成", Toast.LENGTH_SHORT).show();;
			}else{
				if (c_rf.getCount()==0){
					readyflag=true;
				}
			}

			if (readyflag==true){

				final ContentValues cv = new ContentValues();
				String where = TaskData.TdDB.TASK_ID + " = ?";
				String[] whereValue = { TaskData.selTaskID };

				String sum_accomplished= c.getString(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE));
				double sum_contribution= Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE)))
						*Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_URGENCY)));
				//Toast.makeText(getActivity(), ""+sum_contribution, Toast.LENGTH_SHORT).show();;

				final Calendar cal=Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day=cal.get(Calendar.DAY_OF_MONTH);

				SimpleDateFormat formatter2 = new SimpleDateFormat ("yyMMdd");
				String curDate2 = formatter2.format(new Date());
				long closedate = Integer.parseInt(curDate2);
				long closedtimedata = new Date().getTime()/60000;

				cv.put(TaskData.TdDB.TASK_CLOSEDATE,closedate);
				cv.put(TaskData.TdDB.SUM_ACCOMPLISHED, c.getString(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE)));
				cv.put(TaskData.TdDB.SUM_CONTRIBUTION,""+sum_contribution);
				cv.put(TaskData.TdDB.TASK_SEQUENCENO,"");
				cv.put(TaskData.TdDB.TASK_CLOSEDTIMEDATA,closedtimedata);
				cv.put(TaskData.TdDB.TASK_CLOSEDTIME, TaskTool_backup.getCurTime());
				String taskdeadline=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
				if (TimeData.CompareTime_YY(taskdeadline, TaskTool_backup.getCurTime())<=0){
					cv.put(TaskData.TdDB.TASK_DELAYED,"0");
					cv.put(TaskData.TdDB.TASK_DELAYEDDAYS,String.valueOf(TimeData.CompareTime_YY(taskdeadline,curTime)));
					cv.put(TaskData.TdDB.TASK_CLOSEDCOMMENT,String.valueOf("提前"+TimeData.CompareTime_YY(curTime,taskdeadline))+"天完成");
				}else{
					cv.put(TaskData.TdDB.TASK_DELAYED,"1");
					cv.put(TaskData.TdDB.TASK_DELAYEDDAYS,String.valueOf(TimeData.CompareTime_YY(taskdeadline,curTime)));
					cv.put(TaskData.TdDB.TASK_CLOSEDCOMMENT,String.valueOf("延期"+TimeData.CompareTime_YY(taskdeadline,curTime))+"天完成");
				}

				// Log.d("show closedata",taskclosechoice);
				//cv.put(TaskData.TdDB.RECORD_NO, String.valueOf(c2.));
				//cv.put(TaskData.TdDB.RECORD_TIME, curTime);
				//cv.put(TaskData.TdDB.RECORD_PROGRESS, et_recordprogress.getText().toString());

				if (tasklesson!=null&&!org.apache.http.util.TextUtils.isEmpty(tasklesson)){
					tasklesson=tasklesson.trim()+"\n"+"[快速结束]执行力取得积极成果"+TaskData.tag_tasklesson;
				}else{
					tasklesson="[快速结束]执行力取得积极成果"+TaskData.tag_tasklesson;
				}

				if (taskhistory!=null&&!org.apache.http.util.TextUtils.isEmpty(taskhistory)){
					taskhistory=taskhistory.trim()+"\n"+"[快速结束]任务完成"+TaskData.tag_taskhistory;
				}else{
					taskhistory="[快速结束]任务完成"+TaskData.tag_taskhistory;
				}


				duration_unit=Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATIONUNIT)));

				duration=Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATION)));

				switch (duration_unit){

					case 1: hr_timeunit=TaskData.prehour;
						plannedtime=duration*hr_timeunit;
						//Log.d(Tags,"plannedtime:"+plannedtime);
						break;
					case 2: hr_timeunit=1;
						plannedtime=duration*hr_timeunit;;
						//Log.d(Tags,"plannedtime:"+plannedtime);
						break;
					case 3: hr_timeunit=(double)1/60;
						plannedtime=duration*hr_timeunit;;
						//Log.d(Tags,"plannedtime:"+plannedtime);
						break;
					default:break;
				}


				int cur_enjoyment= (int) ((Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PASSION)))
						*plannedtime));

				int cur_achieved= (int) ((Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE)))
						*plannedtime));

				int cur_experience= (int) ((Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DIFFICULTY)))
						*plannedtime));


				//long updateAccomplished=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
				cv.put(TaskData.TdDB.TASK_MODIFIED, TaskTool_backup.getCurTime());
				cv.put(TaskData.TdDB.SUM_ACHIEVED, String.valueOf(cur_achieved));

				//long updateAchieved=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
				cv.put(TaskData.TdDB.SUM_ENJOYMENT, String.valueOf(cur_enjoyment));
				//long updateEnjoyment=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
				cv.put(TaskData.TdDB.SUM_EXPERIENCE, String.valueOf(cur_experience));
				//long updateExperience=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
				cv.put(TaskData.TdDB.SUM_ACCOMPLISHED,sum_accomplished);
				cv.put(TaskData.TdDB.TASK_RESULTSATISFICATION,"1");;
				cv.put(TaskData.TdDB.TASK_PROGRESS,100);
				cv.put(TaskData.TdDB.TASK_STATUS,"finished");
				cv.put(TaskData.TdDB.TASK_RESULTCOMMENT,"结果符合预期");
				cv.put(TaskData.TdDB.TASK_LESSON, tasklesson);
				cv.put(TaskData.TdDB.TASK_HISTORY, taskhistory);
				cv.put(TaskData.TdDB.TASK_MODIFIED, TaskTool_backup.getCurTime());
				//Log.d("submit data",cv.toString() );
				long ur=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);

				if (ur==1){
					TaskData.getSequenceNo();
					TaskData.adapterUpdate();
					Toast.makeText(context, "快速结束任务成功", Toast.LENGTH_SHORT).show();;


				}else{
					Toast.makeText(context, "快速结束任务失败"+TaskData.TdDB.TASK_ID, Toast.LENGTH_SHORT).show();;
				}
			} else{
				//Toast.makeText(getActivity(), "无法快速关闭，还有未完成的子任务", Toast.LENGTH_SHORT).show();;
			}

		}else {
			Toast.makeText(context, "未选定任务", Toast.LENGTH_SHORT).show();
		}
	}


	public static int AppendFieldText(String tbname,String keyfield,String t_sn,String fieldname,String newvalue){

		ContentValues cv_tu=new ContentValues();
		String str_field="";
		String str_field_appended="";
		String whereas_tu=keyfield+"=?";
		String[] whereValues_tu={t_sn};
		int task_tu = 0;

		if (t_sn!=null&&!TextUtils.isEmpty(t_sn)){
			Cursor c_d=TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+
							keyfield+"=?  ",
					new String[]{t_sn});
			c_d.moveToFirst();

			if (c_d!=null&&c_d.getCount()>0){
				str_field=c_d.getString(c_d.getColumnIndex(fieldname));
				str_field_appended = str_field+newvalue;
				cv_tu.put(fieldname, str_field_appended);
				task_tu=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv_tu,whereas_tu,whereValues_tu);
				Log.d("Append "+fieldname,t_sn+task_tu);
			}else{
				Log.d("Append "+fieldname,t_sn+"not found");
			}
		}
		return task_tu;
	}

	public static Uri getResourceUri(Context context, int res) {
		try {
			Context packageContext = context.createPackageContext(context.getPackageName(),
					Context.CONTEXT_RESTRICTED);
			Resources resources = packageContext.getResources();
			String appPkg = packageContext.getPackageName();
			String resPkg = resources.getResourcePackageName(res);
			String type = resources.getResourceTypeName(res);
			String name = resources.getResourceEntryName(res);


			Uri.Builder uriBuilder = new Uri.Builder();
			uriBuilder.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE);
			uriBuilder.encodedAuthority(appPkg);
			uriBuilder.appendEncodedPath(type);
			if (!appPkg.equals(resPkg)) {
				uriBuilder.appendEncodedPath(resPkg + ":" + name);
			} else {
				uriBuilder.appendEncodedPath(name);
			}
			return uriBuilder.build();

		} catch (Exception e) {
			return null;
		}
	}


	public static int GetSubtaskCount(String task_sn){

		if (TaskData.TdDB.TASK_SN!=null){
			Cursor c_d=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+
							TaskData.TdDB.TASK_SN+"=?  ",
					new String[]{task_sn});
			c_d.moveToFirst();
			if (c_d!=null&&c_d.getCount()>0){
				return c_d.getCount();
			}else{
				return 0;
			}
		}
		return -1;
	}

	public static double isWithSubtasks(){

		double sum_closeweight=0;

		if (TaskData.TdDB.TASK_SN!=null){
			Cursor c_d=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
							TaskData.TdDB.TASK_SN+"=?  ",
					new String[]{TaskData.selTaskSN});
			c_d.moveToFirst();
			if (c_d!=null&&c_d.getCount()>0){
				sum_closeweight=Double.parseDouble(c_d.getString(c_d.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
	    	/*
	    	while (c_d.moveToNext()){
	    	 sum_closeweight=sum_closeweight+Double.parseDouble(c_d.getString(c_d.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
	    	}*/
			}
			//TaskData.totalweight=sum_openweight+sum_closeweight;
			Log.d("sum_closeweight",sum_closeweight+" "+c_d.getCount());
		}else{

		}
		return (sum_closeweight);
	}



	public static double GetTotalClosedWeight(){

		double sum_closeweight=0;

		if (TaskData.TdDB.TASK_SN!=null){
			Cursor c_d=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
							TaskData.TdDB.TASK_SN+"=?  ",
					new String[]{TaskData.selTaskSN});
			c_d.moveToFirst();
			if (c_d!=null&&c_d.getCount()>0){
				sum_closeweight=Double.parseDouble(c_d.getString(c_d.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
		    	/*
		    	while (c_d.moveToNext()){
		    	 sum_closeweight=sum_closeweight+Double.parseDouble(c_d.getString(c_d.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
		    	}*/
			}
			//TaskData.totalweight=sum_openweight+sum_closeweight;
			Log.d("sum_closeweight",sum_closeweight+" "+c_d.getCount());
		}else{

		}
		return (sum_closeweight);
	}




}