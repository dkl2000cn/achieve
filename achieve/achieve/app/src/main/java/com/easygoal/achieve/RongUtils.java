package com.easygoal.achieve;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;


/**
 * Created by Acer on 2017/4/23.
 */

public class RongUtils {


    public static void refreshTeamPic(String userId,String name,String uriString){
        if ( RongIM.getInstance()!=null) {

                RongIM.getInstance().refreshUserInfoCache(new UserInfo(
                        userId,
                        name,
                        Uri.parse(uriString)
                ));

            }
    }

    public static void connRong(final Context context){
        final String Tags="|RongUtil|connRong|";
        String Token_denton = "he1vJ95T6bGl4tOeJ6q30cCLYaQnoWV3V6nBdhm9E/3/kWXQ9Np/jxylaPWyc9LkLIB3fXiIlMLKgtTn3JziR1EfAolkvnNS";//test
        String Token_dkl = "BhCBWeWHPvfdny8NVzf7QkPqo1XOKLN3N+vuSNCqIOr5E7LN3CAQCZ1tw4mlDKrcJiJ0yiT+nLfLKQRSLj6+55BPwYvvhbk1";//test
        /**
         * IMKit SDK���õڶ���
         *
         * �����������������
         *
         */
        final String imguri="http://img.blog.csdn.net/20160729161012461";
        if (TaskData.user.equals("15802131279")) {
            RongIM.connect(Token_denton, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    //Connect Token ʧЧ��״̬������Ҫ���»�ȡ Token
                }

                @Override
                public void onSuccess(String userId) {
                    Log.e(Tags, "����onSuccess��-" + userId);
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(TaskData.user, TaskData.usernickname, Uri.parse(imguri)));
                    Toast.makeText(context,""+TaskData.user+"���ӳɹ�", Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e("MainActivity", "����onError��-" + errorCode);
                }
            });
        }

        if (TaskData.user.equals("13917981639")) {
            RongIM.connect(Token_dkl, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    //Connect Token ʧЧ��״̬������Ҫ���»�ȡ Token
                }

                @Override
                public void onSuccess(String userId) {
                    Log.e(Tags, "����onSuccess��-" + userId);
                   /*
					RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId.equals(TaskData.user)?TaskData.user:"10010",
							userId.equals(TaskData.user)?"dffdafdfd":"dfdfdfd",
							userId.equals(TaskData.user)?Uri.parse(imguri):Uri.parse(imguri)));
					*/
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(TaskData.user, TaskData.usernickname, Uri.parse(imguri)));
                    Toast.makeText(context,""+TaskData.user+"���ӳɹ�", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e("MainActivity", "����onError��-" + errorCode);
                }
            });
        }
    }
    /*
    public static void refreshRong(Map<String, String> params,final Context context) {
        final String Tags = "|RongUtil|connRong|";

    }*/

    public static void connRong(final Context context, final String token, final Map<String, String> params){
        final String Tags="|RongUtil|connRong|";

        /**
         * IMKit SDK���õڶ���
         *
         * �����������������
         *
         */

            RongIM.getInstance().connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    //Connect Token ʧЧ��״̬������Ҫ���»�ȡ Token
                }

                @Override
                public void onSuccess(String userId) {

                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(
                            params.get("userId"),
                            params.get("name"),
                            Uri.parse(params.get("portraitUri"))));
                    Log.d(Tags, "����onSuccess��-" +token+ "   "+userId+params.get("portraitUri"));

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e("MainActivity", "����onError��-" + errorCode);
                }
            });
        }


    public static void disConnRong(){
        final String Tags="|RongUtil|connRong|";

        /**
         * IMKit SDK���õڶ���
         *
         * �����������������
         *
         */

        RongIM.getInstance().disconnect();
    }

    public static String TokenRequestPost(Map<String, String> params, String encode) {
        final String Tags = "|RongUtil|connRong|";
        String msg = null;
        StringBuffer res = new StringBuffer();
        String url = "https://api.cn.ronghub.com/user/getToken.json";
        String APPKEY = "App-Key";
        String NONCE = "Nonce";
        String TIMESTAMP = "Timestamp";
        String SIGNATURE = "Signature";

        String App_Key = "k51hidwqkez3b"; //������ƽ̨����� App Key��
        String App_Secret = "cLI4irg3NfshQ";
        String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);//ʱ������� 1970 �� 1 �� 1 �� 0 �� 0 �� 0 �뿪ʼ�����ڵ�������
        String Nonce = String.valueOf(Math.floor(Math.random() * 1000000));//��������޳������ơ�
        String Signature = sha1(App_Secret + Nonce + Timestamp);//����ǩ����
        Log.d(Tags + "Signature", Signature);

        StringBuffer content = new StringBuffer();

        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                // ���ת�����
                try {
                    content.append(entry.getKey()).append("=").append(
                            URLEncoder.encode(entry.getValue(), encode))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            content.deleteCharAt(content.length() - 1);
        }

        // System.out.println(buffer.toString());
        // ɾ��������һ��&

        /*
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpURLConnection.addRequestProperty("version",
                SystemInfo.getVersionChars());
        httpPost.setHeader("App-Key", App_Key);
        httpPost.setHeader("Timestamp", Timestamp);
        httpPost.setHeader("Nonce", Nonce);
        httpPost.setHeader("Signature", Signature);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("userId", username));
        HttpResponse httpResponse = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
            httpResponse = httpClient.execute(httpPost);
            BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line = null;
            while ((line = br.readLine()) != null) {
                res.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            //��������ʽ,����ʱ��Ϣ
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //������������,���:
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //Post��ʽ���ܻ���,���ֶ�����Ϊfalse
            conn.setUseCaches(false);
            conn.addRequestProperty(APPKEY, App_Key);
            conn.addRequestProperty(TIMESTAMP , Timestamp);
            conn.addRequestProperty(NONCE, Nonce);
            conn.addRequestProperty(SIGNATURE, Signature);
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            byte[] mydata = content .toString().getBytes();
            //String data =username.toString();
            //�������дһЩ����ͷ�Ķ���...
            //��ȡ�����
            OutputStream out = conn.getOutputStream();
            out.write(mydata, 0, mydata.length);
            out.flush();
            Log.i(Tags , "token code:" +conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                // ��ȡ��Ӧ������������
                InputStream is = conn.getInputStream();
                // �����ֽ����������
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // �����ȡ�ĳ���
                int len = 0;
                // ���建����
                byte buffer[] = new byte[1024];
                // ���ջ������Ĵ�С��ѭ����ȡ
                while ((len = is.read(buffer)) != -1) {
                    // ���ݶ�ȡ�ĳ���д�뵽os������
                    message.write(buffer, 0, len);
                }
                // �ͷ���Դ
                is.close();
                message.close();
                // �����ַ���
                msg = new String(message.toByteArray());

            }
            Gson gson = new Gson();
            Log.i(Tags , "token msg:" +msg);
            RongUserBean userRespone = gson.fromJson(msg , RongUserBean.class);
            //Logger.i(userRespone.getCode()+"");
            if (userRespone != null) {
                return userRespone.getToken();
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String GetRongCloudToken(String username) {
        final String Tags="|RongUtil|connRong|";
        StringBuffer res = new StringBuffer();
        String url = "https://api.cn.ronghub.com/user/getToken.json";
        String App_Key = "k51hidwqkez3b"; //������ƽ̨����� App Key��
        String App_Secret = "cLI4irg3NfshQ";
        String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);//ʱ������� 1970 �� 1 �� 1 �� 0 �� 0 �� 0 �뿪ʼ�����ڵ�������
        String Nonce = String.valueOf(Math.floor(Math.random() * 1000000));//��������޳������ơ�
        String Signature = sha1(App_Secret + Nonce + Timestamp);//����ǩ����
        Log.d(Tags+"Signature",Signature);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("App-Key", App_Key);
        httpPost.setHeader("Timestamp", Timestamp);
        httpPost.setHeader("Nonce", Nonce);
        httpPost.setHeader("Signature", Signature);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("userId",username));
        HttpResponse httpResponse = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,"utf-8"));
            httpResponse = httpClient.execute(httpPost);
            BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line = null;
            while ((line = br.readLine()) != null) {
                res.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Log.i(Tags+"RongUtils",""+res.toString());
        RongUserBean userRespone = gson.fromJson(res.toString(), RongUserBean.class);
        //Logger.i(userRespone.getCode()+"");
        if (userRespone!=null) {
            return userRespone.getToken();
        }else{
            return "";
        }
    }
    //SHA1����//http://www.rongcloud.cn/docs/server.html#ͨ��_API_�ӿ�ǩ������
    private static String sha1(String data){
        StringBuffer buf = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            for(int i = 0 ; i < bits.length;i++){
                int a = bits[i];
                if(a<0) a+=256;
                if(a<16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
        }catch(Exception e){

        }
        return buf.toString();
    }
    /**
     * �����û���Ϣ���ṩ�ߣ��� R/ongIM ���û�ȡ�û����ƺ�ͷ����Ϣ��
     *
     * @param userInfoProvider �û���Ϣ�ṩ�ߡ�
     * @param isCacheUserInfo  �����Ƿ��� IMKit �������û���Ϣ��<br>
     *                         ��� App �ṩ�� UserInfoProvider
     *                         ÿ�ζ���Ҫͨ�����������û����ݣ������ǽ��û����ݻ��浽�����ڴ棬��Ӱ���û���Ϣ�ļ����ٶȣ�<br>
     *                         ��ʱ��ý�����������Ϊ true���� IMKit ���û���Ϣ���浽�����ڴ��С�
     * @see UserInfoProvider
     */
    /*
     RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

        @Override
        public UserInfo getUserInfo(String userId) {

            return findUserById(userId);//���� userId ȥ����û�ϵͳ���ѯ��Ӧ���û���Ϣ���ظ����� SDK��
        }

    }, true);*/
}
