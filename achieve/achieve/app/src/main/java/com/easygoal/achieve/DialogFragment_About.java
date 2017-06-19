package com.easygoal.achieve;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class DialogFragment_About extends DialogFragment {

	
	public DialogFragment_About() {
		// TODO Auto-generated constructor stub
	  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.leftmenu_about, container);

		ImageView iv_qr=(ImageView)v.findViewById(R.id.iv_qr);
		iv_qr.setImageBitmap(QRUtils.generateBitmap(
				"http://shouji.baidu.com/software/11545025.html",430,430));

	    Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);  
		 
	    btn_closewin.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // ¹Ø±Õ¶Ô»°¿ò  
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
