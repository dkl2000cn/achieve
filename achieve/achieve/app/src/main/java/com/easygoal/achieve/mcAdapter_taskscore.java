package com.easygoal.achieve;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;

public class mcAdapter_taskscore extends CursorAdapter {



	private Cursor c;
	// private Cursor c;
	String Tags="mcAdapter_taskscore";
	ViewHolder holder;
	LayoutInflater inflater;
	private int listviewItemlist=R.layout.lv_taskscore;
	private String usergroup_str;
	final static class ViewHolder {
		public TextView taskname;
		public TextView taskachieved;
		public TextView taskenjoyment;
		public TextView taskexperience;
		public TextView taskprogress;
		public TextView taskaccomplished;
		public Button taskdetails;
	}

	public mcAdapter_taskscore(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );

	}



	@Override
	public void bindView(final View view, final Context context, Cursor cursor) {
		// TODO Auto-generated method stub

		holder = (ViewHolder) view.getTag();
		if (cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))!=null){
			usergroup_str="["+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))+
					"@"+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_OWNERNICKNAME))+"] ";;
		}else{
			usergroup_str="";
		}
		holder.taskname.setText(usergroup_str+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME)));
		holder.taskaccomplished.setText( cursor.getString(cursor
				.getColumnIndex(TaskData.TdDB.SUM_ACCOMPLISHED)));
		holder.taskachieved.setText( cursor.getString(cursor
				.getColumnIndex(TaskData.TdDB.SUM_ACHIEVED)));
		holder.taskenjoyment.setText(cursor.getString(cursor
				.getColumnIndex(TaskData.TdDB.SUM_ENJOYMENT)));
		holder.taskexperience.setText( cursor.getString(cursor
				.getColumnIndex(TaskData.TdDB.SUM_EXPERIENCE)));
		final int p=cursor.getPosition();
		final String click_Id=cursor.getString(cursor.getColumnIndex("_id"));
		holder.taskdetails.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment_TaskDetail dialogfrag_taskdetail=new DialogFragment_TaskDetail();
				//c1.move(p);
				TaskData.selTaskID=click_Id;
				//view.setBackgroundColor(color.gray);
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

				TaskTool.showDialog(dialogfrag_taskdetail);

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
		View view=inflater.inflate(listviewItemlist ,parent,false);

		holder.taskname = (TextView) view.findViewById(R.id.task_item1_name_tv);
		holder.taskaccomplished = (TextView) view.findViewById(R.id.task_item_accomplished_tv);
		holder.taskachieved = (TextView) view.findViewById(R.id.task_item11_achieved_tv);
		holder.taskenjoyment = (TextView) view.findViewById(R.id.task_item12_enjoyment_tv);
		holder.taskexperience = (TextView) view.findViewById(R.id.task_item13_experience_tv);
		holder.taskdetails=(Button)view.findViewById(R.id.taskscore_bt);
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
