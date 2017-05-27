package com.easygoal.achieve;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DialogFragment_Alarm extends DialogFragment {

	
	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			
			View v=inflater.inflate(R.layout.dialogfg_canceltask, container, false);	  
			new AlertDialog.Builder(getActivity()).setTitle("闹钟").setMessage("小猪小猪快起床~").show();
	       return v;
	}
}
