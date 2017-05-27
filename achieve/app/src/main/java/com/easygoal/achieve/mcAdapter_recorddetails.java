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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class mcAdapter_recorddetails extends CursorAdapter {

	    private Context context;
	    private Cursor c;
	    private int layout_listitem;
	    private String r_idno;
	    private String r_type;
	    private String r_weight;
	    private String record_deadline;
	    ViewHolder holder;
	    LayoutInflater inflater;
	    String r_details;
	    String Tags="mcAdapter_recorddetails";
	   // ListAdapter lvBaseAdapter_taskcomments;
	    //lvBaseAdapter_taskcomments lvBaseAdapter_taskcomments; 
	   
		 final class ViewHolder { 
				       
		    	public TextView taskid_no;
		    	public TextView record_time;   
		    	public TextView record_progress;
		    	public CheckBox record_comments;		    
		    	public TextView record_done;
			    //public ImageView record_type;
				public TextView record_type;
		    	public TextView record_details;
		    	
		    }    
	    
	public mcAdapter_recorddetails(Context context, Cursor c,int layout) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		this.context=context;
		this.layout_listitem=layout;
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
	//Log.d("create mcAapter_taskrecord",context.toString());
	}


	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		//Log.d("madapter bind view","ready");
		
		holder = (ViewHolder) view.getTag();
		//cursor=this.c;
		//Log.d("madapter bindview cursor",String.valueOf(cursor.getPosition())+"row"+String.valueOf(cursor.getCount()));
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
		
		Log.d(Tags+"|bindview|"+cursor.getPosition(),"TASKID:"+cursor.getString(cursor  
		                .getColumnIndex(TaskData.TdDB.RECORD_TASKID))+" RECORD_TASKID_NO:"+
		                cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO))+
		                		cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS))); 
		
		if (holder.record_comments!=null){
		  r_idno=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO));
		  r_type=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TYPE));
		  holder.taskid_no.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));	
		  record_deadline=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_DEADLINE));
		//holder.record_comments.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS))+"\n"+"(By:"+record_deadline+")");
		  	  
		  //holder.record_comments.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS))+"("+r_idno+")");
		  //holder.record_comments.setText(r_idno+" "+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS)));
		  r_weight=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT));
		  if (r_type!=null&&r_type.equals("true")){
			  holder.record_type.setVisibility(View.VISIBLE);
		  }else{
			  holder.record_type.setVisibility(View.INVISIBLE);
		  }
		  holder.record_comments.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS))+"(W:"+r_weight+"%)");
		  holder.record_comments.setChecked(Boolean.valueOf(cursor.getString((cursor.getColumnIndex(TaskData.TdDB.RECORD_DONE)))));
		  //String recordtime=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TIME));
		  //TaskTool.GetSpan(context,holder.record_time,recordtime);
		  holder.record_time.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TIME)));
          holder.record_progress.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_PROGRESS))); 
          
          holder.taskid_no.setTextColor(Color.BLACK);
          holder.record_comments.setTextColor(Color.BLACK);
          holder.record_time.setTextColor(Color.BLACK);
          holder.record_progress.setTextColor(Color.BLACK);
		}
		
        r_details=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_DETAILS));
        
        if (r_details!=null&&!TextUtils.isEmpty(r_details)){
        	holder.record_details.setText(r_details);
    		TaskTool.replaceTextFormat(context, holder.record_details,TaskData.icon_header,TaskData.size_header,TaskData.color_header,0);
    	    holder.record_details.setVisibility(View.VISIBLE);
    	}else{
    	    holder.record_details.setVisibility(View.GONE);
    	}
		
	}
	
	private void setViewText(TextView name, String string) {
		// TODO Auto-generated method stub
		name.setText(string);
	}

	public void replace(Context context,TextView tv,String target,ImageSpan span){    
	    String temp=tv.getText().toString();    
	    SpannableStringBuilder spannable = new SpannableStringBuilder(temp);    
	    //CharacterStyle span=null;    
	    Pattern p = Pattern.compile(target);    
	    Matcher m = p.matcher(temp);    
	    SpannableString spanString= new SpannableString(temp);
	   
	    int pos_spanstart=0;
		int pos_spanend=0;
		
	    while (m.find()) {    
	        //span = new ForegroundColorSpan(Color.RED);
	        //span = new ImageSpan(drawable,ImageSpan.XX);
	    	
	    	//list_imgspan.add(imgspan);
	    	pos_spanstart=m.start();
	    	pos_spanend=m.end();
	    	Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.dot);
    		ImageSpan imgspan = new ImageSpan(context, b);
	    	//ForegroundColorSpan colorspan = new ForegroundColorSpan(Color.RED);
	    	spanString.setSpan(imgspan , pos_spanstart, pos_spanend,  
	                          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    	//i++;
	    	//SpannableString subspanString = spanString.subSequence(pos_textstart,  pos_textend);
	    	//str_start=spanString.subSequence(0, start)
	    	
	    } 
	    tv.setText(spanString);   
	}  
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		//final View view = super.newView(context, cursor, parent);  
		
		holder= new ViewHolder();
        View view=inflater.inflate(layout_listitem ,parent,false);
        //Log.d("new view", view.toString());
        holder.taskid_no = (TextView) view.findViewById(R.id.tv_taskid_no); 
        holder.record_time=(TextView) view.findViewById(R.id.tv_recordtime); 
        holder.record_progress=(TextView) view.findViewById(R.id.tv_recordprocess);
        holder.record_comments = (CheckBox) view.findViewById(R.id.cb_recordcomments); 
        holder.record_details=(TextView)view.findViewById(R.id.tv_recorddetails);
		//holder.record_type=(ImageView)view.findViewById(R.id.iv_recordtype);
		holder.record_type=(TextView)view.findViewById(R.id.tv_recordtype);
        //Log.d("holder", holder.taskid_no.toString());
        //Log.d("holder", holder.record_comments.toString());
        //Log.d("holder", holder.record_progress.toString());
        //Log.d("holder", holder.record_time.toString());
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
