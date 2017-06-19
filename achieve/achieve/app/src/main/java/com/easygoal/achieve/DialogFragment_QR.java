package com.easygoal.achieve;

import android.app.DialogFragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.easygoal.achieve.R.drawable.view;

public class DialogFragment_QR extends DialogFragment {


	TextView tv_qr;
    private Context context;
	public DialogFragment_QR() {
		// TODO Auto-generated constructor stub
	   this.context=context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.dfg_qr, container);
		//FragmentDialogFragmentQRBinding viewDataBinding = DataBindingUtil.bind(v);

		//DialogFragmentQRBinding mbinding = DataBindingUtil.inflate(inflater, R.layout.dfg_qr, container, false);
        ImageView iv_qr=(ImageView)v.findViewById(R.id.iv_qr);
		iv_qr.setImageBitmap(QRUtils.generateBitmap(
				"http://shouji.baidu.com/software/11545025.html",430,430));
		/*
		iv_qr.setImageBitmap(QRUtils.createQRwithLogo(
				"http://shouji.baidu.com/software/11545025.html",getResources(),R.drawable.logo));
        */
		Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);
		 
	    btn_closewin.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // ¹Ø±Õ¶Ô»°¿ò  
	                dismiss();  
	            }  
	        });
		Button btn_confirm=(Button)v.findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

 		return v;	
    }

	@OnClick(R.id.btn_confirm)
	public void submit(View view) {
		// TODO submit data to server...
		QRUtils.generateBitmap("i love you",200,200);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
		getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

	}
}
