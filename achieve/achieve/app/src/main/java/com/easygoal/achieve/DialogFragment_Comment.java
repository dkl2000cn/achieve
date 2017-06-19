package com.easygoal.achieve;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class DialogFragment_Comment extends DialogFragment {
	  String curTime;
	  int curRecordCount;
	  String pro_sum; 
	  String str_tasklapsedpercent;
	  Cursor c;
	  Cursor c_rf;
	  Cursor c_r;
	  TextView tv_taskID;
	  TextView tv_header;
	  TextView tv_taskname;
	  TextView tv_taskcreatedtime;
	  TextView tv_taskprogress;
	  TextView tv_taskdeadline;
	  TextView tv_taskstatus;
	  TextView tv_recordtime;
	  EditText et_recordprogress;
	  EditText et_recordcomment;
	  CheckBox cb_mileStone;
	  CheckBox cb_quickfinish;
	  String tasktask;
	  LinearLayout view_taskclose;
	 
	  //final ListView m_listview=(ListView)v.findViewById(R.id.task_show_lv);
	  Button btn_recordclose;  
	  Button btn_recordsubmit;
	  Button btn_closetask;
	  Button btn_canceltask;
	  Button btn_recordview;
	  RecyclerView rv_prog;
	  SeekBar sb_prog;
	  TextView et_actualValue;
	  TextView tv_maxValue;
	  Double newProg=0.0;
	  int MIN_MARK;
	  double MAX_MARK;
	  double MAX_VALUE;
	  String t_sn;
	  mRecyclerAdapter adapter_prog;
	  DecimalFormat df ;
	  int duration_unit;
	  double duration;
	  double plannedtime;
	  double hr_timeunit;
	  String teamname;
	  final String Tags="DialogFragment_Comment";
	    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);  
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
		 df = new DecimalFormat("0.0");
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v=inflater.inflate(R.layout.dialogfg_comment, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		  //TextView tv1=(TextView)v.findViewById(R.id.record_item1_recordID_tv);
		  final TextView tv_teamname=(TextView)v.findViewById(R.id.tv_teamname);
		  tv_header=(TextView)v.findViewById(R.id.tvheader);
		  tv_taskID=(TextView)v.findViewById(R.id.record_item1_taskID_tv);
		  tv_taskname=(TextView)v.findViewById(R.id.task_item1_name_tv);
		  tv_taskcreatedtime=(TextView)v.findViewById(R.id.task_item2_createdtime_tv);
		  tv_taskprogress=(TextView)v.findViewById(R.id.task_item16_progress_tv);
		  tv_taskdeadline=(TextView)v.findViewById(R.id.task_item15_deadline_tv);
		  tv_taskstatus=(TextView)v.findViewById(R.id.task_item18_status_tv);
		  tv_recordtime=(TextView)v.findViewById(R.id.record_item2_recordTime_tv);
		  et_recordprogress=(EditText)v.findViewById(R.id.record_item4_progress_et);
		  et_recordcomment=(EditText)v.findViewById(R.id.record_item5_comment_et);
		  cb_mileStone=(CheckBox)v.findViewById(R.id.cb_mileStone);
		  cb_quickfinish=(CheckBox)v.findViewById(R.id.cb_quickfinish);
		  view_taskclose=(LinearLayout)v.findViewById(R.id.view_taskclose);
		  final LinearLayout ll_new_prog=(LinearLayout)v.findViewById(R.id.ll_newprog);
		  //final ListView m_listview=(ListView)v.findViewById(R.id.task_show_lv);
		  btn_recordclose = (Button) v.findViewById(R.id.record_close_bt);  
		  btn_recordsubmit=(Button)v.findViewById(R.id.record_submit_bt);
		  btn_canceltask=(Button)v.findViewById(R.id.record_canceltask_bt);
		  btn_closetask=(Button)v.findViewById(R.id.record_closetask_bt);
		  tv_maxValue=(TextView)v.findViewById(R.id.tv_MaxValue);
		  rv_prog=(RecyclerView) v.findViewById(R.id.rv_prog);
		  sb_prog=(SeekBar)v.findViewById(R.id.sb_prog);
		  et_actualValue=(TextView)v.findViewById(R.id.et_actualValue);
		final CheckBox cb_addcomment=(CheckBox) v.findViewById(R.id.cb_add) ;
		final LinearLayout ll_addcomment=(LinearLayout) v.findViewById(R.id.ll_add) ;
		ll_addcomment.setVisibility(View.INVISIBLE);

		  LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());  
		//设置布局管理器  
		  rv_prog.setLayoutManager(layoutManager);  
		//设置为垂直布局，这也是默认的  
		layoutManager.setOrientation(OrientationHelper. VERTICAL);  
		//设置Adapter  
		
		 Double totalprog = 0.0;
		 MIN_MARK=0;
		 //MAX_MARK=100;
		 MAX_VALUE=0;
		   
		  
		     //tv_maxValue.setText(""+MAX_VALUE);
		  
			 TaskData.valuelist=null;
			 TaskData.valuelist=new ArrayList<RecordBean>();
			 
	  if (TaskData.selTaskID!=null){
		 tv_taskID.setText(TaskData.selTaskID);
		 String a = TaskData.TdDB.TABLE_NAME_TaskMain;
	     String b = TaskData.TdDB.TABLE_NAME_TaskRecord;

	     c = TaskData.db_TdDB.rawQuery("select * from "+a+" where "+TaskData.TdDB.TASK_ID+"="+TaskData.selTaskID,null);
		 //
		 if (c.getCount()>0){
	        c.moveToFirst();
	         t_sn=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));

			     teamname=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_USERGROUP));
			 if (teamname!=null){
				 tv_teamname.setText("(团队:"+teamname+")");
			 }

			 TaskData.selTaskSN=t_sn;
		     tv_taskID.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_ID)));
		     tv_taskname.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME)));
		     tv_taskdeadline.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
		    // tv_taskcreatedtime.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME)));
		     tv_taskprogress.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
		     tv_taskstatus.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)));
		     tv_recordtime.setText(TaskTool.getCurTime());
		     curRecordCount=Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_RECORDCOUNT)));
			 tasktask=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS));
		     //MAX_MARK=100-Double.parseDouble(tv_taskprogress.getText().toString());
		     
		     duration_unit=Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATIONUNIT)));
			 duration=Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATION)));
			    switch (duration_unit){
		          
			    case 1: hr_timeunit=TaskData.prehour;
			    		plannedtime=duration*hr_timeunit;
			    		Log.d(Tags,"plannedtime:"+plannedtime);
			    		break;
			    case 2: hr_timeunit=1;
			    		plannedtime=duration*hr_timeunit;;
			    		Log.d(Tags,"plannedtime:"+plannedtime);
			    		break;
			    case 3: hr_timeunit=(double)1/60;
			    		plannedtime=duration*hr_timeunit;;
			    		Log.d(Tags,"plannedtime:"+plannedtime);
			    		break;
			    		default:break;	
			    }	
			     
		     
		    if (Double.parseDouble(tv_taskprogress.getText().toString())>=100){
				//view_taskclose.setVisibility(View.VISIBLE);
				btn_closetask.setEnabled(true);
				btn_recordsubmit.setEnabled(false);
			    ll_new_prog.setVisibility(View.INVISIBLE);
				
			} 
		    if (c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)).equals("finished")){
		    	btn_closetask.setEnabled(false);
		    }
		  } 
		 
		    c_r = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.TASK_SN+"="+ "'"+TaskData.selTaskSN+"'",null);
		    Log.d(Tags,"c_r count "+c_r.getCount()+ TaskData.selTaskSN);
		    if (c_r!=null&&c_r.getCount()>0){
		      c_r.moveToFirst();
		      do{
		         String pro_weight = c_r.getString(c_r.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT));
		         if (!TextUtils.isEmpty(pro_weight)){
		            totalprog=totalprog+Double.parseDouble(c_r.getString(c_r.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
		          }else{
		        	  totalprog=totalprog+0;  
		          }		    	  
		      }while(c_r.moveToNext());
			 //tv_taskID.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_ID)));
			 //tv_taskname.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME)));
			 //tv_taskdeadline.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
			// tv_taskcreatedtime.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME)));
		    }
		     //tv_taskprogress.setText(String.valueOf(totalprog));
			 MAX_VALUE=100-GetTotalWeight(c_r);
			 tv_maxValue.setText(""+MAX_VALUE);
		 //TaskData.valuelist.clear();
		   c_rf= TaskData.db_TdDB.rawQuery("select * from "+b+" where "+
			              TaskData.TdDB.TASK_SN+"="+"'"+t_sn+"'"+		              
				         " and "+TaskData.TdDB.RECORD_DONE+"="+"'"+"false"+"'",null); 
		   if (c_rf!=null&&c_rf.getCount()>0){
			 
		     cursorToArrayList(c_rf,TaskData.valuelist);
		 
		   }
		 
		 adapter_prog = new mRecyclerAdapter(getActivity(),TaskData.valuelist,R.layout.lvitem_prog);
         rv_prog.setAdapter(adapter_prog);

		  TaskData.adaptRecordDetails = new mcAdapter_recorddetails(getActivity(),
				  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.TASK_SN+"="+"'"+TaskData.selTaskSN+"'",null),
				  R.layout.lvitem_taskdetailrecord);
          
         //rv_prog.setAdapter( recycleAdapter);  
		 //设置分隔线  
		//rv_prog.addItemDecoration( new DividerGridItemDecoration(getActivity() ));  
		//设置增加或删除条目的动画  
		//rv_prog.setItemAnimator( new DefaultItemAnimator()); 
	     }else {    	
	      Toast.makeText(getActivity(), "请先选定任务", Toast.LENGTH_SHORT).show();	 
	      dismiss();
	     }
	   
		 btn_recordclose.setOnClickListener(new OnClickListener() {  
	  
	           
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
		 
		   final ContentValues cv = new ContentValues();
		
		  et_actualValue.addTextChangedListener(new TextWatcher(){

			  @Override 
	            public void onTextChanged(CharSequence s, int start, int before, int count) {
				  /*
	                if (start > 1) 
	                { 
	                    if (MIN_MARK != -1 && MAX_VALUE != -1) 
	                    { 
	                      int num = Integer.parseInt(s.toString()); 
	                      if (num > MAX_VALUE) 
	                      {     //et_actualValue.setText("0");
	                            //et_actualValue.setBackgroundColor(Color.RED);
	                    	    //Toast.makeText(getActivity(), "输入数据过大,请重新输入", Toast.LENGTH_SHORT).show(); 
	                      } 
	                      else if(num < MIN_MARK) 
	                    	    //Toast.makeText(getActivity(), "输入数据过小,请重新输入", Toast.LENGTH_SHORT).show(); 
	                      return; 
	                    } 
	                }
	                */
	            } 

	            @Override 
	            public void beforeTextChanged(CharSequence s, int start, int count, 
	                    int after) { 
	            	//Toast.makeText(getActivity(), "Max:"+MAX_VALUE, Toast.LENGTH_SHORT).show(); 
	            } 

	            @Override 
	            public void afterTextChanged(Editable s) 
	            { 
	                if (s != null && !TextUtils.isEmpty(s)&&TaskTool.isNumeric(s.toString().trim()))
	                { 
	                    if (MIN_MARK != -1 && MAX_VALUE != -1) 
	                    { 
	                         int markVal = 0; 
	                         try 
	                         { 
	                             markVal = Integer.parseInt(s.toString()); 
	                         } 
	                         catch (NumberFormatException e) 
	                         { 
	                             markVal = 0; 
	                         } 
	                         if (markVal > MAX_VALUE) 
	                         {   et_actualValue.setText("");
	                             //et_actualValue.setBackgroundColor(Color.RED);
	                             Toast.makeText(getActivity(), "输入数据过大,请输入数值<MAX", Toast.LENGTH_SHORT).show(); 
	                           //  et.setText(String.valueOf(MAX_MARK)); 
	                         } 
	                         return; 
	                    } 
	                 }else{
						  et_actualValue.setText("0");
	                	  //Toast.makeText(getActivity(), "请输入数值", Toast.LENGTH_SHORT).show();
	                 }
	            } 
	        }); 
	      
		  /*
		  sb_prog.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				String seekbar_prog = df.format(progress* MAX_VALUE/100);
				et_actualValue.setText(seekbar_prog );
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			  	  
		  });
		   */

		cb_addcomment.setChecked(true);
		cb_addcomment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					ll_addcomment.setVisibility(View.INVISIBLE);
					//Toast.makeText(getActivity(), "显示", Toast.LENGTH_SHORT).show();
				}else{
					ll_addcomment.setVisibility(View.VISIBLE);
					//Toast.makeText(getActivity(), "隐藏", Toast.LENGTH_SHORT).show();
				}
			}
		});

		  btn_recordsubmit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
			   Log.d(Tags,""+TaskData.valuelist.size());
				String inputstr_recordprogress = et_recordcomment.getText().toString().trim();
				String inputstr_recordMileStone;
				if (cb_mileStone.isChecked()){
					inputstr_recordMileStone= String.valueOf(cb_mileStone.isChecked());
				}else{
					inputstr_recordMileStone= "";
				};
			   double actualValue=0.0;

			   if (!TextUtils.isEmpty(et_actualValue.getText().toString().trim())&&TaskTool.isNumeric( et_actualValue.getText().toString().trim())){
			        actualValue=Double.parseDouble(et_actualValue.getText().toString());
			   }else {
				   //return;
				    //Toast.makeText(getActivity(),"请输入数字",Toast.LENGTH_SHORT).show();
			   }
			   //String inputstr_taskname = et_recordcomment.getText().trim();
			   if (TextUtils.isEmpty(inputstr_recordprogress)&&MAX_VALUE<=100) {
					//Toast.makeText(getActivity(),"请输入进度数值" , Toast.LENGTH_SHORT).show();
                  if (TaskData.valuelist.size()>0 && TaskData.valuelistflag==1){
				    for (int i=0;i<TaskData.valuelist.size();i++){
				    	
				     if (((RecordBean)TaskData.valuelist.get(i)).getRECORD_DONE()=="true"){
				    	String p=TaskData.valuelist.get(i).getRECORD_SN();
				    	String idno=TaskData.valuelist.get(i).getRECORD_TASKID_NO();
				    	Cursor c2=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.RECORD_SN+"="+"'"+p+"'", null);
				        Log.d(Tags+"|recordsn|"+i, p+"c2 count"+c2.getCount());
				       if (c2!=null&&c2.getCount()>0){
				    	  c2.moveToFirst();
				    	  double curProg=Double.parseDouble(c2.getString(c2.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
				    	  ContentValues cv2 = new ContentValues();		  
				    	  cv2.put(TaskData.TdDB.RECORD_TIME,curTime);
						  cv2.put(TaskData.TdDB.RECORD_CLOSEDTIME,curTime);
						  cv2.put(TaskData.TdDB.RECORD_DONE,"true");
				    	  cv2.put(TaskData.TdDB.RECORD_TASKID_NO,idno); 
				    	  cv2.put(TaskData.TdDB.TASK_SN, t_sn);
				    	  cv2.put(TaskData.TdDB.RECORD_PROGRESS,curProg);
				    	  cv2.put(TaskData.TdDB.RECORD_COMMENTS,((RecordBean)TaskData.valuelist.get(i)).getRECORD_COMMENTS());
				    	  cv2.put(TaskData.TdDB.RECORD_DETAILS,((RecordBean)TaskData.valuelist.get(i)).getRECORD_DETAILS());
						  cv2.put(TaskData.TdDB.RECORD_CHANGED,((RecordBean)TaskData.valuelist.get(i)).getRECORD_CHANGED());
				    	  cv2.put(TaskData.TdDB.TASK_USER,TaskData.user);
				    	  cv2.put(TaskData.TdDB.TASK_OWNER,TaskData.user);
						  cv2.put(TaskData.TdDB.RECORD_TYPE,inputstr_recordMileStone);
						  cv2.put(TaskData.TdDB.TASK_USERGROUP,teamname);
				    	  String whereas=TaskData.TdDB.RECORD_SN+"=?";
						  String[] whereArgs={p};
						  long update2=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskRecord, cv2, whereas, whereArgs);
						  newProg=newProg+curProg;
						  Log.d(Tags+"|record update|"+i, "done"+update2);
				       } 
				    }else{
				        if (((RecordBean)TaskData.valuelist.get(i)).getRECORD_CHANGED()=="true"){
					    	String sn_u=TaskData.valuelist.get(i).getRECORD_SN();
					    	//String idno=TaskData.valuelist.get(i).getRECORD_TASKID_NO();
					    	Cursor c_u=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.RECORD_SN+"="+"'"+sn_u+"'", null);
					      if (c_u!=null&&c_u.getCount()>0){
					    	c_u.moveToFirst();
					    	double curProg=Double.parseDouble(c_u.getString(c_u.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
					    	ContentValues cv_u = new ContentValues();		  
					    	 cv_u.put(TaskData.TdDB.RECORD_TIME,curTime);
							 cv_u.put(TaskData.TdDB.RECORD_COMMENTS,((RecordBean)TaskData.valuelist.get(i)).getRECORD_COMMENTS());
							 cv_u.put(TaskData.TdDB.RECORD_DETAILS,((RecordBean)TaskData.valuelist.get(i)).getRECORD_DETAILS());
							 cv_u.put(TaskData.TdDB.RECORD_CHANGED,((RecordBean)TaskData.valuelist.get(i)).getRECORD_CHANGED());
							 cv_u.put(TaskData.TdDB.RECORD_AUTHOR,((RecordBean)TaskData.valuelist.get(i)).getRECORD_AUTHOR());
					    	 String whereas=TaskData.TdDB.RECORD_SN+"=?";
							 String[] whereArgs={sn_u};
							long update_u=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskRecord, cv_u, whereas, whereArgs);
							Log.d(Tags+"|record changed|"+i, "done"+update_u); 
					      }	
					    } 
				     }  
				   }   
				         
				    
				    String where = TaskData.TdDB.TASK_SN + " = ?";
					String[] whereValue = { t_sn }; 
					c.moveToFirst();
					Double prog_old= Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
					Double prog_new = newProg;
					Double cur_prog=prog_old+prog_new;
					 pro_sum = df.format(cur_prog);
					//cur_prog
					
				   
				    /*
				    Toast.makeText(getActivity(), ""+task_lapsedpercent+"\n"+
				    		startedtimedata+"\n"+
				    		deadlinetimedata+"\n"+
				    		new Date().getTime()/60000+"\n", Toast.LENGTH_LONG).show();
				    Log.d("lapsedpercent", ""+task_lapsedpercent);
				    */
				    /*tv_header.setText(""+task_lapsedpercent+"\n"+
				    		startedtimedata+"\n"+
				    		deadlinetimedata+"\n"+
				    		new Date().getTime()/60000+"\n");
				    */
				    
				   
				    
				    int old_enjoyment = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.SUM_ENJOYMENT)));
					int new_enjoyment= (int) ((Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PASSION)))
							*plannedtime
							*cur_prog/100));
					int cur_enjoyment=old_enjoyment+new_enjoyment;
					Log.d(Tags,"old"+old_enjoyment+"new"+new_enjoyment);
					int old_achieved = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.SUM_ACHIEVED)));
					int new_achieved= (int) ((Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE)))
							*plannedtime
							*cur_prog/100));
					int cur_achieved=old_achieved+new_achieved;
					 Log.d(Tags, "old"+old_achieved+"new"+new_achieved);
					
					int old_experience = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.SUM_EXPERIENCE)));
					int new_experience= (int) ((Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DIFFICULTY)))
							*plannedtime
							*cur_prog/100));
					int cur_experience=old_experience+new_experience;
					Log.d(Tags, "old"+old_experience+"new"+new_experience);
					/*
					int old_recordcount = c.getInt((c.getColumnIndex(TaskData.TdDB.TASK_RECORDCOUNT)));
					int new_recordcount= old_recordcount+1;
					*/
					ContentValues cv = new ContentValues();    
				
					cv.put(TaskData.TdDB.TASK_PROGRESS, pro_sum); 
					long updateProg=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 	
					Log.d(Tags,"pro_sum update"+updateProg+" "+pro_sum);
					//long updateAccomplished=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 	
					cv.put(TaskData.TdDB.TASK_MODIFIED,TaskTool.getCurTime());
					cv.put(TaskData.TdDB.SUM_ACHIEVED, String.valueOf(cur_achieved)); 
					
					//long updateAchieved=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
					cv.put(TaskData.TdDB.SUM_ENJOYMENT, String.valueOf(cur_enjoyment)); 
					//long updateEnjoyment=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
					cv.put(TaskData.TdDB.SUM_EXPERIENCE, String.valueOf(cur_experience)); 	
					//long updateExperience=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
					//cv.put(TaskData.TdDB.TASK_RECORDCOUNT,new_recordcount);
				    long updateRecordCount=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
				    // Log.d("update data in Task", "Prog "+updateProg+"achieved"+updateAchieved+"enjoyment"+updateEnjoyment+"experience"+updateExperience);
			
				    TaskData.getRecordSequenceNo(t_sn); 
				    TaskData.adapterUpdate();
				    
				    c.requery();
				    c_rf.requery();
				    Log.d(Tags, "c count"+c.getCount());
				    c.moveToFirst();
				    c_rf.moveToFirst();
				    tv_taskprogress.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
					 //tv_taskstatus.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)));
				    MAX_VALUE=100-GetTotalWeight(c_r); 
					tv_maxValue.setText(""+MAX_VALUE);
				    tv_recordtime.setText(TaskTool.getCurTime());
					curRecordCount=Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_RECORDCOUNT)));
				
					
					 TaskData.valuelist=null;
					 TaskData.valuelist=new ArrayList<RecordBean>();
					 cursorToArrayList(c_rf,TaskData.valuelist);
					 TaskData.update_sum(TaskData.tv_progress,c,TaskData.TdDB.TASK_PROGRESS);
					 adapter_prog = new mRecyclerAdapter(getActivity(),TaskData.valuelist,R.layout.lvitem_prog);
			         rv_prog.setAdapter(adapter_prog);
			         TaskData.adaptRecordDetails.getCursor().requery();
			         TaskData.adaptRecordDetails.notifyDataSetChanged();
					 //adapter_prog.notifyDataSetChanged();
					 Log.d(Tags,"c_rf update done"); 
					 TaskData.valuelistflag=0;	
					 newProg=0.0;
					 sb_prog.setProgress(0);
					 et_actualValue.setText("");
					 et_recordcomment.setText("");	

						if (cur_prog>=100 && TaskData.valuelist.size()<=0){
							ll_new_prog.setVisibility(View.INVISIBLE);
							btn_closetask.setEnabled(true);
							//btn_canceltask.setEnabled(false);
						    btn_recordsubmit.setEnabled(false);
							
						}
						Toast.makeText(getActivity(), "进度保存成功", Toast.LENGTH_SHORT).show();
                 } else{
				    	Toast.makeText(getActivity(), "无进度内容", Toast.LENGTH_SHORT).show();
				    }
                   
			   }else{	
			
				 if (MAX_VALUE<=100) {
					 ContentValues cv0 = new ContentValues();
					 c.requery();
					 c.moveToFirst();
					 int curRecordCount1 = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_RECORDCOUNT)));
				     cv0.put(TaskData.TdDB.RECORD_TASKID, tv_taskID.getText().toString());
				     //cv.put(TaskData.TdDB.RECORD_TASKID_NO, tv_taskID.getText().toString()+"_P"+String.valueOf(curRecordCount+1));
			 	     cv0.put(TaskData.TdDB.RECORD_TASKID_NO, "P"+String.valueOf(curRecordCount1+1));
			 	     cv0.put(TaskData.TdDB.RECORD_CREATEDTIME, curTime);
				     cv0.put(TaskData.TdDB.TASK_SN, t_sn);
				     cv0.put(TaskData.TdDB.RECORD_SN, t_sn+TaskTool.addZero(curRecordCount1+1,4));
				     cv0.put(TaskData.TdDB.RECORD_TIME, curTime);
				     cv0.put(TaskData.TdDB.RECORD_DEADLINE,curTime);
				     cv0.put(TaskData.TdDB.RECORD_CLOSEDTIME,curTime);
				     cv0.put(TaskData.TdDB.RECORD_PROGRESS,actualValue);
				     cv0.put(TaskData.TdDB.RECORD_COMMENTS, et_recordcomment.getText().toString());
					 cv0.put(TaskData.TdDB.RECORD_TYPE,inputstr_recordMileStone);
				     cv0.put(TaskData.TdDB.RECORD_WEIGHT, actualValue);
				     cv0.put(TaskData.TdDB.TASK_USER,TaskData.user);
			         cv0.put(TaskData.TdDB.TASK_OWNER,TaskData.user);
					 cv0.put(TaskData.TdDB.TASK_USERGROUP,teamname);
				     cv0.put(TaskData.TdDB.RECORD_DONE,"true");
				    
				     
				     newProg=actualValue;
				     long insertOK = TaskData.db_TdDB.insert(TaskData.TdDB.TABLE_NAME_TaskRecord,null, cv0);
				     Log.d(Tags,"insert data in record ok"+insertOK);
				     
				   if (TaskData.valuelist.size()>0 && TaskData.valuelistflag==1){
				     for (int i=0;i<TaskData.valuelist.size();i++){
				    	
				     if (((RecordBean)TaskData.valuelist.get(i)).getRECORD_DONE()=="true"){
				    	String p=TaskData.valuelist.get(i).getRECORD_SN();
				    	String idno=TaskData.valuelist.get(i).getRECORD_TASKID_NO();
				    	Cursor c2=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.RECORD_SN+"="+"'"+p+"'", null);
				        if (c2!=null&&c2.getCount()>0){
				         c2.moveToFirst();	
				    	 double curProg=Double.parseDouble(c2.getString(c2.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
				    	 ContentValues cv2 = new ContentValues();		  
				    	 cv2.put(TaskData.TdDB.RECORD_TIME,curTime);
						 cv2.put(TaskData.TdDB.RECORD_CLOSEDTIME,curTime);
						 cv2.put(TaskData.TdDB.RECORD_DONE,"true");
				    	 cv2.put(TaskData.TdDB.RECORD_TASKID_NO,idno); 
				    	 cv2.put(TaskData.TdDB.RECORD_DETAILS,((RecordBean)TaskData.valuelist.get(i)).getRECORD_DETAILS());
						 cv2.put(TaskData.TdDB.RECORD_CHANGED,((RecordBean)TaskData.valuelist.get(i)).getRECORD_CHANGED());
				    	 //cv2.put(TaskData.TdDB.RECORD_SN, t_sn+TaskTool.addZero(Integer.parseInt(p),4));
				    	 cv2.put(TaskData.TdDB.RECORD_PROGRESS,curProg);
				    	 String whereas=TaskData.TdDB.RECORD_SN+"=?";
						 String[] whereArgs={p};
						 long update2=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskRecord, cv2, whereas, whereArgs);
						 newProg=newProg+curProg;
						 Log.d(Tags+"|record update|"+i, "done"+update2); 
				        } 
				    }else{
				        if (((RecordBean)TaskData.valuelist.get(i)).getRECORD_CHANGED()=="true"){
					    	String sn_u=TaskData.valuelist.get(i).getRECORD_SN();
					    	//String idno=TaskData.valuelist.get(i).getRECORD_TASKID_NO();
					    	Cursor c_u=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.RECORD_SN+"="+"'"+sn_u+"'", null);
					    	c_u.moveToFirst();
					    	if (c_u!=null&&c_u.getCount()>0){
					    	 double curProg=Double.parseDouble(c_u.getString(c_u.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
					    	 ContentValues cv_u = new ContentValues();		  
					    	 //cv_u.put(TaskData.TdDB.RECORD_TIME,curTime);
							 cv_u.put(TaskData.TdDB.RECORD_COMMENTS,((RecordBean)TaskData.valuelist.get(i)).getRECORD_COMMENTS());
							 cv_u.put(TaskData.TdDB.RECORD_DETAILS,((RecordBean)TaskData.valuelist.get(i)).getRECORD_DETAILS());
							 cv_u.put(TaskData.TdDB.RECORD_CHANGED,((RecordBean)TaskData.valuelist.get(i)).getRECORD_CHANGED());
							 cv_u.put(TaskData.TdDB.RECORD_AUTHOR,((RecordBean)TaskData.valuelist.get(i)).getRECORD_AUTHOR());
					    	 String whereas=TaskData.TdDB.RECORD_SN+"=?";
							 String[] whereArgs={sn_u};
							 long update_u=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskRecord, cv_u, whereas, whereArgs);
							 Log.d(Tags+"|record changed|"+i, "done"+update_u); 
					    	}
					    } 
				     }  
				   }   
				  }       
				    
				    String where = TaskData.TdDB.TASK_SN + " = ?";    
					String[] whereValue = { t_sn }; 
					c.moveToFirst();
					Double prog_old= Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
					Double prog_new = newProg;
					Double cur_prog=prog_old+prog_new;
				    pro_sum = df.format(cur_prog);
					//cur_prog
				   
					 
				    long startedtimedata;
				    startedtimedata=TimeData.changeStrToTime_YY(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STARTEDTIME)));
				    long deadlinetimedata = TimeData.changeStrToTime_YY(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
				    long timedata_taskduration=deadlinetimedata-startedtimedata;
				    Double task_lapsedpercent;
				    if (timedata_taskduration>0){
				        task_lapsedpercent=(double) ((new Date().getTime()/60000-startedtimedata))/(deadlinetimedata-startedtimedata)*100;
				    }else{
				    	task_lapsedpercent=100.0;
				    }
				   // NumberFormat nt = NumberFormat.getPercentInstance();
				    //nt.setMinimumFractionDigits(2); 
				   
				    str_tasklapsedpercent =df.format(task_lapsedpercent);
				    /*
				    Toast.makeText(getActivity(), ""+task_lapsedpercent+"\n"+
				    		startedtimedata+"\n"+
				    		deadlinetimedata+"\n"+
				    		new Date().getTime()/60000+"\n", Toast.LENGTH_LONG).show();
				    Log.d("lapsedpercent", ""+task_lapsedpercent);
				    */
				    /*tv_header.setText(""+task_lapsedpercent+"\n"+
				    		startedtimedata+"\n"+
				    		deadlinetimedata+"\n"+
				    		new Date().getTime()/60000+"\n");
				    */
				    int old_enjoyment = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.SUM_ENJOYMENT)));
					int new_enjoyment= (int) ((Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PASSION)))
							*plannedtime
							*cur_prog/100));
					int cur_enjoyment=old_enjoyment+new_enjoyment;
					Log.d(Tags,"old"+old_enjoyment+"new"+new_enjoyment);
					int old_achieved = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.SUM_ACHIEVED)));
					int new_achieved= (int)( (Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE)))
							*plannedtime
							*cur_prog/100));
					int cur_achieved=old_achieved+new_achieved;
					 Log.d(Tags, "old"+old_achieved+"new"+new_achieved);
					
					int old_experience = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.SUM_EXPERIENCE)));
					int new_experience= (int)( (Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DIFFICULTY)))
							*plannedtime
							*cur_prog/100));
					int cur_experience=old_experience+new_experience;
					Log.d(Tags, "old"+old_experience+"new"+new_experience);
					int old_recordcount = c.getInt((c.getColumnIndex(TaskData.TdDB.TASK_RECORDCOUNT)));
					int new_recordcount= old_recordcount+1;
					
					ContentValues cv = new ContentValues();    
					
					
				
					cv.put(TaskData.TdDB.TASK_PROGRESS, pro_sum); 
					//long updateProg=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 	
					//long updateAccomplished=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 	
					cv.put(TaskData.TdDB.TASK_MODIFIED,TaskTool.getCurTime());
					cv.put(TaskData.TdDB.SUM_ACHIEVED, String.valueOf(cur_achieved)); 
					
					//long updateAchieved=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
					cv.put(TaskData.TdDB.SUM_ENJOYMENT, String.valueOf(cur_enjoyment)); 
					//long updateEnjoyment=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
					cv.put(TaskData.TdDB.SUM_EXPERIENCE, String.valueOf(cur_experience)); 	
					//long updateExperience=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
					cv.put(TaskData.TdDB.TASK_RECORDCOUNT,new_recordcount);
				    //long updateRecordCount=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
				    long updatetask=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
				  
					
				    c.requery();
				    c_rf.requery();
				    TaskData.getRecordSequenceNo(t_sn);
				    TaskData.adapterUpdate();
				    
				    Log.d(Tags, "c count"+c.getCount());
				    c.moveToFirst();
				    c_rf.moveToFirst();
					
				    tv_taskprogress.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
				    MAX_VALUE=100-GetTotalWeight(c_r); 
					tv_maxValue.setText(""+MAX_VALUE);
				    //tv_taskstatus.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)));
					tv_recordtime.setText(TaskTool.getCurTime());
				    
					 TaskData.valuelist=null;
					 TaskData.valuelist=new ArrayList<RecordBean>();
					 cursorToArrayList(c_rf,TaskData.valuelist);
					 adapter_prog = new mRecyclerAdapter(getActivity(),TaskData.valuelist,R.layout.lvitem_prog);
			         rv_prog.setAdapter(adapter_prog);
			         TaskData.update_sum(TaskData.tv_progress,c,TaskData.TdDB.TASK_PROGRESS);
			         //TaskData.update_sum(TaskData.tv_progress,c,TaskData.TdDB.TASK_PROGRESS);
					 if (TaskData.adaptRecordDetails!=null) {
						  TaskData.adaptRecordDetails.getCursor().requery();
						  TaskData.adaptRecordDetails.notifyDataSetChanged();

					 }
						 //adapter_prog.notifyDataSetChanged();
					 Log.d(Tags,"c_rf update done"); 		 
					 TaskData.valuelistflag=0;	
					 newProg=0.0;
					 sb_prog.setProgress(0);
					 et_actualValue.setText("");
					 et_recordcomment.setText("");
					 cb_mileStone.setChecked(false);
					 Toast.makeText(getActivity(), "进度保存成功", Toast.LENGTH_SHORT).show();
					 if (cur_prog>=100 && TaskData.valuelist.size()<=0){
							
							btn_closetask.setEnabled(true);
							//btn_k.setEnabled(false);
						    btn_recordsubmit.setEnabled(false);
						    ll_new_prog.setVisibility(View.INVISIBLE);
						}
					
		 	     }		   
		 	   }
			 } 
		    });
		  

		  btn_closetask.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stu	
					 /* String where = TaskData.TdDB.TASK_ID + " = ?";    
						String[] whereValue = { TaskData.selTaskID }; 
						ContentValues cv3 = new ContentValues();    
						cv3.put(TaskData.TdDB.TASK_STATUS, "close"); 
						cv3.put(TaskData.TdDB.TASK_FINISHED, "yes");
						cv3.put(TaskData.TdDB.TASK_STATUS, "finished");
						cv3.put(TaskData.TdDB.TASK_CLOSEDTIME, curTime);
						long updateOK=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv3, where, whereValue); 
					  Log.d("update Task status", "ok"+updateOK);*/
					 dismiss(); 
					// DialogFragment_CloseTask dialogfrag_closetask=new DialogFragment_CloseTask();
					  //dialogfrag_closetask.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
					  //dialogfrag_closetask.setStyle(DialogFragment.STYLE_NO_TITLE, 0);  
					  //showDialog(dialogfrag_closetask);
						//Log.d("dialogfrag", dialogfrag_closetask.toString());
						DialogFragment_CloseTask dialogfrag_closetask=new DialogFragment_CloseTask();
						//dialogfrag_canceltask.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
						//dialogfrag_canceltask.setStyle(DialogFragment.STYLE_NO_TITLE, 0);  
						showDialog(dialogfrag_closetask);	
						
				}			  
		  });
		  
		  btn_canceltask.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stu	
					 /* String where = TaskData.TdDB.TASK_ID + " = ?";    
						String[] whereValue = { TaskData.selTaskID }; 
						ContentValues cv3 = new ContentValues();    
						cv3.put(TaskData.TdDB.TASK_STATUS, "close"); 
						cv3.put(TaskData.TdDB.TASK_FINISHED, "yes");
						cv3.put(TaskData.TdDB.TASK_STATUS, "finished");
						cv3.put(TaskData.TdDB.TASK_CLOSEDTIME, curTime);
						long updateOK=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv3, where, whereValue); 
					  Log.d("update Task status", "ok"+updateOK);*/
					
					// DialogFragment_CloseTask dialogfrag_closetask=new DialogFragment_CloseTask();
					  //dialogfrag_closetask.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
					  //dialogfrag_closetask.setStyle(DialogFragment.STYLE_NO_TITLE, 0);  
					  //showDialog(dialogfrag_closetask);
						//Log.d("dialogfrag", dialogfrag_closetask.toString());
					if ( tasktask.equals("open")){
					    DialogFragment_CancelTask dialogfrag_canceltask=new DialogFragment_CancelTask();
						//dialogfrag_canceltask.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
						//dialogfrag_canceltask.setStyle(DialogFragment.STYLE_NO_TITLE, 0);  
						showDialog(dialogfrag_canceltask);
						  dismiss(); 
					}else{
						 Toast.makeText(getActivity(), "非OPEN的任务无法撤销",Toast.LENGTH_SHORT).show();	
					}
						
				}			  
		  });
		   
		    btn_recordview = (Button) v.findViewById(R.id.record_view_bt);
			  btn_recordview.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						DialogFragment_CommentList dialogfrag_commentlist=new DialogFragment_CommentList();
						//dialogfrag_commentlist.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
						dialogfrag_commentlist.setStyle(DialogFragment.STYLE_NO_TITLE, 0);  	
						TaskTool.showDialog(dialogfrag_commentlist);
							
					}
			    	
			    	
			    }); 
		  
			return v;
	}	

   public double GetTotalWeight(Cursor c_r){
	    double totalprog=0;		
	    c_r.requery();
	    if (c_r!=null&&c_r.getCount()>0){
	      c_r.moveToFirst();
	      do{
	         String item_weight = c_r.getString(c_r.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT));
	         if (!TextUtils.isEmpty(item_weight)){
	              totalprog=totalprog+Double.parseDouble(item_weight);
	          }else{
	        	  totalprog=totalprog+0;  
	          }		    	  
	      }while(c_r.moveToNext());
		 //tv_taskID.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_ID)));
		 //tv_taskname.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME)));
		 //tv_taskdeadline.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
		// tv_taskcreatedtime.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME)));
	    }
	     //tv_taskprogress.setText(String.valueOf(totalprog));
	    Log.d(Tags+"|GetTotalWeight|","c_r count "+c_r.getCount()+" totalprog"+totalprog);
		 return totalprog;
    }
	
	
	public void ClearArrayList(ArrayList arraylist){
	  if (arraylist!=null)
		 if (arraylist.size()>0){
			for (int i=0;i<arraylist.size();i++){
				arraylist.remove(i);
			}
	  }else{
	   arraylist=new ArrayList<RecordBean>();	 
	  }	  
	}
	
	public void cursorToArrayList(Cursor c_rf,ArrayList arraylist){
		
		 if (c_rf.getCount()>0){
			    Log.d(Tags+"|cursorToArrayList|", "count"+c_rf.getCount());
			    TaskData.valuelist=new ArrayList<RecordBean>();
			    c_rf.moveToFirst();
	         do{
	            RecordBean recbean = new RecordBean();
	            recbean.setRECORD_SN(c_rf.getString(c_rf.getColumnIndex(TaskData.TdDB.RECORD_SN)));
	            recbean.setRECORD_TASKID_NO(c_rf.getString(c_rf.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));
	            recbean.setRECORD_COMMENTS(c_rf.getString(c_rf.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS)));
	            recbean.setRECORD_DONE(c_rf.getString(c_rf.getColumnIndex(TaskData.TdDB.RECORD_DONE)));
	            recbean.setRECORD_WEIGHT(c_rf.getString(c_rf.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
	            if (c_rf.getString(c_rf.getColumnIndex(TaskData.TdDB.RECORD_CHANGED))!=null){
	              recbean.setRECORD_CHANGED(c_rf.getString(c_rf.getColumnIndex(TaskData.TdDB.RECORD_CHANGED)));
	            }
	            if (c_rf.getString(c_rf.getColumnIndex(TaskData.TdDB.RECORD_DETAILS))!=null){
	              recbean.setRECORD_DETAILS(c_rf.getString(c_rf.getColumnIndex(TaskData.TdDB.RECORD_DETAILS)));
	            }
	            Log.d(Tags+"|valuelist recordsn|", ""+c_rf.getString(c_rf.getColumnIndex(TaskData.TdDB.RECORD_SN)));
	            TaskData.valuelist.add(recbean);
	          }while(c_rf.moveToNext());
	          
	           for (int i=0;i<TaskData.valuelist.size();i++){
	        //Log.d("taskidno",TaskData.valuelist.get(i).getRECORD_TASKID_NO());
	    	    Log.d(Tags+"|rec done|",""+TaskData.valuelist.get(i).getRECORD_DONE());
	           }
			 }
			 adapter_prog = new mRecyclerAdapter(getActivity(),TaskData.valuelist,R.layout.lvitem_prog);
	         rv_prog.setAdapter(adapter_prog);
		
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
	 public void onStart() {
	 	// TODO Auto-generated method stub
	 	super.onStart();
	 	DisplayMetrics dm = new DisplayMetrics();
	 	getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
	 	getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	 }  	
	 		
}
