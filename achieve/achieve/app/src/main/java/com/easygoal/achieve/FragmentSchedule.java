package com.easygoal.achieve;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentSchedule extends Fragment {

	 String date1;
	 String date2;
	 String taskdeadlineDate;
	private ImageView iv_left;
	private ImageView iv_right;
	private TextView tv_date;
	private TextView tv_week;
	private TextView tv_today;
	mListView  lv_calendartasks;
	 int year;
	 int month;
	 int day;
	 String MinMonthDate;
	 String MaxMonthDate;
	//com.easygoal.achieve.MonthDateView calendarView;

	 String Tags="FragmentShedule";

	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.subfg_home3_calendar, container, false);
		//DatabaseHelper TdDB=new DatabaseHelper();

		 TaskData.calendarView = (com.easygoal.achieve.MonthDateView )v.findViewById(R.id.calendarView1);
		 lv_calendartasks=(mListView)v.findViewById(R.id.opentasks_lv);
         /*
		 List<Integer> list = new ArrayList<Integer>();
		 list.add(10);
		 list.add(12);
		 list.add(15);
		 list.add(16);*/
		 iv_left = (ImageView) v.findViewById(R.id.iv_left);
		 iv_right = (ImageView) v.findViewById(R.id.iv_right);
		 tv_date = (TextView) v.findViewById(R.id.date_text);
		 tv_week  =(TextView) v.findViewById(R.id.week_text);
		 tv_today = (TextView) v.findViewById(R.id.tv_today);

		 TaskData.calendarView .setTextView(tv_date,tv_week);
		 initShowDaysWithThings();
		 //calendarView.setDaysHasThingList(list);
		 /*
		 new Handler().postDelayed(new Runnable() {
			 @Override
			 public void run() {
				 initShowDaysWithThings();
			 }
		 },3000);*/

		 TaskData.calendarView .setDateClick(new MonthDateView.DateClick() {

			 @Override
			 public void onClickOnDate() {
				 showDayTasks();
				 //Toast.makeText(getActivity(), "µã»÷ÁË£º" + calendarView.getmSelDay(), Toast.LENGTH_SHORT).show();
			 }
		 });
		 setOnlistener();

           
  		  /*
		 calendarView.setOnDateChangeListener(new OnDateChangeListener() {
			 
             public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                 date1 = year + "-" + (month+1) + "-" + dayOfMonth;
                
              
        		 taskdeadlineDate = TimeData.convertDate_YYYYMtoYYMM(date1);
        		
        		  String a = TaskData.TdDB.TABLE_NAME_TaskMain;
        	      String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
        	      TaskData.cursor_calendartasks =  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=? and "+
        	                 TaskData.TdDB.TASK_DEADLINEDATE+"=?  and "+
        	                 TaskData.TdDB.TASK_USER+"=?  "+
        	                 " order by "+TaskData.TdDB.TASK_DEADLINETIMEDATA+" asc",
        	                 new String[]{"open",taskdeadlineDate,TaskData.user});
        	       
        	           
        	       TaskData.adapter_calendartasks = new mcAdapter_taskscalendar(getActivity(),R.layout.lvitem_calendartasks,TaskData.cursor_calendartasks);
				   lv_calendartasks.setAdapter(TaskData.adapter_calendartasks);
       
        		
             }
         });*/

		 lv_calendartasks.setOnItemClickListener(new OnItemClickListener(){
	             int newPos=0;
	             int oldPos=0;
	             View oldview;
	             View newview;
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					//Log.d("click position ", "item "+String.valueOf(id));
	                 //Log.d("click and move to", String.valueOf(TaskData.cursor_calendartasks.getPosition()));
	                 //view.setBackgroundColor(Color.RED);
					 try{ 
	            		 TaskData.selTaskID=String.valueOf(TaskData.cursor_calendartasks.getString(TaskData.cursor_calendartasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
	            		 TaskData.selTaskSN=String.valueOf(TaskData.cursor_calendartasks.getString(TaskData.cursor_calendartasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
						 //new TaskUtils(getActivity(),"c1").showPopupWindow(getActivity(),view);
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

		 lv_calendartasks.setOnDeleteListener(new mListView.OnDeleteListener() {
			 long oldClickTime=0;
			 @Override
			 public void onDelete(int index) {
				 //Log.d("mlistview delete", "del"+index);
				 long task_id =  lv_calendartasks.getAdapter().getItemId(index);
				 TaskData.selTaskID=String.valueOf(task_id);
				 TaskTool.quickCancel(getActivity());
				 TaskData.adapter_calendartasks.getCursor().requery();
				 TaskData.adapter_calendartasks.notifyDataSetChanged();
				 //Toast.makeText(getActivity(),"ÈÎÎñ"+task_id+"É¾³ý³É¹¦",Toast.LENGTH_SHORT).show();
			 }

			 @Override
			 public void onDoubleChick(int index) {
				 if (oldClickTime==0){
					 oldClickTime=System.currentTimeMillis();
				 }
				 if ((System.currentTimeMillis()-oldClickTime)>1000) {
					 long task_id = lv_calendartasks.getAdapter().getItemId(index);
					 TaskData.selTaskID = String.valueOf(task_id);
					 if (TaskData.selTaskID!=null&& Integer.parseInt(TaskData.selTaskID)>0){
						 DialogFragment_TaskDetail dg_taskdetail = new DialogFragment_TaskDetail();
						 TaskTool.showDialog(dg_taskdetail);
					 }
					 oldClickTime=System.currentTimeMillis();
				 }
			 }

			 @Override
			 public void onShow(int index) {
                 /*
				 long task_id =  lv_calendartasks.getAdapter().getItemId(index);
				 TaskData.selTaskID=String.valueOf(task_id);
				 DialogFragment_TaskDetail dg_taskdetail=new DialogFragment_TaskDetail();
				 TaskTool.showDialog(dg_taskdetail);
				 */
			 }

			 @Override
			 public void onClose(int index) {
				 /*
				 long task_id =  lv_calendartasks.getAdapter().getItemId(index);
				 TaskData.selTaskID=String.valueOf(task_id);
				 TaskTool.quickclose(getActivity());
				 //Toast.makeText(getActivity(),"ÈÎÎñ"+task_id+"¿ìËÙ½áÊø",Toast.LENGTH_SHORT).show();
                */
			 }
		 });


		 final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_ly);
		 swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
				 android.R.color.holo_orange_light, android.R.color.holo_red_light);

		 swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			 @Override
			 public void onRefresh() {
				 if ( TaskData.adapter_calendartasks !=null&&  TaskData.adapter_calendartasks.getCursor().getCount()>0) {
					 TaskData.adapter_calendartasks.notifyDataSetChanged();
					 //Toast.makeText(getActivity(),"loading",Toast.LENGTH_SHORT).show();
					 swipeRefreshLayout.setRefreshing(false);
				 }else{
					 swipeRefreshLayout.setRefreshing(false);
				 }
			 }
		 });

		 lv_calendartasks.setOnScrollListener(new ListView.OnScrollListener() {

			 @Override
			 public void onScrollStateChanged(AbsListView view, int scrollState) {
				 if (scrollState ==view.SCROLL_INDICATOR_BOTTOM) {
					 swipeRefreshLayout.setRefreshing(false);
					 Toast.makeText(getActivity(),"ÒÑµ½×îºóÒ³",Toast.LENGTH_SHORT).show();
				 }
				 if (scrollState ==view.SCROLL_INDICATOR_TOP) {
					 swipeRefreshLayout.setRefreshing(false);
					 // ï¿½Ë´ï¿½ï¿½ï¿½ï¿½ï¿½Êµï¿½ï¿½Ä¿ï¿½Ð£ï¿½ï¿½ë»»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ý´ï¿½ï¿½ë£¬sendRequest .....
					 //Toast.makeText(getActivity(),"ÒÑµ½×îÉÏÒ³",Toast.LENGTH_SHORT).show();
				 }
			 }

			 @Override
			 public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				 //lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
			 }

		 });

		return v;
	 
	 }

	private void setOnlistener() {
		iv_left.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TaskData.calendarView .onLeftClick();
				showDayTasks();
				showDaysWithThings();

			}
		});

		iv_right.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TaskData.calendarView .onRightClick();
				showDayTasks();
				showDaysWithThings();
			}
		});

		tv_today.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TaskData.calendarView .setTodayToView();
			}
		});
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
	void showDayTasks(){
		day= TaskData.calendarView .getmSelDay();
		month= TaskData.calendarView .getmSelMonth();
		year= TaskData.calendarView .getmSelYear();
		date1 = year + "-" + (month+1) + "-" + (day);
		LogUtils.d(date1);
		taskdeadlineDate = TimeData.convertDate_YYYYMtoYYMM(date1);

		TaskData.cursor_calendartasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=? and "+
						TaskData.TdDB.TASK_DEADLINEDATE+"=?  and "+
						TaskData.TdDB.TASK_USER+"=?  "+
						" order by "+TaskData.TdDB.TASK_DEADLINETIMEDATA+" asc",
				new String[]{"open",taskdeadlineDate,TaskData.user});

		LogUtils.d(""+ TaskData.cursor_calendartasks.getCount());
		TaskData.adapter_calendartasks = new mcAdapter_taskscalendar(getActivity(),R.layout.lvitem_calendartasks,TaskData.cursor_calendartasks);
		lv_calendartasks.setAdapter(TaskData.adapter_calendartasks);
		LogUtils.d("calendartasks setadapter");
	}

	void initShowDaysWithThings(){
		//day=calendarView.getmSelDay();

		final Calendar cal=Calendar.getInstance();
		year=cal.get(Calendar.YEAR);
		month=cal.get(Calendar.MONTH);
		day=cal.get(Calendar.DAY_OF_MONTH);

		date1 = year + "-" + (month+1) + "-" + (day);

		LogUtils.d("init"+year+"-"+month);
		MinMonthDate= year + "-" + (month+1) + "-" + "1";
		MaxMonthDate= year + "-" + (month+1) + "-" + DateUtils.getMonthDays(year, month);
		//LogUtils.d("init"+MinMonthDate+"-"+MaxMonthDate);
		String minDate= TimeData.convertDate_YYYYMtoYYMM(MinMonthDate)+" 00:00";
		String maxDate= TimeData.convertDate_YYYYMtoYYMM(MaxMonthDate)+" 23:59";
		long minDateData=TimeData.changeStrToTime_YY(minDate);
		long maxDateData=TimeData.changeStrToTime_YY(maxDate);
		//LogUtils.d("init"+minDateData+"-"+maxDateData);
		Cursor c = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=? and "+
						TaskData.TdDB.TASK_DEADLINETIMEDATA+">=?  and "+
						TaskData.TdDB.TASK_DEADLINETIMEDATA+"<=?  and "+
						TaskData.TdDB.TASK_USER+"=?  ",
				new String[]{"open",String.valueOf(minDateData),String.valueOf(maxDateData),TaskData.user});
		List<Integer> list = new ArrayList<Integer>();
		String dateHasThings;
		int dayHasThings;
		if (c.getCount()>0){
			c.moveToFirst();
			do{
				dateHasThings=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINEDATE));
				dayHasThings=Integer.parseInt(dateHasThings.substring(dateHasThings.length()-2,dateHasThings.length()));
				list.add(dayHasThings);
				LogUtils.d("init value:"+dayHasThings);
			}while (c.moveToNext());
		}
		LogUtils.d("init size:"+list.size());
		TaskData.calendarView .setDaysHasThingList(list);
		TaskData.calendarView .invalidate();

		taskdeadlineDate = TimeData.convertDate_YYYYMtoYYMM(date1);
		TaskData.cursor_calendartasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=? and "+
						TaskData.TdDB.TASK_DEADLINEDATE+"=?  and "+
						TaskData.TdDB.TASK_USER+"=?  "+
						" order by "+TaskData.TdDB.TASK_DEADLINETIMEDATA+" asc",
				new String[]{"open",taskdeadlineDate,TaskData.user});


		TaskData.adapter_calendartasks = new mcAdapter_taskscalendar(getActivity(),R.layout.lvitem_calendartasks,TaskData.cursor_calendartasks);
		lv_calendartasks.setAdapter(TaskData.adapter_calendartasks);
	}

	 void showDaysWithThings(){
		 //day=calendarView.getmSelDay();
		 month= TaskData.calendarView .getmSelMonth();
		 year= TaskData.calendarView .getmSelYear();
		 MinMonthDate= year + "-" + (month+1) + "-" + "1";
		 MaxMonthDate= year + "-" + (month+1) + "-" + DateUtils.getMonthDays(year, month);
		 String minDate= TimeData.convertDate_YYYYMtoYYMM(MinMonthDate)+" 00:00";
		 String maxDate= TimeData.convertDate_YYYYMtoYYMM(MaxMonthDate)+" 00:00";
		 long minDateData=TimeData.changeStrToTime_YY(minDate);
		 long maxDateData=TimeData.changeStrToTime_YY(maxDate);
		 LogUtils.d(minDateData+"-"+maxDateData);
		 Cursor c = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=? and "+
						 TaskData.TdDB.TASK_DEADLINETIMEDATA+">=?  and "+
						 TaskData.TdDB.TASK_DEADLINETIMEDATA+"<=?  and "+
						 TaskData.TdDB.TASK_USER+"=?  ",
				 new String[]{"open",String.valueOf(minDateData),String.valueOf(maxDateData),TaskData.user});
		 List<Integer> list = new ArrayList<Integer>();
		 String dateHasThings;
		 int dayHasThings;
			 if (c.getCount()>0){
				 c.moveToFirst();
				 do {
					 dateHasThings=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINEDATE));
					 dayHasThings=Integer.parseInt(dateHasThings.substring(dateHasThings.length()-2,dateHasThings.length()));
					 list.add(dayHasThings);
					 //LogUtils.d("value:"+dayHasThings);
				 }while(c.moveToNext());
			 }
		 //LogUtils.d("size:"+list.size());
		 TaskData.calendarView .setDaysHasThingList(list);
		 TaskData.calendarView .invalidate();
	 }

	 protected void showDialog(DialogFragment dialogFragment) {  
         
	        // Create and show the dialog.   
	    if(dialogFragment == null)  
	          //  dialogFragment = new Fragment_Search();  
	    	   dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);  
	           dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0); 
	         dialogFragment.show(getFragmentManager(), "dialog");  
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
