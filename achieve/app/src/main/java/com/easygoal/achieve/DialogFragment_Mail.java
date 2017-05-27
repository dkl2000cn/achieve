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

public class DialogFragment_Mail extends DialogFragment {


	public DialogFragment_Mail() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.dfg_mail, container);
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
		final EditText mailaddress = (EditText)v.findViewById(R.id.et_address);
		final EditText title = (EditText)v.findViewById(R.id.et_title);
       // mactv.setBackgroundResource(R.drawable.shape_line_rectangle);
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
	                // 关闭对话框
	            	Intent emailIntent = new Intent(Intent.ACTION_SEND);
		     		//设置文本格式
	            	emailIntent.setData(Uri.parse("mailto:"+mailaddress.getText().toString()));
	            	emailIntent.setType("text/plain");
		     		//emailIntent.setType("application/octet-stream");
		     		//设置对方邮件地址
		     		emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{mailaddress.getText().toString()});
		     		//设置标题内容
		     		emailIntent.putExtra(Intent.EXTRA_SUBJECT,title.getText().toString());
		     		//设置邮件文本内容
		     		emailIntent.putExtra(Intent.EXTRA_TEXT,body.getText().toString());
		     		startActivity(Intent.createChooser(emailIntent,"邮件"));
		     		//Toast.makeText(getActivity(), "邮件已发送,谢谢您的反馈!", Toast.LENGTH_SHORT).show();
		     		dismiss();
		     		//Toast.makeText(getActivity(), "邮件已发送,谢谢您的反馈!", Toast.LENGTH_SHORT).show();
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
