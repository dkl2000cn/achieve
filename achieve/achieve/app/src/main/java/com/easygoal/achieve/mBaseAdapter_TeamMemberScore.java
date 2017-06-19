package com.easygoal.achieve;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import java.util.Map;

public class mBaseAdapter_TeamMemberScore extends BaseAdapter {

	  //用来接收传递过来的Context上下文对象
    private Context context;
    private List<Map> ls;
	private String Tags="mBaseAdapter_TeamMember";
    //构造函数
    public mBaseAdapter_TeamMemberScore(Context context, List<Map> ls)
    {
        this.context = context;
        this.ls=ls;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		 if(ls != null)
	            return ls.size();
	        else  
	            return 0;  
	
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		 return ls == null ? null : ls.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	final class ViewHolder {
		public TextView usernickname;
		public ImageView userheadpic;
		public TextView username;
		public TextView phoneNo;
		public TextView accomplished;
		public TextView achieved;
		public TextView experience;
		public TextView enjoyment;
		public TextView contribution;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder= new ViewHolder();

		 if (view==null){
		     view =LayoutInflater.from(context).inflate(R.layout.lvitem_teammemberscore, null);

			 holder.achieved = (TextView) view.findViewById(R.id.tv_achieved);
			 holder.accomplished= (TextView) view.findViewById(R.id.tv_accomplished);
			 holder.experience= (TextView) view.findViewById(R.id.tv_experience);
			 holder.enjoyment= (TextView) view.findViewById(R.id.tv_enjoyment);
			 holder.contribution= (TextView) view.findViewById(R.id.tv_contribution);
			 //holder.phoneNo= (TextView) view.findViewById(R.id.tv_phoneNo);
			 //holder.username=(TextView) view.findViewById(R.id.tv_username);
			 holder.usernickname=(TextView) view.findViewById(R.id.tv_usernickname);
			 holder.userheadpic=(ImageView) view.findViewById(R.id.iv_headpic);
			 view.setTag(holder);
		 }else {
			 holder = (ViewHolder) view.getTag();
		 }
			 //setViewText(holder.usertitle, "" + ls.get(pos).get("title").toString());
			 String userphoneNo=ls.get(pos).get("phoneNo").toString();
			 Cursor cursor_MemberTeamScore = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " +
							 TaskData.TdDB.TASK_USER + "=? and " +
							 TaskData.TdDB.TASK_USERGROUP + "=? and " +
							 TaskData.TdDB.TASK_STATUS + "!=?",
					 new String[]{userphoneNo, TaskData.usergroup, "cancelled"});
			 Cursor cursor_MemmberFinishedTeamTasks = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskMain +
							 " where " + TaskData.TdDB.TASK_USERGROUP + "=? and " +
							 TaskData.TdDB.TASK_USER + "=? and " +
							 TaskData.TdDB.TASK_STATUS + "=?",
					 new String[]{TaskData.usergroup, userphoneNo, "finished"});
			 TaskData.update_sum(holder.accomplished,cursor_MemmberFinishedTeamTasks,TaskData.TdDB.TASK_IMPORTANCE);
		     TaskData.update_sum(holder.contribution,cursor_MemberTeamScore,TaskData.TdDB.SUM_CONTRIBUTION);
			 TaskData.update_sum(holder.achieved,cursor_MemberTeamScore,TaskData.TdDB.SUM_ACHIEVED);
			 TaskData.update_sum(holder.enjoyment,cursor_MemberTeamScore,TaskData.TdDB.SUM_ENJOYMENT);
			 TaskData.update_sum(holder.experience,cursor_MemberTeamScore,TaskData.TdDB.SUM_EXPERIENCE);
			 //setViewText( holder.usernickname, ""+ls.get(pos).get("nickname").toString()+"<"+ls.get(pos).get("phoneNo").toString()+">");
			 setViewText( holder.usernickname, ""+ls.get(pos).get("nickname").toString());
		     Glide.with(context)
				.load(Uri.parse(ls.get(pos).get("headpicuri").toString()))
				//.signature(new StringSignature(UUID.randomUUID().toString()))
				.placeholder(R.drawable.defaultheadpic_100px)
				.skipMemoryCache( true )
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.bitmapTransform(new CircleTransform(context))
				//.centerCrop()
				.dontAnimate()
				.into(holder.userheadpic);
			 //setViewText(holder.username, "" + ls.get(pos).get("username").toString());
		//Log.d(Tags+"pos",ls.get(pos).get("nickname").toString()+ls.get(pos).get("phoneNo").toString());
		return view;
	}

	private void setViewText(TextView name, String string) {
		// TODO Auto-generated method stub
		name.setText(string);
	}
}
