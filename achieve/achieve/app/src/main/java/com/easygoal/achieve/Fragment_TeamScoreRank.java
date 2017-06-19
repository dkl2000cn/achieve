package com.easygoal.achieve;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Fragment_TeamScoreRank extends Fragment {
	
	
	String curTime;
	int selectedID=0;
	String Tags="Fragment_TeamScoreRank";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		final View v=inflater.inflate(R.layout.fg_teamrank, container, false);
		 final TextView tv_teamname=(TextView)v.findViewById(R.id.tv_teamname);

		 if (TaskData.usergroup!=null) {
			 tv_teamname.setText(TaskData.usergroup);
		 }
	    showTeamPerformance(v);
		 final Spinner spin_team = (Spinner)v.findViewById(R.id.spin_team);
		 final List<InputValueSet> list_team=new ArrayList<InputValueSet>();
		 list_team.add(new InputValueSet(1,"A"));
		 list_team.add(new InputValueSet(2,"B"));
		 list_team.add(new InputValueSet(3,"C"));
		 mArrayAdapter adapt_spinteam = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_team);
		 spin_team.setAdapter(adapt_spinteam);
		 spin_team.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			 @Override
			 public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
				 // TODO Auto-generated method stub
				 //TaskData.usergroup = list_team.get(pos).getValue().toString();
				 showTeamPerformance(v);
				 TaskData.adapterUpdate();
			 }

			 @Override
			 public void onNothingSelected(AdapterView<?> arg0) {
				 // TODO Auto-generated method stubn /////////////'[]\

			 }
		 });

		return v;
	 
	 }

	 public void showTeamPerformance(View v){
		 NumberFormat nt = NumberFormat.getPercentInstance();
		 nt.setMinimumFractionDigits(1);
		 // TaskData.tv_todaytaskscount=(TextView)v.findViewById(R.id.tv_todaytaskscount);
		 TaskData.tv_teamfinishedtaskscount=(TextView)v.findViewById(R.id.tv_finishedtaskscount);
		 TaskData.tv_teamtotalaccomplished=(TextView)v.findViewById(R.id.tv_totalaccomplished);
		 TaskData.tv_teamtotalcontribution=(TextView)v.findViewById(R.id.tv_totalcontribution);
		 // TaskData.tv_totalurgency=(TextView)v.findViewById(R.id.tv_totalurgency);
		 TaskData.tv_teamtotaltaskcount=(TextView)v.findViewById(R.id.tv_totaltaskscount);
		 TaskData.tv_teamtotalclosedrate=(TextView)v.findViewById(R.id.tv_totalclosedrate);
		 TaskData.tv_teamtotaltimely=(TextView)v.findViewById(R.id.tv_totaltimely);
		 TaskData.tv_teamtotalresultsatisfaction=(TextView)v.findViewById(R.id.tv_totalsatisfaction);
		 TaskData.tv_teamtotalachieved=(TextView)v.findViewById(R.id.tv_totalachieved);
		 TaskData.tv_teamtotalexperience=(TextView)v.findViewById(R.id.tv_totalexperience);
		 TaskData.tv_teamtotalenjoyment=(TextView)v.findViewById(R.id.tv_totalenjoyment);

		 
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		   // final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		  //  Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));

		  TaskData.cursor_teamfinishedtasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
		                  TaskData.TdDB.TASK_STATUS+"=? and "+
		                  TaskData.TdDB.TASK_USERGROUP+"=?",
		 		     	   new String[]{"finished",TaskData.usergroup});
				       
		  TaskData.cursor_teamalltasks =TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USERGROUP+"=?", new String[]{TaskData.usergroup});
		  TaskData.tv_teamfinishedtaskscount.setText(String.valueOf(TaskData.cursor_teamfinishedtasks.getCount()));
		  TaskData.tv_teamtotaltaskcount.setText(String.valueOf(TaskData.cursor_teamalltasks.getCount()));
		  double totalcloserate = (double) TaskData.cursor_teamfinishedtasks.getCount() / TaskData.cursor_teamalltasks.getCount();
		  Log.d(Tags,"totalcloserate"+totalcloserate);
		  TaskData.tv_teamtotalclosedrate.setText(nt.format(totalcloserate));
		  TaskData.update_avg(TaskData.tv_teamtotalresultsatisfaction, TaskData.cursor_teamfinishedtasks,TaskData.TdDB.TASK_RESULTSATISFICATION);
		  TaskData.update_sum(TaskData.tv_teamtotalaccomplished, TaskData.cursor_teamfinishedtasks,TaskData.TdDB.TASK_IMPORTANCE);
		  TaskData.update_sum(TaskData.tv_teamtotalcontribution, TaskData.cursor_teamfinishedtasks,TaskData.TdDB.TASK_ASSESSMENT);
		  TaskData.update_avgRev(TaskData.tv_teamtotaltimely, TaskData.cursor_teamfinishedtasks,TaskData.TdDB.TASK_DELAYED);
		 // TaskData.update_sum(TaskData.tv_totalurgency, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_URGENCY);
		  TaskData.update_sum(TaskData.tv_teamtotalachieved, TaskData.cursor_teamalltasks,TaskData.TdDB.SUM_ACHIEVED);
		  TaskData.update_sum(TaskData.tv_teamtotalexperience, TaskData.cursor_teamalltasks,TaskData.TdDB.SUM_EXPERIENCE);
		  TaskData.update_sum(TaskData.tv_teamtotalenjoyment, TaskData.cursor_teamalltasks,TaskData.TdDB.SUM_ENJOYMENT);
		  
		    final Calendar cal=Calendar.getInstance();
	    	   int year = cal.get(Calendar.YEAR);
              int month = cal.get(Calendar.MONTH);
              int day=cal.get(Calendar.DAY_OF_MONTH);
              
              
             String str_thismonth = year + "-" + (month+1) + "-" + 1+" "+"00"+":"+"00";
           
             long thismonthTimeData = TimeData.changeStrToTime_YYYY(str_thismonth);
             //Toast.makeText(getActivity(), String.valueOf(thismonthTimeData), Toast.LENGTH_SHORT).show();;
             //Timestamp ts_thismonth = Timestamp.valueOf(str_thismonth);
		  TaskData.tv_teamfinishedtaskscount_thismonth=(TextView)v.findViewById(R.id.tv_finishedtaskscount_thismonth);
		  TaskData.tv_teamtotalresultsatisfaction_thismonth=(TextView)v.findViewById(R.id.tv_totalsatisfaction_thismonth);
		  TaskData.tv_teamtotalaccomplished_thismonth=(TextView)v.findViewById(R.id.tv_totalaccomplished_thismonth);
		  TaskData.tv_teamtotaltaskcount_thismonth=(TextView)v.findViewById(R.id.tv_totaltaskscount_thismonth);
		  TaskData.tv_teamtotalclosedrate_thismonth=(TextView)v.findViewById(R.id.tv_totalclosedrate_thismonth);
		 // TaskData.tv_totalurgency_thismonth=(TextView)v.findViewById(R.id.tv_totalurgency_thismonth);
		  TaskData.tv_teamtotalcontribution_thismonth=(TextView)v.findViewById(R.id.tv_totalcontribution_thismonth);
		  TaskData.tv_teamtotaltimely_thismonth=(TextView)v.findViewById(R.id.tv_totaltimely_thismonth);
		  TaskData.tv_teamtotalachieved_thismonth=(TextView)v.findViewById(R.id.tv_totalachieved_thismonth);
		  TaskData.tv_teamtotalexperience_thismonth=(TextView)v.findViewById(R.id.tv_totalexperience_thismonth);
		  TaskData.tv_teamtotalenjoyment_thismonth=(TextView)v.findViewById(R.id.tv_totalenjoyment_thismonth);
		 
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		   // final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		  //  Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		   
		  TaskData.cursor_teamfinishedtaskscount_thismonth = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+
				                                        " where "+TaskData.TdDB.TASK_STATUS+ "=? and "+
				                                        TaskData.TdDB.TASK_USERGROUP+" =? and "+
				                                        TaskData.TdDB.TASK_DEADLINETIMEDATA+">?", 
				                                        new String[]{"finished",TaskData.usergroup,String.valueOf(thismonthTimeData)});
		  TaskData.cursor_teamalltaskscount_thismonth = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+
                                                         " where "+TaskData.TdDB.TASK_USERGROUP+" =? and "+TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
                                                         new String[]{TaskData.usergroup,String.valueOf(thismonthTimeData)});
		  TaskData.tv_teamfinishedtaskscount_thismonth.setText(String.valueOf(TaskData.cursor_teamfinishedtaskscount_thismonth.getCount()));
		  TaskData.tv_teamtotaltaskcount_thismonth.setText(String.valueOf(TaskData.cursor_teamalltaskscount_thismonth.getCount()));
		  double totalcloserate_thismonth = (double) TaskData.cursor_teamfinishedtaskscount_thismonth.getCount()/TaskData.cursor_teamalltaskscount_thismonth.getCount();
		  Log.d(Tags,"totalcloserate_thismonth "+totalcloserate_thismonth);
		  TaskData.tv_teamtotalclosedrate_thismonth.setText(nt.format(totalcloserate_thismonth));
		  TaskData.update_avg(TaskData.tv_teamtotalresultsatisfaction_thismonth, TaskData.cursor_teamfinishedtaskscount_thismonth,TaskData.TdDB.TASK_RESULTSATISFICATION);
		  TaskData.update_sum(TaskData.tv_teamtotalaccomplished_thismonth, TaskData.cursor_teamfinishedtaskscount_thismonth,TaskData.TdDB.TASK_IMPORTANCE);
		  TaskData.update_sum(TaskData.tv_teamtotalcontribution_thismonth, TaskData.cursor_teamfinishedtaskscount_thismonth,TaskData.TdDB.TASK_ASSESSMENT);
		  TaskData.update_avgRev(TaskData.tv_teamtotaltimely_thismonth, TaskData.cursor_teamfinishedtaskscount_thismonth,TaskData.TdDB.TASK_DELAYED);
		  //TaskData.update_sum(TaskData.tv_totalurgency_thismonth, TaskData.cursor_finishedtaskscount_thismonth,TaskData.TdDB.TASK_URGENCY);
		  TaskData.update_sum(TaskData.tv_teamtotalachieved_thismonth, TaskData.cursor_teamalltaskscount_thismonth,TaskData.TdDB.SUM_ACHIEVED);
		  TaskData.update_sum(TaskData.tv_teamtotalexperience_thismonth, TaskData.cursor_teamalltaskscount_thismonth,TaskData.TdDB.SUM_EXPERIENCE);
		  TaskData.update_sum(TaskData.tv_teamtotalenjoyment_thismonth, TaskData.cursor_teamalltaskscount_thismonth,TaskData.TdDB.SUM_ENJOYMENT);
		  
		     String str_today = year + "-" + (month+1) + "-" + day+" "+"00"+":"+"00";
            
		     long todayTimeData = TimeData.changeStrToTime_YYYY(str_today);
		    // Toast.makeText(getActivity(), String.valueOf(todayTimeData), Toast.LENGTH_SHORT).show();;
            //Timestamp ts_today = Timestamp.valueOf(str_today);
		  TaskData.tv_teamfinishedtaskscount_today=(TextView)v.findViewById(R.id.tv_finishedtaskscount_today);
		  TaskData.tv_teamtotalresultsatisfaction_today=(TextView)v.findViewById(R.id.tv_totalsatisfaction_today);
		  TaskData.tv_teamtotaltaskcount_today=(TextView)v.findViewById(R.id.tv_totaltaskscount_today);
		  TaskData.tv_teamtotalclosedrate_today=(TextView)v.findViewById(R.id.tv_totalclosedrate_today);
		  TaskData.tv_teamtotalaccomplished_today=(TextView)v.findViewById(R.id.tv_totalaccomplished_today);
		 // TaskData.tv_totalurgency_today=(TextView)v.findViewById(R.id.tv_totalurgency_today);
		  TaskData.tv_teamtotalcontribution_today=(TextView)v.findViewById(R.id.tv_totalcontribution_today);
		  TaskData.tv_teamtotaltimely_today=(TextView)v.findViewById(R.id.tv_totaltimely_today);
		  TaskData.tv_teamtotalachieved_today=(TextView)v.findViewById(R.id.tv_totalachieved_today);
		  TaskData.tv_teamtotalexperience_today=(TextView)v.findViewById(R.id.tv_totalexperience_today);
		  TaskData.tv_teamtotalenjoyment_today=(TextView)v.findViewById(R.id.tv_totalenjoyment_today);
		 
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		   // final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		  //  Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		   
		  TaskData.cursor_teamfinishedtaskscount_today = TaskData.db_TdDB.rawQuery("select * from "+
		                                             TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
				                                     TaskData.TdDB.TASK_STATUS+"=? and "+
				                                     TaskData.TdDB.TASK_USERGROUP+" =? and "+
		                                             TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
				                                     new String[]{"finished",TaskData.usergroup,String.valueOf(todayTimeData)});
		  TaskData.cursor_teamalltaskscount_today = TaskData.db_TdDB.rawQuery("select * from "+
                                                     TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                                                     TaskData.TdDB.TASK_USERGROUP+" =? and "+
                                                     TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
                                                     new String[]{TaskData.usergroup,String.valueOf(todayTimeData)});
		  TaskData.tv_teamfinishedtaskscount_today.setText(String.valueOf(TaskData.cursor_teamfinishedtaskscount_today.getCount()));
		  double totalcloserate_today= (double) TaskData.cursor_teamfinishedtaskscount_today.getCount()/ TaskData.cursor_teamalltaskscount_today.getCount();
		  Log.d(Tags,"totalcloserate_today "+totalcloserate_today);
		  TaskData.tv_teamtotalclosedrate_today.setText(nt.format(totalcloserate_today));
		  TaskData.tv_teamtotaltaskcount_today.setText(String.valueOf(TaskData.cursor_teamalltaskscount_today.getCount()));
		  TaskData.update_avg(TaskData.tv_teamtotalresultsatisfaction_today, TaskData.cursor_teamfinishedtaskscount_today,TaskData.TdDB.TASK_RESULTSATISFICATION);
		  TaskData.update_sum(TaskData.tv_teamtotalaccomplished_today, TaskData.cursor_teamfinishedtaskscount_today,TaskData.TdDB.TASK_IMPORTANCE);
		  TaskData.update_sum(TaskData.tv_teamtotalcontribution_today, TaskData.cursor_teamfinishedtaskscount_today,TaskData.TdDB.TASK_ASSESSMENT);
		  TaskData.update_avgRev(TaskData.tv_teamtotaltimely_today, TaskData.cursor_teamfinishedtaskscount_today,TaskData.TdDB.TASK_DELAYED);
		 // TaskData.update_sum(TaskData.tv_totalurgency_today, TaskData.cursor_finishedtaskscount_today,TaskData.TdDB.TASK_URGENCY);
		  TaskData.update_sum(TaskData.tv_teamtotalachieved_today, TaskData.cursor_teamalltaskscount_today,TaskData.TdDB.SUM_ACHIEVED);
		  TaskData.update_sum(TaskData.tv_teamtotalexperience_today, TaskData.cursor_teamalltaskscount_today,TaskData.TdDB.SUM_EXPERIENCE);
		  TaskData.update_sum(TaskData.tv_teamtotalenjoyment_today, TaskData.cursor_teamalltaskscount_today,TaskData.TdDB.SUM_ENJOYMENT);
		 
		  TaskData.teamperformanceflag=1;
		 
	 }
	 
	 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 
		}
   
	
}
