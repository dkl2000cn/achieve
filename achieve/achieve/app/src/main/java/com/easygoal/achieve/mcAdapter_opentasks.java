package com.easygoal.achieve;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v4.widget.CursorAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.text.ParseException;

public class mcAdapter_opentasks extends CursorAdapter {

	
	private Context context;
	private Cursor c;
	//Cursor c2;
	private String deadline_str; 
	private String curTime_str;
	private String usergroup_str;
	private String owner_str;
	private String task_sn;
	private String cycle_unit_str;
	private String cycle_unit;
	LayoutInflater inflater;
	ViewHolder holder;
	private int lvitemlist;
	private int choice;
	GestureDetector mGestureDetector;
    final static class ViewHolder {
		        public TextView taskname;   
		        public TextView taskdeadline;    
		        public TextView taskprogress; 
		        public TextView tasksnno; 
		        public Button taskdetails;
				public TextView taskpriority;
		    }    
   
    String Tags="mcAdapter_opentasks";
    
	public mcAdapter_opentasks(Context context, int lvitemlist,Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		this.context=context;
		this.lvitemlist= lvitemlist;
		deadline_str=null;
		owner_str="";
		curTime_str=TaskTool.getCurTime();
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );

	}


	@Override
	public void bindView(final View view, final Context context, Cursor cursor) {
		// TODO Auto-generated method stub

		holder = (ViewHolder) view.getTag();
		choice=-1;
		task_sn=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_SN));
		cycle_unit=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_CYCLEUNIT));
		deadline_str=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
	    //cursor.moveToFirst();
		if (cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))!=null){
			usergroup_str="["+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))+
					"@"+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_OWNERNICKNAME))+"] ";;
		}else{
			usergroup_str="";
		}

		if (cycle_unit!=null&&!cycle_unit.equals("单次")){

			//cycle_unit_str="["+cycle_unit+"-"+TimeData.getSpecificNewTime(deadline_str,cycle_unit)+"]";
			if (deadline_str!=null) {
				try {
					cycle_unit_str = "[" + TimeData.getSpecificNewTime(deadline_str,cycle_unit)+ "]";
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				cycle_unit_str = "[" + cycle_unit + "]";
			}

		}else{
			cycle_unit_str="";
		}

    	deadline_str=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
        if (TimeData.TimeGap_YYMMDDHHSS(curTime_str, deadline_str)<0){;
    	    SpannableString spanStr_time=new SpannableString("(过期)");
    	    AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(TaskData.overduetextsize); 
 		    ForegroundColorSpan span_color=new ForegroundColorSpan(TaskData.overduetextcolor);
 		    StyleSpan span_style = new StyleSpan(Typeface.ITALIC); 
		    spanStr_time.setSpan(span_style, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 		    spanStr_time.setSpan(span_size, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 		    spanStr_time.setSpan(span_color, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.taskname.setText(usergroup_str+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME))+cycle_unit_str);
			holder.taskname.append(spanStr_time);
		}else{
			holder.taskname.setText(usergroup_str+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME))+cycle_unit_str+owner_str);
		}
    	holder.taskpriority.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_PRIORITY))); 
    	holder.taskdeadline.setText(cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
    	holder.tasksnno.setText(cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_SEQUENCENO)));
    	holder.taskprogress.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
    	final int p=cursor.getPosition();
    	final String click_Id2=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID));
		final String taskstatus=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_STATUS));
		final String click_sn2=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_SN));
		final Cursor c2=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+
				" where "+TaskData.TdDB.TASK_SN+"='"+click_sn2+"'",null);
		//final WeakReference<Cursor> c2 = new WeakReference<Cursor>(c_r);
        //LogUtils.d(click_sn2+" rec count: "+c2.getCount());
		if (taskstatus!=null&&taskstatus.equals("finished")) {
			holder.taskdetails.setBackgroundResource(R.drawable.ok_32px);
			holder.taskdetails.setClickable(false);
			    choice=2;
		}

		if (taskstatus!=null&&taskstatus.equals("cancelled")) {
			holder.taskdetails.setBackgroundResource(R.drawable.cancel_32px);
			choice=3;
		}

		if (taskstatus!=null&&taskstatus.equals("open")& c2!=null&c2.getCount()>0) {
			    holder.taskdetails.setBackgroundResource(R.drawable.todowithchild_32px);
			    choice=1;
		}else{
			if (taskstatus!=null&&taskstatus.equals("open")) {
				holder.taskdetails.setBackgroundResource(R.drawable.todo_32px);
				choice=0;
			}
		}

    	holder.taskdetails.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaskData.selTaskID=click_Id2;
				//Log.d("selTaskID", ""+TaskData.selTaskID +"click" );
				//view.setBackgroundColor(color.green);
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
			//	DialogFragment_TaskDetail dialogfrag_taskdetail=new DialogFragment_TaskDetail();
				//TaskData.showDialog(dialogfrag_taskdetail);
					//Log.d("dialogfrag", dialogfrag_taskdetail.toString());
				if (taskstatus.equals("cancelled")) {
						choice=3;
				}
				if (taskstatus.equals("finished")) {
					    choice=2;
				}
				if (taskstatus.equals("open")& c2!=null&&c2.getCount()>0) {
					    choice=1;
				}else{
					if (taskstatus.equals("open")) {
						choice=0;
					}
				}

				switch (choice){
					case 1:  DialogFragment_TaskDetail dialogfrag_taskdetail=new DialogFragment_TaskDetail();
						     TaskTool.showDialog(dialogfrag_taskdetail);
						     //Toast.makeText(context,"choice1", Toast.LENGTH_SHORT).show();
						     choice=-1;
						     break;
					case 2:  DialogFragment_TaskDetail dialogfrag_taskdetail1=new DialogFragment_TaskDetail();
						      TaskTool.showDialog(dialogfrag_taskdetail1);
						    //Toast.makeText(context,"choice1", Toast.LENGTH_SHORT).show();
						      choice=-1;
						      break;
					case 3:  DialogFragment_TaskDetail dialogfrag_taskdetail2=new DialogFragment_TaskDetail();
								TaskTool.showDialog(dialogfrag_taskdetail2);
								//Toast.makeText(context,"choice1", Toast.LENGTH_SHORT).show();
								choice=-1;
								break;
					case 0: TaskTool.quickclose(context);
						    //Toast.makeText(context,"choice0", Toast.LENGTH_SHORT).show();
						   	  	choice=-1;
						    	break;
					default:	choice=-1;
						   	 	break;
				}
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
        holder.tasksnno = (TextView) view.findViewById(R.id.task_item36_snno_tv);
        holder.taskpriority = (TextView) view.findViewById(R.id.task_item7_priority_tv);
        holder.taskdeadline = (TextView) view.findViewById(R.id.task_item15_deadline_tv);
        holder.taskprogress = (TextView) view.findViewById(R.id.task_item16_progress_tv);
        holder.taskdetails=(Button)view.findViewById(R.id.taskdetails_btn);
        view.setTag(holder);
       
        return view;  
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//Log.d(Tags,"cursor count"+String.valueOf(c.getCount()));
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
