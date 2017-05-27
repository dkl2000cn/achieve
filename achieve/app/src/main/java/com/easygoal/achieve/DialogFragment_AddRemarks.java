package com.easygoal.achieve;

import org.apache.http.util.TextUtils;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

public class DialogFragment_AddRemarks extends DialogFragment {
   
	String taskremarks;
	Cursor c;
	int editflag;
	final String Tags="DialogFragment_AddRemarks";
	
	public DialogFragment_AddRemarks() {
		// TODO Auto-generated constructor stub
	  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.dialogfg_addremarks, container);
	    Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);  
	    Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);  
	    Button btn_addremark= (Button) v.findViewById(R.id.btn_addremark);  
	    Button btn_editremark= (Button) v.findViewById(R.id.btn_editremark);  
	    editflag=0;
	    
	    btn_closewin.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
	    
		 
	    btn_cancel.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        }); 
	    Button btn_confirm = (Button) v.findViewById(R.id.btn_confirm);  
	    final MultiAutoCompleteTextView mact_remarks = (MultiAutoCompleteTextView)v.findViewById(R.id.mactv_remarks);
		//final TextView tv_remarks = (TextView)v.findViewById(R.id.tv_remarks);
		final EditText et_remarks = (EditText)v.findViewById(R.id.et_remarks);
		//final EditText title = (EditText)v.findViewById(R.id.et_title);
       // mactv.setBackgroundResource(R.drawable.shape_line_rectangle);	
		if (TaskData.selTaskID!=null){ 
			   
	          c = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+ToDoDB.TASK_ID+"="+TaskData.selTaskID,null);
		    		//query(TaskData.TdDB.TABLE_NAME_TaskMain, , null, null, null, null, null);
		    Log.d(Tags,String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		    if (c!=null&&c.getCount()>0){
		      c.moveToFirst();
		      TaskData.selTaskSN=c.getString(c.getColumnIndex(TaskData.TdDB.TASK_SN));
			     taskremarks = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_REMARKS));
			    
		    }
		   } 
		
		
		
		//final int i=(Integer) TaskData.map_recordcommentnews.get("index");
		if (taskremarks!=null){
		  et_remarks.setText(taskremarks.trim());
		  et_remarks.setEnabled(false);
		}  
	    mact_remarks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	    	@Override
	    	public void onFocusChange(View v, boolean hasFocus) {
	    	if (hasFocus) {
	    		//InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);  
	    		//imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
	    	   }
	    	 //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	    	 }
	    });
	    
	    btn_editremark.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et_remarks.setEnabled(true);
				et_remarks.setClickable(true);
				/*
				editflag=1;
				et_remarks.setText(tv_remarks.getText().toString());
				et_remarks.setVisibility(View.VISIBLE);
				*/
			}  
	
	    });
	    
	    et_remarks.setOnFocusChangeListener(new OnFocusChangeListener() { 

        @Override 
          public void onFocusChange(View v, boolean hasFocus) { 
           if (et_remarks.hasFocus() == false) { 
        	   et_remarks.setEnabled(false);;// 按钮变search 
            } 

           } 
        });
	    
  
	    btn_addremark.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框
	               if (!TextUtils.isEmpty(mact_remarks.getText().toString().trim())){
	            	 if (!TextUtils.isEmpty(et_remarks.getText().toString().trim())){ 
	            	   et_remarks.setText(et_remarks.getText().toString().trim()); 
	            	   et_remarks.append("\n"+mact_remarks.getText().toString()+"("+TaskTool.getCurTime()+")");
	            	 }else{
	            	   et_remarks.setText(mact_remarks.getText().toString()+"("+TaskTool.getCurTime()+")"); 
	            	 } 
	            	   mact_remarks.setText("");
	               }else{	   
	            	 Toast.makeText(getActivity(), "评论内容为空白", Toast.LENGTH_SHORT).show(); 
	               }
	            	  //dismiss();
	            	  
	            	/*
	            	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
		     		//设置文本格式  
		     		emailIntent.setType("application/octet-stream");
		     		//设置对方邮件地址  
		     		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,mailaddress.getText().toString());  
		     		//设置标题内容  
		     		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,title.getText().toString());  
		     		//设置邮件文本内容  
		     		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,body.getText().toString());  
		     		startActivity(Intent.createChooser(emailIntent,"Choose Email Client"));  
		     		Toast.makeText(getActivity(), "send out!", Toast.LENGTH_SHORT).show(); 
	              */
	               	
               }  
	        });  
	    
	    btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 ContentValues cv = new ContentValues();
           	  cv.put(TaskData.TdDB.TASK_REMARKS, et_remarks.getText().toString());
           	  String where=TaskData.TdDB.TASK_SN+ " = ?";
           	  String[] whereas={TaskData.selTaskSN};
           	  long a=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain, cv, where, whereas);
           	  if (a==1){
           	    Toast.makeText(getActivity(), "评论添加成功", Toast.LENGTH_SHORT).show();
           	    TaskData.adapterUpdate();
           	    dismiss();
           	  }else{
           		Toast.makeText(getActivity(), "评论添加失败", Toast.LENGTH_SHORT).show();  
           	  }
			} 
			
	    });	
 		return v;	
    }
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
		getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	}  
}
