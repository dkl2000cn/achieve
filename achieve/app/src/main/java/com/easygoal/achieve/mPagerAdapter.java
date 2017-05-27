package com.easygoal.achieve;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

public class mPagerAdapter extends PagerAdapter {

	public mPagerAdapter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view==object;
	}

	@Override
	public void destroyItem(View collection, int position, Object object) {
		// TODO Auto-generated method stub
		 ((ViewPager)collection).removeView((View) object);  
		// object=null;  
	  
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(View collection, int position) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = (LayoutInflater) collection.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				int resId = 0;
				switch (position) {
				case 0:
				resId = R.layout.openslide0;
				break;
				case 1:
				resId = R.layout.openslide1;
				break;
				case 2:
				resId = R.layout.openslide2;
				break;
				case 3:
				resId = R.layout.openslide3;
				break;
				case 4:
				resId = R.layout.openslide4;
				break;
				}
				//View view1;
				//LinearLayout layout2=new LinearLayout(getActivity());  
			    //layout2.setOrientation(LinearLayout.VERTICAL);
				View view = inflater.inflate(resId, null);
				//layout2.addView(view1);
				//View circleview = inflater.inflate(R.layout.layout_circle, null);
				//layout2.addView(circleview);
				//outputview=inflater.inflate(layout2, null);
				((ViewPager)collection).addView(view, 0);
			    
				return view;		
	       }		
}
