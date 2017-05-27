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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class mcAdapter_tasks_pagedivided extends CursorAdapter {

	
	 private Context context;
	    private Cursor c;
	   private Cursor c2;
	    private int lvitemlist;
	    int pageNum;
	    int pageCount;
	    private String deadline_str; 
		private String curTime_str;
	private String usergroup_str;
	private int choice;

	    String Tags="mcAdapter_tasks_pagedivided";
		 final class ViewHolder {  
		        public TextView taskname;  
		        public TextView taskassess; 
		        public TextView taskpriority; 
		        public TextView taskdeadline;    
		        public TextView taskprogress;  
		        public Button taskdetails;
		    }    
	    
	public mcAdapter_tasks_pagedivided(Context context, int lvitemlist,Cursor c,int pageNum,int pageCount) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		this.context=context;
		this.lvitemlist= lvitemlist;
		this.pageNum=pageNum;
	    this.pageCount=pageCount;
	    deadline_str=null;
		curTime_str=TaskTool.getCurTime();
		
	}


	@Override
	public void bindView(final View view, final Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = (ViewHolder) view.getTag();

		choice=-1;

	    //cursor.moveToFirst();
		if (cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))!=null){
			usergroup_str="["+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))+"] ";
		}else{
			usergroup_str="";
		}
    	deadline_str=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
        if (TimeData.TimeGap_YYMMDDHHSS(curTime_str, deadline_str)<0){;
        
    	    SpannableString spanStr_time=new SpannableString("(¹ýÆÚ)");
    	    AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(TaskData.overduetextsize); 
 		    ForegroundColorSpan span_color=new ForegroundColorSpan(TaskData.overduetextcolor);
 		    StyleSpan span_style = new StyleSpan(Typeface.ITALIC); 
		    spanStr_time.setSpan(span_style, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 		    spanStr_time.setSpan(span_size, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 		    spanStr_time.setSpan(span_color, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.taskname.setText(usergroup_str+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME)));
			holder.taskname.append(spanStr_time);
		}else{
			holder.taskname.setText(usergroup_str+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME)));
		}
        
    	holder.taskassess.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_ASSESSMENT)));
    	holder.taskpriority.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_PRIORITY)));
    	holder.taskdeadline.setText(cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
    	holder.taskprogress.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
    	final int p=cursor.getPosition();
    	//final int selId=cursor.getColumnIndex(TaskData.TdDB.TASK_ID);
    	final String click_Id2=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID));
		final String taskstatus=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_STATUS));
		final String click_sn2=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_SN));
		c2 = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+
				" where "+TaskData.TdDB.TASK_SN+"='"+click_sn2+"'",null);

		if (taskstatus.equals("finished")) {
			holder.taskdetails.setBackgroundResource(R.drawable.ok_32px);
			holder.taskdetails.setClickable(false);
			choice=2;
		}
		if (taskstatus.equals("open")& c2.getCount()>0) {
			holder.taskdetails.setBackgroundResource(R.drawable.todowithchild_32px);
			choice=1;
		}else{
			if (taskstatus.equals("open")) {
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
				if (taskstatus.equals("finished")) {
					choice=2;
				}
				if (taskstatus.equals("open")& c2.getCount()>0) {
					choice=1;
				}else{
					if (taskstatus.equals("open")) {
						choice=0;
					}
				}

				switch (choice){
					case 1:  DialogFragment_TaskDetail dialogfrag_taskdetail=new DialogFragment_TaskDetail();
						TaskTool.showDialog(dialogfrag_taskdetail);
						//Toast.makeText(mContext,"choice1", Toast.LENGTH_SHORT).show();
						choice=-1;
						break;
					case 0: TaskTool.quickclose(mContext);
						//Toast.makeText(mContext,"choice0", Toast.LENGTH_SHORT).show();
						choice=-1;
						break;
					default:choice=-1;
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
		ViewHolder holder= new ViewHolder();
		
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        
        View view=inflater.inflate(lvitemlist ,parent,false);
       
        holder.taskname = (TextView) view.findViewById(R.id.task_item1_name_tv);  
        holder.taskassess = (TextView) view.findViewById(R.id.task_item6_assess_tv);  
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
		if (pageCount*pageNum<=c.getCount()){
		   return pageCount*pageNum;
		}else{
		   return c.getCount();
		}
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
