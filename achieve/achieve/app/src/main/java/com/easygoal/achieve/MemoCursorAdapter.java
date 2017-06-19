package com.easygoal.achieve;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MemoCursorAdapter extends CursorAdapter  {
	 
	  private Context context;
	  private Cursor c ;
	  boolean NowChoose = false;// 记录当前按钮是否打开,true为打，flase为关  
	  boolean OnSlip = false;// 记录用户是否在滑动的变量  
	  float downx=0;// 按下时的x,当前的x,
	  float upx=0;
      float NowX=0;
	  int pos;
	private int lastX;
	private int lastY;
	private String ivsrc;
	  //Resources  r;
	  //int imgres;
	  Uri imguri; 
	  String Tags="MemoCursorAdapter";
	    //private Rect Btn_On, Btn_Off;// 打开和关闭状态下,游标的Rect  
	  final boolean isChgLsnOn = false;
		
	    final class ViewHolder {  
	        public TextView name;  
	        public TextView content;  
	        public TextView createdtime;    
	        public ImageView imageview; 
	        public TextView alarm;
	        public TextView picUriStr; 
	        public TextView fileUriStr;  
	    }      
	    
	    public MemoCursorAdapter(Context context, Cursor cursor) {
		super(context,cursor);
		this.context = context;
		this.c=cursor;
		//r =context.getResources();
		//imgres=R.drawable.memo_pic1;
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
		  
		
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
		public void bindView(final View convertView, final Context context, Cursor cursor) {
			// TODO Auto-generated method stub
		 
		   ViewHolder holder = (ViewHolder) convertView.getTag();
		 
		   String createdtime = cursor.getString(cursor.getColumnIndex("createdtime"));
		   holder.createdtime.setText("("+createdtime+")");	
		   String name= cursor.getString(cursor.getColumnIndex("name"));
		   //memoid=cursor.getInt(cursor.getColumnIndex("rowid"));
		   final int memoid=cursor.getInt(0);
		   //Log.d(Tags, "memo id "+memoid);
		   SpannableString spanStr_time=new SpannableString("("+createdtime+")");
		   AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(28); 
		   StyleSpan span_style = new StyleSpan(Typeface.ITALIC); 
		   spanStr_time.setSpan(span_style, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		   spanStr_time.setSpan(span_size, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		   holder.name.setText(name);
		   holder.name.append(spanStr_time);
		   //holder.name.setText(name+Html.f.fromHtml(source).fromHtml("<font size='12' style='italic'>"+createdtime+"</font>"));
			//String aaa= cursor.getString(cursor.getColumnIndex("picUriStr"));
			//Log.d("picuristr", aaa);

		   if (!TextUtils.isEmpty(cursor.getString(6))){
			   holder.alarm.setText(cursor.getString(6));
			   holder.alarm.setVisibility(View.VISIBLE);	   
		   }else{
			   holder.alarm.setVisibility(View.GONE);
		   }
		   
		   if (holder.imageview!=null){
			   if (cursor.getString(2)!=null){
				   ivsrc= cursor.getString(2);
				   /*
				   Picasso.with(context)
						   .load(ivsrc)
						   .placeholder(R.drawable.memo_pic1)
						   .error(R.drawable.memo_pic1)
						   .into(holder.imageview);

				   Glide.with(context)
						   .load(ivsrc)
						   .asBitmap()
						   .centerCrop()
						   .dontAnimate()
						   .diskCacheStrategy(DiskCacheStrategy.ALL)
						   //.centerCrop()
						   .skipMemoryCache( true )
						   .placeholder(R.drawable.memo_pic1)
						   .error(R.drawable.memo_pic1)
						   .into(holder.imageview);*/
				   ImageLoader.getInstance().displayImage(ivsrc,holder.imageview);
			   }
			   else{
				   ivsrc= "drawable://"+R.drawable.memo_pic1;
				   /*
				   Picasso.with(context)
						   .load(R.drawable.memo_pic1)
						   .into(holder.imageview);

				   Glide.with(context)
						   .load(R.drawable.memo_pic1)
						   .asBitmap()
						   .centerCrop()
						   .dontAnimate()
						   .diskCacheStrategy(DiskCacheStrategy.ALL)
						   .skipMemoryCache( true )
						   //.centerCrop()
						   .into(holder.imageview);*/
				   ImageLoader.getInstance().displayImage(ivsrc,holder.imageview);
			   }
			   
			   holder.imageview.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//Toast.makeText(context,"memoid"+memoid,Toast.LENGTH_SHORT).show();
					DialogFragment_MemoDetail dg_memodetail=new DialogFragment_MemoDetail(memoid);
	    	    	TaskTool.showDialog(dg_memodetail);
				}
				   
			   }); 
			   
		   }else{
				Log.d(Tags, "imageview null");
			}
		
			String content= cursor.getString(cursor.getColumnIndex("content"));
			//holder.picUriStr.setText(aaa);
			holder.content.setText(content);
			if (cursor.getString(3)!=null&&!cursor.getString(3).trim().isEmpty()){
			  final String fileuristr=cursor.getString(3);
			  //Html.fromHtml("<u>"+fileuristr+"</u>");
			  holder.fileUriStr.setText("附件(可打开)");
		    
			  holder.fileUriStr.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=FileDo.openFile(fileuristr);
					context.startActivity(intent);
				}
				
			});
		   }else{
			  holder.fileUriStr.setVisibility(View.INVISIBLE); 
		   }

			////为每一个view项设置触控监听 左滑出现收藏按钮  跟listView.setOnItemClickListener应该是冲突了
			/*
			convertView.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					//当按下时处理
					//获取到手指处的横坐标和纵坐标
					int x;
					int y;
					//int x = (int) event.getX();
					//int y = (int) event.getY();

					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:

							x = (int) event.getX();
							y = (int) event.getY();

							lastX = x;
							lastY = y;
                            Log.d("down","down x"+lastX+"down y"+lastY);
							break;
						case MotionEvent.ACTION_MOVE:
							x = (int) event.getX();
							y = (int) event.getY();
							//计算移动的距离
							int offX = x - lastX;
							int offY = y - lastY;
							convertView.layout(convertView.getLeft() + offX,
									convertView.getTop() ,
									convertView.getRight() + offX,
									convertView.getBottom() );
							//convertView.scrollBy(-offX,-offY );
							Log.d("off","off x"+offX+"off y"+offY);
							if (offX>convertView.getWidth()/2){
								Toast.makeText(context,"滑动删除", Toast.LENGTH_SHORT).show();
								TaskTool.quickclose(context);
							}
							Toast.makeText(context, "x" + offX + " y" + offY, Toast.LENGTH_SHORT).show();
							break;
					}
					return true;
				}
			});
           */

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// TODO Auto-generated method stub

			   ViewHolder holder=new ViewHolder();;
			
			    LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
		        View view=inflater.inflate(R.layout.lv_memo ,parent,false);
		
				holder.name = (TextView)view.findViewById(R.id.tv_memoname);
			
				holder.content= (TextView)view.findViewById(R.id.tv_memocontent);
		
				holder.createdtime= (TextView)view.findViewById(R.id.tv_memocreatedtime);
				
				holder.imageview= (ImageView)view.findViewById(R.id.iv_memopic);
				
				holder.alarm= (TextView)view.findViewById(R.id.tv_alarm);
				holder.fileUriStr= (TextView)view.findViewById(R.id.tv_file);
			view.setTag(holder);
			return view;
		}

	public boolean onTouchEvent(MotionEvent event) {


		//获取到手指处的横坐标和纵坐标
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:

				lastX = x;
				lastY = y;

				break;

			case MotionEvent.ACTION_MOVE:

				//计算移动的距离
				int offX = x - lastX;
				int offY = y - lastY;
				/*
				//调用layout方法来重新放置它的位置
				layout(getLeft()+offX, getTop()+offY,
						getRight()+offX    , getBottom()+offY);
                */
				Toast.makeText(context,"x"+offX+" y"+offY,Toast.LENGTH_SHORT).show();
				break;
		}

		return true;
	}
}

