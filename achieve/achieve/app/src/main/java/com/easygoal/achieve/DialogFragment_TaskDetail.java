package com.easygoal.achieve;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.util.TextUtils;
import org.litepal.crud.DataSupport;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class DialogFragment_TaskDetail extends DialogFragment {

	Cursor c;
	DecimalFormat df_duration;
	String teamname;
	String taskowner;
	String Tags="DialogFragment_TaskDetail";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);  
		  /*if (TaskData.TdDB==null) {
		    TaskData.TdDB = new ToDoDB(getActivity(), TaskData.db_TdDBname,null, 1);
		    TaskData.db_TdDB = TaskData.TdDB.getWritableDatabase();
			//taskRecord.onCreate(db);
			Log.d("create", TaskData.TdDB.TABLE_NAME_TaskRecord);
		   }else{
			   Log.d("create", "go");
		   }		 
		*/
		 SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
		 Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		 String str = formatter.format(curDate);
		 df_duration = new DecimalFormat("0.0");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v=inflater.inflate(R.layout.dialogfg_view_taskdetails, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		  final TextView tv_teamname=(TextView)v.findViewById(R.id.tv_teamname);
		  final TextView tv0=(TextView)v.findViewById(R.id.task_item0_taskID_tv);
		  final TextView tv1=(TextView)v.findViewById(R.id.task_item1_name_tv);
		  final TextView tv2=(TextView)v.findViewById(R.id.task_item2_createdtime_tv);
		  final TextView tv3=(TextView)v.findViewById(R.id.task_item3_description_tv);
		  final TextView tv4=(TextView)v.findViewById(R.id.task_item4_importance_tv);
		  final TextView tv5=(TextView)v.findViewById(R.id.task_item5_urgency_tv);
		  final TextView tv6=(TextView)v.findViewById(R.id.task_item6_assess_tv);
		  final TextView tv7=(TextView)v.findViewById(R.id.task_item7_priority_tv);
		  final TextView tv8=(TextView)v.findViewById(R.id.task_item8_passion_tv);
		  final TextView tv9=(TextView)v.findViewById(R.id.task_item9_difficulty_tv);
		  final TextView tv10=(TextView)v.findViewById(R.id.task_item10_duration_tv);
		
		  final TextView tv14=(TextView)v.findViewById(R.id.task_item14_startedtime_tv);
		  final TextView tv15=(TextView)v.findViewById(R.id.task_item15_deadline_tv);
		  TaskData.tv_progress=(TextView)v.findViewById(R.id.task_item16_progress_tv);
		  final TextView tv17=(TextView)v.findViewById(R.id.task_item17_reminder_tv);
		  TaskData.tv_status=(TextView)v.findViewById(R.id.task_item18_status_tv);
		  final TextView tv19=(TextView)v.findViewById(R.id.task_item19_finished_tv);
		  final TextView tv20=(TextView)v.findViewById(R.id.task_item20_delayed_tv);
		  final TextView tv_durationunit=(TextView)v.findViewById(R.id.task_durationunit_tv);
		  TextView tv_maxperiod = (TextView)v.findViewById(R.id.task_item21_maxperiod_tv);
		  //final ListView m_listview=(ListView)v.findViewById(R.id.task_show_lv);
		 Button btn_taskupdate=(Button)v.findViewById(R.id.task_update_bt);
		 Button btn_taskedit=(Button)v.findViewById(R.id.task_edit_bt);
		 Button btn_subtaskedit=(Button)v.findViewById(R.id.subtask_edit_bt);
		 Button btn_taskdelete=(Button)v.findViewById(R.id.task_delete_bt);
		 Button btn_addrecord=(Button)v.findViewById(R.id.add_comments_bt);
		 Button btn_quickclose=(Button)v.findViewById(R.id.quickclose_bt);
		 Button btn_addremark=(Button)v.findViewById(R.id.add_remarks_bt);
		 Button btn_canceltask=(Button)v.findViewById(R.id.record_canceltask_bt);
		 Button btn_close = (Button) v.findViewById(R.id.task_close_bt);


		final ContentValues cv = new ContentValues();
		final RatingBar rb_1=(RatingBar)v.findViewById(R.id.rb_importance);
		final RatingBar rb_2=(RatingBar)v.findViewById(R.id.rb_urgency);
		final RatingBar rb_3=(RatingBar)v.findViewById(R.id.rb_difficulty);
		final RatingBar rb_4=(RatingBar)v.findViewById(R.id.rb_passion);

		RatingBar[] rb_assess={
				rb_1,
				rb_2,
				rb_3,
				rb_4,
		};
		String[] column_assess={
				TaskData.TdDB.TASK_IMPORTANCE,
				TaskData.TdDB.TASK_URGENCY,
				TaskData.TdDB.TASK_DIFFICULTY,
				TaskData.TdDB.TASK_PASSION,
		};

		TextView[] tv_task={
				tv0,
				tv1,
				tv2,
				tv3,
				tv4,
				tv5,
				tv6,
				tv7,
				tv8,
				tv9,
				tv10,
				tv14,
				tv15,
				TaskData.tv_progress,
				tv17,
				TaskData.tv_status,
				tv19,
				tv20,
				//tv_maxperiod
				//tv_durationunit
		};
		String[] column_task={
				TaskData.TdDB.TASK_ID,
				TaskData.TdDB.TASK_NAME,
				TaskData.TdDB.TASK_CREATEDTIME,
				TaskData.TdDB.TASK_DESCRIPTION,
				TaskData.TdDB.TASK_IMPORTANCEDETAIL,
				TaskData.TdDB.TASK_URGENCYDETAIL,
				TaskData.TdDB.TASK_ASSESSMENT,
				TaskData.TdDB.TASK_PRIORITY,
				TaskData.TdDB.TASK_PASSIONDETAIL,
				TaskData.TdDB.TASK_DIFFICULTYDETAIL,
				TaskData.TdDB.TASK_DURATION,
				TaskData.TdDB.TASK_STARTEDTIME,
				TaskData.TdDB.TASK_DEADLINE,
				TaskData.TdDB.TASK_PROGRESS,
				TaskData.TdDB.TASK_REMINDER,
				TaskData.TdDB.TASK_STATUS,
				TaskData.TdDB.TASK_FINISHED,
				TaskData.TdDB.TASK_DELAYED,
				//maxperiod,
				//TaskData.TdDB.TASK_DURATIONUNIT
		};
	        btn_close.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
	        String taskdeadline = null;
	        String taskstartedtime = null;
	        String maxperiod = null;
	        String taskstatus = null;
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		    //Toast.makeText(getActivity(), ""+TaskData.selTaskID, Toast.LENGTH_SHORT).show();
		   if (TaskData.selTaskID!=null){
			//TaskData.selTaskSN=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
					c = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_ID+"="+TaskData.selTaskID,null);
							//query(TaskData.TdDB.TABLE_NAME_TaskMain, , null, null, null, null, null);
					Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
					if (c!=null&&c.getCount()>0){
							  c.moveToFirst();
							  TaskData.selTaskSN=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
								 taskdeadline = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));

								 taskstartedtime = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STARTEDTIME));
								 double dayperiod=TimeData.DaySpace_YY(taskstartedtime,taskdeadline);
								 //Toast.makeText(getActivity(),taskdeadline+"\n"+taskstartedtime+"\n"+maxperiod, Toast.LENGTH_SHORT).show();
								 Log.d(Tags, "可用天数"+maxperiod);
								 if (dayperiod<1){
									 maxperiod=df_duration.format(dayperiod);
									 tv_maxperiod.setText("0 天(当日)");
								 }else{
									 maxperiod=df_duration.format(dayperiod);
									 tv_maxperiod.setText(maxperiod+" 天");
								 }
								 taskstatus=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)).trim();
								 taskowner=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_OWNER)).trim();
								//Toast.makeText(getActivity(), ""+taskstatus, Toast.LENGTH_SHORT).show();
								if (!taskstatus.equals("open")){
									int disableColor=getResources().getColor(R.color.lightgray);
									btn_taskedit.setEnabled(false);
									btn_subtaskedit.setEnabled(false);
									btn_taskdelete.setEnabled(false);
									btn_addrecord.setEnabled(false);
									btn_canceltask.setEnabled(false);
									btn_quickclose.setEnabled(false);
									btn_taskedit.setBackgroundColor(disableColor);
									btn_subtaskedit.setBackgroundColor(disableColor);
									btn_taskdelete.setBackgroundColor(disableColor);
									btn_addrecord.setBackgroundColor(disableColor);
									btn_canceltask.setBackgroundColor(disableColor);
									btn_quickclose.setBackgroundColor(disableColor);
								 } else{
									//Toast.makeText(getActivity(), "变化", Toast.LENGTH_SHORT).show();
								}
								if (!taskowner.equals(TaskData.user)){
									int disableColor=getResources().getColor(R.color.lightgray);
									btn_taskedit.setEnabled(false);
									btn_subtaskedit.setEnabled(false);
									btn_taskdelete.setEnabled(false);
									//btn_addrecord.setEnabled(false);
									//btn_canceltask.setEnabled(false);
									//btn_quickclose.setEnabled(false);
									btn_taskedit.setBackgroundColor(disableColor);
									btn_subtaskedit.setBackgroundColor(disableColor);
									btn_taskdelete.setBackgroundColor(disableColor);
									//btn_addrecord.setBackgroundColor(disableColor);
									//btn_canceltask.setBackgroundColor(disableColor);
									//btn_quickclose.setBackgroundColor(disableColor);
								} else{
									//Toast.makeText(getActivity(), "变化", Toast.LENGTH_SHORT).show();
								}

								 if (TaskTool.GetSubtaskCount(TaskData.selTaskSN)>0){
									 int disableColor=getResources().getColor(R.color.lightgray);
									 btn_quickclose.setEnabled(false);
									 btn_quickclose.setBackgroundColor(disableColor);
								 }

								for (int i=0;i<tv_task.length;i++){
									if (column_task[i]!=null&&tv_task[i]!=null){
										if (c.getString(c.getColumnIndex(column_task[i]))!=null) {
											tv_task[i].setText(c.getString(c.getColumnIndex(column_task[i])));
											Log.d(Tags + "|value|", "column " + i + " value " + c.getString(c.getColumnIndex(column_task[i])));
										}
									}
								}

								teamname=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_USERGROUP));
								if (teamname!=null){
									tv_teamname.setText("(团队:"+teamname+")");
								}
								for (int i=0;i<rb_assess.length;i++){
									if (column_assess[i]!=null&&rb_assess[i]!=null){
										rb_assess[i].setMax(3);
										rb_assess[i].setNumStars(3);
										rb_assess[i].setProgress(Integer.parseInt(c.getString(c.getColumnIndex(column_assess[i]))));
										//Log.d("value", "column "+i+" value "+c.getString(c.getColumnIndex(column_task[i])));
									}
								}
								//tv_task[10].setText(c.getString(c.getColumnIndex(column_task[10])));

								int durationunit = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATIONUNIT)));
								switch (durationunit) {
									case 1:tv_durationunit.setText("天");break;
									case 2:tv_durationunit.setText("小时");break;
									case 3:tv_durationunit.setText("分钟");break;
									default:break;
								}

					}
			}




		//  final int click_position;
		//  final SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.show_alltask, c, new String[] {TdDB.TASK_ID,TdDB.TASK_NAME,TdDB.TASK_IMPORTANCE},new int[]{R.id.task1_id_tv,R.id.task1_name_tv,R.id.task1_importance_tv}, 0);
		 // Log.d("adapter",adapter.toString() );
		//  m_listview.setAdapter(adapter); 
  	    //Log.d("show data","ok" +m_listview.toString());

		    
		    String[] column_record={
					//TaskData.TdDB.RECORD_ID,
				    TaskData.TdDB.RECORD_TASKID, 
					//TaskData.TdDB.RECORD_NO, 
					TaskData.TdDB.RECORD_TIME, 
					TaskData.TdDB.RECORD_PROGRESS, 
					TaskData.TdDB.RECORD_COMMENTS, 
					//TaskData.TdDB.RECORD_ACHIEVED, 
					//TaskData.TdDB.RECORD_ENJOYMENT, 
					//TaskData.TdDB.RECORD_EXPERIENCE,  
					//TaskData.TdDB.RECORD_TOTALCOUNT
				    };
    
		    int[] tv_record = {
		    R.id.record_item1_taskID_tv,
		    R.id.record_item2_recordTime_tv,
		    R.id.record_item4_progress_tv,
		    R.id.record_item5_comment_tv};
		    String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
	        final Cursor c2 = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.TASK_SN+"="+"'"+TaskData.selTaskSN+"'",null);
    		//query(TaskData.TdDB.TABLE_NAME_TaskMain, , null, null, null, null, null);
         
            c2.moveToFirst();

            TaskData.adaptRecordDetails = new mcAdapter_recorddetails(getActivity(),c2,R.layout.lvitem_taskdetailrecord);
            final ListView lv_taskrecord=(ListView)v.findViewById(R.id.taskcomments_lv);
            int recorddetailcolor=getActivity().getResources().getColor(R.color.mTextColor2);
            lv_taskrecord.setBackgroundColor(recorddetailcolor);
		    //Toast.makeText(getActivity(),"record"+TaskData.adaptRecordDetails.getCount()+" "+TaskData.selTaskSN,Toast.LENGTH_SHORT).show();
            lv_taskrecord.setAdapter(TaskData.adaptRecordDetails);
            LvHeight lv_height = new LvHeight();
            lv_height.setListViewHeightBasedOnChildren(lv_taskrecord);
		 /* btn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//c.moveToLast();
				//Log.d("insert data", "start");	
			   // String a = String.valueOf(c.getCount()+1);
			   // Log.d("db query","row"+String.valueOf(c.getCount()));
				//cv.put(TdDB.ID, a);
				  cv.put(TaskData.TdDB.TASK_NAME, tv1.getText().toString()); 
				  cv.put(TaskData.TdDB.TASK_IMPORTANCE, tv4.getText().toString()); 
				  cv.put(TaskData.TdDB.TASK_DESCRIPTION, tv3.getText().toString());
				  cv.put(TaskData.TdDB.TASK_URGENCY, tv5.getText().toString()); 
				  cv.put(TaskData.TdDB.TASK_PASSION, tv8.getText().toString()); 
				  cv.put(TaskData.TdDB.TASK_DIFFICULTY, tv9.getText().toString()); 
				  cv.put(TaskData.TdDB.TASK_DURATION, tv10.getText().toString());
				  cv.put(TaskData.TdDB.TASK_ACHIEVED,tv11.getText().toString());
				  cv.put(TaskData.TdDB.TASK_ENJOYMENT,tv12.getText().toString());
				  cv.put(TaskData.TdDB.TASK_EXPERIENCE,tv13.getText().toString());
				  cv.put(TaskData.TdDB.TASK_PROGRESS,tv16.getText().toString());
				  cv.put(TaskData.TdDB.TASK_REMINDER, tv17.getText().toString()); 
				  //Log.d("submit data",cv.toString() );
				  long insertOK = TaskData.db_TdDB.insert(TaskData.TdDB.TABLE_NAME_TaskMain,null, cv);
				  Log.d("insert data", "ok"+insertOK);
			     // Log.d("move cursor", String.valueOf(c.getPosition()));
			     //Cursor c = db.query(TdDB.TABLE_NAME, null, null, null, null, null, null);
			    // Log.d("show data",c.toString() );
			   // SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.addtolist1, c, new String[] {"task_name","task_importance"},new int[]{R.id.task1_name_tv,R.id.task2_importance_tv}, 0);
			     //Log.d("show data",adapter.toString() );
			     //SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.addtolist1, c, null, null, 0)；SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.addtolist1, c, new String[] {"task_name","task_importance","task_urgency","task_passion","task_difficulty","task_duration"},new int[]{R.id.task1_name,R.id.task2_importance,R.id.task4_passion,R.id.task5_difficulty,R.id.task6_duration,R.id.task7_reminder}, 0); 
				  //SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.addtolist1, c, new String[] {TdDB.ID,TdDB.TASK_NAME,TdDB.TASK_IMPORTANCE},new int[]{R.id.task1_id_tv,R.id.task1_name_tv,R.id.task1_importance_tv}, 0);
				  //Log.d("adapter",adapter.toString() );
				//  adapter.getCursor().requery();
				//  adapter.notifyDataSetChanged(); 
			// Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
				 // m_listview.setAdapter(adapter);
					TaskData.adapterUpdate();
					 Log.d("TdDB adapter update", "all done");
				 // AysnTask_AdapterUpdate task = new AysnTask_AdapterUpdate();  
					//Log.d("task create", "new");		
					//task.execute();   
			}  
	    });*/
		    
    
			  btn_taskdelete.setOnClickListener(new OnClickListener(){

					@SuppressWarnings("deprecation")
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						
						if (TaskData.selTaskID!=null){
						
								/*TaskData.cursor_opentasks.moveToPosition(TaskData.cursor_opentasks.getPosition());
								int p=TaskData.cursor_opentasks.getInt(0);
								Log.d("delete row", String.valueOf(p));
								String where=TaskData.TdDB.TASK_ID+"=?";
								String[] whereArgs={Integer.toString(p)};		
								TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain,where, whereArgs);
								Log.d("delete row", "delete OK");
								 Log.d("cursor",String.valueOf(TaskData.cursor_opentasks.getCount())+"position"+String.valueOf(TaskData.cursor_opentasks.getPosition()));
								 */
								 
							  Builder builder=new AlertDialog.Builder(getActivity());
						      builder.setTitle("确认");
							  builder.setMessage("真要删除任务T"+TaskData.selTaskID+"吗？");
							  builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
								    //show.setText("");

									 new ConnMySQL(getActivity()).DelByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain,TaskData.selTaskSN);
									 Log.d(Tags,"del by Mysql done");
									 TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
								             TaskData.TdDB.TASK_SN+"="+"'"+TaskData.selTaskSN+"'"+" and "+
										     TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"';");					 
									    Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId="+"'"+"T"+TaskData.selTaskID+"';");
									
									 if (cr.getCount()>0){
										   cr.moveToFirst();
										   do{
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d(Tags,"delete reminder"+reminderno);   
										   
										   }while(cr.moveToNext());
		 
									   } 
									 
									 TaskData.adapterUpdate();
									 TaskData.selTaskID=null;
								     TaskData.selTaskSN=null;
								     dismiss();
								} 
							  } );
							  builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
								}	  
							  });
							  builder.create().show();	    
						  } else{
								Toast.makeText(getActivity(), "请先选定任务", Toast.LENGTH_SHORT).show();	 
						  };
					 }	  
						
				  });			
			/*		
			  btn_taskdelete.setOnClickListener(new OnClickListener(){

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 
					TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_SN+"="+"'"+TaskData.selTaskSN+"';");
			    		//query(TaskData.TdDB.TABLE_NAME_TaskMain, , null, null, null, null, null);
			      
					//Log.d("delete row", "delete OK");
                    Toast.makeText(getActivity(), "已成功删除",Toast.LENGTH_SHORT).show();
                    //TaskData.selTaskID=null; 
                    //dismiss();
                   TaskData.adapterUpdate();
                   TaskData.selTaskID=null;
                   dismiss();
				}
		  
			  });
			  */
			  btn_taskedit.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
				   if (TaskData.tv_status.getText().toString().trim().equals("open")){
						if (c!=null&&c.getCount()>0&&c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS))!="finished"){
						DialogFragment_TaskEdit df_taskedit=new DialogFragment_TaskEdit();
						TaskTool.showDialog(df_taskedit);
						dismiss();
						}else{
						 Toast.makeText(getActivity(), "任务结束,无法编辑"+TaskData.selTaskID,Toast.LENGTH_SHORT).show();	
						}
					}else{
						 Toast.makeText(getActivity(), "任务结束或撤销,无法编辑",Toast.LENGTH_SHORT).show();	
					} 
				  } 
			 });
			  
			  btn_taskupdate.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					    //Cursor c2 = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+ToDoDB.RECORD_TASKID+"="+TaskData.selTaskID,null);
			    		//query(TaskData.TdDB.TABLE_NAME_TaskMain, , null, null, null, null, null);
			            //Log.d("cursor record",String.valueOf(c2.getCount())+"position"+String.valueOf(c2.getPosition()));
			            //c2.moveToFirst();
			            TaskData.adaptRecordDetails.getCursor().requery();
			            TaskData.adaptRecordDetails.notifyDataSetChanged();
			            //mcAdapter_recorddetails adapt2 = new mcAdapter_recorddetails(getActivity(),c2,R.layout.lvitem_recorddetail);
			            //lv_taskrecord.setAdapter(adapt2);
									
					TaskData.adapterUpdate();
					
					
				} 
			  });
			 
			  btn_canceltask.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stu	
						 /* String where = TaskData.TdDB.TASK_ID + " = ?";    
							String[] whereValue = { TaskData.selTaskID }; 
							ContentValues cv3 = new ContentValues();    
							cv3.put(TaskData.TdDB.TASK_STATUS, "close"); 
							cv3.put(TaskData.TdDB.TASK_FINISHED, "yes");
							cv3.put(TaskData.TdDB.TASK_STATUS, "finished");
							cv3.put(TaskData.TdDB.TASK_CLOSEDTIME, curTime);
							long updateOK=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv3, where, whereValue); 
						  Log.d("update Task status", "ok"+updateOK);*/
						
						// DialogFragment_CloseTask dialogfrag_closetask=new DialogFragment_CloseTask();
						  //dialogfrag_closetask.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
						  //dialogfrag_closetask.setStyle(DialogFragment.STYLE_NO_TITLE, 0);  
						  //showDialog(dialogfrag_closetask);
							//Log.d("dialogfrag", dialogfrag_closetask.toString());
						if (TaskData.tv_status.getText().toString().trim().equals("open")){	
						  DialogFragment_CancelTask dialogfrag_canceltask=new DialogFragment_CancelTask();
							//dialogfrag_canceltask.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
							//dialogfrag_canceltask.setStyle(DialogFragment.STYLE_NO_TITLE, 0);  
						    TaskTool.showDialog(dialogfrag_canceltask);
							  dismiss(); 
						}else{
							 Toast.makeText(getActivity(), "任务结束或撤销,无法编辑",Toast.LENGTH_SHORT).show();	
						}
							
					}			  
			  });
			    
			  btn_addrecord.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						
						// TODO Auto-generated method stub
						 //Log.d("from_fg", from_fg.toString()); 
						
				 if (TaskData.tv_status.getText().toString().trim().equals("open")){	
						DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
						TaskTool.showDialog(dialogfrag_comment);
					    dismiss();

				 }else{
					 Toast.makeText(getActivity(), "任务结束或撤销,无法添加进度",Toast.LENGTH_SHORT).show();	
				}
			    	//TaskData.from_fg=showFrag(TaskData.from_fg,R.id.sublayout_task,subfrag_task,3);
					//	Log.d("task tab", "choice3");
					}
		     });
		
			  btn_addremark.setOnClickListener(new OnClickListener(){
				  
				  @Override
					public void onClick(View v) {
						
				   DialogFragment_AddRemarks dialogfrag_addremark=new DialogFragment_AddRemarks();
				   TaskTool.showDialog(dialogfrag_addremark);
					
				  }
				  
			  });

			  btn_subtaskedit.setOnClickListener(new OnClickListener(){
				  
				  @Override
					public void onClick(View v) {
					  double totalclosedweight=TaskTool.GetTotalClosedWeight();
				  if (TaskData.tv_status.getText().toString().trim().equals("open")||(totalclosedweight>=100)){
				    DialogFragment_SubTaskEdit df_subtaskedit=new DialogFragment_SubTaskEdit();
					TaskTool.showDialog(df_subtaskedit);
					Log.d("dialogfrag", df_subtaskedit.toString());
				   }else{
						 Toast.makeText(getActivity(), "已完成或撤销,子目标无法编辑",Toast.LENGTH_SHORT).show();	
					}
				 }
				  
			  });
			  
			  btn_quickclose.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stu
					
					  if (TaskData.tv_status.getText().toString().trim().equals("open")){
						    quickclose();
					  }else{
						    Toast.makeText(getActivity(), "已完成或撤销,无法快速结束",Toast.LENGTH_SHORT).show();	
				      }	
				}
			  });
		return v;
	}	

 public void quickclose(){
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
		     
			 c_rf= TaskData.db_TdDB.rawQuery("select * from "+b+" where "+
					 TaskData.TdDB.TASK_SN+"="+"'"+t_sn+"'"+		              
					 " and "+TaskData.TdDB.RECORD_DONE+"="+"'"+"false"+"'",null); 
			 	if (c_rf!=null&&c_rf.getCount()>0){
				    Toast.makeText(getActivity(), "无法快速关闭,有子任务未完成", Toast.LENGTH_SHORT).show();;	 
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
			 
			       if (tasklesson!=null&&!TextUtils.isEmpty(tasklesson)){     
			    	   tasklesson=tasklesson.trim()+"\n"+"[快速结束]执行力取得积极成果"+TaskData.tag_tasklesson;
			       }else{
			    	   tasklesson="[快速结束]执行力取得积极成果"+TaskData.tag_tasklesson;  
			       }
			       
			       if (taskhistory!=null&&!TextUtils.isEmpty(taskhistory)){     
			    	   	taskhistory=taskhistory.trim()+"\n"+"[快速结束]任务完成"+TaskData.tag_taskhistory;
			       }else{
			    	   	taskhistory="[快速结束]任务完成"+TaskData.tag_taskhistory;  
			       }
			 

			       duration_unit=Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATIONUNIT)));
			       
			       duration=Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATION)));
				    
			        switch (duration_unit){
			          
				    case 1: hr_timeunit=TaskData.prehour;
				    		plannedtime=duration*hr_timeunit;
				    		Log.d(Tags,"plannedtime:"+plannedtime);
				    		break;
				    case 2: hr_timeunit=1;
				    		plannedtime=duration*hr_timeunit;;
				    		Log.d(Tags,"plannedtime:"+plannedtime);
				    		break;
				    case 3: hr_timeunit=(double)1/60;
				    		plannedtime=duration*hr_timeunit;;
				    		Log.d(Tags,"plannedtime:"+plannedtime);
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
					  Toast.makeText(getActivity(), "快速结束任务成功", Toast.LENGTH_SHORT).show();;	 
					  TaskData.update_tvvalue(TaskData.tv_status,c,TaskData.TdDB.TASK_STATUS);
					  dismiss(); 
				  }else{
					  Toast.makeText(getActivity(), "快速结束任务失败"+TaskData.TdDB.TASK_ID, Toast.LENGTH_SHORT).show();;	   
				  }
			 } else{
			     //Toast.makeText(getActivity(), "无法快速关闭，还有未完成的子任务", Toast.LENGTH_SHORT).show();;	 
		     } 
			 
		 }else {
			    Toast.makeText(getActivity(), "未选定任务", Toast.LENGTH_SHORT).show();;	 
	     }
		 
		 
}

	
	
@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	DisplayMetrics dm = new DisplayMetrics();
	getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
	getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
}  	
		
}
