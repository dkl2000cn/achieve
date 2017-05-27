package com.easygoal.achieve;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentHome extends Fragment {
	
	
	String curTime;
	public SimpleCursorAdapter adapter;
	int selectedID=0;
	View oldview=null;
    View newview = null;
	TextView tv_bluelight;
	TextView tv_yellowlight;
	TextView tv_redlight;
	com.easygoal.achieve.WindMillView tv_worklight;
	Fragment subfg_todaytasks;
	com.easygoal.achieve.mListView lv_todaytasks;
	String Tags="FragmentHome";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.rfg_bottomtab1_home, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		  final TextView tv_todaydate=(TextView)v.findViewById(R.id.tv_todaydate);
		  final TextView tv1=(TextView)v.findViewById(R.id.task_item1_name_tv);
		  final TextView tv4=(TextView)v.findViewById(R.id.task_item4_importance_tv);
		  final TextView tv5=(TextView)v.findViewById(R.id.task_item5_urgency_tv);
		  final TextView tv6=(TextView)v.findViewById(R.id.task_item6_assess_tv);
		  final TextView tv15=(TextView)v.findViewById(R.id.task_item15_deadline_tv);
		  final TextView tv16=(TextView)v.findViewById(R.id.task_item16_progress_tv);
		  final TextView tv18=(TextView)v.findViewById(R.id.task_item18_status_tv);
		  final TextView tv19=(TextView)v.findViewById(R.id.task_item19_finished_tv);
		  final TextView tv20=(TextView)v.findViewById(R.id.task_item20_delayed_tv);
		  TaskData.tv_opentaskscount_t=(TextView)v.findViewById(R.id.tv_opentaskscount);
		  TaskData.tv_todaytaskscount_t=(TextView)v.findViewById(R.id.tv_todaytaskscount);
		  TaskData.tv_overduetaskscount_t=(TextView)v.findViewById(R.id.tv_overduetaskscount);
		  TaskData.tv_opentaskscount_a=(TextView)v.findViewById(R.id.tv_opentaskscount_a);
		  TaskData.tv_todaytaskscount_a=(TextView)v.findViewById(R.id.tv_todaytaskscount_a);
		  TaskData.tv_overduetaskscount_a=(TextView)v.findViewById(R.id.tv_overduetaskscount_a);
		  TaskData.tv_opentaskscount_c=(TextView)v.findViewById(R.id.tv_opentaskscount_c);
		  TaskData.tv_todaytaskscount_c=(TextView)v.findViewById(R.id.tv_todaytaskscount_c);
		  TaskData.tv_overduetaskscount_c=(TextView)v.findViewById(R.id.tv_overduetaskscount_c);
		  TaskData.tv_opentaskscount_b=(TextView)v.findViewById(R.id.tv_opentaskscount_b);
		  TaskData.tv_todaytaskscount_b=(TextView)v.findViewById(R.id.tv_todaytaskscount_b);
		  TaskData.tv_overduetaskscount_b=(TextView)v.findViewById(R.id.tv_overduetaskscount_b);
		  TaskData.tv_opentaskscount_d=(TextView)v.findViewById(R.id.tv_opentaskscount_d);
		  TaskData.tv_todaytaskscount_d=(TextView)v.findViewById(R.id.tv_todaytaskscount_d);
		  TaskData.tv_overduetaskscount_d=(TextView)v.findViewById(R.id.tv_overduetaskscount_d);
		  lv_todaytasks=(com.easygoal.achieve.mListView)v.findViewById(R.id.todaytasks_lv);
		//  TextView textView = ViewFinder.findViewById(R.id.my_textview); 
		 Button btn_addrecord=(Button)v.findViewById(R.id.task_addrecord_bt);
		 Button btn_taskdelete=(Button)v.findViewById(R.id.task_delete_bt);
		 Button btn_taskupdate=(Button)v.findViewById(R.id.task_update_bt);
		 Button btn_taskclear=(Button)v.findViewById(R.id.task_clear_bt);
		 final Button btn_detail=(Button)v.findViewById(R.id.taskdetails_btn);
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		   // final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		  //  Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));

		 tv_bluelight=(TextView)v.findViewById(R.id.tv_bluelight);
		 tv_yellowlight=(TextView)v.findViewById(R.id.tv_yellowlight);
		 tv_redlight=(TextView)v.findViewById(R.id.tv_redlight);
		 tv_worklight=(com.easygoal.achieve.WindMillView)v.findViewById(R.id.tv_worklight);
		 homepage_countupdate();
          /*
		  int opentaskscount_t = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{TaskData.user,"open"}).getCount();
		  TaskData.tv_opentaskscount_t.setText(String.valueOf(opentaskscount_t));
		  int opentaskscount_a= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+TaskData.TdDB.TASK_IMPORTANCE+">=? and "+TaskData.TdDB.TASK_URGENCY+">=?", new String[]{TaskData.user,"open","2","2"}).getCount();
		  TaskData.tv_opentaskscount_a.setText(String.valueOf(opentaskscount_a));
		  int opentaskscount_c= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+TaskData.TdDB.TASK_IMPORTANCE+">=? and "+TaskData.TdDB.TASK_URGENCY+"<?", new String[]{TaskData.user,"open","2","2"}).getCount();
		  TaskData.tv_opentaskscount_c.setText(String.valueOf(opentaskscount_c));
		  int opentaskscount_b= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+TaskData.TdDB.TASK_IMPORTANCE+"<? and "+TaskData.TdDB.TASK_URGENCY+">=?", new String[]{TaskData.user,"open","2","2"}).getCount();
		  TaskData.tv_opentaskscount_b.setText(String.valueOf(opentaskscount_b));
		  int opentaskscount_d= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+TaskData.TdDB.TASK_IMPORTANCE+"<? and "+TaskData.TdDB.TASK_URGENCY+"<?", new String[]{TaskData.user,"open","2","2"}).getCount();
		  TaskData.tv_opentaskscount_d.setText(String.valueOf(opentaskscount_d));
		  
		  //java.util.Date currentdate = new java.util.Date();
		  //String todaydate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		  //Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG);
		  //Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG);
		  
		  TaskData.cursor_todaytasks_t = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
		                               TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
		                               TaskData.TdDB.TASK_DEADLINETIMEDATA+">=?", 
		                               new String[]{TaskData.user,"open",
		                               new SimpleDateFormat ("yy-MM-dd").format(new Date()),
		                       		   String.valueOf(new Date().getTime()/(1000*60))});
		  int todaytaskscount_t = TaskData.cursor_todaytasks_t.getCount();
		  TaskData.tv_todaytaskscount_t.setText(String.valueOf(todaytaskscount_t));
		  TaskData.cursor_todaytasks_a = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+">=? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+">=? and "+
                  TaskData.TdDB.TASK_URGENCY+">=? ", 
                  new String[]{TaskData.user,"open",
                  new SimpleDateFormat ("yy-MM-dd").format(new Date()),
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int todaytaskscount_a = TaskData.cursor_todaytasks_a.getCount();
		  TaskData.tv_todaytaskscount_a.setText(String.valueOf(todaytaskscount_a));
		  TaskData.cursor_todaytasks_c = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+">=? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+">=? and "+
                  TaskData.TdDB.TASK_URGENCY+"<? ", 
                  new String[]{TaskData.user,"open",
                  new SimpleDateFormat ("yy-MM-dd").format(new Date()),
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int todaytaskscount_c = TaskData.cursor_todaytasks_c.getCount();
		  TaskData.tv_todaytaskscount_c.setText(String.valueOf(todaytaskscount_c));
		  TaskData.cursor_todaytasks_b = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+">=? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+"<? and "+
                  TaskData.TdDB.TASK_URGENCY+">=? ", 
                  new String[]{TaskData.user,"open",
                  new SimpleDateFormat ("yy-MM-dd").format(new Date()),
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int todaytaskscount_b = TaskData.cursor_todaytasks_b.getCount();
		  TaskData.tv_todaytaskscount_b.setText(String.valueOf(todaytaskscount_b));
		  TaskData.cursor_todaytasks_d = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+">=? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+"<? and "+
                  TaskData.TdDB.TASK_URGENCY+"<? ", 
                  new String[]{TaskData.user,"open",
                  new SimpleDateFormat ("yy-MM-dd").format(new Date()),
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int todaytaskscount_d = TaskData.cursor_todaytasks_d.getCount();
		  TaskData.tv_todaytaskscount_d.setText(String.valueOf(todaytaskscount_d));
		  
		  TaskData.cursor_overduetasks_t = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+
		                                 TaskData.TdDB.TASK_STATUS+"=? and "+
		                                 TaskData.TdDB.TASK_DEADLINETIMEDATA+"<? "
				                         , new String[]{TaskData.user,"open",String.valueOf(new Date().getTime()/(1000*60))});
		  int overduetaskscount_t = TaskData.cursor_overduetasks_t.getCount();
		  TaskData.tv_overduetaskscount_t.setText(String.valueOf(overduetaskscount_t));
		  TaskData.cursor_overduetasks_a =  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+
		          TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+"<? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+">=? and "+
                  TaskData.TdDB.TASK_URGENCY+">=? ", 
                  new String[]{TaskData.user,"open",    
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int overduetaskscount_a = TaskData.cursor_overduetasks_a.getCount();
		  TaskData.tv_overduetaskscount_a.setText(String.valueOf(overduetaskscount_a));
		  TaskData.cursor_overduetasks_c =  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+
				  TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+"<? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+">=? and "+
                  TaskData.TdDB.TASK_URGENCY+"<? ", 
                  new String[]{TaskData.user,"open",    
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int overduetaskscount_c = TaskData.cursor_overduetasks_c.getCount();
		  TaskData.tv_overduetaskscount_c.setText(String.valueOf(overduetaskscount_c));
		  TaskData.cursor_overduetasks_b =  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+
				  TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+"<? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+"<? and "+
                  TaskData.TdDB.TASK_URGENCY+">=? ", 
                  new String[]{TaskData.user,"open",    
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int overduetaskscount_b = TaskData.cursor_overduetasks_b.getCount();
		  TaskData.tv_overduetaskscount_b.setText(String.valueOf(overduetaskscount_b));
		  TaskData.cursor_overduetasks_d =  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+
				  TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+"<? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+"<? and "+
                  TaskData.TdDB.TASK_URGENCY+"<? ", 
                  new String[]{TaskData.user,"open",    
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int overduetaskscount_d = TaskData.cursor_overduetasks_d.getCount();
		  TaskData.tv_overduetaskscount_d.setText(String.valueOf(overduetaskscount_d));
		  */
		  
		  
		  
		     SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 ");
			 Date curDate = new Date();//获取当前时间
			
			 String curTime = formatter.format(curDate);
	         Calendar calendar = Calendar.getInstance();
	            int day = calendar.get(Calendar.DAY_OF_WEEK);  
	            String weekday= null;  
	            if (day == 2) {  
	                weekday = "星期一";  
	            } else if (day == 3) {  
	                weekday = "星期二";  
	            } else if (day == 4) {  
	            	weekday = "星期三";  
	            } else if (day == 5) {  
	            	weekday = "星期四";  
	            } else if (day == 6) {  
	            	weekday = "星期五";  
	            } else if (day == 7) {  
	            	weekday = "星期六";  
	            } else if (day == 1) {  
	            	weekday = "星期日";  
	            }  
	            String FullcurTime = curTime+" "+weekday;
       		    tv_todaydate.setText(FullcurTime);
                //AnimUtils.applyJump(tv_todaydate);
		        //AnimUtils.applyRocket(container,getActivity());

          /*
		 Intent intent = new Intent(getActivity(),RocketService.class);
		 //在服务中打开activity，需要设置任务栈：
		 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//任务栈
		 getActivity().startService(intent);
          */
		 // TaskData.tv_todaytaskscount.setText(String.valueOf(TaskData.cursor_todaytasks.getCount()));
		   String a = TaskData.TdDB.TABLE_NAME_TaskMain;
	       String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
	   //  TaskData.cursor_todaytasks=TaskData.db_TdDB.rawQuery("select * from "+a+" where "+"df2.parse("+TaskData.TdDB.TASK_DEADLINEDATE+")>?", new String[]{curTime});
	      TaskData.cursor_todaytasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
	                 TaskData.TdDB.TASK_DEADLINETIMEDATA+"<=?"+
	                 " order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", 
	                 new String[]{TaskData.user,"open",
	         		  String.valueOf(TimeData.todayEndTimeData())});
	      //TaskData.todaycontext=getActivity();
	      
	      TaskData.adapter_todaytasks = new mcAdapter_opentasks(getActivity(),R.layout.lv_todaytasks,TaskData.cursor_todaytasks);
		  //Toast.makeText(getActivity(), String.valueOf(TaskData.cursor_todaytasks.getCount()),Toast.LENGTH_SHORT).show();
	       Log.d("adapter_todaytasks",TaskData.adapter_todaytasks.toString() );
		  // todayshow();
		  //Toast.makeText(getActivity(), ""+TaskData.cursor_todaytasks.getCount()+TaskData.adapter_todaytasks.getCount(),Toast.LENGTH_SHORT).show();
	       lv_todaytasks.setAdapter(TaskData.adapter_todaytasks);

		 lv_todaytasks.setOnDeleteListener(new mListView.OnDeleteListener() {
			 long oldClickTime=0;

			 @Override
			 public void onDelete(int index) {
				 //Log.d("mlistview delete", "del"+index);
				 long task_id = lv_todaytasks.getAdapter().getItemId(index);
				 TaskData.selTaskID=String.valueOf(task_id);
				 TaskTool.quickCancel(getActivity());
				 TaskData.adapter_todaytasks.getCursor().requery();
				 TaskData.adapter_todaytasks.notifyDataSetChanged();
				 //Toast.makeText(getActivity(),"任务"+task_id+"删除成功",Toast.LENGTH_SHORT).show();
			 }

			 @Override
			 public void onDoubleChick(int index) {
				 if (oldClickTime==0){
					 oldClickTime=System.currentTimeMillis();
				 }
				 if ((System.currentTimeMillis()-oldClickTime)>1000) {
					 long task_id = lv_todaytasks.getAdapter().getItemId(index);
					 TaskData.selTaskID = String.valueOf(task_id);
					 DialogFragment_TaskDetail dg_taskdetail = new DialogFragment_TaskDetail();
					 TaskTool.showDialog(dg_taskdetail);
					 oldClickTime=System.currentTimeMillis();
				 }
			 }

			 @Override
			 public void onShow(int index) {
				/*
				 long task_id = lv_todaytasks.getAdapter().getItemId(index);
				 TaskData.selTaskID=String.valueOf(task_id);
				 DialogFragment_TaskDetail dg_taskdetail=new DialogFragment_TaskDetail();
				 TaskTool.showDialog(dg_taskdetail);
				 */
			 }

			 @Override
			 public void onClose(int index) {
				 /*
				 long task_id = lv_todaytasks.getAdapter().getItemId(index);
				 TaskData.selTaskID=String.valueOf(task_id);
				 TaskTool.quickclose(getActivity());
				 TaskData.adapter_todaytasks.getCursor().requery();
				 TaskData.adapter_todaytasks.notifyDataSetChanged();
				 */
				 //Toast.makeText(getActivity(),"任务"+task_id+"快速结束",Toast.LENGTH_SHORT).show();

			 }
		 });


         /*
		 final GestureDetector gestureDetector=new GestureDetector(new GestureDetector.OnGestureListener() {

			 private int verticalMinDistance = 20;
			 private int minVelocity         = 0;
			 @Override
			 public boolean onDown(MotionEvent e) {
				 Log.d("gestureDetector", "down");
				 return true;
			 }

			 @Override
			 public void onShowPress(MotionEvent e) {
				 Log.d("gestureDetector", "showpress");
			 }

			 @Override
			 public boolean onSingleTapUp(MotionEvent e) {
				 Log.d("gestureDetector", "singletap");
				 return false;
			 }

			 @Override
			 public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				 Log.d("gestureDetector", "scroll");

				 return false;
			 }

			 @Override
			 public void onLongPress(MotionEvent e) {
				 Log.d("gestureDetector", "long press");

			 }

			 @Override
			 public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				 int dx = (int) (e2.getX() - e1.getX()); //计算滑动的距离
				 if (dx >20) { //降噪处理，必须有较大的动作才识别
					 if (velocityX > 0) {
						 Log.d("gestureDetector", "left"+velocityX);
						 Toast.makeText(getActivity(), "向右手势"+dx,Toast.LENGTH_SHORT).show();
						 return true;
					 } else {

					 }

				 } else {
					 if (dx < -20) {

						 Toast.makeText(getActivity(), "向左手势"+dx, Toast.LENGTH_SHORT).show();
						 Log.d("gestureDetector", "right" + velocityX);
						 return true;
					 }

				 }
				 //Log.d("gestureDetector", "no"+velocityX);
				 Toast.makeText(getActivity(), "无手势"+dx, Toast.LENGTH_SHORT).show();
				 return false; //当然可以处理velocityY处理向上和向下的动作
								 /*
								 if (e1.getX() - e2.getX() > verticalMinDistance ) {

									 // 切换Activity&& Math.abs(velocityX) > minVelocity
									 // Intent intent = new Intent(ViewSnsActivity.this, UpdateStatusActivity.class);
									 // startActivity(intent);
									 Toast.makeText(getActivity(), "向左手势", Toast.LENGTH_SHORT).show();
								 } else if (e2.getX() - e1.getX() > verticalMinDistance) {

									 // 切换Activity && Math.abs(velocityX) > minVelocity
									 // Intent intent = new Intent(ViewSnsActivity.this, UpdateStatusActivity.class);
									 // startActivity(intent);
									 Toast.makeText(getActivity(), "向右手势", Toast.LENGTH_SHORT).show();
								 }
								 Log.d("gestureDetector", "fling");
								 return false;
			 };
		 });
		 tv_todaydate.setOnTouchListener(new View.OnTouchListener() {
			 @Override
			 public boolean onTouch(View v, MotionEvent event) {
				 return gestureDetector.onTouchEvent(event);
			 }
		 });
		   lv_todaytasks.setLongClickable(true);
		*/

	       lv_todaytasks.setOnItemClickListener(new OnItemClickListener(){
			 int newPos=0;
			 int oldPos=0;

			 @Override
			 public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				 // TODO Auto-generated method stub
				 //TaskData.cursor_opentasks.moveToPosition(position);


				 if (TaskData.cursor_todaytasks.getCount()>0){

					 try{
						 TaskData.selTaskID=String.valueOf(TaskData.cursor_todaytasks.getString(TaskData.cursor_todaytasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
						 TaskData.selTaskSN=String.valueOf(TaskData.cursor_todaytasks.getString(TaskData.cursor_todaytasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
						 //new TaskUtils(getActivity(),"h1").showPopupWindow(getActivity(),view);
						 ////为每一个view项设置触控监听 左滑出现收藏按钮  跟listView.setOnItemClickListener应该是冲突了




					    /*
						 view.setOnTouchListener(new View.OnTouchListener() {
							 int lastX;
							 int lastY;

							 @Override
							 public boolean onTouch(View v, MotionEvent event) {
								 //当按下时处理
								 //获取到手指处的横坐标和纵坐标
								 int x;
								 int y;

								 //int x = (int) event.getX();
								 //int y = (int) event.getY();

								 switch (event.getAction()) {
									 case MotionEvent.ACTION_DOWN:

										 x = (int) event.getX();
										 y = (int) event.getY();

										 lastX = x;
										 lastY = y;
										 Log.d("down","down x"+lastX+"down y"+lastY);
										 view.setClickable(true);
										 break;
									 case MotionEvent.ACTION_MOVE:
										 //Toast.makeText(context,"滑动"+click_sn2, Toast.LENGTH_SHORT).show();
										 x = (int) event.getX();
										 y = (int) event.getY();
										 //计算移动的距离
										 int offX = x - lastX;
										 int offY = y - lastY;
										 view.layout(view.getLeft() + offX,
												 view.getTop() ,
												 view.getRight() + offX,
												 view.getBottom() );
										 //convertView.scrollBy(-offX,-offY );
										 Log.d("off","off x"+offX+"off y"+offY);
										 if (offX<-20){
											 view.layout(0,
													 0,
													 0,
													 0);
											 //Toast.makeText(context,"左滑删除"+"x" + offX , Toast.LENGTH_SHORT).show();
											 new Handler().postDelayed(new Runnable() {
												 @Override
												 public void run() {
													 TaskTool.quickdelete(getActivity(),TaskData.selTeamTaskSN);
												 }
											 },300);

											 return true;
										 }
										 if (offX>20){
											 view.layout(view.getLeft() ,
													 view.getTop() ,
													 view.getRight(),
													 view.getBottom() );
											 view.setBackgroundColor(Color.GREEN);
											 Toast.makeText(getActivity(),"右滑完成"+"x" + offX , Toast.LENGTH_SHORT).show();
											 TaskTool.slideClose(getActivity(),TaskData.selTeamTaskSN);
											 return true;
										 }
										 //Toast.makeText(context, "x" + offX + " y" + offY, Toast.LENGTH_SHORT).show();
										 break;
								 }
								 return true;
							 }
						 });*/
					 }catch(Exception e){
						 Log.d(Tags, e.toString());
						 e.printStackTrace();
					 }
				 }
				 if (view!=newview){
					 oldview=newview;
					 newview=view;
					 newview.setBackgroundColor(getResources().getColor(R.color.gray));
					 if (oldview!=null){
						 oldview.setBackgroundColor(getResources().getColor(R.color.mTextColor2));
					 }
				 }
			 }
		 });
         /*
		 lv_todaytasks.setOnItemLongClickListener(new OnItemLongClickListener(){
			 int newPos=0;
			 int oldPos=0;

			 @Override
			 public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
				 // TODO Auto-generated method stub

				 if (TaskData.cursor_todaytasks.getCount()>0){
					 //TaskData.selTaskID=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
					 //TaskData.selTaskSN=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
					 try{
						 TaskData.selTaskID=String.valueOf(TaskData.cursor_todaytasks.getString(TaskData.cursor_todaytasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
						 TaskData.selTaskSN=String.valueOf(TaskData.cursor_todaytasks.getString(TaskData.cursor_todaytasks.getColumnIndex(TaskData.TdDB.TASK_SN)));

					 }catch(Exception e){
						 Log.d(Tags, e.toString());
						 e.printStackTrace();
					 }
				 }
				 DialogFragment_TaskDetail dg_taskdetail=new DialogFragment_TaskDetail();
				 TaskTool.showDialog(dg_taskdetail);

				 return false;
			 }

		 });*/



		 //Log.d("click at", String.valueOf(position)+String.valueOf(ccc));
		 //Log.d("click at select", String.valueOf(m_listview.getSelectedItemPosition()));
		     	/* new Handler().postDelayed(new Runnable() {
		             @Override
		             public void run() {
		            	 m_listview.setSelection(ccc);
		            	 Log.d("Thread click select", String.valueOf(m_listview.getSelectedItemPosition()));
		             }
		         }, 200);


		  }
		 });

		 // Toast.makeText(getActivity(), "todaytakscount"+String.valueOf(TaskData.cursor_todaytasks.getCount()),Toast.LENGTH_SHORT).show();
		  
		  //lv_todaytasks.setFocusable(true);
		  //lv_todaytasks.setItemsCanFocus(true);
		  //lv_todaytasks.setSelected(true);
		 /* final Bundle bundle=null;
		  final Handler hd=new Handler(){
			  public void handleMessage(android.os.Message msg) {

				  super.handleMessage(msg); 
			  if(msg.what==1){
				  Bundle no = msg.getData();
				  selectedID=no.getInt("number");
				  Log.d("click at select", String.valueOf(no.getInt("number")));
			  }
			  } 
		  };
		 
		  final Message msg=null;
		  Thread tr=new Thread(){
			  
			  public void run(){
				  
				  m_listview.setOnItemClickListener(new OnItemClickListener(){
			             int ccc;
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							// TODO Auto-generated method stub
			                 ccc = position;
			                 m_listview.setSelection(ccc);
			                 Log.d("Thread click select", String.valueOf(m_listview.getSelectedItemPosition()));
			                 view.setBackgroundColor(Color.RED);
							Log.d("click at", String.valueOf(position)+String.valueOf(ccc));
					     	Log.d("click at select", String.valueOf(m_listview.getSelectedItemPosition()));
					     	msg.what=1;
							 					  
							bundle.putInt("number", ccc);
							  hd.sendMessage(msg);
						
						}
					  });		  
			  }
		  };
		  tr.start();
		

		
		 m_listview.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.d("touch show", v.toString()+event.toString());
				return true;
			}
		Object view = null;
				switch (event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	                //clear();
	                int x = (int) event.getX();
	                int y = (int) event.getY();
	                int position = m_listview.pointToPosition(x, y);
	                int firstVisiblePosition = m_listview.getFirstVisiblePosition();
	                view = m_listview.getChildAt(position-firstVisiblePosition);
	                if(view==null) return false;
	                if(((View) view).isFocusable()){
	                    Log.i("touch show", "ItemView is focusable ");
	                }
	                if(((View) view).isFocusableInTouchMode()){
	                    Log.i("touch show", "ItemView is focusable in touchMode");
	                }
	                if(((View) view).isInTouchMode()){
	                    Log.i("touch show", "device is in touch mode.");
	                }
	                if(m_listview.isFocusable()){
	                    Log.i("touch show", "mListView is focusable ");
	                }
	                if(m_listview.isFocusableInTouchMode()){
	                    Log.i("touch show", "mListView isFocusableInTouchMode in touchMode");
	                }
	                 
	                if(((View) view).isFocused()){
	                    Log.i("touch show", "ItemView have get focus ");
	                }
	                if(m_listview.isFocused()){
	                    Log.i("touch show", "mListView have get focus ");
	                }
	                if(((View) view).isPressed()){
	                    Log.i("touch show", "ItemView have get pressed ");
	                }
	                break;
	            case MotionEvent.ACTION_UP:
	                if(view==null) return false;
	                Log.i("touch show", "OnTouchListener: up is working ");
	                if(((View) view).isFocused()){
	                    Log.i("touch show", "ItemView have get focus ");
	                }
	                break;
	            default:
	                break;
	            }
	            return false;
			}
		
		  });
		  
		  
		 	  
		  TaskData.TaskData.lv_todaytasks.setOnItemSelectedListener(new OnItemSelectedListener(){
			  int ccc;
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				ccc = position;
				
				Log.d("select at111", String.valueOf(position));
		
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			    
		  });
		  
		  btn_taskdelete.setOnClickListener(new OnClickListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaskData.cursor_todaytasks.moveToPosition(TaskData.cursor_todaytasks.getPosition());
				int p=TaskData.cursor_todaytasks.getInt(0);
				Log.d("delete row", String.valueOf(p));
				String where=TaskData.TdDB.TASK_ID+"=?";
				String[] whereArgs={Integer.toString(p)};		
				TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain,where, whereArgs);
				Log.d("delete row", "delete OK");
				 Log.d("cursor",String.valueOf(TaskData.cursor_todaytasks.getCount())+"position"+String.valueOf(TaskData.cursor_todaytasks.getPosition()));
				 //AysnTask_AdapterUpdate task = new AysnTask_AdapterUpdate();  
					//Log.d("task create", "new");		
					//task.execute(); 
					TaskData.adapterUpdate();
			
			
			}
	  
		  });
		  btn_taskupdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaskData.adapterUpdate();
				
				 Log.d("TdDB adapter update", "all done");
				 Log.d("update cursor",String.valueOf(TaskData.cursor_todaytasks.getCount())+"position"+String.valueOf(TaskData.cursor_todaytasks.getPosition()));
				
				
			} 
		  });
		  
		  btn_addrecord.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					 //Log.d("from_fg", from_fg.toString()); 
					DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
						showDialog(dialogfrag_comment);
						Log.d("dialogfrag", dialogfrag_comment.toString());
					
					
					//TaskData.from_fg=showFrag(TaskData.from_fg,R.id.sublayout_task,subfrag_task,3);
				//	Log.d("task tab", "choice3");
				}
			  });
		  
		  btn_taskclear.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for (int i=0;i<TaskData.cursor_todaytasks.getCount();i++){
				    TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);	
					};
					//TaskData.adapter_todaytasks.notifyDataSetChanged();
					TaskData.adapterUpdate();
					
					
					 Log.d("TdDB adapter update", "all done");  
					 Log.d("clear",String.valueOf(TaskData.cursor_todaytasks.getCount())+"position"+String.valueOf(TaskData.cursor_todaytasks.getPosition()));
					
					
				} 
			  });
		 */

		 final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_ly);
		 swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
				 android.R.color.holo_orange_light, android.R.color.holo_red_light);

		 swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			 @Override
			 public void onRefresh() {
				 if ( TaskData.adapter_todaytasks !=null&& TaskData.adapter_todaytasks.getCursor().getCount()>0) {
					 //TaskData.adapter_todaytasks.notifyDataSetChanged();
					 TaskData.adapterUpdate();
					 //Toast.makeText(getActivity(),"loading",Toast.LENGTH_SHORT).show();
					 swipeRefreshLayout.setRefreshing(false);
				 }else{
					 swipeRefreshLayout.setRefreshing(false);
				 }
			 }
		 });

		 lv_todaytasks.setOnScrollListener(new ListView.OnScrollListener() {

			 @Override
			 public void onScrollStateChanged(AbsListView view, int scrollState) {
				 if (scrollState ==view.SCROLL_INDICATOR_BOTTOM) {
					 swipeRefreshLayout.setRefreshing(false);
					 // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
					 Toast.makeText(getActivity(),"已到最后页",Toast.LENGTH_SHORT).show();
				 }
				 if (scrollState ==view.SCROLL_INDICATOR_TOP) {
					 swipeRefreshLayout.setRefreshing(false);
					 // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
					 //Toast.makeText(getActivity(),"已到最上页",Toast.LENGTH_SHORT).show();
				 }
			 }

			 @Override
			 public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				 //lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
			 }

		 });
        //new BToast(getActivity()).showToast(getActivity(),"火箭发射",R.drawable.rocket_64px);
		 Log.d("fragment home", "return v");
		return v;
	 
	 }



	public  void homepage_countupdate(){
		try {
			int opentaskscount_t = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " + TaskData.TdDB.TASK_USER + "=?" + " order by " + TaskData.TdDB.TASK_SEQUENCENO, new String[]{"open", TaskData.user}).getCount();
			TaskData.tv_opentaskscount_t.setText(String.valueOf(opentaskscount_t));
			int opentaskscount_s = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " + TaskData.TdDB.TASK_USER + "=? and " + TaskData.TdDB.TASK_IMPORTANCE + ">=? and " + TaskData.TdDB.TASK_URGENCY + ">=?", new String[]{"open", TaskData.user, "2", "2"}).getCount();
			TaskData.tv_opentaskscount_a.setText(String.valueOf(opentaskscount_s));
			int opentaskscount_a = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " + TaskData.TdDB.TASK_USER + "=? and " + TaskData.TdDB.TASK_IMPORTANCE + ">=? and " + TaskData.TdDB.TASK_URGENCY + "<?", new String[]{"open", TaskData.user, "2", "2"}).getCount();
			TaskData.tv_opentaskscount_c.setText(String.valueOf(opentaskscount_a));
			int opentaskscount_u = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " + TaskData.TdDB.TASK_USER + "=? and " + TaskData.TdDB.TASK_IMPORTANCE + "<? and " + TaskData.TdDB.TASK_URGENCY + ">=?", new String[]{"open", TaskData.user, "2", "2"}).getCount();
			TaskData.tv_opentaskscount_b.setText(String.valueOf(opentaskscount_u));
			int opentaskscount_n = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " + TaskData.TdDB.TASK_USER + "=? and " + TaskData.TdDB.TASK_IMPORTANCE + "<? and " + TaskData.TdDB.TASK_URGENCY + "<?", new String[]{"open", TaskData.user, "2", "2"}).getCount();
			TaskData.tv_opentaskscount_d.setText(String.valueOf(opentaskscount_n));

			//java.util.Date currentdate = new java.util.Date();
			//String todaydate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			//Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG);
			//Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG);

			TaskData.cursor_todaytasks_t = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_DEADLINEDATE + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + ">=?" +
							" order by " + TaskData.TdDB.TASK_SEQUENCENO + " asc",
					new String[]{"open", TaskData.user,
							new SimpleDateFormat("yy-MM-dd").format(new Date()),
							String.valueOf(new Date().getTime() / (1000 * 60))});
			int todaytaskscount_t =TaskData.cursor_todaytasks_t.getCount();
			TaskData.tv_todaytaskscount_t.setText(String.valueOf(todaytaskscount_t));
			TaskData.cursor_todaytasks_a = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_DEADLINEDATE + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + ">=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + ">=? and " +
							TaskData.TdDB.TASK_URGENCY + ">=? ",
					new String[]{"open", TaskData.user,
							new SimpleDateFormat("yy-MM-dd").format(new Date()),
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int todaytaskscount_a = TaskData.cursor_todaytasks_a.getCount();
			TaskData.tv_todaytaskscount_a.setText(String.valueOf(todaytaskscount_a));
			TaskData.cursor_todaytasks_c = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINEDATE + "=? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + ">=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + ">=? and " +
							TaskData.TdDB.TASK_URGENCY + "<? ",
					new String[]{"open", TaskData.user,
							new SimpleDateFormat("yy-MM-dd").format(new Date()),
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int todaytaskscount_c = TaskData.cursor_todaytasks_c.getCount();
			TaskData.tv_todaytaskscount_c.setText(String.valueOf(todaytaskscount_c));
			TaskData.cursor_todaytasks_b = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINEDATE + "=? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + ">=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + "<? and " +
							TaskData.TdDB.TASK_URGENCY + ">=? ",
					new String[]{"open", TaskData.user,
							new SimpleDateFormat("yy-MM-dd").format(new Date()),
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int todaytaskscount_b = TaskData.cursor_todaytasks_b.getCount();
			TaskData.tv_todaytaskscount_b.setText(String.valueOf(todaytaskscount_b));
			TaskData.cursor_todaytasks_d = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINEDATE + "=? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + ">=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + "<? and " +
							TaskData.TdDB.TASK_URGENCY + "<? ",
					new String[]{"open", TaskData.user,
							new SimpleDateFormat("yy-MM-dd").format(new Date()),
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int todaytaskscount_d = TaskData.cursor_todaytasks_d.getCount();
			TaskData.tv_todaytaskscount_d.setText(String.valueOf(todaytaskscount_d));

			TaskData.cursor_overduetasks_t = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + "<? "
					, new String[]{TaskData.user, "open", String.valueOf(new Date().getTime() / (1000 * 60))});
			int overduetaskscount_t = TaskData.cursor_overduetasks_t.getCount();
			TaskData.tv_overduetaskscount_t.setText(String.valueOf(overduetaskscount_t));
			TaskData.cursor_overduetasks_a = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + "<? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + ">=? and " +
							TaskData.TdDB.TASK_URGENCY + ">=? ",
					new String[]{"open", TaskData.user,
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int overduetaskscount_a = TaskData.cursor_overduetasks_a.getCount();
			TaskData.tv_overduetaskscount_a.setText(String.valueOf(overduetaskscount_a));
			TaskData.cursor_overduetasks_c = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + "<? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + ">=? and " +
							TaskData.TdDB.TASK_URGENCY + "<? ",
					new String[]{"open", TaskData.user,
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int overduetaskscount_c = TaskData.cursor_overduetasks_c.getCount();
			TaskData.tv_overduetaskscount_c.setText(String.valueOf(overduetaskscount_c));
			TaskData.cursor_overduetasks_b = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + "<? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + "<? and " +
							TaskData.TdDB.TASK_URGENCY + ">=? ",
					new String[]{"open", TaskData.user,
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int overduetaskscount_b = TaskData.cursor_overduetasks_b.getCount();
			TaskData.tv_overduetaskscount_b.setText(String.valueOf(overduetaskscount_b));
			TaskData.cursor_overduetasks_d = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + "<? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + "<? and " +
							TaskData.TdDB.TASK_URGENCY + "<? ",
					new String[]{"open", TaskData.user,
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int overduetaskscount_d = TaskData.cursor_overduetasks_d.getCount();
			TaskData.tv_overduetaskscount_d.setText(String.valueOf(overduetaskscount_d));
			if (overduetaskscount_t>0){
				tv_redlight.setBackgroundResource(R.color.red);
			}else{
				tv_redlight.setBackgroundResource(R.color.lightgray);
			}
			if (opentaskscount_t>0){
				tv_worklight.setVisibility(View.VISIBLE);
				//tv_bluelight.setBackgroundResource(R.color.blue);
			}else{
				tv_worklight.setVisibility(View.INVISIBLE);
				//tv_bluelight.setBackgroundResource(R.color.gray);
			}
			if (todaytaskscount_t>0){
				tv_yellowlight.setBackgroundResource(R.color.darkorange);
			}else{
				tv_yellowlight.setBackgroundResource(R.color.lightgray);
			}
			Log.d("fragment home", "all done");
		}catch (Exception e){
			e.printStackTrace();
		}finally {

		}
	}

	private void showLight() {

	}

	private void todayshow() {
		// TODO Auto-generated method stub
		 TaskData.cursor_todaytasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=? and "+
                 TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
                 TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
                 new String[]{"open",
                 new SimpleDateFormat ("yy-MM-dd").format(new Date()),
         		   String.valueOf(new Date().getTime()/(1000*60))});
	       TaskData.adapter_todaytasks = new mcAdapter_opentasks(getActivity(),R.layout.lv_todaytasks,TaskData.cursor_todaytasks);
	}


	public Fragment showFrag(Fragment from_fg,int viewframe,Fragment[] frag,int i){
		  FragmentTransaction ft = getFragmentManager().beginTransaction();

	       if (from_fg==null){
	    	   ft.add(viewframe, frag[i]).commit();

	       }else{
	    	   if (!frag[i].isAdded()){

	    		// ft.hide(from_fg);

	    		 ft.hide(from_fg).add(viewframe,frag[i]).commit();

	    	   }
	    	   else{
	    		   ft.hide(from_fg).show(frag[i]).commit();
	    		   
	    	   }
	         };  
	       from_fg=frag[i];
		   return from_fg;
	 }
		  
	 protected void showDialog(DialogFragment dialogFragment) {  
         
	        // Create and show the dialog.   
	    if(dialogFragment == null)  
	          //  dialogFragment = new Fragment_aearch();  
	    	   //dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
	           dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0); 
	           dialogFragment.show(getFragmentManager(), "dialog");
	    }  
	
	 public int compareDate(String time1,String time2){
			 SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd");
				// Date curDate = new Date();//获取当前时间
		     //SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd HH:mm");
			Date date1 = null;
			Date date2 = null;
			int result=0;
			try {
				date1 = formatter1.parse(time1);
				date2 = formatter1.parse(time2);
				result=date1.compareTo(date2);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			 
		     return result;
			}
		
	 public int samedaycount(Cursor c){
		 c.moveToFirst();
		  String tskdl;
		  int samedaycount=0;
		  int overduedaycount=0;
		  String todaydate;
		  while(c.moveToNext()){
			  tskdl = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
			 // String datestr_tskdl = convertTime(tskdl);
			 todaydate = new SimpleDateFormat("yy-MM-dd").format(new Date());
		     
			
			 //Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG).show();;
			  switch (compareDate(tskdl,todaydate)) {
		      case 0: samedaycount=samedaycount+1;
		              break;
		      case -1:overduedaycount=overduedaycount+1; 
		              break;
		      default:break;        		    	  
		      }
		  }
		  return samedaycount;	 
	 }
	 
	 public int overduedaycount(Cursor c){
		 c.moveToFirst();
		  String tskdl;
		  int overduedaycount=0;
		  String todaydate;
		  while(c.moveToNext()){
			  tskdl = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
			 // String datestr_tskdl = convertTime(tskdl);
			 todaydate = new SimpleDateFormat("yy-MM-dd").format(new Date());
		     
			  
			 //Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG).show();;
			  switch (compareDate(tskdl,todaydate)) {
		      case -1:overduedaycount=overduedaycount+1; 
		              break;
		      default:break;        		    	  
		      }
		  }
		  return overduedaycount;	 
	 }
	 
	 
	 public String convertTime(String time){
		 SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd");
			// Date curDate = new Date();//获取当前时间
	     SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
	     Date date1 = null;
			try {
				
				try {
					date1 = formatter1.parse(time);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     return formatter2.format(date1);
		}
		
	 public double TimeSpace(String str_time1,String str_time2){
	    	
	    	//String str2 = et15_taskdeadline.getText().toString();  //"yyyyMMdd"格式 如 20131022
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//输入日期的格式 
			Date date1 = null;
			Date date2 = null;
			try {
			date2 = simpleDateFormat.parse(str_time2);
			date1= simpleDateFormat.parse(str_time1);
			} catch (ParseException e) {
			e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			//GregorianCalendar cal1 = new GregorianCalendar();  
			GregorianCalendar cal2 = new GregorianCalendar(); 
			GregorianCalendar cal1 = new GregorianCalendar();
			//cal1.setTime(date1);  
			cal2.setTime(date2);  
			cal1.setTime(date1);
			double dayCount = (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*60*60*24);
						
	    	return dayCount;
	    };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (TaskData.cursor_todaytasks!=null){
			TaskData.cursor_todaytasks.requery();
		}else{
			TaskData.cursor_todaytasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
							TaskData.TdDB.TASK_DEADLINETIMEDATA+"<=?"+
							" order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc",
					new String[]{TaskData.user,"open",
							String.valueOf(TimeData.todayEndTimeData())});
		}
		
		 /*  if (TaskData.TdDB==null) {
			   TaskData.TdDB = new ToDoDB(getActivity(), TaskData.db_TdDBname,null, 1);
			 //  TaskData.db_TdDB = TaskData.TdDB.getWritableDatabase();
			   TaskData.TdDB.onCreate(TaskData.db_TdDB);
			 
			    //textView = (TextView) findViewById(R.id.text_view); 
				Log.d("task execute", "task execute");
			Log.d("create TdDB", TaskData.db_TdDB.toString());
		   }else{
			   Log.d("TdDB open", "go");
		   }	*/	 
		}
   
	
}
