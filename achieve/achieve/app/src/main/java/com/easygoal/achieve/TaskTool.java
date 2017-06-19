package com.easygoal.achieve;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

import org.apache.commons.collections.list.CursorableLinkedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.litepal.crud.DataSupport;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

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
import android.os.Environment;
import android.provider.MediaStore;
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

import static android.R.attr.value;

/**
 *
 */
public class TaskTool{



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
			Log.d("TaskTool|isMobileNO11|",""+m.matches());
			return m.matches();
		}else{
			return false;
		}
	}

	public static boolean isMobileNO(String mobiles){

		if (mobiles!=null&&!TextUtils.isEmpty(mobiles)){
			Pattern p = Pattern.compile("^.*(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
			Matcher m = p.matcher(mobiles);
			Log.d("TaskTool|isMobileNO|",""+m.matches());
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

	public static void showRocket(){
		  DialogFragment_Rocket dialogFragment_rocket=new DialogFragment_Rocket();
		  showDialog(dialogFragment_rocket);
	}


	public static void showFlower(){
		DialogFragment_Flower dialogFragment_flower=new DialogFragment_Flower();
		showDialog(dialogFragment_flower);
	}
	public static void showArrow(){
		DialogFragment_Arrow dialogFragment_arrow=new DialogFragment_Arrow();
		showDialog(dialogFragment_arrow);
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
		} catch (java.text.ParseException e) {
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
		reminder.setSn(t_sn+TaskTool.addZero(lastRowId,10));
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
				} catch (java.text.ParseException e) {
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

	public static void updateOverdueCycleTask(){
		String Tags =  "|TaskTool|updateOverdueCycleTask|";

		if (checkTaskCycle()!=null &&checkTaskCycle().size()>0){
			List list_overdueOpentasks=checkTaskCycle();
			Log.d(Tags+"|cycle task|",""+list_overdueOpentasks.size());
			for (int i=0;i<list_overdueOpentasks.size();i++) {
				Cursor c = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_SN + "=" + "'" + list_overdueOpentasks.get(i) + "'", null);
				try {
					//cancelTaskCycle(c);
					//TaskData.adapterUpdate();
					new CycleTaskUtils(c).addLastestTaskCycle();

					Log.d(Tags + "|cycle task|", "" + list_overdueOpentasks.get(i));
				}catch(Exception e){
					e.printStackTrace();
				}finally {
					c.requery();
					queryTaskDetail(list_overdueOpentasks.get(i).toString());
					if (c!=null) {
						c.close();
					}
				}
			}
		}
		//TaskData.adapterUpdate();
	}

	public static void cancelTaskCycle(Cursor c){
			String Tags = " AutoCancelCurrentTask";

			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				String taskhistory = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_HISTORY));
				String tasklesson = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_LESSON));
				String taskstatus = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS));
				String task_sn = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
				Log.d(Tags,"old sn"+task_sn );
				String where = TaskData.TdDB.TASK_SN + " = ?";
				String[] whereValue = { task_sn };

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
				final ContentValues cv = new ContentValues();

				cv.put(TaskData.TdDB.TASK_LESSON, tasklesson);
				cv.put(TaskData.TdDB.TASK_HISTORY, taskhistory);
				//cv.put(TaskData.TdDB.TASK_STATUS, "cancelled");
				//cv.put(TaskData.TdDB.TASK_MODIFIED, TaskTool.getCurTime());
				//cv.put(TaskData.TdDB.TASK_SEQUENCE, "");
				//cv.put(TaskData.TdDB.TASK_SEQUENCENO, "");
				Log.d(Tags,cv.keySet().toString());
				//TaskData.db_TdDB.execSQL("update "+TaskData.TdDB.TABLE_NAME_TaskMain+" set "+TaskData.TdDB.TASK_STATUS+"="+"'cancelled'"+";");
						//TaskData.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
				//Log.d(Tags,"cancelled done"+a+" t_sn"+t_sn);

				final ContentValues cv1 = new ContentValues();

				//cv.put(TaskData.TdDB.TASK_LESSON, tasklesson);
				//cv.put(TaskData.TdDB.TASK_HISTORY, taskhistory);
				cv1.put(TaskData.TdDB.TASK_LESSON, tasklesson);
				cv1.put(TaskData.TdDB.TASK_HISTORY, taskhistory);
				cv1.put(TaskData.TdDB.TASK_STATUS, "cancelled");
				cv1.put(TaskData.TdDB.TASK_MODIFIED, TaskTool.getCurTime());
				cv1.put(TaskData.TdDB.TASK_SEQUENCE, "");
				cv1.put(TaskData.TdDB.TASK_SEQUENCENO, "");
				Log.d(Tags,cv1.keySet().toString());
				long a1 = TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv1, where, whereValue);
				Log.d(Tags,"cancelled done"+a1+" t_sn"+task_sn);

			}else{
				Log.d(Tags,"cancelled"+c.getCount()+"not done");
			}

		}

	public static List checkTaskCycle(){
		String Tags = "|TaskTool|checkTaskCycle|";
		String day_tsn;
		String cycle_unit;
		String deadline_str;
		String curTime_str = TaskTool.getCurTime();
		List list_delaycycletasks=new ArrayList<>();;
		//Cursor cursor=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain,null);

		if (TaskData.cursor_opentasks!=null&&TaskData.cursor_opentasks.getCount()>0) {
			TaskData.cursor_opentasks.requery();
			TaskData.cursor_opentasks.moveToFirst();
			//Log.d(Tags ,""+c.getCount());

			do {
				cycle_unit = TaskData.cursor_opentasks.getString(TaskData.cursor_opentasks.getColumnIndex(TaskData.TdDB.TASK_CYCLEUNIT));
				deadline_str = TaskData.cursor_opentasks.getString(TaskData.cursor_opentasks.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
				Log.d(Tags ,""+cycle_unit+" "+deadline_str);
				if (TimeData.TimeGap_YYMMDDHHSS(curTime_str, deadline_str) < 0) {

					if (cycle_unit != null && cycle_unit.equals("单次")) {

					} else {
						if (cycle_unit != null&& !cycle_unit.equals("单次")) {
							day_tsn = TaskData.cursor_opentasks.getString(TaskData.cursor_opentasks.getColumnIndex(TaskData.TdDB.TASK_SN));
							list_delaycycletasks.add(day_tsn);
						}
					}
				}
			} while (TaskData.cursor_opentasks.moveToNext());
		}

		return list_delaycycletasks;
	}

	public static void queryTaskDetail(String t_sn){
		String Tags = "queryTaskDetail";
		String cycle_unit;
		String deadline_str;
		String curTime_str = TaskTool.getCurTime();
		Cursor c=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_SN+"='"+t_sn+"'",null);

		if (c!=null&&c.getCount()>0) {
			c.moveToFirst();
			//Log.d(Tags ,""+c.getCount());
			for (int i=0;i<c.getColumnCount();i++){
				Log.d("colum"+i, c.getColumnName(i)+"   "+c.getString(i));
			}
		}
		TaskData.adapterUpdate();
	}

	public static void quickclose(Context context){

		boolean readyflag;
		String t_sn;
		String tasklesson;
		String taskimportance;
		String task_status;
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
			curTime=TaskTool.getCurTime();
			c = TaskData.db_TdDB.rawQuery("select * from "+a+" where "+TaskData.TdDB.TASK_ID+"="+TaskData.selTaskID,null);
			//
			c.moveToFirst();
			// Log.d("selTab count", "total "+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME))+" "+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME+" "+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS+""+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)))))));
			t_sn=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
			tasklesson = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_LESSON));
			taskhistory = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_HISTORY));
			taskimportance = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE));
			task_status = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS));

			if (task_status.equals("finished")){
				return;
			}
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
				cv.put(TaskData.TdDB.TASK_CLOSEDTIME,TaskTool.getCurTime());
				String taskdeadline=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
				if (TimeData.CompareTime_YY(taskdeadline,TaskTool.getCurTime())<=0){
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
				if (TaskData.completionAnimation==true) {
					LogUtils.d("completionAnimation"+TaskData.completionAnimation);
                    if (taskimportance.equals("3")) {
                        	showRocket();
                    } else {
                        if (taskimportance.equals("2")) {
                            showFlower();
                        }
                        if (taskimportance.equals("1")) {
                            showArrow();
                        }
                    }
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
				cv.put(TaskData.TdDB.TASK_MODIFIED,TaskTool.getCurTime());
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
				cv.put(TaskData.TdDB.TASK_MODIFIED,TaskTool.getCurTime());
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


	public static void slideClose(Context context,String task_sn){

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
			curTime=TaskTool.getCurTime();
			c = TaskData.db_TdDB.rawQuery("select * from "+a+" where "+TaskData.TdDB.TASK_SN+"="+"'"+task_sn+"'",null);
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
				cv.put(TaskData.TdDB.TASK_CLOSEDTIME,TaskTool.getCurTime());
				String taskdeadline=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
				if (TimeData.CompareTime_YY(taskdeadline,TaskTool.getCurTime())<=0){
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
				cv.put(TaskData.TdDB.TASK_MODIFIED,TaskTool.getCurTime());
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
				cv.put(TaskData.TdDB.TASK_MODIFIED,TaskTool.getCurTime());
				//Log.d("submit data",cv.toString() );
				long ur=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);

				if (ur==1){
					TaskData.getSequenceNo();
					TaskData.adapterUpdate();
					Toast.makeText(context, "快速结束任务成功", Toast.LENGTH_SHORT).show();
					TaskData.selTaskID=null;
					TaskData.selTaskSN=null;
				}else{
					Log.d("TaskTool", "快速结束任务失败"+TaskData.TdDB.TASK_ID);
				}
			} else{
				//Toast.makeText(getActivity(), "无法快速关闭，还有未完成的子任务", Toast.LENGTH_SHORT).show();;
			}

		}else {
			//Toast.makeText(context, "未选定任务", Toast.LENGTH_SHORT).show();
		}
	}
	public static void quickCancel(Context context){

		if (TaskData.selTaskID!=null) {
			String taskhistory;
			String tasklesson;
			String taskstatus;
			// Log.d("show closedata",taskclosechoice);
			//cv.put(TaskData.TdDB.RECORD_NO, String.valueOf(c2.));
			//cv.put(TaskData.TdDB.RECORD_TIME, curTime);
			//cv.put(TaskData.TdDB.RECORD_PROGRESS, et_recordprogress.getText().toString());
			Cursor c=TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain+ " where " +
					TaskData.TdDB.TASK_ID + "=" + "'"+TaskData.selTaskID+"'", null);
			if (c.getCount()>0) {
				c.moveToFirst();
				TaskData.selTaskSN=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
				taskstatus= c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS));
				taskhistory = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_HISTORY));
				tasklesson = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_LESSON));
				//Toast.makeText(context, TaskData.selTaskSN+taskstatus, Toast.LENGTH_SHORT).show();
				if (taskstatus.equals("open")) {
					if (tasklesson != null && !org.apache.http.util.TextUtils.isEmpty(tasklesson)) {
						tasklesson = tasklesson.trim() + "\n" + "快速取消" + TaskData.tag_tasklesson;
					} else {
						tasklesson = "快速取消" + TaskData.tag_tasklesson;
					}

					if (taskhistory != null && !org.apache.http.util.TextUtils.isEmpty(taskhistory)) {
						taskhistory = taskhistory.trim() + "\n" + "快速取消" + TaskData.tag_taskhistory;
					} else {
						taskhistory = "快速取消" + TaskData.tag_taskhistory;
					}
					final ContentValues cv = new ContentValues();
					String where = TaskData.TdDB.TASK_SN + " = ?";
					String[] whereValue = {TaskData.selTaskSN};
					cv.put(TaskData.TdDB.TASK_STATUS, "cancelled");
					cv.put(TaskData.TdDB.TASK_SEQUENCENO,"");
					cv.put(TaskData.TdDB.TASK_MODIFIED, TaskTool.getCurTime());
					cv.put(TaskData.TdDB.TASK_LESSON, tasklesson);
					cv.put(TaskData.TdDB.TASK_HISTORY, taskhistory);
					//Log.d("submit data",cv.toString() );
					long a = TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);

					if (a>0) {
						Toast.makeText(context, "任务快速取消成功", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(context, "任务快速取消失败", Toast.LENGTH_SHORT).show();
						;
					}
					TaskData.adapterUpdate();
				}
			}
		}else{
			        Toast.makeText(context, "请先选择任务", Toast.LENGTH_SHORT).show();
		}
	}

	public static void quickdelete(Context context){

		if (TaskData.selTaskID!=null) {
			String a = TaskData.TdDB.TABLE_NAME_TaskMain;
			Cursor c=TaskData.db_TdDB.rawQuery("select * from " + a + " where " + TaskData.TdDB.TASK_ID + "=" + "'"+TaskData.selTaskID+"'", null);
			if (c.getCount()>0) {
				c.moveToFirst();
				TaskData.selTaskSN=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));

				//TaskData.adapterUpdate();

				if (TaskData.privilege > TaskData.entryright) {
					new ConnMySQL(context).DelByGsonArrayRequestPost("TaskmainServlet", TaskData.TdDB.TABLE_NAME_TaskMain, TaskData.selTaskSN);
					LogUtils.d(TaskData.selTaskSN);
				}
				TaskData.db_TdDB.execSQL("delete from " + a + " where " + TaskData.TdDB.TASK_SN + "=" + "'"+TaskData.selTaskSN+"'");
				LogUtils.d("delete from " + a + " where " + TaskData.TdDB.TASK_SN + "=" + "'"+TaskData.selTaskSN+"'");
				/*
				TaskData.db_TdDB.execSQL("delete from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " +
						TaskData.TdDB.TASK_SN + "=" + "'" + TaskData.selTaskSN + "'" + " and " +
						TaskData.TdDB.TASK_STATUS + "=" + "'finished'" + " and " +
						TaskData.TdDB.TASK_USER + "=" + "'" + TaskData.user + "';");
                 */

				Cursor cr = DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId=" + "'" + "T" + TaskData.selTaskID + "';");

				if (cr.getCount() > 0) {
					cr.moveToFirst();
					do {
						String remindersn = cr.getString(1);
						Alarm.alarmCancel(context, remindersn);
						int reminderno = cr.getInt(0);
						DataSupport.delete(Reminder.class, reminderno);
						Log.d("delete reminder", "delete " + reminderno);

					} while (cr.moveToNext());

				}
				Toast.makeText(context,"删除成功" , Toast.LENGTH_SHORT).show();
				TaskData.adapterUpdate();
				TaskData.selTaskID = null;
				TaskData.selTaskSN = null;
			}else{
				//Toast.makeText(context,"请先指定任务",Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(context,"请先指定任务",Toast.LENGTH_SHORT).show();
		}

	}

	public static void quickdelete(Context context,String task_sn){

		if (task_sn!=null) {
			String a = TaskData.TdDB.TABLE_NAME_TaskMain;
			Cursor c=TaskData.db_TdDB.rawQuery("select * from " + a + " where " + TaskData.TdDB.TASK_SN + "=" + "'"+task_sn+"'", null);
			if (c.getCount()>0) {

				//TaskData.adapterUpdate();

				if (TaskData.privilege > TaskData.entryright) {
					new ConnMySQL(context).DelByGsonArrayRequestPost("TaskmainServlet", TaskData.TdDB.TABLE_NAME_TaskMain, TaskData.selTaskSN);
					LogUtils.d(TaskData.selTaskSN);
				}
				/*
				TaskData.db_TdDB.execSQL("delete from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " +
						TaskData.TdDB.TASK_SN + "=" + "'" + task_sn + "'" + " and " +
						TaskData.TdDB.TASK_STATUS + "=" + "'finished'" + " and " +
						TaskData.TdDB.TASK_USER + "=" + "'" + TaskData.user + "';");*/
				TaskData.db_TdDB.execSQL("delete from " + a + " where " + TaskData.TdDB.TASK_SN + "=" + "'"+task_sn+"'");

				Cursor cr = DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId=" + "'" + "T" + TaskData.selTaskID + "';");

				if (cr.getCount() > 0) {
					cr.moveToFirst();
					do {
						String remindersn = cr.getString(1);
						Alarm.alarmCancel(context, remindersn);
						int reminderno = cr.getInt(0);
						DataSupport.delete(Reminder.class, reminderno);
						Log.d("delete reminder", "delete " + reminderno);

					} while (cr.moveToNext());

				}
				Toast.makeText(context,"删除成功" , Toast.LENGTH_SHORT).show();
				TaskData.adapterUpdate();
				TaskData.selTaskID = null;
				TaskData.selTaskSN = null;
			}else{
				//Toast.makeText(context,"请先指定任务",Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(context,"请先指定任务",Toast.LENGTH_SHORT).show();
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