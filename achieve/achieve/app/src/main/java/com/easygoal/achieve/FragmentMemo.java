package com.easygoal.achieve;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.litepal.crud.DataSupport;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import net.sf.json.JSONArray;

public class FragmentMemo extends Fragment {
	
	
	
	Handler handler=null;
	long selID=-1;
	ListView lv_memo;
	SQLiteDatabase db; 
	JSONArray jolist;
	View oldview=null;
    View newview = null;
    String Tags="FragmentMemo";
    
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.subfg_home_toptab4_memo, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 		
		 
	    lv_memo=(ListView)v.findViewById(R.id.lv_memo);
	     Button btn_memoadd=(Button)v.findViewById(R.id.btn_memoadd);
		 Button btn_memodelete=(Button)v.findViewById(R.id.btn_memodelete);
		 Button btn_memoupdate=(Button)v.findViewById(R.id.btn_memoupdate);
		 Button btn_memoclear=(Button)v.findViewById(R.id.btn_memoclear);
		 
    TaskData.cursor_memo=DataSupport.findBySQL("select id as _id,name,picUriStr,fileUriStr,content,createdtime,deadlinetime from memo where username="+"'"+TaskData.user+"'"+";");
    TaskData.cursor_memo.moveToFirst();   
    TaskData.adapter_memo=new MemoCursorAdapter(getActivity(),TaskData.cursor_memo);
    Log.d(Tags, "cursor_memo count"+TaskData.cursor_memo.getCount());
    
    lv_memo.setAdapter(TaskData.adapter_memo);
	/*handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				boolean isNeedCountTime = false;
				//①：其实在这块需要精确计算当前时间
				for(int index =0;index<TaskData.memolist.size();index++){
					memoBean memoBean = TaskData.memolist.get(index);
					long time = memoBean.getRemainingtime();
					if(time>1000){//判断是否还有条目能够倒计时，如果能够倒计时的话，延迟一秒，让它接着倒计时
						isNeedCountTime = true;
						memoBean.setRemainingtime(time-1000);
					}else{
						memoBean.setRemainingtime(0);
					}
				}
				//②：for循环执行的时间
				adapter.notifyDataSetChanged();
					handler.sendEmptyMessageDelayed(1, 1000);
				break;}
			
			}
		
	};*/
      	
		lv_memo.setOnItemClickListener(new OnItemClickListener(){
             int newPos=0;
             int oldPos=0;
            
             
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Log.d("list memo", "click pos"+String.valueOf(position)+"id"+String.valueOf(id));
				//startActivity(openFile(TaskData.memolist.get(position).getFile().getAbsolutePath()));
				selID=id;
				 if (view!=newview){
					oldview=newview; 
					newview=view;
					newview.setBackgroundColor(getResources().getColor(R.color.gray));
					if (oldview!=null){
					oldview.setBackgroundColor(getResources().getColor(R.color.mTextColor2));
					} 
				}
                 //view.setBackgroundColor(Color.RED);
                 //TaskData.selTaskID=String.valueOf(TaskData.cursor_opentasks.getString(TaskData.cursor_opentasks.getColumnIndex(TaskData.TdDB.memo_ID)));
              /*
				if (parent.getCount()>0){
					  Log.d("childcount", ""+parent.getCount());
				for(int i=0;i<parent.getCount();i++){
                     
                     if (position == i) {
                    	// int horizontal = 1;
						//v.requestFocus();
                         //v.setBackgroundColor( getResources().getColor(R.color.gray));
                         Log.d("position", ""+i+v.toString());
                    	 v.setBackgroundColor(getResources().getColor(R.color.gray));
                     } else {
                        // v.setBackgroundColor(getResources().getColor(R.color.mistyrose));
                    	 Log.d("other", ""+i+v.toString());
                    	 v.setBackgroundColor(getResources().getColor(R.color.green));
                     }
               
                     
                 }  
              }*/   
		  }
		 });
		  
		lv_memo.setOnItemLongClickListener(new OnItemLongClickListener(){
            int newPos=0;
            int oldPos=0;
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
				// TODO Auto-generated method stub
				DialogFragment_MemoDetail dg_memodetail=new DialogFragment_MemoDetail(id);
    	    	TaskTool.showDialog(dg_memodetail);
	
				return false;
			}
            
		 });
		  
		
		
	 	  /*
		  lv_memo.setOnItemSelectedListener(new OnItemSelectedListener(){
			  int ccc;
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				ccc = position;
				
				Log.d("select at111", String.valueOf(position));
	
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			    
		  });
		  */
		
		  btn_memoupdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaskData.cursor_memo.requery();
				TaskData.adapter_memo.notifyDataSetChanged();
				
				Log.d(Tags, "adapter_memo updated");
				
				
			} 
		  });
		 
		 
		  btn_memodelete.setOnClickListener(new OnClickListener(){

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					if (selID>=0){
					
						  Builder builder=new AlertDialog.Builder(getActivity());
					      builder.setTitle("确认");
						  builder.setMessage("真要删除ITEM"+selID+"吗？");
						  builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
							    //show.setText("");
								String memo_sn = DataSupport.find(Memo.class, selID).getSn();
								DataSupport.delete(Memo.class, selID);
								TaskData.cursor_memo.requery();
								TaskData.adapter_memo.notifyDataSetChanged();
								
								Toast.makeText(getActivity(), "已删除ITEM"+selID, Toast.LENGTH_SHORT).show();
								
								if (TaskData.privilege>TaskData.entryright){ 
									
							    	 new ConnMySQL(getActivity()).LitePalDelByGsonArrayRequestPost("MemoServlet","memo",memo_sn); 
								}
								 Cursor cr=DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId="+"'"+"M"+selID+"'"+" and username="+"'"+TaskData.user+"';");
								 
								 if (cr.getCount()>0){
									   cr.moveToFirst();
									   
									   do{
										   String remindersn = cr.getString(1);
										   Alarm.alarmCancel(getActivity(), remindersn);	    
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d(Tags,"delete reminder"+reminderno); 
									   
									   }while(cr.moveToNext());
								   } 
							    
								TaskData.adapterUpdate();
								
								   
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
		  btn_memodelete.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				  if (selID>=0){
					DataSupport.delete(Memo.class, selID);
					TaskData.cursor_memo.requery();
					TaskData.adapter_memo.notifyDataSetChanged();
					Toast.makeText(getActivity(), "已删除"+selID, Toast.LENGTH_SHORT).show();
					
					
					 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId="+"'"+"M"+selID+"'"+" and username="+"'"+TaskData.user+"';");
						
					 if (cr.getCount()>0){
						   cr.moveToFirst();
						   do{
						   int reminderno = cr.getInt(0);
						   DataSupport.delete(Reminder.class, reminderno);
						   Log.d("delete reminder", "delete "+reminderno);   
						   
						   }while(cr.moveToNext());
					   } 
					TaskData.adapterUpdate();
					
					
					
					 Log.d("TdDB adapter update", "all done");
					 Log.d("update cursor",String.valueOf(TaskData.cursor_alltasks.getCount())+"position"+String.valueOf(TaskData.cursor_alltasks.getPosition()));
				   }else{
					   Toast.makeText(getActivity(), "请先选择记录", Toast.LENGTH_SHORT).show();   
				   }
					
				} 
			  });
			 */
		  
		  
		  btn_memoadd.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					 //Log.d("from_fg", from_fg.toString()); 
					DialogFragment_Memo dialogfrag_memo=new DialogFragment_Memo();
						showDialog(dialogfrag_memo);
						
					
					
					//TaskData.from_fg=showFrag(TaskData.from_fg,R.id.sublayout_task,subfrag_task,3);
				//	Log.d("task tab", "choice3");
				}
			  });
	
		  Button btn_export=(Button)v.findViewById(R.id.btn_export);
		  btn_export.setOnClickListener(new OnClickListener(){

				
				@Override
				public void onClick(View v) {
			    	Cursor c=DataSupport.findBySQL("select id as _id,name,picUriStr,fileUriStr,content,createdtime from memo"+" where "+"username"+"="+"'"+TaskData.user+"';");
					// TODO Auto-generated method stub
			    	c.moveToFirst();
				  if (c.getCount()>0){
			       
					 File outfile=new ExportUtils("memo"+TaskTool.getCurTime()).exportToFile();
					 Intent intent2 = new Intent(Intent.ACTION_SEND);
				       intent2.setType("text/*"); 
				      
				       if (outfile != null && outfile.exists()) {  
				    	  
					        Uri u = Uri.fromFile(outfile);  
					     
					       intent2.putExtra(Intent.EXTRA_STREAM, u);  
					      } else{
					    	 
					      }
				       //intent.putExtra("BITMAP", screenshot.takeScreenShot(MainActivity_1.this));
				      // intent2.putExtra(Intent.EXTRA_SUBJECT, "opentasks");  
					   //  intent2.putExtra(Intent.EXTRA_TEXT, "opentasks");  
					    // intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				      
					     startActivity(Intent.createChooser(intent2, "memo")); 
					     Log.d(Tags,"export memo to file done");
			       
				}else{   
					  Toast.makeText(getActivity(), "已清空", Toast.LENGTH_SHORT).show();	 
				  }
				}	
		 });
		  
		  
		  btn_memoclear.setOnClickListener(new OnClickListener(){

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
								int del = DataSupport.deleteAll(Memo.class);
								//Cursor ca = DataSupport.findBySQL("select * from memo");
								
								 Cursor cr=DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId like "+"'"+"M%"+"'"+" and username="+"'"+TaskData.user+"';");
									
								 if (cr.getCount()>0){
									   cr.moveToFirst();
									   do{
										   String remindersn = cr.getString(1);
										   Alarm.alarmCancel(getActivity(), remindersn);
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d("fragment memo", "clear related reminder"+reminderno);   
									   	 
									   }while(cr.moveToNext());
								   } 	
								 
								Toast.makeText(getActivity(),"已清空", Toast.LENGTH_SHORT).show();
								if (TaskData.privilege>TaskData.entryright){ 
									 Map params = new HashMap();
							    	
							    	 params.put("username", TaskData.user);
									 new ConnMySQL(getActivity()).LitePalClearByGsonArrayRequestPost("MemoServlet", params); 
							    }
								 
								TaskData.adapterUpdate();
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
		  btn_memoclear.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for (int i=0;i<TaskData.memolist.size();i++){
				    TaskData.memolist.clear();
					};
					//TaskData.adapter_alltasks.notifyDataSetChanged();
					TaskData.adapter_memo.notifyDataSetChanged();
				
					int del = DataSupport.deleteAll(Memo.class);
						//Cursor ca = DataSupport.findBySQL("select * from memo");
						 Log.i("del",""+del);
						 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like "+"'"+"M%"+"'"+" and username="+"'"+TaskData.user+"';");
							
						 if (cr.getCount()>0){
							   cr.moveToFirst();
							   do{
							   int reminderno = cr.getInt(0);
							   DataSupport.delete(Reminder.class, reminderno);
							   Log.d("clear reminder", "clear "+reminderno);   
							   
							   }while(cr.moveToNext());
						   } 		
						TaskData.adapterUpdate();								
				} 
			  });
		 */
	
		return v;
	 
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
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/* SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
		 Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		 String curTime = formatter.format(curDate);
		  MemoBean newmemobean = new MemoBean();
		  int i=0;
		    newmemobean.setId(i+1);;
			newmemobean.setContent("ss");
			newmemobean.setCreatedtime(curTime);
			newmemobean.setName("dd");
			TaskData.memolist.add(newmemobean);
		*/
		//TaskData.cursor_memo=DataSupport.findBySQL("select * from memo");
		/*
		if (TaskData.memolist==null){
			TaskData.memolist=new ArrayList<Memo>();
		}
		if (TaskData.memolist.size()==0){
			    LitePalApplication.initialize(getActivity());
				db = Connector.getDatabase();
				Toast.makeText(getActivity(),db.toString(), Toast.LENGTH_SHORT).show();
		*/
		//Memo memo=new Memo();
				//memo.setName("kevin");
				//memo.setContent("i love you");
				//memo.save();
			  //List<Memo> memolist = null;
				//TaskData.memolist.add(memo);  
			 // DataSupport.saveAll(TaskData.memolist); 
							 
		
		}

			
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		  
	}	  
}
