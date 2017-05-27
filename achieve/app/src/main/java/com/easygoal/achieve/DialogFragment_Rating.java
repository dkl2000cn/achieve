package com.easygoal.achieve;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class DialogFragment_Rating extends DialogFragment {
	 SQLiteDatabase db;
	 UserRatingBean userRating;
	 String Tags="DialogFragment_Rating";
	public DialogFragment_Rating() {
		// TODO Auto-generated constructor stub
	  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.leftmenu_rating, container);
	    Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);  
	    final RatingBar rb_satisfaction = (RatingBar) v.findViewById(R.id.rb_satisfaction);  
	    final RatingBar rb_problemsolving = (RatingBar) v.findViewById(R.id.rb_problemsolving);  
	    final RatingBar rb_userconvenience = (RatingBar) v.findViewById(R.id.rb_useconvenience);  
	    final RatingBar rb_interfacecomfort = (RatingBar) v.findViewById(R.id.rb_interfacecomfort);  
		final RatingBar[] rb_rating={rb_satisfaction,rb_problemsolving,rb_userconvenience,rb_interfacecomfort		
		};
				
	    btn_closewin.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
	    Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);  
		 
	    btn_cancel.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        }); 
	    Button btn_confirm = (Button) v.findViewById(R.id.btn_confirm);  
		 
	    btn_confirm.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  

	        	   LitePalApplication.initialize(getActivity());
	        	   db = Connector.getDatabase();
	               userRating = new UserRatingBean();
	               if (userRating!=null){
	               userRating.setSatisfaction(rb_satisfaction.getProgress());
	               userRating.setProblemsolving(rb_problemsolving.getProgress());
	               userRating.setUserconvenience(rb_userconvenience.getProgress());
	               userRating.setUicomfort(rb_interfacecomfort.getProgress());
	               userRating.setRatingTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	               userRating.setRatingTimeData(new Date().getTime());
	               userRating.setUsername(TaskData.user);
	               boolean a = userRating.save();
	               Log.d(Tags,"add userrating"+a);
	               int lastRowId=0;
					Cursor cur= DataSupport.findBySQL("select LAST_INSERT_ROWID() from userratingbean");
					 Log.d(Tags,"userrating count"+cur.getCount());
					 if (cur!=null&&cur.getCount()>0){
				    	cur.moveToFirst();
						lastRowId=cur.getInt(0);
					   }	
					   String a_sn=TaskData.user+"a";
					   //newMemo.setSn(t_sn+TaskTool.addZero(lastRowId,4));
					   //newMemo.save();
					   
					   ContentValues cv=new ContentValues();
					   cv.put("sn", a_sn+TaskTool.addZero(lastRowId,10));
					 
					   DataSupport.update(UserRatingBean.class,cv,lastRowId);
	                  
	               
	               int b = DataSupport.findBySQL("select * from userratingbean").getCount();
	               if (a==true){
	               Toast.makeText(getActivity(), "评价成功，谢谢支持！", Toast.LENGTH_SHORT).show();
	               }else{
	               Toast.makeText(getActivity(), "保存失败，请再试！"+b, Toast.LENGTH_SHORT).show();  
	               }
	            }  
	           }    
	        });  
 		return v;	
    }
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
		getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	
	
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
	}  
}
