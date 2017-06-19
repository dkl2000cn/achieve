package com.easygoal.achieve;

import android.content.Context;

public class DimenData {

	public DimenData() {
		// TODO Auto-generated constructor stub
	}
	 public static int dip2px(Context context, float dpValue) {
		    final float scale = context.getResources().getDisplayMetrics().density;
		    return (int) (dpValue * scale + 0.5f);
		  }
}
