package com.easygoal.achieve;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Fragment_TeamTaskRun extends Fragment {
	

	int selectedID=0;
	View oldview=null;
    View newview = null;
    String Tags="Fragment_TeamTaskRun";
	 @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.fg_teamtaskrun, container, false);
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		 // TaskData.tv_taskruncount=(TextView)v.findViewById(R.id.tv_taskruncount);
		 
		  final ListView lv_taskrun=(ListView)v.findViewById(R.id.taskrun_lv);

		 final TextView tv_teamname=(TextView)v.findViewById(R.id.tv_teamname);
		 if (TaskData.usergroup!=null) {
			 tv_teamname.setText(TaskData.usergroup);
		 }
		 final Spinner spin_team = (Spinner)v.findViewById(R.id.spin_team);
		 final List<InputValueSet> list_team=new ArrayList<InputValueSet>();
		 list_team.add(new InputValueSet(1,"A"));
		 list_team.add(new InputValueSet(2,"B"));
		 list_team.add(new InputValueSet(3,"C"));
		 mArrayAdapter adapt_spinteam = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_team);
		 spin_team.setAdapter(adapt_spinteam);
		//  TextView textView = ViewFinder.findViewById(R.id.my_textview); 
		
	
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		   // final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		  //  Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		
		 // TaskData.tv_taskrunc.setText(String.valueOf(TaskData.cursor_taskrun.getCount()));
		
	      //TaskData.cursor_taskrun=TaskData.db_TdDB.rawQuery("select * from "+a+" where "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{"open"});
	      TaskData.cursor_teamtaskrun= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +
				  " where "+TaskData.TdDB.TASK_USERGROUP+"=?"+
				  " and "+TaskData.TdDB.TASK_STATUS+"=?",
				  new String[]{TaskData.usergroup,"open"});
	      TaskData.adapter_teamtaskrun = new mcAdapter_taskrun(getActivity(),R.layout.lv_taskrun,TaskData.cursor_teamtaskrun);
		 
		  lv_taskrun.setAdapter(TaskData.adapter_teamtaskrun);

		 spin_team.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			 @Override
			 public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
				 // TODO Auto-generated method stub
				 //usedTimeEachWeek_et.setText(list_timeUnit.get(pos).toString());
				 //TaskData.usergroup = list_team.get(pos).getValue().toString();
				 TaskData.cursor_teamtaskrun= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +
								 " where "+TaskData.TdDB.TASK_USERGROUP+"=?"+
								 " and "+TaskData.TdDB.TASK_STATUS+"=?",
						 new String[]{TaskData.usergroup,"open"});
				 TaskData.adapter_teamtaskrun = new mcAdapter_taskrun(getActivity(),R.layout.lv_taskrun,TaskData.cursor_teamtaskrun);
				 lv_taskrun.setAdapter(TaskData.adapter_teamtaskrun);
				 TaskData.adapterUpdate();
			 }

			 @Override
			 public void onNothingSelected(AdapterView<?> arg0) {
				 // TODO Auto-generated method stubn /////////////'[]\
			 }
		 });

		  lv_taskrun.setOnItemClickListener(new OnItemClickListener(){
           
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
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
		  	  
		  	 
		return v;
	 
	 }

}
