package com.easygoal.achieve;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.rong.imkit.RongIM;

public class mBaseAdapter_TeamMember extends BaseAdapter {

    private Context context;
    private List<Map> ls;
	ViewHolder holder;
	private String Tags="mBaseAdapter_TeamMember";
    //??????
    public mBaseAdapter_TeamMember(Context context, List<Map> ls)
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

	final static class ViewHolder {
		public TextView username;
		public ImageView headpic;
		public TextView userphoneNo;
		public TextView usernickname;
		public TextView usertype;
		public TextView usertitle;
		public Button chat;
		public Button call;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		 holder= new ViewHolder();
		 Log.d(Tags+"pos",""+pos);
		 if (view==null){
		     view =LayoutInflater.from(context).inflate(R.layout.lv_teammember, null);
			 holder.headpic = (ImageView) view.findViewById(R.id.iv_headpic);
			 holder.username = (TextView) view.findViewById(R.id.tv_username);
			 holder.userphoneNo= (TextView) view.findViewById(R.id.tv_phoneNo);
			 //holder.usertitle=(TextView) view.findViewById(R.id.tv_usertitle);
			 holder.usernickname=(TextView) view.findViewById(R.id.tv_usernickname);
			 holder.usertype=(TextView) view.findViewById(R.id.tv_usertype);
			 holder.chat=(Button) view.findViewById(R.id.btn_chat);
			 holder.call=(Button) view.findViewById(R.id.btn_call);
			 view.setTag(holder);
		 }else {
			 holder = (ViewHolder) view.getTag();
		 }
			 Glide.with(context)
				.load(Uri.parse(ls.get(pos).get("headpicuri").toString()))
				//.signature(new StringSignature(UUID.randomUUID().toString()))
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.skipMemoryCache( true )
				.placeholder(R.drawable.defaultheadpic_100px)
				.bitmapTransform(new CircleTransform(context))
				//.centerCrop()
				.dontAnimate()
				.into(holder.headpic);
		 /*
		  if (TaskData.profileUpdateflag==1) {
			  Glide.with(context)
					  .load(Uri.parse(ls.get(pos).get("headpicuri").toString()))
					  .signature(new StringSignature(UUID.randomUUID().toString()))
					  //.diskCacheStrategy(DiskCacheStrategy.ALL)
					  //.centerCrop()
					  .dontAnimate()
					  .into(holder.headpic);
		  }else{
			  Glide.with(context)
					  .load(Uri.parse(ls.get(pos).get("headpicuri").toString()))
					  //.signature(new StringSignature(UUID.randomUUID().toString()))
					  .diskCacheStrategy(DiskCacheStrategy.ALL)
					  //.centerCrop()
					  .dontAnimate()
					  .into(holder.headpic);
		  }*/
		     //holder.headpic.setImageURI(Uri.parse(ls.get(pos).get("headpicuri").toString()));
			 setViewText( holder.usernickname, ""+ls.get(pos).get("nickname").toString() );
			 setViewText(holder.userphoneNo, ""+ls.get(pos).get("phoneNo").toString());
			 setViewText(holder.username, "" + ls.get(pos).get("username").toString());
		     setViewText(holder.usertype, "" + ls.get(pos).get("usertype").toString());
			 //setViewText(holder.usertitle, "" + ls.get(pos).get("title").toString());

		return view;
	}

	private void setViewText(TextView name, String string) {
		// TODO Auto-generated method stub
		name.setText(string);
	}
}
