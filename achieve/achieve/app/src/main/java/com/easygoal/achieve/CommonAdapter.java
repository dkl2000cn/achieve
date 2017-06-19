package com.easygoal.achieve;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class CommonAdapter<T> extends Adapter<ViewHolder> {

	protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;


    public CommonAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        //ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);

        View view=mInflater.inflate(mLayoutId,parent,false);
        //view.setBackgroundColor(Color.RED);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
     
    }

    public void onBindViewHolder(ViewHolder holder, int pos)
    {
        
        convert(holder, mDatas.get(pos));
    }

    public abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       // public TextView item_tv;
        public ViewHolder(View view){
            super(view);
           // item_tv = (TextView) view.findViewById(R.id.item_tv);
        }
    }
 
    
}
