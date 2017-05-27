package com.easygoal.achieve;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.litepal.crud.DataSupport;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DialogFragment_Memo extends DialogFragment {
	public static final int REQUEST_CODE_PICK_IMAGE = 1;
	public static final int CLICKEDFILE = 0;
	public static final int REQUEST_CODE_LOCATION= 2;
	String curTime;
	Timestamp taskdeadlinetimestamp=null;
	String task_maxperiod;
	String column_insert;
	String value_insert;
	 long taskdeadlineTimeData=0;
	 String taskdeadlineDate;
	 ImageView iv_memoaddpic;
	 TextView tv_memoaddfile;
	 TextView tv_location;
	 CheckBox cb_location;
	 Uri uri;
	 String Tags="DialogFragment_Memo";
	SharedPreferences sp;
	//SharedPreferences.Editor editor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);  
		 //setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		  /*if (TaskData.TdDB==null) {
		    TaskData.TdDB = new ToDoDB(getActivity(), TaskData.db_TdDBname,null, 1);
		    TaskData.db_TdDB = TaskData.TdDB.getWritableDatabase();
			//taskRecord.onCreate(db);
			Log.d("create", TaskData.TdDB.TABLE_NAME_TaskRecord);
		   }else{
			   Log.d("create", "go");
		   }		 
		*/
		 SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
		 Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		 curTime = formatter.format(curDate);

		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v=inflater.inflate(R.layout.dialogfg_memo, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		
		  final EditText et_memoname=(EditText)v.findViewById(R.id.et_memoname);
		  final EditText et_memocontent=(EditText)v.findViewById(R.id.et_memodescription);
		  final TextView tv_memocreatedtime=(TextView)v.findViewById(R.id.tv_memocreatedtime);
		  final EditText et_memodeadlinetime=(EditText)v.findViewById(R.id.et_memodeadline);
		 
		 TextView btn_memoaddpic=(TextView)v.findViewById(R.id.btn_memoaddpic);
		 TextView btn_memoaddfile=(TextView)v.findViewById(R.id.btn_memoaddfile);
		 //Button btn_location=(Button)v.findViewById(R.id.btn_location);
		 iv_memoaddpic=(ImageView)v.findViewById(R.id.iv_memoaddpic);
		 tv_memoaddfile=(TextView)v.findViewById(R.id.tv_memoaddfile);
		 tv_location=(TextView)v.findViewById(R.id.tv_location);
		 cb_location=(CheckBox)v.findViewById(R.id.cb_location);
		 Button btn_confirm=(Button)v.findViewById(R.id.memo_confirm_bt);
		 Button btn_cancel = (Button) v.findViewById(R.id.memo_cancel_bt);  
		 Button btn_close = (Button) v.findViewById(R.id.memo_close_bt);
           /*
		Glide.with(getActivity())
				.load(R.drawable.defaultpic)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				//.centerCrop()
				.dontAnimate()
				.into(iv_memoaddpic);
         */
	        btn_close.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
		 
	        btn_cancel.setOnClickListener(new OnClickListener() {  
		      	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        }); 
		    tv_memocreatedtime.setText(curTime);
		  
		    
		    et_memodeadlinetime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    	
		    	
		    	   @Override
		    	   public void onFocusChange(View v, boolean hasFocus) {
		    	       if (hasFocus) {
		    	    	
		    	    	   DialogFragment_TimeInput dg_time=new DialogFragment_TimeInput(et_memodeadlinetime,0);
			    	       TaskTool.showDialog(dg_time);
		    	    	
		    	       }
		    	   }
		    	});  
		    
		    
		  btn_memoaddpic.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				       Intent intent = new Intent(Intent.ACTION_PICK);  
				       intent.setType("image/*");//相片类型  
				       //startActivityForResult(Intent.createChooser(intent, "love you!!!"),REQUEST_CODE_PICK_IMAGE);
				       startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);  
				       //Toast.makeText(getActivity(), intent.toString(), Toast.LENGTH_LONG).show();           
				     
			   }	
		    });  
		  
		  btn_memoaddfile.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					//Intent intent=new Intent(getActivity(),FileActivity.class);
					 //startActivity(intent);
					 Intent intent = new Intent();    
					 intent.setComponent(new ComponentName("com.easygoal.achieve", "com.easygoal.achieve.Activity_FileOpen"));
		                intent.setAction(Intent.ACTION_VIEW);    
		                intent.putExtra("test", "start");  
		                startActivityForResult(intent, CLICKEDFILE); 
				}
		  });


		cb_location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					//tv_location.setText();
					Intent intent = new Intent();
					intent.setComponent(new ComponentName("com.easygoal.achieve", "com.easygoal.achieve.Activity_BDLocation"));
					startActivityForResult(intent, REQUEST_CODE_LOCATION);
					cb_location.setText("取消地址");
					cb_location.setTextColor(getResources().getColor(R.color.mBlack));
				}else {
					tv_location.setText("");
					cb_location.setText("添加地址");
					cb_location.setTextColor(getResources().getColor(R.color.mBlack));
				}
			}

		});

		  btn_confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (!TextUtils.isEmpty(et_memoname.getText().toString())) {
					 // int i=TaskData.memolist.size();
					    Memo newMemo = new Memo();
					   
						newMemo.setContent(et_memocontent.getText().toString()+"\n"+tv_location.getText().toString());
						newMemo.setName(et_memoname.getText().toString());
						newMemo.setCreatedtime(curTime);
						if (!TextUtils.isEmpty(et_memodeadlinetime.getText().toString().trim())){
						newMemo.setDeadlinetime(et_memodeadlinetime.getText().toString());
						}
						//Toast.makeText(getActivity(), uri.toString(), Toast.LENGTH_LONG).show();  
						//newMemo.setPic(Bitmap2Bytes(getBitmapFromUri(uri)));
					    if (uri!=null){
						newMemo.setPicUriStr(uri.toString());
					   
					    }
					    if (!TextUtils.isEmpty(tv_memoaddfile.getText().toString())){
						//newMemo.setFile(new File(tv_memoaddfile.getText().toString()));
						newMemo.setFileUriStr(tv_memoaddfile.getText().toString());
					
					    }
					    //SimpleDateFormat sdf = new SimpleDateFormat("yy-M-d HH:mm");
					    newMemo.setUsername(TaskData.user);
					    newMemo.save();
					  //TaskData.memolist.add(newMemo);
					  //  DataSupport.saveAll(TaskData.memolist);
					    int lastRowId=0;
						Cursor cur= DataSupport.findBySQL("select LAST_INSERT_ROWID() from memo");
						   if (cur!=null&&cur.getCount()>0){
					    	cur.moveToFirst();
							lastRowId=cur.getInt(0);
						   }	
						   String m_sn=TaskData.user+"m";
						   //newMemo.setSn(t_sn+TaskTool.addZero(lastRowId,4));
						   //newMemo.save();
						   
						   Cursor cr=DataSupport.findBySQL("select id as _id from memo where id="+lastRowId+" and username="+"'"+TaskData.user+"';");
							
							 if (cr.getCount()>0){
								   cr.moveToFirst();
								   do{
								   //int reminderno = cr.getInt(0);
								   ContentValues cv=new ContentValues();
								   cv.put("sn", m_sn+TaskTool.addZero(lastRowId,10));
								   DataSupport.update(Memo.class,cv,lastRowId);
								   Log.d(Tags,"add sn:"+m_sn+TaskTool.addZero(lastRowId,10));   
								   
								   }while(cr.moveToNext());
							   } 
					    				    
					    if (!TextUtils.isEmpty(et_memodeadlinetime.getText().toString())){
					    			   
					    	TaskTool.setReminder(getActivity(),et_memodeadlinetime.getText().toString(),
					    			"M"+lastRowId,et_memoname.getText().toString(),et_memocontent.getText().toString());
					    	
					   
					    }		    
					    //TaskData.cursor_memo.requery();
						//TaskData.adapter_memo.notifyDataSetChanged();
						TaskData.adapterUpdate();
		           	    dismiss();
						
				  
				  }else{
						  final Builder builder=new AlertDialog.Builder(getActivity());
						  builder.setMessage("名称不能为空");
						  builder.create().show();
					  }
				 
		      	}
		   }); 
				  			
		return v;
	}	
	
	public void setReminder(String deadline,String sourceId,String name,String content){
	final SharedPreferences  alarmSettingSP=getActivity().getSharedPreferences("userinfo",Context.MODE_PRIVATE);
	int freq= alarmSettingSP.getInt("freq", 0); 
	int interval= alarmSettingSP.getInt("alarminterval", 0); 
	int advance= alarmSettingSP.getInt("advance", 0); 
		
	Reminder reminder=new Reminder();
	//reminder.setAdvance(et_eventadvance.getText().toString());
    reminder.setSourceId(sourceId);
    reminder.setUsername(TaskData.user);
    reminder.setName(name);
	reminder.setContent(content);
	reminder.setCreatedtime(curTime);
	reminder.setDeadlinetime(deadline);
	//reminder.setFrequency(et_reminderfreq.getText().toString());
	SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
	String dead=deadline;
	long a=0;
	try {
		a=sdf.parse(dead).getTime();
	} catch (java.text.ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//a = TimeData.changeStrToTime_YYYY(et_eventdeadlinetime.getText().toString());
	long b=new Date().getTime();
	String timegap=TimeData.changeLongToTimeStr(a-b);
	//reminder.setRemainingtime(timegap);
	reminder.setAdvance(String.valueOf(advance));
	reminder.setFrequency(String.valueOf(freq));
	reminder.setAlarminterval(String.valueOf(interval));
	reminder.save();
	//TaskData.eventlist.add(reminder);
	//DataSupport.saveAll(TaskData.eventlist);
	Log.d(Tags+"|set alarm|",deadline+"\n"+timegap+"\n"+TimeData.changeStrToTime_YY(deadline)+"advance:"+advance);
	 new Alarm(getActivity(),
			   sourceId,
			   name,	 
		   TimeData.changeStrToMillisTime_YY(deadline),
  		   freq,
  		   advance,
  		   interval).alarmset();
	TaskData.cursor_reminder.requery();
	if (TaskData.adapter_reminder!=null){
	TaskData.adapter_reminder.notifyDataSetChanged();
	}
	//Toast.makeText(getActivity(),"a:"+a+"\n"+"b:"+b, Toast.LENGTH_SHORT).show();
    //new Alarm(getActivity(),a).alarmset();
	}
	
	 @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        // TODO Auto-generated method stub  
	        super.onActivityResult(requestCode, resultCode, data);  
	        //if(requestCode == CLICKEDFILE){
	        switch (requestCode){
	        
	        case CLICKEDFILE: 	
	        	if (data!=null){
	        		if(data.getStringExtra("return")!= null&&!TextUtils.isEmpty(data.getStringExtra("return"))){  
	        	      tv_memoaddfile.setText(data.getStringExtra("return"));
	                  //Toast.makeText(getActivity(), data.getStringExtra("return"),Toast.LENGTH_LONG).show();;
	        	      
	        	      
	        	    }else{
	        		Toast.makeText(getActivity(),data.getStringExtra("return"),Toast.LENGTH_SHORT).show();
	        	    }
	        	}	
	            break;
	        case REQUEST_CODE_PICK_IMAGE:             
	        	if (data!=null){          
		          if(data.getData()!= null){  
		            //use bundle to get data  
		        	uri = data.getData();
					  Glide.with(getActivity())
							  .load(uri.toString())
							  .diskCacheStrategy(DiskCacheStrategy.ALL)
							  //.centerCrop()
							  .dontAnimate()
							  .into(iv_memoaddpic);
					  //ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));//初始化完成
					//ImageLoader.getInstance().displayImage(uri.toString(),iv_memoaddpic);

					/*
		            Bundle bundle = data.getExtras();    
		                if (bundle != null) {                 
		                Drawable  photo = (Drawable) bundle.get("data"); //get bitmap  
		                //spath :生成图片取个名字和路径包含类型                              
		                //saveImage(Bitmap photo, String spath);
		                File sdFileDir=Environment.getExternalStorageDirectory();
		                File file1=new File(sdFileDir.getAbsoluteFile()+"/DCIM/ccc.bmp");
		                Toast toast = Toast.makeText(getActivity(),"带图片的Toast", Toast.LENGTH_LONG);
		                	   toast.setGravity(Gravity.CENTER, 0, 0);
		                	   LinearLayout toastView = (LinearLayout) toast.getView();
		                	   ImageView imageCodeProject = new ImageView(getActivity());
		                	   imageCodeProject.setImageDrawable(photo);
		                	   toastView.addView(imageCodeProject, 0);
		                	   toast.show();          
		               // savePic(photo,file1);
		                } else { 
		                	 
		                	
		                    //Toast.makeText(getActivity(), "err****", Toast.LENGTH_LONG).show();           
		                        
		                 }  
		                */ 
		            } else{
		            	Toast.makeText(getActivity(), "文件没找到", Toast.LENGTH_SHORT).show();   
					//Bitmap photoBmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
					//iv_memoaddpic.setImageURI(uri);
					//Log.d("uri ", photoBmp.toString());  
		            }
	        	 }
	               break;
				case REQUEST_CODE_LOCATION:
					if (data!=null){
							//use bundle to get data
							//uri = data.getData();
							//ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));//初始化完???
							//ImageLoader.getInstance().displayImage(uri.toString(),iv_memopic);
							//Toast.makeText(getActivity(),"lllll"+data.getStringExtra("location"), Toast.LENGTH_LONG).show();
							tv_location.setText(data.getStringExtra("location"));

					}else{
						sp = getActivity().getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
						String oldloc=sp.getString("address","暂无");
						tv_location.setText("最新地址:"+oldloc);
						//Toast.makeText(getActivity(), "文件没找到", Toast.LENGTH_SHORT).show();
					}
					break;
	        default: Toast.makeText(getActivity(), "文件没找到", Toast.LENGTH_SHORT).show();
	                 break;
                 
	         }
	    }  
	
	/*
	 @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  	// TODO Auto-generated method stub
		  if (requestCode == REQUEST_CODE_PICK_IMAGE ) {             
		        uri = data.getData();  
		                 //to do find the path of pic  
		        
		        Log.d("uri ", uri.toString());           
		        if(uri == null){  
		            //use bundle to get data  
		            Bundle bundle = data.getExtras();    
		                if (bundle != null) {                 
		                Drawable  photo = (Drawable) bundle.get("data"); //get bitmap  
		                //spath :生成图片取个名字和路径包含类型                              
		                //saveImage(Bitmap photo, String spath);
		                File sdFileDir=Environment.getExternalStorageDirectory();
		                File file1=new File(sdFileDir.getAbsoluteFile()+"/DCIM/ccc.bmp");
		                Toast toast = Toast.makeText(getActivity(),"带图片的Toast", Toast.LENGTH_LONG);
		                	   toast.setGravity(Gravity.CENTER, 0, 0);
		                	   LinearLayout toastView = (LinearLayout) toast.getView();
		                	   ImageView imageCodeProject = new ImageView(getActivity());
		                	   imageCodeProject.setImageDrawable(photo);
		                	   toastView.addView(imageCodeProject, 0);
		                	   toast.show();          
		               // savePic(photo,file1);
		                } else { 
		                	 
		                	
		                    Toast.makeText(getActivity(), "err****", Toast.LENGTH_LONG).show();           
		                        
		                 }  
		         } else{
		        	 
		        	  
					try {
						Bitmap photoBmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
						iv_memoaddpic.setImageURI(uri);
						Log.d("uri ", photoBmp.toString());           
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
             	   
		         }
	         } else{
	        	 Toast.makeText(getActivity(), "no recieved", Toast.LENGTH_LONG).show();           
                 
	         }
	}	
	 */
	


@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	DisplayMetrics dm = new DisplayMetrics();
	getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
	getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
}  
	
	
}
