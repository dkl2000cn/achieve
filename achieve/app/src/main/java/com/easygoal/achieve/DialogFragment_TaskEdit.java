package com.easygoal.achieve;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast; 

public class DialogFragment_TaskEdit extends DialogFragment {


	String curTime;
	String task_maxperiod;
	String column_insert;
	String value_insert;
	Cursor c;
	Cursor c1;
	Cursor c_r1;
	 long taskdeadlineTimeData=0;
	 String taskdeadlineDate;
	 double plannedtime=0;
	 String taskreminder;
	 String sn_deadline=null;
	 String sn_importance=null;
	 String sn_urgency=null;
	 String sn_duration=null;
	 String sn_sequence=null;
	 String taskdeadline;
	 String taskstartedtime;
	 int sn_sequenceno=0;
	 String deadline_old=null;
	 String reminder_old=null;
	 String taskname_old=null;
	 float float_sequence_old=-1;
	 long createdtimedata_old=-1;
	 int lastrowid=-1;
	 int rec_count=0;
	 int timeunit;
	 long curTimeData;
	 String t_sn;
	 DecimalFormat df_duration;
	 double total_weight;
	 public String dueHour;
	 public String dueMin;
	 String Tags="DialogFragment_TaskEdit";
	 
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
		 Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		 curTime = formatter.format(curDate);
		 curTimeData=new Date().getTime();
		 df_duration = new DecimalFormat("0.0");
		 dueHour=""+TaskData.endhour;
		 dueMin=""+TaskData.endmin;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v=inflater.inflate(R.layout.dialogfg_taskedit, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
         TaskData.tv_totalweight=(TextView)v.findViewById(R.id.tv_totalweight);
        
		  final TextView tv_newtaskheader=(TextView)v.findViewById(R.id.tv_newtaskheader);
		  final TextView tv2_taskcreatedtime=(TextView)v.findViewById(R.id.task_item2_createdtime_tv);
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

		  final EditText et14_taskstartedtime=(EditText)v.findViewById(R.id.task_item14_startedtime_et);
		  final EditText et15_taskdeadline=(EditText)v.findViewById(R.id.task_item15_deadline_et);
		  final EditText et16_taskprogress=(EditText)v.findViewById(R.id.task_item16_progress_et);
		  final CheckBox cb17_taskreminder=(CheckBox)v.findViewById(R.id.task_item17_reminder_cb);
		  final EditText et18_taskstatus=(EditText)v.findViewById(R.id.task_item18_status_et);
		  final EditText et19_taskfinished=(EditText)v.findViewById(R.id.task_item19_finished_et);
		  final EditText et20_taskdelayed=(EditText)v.findViewById(R.id.task_item20_delayed_et);
		  final TextView tv21_taskmaxperiod=(TextView)v.findViewById(R.id.tv_maxAvailDays);
		  final TextView tv_maxvalue=(TextView)v.findViewById(R.id.tv_maxvalue);
		  final RecyclerView rv_progedit=(RecyclerView)v.findViewById(R.id.rv_progedit);
		  //final ListView m_listview=(ListView)v.findViewById(R.id.task_show_lv);  		  
		  final TextView tv_addSubItem=(TextView)v.findViewById(R.id.tv_addSubItem);
		  final Spinner spin_timeunit = (Spinner)v.findViewById(R.id.timeunit_spin);
		  final Spinner spin4_taskimportance = (Spinner)v.findViewById(R.id.task_item4_importance_spin); 
		  final InputValueSet taskimportance[]={new InputValueSet(1,"不重要"),new InputValueSet(2,"2-一般重要"),new InputValueSet(3,"3-非常重要"),new InputValueSet(4,"4-至关重要")};
		 
		  Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);  
		   
		    btn_closewin.setOnClickListener(new OnClickListener() {  
			  
	           
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  

          final List<InputValueSet> list_imp=new ArrayList<InputValueSet>();
          list_imp.add(new InputValueSet(1,"不重要"));
          list_imp.add(new InputValueSet(2,"一般重要"));
          list_imp.add(new InputValueSet(3,"非常重要"));
         // list_imp.add(new InputValueSet(4,"至关重要"));
		  mArrayAdapter adap_spin4_taskimportance = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_imp);
		 // adap_spin4_taskimportance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  spin4_taskimportance.setAdapter(adap_spin4_taskimportance); 
		
		 final Spinner spin5_taskurgency = (Spinner)v.findViewById(R.id.task_item5_urgency_spin);
		 final String urgency[]={"不紧急-1","一般紧急-2","非常紧急-3","十万火急-4"};
		 final List<InputValueSet> list_urg=new ArrayList<InputValueSet>();
         list_urg.add(new InputValueSet(1,"不紧急"));
         list_urg.add(new InputValueSet(2,"一般紧急"));
         list_urg.add(new InputValueSet(3,"非常紧急"));
        // list_urg.add(new InputValueSet(4,"十万火急"));
		 mArrayAdapter adap_spin5_taskurgency = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_urg);
		 // adap_spin5_taskurgency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  spin5_taskurgency.setAdapter(adap_spin5_taskurgency); 
		 final Spinner spin8_taskpassion = (Spinner)v.findViewById(R.id.task_item8_passion_spin);
		 final String passion[]={"1-无兴趣","2-一般兴趣","3-很感兴趣","4-强烈兴趣"};
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
			
					
				  //final Spinner spin_timeunit = (Spinner)v.findViewById(R.id.timeunit_spin);
					 //final String timeUnit[]={"1-全天","2-小时","3-%"};
					 final List<InputValueSet> list_timeUnit=new ArrayList<InputValueSet>();
					 list_timeUnit.add(new InputValueSet(1,"全天"));
			         list_timeUnit.add(new InputValueSet(2,"小时"));
			         list_timeUnit.add(new InputValueSet(3,"分钟"));
			      //   list_difficulty.add(new InputValueSet(4,"极其困难"));	
			    mArrayAdapter adap_spin_timeUnit = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_timeUnit);
					 // adap_spin9_taskdifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					  spin_timeunit.setAdapter(adap_spin_timeUnit); 		  
					  
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
	      
	        //final String[] oldvalue=new String[column_task.length];
			  if (TaskData.selTaskID!=null){
					
				     String a = TaskData.TdDB.TABLE_NAME_TaskMain;
				     String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
				  
				     c = TaskData.db_TdDB.rawQuery("select * from "+a+" where "+TaskData.TdDB.TASK_ID+"="+TaskData.selTaskID,null);
					 //
				   if (c!=null&&c.getCount()>0) {
					   c.moveToFirst();

					   timeunit = c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_DURATIONUNIT));
					   t_sn = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
					   TaskData.selTaskSN=t_sn;
					   et1_taskname.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME)));
					   et3_taskdescription.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DESCRIPTION)));
					   spin4_taskimportance.setSelection(c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE)) - 1, true);
					   spin5_taskurgency.setSelection(c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_URGENCY)) - 1, true);
					   spin8_taskpassion.setSelection(c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_PASSION)) - 1, true);
					   spin9_taskdifficulty.setSelection(c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_DIFFICULTY)) - 1, true);
					   spin_timeunit.setSelection(c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_DURATIONUNIT)) - 1, true);
					   et15_taskdeadline.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
					   et10_taskduration.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATION)));
					   et14_taskstartedtime.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STARTEDTIME)));

					   tv2_taskcreatedtime.setText(TaskTool.getCurTime());
					 /*
					 Toast.makeText(getActivity(),"import"+c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE))+"\n"+"urgency"+
							 c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_URGENCY))+
					         c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_PASSION))+
					         c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_DIFFICULTY))
					         , Toast.LENGTH_LONG).show();
					 */
					   deadline_old = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
					   taskreminder = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_REMINDER));
					   taskname_old = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME));
					   createdtimedata_old = c.getLong(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIMEDATA));
					   float_sequence_old = c.getFloat(c.getColumnIndex(TaskData.TdDB.TASK_SEQUENCE));
					   lastrowid = c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_ID));
					   if (taskreminder!=null) {
						   if (taskreminder.equals("true")) {
							   cb17_taskreminder.setChecked(true);
						   } else {
							   cb17_taskreminder.setChecked(false);
						   }
					   }
                    /*		         
			         for (int i=0;i<column_task.length;i++){
			        	 oldvalue[i]=c.getString(c.getColumnIndex(column_task[i]));
			         }
			         */
				   }
	               
			  }else {    	
			      Toast.makeText(getActivity(), "未选定任务", Toast.LENGTH_SHORT).show();	 
			      dismiss();
			     }        
	        
	        final ContentValues cv = new ContentValues();
		
		    tv2_taskcreatedtime.setText(curTime);
		
		    class EditChangedListener_starttime implements TextWatcher {  
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
					
					//String str1 = curTime;  //"yyyyMMdd"格式 如 20131022
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
			          // editStart = et15_taskdeadline.getSelectionStart();  
			          // editEnd = et15_taskdeadline.getSelectionEnd();    
			           //    int tempSelection = editStart;  
			            //   et15_taskdeadline.setText(s);  
			             //  et15_taskdeadline.setSelection(tempSelection);  
						
					 String inputstr_taskdeadline =et15_taskdeadline.getText().toString().trim();
		             if (TextUtils.isEmpty(inputstr_taskdeadline)){		
		            	 inputstr_taskdeadline= new SimpleDateFormat ("yy-MM-dd HH:mm").format(new Date());
				      }else{
				    	  inputstr_taskdeadline=et15_taskdeadline.getText().toString().trim();
				           }
		                   String str_taskmaxperiod = null;
	                         switch (timeunit){
	                           case 0:
					           str_taskmaxperiod=String.valueOf(TimeData.DaySpace_YY(s.toString(),inputstr_taskdeadline));
					           break;
	                           case 1: str_taskmaxperiod=String.valueOf(TaskData.hours_eachday*TimeData.DaySpace_YY(s.toString(),inputstr_taskdeadline));
					            break;
	                            case 2: str_taskmaxperiod=String.valueOf(0.5*TimeData.DaySpace_YY(s.toString(),inputstr_taskdeadline));
					            break;	   
					           default:break;   
	                       } 				    
						    //Toast.makeText(getActivity(), inputstr_taskdeadline+"\n"+s.toString()+"\n"+str_taskmaxperiod, Toast.LENGTH_SHORT).show();
						    if (str_taskmaxperiod!=null){
						    tv21_taskmaxperiod.setText(str_taskmaxperiod);
						    }
					//tv21_taskmaxperiod.setText(s);
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}

				
		    };     
	
		    EditChangedListener_starttime edlistener_starttime = new EditChangedListener_starttime();
		    et14_taskstartedtime.addTextChangedListener(edlistener_starttime);
		    
		    et14_taskstartedtime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    	
		    	   @Override
		    	   public void onFocusChange(View v, boolean hasFocus) {
		    	       if (hasFocus) {
		    	    	   DialogFragment_TimeInput dg_startedtime=new DialogFragment_TimeInput(et14_taskstartedtime,0);
			    	       TaskTool.showDialog(dg_startedtime);
		    	    	   
		    	       }	   
		    	   }
		    	}); 
		    
		    class EditChangedListener_deadline implements TextWatcher {  
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
					
					//String str1 = curTime;  //"yyyyMMdd"格式 如 20131022
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
			          // editStart = et15_taskdeadline.getSelectionStart();  
			          // editEnd = et15_taskdeadline.getSelectionEnd();    
			           //    int tempSelection = editStart;  
			            //   et15_taskdeadline.setText(s);  
			             //  et15_taskdeadline.setSelection(tempSelection);  
						
					 String inputstr_taskstarttime =et14_taskstartedtime.getText().toString().trim();
		             if (TextUtils.isEmpty(inputstr_taskstarttime)){		
		            	 inputstr_taskstarttime= new SimpleDateFormat ("yy-MM-dd HH:mm").format(new Date());
				      }else{
				    	  inputstr_taskstarttime=et14_taskstartedtime.getText().toString().trim();
				           }
		                   String str_taskmaxperiod = null;
		                   switch (timeunit){
		                      case 0:
						           str_taskmaxperiod=String.valueOf(TimeData.DaySpace_YY(inputstr_taskstarttime,s.toString()));
						      break;
		                      case 1: str_taskmaxperiod=String.valueOf(TaskData.hours_eachday*TimeData.DaySpace_YY(inputstr_taskstarttime,s.toString()));
						      break;
		                      case 2: str_taskmaxperiod=String.valueOf(0.5*TimeData.DaySpace_YY(inputstr_taskstarttime,s.toString()));
						      break;	   
						      default:break;   
		                   } //Toast.makeText(getActivity(), inputstr_taskstarttime+"\n"+s.toString()+"\n"+str_taskmaxperiod, Toast.LENGTH_SHORT).show();
						    
						      tv21_taskmaxperiod.setText(str_taskmaxperiod);   
					//tv21_taskmaxperiod.setText(s);
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}

				
		    };     
	
		    EditChangedListener_deadline edlistener_deadline = new EditChangedListener_deadline();
		    et15_taskdeadline.addTextChangedListener(edlistener_deadline);
		    
		    et15_taskdeadline.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    	  String deadline;
		    	  String date1;
		    			    	
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
		    et15_taskdeadline.addTextChangedListener(new EditChangedListener(getActivity()));
		    spin_timeunit.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					String str_maxperiod = null;
					switch (position){
                     case 0:
                    	   
				           str_maxperiod=String.valueOf(TimeData.DaySpace_YY(et14_taskstartedtime.getText().toString(),et15_taskdeadline.getText().toString()));
				           //Toast.makeText(getActivity(), ""+position+str_maxperiod, Toast.LENGTH_SHORT).show();
				           tv21_taskmaxperiod.setText(str_maxperiod); 
				          break;
                     case 1: str_maxperiod=String.valueOf(TaskData.hours_eachday*TimeData.DaySpace_YY(et14_taskstartedtime.getText().toString(),et15_taskdeadline.getText().toString()));
                     //Toast.makeText(getActivity(), ""+position+str_maxperiod, Toast.LENGTH_SHORT).show();
                          tv21_taskmaxperiod.setText(str_maxperiod); 
                     break;
                     case 2: str_maxperiod=String.valueOf(0.5*TimeData.DaySpace_YY(et14_taskstartedtime.getText().toString(),et15_taskdeadline.getText().toString()));
                          //Toast.makeText(getActivity(), ""+position+str_maxperiod, Toast.LENGTH_SHORT).show();
                          tv21_taskmaxperiod.setText(str_maxperiod); 
                          break;	   
				      default:break;   
                  } 
				      
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
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
					            tv_maxvalue.setText("Max:"+df_duration.format(maxDuration)+"天");
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
						        Log.d(Tags,"Max:"+df_duration.format(maxDuration)+"小时");
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
				                Log.d(Tags,"Max:"+df_duration.format(maxDuration)+"分");	
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
				boolean timeflag = isTimeValid(inputstr_taskdeadline);
				boolean nameflag = TextUtils.isEmpty(et1_taskname.getText().toString().trim());
				boolean deadlineflag=TextUtils.isEmpty(et15_taskdeadline.getText().toString().trim());
				boolean durationflag=TextUtils.isEmpty(et10_taskduration.getText().toString().trim());
				boolean durationcheck=false;
				
				if (nameflag){
					Toast.makeText(getActivity(),"任务名不能为空" , Toast.LENGTH_SHORT).show();
				}
				if (!timeflag) {
					Toast.makeText(getActivity(), "最后期限数字有误", Toast.LENGTH_SHORT).show();
				}
				if (deadlineflag){
					Toast.makeText(getActivity(), "最后期限格式不正确", Toast.LENGTH_SHORT).show();
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
				
				boolean totalflag=(!nameflag)&&(!deadlineflag)&&(!durationflag)&&timeflag&&durationcheck;
			
				if (totalflag) {

								    task_maxperiod=String.valueOf(TimeData.DaySpace_YY(taskstartedtime,inputstr_taskdeadline));
								    tv21_taskmaxperiod.setText(task_maxperiod);
		                            //Toast.makeText(getActivity(), taskstartedtime+"\n"+task_maxperiod, Toast.LENGTH_SHORT).show();
				              
								    	int value_taskimportance = spin4_taskimportance.getSelectedItemPosition()+1;
								    	int value_taskurgency=spin5_taskurgency.getSelectedItemPosition()+1;
								    	int value_taskpassion=spin8_taskpassion.getSelectedItemPosition();
								    	int value_taskdifficulty=spin9_taskdifficulty.getSelectedItemPosition()+1;
								    	double value_taskduration=Double.parseDouble(et10_taskduration.getText().toString());
				
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
				
				//String item11_taskachieved=String.valueOf(value_taskimportance*value_taskurgency);
				//String item12_taskenjoyment=String.valueOf(value_taskpassion*value_taskduration);
				//String item13_taskexperience=String.valueOf(value_taskdifficulty*value_taskduration);
				  
				
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
					 			
				
				  double hr_timeunit;
				  switch (spin_timeunit.getSelectedItemPosition()+1){
				          
				    case 1: hr_timeunit=8;
				    	    plannedtime=Double.parseDouble(et10_taskduration.getText().toString())*hr_timeunit;
	                        Log.d(Tags,"plannedtime:"+plannedtime);
	                        break;
					case 2: hr_timeunit=1;
						    plannedtime=Double.parseDouble(et10_taskduration.getText().toString())*hr_timeunit;
						   
						    Log.d(Tags,"plannedtime"+plannedtime);
					        break;
					case 3: hr_timeunit=0.5;
						    plannedtime=Double.parseDouble(et10_taskduration.getText().toString())*hr_timeunit;
						   
						    Log.d(Tags,"plannedtime"+plannedtime);
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
                //DecimalFormat df_duration = new DecimalFormat("0.0000");//格式化小数，.后跟几个零代表几位小数 
				//float dt=((float)plannedtime/10000);
				//sn_duration=df_duration.format(dt);;
                
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
				
				sn_sequence=sn_deadline+sn_rank+sn_importance+sn_urgency+sn_duration;
				//float float_sn_sequence = Float.parseFloat(sn_sequence);
				//  Log.d(Tags+"|modify data|", "sn_sequence:"+sn_sequence+" float:"+float_sn_sequence);
				//tv_newtaskheader.setText(sn_sequence);
				  final String[] column_task={
				    		TaskData.TdDB.TASK_NAME,
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
				    		TaskData.TdDB.TASK_REMINDER,
				    	    TaskData.TdDB.TASK_DEADLINETIMEDATA,
				    	    TaskData.TdDB.TASK_DEADLINEDATE,
				    	    TaskData.TdDB.TASK_SEQUENCE,
				    	    TaskData.TdDB.TASK_USER, 
				    	    TaskData.TdDB.TASK_OWNER,
				    	    TaskData.TdDB.TASK_MODIFIED
				    };
			    final Object[] value={
			    		et1_taskname.getText().toString(),
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
			    		taskreminder,			    		
			    		taskdeadlineTimeData,
			    		taskdeadlineDate,	
			    		sn_sequence,
			    	    TaskData.user,
						TaskData.user,
						TaskTool.getCurTime()
				    };
		  
				  String updateStr_column = " ";
				
				  //List colChangedList=new ArrayList<>();
				  for (int i=0;i<column_task.length;i++){
					   
					 updateStr_column = updateStr_column+column_task[i]+"="+"'"+value[i]+"',";
						//cv.put(column_task[i], value[i]);  	  
						//a=a+" "+et_task[i].getText().toString();
						Log.d(Tags,"update data "+i+" value:"+i+" "+value[i]);
					    /*
						if (!oldvalue[i].equals(value[i])){
						   valueDifCount=valueDifCount+1;
						   Log.d("data dif",column_task[i]+" oldvalue:"+oldvalue[i]+" new:"+value[i]);
					   }
					   */
				  }
				  
				  updateStr_column=" set "+updateStr_column.substring(0,((updateStr_column.length())-1));
				 
				  //Toast.makeText(getActivity(), updateStr_column, Toast.LENGTH_LONG);

				  String whereStr=" where "+TaskData.TdDB.TASK_ID+"="+TaskData.selTaskID;
				
				  
				  TaskData.db_TdDB.execSQL("update "+TaskData.TdDB.TABLE_NAME_TaskMain+updateStr_column+whereStr);
					 
				  TaskData.getSequenceNo();
					
						if (taskreminder!=null&&taskreminder.equals("false")){
						
							   TaskTool.deleteReminder(getActivity(),"T"+lastrowid);
							
						} else {
							
							if (taskreminder!=null&&taskreminder.equals("true")){
							  
						        	
						        	TaskTool.deleteReminder(getActivity(),"T"+lastrowid);
						        	TaskTool.setReminder(getActivity(),
							    			 et15_taskdeadline.getText().toString(), 
							    			 "T"+lastrowid,
							    			 et1_taskname.getText().toString(),
							    			 et3_taskdescription.getText().toString());
							}
						}
						
					
				    String str_taskedit="任务修改"+TaskData.tag_taskhistory;
				    TaskTool.AppendFieldText(TaskData.TdDB.TABLE_NAME_TaskMain, TaskData.TdDB.TASK_SN, t_sn, TaskData.TdDB.TASK_HISTORY,str_taskedit);
				    TaskData.getRecordSequenceNo(TaskData.selTaskSN);
				    TaskData.adaptRecordDetails.getCursor().requery();
			        TaskData.adaptRecordDetails.notifyDataSetChanged();
				   
				    //TaskData.mcAdapter_taskrecord.notifyDataSetChanged();
					//DialogFragment_TaskDetail dialogfrag_taskdetail=new DialogFragment_TaskDetail();
					//TaskData.showDialog(dialogfrag_taskdetail);
					TaskData.adapterUpdate();
					dismiss();
					    //TaskData.clickedlist=new ArrayList<String>();
					    //TaskData.subItemlist=null;
					    //TaskData.subItemlist=new ArrayList<RecordBean>();
					    //TaskData.subItemlist.clear();
					    //TaskData.subdellist.clear();
					   // TaskData.subItems_idno.clear();
					   // TaskData.subItems_deadline.clear();
					   // TaskData.subItems_comments.clear();
					   // TaskData.subItems_weight.clear();
					   // TaskData.subItems_del.clear();
					   // TaskData.subItems_idno=new ArrayList();
						//TaskData.subItems_deadline=new ArrayList();
						//TaskData.subItems_comments=new ArrayList();
						//TaskData.subItems_weight=new ArrayList();
						//TaskData.subItems_del=new ArrayList();
					    //TaskData.adapterUpdate();	
				}
		     }  	
		  });	  
				  			
		return v;
	}
	boolean isTimeValid(String s){
		if (!TextUtils.isEmpty(s.toString())) {
			try {
				if (TimeData.convertTime_YYYYTIMEtoYYTIME(s.toString()) != null) {
					return true;
				} else {
					Toast.makeText(getActivity(), "日期格式输入有误", Toast.LENGTH_SHORT).show();
					return false;

				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				//Toast.makeText(context, "日期输入正确", Toast.LENGTH_SHORT).show();
			}
		}
		return false;
	}
@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	DisplayMetrics dm = new DisplayMetrics();
	getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
	getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
}  
 
	
	
}
