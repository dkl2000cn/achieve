package com.easygoal.achieve;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Acer on 2017/4/12.
 */

public class LoginUtils {

     public static int Login(Context context, JSONArray response,String phoneNo,Button btn_login){
         String Tags="LoginUtils|Login";
         int login_flag;
         if (response!=null){
                if (response.toString().contains(phoneNo)) {
                     Login_Success(context, response, phoneNo);
                     login_flag=1;
                 }else {
                    Login_Fail(context, btn_login);
                     login_flag=0;
                }
         }else{
                    Login_OfflineLogin(context);
                    login_flag=-1;
         }
         return login_flag;
     }

     public static void Login_Success(Context context, JSONArray response,String phoneNo){
         String Tags="LoginUtils|Login_Success";
         int times=0;
         //Toast.makeText(getActivity(), "back ok",Toast.LENGTH_SHORT).show();
         //TaskData.adapterUpdate();
         Log.d(Tags,"json back"+response.toString());
         if (response.toString().contains(phoneNo)) {
             Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
             String login_username = null;
             String login_nickname = null;
             String login_usertype = null;
             String login_times = "0";
             String login_phoneno = null;
             String login_organization = null;
             String login_userid = null;
             int login_userprivilege = 0;
             try {
                 login_username = response.getJSONObject(0).get("username").toString();
                 login_nickname = response.getJSONObject(0).get("nickname").toString();
                 login_usertype = response.getJSONObject(0).get("usertype").toString();
                 //login_phoneno=response.getJSONObject(0).get("phoneNo").toString();
                 login_userid = response.getJSONObject(0).get("userId").toString();
                 login_userprivilege = response.getJSONObject(0).getInt("userprivilege");
                 login_times = response.getJSONObject(0).get("logintimes").toString();
                 login_organization = response.getJSONObject(0).get("organization").toString();
                 Log.d(Tags, "login info" + login_username + "\n" + login_nickname + "\n");

             } catch (JSONException e) {
                 // TODO Auto-generated catch block
                 //Toast.makeText(getApplication(), e.toString(),Toast.LENGTH_SHORT).show();
                 e.printStackTrace();
             }
             //Toast.makeText(getApplication(), login_username+"\n"+login_nickname+"\n"+login_usertype+login_userprivilege,Toast.LENGTH_SHORT).show();
             //EventBus.getDefault().post(new UserResponseEvent(response.toString()));
             //Log.d("json back", response.toString());
             SharedPreferences loginsp = context.getSharedPreferences("userinfo", context.MODE_PRIVATE);
             SharedPreferences.Editor editor = loginsp.edit();

             int logintimes = Integer.parseInt(login_times);
             String lastlogintime = new Timestamp(new Date().getTime()).toString();
             editor.putString("username", login_username);
             //editor.putString("password", login_userpwd);
             editor.putString("phoneNo", phoneNo);
             editor.putString("nickname", login_nickname);
             editor.putString("usertype", login_usertype);
             editor.putString("userid", login_userid);
             editor.putString("organization", login_organization);
             //editor.putString("username",login_username);
             editor.putString("lastlogintime", lastlogintime);
             editor.putInt("userprivilege", login_userprivilege);

             TaskData.user = phoneNo;
             TaskData.usernickname = login_nickname;
             TaskData.usergroup = login_organization;
             logintimes++;
             editor.putInt("logintimes", logintimes);
             Log.d(Tags, "logintimes " + logintimes);
             editor.commit();

             TaskData.onlinestatus = "(在线)";

             Log.d(TaskData.t_tags, "" + (new Date().getTime() - TaskData.t_start) + "ms");

             HashMap lum = new HashMap();
             lum.put("username", login_username);
             lum.put("phoneNo", phoneNo);
             lum.put("lastlogintime", lastlogintime);
             lum.put("logintimes", logintimes);
             //TaskTool.UpdateUserRequestPost(getApplication(),"UserServlet",lum);
             new RetrofitUtils(context).UpdateUserRequestPost("UserServlet", lum);

             //Intent intent = new Intent((Activity)context, MainActivity_1.class);
             Intent intent = new Intent();
             intent.setComponent(new ComponentName("com.easygoal.achieve", "com.easygoal.achieve.MainActivity_1"));
             intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             context.startActivity(intent);
             Log.d(TaskData.t_tags, "" + (new Date().getTime() - TaskData.t_start) + "ms");
             //(Application)context.getApplicationContext().finish();
             //((Activity)context).finish();
         }else{

         }
     }

    public static void Login_Fail(Context context, Button btn_login){
        String Tags="LoginUtils|Login_Fail";
        Toast.makeText(context, "用户名或密码错误",Toast.LENGTH_SHORT).show();
        btn_login.setText("登录");
        btn_login.setTextColor(context.getResources().getColor(R.color.black));
        Log.d(TaskData.t_tags, "" + (new Date().getTime() - TaskData.t_start) + "ms");

    }

    public static void Login_OfflineLogin(Context context){

        String Tags="LoginUtils|Login_OfflineLogin";
        Toast.makeText(context, "无响应,离线使用",Toast.LENGTH_SHORT).show();
        SharedPreferences loginsp = context.getSharedPreferences("userinfo" ,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginsp.edit();
        int times = loginsp.getInt("logintimes", 0);
        String lastlogintime=new Timestamp(new Date().getTime()).toString();
        editor.putString("phoneNo",TaskData.user);
        editor.putString("lastlogintime",lastlogintime);
        times++;
        editor.putInt("logintimes",times);
        Log.d(Tags,"logintimes "+times);
        editor.commit();
        TaskData.onlinestatus="(离线使用)";
    }

}
