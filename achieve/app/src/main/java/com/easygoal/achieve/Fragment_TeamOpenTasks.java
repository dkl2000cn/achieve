package com.easygoal.achieve;


import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.Map;

public class Fragment_TeamOpenTasks extends Fragment {



	public SimpleCursorAdapter adapter;

	int selectedID=0;
	View oldview=null;
    View newview = null;
    ListView lv_teamopentasks;
    String Tags="Fragment_TeamOpenTasks";

	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 //@BindView(R.id.swipe_ly)
	 //SwipeRefreshLayout swipeRefreshLayout;

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fg_teamopentasks, container, false);
		//DatabaseHelper TdDB=new DatabaseHelper();
		 //ButterKnife.bind(getActivity());
		 final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_ly);
		  TaskData.tv_teamopentaskscount=(TextView)v.findViewById(R.id.tv_opentaskscount);

		  lv_teamopentasks=(ListView)v.findViewById(R.id.opentasks_lv);

		 Button btn_addrecord=(Button)v.findViewById(R.id.task_addrecord_bt);
		 Button btn_taskdelete=(Button)v.findViewById(R.id.task_delete_bt);
		 Button btn_taskupdate=(Button)v.findViewById(R.id.task_update_bt);
		 Button btn_taskclear=(Button)v.findViewById(R.id.task_clear_bt);

		 TaskData.cursor_teamopentasks=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
				 TaskData.TdDB.TASK_USERGROUP+"=?"+" and "+
				 TaskData.TdDB.TASK_STATUS+"=?"+
				 " order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", new String[]{TaskData.usergroup,"open"});
		  TaskData.adapter_teamopentasks = new mcAdapter_tasks(getActivity(),R.layout.lv_teamopentasks,TaskData.cursor_teamopentasks);
		  lv_teamopentasks.setAdapter(TaskData.adapter_teamopentasks);

	      TaskData.update_total(TaskData.tv_teamopentaskscount,TaskData.cursor_teamopentasks);


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


		  lv_teamopentasks.setOnItemClickListener(new OnItemClickListener(){
             int newPos=0;
             int oldPos=0;

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

				try{
					TaskData.selTaskID=String.valueOf(TaskData.cursor_teamopentasks.getString(TaskData.cursor_teamopentasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
	                TaskData.selTaskSN=String.valueOf(TaskData.cursor_teamopentasks.getString(TaskData.cursor_teamopentasks.getColumnIndex(TaskData.TdDB.TASK_SN)));

					new TaskUtils(getActivity(),"t1").showPopupWindow(getActivity(),view);
            	 }catch(Exception e){
            		 Log.d(Tags, e.toString());
            		 e.printStackTrace();
            	 }

                 if (view!=newview){
 					oldview=newview;
 					newview=view;
 					newview.setBackgroundColor(getResources().getColor(R.color.gray));
 					if (oldview!=null){
 					oldview.setBackgroundColor(getResources().getColor(R.color.mTextColor2));
 					}
 				}



                 /*
                 for(int i=0;i<parent.getCount();i++){
                     View v=parent.getChildAt(i);
                     if (position == i) {
                    	 int horizontal = 1;
						//v.requestFocus();
                         v.setBackgroundColor( getResources().getColor(R.color.gray));
                     } else {
                    	 v.setBackgroundColor(getResources().getColor(R.color.white));
                     }


                 }*/
		  }
		 });

		  lv_teamopentasks.setOnItemLongClickListener(new OnItemLongClickListener(){
	            int newPos=0;
	            int oldPos=0;

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
					// TODO Auto-generated method stub

					try{
						TaskData.selTaskID=String.valueOf(TaskData.cursor_teamopentasks.getString(TaskData.cursor_teamopentasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
		                TaskData.selTaskSN=String.valueOf(TaskData.cursor_teamopentasks.getString(TaskData.cursor_teamopentasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
	            	 }catch(Exception e){
	            		 Log.d(Tags, e.toString());
	            		 e.printStackTrace();
	            	 }


					DialogFragment_TaskDetail dg_taskdetail=new DialogFragment_TaskDetail();
	    	    	TaskTool.showDialog(dg_taskdetail);

					return false;
				}

			 });

		  Button btn_export=(Button)v.findViewById(R.id.export_bt);
		  btn_export.setOnClickListener(new OnClickListener(){


				@Override
				public void onClick(View v) {
			    	Cursor c=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USERGROUP+"=?"+" and "+TaskData.TdDB.TASK_STATUS+"=?"+" order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", new String[]{ TaskData.usergroup,"open"});
					// TODO Auto-generated method stub

				  if (c.getCount()>0){

					  ExportUtils.exportListToFile(getActivity(),c,"待办任务清单");

				}else{
					  Toast.makeText(getActivity(), "记录为空", Toast.LENGTH_SHORT).show();
				  }
				}
		 });

		  btn_taskdelete.setOnClickListener(new OnClickListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (TaskData.selTaskID!=null) {
                    String taskowner=null;
                    Cursor c = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_ID+"="+TaskData.selTaskID,null);
                       if (c!=null&&c.getCount()>0){
                            c.moveToFirst();
                            taskowner=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_OWNER)).trim();
                        };
					if (taskowner!=null&&taskowner.equals(TaskData.user)) {
						/*TaskData.cursor_teamopentasks.moveToPosition(TaskData.cursor_teamopentasks.getPosition());
						int p=TaskData.cursor_teamopentasks.getInt(0);
						Log.d("delete row", String.valueOf(p));
						String where=TaskData.TdDB.TASK_ID+"=?";
						String[] whereArgs={Integer.toString(p)};
						TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain,where, whereArgs);
						Log.d("delete row", "delete OK");
						 Log.d("cursor",String.valueOf(TaskData.cursor_teamopentasks.getCount())+"position"+String.valueOf(TaskData.cursor_teamopentasks.getPosition()));
						 */

						Builder builder = new Builder(getActivity());
						builder.setTitle("确认");
						builder.setMessage("真要删除任务T" + TaskData.selTaskID + "吗？");
						builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
								//show.setText("");
								if (TaskData.privilege > TaskData.entryright) {
									new ConnMySQL(getActivity()).DelByGsonArrayRequestPost("TaskmainServlet", TaskData.TdDB.TABLE_NAME_TaskMain, TaskData.selTaskSN);
									Log.d(Tags, "del by Mysql done");
								}
								TaskData.db_TdDB.execSQL("delete from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " +
										TaskData.TdDB.TASK_SN + "=" + "'" + TaskData.selTaskSN + "'" + " and " +
										TaskData.TdDB.TASK_STATUS + "=" + "'open'" + " and " +
										TaskData.TdDB.TASK_USERGROUP + "=" + "'" + TaskData.usergroup + "';");

								Cursor cr = DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId=" + "'" + "T" + TaskData.selTaskID + "';");

								if (cr.getCount() > 0) {
									cr.moveToFirst();
									String remindersn = cr.getString(1);
									Alarm.alarmCancel(getActivity(), remindersn);
									int reminderno = cr.getInt(0);
									DataSupport.delete(Reminder.class, reminderno);
									Log.d(Tags, "delete reminder " + reminderno);
								}
								TaskData.adapterUpdate();
								TaskData.selTaskID = null;
								TaskData.selTaskSN = null;
							}
						});
						builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub

							}
						});
						builder.create().show();
					} else {
						Toast.makeText(getActivity(), "只有创建者才可以删除此任务", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getActivity(), "请先选定任务", Toast.LENGTH_SHORT).show();
				}	;

			 }

		  });

		  btn_taskclear.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					 //new ConnMySQL(getActivity()).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
					 //Toast.makeText(getActivity(), "已全部清除",Toast.LENGTH_SHORT).show();
					 //Log.d("clear by Mysql","done");

					//for (int i=0;i<TaskData.cursor_teamopentasks.getCount();i++){
				    //TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
					//};
					/*
					if (TaskData.cursor_teamopentasks!=null&&TaskData.cursor_teamopentasks.getCount()>0){
					TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
				    }
					*/
					 Builder builder=new Builder(getActivity());
					      builder.setTitle("确认");
						  builder.setMessage("真要清除吗？");
						  builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
							    //show.setText("");
								TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USERGROUP+"="+"'"+ TaskData.usergroup+"' and "+TaskData.TdDB.TASK_STATUS+"="+"'"+"open"+"';");
								//TaskData.adapter_teamopentasks.notifyDataSetChanged();
								
								TaskTool.clearlinkedReminder(getActivity(),"T");
								if (TaskData.privilege>TaskData.entryright){
								 
									Map params = new HashMap();
									params.put("username", TaskData.user);
									params.put(TaskData.TdDB.TASK_STATUS, "open");
						    	 
									new ConnMySQL(getActivity()).ClearByGsonArrayRequestPost("TaskmainServlet", params); 
								}
								/*
								 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");
									
								 if (cr.getCount()>0){
									   cr.moveToFirst();
									   do{
									   int reminderno = cr.getInt(0);
									   DataSupport.delete(Reminder.class, reminderno);
									   Log.d("clear reminder", "clear "+reminderno);   
									   String remindersn = cr.getString(cr.getColumnIndex("sourceId"));
									   Alarm.alarmCancel(getActivity(), remindersn);	
									   }while(cr.moveToNext());
								   } 
								*/
								
								TaskData.adapterUpdate();
								
								
								 //Log.d(Tags,"TdDB adapter update all done");  
								 Log.d(Tags,"cleared and new count is: "+String.valueOf(TaskData.cursor_teamopentasks.getCount())+"position"+String.valueOf(TaskData.cursor_teamopentasks.getPosition()));
							    TaskData.selTaskID=null;
							} 
						  } );
						  builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}	  
						  });
						  builder.create().show();	    
					  } ;
					
			  });
		  
		  btn_taskupdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				 TaskData.adapterUpdate();
				
				 Log.d(Tags,"TdDB adapter update all done");
				 Log.d(Tags,"update cursor "+String.valueOf(TaskData.cursor_teamopentasks.getCount())+"position"+String.valueOf(TaskData.cursor_teamopentasks.getPosition()));
				
				 
			
				 
			} 
		  });
		 
		  btn_addrecord.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					 //Log.d("from_fg", from_fg.toString()); 
					  //Toast.makeText(getActivity(), "TaskID"+TaskData.selTaskID,Toast.LENGTH_SHORT).show();
					if (TaskData.selTaskID!=null){
					    DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
						TaskTool.showDialog(dialogfrag_comment);
						Log.d(Tags,"show addcomment dialogfrag"+dialogfrag_comment.toString());
					  }else{
						Toast.makeText(getActivity(), "请先选定任务", Toast.LENGTH_LONG).show();	 
					  }
					
					//TaskData.from_fg=showFrag(TaskData.from_fg,R.id.sublayout_task,subfrag_task,3);
				//	Log.d("task tab", "choice3");
				}
			  });

		 swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
				 android.R.color.holo_orange_light, android.R.color.holo_red_light);

		 swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			 @Override
			 public void onRefresh() {
				 if (TaskData.adapter_teamopentasks!=null&&TaskData.adapter_teamopentasks.getCursor().getCount()>0) {
					 TaskData.adapter_teamopentasks.notifyDataSetChanged();
					 //Toast.makeText(getActivity(),"loading",Toast.LENGTH_SHORT).show();
					 swipeRefreshLayout.setRefreshing(false);
				 }else{
					 swipeRefreshLayout.setRefreshing(false);
				 }

			 }
		 });

		 lv_teamopentasks.setOnScrollListener(new ListView.OnScrollListener() {

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
					 //Toast.makeText(getActivity(),"to top",Toast.LENGTH_SHORT).show();
				 }
			 }

			 @Override
			 public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				 //lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
			 }

		 });



		 return v;
	 
	 }


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		 
		
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
		};
    
			
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	
		  
	}	  
}
