package com.easygoal.achieve;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.TextUtils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class mRecyclerAdapter extends RecyclerView.Adapter<mRecyclerAdapter.ViewHolder>  {

	protected Context mContext;
	protected Cursor c;
    protected int mLayoutId;
    protected LayoutInflater mInflater;
    
    //private OnItemListener listener;  
   
    //private List<Integer> listCb = new ArrayList<>();//���ڼ�¼λ��  
    public int t;
    public int selId;
    String Tags="mRecyclerAdapter";
    
    public mRecyclerAdapter(Context context,List ls, int layoutId)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
      
        TaskData.totalweight=0;
        TaskData.map_recordcommentnews=new HashMap();
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
    {   //Cursor c1=this.c;
         final int i=pos;
        //c1.moveToFirst();
    	//c1.move(pos);
        //Log.d("recylerview",""+c1.getPosition());
        //final String idno=c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO));
        //Log.d("recylerview","pos"+c1.getPosition()+" idno:"+idno);
        //Log.d("recylerview","t "+t);
    	
    	//Log.d("taskidno",TaskData.valuelist.get(pos).getRECORD_TASKID_NO());
        //Log.d("c1 count",""+c1.getCount());
    	holder.taskid_no.setText(TaskData.valuelist.get(i).getRECORD_TASKID_NO());	
		holder.record_comments.setText(TaskData.valuelist.get(i).getRECORD_TASKID_NO()+" "+TaskData.valuelist.get(i).getRECORD_COMMENTS());
		holder.record_comments.setChecked(Boolean.parseBoolean(TaskData.valuelist.get(i).getRECORD_DONE()));		
        //holder.record_progress.setText(TaskData.valuelist.get(pos).getRECORD_PROGRESS());    
    	holder.record_comments.setTag(new Integer(pos));
    	final String r_details=TaskData.valuelist.get(i).getRECORD_DETAILS();
    	if (r_details!=null&&!TextUtils.isEmpty(r_details)){
    		holder.record_details.setText(r_details);
    		TaskTool.replaceTextFormat(mContext,holder.record_details,TaskData.icon_header,TaskData.size_header,TaskData.color_header,0);
    	    holder.record_details.setVisibility(View.VISIBLE);
    	}else{
    		 holder.record_details.setVisibility(View.GONE);
    	}
    	String newflag=TaskData.valuelist.get(pos).getRECORD_CHANGED();
    	if (newflag!=null&&newflag.equals("true")){
    	    holder.record_newflag.setChecked(true);
    	}else{
    		holder.record_newflag.setChecked(false);
    	}
        //holder.taskid_no.setText(((RecordBean)mDatas.get(pos)).getRECORD_TASKID_NO());	
		//holder.record_comments.setChecked(Boolean.valueOf(((RecordBean)mDatas.get(pos)).getre));		
        //holder.record_progress.setText(((RecordBean)mDatas.get(pos)).getRECORD_PROGRESS());    
       
        //final int changedPos=-1;
        holder.record_addnews.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				TaskData.map_recordcommentnews.put("index",i);
				if (r_details!=null&&!TextUtils.isEmpty(r_details)){
			       TaskData.map_recordcommentnews.put("value",""+TaskData.valuelist.get(i).getRECORD_DETAILS());
				}else{
				   TaskData.map_recordcommentnews.put("value","");
				}
			    DialogFragment_AddRecordNews dialogfg_addrecordnews=new DialogFragment_AddRecordNews();
				TaskTool.showDialog(dialogfg_addrecordnews);
				
			}
        	
        });    
        
        holder.record_comments.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {  
          int selId=-1;
        	@Override  
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  
        		  int index =  (Integer) buttonView.getTag();//�¼�����ǰҪ�ж�״̬  
                  if (index == -2){ 
                      Log.d(Tags,"tag stop"+index);  
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
            	TaskData.valuelistflag=1;
            	Log.d(Tags,"setId get value "+i);
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
    {   if (TaskData.valuelist!=null){
           return TaskData.valuelist.size();
         }else{
        	 return 0;
         }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       // public TextView item_tv;
    	public TextView taskid_no;
    	public TextView record_time;   
    	public TextView record_progress;
    	public CheckBox record_comments;
    	public CheckBox record_newflag;
    	public TextView record_done;
    	public TextView record_details;
    	public Button record_addnews;
        public ViewHolder(View view){
            super(view);
           // item_tv = (TextView) view.findViewById(R.id.item_tv);
            taskid_no = (TextView) view.findViewById(R.id.tv_taskid_no); 
            record_progress=(TextView) view.findViewById(R.id.tv_recordprocess);
            record_comments = (CheckBox) view.findViewById(R.id.cb_recordcomments); 
            record_addnews = (Button) view.findViewById(R.id.btn_addnews); 
            record_newflag = (CheckBox) view.findViewById(R.id.cb_newflag); 
            record_details = (TextView) view.findViewById(R.id.tv_recorddetails); 
            record_comments.setTag(new Integer(-2));
        }
    }

    public void replace(Context context,TextView tv,String target){    
	    String temp=tv.getText().toString();    
	    SpannableStringBuilder spannable = new SpannableStringBuilder(temp);    
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
	public void onViewRecycled(ViewHolder holder) {
		// TODO Auto-generated method stub
		holder.record_comments.setTag(new Integer(-2));  
		super.onViewRecycled(holder);	
		
	}
 
    
}
