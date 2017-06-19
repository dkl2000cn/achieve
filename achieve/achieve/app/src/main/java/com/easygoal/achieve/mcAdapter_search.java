package com.easygoal.achieve;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class mcAdapter_search extends CursorAdapter {

	
	    private Context context;
	    private Cursor c;
	    private Cursor c2;
	    String seekSN;
	    ViewHolder holder;
	    mcAdapter_grouprecorddetails adapt1;
	    int recorddetailcolor;
	    LvHeight lv_height;
	    LayoutInflater inflater;
	    private int listviewItemlist=R.layout.lv_group;
	    String Tags="mcAdapter_search";
	   // ListAdapter lvBaseAdapter_taskcomments;
	    //lvBaseAdapter_taskcomments lvBaseAdapter_taskcomments; 
	   
		 final class ViewHolder { 
			 public TextView taskID; 
		        public TextView taskname;  
		        //public TextView taskimportance;  
		        public TextView taskdeadline;    
		        //public TextView taskachieved;  
		        //public TextView taskenjoyment;  
		        //public TextView taskexperience;  
		        public TextView taskprogress;
		        public TextView taskuser;  
		        public TextView taskcreatedtime;  
		        public ListView comment;
		    }    
	    
	public mcAdapter_search(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		this.context=context;
		recorddetailcolor=context.getResources().getColor(R.color.mTextColor2);;
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
	}


	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		
		holder = (ViewHolder) view.getTag();
		Log.d(Tags+"|bindview|",String.valueOf(cursor.getPosition())+"row"+String.valueOf(cursor.getCount()));
		//Log.d("mcAapter_taskrecord bindview cursor",cursor.toString()+"ok");
		//Log.d("madapter bindview cursor",cursor.getString(cursor  
           //     .getColumnIndex("task_name"))+cursor.getString(cursor  
          //      .getColumnIndex("task_importance"))+cursor.getString(cursor  
           //     .getColumnIndex("record_recordtime"))+cursor.getString(cursor  
          //      .getColumnIndex("record_comments"))); 
		setViewText(holder.taskID, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID)));    
		setViewText(holder.taskname, cursor.getString(cursor.getColumnIndex("task_name")));       
       // setViewText(holder.name, cursor.getString(cursor  
             //   .getColumnIndex("task_importance")));  
		//setViewText(holder.taskuser, cursor.getString(cursor.getColumnIndex("task_user")));        
		setViewText(holder.taskuser, cursor.getString(3));    
		setViewText(holder.taskcreatedtime, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME)));
       // setViewText(holder.taskdeadline, "("+ cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_STARTEDTIME))+
        //         " ~ "+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DEADLINE))+")");
		setViewText(holder.taskdeadline, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
        setViewText(holder.taskprogress, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));    
		//Log.d("mcAapter_taskrecord bind view", cursor.getString(cursor  
			              //.getColumnIndex("record_recordtime"))); 
		 seekSN = cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_SN));
		 Log.d(Tags+"|bindview|","seekID "+seekSN);

	       //Cursor c2 = TaskData.db_TdDB.rawQuery("select * from "+b+" where "+b+"."+ToDoDB.RECORD_TASKID+"="+seekID,null);
		c2 = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.TASK_SN+"="+"'"+seekSN+"'",null);
	   //final Cursor c2 = TaskData.db_TdDB.rawQuery("select * from "+b,new String[]{});
       // Log.d("mcAapter_taskrecord bind view",mca.toString());
        Log.d(Tags+"|bindview|", "seekID "+seekSN+" find "+"c2 count"+String.valueOf(c2.getCount()));
        //setViewText(holder.comment,c2.getString(c2.getColumnIndex("record_comments"))); 
       
        if (c2!=null&&c2.getCount()>0){
	        adapt1 = new mcAdapter_grouprecorddetails(context,c2,R.layout.lvitem_grouprecord);
       /* ListView lv = new ListView(context);
        Log.d("mcAapter_taskrecord bind view", lv.toString()+" adpt:"+adapt1.toString());
        LvHeight lv_height = new LvHeight();
        lv.setBackgroundColor(Color.GREEN);
        lv.setAdapter(adapt1);
        ((ViewGroup) view).addView(lv);
        lv_height.setListViewHeightBasedOnChildren(lv); */
        //holder.name.setText(cursor.getString(cursor.getColumnIndex("task_name")));
        Log.d(Tags+"|bindview|","c2 postion"+cursor.getPosition()+"c2 count"+c2.getCount());

        holder.comment.setBackgroundColor(recorddetailcolor);;
        holder.comment.setAdapter(adapt1);
        lv_height = new LvHeight();
        lv_height.setListViewHeightBasedOnChildren(holder.comment); 
        Log.d(Tags+"|bindview|","c1 postion"+cursor.getPosition()+"bind view done");
        
        /*for(int i=0; i<c2.getCount(); i++) {
            TextView tx = new TextView(context);
            tx.setText(c2.getColumnIndex("record_comments"));
            ((ViewGroup) view).addView(tx);
            // and then adding table row in tablelayout
        }*/
       // super.bindView(view, context, cursor); 
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
		//final View view = super.newView(context, cursor, parent);  
		
		holder= new ViewHolder();
        View view=inflater.inflate(listviewItemlist ,parent,false);
       
        holder.taskID = (TextView) view.findViewById(R.id.task_item1_taskID_tv); 
        holder.taskname = (TextView) view.findViewById(R.id.task_item1_name_tv);
        holder.taskuser = (TextView) view.findViewById(R.id.task_item_user_tv);
        //holder.importance=(TextView) view.findViewById(R.id.task_item4_importance_tv); 
        holder.taskdeadline=(TextView) view.findViewById(R.id.task_item15_deadline_tv); 
        holder.taskcreatedtime=(TextView) view.findViewById(R.id.task_item2_createdtime_tv); 
        holder.taskprogress=(TextView) view.findViewById(R.id.task_item16_progress_tv); 
        holder.comment=(ListView) view.findViewById(R.id.taskcomments_lv); 
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
