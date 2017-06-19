package com.easygoal.achieve;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.litepal.crud.DataSupport;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import net.sf.json.JSONArray;

public class FragmentAllTasks extends Fragment {
	
	
	
	public SimpleCursorAdapter adapter;
	int selectedID=0;
    int pageNum=1;
    int pageCount=10;
	com.easygoal.achieve.mListView lv_alltasks;
    JSONArray jolist;
    View oldview=null;
    View newview = null;
    String Tags="FragmentAllTasks";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 @Override  
	    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.task_subfg_toptab2_alltasks, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		
		  TaskData.tv_alltaskscount=(TextView)v.findViewById(R.id.tv_alltaskscount);
		  final TextView tv1=(TextView)v.findViewById(R.id.task_item1_name_tv);
		  final TextView tv4=(TextView)v.findViewById(R.id.task_item4_importance_tv);
		  final TextView tv5=(TextView)v.findViewById(R.id.task_item5_urgency_tv);
		  final TextView tv6=(TextView)v.findViewById(R.id.task_item6_assess_tv);
		  final TextView tv15=(TextView)v.findViewById(R.id.task_item15_deadline_tv);
		  final TextView tv16=(TextView)v.findViewById(R.id.task_item16_progress_tv);
		  final TextView tv18=(TextView)v.findViewById(R.id.task_item18_status_tv);
		  final TextView tv19=(TextView)v.findViewById(R.id.task_item19_finished_tv);
		  final TextView tv20=(TextView)v.findViewById(R.id.task_item20_delayed_tv);
		  lv_alltasks=(com.easygoal.achieve.mListView)v.findViewById(R.id.alltasks_lv);

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
		 CheckBox cb_tip=(CheckBox)v.findViewById(R.id.cb_tip);
		 final ViewFlipper viewFlipper=(ViewFlipper)v.findViewById(R.id.vf_notice);

		 viewFlipper.setVisibility(View.INVISIBLE);
		 cb_tip.setChecked(false);
		 cb_tip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			 @Override
			 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				 if (isChecked){
					 viewFlipper.addView(inflater.inflate(R.layout.merge_noticeitem,null));
					 viewFlipper.addView(inflater.inflate(R.layout.merge_noticeitem2,null));
					 viewFlipper.addView(inflater.inflate(R.layout.merge_noticeitem3,null));
					 viewFlipper.addView(inflater.inflate(R.layout.merge_noticeitem4,null));
					 viewFlipper.addView(inflater.inflate(R.layout.merge_noticeitem5,null));
					 viewFlipper.addView(inflater.inflate(R.layout.merge_noticeitem6,null));
					 viewFlipper.addView(inflater.inflate(R.layout.merge_noticeitem7,null));
					 viewFlipper.addView(inflater.inflate(R.layout.merge_noticeitem8,null));
					 viewFlipper.setVisibility(View.VISIBLE);
				 }else{
					 viewFlipper.removeAllViews();
					 viewFlipper.setVisibility(View.INVISIBLE);
				 }
			 }
		 });

	      TaskData.cursor_alltasks=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
	      TaskData.tv_alltaskscount.setText(String.valueOf(TaskData.cursor_alltasks.getCount()));
	      //TaskData.adapter_alltasks_pagedivided = new mcAdapter_tasks_pagedivided(getActivity(),R.layout.lv_alltasks,TaskData.cursor_alltasks,pageNum,pageCount);
		  TaskData.adapter_alltasks= new mcAdapter_tasks(getActivity(),R.layout.lv_alltasks,TaskData.cursor_alltasks);
		 // Log.d("adapter_alltasks",TaskData.adapter_alltasks.toString() );
		  lv_alltasks.setAdapter(TaskData.adapter_alltasks);
		  //Toast.makeText(getActivity(), "第"+pageNum+"页",Toast.LENGTH_SHORT).show();
		  // Log.d("lv alltasks show data","ok "+lv_alltasks.toString());

		 lv_alltasks.setOnDeleteListener(new mListView.OnDeleteListener() {
			 long oldClickTime=0;

			 @Override
			 public void onDelete(int index) {
				 //Log.d("mlistview delete", "del"+index);
				 long task_id = lv_alltasks.getAdapter().getItemId(index);
				 TaskData.selTaskID=String.valueOf(task_id);
				 TaskTool.quickCancel(getActivity());
				 TaskData.adapter_alltasks.notifyDataSetChanged();
				 //Toast.makeText(getActivity(),"任务"+task_id+"删除成功",Toast.LENGTH_SHORT).show();
			 }

			 @Override
			 public void onShow(int index) {
				 /*
				 long task_id = lv_alltasks.getAdapter().getItemId(index);
				 TaskData.selTaskID=String.valueOf(task_id);
				 DialogFragment_TaskDetail dg_taskdetail=new DialogFragment_TaskDetail();
				 TaskTool.showDialog(dg_taskdetail);
				 */
			 }

			 @Override
			 public void onDoubleChick(int index) {
				 if (oldClickTime==0){
					 oldClickTime=System.currentTimeMillis();
				 }
				 if ((System.currentTimeMillis()-oldClickTime)>1000) {
					 long task_id = lv_alltasks.getAdapter().getItemId(index);
					 TaskData.selTaskID = String.valueOf(task_id);
					 if (TaskData.selTaskID!=null&& Integer.parseInt(TaskData.selTaskID)>0){
						 DialogFragment_TaskDetail dg_taskdetail = new DialogFragment_TaskDetail();
						 TaskTool.showDialog(dg_taskdetail);
					 }
					 oldClickTime=System.currentTimeMillis();
				 }
			 }

			 @Override
			 public void onClose(int index) {
				 /*
				 long task_id = lv_alltasks.getAdapter().getItemId(index);
				 TaskData.selTaskID=String.valueOf(task_id);
				 TaskTool.quickclose(getActivity());
				 TaskData.adapter_alltasks.notifyDataSetChanged();*/
				 //Toast.makeText(getActivity(),"任务"+task_id+"快速结束",Toast.LENGTH_SHORT).show();

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

		  lv_alltasks.setOnItemClickListener(new OnItemClickListener(){
             int newPos=0;
             int oldPos=0;
              
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
			
              if (TaskData.cursor_alltasks.getCount()>0){ 
            	 try{ 
            		 TaskData.selTaskID=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
            		 TaskData.selTaskSN=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
					 new TaskUtils(getActivity(),"s2").showPopupWindow(getActivity(),view);
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
		  /*
		  lv_alltasks.setOnItemLongClickListener(new OnItemLongClickListener(){
	           
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
					// TODO Auto-generated method stub
					
					 try{ 
	            		 TaskData.selTaskID=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
	            		 TaskData.selTaskSN=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
	            	 }catch(Exception e){
	            		 Log.d(Tags, e.toString());
	            		 e.printStackTrace();
	            	 }
					DialogFragment_TaskDetail dg_taskdetail=new DialogFragment_TaskDetail();
	    	    	TaskTool.showDialog(dg_taskdetail);
		
					return false;
				}
	            
			 });*/

		  
		  Button btn_export=(Button)v.findViewById(R.id.export_bt);
		  btn_export.setOnClickListener(new OnClickListener(){

				
				@Override
				public void onClick(View v) {
			    	export();
				}	
		 });
		  
		  
		  
		  btn_addrecord.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					addrecord();
				}
			  });
		  
		  
		  btn_taskdelete.setOnClickListener(new OnClickListener(){

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				delete();
				 }	  
					
			  });			
					
			  btn_taskclear.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						clear();
						  } ;
						
				  });
			  
			  btn_taskupdate.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					TaskData.adapterUpdate();

					 Log.d(Tags,"update cursor"+String.valueOf(TaskData.cursor_opentasks.getCount())+"position"+String.valueOf(TaskData.cursor_opentasks.getPosition()));
					 
				} 
			  });
		  
		  /*
		  btn_taskclear.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 new ConnMySQL(getActivity()).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
					 //Toast.makeText(getActivity(), "已全部清除",Toast.LENGTH_SHORT).show();
					 Log.d("clear by Mysql","done");
					
				    TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USER+"="+TaskData.user+";"); 
				
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
					
					 Log.d("TdDB adapter update", "all done");  
					 Log.d("clear",String.valueOf(TaskData.cursor_alltasks.getCount())+"position"+String.valueOf(TaskData.cursor_alltasks.getPosition()));
					
					
				} 
			  });
	
		  btn_taskdelete.setOnClickListener(new OnClickListener(){

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					if (TaskData.selTaskID!=null){
					
							
							 
							 new ConnMySQL(getActivity()).DelByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain, TaskData.selTaskSN);
							 Log.d("del by Mysql","done");
							 TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
							             TaskData.TdDB.TASK_SN+"="+"'"+TaskData.selTaskSN+"'"+" and "+
									     TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"';");					 
							 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId="+"'"+"T"+TaskData.selTaskID+"';");
							
							 if (cr.getCount()>0){
								   cr.moveToFirst();
								   do{
								   int reminderno = cr.getInt(0);
								   DataSupport.delete(Reminder.class, reminderno);
								   Log.d("delete reminder", "delete "+reminderno);   
								   
								   }while(cr.moveToNext());
							   } 
							 
							 //Toast.makeText(getActivity(), "已删除TaskID"+TaskData.selTaskID,Toast.LENGTH_SHORT).show(); 
							 TaskData.adapterUpdate();
							 TaskData.selTaskID=null;
						     TaskData.selTaskSN=null;
							
							
					
					    }else{
							Toast.makeText(getActivity(), "请先选定任务", Toast.LENGTH_SHORT).show();	 
						  }
						
					
				
				}
		  
			  });
		   */
		 final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_ly);
		 swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
				 android.R.color.holo_orange_light, android.R.color.holo_red_light);

		 swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			 @Override
			 public void onRefresh() {
				 if (TaskData.adapter_alltasks!=null&&TaskData.adapter_alltasks.getCursor().getCount()>0) {
					 TaskData.adapter_alltasks.notifyDataSetChanged();
					 //Toast.makeText(getActivity(),"loading",Toast.LENGTH_SHORT).show();
					 swipeRefreshLayout.setRefreshing(false);
				 }else{
					 swipeRefreshLayout.setRefreshing(false);
				 }
			 }
		 });

		 lv_alltasks.setOnScrollListener(new ListView.OnScrollListener() {

			 @Override
			 public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState==SCROLL_STATE_IDLE){
					//TaskData.adapter_alltasks.setScrollState(false);
				}
				 if (scrollState ==view.SCROLL_INDICATOR_BOTTOM) {
					 //TaskData.adapter_alltasks.setScrollState(false);
					 swipeRefreshLayout.setRefreshing(false);
					 // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
					 Toast.makeText(getActivity(),"已到最后页",Toast.LENGTH_SHORT).show();
				 }
				 if (scrollState ==view.SCROLL_INDICATOR_TOP) {
					 //TaskData.adapter_alltasks.setScrollState(false);
					 swipeRefreshLayout.setRefreshing(false);
					 // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
					 //Toast.makeText(getActivity(),"已到最上页",Toast.LENGTH_SHORT).show();
				 }

			 }

			 @Override
			 public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				 //lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
				 //TaskData.adapter_alltasks.setScrollState(true);
			 }

		 });

		 return v;
	 
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
	          //  dialogFragment = new Fragment_Search();  
	    	   dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);  
	           dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	           dialogFragment.show(getFragmentManager(), "dialog");
	    }

	private void showPopupWindow(Context context, View rootview) {
		//设置contentView
		View popupView = LayoutInflater.from(context).inflate(R.layout.popupwin_tasktoolbox, null);
		final PopupWindow mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(false);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

		mPopupWindow.setContentView(popupView);

		//设置各个控件的点击响应
		Button btn_addrecord=(Button)popupView.findViewById(R.id.task_addrecord_bt);
		Button btn_taskdelete=(Button)popupView.findViewById(R.id.task_delete_bt);
		Button btn_taskupdate=(Button)popupView.findViewById(R.id.task_update_bt);
		Button btn_taskclear=(Button)popupView.findViewById(R.id.task_clear_bt);
		Button btn_export=(Button)popupView.findViewById(R.id.export_bt);

		ItemClickListener itemlistener = new ItemClickListener();
		btn_addrecord.setOnClickListener(itemlistener);
		btn_taskclear.setOnClickListener(itemlistener);
		btn_taskdelete.setOnClickListener(itemlistener);
		btn_taskupdate.setOnClickListener(itemlistener);
		btn_export.setOnClickListener(itemlistener);

		//显示PopupWindow
		//View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.main, null);
		mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

	}

	class ItemClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case (R.id.task_addrecord_bt): addrecord();break;
				case (R.id.task_update_bt): update();break;
				case (R.id.task_delete_bt): delete();break;
				case (R.id.task_clear_bt): clear();break;
				case (R.id.export_bt): export();break;
				default:break;
			}
		}
	}

	void update(){
		TaskData.adapterUpdate();
	}
	void delete(){
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
					if (TaskData.privilege>TaskData.entryright){
						new ConnMySQL(getActivity()).DelByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain,TaskData.selTaskSN);
						Log.d(Tags,"del by Mysql done");
					}
					TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
							TaskData.TdDB.TASK_SN+"="+"'"+TaskData.selTaskSN+"'"+" and "+
							//TaskData.TdDB.TASK_STATUS+"="+"'open'"+" and "+
							TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"';");



					Cursor cr=DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId="+"'"+"T"+TaskData.selTaskID+"';");

					if (cr.getCount()>0){
						cr.moveToFirst();
						do{
							String remindersn = cr.getString(1);
							Alarm.alarmCancel(getActivity(), remindersn);
							int reminderno = cr.getInt(0);
							DataSupport.delete(Reminder.class, reminderno);
							Log.d(Tags,"delete reminder "+reminderno);

						}while(cr.moveToNext());

					}

					TaskData.adapterUpdate();
					TaskData.selTaskID=null;
					TaskData.selTaskSN=null;
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
	void addrecord(){
		if (TaskData.selTaskID!=null){
			DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
			showDialog(dialogfrag_comment);
			Log.d(Tags,"dialogfrag"+dialogfrag_comment.toString());
		}else{
			Toast.makeText(getActivity(), "请先选定任务", Toast.LENGTH_LONG).show();
		}
	}
	void clear(){
		Builder builder=new AlertDialog.Builder(getActivity());
		builder.setTitle("确认");
		builder.setMessage("真要清除吗？");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
				//show.setText("");
				TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"';");
				//TaskData.adapter_opentasks.notifyDataSetChanged();
				TaskTool.clearlinkedReminder(getActivity(),"T");
									 /*
									 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");

									 if (cr.getCount()>0){
										   cr.moveToFirst();
										   do{
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d(Tags,"clear reminder"+reminderno);

										   }while(cr.moveToNext());
									   }
									*/
				if (TaskData.privilege>TaskData.entryright){
					Map params = new HashMap();

					params.put("username", TaskData.user);
					new ConnMySQL(getActivity()).ClearByGsonArrayRequestPost("TaskmainServlet",params);
					Log.d(Tags,"clear all taks by mysql");
				}


				TaskData.adapterUpdate();



				Log.d(Tags,"clear"+String.valueOf(TaskData.cursor_alltasks.getCount())+"position"+String.valueOf(TaskData.cursor_alltasks.getPosition()));
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
	}
	void export(){
		Cursor c= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
		// TODO Auto-generated method stub

		if (c.getCount()>0){

			ExportUtils.exportListToFile(getActivity(),c,"全部任务清单");

		}else{
			Toast.makeText(getActivity(), "记录为空", Toast.LENGTH_SHORT).show();
		}
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

 class mScrollListener implements OnScrollListener {  
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totaltemCount) {
		// TODO Auto-generated method stub
		Log.d(Tags,"scorll to"+firstVisibleItem+"count"+visibleItemCount);
		TaskData.adapter_alltasks.setScrollState(false);
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState==SCROLL_STATE_IDLE){
			TaskData.adapter_alltasks.setScrollState(false);
			/*
			if (view.getLastVisiblePosition()==(view.getCount()-1)){
				int aaa=TaskData.cursor_alltasks.getCount()-pageCount*pageNum;
			  if ((aaa<=pageCount)&&(aaa>0))
			     { 
				  pageNum++;
				    TaskData.adapter_alltasks_pagedivided = new mcAdapter_tasks_pagedivided(getActivity(),R.layout.lv_alltasks,TaskData.cursor_alltasks,pageNum,pageCount);
				    //Log.d("adapter_alltasks",TaskData.adapter_alltasks.toString() );
				    TaskData.adapter_alltasks_pagedivided.notifyDataSetChanged();
				    lv_alltasks.setAdapter(TaskData.adapter_alltasks_pagedivided); 
				    //Toast.makeText(getActivity(), "最后第"+pageNum+"页",Toast.LENGTH_SHORT).show();
			     
			     }else{ if (aaa>pageCount){
						pageNum++;
					    TaskData.adapter_alltasks_pagedivided = new mcAdapter_tasks_pagedivided(getActivity(),R.layout.lv_alltasks,TaskData.cursor_alltasks,pageNum,pageCount);
					    //Log.d("adapter_alltasks",TaskData.adapter_alltasks.toString() );
					    TaskData.adapter_alltasks_pagedivided.notifyDataSetChanged();
					    lv_alltasks.setAdapter(TaskData.adapter_alltasks_pagedivided); 
						
						 //Toast.makeText(getActivity(), "第"+pageNum+"页",Toast.LENGTH_SHORT).show();
			          }	
			     }
			  }*/
		}else{
			 TaskData.adapter_alltasks.setScrollState(true);
		}
	}
  }

}
