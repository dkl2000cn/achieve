package com.easygoal.achieve;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentTaskScore extends Fragment {
	
	

	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	    String Tags="FragmentTaskScore";
	
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.subfg_scoretab_toptab1_taskscore, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		

		 
		
		  final ListView lv_taskscore=(ListView)v.findViewById(R.id.taskscore_lv);		
		  //Button btn1=(Button)v.findViewById(R.id.record_confirm_bt);
		 
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		    TaskData.cursor_taskscore= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=? and "+TaskData.TdDB.TASK_STATUS+"!=?", new String[]{TaskData.user,"cancelled"});   
		
	        TaskData.cursor_finishedtasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=? and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{TaskData.user,"finished"});
			TaskData.tv_scoreheader_accomplished=(TextView)v.findViewById(R.id.tv_scoreheader_accomplished);
			TaskData.tv_scoreheader_achieved=(TextView)v.findViewById(R.id.tv_scoreheader_achieved);
    		TaskData.tv_scoreheader_enjoyment=(TextView)v.findViewById(R.id.tv_scoreheader_enjoyment);
   			 TaskData.tv_scoreheader_experience=(TextView)v.findViewById(R.id.tv_scoreheader_experience);
   			 TaskData.update_sum(TaskData.tv_scoreheader_accomplished,TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_IMPORTANCE);
   			 TaskData.update_sum_achieved();
   			 TaskData.update_sum_enjoyment();
			TaskData.update_sum_experience();
		 TaskData.mcadapter_taskscore=new mcAdapter_taskscore(getActivity(), TaskData.cursor_taskscore);  
		 //SimpleCursorAdapter adapter_score = new SimpleCursorAdapter(getActivity(), R.layout.lv_taskscore, c,itemlist_taskscore,controllist_taskscore, 0);
		
		 lv_taskscore.setAdapter(TaskData.mcadapter_taskscore); 
  	     Log.d(Tags,"lv_taskscore show data ok" +lv_taskscore.toString());
  	   
		return v;
	 }   

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
			  
	}

	  
}
