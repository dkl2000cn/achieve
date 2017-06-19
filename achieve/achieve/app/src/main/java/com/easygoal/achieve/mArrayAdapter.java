package com.easygoal.achieve;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class mArrayAdapter extends  BaseAdapter {
	private int mResourceId;
	private Context context;
	private List<InputValueSet> mList;
	String Tags="mArrayAdapter";
	
	public mArrayAdapter(Context context, int textViewResourceId, List<InputValueSet> pList) {
		// TODO Auto-generated constructor stub
		  this.context=context;
		  this.mResourceId = textViewResourceId;
		  this.mList = pList;
		  Log.d(Tags,"textid"+mResourceId);
		  
	}
	
	  
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//InputValueSet inputvalue = getItem(position);
         //LayoutInflater inflater = (LayoutInflater)context.getSystemService
           //      (Context.LAYOUT_INFLATER_SERVICE);
         LayoutInflater inflater=LayoutInflater.from(context);
         convertView = inflater.inflate(mResourceId, null);
         if(convertView!=null){
         
         TextView spin_no =(TextView)convertView.findViewById(R.id.spinnerno);
         TextView spin_text =(TextView)convertView.findViewById(R.id.spinnertext);
         //spin_no.setText("no"+mList.get(position).getNo());
         
         spin_no.setText(String.valueOf(mList.get(position).getNo()));
         spin_text.setText(mList.get(position).getValue());
         }else{
         Log.d(Tags,"false" );
         };

         return convertView;
	}
	
	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
}
