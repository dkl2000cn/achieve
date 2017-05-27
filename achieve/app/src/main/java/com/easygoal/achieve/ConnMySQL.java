package com.easygoal.achieve;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ConnMySQL {
	SingleVolleyRequestQueue requestQueue;
	Context context;
	private String Tags="ConnMySQL";
	public ConnMySQL(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		requestQueue=SingleVolleyRequestQueue.getInstance(context);
	}


	public void SynLoginRequestPost(String servletName,Map params){

		final Map usermap=params;
		final String url = TaskData.hostname+servletName;
		final String username=usermap.get("username").toString();
		final String phoneNo=usermap.get("phoneNo").toString();
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		//RequestQueue requestQueue=Volley.newRequestQueue(context);

		JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
		JsonArray jolist=new JsonArray();
		jolist.add(jostring);
		Log.d(Tags,"login data"+jolist.toString());

		mGsonArrayRequest gsonObjectRequest = new mGsonArrayRequest(url,jolist,"7",new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {

				if(response != null){

					Log.d(Tags,"json back"+response.toString());
					if (response.toString().contains(phoneNo)){
						conSyn_db();
						TaskData.onlinestatus="(在线)";
						Toast.makeText(context, "数据同步已完成",Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(context, "请先登陆或注册",Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(context,LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						context.startActivity(intent);

					}

				}else{

					Toast.makeText(context, "暂无响应,请离线使用",Toast.LENGTH_SHORT).show();

				}

			}
		},new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// responseText.setText(error.getMessage());
				Toast.makeText(context, "网络异常,暂时无法同步",Toast.LENGTH_SHORT).show();

			}
		});

		requestQueue.add(gsonObjectRequest);
		requestQueue.start();

		return ;
	}

	public void initData(){
		ConnectivityManager con = (ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		if(wifi|internet){

			SharedPreferences sp = context.getSharedPreferences("userinfo" , context.MODE_PRIVATE);
			String phoneNo=TaskData.user;
			String username=sp.getString("username", "");
			if (!org.apache.http.util.TextUtils.isEmpty(TaskData.user)&&TaskTool.isMobileNO11(TaskData.user)) {
				// conSyn_db();

				//Cursor cursor_rating = DataSupport.findBySQL("select * from userratingbean where username="+"'"+TaskData.user+"'");
				//Cursor cursor_reminder = DataSupport.findBySQL("select * from reminder where username="+"'"+TaskData.user+"'");
				//Cursor cursor_memo= DataSupport.findBySQL("select * from memo where username="+"'"+TaskData.user+"'");
				initConSyn_db();
				//Cursor cursor_taskrec = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord + " where " + TaskData.TdDB.TASK_USER + "=?", new String[]{TaskData.user});
				//InitDataGsonArrayRequestPostSuccess("TaskrecServlet", cursor_taskrec);
				//TaskData.cursor_alltasks=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
				//InitDataGsonArrayRequestPostSuccess("TaskmainServlet",TaskData.cursor_alltasks);

				//InitDataGsonArrayRequestPostSuccess("MemoServlet",cursor_memo);
				//conn.InitDataGsonArrayRequestPostSuccess("ReminderServlet",cursor_reminder);
				//InitDataGsonArrayRequestPostSuccess("RatingServlet",cursor_rating);

			}
		}
	};

	public void quitConSyn_db() {
		ConnectivityManager con = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		if (wifi | internet) {
			if (!org.apache.http.util.TextUtils.isEmpty(TaskData.user) && TaskTool.isMobileNO11(TaskData.user)) {
				conSyn_db();
			}
		}
	}

	public void initConSyn_db(){

		//Cursor cursor_rating = DataSupport.findBySQL("select * from userratingbean where username="+"'"+TaskData.user+"'");
		//Cursor cursor_reminder = DataSupport.findBySQL("select * from reminder where username="+"'"+TaskData.user+"'");
		//
		//TaskData.cursor_alltasks=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
		//Log.d("cursor alltasks", ""+TaskData.cursor_alltasks.getCount());
		Cursor cursor_alltasks=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
		Cursor cursor_rating = DataSupport.findBySQL("select * from userratingbean where username="+"'"+TaskData.user+"'");
		Cursor cursor_reminder = DataSupport.findBySQL("select * from reminder where username="+"'"+TaskData.user+"'");
		Cursor cursor_memo = DataSupport.findBySQL("select * from memo where username="+"'"+TaskData.user+"'");

		//TaskData.cursor_alltasks=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
		//Log.d("cursor alltasks", ""+TaskData.cursor_alltasks.getCount());
		if (cursor_alltasks!=null) {
			try {
			  InitDataGsonArrayRequestPostSuccess("TaskmainServlet", cursor_alltasks);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor_alltasks != null) {
					cursor_alltasks.close();
				}
			}
		}

		if (cursor_memo!=null) {
			try {
				InitDataGsonArrayRequestPostSuccess("MemoServlet", cursor_memo);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor_memo != null) {
					cursor_memo.close();
				}
			}
		}
        /*
		if (TaskData.cursor_reminder!=null) {
			TaskData.cursor_reminder.requery();
			GsonArrayRequestPostSuccess("ReminderServlet", TaskData.cursor_reminder);
		}*/
        /*
		if (cursor_reminder!=null) {
			try {
				InitDataGsonArrayRequestPostSuccess("ReminderServlet", cursor_reminder);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor_reminder != null) {
					cursor_reminder.close();
				}
			}
		}*/
		if (cursor_rating!=null) {
			try {
				InitDataGsonArrayRequestPostSuccess("RatingServlet", cursor_rating);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor_rating != null) {
					cursor_rating.close();
				}
			}
		}


	}

	public void conSyn_db(){

		Cursor cursor_alltasks=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
		Cursor cursor_rating = DataSupport.findBySQL("select * from userratingbean where username="+"'"+TaskData.user+"'");
		Cursor cursor_reminder = DataSupport.findBySQL("select * from reminder where username="+"'"+TaskData.user+"'");
		Cursor cursor_memo = DataSupport.findBySQL("select * from memo where username="+"'"+TaskData.user+"'");
	    final Cursor cursor_taskrec = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord + " where " + TaskData.TdDB.TASK_USER + "=?", new String[]{TaskData.user});
		//TaskData.cursor_alltasks=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
		//Log.d("cursor alltasks", ""+TaskData.cursor_alltasks.getCount());
		if (cursor_alltasks!=null) {
			try {
				  GsonArrayRequestPostSuccess("TaskmainServlet", cursor_alltasks);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor_alltasks != null) {
					cursor_alltasks.close();
				}
			}
		}

		if (cursor_memo!=null) {
			try {
				GsonArrayRequestPostSuccess("MemoServlet", cursor_memo);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor_memo != null) {
					cursor_memo.close();
				}
			}
		}
        /*
		if (TaskData.cursor_reminder!=null) {
			TaskData.cursor_reminder.requery();
			GsonArrayRequestPostSuccess("ReminderServlet", TaskData.cursor_reminder);
		}*/

		if (cursor_reminder!=null) {
			try {
				GsonArrayRequestPostSuccess("ReminderServlet", cursor_reminder);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor_reminder != null) {
					cursor_reminder.close();
				}
			}
		}
		if (cursor_rating!=null) {
			try {
				GsonArrayRequestPostSuccess("RatingServlet", cursor_rating);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor_rating != null) {
					cursor_rating.close();
				}
			}
		}
        /*
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (cursor_taskrec!=null) {
					try {
						 GsonArrayRequestPostSuccess("TaskrecServlet",cursor_taskrec);
					}catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (cursor_taskrec != null) {
							cursor_taskrec.close();
						}
					}
				}
			}
		},1000);*/
	}



	void GsonArrayRequestPostSuccess(final String servletName,Cursor cursor){
		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.105:8090/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		//RequestQueue requestQueue;
		String url = TaskData.hostname+servletName;
		Log.d(Tags,"json send"+url );
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		//requestQueue=Volley.newRequestQueue(context);
		if (cursor!=null&&cursor.getCount()>0){
			mGsonArrayRequest gsonArrayRequest;
			JsonArray jolist=new JsonArray();
			//jolist = new JSONArray();
			cursor.moveToFirst();
			int t=0;
			do{
				Map<String, String> params = new HashMap<String, String>();
				//params.put("_id", ca.getString(1));
				for (int i=1;i<cursor.getColumnCount();i++){
					params.put(cursor.getColumnName(i), cursor.getString(i));
					//Log.d("ca"+i, ca.getColumnName(i)+"       "+ca.getString(i));

				};

				JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
				// Log.d("JsonObject"+t, jostring.toString());
				t++;
				jolist.add(jostring);

			} while(cursor.moveToNext());
			//JSONObject jsonObject = new JSONObject(params);
			Log.d(Tags,"JsonArray"+t+jolist.toString());

			gsonArrayRequest = new mGsonArrayRequest(url,jolist,"1",new Response.Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray response) {
					if (response != null&&!TextUtils.isEmpty(response.toString().trim())) {

						if (servletName=="TaskmainServlet"){
							if (response.toString().contains(TaskData.user)) {
								Log.d(Tags, "taskmain json back" + response.toString());
								GsonArray2Sqlite(response, TaskData.TdDB.TABLE_NAME_TaskMain, "_sn");
								//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
								Log.d(Tags, "GsonArray2Sqlite" + "taskmain ok");
								//TaskData.adapterUpdate();
								final Cursor cursor_taskrec = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord + " where " + TaskData.TdDB.TASK_USER + "=?", new String[]{TaskData.user});
								GsonArrayRequestPostSuccess("TaskrecServlet", cursor_taskrec);
								Log.d(Tags, "run taskrecServlet ok");
							}
						}
						if (servletName=="TaskrecServlet"){
							if (response.toString().contains(TaskData.user)) {
								Log.d(Tags, "taskrec json back" + response.toString());
								GsonArray2Sqlite(response, TaskData.TdDB.TABLE_NAME_TaskRecord, "r_sn");
								//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
								Log.d(Tags, "GsonArray2Sqlite" + "taskrec ok");
							}
						}
						if (servletName=="MemoServlet"){
							if (response.toString().contains(TaskData.user)) {
								Log.d(Tags, "memo json back" + response.toString());
								Memo newMemo = new Memo();
								GsonArray2LitePal(response, "sn", "memo", Memo.class);
								//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
								Log.d(Tags, "GsonArray2LitePal" + "memo ok");
							}

						}

						if (servletName=="ReminderServlet"){
							if (response.toString().contains(TaskData.user)) {
								//Reminder newReminder=new Reiminder();
								Log.d(Tags + "|reminder json back|", response.toString());
								GsonArray2LitePal(response, "sn", "reminder", Reminder.class);
								//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
								Log.d(Tags + "|GsonArray2LitePal|", "reminder ok");
							}

						}
						if (servletName=="RatingServlet"){
							if (response.toString().contains(TaskData.user)) {
								//UserRatingBean newRating=new UserRatingBean();
								GsonArray2LitePal(response, "sn", "userratingbean", UserRatingBean.class);
								//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
								Log.d(Tags + "|GsonArray2LitePal|", "userratingbean ok");
							}

						}

					}else{
						//Toast.makeText(getApplicationContext(), "无数据",Toast.LENGTH_SHORT).show();
						Log.d(Tags, "无数据");
					}

				}
			},new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					// responseText.setText(error.getMessage());
					connFailureNoWarning( error);
				}
			});
			requestQueue.add(gsonArrayRequest);
			requestQueue.start();

		}
	}

	void LitePalDelByGsonArrayRequestPost(String servletName,String tbname,String sn){
		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		String url = TaskData.hostname+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";

		//RequestQueue requestQueue=Volley.newRequestQueue(context);
		String queryStr="select * from "+tbname+" where "+"sn"+"="+"'"+sn+"';";
		//Toast.makeText(context, queryStr, Toast.LENGTH_SHORT).show();
		Cursor ca=DataSupport.findBySQL(queryStr);
		if (ca!=null&&ca.getCount()>0){

			mGsonArrayRequest DelGsonArrayRequest;
			JsonArray jolist=new JsonArray();
			//jolist = new JSONArray();
			ca.moveToFirst();

			int t=0;
			do{
				Map<String, String> params = new HashMap<String, String>();
				for (int i=0;i<ca.getColumnCount();i++){


					params.put(ca.getColumnName(i), ca.getString(i));


				};

				JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
				// Log.d("JsonObject"+t, jostring.toString());
				t++;
				jolist.add(jostring);

			} while(ca.moveToNext());
			//JSONObject jsonObject = new JSONObject(params);
			Log.d("JsonArray"+t, jolist.toString());
			//Toast.makeText(context, jolist.toString(), Toast.LENGTH_SHORT).show();
			DelGsonArrayRequest = new mGsonArrayRequest(url,jolist,"3",new Response.Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray response) {
					if(response != null){
						Log.d("json back", response.toString());
						//GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskMain);
						;
						if (response.toString().contains("OK")){
							Toast.makeText(context, "删除成功",Toast.LENGTH_SHORT).show();
							TaskData.adapterUpdate();
						}else{
							Toast.makeText(context, "删除失败",Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(context, "系统无响应",Toast.LENGTH_SHORT).show();
					}

				}
			},new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					// responseText.setText(error.getMessage());
					connFailureNoWarning(error);
				}
			});

			requestQueue.add(DelGsonArrayRequest);
			requestQueue.start();
		}

	}

	void LitePalClearByGsonArrayRequestPost(String servletName,Map params){
		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		String url = TaskData.hostname+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";

		//RequestQueue requestQueue=Volley.newRequestQueue(context);
		//String clearResult = "0";

		//String sql = "delete from task_main ";
		//TaskData.db_TdDB.execSQL(sql);
		//Toast.makeText(context, "no data",Toast.LENGTH_SHORT).show();

		JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
		JsonArray jolist = new JsonArray();
		jolist.add(jostring);

		mGsonArrayRequest ClearGsonArrayRequest = new mGsonArrayRequest(url,jolist,"4",new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				if(response != null){
					if (response.toString().contains("OK")){
						Toast.makeText(context, "清除成功",Toast.LENGTH_SHORT).show();
						TaskData.adapterUpdate();
					}else{
						Toast.makeText(context, "清除失败",Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(context, "系统无响应",Toast.LENGTH_SHORT).show();
				}

			}
		},new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// responseText.setText(error.getMessage());
				connFailureNoWarning(error);
			}
		});

		requestQueue.add(ClearGsonArrayRequest);
		requestQueue.start();
	}

	void InitDataGsonArrayRequestPostSuccess(final String servletName, Cursor cursor){
		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.105:8090/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		final String Tags="InitData";
		mGsonArrayRequest gsonArrayRequest;
		String url = TaskData.hostname+servletName;
		Log.d(Tags,"json send"+url );
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		//RequestQueue requestQueue=Volley.newRequestQueue(context);
		if (cursor==null||cursor.getCount()==0){

			JsonArray jolist=new JsonArray();
			Map<String, String> params = new HashMap<String, String>();
			//params.put("_id", ca.getString(1));
			/*
			if (!url.equals(TaskData.hostname+"TaskrecServlet")) {
				params.put("username", TaskData.user);
				//Log.d("ca"+i, ca.getColumnName(i)+"       "+ca.getString(i));
			}else{
				params.put("username", TaskData.user);
			}*/
			params.put("username", TaskData.user);
			JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
			// Log.d("JsonObject"+t, jostring.toString());
			jolist.add(jostring);

			gsonArrayRequest = new mGsonArrayRequest(url, jolist, "7", new Response.Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray response) {
					if (response != null && response.toString().contains(TaskData.user)) {

						if (servletName == "TaskmainServlet") {
							Log.d(Tags, "taskmain json back" + response.toString());
							if (response.toString().contains(TaskData.user)) {
								GsonArray2Sqlite(response, TaskData.TdDB.TABLE_NAME_TaskMain, "_sn");
								//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
								Log.d(Tags, "GsonArray2Sqlite" + "taskmain ok");
								//TaskData.adapterUpdate();
								final Cursor cursor_taskrec = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord + " where " + TaskData.TdDB.TASK_USER + "=?", new String[]{TaskData.user});
								InitDataGsonArrayRequestPostSuccess("TaskrecServlet", cursor_taskrec);
								Log.d(Tags, "run taskrecServlet ok");
							}
						}

						if (servletName == "MemoServlet") {
							Log.d(Tags, "memo json back" + response.toString());
							if (response.toString().contains(TaskData.user)) {
								GsonArray2LitePal(response, "sn", "memo", Memo.class);
								//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
								Log.d(Tags, "GsonArray2LitePal" + "memo ok");
							}
						}
						if (servletName == "ReminderServlet") {
							//Reminder newReminder=new Reiminder();
							Log.d(Tags + "|reminder json back|", response.toString());
							if (response.toString().contains(TaskData.user)) {
								GsonArray2LitePal(response, "sn", "reminder", Reminder.class);
								//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
								Log.d(Tags + "|GsonArray2LitePal|", "reminder ok");
							}
						}
						if (servletName == "RatingServlet") {
							//UserRatingBean newRating=new UserRatingBean();
							if (response.toString().contains(TaskData.user)) {
								GsonArray2LitePal(response, "sn", "userratingbean", UserRatingBean.class);
								//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
								Log.d(Tags + "|GsonArray2LitePal|", "userratingbean ok");
							}

						}if (servletName == "TaskrecServlet") {
							Log.d(Tags, "taskrec json back" + response.toString());
							if (response.toString().contains(TaskData.user)) {
								GsonArray2Sqlite(response, TaskData.TdDB.TABLE_NAME_TaskRecord, "r_sn");
								//Toast.makeText(context, "record " + response.toString(), Toast.LENGTH_SHORT).show();
								Log.d(Tags, "GsonArray2Sqlite" + "taskrec ok");
							}
						}

						//Toast.makeText(getApplicationContext(), "载入数据完成", Toast.LENGTH_SHORT).show();
					} else {
						//Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
						Log.d(Tags, "无数据");
					}

				}

			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					// responseText.setText(error.getMessage());
					connFailureNoWarning(error);
				}
			});

			requestQueue.add(gsonArrayRequest);
			requestQueue.start();
		}else{
			JsonArray jolist=new JsonArray();
			Map<String, String> params = new HashMap<String, String>();
			//params.put("_id", ca.getString(1));
			if (!url.equals(TaskData.hostname+"TaskrecServlet")) {
				params.put("username", TaskData.user);
				//Log.d("ca"+i, ca.getColumnName(i)+"       "+ca.getString(i));
			}else{
				params.put("username", TaskData.user);
			}
			JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
			// Log.d("JsonObject"+t, jostring.toString());
			jolist.add(jostring);

			gsonArrayRequest = new mGsonArrayRequest(url, jolist, "7", new Response.Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray response) {
					if (response != null && response.toString().contains(TaskData.user)) {

						if (servletName == "TaskmainServlet") {
							Log.d(Tags, "taskmain json back" + response.toString());
							GsonArray2Sqlite(response, TaskData.TdDB.TABLE_NAME_TaskMain, "_sn");
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d(Tags, "GsonArray2Sqlite" + "taskmain ok");
							//TaskData.adapterUpdate();
						}
						if (servletName == "TaskrecServlet") {
							Log.d(Tags, "taskrec json back" + response.toString());
							GsonArray2Sqlite(response, TaskData.TdDB.TABLE_NAME_TaskRecord, "r_sn");
							//Toast.makeText(getApplicationContext(), "record " + response.toString(), Toast.LENGTH_SHORT).show();
							Log.d(Tags, "GsonArray2Sqlite" + "taskrec ok");
						}
						if (servletName == "MemoServlet") {
							Log.d(Tags, "memo json back" + response.toString());
							//Memo newMemo = new Memo();
							GsonArray2LitePal(response, "sn", "memo", Memo.class);
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d(Tags, "GsonArray2LitePal" + "memo ok");

						}

						if (servletName == "ReminderServlet") {
							//Reminder newReminder=new Reiminder();
							Log.d(Tags + "|reminder json back|", response.toString());
							//GsonArray2LitePal(response, "sn", "reminder", Reminder.class);
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d(Tags + "|GsonArray2LitePal|", "reminder ok");
						}
						if (servletName == "RatingServlet") {
							//UserRatingBean newRating=new UserRatingBean();
							//GsonArray2LitePal(response,"id","userratingbean",UserRatingBean.class);
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d(Tags + "|GsonArray2LitePal|", "userratingbean ok");

						}
						//Toast.makeText(getApplicationContext(), "载入数据完成", Toast.LENGTH_SHORT).show();
					} else {
						//Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
						Log.d(Tags, "无数据");
					}

				}

			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					// responseText.setText(error.getMessage());
					connFailureNoWarning(error);
				}
			});

			requestQueue.add(gsonArrayRequest);
			 requestQueue.start();
		}

	}

	void DelByGsonArrayRequestPost(String servletName,String tbname,String sn){
		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		String url = TaskData.hostname+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";

		//RequestQueue requestQueue=Volley.newRequestQueue(context);
		String queryStr="select * from "+tbname+" where "+TaskData.TdDB.TASK_SN+"="+"'"+sn+"'";
		//Toast.makeText(context, queryStr, Toast.LENGTH_SHORT).show();
		Cursor ca=TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+TaskData.TdDB.TASK_SN+"="+"'"+sn+"'", null);
		if (ca!=null&&ca.getCount()>0){

			mGsonArrayRequest DelGsonArrayRequest;
			JsonArray jolist=new JsonArray();
			//jolist = new JSONArray();
			ca.moveToFirst();

			int t=0;
			do{
				Map<String, String> params = new HashMap<String, String>();
				for (int i=0;i<ca.getColumnCount();i++){


					params.put(ca.getColumnName(i), ca.getString(i));


				};

				JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
				// Log.d("JsonObject"+t, jostring.toString());
				t++;
				jolist.add(jostring);

			} while(ca.moveToNext());
			//JSONObject jsonObject = new JSONObject(params);
			Log.d("JsonArray"+t, jolist.toString());
			//Toast.makeText(context, jolist.toString(), Toast.LENGTH_SHORT).show();
			DelGsonArrayRequest = new mGsonArrayRequest(url,jolist,"3",new Response.Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray response) {
					if(response != null){
						Log.d("json back", response.toString());
						//GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskMain);
						;
						if (response.toString().contains("OK")){
							Toast.makeText(context, "删除成功",Toast.LENGTH_SHORT).show();
							TaskData.adapterUpdate();
						}else{
							Toast.makeText(context, "删除失败",Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(context, "系统无响应",Toast.LENGTH_SHORT).show();
					}

				}
			},new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					// responseText.setText(error.getMessage());
					connFailureNoWarning(error);
				}
			});

			requestQueue.add(DelGsonArrayRequest);
			requestQueue.start();
		}

	}

	void ClearByGsonArrayRequestPost(String servletName,Map params){
		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		String url = TaskData.hostname+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";

		//RequestQueue requestQueue=Volley.newRequestQueue(context);

		JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
		JsonArray jolist = new JsonArray();
		jolist.add(jostring);

		mGsonArrayRequest ClearGsonArrayRequest = new mGsonArrayRequest(url,jolist,"4",new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				if(response != null){
					if (response.toString().contains("OK")){
						Toast.makeText(context, "清除成功",Toast.LENGTH_SHORT).show();
						TaskData.adapterUpdate();
					}else{
						Toast.makeText(context, "清除失败",Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(context, "系统无响应",Toast.LENGTH_SHORT).show();
				}

			}
		},new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// responseText.setText(error.getMessage());
				connFailureNoWarning(error);
			}
		});

		requestQueue.add(ClearGsonArrayRequest);
		requestQueue.start();
	}

	void GsonArray2Sqlite(JSONArray ja,String tbname,String index){
		String Tags="|GsonArray2Sqlite|"+tbname+"|";
		JSONObject jo;
		ContentValues cv;
		Cursor cursor=null;
		String key;
		Object u;
		Iterator<String> it;
		String indexvalue;
		String whereAs;
		if (ja!=null&&ja.length()>0) {
			try {
				for (int i = 0; i < ja.length(); i++) {
					try {
						jo = ja.getJSONObject(i);
						it = jo.keys();
						//String indexkey = (String) it.next();
						indexvalue = jo.getString(index);

						cv = new ContentValues();
						//it.next();
						do {
							key = (String) it.next();
							u = jo.get(key);
							//Log.d(key, u.toString());
							if (!key.equals("_id")) {
								cv.put(key, u.toString());
							}
						} while (it.hasNext());
						cursor = TaskData.db_TdDB.rawQuery("select * from " + tbname + " where " + "username=" + "'" + TaskData.user + "'" + " and " + index + "=" + "'" + indexvalue + "'", null);
						if (cursor != null && cursor.getCount() == 0) {
							//Log.d("|insert backdata|", "diff:"+i);
							long insertcode = TaskData.db_TdDB.insert(tbname, null, cv);
							Log.d(Tags + "|insert backdata|", "ok" + insertcode);
						} else {
							/*
							if (cursor != null && cursor.getCount() > 0) {
								cursor.moveToFirst();
								whereAs = index + "=?";
								String[] whereValue = {cursor.getString(cursor.getColumnIndex(index))};

								int updatecode = TaskData.db_TdDB.update(tbname, cv, whereAs, whereValue);
								Log.d(Tags + "|udpate backdata|", "ok:" + i + " " + updatecode);
							}*/
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
	}

	 Void QueryRequestPost(String servletName, Map params){
        final Map userMap=null;
		final Handler handler;
		String url = TaskData.hostname+servletName;
		JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
		JsonArray jolist=new JsonArray();
		jolist.add(jostring);

		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				String resp = msg.getData().getString("response");
				Toast.makeText(context,resp,Toast.LENGTH_SHORT).show();
			}
		};

		mGsonArrayRequest gsonObjectRequest = new mGsonArrayRequest(url,jolist,"5",new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(final JSONArray response) {
				if(response != null){
					if (response.toString().contains("phoneNo")){
						new Thread(new Runnable() {
							@Override
							public void run() {
								Bundle bundle=new Bundle();
								bundle.putString("reponse",response.toString().trim());
								Message msg=new Message();
								msg.setData(bundle);
								handler.sendMessage(msg);
							}
						}).start();

					}
					Log.d("json back", response.toString());

				}else{
					Toast.makeText(context, "no data",Toast.LENGTH_SHORT).show();
				}

			}
		},new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// responseText.setText(error.getMessage());
				connFailureNoWarning(error);
			}
		});

		requestQueue.add(gsonObjectRequest);
		requestQueue.start();

		return null;
	}


	void UpdateUserRequestPost(String servletName, Map params){

		//RequestQueue requestQueue;
		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		String url = TaskData.hostname+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		//requestQueue=Volley.newRequestQueue(context);

		JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
		JsonArray jolist=new JsonArray();
		jolist.add(jostring);

		mGsonArrayRequest gsonObjectRequest = new mGsonArrayRequest(url,jolist,"6",new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				if(response != null){
					//Log.d("json back", "ddd "+response.toString());

					//Toast.makeText(mContext, "back ok",Toast.LENGTH_SHORT).show();
					//TaskData.adapterUpdate();

					//EventBus.getDefault().post(new UserResponseEvent(response.toString()));
					Log.d("json back", response.toString());

				}else{
					Toast.makeText(context, "no data",Toast.LENGTH_SHORT).show();
				}

			}
		},new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// responseText.setText(error.getMessage());
				connFailureNoWarning(error);
			}
		});

		requestQueue.add(gsonObjectRequest);
		requestQueue.start();

		return;
	}

	void GsonArray2LitePal(JSONArray ja,String rowId,String tbname,Class classname){
		String Tags="|GsonArray2LitePal|"+tbname+"|";
		JSONObject jo;
		ContentValues cv;
		Cursor cursor=null;
		String key;
		Object u;
		Iterator<String> it;
		String indexvalue;
		String whereAs;
		if (ja!=null&&ja.length()>0) {
			try {
				for (int i = 0; i < ja.length(); i++) {

					try {
						jo = ja.getJSONObject(i);
						it = jo.keys();

						String rowIdvalue = jo.get(rowId).toString();

						//cv.put(rowId, rowIdvalue);
						cv = new ContentValues();
						do {
							key = (String) it.next();
							u = jo.get(key);
							if (!key.equals("_id")) {
								cv.put(key, u.toString());
							}
						} while (it.hasNext());
						//Cursor cursor = DataSupport.findBySQL("select * from "+tbname+" where "+"username="+"'"+TaskTool.getPhoneNumber(getApplicationContext())+"'"+" and "+rowId+"="+"'"+rowIdvalue+"';");
						cursor = DataSupport.findBySQL("select * from " + tbname + " where " + rowId + "=" + "'" + rowIdvalue + "';");

						if (cursor != null && cursor.getCount() == 0) {
							String root = context.getFilesDir().getAbsolutePath();
							Log.d(Tags + "|root|", root);
							//String path ="data/data/com.example.easygoal/"+tbname;
							//String path=root.replace("files", "databases")+File.separator+tbname;
							String path = root.replace("files", tbname);
							Log.d(Tags + "|path|", path);
							//SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
							//SQLiteDatabase db=this.openOrCreateDatabase("Memo.db", Context.MODE_PRIVATE, null);
							long insertid = TaskData.db.insert(tbname, null, cv);
							//DataSupport.insert(classname,cv);
							Log.d(Tags + "|insert backdata|", "ok" + insertid);
						} else {
					/*
				   cursor.moveToFirst();
				   String whereAs=rowId+"=?";
				   String[] whereValue={cursor.getString(cursor.getColumnIndex(rowId))};
				   DataSupport.update(classname, cv, Long.parseLong(rowIdvalue));
				   Log.d("udpate backdata", "ok");
				   */
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
	}


	 void connFailure(VolleyError error){
		String tags="volly error";
		NetworkResponse response = error.networkResponse;

		if (response != null) {
			switch (response.statusCode) {
				case 404:Toast.makeText(context, "网络异常，请重试或离线使用", Toast.LENGTH_SHORT).show();
					break;
				case 422:break;
				case 401:
					try {
						// server might return error like this { "error": "Some error occured" }
						// Use "Gson" to parse the result
						HashMap<String, String> result = new Gson().fromJson(new String(response.data),
								new TypeToken<Map<String, String>>() {
								}.getType());

						if (result != null && result.containsKey("error")) {
							Log.d(tags,  result.get("error"));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					// invalid request

					Log.d(tags,  error.getMessage());
					Toast.makeText(context, "访问被拒绝，请重试", Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(context, "服务器繁忙,请重试", Toast.LENGTH_SHORT).show();
					Log.d(tags,context.getResources().getString(R.string.generic_server_down));
					break;
			}
		}
		Log.d(tags, context.getResources().getString(R.string.generic_error));
	}

	 void connFailureNoWarning(VolleyError error){
		String tags="volly error";
		NetworkResponse response = error.networkResponse;

		if (response != null) {
			switch (response.statusCode) {
				case 404:
					break;
				case 422:break;
				case 401:
					try {
						// server might return error like this { "error": "Some error occured" }
						// Use "Gson" to parse the result
						HashMap<String, String> result = new Gson().fromJson(new String(response.data),
								new TypeToken<Map<String, String>>() {
								}.getType());

						if (result != null && result.containsKey("error")) {
							Log.d(tags,  result.get("error"));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					// invalid request

					Log.d(tags,  error.getMessage());

					break;
				default:

					Log.d(tags,context.getResources().getString(R.string.generic_server_down));
					break;
			}
		}
		Log.d(tags, context.getResources().getString(R.string.generic_error));
	}


	/*
	void GsonArray2Sqlite(JSONArray ja,String tbname,String index){
		Cursor cursor=null;
		ContentValues cv;
		JSONObject jo;
		for (int i=0;i<ja.length();i++){
			try {
				jo = ja.getJSONObject(i);
				Iterator<String> it=jo.keys();
				//String indexkey = (String) it.next();
				String indexvalue=jo.getString(index);

				cv=new ContentValues();
				//it.next();
				do{
					String key = (String) it.next();
					Object u=jo.get(key);
					//Log.d(key, u.toString());
					if (!key.equals("_id")){
						cv.put(key, u.toString());
					}
				}while(it.hasNext());
				cursor = TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+"username="+"'"+TaskData.user+"'"+" and "+index+"="+"'"+indexvalue+"'", null);
				if (cursor.getCount()==0){
					Log.d(Tags+"|insert backdata|", "diff:"+i);
					long insertcode=TaskData.db_TdDB.insert(tbname, null, cv);
					Log.d(Tags+"|insert backdata|", "ok"+insertcode);
				}else{
					if (cursor.getCount()>0){
						cursor.moveToFirst();
						String whereAs=index+"=?";
						String[] whereValue={cursor.getString(cursor.getColumnIndex(index))};

						int updatecode = TaskData.db_TdDB.update(tbname, cv, whereAs, whereValue);
						Log.d(Tags+"|udpate backdata|", "ok:"+i+" "+updatecode);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	void GsonArray2LitePal(JSONArray ja,String rowId,String tbname,Class classname){
		Cursor cursor=null;
		ContentValues cv;
		JSONObject jo;
		for (int i=0;i<ja.length();i++){
			cv=new ContentValues();
			try {
				jo = ja.getJSONObject(i);
				Iterator<String> it=jo.keys();

				String rowIdvalue=jo.get(rowId).toString();

				cv.put(rowId, rowIdvalue);
				it.next();

				do{
					String key = (String) it.next();
					Object u=jo.get(key);
					cv.put(key, u.toString());

				}while(it.hasNext());
				//Cursor cursor = DataSupport.findBySQL("select * from "+tbname+" where "+"username="+"'"+TaskTool.getPhoneNumber(getApplicationContext())+"'"+" and "+rowId+"="+"'"+rowIdvalue+"';");
				cursor = DataSupport.findBySQL("select * from "+tbname+" where "+rowId+"="+"'"+rowIdvalue+"';");
				if (cursor.getCount()==0){
					String root=context.getFilesDir().getAbsolutePath();
					Log.d(Tags+"|root|", root);
					//String path ="data/data/com.example.easygoal/"+tbname;
					//String path=root.replace("files", "databases")+File.separator+tbname;
					String path=root.replace("files", tbname);
					Log.d(Tags+"|path|", path);
					//SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
					//SQLiteDatabase db=this.openOrCreateDatabase("Memo.db", Context.MODE_PRIVATE, null);
					TaskData.db.insert(tbname, null, cv);
					//DataSupport.insert(classname,cv);
					Log.d(Tags+"|insert backdata|", "ok");
				}else{

				   cursor.moveToFirst();
				   String whereAs=rowId+"=?";
				   String[] whereValue={cursor.getString(cursor.getColumnIndex(rowId))};
				   DataSupport.update(classname, cv, Long.parseLong(rowIdvalue));
				   Log.d("udpate backdata", "ok");

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    */

	void QueryTeamTask(final String servletName, Map<String, String> params){

		final String Tags="QueryTeamTask";
		String url = TaskData.hostname+servletName;
		//RequestQueue requestQueue=Volley.newRequestQueue(context);
			JsonArray jolist=new JsonArray();
			JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
			Log.d(Tags, jostring.toString());
			jolist.add(jostring);

			mGsonArrayRequest gsonArrayRequest = new mGsonArrayRequest(url, jolist, "7", new Response.Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray response) {
					if (response != null && !response.toString().contains("FAIL") && !TextUtils.isEmpty(response.toString().trim())) {

						if (servletName == "TaskmainServlet") {
							Log.d(Tags, "taskmain json back" + response.toString());
							GsonArray2Sqlite(response, TaskData.TdDB.TABLE_NAME_TaskMain, "_sn");
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d(Tags, "GsonArray2Sqlite" + "taskmain ok");
							//TaskData.adapterUpdate();
						}
						if (servletName == "TaskrecServlet") {
							Log.d(Tags, "taskrec json back" + response.toString());
							GsonArray2Sqlite(response, TaskData.TdDB.TABLE_NAME_TaskRecord, "r_sn");
							//Toast.makeText(context, "record " + response.toString(), Toast.LENGTH_SHORT).show();
							Log.d(Tags, "GsonArray2Sqlite" + "taskrec ok");
						}
						if (servletName == "MemoServlet") {
							Log.d(Tags, "memo json back" + response.toString());
							Memo newMemo = new Memo();
							GsonArray2LitePal(response, "sn", "memo", Memo.class);
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d(Tags, "GsonArray2LitePal" + "memo ok");

						}
						if (servletName == "ReminderServlet") {
							//Reminder newReminder=new Reiminder();
							Log.d(Tags + "|reminder json back|", response.toString());
							GsonArray2LitePal(response, "sn", "reminder", Reminder.class);
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d(Tags + "|GsonArray2LitePal|", "reminder ok");
						}
						if (servletName == "RatingServlet") {
							//UserRatingBean newRating=new UserRatingBean();
							GsonArray2LitePal(response,"id","userratingbean",UserRatingBean.class);
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d(Tags + "|GsonArray2LitePal|", "userratingbean ok");

						}
						//Toast.makeText(getApplicationContext(), "载入数据完成", Toast.LENGTH_SHORT).show();
					} else {
						//Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
						Log.d(Tags, "无数据");
					}

				}

			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					// responseText.setText(error.getMessage());
					connFailureNoWarning(error);
				}
			});

			requestQueue.add(gsonArrayRequest);
			// requestQueue.start();
		}

	void passwordRequestPost(String servletName, Map params){

		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.100:8090/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");

		String url = TaskData.hostname+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		//RequestQueue requestQueue=Volley.newRequestQueue(context);

		JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
		JsonArray jolist=new JsonArray();
		jolist.add(jostring);
		Log.d(Tags,"login data"+jolist.toString());
		mGsonArrayRequest gsonObjectRequest = new mGsonArrayRequest(url,jolist,"5",new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				if(response != null){
					Log.d("json back", " "+response.toString());
					//Toast.makeText(getApplication(), response.toString(),Toast.LENGTH_LONG).show();
					String pwd=findInfo(response,"password");

					if (pwd!= null&&!TextUtils.isEmpty(pwd)){
						try{
							Toast.makeText(context, "[目标达]您的密码已发至通知栏",Toast.LENGTH_SHORT).show();
							TaskTool.notiSend(context,"[目标达]您的密码是:",pwd,System.currentTimeMillis());
						}catch(Exception e){
							Toast.makeText(context,"[目标达]您的密码是:"+pwd,Toast.LENGTH_SHORT).show();
							Log.d("password send failure", e.toString());
							e.printStackTrace();
						}
						//Toast.makeText(getApplication(),"用户名或手机号不正确",Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(context,"用户名或手机号不正确",Toast.LENGTH_SHORT).show();
					}

				}else{
					Toast.makeText(context, "用户名或手机号不正确",Toast.LENGTH_SHORT).show();

				}
			}
		},new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// responseText.setText(error.getMessage());
				connFailure(error);
			}
		});

		requestQueue.add(gsonObjectRequest);
		requestQueue.start();

		return ;
	}

	String findInfo(JSONArray ja,String index){
		String indexvalue = null;
		for (int i=0;i<ja.length();i++){
			JSONObject jo;
			try {
				jo = ja.getJSONObject(i);
				Iterator<String> it=jo.keys();
				//String indexkey = (String) it.next();
				indexvalue=jo.getString(index);
				Log.d("index", indexvalue);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return indexvalue;
	}
}
     /*
      *
      *
      *
      private void GsonObjectRequestPostSuccess(String servletName,Cursor cursor){
	   // String url = "http://route.showapi.com/213-3";
	    //String url = "http://192.168.1.105:8090/EasyTest/TaskmainServlet";
	    //Map<String, String> params = new HashMap<String, String>();
	    //params.put("taskname", "value1");
	    //params.put("deadline", "value2");
	    String url = TaskData.hostname+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
	  if (cursor!=null&&cursor.getCount()>0){
		Cursor ca = cursor;


	    mJsonObjectRequest jsonObjectRequest;

			//jolist = new JSONArray();
			ca.moveToFirst();
			int t=0;
			do{
				Map<String, String> params = new HashMap<String, String>();
			    for (int i=0;i<ca.getColumnCount();i++){


				     params.put(ca.getColumnName(i), ca.getString(i));


			    };
			   // jolist.add(jo);
			    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
			    Log.d(Tags,"JsonObject"+t+jostring.toString());
			    t++;
			    jsonObjectRequest = new mJsonObjectRequest(url,jostring,new Response.ErrorListener() {
			        @Override
			        public void onErrorResponse(VolleyError error) {
			           // responseText.setText(error.getMessage());
			        }
			    });

			    requestQueue.add(jsonObjectRequest);

			} while(ca.moveToNext());
	    //JSONObject jsonObject = new JSONObject(params);

	   }

	}

	private void DelByGsonArrayRequestPost(String servletName,String tbname,int delId){
		   // String url = "http://route.showapi.com/213-3";
		    //String url = "http://192.168.1.105:8090/EasyTest/TaskmainServlet";
		    //Map<String, String> params = new HashMap<String, String>();
		    //params.put("taskname", "value1");
		    //params.put("deadline", "value2");
		    String url = TaskData.hostname+servletName;
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";

		   RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
		   Cursor ca=TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+TaskData.TdDB.TASK_ID+"="+delId, null);
		if (ca!=null&&ca.getCount()>0){

		    mGsonArrayRequest DelGsonArrayRequest;
		    JsonArray jolist=new JsonArray();
				//jolist = new JSONArray();
				ca.moveToFirst();

				int t=0;
				do{
					Map<String, String> params = new HashMap<String, String>();
				    for (int i=0;i<ca.getColumnCount();i++){


					     params.put(ca.getColumnName(i), ca.getString(i));


				    };

				    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
				   // Log.d("JsonObject"+t, jostring.toString());
				    t++;
				    jolist.add(jostring);

				} while(ca.moveToNext());
		    //JSONObject jsonObject = new JSONObject(params);
				 Log.d(Tags,"JsonArray"+t+jolist.toString());

				DelGsonArrayRequest = new mGsonArrayRequest(url,jolist,"3",new Response.Listener<JSONArray>() {
			           @Override
			           public void onResponse(JSONArray response) {
			           	if(response != null){
			           		Log.d(Tags,"json back"+response.toString());
			           		GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskMain,"_sn");
			           		//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
			                TaskData.adapterUpdate();
			           	 }else{
			               	 //Toast.makeText(getApplicationContext(), "no data",Toast.LENGTH_SHORT).show();
			               }

			           }
			       },new Response.ErrorListener() {
					@Override
				        public void onErrorResponse(VolleyError error) {
				           // responseText.setText(error.getMessage());
				        }
				    });

				    requestQueue.add(DelGsonArrayRequest);
				    requestQueue.start();
		   }

		}


	private void jsonArrayRequestPostSuccess(String servletName,Cursor cursor){
		 final JSONArray jolist = new JSONArray();

		String url = TaskData.hostname+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
	  if (cursor!=null&&cursor.getCount()>0){
		Cursor ca = cursor;

		ca.moveToFirst();
		int t=0;
		Gson gson=new Gson();
		List<List> lll=new ArrayList<List>();
		do{

			ArrayList<Map<String,String>> arraylist = new ArrayList<Map<String,String>>();
		    for (int i=0;i<ca.getColumnCount();i++){
			    Map<String, String> newmap =  new HashMap<String,String>();
				newmap.put(ca.getColumnName(i), ca.getString(i));
				arraylist.add(newmap);
		    }
		   lll.add(arraylist);
		    //jolist.put(jo.toJson(arraylist));
		   //Log.d("jolist"+t,jolist.toString());
		   t++;
		} while(ca.moveToNext());
		//jolist=gson.toJsonTree(lll,new TypeToken<List<List>>(){}.getType()).getAsJsonArray();
		Log.d(Tags+"|jolist|",jolist.toString());
	  }


		Response.Listener rl=new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {
           	if(response != null){

                   //Toast.makeText(getApplicationContext(), response.toString(),Toast.LENGTH_SHORT).show();
               }else{
               	 Toast.makeText(getApplicationContext(), "无数据",Toast.LENGTH_SHORT).show();
               }

           }
       };



      Response.ErrorListener rel=new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               //Log.i("res errorlistener",error.getMessage());
           }
       };

		myJsonArrayRequest jr = new myJsonArrayRequest(url,jolist,rl, rel);
		requestQueue.add(jr);
		requestQueue.start();

	}

      void InsertGsonArrayRequestPost(String servletName,String tbname,String sn){
		   // String url = "http://route.showapi.com/213-3";
		    //String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";
		    //Map<String, String> params = new HashMap<String, String>();
		    //params.put("taskname", "value1");
		    //params.put("deadline", "value2");
		    String url = TaskData.hostname+servletName;
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
			RequestQueue requestQueue=Volley.newRequestQueue(context);
			Cursor ca = TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+TaskData.TdDB.TASK_SN+"="+"'"+sn+"'", null);
			if (ca!=null&&ca.getCount()>0){



		    mGsonArrayRequest gsonArrayRequest;
		    JsonArray jolist=new JsonArray();
				//jolist = new JSONArray();
				ca.moveToFirst();
				int t=0;
				do{
					Map<String, String> params = new HashMap<String, String>();
				    for (int i=0;i<ca.getColumnCount();i++){


					     params.put(ca.getColumnName(i), ca.getString(i));


				    };

				    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
				   // Log.d("JsonObject"+t, jostring.toString());
				    t++;
				    jolist.add(jostring);

				} while(ca.moveToNext());
		    //JSONObject jsonObject = new JSONObject(params);
				 Log.d("JsonArray"+t, jolist.toString());
				 Toast.makeText(context, jolist.toString(),Toast.LENGTH_SHORT).show();
				gsonArrayRequest = new mGsonArrayRequest(url,jolist,"2",new Response.Listener<JSONArray>() {
			           @Override
			           public void onResponse(JSONArray response) {
			           	if(response != null){
			           		Log.d("json back", "ddd "+response.toString());
			           		//GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskMain);
			           		Toast.makeText(context, response.toString(),Toast.LENGTH_SHORT).show();
			                TaskData.adapterUpdate();
			           	 }else{
			               	 Toast.makeText(context, "no data",Toast.LENGTH_SHORT).show();
			               }

			           }
			       },new Response.ErrorListener() {
					@Override
				        public void onErrorResponse(VolleyError error) {
				           // responseText.setText(error.getMessage());
				        }
				    });

				    requestQueue.add(gsonArrayRequest);
				    requestQueue.start();
		   }

		}

	void GsonArray2Sqlite(JSONArray ja,String tbname){

	 for (int i=0;i<ja.length();i++){
		JSONObject jo;
		try {
			jo = ja.getJSONObject(i);
			Iterator<String> it=jo.keys();
			String rowId=it.next();
			 ContentValues cv=new ContentValues();
			do{
			 String key = (String) it.next();
			 Object u=jo.get(key);

			 cv.put(key, u.toString());
			}while(it.hasNext());
			 Cursor cursor = TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+"_id"+"="+rowId, null);
			 if (cursor.getCount()==0){
			   TaskData.db_TdDB.insert(tbname, null, cv);
			   Log.d("insert backdata", "ok");
			 }else{
			   cursor.moveToFirst();
			   String whereAs=TaskData.TdDB.TASK_ID+"=?";
			   String[] whereValue={cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID))};
			   TaskData.db_TdDB.update(tbname, cv, whereAs, whereValue);
			   Log.d("udpate backdata", "ok");
			 }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	 }
	}
	*/



