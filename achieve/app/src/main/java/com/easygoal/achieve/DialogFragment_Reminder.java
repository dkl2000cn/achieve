package com.easygoal.achieve;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DialogFragment_Reminder extends DialogFragment {
	String curTime;
	Timestamp taskdeadlinetimestamp=null;
	String task_maxperiod;
	String column_insert;
	String value_insert;
	 long taskdeadlineTimeData=0;
	 String taskdeadlineDate;
	 String Tags="DialogFragment_reminder";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);  
		 //setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v=inflater.inflate(R.layout.dialogfg_reminder, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		
		  final EditText et_remindername=(EditText)v.findViewById(R.id.et_remindername);
		  final EditText et_remindercontent=(EditText)v.findViewById(R.id.et_reminderdescription);
		  final TextView tv_remindercreatedtime=(TextView)v.findViewById(R.id.tv_remindercreatedtime);
		  final EditText et_reminderdeadlinetime=(EditText)v.findViewById(R.id.et_reminderdeadline);
		 
		  final EditText et_reminderadvance=(EditText)v.findViewById(R.id.et_reminderadvance);
		 
		  final Spinner spin_reminderfreq=(Spinner)v.findViewById(R.id.spin_reminderfreq);
		  final EditText et_reminderinterval=(EditText)v.findViewById(R.id.et_reminderinterval);
		  //final ToggleButton tb_alarmsetting=(ToggleButton)v.findViewById(R.id.tb_alarmsetting);
		  //final LinearLayout ll_customsetting =(LinearLayout)v.findViewById(R.id.ll_customsetting);
          final List<InputValueSet> list_reminderfreq=new ArrayList<InputValueSet>();
          list_reminderfreq.add(new InputValueSet(1,"一次"));
          list_reminderfreq.add(new InputValueSet(2,"重复"));
        
         // list_imp.add(new InputValueSet(4,"至关重要"));
		  mArrayAdapter adap_spin_reminderfreq = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_reminderfreq);
		 // adap_spin4_taskimportance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  spin_reminderfreq.setAdapter(adap_spin_reminderfreq); 
		  
		    SharedPreferences sp = getActivity().getSharedPreferences("userinfo",getActivity().MODE_PRIVATE); 
		    int freq=sp.getInt("alarmfreq", 0); 
		    String interval=""+sp.getInt("alarminterval", 5); 
			String advance=""+sp.getInt("alarmadvance", 0); 
		  
			et_reminderadvance.setText(advance);
			et_reminderinterval.setText(interval);
			spin_reminderfreq.setSelection(freq);
			
		 Button btn_confirm=(Button)v.findViewById(R.id.reminder_confirm_bt);
		 Button btn_cancel = (Button) v.findViewById(R.id.reminder_cancel_bt);  
		 Button btn_close = (Button) v.findViewById(R.id.reminder_close_bt);  
		 
	        btn_close.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
		 
	        btn_cancel.setOnClickListener(new OnClickListener() {  
	      	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        }); 
	        
		    tv_remindercreatedtime.setText(curTime);
			    
		    et_reminderdeadlinetime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    	
		    	
		    	   @Override
		    	   public void onFocusChange(View v, boolean hasFocus) {
		    	       if (hasFocus) {
		    	    	
		    	    	   DialogFragment_TimeInput dg_time=new DialogFragment_TimeInput(et_reminderdeadlinetime,0);
			    	       TaskTool.showDialog(dg_time);
		    	    	
		    	       }
		    	   }
		    	});
		
	      
		  btn_confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                
			
				 SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
				  long taskdeadlinedate=0;
				  boolean nameflag=TextUtils.isEmpty(et_remindername.getText().toString().trim());
				  boolean deadlineflag=TextUtils.isEmpty(et_reminderdeadlinetime.getText().toString().trim());
				  
				  if (nameflag){
					  Toast.makeText(getActivity(), "名称不能为空", Toast.LENGTH_SHORT).show();  
				  }
				  
				  if (deadlineflag){
					  Toast.makeText(getActivity(), "最后期限不能为空", Toast.LENGTH_SHORT).show();  
				  }

				String inputStr_advance=et_reminderadvance.getText().toString().trim();
				String inputStr_alarmInterval=et_reminderinterval.getText().toString().trim();
				if (!TextUtils.isEmpty(inputStr_advance)&&TaskTool.isNumeric(inputStr_advance)){
					inputStr_advance=et_reminderadvance.getText().toString().trim();
				}else{
					Toast.makeText(getActivity(),"提前时间请输入有效数字", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!TextUtils.isEmpty(inputStr_alarmInterval)&&TaskTool.isNumeric(inputStr_alarmInterval)){
					inputStr_alarmInterval=et_reminderinterval.getText().toString().trim();
				}	else{
					Toast.makeText(getActivity(),"间隔请输入有效数字", Toast.LENGTH_SHORT).show();
					return;
				}

				if (!nameflag&&!deadlineflag){
					    Reminder reminder=new Reminder();
					    reminder.setName(et_remindername.getText().toString());
					    reminder.setContent(et_remindercontent.getText().toString());
						reminder.setCreatedtime(tv_remindercreatedtime.getText().toString());
						reminder.setDeadlinetime(et_reminderdeadlinetime.getText().toString());
						reminder.setAdvance(inputStr_advance);
						reminder.setFrequency(String.valueOf(spin_reminderfreq.getSelectedItemPosition()));
						reminder.setAlarminterval(inputStr_alarmInterval);
						reminder.setPiaction("0");;
						reminder.setUsername(TaskData.user);
						SimpleDateFormat sdf = new SimpleDateFormat("yy-M-d HH:mm");
						String dead=et_reminderdeadlinetime.getText().toString();
						long a=0;
						try {
							a=sdf.parse(dead).getTime();
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//a = TimeData.changeStrToTime_YYYY(et_reminderdeadlinetime.getText().toString());
						String timegap;
						long b=new Date().getTime();
						
						long t=Integer.parseInt(et_reminderadvance.getText().toString())*60*1000;
						if (t==0){
						timegap=TimeData.changeLongToTimeStr(a-b);
						}else{
					    timegap=TimeData.changeLongToTimeStr(a-b-t);	
						}
						//reminder.setRemainingtime(timegap);
						
						reminder.save();
						
						
						  int lastRowId=0;
							Cursor cur= DataSupport.findBySQL("select LAST_INSERT_ROWID() from reminder");
							   if (cur!=null&&cur.getCount()>0){
						    	cur.moveToFirst();
								lastRowId=cur.getInt(0);
							   }else{
								lastRowId=1; 
							   }
							   String r_sn=TaskData.user+"r";
							   //newMemo.setSn(t_sn+TaskTool.addZero(lastRowId,4));
							   //newMemo.save();
							   
							   ContentValues cv=new ContentValues();
							   cv.put("sn",r_sn+TaskTool.addZero(lastRowId,10));
							   cv.put("sourceid","R"+lastRowId);
							   DataSupport.update(Reminder.class,cv,lastRowId);
							   
							   /*
							   Cursor cr=DataSupport.findBySQL("select id as _id from reminder where id="+lastRowId+" and username="+"'"+TaskData.user+"';");
								
								 if (cr.getCount()>0){
									   cr.moveToFirst();
									   do{
									   //int reminderno = cr.getInt(0);
									   ContentValues cv=new ContentValues();
									   cv.put("sn", r_sn+TaskTool.addZero(lastRowId,10));
									   cv.put("sourceid", "R"+lastRowId);
									   DataSupport.update(Reminder.class,cv,lastRowId);
									   Log.d("add sn", r_sn+TaskTool.addZero(lastRowId,10));   
									   
									   }while(cr.moveToNext());
								   } 
						      */
						
						
						//TaskData.reminderlist.add(reminder);
						//DataSupport.saveAll(TaskData.reminderlist); 
						Log.d(Tags,"reminder count"+TaskData.cursor_reminder.getCount());
						
						TaskData.cursor_reminder.requery();
						TaskData.adapter_reminder.notifyDataSetChanged();
						//Toast.makeText(getActivity(),"a:"+a+"\n"+"b:"+b, Toast.LENGTH_SHORT).show();
				        new Alarm(getActivity(),
				        		DataSupport.find(Reminder.class,lastRowId).getSourceId(),
				        		et_remindername.getText().toString(),      		
				        		a,
				        		spin_reminderfreq.getSelectedItemPosition(),	
				        		Integer.parseInt(et_reminderadvance.getText().toString()),
				        		Integer.parseInt(et_reminderinterval.getText().toString())).alarmset();
				        dismiss();
				    			
				  }
				 
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
	
}
