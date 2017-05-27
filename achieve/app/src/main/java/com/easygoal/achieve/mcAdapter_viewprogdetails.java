package com.easygoal.achieve;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.TextUtils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class mcAdapter_viewprogdetails extends CursorAdapter {

	private Context context;
	private Cursor c;
	private int layout_listitem;
	LayoutInflater inflater;
	ViewHolder holder;
	String Tags="mcAdapter_viewprogdetails";
	// ListAdapter lvBaseAdapter_taskcomments;
	//lvBaseAdapter_taskcomments lvBaseAdapter_taskcomments;

	final class ViewHolder {

		public TextView taskid_no;
		public TextView record_sn;
		public TextView record_time;
		public TextView record_progress;
		public CheckBox record_comments;
		public TextView record_done;
		public TextView record_details;

	}

	public mcAdapter_viewprogdetails(Context context, Cursor c,int layout) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		this.context=context;
		this.layout_listitem=layout;
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );


	}



	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub

       holder = (ViewHolder) view.getTag();

		Log.d(Tags+"|bindview|",cursor.getString(cursor
				.getColumnIndex(TaskData.TdDB.RECORD_TASKID))+
				cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO))+
				cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS)));
		if (holder.taskid_no!=null){
			holder.taskid_no.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID))+
					cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_SN)));
		}
		holder.record_sn.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_SN)));
		if (holder.record_comments!=null){
			holder.taskid_no.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));

			//holder.record_comments.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS))+"\n"+"(By:"+record_deadline+")");
			holder.record_comments.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS)));
			holder.record_comments.setChecked(Boolean.valueOf(cursor.getString((cursor.getColumnIndex(TaskData.TdDB.RECORD_DONE)))));
			holder.record_time.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TIME)));
			holder.record_progress.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_PROGRESS)));
			String r_details=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_DETAILS));
			holder.record_details.setVisibility(View.VISIBLE);
			if (r_details!=null&&!TextUtils.isEmpty(r_details)){
    		/*
    		String icon_item="><";
    		holder.record_details.setText(r_details);
      	    replace(context,holder.record_details,icon_item);
    	    */
				holder.record_details.setText(r_details);
				TaskTool.replaceTextFormat(context, holder.record_details,"><");
				holder.record_details.setVisibility(View.VISIBLE);
			}else{
				holder.record_details.setVisibility(View.GONE);
			}
			holder.taskid_no.setTextColor(Color.BLACK);
			holder.record_comments.setTextColor(Color.BLACK);
			holder.record_time.setTextColor(Color.BLACK);
			holder.record_progress.setTextColor(Color.BLACK);


		}
	}


	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		//final View view = super.newView(context, cursor, parent);  

		View view=inflater.inflate(layout_listitem ,parent,false);
		holder.taskid_no = (TextView) view.findViewById(R.id.tv_taskid_no);
		holder.record_sn = (TextView) view.findViewById(R.id.tv_recordsn);
		holder.record_time=(TextView) view.findViewById(R.id.tv_recordtime);
		holder.record_progress=(TextView) view.findViewById(R.id.tv_recordprocess);
		holder.record_comments = (CheckBox) view.findViewById(R.id.cb_recordcomments);
		holder.record_details=(TextView)view.findViewById(R.id.tv_recorddetails);
		view.setTag(holder);
		return view;
	}

	public void replace(Context context,TextView tv,String target){
		String temp=tv.getText().toString();

		//CharacterStyle span=null;
		Pattern p = Pattern.compile(target);
		Matcher m = p.matcher(temp);
		SpannableString spanString= new SpannableString(temp);
		Log.d(Tags, "target:"+target+"\n temp"+temp.toString());
		//List<Span> spans1 = new ArrayList<Span>();

		int pos_spanstart=0;
		int pos_spanend=0;

		while (m.find()) {

			pos_spanstart=m.start();
			pos_spanend=m.end();
			Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.dot);
			ImageSpan imgspan = new ImageSpan(context, b);
			spanString.setSpan(imgspan , pos_spanstart, pos_spanend,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		tv.setText(spanString);
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
