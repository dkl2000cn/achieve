package com.easygoal.achieve;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoginActivitybackup extends Activity {
  
    // �ʺź�����  
 
    private TextView tv_register;  
    private Button btn_login;
    private TextView tv_guestlogin;
    private TextView tv_forgetpwd; 
    // ����SQLite���ݿ�  
    
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
        
        /*
    	CONN_FLAG=TaskTool.getConnCode(getApplication()); 
        String userPhone = TaskTool.getPhoneNumber(getApplication());
        SharedPreferences sp = this.getSharedPreferences("userinfo" , MODE_PRIVATE);
        Editor logineditor = sp.edit();
        if(sp.getString("username", null) == null){ //idΪ�գ��û����״�ʹ��Ӧ��
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
        //�������״�ʹ��
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
        		  Toast.makeText(getApplicationContext(), "����δ����,����ʹ��", Toast.LENGTH_SHORT).show();
        	  }
		        
        	}else{
        	   Toast.makeText(getApplicationContext(), "��Ϣ�д���", Toast.LENGTH_SHORT).show();
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
        // ��ת��ע�����?  
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
       
        if (!TextUtils.isEmpty(TaskData.user)&&TaskTool.isMobileNO(TaskData.user)){
        	   et_userphone.setVisibility(View.GONE);
        	   et_username.setHint("手机�?/用户�?");
        }else{	
        	   et_userphone.setVisibility(View.VISIBLE);
        	   et_username.setHint("用户�?");
        	   phoneflag=1;
        }
        
        tv_guestlogin.setOnClickListener(new OnClickListener() {  
        	  
            @Override  
            public void onClick(View v) {  
            	
             
            		SharedPreferences sp = getApplication().getSharedPreferences("userinfo" , MODE_PRIVATE);
            		final Editor logineditor = sp.edit();   
            		phoneNo=sp.getString("phoneNo", "");
            	    
	               if (TextUtils.isEmpty(phoneNo)){
	            	   
	            	   Log.d(Tags,"firstrun"+sp.getBoolean("firstrun", false));
	            	   int times=sp.getInt("logintimes", 0);
	            	   String lastlogintime=new Timestamp(new Date().getTime()).toString();
	            	   logineditor.putBoolean("fisrtrun",false);
	            	   logineditor.putString("phoneNo",TaskData.user);
	            	   logineditor.putString("lastlogintime",lastlogintime);
	            	   times++;
	            	   logineditor.putInt("logintimes",times);
	            	   logineditor.commit();   
	            	   TaskData.privilege=0;
	           		   
	           		   Toast.makeText(getApplication(), "�ο͵�¼",Toast.LENGTH_SHORT).show(); 
	              }else{
	            	    int times = sp.getInt("logintimes", 0);
		                String lastlogintime=new Timestamp(new Date().getTime()).toString();
		    		    logineditor.putString("lastlogintime",lastlogintime);
		    		    times++;
		    		    logineditor.putInt("logintimes",times);
		    		    Log.d(Tags,"logintimes "+times);
		    		    logineditor.commit();
		    		    TaskData.onlinestatus="(����ʹ��)";     		    
		    		    TaskData.privilege=1;
		    		    Toast.makeText(getApplication(), "����ʹ��",Toast.LENGTH_SHORT).show(); 
	              }
	           	/*
            	Editor editor = preference.edit(); //��ȡ�༭��
            	String userPhone = getPhoneNumber();
            	String lastlogintime=new Timestamp(new Date().getTime()).toString();
            	editor.putString("userId",userPhone);
            	editor.putString("username",userPhone+"�ο�");
            	editor.putString("userPhone",userPhone);
            	editor.putString("nickname",userPhone+"�ο�");
            	editor.putString("usertype","����");
            	//editor.putLong("usercredit",1);
                editor.putString("lastlogintime",lastlogintime);
                //times++;
                //editor.putInt("logintimes",times);
                editor.commit();//�ύ�޸�
            	 */        	
            	 Intent intent = new Intent();  
                 intent.setClass(LoginActivitybackup.this, MainActivity_1.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
                 startActivity(intent); 
                 finish();
                   
            }
        });   
        tv_register.setOnClickListener(new OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                Intent intent = new Intent();  
                intent.setClass(LoginActivitybackup.this, RegistersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
                startActivity(intent);  
            }  
            
        });  
        
        btn_login.setOnClickListener(new OnClickListener(){
        	

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				phoneNo = TaskData.user;

				if (phoneflag == 1) {
					String inputstr_phone = et_userphone.getText().toString().trim();
					if (!TextUtils.isEmpty(inputstr_phone) && TaskTool.isMobileNO11(inputstr_phone)) {
						phoneNo = et_userphone.getText().toString().trim();
						Toast.makeText(getApplicationContext(), phoneNo, Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(), "��������ȷ�ֻ���", Toast.LENGTH_SHORT).show();
						//phoneNo="test";
						//Toast.makeText(getApplicationContext(), phoneNo, Toast.LENGTH_SHORT).show();
						return;
					}
				}

				if (TextUtils.isEmpty(et_username.getText().toString().trim()) | TextUtils.isEmpty(et_password.getText().toString().trim())) {
					Toast.makeText(getApplicationContext(), "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
					//Ϊ��
				} else {
					Map params = new HashMap<String, String>();

					if (TaskTool.isMobileNO11(et_username.getText().toString().trim())) {
						password = et_password.getText().toString().trim();
						phoneNo = et_username.getText().toString().trim();
						//params.put("username", username);
						params.put("password", password);
						params.put("phoneNo", phoneNo);
						//Toast.makeText(getApplicationContext(), "�ֻ�����ȷ", Toast.LENGTH_SHORT).show();
					} else {
						username = et_username.getText().toString().trim();
						password = et_password.getText().toString().trim();
						//phoneNo=TaskData.user;
						params.put("username", username);
						params.put("password", password);
						params.put("phoneNo", phoneNo);
						//Toast.makeText(getApplicationContext(), username+password+phoneNo, Toast.LENGTH_SHORT).show();
					}

					btn_login.setText("��¼��...");
					btn_login.setTextColor(getResources().getColor(R.color.darkgray));
				  /*
				 AlertDialog.Builder dialog=new AlertDialog.Builder(LoginActivity.this);
				 //dialog.setco(getResources().getDrawable(R.drawable.running)).create().show();
				 dlg_login=dialog.create();
				 dlg_login.setTitle("���Ժ�...");;
				 dlg_login.show();
				 */


					LoginRequestPost("UserServlet", params);
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
			    phoneNo=TaskData.user;
					
				if(phoneflag==1){
						 if (!TextUtils.isEmpty(phoneNo)){ 
						     phoneNo=et_userphone.getText().toString().trim();
						 }else{
							 Toast.makeText(getApplicationContext(),"�������ֻ���",Toast.LENGTH_SHORT).show();
						 }
			   }
					   	
		  	if(TextUtils.isEmpty(et_username.getText().toString().trim())){ 
		     	Toast.makeText(getApplicationContext(),"���벻��Ϊ��",Toast.LENGTH_SHORT).show();
		   	//Ϊ��  
		     	}else{  
		     	
		     		 Map params=new HashMap<String,String>();
		     	    
				    	if (TaskTool.isMobileNO11(et_username.getText().toString().trim())&&et_username.getText().toString().trim().equals(TaskData.user)){
							 //password=et_password.getText().toString().trim(); 
							 phoneNo=et_username.getText().toString().trim(); 
							 //params.put("username", username);
							 //params.put("password", password);
						     params.put("phoneNo", phoneNo);
						     //Toast.makeText(getApplicationContext(), "�ֻ�����ȷ", Toast.LENGTH_SHORT).show();
						}else{
							 username=et_username.getText().toString().trim();  
							// password=et_password.getText().toString().trim(); 
							 //phoneNo=TaskData.user;
							 params.put("username", username);
					     	 //params.put("password", password);
					     	 params.put("phoneNo", phoneNo);
					     	 //Toast.makeText(getApplicationContext(), "�ֻ�������", Toast.LENGTH_SHORT).show();
						}
						
		       	     //params.put("username",username );
		       	     //params.put("phoneNo", phoneNo);
		       	    
		             passwordRequestPost(getApplication(),"UserServlet",params);	
		         
		     	 //Toast.makeText(getApplicationContext(), "��½ʧ��"+loginresult, Toast.LENGTH_SHORT).show();  
  
		   		}
				
            }	
			
        });  
        
        
        
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
				           		   	Toast.makeText(getApplication(), "��¼�ɹ�",Toast.LENGTH_SHORT).show();
				           		    /*
				           		   	Message msg=new Message();
				           		    msg.what=9;
				           		   	mhandler.sendMessage(msg); 
				           		   	*/
				           		   	//dlg_login.dismiss();
				           		   	String login_username = null;
				           		   	String login_nickname=null;
				           		   	String login_usertype=null;
				           		   	String login_times=null;
				           		    String login_phoneno=null;
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
				           		   int logintimes = loginsp.getInt("logintimes", 0);
				           		   String lastlogintime=new Timestamp(new Date().getTime()).toString();
				           		   editor.putString("username", login_username);
				           		   //editor.putString("password", login_userpwd);
				           		   editor.putString("phoneNo", phoneNo);
				           		   editor.putString("nickname", login_nickname);
				           		   editor.putString("usertype", login_usertype);
				           		   editor.putString("userid", login_userid);
				           		   //editor.putString("username",login_username);
				           		   editor.putString("lastlogintime",lastlogintime);
				           		   editor.putInt("userprivilege",login_userprivilege);
				           		  
				           		   TaskData.user= phoneNo;
				           		   logintimes++;
				           		   editor.putInt("logintimes",logintimes);
				           		   Log.d(Tags,"logintimes "+logintimes);
				           		   editor.commit();
				    		  
				           		   TaskData.onlinestatus="(����)";

				           		   Log.d(TaskData.t_tags,""+(new Date().getTime()-TaskData.t_start)+"ms");	  
				        		   HashMap lum=new HashMap();

				        		   lum.put("username", login_username);
				        		   lum.put("phoneNo",  phoneNo);
				        		   lum.put("lastlogintime",lastlogintime);
				            	   lum.put("logintimes",logintimes );
				        		   //TaskTool.UpdateUserRequestPost(getApplication(),"UserServlet",lum);
				        		  
				        		   Intent intent= new Intent(LoginActivitybackup.this,MainActivity_1.class);
				        		   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				        		   startActivity(intent);
				           		   Log.d(TaskData.t_tags,""+(new Date().getTime()-TaskData.t_start)+"ms");
				           		   finish();  	  		   	           	     
				           	     
				           	   }else{
				           		   Toast.makeText(getApplication(), "�û������������?",Toast.LENGTH_SHORT).show(); 
				           	     //Intent intent=new Intent(LoginActivity.this,MainActivity_1.class);
				           	     //startActivity(intent);
				           		   //EventBus.getDefault().post(new UserResponseEvent(response.toString()));
				           		   //Log.d("json back", response.toString());
				           		   //TaskData.onlinestatus="(����ʹ��)";
				           		  btn_login.setText("��¼");
							      btn_login.setTextColor(getResources().getColor(R.color.black));
				           		    	
				           	   }
				           	   
				           	 }else{
				               	
					           	    Toast.makeText(getApplication(), "����Ӧ,����ʹ��",Toast.LENGTH_SHORT).show(); 
					           	     //Intent intent=new Intent(LoginActivity.this,MainActivity_1.class);
					           	     //startActivity(intent);
					           	   //EventBus.getDefault().post(new UserResponseEvent(response.toString()));
					           	   //Log.d("json back", response.toString());
					           		//TaskData.onlinestatus="(����ʹ��)";
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
					    		    TaskData.onlinestatus="(����ʹ��)";   
					           	    Intent intent= new Intent(LoginActivitybackup.this,MainActivity_1.class);
					           	    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					           	    startActivity(intent);
			    	    	    	finish();  	
				           	 
				           	 }  
				              
				            }  
				        },new Response.ErrorListener() {  
						@Override  
					        public void onErrorResponse(VolleyError error) {  
					           // responseText.setText(error.getMessage()); 
							      //TaskTool.connFailure(getApplicationContext(), error);
							      Toast.makeText(getApplication(), "����æ,���Ժ����Ի����߽���",Toast.LENGTH_SHORT).show(); 
							      btn_login.setText("��¼");
							      btn_login.setTextColor(getResources().getColor(R.color.black));
						       	
					        }  
					    });  
					       	
					    requestQueue.add(gsonObjectRequest); 
					    requestQueue.start();
					    				    
		return ;			    
	}


		public void passwordRequestPost(Context context,String servletName,Map params){ 
			
			   // String url = "http://route.showapi.com/213-3";  
			    //String url = "http://192.168.1.100:8090/EasyTest/TaskmainServlet";  
			    //Map<String, String> params = new HashMap<String, String>();  
			    //params.put("taskname", "value1");  
			    //params.put("deadline", "value2"); 
			    final Context mContext=context;
			    String url = TaskData.hostname+servletName;  
				//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
				RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
				 
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
	                            	  Toast.makeText(getApplication(), "���������ѷ�����Ϣ��",Toast.LENGTH_LONG).show();
	   				           	      TaskTool.notiSend(mContext,"����������:",pwd,System.currentTimeMillis());
	                               }catch(Exception e){
	                            	  Toast.makeText(getApplication(),"[Ŀ���]����������:"+pwd,Toast.LENGTH_SHORT).show(); 
	                            	  Log.d("password send failure", e.toString());
	                            	  e.printStackTrace();
	                               }
	                               //Toast.makeText(getApplication(),"�û������ֻ��Ų���ȷ",Toast.LENGTH_LONG).show();
				           	    }else{
				           	       Toast.makeText(getApplication(),"�û������ֻ��Ų���ȷ",Toast.LENGTH_SHORT).show();
				           	    }
						     
				           }else{
				               	Toast.makeText(getApplication(), "�û������ֻ��Ų���ȷ",Toast.LENGTH_SHORT).show();  
				              
				           }  
						 } 	
				        },new Response.ErrorListener() {  
						@Override  
					        public void onErrorResponse(VolleyError error) {  
					           // responseText.setText(error.getMessage());
							       //TaskTool.connFailure(getApplicationContext(), error);
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
			
			
		
		 public void UserLoginRequestPost(String servletName,Map params){ 
				
			   // String url = "http://route.showapi.com/213-3";  
			    //String url = "http://192.168.1.100:8090/EasyTest/TaskmainServlet";  
			    //Map<String, String> params = new HashMap<String, String>();  
			    //params.put("taskname", "value1");  
			    //params.put("deadline", "value2"); 
			    String url = TaskData.hostname+servletName;  
				//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
				RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
			 
					    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
					    JsonArray jolist=new JsonArray(); 
					    jolist.add(jostring);
					
					mGsonArrayRequest gsonObjectRequest = new mGsonArrayRequest(url,jolist,"5",new Response.Listener<JSONArray>() {  
						    
						@Override  
				           public void onResponse(JSONArray response) {  
				           	if(response != null){
				           		//Log.d("json back", "ddd "+response.toString());
				           		
				           		//Toast.makeText(getActivity(), "back ok",Toast.LENGTH_SHORT).show();
				                //TaskData.adapterUpdate();  
				          
				           	   EventBus.getDefault().post(new UserResponseEvent(response.toString()));
				           	   Log.d(Tags,"json back"+response.toString());				        	   
				           	   
				           	 }else{
				               	 Toast.makeText(getApplication(), "no data",Toast.LENGTH_SHORT).show();
				               }  
				              
				            }  
				        },new Response.ErrorListener() {  
						@Override  
					        public void onErrorResponse(VolleyError error) {  
					           // responseText.setText(error.getMessage());  
							     //TaskTool.connFailure(getApplicationContext(), error);
					        }  
					    });  
					       	
					    requestQueue.add(gsonObjectRequest); 
					    requestQueue.start();
					    				    
		return ;			    
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
	   	          Toast.makeText(getApplicationContext(), "��½�ɹ�"+TaskData.loginstatus, Toast.LENGTH_SHORT).show();
	   	          Intent intent = new Intent();  
                  intent.setClass(LoginActivity.this, MainActivity_1.class);  
                  startActivity(intent); 
                 // finish();
                } else{
	    	      Toast.makeText(getApplicationContext(), "��½ʧ��"+TaskData.loginstatus, Toast.LENGTH_SHORT).show();  
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
    	     
    	        //���û������������HashMap��         
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
    	        
    	        
    	        // ��������  
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
    						
            	        	 // �õ�Ӧ����ַ�������Ҳ��һ��? JSON ��ʽ���������?     
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
            	                //Toast.makeText(getApplicationContext(), "��½�ɹ�"+loginresult, Toast.LENGTH_SHORT).show();
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
                // ������Ϣ��  
                new AlertDialog.Builder(MainActivity.this).setTitle("����")  
                        .setMessage("�ʺŻ����벻�ܿ�").setPositiveButton("ȷ��", null)  
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
  
        // �ж�������û��Ƿ����?  
       public Boolean isUserinfo(String name, String pwd) {  
            try{  
                String str="select * from tb_user where name=? and password=?";  
                Cursor cursor = TaskData.db_TdDB.rawQuery(str, new String []{name,pwd});  
                if(cursor.getCount()<=0){  
                    new AlertDialog.Builder(LoginActivity.this).setTitle("����")  
                    .setMessage("�ʺŻ��������?").setPositiveButton("ȷ��", null)  
                    .show();  
                    return false;  
                }else{  
                    new AlertDialog.Builder(LoginActivity.this).setTitle("��ȷ")  
                    .setMessage("�ɹ���¼").setPositiveButton("ȷ��", null)  
                    .show();
                    return true;  
                }  
                  
            }catch(SQLiteException e){  
                createDb();  
            }  
            return false;  
        }         
        
    }  
     
    // �������ݿ���û���?  
    public void createDb() {  
        
    }  */
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        getMenuInflater().inflate(R.menu.main_menu, menu);  
        return true;  
    }  
  
}
