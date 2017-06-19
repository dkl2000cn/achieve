package com.easygoal.achieve;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
import android.widget.TextView;

public class FragmentScoreRank extends Fragment {
	
	
	String curTime;
	int selectedID=0;
	String Tags="FragmentScoreRank";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.subfg_scoretab_toptab2_rank, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		 showPerformance(v);
		  
		return v;
	 
	 }

	 public void showPerformance(View v){
		 // TaskData.tv_todaytaskscount=(TextView)v.findViewById(R.id.tv_todaytaskscount);
		 NumberFormat nt = NumberFormat.getPercentInstance();
		 nt.setMinimumFractionDigits(1);

		  TaskData.tv_finishedtaskscount=(TextView)v.findViewById(R.id.tv_finishedtaskscount);
		  TaskData.tv_totalaccomplished=(TextView)v.findViewById(R.id.tv_totalaccomplished);
		  TaskData.tv_totalcontribution=(TextView)v.findViewById(R.id.tv_totalcontribution);
		 // TaskData.tv_totalurgency=(TextView)v.findViewById(R.id.tv_totalurgency);
		  TaskData.tv_totaltimely=(TextView)v.findViewById(R.id.tv_totaltimely);
		  TaskData.tv_totaltaskcount=(TextView)v.findViewById(R.id.tv_totaltaskscount);
		  TaskData.tv_totalclosedrate=(TextView)v.findViewById(R.id.tv_totalclosedrate);
		  TaskData.tv_totalresultsatisfaction=(TextView)v.findViewById(R.id.tv_totalsatisfaction);
		  TaskData.tv_totalachieved=(TextView)v.findViewById(R.id.tv_totalachieved);
		  TaskData.tv_totalexperience=(TextView)v.findViewById(R.id.tv_totalexperience);
		  TaskData.tv_totalenjoyment=(TextView)v.findViewById(R.id.tv_totalenjoyment);
		 
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		   // final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		  //  Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		  
		  TaskData.cursor_finishedtasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
		                  TaskData.TdDB.TASK_STATUS+"=? and "+
		                  TaskData.TdDB.TASK_USER+"=?", 
		 		     	   new String[]{"finished",TaskData.user});
				       
		  TaskData.cursor_alltasks =TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});

		  TaskData.tv_finishedtaskscount.setText(String.valueOf(TaskData.cursor_finishedtasks.getCount()));
		  TaskData.tv_totaltaskcount.setText(String.valueOf(TaskData.cursor_alltasks.getCount()));
		  double totalcloserate = (double) TaskData.cursor_finishedtasks.getCount() / TaskData.cursor_alltasks.getCount();
		  Log.d(Tags,"totalcloserate"+totalcloserate);
		  TaskData.tv_totalclosedrate.setText(nt.format(totalcloserate));
		  TaskData.update_avg(TaskData.tv_totalresultsatisfaction, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_RESULTSATISFICATION);
		  TaskData.update_sum(TaskData.tv_totalaccomplished, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_IMPORTANCE);
		  TaskData.update_sum(TaskData.tv_totalcontribution, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_ASSESSMENT);
		  TaskData.update_avgRev(TaskData.tv_totaltimely, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_DELAYED);
		 // TaskData.update_sum(TaskData.tv_totalurgency, TaskData.cursor_finishedtasks,TaskData.TdDB.TASK_URGENCY);
		  TaskData.update_sum(TaskData.tv_totalachieved, TaskData.cursor_alltasks,TaskData.TdDB.SUM_ACHIEVED);
		  TaskData.update_sum(TaskData.tv_totalexperience, TaskData.cursor_alltasks,TaskData.TdDB.SUM_EXPERIENCE);
		  TaskData.update_sum(TaskData.tv_totalenjoyment, TaskData.cursor_alltasks,TaskData.TdDB.SUM_ENJOYMENT);
		  
		    final Calendar cal=Calendar.getInstance();
	    	   int year = cal.get(Calendar.YEAR);
              int month = cal.get(Calendar.MONTH);
              int day=cal.get(Calendar.DAY_OF_MONTH);
              
              
             String str_thismonth = year + "-" + (month+1) + "-" + 1+" "+"00"+":"+"00";
           
             long thismonthTimeData = TimeData.changeStrToTime_YYYY(str_thismonth);
             //Toast.makeText(getActivity(), String.valueOf(thismonthTimeData), Toast.LENGTH_SHORT).show();;
             //Timestamp ts_thismonth = Timestamp.valueOf(str_thismonth);
		  TaskData.tv_totaltaskscount_thismonth=(TextView)v.findViewById(R.id.tv_totaltaskscount_thismonth);
		  TaskData.tv_totalclosedrate_thismonth=(TextView)v.findViewById(R.id.tv_totalclosedrate_thismonth);
		  TaskData.tv_finishedtaskscount_thismonth=(TextView)v.findViewById(R.id.tv_finishedtaskscount_thismonth);
		  TaskData.tv_totaltimely_thismonth=(TextView)v.findViewById(R.id.tv_totaltimely_thismonth);
		  TaskData.tv_totalresultsatisfaction_thismonth=(TextView)v.findViewById(R.id.tv_totalsatisfaction_thismonth);
		  TaskData.tv_totalaccomplished_thismonth=(TextView)v.findViewById(R.id.tv_totalaccomplished_thismonth);
		 // TaskData.tv_totalurgency_thismonth=(TextView)v.findViewById(R.id.tv_totalurgency_thismonth);
		  TaskData.tv_totalcontribution_thismonth=(TextView)v.findViewById(R.id.tv_totalcontribution_thismonth);
		  TaskData.tv_totalachieved_thismonth=(TextView)v.findViewById(R.id.tv_totalachieved_thismonth);
		  TaskData.tv_totalexperience_thismonth=(TextView)v.findViewById(R.id.tv_totalexperience_thismonth);
		  TaskData.tv_totalenjoyment_thismonth=(TextView)v.findViewById(R.id.tv_totalenjoyment_thismonth);
		 
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		   // final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		  //  Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		   
		  TaskData.cursor_finishedtaskscount_thismonth = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+
				                                        " where "+TaskData.TdDB.TASK_STATUS+ "=? and "+
				                                        TaskData.TdDB.TASK_USER+" =? and "+
				                                        TaskData.TdDB.TASK_DEADLINETIMEDATA+">?", 
				                                        new String[]{"finished",TaskData.user,String.valueOf(thismonthTimeData)});
		  TaskData.cursor_alltaskscount_thismonth = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+
                                                         " where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_DEADLINETIMEDATA+">?", 
                                                         new String[]{TaskData.user,String.valueOf(thismonthTimeData)});
		  TaskData.tv_totaltaskscount_thismonth.setText(String.valueOf(TaskData.cursor_alltaskscount_thismonth.getCount()));
		  double totalcloserate_thismonth = (double) TaskData.cursor_finishedtaskscount_thismonth.getCount()/TaskData.cursor_alltaskscount_thismonth.getCount();
		  Log.d(Tags,"totalcloserate_thismonth "+totalcloserate_thismonth);
		  TaskData.tv_totalclosedrate_thismonth.setText(nt.format(totalcloserate_thismonth));
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
		    // Toast.makeText(getActivity(), String.valueOf(todayTimeData), Toast.LENGTH_SHORT).show();;
            //Timestamp ts_today = Timestamp.valueOf(str_today);
		  TaskData.tv_totaltaskscount_today=(TextView)v.findViewById(R.id.tv_totaltaskscount_today);
		  TaskData.tv_totalclosedrate_today=(TextView)v.findViewById(R.id.tv_totalclosedrate_today);
		  TaskData.tv_finishedtaskscount_today=(TextView)v.findViewById(R.id.tv_finishedtaskscount_today);
		  TaskData.tv_totalresultsatisfaction_today=(TextView)v.findViewById(R.id.tv_totalsatisfaction_today);
		  TaskData.tv_totalaccomplished_today=(TextView)v.findViewById(R.id.tv_totalaccomplished_today);
		 // TaskData.tv_totalurgency_today=(TextView)v.findViewById(R.id.tv_totalurgency_today);
		  TaskData.tv_totalcontribution_today=(TextView)v.findViewById(R.id.tv_totalcontribution_today);
		  TaskData.tv_totaltimely_today=(TextView)v.findViewById(R.id.tv_totaltimely_today);
		  TaskData.tv_totalachieved_today=(TextView)v.findViewById(R.id.tv_totalachieved_today);
		  TaskData.tv_totalexperience_today=(TextView)v.findViewById(R.id.tv_totalexperience_today);
		  TaskData.tv_totalenjoyment_today=(TextView)v.findViewById(R.id.tv_totalenjoyment_today);
		 
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		   // final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		  //  Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		   
		  TaskData.cursor_finishedtaskscount_today = TaskData.db_TdDB.rawQuery("select * from "+
		                                             TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
				                                     TaskData.TdDB.TASK_STATUS+"=? and "+
				                                     TaskData.TdDB.TASK_USER+" =? and "+
		                                             TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
				                                     new String[]{"finished",TaskData.user,String.valueOf(todayTimeData)});
		  TaskData.cursor_alltaskscount_today = TaskData.db_TdDB.rawQuery("select * from "+
                                                     TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                                                     TaskData.TdDB.TASK_USER+" =? and "+
                                                     TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
                                                     new String[]{TaskData.user,String.valueOf(todayTimeData)});
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
		 
		  TaskData.performanceflag=1;
		 
	 }
	 
	 
	 
	 public Fragment showFrag(Fragment from_fg,int viewframe,Fragment[] frag,int i){
		  FragmentTransaction ft = getFragmentManager().beginTransaction();

	       if (from_fg==null){
	    	   ft.add(viewframe, frag[i]).commit(); 
	    	  
	       }else{
	    	   if (!frag[i].isAdded()){
	    		 ; 
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
	
	 public int compareDate(String time1,String time2){
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
				
			}
			 
		     return result;
			}
		
	 public int samedaycount(Cursor c){
		 c.moveToFirst();
		  String tskdl;
		  int samedaycount=0;
		  int overduedaycount=0;
		  String todaydate;
		  while(c.moveToNext()){
			  tskdl = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
			 // String datestr_tskdl = convertTime(tskdl);
			 todaydate = new SimpleDateFormat("yy-MM-dd").format(new Date(System.currentTimeMillis()));
		     
			  Log.d(Tags,"deadline "+tskdl+"now:"+todaydate);
			 //Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG).show();;
			  switch (compareDate(tskdl,todaydate)) {
		      case 0: samedaycount=samedaycount+1;
		              break;
		      case -1:overduedaycount=overduedaycount+1; 
		              break;
		      default:break;        		    	  
		      }
		  }
		  return samedaycount;	 
	 }
	 
	 public int overduedaycount(Cursor c){
		 c.moveToFirst();
		  String tskdl;
		  int overduedaycount=0;
		  String todaydate;
		  while(c.moveToNext()){
			  tskdl = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
			 // String datestr_tskdl = convertTime(tskdl);
			 todaydate = new SimpleDateFormat("yy-MM-dd").format(new Date(System.currentTimeMillis()));
		     
			  Log.d(Tags,"deadline"+tskdl+"now:"+todaydate);
			 //Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG).show();;
			  switch (compareDate(tskdl,todaydate)) {
		      case -1:overduedaycount=overduedaycount+1; 
		              break;
		      default:break;        		    	  
		      }
		  }
		  return overduedaycount;	 
	 }
	 
	 
	 public String convertTime(String time){
		 SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd");
			// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
	     SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
	     Date date1 = null;
			try {
				
				try {
					date1 = formatter1.parse(time);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     return formatter2.format(date1);
		}
		
	 public double TimeSpace(String str_time1,String str_time2){
	    	
	    	//String str2 = et15_taskdeadline.getText().toString();  //"yyyyMMdd"格式 如 20131022
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//输入日期的格式 
			Date date1 = null;
			Date date2 = null;
			try {
			date2 = simpleDateFormat.parse(str_time2);
			date1= simpleDateFormat.parse(str_time1);
			} catch (ParseException e) {
			e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			//GregorianCalendar cal1 = new GregorianCalendar();  
			GregorianCalendar cal2 = new GregorianCalendar(); 
			GregorianCalendar cal1 = new GregorianCalendar();
			//cal1.setTime(date1);  
			cal2.setTime(date2);  
			cal1.setTime(date1);
			double dayCount = (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*3600*24);
						
	    	return dayCount;
	    };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		}
   
	
}
