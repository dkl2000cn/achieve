package com.easygoal.achieve;



import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.litepal.crud.DataSupport;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class TaskData {

	public static final String STATUS_OPEN = "open";
	public static final String STATUS_FINISHED = "finished"; 
	public static final String STATUS_CANCELLED = "cancelled"; 
	public static ToDoDB TdDB;
	public static SQLiteDatabase db;
	public static SQLiteDatabase db_TdDB;
	//public static TaskLog tasklog;
	public static String db_TdDBname = "TdDBData";
	
	/*
	public static ListView lv_tasklog;
	public static ListView m_listview; 
	public static ListView lv_opentasks; 
	public static ListView lv_alltasks; 
	public static ListView lv_finishedtasks;
	public static ListView lv_cancelledtasks; 
	public static ListView lv_taskscore; 
	public static ListView lv_taskslog; 
	
    public static List<Reminder> eventlist;
	public static List<Memo> memolist;
	public static List<String> clickedlist;
	*/
	
	public static ListView lv_todaytasks;
	
	public static ArrayList<RecordBean> ls1;
	
	public static List<RecordBean> subItemlist;
	
	public static ArrayList<String> subdellist;
	
	/*
	public static ArrayList<TextView> subItems_idno;
	public static ArrayList<EditText> subItems_comments;
	public static ArrayList<EditText> subItems_deadline;
	public static ArrayList<EditText> subItems_weight;
	public static ArrayList<Button> subItems_del;
	*/
	public static ArrayList<Memo> memolist;
	public static ArrayList<RecordBean> valuelist;
	
	public static List<PendingIntent> alarmpilist;
	public static List<BroadcastReceiver> bcreceiverlist;
	public static List<String> alarmsourceidlist;
	
	public static Set<BroadcastReceiver> alarmReceiverSet;
	
	public static mcAdapter_opentasks adapter_opentasks;
	public static mcAdapter_opentasks adapter_todaytasks;
	public static mcAdapter_tasks_pagedivided adapter_alltasks_pagedivided;
	public static mcAdapter_tasks adapter_finishedtasks;
	public static mcAdapter_tasks adapter_cancelledtasks;
	public static mcAdapter_taskscore mcadapter_taskscore;
	public static mcAdapter_taskrecord mcAdapter_taskrecord;
	public static mcAdapter_taskreview adapter_taskreview;
	public static mcAdapter_taskscalendar adapter_calendartasks;
	public static mcAdapter_taskrun adapter_taskrun;
	public static mRecyAdapter_taskedit adapter_progedit;
	public static mcAdapter_recorddetails adaptRecordDetails;
	public static mcAdapter_group mcAdapter_group;
	public static SimpleCursorAdapter adapter_commentlist;
	public static CountTimeAdapter adapter_reminder;
	public static MemoCursorAdapter adapter_memo;
	
	public static Cursor cursor_opentasks;
	
	public static Cursor cursor_alltasks;
	public static Cursor cursor_finishedtasks;
	public static Cursor cursor_cancelledtasks;
	public static Cursor cursor_todaytasks;
	public static Cursor cursor_calendartasks;
	
	public static Cursor cursor_taskscore;
	public static Cursor cursor_allcomments;
	public static Cursor cursor_taskrecord;
	public static Cursor cursor_taskrun;
	public static Cursor cursor_taskreview;
	public static Cursor cursor_taskloglist;

	public static Cursor cursor_reminder;
	public static Cursor cursor_memo;
	public static Cursor cursor_rating;
	public static Cursor cursor_sequenceno;
	
	public static Cursor cursor_group;
	
	public static Cursor cursor_alltaskscount_today;
	public static Cursor cursor_finishedtaskscount_today;
	
	public static Cursor cursor_finishedtaskscount_thismonth;
	public static Cursor cursor_alltaskscount_thismonth;


	public static TextView tv_opentasksheader;
	public static TextView tv_opentaskscount;
	public static TextView tv_alltaskscount;
	public static TextView tv_finishedtaskscount;
	public static TextView tv_scoreheader_achieved;
	public static TextView tv_scoreheader_enjoyment;
	public static TextView tv_scoreheader_experience;
	public static TextView tv_scoreheader_accomplished;
	public static TextView tv_cancelledtasksheader;
	public static TextView tv_finishedtasksheader;
	public static TextView tv_todaytaskscount;
	public static TextView tv_cancelledtaskscount;
	
	public static TextView tv_opentaskscount_t;
	public static TextView tv_todaytaskscount_t;
	public static TextView tv_overduetaskscount_t;
	public static TextView tv_opentaskscount_a;
	public static TextView tv_todaytaskscount_a;
	public static TextView tv_overduetaskscount_a;
	public static TextView tv_opentaskscount_c;
	public static TextView tv_todaytaskscount_c;
	public static TextView tv_overduetaskscount_c;
	public static TextView tv_opentaskscount_b;
	public static TextView tv_todaytaskscount_b;
	public static TextView tv_overduetaskscount_b;
	public static TextView tv_opentaskscount_d;
	public static TextView tv_todaytaskscount_d;
	public static TextView tv_overduetaskscount_d;
	
	public static TextView tv_totalimportance;
	public static TextView tv_totalurgency;
	public static TextView tv_totaltimely;
	public static TextView tv_totalresultsatisfaction;
	public static TextView tv_totalachieved;
	public static TextView tv_totalexperience;
	public static TextView tv_totalenjoyment;
	public static TextView tv_totalaccomplished;
	public static TextView tv_totalcontribution;
	
	public static TextView tv_finishedtaskscount_thismonth;
	public static TextView tv_finishedtaskscount_today;
	public static TextView tv_totalresultsatisfaction_today;
	public static TextView tv_totalurgency_today;
	public static TextView tv_totaltimely_today;
	public static TextView tv_totalachieved_today;
	public static TextView tv_totalexperience_today;
	public static TextView tv_totalenjoyment_today;
	public static TextView tv_totalaccomplished_today;
	public static TextView tv_totalcontribution_today;
	
	public static TextView tv_totalimportance_thismonth;
	public static TextView tv_totalresultsatisfaction_thismonth;
	public static TextView tv_totalurgency_thismonth;
	public static TextView tv_totaltimely_thismonth;
	public static TextView tv_totalachieved_thismonth;
	public static TextView tv_totalexperience_thismonth;
	public static TextView tv_totalenjoyment_thismonth;
	public static TextView tv_totalaccomplished_thismonth;
	public static TextView tv_totalcontribution_thismonth;
	
	
	public static int sum_enjoyment;
	public static int sum_achieved;
	public static int sum_experience;
	
    public static Fragment from_fg;
    public static FragmentManager fm;
    
    /*
	public static Fragment[] subfrag_task;
	public static Fragment[] frag;
	
	public static Fragment fragment_home;
	public static Fragment fragment_todaytasks;
	public static Fragment fragment_tasktab;
	public static Fragment fragment_taskscore;
	public static Fragment fragment_taskrecordtab;
	
	public static Fragment fragment_tasklog;
	public static Fragment fragment_taskrecord ;
	public static Fragment fragment_taskreview ;
	
	public static Fragment fragment_alltasks ;
	public static Fragment fragment_opentasks ;
	public static Fragment fragment_finishedtasks ;
	public static Fragment fragment_cancelledtasks;
	*/
    public static int overduetextsize=22;
    public static int overduetextcolor;
    
	public static int performanceflag=0;
	public static int updateflag=0;
	public static int initflag=2;
	public static int initteamflag=2;
	public static String selTaskID;
	public static String selTaskSN;


	public static Reminder reminder;   
	
	public static String loginstatus;
	public static String onlinestatus="";
	
	public static int weekhoursum;
	public static int usermodel;
	public static int preweight ;
	public static int dataperiod;
	public static int hours_eachday=8;
	public static int prehour;
	protected static int valuelistflag;

	public static int privilege ;
	public static String user="";
	public static String hostname;
	public static String userResponseJsonArrayStr;

	public static int initreminderflag;
	
	public static TextView tv_totalweight;
	public static TextView tv_progress;
	public static TextView tv_status;
	
	public static double totalweight;
	
	public static Map map_recordcommentnews;
	static String Tags="TaskData";
	public static String tag_taskhistory_phone="";
	public static String tag_taskhistory_time="";
	public static String tag_taskhistory="";
	public static String tag_tasklesson="";
	public static boolean alarmclock;
	public static boolean vibration;
	public static boolean notification;
	public static boolean alarmring;
	public static int alarmfreq;
	public static int alarminterval;
	public static int alarmadvance;

	public static int endhour;
	public static int endmin;
	protected static boolean durationcheck;
	public static int entryright=0;
	public static int vipentryright=2;
	public static int profileUpdateflag=1;
	public static long t_start;
 
	public final static String t_tags="lapsed time";
 
	public final static String icon_header="·";
	public final static int size_header=32;
	public static int color_header=0;
	public final static int style_header=Typeface.ITALIC;
	public static mcAdapter_taskrecord mcAdapter_taskloglist;
	public static String usergroup;
	public static Cursor cursor_teamscore;
	public static Cursor cursor_finishedteamtasks;
	public static TextView tv_scoreheader_teamaccomplished;
	public static TextView tv_scoreheader_teamachieved;
	public static TextView tv_scoreheader_teamenjoyment;
	public static TextView tv_scoreheader_teamexperience;
	public static mcAdapter_taskscore mcadapter_teamscore;
	public static Cursor cursor_teamtaskreview;
	public static mcAdapter_taskreview adapter_teamtaskreview;
	public static Cursor cursor_teamtaskrun;
	public static mcAdapter_taskrun adapter_teamtaskrun;
	public static int teamperformanceflag=0;
	public static TextView tv_teamalltaskscount;
	public static Cursor cursor_teamalltasks;
	public static mcAdapter_tasks_pagedivided adapter_teamalltasks_pagedivided;
	public static TextView tv_teamcancelledtaskscount;
	public static Cursor cursor_teamcancelledtasks;
	public static mcAdapter_tasks adapter_teamcancelledtasks;
	public static TextView tv_teamfinishedtaskscount;
	public static Cursor cursor_teamfinishedtasks;
	public static mcAdapter_tasks adapter_teamfinishedtasks;
	public static TextView tv_teamopentaskscount;
	public static Cursor cursor_teamopentasks;
	public static mcAdapter_tasks adapter_teamopentasks;
	public static String selTeamTaskID;
	public static String selTeamTaskSN;
	public static Cursor cursor_team;
    public static Cursor cursor_usergroup;
	public static String usernickname;
    public static JSONArray usergroupArray;
	public static List<UserBean> list_usergroup;
	public static List<UserBean> userlist_usergroup;
	public static List<Map> userinfolist_usergroup;
	public static mcAdapter_tasks adapter_alltasks;
	public static mcAdapter_tasks adapter_teamalltasks;
    public static TextView tv_scoreheader_teamcontribution;
    public static String selTaskOwner;

    //public static FragmentManager fm1;
	public static int flag_findteam;
	public static Cursor cursor_todaytasks_t;
	public static Cursor cursor_todaytasks_a;
	public static Cursor cursor_todaytasks_c;
	public static Cursor cursor_todaytasks_b;
	public static Cursor cursor_todaytasks_d;
	public static Cursor cursor_overduetasks_t;
	public static Cursor cursor_overduetasks_a;
	public static Cursor cursor_overduetasks_c;
	public static Cursor cursor_overduetasks_b;
	public static Cursor cursor_overduetasks_d;
	public static Cursor cursor_taskrec;
	public static TextView tv_teamtotalaccomplished;
	public static TextView tv_teamtotalcontribution;
	public static TextView tv_teamtotaltimely;
	public static TextView tv_teamtotalresultsatisfaction;
	public static TextView tv_teamtotalachieved;
	public static TextView tv_teamtotalexperience;
	public static TextView tv_teamtotalenjoyment;
	public static Cursor teamcursor_finishedtasks;
	public static Cursor teamcursor_alltasks;
	public static TextView tv_teamfinishedtaskscount_thismonth;
	public static TextView tv_teamtotalresultsatisfaction_thismonth;
	public static TextView tv_teamtotalaccomplished_thismonth;
	public static TextView tv_teamtotalcontribution_thismonth;
	public static TextView tv_teamtotaltimely_thismonth;
	public static TextView tv_teamtotalachieved_thismonth;
	public static TextView tv_teamtotalexperience_thismonth;
	public static TextView tv_teamtotalenjoyment_thismonth;
	public static Cursor cursor_teamfinishedtaskscount_thismonth;
	public static Cursor cursor_teamalltaskscount_thismonth;
	public static TextView tv_teamfinishedtaskscount_today;
	public static TextView tv_teamtotalresultsatisfaction_today;
	public static TextView tv_teamtotalaccomplished_today;
	public static TextView tv_teamtotalcontribution_today;
	public static TextView tv_teamtotaltimely_today;
	public static TextView tv_teamtotalachieved_today;
	public static TextView tv_teamtotalexperience_today;
	public static TextView tv_teamtotalenjoyment_today;
	public static Cursor cursor_teamfinishedtaskscount_today;
	public static Cursor cursor_teamalltaskscount_today;
    public static TextView tv_totaltaskcount;
	public static TextView tv_totalclosedrate;
	public static TextView tv_totaltaskscount_thismonth;
	public static TextView tv_totalclosedrate_thismonth;
	public static TextView tv_totaltaskscount_today;
	public static TextView tv_totalclosedrate_today;
	public static TextView tv_teamtotaltaskcount;
	public static TextView tv_teamtotalclosedrate;
	public static TextView tv_teamtotaltaskcount_thismonth;
	public static TextView tv_teamtotalclosedrate_thismonth;
	public static TextView tv_teamtotaltaskcount_today;
	public static TextView tv_teamtotalclosedrate_today;
    public static String userRongtoken;
	public static boolean connRongflag;
    public static String selMemberID;
	public static String selMemberName;
    public static String headpicuri;
	public static String username;
    public static String latestVersionName;

    public static void adapterInit(){
		//cursor_opentasks= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{"open"});
		//cursor_todaytasks= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{"open"});

		cursor_opentasks= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=?"+" and "+TaskData.TdDB.TASK_STATUS+"=?"+" order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", new String[]{TaskData.user,"open"});
		Log.d(Tags,"opentasks cursor "+String.valueOf(cursor_opentasks.getCount())+" position"+String.valueOf(cursor_opentasks.getColumnCount()));
		cursor_alltasks=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
		Log.d(Tags,"alltasks cursor"+String.valueOf(cursor_alltasks.getCount())+" position"+String.valueOf(cursor_alltasks.getPosition()));
		cursor_finishedtasks= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=?"+" and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{TaskData.user,"finished"});
		Log.d(Tags,"finishedtasks cursor"+String.valueOf(cursor_finishedtasks.getCount())+" position"+String.valueOf(cursor_finishedtasks.getColumnCount()));
		cursor_taskreview= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=?"+"and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{TaskData.user,"finished"});
		Log.d(Tags,"taskreview cursor"+String.valueOf(cursor_taskreview.getCount())+" position"+String.valueOf(cursor_taskreview.getColumnCount()));
		cursor_cancelledtasks=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=?"+" and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{TaskData.user,"cancelled"});
		Log.d(Tags,"cancelledtasks cursor"+String.valueOf(cursor_cancelledtasks.getCount())+" position"+String.valueOf(cursor_cancelledtasks.getColumnCount()));
		
		//TaskData.cursor_reminder=DataSupport.findBySQL("select  id as _id,name,sourceId,deadlinetime,frequency,advance,alarminterval,piaction,sn,content from reminder"+" where "+"username"+"="+"'"+TaskData.user+"';");;
		//Log.d(Tags,"reminder cursor"+String.valueOf(cursor_reminder.getCount())+" position"+String.valueOf(cursor_reminder.getColumnCount()));
	
		//cursor_memo=DataSupport.findBySQL("select id as _id,name,picUriStr,fileUriStr,content,createdtime,sn from memo"+" where "+"username"+"="+"'"+TaskData.user+"';");
		cursor_taskrec = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord + " where " + TaskData.TdDB.TASK_USER + "=?", new String[]{TaskData.user});
		cursor_rating = DataSupport.findBySQL("select * from userratingbean where username="+"'"+TaskData.user+"'");
		cursor_reminder = DataSupport.findBySQL("select * from reminder where username="+"'"+TaskData.user+"'");
		cursor_memo= DataSupport.findBySQL("select * from memo where username="+"'"+TaskData.user+"'");
		Log.d(Tags,"memo cursor"+String.valueOf(cursor_memo.getCount())+" position"+String.valueOf(cursor_memo.getPosition()));
		cursor_taskrecord=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=?"+" and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{TaskData.user,"open"});
		Log.d(Tags,"taskrecord cursor"+String.valueOf(cursor_taskrecord.getCount())+" position"+String.valueOf(cursor_taskrecord.getColumnCount()));
		cursor_taskrun= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?"+" and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{TaskData.user,"open"});
		Log.d(Tags,"taskruncursor"+String.valueOf(cursor_taskrun.getCount())+" position"+String.valueOf(cursor_taskrun.getColumnCount()));
		cursor_taskscore=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=? and "+TaskData.TdDB.TASK_STATUS+"!=?", new String[]{TaskData.user,"cancelled"});   
		Log.d(Tags,"taskscore cursor"+String.valueOf(cursor_taskscore.getCount())+" position"+String.valueOf(cursor_taskscore.getColumnCount()));		
		
		cursor_todaytasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
                 TaskData.TdDB.TASK_DEADLINETIMEDATA+"<=?"+
                 " order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", 
                 new String[]{TaskData.user,"open",
         		  String.valueOf(TimeData.todayEndTimeData())});
		Log.d(Tags,"todday tasks"+String.valueOf(cursor_todaytasks.getCount())+" position"+String.valueOf(TimeData.todayEndTimeData()));		
		
		cursor_sequenceno= TaskData.db_TdDB.rawQuery("select  *  from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                  TaskData.TdDB.TASK_STATUS+"='open'"+" and "+
                  TaskData.TdDB.TASK_USER+"='"+TaskData.user+"'"+
                  " order by "+TaskData.TdDB.TASK_SEQUENCE,null);
		
		 final Calendar cal=Calendar.getInstance();
  	     int year=cal.get(Calendar.YEAR);
         int month=cal.get(Calendar.MONTH);
         int day=cal.get(Calendar.DAY_OF_MONTH);
       
         String date1 = year + "-" + (month+1) + "-" + (day);
		 
         String taskdeadlineDate = TimeData.convertDate_YYYYMtoYYMM(date1);
        
		
	     TaskData.cursor_calendartasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=? and "+
               TaskData.TdDB.TASK_DEADLINEDATE+"=?  and "+
               TaskData.TdDB.TASK_USER+"=?  "+
               " order by "+TaskData.TdDB.TASK_DEADLINETIMEDATA+" asc",
               new String[]{"open", taskdeadlineDate,TaskData.user});

		TaskData.cursor_group=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
				TaskData.TdDB.TASK_USERGROUP+"="+"'"+TaskData.usergroup+"'"+" and "+
				TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"'", null);
	      
		TaskData.selTaskID=null;
		TaskData.selTaskSN=null;
	   
	    //TaskData.clickposition=null;

	}

	public static void showTeamPerformance(){
		if (TaskData.teamperformanceflag==1){
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(1);
			TaskData.cursor_finishedtasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
							TaskData.TdDB.TASK_STATUS+"=? and "+
							TaskData.TdDB.TASK_USERGROUP+"=?",
					new String[]{"finished",TaskData.usergroup});

			TaskData.cursor_alltasks =TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USERGROUP+"=?", new String[]{TaskData.usergroup});
			TaskData.tv_finishedtaskscount.setText(String.valueOf(TaskData.cursor_finishedtasks.getCount()));
			TaskData.tv_totaltaskcount.setText(String.valueOf(TaskData.cursor_alltasks.getCount()));
			double totalcloserate = (double) TaskData.cursor_finishedtasks.getCount() / TaskData.cursor_alltasks.getCount();
			Log.d(Tags,"totalcloserate"+totalcloserate);
			TaskData.tv_totalclosedrate.setText(nt.format(totalcloserate));
			TaskData.update_avg(TaskData.tv_totalresultsatisfaction, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_RESULTSATISFICATION);
			TaskData.update_sum(TaskData.tv_totalaccomplished, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_IMPORTANCE);
			TaskData.update_sum(TaskData.tv_totalcontribution, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_ASSESSMENT);
			TaskData.update_avgRev(TaskData.tv_totaltimely, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_DELAYED);

			TaskData.update_sum(TaskData.tv_totalachieved, TaskData.cursor_alltasks,TaskData.TdDB.SUM_ACHIEVED);
			TaskData.update_sum(TaskData.tv_totalexperience, TaskData.cursor_alltasks,TaskData.TdDB.SUM_EXPERIENCE);
			TaskData.update_sum(TaskData.tv_totalenjoyment, TaskData.cursor_alltasks,TaskData.TdDB.SUM_ENJOYMENT);

			final Calendar cal=Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day=cal.get(Calendar.DAY_OF_MONTH);


			String str_thismonth = year + "-" + (month+1) + "-" + 1+" "+"00"+":"+"00";

			long thismonthTimeData = TimeData.changeStrToTime_YYYY(str_thismonth);

			TaskData.cursor_finishedtaskscount_thismonth = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+
							" where "+TaskData.TdDB.TASK_STATUS+ "=? and "+
							TaskData.TdDB.TASK_USERGROUP+" =? and "+
							TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
					new String[]{"finished",TaskData.usergroup,String.valueOf(thismonthTimeData)});
			TaskData.cursor_alltaskscount_thismonth = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+
							" where "+TaskData.TdDB.TASK_USERGROUP+" =? and "+TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
					new String[]{TaskData.usergroup,String.valueOf(thismonthTimeData)});
			TaskData.tv_finishedtaskscount_thismonth.setText(String.valueOf(TaskData.cursor_finishedtaskscount_thismonth.getCount()));
			TaskData.tv_totaltaskscount_thismonth.setText(String.valueOf(TaskData.cursor_alltaskscount_thismonth.getCount()));
			double totalcloserate_thismonth = (double) TaskData.cursor_finishedtaskscount_thismonth.getCount()/TaskData.cursor_alltaskscount_thismonth.getCount();
			Log.d(Tags,"totalcloserate_thismonth "+totalcloserate_thismonth);
			TaskData.tv_totalclosedrate_thismonth.setText(nt.format(totalcloserate_thismonth));
			TaskData.update_avg(TaskData.tv_totalresultsatisfaction_thismonth, TaskData.cursor_finishedtaskscount_thismonth,TaskData.TdDB.TASK_RESULTSATISFICATION);
			TaskData.update_sum(TaskData.tv_totalaccomplished_thismonth, TaskData.cursor_finishedtaskscount_thismonth,TaskData.TdDB.TASK_IMPORTANCE);
			TaskData.update_sum(TaskData.tv_totalcontribution_thismonth, TaskData.cursor_finishedtaskscount_thismonth,TaskData.TdDB.TASK_ASSESSMENT);
			TaskData.update_avgRev(TaskData.tv_totaltimely_thismonth, TaskData.cursor_finishedtaskscount_thismonth,TaskData.TdDB.TASK_DELAYED);
			//TaskData.update_sum(TaskData.tv_totalurgency_thismonth, TaskData.cursor_finishedtaskscount_thismonth,TaskData.TdDB.TASK_URGENCY);
			TaskData.update_sum(TaskData.tv_totalachieved_thismonth, TaskData.cursor_alltaskscount_thismonth,TaskData.TdDB.SUM_ACHIEVED);
			TaskData.update_sum(TaskData.tv_totalexperience_thismonth, TaskData.cursor_alltaskscount_thismonth,TaskData.TdDB.SUM_EXPERIENCE);
			TaskData.update_sum(TaskData.tv_totalenjoyment_thismonth, TaskData.cursor_alltaskscount_thismonth,TaskData.TdDB.SUM_ENJOYMENT);

			String str_today = year + "-" + (month+1) + "-" + day+" "+"00"+":"+"00";

			long todayTimeData = TimeData.changeStrToTime_YYYY(str_today);

			TaskData.cursor_finishedtaskscount_today = TaskData.db_TdDB.rawQuery("select * from "+
							TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
							TaskData.TdDB.TASK_STATUS+"=? and "+
							TaskData.TdDB.TASK_USERGROUP+" =? and "+
							TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
					new String[]{"finished",TaskData.usergroup,String.valueOf(todayTimeData)});
			TaskData.cursor_alltaskscount_today = TaskData.db_TdDB.rawQuery("select * from "+
							TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
							TaskData.TdDB.TASK_USERGROUP+" =? and "+
							TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
					new String[]{TaskData.usergroup,String.valueOf(todayTimeData)});
			TaskData.tv_finishedtaskscount_today.setText(String.valueOf(TaskData.cursor_finishedtaskscount_today.getCount()));
			TaskData.tv_totaltaskscount_today.setText(String.valueOf(TaskData.cursor_alltaskscount_today.getCount()));
			double totalcloserate_today= (double) TaskData.cursor_finishedtaskscount_today.getCount()/ TaskData.cursor_alltaskscount_today.getCount();
			Log.d(Tags,"totalcloserate_today "+totalcloserate_today);
			TaskData.tv_totalclosedrate_today.setText(nt.format(totalcloserate_today));
			TaskData.update_avg(TaskData.tv_totalresultsatisfaction_today, TaskData.cursor_finishedtaskscount_today,TaskData.TdDB.TASK_RESULTSATISFICATION);
			TaskData.update_sum(TaskData.tv_totalaccomplished_today, TaskData.cursor_finishedtaskscount_today,TaskData.TdDB.TASK_IMPORTANCE);
			TaskData.update_sum(TaskData.tv_totalcontribution_today, TaskData.cursor_finishedtaskscount_today,TaskData.TdDB.TASK_ASSESSMENT);
			TaskData.update_avgRev(TaskData.tv_totaltimely_today, TaskData.cursor_finishedtaskscount_today,TaskData.TdDB.TASK_DELAYED);
			// TaskData.update_sum(TaskData.tv_totalurgency_today, TaskData.cursor_finishedtaskscount_today,TaskData.TdDB.TASK_URGENCY);
			TaskData.update_sum(TaskData.tv_totalachieved_today, TaskData.cursor_alltaskscount_today,TaskData.TdDB.SUM_ACHIEVED);
			TaskData.update_sum(TaskData.tv_totalexperience_today, TaskData.cursor_alltaskscount_today,TaskData.TdDB.SUM_EXPERIENCE);
			TaskData.update_sum(TaskData.tv_totalenjoyment_today, TaskData.cursor_alltaskscount_today,TaskData.TdDB.SUM_ENJOYMENT);
			TaskData.teamperformanceflag=1;
		}else{

		}
	}


	 public static void showPerformance(){
		 		
	     if (TaskData.performanceflag==1){
	    	 
			  TaskData.cursor_finishedtasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
			  TaskData.TdDB.TASK_STATUS+"=? and "+
			   TaskData.TdDB.TASK_USER+" =?  ", new String[]{"finished",TaskData.user});
			  TaskData.tv_finishedtaskscount.setText(String.valueOf(TaskData.cursor_finishedtasks.getCount()));
			  TaskData.update_avg(TaskData.tv_totalresultsatisfaction, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_RESULTSATISFICATION);
			  TaskData.update_sum(TaskData.tv_totalaccomplished, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_IMPORTANCE);
			  TaskData.update_sum(TaskData.tv_totalcontribution, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_ASSESSMENT);
			  TaskData.update_avgRev(TaskData.tv_totaltimely, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_DELAYED);
			  //TaskData.update_sum(TaskData.tv_totalurgency, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_URGENCY);
			  TaskData.update_sum(TaskData.tv_totalachieved, TaskData.cursor_alltasks,TaskData.TdDB.SUM_ACHIEVED);
			  TaskData.update_sum(TaskData.tv_totalexperience, TaskData.cursor_alltasks,TaskData.TdDB.SUM_EXPERIENCE);
			  TaskData.update_sum(TaskData.tv_totalenjoyment, TaskData.cursor_alltasks,TaskData.TdDB.SUM_ENJOYMENT);
			  
			    final Calendar cal=Calendar.getInstance();
		    	   int year = cal.get(Calendar.YEAR);
	              int month = cal.get(Calendar.MONTH);
	              int day=cal.get(Calendar.DAY_OF_MONTH);
	              
	              
	             String str_thismonth = year + "-" + (month+1) + "-" + 1+" "+"00"+":"+"00";
	           
	             long thismonthTimeData = TimeData.changeStrToTime_YYYY(str_thismonth);
	         
			  TaskData.cursor_finishedtaskscount_thismonth = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
			                                         TaskData.TdDB.TASK_STATUS+"=? and "+
			                                         TaskData.TdDB.TASK_USER+" =? and "+
					                                 TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
					                                 new String[]{"finished",TaskData.user,String.valueOf(thismonthTimeData)});
			  TaskData.tv_finishedtaskscount_thismonth.setText(String.valueOf(TaskData.cursor_finishedtaskscount_thismonth.getCount()));
			  TaskData.update_avg(TaskData.tv_totalresultsatisfaction_thismonth, TaskData.cursor_finishedtaskscount_thismonth,TaskData.TdDB.TASK_RESULTSATISFICATION);
			  TaskData.update_sum(TaskData.tv_totalaccomplished_thismonth, TaskData.cursor_finishedtaskscount_thismonth,TaskData.TdDB.TASK_IMPORTANCE);
			  TaskData.update_sum(TaskData.tv_totalcontribution_thismonth, TaskData.cursor_finishedtaskscount_thismonth,TaskData.TdDB.TASK_ASSESSMENT);
			  TaskData.update_avgRev(TaskData.tv_totaltimely_thismonth, TaskData.cursor_finishedtaskscount_thismonth,TaskData.TdDB.TASK_DELAYED);
			  //TaskData.update_sum(TaskData.tv_totalurgency_thismonth, TaskData.cursor_finishedtaskscount_thismonth,TaskData.TdDB.TASK_URGENCY);
			  TaskData.update_sum(TaskData.tv_totalachieved_thismonth, TaskData.cursor_alltaskscount_thismonth,TaskData.TdDB.SUM_ACHIEVED);
			  TaskData.update_sum(TaskData.tv_totalexperience_thismonth, TaskData.cursor_alltaskscount_thismonth,TaskData.TdDB.SUM_EXPERIENCE);
			  TaskData.update_sum(TaskData.tv_totalenjoyment_thismonth, TaskData.cursor_alltaskscount_thismonth,TaskData.TdDB.SUM_ENJOYMENT);
			  
			     String str_today = year + "-" + (month+1) + "-" + day+" "+"00"+":"+"00";
	            
			     long todayTimeData = TimeData.changeStrToTime_YYYY(str_today);
			   
			  TaskData.cursor_finishedtaskscount_today = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
			                      TaskData.TdDB.TASK_STATUS+"=? and "+
			                      TaskData.TdDB.TASK_USER+" =? and "+
					              TaskData.TdDB.TASK_DEADLINETIMEDATA+">?", 
					              new String[]{"finished",TaskData.user,String.valueOf(todayTimeData)});
			  TaskData.tv_finishedtaskscount_today.setText(String.valueOf(TaskData.cursor_finishedtaskscount_today.getCount()));
			  TaskData.update_avg(TaskData.tv_totalresultsatisfaction_today, TaskData.cursor_finishedtaskscount_today,TaskData.TdDB.TASK_RESULTSATISFICATION);
			  TaskData.update_sum(TaskData.tv_totalaccomplished_today, TaskData.cursor_finishedtaskscount_today,TaskData.TdDB.TASK_IMPORTANCE);
			  TaskData.update_sum(TaskData.tv_totalcontribution_today, TaskData.cursor_finishedtaskscount_today,TaskData.TdDB.TASK_ASSESSMENT);
			  TaskData.update_avgRev(TaskData.tv_totaltimely_today, TaskData.cursor_finishedtaskscount_today,TaskData.TdDB.TASK_DELAYED);
			  //TaskData.update_sum(TaskData.tv_totalurgency_today, TaskData.cursor_finishedtaskscount_today,TaskData.TdDB.TASK_URGENCY);
			  TaskData.update_sum(TaskData.tv_totalachieved_today, TaskData.cursor_alltaskscount_today,TaskData.TdDB.SUM_ACHIEVED);
			  TaskData.update_sum(TaskData.tv_totalexperience_today, TaskData.cursor_alltaskscount_today,TaskData.TdDB.SUM_EXPERIENCE);
			  TaskData.update_sum(TaskData.tv_totalenjoyment_today, TaskData.cursor_alltaskscount_today,TaskData.TdDB.SUM_ENJOYMENT);
			 
			  TaskData.performanceflag=1;
	   }else{
		   
	   }
		 
	 }


	public static void homepage_countupdate(){
		try {
			int opentaskscount_t = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " + TaskData.TdDB.TASK_USER + "=?" + " order by " + TaskData.TdDB.TASK_SEQUENCENO, new String[]{"open", TaskData.user}).getCount();
			TaskData.tv_opentaskscount_t.setText(String.valueOf(opentaskscount_t));
			int opentaskscount_s = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " + TaskData.TdDB.TASK_USER + "=? and " + TaskData.TdDB.TASK_IMPORTANCE + ">=? and " + TaskData.TdDB.TASK_URGENCY + ">=?", new String[]{"open", TaskData.user, "2", "2"}).getCount();
			TaskData.tv_opentaskscount_a.setText(String.valueOf(opentaskscount_s));
			int opentaskscount_a = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " + TaskData.TdDB.TASK_USER + "=? and " + TaskData.TdDB.TASK_IMPORTANCE + ">=? and " + TaskData.TdDB.TASK_URGENCY + "<?", new String[]{"open", TaskData.user, "2", "2"}).getCount();
			TaskData.tv_opentaskscount_c.setText(String.valueOf(opentaskscount_a));
			int opentaskscount_u = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " + TaskData.TdDB.TASK_USER + "=? and " + TaskData.TdDB.TASK_IMPORTANCE + "<? and " + TaskData.TdDB.TASK_URGENCY + ">=?", new String[]{"open", TaskData.user, "2", "2"}).getCount();
			TaskData.tv_opentaskscount_b.setText(String.valueOf(opentaskscount_u));
			int opentaskscount_n = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " + TaskData.TdDB.TASK_USER + "=? and " + TaskData.TdDB.TASK_IMPORTANCE + "<? and " + TaskData.TdDB.TASK_URGENCY + "<?", new String[]{"open", TaskData.user, "2", "2"}).getCount();
			TaskData.tv_opentaskscount_d.setText(String.valueOf(opentaskscount_n));

			//java.util.Date currentdate = new java.util.Date();
			//String todaydate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			//Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG);
			//Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG);

			cursor_todaytasks_t = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_DEADLINEDATE + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + ">=?" +
							" order by " + TaskData.TdDB.TASK_SEQUENCENO + " asc",
					new String[]{"open", TaskData.user,
							new SimpleDateFormat("yy-MM-dd").format(new Date()),
							String.valueOf(new Date(System.currentTimeMillis()).getTime() / (1000 * 60))});
			int todaytaskscount_t = cursor_todaytasks_t.getCount();
			TaskData.tv_todaytaskscount_t.setText(String.valueOf(todaytaskscount_t));
			cursor_todaytasks_a = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_DEADLINEDATE + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + ">=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + ">=? and " +
							TaskData.TdDB.TASK_URGENCY + ">=? ",
					new String[]{"open", TaskData.user,
							new SimpleDateFormat("yy-MM-dd").format(new Date()),
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int todaytaskscount_a = cursor_todaytasks_a.getCount();
			TaskData.tv_todaytaskscount_a.setText(String.valueOf(todaytaskscount_a));
			cursor_todaytasks_c = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINEDATE + "=? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + ">=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + ">=? and " +
							TaskData.TdDB.TASK_URGENCY + "<? ",
					new String[]{"open", TaskData.user,
							new SimpleDateFormat("yy-MM-dd").format(new Date()),
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int todaytaskscount_c = cursor_todaytasks_c.getCount();
			TaskData.tv_todaytaskscount_c.setText(String.valueOf(todaytaskscount_c));
			cursor_todaytasks_b = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINEDATE + "=? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + ">=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + "<? and " +
							TaskData.TdDB.TASK_URGENCY + ">=? ",
					new String[]{"open", TaskData.user,
							new SimpleDateFormat("yy-MM-dd").format(new Date()),
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int todaytaskscount_b = cursor_todaytasks_b.getCount();
			TaskData.tv_todaytaskscount_b.setText(String.valueOf(todaytaskscount_b));
			cursor_todaytasks_d = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINEDATE + "=? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + ">=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + "<? and " +
							TaskData.TdDB.TASK_URGENCY + "<? ",
					new String[]{"open", TaskData.user,
							new SimpleDateFormat("yy-MM-dd").format(new Date()),
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int todaytaskscount_d = cursor_todaytasks_d.getCount();
			TaskData.tv_todaytaskscount_d.setText(String.valueOf(todaytaskscount_d));

			cursor_overduetasks_t = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + "<? "
					, new String[]{TaskData.user, "open", String.valueOf(new Date().getTime() / (1000 * 60))});
			int overduetaskscount_t = cursor_overduetasks_t.getCount();
			TaskData.tv_overduetaskscount_t.setText(String.valueOf(overduetaskscount_t));
			cursor_overduetasks_a = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + "<? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + ">=? and " +
							TaskData.TdDB.TASK_URGENCY + ">=? ",
					new String[]{"open", TaskData.user,
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int overduetaskscount_a = cursor_overduetasks_a.getCount();
			TaskData.tv_overduetaskscount_a.setText(String.valueOf(overduetaskscount_a));
			cursor_overduetasks_c = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + "<? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + ">=? and " +
							TaskData.TdDB.TASK_URGENCY + "<? ",
					new String[]{"open", TaskData.user,
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int overduetaskscount_c = cursor_overduetasks_c.getCount();
			TaskData.tv_overduetaskscount_c.setText(String.valueOf(overduetaskscount_c));
			cursor_overduetasks_b = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + "<? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + "<? and " +
							TaskData.TdDB.TASK_URGENCY + ">=? ",
					new String[]{"open", TaskData.user,
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int overduetaskscount_b = cursor_overduetasks_b.getCount();
			TaskData.tv_overduetaskscount_b.setText(String.valueOf(overduetaskscount_b));
			cursor_overduetasks_d = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " + TaskData.TdDB.TASK_STATUS + "=? and " +
							TaskData.TdDB.TASK_DEADLINETIMEDATA + "<? and " +
							TaskData.TdDB.TASK_USER + "=? and " +
							TaskData.TdDB.TASK_IMPORTANCE + "<? and " +
							TaskData.TdDB.TASK_URGENCY + "<? ",
					new String[]{"open", TaskData.user,
							String.valueOf(new Date().getTime() / (1000 * 60)), "2", "2"});
			int overduetaskscount_d = cursor_overduetasks_d.getCount();
			TaskData.tv_overduetaskscount_d.setText(String.valueOf(overduetaskscount_d));
		}catch (Exception e){
			e.printStackTrace();
		}finally {

		}
	}
	
	
	 public static int samedaycount(Cursor c){
		 c.moveToFirst();
		  String tskdl;
		  String tskdl_notime;
		  int samedaycount=0;
		  int overduedaycount=0;
		  String todaydate;
		  do{
			  tskdl =c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
			  //tskdl_fulldata =c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINEDATE));
			 // String datestr_tskdl = convertTime(tskdl);
			  //tskdl_notime=TimeData.convertDataNoTime_YY(tskdl);
			  todaydate = new SimpleDateFormat("yy-MM-dd").format(new Date(System.currentTimeMillis()));
			  //todaydate_fulltime = new Date().getTime();
			  Log.d("deadline", tskdl+"now:"+todaydate);
			 //Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG).show();;
			  switch (compareDate(tskdl,todaydate)) {
		      case 0: 
		    	      samedaycount=samedaycount+1;
		              break;
		      case -1:overduedaycount=overduedaycount+1; 
		              break;
		      default:break;        		    	  
		      }
		  }while(c.moveToNext());
		  return samedaycount;	 
	 }
	 
	 public static int compareDate(String time1,String time2){
		 SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd");
			// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
	     //SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date date1 = null;
		Date date2 = null;
		int result=0;
		try {
			date1 = formatter1.parse(time1);
			date2 = formatter1.parse(time2);
			result=date1.compareTo(date2);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("parseException", "error");
		}
		 
	     return result;
		}
	 
	public static int overduedaycount(Cursor c){
		 c.moveToFirst();
		  String tskdl;
		  int overduedaycount=0;
		  String todaydate;
		  do{
			  tskdl = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
			 // String datestr_tskdl = convertTime(tskdl);
			 todaydate = new SimpleDateFormat("yy-MM-dd").format(new Date(System.currentTimeMillis()));
		     
			  Log.d("deadline", tskdl+"now:"+todaydate);
			 //Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG).show();;
			  switch (compareDate(tskdl,todaydate)) {
		      case -1:overduedaycount=overduedaycount+1; 
		              break;
		      default:break;        		    	  
		      }
		  } while(c.moveToNext());
		  return overduedaycount;	 
	 }  
	
	
	public static void update_sum_achieved(){
		
		  TaskData.sum_achieved=0;
		  TaskData.cursor_alltasks.requery();
		  for(TaskData.cursor_alltasks.moveToFirst();!TaskData.cursor_alltasks.isAfterLast();TaskData.cursor_alltasks.moveToNext()){
	      int taskvalue_achieved = Integer.parseInt(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.SUM_ACHIEVED)));
		  TaskData.sum_achieved=taskvalue_achieved+TaskData.sum_achieved;
		  Log.d("taskscore show achieved",String.valueOf(taskvalue_achieved));
		  }
		  TaskData.tv_scoreheader_achieved.setText(String.valueOf(TaskData.sum_achieved));
	}
	
	
	public static void update_tvvalue(TextView tv,Cursor c,String field){
		
		  
		  if (c!=null&&c.getCount()>0){
			  c.requery();
			  c.moveToFirst();
			  if (tv!=null) {
				  tv.setText(c.getString(c.getColumnIndex(field)));
			  }
		  }	 
	}
	
	/*
	 public static void update_sum(TextView tv,Cursor c,String field){
		
		  int field_sum = 0;
		  c.requery();
		  for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
	      int fieldvalue = Integer.parseInt(c.getString(c.getColumnIndex(field)));
		  field_sum=fieldvalue+field_sum;
		  Log.d("fieldvalue "+field,String.valueOf(fieldvalue));	 
		  }
		  tv.setText(String.valueOf(field_sum));
		
	}*/
	public static void update_avgRev(TextView tv,Cursor c,String field){
		
		  int field_sum = 0;
		  c.requery();
		  for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
	      int fieldvalue = Integer.parseInt(c.getString(c.getColumnIndex(field)));
		  field_sum=fieldvalue+field_sum;
		  Log.d(Tags,"fieldvalue "+field+String.valueOf(fieldvalue));	 
		  }
		  double field_avg=0;
		  if (c.getCount()!=0){
		  field_avg =(c.getCount()-field_sum)/c.getCount();
		  }else{
		  field_avg =0;  
		  }
		  NumberFormat nt = NumberFormat.getPercentInstance();

		   nt.setMinimumFractionDigits(1);
		  String str_fieldavg = ""+nt.format(field_avg);
		  tv.setText(str_fieldavg);
		
	}
	public static void update_avg(TextView tv,Cursor c,String field){
		
		  double field_sum = 0.0;
			  c.requery();
			  for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				  double fieldvalue = Double.parseDouble(c.getString(c.getColumnIndex(field)));
				  field_sum = fieldvalue + field_sum;
				  Log.d(Tags, "fieldvalue " + field + String.valueOf(fieldvalue));
			  }
			  double field_avg = 0.0;
			  if (c.getCount() != 0) {
				  field_avg = field_sum / c.getCount();
			  } else {
				  field_avg = 0.0;
			  }
			  NumberFormat nt = NumberFormat.getPercentInstance();

			  nt.setMinimumFractionDigits(1);
			  String str_fieldavg = "" + nt.format(field_avg);
			  if (tv != null) {
				  tv.setText(str_fieldavg);
			  }

	}

	public static void update_sum(TextView tv,Cursor c,String field){
		
		  Double field_sum=0.0;
		  c.requery();
		  for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
	      Double fieldvalue = Double.parseDouble(c.getString(c.getColumnIndex(field)));
		  field_sum=fieldvalue+field_sum;
		  Log.d(Tags,"fieldvalue "+field+String.valueOf(fieldvalue));	 
		  }
		  DecimalFormat df = new DecimalFormat("0");
		  String str_fieldsum = df.format(field_sum);
		 if (tv!=null) {
			 tv.setText(str_fieldsum);
		 }
	}
	
	public static double field_sum(Cursor c,String field){
		
		  Double field_sum=0.0;
		  c.requery();
		  for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
	      Double fieldvalue = Double.parseDouble(c.getString(c.getColumnIndex(field)));
		  field_sum=fieldvalue+field_sum;
		  Log.d(Tags,"fieldvalue "+field+String.valueOf(fieldvalue));	 
		  }
		  return field_sum;	
	}
	
	public static void update_sum_enjoyment(){

		  TaskData.cursor_alltasks.requery();
		
		      TaskData.sum_enjoyment=0;
			  for(TaskData.cursor_alltasks.moveToFirst();!TaskData.cursor_alltasks.isAfterLast();TaskData.cursor_alltasks.moveToNext()){
		      int taskvalue_enjoyment = Integer.parseInt(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.SUM_ENJOYMENT)));
			  TaskData.sum_enjoyment=taskvalue_enjoyment+TaskData.sum_enjoyment;
			  Log.d(Tags,"taskscore show enjoyment"+String.valueOf(taskvalue_enjoyment));	 
			  }
			   TaskData.tv_scoreheader_enjoyment.setText(String.valueOf(TaskData.sum_enjoyment));
     	}
	public static void update_sum_experience(){

		  TaskData.cursor_alltasks.requery();
		
				  TaskData.sum_experience=0;
				  for(TaskData.cursor_alltasks.moveToFirst();!TaskData.cursor_alltasks.isAfterLast();TaskData.cursor_alltasks.moveToNext()){
			      int taskvalue_experience = Integer.parseInt(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.SUM_EXPERIENCE)));
				  TaskData.sum_experience=taskvalue_experience+TaskData.sum_experience;
				  Log.d(Tags,"taskscore show experience"+String.valueOf(taskvalue_experience));
				 
				  }
				   TaskData.tv_scoreheader_experience.setText(String.valueOf(TaskData.sum_experience));	
	}	
	
	public static void update_total(TextView tv,Cursor c){
		int sum=0;
		  c.requery();
		  sum=c.getCount();
		  tv.setText(String.valueOf(sum));
		  Log.d(Tags,"sum"+sum+c.toString());
	}
	
	static void batchTotalUpdate(){
	update_total(tv_opentaskscount, TaskData.cursor_opentasks);
	Log.d(Tags+"|batch total update|", "opentasks count done");
	update_total(tv_alltaskscount, TaskData.cursor_alltasks);
	Log.d(Tags+"|batch total update|", "alltasks count done");
	update_total(tv_finishedtaskscount, TaskData.cursor_finishedtasks);
	Log.d(Tags+"|batch total update|", "cancelledtasks count done");
	update_total(tv_cancelledtaskscount, TaskData.cursor_cancelledtasks);
	Log.d(Tags+"|batch total update|", "cancelledtasks count done");
	//tv_finishedtaskscount.setText(String.valueOf(TaskData.cursor_finishedtasks.getCount()));
	TaskData.update_sum(TaskData.tv_totalresultsatisfaction, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_RESULTSATISFICATION);
    TaskData.update_sum(TaskData.tv_totalachieved, TaskData.cursor_finishedtasks,TaskData.TdDB.SUM_ACHIEVED);
	TaskData.update_sum(TaskData.tv_totalexperience, TaskData.cursor_finishedtasks,TaskData.TdDB.SUM_EXPERIENCE);
	TaskData.update_sum(TaskData.tv_totalenjoyment, TaskData.cursor_finishedtasks,TaskData.TdDB.SUM_ENJOYMENT);


	}
	
	public static void getSequenceNo(){
		  Cursor c= TaskData.db_TdDB.rawQuery("select  *  from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                  TaskData.TdDB.TASK_STATUS+"='open'"+" and "+
                  TaskData.TdDB.TASK_USER+"='"+TaskData.user+"'"+
                  " order by "+TaskData.TdDB.TASK_SEQUENCE,null);
          //String[] sn=new String[ c.getCount()];
		 //Cursor c= TaskData.db_TdDB.rawQuery("select  *  from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"='open'"+" order by "+TaskData.TdDB.TASK_SEQUENCE,null);
	   if (c.getCount()>0){
		  String[] sn=new String[c.getCount()];
		  int i = 0;
		  String sq;
		  c.moveToFirst();
		  ContentValues cv = new ContentValues();
			do {
				sq=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SEQUENCE));
				sn[i]=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
				cv.put(TaskData.TdDB.TASK_SEQUENCENO, i+1);
				String where = TaskData.TdDB.TASK_SN + " = ?";    
				String[] whereValue = { sn[i]};
				int a = TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
				Log.d(Tags+"|sn no|", "cursor"+c.getPosition()+"i:"+i+"sn:"+sn[i]+" sequence:"+sq+" action"+a);
				i++;
			} while (c.moveToNext()); 
		  }else{
			 Log.d("sequence rank", "no data");
		  }
	}
	
	public static void getRecordSequenceNo(String task_sn){
		  Cursor c= TaskData.db_TdDB.rawQuery("select  *  from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+
				TaskData.TdDB.TASK_SN+"='"+task_sn+"'",null);
                //" order by "+TaskData.TdDB.TASK_SEQUENCE,null);
        //String[] sn=new String[ c.getCount()];
		 //Cursor c= TaskData.db_TdDB.rawQuery("select  *  from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"='open'"+" order by "+TaskData.TdDB.TASK_SEQUENCE,null);
		  if (c.getCount()!=0){
		  String[] sn=new String[c.getCount()];
		  int i = 0;
		  c.moveToFirst();
		  ContentValues cv = new ContentValues();
			do {
				sn[i]=c.getString(c.getColumnIndex(TaskData.TdDB.RECORD_SN));
				cv.put(TaskData.TdDB.RECORD_TASKID_NO, "P"+(i+1));
				String where = TaskData.TdDB.RECORD_SN + " = ?";    
				String[] whereValue = { String.valueOf(sn[i])};
				int a = TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskRecord, cv, where, whereValue);
				Log.d(Tags+"|sn no|", "cursor"+c.getPosition()+"i:"+i+" action"+a);
				i++;
			} while (c.moveToNext()); 
		  }else{
			 
		  }
	}
	
	public static void taskdetails(){
	  if (TaskData.selTaskSN!=null){
		      Cursor c = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
				  TaskData.TdDB.TASK_USER+"='"+TaskData.user+"'"+" and "+TaskData.TdDB.TASK_SN+"="+"'"+TaskData.selTaskSN+"'",null);
		      try {
				  if (c != null && c.getCount() > 0) {
					  c.moveToFirst();

					  if (TaskData.tv_progress != null) {
						  TaskData.tv_progress.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
					  }
					  if (TaskData.tv_status != null) {
						  TaskData.tv_status.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)));
					  }
				  }
			  }catch(Exception e){
				  e.printStackTrace();
			  }finally {
				  if (c!=null) {
					  c.close();
				  }
			  }
		 }else{
						  Log.d(Tags+"|selTaskSN|", "null");
		 }
	}

	public static void adapterUpdate(){
		//TaskTool.checkTaskCycle();
		if (TaskData.adapter_opentasks!=null){
	    TaskData.adapter_opentasks.getCursor().requery();
		TaskData.cursor_opentasks.requery();
		getSequenceNo();
		TaskData.adapter_opentasks.notifyDataSetChanged();
		update_total(tv_opentaskscount, TaskData.cursor_opentasks);
		Log.d(Tags+"|Adapter update|", "opentasks done");
		}
			
		if (TaskData.adapter_alltasks!=null){
			TaskData.adapter_alltasks.getCursor().requery();
			TaskData.adapter_alltasks.notifyDataSetChanged();
				update_total(tv_alltaskscount, TaskData.cursor_alltasks);
			 Log.d(Tags+"|Adapter update|", "alltasks done");
		}	 
		if (TaskData.adapter_finishedtasks!=null){
			TaskData.adapter_finishedtasks.getCursor().requery();
			TaskData.adapter_finishedtasks.notifyDataSetChanged();
				//TaskData.tv_finishedtaskscount.setText(String.valueOf(TaskData.cursor_finishedtasks.getCount()));
				update_total(tv_finishedtaskscount, TaskData.cursor_finishedtasks);
			Log.d(Tags+"|Adapter update|", "finishedtasks done");
		}
		/*
		if (TaskData.adapter_commentlist!=null){
			TaskData.adapter_commentlist.getCursor().requery();
			TaskData.adapter_commentlist.notifyDataSetChanged();
			Log.d(Tags+"|Adapter update|", "commentlist done");
		}
		if (TaskData.adaptRecordDetails!=null){
			TaskData.adaptRecordDetails.getCursor().requery();
			TaskData.adaptRecordDetails.notifyDataSetChanged();
			Log.d(Tags+"|Adapter update|", "RecordDetail done");
		}*/

		if (TaskData.adapter_taskreview!=null){
			TaskData.adapter_taskreview.getCursor().requery();
			TaskData.adapter_taskreview.notifyDataSetChanged();
			Log.d(Tags+"|Adapter update|", "taskreview done");
		}
		if (TaskData.adapter_reminder!=null){
			TaskData.adapter_reminder.getCursor().requery();
			TaskData.adapter_reminder.notifyDataSetChanged();
			Log.d(Tags+"|Adapter update|", "adapter_reminder done");
		}

		if (TaskData.adapter_cancelledtasks!=null){
			TaskData.adapter_cancelledtasks.getCursor().requery();
			TaskData.adapter_cancelledtasks.notifyDataSetChanged();
			
				//TaskData.tv_cancelledtaskscount.setText(String.valueOf(TaskData.cursor_cancelledtasks.getCount()));
				update_total(tv_cancelledtaskscount, TaskData.cursor_cancelledtasks);
			Log.d(Tags+"|Adapter update|", "cancelledtasks done");
		}
		/*
		Cursor cursor_todaytasks1 = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=?  and "+
				 TaskData.TdDB.TASK_STATUS+"=? and "+
                 TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
                 TaskData.TdDB.TASK_DEADLINETIMEDATA+">=?", 
                 new String[]{TaskData.user,"open",
                 new SimpleDateFormat ("yy-MM-dd").format(new Date()),
         		   String.valueOf(new Date().getTime()/(1000*60))});	
		   // new RefreshTodayList().onPostExecute(TaskData.cursor_todaytasks);
		TaskData.adapter_todaytasks= new mcAdapter_opentasks( TaskData.todaycontext,R.layout.lv_todaytasks,cursor_todaytasks1); 
		
	            cursor_todaytasks1.requery(); 
		       TaskData.adapter_todaytasks.notifyDataSetChanged();
			  //Toast.makeText(TaskData.todaycontext, "todaytask"+String.valueOf(cursor_todaytasks1.getCount()),Toast.LENGTH_SHORT).show();
				 Log.d("today Adapter update", "todaytasks done"+cursor_todaytasks1.getCount());
				 TaskData.lv_todaytasks.setAdapter(TaskData.adapter_todaytasks); 
		*/
		if (TaskData.adapter_todaytasks!=null){
		       TaskData.adapter_todaytasks.getCursor().requery();
		       TaskData.adapter_todaytasks.notifyDataSetChanged();
		       Log.d(Tags+"|Adapter update|", "todaytasks done");
		}
		
		if (TaskData.mcAdapter_taskrecord!=null){
			TaskData.mcAdapter_taskrecord.getCursor().requery();
			TaskData.mcAdapter_taskrecord.notifyDataSetChanged();
			Log.d(Tags+"|Adapter update|", "taskrecord done");
		}
		if (TaskData.adapter_taskrun!=null){
			TaskData.adapter_taskrun.getCursor().requery();
			TaskData.adapter_taskrun.notifyDataSetChanged();
			Log.d(Tags+"|Adapter update|", "taskrun done");
		}
		if (TaskData.adapter_memo!=null){
			TaskData.adapter_memo.getCursor().requery();
			TaskData.adapter_memo.notifyDataSetChanged();
			Log.d(Tags+"|Adapter update|", "memo done");
		}
		if (TaskData.adapter_reminder!=null){
			TaskData.adapter_reminder.getCursor().requery();	
		    TaskData.adapter_reminder.notifyDataSetChanged();
		    Log.d(Tags+"|Adapter update|", "reminder done");
		}
		if (TaskData.adapter_calendartasks!=null){
			TaskData.adapter_calendartasks.getCursor().requery();
			TaskData.adapter_calendartasks.notifyDataSetChanged();
			Log.d(Tags+"|Adapter update|", "calendartasks done");
		}
		if (TaskData.mcadapter_taskscore!=null){
			TaskData.mcadapter_taskscore.getCursor().requery();
			TaskData.mcadapter_taskscore.notifyDataSetChanged();
			TaskData.update_sum(TaskData.tv_scoreheader_accomplished,TaskData.cursor_finishedtasks,TaskData.TdDB.SUM_ACCOMPLISHED);
			update_sum_achieved();
			update_sum_enjoyment();
			update_sum_experience();

		}

		//batchTotalUpdate();
		Log.d(Tags+"|Adapter update|", "all done");
		
		if (TaskData.mcAdapter_group!=null){
			TaskData.mcAdapter_group.getCursor().requery();
			TaskData.mcAdapter_group.notifyDataSetChanged();
			Log.d(Tags+"|Adapter update|", "group done");
		}

		if ( TaskData.adapter_teamtaskrun!=null){
			TaskData.adapter_teamtaskrun.getCursor().requery();
			TaskData.adapter_teamtaskrun.notifyDataSetChanged();
			Log.d(Tags+"|Adapter update|", "team run done");
		}
		if ( TaskData.adapter_teamtaskreview!=null){
			TaskData.adapter_teamtaskreview.getCursor().requery();
			TaskData.adapter_teamtaskreview.notifyDataSetChanged();
			Log.d(Tags+"|Adapter update|", "team review done");
		}
		if (TaskData.mcadapter_teamscore!=null){
			TaskData.mcadapter_teamscore.getCursor().requery();
			TaskData.mcadapter_teamscore.notifyDataSetChanged();
			TaskData.update_sum(TaskData.tv_scoreheader_accomplished,TaskData.cursor_finishedteamtasks,TaskData.TdDB.SUM_ACCOMPLISHED);
			TaskData.update_sum(TaskData.tv_scoreheader_teamachieved,TaskData.cursor_teamscore,TaskData.TdDB.SUM_ACHIEVED);
			TaskData.update_sum(TaskData.tv_scoreheader_teamenjoyment,TaskData.cursor_teamscore,TaskData.TdDB.SUM_ENJOYMENT);
			TaskData.update_sum(TaskData.tv_scoreheader_teamexperience,TaskData.cursor_teamscore,TaskData.TdDB.SUM_EXPERIENCE);
			Log.d(Tags+"|Adapter update|", "team score done");
		}
		if (TaskData.adapter_teamopentasks!=null){
			TaskData.adapter_teamopentasks.getCursor().requery();
			TaskData.cursor_teamopentasks.requery();
			TaskData.adapter_teamopentasks.notifyDataSetChanged();
			update_total(tv_teamopentaskscount, TaskData.cursor_teamopentasks);
			Log.d(Tags+"|Adapter update|", "team opentasks done");
		}

		if (TaskData.adapter_teamalltasks!=null){
			TaskData.adapter_teamalltasks.getCursor().requery();
			TaskData.adapter_teamalltasks.notifyDataSetChanged();
			update_total(tv_teamalltaskscount, TaskData.cursor_teamalltasks);
			Log.d(Tags+"|Adapter update|", "team alltasks done");
		}
		if (TaskData.adapter_teamfinishedtasks!=null){
			TaskData.adapter_teamfinishedtasks.getCursor().requery();
			TaskData.adapter_teamfinishedtasks.notifyDataSetChanged();
			//TaskData.tv_finishedtaskscount.setText(String.valueOf(TaskData.cursor_finishedtasks.getCount()));
			update_total(tv_teamfinishedtaskscount, TaskData.cursor_teamfinishedtasks);
			Log.d(Tags+"|Adapter update|", "team finishedtasks done");
		}

		if (TaskData.adapter_teamcancelledtasks!=null){
			TaskData.adapter_teamcancelledtasks.getCursor().requery();
			TaskData.adapter_teamcancelledtasks.notifyDataSetChanged();
			//TaskData.tv_cancelledtaskscount.setText(String.valueOf(TaskData.cursor_cancelledtasks.getCount()));
			update_total(tv_teamcancelledtaskscount, TaskData.cursor_teamcancelledtasks);
			Log.d(Tags+"|Adapter update|", "team cancelledtasks done");
		}

		TaskData.updateflag=1;
		homepage_countupdate();
		showPerformance();
		//showTeamPerformance();
		taskdetails();
		
	}



	

}
