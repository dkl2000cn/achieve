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
            //设置请求方式,请求超时信息
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //设置运行输入,输出:
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //Post方式不能缓存,需手动设置为false
            conn.setUseCaches(false);
            //我们请求的数据:
            String data =code+jolist.toString();
            //这里可以写一些请求头的东东...
            //获取输出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
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
                //设置请求方式,请求超时信息
                conn.setRequestMethod("POST");
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                //设置运行输入,输出:
                conn.setDoOutput(true);
                conn.setDoInput(true);
                //Post方式不能缓存,需手动设置为false
                conn.setUseCaches(false);
                conn.addRequestProperty("filePath", filePath);
                /*
                ContentResolver resolver = context.getContentResolver();
                byte[] imgData = new byte[0];
                imgData= ImageUtils.readStream(resolver.openInputStream(Uri.parse(imgUri.toString())));
                */
                //我们请求的数据:
                //String data =code+jolist.toString();
                //这里可以写一些请求头的东东...
                //获取输出流
                OutputStream out = conn.getOutputStream();
                out.write(ImageUtils.getByteFromUri(context,imgUri));
                out.flush();
                if (conn.getResponseCode() == 200) {
                    // 获取响应的输入流对象
                    InputStream is = conn.getInputStream();
                    // 创建字节输出流对象
                    ByteArrayOutputStream message = new ByteArrayOutputStream();
                    // 定义读取的长度
                    int len = 0;
                    // 定义缓冲区
                    byte buffer[] = new byte[1024];
                    // 按照缓冲区的大小，循环读取
                    while ((len = is.read(buffer)) != -1) {
                        // 根据读取的长度写入到os对象中
                        message.write(buffer, 0, len);
                    }
                    // 释放资源
                    is.close();
                    message.close();
                    // 返回字符串
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
            //设置请求方式,请求超时信息
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //设置运行输入,输出:
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //Post方式不能缓存,需手动设置为false
            conn.setUseCaches(false);
            //我们请求的数据:
            String data =code+jolist.toString();
            //这里可以写一些请求头的东东...
            //获取输出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
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

        String App_Key = "k51hidwqkez3b"; //开发者平台分配的 App Key。
        String App_Secret = "cLI4irg3NfshQ";
        String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳，从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的秒数。
        String Nonce = String.valueOf(Math.floor(Math.random() * 1000000));//随机数，无长度限制。
        String Signature = sha1(App_Secret + Nonce + Timestamp);//数据签名。
        Log.d(Tags + "Signature", Signature);

        StringBuffer content = new StringBuffer();

            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    // 完成转码操作
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
            // 删除掉最有一个&

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
            //设置请求方式,请求超时信息
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //设置运行输入,输出:
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //Post方式不能缓存,需手动设置为false
            conn.setUseCaches(false);
            conn.addRequestProperty(APPKEY, App_Key);
            conn.addRequestProperty(TIMESTAMP , Timestamp);
            conn.addRequestProperty(NONCE, Nonce);
            conn.addRequestProperty(SIGNATURE, Signature);
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            byte[] mydata = content .toString().getBytes();
            //String data =username.toString();
            //这里可以写一些请求头的东东...
            //获取输出流
            OutputStream out = conn.getOutputStream();
            out.write(mydata, 0, mydata.length);
            out.flush();
            Log.i(Tags , "token code:" +conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
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

        //SHA1加密//http://www.rongcloud.cn/docs/server.html#通用_API_接口签名规则
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
     * 设置用户信息的提供者，供 R/ongIM 调用获取用户名称和头像信息。
     *
     * @param userInfoProvider 用户信息提供者。
     * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
     *                         如果 App 提供的 UserInfoProvider
     *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
     *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
     * @see UserInfoProvider
     */
    /*
     RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

        @Override
        public UserInfo getUserInfo(String userId) {

            return findUserById(userId);//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
        }

    }, true);*/


}
