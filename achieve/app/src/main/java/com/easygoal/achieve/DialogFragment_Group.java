package com.easygoal.achieve;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class DialogFragment_Group extends DialogFragment {

	Cursor c;
	View oldview=null;
	View newview=null;
	
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v=inflater.inflate(R.layout.dialogfg_group, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		int selectedID=0;
		 
		  //Log.d("create database", "start"+TaskData.d.toString());
		  final EditText et1=(EditText)v.findViewById(R.id.record_item2_recordTime_et);
		  final EditText et3=(EditText)v.findViewById(R.id.record_item4_progress_et);
		  final EditText et4=(EditText)v.findViewById(R.id.record_item5_comment_et);
		 
		  final ListView lv_taskrecord=(ListView)v.findViewById(R.id.taskrecord_lv);
		  final ListView lv_taskcomments=(ListView)v.findViewById(R.id.taskcomments_lv);
		  
		 //Button btn_confirm=(Button)v.findViewById(R.id.btn_confirm);
		  Button btn_update=(Button)v.findViewById(R.id.btn_update);
		  Button btn_addrecord = (Button) v.findViewById(R.id.btn_addrecord);  
		  Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);  
		  Button btn_close = (Button) v.findViewById(R.id.btn_close);  
			 
		  
		  
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		 
		  
		    //final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		    //Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		 //  final Cursor c2 = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskRecord, null, null, null, null, null, null);
		//   Log.d("cursor2",String.valueOf(c2.getCount())+"position"+String.valueOf(c2.getPosition()));
		 //   final ContentValues cv = new ContentValues();
		/*  final int click_position;
		    String[] itemlist_taskrecord={
		            TaskData.TdDB.TASK_NAME, 
		            TaskData.TdDB.TASK_DESCRIPTION,
		            TaskData.TdDB.TASK_IMPORTANCE,
		            TaskData.TdDB.TASK_URGENCY,
		            TaskData.TdDB.TASK_DEADLINE,
		            TaskData.taskRecord.RECORD_TIME,
		            TaskData.taskRecord.RECORD_PROGRESS,		            
		              		
  };
	int[] controllist_taskrecord={
			R.id.task_item1_name_tv,
			R.id.task_item11_achieved_tv,
			R.id.task_item12_enjoyment_tv,
			R.id.task_item13_experience_tv,
			R.id.task_item16_progress_tv,
			R.id.					
	};*/
		// lvBaseAdapter_taskcomments lvBaseAdapter_taskcomments = new lvBaseAdapter_taskcomments(getActivity()); 
		// mCursorAdapter_taskrecord adapter_record=new mCursorAdapter_taskrecord(getActivity(), c);  
		 //SimpleCursorAdapter adapter_record = new SimpleCursorAdapter(getActivity(), R.layout.lv_taskscore, c,itemlist_taskscore,controllist_taskscore, 0);
		// Log.d("adapter_record",adapter_record.toString() );
	//	lv_taskrecord.setAdapter(adapter_record); 
  	   //  Log.d("show data","ok" +lv_taskrecord.toString());
  	 //  mCursorAdapter_taskcomment mcAdapter_taskcomment = new mCursorAdapter_taskcomment(getActivity(),c2);
		// lv_taskcomments.setAdapter(lvBaseAdapter_taskcomments);
  	// Log.d("adapter_record",mcAdapter_taskcomment.toString());
		  
		  btn_close.setOnClickListener(new OnClickListener() {  
			  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
		 
	        btn_cancel.setOnClickListener(new OnClickListener() {  
		      	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });   
		  
		  
		lv_taskrecord.setAdapter(query());
		   
		//lv_taskrecord.setClickable(true);
		
		 lv_taskrecord.setOnItemClickListener(new OnItemClickListener(){
             int newPos=0;
             int oldPos=0;
              
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Log.d("click position ", "item "+String.valueOf(id));
                 Log.d("click and move to", String.valueOf(TaskData.cursor_group.getPosition()));
                 //view.setBackgroundColor(Color.RED);
                 TaskData.selTaskID=String.valueOf(TaskData.cursor_group.getString(TaskData.cursor_group.getColumnIndex(TaskData.TdDB.TASK_ID)));
                 TaskData.selTaskSN=String.valueOf(TaskData.cursor_group.getString(TaskData.cursor_group.getColumnIndex(TaskData.TdDB.TASK_SN)));
                 if (view!=newview){
 					oldview=newview; 
 					newview=view;
 					newview.setBackgroundResource(R.color.gray);
 					if (oldview!=null){
 					oldview.setBackgroundResource(R.color.mTextColor2);
 					} 
 				}
                   
		  }
		 });
			
		
		btn_addrecord.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				 //Log.d("from_fg", from_fg.toString()); 
				 DialogFragment_GroupRecord dialogfrag_grouprecord=new DialogFragment_GroupRecord();
			     TaskTool.showDialog(dialogfrag_grouprecord);
				
				
				//TaskData.from_fg=showFrag(TaskData.from_fg,R.id.sublayout_task,subfrag_task,3);
			//	Log.d("task tab", "choice3");
			}
		  });	
		
		 btn_update.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					TaskData.adapterUpdate();
					 
				} 
			  });
		
		
		return v;
	}	
	
	 public mcAdapter_group query(){
		 //Log.d("start join query","OK");
	     
		   String a = TaskData.TdDB.TABLE_NAME_TaskMain;
	       String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
	      // Log.d("start join query","a"+a+"b"+b);
	       //Log.d("start join query",TaskData.TdDB.getDatabaseName());
	    //  Cursor cursor = db.rawQuery("SELECT a.*,b*b FROM a,b INNER JOIN a.TASK_ID=b.TASK_NO;",new String[]{});
	       //String TASK_NAME = "task_name";
	     
		//Cursor c1=TaskData.db_TdDB.rawQuery("select * from "+a+","+b+ " where "+a+"."+TASK_NAME+" like ?", new String[]{"name"});
	    TaskData.cursor_group=TaskData.db_TdDB.rawQuery("select * from "+a, null);
	    //Log.d("rawquery","row"+c1.getCount()+"column"+c1.getColumnCount()+"position"+c1.getPosition());
		//c1.moveToFirst();
		TaskData.mcAdapter_group = new mcAdapter_group(getActivity(),TaskData.cursor_group);
		Log.d("adapter_group",TaskData.mcAdapter_group.toString());
		return TaskData.mcAdapter_group;
		// lv_taskrecord.setAdapter(adapter_record); 
	 
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
