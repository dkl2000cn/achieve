package com.easygoal.achieve;

import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class mRecyAdapter_taskedit extends RecyclerView.Adapter<mRecyAdapter_taskedit.ViewHolder>  {

	protected Context mContext;
	protected Cursor c1;
    protected int mLayoutId;
    protected List ls;
    protected LayoutInflater mInflater;
    
    //private OnItemListener listener;  
    
    //private List<Integer> listCb = new ArrayList<>();//用于记录位置  
    public int t;
    public int selId;
    private String task_deadline; 
    private double temp_totalweight;
    Cursor c;
    int p=-1;
    
    public mRecyAdapter_taskedit(Context context, int layoutId, List ls)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        this.ls =ls;
        this.t=ls.size();
        TaskData.totalweight=0;
        c1=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                TaskData.TdDB.TASK_ID+"=? ",
		          new String[]{TaskData.selTaskID});
        c1.moveToFirst();
        task_deadline=c1.getString(c1.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
        //Log.d("recyclerview count", "Cursor:"+c.getCount()+" valuelist:"+Data.valuelist.size());
        /*
        c1.moveToFirst();
        do{
        RecordBean recbean = new RecordBean();
        recbean.setRECORD_TASKID_NO(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));
        //Log.d("valuelist ", ""+c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));
        recbean.setRECORD_DONE(Boolean.valueOf("true"));
        recbean.setRECORD_WEIGHT(c1.getString(c1.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
        TaskData.subItemlist.add(new RecordBean());
        
        }while(c1.moveToNext());
        Log.d("valuelist count",""+TaskData.subItemlist.size());
        for (int i=0;i<TaskData.subItemlist.size();i++){
        //Log.d("taskidno",TaskData.subItemlist.get(i).getRECORD_TASKID_NO());
    	Log.d("rec comments",((RecordBean)TaskData.subItemlist.get(i)).getRECORD_COMMENTS());
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
    	final int i=pos;

    	//Log.d("taskidno",TaskData.subItemlist.get(pos).getRECORD_TASKID_NO());
    	//Log.d("rec comments",TaskData.subItemlist.get(pos).getRECORD_COMMENTS());
    	holder.taskid_no.setText(""+(pos+1));
    	//Log.d("mRecyAdapter_taskedit onbindviewholder "+pos,"view"+holder.itemView.getTag());;
    	Log.d("mRecyAdapter_taskedit onbindviewholder "+pos,"comments"+holder.record_comments.getTag());;
    	Log.d("mRecyAdapter_taskedit onbindviewholder "+pos,"deadline"+holder.record_deadline.getTag());
    	Log.d("mRecyAdapter_taskedit onbindviewholder "+pos,"weight"+holder.record_weight.getTag());
    	if (holder.record_comments.getTag().equals(pos)){
		   Log.d("mRecyAdapter_taskedit onbindviewholder "+pos,"same comments"+holder.record_comments.toString());
		}else{
		   holder.record_comments.setText(TaskData.subItemlist.get(pos).getRECORD_COMMENTS());		
		   Log.d("mRecyAdapter_taskedit onbindviewholder "+pos,"diff comments"+holder.record_comments.getText().toString()); 
		   holder.record_comments.setTag(new Integer(pos));
		}
    	if (holder.record_deadline.getTag().equals(pos)){
    		Log.d("mRecyAdapter_taskedit onbindviewholder "+pos,"same deadline"+holder.record_comments.toString());
    	}else{	
		 holder.record_deadline.setText(TaskData.subItemlist.get(pos).getRECORD_DEADLINE());		
		 holder.record_deadline.setTag(new Integer(pos));
		 Log.d("mRecyAdapter_taskedit onbindviewholder "+pos,"diff deadline"+holder.record_deadline.getTag());
		}
    	if (holder.record_weight.getTag().equals(pos)){
    		Log.d("mRecyAdapter_taskedit onbindviewholder "+pos,"same weight"+holder.record_weight.toString());
    	}else{
		 holder.record_weight.setText(TaskData.subItemlist.get(pos).getRECORD_WEIGHT());    
		 holder.record_weight.setTag(new Integer(pos));
		 Log.d("mRecyAdapter_taskedit onbindviewholder "+pos,"diff weight"+holder.record_weight.getTag());
		}
		
		//holder.record_comments.setText(((RecordBean)mDatas.get(pos)).getRECORD_COMMENTS());
        //holder.taskid_no.setText(((RecordBean)mDatas.get(pos)).getRECORD_TASKID_NO());	
		//holder.record_comments.setChecked(Boolean.valueOf(((RecordBean)mDatas.get(pos)).getre));		
        //holder.record_progress.setText(((RecordBean)mDatas.get(pos)).getRECORD_PROGRESS());    
        
        //final int changedPos=-1;
        
        TaskData.totalweight=0;
        temp_totalweight=0;
        holder.record_deadline.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder.record_deadline.setBackgroundColor(Color.YELLOW);
				DialogFragment_TimeInput dg_time=new DialogFragment_TimeInput(holder.record_deadline,0);
    	    	TaskTool.showDialog(dg_time);
			}
        	
        });
      
        TextWatcher tw_comments = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable et) {
				// TODO Auto-generated method stub
				int index =  (Integer) holder.record_comments.getTag();//事件处理前要判断状态  
                if (index == -2){ 
                	Log.d("mRecyAdapter_taskedit onbindviewholder "+i,"edit tag:-2"+holder.record_comments.getText().toString());  
              	  return;  
                }else{
                	if (!TextUtils.isEmpty(et.toString().trim())){
				       TaskData.subItemlist.get(index).setRECORD_COMMENTS(holder.record_comments.getText().toString());
				       Log.d("mRecyAdapter_taskedit onbindviewholder "+i,"edit tag:"+index+" "+holder.record_comments.getText().toString()); 
                	}else{
			      //Toast.makeText(context, "内容不能为空", Toast.LENGTH_SHORT).show(); 
			         return; 
			        }	
			    }  
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
        	
        	
        	
        };
        
        holder.record_comments.addTextChangedListener(tw_comments);
       
    
        TextWatcher tw_deadline = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable et) {
				// TODO Auto-generated method stub
				int index =  (Integer) holder.record_deadline.getTag();//事件处理前要判断状态  
                if (index == -2){ 
                	Log.d("mRecyAdapter_taskedit onbindviewholder "+i,"edit tag:-2"+holder.record_deadline.getText().toString());  
                	
              	  return;  
                }
                if (!TextUtils.isEmpty(et.toString().trim())){
                  if (TimeData.CompareTime_YY(et.toString().trim(),task_deadline)>0){	
  				    TaskData.subItemlist.get(i).setRECORD_DEADLINE(holder.record_deadline.getText().toString());
  				  Log.d("mRecyAdapter_taskedit onbindviewholder "+i,"edit tag:"+index+" "+holder.record_deadline.getText().toString()); 
                  }else{
                	  holder.record_deadline.setText("");
                	  holder.record_deadline.setBackgroundColor(Color.YELLOW); 
                	  Toast.makeText(mContext, "子目标不能超过任务期限", Toast.LENGTH_SHORT).show();
                  }	  
                }else{
  			      //Toast.makeText(context, "内容不能为空", Toast.LENGTH_SHORT).show(); 
  			    }	
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
        	
        	
        	
        };
        
        holder.record_deadline.addTextChangedListener(tw_deadline);
       
        TextWatcher tw_weight = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable et) {
				// TODO Auto-generated method stub
				int index =  (Integer) holder.record_weight.getTag();//事件处理前要判断状态  
                if (index == -2){ 
                    Log.d("tag stop",""+index);  
              	  return;  
                }
                String input_weight = et.toString().trim();
                double new_weight=0;
                double Max = 100-temp_totalweight;
                Log.d("weight max", ""+Max);
                if (TaskTool.isNumeric(input_weight)){
                	 double d_weight = Double.parseDouble(input_weight);
                 
                  if (d_weight<=Max){
                	  new_weight=d_weight ;
  				      TaskData.subItemlist.get(i).setRECORD_WEIGHT(holder.record_weight.getText().toString());
  				      Log.d("et value",""+ TaskData.subItemlist.get(i).getRECORD_WEIGHT()); 
                  }else{
                	  //Toast.makeText(context, "数值须<="+Max, Toast.LENGTH_SHORT).show(); 
                	  new_weight=0;
                	  holder.record_weight.setText("");
                	  TaskData.subItemlist.get(i).setRECORD_WEIGHT("0");
                	  TaskData.tv_totalweight.setText("超出范围");
                  }
                }else{
                	new_weight=0;
                    TaskData.subItemlist.get(i).setRECORD_WEIGHT("0");
  			        //Toast.makeText(context, "内容不能为空", Toast.LENGTH_SHORT).show(); 
  			    }	
			    double sum_openweight = 0;
			    double sum_closeweight=0;
			    double temp_openweight=0;
				for (int j=0;j<TaskData.subItemlist.size();j++){
					if (!TextUtils.isEmpty(TaskData.subItemlist.get(j).getRECORD_WEIGHT().trim())){
						    sum_openweight=sum_openweight+Double.parseDouble(TaskData.subItemlist.get(j).getRECORD_WEIGHT());
						if (j!=i){
					    	temp_openweight=temp_openweight+Double.parseDouble(TaskData.subItemlist.get(j).getRECORD_WEIGHT());
				        }else{
				        	temp_openweight=temp_openweight+0;
				        }
				     }
				     else{
						    sum_openweight=sum_openweight+0;
					}
			    }
			      Log.d("subItemlist",""+sum_openweight); 
				c=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+
				                  TaskData.TdDB.RECORD_TASKID+"=? and "+
						          TaskData.TdDB.RECORD_DONE+"=?",
						          new String[]{TaskData.selTaskID,"true"});
			    c.moveToFirst();
			   
			    if (c!=null&&c.getCount()>0){
			    	while (c.moveToNext()){
			    	       sum_closeweight=sum_closeweight+Double.parseDouble(c.getString(c.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
			    	}
			    }
				TaskData.totalweight=sum_openweight+sum_closeweight;
				temp_totalweight=temp_openweight+sum_closeweight;
				   if (TaskData.totalweight>100){	
		                //tv_totalweight.setTextColor(Color.RED);
		               //total_weight=sum_weight;
					   TaskData.tv_totalweight.setText("合计权重(%):"+TaskData.totalweight);
					   TaskData.tv_totalweight.setTextColor(Color.RED);
						//Toast.makeText(context, " total: "+" 当前总权重大于100", Toast.LENGTH_SHORT).show(); 
			   	   }else{
			   		 TaskData.tv_totalweight.setText("合计权重(%):"+TaskData.totalweight);
			   	   }
				  //TaskData.adapter_progedit.notifyDataSetChanged();   
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
        	
        	
        	
        };
        
        holder.record_weight.addTextChangedListener(tw_weight);
        
        holder.delItem.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			   double curWeight=GetTotalWeight(i)-Double.parseDouble(TaskData.subItemlist.get(i).getRECORD_WEIGHT());
			   TaskData.tv_totalweight.setText("合计权重(%):"+curWeight);
			   TaskData.subdellist.add(TaskData.subItemlist.get(i).getRECORD_SN());	
			   TaskData.subItemlist.remove(i);
			   TaskData.adapter_progedit.notifyDataSetChanged();
			  
			   
			}		
			
		});
        
        
        /*
        holder.record_progress.setOnFocusChangeListener(new OnFocusChangeListener() {  
        	int selId=-1;

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
				for (int j=0;j<t;j++){
            		if (TaskData.subItemlist.get(j).getRECORD_TASKID_NO()==idno){
            			selId=i;
            		}
            	}
				TaskData.subItemlist.get(selId).setRECORD_PROGRESS((holder.record_progress.getText().toString())); 			
				TaskData.subItemlist.get(selId).setRECORD_CHANGED(idno); 
			}  
        }); 
        */
    
    }

    private double GetTotalWeight(int pos) {
		// TODO Auto-generated method stub
    	double sum_openweight = 0;
	    double sum_closeweight=0;
	    double temp_openweight=0;
		for (int j=0;j<TaskData.subItemlist.size();j++){
			if (!TextUtils.isEmpty(TaskData.subItemlist.get(j).getRECORD_WEIGHT().trim())){
				sum_openweight=sum_openweight+Double.parseDouble(TaskData.subItemlist.get(j).getRECORD_WEIGHT());
				if (j!=pos){
			    	temp_openweight=temp_openweight+Double.parseDouble(TaskData.subItemlist.get(j).getRECORD_WEIGHT());
		        }else{
		        	temp_openweight=temp_openweight+0;
		        }
		     }
		     else{
				 sum_openweight=sum_openweight+0;
			}
	    }
	     Log.d("sum_openweight",""+sum_openweight+""+temp_openweight); 
	    if (TaskData.selTaskSN!=null){ 
		   Cursor c_d=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                TaskData.TdDB.TASK_SN+"=?  ",
		          new String[]{TaskData.selTaskSN});
            c_d.moveToFirst();
          if (c_d!=null&&c_d.getCount()>0){
	          sum_closeweight=Double.parseDouble(c_d.getString(c_d.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
	       Log.d("sum_closeweight",""+sum_closeweight); 
            }
	    } else{
	       Log.d("task seltasksn",""+TaskData.selTaskSN); 	
	    }
	    return  sum_openweight+sum_closeweight;
	}

	// public abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount()
    {
        return ls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       // public TextView item_tv;
    	public TextView taskid_no;
    	public EditText record_comments; 
    	public EditText record_weight;  
    	public EditText record_deadline;  
    	public Button delItem;
        public ViewHolder(View view){
            super(view);
           // item_tv = (TextView) view.findViewById(R.id.item_tv);
            taskid_no = (TextView) view.findViewById(R.id.tv_taskid_no); 
            record_deadline = (EditText) view.findViewById(R.id.et_recorddeadline); 
            record_weight=(EditText) view.findViewById(R.id.et_recordweight);
            record_comments = (EditText) view.findViewById(R.id.et_recordcomments); 
            delItem=(Button)view.findViewById(R.id.btn_deleteItem);    
            record_comments.setTag(new Integer(-2));
            record_weight.setTag(new Integer(-2));
            record_deadline.setTag(new Integer(-2));
        }
    }

	@Override
	public void onViewRecycled(ViewHolder holder) {
		// TODO Auto-generated method stub
		holder.record_comments.setTag(new Integer(-2)); 
		holder.record_deadline.setTag(new Integer(-2)); 
		holder.record_weight.setTag(new Integer(-2));
		//holder.record_comments.setText("");; 
		//holder.record_deadline.setText("");
		//holder.record_weight.setText("0");
		super.onViewRecycled(holder);	
		
	}

	@Override
	public int getItemViewType(int position) {
		//return ((List) ls.get(position)).get;
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
 
	
}
