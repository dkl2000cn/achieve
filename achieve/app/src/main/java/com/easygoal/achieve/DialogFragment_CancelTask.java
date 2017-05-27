package com.easygoal.achieve;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.util.TextUtils;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DialogFragment_CancelTask extends DialogFragment {
	String curTime;
	Cursor c;
	String taskclosechoice=null;
	String taskhornorchoice=null;
	String tasklesson;
	String taskhistory;
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
		 SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
		 Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		 curTime = formatter.format(curDate);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v=inflater.inflate(R.layout.dialogfg_canceltask, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		 
		  Log.d("create database", "start"+TaskData.db_TdDB.toString());
		  //TextView tv1=(TextView)v.findViewById(R.id.record_item1_recordID_tv);
		  final TextView tv_taskID=(TextView)v.findViewById(R.id.record_item1_taskID_tv);
		  final TextView tv_taskname=(TextView)v.findViewById(R.id.task_item1_name_tv);
		  TextView tv_taskcreatedtime=(TextView)v.findViewById(R.id.task_item2_createdtime_tv);
		  TextView tv_taskprogress=(TextView)v.findViewById(R.id.task_item16_progress_tv);
		  TextView tv_taskstatus=(TextView)v.findViewById(R.id.task_item18_status_tv);
		  TextView tv_taskdeadline=(TextView)v.findViewById(R.id.task_item15_deadline_tv);
		  final TextView tv_recordtime=(TextView)v.findViewById(R.id.tv_recordtime);
		  
		  final EditText et_recordprogress=(EditText)v.findViewById(R.id.record_item4_progress_et);
		  final EditText et_recordcomment=(EditText)v.findViewById(R.id.record_item5_comment_et);
		
		  RadioGroup  rg_taskhornor=(RadioGroup )v.findViewById(R.id.radioGroup_taskhonor);
		  final RadioButton rb_taskhornor_high=(RadioButton)v.findViewById(R.id.rb_taskhornor_high);
		  RadioButton rb_taskhonor_meet=(RadioButton)v.findViewById(R.id.rb_taskhornor_meet);
		  RadioButton rb_taskhonor_low=(RadioButton)v.findViewById(R.id.rb_taskhornor_low);
		 
		  final EditText et_taskcancelremarks=(EditText)v.findViewById(R.id.task_item31_cancelremarks_et);
		
		  //final ListView m_listview=(ListView)v.findViewById(R.id.task_show_lv);
		  Button btn_confirm = (Button) v.findViewById(R.id.btn_confirm);
		  Button btn_abort = (Button) v.findViewById(R.id.btn_abort); 
		 Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);  
		// Button btn_recordclose = (Button) v.findViewById(R.id.record_close_bt);  
		// Button btn_recordconfirm=(Button)v.findViewById(R.id.record_confirm_bt);
		// Button btn_closetask=(Button)v.findViewById(R.id.record_closetask_bt);
		// Button btn_recordview = (Button) v.findViewById(R.id.record_view_bt);  
	     if (TaskData.selTaskID!=null){
		 tv_taskID.setText(TaskData.selTaskID);
		 String a = TaskData.TdDB.TABLE_NAME_TaskMain;
	     String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
	     c = TaskData.db_TdDB.rawQuery("select * from "+a+" where "+TaskData.TdDB.TASK_ID+"="+TaskData.selTaskID,null);
		 //
		 c.moveToFirst();
		 TaskData.selTaskSN=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
		// Log.d("selTab count", "total "+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME))+" "+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME+" "+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS+""+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)))))));
		 tv_taskname.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME)));
		 tv_taskdeadline.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
		 // tv_taskcreatedtime.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME)));
		 tv_taskprogress.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
		 tv_taskstatus.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)));
		 taskhistory = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_HISTORY));
		 tasklesson = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_LESSON));
		
	     }else {
	      Toast.makeText(getActivity(), "未选定任务", Toast.LENGTH_SHORT).show();;	 
	     }
		  tv_recordtime.setText(curTime);
		 btn_closewin.setOnClickListener(new OnClickListener() {  
	  
	           
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
	     
		 btn_abort.setOnClickListener(new OnClickListener() {  
			  
	           
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
	     	
		 
	 
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		    //final Cursor c = db.query(TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		    //Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		    final ContentValues cv = new ContentValues();
		//  final int click_position;
		//  final SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.show_alltask, c, new String[] {TdDB.TASK_ID,TdDB.TASK_NAME,TdDB.TASK_IMPORTANCE},new int[]{R.id.task1_id_tv,R.id.task1_name_tv,R.id.task1_importance_tv}, 0);
		 // Log.d("adapter",adapter.toString() );
		//  m_listview.setAdapter(adapter); 
  	    //Log.d("show data","ok" +m_listview.toString());
		   

		    
		    
		  btn_confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//c.moveToLast();
				//Log.d("insert data", "start"+String.valueOf(c.getPosition()));	
			   // String a = String.valueOf(c.getCount()+1);
			   // Log.d("db query","row"+String.valueOf(c.getCount()));
				//cv.put(TdDB.ID, a);
				if (TaskData.selTaskSN!=null){
				
					    final ContentValues cv = new ContentValues();
					    String where = TaskData.TdDB.TASK_SN + " = ?";    
						String[] whereValue = { TaskData.selTaskSN }; 
			        	cv.put(TaskData.TdDB.TASK_STATUS,"cancelled");
			        	cv.put(TaskData.TdDB.TASK_MODIFIED,TaskTool.getCurTime());
				      // Log.d("show closedata",taskclosechoice);
				 //cv.put(TaskData.TdDB.RECORD_NO, String.valueOf(c2.));
				 //cv.put(TaskData.TdDB.RECORD_TIME, curTime);
				  //cv.put(TaskData.TdDB.RECORD_PROGRESS, et_recordprogress.getText().toString()); 
			   
			    if (tasklesson!=null&&!TextUtils.isEmpty(tasklesson)){     
				      tasklesson=tasklesson.trim()+"\n"+et_taskcancelremarks.getText().toString()+TaskData.tag_tasklesson;
				  }else{
					  tasklesson=et_taskcancelremarks.getText().toString().trim()+TaskData.tag_tasklesson;  
				  }
			     
			      if (taskhistory!=null&&!TextUtils.isEmpty(taskhistory)){     
			    	  taskhistory=taskhistory.trim()+"\n"+"任务取消"+TaskData.tag_taskhistory;
				  }else{
					  taskhistory="任务取消"+TaskData.tag_taskhistory;  
				  } 
			      cv.put(TaskData.TdDB.TASK_LESSON,tasklesson); 
			      cv.put(TaskData.TdDB.TASK_HISTORY,taskhistory); 
				  //Log.d("submit data",cv.toString() );
				  long a=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
				 
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
				  if (a==1){
				     Toast.makeText(getActivity(), "任务撤销成功", Toast.LENGTH_SHORT).show();;	 
				  }else{
					 Toast.makeText(getActivity(), "任务撤销失败"+TaskData.TdDB.TASK_ID, Toast.LENGTH_SHORT).show();;	   
				  }
				  TaskData.update_tvvalue(TaskData.tv_status,c,TaskData.TdDB.TASK_STATUS);
				  TaskData.adapterUpdate();
					// Log.d("TdDB adapter update ", TaskData.cursor_alltasks.getPosition()+" seltaskId "+TaskData.selTaskID);
					dismiss(); 
					  //Toast.makeText(getActivity(), "已取消任务"+TaskData.TdDB.TASK_ID, Toast.LENGTH_LONG);	
					//  DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
					 // dialogfrag_comment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);  
					  //showDialog(dialogfrag_comment);
				   	//  Toast.makeText(getActivity(),"close ok", Toast.LENGTH_LONG).show();
					// DialogFragment_CancelTask dialogfrag_canceltask=new DialogFragment_CancelTask();
				  //	showDialog(dialogfrag_canceltask);
					//	Log.d("dialogfrag", dialogfrag_canceltask.toString());
				}else{
				  Toast.makeText(getActivity(), "未选定任务", Toast.LENGTH_SHORT).show();;		
				}
			 }  
		    });  	  
		  
			return v;
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
