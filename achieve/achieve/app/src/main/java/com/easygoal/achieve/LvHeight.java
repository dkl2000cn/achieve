package com.easygoal.achieve;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class LvHeight { 
    public static void setListViewHeightBasedOnChildren(ListView listView) { 
        ListAdapter listAdapter = listView.getAdapter();  
   
        if (listAdapter == null) { 
            // pre-condition 
            return; 
        } 
 
        int totalHeight = 0; 
        //Log.d("lvheight", "Adapter:"+listAdapter.toString()+" count:"+listAdapter.getCount());
        for (int i = 0; i < listAdapter.getCount(); i++) { 
            View listItem = listAdapter.getView(i, null, listView); 
            listItem.measure(0, 0); 
            totalHeight += listItem.getMeasuredHeight(); 
        } 
        //Log.d("lvheight",String.valueOf(totalHeight));
        ViewGroup.LayoutParams params = listView.getLayoutParams(); 
        //Log.d("lvheight",params.toString());
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
        //Log.d("lvheight",String.valueOf(params.height));
        listView.setLayoutParams(params); 
        //Log.d("lvheight","set params");
    } 
} 