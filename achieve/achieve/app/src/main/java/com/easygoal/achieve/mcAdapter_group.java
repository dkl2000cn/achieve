package com.easygoal.achieve;





import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;

public class mcAdapter_group extends CursorAdapter {

	
	    private Context context;
	    private Cursor c;
	    private Cursor c2;
	    private String user_str;
	    LvHeight lv_height;
	    int recorddetailcolor;
	    private int listviewItemlist=R.layout.lv_group;
	    ViewHolder holder= new ViewHolder();
	    LayoutInflater inflater;
	    String Tags="mcAdapter_group";
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
		        public TextView taskuser;  
		        public TextView taskcreatedtime;  
		        public ListView comment;
		        public CheckBox tasktree;
		    }    
	    
	public mcAdapter_group(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		this.context=context;
		recorddetailcolor=context.getResources().getColor(R.color.mTextColor2);;
		ViewHolder holder= new ViewHolder();
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
	
	Log.d(Tags,"create mcAapter_group"+context.toString());
	}


	
	@Override
	public void bindView(final View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		
	     holder = (ViewHolder) view.getTag();

		//Log.d("madapter bindview cursor",cursor.getString(cursor  
           //     .getColumnIndex("task_name"))+cursor.getString(cursor  
          //      .getColumnIndex("task_importance"))+cursor.getString(cursor  
           //     .getColumnIndex("record_recordtime"))+cursor.getString(cursor  
          //      .getColumnIndex("record_comments"))); 
		setViewText(holder.taskID, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID)));    
		setViewText(holder.taskname, "["+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USERGROUP))+"] "
						+cursor.getString(cursor.getColumnIndex("task_name")));
       // setViewText(holder.name, cursor.getString(cursor  
             //   .getColumnIndex("task_importance")));  
		//setViewText(holder.taskuser, cursor.getString(cursor.getColumnIndex("task_user")));
		if (TaskData.userinfolist_usergroup!=null&&TaskData.userinfolist_usergroup.size()>0) {
			setViewText(holder.taskuser, getNickName(TaskData.userinfolist_usergroup, cursor.getString(3)));
		}
		setViewText(holder.taskcreatedtime, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_CREATEDTIME)));
       // setViewText(holder.taskdeadline, "("+ cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_STARTEDTIME))+
        //         " ~ "+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DEADLINE))+")");
		setViewText(holder.taskdeadline, "(By:"+cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DEADLINE))+")");
        setViewText(holder.taskprogress, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));    
		//Log.d("mcAapter_taskrecord bind view", cursor.getString(cursor  
			              //.getColumnIndex("record_recordtime"))); 
		String seekSN = cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_SN));
		 Log.d(Tags+"|mcAapter_taskrecord bind view|","seekID "+seekSN);

	       //Cursor c2 = TaskData.db_TdDB.rawQuery("select * from "+b+" where "+b+"."+ToDoDB.RECORD_TASKID+"="+seekID,null);
	        c2 = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+TaskData.TdDB.TASK_SN+"="+"'"+seekSN+"'",null);
	   //final Cursor c2 = TaskData.db_TdDB.rawQuery("select * from "+b,new String[]{});
       // Log.d("mcAapter_taskrecord bind view",mca.toString());
        Log.d(Tags+"|mcAapter_taskrecord bind view|", "seekID "+seekSN+" find "+"c2 count"+String.valueOf(c2.getCount()));
        //setViewText(holder.comment,c2.getString(c2.getColumnIndex("record_comments"))); 
       
	    view.setBackgroundResource(R.color.mistyrose);

		final int pos = cursor.getPosition();
		holder.comment.setTag("h"+pos);
		holder.tasktree.setTag(pos);

        if (c2!=null&&c2.getCount()>0){
	    		mcAdapter_grouprecorddetails adapt1 = new mcAdapter_grouprecorddetails(context,c2,R.layout.lvitem_grouprecord);
       /* ListView lv = new ListView(context);
        Log.d("mcAapter_taskrecord bind view", lv.toString()+" adpt:"+adapt1.toString());
        LvHeight lv_height = new LvHeight();
        lv.setBackgroundColor(Color.GREEN);
        lv.setAdapter(adapt1);
        ((ViewGroup) view).addView(lv);
        lv_height.setListViewHeightBasedOnChildren(lv); */
        //holder.name.setText(cursor.getString(cursor.getColumnIndex("task_name")));
			/*
        Log.d(Tags+"|mcAapter_taskrecord bind view|","c2 postion"+cursor.getPosition()+"c2 count"+c2.getCount());
        int recorddetailcolor=context.getResources().getColor(R.color.mTextColor2);;
        holder.comment.setBackgroundColor(recorddetailcolor);;
        holder.comment.setAdapter(adapt1); 
       // holder.comment.setAdapter(adapt1);
        LvHeight lv_height = new LvHeight();
        lv_height.setListViewHeightBasedOnChildren(holder.comment); 
        Log.d(Tags+"|mcAapter_taskrecord bind view|","c1 postion"+cursor.getPosition()+"bind view done");
        holder.comment.setVisibility(View.GONE);
        Log.d(Tags+"|mcAapter_taskrecord bind view|","c1 postion"+cursor.getPosition()+"bind view done");
         */


			holder.comment.setBackgroundColor(recorddetailcolor);;
			holder.comment.setAdapter(adapt1);
			lv_height = new LvHeight();
			lv_height.setListViewHeightBasedOnChildren(holder.comment);
			holder.tasktree.setChecked(true);
			holder.comment.setVisibility(View.VISIBLE);
			//lv_height.setListViewHeightBasedOnChildren(holder.comment);


        		holder.tasktree.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener(){


						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked){
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
									  //LvHeight lv_height = new LvHeight();
									  //lv_height.setListViewHeightBasedOnChildren(holder.comment);
									 // treestatus=false;
							}
		                }
				});
        } else{
					holder.comment.setAdapter(null);
					holder.comment.setVisibility(View.GONE);
		}
	}

	private String getNickName(List<Map> list,String phoneNo){
		String nickname="";
		if (list!=null&&list.size()>0) {
			for (Map element : list) {
				if (element.get("phoneNo").equals(phoneNo)) {
					nickname = element.get("nickname").toString();
				}
			}
		}
		return  nickname;
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
        holder.tasktree=(CheckBox)view.findViewById(R.id.rb_tasktree);
        // holder.achieved = (TextView) view.findViewById(R.id.task_item11_achieved_tv);  
        //holder.enjoyment = (TextView) view.findViewById(R.id.task_item12_enjoyment_tv);  
        //holder.experience = (TextView) view.findViewById(R.id.task_item13_experience_tv);  
        //holder.progress = (TextView) view.findViewById(R.id.task_item16_progress_tv);  
        //holder.recordlist=(ListView)view.findViewById(R.id.taskcomments_lv);
        view.setTag(holder); 
        Log.d(Tags+"|mcAapter_taskrecord new view|", "new view done");
        return view;  
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d(Tags+"|mcAapter_taskrecord cursor count|", String.valueOf(c.getCount()));
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
