package com.easygoal.achieve;

/**
 * Created by Acer on 2017/3/14.
 */


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RetrofitUtils {

    private Context context;
    public static int CONNECT_TIMEOUT = 20;
    public static int READ_TIMEOUT  = 20;
    public static int WRITE_TIMEOUT  = 20;
    public static int CACHE_SIZE= 10 * 1024 * 1024;
    public static String Tags= "RetrofitUtils";
    public RetrofitUtils(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    interface UserBeanService {
        @POST("http://123.206.229.72:8080/EasyTest/UserServlet")
        Observable<String> queryResult();

        //Call<String> query(@Body RequestBody requestBody);
        //Call<String> query();
        //@POST("http://123.206.229.72:8080/EasyTest/UserServlet")
    }

    public void UpdateUserRequestPost(String servletName,Map params) {
        final String tags="RetrofitUtils|UpdateUserRequestPost|";
        final String url = TaskData.hostname + servletName;

        JsonArray jolist = new JsonArray();
        JsonObject jostring = new Gson().toJsonTree(params).getAsJsonObject();
        jolist.add(jostring);
        final String postBody = "6" + jolist.toString();
        //final String postBody = "7"+"[]";

        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");
        final RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
        Log.d("postBody", postBody);

        Interceptor mTokenInterceptor = new Interceptor() {
            @Override public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                if (TaskData.token == null ) {
               // if (TaskData.token == null || alreadyHasAuthorizationHeader(originalRequest)) {
                    return chain.proceed(originalRequest);
                }
                Request authorised = originalRequest.newBuilder()
                        .header("Authorization", TaskData.token)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .addHeader("Cookie", "love")
                        .build();
                return chain.proceed(authorised);
            }
        };

        Interceptor respInterceptor = new Interceptor() {
            @Override public okhttp3.Response intercept(Chain chain) throws IOException {
                //Request originalRequest = chain.request();
                LogUtils.d("respInterceptor");
                okhttp3.Response originalResponse = chain.proceed(chain.request());

                //if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                    final StringBuffer cookieBuffer = new StringBuffer();
                    Observable.from(originalResponse.headers("Set-Cookie"))
                            .map(new Func1<String, String>() {
                                @Override
                                public String call(String s) {
                                    String[] cookieArray = s.split(";");
                                    return cookieArray[0];
                                }
                            })
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String cookie) {
                                    cookieBuffer.append(cookie).append(";");
                                    LogUtils.d(cookieBuffer.toString());
                                }
                            });
               // }
                return originalResponse;

            }
        };
        Interceptor myInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                request = new Request.Builder()
                        .url(url)
                        .method(request.method(), requestBody)
                        .build();
                okhttp3.Response response = chain.proceed(request);
                return response;
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//???車?????
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//????????????
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(mTokenInterceptor)
                .addInterceptor(respInterceptor)
                .addInterceptor(myInterceptor);//????忱??????
        Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);
        builder.cache(cache);
        //builder.addInterceptor(interceptor);
        OkHttpClient mOkHttpClient = builder.build();
        /*
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();

        mOkHttpClient.newCall(request);
        */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url + "/")
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        UserBeanService userService = retrofit.create(UserBeanService.class);

        userService.queryResult().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        //Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show();
                        Log.i(tags,"user data update done");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(context,"not ok",Toast.LENGTH_SHORT).show();
                        Log.i(tags,"retrofit error"+e.toString());
                        Error(e);
                    }

                    @Override
                    public void onNext(String s) {
                        //Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
                        Log.i(tags+"retrofit response", s);
                        if(s != null){
                            //Toast.makeText(context, "??????????",Toast.LENGTH_SHORT).show();
                            Log.d(tags,"json back"+s.toString());

                        }else{
                            //Toast.makeText(context, "no data",Toast.LENGTH_SHORT).show();
                            Log.d(tags,"no data backup");
                        }
                    }
                });

    }

    public void UserLoginRequestPost(String servletName, Map params, final Button btn_login) {
        final String tags="RetrofitUtils|UserLoginRequestPost|";
        final String url = TaskData.hostname + servletName;
        final String phoneNo=params.get("phoneNo").toString();
        //int login_flag;
        JsonArray jolist = new JsonArray();
        JsonObject jostring = new Gson().toJsonTree(params).getAsJsonObject();
        jolist.add(jostring);
        final String postBody = "7" + jolist.toString();
        //final String postBody = "7"+"[]";

        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");
        final RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
        Log.d("postBody", postBody);

        Interceptor myInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                request = new Request.Builder()
                        .url(url)
                        .method(request.method(), requestBody)
                        .build();
                okhttp3.Response response = chain.proceed(request);
                return response;
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//???車?????
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//????????????
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(myInterceptor);//????忱??????
        Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);
        builder.cache(cache);
        //builder.addInterceptor(interceptor);
        OkHttpClient mOkHttpClient = builder.build();
        /*
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();

        mOkHttpClient.newCall(request);
        */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url + "/")
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        UserBeanService userService = retrofit.create(UserBeanService.class);

        userService.queryResult().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        //Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show();
                        Log.i(tags,"user data update done");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(context,"not ok",Toast.LENGTH_SHORT).show();
                        Log.i(tags,"retrofit error"+e.toString());
                        Error(e);
                    }

                    @Override
                    public void onNext(String s) {
                        //Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
                        Log.i(tags+"retrofit response", s);
                        LoginUtils.Login(context, ConvertStr2JSONArray(s),phoneNo,btn_login);
                    }
                });
    }

    interface QueryUserService{
        @POST("http://123.206.229.72:8080/EasyTest/UserServlet")
        Observable<String> queryResult();

        //Call<String> query(@Body RequestBody requestBody);
        //Call<String> query();
        //@POST("http://123.206.229.72:8080/EasyTest/UserServlet")

    }

    public void QueryUser(String servletName, Map<String, String> params) {
        final String tags="RetrofitUtils|QueryUser|";
        final String url = TaskData.hostname + servletName;

        //String queryStr="select * from "+tbname+";";
        //Cursor ca=DataSupport.findBySQL(queryStr);

        JsonArray jolist = new JsonArray();
        JsonObject jostring = new Gson().toJsonTree(params).getAsJsonObject();
        jolist.add(jostring);
        final String postBody = "5" + jolist.toString();
        //final String postBody = "7"+"[]";

        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");
        final RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
        Log.d("postBody", postBody);

        Interceptor myInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //??????????request?????????百???????
                request = new Request.Builder()
                        .url(url)
                        .method(request.method(), requestBody)
                        .build();
                okhttp3.Response response = chain.proceed(request);
                return response;
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//???車?????
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//????????????
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(myInterceptor);//????忱??????
        Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);
        builder.cache(cache);
        //builder.addInterceptor(interceptor);
        OkHttpClient mOkHttpClient = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();

        mOkHttpClient.newCall(request);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url + "/")
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        QueryUserService queryuserService = retrofit.create(QueryUserService.class);

        queryuserService.queryResult().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        //Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show();
                        Log.i(tags+"retrofit user back", "done");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(context,"not ok",Toast.LENGTH_SHORT).show();
                        Log.i(tags+"retrofit user error2", e.toString());

                    }

                    @Override
                    public void onNext(String s) {
                        //Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
                        Log.i(tags+"retrofit response", s);
                        JSONArray gsonArrayback=null;
                        gsonArrayback=ConvertStr2JSONArray(s);
                        GetJSONArray(gsonArrayback);
                        if (gsonArrayback!=null&&gsonArrayback.length()>0){
                            TaskData.flag_findteam=1;
                            //Toast.makeText(context,"find team"+TaskData.flag_findteam,Toast.LENGTH_SHORT).show();
                        }
                        /*
                        Cursor cursor_userbean = DataSupport.findBySQL("select * from UserBean");
                        Toast.makeText(context,"userbean column count"+cursor_userbean.getColumnCount()+" back length:"+gsonArrayback.length() ,Toast.LENGTH_SHORT).show();
                        for (int i=0;i<cursor_userbean.getColumnCount();i++){
                            System.out.println("No"+i+" "+cursor_userbean.getColumnName(i)+"  ");
                        }
                        GsonArray2LitePal(gsonArrayback,"sn","userbean",UserBean.class);
                        */
                        //Toast.makeText(context,"user back"+ TaskData.userinfolist_usergroup.size(),Toast.LENGTH_SHORT).show();
                        //Toast.makeText(context,"user back"+ TaskData.userinfolist_usergroup.get(0).get("nickname").toString(),Toast.LENGTH_SHORT).show();
                        //Log.d("pos",ls.get(pos).get("nickname").toString()+ls.get(pos).get("phoneNo").toString());
                    }
                });

    }
    private JSONArray ConvertStr2JSONArray(String s){

        JSONArray gsonArrayback=null;
        if (!TextUtils.isEmpty(s.trim())&&!s.trim().equals("null")){
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
        JSONObject jo;

        for (int i=0;i<ja.length();i++){

                try {
                    jo = ja.getJSONObject(i);
                    System.out.println("No"+i+" "+jo.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }

    }

    void GetJSONArray(JSONArray ja){
        JSONObject jo;
        Map userrole;
        if (ja.length()>0) {
            TaskData.userinfolist_usergroup=new ArrayList<Map>();
            for (int i = 0; i < ja.length(); i++) {

                try {
                    jo = ja.getJSONObject(i);
                    System.out.println("No" + i + " " + jo.toString());
                    userrole=new HashMap();
                    userrole.put("RongToken",jo.get("RongToken").toString());
                    userrole.put("headpicuri",jo.get("headpicuri").toString());
                    userrole.put("nickname",jo.get("nickname").toString());
                    userrole.put("phoneNo",jo.get("phoneNo").toString());
                    userrole.put("username",jo.get("username").toString());
                    userrole.put("usertype",jo.get("usertype").toString());
                    //userrole.put("title",jo.get("title").toString());
                    if (TaskData.userinfolist_usergroup!=null) {
                        TaskData.userinfolist_usergroup.add(userrole);
                    }
                    System.out.println("add No"+i+" "+jo.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }else{
                    System.out.println("no team member");
        }
    }

/*
    void getJSONArrayLength(JSONArray jolist){

        int jsonLength = 0;

        for(var item in jolist){

            jsonLength++;

        }

        return jsonLength;

    }*/

    interface QueryTeamTaskService{
        @POST("http://123.206.229.72:8080/EasyTest/TaskmainServlet")
        Observable<String> queryResult();

        //Call<String> query(@Body RequestBody requestBody);
        //Call<String> query();
        //@POST("http://123.206.229.72:8080/EasyTest/UserServlet")

    }

    public void QueryTeamTask(String servletName, Map<String, String> params) {
        final String tags="RetrofitUtils|QueryTeamTask|";
        final String url = TaskData.hostname + servletName;

        //String queryStr="select * from "+tbname+";";
        //Cursor ca=DataSupport.findBySQL(queryStr);

        JsonArray jolist = new JsonArray();
        JsonObject jostring = new Gson().toJsonTree(params).getAsJsonObject();
        jolist.add(jostring);
        final String postBody = "5" + jolist.toString();
        //final String postBody = "7"+"[]";

        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");
        final RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
        Log.d("postBody", postBody);

        Interceptor myInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //??????????request?????????百???????
                request = new Request.Builder()
                        .url(url)
                        .method(request.method(), requestBody)
                        .build();
                okhttp3.Response response = chain.proceed(request);
                return response;
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//???車?????
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//????????????
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(myInterceptor);//????忱??????
        Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);
        builder.cache(cache);
        //builder.addInterceptor(interceptor);
        OkHttpClient mOkHttpClient = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();

        mOkHttpClient.newCall(request);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url + "/")
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        QueryUserService queryuserService = retrofit.create(QueryUserService.class);

        queryuserService.queryResult().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        //Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show();
                        Log.i(tags, "complated");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(context,"not ok",Toast.LENGTH_SHORT).show();
                        Log.i(tags+"error", e.toString());

                    }

                    @Override
                    public void onNext(String s) {
                        //Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
                        Log.i(tags+"response", s);
                        JSONArray gsonArrayback=null;
                        if (!TextUtils.isEmpty(s.trim())&&!s.trim().equals("null")){
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
                        GroupGsonArray2Sqlite(gsonArrayback, TaskData.TdDB.TABLE_NAME_TaskMain, "_sn");
                        Log.d(tags, gsonArrayback.toString());
                    }
                });
    }

    interface QueryTeamRecTaskService{
        @POST("http://123.206.229.72:8080/EasyTest/TaskrecServlet")
        Observable<String> queryResult();

        //Call<String> query(@Body RequestBody requestBody);
        //Call<String> query();
        //@POST("http://123.206.229.72:8080/EasyTest/UserServlet")

    }

    public void QueryTeamRec(String servletName, Map<String, String> params) {
        final String tags="RetrofitUtils|QueryTeamRec|";
        final String url = TaskData.hostname + servletName;

        //String queryStr="select * from "+tbname+";";
        //Cursor ca=DataSupport.findBySQL(queryStr);

        JsonArray jolist = new JsonArray();
        JsonObject jostring = new Gson().toJsonTree(params).getAsJsonObject();
        jolist.add(jostring);
        final String postBody = "5" + jolist.toString();
        //final String postBody = "7"+"[]";

        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");
        final RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
        Log.d("postBody", postBody);

        Interceptor myInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //??????????request?????????百???????
                request = new Request.Builder()
                        .url(url)
                        .method(request.method(), requestBody)
                        .build();
                okhttp3.Response response = chain.proceed(request);
                return response;
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//???車?????
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//????????????
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(myInterceptor);//????忱??????
        Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);
        builder.cache(cache);
        //builder.addInterceptor(interceptor);
        OkHttpClient mOkHttpClient = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();

        mOkHttpClient.newCall(request);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url + "/")
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        QueryUserService queryuserService = retrofit.create(QueryUserService.class);

        queryuserService.queryResult().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        //Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show();
                        Log.i(tags, "complated");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(context,"not ok",Toast.LENGTH_SHORT).show();
                        Log.i(tags+"error", e.toString());

                    }

                    @Override
                    public void onNext(String s) {
                        //Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
                        Log.i(tags+"response", s);
                        JSONArray gsonArrayback=null;
                        if (!TextUtils.isEmpty(s.trim())&&!s.trim().equals("null")){
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
                        if (gsonArrayback.length()>0) {
                            GroupGsonArray2Sqlite(gsonArrayback, TaskData.TdDB.TABLE_NAME_TaskRecord, "r_sn");
                            Log.d(tags, gsonArrayback.toString());
                        }
                    }
                });
    }

    void GroupGsonArray2Sqlite(JSONArray ja,String tbname,String index){
        JSONObject jo;
        ContentValues cv;
        Cursor cursor=null;
        String key;
        Object u;
        Iterator<String> it;
        String indexvalue;
        String whereAs;

        try {
            for (int i = 0; i < ja.length(); i++) {

                try {
                    jo = ja.getJSONObject(i);
                    it = jo.keys();
                    //String indexkey = (String) it.next();
                    indexvalue = jo.getString(index);

                     cv = new ContentValues();
                    //it.next();
                    do {
                        key = (String) it.next();
                        u = jo.get(key);
                        //Log.d(key, u.toString());
                        if (!key.equals("_id")) {
                            cv.put(key, u.toString());
                        }
                    } while (it.hasNext());
                    cursor = TaskData.db_TdDB.rawQuery("select * from " + tbname + " where " + TaskData.TdDB.TASK_USERGROUP + "='" + TaskData.usergroup + "'" + " and " + index + "=" + "'" + indexvalue + "'", null);
                    if (cursor!=null&&cursor.getCount() == 0) {
                        Log.d("|insert backdata|", "diff:" + i);
                        long insertcode = TaskData.db_TdDB.insert(tbname, null, cv);
                        Log.d("|insert backdata|", "ok" + insertcode);
                    } else {
                        if (cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            whereAs = index + "=?";
                            String[] whereValue = {cursor.getString(cursor.getColumnIndex(index))};

                            int updatecode = TaskData.db_TdDB.update(tbname, cv, whereAs, whereValue);
                            Log.d("|udpate backdata|", "ok:" + i + " " + updatecode);
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
               e.printStackTrace();
        }finally {
            if (cursor!=null) {
                cursor.close();
            }
        }
    }

    void GsonArray2Sqlite(JSONArray ja,String tbname,String index){
        JSONObject jo;
        ContentValues cv;
        Cursor cursor=null;
        String key;
        Object u;
        Iterator<String> it;
        String indexvalue;
        String whereAs;
        try{
           for (int i=0;i<ja.length();i++){
               try {
                jo = ja.getJSONObject(i);
                it=jo.keys();
                //String indexkey = (String) it.next();
                indexvalue=jo.getString(index);

                cv=new ContentValues();
                //it.next();
                do{
                    key = (String) it.next();
                    u=jo.get(key);
                    //Log.d(key, u.toString());
                    if (!key.equals("_id")){
                        cv.put(key, u.toString());
                    }
                }while(it.hasNext());
                 cursor = TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+"username="+"'"+TaskData.user+"'"+" and "+index+"="+"'"+indexvalue+"'", null);
                if (cursor!=null&&cursor.getCount()==0){
                    Log.d("|insert backdata|", "diff:"+i);
                    long insertcode=TaskData.db_TdDB.insert(tbname, null, cv);
                    Log.d("|insert backdata|", "ok"+insertcode);
                }else{
                    if (cursor!=null&&cursor.getCount()>0){
                        cursor.moveToFirst();
                        whereAs=index+"=?";
                        String[] whereValue={cursor.getString(cursor.getColumnIndex(index))};

                        int updatecode = TaskData.db_TdDB.update(tbname, cv, whereAs, whereValue);
                        Log.d("|udpate backdata|", "ok:"+i+" "+updatecode);
                    }
                }
              } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
             }
          }
        }catch(Exception e){
               e.printStackTrace();
        }finally {
            if (cursor!=null) {
                cursor.close();
            }
        }
    }

    void GsonArray2LitePal(JSONArray ja,String rowId,String tbname,Class classname){
        JSONObject jo;
        ContentValues cv;
        Cursor cursor=null;
        String key;
        Object u;
        Iterator<String> it;
        String indexvalue;
        String whereAs;
        try {
            for (int i=0;i<ja.length();i++){
                cv=new ContentValues();
                try {
                    jo = ja.getJSONObject(i);
                    it=jo.keys();

                    String rowIdvalue=jo.get(rowId).toString();

                    cv.put(rowId, rowIdvalue);
                    it.next();

                    do{
                        key = (String) it.next();
                        u=jo.get(key);
                        cv.put(key, u.toString());

                    }while(it.hasNext());
                    //Cursor cursor = DataSupport.findBySQL("select * from "+tbname+" where "+"username="+"'"+TaskTool.getPhoneNumber(getApplicationContext())+"'"+" and "+rowId+"="+"'"+rowIdvalue+"';");
                    cursor = DataSupport.findBySQL("select * from "+tbname+" where "+rowId+"="+"'"+rowIdvalue+"';");

                    if (cursor != null && cursor.getCount() == 0) {
                        String root =context.getFilesDir().getAbsolutePath();
                        Log.d(Tags + "|root|", root);
                        //String path ="data/data/com.example.easygoal/"+tbname;
                        //String path=root.replace("files", "databases")+File.separator+tbname;
                        String path = root.replace("files", tbname);
                        Log.d(Tags + "|path|", path);
                        //SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
                        //SQLiteDatabase db=this.openOrCreateDatabase("Memo.db", Context.MODE_PRIVATE, null);
                        TaskData.db.insert(tbname, null, cv);
                        //DataSupport.insert(classname,cv);
                        Log.d(Tags + "|insert backdata|", "ok");
                    } else {
					/*
				   cursor.moveToFirst();
				   String whereAs=rowId+"=?";
				   String[] whereValue={cursor.getString(cursor.getColumnIndex(rowId))};
				   DataSupport.update(classname, cv, Long.parseLong(rowIdvalue));
				   Log.d("udpate backdata", "ok");
				   */
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null) {
                cursor.close();
            }
        }
    }



    public void Error(Throwable e){
        String tags="RetrofitUtils|Error|";
        Throwable throwable = e;
        //???????????
        while(throwable.getCause() != null){
            e = throwable;
            throwable = throwable.getCause();
        }

        if (e instanceof HttpException) {             //HTTP????
            HttpException httpException = (HttpException) e;

            switch (httpException.code()) {
                case 404:
                    Log.d(tags, "404 error");
                    break;
                case 422:
                    Log.d(tags, "422 error");
                    break;
                case 401:
                    Log.d(tags, "401 error");
                    break;
                default:
                    Log.d(tags, context.getResources().getString(R.string.generic_server_down));
                    break;
            }
        }
    }

}