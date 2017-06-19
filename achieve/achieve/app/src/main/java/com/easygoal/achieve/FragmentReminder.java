package com.easygoal.achieve;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.litepal.crud.DataSupport;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import net.sf.json.JSONArray;

public class FragmentReminder extends Fragment {
	 
	Handler handler=null;
	int selectedID=0;
	ListView lv_reminder;
	JSONArray jolist;
	long selId;
	int selPos;
	String Tags="FragmentReminder";
	//final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.subfg_home_toptab2_reminder, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 		
		 
	     lv_reminder=(ListView)v.findViewById(R.id.lv_reminder);
	     Button btn_reminderadd=(Button)v.findViewById(R.id.btn_reminderadd);
		 Button btn_reminderdelete=(Button)v.findViewById(R.id.btn_reminderdelete);
		 Button btn_reminderupdate=(Button)v.findViewById(R.id.btn_reminderupdate);
		 Button btn_reminderclear=(Button)v.findViewById(R.id.btn_reminderclear);
		 final Button btn_detail=(Button)v.findViewById(R.id.taskdetails_btn);
		 final AlarmManager alarmgr = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
         TaskData.cursor_reminder=DataSupport.findBySQL("select  id as _id,name,sourceId,deadlinetime,frequency,advance,alarminterval,piaction,sn,content from reminder"+" where "+"username"+"="+"'"+TaskData.user+"';");;
  
    TaskData.adapter_reminder= new CountTimeAdapter(getActivity(), TaskData.cursor_reminder) ;
    lv_reminder.setAdapter(TaskData.adapter_reminder);

    //timefilter(alarmgr,TaskData.cursor_reminder,3);
       
  
    /*       
     for
    Cursor c_alarm=DataSupport.findBySQL("select id as _id,name,sourceId,deadlinetime,frequency,advance,alarminterval,piaction from reminder where piaction!='0';");
    if (c_alarm!=null&&c_alarm.getCount()>0){
    	 c_alarm.moveToFirst();
   	do {	
      
       new Alarm(getActivity(),
    		   c_alarm.getString(2),
    		   c_alarm.getString(1),	 
    		   TimeData.changeStrToTime_YY(c_alarm.getString(c_alarm.getColumnIndex("deadlinetime"))),
    		   Integer.parseInt(c_alarm.getString(c_alarm.getColumnIndex("frequency"))),
    		   Integer.parseInt(c_alarm.getString(c_alarm.getColumnIndex("advance"))),
    		   Integer.parseInt(c_alarm.getString(c_alarm.getColumnIndex("alarminterval")))).alarmset();	
 (int i=0;i<c_alarm.getColumnCount();i++){
	    	
    	     
		     Log.d(c_alarm.getColumnName(i), c_alarm.getString(i));  
    	     
    	   
		      
	    }; 
           	Log.d("alarm col"+i,"null"); 
    	 
      }while(c_alarm.moveToNext());
       /*
       new Alarm(getActivity(),
    		   c_alarm.getString(2),
    		   c_alarm.getString(1),	 
    		   TimeData.changeStrToTime_YY(c_alarm.getString(c_alarm.getColumnIndex("deadlinetime"))),
    		   Integer.parseInt(c_alarm.getString(c_alarm.getColumnIndex("frequency"))),
    		   Integer.parseInt(c_alarm.getString(c_alarm.getColumnIndex("advance"))),
    		   Integer.parseInt(c_alarm.getString(c_alarm.getColumnIndex("interval")))).alarmset();	
      
       
       Log.d("reminder alarm init", ""+c_alarm.getCount());
      
    }
    Log.d("cursor_reminder", ""+TaskData.cursor_reminder.getCount());
     */
	
  	  /*handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				boolean isNeedCountTime = false;
				//①：其实在这块需要精确计算当前时间
				for(int index =0;index<TaskData.reminderlist.size();index++){
					reminderBean reminderBean = TaskData.reminderlist.get(index);
					long time = reminderBean.getRemainingtime();
					if(time>1000){//判断是否还有条目能够倒计时，如果能够倒计时的话，延迟一秒，让它接着倒计时
						isNeedCountTime = true;
						reminderBean.setRemainingtime(time-1000);
					}else{
						reminderBean.setRemainingtime(0);
					}
				}
				//②：for循环执行的时间
				adapter.notifyDataSetChanged();
					handler.sendEmptyMessageDelayed(1, 1000);
				break;}
			
			}
		
	};*/
  	    
		lv_reminder.setOnItemClickListener(new OnItemClickListener(){
             int newPos=0;
             int oldPos=0;
            
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				selPos=position;
				selId=id;
				Log.d(Tags,"click position "+selPos+" item "+selId);
				Log.d(Tags,"TaskDataAlarmlist count "+TaskData.bcreceiverlist.size());
                  // v.setBackgroundColor(Color.RED);
                 //TaskData.selTaskID=String.valueOf(TaskData.cursor_opentasks.getString(TaskData.cursor_opentasks.getColumnIndex(TaskData.TdDB.reminder_ID)));
                 for(int i=0;i<parent.getCount();i++){
                     View v=parent.getChildAt(i);
                     if (position == i) {
                    	// int horizontal = 1;
						//v.requestFocus();
                         v.setBackgroundColor( getResources().getColor(R.color.gray));
                     } else {
                         v.setBackgroundColor(Color.TRANSPARENT);
                     }
                
                     
                 }    
		  }
		 });
		  
		  btn_reminderdelete.setOnClickListener(new OnClickListener(){

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					if (selId>=0){
					
						  Builder builder=new AlertDialog.Builder(getActivity());
					      builder.setTitle("确认");
						  builder.setMessage("真要删除ITEM"+selId+"吗？");
						  builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
							    //show.setText("");

								  Reminder selitem = DataSupport.find(Reminder.class, selId);
								  if (selitem!=null){
									String reminder_sn = DataSupport.find(Reminder.class, selId).getSn();
									String reminder_sourceId = DataSupport.find(Reminder.class, selId).getSourceId();
									
									if (TaskData.privilege>0){ 
									 new ConnMySQL(getActivity()).LitePalDelByGsonArrayRequestPost("ReminderServlet","Reminder",reminder_sn); 
									}
									
									DataSupport.delete(Reminder.class, selId);
								    //TaskData.alarmlist.remove(selPos);
									Alarm.alarmCancel(getActivity(), reminder_sourceId);
				
									TaskData.cursor_reminder.requery();
									Toast.makeText(getActivity(), "已删除ITEM"+selId, Toast.LENGTH_SHORT).show();
									//TaskData.adapter_alltasks.notifyDataSetChanged();
									TaskData.adapter_reminder.notifyDataSetChanged();						
								 }else{
									Toast.makeText(getActivity(), "未选中ITEM", Toast.LENGTH_SHORT).show();	 
								 }
								   
							} 
						  } );
						  builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}	  
						  });
						  builder.create().show();	    
					  } else{
							Toast.makeText(getActivity(), "请先选定ITEM", Toast.LENGTH_SHORT).show();	 
					  };
				 }	  
					
			  });	
		
		/*
		  btn_reminderdelete.setOnClickListener(new OnClickListener(){

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if (selPos>=0){
						alarmgr.cancel(TaskData.alarmlist.get(selPos));
						DataSupport.delete(Reminder.class, selId);
					    //TaskData.alarmlist.remove(selPos);
						TaskData.cursor_reminder.requery();
						Toast.makeText(getActivity(), "已删除"+selId, Toast.LENGTH_SHORT).show();
						//TaskData.adapter_alltasks.notifyDataSetChanged();
						TaskData.adapter_reminder.notifyDataSetChanged();						
					
					    }else{
							Toast.makeText(getActivity(), "请先选定item", Toast.LENGTH_SHORT).show();	 
						  }
					
					if (TaskData.reminderlist!=null&&selId>=0){
					
						TaskData.reminderlist.remove(selId);
						DataSupport.saveAll(TaskData.reminderlist);
						Toast.makeText(getActivity(), "item"+selId+"deleted", Toast.LENGTH_SHORT).show();
						//TaskData.adapter_alltasks.notifyDataSetChanged();
						
						TaskData.adapter_reminder.notifyDataSetChanged();						
					
					    }else{
							Toast.makeText(getActivity(), "请先选定任务", Toast.LENGTH_SHORT).show();	 
						  }
			     }			
		    });
		    */
		  btn_reminderupdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaskData.adapter_reminder.notifyDataSetChanged();
				
				
			} 
		  });
		 
		  btn_reminderadd.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					 //Log.d("from_fg", from_fg.toString()); 
					    DialogFragment_Reminder dialogfrag_reminder=new DialogFragment_Reminder();
						TaskTool.showDialog(dialogfrag_reminder);
						Log.d(Tags,"show dialogfrag"+dialogfrag_reminder.toString());
					
					
					//TaskData.from_fg=showFrag(TaskData.from_fg,R.id.sublayout_task,subfrag_task,3);
				//	Log.d("task tab", "choice3");
				}
			  });
		  
		  
		  btn_reminderclear.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					 Builder builder=new AlertDialog.Builder(getActivity());
					      builder.setTitle("确认");
						  builder.setMessage("真要清除吗？");
						  builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
							    //show.setText("");
								for (int i=0;i<TaskData.alarmpilist.size();i++){
									alarmgr.cancel(TaskData.alarmpilist.get(i));						
									Log.d(Tags,"alarmmgr cancel "+i);	
								}
								    Log.d(Tags,"alarmReceiverlist size:"+TaskData.bcreceiverlist.size());
								for (int j=0;j<TaskData.bcreceiverlist.size();j++){
									try{
						    			  getActivity().unregisterReceiver(TaskData.bcreceiverlist.get(j));
						    			  Log.d(Tags,"unregit alarm No."+j+" Receiver ID "+TaskData.bcreceiverlist.get(j).toString());
									  }catch (IllegalArgumentException e) { 
											    if (e.getMessage().contains("Receiver not registered")) { 
											        // Ignore this exception. This is exactly what is desired 
											   	 Log.d(Tags,"unregit alarm No."+j+" error Receiver not registered");
											    } else { 
											        // unexpected, re-throw 
											    	 Log.d(Tags,"unregit alarm No."+j+" error"+e.toString());
											    	 throw e; 
											    }	 
									  }
									TaskData.alarmsourceidlist.remove(j);
									TaskData.alarmpilist.remove(j);
									TaskData.bcreceiverlist.remove(j);
									Log.d(Tags,"alarmReceiverlist "+j+" clear");	
								}
								DataSupport.deleteAll(Reminder.class);
								
								if (TaskData.privilege>TaskData.entryright){
									 Map params = new HashMap();
							    	
							    	 params.put("username", TaskData.user);
									 new ConnMySQL(getActivity()).LitePalClearByGsonArrayRequestPost("ReminderServlet",params); 
							    }
								 
								TaskData.cursor_reminder.requery();
								TaskData.adapter_reminder.notifyDataSetChanged();	
								
								Toast.makeText(getActivity(), "已清空", Toast.LENGTH_SHORT).show();	
							} 
						  } );
						  builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}	  
						  });
						  builder.create().show();	    
					  } ;
					
			  });
		  
		  
		/*
		  btn_reminderclear.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
	                //int a=TaskData.reminderlist.size();				
					//TaskData.reminderlist.clear();
					//DataSupport.saveAll(TaskData.reminderlist);
					//Toast.makeText(getActivity(), "item"+a+"clear", Toast.LENGTH_SHORT).show();
					//TaskData.adapter_alltasks.notifyDataSetChanged();
					
					for (int i=0;i<TaskData.alarmlist.size();i++){
						alarmgr.cancel(TaskData.alarmlist.get(i));						
						Log.d("alarmmgr cancel "+i, "cancel");	
					}
					for (int i=0;i<TaskData.alarmlist.size();i++){
						
						TaskData.alarmlist.remove(i);
						Log.d("alarmlist"+i, "clear");	
					}
					DataSupport.deleteAll(Reminder.class);
					TaskData.cursor_reminder.requery();
					TaskData.adapter_reminder.notifyDataSetChanged();
					
					
					Toast.makeText(getActivity(), "已清空", Toast.LENGTH_SHORT).show();									
				} 
			  });
			 */
		  /*
			Button btn_conn=(Button)v.findViewById(R.id.btn_conn);
			btn_conn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Cursor ca = DataSupport.findBySQL("select * from reminder");
					jolist = new JSONArray();
					ca.moveToFirst();
					while(ca.moveToNext()){
						JSONObject jo=new JSONObject(); 
					    for (int i=0;i<ca.getColumnCount();i++){
						    jo.put(ca.getColumnName(i), ca.getString(i)); 				      
					    }
					    jolist.add(jo);
					}   
					    //将用户名和密码放入HashMap中      
					    Log.d("json data", jolist.toString());
					    class MemoThread implements Runnable  
					    {  
					        
					        @Override  
					        public void run() {  
					            // TODO Auto-generated method stub  
					        	String uri = "http://192.168.1.100:8080/EasyTest/ReminderServlet";  
					    		//String uri2 = "http://www.163.com/";
					    	    HttpPost request = new HttpPost(uri); 
					    	    //HttpGet request2 = new HttpGet(uri); 
					    	    Log.i("create request",request.toString());
					    	     
					    
		    	        StringEntity se = null;
		    			try {
		    				se = new StringEntity(jolist.toString().trim());
		    				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		    				 request.setEntity(se); 
		    				 Log.i("set entity",jolist.toString());
		    			} catch (UnsupportedEncodingException e1) {
		    				// TODO Auto-generated catch block
		    				e1.printStackTrace();
		    			}       
		    	        
		    	        // 发送请求  
		    			HttpResponse  httpResponse;
		    			BasicHttpParams httpParams = new BasicHttpParams();
		    	        HttpConnectionParams.setConnectionTimeout(httpParams, 10*1000);
		    	        HttpConnectionParams.setSoTimeout(httpParams, 20*1000);
		    			HttpClient httpClient = new DefaultHttpClient(httpParams);  
		    			//httpClient.getConnectionManager().closeExpiredConnections();;
		    			String retSrc=null;
		    				try {               
		    					httpResponse = httpClient.execute(request);             // read response entity              // do something!!!              
		    					Log.i("httpresponse",httpResponse.toString());
		    					
		    					if(httpResponse.getStatusLine().getStatusCode()==200)
		            	        {
		    						
		            	        	 // 得到应答的字符串，这也是一个 JSON 格式保存的数据     
		            				try {
		            					retSrc = EntityUtils.toString(httpResponse.getEntity());
		            				  
		            					Log.i("retSrc",retSrc);
		            				
		            				} catch (ParseException e1) {
		            					// TODO Auto-generated catch block
		            					e1.printStackTrace();
		            				} catch (IOException e1) {
		            					// TODO Auto-generated catch block
		            					e1.printStackTrace();
		            				}  
		            	          }else{
		      						//loginresult="void";
		      					  }
		            	        
		    				    } catch (Exception e) {              
		    						e.printStackTrace();               
		    						throw new RuntimeException(e);          
		    					} finally {               
		    							request.abort();               
		    							Log.i("request",request.toString());                				
		    					} 
				           }
					       
					   }   
					   MemoThread memothread=new MemoThread();
				        Thread thread_memo = new Thread(memothread);
				    	thread_memo.start();
				}   	
			}); 
		  */
		  
		  
		return v;
	 
	 }

	 void timefilter(AlarmManager alarmgr,Cursor c,int filtermin){
	   if (c!=null&&c.getCount()>0){ 
		 c.moveToFirst();
		 do{
			String deadlinetime= c.getString(c.getColumnIndex("deadlinetime"));
			String advance= c.getString(6);
			long a=0;
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
			
		   
			        try {
				    a=sdf.parse(deadlinetime).getTime();
			        } catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
			         	e.printStackTrace();
			        }
		   
			//a = TimeData.changeStrToTime_YYYY(et_reminderdeadlinetime.getText().toString());
			
			   long b=new Date().getTime();
			
			   long t = 0;
			    if ( c.getString(6)!=null){		
			           t=Integer.parseInt(advance)*60*1000;
			     }	
		        long timegap=a-b-t;
		        Log.d("time gap",""+timegap);
		        long reminder_id = c.getLong(0);
		        Log.d("reminder id",""+reminder_id);
		        if (timegap<-filtermin*60*1000){
			         alarmgr.cancel(TaskData.alarmpilist.get(c.getPosition()));
			         DataSupport.delete(Reminder.class, reminder_id );
		             //TaskData.alarmlist.remove(selPos);
			         c.requery();
			         //Toast.makeText(getActivity(), "已删除"+selId, Toast.LENGTH_SHORT).show();
			          //TaskData.adapter_alltasks.notifyDataSetChanged();
			         TaskData.adapter_reminder.notifyDataSetChanged();
			         Log.d("time filter", TaskData.cursor_reminder.getPosition()+"filtered");
			   
		         }  
		 }while(c.moveToNext());
	   } 
	 }
	 
	 public Fragment showFrag(Fragment from_fg,int viewframe,Fragment[] frag,int i){
		  FragmentTransaction ft = getFragmentManager().beginTransaction();

	       if (from_fg==null){
	    	   ft.add(viewframe, frag[i]).commit(); 
	    	    
	       }else{
	    	   if (!frag[i].isAdded()){
	    		 
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
	 
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
	
		//TaskTool.RegisterAllReminders(getActivity());
		
		
		/*
		if (TaskData.reminderlist==null){
			TaskData.reminderlist=new ArrayList<Reminder>();
			
		LitePalApplication.initialize(getActivity());
	    db = Connector.getDatabase();
	    Log.d("db reminder", "db initialized");
		}else{
	     Log.d("db reminder", "already existed");
		}*/
	  	
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/* SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
		 Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		 String curTime = formatter.format(curDate);
		  reminderBean newreminderbean = new reminderBean();
		  int i=0;
		    newreminderbean.setId(i+1);;
			newreminderbean.setAdvance("100");
			newreminderbean.setContent("ss");
			newreminderbean.setCreatedtime(curTime);
			newreminderbean.setDeadlinetime(curTime);
			newreminderbean.setStartedtime(curTime);
			newreminderbean.setName("dd");
			newreminderbean.setFrequency(curTime);
			TaskData.reminderlist.add(newreminderbean);
		*/
	/*	if (TaskData.reminderlist==null){
			TaskData.reminderlist=new ArrayList<reminderBean>();
		}
		if (TaskData.reminderlist.size()==0){
			SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
			 Date curDate = new Date(System.currentTimeMillis());//获取当前时间
			 String curTime = formatter.format(curDate);
			reminderBean newreminderbean = new reminderBean();
			  int i=0;
			    newreminderbean.setId(i+1);;
				newreminderbean.setAdvance("100");
				newreminderbean.setContent("ss");
				newreminderbean.setCreatedtime(curTime);
				newreminderbean.setDeadlinetime(curTime);
				newreminderbean.setStartedtime(curTime);
				newreminderbean.setName("dd");
				newreminderbean.setFrequency(curTime);
				TaskData.reminderlist.add(newreminderbean);
			*/	  
	      
	}
	
			
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		//TaskTool.RegisterAllReminders(getActivity());
		
		super.onActivityCreated(savedInstanceState);
		
		
			
	}



	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
		
	
	}


  
}
