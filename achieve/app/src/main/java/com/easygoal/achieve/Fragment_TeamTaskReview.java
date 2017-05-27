package com.easygoal.achieve;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
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

public class Fragment_TeamTaskReview extends Fragment {


	View oldview=null;
	View newview=null;
	int selectedID=0;
	String Tags="Fragment_TeamTaskReview";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.fg_teamreview, container, false);
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		 // TaskData.tv_taskreviewcount=(TextView)v.findViewById(R.id.tv_taskreviewcount);
		 
		  final ListView lv_taskreview=(ListView)v.findViewById(R.id.taskreview_lv);

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
		  
		 // TaskData.tv_taskreviewc.setText(String.valueOf(TaskData.cursor_taskreview.getCount()));
		
	     //TaskData.cursor_taskreview=TaskData.db_TdDB.rawQuery("select * from "+a+" where "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{"finished"});
	       
	       TaskData.cursor_teamtaskreview= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+
				   " where "+TaskData.TdDB.TASK_USERGROUP+"=?"+"and "+
				   TaskData.TdDB.TASK_STATUS+"=?",
				   new String[]{TaskData.usergroup,"finished"});
	       //TaskData.cursor_taskreview.requery();
	       TaskData.adapter_teamtaskreview = new mcAdapter_taskreview(getActivity(),R.layout.lv_taskreview,TaskData.cursor_teamtaskreview);
		
		   lv_taskreview.setAdapter(TaskData.adapter_teamtaskreview);

		 spin_team.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			 @Override
			 public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
				 // TODO Auto-generated method stub
				 //usedTimeEachWeek_et.setText(list_timeUnit.get(pos).toString());
				 //TaskData.usergroup = list_team.get(pos).getValue().toString();
				 TaskData.cursor_teamtaskreview= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+
								 " where "+TaskData.TdDB.TASK_USERGROUP+"=?"+"and "+
								 TaskData.TdDB.TASK_STATUS+"=?",
						 new String[]{TaskData.usergroup,"finished"});
				 //TaskData.cursor_taskreview.requery();
				 TaskData.adapter_teamtaskreview = new mcAdapter_taskreview(getActivity(),R.layout.lv_taskreview,TaskData.cursor_teamtaskreview);
				 lv_taskreview.setAdapter(TaskData.adapter_teamtaskreview);
				 TaskData.adapterUpdate();
			 }

			 @Override
			 public void onNothingSelected(AdapterView<?> arg0) {
				 // TODO Auto-generated method stubn /////////////'[]\

			 }
		 });
		  

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
		  tr.start();*/
		
		  
		  lv_taskreview.setOnItemClickListener(new OnItemClickListener(){
             int newPos=0;
             int oldPos=0;
              
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

				try {
					TaskData.selTaskID = String.valueOf(TaskData.cursor_group.getString(TaskData.cursor_group.getColumnIndex(TaskData.TdDB.TASK_ID)));
					TaskData.selTaskSN = String.valueOf(TaskData.cursor_group.getString(TaskData.cursor_group.getColumnIndex(TaskData.TdDB.TASK_SN)));
				} catch (Exception e) {
					Log.d(Tags, e.toString());
					e.printStackTrace();
				}

				if (view != newview) {
					oldview = newview;
					newview = view;
					newview.setBackgroundColor(getResources().getColor(R.color.gray));
					if (oldview != null) {
						oldview.setBackgroundColor(getResources().getColor(R.color.mTextColor2));
					}
				}
			
		  }
		 });
		  	  
		  	 
		return v;
	 
	 }

}
