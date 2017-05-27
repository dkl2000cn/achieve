package com.easygoal.achieve;

import org.apache.http.util.TextUtils;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DialogFragment_PhoneInput extends DialogFragment {
    private Context context;
	public DialogFragment_PhoneInput() {
		// TODO Auto-generated constructor stub
	   //this.context=context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
 	   View view = View.inflate(getActivity(), R.layout.dialogfg_phoneinput, null);  
 	  
   	      final EditText et_userphone = (EditText)view.findViewById(R.id.et_userphone);
   	   
             Button btn_confirm=(Button)view.findViewById(R.id.confirm_bt);
             Button btn_cancel=(Button)view.findViewById(R.id.cancel_bt);
           
             btn_confirm.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				    String phoneNo=et_userphone.getText().toString().trim();
				    
				    if (!TextUtils.isEmpty(phoneNo)&&TaskTool.isMobileNO(phoneNo)){
			    		TaskData.user=phoneNo;
			    		Toast.makeText(getActivity(),"确认成功", Toast.LENGTH_SHORT).show(); 
			    		dismiss();
			    	}else{	
			    		Toast.makeText(getActivity(),"请输入正确的手机号码", Toast.LENGTH_SHORT).show();               	     
			    	}  
	              
				   
				}
            	 
             });
             
             btn_cancel.setOnClickListener(new OnClickListener(){

 				@Override
 				public void onClick(View arg0) {
 					// TODO Auto-generated method stub
 					dismiss();
 				}
             	 
              });
			return view;	
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
