package com.easygoal.achieve;

import org.apache.http.util.TextUtils;
import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class DialogFragment_MemoDetail extends DialogFragment {
	public static final int REQUEST_CODE_PICK_IMAGE = 1;
	public static final int CLICKEDFILE = 0;
	public static final int REQUEST_CODE_LOCATION= 2;
	String memocontent;
	String memodeadline;
	String memocreatedtime;
	String memoname;
	String ivsrc;
	String memofileuri;
	private Uri uri;
	TextView tv_memoaddfile;
	ImageView iv_memopic;
	TextView tv_location;
	CheckBox cb_location;
	private Cursor c;
	int editflag;
	int deadlineflag;
	private String sn;
	long id;
	final String Tags="DialogFragment_MemoDetail";

	public DialogFragment_MemoDetail(long id) {
		// TODO Auto-generated constructor stub
		this.id=id;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.dialogfg_memodetail, container);
		Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);
		Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
		Button btn_confirm = (Button) v.findViewById(R.id.btn_confirm);
		Button btn_edit = (Button) v.findViewById(R.id.btn_edit);
		final EditText et_memocontent = (EditText)v.findViewById(R.id.et_memocontent);
		final TextView tv_memoname= (TextView)v.findViewById(R.id.tv_memoname);
		final TextView tv_memocreatedtime= (TextView)v.findViewById(R.id.tv_memocreatedtime);
		final TextView tv_memodeadline = (TextView)v.findViewById(R.id.tv_memodeadline);
		iv_memopic = (ImageView)v.findViewById(R.id.iv_memoaddpic);
		tv_memoaddfile= (TextView)v.findViewById(R.id.tv_memoaddfile);
		cb_location=(CheckBox)v.findViewById(R.id.cb_location);
		TextView btn_memoaddpic=(TextView)v.findViewById(R.id.btn_memoaddpic);
		TextView btn_memoaddfile=(TextView)v.findViewById(R.id.btn_memoaddfile);

		editflag=0;
		deadlineflag=0;

		btn_closewin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 关闭对话???
				dismiss();
			}
		});


		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 关闭对话???
				dismiss();
			}
		});

		//final EditText title = (EditText)v.findViewById(R.id.et_title);
		// mactv.setBackgroundResource(R.drawable.shape_line_rectangle);
		if (id>=0){

			// c = DataSupport.findBySQL("select * from memo"+" where "+"sn"+"="+"'"+sn+"';");
			//query(TaskData.TdDB.TABLE_NAME_TaskMain, , null, null, null, null, null);
			c=DataSupport.findBySQL("select id as _id,name,picUriStr,fileUriStr,content,createdtime,deadlinetime,sn from memo"+" where "+
					"username"+"="+"'"+TaskData.user+"' and "+
					"id "+"="+"'"+id+"';");
			Log.d(Tags,String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
			if (c!=null&&c.getCount()>0){
				c.moveToFirst();
				memocreatedtime= c.getString(c.getColumnIndex("createdtime"));
				memoname= c.getString(c.getColumnIndex("name"));
				memocontent= c.getString(c.getColumnIndex("content"));
				memodeadline= c.getString(c.getColumnIndex("deadlinetime"));
				ivsrc= c.getString(2);
				memofileuri= c.getString(3);
				tv_memocreatedtime.setText(memocreatedtime);
				tv_memoname.setText("M"+id+" "+memoname);
				Log.d(Tags,memoname+memocontent+memodeadline);
				if (memocontent!=null&&!TextUtils.isEmpty(memocontent)){
					et_memocontent.setText(memocontent);
				}
				if (memodeadline!=null&&!TextUtils.isEmpty(memodeadline)){
					tv_memodeadline.setText(memodeadline);
				}

				if (memofileuri!=null&&!TextUtils.isEmpty(memofileuri)){
					tv_memoaddfile.setText(memofileuri);
				}
				if (ivsrc!=null&&!TextUtils.isEmpty(ivsrc)){
					//ImageLoader.getInstance().displayImage(ivsrc,iv_memopic);
					Glide.with(getActivity())
							.load(ivsrc)
							.diskCacheStrategy(DiskCacheStrategy.ALL)
							//.centerCrop()
							.dontAnimate()
							.into(iv_memopic);
				}


			}
		}



		//final int i=(Integer) TaskData.map_recordcommentnews.get("index");
		if (memocontent!=null){
			et_memocontent.setText(memocontent.trim());
			et_memocontent.setEnabled(false);
		}

		et_memocontent.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (et_memocontent.hasFocus() == false) {
					et_memocontent.setEnabled(false);;// 按钮变search
				}

			}
		});



		tv_memodeadline.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment_TimeInput dg_time=new DialogFragment_TimeInput(tv_memodeadline,1);
				TaskTool.showDialog(dg_time);
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

		btn_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				et_memocontent.setEnabled(true);
				et_memocontent.setClickable(true);
			}
		});

		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues cv = new ContentValues();
				cv.put("content", et_memocontent.getText().toString());
				cv.put("deadlinetime", tv_memodeadline.getText().toString());
				cv.put("fileUriStr",  tv_memoaddfile.getText().toString());
				if (uri!=null&&!TextUtils.isEmpty(uri.toString().trim())){
					cv.put("picUriStr", uri.toString());
				}
				String where="sn"+ " = ?";
				String[] whereas={sn};
				//int memoid = DataSupport.ffindBySQL("select id from memo where sn="+"'"+sn+"';").getString()getInt("id");
				long a=DataSupport.update(Memo.class, cv,id);

				String newdeadline = tv_memodeadline.getText().toString();

				if (newdeadline.equals(memodeadline)){
					deadlineflag=0;
				}else{
					deadlineflag=1;
				}
				Log.d(Tags, ""+deadlineflag);

				if (!TextUtils.isEmpty(memodeadline)&&deadlineflag==1){
					Cursor cr=DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId="+"'"+"M"+id+"'"+" and username="+"'"+TaskData.user+"';");

					if (cr.getCount()>0){
						cr.moveToFirst();

						do{
							cr.moveToFirst();
							String remindersn = cr.getString(1);
							Alarm.alarmCancel(getActivity(), remindersn);
							int reminderno = cr.getInt(0);
							DataSupport.delete(Reminder.class, reminderno);
							Log.d(Tags,"delete reminder "+reminderno);

						}while(cr.moveToNext());
					}
				}

				if (!TextUtils.isEmpty(tv_memodeadline.getText().toString())&&deadlineflag==1){

					TaskTool.setReminder(getActivity(),tv_memodeadline.getText().toString(),
							"M"+id,memoname,et_memocontent.getText().toString());

				}

				if (a==1){
					Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
					TaskData.adapterUpdate();
					dismiss();
				}else{
					Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
				}
			}

		});
		return v;
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
						//ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));//初始化完???
						//ImageLoader.getInstance().displayImage(uri.toString(),iv_memopic);
						Glide.with(getActivity())
								.load(uri.toString())
								.into(iv_memopic);

					} else{

						Toast.makeText(getActivity(), "文件没找到", Toast.LENGTH_SHORT).show();
					}
				}
				break;

			default: Toast.makeText(getActivity(), "文件没找到", Toast.LENGTH_SHORT).show();
				break;

		}
	}


	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
		getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	}
}