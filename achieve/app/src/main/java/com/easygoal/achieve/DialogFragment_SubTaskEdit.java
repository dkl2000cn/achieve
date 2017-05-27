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
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast; 

public class DialogFragment_SubTaskEdit extends DialogFragment {


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
	 String taskreminder="no";
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
	 String t_sn;
	 DecimalFormat df_duration;
	 double total_weight;
	 String Tags="DialogFragment_SubTaskEdit";
	 
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
		 df_duration = new DecimalFormat("0.0");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v=inflater.inflate(R.layout.dialogfg_subtaskedit, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
         TaskData.tv_totalweight=(TextView)v.findViewById(R.id.tv_totalweight);
       
		  //final ListView m_listview=(ListView)v.findViewById(R.id.task_show_lv);  		  
		  final TextView tv_addSubItem=(TextView)v.findViewById(R.id.tv_addSubItem);
		  final RecyclerView rv_progedit=(RecyclerView)v.findViewById(R.id.rv_progedit);		  
		 Button btn_confirm=(Button)v.findViewById(R.id.task_confirm_bt);
		 Button btn_cancel=(Button)v.findViewById(R.id.task_cancel_bt);
		 Button btn_close = (Button) v.findViewById(R.id.task_close_bt);  
		 Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);  
		   
		    btn_closewin.setOnClickListener(new OnClickListener() {  
			  
	           
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
		  
			  if (TaskData.selTaskID!=null){
					
				     String a = TaskData.TdDB.TABLE_NAME_TaskMain;
				     String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
				  
				     TaskData.subItemlist.clear();
				     TaskData.subdellist.clear();

				  c= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
								  TaskData.TdDB.TASK_ID+"=? ",
						  new String[]{TaskData.selTaskID});
				  if (c!=null&&c.getCount()>0){
					  c.moveToFirst();
					  timeunit = c.getInt(c.getColumnIndex(TaskData.TdDB.TASK_DURATIONUNIT));
					  t_sn=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
					  TaskData.selTaskSN=t_sn;
					  Log.d("df_subtaskedit", "timeunit"+timeunit+" t_sn"+t_sn);
				  }
				  c1 = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+
						  TaskData.TdDB.TASK_SN+"="+"'"+t_sn+"'"+
						  " and "+TaskData.TdDB.RECORD_DONE+"="+"'"+"false"+"'",null);
				  Log.d(Tags,"undone count"+c1.getCount());
				 
		       if (c1.getCount()>0){
		           c1.moveToFirst();
		           total_weight=0.0;
		     	
		          do{
		        	 //t_sn=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN)); 
		        	 RecordBean recBean = new RecordBean();
		        	 recBean.setRECORD_COMMENTS(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS)));
		        	 recBean.setRECORD_WEIGHT(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
		        	 recBean.setRECORD_DEADLINE(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_DEADLINE)));
		        	 recBean.setRECORD_TASKID_NO(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));
		        	 recBean.setRECORD_TASKID(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_TASKID)));
		        	 recBean.setTASK_USER(c1.getString(c1.getColumnIndex(TaskData.TdDB.TASK_USER)));
		        	 recBean.setTASK_SN(c1.getString(c1.getColumnIndex(TaskData.TdDB.TASK_SN)));
		        	 recBean.setRECORD_SN(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_SN)));
		        	 //recBean.setTASK_SN(t_sn+TaskTool.addZero(, 4));
		        	 //total_weight=total_weight+Double.parseDouble(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
		        	 TaskData.subItemlist.add(recBean);
		         	
		        	 
		          }while(c1.moveToNext());
		     }  
		          rec_count = c1.getCount();
		          //TaskData.tv_totalweight.setText("当前子任务项:"+rec_count+" 合计权重:"+total_weight);
				  //Toast.makeText(getActivity(), "当前子目标数量"+rec_count+"size"+ TaskData.subItemlist.size(), Toast.LENGTH_SHORT).show();
				  if (total_weight>100){
		                //tv_totalweight.setTextColor(Color.RED);
						//Toast.makeText(getActivity(), "当前总权重大于100", Toast.LENGTH_SHORT).show(); 
			       }  

				  LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		  		//设置布局管理器  
		        
				 rv_progedit.setLayoutManager(layoutManager);
		  		//设置为垂直布局，这也是默认的  
		  		 layoutManager.setOrientation(OrientationHelper. VERTICAL);
		  		//设置Adapter   		          
		         TaskData.adapter_progedit = new mRecyAdapter_taskedit(getActivity(),R.layout.lvitem_progedit,TaskData.subItemlist);
		         rv_progedit.setAdapter(TaskData.adapter_progedit);
		 

			         c_r1 = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+
			                  TaskData.TdDB.TASK_SN+"=? ",
					          new String[]{t_sn});
			         //c_r1.requery();
			         double sum_weight = 0;
					    if (c_r1!=null&&c_r1.getCount()>0){
					    	while (c_r1.moveToNext()){
					    	 sum_weight=sum_weight+Double.parseDouble(c_r1.getString(c_r1.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
					    	}
					    }
					 
					 TaskData.tv_totalweight.setText("合计权重(%):"+sum_weight); 
					 Log.d(Tags,"df_subtaskedit"+"合计权重(%):"+sum_weight);      
	               
			  }else {    	
			      Toast.makeText(getActivity(), "未选定任务", Toast.LENGTH_SHORT).show();	 
			      dismiss();
			     }        
	        
	        final ContentValues cv = new ContentValues();
		
		    final EditText et_addsub_content=(EditText)v.findViewById(R.id.et_addsub_content);
		    final EditText et_addsub_weight=(EditText)v.findViewById(R.id.et_addsub_weight);
		    final EditText et_addsub_deadline=(EditText)v.findViewById(R.id.et_addsub_deadline);
		    //final Button btn_deleteItem=(Button)v.findViewById(R.id.btn_deleteItem);
		    
		    et_addsub_deadline.setOnFocusChangeListener(new OnFocusChangeListener(){

				@Override
				public void onFocusChange(View arg0, boolean arg1) {
					// TODO Auto-generated method stub
					DialogFragment_TimeInput dg_time=new DialogFragment_TimeInput(et_addsub_deadline,0);
	    	    	TaskTool.showDialog(dg_time);
				}
		    	
		    	
		    });
		    
		   
		    tv_addSubItem.setOnClickListener(new OnClickListener(){
		    int recnum;
		    int firstnum;
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//c_r1.requery();
					String newitem_weight = et_addsub_weight.getText().toString().trim();
					String newitem_content=et_addsub_content.getText().toString().trim();	        
					
				if (TaskTool.isNumeric(newitem_weight)&&!TextUtils.isEmpty(newitem_content)){		       
				         
			     if (c_r1!=null&&c_r1.getCount()>0){
					   c_r1.moveToLast();
				       String lastno = c_r1.getString(c_r1.getColumnIndex(TaskData.TdDB.RECORD_SN));
				       int lastnum=Integer.parseInt(lastno.substring(lastno.length()-4, lastno.length()));
				       Log.d(Tags+"|lastnum|", ""+lastnum);
				       if (lastnum>recnum){
				        recnum = lastnum;
				        }
				       TaskData.totalweight=GetTotalWeight();
				       double sum_addweight = TaskData.totalweight+Double.parseDouble(et_addsub_weight.getText().toString());
				      //Toast.makeText(getActivity(),"总权重:"+sum_addweight, Toast.LENGTH_SHORT).show(); 
				       Log.d(Tags,"totalweight: "+TaskData.totalweight+" sum: "+sum_addweight);  
				   if (sum_addweight<=100){	
					    
			           RecordBean newSubItem = new RecordBean();
			          //newSubItem.setRECORD_TASKID_NO("C"+(rec_count+1));
			          newSubItem.setRECORD_TASKID_NO("P"+(c_r1.getCount()+1));
					  newSubItem.setRECORD_TASKID(TaskData.selTaskID);
			          newSubItem.setRECORD_SN(t_sn+TaskTool.addZero(recnum+1, 4));
			          newSubItem.setTASK_SN(t_sn);
			          newSubItem.setTASK_USER(TaskData.user);
			          newSubItem.setTASK_OWNER(TaskData.user);
			          newSubItem.setRECORD_COMMENTS(et_addsub_content.getText().toString());
			          newSubItem.setRECORD_DEADLINE(et_addsub_deadline.getText().toString());
			          newSubItem.setRECORD_WEIGHT(et_addsub_weight.getText().toString());
			          newSubItem.setRECORD_DONE("false");
			          newSubItem.setRECORD_TYPE("checklist");
			          newSubItem.setRECORD_VALIDITY("true");
					  newSubItem.setRECORD_PROGRESS("0");
					
			        if (TaskData.subItemlist!=null){
			         TaskData.subItemlist.add(newSubItem);
			        }else{
			          TaskData.subItemlist=new ArrayList<RecordBean>();
			          TaskData.subItemlist.add(newSubItem);
			        }
			        //TaskData.subItems_idno.clear();
				    //TaskData.subItems_deadline.clear();
				    //TaskData.subItems_comments.clear();
				    //TaskData.subItems_weight.clear();
				    //TaskData.subItems_del.clear(); 
			        //total_weight=total_weight+Double.parseDouble(et_addsub_weight.getText().toString());
			        //TaskData.tv_totalweight.setText("当前子任务项:"+rec_count+" 合计权重:"+total_weight);
			        TaskData.totalweight=TaskData.totalweight+Double.parseDouble(et_addsub_weight.getText().toString());
			        TaskData.adapter_progedit.notifyDataSetChanged();
			        TaskData.tv_totalweight.setText("合计权重(%):"+TaskData.totalweight);
			        et_addsub_content.setText("");
				    et_addsub_weight.setText("");
				    et_addsub_deadline.setText("");
			        //Toast.makeText(getActivity(), "新添子项"+t_sn+TaskTool.addZero(recnum+1, 4), Toast.LENGTH_SHORT).show(); 
			        //Toast.makeText(getActivity(), "子项添加成功!", Toast.LENGTH_SHORT).show(); 
			        recnum++;
			        
				  }else{
					Toast.makeText(getActivity(), "总权重超过100%", Toast.LENGTH_SHORT).show(); 
				  }  
				   
			   	}else{
			   	   TaskData.totalweight=GetTotalWeight();
			   	   double sum_addweight = TaskData.totalweight+Double.parseDouble(et_addsub_weight.getText().toString());
				   //Toast.makeText(getActivity(),"总权重:"+sum_addweight, Toast.LENGTH_SHORT).show(); 
				   Log.d(Tags, "totalweight: "+TaskData.totalweight+" sum: "+sum_addweight);  
				   if (sum_addweight<=100){	
			        
					   
					   if (TaskData.subItemlist==null){
			          TaskData.subItemlist=new ArrayList<RecordBean>();
			           }
			       
			   	    RecordBean newSubItem = new RecordBean();
			        //newSubItem.setRECORD_TASKID_NO("C"+(rec_count+1));
			   	    newSubItem.setRECORD_TASKID_NO("P"+(TaskData.subItemlist.size()+1));
					newSubItem.setRECORD_TASKID(TaskData.selTaskID);
			        newSubItem.setRECORD_SN(t_sn+TaskTool.addZero((TaskData.subItemlist.size()+1), 4));
			        newSubItem.setTASK_SN(t_sn);
			        newSubItem.setTASK_USER(TaskData.user);
			        newSubItem.setTASK_OWNER(TaskData.user);
			        newSubItem.setRECORD_COMMENTS(et_addsub_content.getText().toString());
			        newSubItem.setRECORD_DEADLINE(et_addsub_deadline.getText().toString());
			        newSubItem.setRECORD_WEIGHT(et_addsub_weight.getText().toString());
			        newSubItem.setRECORD_DONE("false");
			        newSubItem.setRECORD_TYPE("checklist");
			        newSubItem.setRECORD_VALIDITY("true");
					newSubItem.setRECORD_PROGRESS("0");
					TaskData.subItemlist.add(newSubItem);
					//Toast.makeText(getActivity(), "新添子项"+t_sn+TaskTool.addZero((TaskData.subItemlist.size()+1), 4), Toast.LENGTH_SHORT).show(); 
					//Toast.makeText(getActivity(), "子项添加成功!", Toast.LENGTH_SHORT).show(); 

					TaskData.adapter_progedit.notifyDataSetChanged();
					TaskData.totalweight=TaskData.totalweight+Double.parseDouble(et_addsub_weight.getText().toString());
				    TaskData.tv_totalweight.setText("合计权重(%):"+TaskData.totalweight);
				    et_addsub_content.setText("");
				    et_addsub_weight.setText("");
				    et_addsub_deadline.setText("");
				   }else{
					  Toast.makeText(getActivity(), "总权重超过100%", Toast.LENGTH_SHORT).show();  
				   }
			   	  }
			      
		    	}else{
		    		 Toast.makeText(getActivity(), "请输入名称及数字权重(%)", Toast.LENGTH_SHORT).show(); 
		    	}
			  }	
		    });
		    
		  
		  btn_confirm.setOnClickListener(new OnClickListener(){
           
			  
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				   
					  String subtask_historychange="";
				      
				      if (TaskData.subdellist!=null&&TaskData.subdellist.size()>0){
							int delcount=TaskData.subdellist.size();
							for (int k=0;k<delcount;k++){
							   
								String del_sn=TaskData.subdellist.get(k);
							    String whereas3=TaskData.TdDB.RECORD_SN+"=?";
							    String[] whereValues3={del_sn};
								//TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskRecord,cv3, whereas3, whereValues3);
								TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskRecord,whereas3, whereValues3);	
								Log.d(Tags+"|list upate|", "del"+del_sn);
								
								subtask_historychange=subtask_historychange+"删除子任务"+del_sn.substring(del_sn.length()-3, del_sn.length())+TaskData.tag_taskhistory;
								
								/*
								cv_tu.put(TaskData.TdDB.TASK_HISTORY, "删除子任务"+del_sn+TaskData.tag_taskhistory);
							    task_tu=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv_tu,whereas_tu,whereValues_tu);
							    Log.d(Tags+"|list upate|"+k, "任务更新"+task_tu+"tasksn"+t_sn);
							    */
							}
					   } 
					
						
						int t=TaskData.subItemlist.size();
						
						double sum_weight = 0;
						//int oldcount=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.RECORD_SN+"="+"'"+r_sn+"'",null).getCount();
						for (int j=0;j<t;j++){
							
			                ContentValues cv2=new ContentValues();
						    //
						    //String sn=TaskData.subItemlist.get(j).getTASKSN();
						    String sn=TaskData.subItemlist.get(j).getRECORD_SN();
						    cv2.put(TaskData.TdDB.RECORD_TASKID, TaskData.selTaskID);
						    //cv2.put(TaskData.TdDB.RECORD_TASKID_NO, "P"+(j+1));
						    cv2.put(TaskData.TdDB.RECORD_COMMENTS, TaskData.subItemlist.get(j).getRECORD_COMMENTS());
						    cv2.put(TaskData.TdDB.RECORD_DEADLINE, TaskData.subItemlist.get(j).getRECORD_DEADLINE());
						    cv2.put(TaskData.TdDB.RECORD_WEIGHT, TaskData.subItemlist.get(j).getRECORD_WEIGHT());
						    //cv2.put(TaskData.TdDB.RECORD_PROGRESS,0);
							cv2.put(TaskData.TdDB.RECORD_SN, TaskData.subItemlist.get(j).getRECORD_SN());
							//cv2.put(TaskData.TdDB.RECORD_SN, (t_sn+TaskTool.addZero(j+1, 4)));
						    cv2.put(TaskData.TdDB.TASK_SN,TaskData.subItemlist.get(j).getTASK_SN());
						    cv2.put(TaskData.TdDB.RECORD_PROGRESS,0);
						    cv2.put(TaskData.TdDB.RECORD_TASKID_NO,"P"+(j+1));
						    //cv2.put(TaskData.TdDB.RECORD_PROGRESS,0);
						    cv2.put(TaskData.TdDB.TASK_USER,TaskData.user);
						    cv2.put(TaskData.TdDB.RECORD_DONE,"false");
						    cv2.put(TaskData.TdDB.RECORD_CREATEDTIME,curTime);
						    //Log.d("subtask edit"+j,sn);
						    String rsn = TaskData.subItemlist.get(j).getRECORD_SN();
						   
						    String whereas=TaskData.TdDB.RECORD_SN+"=?";
						    String[] whereValues={sn};
						   
						    long rec_update;
						    long rec_insert;
						    int seekCount = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.RECORD_SN+"="+"'"+sn+"'",null).getCount();
						   
						    if (seekCount>0){
						    	
						       rec_update=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskRecord, cv2, whereas, whereValues);
						       Log.d(Tags+"|list upate|"+j, "updated："+rec_update+"tasksn"+TaskData.subItemlist.get(j).getTASK_SN()+" record sn "+sn);
						      
						       subtask_historychange=subtask_historychange+"修改子任务"+rsn.substring(rsn.length()-3, rsn.length())+TaskData.tag_taskhistory;
						       /*
						       cv_tu.put(TaskData.TdDB.TASK_HISTORY, "插入子任务"+rsn+TaskData.tag_taskhistory);
						       task_tu=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv_tu,whereas_tu,whereValues_tu);
						       Log.d(Tags+"|list upate|"+j, "任务更新"+task_tu+"tasksn"+t_sn);
						       */
						    }else{
						    	
						       rec_insert=TaskData.db_TdDB.insert(TaskData.TdDB.TABLE_NAME_TaskRecord, null,cv2);	
						       Log.d(Tags+"|list upate|"+j, "insert"+rec_insert+"tasksn"+TaskData.subItemlist.get(j).getTASK_SN()+" recordsn "+sn);
						       
						       subtask_historychange=subtask_historychange+"插入子任务"+rsn.substring(rsn.length()-3, rsn.length())+TaskData.tag_taskhistory;
						       /*
						       cv_tu.put(TaskData.TdDB.TASK_HISTORY, "插入子任务"+rsn+TaskData.tag_taskhistory);
						       task_tu=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv_tu,whereas_tu,whereValues_tu);
						       Log.d(Tags+"|list upate|"+j, "任务更新"+task_tu+"tasksn"+t_sn);
						       */
						    }
						    //TaskData.cursor_taskrecord.requery();
						}	
						    Log.d(Tags+"|history|", subtask_historychange);
						    int atu=TaskTool.AppendFieldText(TaskData.TdDB.TABLE_NAME_TaskMain, TaskData.TdDB.TASK_SN,t_sn,TaskData.TdDB.TASK_HISTORY,subtask_historychange);
						    Log.d(Tags+"|history|", subtask_historychange+atu);
						    TaskData.getRecordSequenceNo(TaskData.selTaskSN);
						    TaskData.adaptRecordDetails.getCursor().requery();
					        TaskData.adaptRecordDetails.notifyDataSetChanged();
						   
						    //TaskData.mcAdapter_taskrecord.notifyDataSetChanged();
							TaskData.adapterUpdate();
							Toast.makeText(getActivity(), "子目标修改成功", Toast.LENGTH_SHORT).show(); 
							dismiss();//Log.d("TdDB adapter update", "all done");	
				 
		        }
				

		  });	  
		   		
		return v;
	}	
		    
 double GetTotalWeight(){	
	double sum_openweight = 0;
    double sum_closeweight=0;
	for (int j=0;j<TaskData.subItemlist.size();j++){
		if (!TextUtils.isEmpty(TaskData.subItemlist.get(j).getRECORD_WEIGHT().trim())){
	         sum_openweight=sum_openweight+Double.parseDouble(TaskData.subItemlist.get(j).getRECORD_WEIGHT());
		}else{
			 sum_openweight=sum_openweight+0;
		}
    }
      Log.d(Tags,"sum_openweight"+sum_openweight); 
    if (TaskData.TdDB.TASK_SN!=null){
	    Cursor c_d=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
	                  TaskData.TdDB.TASK_SN+"=?  ",
			          new String[]{TaskData.selTaskSN});
        c_d.moveToFirst();
      if (c_d!=null&&c_d.getCount()>0){
    	sum_closeweight=Double.parseDouble(c_d.getString(c_d.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
    	/*
    	while (c_d.moveToNext()){
    	 sum_closeweight=sum_closeweight+Double.parseDouble(c_d.getString(c_d.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
    	}*/
      }
	//TaskData.totalweight=sum_openweight+sum_closeweight;
      Log.d(Tags,"sum_closeweight"+sum_closeweight+" "+c_d.getCount()); 
    }else{
    	Toast.makeText(getActivity(), "tasksn"+TaskData.TdDB.TASK_SN, Toast.LENGTH_SHORT).show();
    }
	return (sum_openweight+sum_closeweight);
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
