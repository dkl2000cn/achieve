package com.easygoal.achieve;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.easygoal.achieve.databinding.ActivityChangeteamBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class Activity_ChangeTeam extends AppCompatActivity {
	public LocationClient mLocationClient = null;
	SharedPreferences sp;
	SharedPreferences.Editor editor;
	EditText et_teamname;
	EditText et_teamowner;
	TextView tv_teamname;
	final String Tags = "Activity_ChangeTeam";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changeteam);
		//EventBus.getDefault().register(getApplicationContext());

		ActivityChangeteamBinding mbinding = DataBindingUtil.setContentView(this, R.layout.activity_changeteam);
		/*
		et_teamname=(EditText)findViewById(R.id.et_teamname);
		et_teamowner=(EditText)findViewById(R.id.et_teamowner);
		tv_teamname=(TextView)findViewById(R.id.tv_teamname);

		Button btn_confirm = (Button) findViewById(R.id.btn_confirm);
		Button btn_seek = (Button) findViewById(R.id.btn_seek);
		Button btn_create = (Button) findViewById(R.id.btn_create);
		Button btn_cancel = (Button)findViewById(R.id.btn_cancel);
        */
		ImageButton btn_back= (ImageButton) findViewById(R.id.ibtn_back);

		btn_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Activity_ChangeTeam.this, Activity_Team.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});

		mbinding.btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// ???????
				finish();
			}
		});

		mbinding.btnSeek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

		mbinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String teamname=et_teamname.getText().toString().trim();
				String teamowner=et_teamowner.getText().toString().trim();
				if (!TextUtils.isEmpty(teamname)&&!TextUtils.isEmpty(teamowner)){
					     Map params=new HashMap<String,String>();
					     params.put("nickname",teamowner);
					     params.put("organization",teamname);
					     JoinOldTeam(getApplicationContext(),"UserServlet",params);

				}else{
					  Toast.makeText(getApplication(), "团队名称或口令不能为空", Toast.LENGTH_LONG).show();
				}
			}
		});

		mbinding.btnCreate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String teamname=et_teamname.getText().toString().trim();
				String teamowner=et_teamowner.getText().toString().trim();
				if (!TextUtils.isEmpty(teamname)){
					Map params=new HashMap<String,String>();
					params.put("organization",teamname);
					CreateNewTeam(getApplicationContext(),"UserServlet",params);
				}else{
					Toast.makeText(getApplication(), "团队名称或口令不能为空", Toast.LENGTH_LONG).show();
				}
			}
		});



	};

	private int ValidateTeamEntry(final Context context,String teamname,String teamowner){
		SharedPreferences sp= context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		SharedPreferences.Editor spEditor = sp.edit();

		final String username =  sp.getString("username", "");
		;
		Log.d(Tags+"|ValidateTeamEntry|",""+username+teamowner);
		//TeamTaskUtils.FindTeam(context,params);
		return TaskData.flag_findteam;
		//TeamTaskUtils.queryUserGroup(getActivity(),editorg.getText().toString().trim());
	};

	void JoinOldTeam(final Context context, final String servletName, final Map<String, String> params){

		final String Tags="JoinOldTeam";
		String url = TaskData.hostname+servletName;
		RequestQueue requestQueue= Volley.newRequestQueue(context);
		JsonArray jolist=new JsonArray();
		JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
		Log.d(Tags, jostring.toString());
		jolist.add(jostring);

		mGsonArrayRequest gsonArrayRequest = new mGsonArrayRequest(url, jolist, "7", new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				//tv_teamname.setText(""+response.toString());
				if (response != null){
					if (!response.toString().contains("nickname")) {
						//tv_teamname.setText("团队不存在");
						Toast.makeText(getApplicationContext(), "此团队不存在或口令不正确", Toast.LENGTH_SHORT).show();
					}else{
						//JSONArray ja = ConvertStr2JSONArray(response.toString());
						String teamname = params.get("organization");
						joinTeam(getApplicationContext(), teamname);
						Toast.makeText(getApplicationContext(), "加入团队[" + teamname + "]成功", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(Activity_ChangeTeam.this, Activity_Team.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
		                finish();
					}
				} else {
					//Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
					//Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
					Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
					Log.d(Tags, "无数据");
				}

			}

		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// responseText.setText(error.getMessage());
				new ConnMySQL(getApplicationContext()).connFailureNoWarning( error);
			}
		});
		requestQueue.add(gsonArrayRequest);
		// requestQueue.start();
	}

	void CreateNewTeam(final Context context, final String servletName, final Map<String, String> params){

		 final String Tags="CreateTeam";
		 String url = TaskData.hostname+servletName;
		 RequestQueue requestQueue= Volley.newRequestQueue(context);
		 JsonArray jolist=new JsonArray();
		 JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
		 Log.d(Tags, jostring.toString());
		 jolist.add(jostring);

		mGsonArrayRequest gsonArrayRequest = new mGsonArrayRequest(url, jolist, "7", new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				//tv_teamname.setText(""+response.toString());
				if (response != null){
					  if (!response.toString().contains("nickname")) {
						  String teamname = params.get("organization");
						  joinTeam(getApplicationContext(), teamname);
						  Toast.makeText(getApplicationContext(), "创建团队[" + teamname + "]成功", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(Activity_ChangeTeam.this, Activity_Team.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							finish();
					  }else{
						  //JSONArray ja = ConvertStr2JSONArray(response.toString());
						  //tv_teamname.setText("团队已存在");
						  Toast.makeText(getApplicationContext(), "此团队已存在", Toast.LENGTH_SHORT).show();
					  }
				} else {
					//Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
					//Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
					     Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
					Log.d(Tags, "无数据");
				}

			}

		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// responseText.setText(error.getMessage());
				new ConnMySQL(getApplicationContext()).connFailureNoWarning( error);
			}
		});
		requestQueue.add(gsonArrayRequest);
		// requestQueue.start();
	}

	private JSONArray ConvertStr2JSONArray(String s){

		JSONArray gsonArrayback=null;
		if (!TextUtils.isEmpty(s)){
			try {
				gsonArrayback = new JSONArray(s.trim());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else{
			try {
				gsonArrayback=new JSONArray("[]");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return gsonArrayback;
	}

	void printJSONArray(JSONArray ja){

		for (int i=0;i<ja.length();i++){
			JSONObject jo;
			try {
				jo = ja.getJSONObject(i);
				System.out.println("No"+i+" "+jo.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

	private int FindTeamIsExist(final Context context,String teamname){
		SharedPreferences sp= context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		SharedPreferences.Editor spEditor = sp.edit();

		final String username =  sp.getString("username", "");
		Map params=new HashMap<String,String>();
		//params.put("nickname",teamowner);
		//params.put("phoneNo",TaskData.user);
		params.put("organization",teamname);
		Log.d(Tags+"|FindTeamIsExist|",""+username);
		TeamTaskUtils.FindTeam(context,params);
		return TaskData.flag_findteam;
		//TeamTaskUtils.queryUserGroup(getActivity(),editorg.getText().toString().trim());
	};

	private void joinTeam(final Context context,String newTeamName){
		SharedPreferences sp= context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		SharedPreferences.Editor spEditor = sp.edit();

		final String username =  sp.getString("username", "");
		Map params=new HashMap<String,String>();
		params.put("username",username);
		params.put("phoneNo",TaskData.user);
		params.put("organization",newTeamName);
		Log.d(Tags+"|joinTeam",""+username+TaskData.user+newTeamName);
		TeamTaskUtils.updateUserGroup(context,params);
		//TeamTaskUtils.queryUserGroup(getActivity(),editorg.getText().toString().trim());
		TaskData.usergroup=newTeamName;

		spEditor.putString("organization", TaskData.usergroup);
		spEditor.commit();
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//EventBus.getDefault().unregister(getApplicationContext());
	}
}
