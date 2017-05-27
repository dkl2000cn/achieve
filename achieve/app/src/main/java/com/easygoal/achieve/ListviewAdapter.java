package com.easygoal.achieve;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListviewAdapter extends BaseAdapter {

	  //用来接收传递过来的Context上下文对象
    private Context context;

    //构造函数
    public ListviewAdapter(Context context)
    {   Log.d("Context", context.toString());
        this.context = context;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub  
		  //LinearLayout pageLayout = ((LinearLayout) inflater).inflate(context, LayoutId, null);  
		 // convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_find_listview, null);
		 convertView =LayoutInflater.from(context).inflate(R.layout.lv_allcomments, null);
		
		// TextView tv1=(TextView)convertView.findViewById(R.id.record_item1_recordID_tv);
		 Log.d("view", "show view");
		
		return convertView;
	}

	private Context getBaseContext() {
		// TODO Auto-generated method stub
		return this.context;
	}

	

	
}
