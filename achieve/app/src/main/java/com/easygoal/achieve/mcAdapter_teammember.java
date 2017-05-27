package com.easygoal.achieve;





import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

public class mcAdapter_teammember extends CursorAdapter {


	    private Cursor c;
	    private int listviewItemlist=R.layout.lv_teammember;
	    LayoutInflater inflater;
	    ViewHolder holder;
	    String Tags="mcAdapter_teammember";

		 final static class ViewHolder {
		        public TextView username;
		        public TextView userphoneNo;
		        public TextView usernickname;
		        public TextView usertitle;
		    }

	public mcAdapter_teammember(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
	    Log.d(Tags,"create mcAapter_group"+context.toString());
	}



	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub

		holder = (ViewHolder) view.getTag();
		setViewText(holder.username, "["+cursor.getString(cursor.getColumnIndex("username"))+ "] ");
		setViewText(holder.userphoneNo, "["+cursor.getString(cursor.getColumnIndex("phoneNo"))+ "] ");
		setViewText(holder.usernickname, "["+cursor.getString(cursor.getColumnIndex("nickname"))+ "] ");
		//setViewText(holder.usertitle, "["+cursor.getString(cursor.getColumnIndex("title"))+ "] ");

	}

	private void setViewText(TextView name, String string) {
		// TODO Auto-generated method stub
		name.setText(string);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub

		holder= new ViewHolder();
        View view=inflater.inflate(listviewItemlist ,parent,false);
        holder.username = (TextView) view.findViewById(R.id.tv_username);
        holder.userphoneNo= (TextView) view.findViewById(R.id.tv_phoneNo);
        holder.usertitle=(TextView) view.findViewById(R.id.tv_usertitle);
        holder.usernickname=(TextView) view.findViewById(R.id.tv_usernickname);
        view.setTag(holder);

        return view;  
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d(Tags+"|mcAapter_taskrecord cursor count|", String.valueOf(c.getCount()));
		return c.getCount();
	}

	@Override
	public Cursor getCursor() {
		// TODO Auto-generated method stub
		return super.getCursor();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

}
