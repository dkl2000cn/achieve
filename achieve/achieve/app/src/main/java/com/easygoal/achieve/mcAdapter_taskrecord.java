package com.easygoal.achieve;



import org.apache.http.util.TextUtils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.CursorAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

public class mcAdapter_taskrecord extends CursorAdapter {


	private Context context;
	private Cursor c;
	private Cursor c2;
	boolean flag=false;
	private String deadline_str;
	private String curTime_str;
	private String usergroup_str;
	private String owner_str;
	int recorddetailcolor;
	LvHeight lv_height;
	LayoutInflater inflater;
	ViewHolder holder;
	String taskname;
	String taskid;
	String seekSN ;
	String Tags="mcAdapter_taskrecord";
	// ListAdapter lvBaseAdapter_taskcomments;
	//lvBaseAdapter_taskcomments lvBaseAdapter_taskcomments;

	final static class ViewHolder {
		public TextView taskID;
		public TextView taskname;
		//public TextView taskimportance;
		public TextView taskdeadline;
		//public TextView taskachieved;
		//public TextView taskenjoyment;
		//public TextView taskexperience;
		public TextView taskprogress;
		public TextView taskcreatedtime;

		//public com.easygoal.achieve.MyListView comment;
		public ListView comment;
		public CheckBox tasktree;
	}

	public mcAdapter_taskrecord(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		this.context=context;
		deadline_str=null;
		curTime_str=TaskTool.getCurTime();
		recorddetailcolor=context.getResources().getColor(R.color.mTextColor2);;
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
		//Log.d("create mcAapter_taskrecord",context.toString());
	}



	@Override
	public void bindView(final View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub

		holder = (ViewHolder) view.getTag();
		setViewText(holder.taskID, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID)));
		taskname=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME));
		taskid=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID));
		seekSN = cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_SN));
		//Log.d("mcadapter record"+cursor.getPosition(),taskid+" "+"sn"+seekSN+" "+taskname);
		if (cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))!=null){
			usergroup_str="["+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))+
					"@"+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_OWNERNICKNAME))+"] ";;
		}else{
			usergroup_str="";
		}
		deadline_str=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
		if (TimeData.TimeGap_YYMMDDHHSS(curTime_str, deadline_str)<0){;
    	    /*
            SpannableString spanStr_time=new SpannableString("(过期)");
 		    AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(TaskData.overduetextsize);
 		    ForegroundColorSpan span_color=new ForegroundColorSpan(TaskData.overduetextcolor);
 		    StyleSpan span_style = new StyleSpan(Typeface.ITALIC);
		    spanStr_time.setSpan(span_style, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 		    spanStr_time.setSpan(span_size, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 		    spanStr_time.setSpan(span_color, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 		    */
			holder.taskname.setText(usergroup_str+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME)));
 		    /*
 		    SpannableString spanStr_time=new SpannableString("(过期)");
   	        AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(22);
		    ForegroundColorSpan span_color=new ForegroundColorSpan(context.getResources().getColor(R.color.overduetextcolor));
		    StyleSpan span_style = new StyleSpan(Typeface.ITALIC);
		    spanStr_time.setSpan(span_style, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		    spanStr_time.setSpan(span_size, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		    spanStr_time.setSpan(span_color, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		    holder.taskname.append(spanStr_time);
		    */
			//holder.taskname.append(TaskTool.GetSpan(context,"(过期)",TaskData.overduetextsize,TaskData.overduetextcolor,Typeface.ITALIC));
			holder.taskname.append("\n"+"><"+" "+deadline_str);
		}else{
			holder.taskname.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME)));
			holder.taskname.append("\n"+"><"+" "+deadline_str);
		}

		TaskTool.replace(context,holder.taskname,"><",R.drawable.duetime_24px);


		setViewText(holder.taskcreatedtime, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME)));
		setViewText(holder.taskdeadline, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));

		setViewText(holder.taskprogress, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
		//setViewText(holder.taskprogress, cursor.getString(23));    
		//Log.d("mcAapter_taskrecord bind view", cursor.getString(cursor  
		//.getColumnIndex("record_recordtime")));

		Log.d(Tags+"|bind view|", "pos"+cursor.getPosition()+" seekSN "+seekSN);
		//String a = TaskData.TdDB.TABLE_NAME_TaskMain;
		// String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
		//Cursor c2 = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+
		//		        " where "+TaskData.TdDB.TASK_SN+"='"+seekSN+"'",null);
		final int pos = cursor.getPosition();
		holder.comment.setTag("h"+pos);
		holder.tasktree.setTag(pos);
		Cursor c2 = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord +
					" where " + TaskData.TdDB.TASK_SN + "='" + seekSN + "'", null);

			 if (c2 != null && c2.getCount() > 0) {
				 Log.d(Tags + "|bind view|", "c2 pos:" + c2.getPosition() + " count:" + c2.getCount());
				 mcAdapter_recorddetails adapt1 = new mcAdapter_recorddetails(context, c2, R.layout.lvitem_recorddetail);
				 holder.comment.setBackgroundColor(recorddetailcolor);
				 ;
				 holder.comment.setAdapter(adapt1);
				 lv_height = new LvHeight();
				 lv_height.setListViewHeightBasedOnChildren(holder.comment);
				 holder.tasktree.setChecked(true);
				 holder.comment.setVisibility(View.VISIBLE);

				 holder.tasktree.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

					 @Override
					 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						 // TODO Auto-generated method stub
						 if (isChecked) {
							 //LvHeight lv_height = new LvHeight();
							 //lv_height.setListViewHeightBasedOnChildren(holder.comment);
							 String tag0=buttonView.getTag().toString();

							 String tag1=holder.tasktree.getTag().toString();
							 String tag2=holder.comment.getTag().toString();
							 Log.d(Tags,"tree checked"+tag0+tag1+tag2);
							 view.findViewWithTag("h"+tag0).setVisibility(View.VISIBLE);
							 //holder.taskname.setVisibility(View.VISIBLE);
							// holder.comment.setVisibility(View.VISIBLE);

						 } else {
							 String tag0=buttonView.getTag().toString();
							 String tag1=holder.tasktree.getTag().toString();
							 String tag2=holder.comment.getTag().toString();
							 Log.d(Tags,"tree not checked"+tag0+tag1+tag2);
							 view.findViewWithTag("h"+tag0).setVisibility(View.GONE);
							 //holder.taskname.setVisibility(View.INVISIBLE);
							  /*
							 if (view.getTag().equals(tag)){
								 ((ViewHolder) view.getTag()).comment.setVisibility(View.VISIBLE);
							 }*/
							 //holder.comment.setVisibility(View.GONE);
							 //LvHeight lv_height = new LvHeight();
							 //lv_height.setListViewHeightBasedOnChildren(holder.comment);
							 // treestatus=false;
							 Log.d(Tags,"gone");
						 }
					 }

				 });


			 } else {
				 holder.comment.setAdapter(null);
				 holder.comment.setVisibility(View.GONE);

			 }


	}



	private Object getResources() {
		// TODO Auto-generated method stub
		return null;
	}



	private void setViewText(TextView name, String string) {
		// TODO Auto-generated method stub
		name.setText(string);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub

		holder= new ViewHolder();
		View view=inflater.inflate(R.layout.lv_taskrecord ,parent,false);
		holder.taskID = (TextView) view.findViewById(R.id.task_item1_taskID_tv);
		holder.taskname = (TextView) view.findViewById(R.id.task_item1_name_tv);
		//holder.importance=(TextView) view.findViewById(R.id.task_item4_importance_tv);
		holder.taskdeadline=(TextView) view.findViewById(R.id.task_item15_deadline_tv);
		holder.taskdeadline.setTextColor(Color.BLACK);
		holder.taskcreatedtime=(TextView) view.findViewById(R.id.task_item2_createdtime_tv);
		holder.taskprogress=(TextView) view.findViewById(R.id.task_item16_progress_tv);
		holder.comment=(ListView) view.findViewById(R.id.taskcomments_lv1);
		holder.tasktree=(CheckBox)view.findViewById(R.id.rb_tasktree);
		// holder.achieved = (TextView) view.findViewById(R.id.task_item11_achieved_tv);
		//holder.enjoyment = (TextView) view.findViewById(R.id.task_item12_enjoyment_tv);
		//holder.experience = (TextView) view.findViewById(R.id.task_item13_experience_tv);
		//holder.progress = (TextView) view.findViewById(R.id.task_item16_progress_tv);
		//holder.recordlist=(ListView)view.findViewById(R.id.taskcomments_lv);
		view.setTag(holder);

		return view;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//Log.d("mcAapter_taskrecord cursor count", String.valueOf(c.getCount()));
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
