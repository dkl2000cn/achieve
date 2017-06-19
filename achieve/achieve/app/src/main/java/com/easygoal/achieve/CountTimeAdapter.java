package com.easygoal.achieve;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlarmManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CountTimeAdapter extends CursorAdapter  {
	  private Context context;
	  private Cursor c ;
	  boolean NowChoose = false;// 记录当前按钮是否打开,true为打，flase为关  
	    boolean OnSlip = false;// 记录用户是否在滑动的变量  
	    float DownX=0;// 按下时的x,当前的x,
	    float UpX=0;
		float NowX=0;
		int pos;
		final AlarmManager alarmgr; 
		String Tags="CountTimeAdapter";
	    //private Rect Btn_On, Btn_Off;// 打开和关闭状态下,游标的Rect  
	    final boolean isChgLsnOn = false;
		
	    final class ViewHolder { 
	    	public TextView tv_sourceId;
	    	public TextView tv_reminderName;
	    	public Button btn_downcounttime;
	    	
	    	
	    }        
	    
	    @SuppressWarnings("deprecation")
		public CountTimeAdapter(Context context, Cursor cursor) {
		super(context,cursor);
		this.context = context;
		this.c=cursor;
		alarmgr= (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
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

		@Override
		public int getCount() {
			return c.getCount();
		}

		@Override
		public void bindView(View convertView, Context context, Cursor cursor) {
			// TODO Auto-generated method stub
			//if(convertView == null){
				//convertView = View.inflate(context, R.layout.lv_eventbean,null);
		   ViewHolder holder = (ViewHolder) convertView.getTag();
		   cursor=this.c;
		   //String remainingtime = cursor.getString(cursor.getColumnIndex("remainingtime"));
		
	    if (cursor!=null && cursor.getCount()>0){
	    	
		    String name= cursor.getString(cursor.getColumnIndex("name"));
			//String deadlinetime= cursor.getString(cursor.getColumnIndex("deadlinetime"));
			//String advance= cursor.getString(cursor.getColumnIndex("advane"));
			String deadlinetime= cursor.getString(cursor.getColumnIndex("deadlinetime"));
			String advance= cursor.getString(6);
			final String sn;
			final long id;
			long a=0;
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
			
		  if ( cursor.getString(cursor.getColumnIndex("deadlinetime"))!=null){	
			   try {
				  a=sdf.parse(deadlinetime).getTime();
			   } catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
		   }else{
			    Toast.makeText(context, "无提醒时间", Toast.LENGTH_SHORT).show();
		        }
			//a = TimeData.changeStrToTime_YYYY(et_eventdeadlinetime.getText().toString());
		     	
		          sn=cursor.getString(cursor.getColumnIndex("sn"));
		          id=cursor.getLong(0);
		          Log.d(Tags, sn+" "+id);
		       long b=new Date().getTime();
			
			   long t = 0;
			  if ( cursor.getString(6)!=null){		
			       t=Integer.parseInt(advance)*60*1000;
			   }	
			  
		        long timegap=a-b;
		        String timegapStr;
		  
		        timegapStr = TimeData.changeLongToTimeStrNoSecond(timegap);
		       if (holder!=null&&holder.btn_downcounttime!=null){
		 		  //holder.btn_downcounttime.setText( name+"   最后期限   "+deadlinetime+"\n"+"还剩下 "+spanString);
		 			//holder.btn_downcounttime.setText(outStr);
		 			//holder.btn_downcounttime.setText(outStr);
		 		   //Log.d("reminder", name+" "+deadlinetime);
		 		  // holder.tv_sourceId.setText(cursor.getString(2));
		 		   //SpannableString spanString = new SpannableString("  "+timegap);  
		 		   //ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);  
		 		  // spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);    

		 		   //holder.tv_reminderName.setText(cursor.getString(2)+"  "+name+"\n"+deadlinetime);
		 		   holder.tv_reminderName.setText(name+"\n"+deadlinetime);
		 		  
		 		   if (timegap>=t){
		 		      holder.btn_downcounttime.setTextColor(Color.WHITE);;
		 		      holder.btn_downcounttime.setText(timegapStr);
		 		   }else{
		 			  if (timegap>=0){ 
		 			     holder.btn_downcounttime.setTextColor(Color.YELLOW);  
		 			     holder.btn_downcounttime.setText(timegapStr+"(到期)");
		 		      }else{
		 		    	 holder.btn_downcounttime.setText(timegapStr+"(过期)");
		 		    	 holder.btn_downcounttime.setTextColor(Color.YELLOW);  
		 		      }  
		 		   }
		 		  holder.btn_downcounttime.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						DialogFragment_ReminderDetail dg_reminderdetail=new DialogFragment_ReminderDetail(sn,id);
		    	    	TaskTool.showDialog(dg_reminderdetail);
		    	    	
					}
		 			  
		 		  });
		   }	
		  }
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder holder=new ViewHolder();;
			
			LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
		    View view=inflater.inflate(R.layout.lv_reminder ,parent,false);
			holder.tv_reminderName=(TextView)view.findViewById(R.id.tv_reminderName);
		    holder.btn_downcounttime = (Button)view.findViewById(R.id.btn_downcounttime);
		    holder.tv_sourceId = (TextView)view.findViewById(R.id.tv_sourceId);
		    view.setTag(holder);
			return view;
		}
	
	}

