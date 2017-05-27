package com.easygoal.achieve;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DialogFragment_Flower extends DialogFragment {

    private Context context;
	public DialogFragment_Flower() {
		// TODO Auto-generated constructor stub
	  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.dfg_flower, container);
		//v.setBackgroundResource(R.drawable.sky);
		//LinearLayout ll_sky= (LinearLayout) v.findViewById(R.id.ll_sky);
		//ImageView iv_sky = (ImageView) v.findViewById(R.id.iv_sky);
		ImageView iv_flower = (ImageView) v.findViewById(R.id.iv_flower);
		//TextView toast_text = (TextView) v.findViewById(R.id.toast_text);
		iv_flower.setVisibility(View.INVISIBLE);

		AnimUtils.applyFlower(iv_flower);
		/*
		Intent intent = new Intent(context,SoundIntentService.class);
		LogUtils.d("intent ready");
		context.startService(intent);
		LogUtils.d("intent done");*/
		ServiceUtils.playMusicService(context);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dismiss();
			}
		},100000);
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
