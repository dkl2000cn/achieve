package com.easygoal.achieve;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class mcAdapter_taskrun extends CursorAdapter {

	 	private Context context;
	    private Cursor c;
	    private int lvitemlist;
	    private String lapsedcomment;
	    private String usergroup_str;
	    private String owner_str;
	    LayoutInflater inflater;
	    ViewHolder holder;
	    SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd");
	// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
	    SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
	    Date date1 = null;
	    DecimalFormat df = new DecimalFormat("0.0");
		
	    final static class ViewHolder {
		          public TextView tv_id;
				  public TextView tv_name;
				  public TextView tv_deadlinetime;
				  public TextView tv_finishedpercent;
				  public TextView tv_lapsedpercent;
				  public TextView tv_lapsedcomment;
		    }    
	    
	public mcAdapter_taskrun(Context context, int lvitemlist,Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		this.context=context;
		this.lvitemlist= lvitemlist;
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
	}


	@Override
	public void bindView(final View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		
		holder = (ViewHolder) view.getTag();
		if (cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))!=null){
			usergroup_str="["+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))+
					"@"+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_OWNERNICKNAME))+"] ";
		}else{
			usergroup_str="";
		}
		holder.tv_name.setText(usergroup_str+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME)));
		String taskid=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID));
    	holder.tv_id.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID)));
    	//holder.tv_name.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME)));


    	//holder.taskclosedcomment.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DELAYEDDAYS)));
    	/*holder.taskassess.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_ASSESSMENT)));
    	holder.taskdeadline.setText(cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
    	holder.taskprogress.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));*/

		try {
			date1 = formatter1.parse(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String deadlinetime = formatter2.format(date1);
        
        
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
     
    	holder.tv_deadlinetime.setText(deadlinetime);
    	holder.tv_finishedpercent.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
    	
    	Double a=Double.parseDouble(cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
    	Double b;
    	
    	b=task_lapsedpercent;
    	holder.tv_lapsedpercent.setText(df.format(task_lapsedpercent));
    
    	double c=a-b;
    	String c_str=new DecimalFormat("0.0").format(c);
    	if (c<0) {
    		lapsedcomment=c_str+"\n"+"(落后)";
    		holder.tv_lapsedcomment.setText(lapsedcomment);
    		holder.tv_lapsedcomment.setTextColor(context.getResources().getColor(R.color.overduetextcolor));				
    	}else{
    		if (c==0) {
    		lapsedcomment="同步";	
    		holder.tv_lapsedcomment.setTextColor(Color.BLACK);
    		}else{
    			lapsedcomment=c_str+"\n"+"(领先)";
    			holder.tv_lapsedcomment.setTextColor(context.getResources().getColor(R.color.darkgreen));
    		}
    	}
    	holder.tv_lapsedcomment.setText(lapsedcomment);
    	/*final int p=cursor.getPosition();
    	//final int selId=cursor.getColumnIndex(TaskData.TdDB.TASK_ID);
    	final String click_Id2=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID));
    	holder.taskdetails.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				TaskData.selTaskID=click_Id2;
				//Log.d("selTaskID", ""+TaskData.selTaskID +"click" );
				view.setBackgroundColor(color.red);
				ViewGroup parent = (ViewGroup) view.getParent();
				for(int i=0;i<parent.getChildCount();i++){
                    View childview=((ViewGroup) view.getParent()).getChildAt(i);
                    if (p == i) {
                   	 int horizontal = 1;
						childview.requestFocus();
                        childview.setBackgroundColor(color.gray);
                    } else {
                        childview.setBackgroundColor(Color.TRANSPARENT);
                    }
				 }  
			DialogFragment_TaskDetail dialogfrag_taskdetail=new DialogFragment_TaskDetail();
				TaskData.showDialog(dialogfrag_taskdetail);
					//Log.d("dialogfrag", dialogfrag_taskdetail.toString());
			}
        });
        Log.d("bind view", "bine view done");
       // super.bindView(view, context, cursor); 
        
        */
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
        holder.tv_id=(TextView)view.findViewById(R.id.task_item0_id_tv);
        holder.tv_name=(TextView)view.findViewById(R.id.task_item1_name_tv);
        holder.tv_deadlinetime=(TextView)view.findViewById(R.id.task_item30_closedtime_tv);
        holder.tv_finishedpercent=(TextView)view.findViewById(R.id.task_item31_finishedpercent_tv);
        holder.tv_lapsedpercent=(TextView)view.findViewById(R.id.task_item32_lapsedpercent_tv);
        holder.tv_lapsedcomment=(TextView)view.findViewById(R.id.task_item33_progcomment_tv);
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
