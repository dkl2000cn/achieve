package com.easygoal.achieve;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.MultiAutoCompleteTextView;

public class FeedbackActivity extends Activity {

	public FeedbackActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leftmenu_feedback);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		 ActionBar actionBar = getActionBar();  
			actionBar.setDisplayHomeAsUpEnabled(true); 	
			//getActionBar().setHomeButtonEnabled(true);
        MultiAutoCompleteTextView mactv=(MultiAutoCompleteTextView)findViewById(R.id.mactv_body);
       // mactv.setBackgroundResource(R.drawable.shape_line_rectangle);	
		mactv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	    	@Override
	    	public void onFocusChange(View v, boolean hasFocus) {
	    	if (hasFocus) {
	    		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);  
	    		imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
	    	   }
	    	 //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	    	 }
	    });
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
