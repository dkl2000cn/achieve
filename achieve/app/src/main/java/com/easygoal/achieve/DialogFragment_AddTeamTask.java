package com.easygoal.achieve;


import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.ParseException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class DialogFragment_AddTeamTask extends DialogFragment {


	String curTime;
	Timestamp taskdeadlinetimestamp=null;
	String task_maxperiod;
	String column_insert;
	String value_insert;
	 long taskdeadlineTimeData=0;
	 String taskdeadlineDate;
	 double plannedtime=0;
	 String taskreminder=null;
	 String sn_deadline=null;
	 String sn_importance=null;
	 String sn_urgency=null;
	 String sn_duration=null;
	 String sn_sequence=null;
	 String taskdeadline;
	 String taskstartedtime;
	 String deadline_model1;
	 String deadlinedate_model1;
	 long deadlinetimedata_model1;
	 int sn_sequenceno=0;
	 long curTimeData;
	 int recordno=0;
	 //List ls1;
     public String dueHour;
     public String dueMin;
	 lvBaseAdapter_AddTaskModel1 lvadapter_model1;
	 DecimalFormat df_duration;
	 double model1_duration;
	 double dm1;
	 int model1_durationunit=0;
	 double dayspace;
	 double timespace;
	 double hr_timeunit;
	 String Tags="DialogFragment_Task";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);
		// setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		  /*if (TaskData.TdDB==null) {
		    TaskData.TdDB = new ToDoDB(getActivity(), TaskData.db_TdDBname,null, 1);
		    TaskData.db_TdDB = TaskData.TdDB.getWritableDatabase();
			//taskRecord.onCreate(db);
			Log.d("create", TaskData.TdDB.TABLE_NAME_TaskRecord);
		   }else{
			   Log.d("create", "go");
		   }
		*/

		 SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
		 Date curDate = new Date();//获取当前时间
		 curTime = formatter.format(curDate);
	     curTimeData=new Date().getTime();
	     df_duration = new DecimalFormat("0.0");
	     dueHour=""+TaskData.endhour;
	     dueMin=""+TaskData.endmin;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View v=inflater.inflate(R.layout.dialogfg_addteamtask, container, false);
		//DatabaseHelper TdDB=new DatabaseHelper();

		 final TabHost tabhost=(TabHost)v.findViewById(android.R.id.tabhost);
	 	   tabhost.setup();
	 	   tabhost.addTab(tabhost.newTabSpec("1").setIndicator("简易模式").setContent(R.id.tab1));
	 	   tabhost.addTab(tabhost.newTabSpec("2").setIndicator("标准模式").setContent(R.id.tab2));

	 	  //TabWidget tabWidget = (TabWidget) v.findViewById(android.R.id.tabs);
	 	  final TextView[] tv = new TextView[2];
	      for (int i=0; i<tabhost.getTabWidget().getChildCount(); i++){                         //循环每个tabView
	          //View view = tabWidget.getChildAt(i);                                 //获取tabView项
	          //view.setContentDescription(Integer.toString(i+1));
	          //view.getLayoutParams().height = (int) (view.getLayoutParams().height / 1.2);


	          tv[i] = (TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
	          //tv.setTextColor(R.drawable.selector_bottomtab1_textcolor);
	          //tv.setText("XXXX");
	          //iv.setImageDrawable(getResources().getDrawable(R.drawable.icon));
	           Log.d("tv"+i,"tv"+ tv.toString());
	           tv[i].setTextSize(20);
	           tv[i].setTextColor(R.drawable.selector_bottomtab1_textcolor);
	      }
	 	tabhost.setCurrentTab(TaskData.usermodel);

		final ListView lv_quickinput_model1=(ListView)v.findViewById(R.id.lv_quickinput);
	    final Spinner spin_deadline_model1 = (Spinner)v.findViewById(R.id.model_task_item15_deadline_spin);
	   	final EditText et_checklist_model1=(EditText)v.findViewById(R.id.model1_checklist_et);
	   	final TextView tv_taskdeadline_model1=(TextView)v.findViewById(R.id.model1_task_item15_deadline_tv);
		final TextView et_taskname_model1=(TextView)v.findViewById(R.id.model1_taskname_et);
		final Button model1_taskadd_btn=(Button)v.findViewById(R.id.model1_taskadd_btn);
	   	final CheckBox model1_reminder_cb=(CheckBox)v.findViewById(R.id.model1_reminder_cb);
	   	final Button model1_task_confirm_bt=(Button)v.findViewById(R.id.model1_task_confirm_bt);
		final Button model1_task_cancel_bt=(Button)v.findViewById(R.id.model1_task_cancel_bt);
	   	final TextView tv_taskcreatedtime_model1=(TextView)v.findViewById(R.id.model1_task_item2_createdtime_tv);
		final TextView model1_taskteam_tv=(TextView)v.findViewById(R.id.model1_taskteam_tv);
		final TextView model2_taskteam_tv=(TextView)v.findViewById(R.id.model2_taskteam_tv);
	    final TextView tv2_taskcreatedtime=(TextView)v.findViewById(R.id.task_item2_createdtime_tv);
	   	//final TextView tv_newtaskheader=(TextView)v.findViewById(R.id.tv_newtaskheader);
	   	final TextView tv_maxvalue=(TextView)v.findViewById(R.id.tv_maxvalue);
		  final EditText et1_taskname=(EditText)v.findViewById(R.id.task_item1_name_et);
		  final EditText et2_taskcreatedtime=(EditText)v.findViewById(R.id.task_item2_createdtime_et);
		  final EditText et3_taskdescription=(EditText)v.findViewById(R.id.task_item3_description_et);
		  final EditText et4_taskimportance=(EditText)v.findViewById(R.id.task_item4_importance_et);
		  final EditText et5_taskurgency=(EditText)v.findViewById(R.id.task_item5_urgency_et);
		  final EditText et6_taskassess=(EditText)v.findViewById(R.id.task_item6_assess_et);
		  final EditText et7_taskpriority=(EditText)v.findViewById(R.id.task_item7_priority_et);
		  final EditText et8_taskpassion=(EditText)v.findViewById(R.id.task_item8_passion_et);
		  final EditText et9_taskdifficulty=(EditText)v.findViewById(R.id.task_item9_difficulty_et);
		  final EditText et10_taskduration=(EditText)v.findViewById(R.id.task_item10_duration_et);
		  final EditText et11_taskachieved=(EditText)v.findViewById(R.id.task_item11_achieved_et);
		  final EditText et12_taskenjoyment=(EditText)v.findViewById(R.id.task_item12_enjoyment_et);
		  final EditText et13_taskexperience=(EditText)v.findViewById(R.id.task_item13_experience_et);
		  final EditText et14_taskstartedtime=(EditText)v.findViewById(R.id.task_item14_startedtime_et);
		  final EditText et15_taskdeadline=(EditText)v.findViewById(R.id.task_item15_deadline_et);
		  final EditText et16_taskprogress=(EditText)v.findViewById(R.id.task_item16_progress_et);
		  final CheckBox cb17_taskreminder=(CheckBox)v.findViewById(R.id.task_item17_reminder_cb);
		  final EditText et18_taskstatus=(EditText)v.findViewById(R.id.task_item18_status_et);
		  final EditText et19_taskfinished=(EditText)v.findViewById(R.id.task_item19_finished_et);
		  final EditText et20_taskdelayed=(EditText)v.findViewById(R.id.task_item20_delayed_et);
		  final TextView tv21_taskmaxperiod=(TextView)v.findViewById(R.id.tv_maxAvailDays);
		  //final ListView m_listview=(ListView)v.findViewById(R.id.task_show_lv);



		  final EditText et_model1_duration_et=(EditText)v.findViewById(R.id.model1_duration_et);
		  final Spinner spin_model1_timeunit=(Spinner)v.findViewById(R.id.model1_timeunit_spin);

		  final List<InputValueSet> list_deadline=new ArrayList<InputValueSet>();
          list_deadline.add(new InputValueSet(1,"今天目标"));
          list_deadline.add(new InputValueSet(2,"明天目标"));
          list_deadline.add(new InputValueSet(3,"本周目标"));
          list_deadline.add(new InputValueSet(4,"下周目标"));
          list_deadline.add(new InputValueSet(5,"本月目标"));
          list_deadline.add(new InputValueSet(6,"下月目标"));
          list_deadline.add(new InputValueSet(7,"选择目标时间"));
         // list_imp.add(new InputValueSet(4,"至关重要"));
		  mArrayAdapter adap_spin_deadline = new mArrayAdapter(getActivity(), R.layout.spinner_timechoice, list_deadline);
		 // adap_spin4_taskimportance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  spin_deadline_model1.setAdapter(adap_spin_deadline);

        final Spinner spin_user_model1=(Spinner) v.findViewById(R.id.model1_user_spin);
        final Spinner spin_user_model2=(Spinner) v.findViewById(R.id.model2_user_spin);
        final List<InputValueSet> list_teammember=new ArrayList<InputValueSet>();
        for (int i=0;i<TaskData.userinfolist_usergroup.size();i++){
            list_teammember.add(new InputValueSet(i,TaskData.userinfolist_usergroup.get(i).get("nickname").toString()));
        }
        mArrayAdapter adapt_spin_user = new mArrayAdapter(getActivity(), R.layout.spinner_timechoice, list_teammember);
        spin_user_model1.setAdapter(adapt_spin_user);
        spin_user_model2.setAdapter(adapt_spin_user);

		  final Spinner spin4_taskimportance = (Spinner)v.findViewById(R.id.task_item4_importance_spin);
		  //final InputValueSet taskimportance[]={new InputValueSet(1,"不重要"),new InputValueSet(2,"2-一般重要"),new InputValueSet(3,"3-非常重要"),new InputValueSet(4,"4-至关重要")};

          final List<InputValueSet> list_imp=new ArrayList<InputValueSet>();
          list_imp.add(new InputValueSet(1,"不重要"));
          list_imp.add(new InputValueSet(2,"一般重要"));
          list_imp.add(new InputValueSet(3,"非常重要"));
         // list_imp.add(new InputValueSet(4,"至关重要"));
		  mArrayAdapter adap_spin4_taskimportance = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_imp);
		 // adap_spin4_taskimportance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  spin4_taskimportance.setAdapter(adap_spin4_taskimportance);

		  final Spinner spin5_taskurgency = (Spinner)v.findViewById(R.id.task_item5_urgency_spin);
		 //final String urgency[]={"1-不紧急","2-一般紧急","3-非常紧急","4-十万火急"};
		 final List<InputValueSet> list_urg=new ArrayList<InputValueSet>();
         list_urg.add(new InputValueSet(1,"不紧急"));
         list_urg.add(new InputValueSet(2,"一般紧急"));
         list_urg.add(new InputValueSet(3,"非常紧急"));
        // list_urg.add(new InputValueSet(4,"十万火急"));
		 mArrayAdapter adap_spin5_taskurgency = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_urg);
		 // adap_spin5_taskurgency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  spin5_taskurgency.setAdapter(adap_spin5_taskurgency);
		 final Spinner spin8_taskpassion = (Spinner)v.findViewById(R.id.task_item8_passion_spin);
		 //final String passion[]={"1-无兴趣","2-一般兴趣","3-很感兴趣","4-强烈兴趣"};
		 final List<InputValueSet> list_passion=new ArrayList<InputValueSet>();
         list_passion.add(new InputValueSet(1,"无兴趣"));
         list_passion.add(new InputValueSet(2,"一般兴趣"));
         list_passion.add(new InputValueSet(3,"很感兴趣"));
        // list_passion.add(new InputValueSet(4,"强烈兴趣"));
		 mArrayAdapter adap_spin8_taskpassion = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_passion);
			//  adap_spin8_taskpassion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			  spin8_taskpassion.setAdapter(adap_spin8_taskpassion);

			     final Spinner spin9_taskdifficulty = (Spinner)v.findViewById(R.id.task_item9_difficulty_spin);
				 final String difficulty[]={"1-无困难","2-一般困难","3-很有困难","4-极其困难"};
				 final List<InputValueSet> list_difficulty=new ArrayList<InputValueSet>();
				 list_difficulty.add(new InputValueSet(1,"无困难"));
		         list_difficulty.add(new InputValueSet(2,"一般困难"));
		         list_difficulty.add(new InputValueSet(3,"非常困难"));
		      //   list_difficulty.add(new InputValueSet(4,"极其困难"));
		         mArrayAdapter adap_spin9_taskdifficulty = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_difficulty);
				 // adap_spin9_taskdifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				  spin9_taskdifficulty.setAdapter(adap_spin9_taskdifficulty);


				  final Spinner spin_timeunit = (Spinner)v.findViewById(R.id.timeunit_spin);
					 //final String timeUnit[]={"1-全天","2-小时","3-%"};
					 final List<InputValueSet> list_timeUnit=new ArrayList<InputValueSet>();
					 list_timeUnit.add(new InputValueSet(1,"全天"));
			         list_timeUnit.add(new InputValueSet(2,"小时"));
			         list_timeUnit.add(new InputValueSet(3,"分钟"));
			      //   list_difficulty.add(new InputValueSet(4,"极其困难"));
			    mArrayAdapter adap_spin_timeUnit = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_timeUnit);
					 // adap_spin9_taskdifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					  spin_timeunit.setAdapter(adap_spin_timeUnit);

				mArrayAdapter adap_spin_timeUnit_model1 = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_timeUnit);
						 // adap_spin9_taskdifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spin_model1_timeunit.setAdapter(adap_spin_timeUnit_model1);


		 Button btn_confirm=(Button)v.findViewById(R.id.task_confirm_bt);
		 Button btn_cancel=(Button)v.findViewById(R.id.task_cancel_bt);
		 Button btn_close = (Button) v.findViewById(R.id.task_close_bt);


	     btn_cancel.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View arg0) {
	                // 关闭对话框
	                dismiss();
	            }
	        });

		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		    //final Cursor c = db.query(TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		    //Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		    final ContentValues cv = new ContentValues();
		//  final int click_position;
		//  final SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.show_alltask, c, new String[] {TdDB.TASK_ID,TdDB.TASK_NAME,TdDB.TASK_IMPORTANCE},new int[]{R.id.task1_id_tv,R.id.task1_name_tv,R.id.task1_importance_tv}, 0);
		 // Log.d("adapter",adapter.toString() );
		//  m_listview.setAdapter(adapter);
  	    //Log.d("show data","ok" +m_listview.toString());
		    tv2_taskcreatedtime.setText(curTime);
		  /*  final TextView[] et_task={
		    		et1_taskname,
		    		tv2_taskcreatedtime,
		    		et3_taskdescription,
		    		et6_taskassess,
		    		et7_taskpriority,
		    		et10_taskduration,
		    		et11_taskachieved,
		    		et12_taskenjoyment,
		    		et13_taskexperience,
		    		et14_taskstartedtime,
		    		et15_taskdeadline,
		    		et16_taskprogress,
		    		et17_taskreminder,
		    		et18_taskstatus,
		    		et19_taskfinished,
		    		et20_taskdelayed};
	        };
		    final Spinner[] spin_task={
		    		spin4_taskimportance,
		    		spin5_taskurgency,
		    		spin8_taskpassion,
		    		spin9_taskdifficulty
		    };
		    */

		    //final EditText usedTimeEachWeek_et=(EditText)v.findViewById(R.id.usedTimeEachWeek_et);

		    spin_timeunit.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
					// TODO Auto-generated method stub
					//usedTimeEachWeek_et.setText(list_timeUnit.get(pos).toString());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stubn /////////////'[]\


				}

		    });


		    tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
            {
					@Override
					public void onTabChanged(String arg0) {

					// TODO Auto-generated method stub
					switch (tabhost.getCurrentTab()){
					case 0:
						tv_taskcreatedtime_model1.setText(curTime);

                       break;
					case 1:
						   break;
					default:break;
					}
				 }
            });

		    tv_taskcreatedtime_model1.setText(curTime);
		    model1_taskteam_tv.setText("当前团队-"+TaskData.usergroup);
	    	model2_taskteam_tv.setText("当前团队-"+TaskData.usergroup);
		    spin_deadline_model1.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub

					int selItem_deadline=spin_deadline_model1.getSelectedItemPosition();
					//Toast.makeText(getActivity(), "点击了"+selItem_deadline, Toast.LENGTH_SHORT).show();
					switch (selItem_deadline){
		   			case 0:
		   				   Calendar cal = Calendar.getInstance();

		                   int year0 = cal.get(Calendar.YEAR);
		   		           int month0 = cal.get(Calendar.MONTH);
		   		           int day0=cal.get(Calendar.DAY_OF_MONTH);
		   				   String str_deadline0 = year0 + "-" + (month0+1) + "-" + day0+" "+dueHour+":"+dueMin;
		   			       deadlinetimedata_model1 = TimeData.changeStrToTime_YYYY(str_deadline0);
		   			       deadline_model1=TimeData.convertTime_YYYYTIMEtoYYTIME(str_deadline0);
		   			       deadlinedate_model1=TimeData.yyMdHHmm2yyMMdd(str_deadline0);
		   			       //model1_duration=1;
		   			       //model1_durationunit=1;
		   			       tv_taskdeadline_model1.setText(deadline_model1);
		   			       break;
		   			case 1:
		   				   Calendar cal1 = Calendar.getInstance();

		                   int year1 = cal1.get(Calendar.YEAR);
		   		           int month1 = cal1.get(Calendar.MONTH);
		   		           int day1=cal1.get(Calendar.DAY_OF_MONTH);
		   				   String str_deadline1 = year1 + "-" + (month1+1) + "-" + (day1+1)+" "+dueHour+":"+dueMin;
		   			       deadlinetimedata_model1 = TimeData.changeStrToTime_YYYY(str_deadline1);
		   			       deadline_model1=TimeData.convertTime_YYYYTIMEtoYYTIME(str_deadline1);
		   			       deadlinedate_model1=TimeData.yyMdHHmm2yyMMdd(str_deadline1);
		   			       //model1_duration=1;
		   			       //model1_durationunit=1;
		   			       tv_taskdeadline_model1.setText(deadline_model1);
		   			       break;
		   			case 2:
		   				   Calendar cal2 = Calendar.getInstance();
		                    cal2.set(Calendar.DAY_OF_WEEK,
		                     cal2.getActualMaximum(Calendar.DAY_OF_WEEK));
		                    int year2 = cal2.get(Calendar.YEAR);
		   	               int month2 = cal2.get(Calendar.MONTH);
		   	               int day2=cal2.get(Calendar.DAY_OF_MONTH);
		   			       String str_deadline2 = year2 + "-" + (month2+1) + "-" + day2+" "+dueHour+":"+dueMin;
		   		           deadlinetimedata_model1 = TimeData.changeStrToTime_YYYY(str_deadline2);
		   		           deadline_model1=TimeData.convertTime_YYYYTIMEtoYYTIME(str_deadline2);
		   		           deadlinedate_model1=TimeData.yyMdHHmm2yyMMdd(str_deadline2);
		   		           //model1_duration=6;
		   		           //model1_durationunit=1;
		   		           tv_taskdeadline_model1.setText(deadline_model1);
		   		           break;
		   			case 3:Calendar cal3 = Calendar.getInstance();
                           cal3.set(Calendar.DAY_OF_WEEK,
                           cal3.getActualMaximum(Calendar.DAY_OF_WEEK));
                           cal3.add(Calendar.DAY_OF_MONTH,+7);
                           int year3 = cal3.get(Calendar.YEAR);
   	                       int month3 = cal3.get(Calendar.MONTH);
   	                       int day3=cal3.get(Calendar.DAY_OF_MONTH);
   			               String str_deadline3 = year3 + "-" + (month3+1) + "-" + day3+" "+dueHour+":"+dueMin;
   		                   deadlinetimedata_model1 = TimeData.changeStrToTime_YYYY(str_deadline3);
   		                   deadline_model1=TimeData.convertTime_YYYYTIMEtoYYTIME(str_deadline3);
   		                   deadlinedate_model1=TimeData.yyMdHHmm2yyMMdd(str_deadline3);
   		                   //model1_duration=6;
		   		           //model1_durationunit=1;
   		                   tv_taskdeadline_model1.setText(deadline_model1);
   		                   break;
		   			case 4:Calendar cal4 = Calendar.getInstance();
		                   cal4.set(Calendar.DAY_OF_MONTH,
		                   cal4.getActualMaximum(Calendar.DAY_OF_MONTH));
		                   int year4 = cal4.get(Calendar.YEAR);
		   	               int month4 = cal4.get(Calendar.MONTH);
		   	               int day4=cal4.get(Calendar.DAY_OF_MONTH);
		   			       String str_deadline4 = year4 + "-" + (month4+1) + "-" + day4+" "+dueHour+":"+dueMin;
		   		           deadlinetimedata_model1 = TimeData.changeStrToTime_YYYY(str_deadline4);
		   		           deadline_model1=TimeData.convertTime_YYYYTIMEtoYYTIME(str_deadline4);
		   		           deadlinedate_model1=TimeData.yyMdHHmm2yyMMdd(str_deadline4);
		   		           //model1_duration=22;
		   		           //model1_durationunit=1;
		   		           tv_taskdeadline_model1.setText(deadline_model1);
		   		           break;
		   			case 5:Calendar cal5 = Calendar.getInstance();
                            cal5.set(Calendar.DAY_OF_MONTH,
                            cal5.getActualMaximum(Calendar.DAY_OF_MONTH));
                            int year5 = cal5.get(Calendar.YEAR);
  	                        int month5 = cal5.get(Calendar.MONTH);
  	                        int day5=cal5.get(Calendar.DAY_OF_MONTH);
  			                String str_deadline5 = year5 + "-" + (month5+2) + "-" + day5+" "+dueHour+":"+dueMin;
  		                    deadlinetimedata_model1 = TimeData.changeStrToTime_YYYY(str_deadline5);
  		                    deadline_model1=TimeData.convertTime_YYYYTIMEtoYYTIME(str_deadline5);
  		                    deadlinedate_model1=TimeData.yyMdHHmm2yyMMdd(str_deadline5);
  		                    //model1_duration=22;
		   		            //model1_durationunit=1;
  		                    tv_taskdeadline_model1.setText(deadline_model1);
  		                    break;
		   			case 6:tv_taskdeadline_model1.setText("点击选择时间");
		   		            DialogFragment_TimeInput dg_time=new DialogFragment_TimeInput(tv_taskdeadline_model1,0);
	    	                TaskTool.showDialog(dg_time);



		   			default:break;
					}

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}


		    });



		    tv_taskdeadline_model1.setOnClickListener(new OnClickListener() {
		    	  
		    			    	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DialogFragment_TimeInput dg_time=new DialogFragment_TimeInput(tv_taskdeadline_model1,1);
	    	    	TaskTool.showDialog(dg_time);
				}   
		    	      
		    });
           	
		    
			tv_taskdeadline_model1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    	  
		    			    	
		    	   @Override
		    	   public void onFocusChange(View v, boolean hasFocus) {
		    	       if (hasFocus) {
		    	    	DialogFragment_TimeInput dg_time=new DialogFragment_TimeInput(tv_taskdeadline_model1,1);
		    	    	TaskTool.showDialog(dg_time);
		    	    	
		    	       }
		    	   }   
		    	      
		    });
		
			
            
            
            
			TaskData.ls1.clear();
		    TaskData.ls1=new ArrayList<RecordBean>(); 
		    Iterator it1 = TaskData.ls1.iterator();
		    lvadapter_model1=new lvBaseAdapter_AddTaskModel1(getActivity(),TaskData.ls1);
		    lv_quickinput_model1.setAdapter(lvadapter_model1);
		    
		    model1_taskadd_btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					   //TaskData.ls1.clear();
				  if (!TextUtils.isEmpty(et_checklist_model1.getText().toString())) {
					  RecordBean recordbean = new RecordBean();
						
						recordbean.setRECORD_COMMENTS(et_checklist_model1.getText().toString());
						recordbean.setRECORD_CREATEDTIME(curTime);
						recordbean.setRECORD_DONE("false");
						recordbean.setRECORD_TYPE("checklist");
						recordbean.setRECORD_VALIDITY("true");
						TaskData.ls1.add(recordbean);
						lvadapter_model1.notifyDataSetChanged();
						 Log.d(Tags+"|recordbean ls1|","ls1"+TaskData.ls1.size());
						 et_checklist_model1.setText("");
 		         }else{	
 		        	Toast.makeText(getActivity(),"内容为空" , Toast.LENGTH_SHORT).show();
 		           }	
				}
				  
		      });
		    
		    
		    
		    model1_task_confirm_bt.setOnClickListener(new OnClickListener(){

		    	double plannedtime_model1;
		    	
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				  String inputstr_taskname =et_taskname_model1.getText().toString().trim();
				if (TextUtils.isEmpty(inputstr_taskname)) {
								Toast.makeText(getActivity(),"任务名不能为空" , Toast.LENGTH_SHORT).show();
		 		}else{	
					ContentValues cv1 = new ContentValues();
					double daygap=TimeData.DaySpace_YY(curTime,tv_taskdeadline_model1.getText().toString());
					//Toast.makeText(getActivity(),"daygap"+daygap, Toast.LENGTH_SHORT).show();
					if (daygap>=1){
					    dm1=daygap;
	   		            model1_durationunit=1;
	   		            plannedtime_model1=daygap*TaskData.prehour;
					}else{
						long min_gap=TimeData.changeStrToTime_YY(tv_taskdeadline_model1.getText().toString())-(new Date().getTime())/1000/60;
					    double mDuration = (double)(min_gap/60);
					    Log.d("mDuration", ""+mDuration);
					    if (mDuration>TaskData.prehour){
					    	dm1=TaskData.prehour;
					    	 plannedtime_model1=dm1;
					    }else{
					       if (mDuration>0){
					    	  dm1=mDuration;
					    	  plannedtime_model1=dm1;
					       }else{
					    	  dm1=0;
						      plannedtime_model1=dm1; 
					       }
					    }
					    model1_durationunit=2;
					    Log.d("model1_duration", "dm1:"+dm1+" model1_durationunit:"+model1_durationunit);
					}
					//double dm1 = (double)(TimeData.DaySpace_YY(tv_taskdeadline_model1.getText().toString(),curTime));
					//df_duration.format(dm1);
					//Toast.makeText(getActivity(),"duration"+df_duration.format(dm1), Toast.LENGTH_SHORT).show();
					String reminder_model1 = String.valueOf(model1_reminder_cb.isChecked());
				
				
				    String sn_deadline_model1=TimeData.convertShortDate_YYMdtoYYYYMMdd( deadlinedate_model1);
				   
				    //DecimalFormat df_sn = new DecimalFormat("0.0000");//格式化小数，.后跟几个零代表几位小数 
				    //float dt=((float)plannedtime_model1/10000);
				    //String sn_duration_model1 = df_sn.format(dt);
				    
				    String sn_duration_model1;
				    if (plannedtime_model1>0){
				        sn_duration_model1=TaskTool.addZero((int)plannedtime_model1*10, 6);
				    }else{
				    	sn_duration_model1="00000";
				    }	
				   
				    int model1value_taskimportance=2;
				    int model1value_taskurgency=2;
				    
				    switch (model1value_taskimportance){
				    	case 3:sn_importance="1";
				    			break;
				    	case 2:sn_importance="2";
				    			break;
				    	case 1:sn_importance="3";
				    			break;
				    	default:break;   
				    }
				    
				    switch (model1value_taskurgency){
				    	case 3:sn_urgency="1";
				    			break;
				    	case 2:sn_urgency="2";
				    	break;
				    	case 1:sn_urgency="3";
				    			break;
				    	default:break;   
				    }
			
				    //sn_sequence=sn_deadline+sn_importance+sn_urgency+sn_duration;
				       String sn_rank="2";
				       sn_sequence=sn_deadline_model1+sn_rank+sn_importance+sn_urgency+sn_duration_model1;
	    
				    //long float_sn_sequence = Long.parseLong(sn_sequence);
				    //Log.d(Tags+"|insert data|", "sn_sequence:"+sn_sequence+" long:"+float_sn_sequence);
				    //tv_newtaskheader.setText(sn_sequence);
					
					
					cv1.put(TaskData.TdDB.TASK_STARTEDTIME, curTime);
			        //cv1.put("_sn",1);
					cv1.put(TaskData.TdDB.TASK_PROGRESS,0);
					cv1.put(TaskData.TdDB.TASK_NAME, et_taskname_model1.getText().toString());
				    cv1.put(TaskData.TdDB.TASK_DEADLINE,  tv_taskdeadline_model1.getText().toString());
				    cv1.put(TaskData.TdDB.TASK_DEADLINEDATE,TimeData.convertDataNoTime_YY(tv_taskdeadline_model1.getText().toString()));
				    cv1.put(TaskData.TdDB.TASK_DEADLINETIMEDATA,TimeData.changeStrToTime_YY(tv_taskdeadline_model1.getText().toString()));
					cv1.put(TaskData.TdDB.TASK_CREATEDTIME, curTime);
					cv1.put(TaskData.TdDB.TASK_IMPORTANCE, list_imp.get(1).getNo());
					cv1.put(TaskData.TdDB.TASK_IMPORTANCEDETAIL, list_imp.get(1).getValue());
					cv1.put(TaskData.TdDB.TASK_URGENCY, list_urg.get(1).getNo());
					cv1.put(TaskData.TdDB.TASK_URGENCYDETAIL, list_urg.get(1).getValue());
					cv1.put(TaskData.TdDB.TASK_ASSESSMENT, list_imp.get(1).getNo()*list_urg.get(1).getNo());
					cv1.put(TaskData.TdDB.TASK_PRIORITY, "A4");
					cv1.put(TaskData.TdDB.TASK_DURATION, df_duration.format(dm1));
					cv1.put(TaskData.TdDB.TASK_DURATIONUNIT, model1_durationunit);
					cv1.put(TaskData.TdDB.TASK_DIFFICULTY, list_difficulty.get(1).getNo());
					cv1.put(TaskData.TdDB.TASK_DIFFICULTYDETAIL, list_difficulty.get(1).getValue());
					cv1.put(TaskData.TdDB.TASK_PASSION, list_passion.get(1).getNo());
					cv1.put(TaskData.TdDB.TASK_PASSIONDETAIL, list_passion.get(1).getValue());
					//cv1.put(TaskData.TdDB.TASK_ACHIEVED, list_imp.get(1).getNo()*dm1);
					//cv1.put(TaskData.TdDB.TASK_EXPERIENCE, list_difficulty.get(1).getNo()*dm1);
					//cv1.put(TaskData.TdDB.TASK_ENJOYMENT, list_passion.get(1).getNo()*dm1);
					cv1.put(TaskData.TdDB.SUM_ACCOMPLISHED, 0);
					cv1.put(TaskData.TdDB.SUM_ACHIEVED, 0);
					cv1.put(TaskData.TdDB.SUM_CONTRIBUTION, 0);
					cv1.put(TaskData.TdDB.SUM_EXPERIENCE, 0);
					cv1.put(TaskData.TdDB.SUM_ENJOYMENT, 0);
					cv1.put(TaskData.TdDB.TASK_DELAYED, 0);
					cv1.put(TaskData.TdDB.TASK_REMINDER, reminder_model1);
					cv1.put(TaskData.TdDB.TASK_DELAYEDDAYS, 0);
					cv1.put(TaskData.TdDB.TASK_RECORDCOUNT, TaskData.ls1.size());
					cv1.put(TaskData.TdDB.TASK_USER, TaskData.userinfolist_usergroup.get(spin_user_model1.getSelectedItemPosition()).get("phoneNo").toString());
                    cv1.put(TaskData.TdDB.TASK_USERNICKNAME, TaskData.userinfolist_usergroup.get(spin_user_model1.getSelectedItemPosition()).get("nickname").toString());
                    cv1.put(TaskData.TdDB.TASK_OWNER, TaskData.userinfolist_usergroup.get(spin_user_model1.getSelectedItemPosition()).get("phoneNo").toString());
                    cv1.put(TaskData.TdDB.TASK_OWNERNICKNAME, TaskData.userinfolist_usergroup.get(spin_user_model1.getSelectedItemPosition()).get("nickname").toString());
                    cv1.put(TaskData.TdDB.TASK_CREATOR, TaskData.user);
                    cv1.put(TaskData.TdDB.TASK_CREATORNICKNAME, TaskData.usernickname);
					cv1.put(TaskData.TdDB.TASK_USERGROUP, TaskData.usergroup);
					cv1.put(TaskData.TdDB.TASK_STATUS, "open");
					cv1.put(TaskData.TdDB.TASK_SEQUENCE, sn_sequence);
					cv1.put(TaskData.TdDB.TASK_HISTORY, "任务创建"+TaskData.tag_taskhistory);
					;
					//int curRecordCount = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_RECORDCOUNT)));
					
					TaskData.db_TdDB.insert(TaskData.TdDB.TABLE_NAME_TaskMain, null, cv1);
					Cursor c_taskno_model1=TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID() from "+TaskData.TdDB.TABLE_NAME_TaskMain ,null);
					c_taskno_model1.moveToFirst();
					int taskid = c_taskno_model1.getInt(0);
					//String tasksn = c_taskno_model1.getString(c_taskno_model1.getColumnIndex(TaskData.TdDB.TASK_SN));
					//Log.d("insert in model","done "+taskid);
					
					ContentValues cv3 = new ContentValues();
					String t_sn=TaskData.user+"0"+TaskTool.addZero(taskid,10);
					 cv3.put("_sn",t_sn);
					// String where_task=TaskData.TdDB.TASK_ID+"=?";
					 //String[] where_taskid={String.valueOf(taskid)};
					 //TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv3, where_task, where_taskid);
					 TaskData.db_TdDB.execSQL("update "+TaskData.TdDB.TABLE_NAME_TaskMain+ " set "+TaskData.TdDB.TASK_SN+"="+"'"+t_sn+"'"+" where "+TaskData.TdDB.TASK_ID+"="+taskid+";");
				 if (TaskData.ls1.size()>0){	 
				    for(int i=0;i<TaskData.ls1.size();i++){
				    	if (TaskData.ls1.get(i).getRECORD_VALIDITY().equals("false")){
				    		TaskData.ls1.remove(i);
				    		Log.d(Tags+"|ls delete|", ""+i);
				    	}
				    }
				  
				if (TaskData.ls1.size()>0){
					 //String subweight=new DecimalFormat("0.0").format((double)(TaskData.preweight/TaskData.ls1.size()));
					 String subweight=String.valueOf(TaskData.preweight/TaskData.ls1.size());
					 ContentValues cv2 = new ContentValues();
					 int j=0;
				   	for(int i=0;i<TaskData.ls1.size();i++){
						if (TaskData.ls1.get(i).getRECORD_VALIDITY().equals("true")){
				    		
						 //cv2.put(TaskData.TdDB.RECORD_TASKID_NO,""+taskid+"_C"+(j+1));
						 cv2.put(TaskData.TdDB.RECORD_TASKID_NO,"P"+(j+1));
						 cv2.put(TaskData.TdDB.RECORD_TASKID,String.valueOf(taskid));
						 cv2.put(TaskData.TdDB.TASK_SN,t_sn);
						 cv2.put(TaskData.TdDB.RECORD_SN,  t_sn+TaskTool.addZero(j+1,4));
						 cv2.put(TaskData.TdDB.TASK_USER,  TaskData.user);
						 cv2.put(TaskData.TdDB.TASK_USERGROUP, TaskData.usergroup);
						 cv2.put(TaskData.TdDB.TASK_OWNER, TaskData.user);
						 cv2.put(TaskData.TdDB.RECORD_COMMENTS,((RecordBean)TaskData.ls1.get(i)).getRECORD_COMMENTS());
						 cv2.put(TaskData.TdDB.RECORD_CREATEDTIME,((RecordBean)TaskData.ls1.get(i)).getRECORD_CREATEDTIME());
						 cv2.put(TaskData.TdDB.RECORD_PROGRESS,0);
						
						 cv2.put(TaskData.TdDB.RECORD_DEADLINE, tv_taskdeadline_model1.getText().toString());
						 cv2.put(TaskData.TdDB.RECORD_DONE,"false");
						 if (i!=(TaskData.ls1.size()-1))
						   cv2.put(TaskData.TdDB.RECORD_WEIGHT,subweight);
						 else{
						   double lastsubweight = TaskData.preweight-(TaskData.ls1.size()-1)*Double.parseDouble(subweight);
						   //Toast.makeText(getActivity(), ""+lastsubweight, Toast.LENGTH_SHORT).show();
						   cv2.put(TaskData.TdDB.RECORD_WEIGHT,new DecimalFormat("0").format(lastsubweight)); 
						 }
						 //cv2.put(TaskData.TdDB.RECORD_TASKID_NO,((RecordBean)TaskData.ls1.get(i)).getRECORD_TASKID_NO());
						 //String where=TaskData.TdDB.RECORD_SN+"=?";
						 //String`[] whereArgs={String.valueOf(taskid)};
						 //Log.d("cv2 taskrecord",""+((RecordBean)TaskData.ls1.get(i)).getRECORD_TASKID());
						 //Log.d("cv2 taskrecord",((RecordBean)TaskData.ls1.get(i)).getRECORD_COMMENTS());
						 //Log.d("cv2 taskrecord",((RecordBean)TaskData.ls1.get(i)).getRECORD_CREATEDTIME());					 
						 TaskData.db_TdDB.insert(TaskData.TdDB.TABLE_NAME_TaskRecord,null,cv2); 
						 //Log.d("insert taskrecord","item"+i+"done "+taskid);
						 j++;
						}
					  }
				   }
				 }	     
					
					//Cursor c= TaskData.db_taskRecord.rawQuery("select * form "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.TASK_ID+"=?",new String[]{String.valueOf(taskid)});
					 // lvadapter_model1=new lvBaseAdapter_AddTaskModel1(getActivity(),TaskData.ls1);
					  //lv_quickinput_model1.setAdapter(lvadapter_model1);
					//lvadapter_model1.getCursor().requery();
					//lvadapter_model1.notifyDataSetChanged();
					//Log.d("update taskrecord","done "+taskid);
	
					
				     if (reminder_model1=="true"){
		   
				     TaskTool.setReminder(getActivity(),tv_taskdeadline_model1.getText().toString(), 
				    		 "T"+taskid,
			    			 et_taskname_model1.getText().toString(),
			    			 null);
				     }
				     
					 TaskData.getSequenceNo();
					
					    Toast.makeText(getActivity(), "任务添加成功",Toast.LENGTH_SHORT).show();
						TaskData.adapterUpdate();
					    TaskTool.queryTaskDetail(t_sn);
						//TaskData.ls1.clear();
						dismiss();  
				     
		 		 }    
				}
				
		    });
		    
		    model1_task_cancel_bt.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					dismiss();
					
				}
		    });	
		    
		    et15_taskdeadline.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    	  
		    	   @Override
		    	   public void onFocusChange(View v, boolean hasFocus) {
		    	       if (hasFocus) {
		    	    	DialogFragment_TimeInput dg_time=new DialogFragment_TimeInput(et15_taskdeadline,0);
		    	    	TaskTool.showDialog(dg_time);
		    	    	//deadline=dg_time.GetDeadline();
		    	    	//date1=dg_time.GetDate();
		    	    	//Toast.makeText(getActivity(), deadline+"\n"+date1, Toast.LENGTH_LONG).show(); 
		    	    	//taskdeadlineTimeData=TimeData.changeStrToTime_YYYY(deadline);
	                       //taskdeadlinetimestamp=Timestamp.valueOf(deadline);
	                     //  taskdeadlineDate=TimeData.convertDate_YYYYtoYY(date1);
	                     //  sn_deadline=TimeData.convertShortDate_YYYYMdtoYYYYMMdd(date1);
	                     //  et15_taskdeadline.setText(TimeData.convertTime_YYYYTIMEtoYYTIME(deadline));
		    	       }
		    	      
		    	     }  
		    	   });
	   
		    
		    et14_taskstartedtime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    	
		    	   @Override
		    	   public void onFocusChange(View v, boolean hasFocus) {
		    	       if (hasFocus) {
		    	    	   DialogFragment_TimeInput dg_startedtime=new DialogFragment_TimeInput(et14_taskstartedtime,0);
			    	       TaskTool.showDialog(dg_startedtime);
		    	    	   
		    	       }	   
		    	   }
		    	});
		    
		    class EditChangedListener implements TextWatcher {  
		        private CharSequence temp;//监听前的文本  
		        private int editStart;//光标开始位置  
		        private int editEnd;//光标结束位置  
		        private final int charMaxNum = 10;  
		   
		        @Override  
		        public void beforeTextChanged(CharSequence s, int start, int count, int after) {  
		           
		            temp = s;  
		        }  

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
					//String str1 = curTime;  //"yyyyMMdd"格式 如 20151022
					//System.out.println("\n结束时间:");  
					String str2 = s.toString();  //"yyyyMMdd"格式 如 20131022
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");//输入日期的格式 
					Date date1 = null;
					
					Date date2 = null;
					try {
					date2 = simpleDateFormat.parse(str2);
					} catch (ParseException e) {
					e.printStackTrace();
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
					//GregorianCalendar cal1 = new GregorianCalendar();  
					GregorianCalendar cal2 = new GregorianCalendar();  
					//cal1.setTime(date1);  
					cal2.setTime(date2);  
					double dayCount = (cal2.getTimeInMillis()-System.currentTimeMillis())/(1000*3600*24);
				
				     /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */  
			           editStart = et15_taskdeadline.getSelectionStart();  
			           editEnd = et15_taskdeadline.getSelectionEnd();    
			               int tempSelection = editStart;  
			               et15_taskdeadline.setText(s);  
			               et15_taskdeadline.setSelection(tempSelection);  
						
					//tv_maxvalue.setText(s);
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}

				
		    };     
	    
		
	       /*
	        et15_taskdeadline.setOnClickListener(new OnClickListener() {
	        	 Calendar c = Calendar.getInstance();
	        	@Override
	           
	            public void onClick(View v) {
	                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
	                    @Override
	                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	                        c.set(year, monthOfYear, dayOfMonth);
	                        et15_taskdeadline.setText(DateFormat.format("yyy-MM-dd", c));
	                    }
	                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
	                dialog.show();
	            }
	        });  
		    */
		   
		  cb17_taskreminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
			     if(isChecked){
			    	 taskreminder="true";
			       
			     }else{
			    	 taskreminder="false";
			     }
			}		  
		  }); 

		  
		  TextWatcher tw_duration = new TextWatcher() {  
		        private CharSequence temp;//监听前的文本  
		        private int editStart;//光标开始位置  
		        private int editEnd;//光标结束位置  
		        private final int charMaxNum = 10;  
		   
		        @Override  
		        public void beforeTextChanged(CharSequence s, int start, int count, int after) {  
		           
		            temp = s;  
		        }  

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
				  if (!TextUtils.isEmpty(s.toString().trim())&&TaskTool.isNumeric(s.toString().trim())){
					 double temp_duration = 0;
					 double maxDuration = 0;
					 String durationunit = null;
					 String startedTime_YY;
					 String endTime_YY;
					 if (TaskData.durationcheck==true){
						
					      endTime_YY=et15_taskdeadline.getText().toString().trim();
				       if (!TextUtils.isEmpty(endTime_YY)){
						 
					       switch (spin_timeunit.getSelectedItemPosition()+1){
			          
					   		case 1: //hr_timeunit=8;
					            temp_duration = Double.parseDouble(s.toString());
					            if (!TextUtils.isEmpty(et14_taskstartedtime.getText().toString().trim())){
					            	startedTime_YY=et14_taskstartedtime.getText().toString().trim();
					            }else{
					            	startedTime_YY = TaskTool.getCurTime();
					            }
					            endTime_YY=et15_taskdeadline.getText().toString().trim();
					            maxDuration=TimeData.DaySpace_YY(startedTime_YY,endTime_YY);
					            durationunit="天";
					            tv_maxvalue.setText("Max:"+maxDuration+"天");
					            Log.d(Tags,"Max:"+df_duration.format(maxDuration)+"天");
					            break;	
					   	   case 2: //hr_timeunit=1;
						        temp_duration = Double.parseDouble(s.toString());
						        if (!TextUtils.isEmpty(et14_taskstartedtime.getText().toString().trim())){
					            	startedTime_YY=et14_taskstartedtime.getText().toString().trim();
					            }else{
					            	startedTime_YY = TaskTool.getCurTime();
					            }
						        endTime_YY=et15_taskdeadline.getText().toString().trim();
						        maxDuration=(double)TimeData.TimeGap_YYMMDDHHSS(startedTime_YY,endTime_YY)/(1000*60*60);;
						        durationunit="小时";
						        tv_maxvalue.setText("Max:"+df_duration.format(maxDuration)+"小时"); 
						        Log.d(Tags,"Max:"+maxDuration+"小时");
						        break;	
					   	   case 3: //hr_timeunit=(double)1/60;
						        temp_duration = Double.parseDouble(s.toString());
						        if (!TextUtils.isEmpty(et14_taskstartedtime.getText().toString().trim())){
					            	startedTime_YY=et14_taskstartedtime.getText().toString().trim();
					            }else{
					            	startedTime_YY = TaskTool.getCurTime();
					            }
						        endTime_YY=et15_taskdeadline.getText().toString().trim();
				                maxDuration=(double)TimeData.TimeGap_YYMMDDHHSS(startedTime_YY,endTime_YY)/(1000*60);
				                durationunit="分";
				                tv_maxvalue.setText("Max:"+df_duration.format(maxDuration)+"分"); 
				                Log.d(Tags,"Max:"+maxDuration+"分");
						        break;	
					   	   default:break;	
						}	
					   
					    if (temp_duration> maxDuration){
						 Toast.makeText(getActivity(), "超过MAX"+df_duration.format(maxDuration)+durationunit,Toast.LENGTH_SHORT).show();
						 et10_taskduration.setText("");
					   }
				      }else{
						   Toast.makeText(getActivity(), "请先输入最后期限",Toast.LENGTH_SHORT).show();
					  }  
				     }  
				  }else{
					  Toast.makeText(getActivity(), "请输入数值",Toast.LENGTH_SHORT).show();
				  }
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}

				
		    };     
			
		    et10_taskduration.addTextChangedListener(tw_duration);
		  
		  btn_confirm.setOnClickListener(new OnClickListener(){
           
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				String inputstr_taskdeadline = et15_taskdeadline.getText().toString().trim();
				String inputstr_taskname = et1_taskname.getText().toString().trim();
				String inputstr_startedtime = et14_taskstartedtime.getText().toString().trim();
				String inputstr_duration=et10_taskduration.getText().toString().trim();
				  
	             if (TextUtils.isEmpty(inputstr_startedtime )){		
				       taskstartedtime=new SimpleDateFormat ("yy-MM-dd HH:mm").format(new Date());
			      }else{
				       taskstartedtime=et14_taskstartedtime.getText().toString();
			      }
				
				boolean nameflag = TextUtils.isEmpty(et1_taskname.getText().toString().trim());
				boolean deadlineflag=TextUtils.isEmpty(et15_taskdeadline.getText().toString().trim());
				boolean durationflag=TextUtils.isEmpty(et10_taskduration.getText().toString().trim());
				boolean durationcheck=false;
				
				if (nameflag){
					Toast.makeText(getActivity(),"任务名不能为空" , Toast.LENGTH_SHORT).show();
				}
				
				if (deadlineflag){
					Toast.makeText(getActivity(),"最后期限不能为空" , Toast.LENGTH_SHORT).show();
				}
				
				if (durationflag){
					Toast.makeText(getActivity(),"预计用时不能为空" , Toast.LENGTH_SHORT).show();
				}else{
					if (TaskTool.isNumeric(inputstr_duration)){
				
						if (Double.parseDouble(inputstr_duration)>=0){
							durationcheck=true;	
						}else{
							Toast.makeText(getActivity(),"预计用时必须为≥0数字" , Toast.LENGTH_SHORT).show();
						}
						
					}else{
						Toast.makeText(getActivity(),"预计用时必须为≥0数字" , Toast.LENGTH_SHORT).show();
					}	
				}
				
				boolean totalflag=(!nameflag)&&(!deadlineflag)&&(!durationflag)&&durationcheck;
				
				if (totalflag) {

					    task_maxperiod=String.valueOf(TimeData.DaySpace_YY(taskstartedtime,inputstr_taskdeadline));
					    tv21_taskmaxperiod.setText(task_maxperiod);
				
					    int value_taskimportance = spin4_taskimportance.getSelectedItemPosition()+1;
					    int value_taskurgency=spin5_taskurgency.getSelectedItemPosition()+1;
					    int value_taskpassion=spin8_taskpassion.getSelectedItemPosition();
					    int value_taskdifficulty=spin9_taskdifficulty.getSelectedItemPosition()+1;
					    double value_taskduration=Double.parseDouble(et10_taskduration.getText().toString());
					    int value_taskdurationunit=spin_timeunit.getSelectedItemPosition()+1;
				
					    String item6_taskassess = String.valueOf(value_taskimportance*value_taskurgency);
					    String item7_taskprority = null;
					    String sn_rank=null;
					    if (value_taskimportance>=2&&value_taskurgency>=2){
					    	item7_taskprority="A"+(value_taskimportance)*(value_taskurgency);
					    	if (value_taskimportance>2&&value_taskurgency>2){
					    	    sn_rank="1";
					    	}else{
					    		sn_rank="2";
					    	}
					    	//item7_taskprority="S"+4;	
					/*
					if (value_taskpassion>=2&&value_taskdifficulty>=2){
						item7_taskprority="S"+4;	
					}
					if (value_taskpassion>=2&&value_taskdifficulty<2){
						item7_taskprority="S"+3;	
					}
					if (value_taskpassion<2&&value_taskdifficulty>=2){
						item7_taskprority="S"+2;	
					}
					if (value_taskpassion<2&&value_taskdifficulty<2){
						item7_taskprority="S"+1;	
					}
					*/
					    }
					    if (value_taskimportance==1&&value_taskurgency>=2){
					    	item7_taskprority="B"+(value_taskimportance)*(value_taskurgency);
					    	sn_rank="3";
					 /*
					if (value_taskpassion>=2&&value_taskdifficulty>=2){
						item7_taskprority="S"+4;	
					}
					if (value_taskpassion>=2&&value_taskdifficulty<2){
						item7_taskprority="S"+3;	
					}
					if (value_taskpassion<2&&value_taskdifficulty>=2){
						item7_taskprority="S"+2;	
					}
					if (value_taskpassion<2&&value_taskdifficulty<2){
						item7_taskprority="S"+1;	
					}
					*/
					    }
					    if (value_taskimportance>=2 && value_taskurgency==1){
					    	item7_taskprority="C"+(value_taskimportance)*(value_taskurgency);
					    	sn_rank="4";
					    	
					/*
					if (value_taskpassion>=2&&value_taskdifficulty>=2){
						item7_taskprority="S"+4;	
					}
					if (value_taskpassion>=2&&value_taskdifficulty<2){
						item7_taskprority="S"+3;	
					}
					if (value_taskpassion<2&&value_taskdifficulty>=2){
						item7_taskprority="S"+2;	
					}
					if (value_taskpassion<2&&value_taskdifficulty<2){
						item7_taskprority="S"+1;	
					}
					*/
					    }
					    if (value_taskimportance==1&&value_taskurgency==1){
					    	item7_taskprority="D";
					    	sn_rank="5";
					/*
					if (value_taskpassion>=2&&value_taskdifficulty>=2){
						item7_taskprority="S"+4;	
					}
					if (value_taskpassion>=2&&value_taskdifficulty<2){
						item7_taskprority="S"+3;	
					}
					if (value_taskpassion<2&&value_taskdifficulty>=2){
						item7_taskprority="S"+2;	
					}
					if (value_taskpassion<2&&value_taskdifficulty<2){
						item7_taskprority="S"+1;	
					}
					*/
					    }
				
					  
					    String item11_taskachieved=String.valueOf(value_taskimportance*value_taskurgency);
					    String item12_taskenjoyment=String.valueOf(value_taskpassion*value_taskduration);
					    String item13_taskexperience=String.valueOf(value_taskdifficulty*value_taskduration);
				       
				
					    SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
					    long taskdeadlinedate=0;
					    try {
					    	taskdeadlinedate=(long)df.parse(et15_taskdeadline.getText().toString()).getTime()/(1000*60);
					    	long dd = (long) ((new Date().getTime())/(1000*60));
					//tv_newtaskheader.setText(""+taskdeadlinedate+"system"+dd);
					// tv_newtaskheader.setText(""+dd);
				
				 
				  
					    } catch (java.text.ParseException e1) {
					    	// TODO Auto-generated catch block
					    	e1.printStackTrace();
					    }
				
				 
				  
					    switch (spin_timeunit.getSelectedItemPosition()+1){
				          
					    case 1: hr_timeunit=8;
					    		plannedtime=Double.parseDouble(et10_taskduration.getText().toString())*hr_timeunit;
					    		Log.d(Tags,"plannedtime:"+plannedtime);
					    		break;
					    case 2: hr_timeunit=1;
					    		plannedtime=Double.parseDouble(et10_taskduration.getText().toString())*hr_timeunit;
					    		Log.d(Tags,"plannedtime:"+plannedtime);
					    		break;
					    case 3: hr_timeunit=(double)1/60;
					    		plannedtime=Double.parseDouble(et10_taskduration.getText().toString())*hr_timeunit;
					    		Log.d(Tags,"plannedtime:"+plannedtime);
					    		break;
					    		default:break;	
					    }	
					    
					    taskdeadline=et15_taskdeadline.getText().toString();
					    //date1=dg_time.GetDate();
					    //Toast.makeText(getActivity(), deadline+"\n"+date1, Toast.LENGTH_LONG).show(); 
					    taskdeadlineTimeData=TimeData.changeStrToTime_YY(taskdeadline);
					    //taskdeadlinetimestamp=Timestamp.valueOf(deadline);
					    taskdeadlineDate=TimeData.convertDataNoTime_YY(taskdeadline);
					    
					    sn_deadline=TimeData.convertShortDate_YYMdtoYYYYMMdd(taskdeadlineDate);
					    //DecimalFormat df_sn = new DecimalFormat("0.0000");//格式化小数，.后跟几个零代表几位小数 
					    //float dt=((float)plannedtime/10000);
					    //sn_duration=df_sn.format(dt);;
					    sn_duration=TaskTool.addZero((int)(plannedtime*10), 6);
					    
					    switch (value_taskimportance){
					    	case 3:sn_importance="1";
					    			break;
					    	case 2:sn_importance="2";
					    			break;
					    	case 1:sn_importance="3";
					    			break;
					    	default:break;   
					    }
					    
					    switch (value_taskurgency){
					    	case 3:sn_urgency="1";
					    			break;
					    	case 2:sn_urgency="2";
					    	break;
					    	case 1:sn_urgency="3";
					    			break;
					    	default:break;   
					    }
				
					    //sn_sequence=sn_deadline+sn_importance+sn_urgency+sn_duration;
					   
					    
					    
					    
					       sn_sequence=sn_deadline+sn_rank+sn_importance+sn_urgency+sn_duration;
					 
					    
					    //long float_sn_sequence = Long.parseLong(sn_sequence);
					    //Log.d(Tags+"|insert data|", "sn_sequence:"+sn_sequence+" float:"+float_sn_sequence);
					    //tv_newtaskheader.setText(sn_sequence);
					    final String[] column_task={
					    		TaskData.TdDB.TASK_NAME,
					    		TaskData.TdDB.TASK_CREATEDTIME,
					    		TaskData.TdDB.TASK_CREATEDTIMEDATA,
					    		TaskData.TdDB.TASK_DESCRIPTION,
					    		TaskData.TdDB.TASK_IMPORTANCE, 
					    		TaskData.TdDB.TASK_IMPORTANCEDETAIL, 
					    		TaskData.TdDB.TASK_URGENCY,
					    	    TaskData.TdDB.TASK_URGENCYDETAIL, 
					    		TaskData.TdDB.TASK_ASSESSMENT,
					    		TaskData.TdDB.TASK_PRIORITY,
					    		TaskData.TdDB.TASK_PASSION,
					    	    TaskData.TdDB.TASK_PASSIONDETAIL, 
					    		TaskData.TdDB.TASK_DIFFICULTY,
					    	    TaskData.TdDB.TASK_DIFFICULTYDETAIL,
					    		TaskData.TdDB.TASK_DURATION, 
					    		TaskData.TdDB.TASK_DURATIONUNIT, 
					    		TaskData.TdDB.TASK_STARTEDTIME,
					    		TaskData.TdDB.TASK_DEADLINE,
					    		TaskData.TdDB.TASK_PROGRESS,
					    		TaskData.TdDB.TASK_REMINDER,
					    		TaskData.TdDB.TASK_STATUS,
					    		TaskData.TdDB.TASK_FINISHED,
					    		TaskData.TdDB.TASK_DELAYED,
					    		TaskData.TdDB.SUM_ACCOMPLISHED,   
					    	    TaskData.TdDB.SUM_ACHIEVED,
					    	    TaskData.TdDB.SUM_ENJOYMENT,
					    	    TaskData.TdDB.SUM_EXPERIENCE,
					    	    TaskData.TdDB.SUM_HORNOR,
					    	    TaskData.TdDB.TASK_DEADLINETIMEDATA,
					    	    TaskData.TdDB.TASK_DEADLINEDATE,
					    	    TaskData.TdDB.TASK_DELAYEDDAYS,
					    	    TaskData.TdDB.TASK_RECORDCOUNT,
					    	    TaskData.TdDB.TASK_SEQUENCE,
								TaskData.TdDB.TASK_USER,
								TaskData.TdDB.TASK_USERNICKNAME,
								TaskData.TdDB.TASK_OWNER,
								TaskData.TdDB.TASK_OWNERNICKNAME,
								TaskData.TdDB.TASK_CREATOR,
								TaskData.TdDB.TASK_CREATORNICKNAME,
								TaskData.TdDB.TASK_USERGROUP,
					    	    TaskData.TdDB.TASK_REMARKS,
					    	    TaskData.TdDB.TASK_HISTORY,
					    	    
					    };
					    
					    final Object[] value={
				    		et1_taskname.getText().toString(),
				    		curTime,
				    		curTimeData,
				    		et3_taskdescription.getText().toString(),
				    		String.valueOf(list_imp.get(spin4_taskimportance.getSelectedItemPosition()).getNo()),
				    		String.valueOf(list_imp.get(spin4_taskimportance.getSelectedItemPosition()).getValue()),
				    		String.valueOf(list_urg.get(spin5_taskurgency.getSelectedItemPosition()).getNo()),
				    		String.valueOf(list_urg.get(spin5_taskurgency.getSelectedItemPosition()).getValue()),
				    		item6_taskassess,
				    		item7_taskprority,
				    		String.valueOf(list_passion.get(spin8_taskpassion.getSelectedItemPosition()).getNo()),
				    		String.valueOf(list_passion.get(spin8_taskpassion.getSelectedItemPosition()).getValue()),
				    		String.valueOf(list_difficulty.get(spin9_taskdifficulty.getSelectedItemPosition()).getNo()),
				    		String.valueOf(list_difficulty.get(spin9_taskdifficulty.getSelectedItemPosition()).getValue()),
				    		et10_taskduration.getText().toString(),
				    		String.valueOf(spin_timeunit.getSelectedItemPosition()+1),
				    		taskstartedtime,
				    		taskdeadline,
				    		"0",
				    		taskreminder,
				    		"open",
				    		"no",
				    		"0",
				    		"0",
				    		"0",
				    		"0",
				    		"0",
				    		"0",				    		
				    		taskdeadlineTimeData,
				    		taskdeadlineDate,				    		
				    		 1,
				    		 0,
				    		 sn_sequence,
							 TaskData.userinfolist_usergroup.get(spin_user_model2.getSelectedItemPosition()).get("phoneNo").toString(),
							 TaskData.userinfolist_usergroup.get(spin_user_model2.getSelectedItemPosition()).get("nickname").toString(),
							 TaskData.userinfolist_usergroup.get(spin_user_model2.getSelectedItemPosition()).get("phoneNo").toString(),
							 TaskData.userinfolist_usergroup.get(spin_user_model2.getSelectedItemPosition()).get("nickname").toString(),
							 TaskData.user,
							 TaskData.usernickname,
							 TaskData.usergroup,
							 "",
							 "任务创建"+TaskData.tag_taskhistory
					    };
			  
					    String insertstr_column = " (";
					    for (int i=0;i<column_task.length-1;i++){
					   
					    	insertstr_column = insertstr_column+column_task[i]+",";
					    	//cv.put(column_task[i], value[i]);  	  
					    	//a=a+" "+et_task[i].getText().toString();
					    	Log.d(Tags+"|insert data|", "value"+i+" "+value[i]);
					    }
					    
					    insertstr_column=insertstr_column+column_task[column_task.length-1]+") ";
					    //Log.d("time compare", "deadline"+taskdeadlinedate+"system"+new Date().getTime());
					    //Toast.makeText(getActivity(), insertstr_column, Toast.LENGTH_LONG);
					    String aaa = "  values (";
					    String insertstr_value="";
				  for (int i=0;i<value.length;i++){
					   
					 insertstr_value = insertstr_value+"'"+value[i]+"'"+",";
						//cv.put(column_task[i], value[i]);  	  
						//a=a+" "+et_task[i].getText().toString();
						Log.d(Tags+"|insert data|", "value"+i+" "+value[i]);
					  }
				  insertstr_value=aaa+insertstr_value.substring(0,((insertstr_value.length())-1))+");";
				  //Toast.makeText(getActivity(), insertstr_value, Toast.LENGTH_LONG);
				  String column_insert="("+TaskData.TdDB.TASK_DELAYEDDAYS+","+TaskData.TdDB.TASK_DEADLINEDATE+")";
				  String value_insert=insertstr_value;
				
					value_insert = " values ("+1+","+taskdeadlinedate+")";
					
				  
					  TaskData.db_TdDB.execSQL("insert into "+TaskData.TdDB.TABLE_NAME_TaskMain+insertstr_column+insertstr_value);
					 
					  Cursor cur= TaskData.db_TdDB.rawQuery("select LAST_INSERT_ROWID()",null);
					  cur.moveToFirst();
					  int lastRowId=cur.getInt(0);
					  
						
					  ContentValues cv3 = new ContentValues();
					   String t_sn=TaskData.user+"0"+TaskTool.addZero(lastRowId,10);
						 cv3.put("_sn",TaskData.user+"0"+TaskTool.addZero(lastRowId,10));
						 String where_task=TaskData.TdDB.TASK_ID+"=?";
						 String[] where_taskid={String.valueOf(lastRowId)};
						 TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv3, where_task, where_taskid);
					 
					 TaskData.getSequenceNo();
						  
					   //cv.put(TaskData.TdDB.TASK_REMINDERNO, lastRowId);
					   //TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, null, null);
					   
					   /*
					   new ConnMySQL(getActivity()).InsertGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain,lastRowId);
					   Toast.makeText(getActivity(), "已同步插入"+lastRowId,Toast.LENGTH_SHORT).show();
						 Log.d("insert by Mysql","done");
						*/
						
				     if (taskreminder=="true"){
					     
				    	 TaskTool.setReminder(getActivity(),
				    			 et15_taskdeadline.getText().toString(), 
				    			 "T"+lastRowId,
				    			 et1_taskname.getText().toString(),
				    			 et3_taskdescription.getText().toString());
				    	
				     }
				   
				        Toast.makeText(getActivity(), "任务添加成功",Toast.LENGTH_SHORT).show();
				     	TaskTool.queryTaskDetail(t_sn);
						TaskData.adapterUpdate();
						dismiss(); 
				 }	
				        
				         
				     //Toast.makeText(getActivity(),"sequence"+float_sn_sequence, Toast.LENGTH_SHORT).show();
		     }
				  
		  });	  
		 
		  
		return v;
	}	
	
@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	DisplayMetrics dm = new DisplayMetrics();
	getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
	getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
}

@Override
public void onDestroy() {
	// TODO Auto-generated method stub
	for (BroadcastReceiver bcr:TaskData.alarmReceiverSet) {
		 try{
			  getActivity().unregisterReceiver(bcr);
			          Log.d(Tags,"unregit bcr");
		    }catch (IllegalArgumentException e) { 
				    if (e.getMessage().contains("Receiver not registered")) { 
				        // Ignore this exception. This is exactly what is desired 
				   	   Log.d(Tags,"bcr error Receiver not registered");
				    	
				    } else { 
				        // unexpected, re-throw 
				    	 
				      try {
						throw e;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						Log.d(Tags," bcr error"+e1.toString());
						e1.printStackTrace();
					}
		             }
		   }
	}
	super.onDestroy();
}  
 
	
	
}
