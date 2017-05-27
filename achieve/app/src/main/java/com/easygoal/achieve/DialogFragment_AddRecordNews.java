package com.easygoal.achieve;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.TextUtils;

import android.app.DialogFragment;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html.ImageGetter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class DialogFragment_AddRecordNews extends DialogFragment {

	String tasknews;
	Cursor c;
	int editflag;
	final String Tags="DialogFragment_AddRecordNews";
	public DialogFragment_AddRecordNews() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.dialogfg_addrecordnews, container);
		Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);
		Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
		Button btn_addnews= (Button) v.findViewById(R.id.btn_addnews);
		Button btn_editnews= (Button) v.findViewById(R.id.btn_editnews);
		editflag=0;

		btn_closewin.setOnClickListener(new OnClickListener() {

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
		Button btn_confirm = (Button) v.findViewById(R.id.btn_confirm);
		final MultiAutoCompleteTextView mact_news = (MultiAutoCompleteTextView)v.findViewById(R.id.mactv_news);
		//final TextView tv_news = (TextView)v.findViewById(R.id.tv_news);
		final EditText et_news = (EditText)v.findViewById(R.id.et_news);
		//final EditText title = (EditText)v.findViewById(R.id.et_title);
		// mactv.setBackgroundResource(R.drawable.shape_line_rectangle);
		final int i=(Integer) TaskData.map_recordcommentnews.get("index");
		tasknews=TaskData.map_recordcommentnews.get("value").toString();

		//final int i=(Integer) TaskData.map_recordcommentnews.get("index");
		et_news.setText(tasknews.trim());
		TaskTool.replaceTextFormat(getActivity(), et_news, TaskData.icon_header,TaskData.size_header,TaskData.size_header,0);
		et_news.setEnabled(false);

		mact_news.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					//InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
					//imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
				}
				//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
		});

		btn_editnews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et_news.setEnabled(true);
				et_news.setClickable(true);
				/*
				editflag=1;
				et_news.setText(tv_news.getText().toString());
				et_news.setVisibility(View.VISIBLE);
				*/
			}

		});

		et_news.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (et_news.hasFocus() == false) {
					et_news.setEnabled(false);;// 按钮变search
				}

			}
		});

		btn_addnews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 关闭对话框
				if (!TextUtils.isEmpty(mact_news.getText().toString().trim())){
					String temp_news=mact_news.getText().toString().trim();
                       /*
                       Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.dot);
	           		   ImageSpan imgSpan = new ImageSpan(getActivity(), b);
	           		   String icon_item="><";
	           		   SpannableString spanString = new SpannableString(icon_item+temp_news);
	           		   spanString.setSpan(imgSpan, 0, icon_item.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	           		   //mact_news.setText(spanString);
	           		   */
					if (!TextUtils.isEmpty(et_news.getText().toString().trim())){
						et_news.append("\n"+TaskTool.GetSpan(getActivity(), TaskData.icon_header,TaskData.size_header,TaskData.color_header));
						et_news.append(" "+temp_news);
						et_news.append("("+TaskTool.getCurTime()+")");
						//et_news.append("\n"+"   ("+TaskTool.getCurTime()+")");
					}else{
						et_news.append(TaskTool.GetSpan(getActivity(),TaskData.icon_header,TaskData.size_header,TaskData.color_header));
						et_news.append(" "+temp_news);
						et_news.append("("+TaskTool.getCurTime()+")");
						//et_news.append("\n"+"   ("+TaskTool.getCurTime()+")");
					}
					TaskTool.replaceTextFormat(getActivity(),et_news, TaskData.icon_header,TaskData.size_header,TaskData.color_header,0);
					mact_news.setText("");

				}
			}
		});

		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//TaskData.map_recordcommentnews.put("value",mact_news.getText().toString());
				TaskData.valuelist.get(i).setRECORD_DETAILS(et_news.getText().toString());
				TaskData.valuelist.get(i).setRECORD_AUTHOR(TaskData.user);
				TaskData.valuelist.get(i).setRECORD_CHANGED("true");
				TaskData.valuelistflag=1;
				Log.d(Tags,"news add"+i+TaskTool.getCurTime()+" "+mact_news.getText().toString());

				Toast.makeText(getActivity(), "保存进度后生效", Toast.LENGTH_SHORT).show();

				TaskData.map_recordcommentnews.clear();
				TaskData.adapterUpdate();
				dismiss();
				//dismiss();

			}
		});
		return v;
	}

	public void hightlight(TextView tv,String target){
		String temp=tv.getText().toString();
		SpannableStringBuilder spannable = new SpannableStringBuilder(temp);
		CharacterStyle span=null;

		Pattern p = Pattern.compile(target);
		Matcher m = p.matcher(temp);
		while (m.find()) {
			span = new ForegroundColorSpan(Color.RED);//需要重复！
			//span = new ImageSpan(drawable,ImageSpan.XX);//设置现在图片
			spannable.setSpan(span, m.start(),  m.end(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		tv.setText(spannable);
	}

	ImageGetter imgGetter = new ImageGetter() {

		@Override
		public Drawable getDrawable(String source) {
			// TODO Auto-generated method stub
			int id = Integer.parseInt(source);
			Drawable d = getResources().getDrawable(id);
			d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			return d;
		}
	};

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
		getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	}
}
