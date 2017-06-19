package com.easygoal.achieve;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class DialogFragment_AlarmSetting extends DialogFragment {

			int freq= 0;
			int advance=0;
			int interval=0;
		final String Tags="DialogFragment_AlarmSetting";
		
	public DialogFragment_AlarmSetting() {
		// TODO Auto-generated constructor stub
	  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.leftmenu_alarmsetting, container);
	    final CheckBox cb1=(CheckBox)v.findViewById(R.id.cb1);
	    final CheckBox cb2=(CheckBox)v.findViewById(R.id.cb2);
	    final CheckBox cb3=(CheckBox)v.findViewById(R.id.cb3);
	    final CheckBox cb4=(CheckBox)v.findViewById(R.id.cb4);
	    
	    final RadioButton rb_freq1=(RadioButton)v.findViewById(R.id.rb_freq1);
	    final RadioButton rb_freq2=(RadioButton)v.findViewById(R.id.rb_freq2);
	    final EditText et_advance=(EditText)v.findViewById(R.id.et_advance);
	    final EditText et_alarminterval=(EditText)v.findViewById(R.id.et_alarminterval);
		
	    Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);  
		
	    SharedPreferences spp=getActivity().getSharedPreferences("userinfo",Context.MODE_PRIVATE);
	    Editor sppEditor = spp.edit();
		if (spp!=null){
			
			   TaskData.alarmclock = spp.getBoolean("alarmclock", true);
			   TaskData.vibration = spp.getBoolean("vibration", true);
			   TaskData.alarmring= spp.getBoolean("alarmring", true);
			   TaskData.notification= spp.getBoolean("notification",  true);
			   TaskData.alarmfreq=spp.getInt("alarmfreq", 0); 
			   TaskData.alarminterval=spp.getInt("alarminterval", 5); 
			   TaskData.alarmadvance=spp.getInt("alarmadvance", 0); 
			   
	    }else{      
	       sppEditor.putBoolean("alarmclock",true);
		   sppEditor.putBoolean("vibration",true);
		   sppEditor.putBoolean("alarmring",true);
		   sppEditor.putBoolean("notification", true);
		   sppEditor.putInt("alarmfreq", 0);
		   sppEditor.putInt("alarminterval", 5);
		   sppEditor.putInt("alarmadvance", 0); 
		   sppEditor.commit();
		   TaskData.alarmclock = spp.getBoolean("alarmclock", true);
		   TaskData.vibration = spp.getBoolean("vibration", true);
		   TaskData.alarmring= spp.getBoolean("alarmring", true);
		   TaskData.notification= spp.getBoolean("notification",true);
		   TaskData.alarmfreq=spp.getInt("alarmfreq", 0); 
		   TaskData.alarminterval=spp.getInt("alarminterval", 5); 
		   TaskData.alarmadvance=spp.getInt("alarmadvance", 0); 
	    }			
			    cb1.setChecked(TaskData.alarmclock);
				cb2.setChecked(TaskData.vibration);
				cb3.setChecked(TaskData.alarmring);
				cb4.setChecked(TaskData.notification);
			    
			    et_advance.setText(""+TaskData.alarmadvance);
				et_alarminterval.setText(""+TaskData.alarminterval);
				switch (TaskData.alarmfreq){
			    	case 0:	rb_freq1.setChecked(true);
		                    rb_freq2.setChecked(false);
			    		   break;
			    	case 1:	rb_freq1.setChecked(false);
		                    rb_freq2.setChecked(true);
			    		   break;
			    	default:break;	   
			    }
				
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
	    
	    
	    
	    OnCheckedChangeListener rblisterner_freq = new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
					if (buttonView.getText().toString().equals("一次")){
						freq= 0;
					}
					if (buttonView.getText().toString().equals("重复")){
						freq= 1;
					}
					
				}
				
			}	
    		
    	};
	    
    	rb_freq1.setOnCheckedChangeListener( rblisterner_freq );
    	rb_freq2.setOnCheckedChangeListener( rblisterner_freq );
    	
    	Button btn_confirm = (Button) v.findViewById(R.id.btn_confirm);  
	    btn_confirm.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框 
	              
	             SharedPreferences sp=getActivity().getSharedPreferences("userinfo",Context.MODE_PRIVATE);
	             Editor alarmEditor = sp.edit();
				 String inputStr_advance=et_advance.getText().toString().trim();
				 String inputStr_alarmInterval=et_alarminterval.getText().toString().trim();
	             if (!TextUtils.isEmpty(inputStr_advance)&&TaskTool.isNumeric(inputStr_advance)){
	            	 advance = Integer.parseInt(inputStr_advance);
				 }else{
					 Toast.makeText(getActivity(),"提前时间请输入有效数字", Toast.LENGTH_SHORT).show();
					 return;
				 }
				 if (!TextUtils.isEmpty(inputStr_alarmInterval)&&TaskTool.isNumeric(inputStr_alarmInterval)){
					 interval = Integer.parseInt(inputStr_alarmInterval);
				 }	else{
					 Toast.makeText(getActivity(),"间隔请输入有效数字", Toast.LENGTH_SHORT).show();
					 return;
				 }
	            	 
	                 alarmEditor.putBoolean("alarmclock",cb1.isChecked());
					 alarmEditor.putBoolean("vibration", cb2.isChecked());
					 alarmEditor.putBoolean("alarmring", cb3.isChecked());
					 alarmEditor.putBoolean("notification",cb4.isChecked()); 
					 alarmEditor.putInt("alarmfreq", freq); 
					 alarmEditor.putInt("alarminterval", interval); 
					 alarmEditor.putInt("alarmadvance", advance); 
					 alarmEditor.commit();
					 
					 
					 
	               //String totalAlarmSetting = ""+cb1.isChecked()+
	               //	   cb3.isChecked()+cb2.isChecked()+(cb4.isChecked());
	               //Toast.makeText(getActivity(),totalAlarmSetting , Toast.LENGTH_SHORT).show();
	              
	               TaskData.alarmclock =sp.getBoolean("alarmclock", true);
	    		   TaskData.vibration = sp.getBoolean("vibration", true);
	    		   TaskData.alarmring= sp.getBoolean("alarmring", true);
	    		   TaskData.notification= sp.getBoolean("notification", false);
	    		   //String totalAlarmSetting = ""+TaskData.alarmclock+
	    			//	   TaskData.vibration+TaskData.alarmring+TaskData.notification;
	    		    Toast.makeText(getActivity(),"闹钟设置已生效", Toast.LENGTH_SHORT).show();
	    		    dismiss();
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
