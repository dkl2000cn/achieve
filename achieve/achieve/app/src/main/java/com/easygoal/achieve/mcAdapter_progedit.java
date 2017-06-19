package com.easygoal.achieve;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class mcAdapter_progedit extends CursorAdapter {

	    private Context context;
	    private Cursor c;
	    private int layout_listitem;
	    LayoutInflater inflater;
	    ViewHolder holder;
	    String Tags="mcAdapter_progedit";
	   // ListAdapter lvBaseAdapter_taskcomments;
	    //lvBaseAdapter_taskcomments lvBaseAdapter_taskcomments; 
	   
		 final class ViewHolder { 
				       
		    	public TextView taskid_no;
		    	public EditText record_comments; 
		    	public EditText record_weight;  
		    	public EditText record_deadline;  
		    	public Button delItem;
		    	
		    	
		    }    
	    
	public mcAdapter_progedit(Context context, Cursor c,int layout) {
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
		
		Log.d(Tags+"|bindview|",cursor.getString(cursor  
		                .getColumnIndex(TaskData.TdDB.RECORD_TASKID))+
		                cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO))+
		                		cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS))); 
		if (holder.taskid_no!=null){
		holder.taskid_no.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));	
		holder.record_comments.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS)));
		holder.record_deadline.setText(cursor.getString((cursor.getColumnIndex(TaskData.TdDB.RECORD_DEADLINE))));		
        holder.record_weight.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));    
		final  int p=cursor.getPosition();
        holder.delItem.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			   TaskData.subItemlist.remove(p);	
			}		
			
		});
		}
		
		
		
	}

	
	private void setViewText(TextView name, String string) {
		// TODO Auto-generated method stub
		name.setText(string);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		//final View view = super.newView(context, cursor, parent);  
		
		holder= new ViewHolder();
        View view=inflater.inflate(layout_listitem ,parent,false);
        holder.taskid_no = (TextView) view.findViewById(R.id.tv_taskid_no); 
        holder.record_deadline = (EditText) view.findViewById(R.id.et_recorddeadline); 
        holder.record_weight=(EditText) view.findViewById(R.id.et_recordweight);
        holder.record_comments = (EditText) view.findViewById(R.id.et_recordcomments); 
        holder.delItem=(Button)view.findViewById(R.id.btn_deleteItem);
        view.setTag(holder);
        return view;  
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d(Tags,"cursor count"+ String.valueOf(c.getCount()));
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
