package com.easygoal.achieve;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class mcAdapter_prog extends CursorAdapter {

	    private Context context;
	    private Cursor c;
	    ViewHolder holder;
	    LayoutInflater inflater;

	private int layout_listitem;
	    String r_idno;
	    String Tags="mcAdapter_prog";
	   // ListAdapter lvBaseAdapter_taskcomments;
	    //lvBaseAdapter_taskcomments lvBaseAdapter_taskcomments; 
	   
		 final class ViewHolder { 
				       
		    	public TextView taskid_no;
		    	public TextView record_time;   
		    	public TextView record_progress;
		    	public CheckBox record_comments;		    
		    	public TextView record_done;
		    	
		    }    
	    
	public mcAdapter_prog(Context context, Cursor c,int layout) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		this.context=context;
		this.layout_listitem=layout;
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );

		Log.d(Tags,"create mcAapter_taskrecord"+context.toString());
	}


	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		
		holder = (ViewHolder) view.getTag();
		Log.d(Tags+"|bindView|",String.valueOf(cursor.getPosition())+"row"+String.valueOf(cursor.getCount()));
		//Log.d("mcAapter_taskrecord bindview cursor",cursor.toString()+"ok");
		//Log.d("madapter bindview cursor",cursor.getString(cursor  
           //     .getColumnIndex("task_name"))+cursor.getString(cursor  
          //      .getColumnIndex("task_importance"))+cursor.getString(cursor  
           //     .getColumnIndex("record_recordtime"))+cursor.getString(cursor  
          //      .getColumnIndex("record_comments"))); 
		//setViewText(holder.taskid_no, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO))); 
		//setViewText(holder.taskid_no, cursor.getString(3));      
		//holder.record_comments.setText(cursor.getString(5));       
       // setViewText(holder.name, cursor.getString(cursor  
             //   .getColumnIndex("task_importance"))); 
		
		Log.d(Tags+"|bindView|",cursor.getString(cursor  
		                .getColumnIndex(TaskData.TdDB.RECORD_TASKID))+
		                cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO))+
		                		cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS))); 
		if (holder.record_comments!=null){
		r_idno=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO));
		//holder.taskid_no.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));	
		holder.record_comments.setText(r_idno+" "+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS)));
		holder.record_comments.setChecked(Boolean.valueOf(cursor.getString((cursor.getColumnIndex(TaskData.TdDB.RECORD_DONE)))));		
        holder.record_progress.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_PROGRESS)));    
		}
		final int p=cursor.getPosition();
		holder.record_comments.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean clicked) {
				// TODO Auto-generated method stub
				if (clicked==true){
					 SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
					 Date curDate = new Date();//获取当前时间
					 String curTime = formatter.format(curDate);
				     //long curTimeData = new Date().getTime();
					String p=holder.taskid_no.getText().toString();
					//TaskData.clickedlist.add(p);
					/*
				    ContentValues cv2 = new ContentValues();		 
					 cv2.put(TaskData.TdDB.RECORD_TIME,curTime);
					 cv2.put(TaskData.TdDB.RECORD_CLOSEDTIME,curTime);
					 cv2.put(TaskData.TdDB.RECORD_DONE,"true");
					 TaskData.clickedlist.add(p);
					 String where=TaskData.TdDB.RECORD_TASKID_NO+"=?";
					 String[] whereArgs={p};
					 TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskRecord, cv2, where, whereArgs);
				     */
				     
				     //RecordBean rbean = new RecordBean();
				     //rbean.ad
				}else{
					/*
					if (TaskData.clickedlist.contains(p)) {
					 TaskData.clickedlist.remove(p);
					}
					*/
				}
				
			}
			
		});
		
	}

	
	
	
	
	private void setViewText(TextView name, String string) {
		// TODO Auto-generated method stub
		name.setText(string);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		//final View view = super.newView(context, cursor, parent);  
		
        holder=new ViewHolder();
		View view=inflater.inflate(layout_listitem ,parent,false);
        holder.taskid_no = (TextView) view.findViewById(R.id.tv_taskid_no);
        holder.record_progress=(TextView) view.findViewById(R.id.tv_recordprocess);
        holder.record_comments = (CheckBox) view.findViewById(R.id.cb_recordcomments);
        view.setTag(holder);
        return view;  
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d(Tags,"mcAapter_taskrecord cursor count"+String.valueOf(c.getCount()));
		return c.getCount();
	}

	@Override
	public Cursor getCursor() {
		// TODO Auto-generated method stub
		return super.getCursor();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

}
