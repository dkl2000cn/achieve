package com.easygoal.achieve;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DialogFragment_GroupRecord extends DialogFragment {
	String curTime;
	int curRecordCount;
	String pro_sum; 
	String str_tasklapsedpercent;
	Cursor c;
	Cursor c1;
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
	  LinearLayout view_taskclose;
	  //final ListView m_listview=(ListView)v.findViewById(R.id.task_show_lv);
	  Button btn_recordclose;  
	  Button btn_recordsubmit;
	  Button btn_quit;
	  Button btn_closetask;
	  Button btn_recordview;
	  RecyclerView rv_prog;
	  SeekBar sb_prog;
	  TextView et_actualValue;
	  //TextView tv_maxValue;
	  Double newProg=0.0;
	   final int MIN_MARK=0;
	    double MAX_MARK=100;
	    double MAX_VALUE=0;
	    String t_sn;
	    mRecyclerAdapter adapter_prog;
	    DecimalFormat df ;
	    String Tags="DialogFragment_GroupRecord";
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
		
		View v=inflater.inflate(R.layout.dialogfg_addgrouprecord, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		 
		
		  //TextView tv1=(TextView)v.findViewById(R.id.record_item1_recordID_tv);
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
		  view_taskclose=(LinearLayout)v.findViewById(R.id.view_taskclose);
		  //final ListView m_listview=(ListView)v.findViewById(R.id.task_show_lv);
		  btn_recordclose = (Button) v.findViewById(R.id.record_close_bt);  
		  btn_recordsubmit=(Button)v.findViewById(R.id.record_submit_bt);
		  //btn_canceltask=(Button)v.findViewById(R.id.record_canceltask_bt);
		  btn_closetask=(Button)v.findViewById(R.id.record_closetask_bt);
		  btn_quit=(Button)v.findViewById(R.id.record_quit_bt);
		  //tv_maxValue=(TextView)v.findViewById(R.id.tv_MaxValue);
		  rv_prog=(RecyclerView) v.findViewById(R.id.rv_prog);
		  sb_prog=(SeekBar)v.findViewById(R.id.sb_prog);
		  et_actualValue=(TextView)v.findViewById(R.id.et_actualValue);
		  LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());  
		//设置布局管理器  
		  rv_prog.setLayoutManager(layoutManager);  
		//设置为垂直布局，这也是默认的  
		layoutManager.setOrientation(OrientationHelper.VERTICAL);  
		//设置Adapter  
		
		 btn_closetask.setEnabled(false);
		 btn_recordsubmit.setEnabled(true);
		 Double totalprog = 0.0;
		    Cursor c_r = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+ToDoDB.RECORD_TASKID+"="+TaskData.selTaskID,null);
		   
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
		   
			 MAX_VALUE=100-totalprog;
		  
		     //tv_maxValue.setText(""+MAX_VALUE);
		  
			 TaskData.valuelist=null;
			 TaskData.valuelist=new ArrayList<RecordBean>();
			
	  if (TaskData.selTaskID!=null){
		 tv_taskID.setText(TaskData.selTaskID);
		 String a = TaskData.TdDB.TABLE_NAME_TaskMain;
	     String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
	     c = TaskData.db_TdDB.rawQuery("select * from "+a+" where "+ToDoDB.TASK_ID+"="+TaskData.selTaskID,null);
		 //
		 if (c.getCount()>0){
	        c.moveToFirst();
	        t_sn=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
		    tv_taskID.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_ID)));
		     tv_taskname.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME)));
		     tv_taskdeadline.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
		    // tv_taskcreatedtime.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME)));
		     tv_taskprogress.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
		     tv_taskstatus.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)));
		     tv_recordtime.setText(curTime);
		     curRecordCount=Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_RECORDCOUNT)));
		     MAX_MARK=100-Double.parseDouble(tv_taskprogress.getText().toString());
		   
		 
		    if (c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)).equals("finished")){
		    	btn_closetask.setEnabled(false);
		    }
		    if (Double.parseDouble(tv_taskprogress.getText().toString())>=100){
				//view_taskclose.setVisibility(View.VISIBLE);
				btn_closetask.setEnabled(true);
				btn_closetask.setClickable(true);
				btn_recordsubmit.setEnabled(false);
				btn_recordsubmit.setClickable(false);
			} else{
			    btn_closetask.setEnabled(false);
			    btn_closetask.setClickable(false);
				btn_recordsubmit.setEnabled(true);
				btn_recordsubmit.setClickable(true);
			}
		    
		  } 
		 
		
		 //TaskData.valuelist.clear();
		 c1 = TaskData.db_TdDB.rawQuery("select * from "+b+" where "+
			              TaskData.TdDB.TASK_SN+"="+"'"+t_sn+"'"+		              
				         " and "+ToDoDB.RECORD_DONE+"="+"'"+"false"+"'",null); 
		 if (c1!=null&&c1.getCount()>0){
			 
		     cursorToArrayList(c1,TaskData.valuelist);
		   for (int i=0;i<TaskData.valuelist.size();i++){
		        //Log.d("taskidno",TaskData.valuelist.get(i).getRECORD_TASKID_NO());
		    	    Log.d(Tags+"|rec done|",""+TaskData.valuelist.get(i).getRECORD_DONE());
           }
		 } 
		 /*
		 if (c1.getCount()>0){
		    Log.d("quene c1", ""+c1.getCount());
		    TaskData.valuelist=new ArrayList<RecordBean>();
		    c1.moveToFirst();
         do{
            RecordBean recbean = new RecordBean();
            recbean.setRECORD_TASKID_NO(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));
            recbean.setRECORD_COMMENTS(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS)));
            Log.d("valuelist ", ""+c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));
            recbean.setRECORD_DONE("false");
            recbean.setRECORD_WEIGHT(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
            TaskData.valuelist.add(recbean);
          }while(c1.moveToNext());
            Log.d("valuelist count", ""+TaskData.valuelist.size());
		    Log.d("valuelist count",""+TaskData.valuelist.size());
           */
		 
		 adapter_prog = new mRecyclerAdapter(getActivity(),TaskData.valuelist,R.layout.lvitem_prog);
         rv_prog.setAdapter(adapter_prog);
         
          
         //rv_prog.setAdapter( recycleAdapter);  
		 //设置分隔线  
		//rv_prog.addItemDecoration( new DividerGridItemDecoration(getActivity() ));  
		//设置增加或删除条目的动画  
		//rv_prog.setItemAnimator( new DefaultItemAnimator()); 
	     }else {    	
	      Toast.makeText(getActivity(), "未选定任务", Toast.LENGTH_LONG).show();	 
	      dismiss();
	     }
	   
	     
	     
	     
	     
	     //
		 btn_recordclose.setOnClickListener(new OnClickListener() {  
	  
	           
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
  	    //Log.d("show data","ok" +m_listview.toString());dvhu
		    /*
		    class MyHandler extends Handler  
		    {  
		        @Override  
		        public void handleMessage(Message msg) {  
		            // TODO Auto-generated method stub  
		            super.handleMessage(msg);  
		            if(msg.what==1)  
		            {  
		            	
		        		 tv_taskprogress.setText(msg.getData().getString("curprog"));
		        		 tv_recordtime.setText(msg.getData().getString("curTime"));
		        		 MAX_MARK=100-Double.parseDouble(tv_taskprogress.getText().toString());
		        		 Toast.makeText(getActivity(), String.valueOf(MAX_MARK), Toast.LENGTH_SHORT).show();
		            }  
		        }  
		    };  
		  
		    final MyHandler handler=new MyHandler();
		    class MyThread implements Runnable  
		    {  
		        
		        @Override  
		        public void run() {  
		            // TODO Auto-generated method stub  
				    Message msg=new Message();
				    msg.what=1;
				    Bundle bundle=new Bundle();  
                    
                    bundle.putString("curprog", pro_sum); 
                    msg.setData(bundle);
				    handler.sendMessage(msg);
		        }
		    };  
		    */
		 /*
		  et_actualValue.addTextChangedListener(new TextWatcher(){

			  @Override 
	            public void onTextChanged(CharSequence s, int start, int before, int count) { 
	                if (start > 1) 
	                { 
	                    if (MIN_MARK != -1 && MAX_VALUE != -1) 
	                    { 
	                      int num = Integer.parseInt(s.toString()); 
	                      if (num > MAX_VALUE) 
	                      { 
	                    	    Toast.makeText(getActivity(), "输入数据过大", Toast.LENGTH_SHORT).show(); 
	                      } 
	                      else if(num < MIN_MARK) 
	                    	    Toast.makeText(getActivity(), "输入数据过小", Toast.LENGTH_SHORT).show(); 
	                      return; 
	                    } 
	                } 
	            } 

	            @Override 
	            public void beforeTextChanged(CharSequence s, int start, int count, 
	                    int after) { 
	            	
	            } 

	            @Override 
	            public void afterTextChanged(Editable s) 
	            { 
	                if (s != null && !s.equals("")) 
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
	                         { 
	                             Toast.makeText(getActivity(), "输入数据过大", Toast.LENGTH_SHORT).show(); 
	                           //  et.setText(String.valueOf(MAX_MARK)); 
	                         } 
	                         return; 
	                    } 
	                 } 
	            } 
	        }); 
	       */ 
		  
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
		    
		  btn_recordsubmit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//c.moveToLast();
				//Log.d("insert data", "start"+String.valueOf(c.getPosition()));	
			   // String a = String.valueOf(c.getCount()+1);
			   // Log.d("db query","row"+String.valueOf(c.getCount()));
				//cv.put(TdDB.ID, a);
			   String inputstr_recordprogress = et_recordcomment.getText().toString().trim();
			   double actualValue=0.0;
			   if (!TextUtils.isEmpty(et_actualValue.getText().toString().trim())){
			    actualValue=Double.parseDouble(et_actualValue.getText().toString());
			   }
			   //String inputstr_taskname = et_recordcomment.getText().trim();
			   if (TextUtils.isEmpty(inputstr_recordprogress)) {
					//Toast.makeText(getActivity(),"进度不能为空，可以为0" , Toast.LENGTH_SHORT).show();
                  if (TaskData.valuelist.size()>0 && TaskData.valuelistflag==1){
				    for (int i=0;i<TaskData.valuelist.size();i++){
				    	
				     if (((RecordBean)TaskData.valuelist.get(i)).getRECORD_DONE()=="true"){
				    	String p=TaskData.valuelist.get(i).getRECORD_SN();
				    	String idno=TaskData.valuelist.get(i).getRECORD_TASKID_NO();
				    	Cursor c2=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.RECORD_SN+"="+"'"+p+"'", null);
				    	c2.moveToFirst();
				    	double curProg=Double.parseDouble(c2.getString(c2.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
				    	ContentValues cv2 = new ContentValues();		  
				    	 cv2.put(TaskData.TdDB.RECORD_TIME,curTime);
						 cv2.put(TaskData.TdDB.RECORD_CLOSEDTIME,curTime);
						 cv2.put(TaskData.TdDB.RECORD_DONE,"true");
				    	 cv2.put(TaskData.TdDB.RECORD_TASKID_NO,idno); 
				    	 cv2.put(TaskData.TdDB.TASK_SN, t_sn);
				    	 cv2.put(TaskData.TdDB.RECORD_PROGRESS,curProg);
				    	 cv2.put(TaskData.TdDB.TASK_USER,TaskData.user);
				    	 cv2.put(TaskData.TdDB.TASK_OWNER,TaskData.user);
				    	 String whereas=TaskData.TdDB.RECORD_SN+"=?";
						 String[] whereArgs={p};
						long update2=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskRecord, cv2, whereas, whereArgs);
						newProg=newProg+curProg;
						 Log.d("cv2 update"+i, "done"+update2); 
				    }
				   }   
				         
				    
				  String where = TaskData.TdDB.TASK_ID + " = ?";    
					String[] whereValue = { TaskData.selTaskID }; 
					Double prog_old= Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
					Double prog_new = newProg;
					Double cur_prog=prog_old+prog_new;
					 pro_sum = df.format(cur_prog);
					//cur_prog
					
				    long startedtimedata;
				    startedtimedata=TimeData.changeStrToTime_YY(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STARTEDTIME)));
				    long deadlinetimedata = TimeData.changeStrToTime_YY(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
				    Double task_lapsedpercent=(double) ((new Date().getTime()/60000-startedtimedata))/(deadlinetimedata-startedtimedata)*100;
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
					int new_enjoyment= (int) (Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PASSION)))
							*Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATION)))
							*cur_prog/100);
					int cur_enjoyment=old_enjoyment+new_enjoyment;
					Log.d("cur_enjoyment", "old"+old_enjoyment+"new"+new_enjoyment);
					int old_achieved = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.SUM_ACHIEVED)));
					int new_achieved= (int) (Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE)))
							*Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATION)))
							*cur_prog/100);
					int cur_achieved=old_achieved+new_achieved;
					 Log.d("cur_achieved", "old"+old_achieved+"new"+new_achieved);
					
					int old_experience = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.SUM_EXPERIENCE)));
					int new_experience= (int) (Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DIFFICULTY)))
							*Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATION)))
							*cur_prog/100);
					int cur_experience=old_experience+new_experience;
					Log.d("cur_experience", "old"+old_experience+"new"+new_experience);
					int old_recordcount = c.getInt((c.getColumnIndex(TaskData.TdDB.TASK_RECORDCOUNT)));
					int new_recordcount= old_recordcount+1;
					
					ContentValues cv = new ContentValues();    
					
					
					cv.put(TaskData.TdDB.TASK_PROGRESS, pro_sum); 
					//long updateProg=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 	
					//long updateAccomplished=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 	
				   
					cv.put(TaskData.TdDB.SUM_ACHIEVED, String.valueOf(cur_achieved)); 
					
					//long updateAchieved=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
					cv.put(TaskData.TdDB.SUM_ENJOYMENT, String.valueOf(cur_enjoyment)); 
					//long updateEnjoyment=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
					cv.put(TaskData.TdDB.SUM_EXPERIENCE, String.valueOf(cur_experience)); 	
					//long updateExperience=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
					cv.put(TaskData.TdDB.TASK_RECORDCOUNT,new_recordcount);
				    long updateRecordCount=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
				    // Log.d("update data in Task", "Prog "+updateProg+"achieved"+updateAchieved+"enjoyment"+updateEnjoyment+"experience"+updateExperience);
			
					 
				    TaskData.adapterUpdate();
				    
				    c.requery();
				    Log.d(Tags,"c count"+c.getCount());
				    c.moveToFirst();
				    tv_taskprogress.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
					 //tv_taskstatus.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)));
					 tv_recordtime.setText(curTime);
					 curRecordCount=Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_RECORDCOUNT)));
					
				    Double totalprog = 0.0;
				    Cursor c_r = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+ToDoDB.RECORD_TASKID+"="+TaskData.selTaskID,null);
				   
				    if (c_r!=null&&c_r.getCount()>0){
				      c_r.moveToFirst();
				      do{
				    	if (!TextUtils.isEmpty(c_r.getString(c_r.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)))) {  
				           totalprog=totalprog+Double.parseDouble(c_r.getString(c_r.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
				    	}else{
				    	   totalprog=totalprog+0.0;
				    	}
				      }while(c_r.moveToNext());
					 //tv_taskID.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_ID)));
					 //tv_taskname.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME)));
					 //tv_taskdeadline.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
					// tv_taskcreatedtime.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME)));
				    }
				   
					 MAX_VALUE=100-totalprog;
				  
				     //tv_maxValue.setText(""+MAX_VALUE);
					/*
					 Log.d("TdDB adapter update ", TaskData.cursor_alltasks.getPosition()+" seltaskId "+TaskData.selTaskID);
					  dismiss(); 
					  DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
					  dialogfrag_comment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);  
				   	showDialog(dialogfrag_comment);
						Log.d("dialogfrag", dialogfrag_comment.toString());
			        
					 */
					 Cursor c5 = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+
							  TaskData.TdDB.TASK_SN+"="+"'"+t_sn+"'"+	
					         " and "+ToDoDB.RECORD_DONE+"="+"'"+"false"+"'",null); 
					 Log.d(Tags+"|c5|", "count"+c5.getCount());
					 //adapter_prog.c1.requery();
					 //adapter_prog.notifyDataSetChanged();
					// c1.requery();
					 //ClearArrayList(TaskData.valuelist);
					 TaskData.valuelist=null;
					 TaskData.valuelist=new ArrayList<RecordBean>();
					 cursorToArrayList(c5,TaskData.valuelist);
					
					 adapter_prog = new mRecyclerAdapter(getActivity(),TaskData.valuelist,R.layout.lvitem_prog);
			         rv_prog.setAdapter(adapter_prog);
			         //TaskData.adaptRecordDetails.getCursor().requery();
			         //TaskData.adaptRecordDetails.notifyDataSetChanged();
					 //adapter_prog.notifyDataSetChanged();
					
					 TaskData.valuelistflag=0;	
					 newProg=0.0;
					 sb_prog.setProgress(0);
					 et_actualValue.setText("");
					 et_recordcomment.setText("");	

						if (cur_prog>=100 && TaskData.valuelist.size()<=0){
							
							btn_closetask.setEnabled(true);
							//btn_canceltask.setEnabled(false);
						    btn_recordsubmit.setEnabled(false);
							
						}
					 
                 } else{
				    	Toast.makeText(getActivity(), "无进度", Toast.LENGTH_SHORT).show();
				    }
                   
			   }else{		  
			 	 cv.put(TaskData.TdDB.RECORD_TASKID, tv_taskID.getText().toString());
				
				 //cv.put(TaskData.TdDB.RECORD_TASKID_NO, tv_taskID.getText().toString()+"_P"+String.valueOf(curRecordCount+1));
			 	 cv.put(TaskData.TdDB.RECORD_TASKID_NO, "P"+String.valueOf(curRecordCount+1));
			 	 cv.put(TaskData.TdDB.RECORD_CREATEDTIME, curTime);
				 cv.put(TaskData.TdDB.TASK_SN, t_sn);
				 cv.put(TaskData.TdDB.RECORD_TIME, curTime); 
				 cv.put(TaskData.TdDB.RECORD_DEADLINE,curTime);
				 cv.put(TaskData.TdDB.RECORD_CLOSEDTIME,curTime);
				  cv.put(TaskData.TdDB.RECORD_PROGRESS,actualValue); 			
				  cv.put(TaskData.TdDB.RECORD_COMMENTS, et_recordcomment.getText().toString()); 
				  cv.put(TaskData.TdDB.RECORD_WEIGHT, actualValue);
				  cv.put(TaskData.TdDB.TASK_USER,TaskData.user);
			      cv.put(TaskData.TdDB.TASK_OWNER,TaskData.user);
				  cv.put(TaskData.TdDB.RECORD_DONE,"true");
			       
				  //Log.d("submit data",cv.toString() );
				  newProg=actualValue;
				  long insertOK = TaskData.db_TdDB.insert(TaskData.TdDB.TABLE_NAME_TaskRecord,null, cv);
				  Log.d(Tags+"|insert record data|", "ok"+insertOK);
				  if (TaskData.valuelist.size()>0 && TaskData.valuelistflag==1){
				    for (int i=0;i<TaskData.valuelist.size();i++){
				    	
				     if (((RecordBean)TaskData.valuelist.get(i)).getRECORD_DONE()=="true"){
				    	String p=TaskData.valuelist.get(i).getRECORD_SN();
				    	String idno=TaskData.valuelist.get(i).getRECORD_TASKID_NO();
				    	Cursor c2=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.RECORD_SN+"="+"'"+p+"'", null);
				    	c2.moveToFirst();
				    	double curProg=Double.parseDouble(c2.getString(c2.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
				    	ContentValues cv2 = new ContentValues();		  
				    	 cv2.put(TaskData.TdDB.RECORD_TIME,curTime);
						 cv2.put(TaskData.TdDB.RECORD_CLOSEDTIME,curTime);
						 cv2.put(TaskData.TdDB.RECORD_DONE,"true");
				    	 cv2.put(TaskData.TdDB.RECORD_TASKID_NO,idno); 
				    	 //cv2.put(TaskData.TdDB.RECORD_SN, t_sn+TaskTool.addZero(Integer.parseInt(p),4));
				    	 cv2.put(TaskData.TdDB.RECORD_PROGRESS,curProg);
				    	 String whereas=TaskData.TdDB.RECORD_SN+"=?";
						 String[] whereArgs={p};
						long update2=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskRecord, cv2, whereas, whereArgs);
						newProg=newProg+curProg;
						 Log.d(Tags,"cv2 update"+i+"done"+update2); 
				    }
				   }   
				  }       
				    
				  String where = TaskData.TdDB.TASK_ID + " = ?";    
					String[] whereValue = { TaskData.selTaskID }; 
					Double prog_old= Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
					Double prog_new = newProg;
					Double cur_prog=prog_old+prog_new;
				    pro_sum = df.format(cur_prog);
					//cur_prog
					
				    long startedtimedata;
				    startedtimedata=TimeData.changeStrToTime_YY(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STARTEDTIME)));
				    long deadlinetimedata = TimeData.changeStrToTime_YY(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
				    Double task_lapsedpercent=(double) ((new Date().getTime()/60000-startedtimedata))/(deadlinetimedata-startedtimedata)*100;
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
					int new_enjoyment= (int) (Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PASSION)))
							*Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATION)))
							*cur_prog/100);
					int cur_enjoyment=old_enjoyment+new_enjoyment;
					
					int old_achieved = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.SUM_ACHIEVED)));
					int new_achieved= (int) (Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE)))
							*Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATION)))
							*cur_prog/100);
					int cur_achieved=old_achieved+new_achieved;
					
					int old_experience = Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.SUM_EXPERIENCE)));
					int new_experience= (int) (Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DIFFICULTY)))
							*Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DURATION)))
							*cur_prog/100);
					int cur_experience=old_experience+new_experience;
					
					int old_recordcount = c.getInt((c.getColumnIndex(TaskData.TdDB.TASK_RECORDCOUNT)));
					int new_recordcount= old_recordcount+1;
					
					ContentValues cv = new ContentValues();    
					
					
					
					cv.put(TaskData.TdDB.TASK_PROGRESS, pro_sum); 
					long updateProg=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 	
					long updateAccomplished=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 	
					
					cv.put(TaskData.TdDB.SUM_ACHIEVED, String.valueOf(cur_achieved)); 
					
					long updateAchieved=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
					cv.put(TaskData.TdDB.SUM_ENJOYMENT, String.valueOf(cur_enjoyment)); 
					long updateEnjoyment=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
					cv.put(TaskData.TdDB.SUM_EXPERIENCE, String.valueOf(cur_experience)); 	
					long updateExperience=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
					cv.put(TaskData.TdDB.TASK_RECORDCOUNT,new_recordcount);
				    long updateRecordCount=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
				  Log.d(Tags,"update data in Task"+updateProg+"achieved"+updateAchieved+"enjoyment"+updateEnjoyment+"experience"+updateExperience);
			    

				  // Log.d("move cursor", String.valueOf(c.getPosition()));
			     //Cursor c = db.query(TdDB.TABLE_NAME, null, null, null, null, null, null);
			    // Log.d("show data",c.toString() );
			   // SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.addtolist1, c, new String[] {"task_name","task_importance"},new int[]{R.id.task1_name_tv,R.id.task2_importance_tv}, 0);
			     //Log.d("show data",adapter.toString() );
			     //SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.addtolist1, c, null, null, 0)；SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.addtolist1, c, new String[] {"task_name","task_importance","task_urgency","task_passion","task_difficulty","task_duration"},new int[]{R.id.task1_name,R.id.task2_importance,R.id.task4_passion,R.id.task5_difficulty,R.id.task6_duration,R.id.task7_reminder}, 0); 
				  //SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.addtolist1, c, new String[] {TdDB.ID,TdDB.TASK_NAME,TdDB.TASK_IMPORTANCE},new int[]{R.id.task1_id_tv,R.id.task1_name_tv,R.id.task1_importance_tv}, 0);
				  //Log.d("adapter",adapter.toString() );
				//  adapter.getCursor().requery();
				//  adapter.notifyDataSetChanged(); 
			// Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
				 // m_listview.setAdapter(adapter);				
					
					//MyThread mythread = new MyThread();
				    //Thread thread_update = new Thread(mythread);
					//thread_update.start();
					//thread_update.run();
				    //tv_taskprogress.setText(pro_sum);
					
					 
					 
				    TaskData.adapterUpdate();
				    
				    c.requery();
				    Log.d(Tags,"c count"+c.getCount());
				    c.moveToFirst();
				    tv_taskprogress.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
					 //tv_taskstatus.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)));
					 tv_recordtime.setText(curTime);
					 curRecordCount=Integer.parseInt(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_RECORDCOUNT)));
					
				    Double totalprog = 0.0;
				    Cursor c_r = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.TASK_SN+"="+"'"+t_sn+"'",null);
				   
				    if (c_r!=null&&c_r.getCount()>0){
				      c_r.moveToFirst();
				      do{
				      totalprog=totalprog+Double.parseDouble(c_r.getString(c_r.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
				      }while(c_r.moveToNext());
					 //tv_taskID.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_ID)));
					 //tv_taskname.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME)));
					 //tv_taskdeadline.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
					// tv_taskcreatedtime.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME)));
				    }
				   
					 MAX_VALUE=100-totalprog;
				  
				     //tv_maxValue.setText(""+MAX_VALUE);
					/*
					 Log.d("TdDB adapter update ", TaskData.cursor_alltasks.getPosition()+" seltaskId "+TaskData.selTaskID);
					  dismiss(); 
					  DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
					  dialogfrag_comment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);  
				   	showDialog(dialogfrag_comment);
						Log.d("dialogfrag", dialogfrag_comment.toString());
			        
					 */
					 Cursor c5 = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+
							 TaskData.TdDB.TASK_SN+"="+"'"+t_sn+"'"+
					         " and "+ToDoDB.RECORD_DONE+"="+"'"+"false"+"'",null); 
					 Log.d(Tags,"c5 update"+c5.getCount());
					 //adapter_prog.c1.requery();
					 //adapter_prog.notifyDataSetChanged();
					// c1.requery();
					 TaskData.valuelist=null;
					 TaskData.valuelist=new ArrayList<RecordBean>();
					 cursorToArrayList(c5,TaskData.valuelist);
					 adapter_prog = new mRecyclerAdapter(getActivity(),TaskData.valuelist,R.layout.lvitem_prog);
			         rv_prog.setAdapter(adapter_prog);
			         TaskData.adaptRecordDetails.getCursor().requery();
			         TaskData.adaptRecordDetails.notifyDataSetChanged();
					 //adapter_prog.notifyDataSetChanged();
					
					 TaskData.valuelistflag=0;	
					 newProg=0.0;
					 sb_prog.setProgress(0);
					 et_actualValue.setText("");
					 et_recordcomment.setText("");
					 
					 if (cur_prog>=100 && TaskData.valuelist.size()<=0){
							
							btn_closetask.setEnabled(true);
							//btn_k.setEnabled(false);
						    btn_recordsubmit.setEnabled(false);
							
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
		  
		  
		    btn_quit.setOnClickListener(new OnClickListener(){

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
					 //DialogFragment_CancelTask dialogfrag_canceltask=new DialogFragment_CancelTask();
					  //dialogfrag_canceltask.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
					   //dialogfrag_canceltask.setStyle(DialogFragment.STYLE_NO_TITLE, 0);  
					   //showDialog(dialogfrag_canceltask);
					 //	Log.d("dialogfrag", dialogfrag_closetask.toString());
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
	
	
	public void cursorToArrayList(Cursor c1,ArrayList arraylist){
		
		 if (c1.getCount()>0){
			    Log.d(Tags+"|cursorToArrayList|", "count"+c1.getCount());
			    TaskData.valuelist=new ArrayList<RecordBean>();
			    c1.moveToFirst();
	         do{
	            RecordBean recbean = new RecordBean();
	            recbean.setRECORD_SN(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_SN)));
	            recbean.setRECORD_TASKID_NO(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));
	            recbean.setRECORD_COMMENTS(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS)));
	           
	            recbean.setRECORD_DONE("false");
	            recbean.setRECORD_WEIGHT(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
	            TaskData.valuelist.add(recbean);
	          }while(c1.moveToNext());
	          
	           for (int i=0;i<TaskData.valuelist.size();i++){
	        //Log.d("taskidno",TaskData.valuelist.get(i).getRECORD_TASKID_NO());
	    	    
	           }
			 }
			 adapter_prog = new mRecyclerAdapter(getActivity(),TaskData.valuelist,R.layout.lvitem_prog);
	         rv_prog.setAdapter(adapter_prog);
		
	}

	protected void showDialog(DialogFragment dialogFragment) {  
         
	        // Create and show the dialog.   
	    if(dialogFragment == null)  
	          //  dialogFragment = new Fragment_Search();  
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
