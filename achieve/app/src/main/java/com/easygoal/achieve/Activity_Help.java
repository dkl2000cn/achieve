package com.easygoal.achieve;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.baidu.location.LocationClient;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import retrofit2.http.Url;


public class Activity_Help extends AppCompatActivity {
	public LocationClient mLocationClient = null;
	SharedPreferences sp;
	SharedPreferences.Editor editor;
	WebView webView;
	String url;
	final String Tags = "Activity_Help";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_help);
		//EventBus.getDefault().register(getApplicationContext());
		webView = (WebView) findViewById(R.id.ww_help);
		final TextView tv_version = (TextView) findViewById(R.id.tv_version);
		ImageView iv_verbarcode = (ImageView) findViewById(R.id.iv_verbarcode);
		ImageButton btn_back= (ImageButton) findViewById(R.id.ibtn_back);
		btn_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		tv_version.setText("正在检查最新版本...");
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				tv_version.setText("当前正式版本为:"+TaskData.latestVersionName);
			}
		},1000);

		Glide.with(getApplicationContext())
				.load("http://d.hiphotos.bdimg.com/wisegame/pic/item/323d269759ee3d6dd6c9f7d349166d224f4ade9f.jpg")
				.placeholder(R.drawable.qr_wandoujia)
				.dontAnimate()
				.into(iv_verbarcode);
		/*
		ww_help.requestFocus();
		ww_help.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		ww_help.getSettings().setJavaScriptEnabled(true);
		ww_help.getSettings().setDefaultTextEncodingName("utf-8"); // 支持缩放
		ww_help.getSettings().setSupportZoom(true);
		ww_help.getSettings().setDomStorageEnabled(true);
		ww_help.getSettings().setAllowFileAccess(true);*/
		//ww_help.loadUrl("file:///android_asset/help.html");
		//url = "http://123.206.229.72:8080/EasyTest/";
		url="http://shouji.baidu.com/software/11432820.html";
		//showWebview();
		webView.loadUrl(url);
	}
	public void showWebview(){
		//获取websetings 设置
		WebSettings settings = webView.getSettings();
		settings.setSupportZoom(true);
		//设置浏览器支持javaScript
		settings.setJavaScriptEnabled(true);
		//设置打开自带缩放按钮
		settings.setBuiltInZoomControls(true);
		// 进行跳转用户输入的url地址
		webView.loadUrl(url);
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			//速度正在改变
			public void onProgressChanged(WebView view, int newProgress) {
				//progressDialog.setMessage("加载" + newProgress);
				Log.d("1507", "5");
			}
		});
		webView.setWebViewClient(new WebViewClient(){
			@Override
			// 显示读渠道的内容
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				Log.d("1507", "3");
				return true;
			}

			/**
			 * 页面开始的时候 回调此方法
			 * @param view
			 * @param url
			 * @param favicon
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				/*
				if (progressDialog == null){
					progressDialog.setMessage("加载中。。。。。。。。。。。");
				}
				Log.d("1507","1");
				progressDialog.show();*/
			}

			/**
			 * 页面结束的时候 回调此方法
			 * @param view
			 * @param url
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				//progressDialog.dismiss();
//                Log.d("1507", "1");
			}
		});

/*
		ww_help.setWebViewClient(new WebViewClient(){

			@Oerride
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				handler.proceed();

				if (error.getPrimaryError() == SslError.SSL_DATE_INVALID
						|| error.getPrimaryError() == SslError.SSL_EXPIRED
						|| error.getPrimaryError() == SslError.SSL_INVALID
						|| error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
					handler.proceed();
				} else {
					handler.cancel();
				}

				super.onReceivedSslError(view, handler, error);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				Toast.makeText(getApplicationContext(), "ddd"+url, Toast.LENGTH_SHORT).show();
				ww_help.loadUrl("http://www.163.com");
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Toast.makeText(getApplicationContext(), "sss"+url, Toast.LENGTH_SHORT).show();
				ww_help.loadUrl("http://www.163.com");
				super.onPageStarted(view, url, favicon);
			}
		});*/
		//ww_help.loadUrl("http://www.163.com");
		//String url = "file:///android_asset/help.html";
		//ww_help.loadUrl("http://123.206.229.72:8080/EasyTest/");
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//EventBus.getDefault().unregister(getApplicationContext());
	}
}
