package com.easygoal.achieve;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Acer on 2017/4/22.
 */

public class HttpUtils {



    public static String RequestPost(String servletName, Map params, String code)
    {   String Tags = "|HttpUtils|";
        String msg = "";
        String url = TaskData.hostname + servletName;
        //String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
        //RequestQueue requestQueue=Volley.newRequestQueue(context);

        JsonObject jostring = new Gson().toJsonTree(params).getAsJsonObject();
        JsonArray jolist = new JsonArray();
        jolist.add(jostring);
        Log.d(Tags, "login data" + jolist.toString());
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            //��������ʽ,����ʱ��Ϣ
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //������������,���:
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //Post��ʽ���ܻ���,���ֶ�����Ϊfalse
            conn.setUseCaches(false);
            //�������������:
            String data =code+jolist.toString();
            //�������дһЩ����ͷ�Ķ���...
            //��ȡ�����
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
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
                return msg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }


        public static String uploadFile(Context context, String servletName, String imgUri,String filePath) {
            String url = TaskData.hostname + servletName;
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                //��������ʽ,����ʱ��Ϣ
                conn.setRequestMethod("POST");
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                //������������,���:
                conn.setDoOutput(true);
                conn.setDoInput(true);
                //Post��ʽ���ܻ���,���ֶ�����Ϊfalse
                conn.setUseCaches(false);
                conn.addRequestProperty("filePath", filePath);
                /*
                ContentResolver resolver = context.getContentResolver();
                byte[] imgData = new byte[0];
                imgData= ImageUtils.readStream(resolver.openInputStream(Uri.parse(imgUri.toString())));
                */
                //�������������:
                //String data =code+jolist.toString();
                //�������дһЩ����ͷ�Ķ���...
                //��ȡ�����
                OutputStream out = conn.getOutputStream();
                out.write(ImageUtils.getByteFromUri(context,imgUri));
                out.flush();
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
                    String msg = new String(message.toByteArray());
                    return msg;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    public static String ImageRequestPost(String servletName, Map params, String code)
    {   String Tags = "|HttpUtils|ImageRequestPost}";
        String msg ="";
        String url = TaskData.hostname + servletName;
        //String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
        //RequestQueue requestQueue=Volley.newRequestQueue(context);

        JsonObject jostring = new Gson().toJsonTree(params).getAsJsonObject();
        JsonArray jolist = new JsonArray();
        jolist.add(jostring);
        Log.d(Tags, "login data" + jolist.toString());
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            //��������ʽ,����ʱ��Ϣ
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //������������,���:
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //Post��ʽ���ܻ���,���ֶ�����Ϊfalse
            conn.setUseCaches(false);
            //�������������:
            String data =code+jolist.toString();
            //�������дһЩ����ͷ�Ķ���...
            //��ȡ�����
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
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
                return msg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static String TokenRequestPost(Map<String, String> params, String encode) {
        final String Tags = "|HttpUtils|connRong|";
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
