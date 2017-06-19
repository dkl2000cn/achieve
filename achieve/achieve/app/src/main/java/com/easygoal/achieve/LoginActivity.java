package com.easygoal.achieve;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import com.umeng.message.PushAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.easygoal.achieve.R.drawable.result;

public class LoginActivity extends Activity {

	// 帐号和密码

	private TextView tv_register;
	private Button btn_login;
	private TextView tv_guestlogin;
	private TextView tv_forgetpwd;
	// 创建SQLite数据库

	String username;
	String userphone;
	String password;
	String phoneNo;
	String loginresult;
	AlertDialog dlg_login;
	int phoneflag=-1;
	Handler mhandler;
	int CONN_FLAG=-1;
	String Tags="LoginActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//PushAgent.getInstance(getApplicationContext()).onAppStart();
        /*
    	CONN_FLAG=TaskTool.getConnCode(getApplication());
        String userPhone = TaskTool.getPhoneNumber(getApplication());
        SharedPreferences sp = this.getSharedPreferences("userinfo" , MODE_PRIVATE);
        Editor logineditor = sp.edit();
        if(sp.getString("username", null) == null){ //id为空，用户是首次使用应用
        	Log.d("firstrun", ""+sp.getBoolean("firstrun", true));
          int times=0;
          String lastlogintime=new Timestamp(new Date().getTime()).toString();
            logineditor.putBoolean("fisrtrun",false);
            logineditor.putString("username","guest"+userPhone);
		    logineditor.putString("phoneNo",userPhone);
		    logineditor.putString("lastlogintime",lastlogintime);
		    times++;
		    logineditor.putInt("logintimes",times);
		    logineditor.commit();

        }else{
        //否则不是首次使用
        	if (sp.getString("username", null) != null){
        	  String password=sp.getString("password", null);
        	  String phoneNo=sp.getString("phoneNo", null);
        	  String username=sp.getString("username", null);
        	  if (CONN_FLAG>0){
        	      Map params=new HashMap<String,String>();
		     	  params.put("username", username);
		     	  params.put("password", password);
		       	  params.put("phoneNo", phoneNo);
		          LoginRequestPost("UserServlet",params);
        	  }else{
        		  Toast.makeText(getApplicationContext(), "网络未连接,离线使用", Toast.LENGTH_SHORT).show();
        	  }

        	}else{
        	   Toast.makeText(getApplicationContext(), "信息有错误", Toast.LENGTH_SHORT).show();
        	}

          }
        */
		setContentView(R.layout.login);
		final EditText et_username = (EditText) findViewById(R.id.et_username);
		final EditText et_userphone = (EditText) findViewById(R.id.et_userphone);
		final EditText et_password = (EditText) findViewById(R.id.et_password);
		tv_register = (TextView) findViewById(R.id.tv_register);
		btn_login = (Button) findViewById(R.id.bt_login);
		tv_guestlogin=(TextView)findViewById(R.id.tv_guestlogin);
		tv_forgetpwd=(TextView)findViewById(R.id.tv_forgetpwd);
		// db = SQLiteDatabase.openOrCreateDatabase(MainActivity.this.getFilesDir().toString()
		//        + "/test.dbs", null);
		// 跳转到注册界面
      /*  StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .detectLeakedClosableObjects()
                        .penaltyLog()
                        .penaltyDeath()
                        .build()); */

		if (!TextUtils.isEmpty(TaskData.user)&&TaskTool.isMobileNO11(TaskData.user)){
			et_userphone.setVisibility(View.GONE);
			et_username.setHint("手机号");
		}else{
			et_userphone.setVisibility(View.VISIBLE);
			et_username.setHint("手机号");
			//et_username.setVisibility(View.GONE);
			et_userphone.setVisibility(View.GONE);
			phoneflag=1;
		}

		final SharedPreferences sp = getApplication().getSharedPreferences("userinfo" , MODE_PRIVATE);
		final Editor logineditor = sp.edit();
		phoneNo=sp.getString("phoneNo", "");
		//phoneNo=TaskData.user;

		tv_guestlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (phoneNo != null) {
					if (TextUtils.isEmpty(phoneNo.trim()) || !TaskTool.isMobileNO11(phoneNo.trim())) {

						Log.d(Tags, "firstrun" + sp.getBoolean("firstrun", false));
						int times = sp.getInt("logintimes", 0);
						String lastlogintime = new Timestamp(new Date().getTime()).toString();
						logineditor.putBoolean("fisrtrun", false);
						logineditor.putString("username", "");
						logineditor.putString("nickname", "");
						logineditor.putString("usertype", "游客");
						logineditor.putString("phoneNo", TaskTool.getPhoneNumber(getApplication()));
						logineditor.putString("lastlogintime", lastlogintime);
						times++;
						logineditor.putInt("logintimes", times);
						logineditor.commit();
						TaskData.privilege = 0;
						TaskData.user = TaskTool.getPhoneNumber(getApplication());
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, MainActivity_1.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
						Toast.makeText(getApplication(), "游客登录", Toast.LENGTH_SHORT).show();
					} else {
						int times = sp.getInt("logintimes", 0);
						String lastlogintime = new Timestamp(new Date().getTime()).toString();
						logineditor.putString("lastlogintime", lastlogintime);
						times++;
						logineditor.putInt("logintimes", times);
						Log.d(Tags, "logintimes " + times);
						logineditor.commit();
						TaskData.onlinestatus = "(离线使用)";
						TaskData.privilege = 1;
						TaskData.user = phoneNo.trim();
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, MainActivity_1.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
						Toast.makeText(getApplication(), "离线使用", Toast.LENGTH_SHORT).show();
					}
	           	/*
            	Editor editor = preference.edit(); //获取编辑器
            	String userPhone = getPhoneNumber();
            	String lastlogintime=new Timestamp(new Date().getTime()).toString();
            	editor.putString("userId",userPhone);
            	editor.putString("username",userPhone+"游客");
            	editor.putString("userPhone",userPhone);
            	editor.putString("nickname",userPhone+"游客");
            	editor.putString("usertype","游侠");
            	//editor.putLong("usercredit",1);
                editor.putString("lastlogintime",lastlogintime);
                //times++;
                //editor.putInt("logintimes",times);
                editor.commit();//提交修改
            	 */


				}
			}
		});
		tv_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, RegistersActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}

		});

		btn_login.setOnClickListener(new OnClickListener(){


			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				phoneNo=TaskData.user;


				if(phoneflag==1){
					//String inputstr_phone=et_userphone.getText().toString().trim();
					String inputstr_phone=et_username.getText().toString().trim();
					if (!TextUtils.isEmpty(inputstr_phone)&&TaskTool.isMobileNO11(inputstr_phone)){
						//phoneNo=et_username.getText().toString().trim();
						phoneNo=inputstr_phone;
						//Toast.makeText(getApplicationContext(), phoneNo, Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getApplicationContext(),"请输入正确手机号",Toast.LENGTH_SHORT).show();
						return;
					}
				}
				if(TextUtils.isEmpty(et_username.getText().toString().trim())){
				//if(TextUtils.isEmpty(et_username.getText().toString().trim())|TextUtils.isEmpty(et_password.getText().toString().trim())){
					Toast.makeText(getApplicationContext(),"输入不能为空",Toast.LENGTH_SHORT).show();
					//为空
				}else{
					final Map params=new HashMap<String,String>();

					if (TaskTool.isMobileNO11(et_username.getText().toString().trim())){
						password=et_password.getText().toString().trim();
						phoneNo=et_username.getText().toString().trim();
						//params.put("username", username);
						params.put("password", password);
						params.put("phoneNo", phoneNo);
						//Toast.makeText(getApplicationContext(), "手机号正确", Toast.LENGTH_SHORT).show();
					}else{
						password=et_password.getText().toString().trim();
						phoneNo=et_username.getText().toString().trim();
						//username=et_username.getText().toString().trim();
						//password=et_password.getText().toString().trim();
						//phoneNo=TaskData.user;
						//params.put("username", username);
						params.put("password", password);
						params.put("phoneNo", phoneNo);
						//Toast.makeText(getApplicationContext(), username+password+phoneNo, Toast.LENGTH_SHORT).show();
					}

					btn_login.setText("登录中...");
					btn_login.setTextColor(getResources().getColor(R.color.darkgray));
				  /*
				 AlertDialog.Builder dialog=new AlertDialog.Builder(LoginActivity.this);
				 //dialog.setco(getResources().getDrawable(R.drawable.running)).create().show();
				 dlg_login=dialog.create();
				 dlg_login.setTitle("请稍候...");;
				 dlg_login.show();
				 */
					if (getConnCode()>0) {
						Login(params);
					}else{
						btn_login.setText("登录");
						btn_login.setTextColor(getResources().getColor(R.color.black));
						Toast.makeText(getApplicationContext(),
								"亲，网络连了么？", Toast.LENGTH_SHORT)
								.show();
					}


					//LoginRequestPost("UserServlet",params);
					//new RetrofitUtils(getApplicationContext()).UserLoginRequestPost("UserServlet", params, btn_login);
				}

			}

		});
        /*
        mhandler= new Handler(){

        	public void handmesage(Message msg){
        		if (msg.what==9){
        			 dlg_login.dismiss();
            	};
        	}
        };
        */
		tv_forgetpwd.setOnClickListener(new OnClickListener(){


			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//username=et_username.getText().toString().trim();
				//String requestPhoneNo=et_userphone.getText().toString().trim();
				String requestPhoneNo=et_username.getText().toString().trim();
				//String requestUsername=et_username.getText().toString().trim();
				if(phoneflag==1){
					//if (!TextUtils.isEmpty(requestPhoneNo)&&!TextUtils.isEmpty(requestUsername)&&TaskTool.isMobileNO11(requestPhoneNo)){
					if (!TextUtils.isEmpty(requestPhoneNo)&&TaskTool.isMobileNO11(requestPhoneNo)){
						Map params=new HashMap<String,String>();
						//params.put("username",requestUsername);
						params.put("phoneNo", requestPhoneNo);
						//HttpUtils.passwordRequestPost("UserServlet",params);
						if (getConnCode()>0) {
							GetPassword(params);
						}else{
							Toast.makeText(getApplicationContext(),
									"亲，网络连了么？", Toast.LENGTH_SHORT)
									.show();
						}
					}else{
						Toast.makeText(getApplicationContext(),"请输入正确手机号",Toast.LENGTH_SHORT).show();
					}
				}else{

					if(TextUtils.isEmpty(requestPhoneNo)){
						Toast.makeText(getApplicationContext(),"输入不能为空",Toast.LENGTH_SHORT).show();
						//为空
					}else{
						if (TaskTool.isMobileNO11(requestPhoneNo)&&requestPhoneNo.equals(TaskTool.getPhoneNumber(getApplication()))){

							Map params=new HashMap<String,String>();
							//params.put("username",requestUsername);
							params.put("phoneNo", requestPhoneNo);
							//Toast.makeText(getApplicationContext(), "手机号正确", Toast.LENGTH_SHORT).show();
							//new ConnMySQL(getApplicationContext()).passwordRequestPost("UserServlet",params);
							if (getConnCode()>0) {
								GetPassword(params);
							}else{
								Toast.makeText(getApplicationContext(),
										"亲，网络连了么？", Toast.LENGTH_SHORT)
										.show();
							}
						}else{
							Toast.makeText(getApplicationContext(),"请输入正确手机号",Toast.LENGTH_SHORT).show();
						}
					}
				}

			}

		});
	}



	public  int getConnCode(){

		ConnectivityManager con = (ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);  ;
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

		if (wifi==true&& internet==false){
			return 1;
		}
		if (wifi==false&& internet==true){
			return 2;
		}
		if (wifi==true&& internet==true){
			return 3;
		}
		return 0;
	}
	void Login(final Map params){
		final Handler handler=new Handler(){
			public void handleMessage(android.os.Message msg){
				//Toast.makeText(getApplicationContext(), msg.what, Toast.LENGTH_SHORT).show();
				Log.d("login back",""+msg.what);
				switch (msg.what){
					case 1:   Bundle b = msg.getData();
						String result = b.getString("data");
						if(result!=null){
							if (result.contains(phoneNo)) {
								try {
									LoginSuccess(new JSONArray(result));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}else{
								try {
									LoginFail(new JSONArray(result));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}else {
							LoginNoResponse();
						}
						break;
					default:break;
				}

			}
		};

		new Thread(new Runnable() {
			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				Bundle bundle = new Bundle();
				bundle.putString("data",  HttpUtils.RequestPost("UserServlet",params,"7"));
				message.setData(bundle);
				handler.sendMessage(message);
			}
		}).start();

	}
	void GetPassword(final Map params){
		final Handler handler=new Handler(){
			public void handleMessage(android.os.Message msg){
				//Toast.makeText(getApplicationContext(), msg.what, Toast.LENGTH_SHORT).show();
				Log.d("login back",""+msg.what);
				switch (msg.what){
					case 1:   Bundle b = msg.getData();
						String result = b.getString("data");
						Log.d("GetPassword",""+result );
						if(result!=null){
							if (result.contains(params.get("phoneNo").toString())) {
								Log.d("GetPassword",""+result );
								try {
									Log.d("GetPassword",""+result );
										PasswordSuccess(new JSONArray(result));
										//Toast.makeText(getApplication(),""+result,Toast.LENGTH_SHORT).show();
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}else{
									PasswordFail();
							}
						}else {
							PasswordNoResponse();
						}
						break;
					default:break;
				}

			}
		};

		new Thread(new Runnable() {
			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				Bundle bundle = new Bundle();
				bundle.putString("data",  HttpUtils.RequestPost("UserServlet",params,"5"));
				message.setData(bundle);
				handler.sendMessage(message);
			}
		}).start();
	}



	public void LoginRequestPost(String servletName,Map params){

		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.100:8090/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		final Map usermap=params;
		final String url = TaskData.hostname+servletName;
		//final String username=usermap.get("username").toString();
		//final String password=usermap.get("password").toString();
		final String phoneNo=usermap.get("phoneNo").toString();
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";

		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());

		JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
		JsonArray jolist=new JsonArray();
		jolist.add(jostring);
		Log.d(Tags,"login data"+jolist.toString());
		mGsonArrayRequest gsonObjectRequest = new mGsonArrayRequest(url,jolist,"7",new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {

				if(response != null){
					//Log.d("json back", "ddd "+response.toString());
					int times=0;
					//Toast.makeText(getActivity(), "back ok",Toast.LENGTH_SHORT).show();
					//TaskData.adapterUpdate();
					Log.d(Tags,"json back"+response.toString());
					if (response.toString().contains(phoneNo)){
						Toast.makeText(getApplication(), "登录成功",Toast.LENGTH_SHORT).show();
				           		    /*
				           		   	Message msg=new Message();
				           		    msg.what=9;
				           		   	mhandler.sendMessage(msg);
				           		   	*/
						//dlg_login.dismiss();
						String login_username = null;
						String login_nickname=null;
						String login_usertype=null;
						String login_times="0";
						String login_phoneno=null;
						String login_organization=null;
						String login_userid=null;
						int login_userprivilege=0;
						try {
							login_username=response.getJSONObject(0).get("username").toString();
							login_nickname=response.getJSONObject(0).get("nickname").toString();
							login_usertype=response.getJSONObject(0).get("usertype").toString();
							//login_phoneno=response.getJSONObject(0).get("phoneNo").toString();
							login_userid=response.getJSONObject(0).get("userId").toString();
							login_userprivilege=response.getJSONObject(0).getInt("userprivilege");
							login_times=response.getJSONObject(0).get("logintimes").toString();
							login_organization=response.getJSONObject(0).get("organization").toString();
							Log.d(Tags,"login info"+login_username+"\n"+login_nickname+"\n");

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							//Toast.makeText(getApplication(), e.toString(),Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
						//Toast.makeText(getApplication(), login_username+"\n"+login_nickname+"\n"+login_usertype+login_userprivilege,Toast.LENGTH_SHORT).show();
						//EventBus.getDefault().post(new UserResponseEvent(response.toString()));
						//Log.d("json back", response.toString());
						SharedPreferences loginsp = getApplication().getSharedPreferences("userinfo" , MODE_PRIVATE);
						Editor editor = loginsp.edit();

						int logintimes = Integer.parseInt(login_times);
						String lastlogintime=new Timestamp(new Date().getTime()).toString();
						editor.putString("username", login_username);
						//editor.putString("password", login_userpwd);
						editor.putString("phoneNo", phoneNo);
						editor.putString("nickname", login_nickname);
						editor.putString("usertype", login_usertype);
						editor.putString("userid", login_userid);
						editor.putString("organization",login_organization);
						//editor.putString("username",login_username);
						editor.putString("lastlogintime",lastlogintime);
						editor.putInt("userprivilege",login_userprivilege);

						TaskData.user= phoneNo;
						TaskData.usernickname=login_nickname;
						TaskData.usergroup=login_organization;
						logintimes++;
						editor.putInt("logintimes",logintimes);
						Log.d(Tags,"logintimes "+logintimes);
						editor.commit();

						TaskData.onlinestatus="(在线)";

						Log.d(TaskData.t_tags,""+(new Date().getTime()-TaskData.t_start)+"ms");

						HashMap lum=new HashMap();
						lum.put("username", login_username);
						lum.put("phoneNo",  phoneNo);
						lum.put("lastlogintime",lastlogintime);
						lum.put("logintimes",logintimes );
						//TaskTool.UpdateUserRequestPost(getApplication(),"UserServlet",lum);
						new RetrofitUtils(getApplication()).UpdateUserRequestPost("UserServlet",lum);

						Intent intent= new Intent(LoginActivity.this,MainActivity_1.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						Log.d(TaskData.t_tags,""+(new Date().getTime()-TaskData.t_start)+"ms");
						finish();

					}else{
						Toast.makeText(getApplication(), "用户名或密码错误",Toast.LENGTH_SHORT).show();
						//Intent intent=new Intent(LoginActivity.this,MainActivity_1.class);
						//startActivity(intent);
						//EventBus.getDefault().post(new UserResponseEvent(response.toString()));
						//Log.d("json back", response.toString());
						//TaskData.onlinestatus="(离线使用)";
						btn_login.setText("登录");
						btn_login.setTextColor(getResources().getColor(R.color.black));

					}

				}else{

					Toast.makeText(getApplication(), "无响应,离线使用",Toast.LENGTH_SHORT).show();
					//Intent intent=new Intent(LoginActivity.this,MainActivity_1.class);
					//startActivity(intent);
					//EventBus.getDefault().post(new UserResponseEvent(response.toString()));
					//Log.d("json back", response.toString());
					//TaskData.onlinestatus="(离线使用)";
					SharedPreferences loginsp = getApplication().getSharedPreferences("userinfo" , MODE_PRIVATE);
					Editor editor = loginsp.edit();
					int times = loginsp.getInt("logintimes", 0);
					String lastlogintime=new Timestamp(new Date().getTime()).toString();
					editor.putString("phoneNo",TaskData.user);
					editor.putString("lastlogintime",lastlogintime);
					times++;
					editor.putInt("logintimes",times);
					Log.d(Tags,"logintimes "+times);
					editor.commit();
					TaskData.onlinestatus="(离线使用)";
					Intent intent= new Intent(LoginActivity.this,MainActivity_1.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				}

			}
		},new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// responseText.setText(error.getMessage());
				new ConnMySQL(getApplicationContext()).connFailure(error);
				Toast.makeText(getApplication(), "网络忙,请稍后再试或离线进入",Toast.LENGTH_SHORT).show();
				btn_login.setText("登录");
				btn_login.setTextColor(getResources().getColor(R.color.black));

			}
		});

		requestQueue.add(gsonObjectRequest);
		requestQueue.start();

		return ;
	}

	public void LoginSuccess(JSONArray response){
		Toast.makeText(getApplication(), "登录成功",Toast.LENGTH_SHORT).show();
											/*
											Message msg=new Message();
											msg.what=9;
											mhandler.sendMessage(msg);
											*/
		//dlg_login.dismiss();
		String login_username = null;
		String login_nickname=null;
		String login_usertype=null;
		String login_times="0";
		String login_phoneno=null;
		String login_organization=null;
		String login_userid=null;
		String login_rongtoken=null;
		String login_headpicuri=null;
		int login_userprivilege=0;
		try {
			login_username=response.getJSONObject(0).get("username").toString();
			login_nickname=response.getJSONObject(0).get("nickname").toString();
			login_usertype=response.getJSONObject(0).get("usertype").toString();
			//login_phoneno=response.getJSONObject(0).get("phoneNo").toString();
			login_userid=response.getJSONObject(0).get("userId").toString();
			login_userprivilege=response.getJSONObject(0).getInt("userprivilege");

			if (response.getJSONObject(0).get("headpicuri")!=null) {
				login_headpicuri = response.getJSONObject(0).get("headpicuri").toString();
			}else{
				Log.d(Tags,"no headpicuri");
			}
			if (response.getJSONObject(0).get("RongToken")!=null) {
				login_rongtoken = response.getJSONObject(0).get("RongToken").toString();
			}else{
				Log.d(Tags,"no RongToken");
			}
			if (response.getJSONObject(0).get("organization")!=null) {
				login_organization = response.getJSONObject(0).get("organization").toString();
			}else{
				Log.d(Tags,"no organization");
			}
			login_times=response.getJSONObject(0).get("logintimes").toString();
			Log.d(Tags,"login info"+login_username+"\n"+login_nickname+"\n");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//Toast.makeText(getApplication(), e.toString(),Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		//Toast.makeText(getApplication(), login_username+"\n"+login_nickname+"\n"+login_usertype+login_userprivilege,Toast.LENGTH_SHORT).show();
		//EventBus.getDefault().post(new UserResponseEvent(response.toString()));
		//Log.d("json back", response.toString());
		SharedPreferences loginsp = getApplication().getSharedPreferences("userinfo" , MODE_PRIVATE);
		Editor editor = loginsp.edit();

		int logintimes = Integer.parseInt(login_times);
		String lastlogintime=new Timestamp(new Date().getTime()).toString();
		editor.putString("username", login_username);
		//editor.putString("password", login_userpwd);
		editor.putString("phoneNo", phoneNo);
		editor.putString("nickname", login_nickname);
		editor.putString("usertype", login_usertype);
		editor.putString("userid", login_userid);
		editor.putString("organization",login_organization);
		//editor.putString("username",login_username);
		editor.putString("lastlogintime",lastlogintime);
		editor.putInt("userprivilege",login_userprivilege);
		editor.putString("headpicuri",login_headpicuri);
		editor.putString("RongToken",login_rongtoken);
		TaskData.username=login_username;
		TaskData.user= phoneNo;
		TaskData.usernickname=login_nickname;
		TaskData.usergroup=login_organization;
		TaskData.connRongflag=false;
		logintimes++;
		editor.putInt("logintimes",logintimes);
		Log.d(Tags,"logintimes "+logintimes);
		editor.commit();

		TaskData.onlinestatus="(在线)";

		Log.d(TaskData.t_tags,""+(new Date().getTime()-TaskData.t_start)+"ms");

		HashMap lum=new HashMap();
		lum.put("username", login_username);
		lum.put("phoneNo",  phoneNo);
		lum.put("lastlogintime",lastlogintime);
		lum.put("logintimes",logintimes );
		//TaskTool.UpdateUserRequestPost(getApplication(),"UserServlet",lum);
		new RetrofitUtils(getApplication()).UpdateUserRequestPost("UserServlet",lum);

		Intent intent= new Intent(LoginActivity.this,MainActivity_1.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		Log.d(TaskData.t_tags,""+(new Date().getTime()-TaskData.t_start)+"ms");
		finish();
	}

	public void LoginFail(JSONArray response){
		Toast.makeText(getApplication(), "用户名或密码错误",Toast.LENGTH_SHORT).show();
		//Intent intent=new Intent(LoginActivity.this,MainActivity_1.class);
		//startActivity(intent);
		//EventBus.getDefault().post(new UserResponseEvent(response.toString()));
		//Log.d("json back", response.toString());
		//TaskData.onlinestatus="(离线使用)";
		btn_login.setText("登录");
		btn_login.setTextColor(getResources().getColor(R.color.black));
	}

	public void LoginError(JSONArray response){
		//new ConnMySQL(getApplicationContext()).connFailure(error);
		Toast.makeText(getApplication(), "网络忙,请稍后再试或离线进入",Toast.LENGTH_SHORT).show();
		btn_login.setText("登录");
		btn_login.setTextColor(getResources().getColor(R.color.black));
	}

	public void LoginNoResponse(){
		Toast.makeText(getApplication(), "无响应,离线使用",Toast.LENGTH_SHORT).show();
		//Intent intent=new Intent(LoginActivity.this,MainActivity_1.class);
		//startActivity(intent);
		//EventBus.getDefault().post(new UserResponseEvent(response.toString()));
		//Log.d("json back", response.toString());
		//TaskData.onlinestatus="(离线使用)";
		SharedPreferences loginsp = getApplication().getSharedPreferences("userinfo" , MODE_PRIVATE);
		Editor editor = loginsp.edit();
		int times = loginsp.getInt("logintimes", 0);
		String lastlogintime=new Timestamp(new Date().getTime()).toString();
		editor.putString("phoneNo",TaskData.user);
		editor.putString("lastlogintime",lastlogintime);
		times++;
		editor.putInt("logintimes",times);
		Log.d(Tags,"logintimes "+times);
		editor.commit();
		TaskData.onlinestatus="(离线使用)";
		Intent intent= new Intent(LoginActivity.this,MainActivity_1.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	public void PasswordSuccess(JSONArray response){
		String pwd=findInfo(response,"password");
		try{
			Toast.makeText(getApplicationContext(), "[目标达]您的密码已发至通知栏",Toast.LENGTH_SHORT).show();
			TaskTool.notiSend(getApplicationContext(),"[目标达]您的密码是:",pwd,System.currentTimeMillis());
		}catch(Exception e){
			Toast.makeText(getApplicationContext(),"[目标达]您的密码是:"+pwd,Toast.LENGTH_SHORT).show();
			Log.d("password send failure", e.toString());
			e.printStackTrace();
		}
	}

	public void PasswordFail(){
		Toast.makeText(getApplicationContext(),"手机号不正确",Toast.LENGTH_SHORT).show();
	}

	public void PasswordNoResponse(){
		Toast.makeText(getApplication(), "网络忙,请稍后再试",Toast.LENGTH_SHORT).show();
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




	/*
    public void login1()
    {  loginresult=null;
    Integer ddd;
    String result;


    final Handler handler;
    Message msg;


    class MyHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if(msg.what==1)
            {
              TaskData.loginstatus=msg.getData().getString("result");
              Log.d("login status", "a"+TaskData.loginstatus.length()+TaskData.loginstatus.contains("true")+"start?"+TaskData.loginstatus.startsWith("true"));
              if (msg.getData().getString("result").contains("true")){
                     Toast.makeText(getApplicationContext(), "登陆成功"+TaskData.loginstatus, Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent();
                  intent.setClass(LoginActivity.this, MainActivity_1.class);
                  startActivity(intent);
                 // finish();
                } else{
                  Toast.makeText(getApplicationContext(), "登陆失败"+TaskData.loginstatus, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    handler=new MyHandler();
    class MyThread implements Runnable
    {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            String uri = TaskData.hostname+"LoginServlet";
            //String uri = "http://www.163.com/";
            HttpPost request = new HttpPost(uri);
            //HttpGet request2 = new HttpGet(uri);
            Log.i("create request",request.toString());
            JSONObject param = new JSONObject();
            //String username = null;
            //String password = null;
            try {
                 param.put("username",username);
                 param.put("password",password);
                 Log.i("object param",param.toString());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

                //将用户名和密码放入HashMap中
                StringEntity se = null;
                try {
                    se = new StringEntity(param.toString());
                    se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                     request.setEntity(se);
                     Log.i("set entity",param.toString());
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }


                // 发送请求
                HttpResponse  httpResponse;
                BasicHttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 10*1000);
                HttpConnectionParams.setSoTimeout(httpParams, 20*1000);
                HttpClient httpClient = new DefaultHttpClient(httpParams);
                //httpClient.getConnectionManager().closeExpiredConnections();;
                String retSrc=null;
                    try {
                        httpResponse = httpClient.execute(request);             // read response entity              // do something!!!
                        Log.i("httpresponse",httpResponse.toString());

                        if(httpResponse.getStatusLine().getStatusCode()==200)
                        {

                             // 得到应答的字符串，这也是一个 JSON 格式保存的数据
                            try {
                                retSrc = EntityUtils.toString(httpResponse.getEntity());
                                Message msg=new Message();
                                msg.what=1;
                                Bundle bundle=new Bundle();
                                bundle.putString("result", retSrc);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                                //aa=retSrc;
                                //loginresult=retSrc;
                                Log.i("retSrc",retSrc);
                                //publishProgress(retSrc);
                                //Intent intent = new Intent();
                                //intent.setClass(LoginActivity.this, MainActivity_1.class);
                                //startActivity(intent);
                                //Toast.makeText(getApplicationContext(), "登陆成功"+loginresult, Toast.LENGTH_SHORT).show();
                            } catch (ParseException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                          }else{
                              //loginresult="void";
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        } finally {
                                request.abort();
                                Log.i("request",request.toString());
                        }

        }
    };

    MyThread mythread=new MyThread();
    Thread thread_login = new Thread(mythread);
    thread_login.start();

        return ;
    }

    private HttpClient getHttpClient() {
        // TODO Auto-generated method stub
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5*1000);
        HttpConnectionParams.setSoTimeout(httpParams, 10*1000);
        HttpClient client = new DefaultHttpClient(httpParams);
        return client;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
       // TaskData.db_TdDB.close();
    }


    class LoginListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            //login();
            /*
            String name = edname.getText().toString();
            String password = edpassword.getText().toString();
            if (name.equals("") || password.equals("")) {
                // 弹出消息框
                new AlertDialog.Builder(MainActivity.this).setTitle("错误")
                        .setMessage("帐号或密码不能空").setPositiveButton("确定", null)
                        .show();
                isUserinfo(name, password);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MainActivity_1.class);
                startActivity(intent);
            } else {
                isUserinfo(name, password);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MainActivity_1.class);
                startActivity(intent);

            }
        }

        // 判断输入的用户是否正确
       public Boolean isUserinfo(String name, String pwd) {
            try{
                String str="select * from tb_user where name=? and password=?";
                Cursor cursor = TaskData.db_TdDB.rawQuery(str, new String []{name,pwd});
                if(cursor.getCount()<=0){
                    new AlertDialog.Builder(LoginActivity.this).setTitle("错误")
                    .setMessage("帐号或密码错误！").setPositiveButton("确定", null)
                    .show();
                    return false;
                }else{
                    new AlertDialog.Builder(LoginActivity.this).setTitle("正确")
                    .setMessage("成功登录").setPositiveButton("确定", null)
                    .show();
                    return true;
                }

            }catch(SQLiteException e){
                createDb();
            }
            return false;
        }

    }

    // 创建数据库和用户表
    public void createDb() {

    }  */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

}
