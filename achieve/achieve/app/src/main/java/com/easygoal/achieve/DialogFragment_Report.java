package com.easygoal.achieve;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;

public class DialogFragment_Report extends DialogFragment {

	   SharedPreferences spp;
       Editor sppEditor;
       int model ;
	   int dataperiod;
	   int preweight;
		    
	public DialogFragment_Report() {
		// TODO Auto-generated constructor stub
	  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.leftmenu_report, container);
	   
	    final RadioButton rb_period1=(RadioButton)v.findViewById(R.id.rb_period1);
	    final RadioButton rb_period2=(RadioButton)v.findViewById(R.id.rb_period2);
	    final RadioButton rb_period3=(RadioButton)v.findViewById(R.id.rb_period3);
		Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);  
		 
			
	    btn_closewin.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
	    Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);  
		 
	    btn_cancel.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        }); 
	   
	    switch (dataperiod){
    	    case 1:	rb_period1.setChecked(true);
    	            rb_period2.setChecked(false);
    	            rb_period3.setChecked(false);
    	    	   break;
    	    case 2:
    	    		rb_period1.setChecked(false);
    	    		rb_period2.setChecked(true);
    	    		rb_period3.setChecked(false);
    	    	   break;
    	    case 3:
    	    		rb_period1.setChecked(false);
    	    		rb_period2.setChecked(false);
    	    		rb_period3.setChecked(true);
    	    	   break;
        }
		
    	
    	OnCheckedChangeListener rblisterner_period = new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
					
					if (buttonView.getText().toString().equals("当日日报")){
						dataperiod=1;
					}
					if (buttonView.getText().toString().equals("本周周报")){
						dataperiod=2;
					}
					if (buttonView.getText().toString().equals("本月月报")){
						dataperiod=3;
					}
				}
				
			}	
    		
    	};
    	
    	
    	rb_period1.setOnCheckedChangeListener(rblisterner_period);
    	rb_period2.setOnCheckedChangeListener(rblisterner_period);
    	rb_period3.setOnCheckedChangeListener(rblisterner_period);
    	
    	  
	    Button btn_confirm = (Button) v.findViewById(R.id.btn_confirm);  
		 
	    btn_confirm.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框 
	              
	                 ExportUtils.exportToReport(getActivity(), dataperiod);
	               
	            }  
	        });  
 	
	  Button btn_sendmail = (Button) v.findViewById(R.id.btn_sendmail);  
		 
	    btn_confirm.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框 
	            	  // 关闭对话框  
	            	ExportUtils.exportToReportBymail(getActivity(), dataperiod);
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
