package com.easygoal.achieve;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie2;

/**
 * Created by Acer on 2017/4/12.
 */

public class SingleVolleyRequestQueue {
    //私有化属性
    private static SingleVolleyRequestQueue singleQueue;
    private RequestQueue requestQueue;
    private static Context context;


    //私有化构造
    private SingleVolleyRequestQueue(Context context){
        this.context=context;
        requestQueue=getRequestQueue();
    }
    //提供获得请求队列的方法
    private RequestQueue getRequestQueue(){
        if(requestQueue==null){
            //DefaultHttpClient mHttpClient = new DefaultHttpClient();
            // create the request queue
            //requestQueue = Volley.newRequestQueue(context, new HttpClientStack(mHttpClient));
            requestQueue= Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
    //提供获取类对象的方法
    public static synchronized SingleVolleyRequestQueue getInstance(Context context){   //synchronized加锁防止并发
        if(singleQueue==null){
            singleQueue=new SingleVolleyRequestQueue(context);
        }
        return  singleQueue;
    }
    public <T> void  add(Request<T> req){
//        requestQueue.add(req);  //防止被回收造成空指针异常，所以一般不用
        getRequestQueue().add(req);
    }
    public  void  start(){
//        requestQueue.add(req);  //防止被回收造成空指针异常，所以一般不用
        getRequestQueue().start();
    }
    /**
     * Method to set a cookie

    public void setCookie() {
        CookieStore cs = mHttpClient.getCookieStore();
        // create a cookie
        cs.addCookie(new BasicClientCookie2("cookie", "spooky"));
    }

     */
    // add the cookie before adding the request to the queue


}