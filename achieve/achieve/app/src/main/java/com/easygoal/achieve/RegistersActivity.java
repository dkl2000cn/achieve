package com.easygoal.achieve;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
  
  
public class RegistersActivity extends Activity {  
  
    private EditText et_newUserName;
    private EditText et_newUserPhone;
    private EditText et_newUserPassword;  
    private EditText et_newUserPassword2;
    private Button bt_register; 
    private String username;
    private String password;
    private String password2nd;  
    private String phoneNo;
    int phoneflag=-1;
    private String nickname;
    String registerstatus;
    SQLiteDatabase db;
    UserBean userBean; 
    List<UserBean> userlist;
    String Tags="RegistersActivity";
    @Override  
    protected void onDestroy() {  
        // TODO Auto-generated method stub  
        super.onDestroy();  
      
    }  
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState); 
       // ActionBar actionBar = getActionBar();  
		//actionBar.setDisplayHomeAsUpEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);  
       
    
        et_newUserName = (EditText) findViewById(R.id.et_username);
        et_newUserPhone = (EditText) findViewById(R.id.et_userphone); 
        et_newUserPassword = (EditText) findViewById(R.id.et_password); 
        et_newUserPassword2 = (EditText) findViewById(R.id.et_password2);
        bt_register = (Button) findViewById(R.id.bt_register);  
        TextView tv_backtologin = (TextView) findViewById(R.id.tv_backtologin); 
       
        if (!TextUtils.isEmpty(TaskData.user)&&TaskTool.isMobileNO11(TaskData.user)){
        	et_newUserPhone.setVisibility(View.GONE);
        }else{	
        	et_newUserPhone.setVisibility(View.VISIBLE);
     	    phoneflag=1;
        }
        
        tv_backtologin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				    Intent intent = new Intent();  
	                intent.setClass(RegistersActivity.this, LoginActivity.class);
	                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	                startActivity(intent);  
			}  	
        });
        bt_register.setOnClickListener(new OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
            	username=et_newUserName.getText().toString().trim();  
            	phoneNo=TaskData.user; 
		        password=et_newUserPassword.getText().toString().trim(); 
		        password2nd = et_newUserPassword2.getText().toString().trim(); 
		        nickname=et_newUserName.getText().toString().trim(); 
		   
		        Log.i(Tags,"submit user "+username+"pwd"+password);
		        
		        if(phoneflag==1){
		        		String inputstr_phone=et_newUserPhone.getText().toString().trim();
				 if (!TextUtils.isEmpty(inputstr_phone)&&TaskTool.isMobileNO11(inputstr_phone)){
				     	phoneNo=et_newUserPhone.getText().toString().trim();
				 }else{
					 	Toast.makeText(getApplicationContext(),"请输入正确手机号",Toast.LENGTH_SHORT).show();
				 }
			    }  	
   
		     	if(TextUtils.isEmpty(username)|TextUtils.isEmpty(password)|!TaskTool.isMobileNO11(phoneNo)){
		     			Toast.makeText(getApplicationContext(),"输入不能为空",Toast.LENGTH_SHORT).show();
		   	//为空  
		     	}else{  
		     	 
		   	   if (password.equals(password2nd)){
		   		   
		   		 if (TaskTool.isPwdFormat(password)&&password.length()>=6){
		   		    Map params=new HashMap<String,String>();
		     	    params.put("username", username);
		     	    params.put("sn", "01"+"01"+phoneNo);
		     	    params.put("password", password);
		     	    params.put("nickname", username);
		     	    params.put("phoneNo", phoneNo);
		            //RegisterRequestPost("UserServlet",params);
					 if (getConnCode()>0) {
						 Register(params);
					 }else{
						 Toast.makeText(getApplicationContext(),
								 "亲，网络连了么？", Toast.LENGTH_SHORT)
								 .show();
					 }
		   		 }else{
		   			Toast.makeText(getApplicationContext(),"密码至少要为6位以上数字",0).show();   
		   		 }
		   		   
		   		//register();
				//Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
		   	   }else{
		   		Toast.makeText(getApplicationContext(),"密码输入有误,请重新输入",0).show();    
		   		  
		   	   }
		      } 
            }	
        });  
  
    }

	void Register(final Map params){
					final Handler handler=new Handler(){
						public void handleMessage(android.os.Message msg){
							//Toast.makeText(getApplicationContext(), msg.what, Toast.LENGTH_SHORT).show();
							Log.d("login back",""+msg.what);
							switch (msg.what){
								case 1:   Bundle b = msg.getData();
									String result = b.getString("data");
									if(result!=null){
										if (result.contains("OK")) {
											try {
												RegisterSuccess(new JSONArray(result));
											} catch (JSONException e) {
												e.printStackTrace();
											}
										}else{
											try {
												 RegisterFail(new JSONArray(result));
											} catch (JSONException e) {
												e.printStackTrace();
											}
										}
									}else {
												RegisterNoResponse();
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
							bundle.putString("data",  HttpUtils.RequestPost("UserServlet",params,"2"));
							message.setData(bundle);
							handler.sendMessage(message);
						}
					}).start();
	}

	public void RegisterSuccess(JSONArray response){
				Toast.makeText(getApplication(), "注册成功",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(RegistersActivity.this,LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
	}

	public void RegisterFail(JSONArray response){
			Toast.makeText(getApplication(), "该用户或手机已注册",Toast.LENGTH_SHORT).show();
	}

	public void RegisterError(JSONArray response){
		//new ConnMySQL(getApplicationContext()).connFailure(error);
			Toast.makeText(getApplication(), "网络忙,请稍后再试或离线进入",Toast.LENGTH_SHORT).show();
	}

	public void RegisterNoResponse(){
			Toast.makeText(getApplication(), "网络忙,请稍后再试或离线进入",Toast.LENGTH_SHORT).show();
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

	// 添加用户
   /*
    public Boolean addUser(String name, String password) {  
        String str = "insert into tb_user values(?,?) ";  
        MainActivity main = new MainActivity();  
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()  
                + "/test.dbs", null);  
        main.db = db;  
        try {  
            db.execSQL(str, new String[] { name, password });  
            return true;  
        } catch (Exception e) {  
            main.createDb();  
        }  
        return false;  
    }  */

	public void RegisterRequestPost(String servletName,Map params){ 
		
		   // String url = "http://route.showapi.com/213-3";  
		    //String url = "http://192.168.1.100:8090/EasyTest/TaskmainServlet";  
		    //Map<String, String> params = new HashMap<String, String>();  
		    //params.put("taskname", "value1");  
		    //params.put("deadline", "value2"); 
		    final Map usermap=params;
		    String url = TaskData.hostname+servletName;  
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
			RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
		 
				    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
				    JsonArray jolist=new JsonArray(); 
				    jolist.add(jostring);
				    Log.d(Tags,"register data"+jolist.toString());
				mGsonArrayRequest gsonObjectRequest = new mGsonArrayRequest(url,jolist,"2",new Response.Listener<JSONArray>() {  
					    
					@Override  
			           public void onResponse(JSONArray response) {  
			           	if(response != null){
			           		//Log.d("json back", "ddd "+response.toString());
			           		
			           		//Toast.makeText(getActivity(), "back ok",Toast.LENGTH_SHORT).show();
			                //TaskData.adapterUpdate();  
			           	   Log.d(Tags+"|json back|", response.toString());
			           	   if (response.toString().contains("OK")) {
			           		Toast.makeText(getApplication(), "注册成功",Toast.LENGTH_SHORT).show();
							   /*
			           		  TaskData.user= phoneNo;
			           		  SharedPreferences sp = getSharedPreferences("userinfo" , MODE_PRIVATE);
			                  Editor logineditor = sp.edit();
			                  
			                    String lastlogintime=new Timestamp(new Date().getTime()).toString();
			                    
			                    logineditor.putString("username", usermap.get("username").toString());
			                    //logineditor.putString("password",password);
			        		    logineditor.putString("phoneNo",usermap.get("phoneNo").toString());
			        		    logineditor.putString("nickname",usermap.get("nickname").toString());
			        		    logineditor.putString("lastlogintime",lastlogintime);
			        		    logineditor.putString("registertime",lastlogintime);	
			        		    logineditor.putBoolean("fisrtrun",false);
			        		    logineditor.commit();
                                
			        		    TaskData.user=usermap.get("phoneNo").toString();
			        		    */
			        		    Intent intent=new Intent(RegistersActivity.this,LoginActivity.class);
			        		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			        		    startActivity(intent);
			        		    finish();
			           	   //EventBus.getDefault().post(new UserResponseEvent(response.toString()));
			           	    Log.d(Tags, response.toString());
			           	   }
			           	  if (response.toString().contains("FAIL")||response.toString().contains("null")){
				           		Toast.makeText(getApplication(), "该用户或手机已注册",Toast.LENGTH_SHORT).show(); 
				           	     //Intent intent=new Intent(LoginActivity.this,MainActivity_1.class);
				           	     //startActivity(intent);
				           	   //EventBus.getDefault().post(new UserResponseEvent(response.toString()));
				           	   //Log.d("json back", response.toString());
				           	   }		           	   
			           	 }else{
			               	// Toast.makeText(getActivity(), "no data",Toast.LENGTH_SHORT).show();
			           		Log.d(Tags,"registered failed");   
			           	 }  
			              
			            }  
			        },new Response.ErrorListener() {  
					@Override  
				        public void onErrorResponse(VolleyError error) {  
				           // responseText.setText(error.getMessage()); 
						  new ConnMySQL(getApplicationContext()).connFailure(error);
					      Toast.makeText(getApplication(), "网络忙,请稍后再试或离线进入",Toast.LENGTH_SHORT).show();   		         
						
				        }  
				    });  
				       	
				    requestQueue.add(gsonObjectRequest); 
				    requestQueue.start();
				    				    
	return ;			    
}

	/*
    public void register()  
    {  
    
    String result;
    final Handler handler2;
    Message msg;
   
    final PhoneInfo siminfo = new PhoneInfo(RegistersActivity.this);  
    //Toast.makeText(getBaseContext(), siminfo.getNativePhoneNumber(), Toast.LENGTH_LONG).show();
    
    final Timestamp createdtime = new Timestamp(Calendar.getInstance().getTimeInMillis());
    
    class MyHandler2 extends Handler  
    {  
        @Override  
        public void handleMessage(Message msg) {  
            // TODO Auto-generated method stub  
            super.handleMessage(msg);  
            if(msg.what==1)  
            {  
              registerstatus=msg.getData().getString("result");	
              
              if (msg.getData().getString("result").startsWith("1")){
	   	          Toast.makeText(getApplicationContext(), "注册成功，直接登录", Toast.LENGTH_SHORT).show();
	   	          Intent intent = new Intent();  
                  intent.setClass(RegistersActivity.this, MainActivity_1.class);  
                  startActivity(intent); 
                 // finish();
                } else{
	    	      Toast.makeText(getApplicationContext(), "此账号已被使用", Toast.LENGTH_SHORT).show();  
	            }
            }
        }   
    }       
   
    handler2=new MyHandler2(); 
    class MyThread2 implements Runnable  
    {  
        
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  
        	String uri = "http://192.168.1.105:8090/EasyTest/RegisterServlet";  
    		//String uri2 = "http://www.163.com/";
    	    HttpPost request = new HttpPost(uri); 
    	    //HttpGet request2 = new HttpGet(uri); 
    	    Log.i("create request",request.toString());
    	    JSONArray jolist = new JSONArray(); 
    	    JSONObject param = new JSONObject();
    	    List<UserBean> userlist=new ArrayList<UserBean>(); 
    	    //String username = null;
    	    //String password = null;
    	    
    		try {
    			 param.put("username",username);
    			 param.put("password",password);
    			 param.put("phoneNo", siminfo.getNativePhoneNumber());
    			 //param.put("createdtime", createdtime);
    			 Log.i("object param",param.toString());
    			 jolist.add(param);
    		    	   
    			 userBean = new UserBean();
                 if (userBean!=null){
                 userBean.setUsername(username);
                 userBean.setPassword(password);
                 userBean.setPhoneno(siminfo.getNativePhoneNumber());
                 userBean.setRegisteredtime(createdtime);
                 boolean a = userBean.save();
                 userlist.add(userBean);
                 int b = DataSupport.findBySQL("select * from userbean").getCount();
                 if (a==true){
                 
                 Log.d("保存成功",""+b);
                 }else{
                  Log.d("保存失败", ""+b);  
                 }		 
                } 
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			Log.d("json get ","failed" );
    		}
    	     
    		
    		
    	        //将用户名和密码放入HashMap中         
    	        StringEntity se = null;
    			try {
    				se = new StringEntity(jolist.toString().trim());
    				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
    				 request.setEntity(se); 
    				 Log.i("set entity",jolist.toString());
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
            				    handler2.sendMessage(msg);
            				
            					Log.i("retSrc",retSrc);
            				
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
    
    MyThread2 mythread2=new MyThread2();
    Thread thread_register = new Thread(mythread2);
	thread_register.start();
	
		return ; 
    }  */
}
