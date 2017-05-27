package com.easygoal.achieve;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import net.sf.json.JSONArray;

public class DialogFragment_CommentList extends DialogFragment {
	String curTime;
	JSONArray jolist;
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
		 SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
		 Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		 curTime = formatter.format(curDate);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v=inflater.inflate(R.layout.dialogfg_view_commentlist, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		 
		  Log.d("create database", "start"+TaskData.db_TdDB.toString());

		  //final ListView m_listview=(ListView)v.findViewById(R.id.task_show_lv);
		 Button btn_commentsave=(Button)v.findViewById(R.id.comment_save_bt);
		 Button btn_commentedit=(Button)v.findViewById(R.id.comment_edit_bt);
		 Button btn_commentclose = (Button) v.findViewById(R.id.comment_close_bt); 
		 Button btn_commentdelete=(Button)v.findViewById(R.id.comment_delete_bt);
		 Button btn_recordclear=(Button)v.findViewById(R.id.record_clear_bt);
		// Button btn_addrecord=(Button)v.findViewById(R.id.add_comments_bt);
		  
	        btn_commentclose.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
		 
	        String[] column_record={
					TaskData.TdDB.RECORD_ID,
					TaskData.TdDB.RECORD_TASKID, 
					//TaskData.TdDB.RECORD_NO, 
					TaskData.TdDB.RECORD_TIME, 
					TaskData.TdDB.RECORD_PROGRESS, 
					TaskData.TdDB.RECORD_COMMENTS, 
					//TaskData.TdDB.RECORD_ACHIEVED, 
					//TaskData.TdDB.RECORD_ENJOYMENT, 
					//TaskData.TdDB.RECORD_EXPERIENCE,  
					//TaskData.TdDB.RECORD_TOTALCOUNT
				    };
    
		    int[] tv_record = {
		    R.id.record_item1_recordID_tv,
		    R.id.record_item1_taskID_tv,
		    R.id.record_item2_recordTime_tv,
		    R.id.record_item4_progress_tv,
		    R.id.record_item5_comment_tv};
		    String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
	        TaskData.cursor_allcomments = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord,null);
    		//query(TaskData.TdDB.TABLE_NAME_TaskMain, , null, null, null, null, null);
            Log.d("cursor record",String.valueOf(TaskData.cursor_allcomments.getCount())+"position"+String.valueOf(TaskData.cursor_allcomments.getPosition()));
            TaskData.cursor_allcomments.moveToFirst();
            
            TaskData.adapter_commentlist = new SimpleCursorAdapter(getActivity(), R.layout.lvitemlist_taskcomments, TaskData.cursor_allcomments, column_record,tv_record);
            final ListView lv_taskcomment=(ListView)v.findViewById(R.id.taskcomments_lv);
            //lv_taskcomment.setBackgroundColor(Color.GREEN);
            lv_taskcomment.setAdapter(TaskData.adapter_commentlist);
	        
	        
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
		  btn_commentdelete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				  //  TaskData.db_TdDB.delete(table, whereClause, whereArgs)
				TaskData.cursor_allcomments.moveToPosition(TaskData.cursor_allcomments.getPosition());
				int p=TaskData.cursor_allcomments.getInt(0);
				Log.d("delete row", String.valueOf(p));
				String where=TaskData.TdDB.RECORD_ID+"=?";
				String[] whereArgs={Integer.toString(p)};		
				TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskRecord,where, whereArgs);
				Log.d("delete row", "delete OK");
				TaskData.adapter_commentlist.getCursor().requery();
				TaskData.adapter_commentlist.notifyDataSetChanged();
				Log.d("TaskData.cursor_allcomments comments",String.valueOf(TaskData.cursor_allcomments.getCount())+"position"+String.valueOf(TaskData.cursor_allcomments.getPosition()));
				TaskData.adapterUpdate();
					
				   
			}  
		    }); 
		  
		  btn_commentedit.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 TaskData.cursor_allcomments.requery();
					 Log.d("all comments count", String.valueOf(TaskData.cursor_allcomments.getCount()));
					 TaskData.adapter_commentlist.notifyDataSetChanged();
					//TaskData.adapterUpdate();
					
					 Log.d("TdDB adapter update", "all done");
					// Log.d("update cursor",String.valueOf(TaskData.cursor_commentlist.getCount())+"position"+String.valueOf(TaskData.cursor_opentasks.getPosition()));
					
					
				} 
			  });
		  btn_recordclear.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for (int i=0;i<TaskData.cursor_taskrecord.getCount();i++){
				    TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskRecord, null, null);	
					};
					//TaskData.adapter_opentasks.notifyDataSetChanged();
					TaskData.adapterUpdate();
					
					
					 Log.d("TdDB adapter update", "all done");  
					 Log.d("clear",String.valueOf(TaskData.cursor_opentasks.getCount())+"position"+String.valueOf(TaskData.cursor_opentasks.getPosition()));
					
					
				} 
			  });
		  
		 /*
			Button btn_conn=(Button)v.findViewById(R.id.btn_conn);
			btn_conn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Cursor ca = TaskData.cursor_allcomments;
					jolist = new JSONArray();
					ca.moveToFirst();
					do{
						JSONObject jo=new JSONObject(); 
					    for (int i=0;i<ca.getColumnCount();i++){
						    jo.put(ca.getColumnName(i), ca.getString(i)); 				      
					    }
					    jolist.add(jo);
					} while(ca.moveToNext());  
					    //将用户名和密码放入HashMap中      
					    Log.d("json data", jolist.toString());
					    class MemoThread implements Runnable  
					    {  
					        
					        @Override  
					        public void run() {  
					            // TODO Auto-generated method stub  
					        	String uri = "http://192.168.1.100:8080/EasyTest/TaskrecServlet";  
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

	@Override
	 public void onStart() {
	 	// TODO Auto-generated method stub
	 	super.onStart();
	 	DisplayMetrics dm = new DisplayMetrics();
	 	getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
	 	getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	 }  
}
