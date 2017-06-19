package com.easygoal.achieve;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.util.TextUtils;
import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.support.multidex.MultiDex;
import android.util.Log;


//import com.umeng.message.IUmengRegisterCallback;
//import com.umeng.message.PushAgent;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class AppData extends Application {

	 
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		    //LeakCanary.install(this);
		    RongIM.init(this);

			TaskData.TdDB = new ToDoDB(getApplicationContext(), TaskData.db_TdDBname,null, 2);
			TaskData.db_TdDB = TaskData.TdDB.getWritableDatabase();
			//TaskData.TdDB.onCreate(TaskData.db_TdDB);
			LitePalApplication.initialize(getApplicationContext());
		    TaskData.db = Connector.getDatabase();
		    TaskData.hostname="http://123.206.229.72:8080/EasyTest/";
		    //TaskData.hostname="http://192.168.1.100:8080/EasyTest/";
		//final PushAgent mPushAgent = PushAgent.getInstance(this);
//????????????¦Å???register??????????????
		/*
		new Thread(new Runnable() {
			@Override
			public void run() {
				mPushAgent.register(new IUmengRegisterCallback() {

					@Override
					public void onSuccess(String deviceToken) {
						//?????????device token
						Log.d("deviceToken",deviceToken);
					}

					@Override
					public void onFailure(String s, String s1) {
						Log.d("deviceToken failure",""+s+" "+s1);
					}
				});

			}
		}).start();*/

		    init();

		    SharedPreferences sp = getSharedPreferences("userinfo" , MODE_PRIVATE);
		    TaskData.user=sp.getString("phoneNo", "");
		    //TaskData.user=TaskTool.getPhoneNumber(getApplicationContext());

		    TaskData.adapterInit();

		//initphone(getApplicationContext());

		    //TaskData.hostname="http://192.168.1.104:8080/Easy/";
		    //TaskData.hostname="http://123.206.229.72:8080/EasyTest/";
		   
			//TaskData.performanceflag=0;
			//TaskData.valuelistflag=0;
			//TaskData.privilege=0;
			//TaskData.overduetextcolor=getApplicationContext().getResources().getColor(R.color.overduetextcolor);
			//TaskData.tag_taskhistory="("+TaskTool.getCurTime()+"-"+TaskTool.getPhoneNumber(getApplicationContext())+")"+"\n";
			//TaskData.tag_taskhistory="("+TaskTool.getCurTime()+")"+"\n";
			//TaskData.tag_tasklesson="("+TaskTool.getCurTime()+")";
			
			TaskData.ls1=new ArrayList<RecordBean>();
			//TaskData.valuelist=null;
			//TaskData.alarmlist=new ArrayList<PendingIntent>();
			//TaskData.bcreceiverlist=new ArrayList<BroadcastReceiver>() ;
			//TaskData.alarmReceiverSet=new HashSet<BroadcastReceiver>();
			//TaskData.subItemlist=new ArrayList<RecordBean>();
			//TaskData.subdellist=new ArrayList<String>();	
			//TaskData.clickedlist=new ArrayList<String>();
			//TaskData.subItems_idno=new ArrayList();
			//TaskData.subItems_deadline=new ArrayList();
			//TaskData.subItems_comments=new ArrayList();
			//TaskData.subItems_weight=new ArrayList();
			//TaskData.subItems_del=new ArrayList();
			//notiSendini();
			//TaskTool.notiSend(getApplicationContext(), "????", "?????????!", System.currentTimeMillis());	

	   super.onCreate();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

	}
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	/*
	public void login(){
	SharedPreferences preference = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
	int times=preference.getInt("logintimes",1);
	Editor editor = preference.edit(); //???????
	String userPhone = getPhoneNumber();
	String lastlogintime=new Timestamp(new Date().getTime()).toString();
    editor.putString("userPhone",userPhone);
    editor.putString("lastlogintime",lastlogintime);
    times++;
    editor.putInt("logintimes",times);
    editor.commit();//?????
   
	Log.d("login data",userPhone+" lastloginat:"+lastlogintime+" times:"+times);
	}
	*/

	public void initData() {
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		if (wifi | internet) {

			SharedPreferences sp = this.getSharedPreferences("userinfo", MODE_PRIVATE);
			String phoneNo = TaskData.user;
			String username = sp.getString("username", "");
			if (!TextUtils.isEmpty(TaskData.user) && TaskTool.isMobileNO11(TaskData.user)) {
				// conSyn_db();

				Cursor cursor_rating = DataSupport.findBySQL("select * from userratingbean where username=" + "'" + TaskData.user + "'");
				Cursor cursor_reminder = DataSupport.findBySQL("select * from reminder where username=" + "'" + TaskData.user + "'");
				Cursor cursor_memo = DataSupport.findBySQL("select * from memo where username=" + "'" + TaskData.user + "'");

				final ConnMySQL conn = new ConnMySQL(getApplicationContext());
				boolean flag = false;
				Cursor cursor_taskrec = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord + " where " + TaskData.TdDB.TASK_USER + "=?", new String[]{TaskData.user});
				conn.InitDataGsonArrayRequestPostSuccess("TaskrecServlet", cursor_taskrec);
				TaskData.cursor_alltasks = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_USER + "=?", new String[]{TaskData.user});
				conn.InitDataGsonArrayRequestPostSuccess("TaskmainServlet", TaskData.cursor_alltasks);


				//conn.InitDataGsonArrayRequestPostSuccess("MemoServlet",cursor_memo);
				//conn.InitDataGsonArrayRequestPostSuccess("ReminderServlet",cursor_reminder);
				//conn.InitDataGsonArrayRequestPostSuccess("RatingServlet",cursor_rating);
			}

		}
	};


	public void notiSendini(){
	//???????NotificationManager??????
	String ns = Context.NOTIFICATION_SERVICE;
	NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);

	//????Notification?????????
	Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.user);
			//getResources().getDrawable(R.drawable.ic_launcher); //?????
	//CharSequence tickerText = "Hello"; //?????????????????
	//long when = System.currentTimeMillis(); //?????????????????????????
	//???????????????Nofification
	
	//Notification notification = new Notification(icon,tickerText,when);
	Notification notification = new Notification.Builder(getBaseContext()).
			setContentText("i love you")
			.setContentTitle("ok")
			.setTicker("????")
			.setLargeIcon(icon)
			.setSmallIcon(R.drawable.ic_launcher)
			.getNotification();
			
	
	
	/*
	* ???????
	* notification.defaults |=Notification.DEFAULT_SOUND;
	* ????????????????
	* notification.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");
	* notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
	* ?????????????????????????????????????????????notification??flags???????"FLAG_INSISTENT"
	* ???notification??defaults????????"DEFAULT_SOUND"???????????????????sound????§Ø????????
	
	* ?????

	* notification.defaults |= Notification.DEFAULT_VIBRATE;

	* ?????????????????????

	* long[] vibrate = {0,100,200,300}; //0???????????100????????????200??????????300????

	* notification.vibrate = vibrate;

	* long?????????????????¦Ê¦Ã???

	* ???notification??defaults????????"DEFAULT_VIBRATE",??????????????vibrate????§Ø??????

	*/

	/*

	* ???LED??????

	* notification.defaults |= Notification.DEFAULT_LIGHTS;

	* ????????????LED??????:

	* notification.ledARGB = 0xff00ff00;

	* notification.ledOnMS = 300; //???????

	* notification.ledOffMS = 1000; //??????

	* notification.flags |= Notification.FLAG_SHOW_LIGHTS;

	*/

	/*

	* ?????????????

	* notification.flags |= FLAG_AUTO_CANCEL; //??????????????????????????

	* notification.flags |= FLAG_INSISTENT; //??????????????????????????

	* notification.flags |= FLAG_ONGOING_EVENT; //???????????????"Ongoing"??"????????"????

	* notification.flags |= FLAG_NO_CLEAR; //???????????????§Ö?"?????"????????????

	* //??????FLAG_ONGOING_EVENT??????

	* notification.number = 1; //number??¦Á???????????????????????????????????????????

	* //??????????¦²??????1???

	* notification.iconLevel = ; //

	*/

	//??????????????
	Context context = getApplicationContext(); //??????
	CharSequence contentTitle = "EasyGoal"; //????????
	CharSequence contentText = "welcome on board!"; //????????
	Intent notificationIntent = new Intent(context,MainActivity.class); //???????????????Activity
	PendingIntent contentIntent = PendingIntent.getActivity(context,0,notificationIntent,0);
	//notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	//??Notification?????NotificationManager
    int ID_NOTIFICATION = 0;
	mNotificationManager.notify(ID_NOTIFICATION,notification);
	Log.d("notification", "start"+contentIntent.toString());
 }		
	  public void init(){
	    	
	    	TaskData.privilege=0; 
	        TaskData.initreminderflag=1;
	 		TaskData.alarmReceiverSet=new HashSet<BroadcastReceiver>();
	 		TaskData.alarmpilist=new ArrayList<PendingIntent>();
	 		TaskData.bcreceiverlist=new ArrayList<BroadcastReceiver>();
	 		TaskData.alarmsourceidlist=new ArrayList<String>();
	 		TaskData.overduetextcolor=getApplicationContext().getResources().getColor(R.color.overduetextcolor);
	 		TaskData.color_header=getApplicationContext().getResources().getColor(R.color.orange);
	 		//TaskData.tag_taskhistory="("+TaskTool.getCurTime()+"-"+TaskTool.getPhoneNumber(getApplicationContext())+")"+"\n";

	 		//TaskData.ls1=new ArrayList<RecordBean>();
	 		TaskData.valuelist=null;
	 		TaskData.valuelistflag=0;  
	 		//TaskData.alarmlist=new ArrayList<PendingIntent>();
	 		//TaskData.bcreceiverlist=new ArrayList<BroadcastReceiver>() ;
		    TaskData.flag_findteam=-1;
	 		TaskData.subItemlist=new ArrayList<RecordBean>();
	 		TaskData.subdellist=new ArrayList<String>(); 
	 		
	    }
   
public void initphone(Context context){
		  
	  if (!TextUtils.isEmpty(TaskTool.getPhoneNumber(context))&&TaskTool.isMobileNO11(TaskTool.getPhoneNumber(context))){
		    TaskData.user=TaskTool.getPhoneNumber(context);
		    TaskData.adapterInit();
	  } 	
	//Toast.makeText(getApplicationContext(), "phoneNo:"+TaskData.user, Toast.LENGTH_SHORT).show();
 }
	
  
}
