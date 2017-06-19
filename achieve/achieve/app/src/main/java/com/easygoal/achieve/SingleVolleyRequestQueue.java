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
    //˽�л�����
    private static SingleVolleyRequestQueue singleQueue;
    private RequestQueue requestQueue;
    private static Context context;


    //˽�л�����
    private SingleVolleyRequestQueue(Context context){
        this.context=context;
        requestQueue=getRequestQueue();
    }
    //�ṩ���������еķ���
    private RequestQueue getRequestQueue(){
        if(requestQueue==null){
            //DefaultHttpClient mHttpClient = new DefaultHttpClient();
            // create the request queue
            //requestQueue = Volley.newRequestQueue(context, new HttpClientStack(mHttpClient));
            requestQueue= Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
    //�ṩ��ȡ�����ķ���
    public static synchronized SingleVolleyRequestQueue getInstance(Context context){   //synchronized������ֹ����
        if(singleQueue==null){
            singleQueue=new SingleVolleyRequestQueue(context);
        }
        return  singleQueue;
    }
    public <T> void  add(Request<T> req){
//        requestQueue.add(req);  //��ֹ��������ɿ�ָ���쳣������һ�㲻��
        getRequestQueue().add(req);
    }
    public  void  start(){
//        requestQueue.add(req);  //��ֹ��������ɿ�ָ���쳣������һ�㲻��
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