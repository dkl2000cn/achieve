package com.easygoal.achieve;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.bumptech.glide.Glide;


public class Activity_Study extends AppCompatActivity {
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

		ImageButton btn_back= (ImageButton) findViewById(R.id.ibtn_back);
		btn_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		/*
		ww_help.requestFocus();
		ww_help.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		ww_help.getSettings().setJavaScriptEnabled(true);
		ww_help.getSettings().setDefaultTextEncodingName("utf-8"); // ֧������
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
		//��ȡwebsetings ����
		WebSettings settings = webView.getSettings();
		settings.setSupportZoom(true);
		//���������֧��javaScript
		settings.setJavaScriptEnabled(true);
		//���ô��Դ����Ű�ť
		settings.setBuiltInZoomControls(true);
		// ������ת�û������url��ַ
		webView.loadUrl(url);
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			//�ٶ����ڸı�
			public void onProgressChanged(WebView view, int newProgress) {
				//progressDialog.setMessage("����" + newProgress);
				Log.d("1507", "5");
			}
		});
		webView.setWebViewClient(new WebViewClient(){
			@Override
			// ��ʾ������������
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				Log.d("1507", "3");
				return true;
			}

			/**
			 * ҳ�濪ʼ��ʱ�� �ص��˷���
			 * @param view
			 * @param url
			 * @param favicon
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				/*
				if (progressDialog == null){
					progressDialog.setMessage("�����С���������������������");
				}
				Log.d("1507","1");
				progressDialog.show();*/
			}

			/**
			 * ҳ�������ʱ�� �ص��˷���
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
