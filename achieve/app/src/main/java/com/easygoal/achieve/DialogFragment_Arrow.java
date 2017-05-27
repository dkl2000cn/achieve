package com.easygoal.achieve;

import android.app.DialogFragment;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DialogFragment_Arrow extends DialogFragment {


	public DialogFragment_Arrow() {
		// TODO Auto-generated constructor stub
	  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.dfg_arrow, container);
		//v.setBackgroundResource(R.drawable.sky);
		//LinearLayout ll_sky= (LinearLayout) v.findViewById(R.id.ll_sky);
		//ImageView iv_sky = (ImageView) v.findViewById(R.id.iv_sky);
		com.easygoal.achieve.RingView rv_arrow = (com.easygoal.achieve.RingView) v.findViewById(R.id.rv_arrow);
		//TextView toast_text = (TextView) v.findViewById(R.id.toast_text);
		rv_arrow.setVisibility(View.INVISIBLE);

		AnimUtils.applyArrow(rv_arrow);
		//Intent intent = new Intent(getActivity(), SoundIntentService.class);
		//getActivity().startService(intent);
		ServiceUtils.playMusicService(getActivity());
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
}
