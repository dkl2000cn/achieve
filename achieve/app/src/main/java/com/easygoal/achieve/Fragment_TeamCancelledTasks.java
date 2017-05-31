package com.easygoal.achieve;


import android.app.Activity;
import android.app.AlertDialog;
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

public class Fragment_TeamCancelledTasks extends Fragment {
	
	
	
	public SimpleCursorAdapter adapter;
	int selectedID=0;
	View oldview=null;
    View newview = null;
    String Tags="FragmentCancelledTasks";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.fg_teamancelledtasks, container, false);
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		  TaskData.tv_teamcancelledtaskscount=(TextView)v.findViewById(R.id.tv_cancelledtaskscount);
		  final ListView lv_cancelledtasks=(ListView)v.findViewById(R.id.cancelledtasks_lv);
		//  TextView textView = ViewFinder.findViewById(R.id.my_textview);
		 Button btn_addrecord=(Button)v.findViewById(R.id.task_addrecord_bt);
		 Button btn_taskdelete=(Button)v.findViewById(R.id.task_delete_bt);
		 Button btn_taskupdate=(Button)v.findViewById(R.id.task_update_bt);
		 Button btn_taskclear=(Button)v.findViewById(R.id.task_clear_bt);

		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		   // final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		  //  Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		 

		 
	      TaskData.cursor_teamcancelledtasks=  TaskData.db_TdDB.rawQuery("select * from "+ TaskData.TdDB.TABLE_NAME_TaskMain+
				  " where "+TaskData.TdDB.TASK_USERGROUP+"=?"+" and "+
				  TaskData.TdDB.TASK_STATUS+"=?", new String[]{TaskData.usergroup,"cancelled"});
		  TaskData.adapter_teamcancelledtasks = new mcAdapter_tasks(getActivity(),R.layout.lv_teamcancelledtasks,TaskData.cursor_teamcancelledtasks);
		  TaskData.tv_teamcancelledtaskscount.setText(String.valueOf(TaskData.cursor_teamcancelledtasks.getCount()));
		  lv_cancelledtasks.setAdapter(TaskData.adapter_teamcancelledtasks);
  	    Log.d(Tags,"lv cancelledtasks show data ok ");
  	      
		 
		  
		  //lv_teamcancelledtasks.setFocusable(true);
		  //lv_teamcancelledtasks.setItemsCanFocus(true);
		  //lv_teamcancelledtasks.setSelected(true);
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
		
		  
		  lv_cancelledtasks.setOnItemClickListener(new OnItemClickListener(){
	             int newPos=0;
	             int oldPos=0;
	              
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					try{ 
						 TaskData.selTaskID=String.valueOf(TaskData.cursor_teamcancelledtasks.getString(TaskData.cursor_teamcancelledtasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
		                 TaskData.selTaskSN=String.valueOf(TaskData.cursor_teamcancelledtasks.getString(TaskData.cursor_teamcancelledtasks.getColumnIndex(TaskData.TdDB.TASK_SN)));

						 new TaskUtils(getActivity(),"t4").showPopupWindow(getActivity(),view);
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
	                  
			  }
			 });

		  lv_cancelledtasks.setOnItemLongClickListener(new OnItemLongClickListener(){
	            int newPos=0;
	            int oldPos=0;

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
					// TODO Auto-generated method stub

					try{
						 TaskData.selTaskID=String.valueOf(TaskData.cursor_teamcancelledtasks.getString(TaskData.cursor_teamcancelledtasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
		                 TaskData.selTaskSN=String.valueOf(TaskData.cursor_teamcancelledtasks.getString(TaskData.cursor_teamcancelledtasks.getColumnIndex(TaskData.TdDB.TASK_SN)));

	            	 }catch(Exception e){
	            		 Log.d(Tags, e.toString());
	            		 e.printStackTrace();
	            	 }

					DialogFragment_TaskDetail dg_taskdetail=new DialogFragment_TaskDetail();
	    	    	TaskTool.showDialog(dg_taskdetail);

					return false;
				}

			 });
		  
		 /* m_listview.setOnTouchListener(new OnTouchListener(){

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
		
		  });*/
		  
		  
		
		  btn_taskupdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaskData.adapterUpdate();
				
		
				 Log.d(Tags,"update cursor"+String.valueOf(TaskData.cursor_teamcancelledtasks.getCount())+"position"+String.valueOf(TaskData.cursor_teamcancelledtasks.getPosition()));
				
				
			} 
		  });
		 
		
		  
		  Button btn_export=(Button)v.findViewById(R.id.export_bt);
		  btn_export.setOnClickListener(new OnClickListener(){

				
				@Override
				public void onClick(View v) {
			    	Cursor c=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{"cancelled"});
					// TODO Auto-generated method stub			    
			    	
				  if (c.getCount()>0){
			       
					  ExportUtils.exportListToFile(getActivity(),c,"���������嵥");
			       
				}else{   
					  Toast.makeText(getActivity(), "��¼Ϊ��", Toast.LENGTH_SHORT).show();	 
				  }
				}	
		 });
		  
		  
		  
		  btn_addrecord.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					 //Toast.makeText(getActivity(), "TaskID"+TaskData.selTaskID,Toast.LENGTH_SHORT).show();
						if (TaskData.selTaskID!=null){
						    DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
							TaskTool.showDialog(dialogfrag_comment);
							Log.d(Tags,"dialogfrag"+dialogfrag_comment.toString());
						  }else{
							Toast.makeText(getActivity(), "����ѡ������", Toast.LENGTH_LONG).show();	 
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
							/*TaskData.cursor_opentasks.moveToPosition(TaskData.cursor_opentasks.getPosition());
							int p=TaskData.cursor_opentasks.getInt(0);
							Log.d("delete row", String.valueOf(p));
							String where=TaskData.TdDB.TASK_ID+"=?";
							String[] whereArgs={Integer.toString(p)};		
							TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain,where, whereArgs);
							Log.d("delete row", "delete OK");
							 Log.d("cursor",String.valueOf(TaskData.cursor_opentasks.getCount())+"position"+String.valueOf(TaskData.cursor_opentasks.getPosition()));
							 */

							Builder builder = new AlertDialog.Builder(getActivity());
							builder.setTitle("ȷ��");
							builder.setMessage("��Ҫɾ������T" + TaskData.selTaskID + "��");
							builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
									//show.setText("");
									if (TaskData.privilege > TaskData.entryright) {
										new ConnMySQL(getActivity()).DelByGsonArrayRequestPost("TaskmainServlet", TaskData.TdDB.TABLE_NAME_TaskMain, TaskData.selTaskSN);
										Log.d("del by Mysql", "done");
									}
									TaskData.db_TdDB.execSQL("delete from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " +
											TaskData.TdDB.TASK_SN + "=" + "'" + TaskData.selTaskSN + "'" + " and " +
											TaskData.TdDB.TASK_STATUS + "=" + "'cancelled'" + " and " +
											TaskData.TdDB.TASK_USERGROUP + "=" + "'" + TaskData.usergroup + "';");


									Cursor cr = DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId=" + "'" + "T" + TaskData.selTaskSN + "';");

									if (cr.getCount() > 0) {
										cr.moveToFirst();
										do {
											String remindersn = cr.getString(1);
											Alarm.alarmCancel(getActivity(), remindersn);
											int reminderno = cr.getInt(0);
											DataSupport.delete(Reminder.class, reminderno);
											Log.d("delete reminder", "delete " + reminderno);

										} while (cr.moveToNext());

									}

									TaskData.adapterUpdate();
									TaskData.selTaskID = null;
									TaskData.selTaskSN = null;
								}
							});
							builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub

								}
							});
							builder.create().show();
						} else {
							Toast.makeText(getActivity(), "ֻ�д����߲ſ���ɾ��������", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(getActivity(), "����ѡ������", Toast.LENGTH_SHORT).show();
					}	;
				 }	  
					
			  });			
					
			  btn_taskclear.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						 //new ConnMySQL(getActivity()).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
						 //Toast.makeText(getActivity(), "��ȫ�����",Toast.LENGTH_SHORT).show();
						 //Log.d("clear by Mysql","done");
						
						//for (int i=0;i<TaskData.cursor_opentasks.getCount();i++){
					    //TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);	
						//};
						/*
						if (TaskData.cursor_opentasks!=null&&TaskData.cursor_opentasks.getCount()>0){
						TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);	
					    }
						*/
						 Builder builder=new AlertDialog.Builder(getActivity());
						      builder.setTitle("ȷ��");
							  builder.setMessage("��Ҫ�����");
							  builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
								    //show.setText("");
									TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USERGROUP+"="+"'"+TaskData.usergroup+"' and "+TaskData.TdDB.TASK_STATUS+"="+"'"+"cancelled"+"';");
									//TaskData.adapter_opentasks.notifyDataSetChanged();
									
									TaskTool.clearlinkedReminder(getActivity(),"T");
									/*
									 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");
										
									 if (cr.getCount()>0){
										   cr.moveToFirst();
										   do{
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d("clear reminder", "clear "+reminderno);   
										   
										   }while(cr.moveToNext());
									   } 
									*/
									if (TaskData.privilege>TaskData.entryright){
										 
											Map params = new HashMap();
											params.put("username", TaskData.user);
											params.put(TaskData.TdDB.TASK_STATUS, "finished");
							    	 
											new ConnMySQL(getActivity()).ClearByGsonArrayRequestPost("TaskmainServlet", params); 
									}
									
									TaskData.adapterUpdate();
									
									
									
									 Log.d(Tags,String.valueOf(TaskData.cursor_teamcancelledtasks.getCount())+"position"+String.valueOf(TaskData.cursor_teamcancelledtasks.getPosition()));
								    TaskData.selTaskID=null;
								} 
							  } );
							  builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){

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
					
					 Log.d(Tags,"update cursor"+String.valueOf(TaskData.cursor_teamcancelledtasks.getCount())+"position"+String.valueOf(TaskData.cursor_opentasks.getPosition()));
					
					 
				
					 
				} 
			  });
		  
		  /*
		  
		  btn_taskdelete.setOnClickListener(new OnClickListener(){

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					if (TaskData.selTaskID!=null){
					
						
							 new ConnMySQL(getActivity()).DelByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain, TaskData.selTaskSN);
							 Log.d("del by Mysql","done");
							 //TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_SN+"="+"'"+TaskData.selTaskSN+"';");
							 TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USER+"="+TaskData.user+" and "+TaskData.TdDB.TASK_STATUS+"="+"'"+"cancelled"+"';");
							 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId="+"'"+"T"+TaskData.selTaskID+"';");
							
							 if (cr.getCount()>0){
								   cr.moveToFirst();
								   do{
								   int reminderno = cr.getInt(0);
								   DataSupport.delete(Reminder.class, reminderno);
								   Log.d("delete reminder", "delete "+reminderno);   
								   
								   }while(cr.moveToNext());
							   } 
							 
							 //Toast.makeText(getActivity(), "��ɾ��TaskID"+TaskData.selTaskID,Toast.LENGTH_SHORT).show(); 
							 TaskData.adapterUpdate();
							TaskData.selTaskID=null;
							
							
					
					    }else{
							Toast.makeText(getActivity(), "����ѡ������", Toast.LENGTH_SHORT).show();	 
						  }
						
					
				
				}
		  
			  });
		 
		  
		  btn_taskclear.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USER+"="+TaskData.user+" and "+TaskData.TdDB.TASK_STATUS+"="+"'"+"cancelled"+"';");	
					//TaskData.adapter_opentasks.notifyDataSetChanged();
					
					 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");
						
					 if (cr.getCount()>0){
						   cr.moveToFirst();
						   do{
						   int reminderno = cr.getInt(0);
						   DataSupport.delete(Reminder.class, reminderno);
						   Log.d("clear reminder", "clear "+reminderno);   
						   
						   }while(cr.moveToNext());
					   } 
		
					
					TaskData.adapterUpdate();
					TaskData.selTaskID=null;
					TaskData.selTaskSN=null;
				} 
			  });
		 
		 */
		 final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_ly);
		 swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
				 android.R.color.holo_orange_light, android.R.color.holo_red_light);

		 swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			 @Override
			 public void onRefresh() {
				 if (TaskData.adapter_teamcancelledtasks!=null&&TaskData.adapter_teamcancelledtasks.getCursor().getCount()>0) {
					 TaskData.adapter_teamcancelledtasks.notifyDataSetChanged();
					 //Toast.makeText(getActivity(),"loading",Toast.LENGTH_SHORT).show();
					 swipeRefreshLayout.setRefreshing(false);
				 }else{
					 swipeRefreshLayout.setRefreshing(false);
				 }

			 }
		 });

		 lv_cancelledtasks.setOnScrollListener(new ListView.OnScrollListener() {

			 @Override
			 public void onScrollStateChanged(AbsListView view, int scrollState) {
				 if (scrollState ==view.SCROLL_INDICATOR_BOTTOM) {
					 swipeRefreshLayout.setRefreshing(false);
					 // �˴�����ʵ��Ŀ�У��뻻�������������ݴ��룬sendRequest .....
					 Toast.makeText(getActivity(),"�ѵ����ҳ",Toast.LENGTH_SHORT).show();
				 }
				 if (scrollState ==view.SCROLL_INDICATOR_TOP) {
					 swipeRefreshLayout.setRefreshing(false);
					 // �˴�����ʵ��Ŀ�У��뻻�������������ݴ��룬sendRequest .....
					 //Toast.makeText(getActivity(),"�ѵ�����ҳ",Toast.LENGTH_SHORT).show();
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
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
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
		}

			
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	
		  
	}	  
}
