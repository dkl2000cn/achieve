package com.easygoal.achieve;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class mCursorRecyclerAdapter extends RecyclerView.Adapter<mCursorRecyclerAdapter.ViewHolder>  {

	protected Context context;
	protected Cursor c1;
    protected int mLayoutId;
    //protected List<T> mDatas;
    protected LayoutInflater mInflater;
    
    //private OnItemListener listener;  
   
    //private List<Integer> listCb = new ArrayList<>();//用于记录位置  
    public int t;
    public int selId;
    
    public mCursorRecyclerAdapter(Context context, int layoutId, Cursor c)
    {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        this.c1 = c;
        this.t=c1.getCount();
        //Log.d("recyclerview count", "Cursor:"+c.getCount()+" valuelist:"+Data.valuelist.size());
        /*
        c1.moveToFirst();
        do{
        RecordBean recbean = new RecordBean();
        recbean.setRECORD_TASKID_NO(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));
        //Log.d("valuelist ", ""+c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));
        recbean.setRECORD_DONE(Boolean.valueOf("false"));
        recbean.setRECORD_WEIGHT(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
        TaskData.valuelist.add(new RecordBean());
        
        }while(c1.moveToNext());
        Log.d("valuelist count",""+TaskData.valuelist.size());
        for (int i=0;i<TaskData.valuelist.size();i++){
        //Log.d("taskidno",TaskData.valuelist.get(i).getRECORD_TASKID_NO());
    	Log.d("rec comments",((RecordBean)TaskData.valuelist.get(i)).getRECORD_COMMENTS());
        }
        */
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        //ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);

        View view=mInflater.inflate(mLayoutId,parent,false);
        //view.setBackgroundColor(Color.RED);
        ViewHolder holder=new ViewHolder(view);
        return holder;
     
    }

    public void onBindViewHolder(final ViewHolder holder, int pos)
    {   //Cursor c1=this.cursor;
        //c1.moveToFirst();
    	//c1.move(pos);
        //Log.d("recylerview",""+c1.getPosition());
        //final String idno=c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO));
        //Log.d("recylerview","pos"+c1.getPosition()+" idno:"+idno);
        //Log.d("recylerview","t "+t);
    	
    	//Log.d("taskidno",TaskData.valuelist.get(pos).getRECORD_TASKID_NO());
    	//Log.d("rec comments",TaskData.valuelist.get(pos).getRECORD_COMMENTS());
    	holder.taskid_no.setText(TaskData.valuelist.get(pos).getRECORD_TASKID_NO());	
		holder.record_comments.setText(TaskData.valuelist.get(pos).getRECORD_COMMENTS());
		holder.record_comments.setChecked(Boolean.parseBoolean(TaskData.valuelist.get(pos).getRECORD_DONE()));		
        holder.record_progress.setText(TaskData.valuelist.get(pos).getRECORD_PROGRESS());    
    	holder.record_comments.setTag(new Integer(pos));
    	
        //holder.record_comments.setText(((RecordBean)mDatas.get(pos)).getRECORD_COMMENTS());
        //holder.taskid_no.setText(((RecordBean)mDatas.get(pos)).getRECORD_TASKID_NO());	
		//holder.record_comments.setChecked(Boolean.valueOf(((RecordBean)mDatas.get(pos)).getre));		
        //holder.record_progress.setText(((RecordBean)mDatas.get(pos)).getRECORD_PROGRESS());    
        final int i=pos;
        //final int changedPos=-1;
        holder.record_comments.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {  
          int selId=-1;
        	@Override  
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  
        		  int index =  (Integer) buttonView.getTag();//事件处理前要判断状态  
                  if (index == -2){ 
                      Log.d("tag stop",""+index);  
                	  return;  
                  }
            	if (isChecked){
            		/*
            		for (int j=0;j<t;j++){
                		Log.d("valuelist"+j, TaskData.valuelist.get(j).getRECORD_TASKID_NO().toString());
                		if (TaskData.valuelist.get(j).getRECORD_TASKID_NO()==idno){
                			selId=j;
                			Log.d("selId", ""+selId);
                		}
                	}
                	*/		
            	TaskData.valuelist.get(i).setRECORD_DONE("true"); 
            	TaskData.valuelist.get(i).setRECORD_CHANGED("true"); 
            	Log.d("selId", ""+i);
            	}else{
            	//TaskData.valuelist.get(selId).setRECORD_DONE(Boolean.parseBoolean("false")); 
                //TaskData.valuelist.get(selId).setRECORD_CHANGED(idno); 	
            	TaskData.valuelist.get(i).setRECORD_DONE("false"); 
                TaskData.valuelist.get(i).setRECORD_CHANGED("false"); 
            	}
            }  
        }); 
        
      
        /*
        holder.record_progress.setOnFocusChangeListener(new OnFocusChangeListener() {  
        	int selId=-1;

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
				for (int j=0;j<t;j++){
            		if (TaskData.valuelist.get(j).getRECORD_TASKID_NO()==idno){
            			selId=i;
            		}
            	}
				TaskData.valuelist.get(selId).setRECORD_PROGRESS((holder.record_progress.getText().toString())); 			
				TaskData.valuelist.get(selId).setRECORD_CHANGED(idno); 
			}  
        }); 
        */
    
    }

    // public abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount()
    {
        return c1.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       // public TextView item_tv;
    	public TextView taskid_no;
    	public TextView record_time;   
    	public TextView record_progress;
    	public CheckBox record_comments;		    
    	public TextView record_done;
    	
        public ViewHolder(View view){
            super(view);
           // item_tv = (TextView) view.findViewById(R.id.item_tv);
            taskid_no = (TextView) view.findViewById(R.id.tv_taskid_no); 
            record_progress=(TextView) view.findViewById(R.id.tv_recordprocess);
            record_comments = (CheckBox) view.findViewById(R.id.cb_recordcomments); 
          
            //record_comments.setTag(new Integer(-2));
        }
    }

	@Override
	public void onViewRecycled(ViewHolder holder) {
		// TODO Auto-generated method stub
		holder.record_comments.setTag(new Integer(-2));  
		super.onViewRecycled(holder);	
		
	}
 
    
}
