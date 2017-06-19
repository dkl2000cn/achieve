package com.easygoal.achieve;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerListAdapter extends BaseAdapter {

	private ArrayList<DrawerListItems> list;
	private Context context;
	class Holder {  
        ImageView imageView;  
        TextView textView;  
    }  
	
	public DrawerListAdapter(ArrayList<DrawerListItems> list,Context context) {
		// TODO Auto-generated constructor stub
		this.list=list;
		this.context=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		// TODO Auto-generated method stub
		 if (convertView == null) {  
             // 通过LayoutInflater将xml中定义的视图实例化到一个View中  
             convertView = LayoutInflater.from(context).inflate(  
                     R.layout.lvitem_drawer, null);  

             // 实例化一个封装类holder，并实例化它的两个域  
             holder = new Holder();  
             holder.imageView = (ImageView) convertView  
                     .findViewById(R.id.image);  
             holder.textView = (TextView) convertView  
                     .findViewById(R.id.title);  

             // 将holder对象传递给convertView  
             convertView.setTag(holder);
            
         } else {  
             // 从converView中获取holder对象  
             holder = (Holder) convertView.getTag();  
         }  

        
		// 获取到mList中指定索引位置的资源  
         Drawable img = list.get(position).getImage();  
         String title = list.get(position).getTitle();  

         // 将资源传递给holder的两个域对象  
        
         if (position==0){
        	// LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(  
        		//	 LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  
        		//	layout.setMargins(30, 0, 0, 0);  
        		//	holder.imageView.setLayoutParams(layout); 
        			holder.imageView.setPadding(0, DimenData.dip2px(context, 20), 0, 0);
        			holder.imageView.setImageDrawable(img);
        			holder.textView.setPadding(DimenData.dip2px(context, 15), DimenData.dip2px(context, 20), 0, 0);
        			holder.textView.setText(title); 
         }else{
         holder.imageView.setImageDrawable(img);  
         holder.textView.setText(title); 
         }	
		
		return convertView;
	}

}
