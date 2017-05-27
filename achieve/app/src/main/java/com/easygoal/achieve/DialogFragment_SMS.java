package com.easygoal.achieve;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.util.List;

public class DialogFragment_SMS extends DialogFragment {


	public DialogFragment_SMS() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.dfg_sms, container);
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
	    Button btn_confirm = (Button) v.findViewById(R.id.btn_confirm);
	    final MultiAutoCompleteTextView body = (MultiAutoCompleteTextView)v.findViewById(R.id.mactv_body);
		final EditText et_phontno = (EditText)v.findViewById(R.id.et_phoneno);
		final EditText title = (EditText)v.findViewById(R.id.et_title);
       // mactv.setBackgroundResource(R.drawable.shape_line_rectangle);
		et_phontno.setText(TaskData.selMemberID);
		body.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	    	@Override
	    	public void onFocusChange(View v, boolean hasFocus) {
	    	if (hasFocus) {
	    		//InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
	    		//imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
	    	   }
	    	 //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	    	 }
	    });

	    btn_confirm.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View arg0) {

					doSendSMSTo(TaskData.selMemberID,body.getText().toString());
		     		//Toast.makeText(getActivity(), "邮件已发送,谢谢您的反馈!", Toast.LENGTH_SHORT).show();
		     		dismiss();
		     		//Toast.makeText(getActivity(), "邮件已发送,谢谢您的反馈!", Toast.LENGTH_SHORT).show();
	            }  
	        });  
 		return v;	
    }

	public void doSendSMSTo(String phoneNumber,String message){
		if(TaskTool.isMobileNO11(phoneNumber)){
			//Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNumber));
			//intent.putExtra("sms_body", message);
			//startActivity(intent);

			android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
			//拆分短信内容（手机短信长度限制）
			List<String> divideContents = smsManager.divideMessage(message);
			for (String text : divideContents) {
				smsManager.sendTextMessage(TaskData.selMemberID, null, text, null, null);
			}
		}else {
			Toast.makeText(getActivity(), "此号码无效", Toast.LENGTH_SHORT).show();
		}

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
