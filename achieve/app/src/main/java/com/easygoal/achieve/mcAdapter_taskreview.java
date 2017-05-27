package com.easygoal.achieve;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class mcAdapter_taskreview extends CursorAdapter {

	
	    private Context context;
	    private String taskid;
	    private Cursor c;
	    private int lvitemlist;
	    Date date1;
	    ViewHolder holder;
	    LayoutInflater inflater;
	    SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd");
	// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
	    SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
	    private String usergroup_str;
	    private String owner_str;
	    String Tags="mcAdapter_taskreview";
	    
		 final static class  ViewHolder {
			 public TextView taskID;
		        public TextView taskname;  
		       //public TextView taskassess;  
		       //public TextView taskdeadline;    
		       //public TextView taskprogress;  
		        public TextView taskclosedtime; 
		        public TextView taskclosedcomment;
		        public TextView taskresultcomment; 
		        public TextView taskremark; 
		        public TextView tasklesson; 
		        public TextView taskhistory; 
		        public Button taskdetails;
		    }    
	    
	public mcAdapter_taskreview(Context context, int lvitemlist,Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		this.context=context;
		this.lvitemlist= lvitemlist;
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
	}


	@Override
	public void bindView(final View view, final Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		
		holder = (ViewHolder) view.getTag();
	    //cursor.moveToFirst();
		if (cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))!=null){
			usergroup_str="["+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))+
					"@"+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_OWNERNICKNAME))+"] ";;
		}else{
			usergroup_str="";
		}
		holder.taskname.setText(usergroup_str+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME)));
    	taskid=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID));
    	//holder.taskID.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID)));
    	//holder.taskname.setText("T"+taskid+" "+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME)));
    	//holder.taskclosedcomment.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DELAYEDDAYS)));
    	/*holder.taskassess.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_ASSESSMENT)));
    	holder.taskdeadline.setText(cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
    	holder.taskprogress.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));*/

        date1 = null;
		try {
			date1 = formatter1.parse(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_CLOSEDTIME)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String closedtime = formatter2.format(date1);
        
    	holder.taskclosedtime.setText(closedtime);
    	holder.taskresultcomment.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_RESULTCOMMENT)));
    	holder.taskclosedcomment.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_CLOSEDCOMMENT)));
    	holder.taskremark.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_REMARKS)));
    	holder.tasklesson.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_LESSON)));
    	holder.taskhistory.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_HISTORY)));
    	final int p=cursor.getPosition();
    	//final int selId=cursor.getColumnIndex(TaskData.TdDB.TASK_ID);
    	final String click_Id2=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID));
    	holder.taskdetails.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				TaskData.selTaskID=click_Id2;
				//Log.d("selTaskID", ""+TaskData.selTaskID +"click" );
				view.setBackgroundColor(Color.RED);
				ViewGroup parent = (ViewGroup) view.getParent();
				for(int i=0;i<parent.getChildCount();i++){
                    View childview=((ViewGroup) view.getParent()).getChildAt(i);
                    if (p == i) {
                    	
 						//childview.requestFocus();
                         childview.setBackgroundColor(context.getResources().getColor(R.color.gray));
                     } else {
                         childview.setBackgroundColor(context.getResources().getColor(R.color.mTextColor2));
                     }
				 }  
			DialogFragment_TaskDetail dialogfrag_taskdetail=new DialogFragment_TaskDetail();
			TaskTool.showDialog(dialogfrag_taskdetail);
					//Log.d("dialogfrag", dialogfrag_taskdetail.toString());
			}
        });
       
       // super.bindView(view, context, cursor); 
	}

	public void setViewText(TextView name, String string) {
		// TODO Auto-generated method stub
		name.setText(string);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		//final View view = super.newView(context, cursor, parent);
		holder= new ViewHolder();
        View view=inflater.inflate(lvitemlist ,parent,false);
        holder.taskname = (TextView) view.findViewById(R.id.task_item1_name_tv);  
       // holder.taskassess = (TextView) view.findViewById(R.id.task_item6_assess_tv);  
      //  holder.taskdeadline = (TextView) view.findViewById(R.id.task_item15_deadline_tv);  
       // holder.taskprogress = (TextView) view.findViewById(R.id.task_item16_progress_tv);  
        holder.taskID = (TextView) view.findViewById(R.id.task_item0_id_tv); 
        holder.taskclosedtime = (TextView) view.findViewById(R.id.task_item30_closedtime_tv); 
        holder.taskclosedcomment = (TextView) view.findViewById(R.id.task_item30_closedcomment_tv); 
        holder.taskresultcomment = (TextView) view.findViewById(R.id.task_item30_resultcomment_tv); 
        holder.taskremark = (TextView) view.findViewById(R.id.task_item30_remark_tv); 
        holder.tasklesson = (TextView) view.findViewById(R.id.task_lesson_tv); 
        holder.taskhistory = (TextView) view.findViewById(R.id.task_history_tv); 
        holder.taskdetails=(Button)view.findViewById(R.id.taskdetails_btn);
        view.setTag(holder); 
      
        return view;  
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
