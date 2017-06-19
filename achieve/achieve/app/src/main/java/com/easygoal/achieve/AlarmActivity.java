package com.easygoal.achieve;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

//import com.umeng.message.PushAgent;

public class AlarmActivity extends Activity {

	Handler handler;
	Timer timer = new Timer();
	String curTime;
	SimpleDateFormat formatter;
	SimpleDateFormat sdf_day;
	String recAlarmName;
	BroadcastReceiver reciever;
	MediaPlayer mMediaPlayer;

	public AlarmActivity() {
		// TODO Auto-generated constructor stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		//new AlertDialog.Builder(getApplication()).setTitle("闹钟").setMessage("小猪小猪快起床~").show();
		//Toast.makeText(getApplication(), "闹钟时间到了!", Toast.LENGTH_SHORT).show();

		//vibrator.vibrate(1000);

		//IntentFilter filter=new IntentFilter();
		//AlarmReciever reciever = new AlarmReciever();
		//getApplication().registerReceiver(reciever,filter);
		//PushAgent.getInstance(getApplicationContext()).onAppStart();
		final String recAlarmName = getIntent().getStringExtra("alarmName");
		final String recSourceId = getIntent().getStringExtra("sourceId");
		final String recRecurring = getIntent().getStringExtra("recurring");

		if(recAlarmName!=null){
			//Toast.makeText(getApplicationContext(), "闹钟时间到了!"+recSourceId+"no"+recAlarmno+"recurr"+ recRecurring , Toast.LENGTH_SHORT).show();

			SharedPreferences sp=getApplication().getSharedPreferences("userinfo",Context.MODE_PRIVATE);

			boolean recVibration= sp.getBoolean("vibration",true);

			if  (recVibration==true){
				Vibrator vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
				vibrator.vibrate(2000);
			}
			// String recRing=getIntent().getStringExtra("vibration");
			boolean recAlarmRing= sp.getBoolean("alarmring",true);

			if  ( recAlarmRing==true){
				//Toast.makeText(getApplicationContext(), "闹钟铃声"+recAlarmRing, Toast.LENGTH_SHORT).show();
				try {
					mMediaPlayer = MediaPlayer.create(getApplication(), getSystemDefultRingtoneUri());
					if (mMediaPlayer!=null) {
						mMediaPlayer.setLooping(true);
						//mMediaPlayer.prepare();
						mMediaPlayer.start();
					}
				} catch (IllegalStateException e) {
					Log.d("alarm activity",""+e.toString() );
					e.printStackTrace();
				}

			}else{
				//Toast.makeText(getApplicationContext(), "无闹钟铃声"+recAlarmRing, Toast.LENGTH_SHORT).show();
			}

			boolean recNotify= sp.getBoolean("notification",true);
			//  String recNotify=getIntent().getStringExtra("notification");
			if  (recNotify==true){
				//Toast.makeText(getApplicationContext(), "大事提醒"+recNotify, Toast.LENGTH_SHORT).show();
				TaskTool.notiSend(getApplication(),"大事提醒", recAlarmName, System.currentTimeMillis());
			}else{
				//Toast.makeText(getApplicationContext(), "无大事提醒", Toast.LENGTH_SHORT).show();
			}

			//long curTimeData = new Date().getTime();

			formatter = new SimpleDateFormat ("HH:mm:ss");
			sdf_day = new SimpleDateFormat ("yyyy-MM-dd");

			final TextView tv_curtime=(TextView)findViewById(R.id.tv_curtime);
			final TextView tv_curday=(TextView)findViewById(R.id.tv_curday);
			final TextView tv_cancel=(TextView)findViewById(R.id.tv_cancel);
			final TextView tv_alarmName=(TextView)findViewById(R.id.tv_alarmName);
			final TextView tv_timeout=(TextView)findViewById(R.id.tv_timeout);
			tv_alarmName.setText(recAlarmName+"("+recSourceId+")");
			AnimUtils.applyShimmer(tv_timeout);
			AnimUtils.applyJump(tv_alarmName);

			TextView tv_close=(TextView)findViewById(R.id.tv_close);

			tv_cancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					if (recSourceId!=null){

						Alarm.alarmCancel(getApplicationContext(), recSourceId);
						if (mMediaPlayer != null)
						{  try{
							if(mMediaPlayer.isPlaying()){
								mMediaPlayer.stop();
							}
							mMediaPlayer.release();
						} catch (IllegalStateException e) {
							Log.d("alarm activity",""+e.toString() );
							e.printStackTrace();
						}
						}


						finish();
					}else{
						//Toast.makeText(getApplication(), "闹钟不存在-"+recSourceId, Toast.LENGTH_LONG).show();
						finish();
					}

				}

			});

			tv_close.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (Integer.parseInt(recRecurring)==0){
						Alarm.alarmCancel(getApplicationContext(), recSourceId);
					}
					if (mMediaPlayer != null)
					{
						if(mMediaPlayer.isPlaying()){
							mMediaPlayer.stop();
						}
						mMediaPlayer.release();
					}

					finish();
				}

			});





			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					// 需要做的事:发送消息
					Message message = new Message();
					message.what = 1;

					handler.sendMessage(message);
				}
				// 1s后执行task,经过1s再次执行
			};
			handler = new Handler() {
				public void handleMessage(Message msg) {
					if (msg.what == 1) {

						Date curDate = new Date();//获取当前时间
						curTime = formatter.format(curDate);
						tv_curtime.setText(curTime);
						String curDay = sdf_day.format(curDate);
						tv_curday.setText(curDay);
						//TaskData.adapterUpdate();
						//if (TaskData.adapter_eventcounttime!=null){
						//TaskData.adapter_eventcounttime.notifyDataSetChanged();;
						//}
						// TODO Auto-generated method stub
						// TaskData.cursor_todaytasks.requery();
						//TaskData.adapter_todaytasks.notifyDataSetChanged();
				   	/*  TaskData.cursor_todaytasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=? and "+
			                   TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
			                   TaskData.TdDB.TASK_DEADLINETIMEDATA+">?", 
			                   new String[]{"open",
			                   new SimpleDateFormat ("yy-MM-dd").format(new Date()),
			           		   String.valueOf(new Date().getTime()/(1000*60))});
				     
				       TaskData.adapter_todaytasks = new mcAdapter_tasks( TaskData.todaycontext,R.layout.lv_todaytasks,TaskData.cursor_todaytasks); 
				       Log.d("adapter_todaytasks",TaskData.adapter_todaytasks.toString() );
				       TaskData.adapter_todaytasks.notifyDataSetChanged();
				       TaskData.lv_todaytasks.setAdapter(TaskData.adapter_todaytasks);
				       */
						//Toast.makeText(getApplicationContext(), "update!!!"+TaskData.cursor_todaytasks.getCount(), Toast.LENGTH_SHORT).show();;
					}
					super.handleMessage(msg);
				};
			};


			timer.schedule(task, 1000, 1000);

		}

	}


	private Uri getSystemDefultRingtoneUri() {

		return RingtoneManager.getActualDefaultRingtoneUri(this,
				RingtoneManager.TYPE_RINGTONE);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}


}
