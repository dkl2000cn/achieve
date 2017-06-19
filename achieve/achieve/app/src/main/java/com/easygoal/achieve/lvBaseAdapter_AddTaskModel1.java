package com.easygoal.achieve;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class lvBaseAdapter_AddTaskModel1 extends BaseAdapter {

	  //用来接收传递过来的Context上下文对象
    private Context context;
  

    //构造函数
    public lvBaseAdapter_AddTaskModel1(Context context,ArrayList<RecordBean> ls)
    {   Log.d("Context", context.toString());
        this.context = context;
        TaskData.ls1=ls;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		 if(TaskData.ls1 != null)  
	            return TaskData.ls1.size();  
	        else  
	            return 0;  
	
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		 return TaskData.ls1 == null ? null : TaskData.ls1.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub  
		  //LinearLayout pageLayout = ((LinearLayout) inflater).inflate(context, LayoutId, null);  
		 // convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_find_listview, null);
		 Log.d("view", "show view ready");
		 final int pos=position;
		 if (convertView==null){
		 convertView =LayoutInflater.from(context).inflate(R.layout.lvitem_checklist, null);
		 }
		 //ViewHolder viewholder = new ViewHolder();
		 TextView tv_taskId=(TextView)convertView.findViewById(R.id.task_item1_taskID_tv);
		 final CheckBox cb_checklistItem=(CheckBox)convertView.findViewById(R.id.taskchecklist_cb);
		 final EditText et_checklistItem=(EditText)convertView.findViewById(R.id.taskchecklist_et);
		 tv_taskId.setText(String.valueOf(position+1));
		 cb_checklistItem.setText((TaskData.ls1.get(pos)).getRECORD_COMMENTS());
		 if (TaskData.ls1.get(pos).getRECORD_VALIDITY()=="true"){		
		     cb_checklistItem.setChecked(true);
		 }else{
			 cb_checklistItem.setChecked(false);
			 cb_checklistItem.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);  
		 }
		 cb_checklistItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
				     if(isChecked){
				    	 et_checklistItem.requestFocus(); 
				    	// taskreminder="yes";
				    	 //et_checklistItem.setText(et_checklistItem.getText().toString());
				    	 cb_checklistItem.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);; 
				    	 (TaskData.ls1.get(pos)).setRECORD_VALIDITY("true");
				     }else{
				    	 //et_checklistItem.setText(et_checklistItem.getText().toString());
				    	 cb_checklistItem.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);  
				    	 TaskData.ls1.get(pos).setRECORD_VALIDITY("false");
				     }
				}		  
			  }); 
		 /*
		 et_checklistItem.setOnClickListener(new OnClickListener(){
			 EditText et_item = new EditText(context);
			 //et_item.setText(et_checklistItem.getText().toString());;
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et_checklistItem.requestFocus(); 
				
				  new AlertDialog.Builder(context)
	 		  	 	.setTitle("请输入")
	 		  	 	.setIcon(android.R.drawable.ic_dialog_info)
	 		  	 	.setView(et_item)	  	 	
	 		  	 	.setPositiveButton("确认", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							et_checklistItem.setText(et_item.getText().toString());
						}
	 		  	 	})
	 		  	 .setNegativeButton("取消",  new DialogInterface.OnClickListener(){


	 					@Override
	 					public void onClick(DialogInterface arg0, int arg1) {
	 						// TODO Auto-generated method stub
	 						//dismiss();
	 					}
	 		  	 		
	 		  	 	  })
	 		  	 	.show();
	 		  	 	
			}
			 
		 });		 
	    
		 et_checklistItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
			    if (hasFocus){	
			         	
			       et_checklistItem.setText(et_checklistItem.getText().toString());
			    }else{
				   et_checklistItem.setText(et_checklistItem.getText().toString());
			    }
			 } 
			
		 });
		 */
		 //tv_checklistItem.setText(((RecordBean)mlist.get(position)).getRECORD_COMMENTS());
		 Log.d("tv_checklistItem", (TaskData.ls1.get(position)).getRECORD_COMMENTS());
		
		 /*
		 tv_checklistItem.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
			    @Override
			    public void onDoubleClick() {
			        //处理双击事件
			    	  new AlertDialog.Builder(context)
		 		  	 	.setTitle("请输入")
		 		  	 	.setIcon(android.R.drawable.ic_dialog_info)
		 		  	 	.setView(et_nickname)	  	 	
		 		  	 	.setPositiveButton("确认", new DialogInterface.OnClickListener(){
		 		  	 	}
			    }
			}));
		 */
		 
		 
		return convertView;
	}

	

	
}
