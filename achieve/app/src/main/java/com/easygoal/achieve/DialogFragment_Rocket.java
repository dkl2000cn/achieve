package com.easygoal.achieve;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DialogFragment_Rocket extends DialogFragment {

	private Context context;
	public DialogFragment_Rocket() {
		// TODO Auto-generated constructor stub
	  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.rocket, container);
		//v.setBackgroundResource(R.drawable.sky);
		//LinearLayout ll_sky= (LinearLayout) v.findViewById(R.id.ll_sky);
		//ImageView iv_sky = (ImageView) v.findViewById(R.id.iv_sky);
		ImageView iv_rocket = (ImageView) v.findViewById(R.id.iv_rocket);
		//TextView toast_text = (TextView) v.findViewById(R.id.toast_text);
		iv_rocket.setVisibility(View.INVISIBLE);

		float x = iv_rocket.getX();
		float y=iv_rocket.getY();
		AnimUtils.applyRocket(iv_rocket);
		ServiceUtils.playMusicService(context);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dismiss();
			}
		},2000);
 		return v;	
    }
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
        /*
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
		getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height*1/2 );
        */
	}
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.context=context;
	}
}
