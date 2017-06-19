package com.easygoal.achieve;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.util.TextUtils;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DialogFragment_CloseTask extends DialogFragment {
	String curTime;
	Cursor c;
	String taskclosechoice=null;
	//String taskhornorchoice=null;
	String task_resultcomment;
	String tasklesson;
	String taskhistory;
	String Tags="DialogFragment_CloseTask";
	 
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v=inflater.inflate(R.layout.dialogfg_closetask, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		 
		  //TextView tv1=(TextView)v.findViewById(R.id.record_item1_recordID_tv);
		  final TextView tv_taskID=(TextView)v.findViewById(R.id.record_item1_taskID_tv);
		  final TextView tv_taskname=(TextView)v.findViewById(R.id.task_item1_name_tv);
		  final TextView tv_recordtime=(TextView)v.findViewById(R.id.tv_recordtime);
		  TextView tv_taskcreatedtime=(TextView)v.findViewById(R.id.task_item2_createdtime_tv);
		  TextView tv_taskdeadline=(TextView)v.findViewById(R.id.task_item15_deadline_tv);
		  TextView tv_taskprogress=(TextView)v.findViewById(R.id.task_item16_progress_tv);
		  TextView tv_taskstatus=(TextView)v.findViewById(R.id.task_item18_status_tv);
		  
		  
		  final EditText et_recordprogress=(EditText)v.findViewById(R.id.record_item4_progress_et);
		  final EditText et_recordcomment=(EditText)v.findViewById(R.id.record_item5_comment_et);
		 
		  RadioGroup  rg_taskhornor=(RadioGroup )v.findViewById(R.id.radioGroup_taskhonor);
		  final RadioButton rb_taskhornor_high=(RadioButton)v.findViewById(R.id.rb_taskhornor_high);
		  final RadioButton rb_taskhornor_meet=(RadioButton)v.findViewById(R.id.rb_taskhornor_meet);
		  final RadioButton rb_taskhornor_low=(RadioButton)v.findViewById(R.id.rb_taskhornor_low);
		 
		  final EditText et_tasklesson=(EditText)v.findViewById(R.id.task_lesson_et);
		  //final ListView m_listview=(ListView)v.findViewById(R.id.task_show_lv);
		  Button btn_confirm= (Button) v.findViewById(R.id.btn_confirm);
		  Button btn_abort= (Button) v.findViewById(R.id.btn_abort); 
		  Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);  
		// Button btn_recordclose = (Button) v.findViewById(R.id.record_close_bt);  
		// Button btn_recordconfirm=(Button)v.findViewById(R.id.record_confirm_bt);
		// Button btn_closetask=(Button)v.findViewById(R.id.record_closetask_bt);
		// Button btn_recordview = (Button) v.findViewById(R.id.record_view_bt);  
	     if (TaskData.selTaskID!=null){
		 tv_taskID.setText(TaskData.selTaskID);
		 String a = TaskData.TdDB.TABLE_NAME_TaskMain;
	     String b = TaskData.TdDB.TABLE_NAME_TaskRecord;

	     c = TaskData.db_TdDB.rawQuery("select * from "+a+" where "+TaskData.TdDB.TASK_ID+"="+TaskData.selTaskID,null);
		 //
		 c.moveToFirst();
		// Log.d("selTab count", "total "+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME))+" "+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME+" "+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS+""+c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)))))));
		 tv_taskname.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_NAME)));
		 tv_taskdeadline.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
		 // tv_taskcreatedtime.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME)));
		 tv_taskprogress.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
		 tv_taskstatus.setText(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_STATUS)));
		 tasklesson = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_LESSON));
		 taskhistory = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_HISTORY));
	     }else {
	      Toast.makeText(getActivity(), "未选定任务", Toast.LENGTH_SHORT).show();;	 
	     }
		  tv_recordtime.setText(curTime);
		 btn_closewin.setOnClickListener(new OnClickListener() {  
	  
	           
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
	     	
			    RadioGroup.OnCheckedChangeListener mChangeRadio_taskhornor = new 
				           RadioGroup.OnCheckedChangeListener()
				  { 
				    @Override 
				    public void onCheckedChanged(RadioGroup group, int checkedId)
				    { 
				      // TODO Auto-generated method stub 
				      if(checkedId== rb_taskhornor_high.getId())
				      { 
				        /*把mRadio1的内容传到mTextView1*/
				    	  taskclosechoice="1.5";
				    	  task_resultcomment=rb_taskhornor_high.getText().toString();
				    	 // Toast.makeText(getActivity(),"close"+taskhornorchoice, Toast.LENGTH_LONG).show();
				      } 
				      else if(checkedId==rb_taskhornor_meet.getId()) 
				      { 
				        /*把mRadio2的内容传到mTextView1*/
				    	  taskclosechoice="1"; 
				    	  task_resultcomment=rb_taskhornor_meet.getText().toString();
				    	  //Toast.makeText(getActivity(),"close"+taskhornorchoice, Toast.LENGTH_LONG).show();
				      }
				      else if(checkedId==rb_taskhornor_low.getId()) 
				      { 
				        /*把mRadio2的内容传到mTextView1*/
				    	  taskclosechoice="0.5"; 
				    	 task_resultcomment=rb_taskhornor_low.getText().toString();
				    	 // Toast.makeText(getActivity(),"close"+taskhornorchoice, Toast.LENGTH_LONG).show();
				      }   
				    } 
				  }; 
				    rg_taskhornor.setOnCheckedChangeListener(mChangeRadio_taskhornor);
		    
		    
		  btn_confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//c.moveToLast();
				//Log.d("insert data", "start"+String.valueOf(c.getPosition()));	
			   // String a = String.valueOf(c.getCount()+1);
			   // Log.d("db query","row"+String.valueOf(c.getCount()));
				//cv.put(TdDB.ID, a);
				 	
				
					    final ContentValues cv = new ContentValues();
					    String where = TaskData.TdDB.TASK_ID + " = ?";    
						String[] whereValue = { TaskData.selTaskID }; 
			        	cv.put(TaskData.TdDB.TASK_STATUS,"finished");
			        	
				       cv.put(TaskData.TdDB.TASK_RESULTSATISFICATION,String.valueOf(taskclosechoice));;
				       cv.put(TaskData.TdDB.TASK_CLOSEDTIME,curTime);
				       cv.put(TaskData.TdDB.TASK_SEQUENCENO,"");
				       String sum_accomplished= c.getString(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE));
					   cv.put(TaskData.TdDB.SUM_ACCOMPLISHED,sum_accomplished);
				       double sum_contribution= Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE)))
							   *Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.TASK_URGENCY)));
				       cv.put(TaskData.TdDB.SUM_CONTRIBUTION,""+sum_contribution);
				       //Toast.makeText(getActivity(), "贡献"+sum_contribution, Toast.LENGTH_SHORT).show();;
				final Calendar cal=Calendar.getInstance();
			    	   int year = cal.get(Calendar.YEAR);
		               int month = cal.get(Calendar.MONTH);
		               int day=cal.get(Calendar.DAY_OF_MONTH);
		               
		             SimpleDateFormat formatter2 = new SimpleDateFormat ("yyMMdd");
		     		 String curDate2 = formatter2.format(new Date());
		             long closedate = Integer.parseInt(curDate2);
		             long closedtimedata = new Date().getTime()/60000;  
				       cv.put(TaskData.TdDB.TASK_CLOSEDATE,closedate);
				       cv.put(TaskData.TdDB.SUM_ACCOMPLISHED, c.getString(c.getColumnIndex(TaskData.TdDB.TASK_IMPORTANCE))); 
				       cv.put(TaskData.TdDB.TASK_CLOSEDTIMEDATA,closedtimedata);
				       cv.put(TaskData.TdDB.TASK_RESULTCOMMENT,task_resultcomment);
				       String taskdeadline=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
				       if (TimeData.CompareTime_YY(taskdeadline,curTime)<=0){
				    	   cv.put(TaskData.TdDB.TASK_DELAYED,"0");
				    	   cv.put(TaskData.TdDB.TASK_DELAYEDDAYS,String.valueOf(TimeData.CompareTime_YY(taskdeadline,curTime)));
				    	   cv.put(TaskData.TdDB.TASK_CLOSEDCOMMENT,String.valueOf("提前"+TimeData.CompareTime_YY(curTime,taskdeadline))+"天完成");
				       }else{
				    	   cv.put(TaskData.TdDB.TASK_DELAYED,"1");
				    	   cv.put(TaskData.TdDB.TASK_DELAYEDDAYS,String.valueOf(TimeData.CompareTime_YY(taskdeadline,curTime)));
				    	   cv.put(TaskData.TdDB.TASK_CLOSEDCOMMENT,String.valueOf("延期"+TimeData.CompareTime_YY(taskdeadline,curTime))+"天完成");
				       }
				       
				      // Log.d("show closedata",taskclosechoice);
				 //cv.put(TaskData.TdDB.RECORD_NO, String.valueOf(c2.));
				 //cv.put(TaskData.TdDB.RECORD_TIME, curTime);
				  //cv.put(TaskData.TdDB.RECORD_PROGRESS, et_recordprogress.getText().toString());  
				  if (tasklesson!=null&&!TextUtils.isEmpty(tasklesson)){     
				      tasklesson=tasklesson.trim()+"\n"+et_tasklesson.getText().toString()+TaskData.tag_tasklesson;
				  }else{
					  tasklesson="执行力取得积极成果"+TaskData.tag_tasklesson;  
				  }
				  if (taskhistory!=null&&!TextUtils.isEmpty(taskhistory)){     
					  taskhistory=taskhistory.trim()+"\n"+"任务完成"+TaskData.tag_taskhistory;
				  }else{
					  taskhistory="任务完成"+TaskData.tag_taskhistory;  
				  }
				  cv.put(TaskData.TdDB.TASK_LESSON, tasklesson); 
				  cv.put(TaskData.TdDB.TASK_HISTORY, taskhistory);
				  cv.put(TaskData.TdDB.TASK_MODIFIED,TaskTool.getCurTime());
				  //Log.d("submit data",cv.toString() );
				  long a=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue); 
				 
				  if (a==1){
					    getSequenceNo();
					    TaskData.adapterUpdate();
					    Toast.makeText(getActivity(), "任务结束成功", Toast.LENGTH_SHORT).show();;	 
					    dismiss(); 
				  }else{
						 Toast.makeText(getActivity(), "任务结束失败"+TaskData.TdDB.TASK_ID, Toast.LENGTH_SHORT).show();;	   
				  }
				  
				  TaskData.update_tvvalue(TaskData.tv_status,c,TaskData.TdDB.TASK_STATUS);
				  //Log.d("closedtimedata",""+closedtimedata);
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
				  
					// Log.d("TdDB adapter update ", TaskData.cursor_alltasks.getPosition()+" seltaskId "+TaskData.selTaskID);
				   
					
					 // DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
					  //dialogfrag_comment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);  
					  //showDialog(dialogfrag_comment);
				   	  //Toast.makeText(getActivity(),"close ok", Toast.LENGTH_LONG).show();
					 //DialogFragment_CloseTask dialogfrag_closetask=new DialogFragment_CloseTask();
				  	//showDialog(dialogfrag_closetask);
						//Log.d("dialogfrag", dialogfrag_closetask.toString());
			
			}  
		    });
		  
		    
		  
		  
		    btn_abort.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stu	
					
				}
		    });
		  
		  
		  
			return v;
	}	

	public void getSequenceNo(){
		  Cursor c= TaskData.db_TdDB.rawQuery("select  *  from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"='open'"+" order by "+TaskData.TdDB.TASK_SEQUENCE,null);
		  if (c.getCount()!=0){
		  int[] id=new int[ c.getCount()];
		  int i = 0;
		  c.moveToFirst();
		  ContentValues cv = new ContentValues();
			do {
				id[i]=c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_ID));
				cv.put(TaskData.TdDB.TASK_SEQUENCENO, i+1);
				String where = TaskData.TdDB.TASK_ID + " = ?";    
				String[] whereValue = { String.valueOf(id[i])};
				int a = TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereValue);
				Log.d("sn no", "cursor"+c.getPosition()+"i:"+i+" action"+a);
				i++;
			} while (c.moveToNext()); 
		  }else{
			 
		  }
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
