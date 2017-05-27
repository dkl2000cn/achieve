package com.easygoal.achieve;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

public class FragmentTaskLogList extends Fragment {
	
	

	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	    String Tags="FragmentTaskRecord";
	
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.subfg_recordtab_toptab1_record, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		  //Log.d("create database", "start"+TaskData.d.toString());
		  final EditText et1=(EditText)v.findViewById(R.id.record_item2_recordTime_et);
		  final EditText et3=(EditText)v.findViewById(R.id.record_item4_progress_et);
		  final EditText et4=(EditText)v.findViewById(R.id.record_item5_comment_et);
		 
		  final ListView lv_taskloglist=(ListView)v.findViewById(R.id.taskrecord_lv);
		  final ListView lv_taskcomments=(ListView)v.findViewById(R.id.taskcomments_lv);
		  
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
		lv_taskloglist.setAdapter(query());
  	   return v;
	 }   

	 public mcAdapter_taskrecord query(){
		 //Log.d("start join query","OK");
	     
		   String a = TaskData.TdDB.TABLE_NAME_TaskMain;
	       String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
	      // Log.d("start join query","a"+a+"b"+b);
	       //Log.d("start join query",TaskData.TdDB.getDatabaseName());
	    //  Cursor cursor = db.rawQuery("SELECT a.*,b*b FROM a,b INNER JOIN a.TASK_ID=b.TASK_NO;",new String[]{});
	       //String TASK_NAME = "task_name";
	     
		//Cursor c1=TaskData.db_TdDB.rawQuery("select * from "+a+","+b+ " where "+a+"."+TASK_NAME+" like ?", new String[]{"name"});
	    //TaskData.cursor_taskrecord=TaskData.db_TdDB.rawQuery("select * from "+a, null);
	    //Log.d("rawquery","row"+c1.getCount()+"column"+c1.getColumnCount()+"position"+c1.getPosition());
		//c1.moveToFirst();
	    TaskData.cursor_taskloglist=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
		TaskData.mcAdapter_taskloglist = new mcAdapter_taskrecord(getActivity(),TaskData.cursor_taskloglist);

		return TaskData.mcAdapter_taskrecord;
		// lv_taskrecord.setAdapter(adapter_record); 
	 
	 
	 }
	 
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
 	
	}

}
