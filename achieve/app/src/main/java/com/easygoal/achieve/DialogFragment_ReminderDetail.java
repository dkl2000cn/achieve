package com.easygoal.achieve;

import org.litepal.crud.DataSupport;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class DialogFragment_ReminderDetail extends DialogFragment {

	String remindercontent;
	String remindersourceId;
	String reminderdeadline;
	String remindertitle;
	private Cursor c;
	int editflag;
	private String sn;
	private long id;
	final String Tags="DialogFragment_ReminderDetail";

	public DialogFragment_ReminderDetail(String sn,long id) {
		// TODO Auto-generated constructor stub
		this.sn=sn;
		this.id=id;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.dialogfg_reminderdetail, container);
		Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);
		Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
		Button btn_confirm = (Button) v.findViewById(R.id.btn_confirm);
		Button btn_edit = (Button) v.findViewById(R.id.btn_edit);
		final EditText et_reminder = (EditText)v.findViewById(R.id.et_reminder);
		final TextView tv_remindertitle = (TextView)v.findViewById(R.id.tv_remindertitle);
		final TextView tv_remindersource = (TextView)v.findViewById(R.id.tv_remindersource);
		final TextView tv_reminderdeadline = (TextView)v.findViewById(R.id.tv_reminderdeadline);

		editflag=0;

		btn_closewin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dismiss();
			}
		});


		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dismiss();
			}
		});

		//final EditText title = (EditText)v.findViewById(R.id.et_title);
		// mactv.setBackgroundResource(R.drawable.shape_line_rectangle);
		if (sn!=null){

			// c = DataSupport.findBySQL("select * from reminder"+" where "+"sn"+"="+"'"+sn+"';");
			//query(TaskData.TdDB.TABLE_NAME_TaskMain, , null, null, null, null, null);
			c=DataSupport.findBySQL("select  id as _id,name,sourceId,deadlinetime,frequency,advance,alarminterval,piaction,sn,content from reminder"+" where "+
					"username"+"="+"'"+TaskData.user+"' and "+
					"sn"+"="+"'"+sn+"';");

			Log.d(Tags,String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
			if (c!=null&&c.getCount()>0){
				c.moveToFirst();
				remindersourceId= c.getString(2);
				remindertitle= c.getString(c.getColumnIndex("name"));
				remindercontent= c.getString(c.getColumnIndex("content"));
				reminderdeadline= c.getString(c.getColumnIndex("deadlinetime"));
				et_reminder.setText(remindercontent);
				tv_reminderdeadline.setText(reminderdeadline);
				tv_remindertitle.setText(remindertitle);
				String sourcecode = null;

				if (remindersourceId.substring(0, 1).equals("M")){
					sourcecode="备忘录";
				}
				if (remindersourceId.subSequence(0, 1).equals("T")){
					sourcecode="任务圈";
				}
				if (remindersourceId.subSequence(0, 1).equals("R")){
					sourcecode="倒计时";
				}

				tv_remindertitle.setText(remindersourceId+" "+remindertitle);
				tv_remindersource.setText("创建来自:"+sourcecode);
			}
		}



		//final int i=(Integer) TaskData.map_recordcommentnews.get("index");
		if (remindercontent!=null){
			et_reminder.setText(remindercontent.trim());
			et_reminder.setEnabled(false);
		}

		et_reminder.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (et_reminder.hasFocus() == false) {
					et_reminder.setEnabled(false);;// 鎸夐挳鍙榮earch
				}

			}
		});

		tv_reminderdeadline.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment_TimeInput dg_time=new DialogFragment_TimeInput(tv_reminderdeadline,1);
				TaskTool.showDialog(dg_time);
			}

		});

		btn_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				et_reminder.setEnabled(true);
				et_reminder.setClickable(true);
			}
		});

		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues cv = new ContentValues();
				cv.put("content", et_reminder.getText().toString());
				cv.put("deadlinetime", tv_reminderdeadline.getText().toString());
				String where="sn"+ " = ?";
				String[] whereas={sn};
				//int reminderid = DataSupport.ffindBySQL("select id from reminder where sn="+"'"+sn+"';").getString()getInt("id");
				long a=DataSupport.update(Reminder.class, cv,id);
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
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
		getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	}
}
